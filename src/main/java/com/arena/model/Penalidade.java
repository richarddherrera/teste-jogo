package com.arena.model;

import com.arena.enums.StatusPenalidade;
import com.arena.enums.TipoPenalidade;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa uma penalidade aplicada a um jogador.
 */
public class Penalidade {
    private Jogador jogador;
    private TipoPenalidade tipo;
    private String descricao;
    private LocalDate data;
    private StatusPenalidade status;

    public Penalidade(Jogador jogador, TipoPenalidade tipo, String descricao) {
        if (jogador == null) {
            throw new IllegalArgumentException("Jogador não pode ser nulo");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de penalidade não pode ser nulo");
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia");
        }

        this.jogador = jogador;
        this.tipo = tipo;
        this.descricao = descricao;
        this.data = LocalDate.now();
        this.status = StatusPenalidade.ATIVA;
    }

    public boolean isAtiva() {
        return status == StatusPenalidade.ATIVA;
    }

    public void cumprir() {
        this.status = StatusPenalidade.CUMPRIDA;
    }

    public void revogar() {
        this.status = StatusPenalidade.REVOGADA;
    }

    // Getters
    public Jogador getJogador() {
        return jogador;
    }

    public TipoPenalidade getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public StatusPenalidade getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Penalidade that = (Penalidade) o;
        return Objects.equals(jogador, that.jogador) && 
               Objects.equals(data, that.data) && 
               tipo == that.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jogador, tipo, data);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s: %s (%s)", 
            data, jogador.getNickname(), tipo, descricao, status);
    }
}
