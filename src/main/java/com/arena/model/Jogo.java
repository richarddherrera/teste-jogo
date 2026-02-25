package com.arena.model;

import com.arena.enums.GeneroJogo;
import com.arena.enums.Plataforma;

import java.util.Objects;

/**
 * Representa um jogo disponível para torneios.
 */
public class Jogo {
    private String nome;
    private GeneroJogo genero;
    private int maxJogadoresPorTime;
    private Plataforma plataforma;

    public Jogo(String nome, GeneroJogo genero, int maxJogadoresPorTime, Plataforma plataforma) {
        validarNome(nome);
        validarMaxJogadores(maxJogadoresPorTime);
        
        this.nome = nome;
        this.genero = genero;
        this.maxJogadoresPorTime = maxJogadoresPorTime;
        this.plataforma = plataforma;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do jogo não pode ser vazio");
        }
    }

    private void validarMaxJogadores(int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("Número máximo de jogadores deve ser maior que zero");
        }
    }

    public String getNome() {
        return nome;
    }

    public GeneroJogo getGenero() {
        return genero;
    }

    public int getMaxJogadoresPorTime() {
        return maxJogadoresPorTime;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jogo jogo = (Jogo) o;
        return Objects.equals(nome, jogo.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return String.format("%s (%s - %s) - Max %d jogadores/time", 
            nome, genero.getDescricao(), plataforma, maxJogadoresPorTime);
    }
}
