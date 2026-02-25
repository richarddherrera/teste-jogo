package com.arena.controller;

import com.arena.dto.CreateJogadorRequest;
import com.arena.dto.JogadorDTO;
import com.arena.enums.Categoria;
import com.arena.exception.NicknameDuplicadoException;
import com.arena.model.Jogador;
import com.arena.repository.JogadorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST para operações com Jogadores.
 */
@RestController
@RequestMapping("/api/jogadores")
@CrossOrigin(origins = "http://localhost:3000")
public class JogadorController {

    @Autowired
    private JogadorRepository jogadorRepository;

    /**
     * Lista todos os jogadores.
     */
    @GetMapping
    public ResponseEntity<List<JogadorDTO>> listarTodos() {
        List<JogadorDTO> jogadores = jogadorRepository.findAll()
            .stream()
            .map(JogadorDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(jogadores);
    }

    /**
     * Busca jogador por nickname.
     */
    @GetMapping("/{nickname}")
    public ResponseEntity<JogadorDTO> buscarPorNickname(@PathVariable String nickname) {
        return jogadorRepository.findByNickname(nickname)
            .map(JogadorDTO::fromEntity)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo jogador.
     * NOTA: Este endpoint está deprecado. Use /api/auth/register para criar novos jogadores.
     */
    @PostMapping
    public ResponseEntity<JogadorDTO> criar(@Valid @RequestBody CreateJogadorRequest request) {
        if (jogadorRepository.existsByNickname(request.getNickname())) {
            throw new NicknameDuplicadoException(request.getNickname());
        }

        // Senha padrão para jogadores criados por este endpoint
        Jogador jogador = new Jogador(
            request.getNickname(),
            request.getNomeReal(),
            request.getEmail(),
            "senhaTemporaria123", // Senha padrão - usuário deve trocar
            request.getDataNascimento()
        );

        Jogador salvo = jogadorRepository.save(jogador);
        return ResponseEntity.status(HttpStatus.CREATED).body(JogadorDTO.fromEntity(salvo));
    }

    /**
     * Atualiza ELO do jogador.
     */
    @PatchMapping("/{nickname}/elo")
    public ResponseEntity<JogadorDTO> atualizarElo(
            @PathVariable String nickname,
            @RequestParam int pontos,
            @RequestParam(defaultValue = "ganhar") String acao) {
        
        return jogadorRepository.findByNickname(nickname)
            .map(jogador -> {
                if ("ganhar".equals(acao)) {
                    jogador.ganharElo(pontos);
                } else {
                    jogador.perderElo(pontos);
                }
                Jogador atualizado = jogadorRepository.save(jogador);
                return ResponseEntity.ok(JogadorDTO.fromEntity(atualizado));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retorna ranking global (top 10).
     */
    @GetMapping("/ranking")
    public ResponseEntity<List<JogadorDTO>> ranking(@RequestParam(defaultValue = "10") int limit) {
        List<JogadorDTO> ranking = jogadorRepository.findTopByOrderByEloDesc()
            .stream()
            .limit(limit)
            .map(JogadorDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(ranking);
    }

    /**
     * Retorna jogadores por categoria.
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<JogadorDTO>> porCategoria(@PathVariable Categoria categoria) {
        List<JogadorDTO> jogadores = jogadorRepository.findByCategoria(categoria)
            .stream()
            .map(JogadorDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(jogadores);
    }

    /**
     * Deleta um jogador.
     */
    @DeleteMapping("/{nickname}")
    public ResponseEntity<Void> deletar(@PathVariable String nickname) {
        return jogadorRepository.findByNickname(nickname)
            .map(jogador -> {
                jogadorRepository.delete(jogador);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
