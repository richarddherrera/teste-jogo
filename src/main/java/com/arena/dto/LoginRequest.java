package com.arena.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    @NotBlank(message = "Nickname é obrigatório")
    private String nickname;
    
    @NotBlank(message = "Senha é obrigatória")
    private String senha;
}
