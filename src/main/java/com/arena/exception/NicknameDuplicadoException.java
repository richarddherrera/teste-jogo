package com.arena.exception;

/**
 * Exceção lançada quando se tenta cadastrar um jogador com nickname já existente.
 */
public class NicknameDuplicadoException extends RuntimeException {
    
    public NicknameDuplicadoException(String nickname) {
        super(String.format("Já existe um jogador cadastrado com o nickname '%s'", nickname));
    }
}
