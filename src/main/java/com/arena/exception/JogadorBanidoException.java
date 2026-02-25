package com.arena.exception;

/**
 * Exceção lançada quando um jogador banido tenta realizar uma ação não permitida.
 */
public class JogadorBanidoException extends RuntimeException {
    
    public JogadorBanidoException(String nickname, String acao) {
        super(String.format("Jogador '%s' está banido e não pode %s", nickname, acao));
    }
}
