package com.arena.exception;

/**
 * Exceção lançada quando se tenta inscrever um participante em um torneio lotado.
 */
public class TorneioLotadoException extends RuntimeException {
    
    public TorneioLotadoException(String message) {
        super(message);
    }
    
    public TorneioLotadoException(String nomeTorneio, int maxParticipantes) {
        super(String.format("Torneio '%s' já atingiu o limite de %d participantes", 
            nomeTorneio, maxParticipantes));
    }
}
