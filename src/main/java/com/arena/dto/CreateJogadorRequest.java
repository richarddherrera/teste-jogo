package com.arena.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Request para criação de jogador.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateJogadorRequest {
    
    @NotBlank(message = "Nickname é obrigatório")
    private String nickname;
    
    @NotBlank(message = "Nome real é obrigatório")
    private String nomeReal;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;
}
