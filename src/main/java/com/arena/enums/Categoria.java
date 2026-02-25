package com.arena.enums;

/**
 * Representa as categorias de ranking dos jogadores baseadas no ELO.
 */
public enum Categoria {
    BRONZE(0, 999),
    PRATA(1000, 1499),
    OURO(1500, 1999),
    DIAMANTE(2000, 2499),
    MESTRE(2500, Integer.MAX_VALUE);

    private final int eloMinimo;
    private final int eloMaximo;

    Categoria(int eloMinimo, int eloMaximo) {
        this.eloMinimo = eloMinimo;
        this.eloMaximo = eloMaximo;
    }

    public int getEloMinimo() {
        return eloMinimo;
    }

    public int getEloMaximo() {
        return eloMaximo;
    }

    /**
     * Retorna a categoria correspondente ao ELO fornecido.
     * 
     * @param elo o valor do ELO do jogador
     * @return a categoria correspondente
     */
    public static Categoria getCategoriaPorElo(int elo) {
        for (Categoria categoria : values()) {
            if (elo >= categoria.eloMinimo && elo <= categoria.eloMaximo) {
                return categoria;
            }
        }
        return BRONZE; // fallback
    }
}
