package com.arena.exception;

/**
 * Exceção lançada quando se tenta inscrever em um torneio com inscrições fechadas.
 */
public class InscricaoFechadaException extends RuntimeException {
    
    public InscricaoFechadaException(String message) {
        super(message);
    }
    
    public InscricaoFechadaException(String nomeTorneio) {
        super(String.format("As inscrições para o torneio '%s' estão fechadas", nomeTorneio));
    }
}
