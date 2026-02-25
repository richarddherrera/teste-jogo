package com.arena.exception;

/**
 * Exceção lançada quando um time não atende aos requisitos mínimos.
 */
public class TimeIncompletoException extends RuntimeException {
    
    public TimeIncompletoException(String nomeTime, String motivo) {
        super(String.format("Time '%s' está incompleto: %s", nomeTime, motivo));
    }
}
