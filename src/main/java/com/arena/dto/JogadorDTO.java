package com.arena.dto;

import com.arena.enums.Categoria;
import com.arena.enums.StatusJogador;
import com.arena.model.Jogador;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO para transferência de dados de Jogador.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JogadorDTO {
    private Long id;
    private String nickname;
    private String nomeReal;
    private String email;
    private LocalDate dataNascimento;
    private int elo;
    private StatusJogador status;
    private Categoria categoria;
    
    // Estatísticas
    private int totalPartidas;
    private int vitorias;
    private int derrotas;
    private int kills;
    private int deaths;
    private int assists;
    private double kdRatio;
    private double winRate;
    private long tempoJogoMinutos;
    private String modoFavorito;
    private LocalDateTime ultimoLogin;
    private LocalDateTime dataCriacao;

    public static JogadorDTO fromEntity(Jogador jogador) {
        JogadorDTO dto = new JogadorDTO();
        dto.setId(jogador.getId());
        dto.setNickname(jogador.getNickname());
        dto.setNomeReal(jogador.getNomeReal());
        dto.setEmail(jogador.getEmail());
        dto.setDataNascimento(jogador.getDataNascimento());
        dto.setElo(jogador.getElo());
        dto.setStatus(jogador.getStatus());
        dto.setCategoria(jogador.getCategoria());
        dto.setTotalPartidas(jogador.getTotalPartidas());
        dto.setVitorias(jogador.getVitorias());
        dto.setDerrotas(jogador.getDerrotas());
        dto.setKills(jogador.getKills());
        dto.setDeaths(jogador.getDeaths());
        dto.setAssists(jogador.getAssists());
        dto.setKdRatio(jogador.getKdRatio());
        dto.setWinRate(jogador.getWinRate());
        dto.setTempoJogoMinutos(jogador.getTempoJogoMinutos());
        dto.setModoFavorito(jogador.getModoFavorito());
        dto.setUltimoLogin(jogador.getUltimoLogin());
        dto.setDataCriacao(jogador.getDataCriacao());
        return dto;
    }
}
