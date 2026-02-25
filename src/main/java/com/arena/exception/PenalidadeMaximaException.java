package com.arena.exception;

/**
 * Exceção lançada quando um jogador atinge o limite de penalidades e é banido.
 */
public class PenalidadeMaximaException extends RuntimeException {
    
    public PenalidadeMaximaException(String nickname, int totalPenalidades) {
        super(String.format("Jogador '%s' atingiu %d penalidades ativas e foi banido automaticamente", 
            nickname, totalPenalidades));
    }
}
