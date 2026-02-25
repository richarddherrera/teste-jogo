package com.arena.model;

import com.arena.enums.Categoria;
import com.arena.enums.StatusJogador;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa um jogador no sistema de torneios.
 */
@Entity
@Table(name = "jogadores")
@Getter
@Setter
@NoArgsConstructor
public class Jogador {
    private static final int ELO_INICIAL = 1000;
    private static final int ELO_MINIMO = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String nomeReal;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha; // Será criptografada

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private int elo = ELO_INICIAL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusJogador status = StatusJogador.ATIVO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    // Estatísticas
    @Column(nullable = false)
    private int totalPartidas = 0;

    @Column(nullable = false)
    private int vitorias = 0;

    @Column(nullable = false)
    private int derrotas = 0;

    @Column(nullable = false)
    private int kills = 0;

    @Column(nullable = false)
    private int deaths = 0;

    @Column(nullable = false)
    private int assists = 0;

    @Column(nullable = false)
    private long tempoJogoMinutos = 0;

    @Column
    private String modoFavorito;

    @Column
    private LocalDateTime ultimoLogin;

    @Column
    private LocalDateTime dataCriacao;

    public Jogador(String nickname, String nomeReal, String email, String senha, LocalDate dataNascimento) {
        validarCampos(nickname, nomeReal, email, dataNascimento);
        
        this.nickname = nickname;
        this.nomeReal = nomeReal;
        this.email = email;
        this.senha = senha; // Será criptografada no service
        this.dataNascimento = dataNascimento;
        this.elo = ELO_INICIAL;
        this.status = StatusJogador.ATIVO;
        this.categoria = Categoria.getCategoriaPorElo(this.elo);
        this.dataCriacao = LocalDateTime.now();
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
     * Registra uma vitória.
     */
    public void registrarVitoria() {
        this.totalPartidas++;
        this.vitorias++;
    }

    /**
     * Registra uma derrota.
     */
    public void registrarDerrota() {
        this.totalPartidas++;
        this.derrotas++;
    }

    /**
     * Calcula o K/D ratio.
     */
    public double getKdRatio() {
        if (deaths == 0) return kills;
        return (double) kills / deaths;
    }

    /**
     * Calcula o win rate.
     */
    public double getWinRate() {
        if (totalPartidas == 0) return 0.0;
        return (double) vitorias / totalPartidas;
    }

    public boolean isBanido() {
        return this.status == StatusJogador.BANIDO;
    }

    public boolean isAtivo() {
        return this.status == StatusJogador.ATIVO;
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
