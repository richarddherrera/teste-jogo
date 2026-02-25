package com.arena.model;

import com.arena.enums.Categoria;
import com.arena.enums.StatusJogador;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representa um jogador no sistema de torneios.
 */
public class Jogador extends Participante {
    private static final int ELO_INICIAL = 1000;
    private static final int ELO_MINIMO = 0;

    private String nickname;
    private String nomeReal;
    private String email;
    private LocalDate dataNascimento;
    private int elo;
    private StatusJogador status;
    private Categoria categoria;

    public Jogador(String nickname, String nomeReal, String email, LocalDate dataNascimento) {
        validarCampos(nickname, nomeReal, email, dataNascimento);
        
        this.nickname = nickname;
        this.nomeReal = nomeReal;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.elo = ELO_INICIAL;
        this.status = StatusJogador.ATIVO;
        this.categoria = Categoria.getCategoriaPorElo(this.elo);
    }

    private void validarCampos(String nickname, String nomeReal, String email, LocalDate dataNascimento) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("Nickname não pode ser vazio");
        }
        if (nomeReal == null || nomeReal.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome real não pode ser vazio");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        if (dataNascimento == null || dataNascimento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento inválida");
        }
    }

    /**
     * Adiciona pontos de ELO ao jogador e atualiza sua categoria.
     * 
     * @param pontos quantidade de pontos a adicionar
     */
    public void ganharElo(int pontos) {
        if (pontos < 0) {
            throw new IllegalArgumentException("Pontos devem ser positivos");
        }
        this.elo += pontos;
        atualizarCategoria();
    }

    /**
     * Remove pontos de ELO do jogador e atualiza sua categoria.
     * O ELO nunca fica negativo.
     * 
     * @param pontos quantidade de pontos a remover
     */
    public void perderElo(int pontos) {
        if (pontos < 0) {
            throw new IllegalArgumentException("Pontos devem ser positivos");
        }
        this.elo = Math.max(ELO_MINIMO, this.elo - pontos);
        atualizarCategoria();
    }

    /**
     * Atualiza a categoria do jogador baseada no ELO atual.
     */
    public void atualizarCategoria() {
        this.categoria = Categoria.getCategoriaPorElo(this.elo);
    }

    /**
     * Verifica se o jogador está banido.
     * 
     * @return true se o jogador está banido
     */
    public boolean isBanido() {
        return this.status == StatusJogador.BANIDO;
    }

    /**
     * Verifica se o jogador está ativo.
     * 
     * @return true se o jogador está ativo
     */
    public boolean isAtivo() {
        return this.status == StatusJogador.ATIVO;
    }

    @Override
    public String getNome() {
        return nickname;
    }

    @Override
    public List<Jogador> getMembros() {
        return Collections.singletonList(this);
    }

    // Getters e Setters
    public String getNickname() {
        return nickname;
    }

    public String getNomeReal() {
        return nomeReal;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public int getElo() {
        return elo;
    }

    public StatusJogador getStatus() {
        return status;
    }

    public void setStatus(StatusJogador status) {
        this.status = status;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jogador jogador = (Jogador) o;
        return Objects.equals(nickname, jogador.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (ELO: %d) - %s", 
            categoria, nickname, nomeReal, elo, status);
    }
}
