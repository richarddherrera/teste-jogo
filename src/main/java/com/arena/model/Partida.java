package com.arena.model;

import com.arena.enums.StatusPartida;
import com.arena.enums.TipoPenalidade;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa uma partida entre dois participantes.
 */
public class Partida {
    private static final int ELO_VITORIA = 25;
    private static final int ELO_DERROTA = 15;
    private static final int ELO_EMPATE = 5;
    private static final int ELO_WO_VENCEDOR = 10;
    private static final int ELO_WO_PERDEDOR = 30;

    private Torneio torneio;
    private Participante participante1;
    private Participante participante2;
    private int placar1;
    private int placar2;
    private LocalDateTime dataHora;
    private StatusPartida status;
    private int rodada;
    private Participante vencedor;

    public Partida(Torneio torneio, Participante participante1, Participante participante2, int rodada) {
        validarCampos(torneio, participante1, participante2);
        
        this.torneio = torneio;
        this.participante1 = participante1;
        this.participante2 = participante2;
        this.rodada = rodada;
        this.dataHora = LocalDateTime.now();
        this.status = StatusPartida.AGENDADA;
        this.placar1 = 0;
        this.placar2 = 0;
    }

    private void validarCampos(Torneio torneio, Participante p1, Participante p2) {
        if (torneio == null) {
            throw new IllegalArgumentException("Torneio não pode ser nulo");
        }
        if (p1 == null || p2 == null) {
            throw new IllegalArgumentException("Participantes não podem ser nulos");
        }
        if (p1.equals(p2)) {
            throw new IllegalArgumentException("Participantes devem ser diferentes");
        }
    }

    /**
     * Finaliza a partida com o placar informado e distribui ELO.
     * 
     * @param placar1 placar do participante 1
     * @param placar2 placar do participante 2
     */
    public void finalizar(int placar1, int placar2) {
        if (status == StatusPartida.FINALIZADA || status == StatusPartida.WO) {
            throw new IllegalStateException("Partida já foi finalizada");
        }
        if (placar1 < 0 || placar2 < 0) {
            throw new IllegalArgumentException("Placar não pode ser negativo");
        }

        this.placar1 = placar1;
        this.placar2 = placar2;
        this.status = StatusPartida.FINALIZADA;

        distribuirElo();
        definirVencedor();
    }

    private void distribuirElo() {
        if (isEmpate()) {
            // Empate: ambos ganham +5 ELO
            distribuirEloPara(participante1, ELO_EMPATE);
            distribuirEloPara(participante2, ELO_EMPATE);
        } else {
            Participante vencedor = placar1 > placar2 ? participante1 : participante2;
            Participante perdedor = placar1 > placar2 ? participante2 : participante1;

            // Vencedor ganha +25 ELO
            distribuirEloPara(vencedor, ELO_VITORIA);
            // Perdedor perde -15 ELO
            removerEloDe(perdedor, ELO_DERROTA);
        }
    }

    /**
     * Registra um WO (walkover) quando um participante não comparece.
     * 
     * @param ausente o participante que não compareceu
     * @return a penalidade gerada
     */
    public Penalidade darWO(Participante ausente) {
        if (status == StatusPartida.FINALIZADA || status == StatusPartida.WO) {
            throw new IllegalStateException("Partida já foi finalizada");
        }
        if (!ausente.equals(participante1) && !ausente.equals(participante2)) {
            throw new IllegalArgumentException("Participante não está nesta partida");
        }

        this.status = StatusPartida.WO;
        Participante presente = ausente.equals(participante1) ? participante2 : participante1;

        // Ausente perde -30 ELO
        removerEloDe(ausente, ELO_WO_PERDEDOR);
        // Presente ganha +10 ELO
        distribuirEloPara(presente, ELO_WO_VENCEDOR);

        this.vencedor = presente;

        // Criar penalidade para o primeiro membro do time/jogador ausente
        Jogador jogadorPenalizado = ausente.getMembros().get(0);
        return new Penalidade(
            jogadorPenalizado, 
            TipoPenalidade.WO, 
            String.format("WO na partida contra %s", presente.getNome())
        );
    }

    private void distribuirEloPara(Participante participante, int pontos) {
        for (Jogador jogador : participante.getMembros()) {
            jogador.ganharElo(pontos);
        }
    }

    private void removerEloDe(Participante participante, int pontos) {
        for (Jogador jogador : participante.getMembros()) {
            jogador.perderElo(pontos);
        }
    }

    private void definirVencedor() {
        if (isEmpate()) {
            this.vencedor = null;
        } else {
            this.vencedor = placar1 > placar2 ? participante1 : participante2;
        }
    }

    /**
     * Verifica se a partida terminou em empate.
     * 
     * @return true se houve empate
     */
    public boolean isEmpate() {
        return placar1 == placar2 && status == StatusPartida.FINALIZADA;
    }

    public void iniciar() {
        if (status != StatusPartida.AGENDADA) {
            throw new IllegalStateException("Partida não está agendada");
        }
        this.status = StatusPartida.EM_ANDAMENTO;
        this.dataHora = LocalDateTime.now();
    }

    // Getters
    public Torneio getTorneio() {
        return torneio;
    }

    public Participante getParticipante1() {
        return participante1;
    }

    public Participante getParticipante2() {
        return participante2;
    }

    public int getPlacar1() {
        return placar1;
    }

    public int getPlacar2() {
        return placar2;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public StatusPartida getStatus() {
        return status;
    }

    public int getRodada() {
        return rodada;
    }

    public Participante getVencedor() {
        return vencedor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partida partida = (Partida) o;
        return Objects.equals(torneio, partida.torneio) && 
               Objects.equals(participante1, partida.participante1) && 
               Objects.equals(participante2, partida.participante2) && 
               rodada == partida.rodada;
    }

    @Override
    public int hashCode() {
        return Objects.hash(torneio, participante1, participante2, rodada);
    }

    @Override
    public String toString() {
        String resultado = status == StatusPartida.FINALIZADA || status == StatusPartida.WO
            ? String.format("%d x %d", placar1, placar2)
            : "vs";
        
        return String.format("[Rodada %d] %s %s %s - %s", 
            rodada, participante1.getNome(), resultado, participante2.getNome(), status);
    }
}
