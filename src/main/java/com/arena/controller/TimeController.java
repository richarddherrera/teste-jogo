package com.arena.controller;

import com.arena.model.Time;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Endpoints simples para gerenciamento de times.
 * Como a aplicação ainda não possui persistência para times, os dados
 * ficam em memória e são reinicializados a cada reinício da API.
 */
@RestController
@RequestMapping("/api/times")
@CrossOrigin(origins = "http://localhost:3000")
public class TimeController {

    private final List<Time> times = new ArrayList<>();

    // alguns times de exemplo para que a lista não fique vazia na primeira
    // execução. Usamos o próprio modelo para montar objetos mínimos.
    public TimeController() {
        try {
            // depende de classes Jogo e Jogador; criamos instâncias simplórias
            Jogador dummy = new Jogador("aspas", "Erick Santos", "", "", LocalDate.now());
            Jogo jogo = new Jogo("Valorant", GeneroJogo.FPS, 5, Plataforma.PC);
            Time t = new Time("Time Exemplo", "EXM", dummy, jogo);
            times.add(t);
        } catch (Exception e) {
            // caso algo falhe não interrompemos a aplicação
        }
    }

    @GetMapping
    public ResponseEntity<List<Time>> listarTodos() {
        return ResponseEntity.ok(times);
    }

    @PostMapping
    public ResponseEntity<Time> criar(@RequestBody Time time) {
        // validações básicas feitas no próprio modelo
        times.add(time);
        return ResponseEntity.status(HttpStatus.CREATED).body(time);
    }

    @DeleteMapping
    public ResponseEntity<Void> limpar() {
        times.clear();
        return ResponseEntity.noContent().build();
    }
}