package com.arena.controller;

import com.arena.dto.AuthResponse;
import com.arena.dto.JogadorDTO;
import com.arena.dto.LoginRequest;
import com.arena.dto.RegisterRequest;
import com.arena.exception.NicknameDuplicadoException;
import com.arena.model.Jogador;
import com.arena.repository.JogadorRepository;
import com.arena.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Controller para autenticação (login e registro).
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Registra um novo jogador.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        // Verificar se nickname já existe
        if (jogadorRepository.existsByNickname(request.getNickname())) {
            throw new NicknameDuplicadoException(request.getNickname());
        }

        // Verificar se email já existe
        if (jogadorRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        // Criar jogador
        String senhaCriptografada = passwordEncoder.encode(request.getSenha());
        Jogador jogador = new Jogador(
            request.getNickname(),
            request.getNomeReal(),
            request.getEmail(),
            senhaCriptografada,
            request.getDataNascimento()
        );

        Jogador salvo = jogadorRepository.save(jogador);

        // Gerar token
        String token = jwtUtil.generateToken(salvo.getNickname());

        AuthResponse response = new AuthResponse(token, JogadorDTO.fromEntity(salvo));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Faz login de um jogador.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Jogador jogador = jogadorRepository.findByNickname(request.getNickname())
            .orElseThrow(() -> new IllegalArgumentException("Nickname ou senha inválidos"));

        // Verificar senha
        if (!passwordEncoder.matches(request.getSenha(), jogador.getSenha())) {
            throw new IllegalArgumentException("Nickname ou senha inválidos");
        }

        // Atualizar último login
        jogador.setUltimoLogin(LocalDateTime.now());
        jogadorRepository.save(jogador);

        // Gerar token
        String token = jwtUtil.generateToken(jogador.getNickname());

        AuthResponse response = new AuthResponse(token, JogadorDTO.fromEntity(jogador));
        return ResponseEntity.ok(response);
    }

    /**
     * Verifica se o token é válido.
     */
    @GetMapping("/validate")
    public ResponseEntity<JogadorDTO> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7); // Remove "Bearer "
            String nickname = jwtUtil.extractNickname(token);
            
            Jogador jogador = jogadorRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Jogador não encontrado"));

            if (jwtUtil.validateToken(token, nickname)) {
                return ResponseEntity.ok(JogadorDTO.fromEntity(jogador));
            }
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
