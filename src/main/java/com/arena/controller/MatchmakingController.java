package com.arena.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador simples para simular uma fila de matchmaking. Os jogadores que
 * participam são apenas identificados pelo nickname e guardados em lista
 * em memória.
 */
@RestController
@RequestMapping("/api/matchmaking")
@CrossOrigin(origins = "http://localhost:3000")
public class MatchmakingController {

    private final List<String> fila = new ArrayList<>();

    @GetMapping("/fila")
    public ResponseEntity<List<String>> verFila() {
        return ResponseEntity.ok(fila);
    }

    @PostMapping("/entrar")
    public ResponseEntity<Void> entrar(@RequestParam String nickname) {
        if (!fila.contains(nickname)) {
            fila.add(nickname);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sair")
    public ResponseEntity<Void> sair(@RequestParam String nickname) {
        fila.remove(nickname);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> limpar() {
        fila.clear();
        return ResponseEntity.noContent().build();
    }
}