package com.arena.model;

import java.util.Objects;

/**
 * Representa a premiação de um participante em um torneio.
 */
public class Premiacao {
    private Torneio torneio;
    private Participante participante;
    private int colocacao;
    private double valor;

    public Premiacao(Torneio torneio, Participante participante, int colocacao, double valor) {
        if (torneio == null) {
            throw new IllegalArgumentException("Torneio não pode ser nulo");
        }
        if (participante == null) {
            throw new IllegalArgumentException("Participante não pode ser nulo");
        }
        if (colocacao < 1 || colocacao > 3) {
            throw new IllegalArgumentException("Colocação deve ser entre 1 e 3");
        }
        if (valor < 0) {
            throw new IllegalArgumentException("Valor não pode ser negativo");
        }

        this.torneio = torneio;
        this.participante = participante;
        this.colocacao = colocacao;
        this.valor = valor;
    }

    /**
     * Calcula o valor por membro se o participante for um time.
     * 
     * @return valor dividido pelo número de membros
     */
    public double getValorPorMembro() {
        int numMembros = participante.getMembros().size();
        return valor / numMembros;
    }

    public String getEmoji() {
        return switch (colocacao) {
            case 1 -> "🥇";
            case 2 -> "🥈";
            case 3 -> "🥉";
            default -> "";
        };
    }

    // Getters
    public Torneio getTorneio() {
        return torneio;
    }

    public Participante getParticipante() {
        return participante;
    }

    public int getColocacao() {
        return colocacao;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Premiacao that = (Premiacao) o;
        return colocacao == that.colocacao && 
               Objects.equals(torneio, that.torneio) && 
               Objects.equals(participante, that.participante);
    }

    @Override
    public int hashCode() {
        return Objects.hash(torneio, participante, colocacao);
    }

    @Override
    public String toString() {
        return String.format("%s %dº lugar - %s: R$ %.2f (R$ %.2f por membro)", 
            getEmoji(), colocacao, participante.getNome(), valor, getValorPorMembro());
    }
}
