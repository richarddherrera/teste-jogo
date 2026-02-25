package com.arena.dto;

import com.arena.enums.Categoria;
import com.arena.enums.StatusJogador;
import com.arena.model.Jogador;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    public static JogadorDTO fromEntity(Jogador jogador) {
        return new JogadorDTO(
            jogador.getId(),
            jogador.getNickname(),
            jogador.getNomeReal(),
            jogador.getEmail(),
            jogador.getDataNascimento(),
            jogador.getElo(),
            jogador.getStatus(),
            jogador.getCategoria()
        );
    }
}
