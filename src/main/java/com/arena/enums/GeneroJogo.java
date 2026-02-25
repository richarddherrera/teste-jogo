package com.arena.enums;

/**
 * Representa os gêneros de jogos disponíveis no sistema.
 */
public enum GeneroJogo {
    FPS("First Person Shooter"),
    MOBA("Multiplayer Online Battle Arena"),
    BATTLE_ROYALE("Battle Royale"),
    FIGHTING("Fighting Game"),
    RACING("Racing Game"),
    SPORTS("Sports Game"),
    RTS("Real-Time Strategy"),
    CARD_GAME("Card Game");

    private final String descricao;

    GeneroJogo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
