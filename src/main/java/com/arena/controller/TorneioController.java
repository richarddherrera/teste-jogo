package com.arena.controller;

import com.arena.model.Torneio;
import com.arena.model.Jogo;
import com.arena.enums.FormatoTorneio;
import com.arena.enums.GeneroJogo;
import com.arena.enums.Plataforma;
import com.arena.enums.StatusTorneio;

import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controle mínimo para torneios. Assim como os times, não há persistência -
 * apenas lista em memória para permitir que o front‑end consulte algo e não
 * receba 404.
 */
@RestController
@RequestMapping("/api/torneios")
@CrossOrigin(origins = "http://localhost:3000")
public class TorneioController {

    private final List<Torneio> torneios = new ArrayList<>();

    // adiciona um torneio de exemplo na inicialização para facilitar testes
    public TorneioController() {
        try {
            // construímos rapidamente objetos necessários
            Jogo jogo = new Jogo("Valorant", GeneroJogo.FPS, 5, Plataforma.PC);
            Torneio t = new Torneio(
                    "Torneio Exemplo",
                    jogo,
                    FormatoTorneio.ELIMINACAO_SIMPLES,
                    LocalDate.now().plusDays(7),
                    LocalDate.now().plusDays(8),
                    StatusTorneio.INSCRICOES_ABERTAS,
                    5000,
                    new ArrayList<>(),
                    new ArrayList<>());
            torneios.add(t);
        } catch (Exception e) {
            // se algo falhar não atrapalha a inicialização
        }
    }

    @GetMapping
    public ResponseEntity<List<Torneio>> listar() {
        return ResponseEntity.ok(torneios);
    }

    @PostMapping
    public ResponseEntity<Torneio> criar(@RequestBody Torneio t) {
        torneios.add(t);
        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }

    @DeleteMapping
    public ResponseEntity<Void> limpar() {
        torneios.clear();
        return ResponseEntity.noContent().build();
    }
}