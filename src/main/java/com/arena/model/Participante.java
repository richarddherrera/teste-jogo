package com.arena.model;

import java.util.List;

/**
 * Classe abstrata que representa um participante de torneio.
 * Pode ser um jogador solo ou um time.
 */
public abstract class Participante {
    
    /**
     * Retorna o nome do participante.
     * 
     * @return o nome do participante
     */
    public abstract String getNome();
    
    /**
     * Retorna a lista de jogadores que compõem este participante.
     * Para jogador solo, retorna uma lista contendo apenas ele mesmo.
     * Para time, retorna todos os membros.
     * 
     * @return lista de jogadores
     */
    public abstract List<Jogador> getMembros();
}
