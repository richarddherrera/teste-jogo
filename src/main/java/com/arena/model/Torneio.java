package com.arena.model;

import com.arena.enums.FormatoTorneio;
import com.arena.enums.StatusTorneio;
import com.arena.exception.InscricaoFechadaException;
import com.arena.exception.JogadorBanidoException;
import com.arena.exception.TimeIncompletoException;
import com.arena.exception.TorneioLotadoException;

import java.time.LocalDate;
import java.util.*;

/**
 * Representa um torneio de e-sports.
 */
public class Torneio {
    private static final int MIN_PARTICIPANTES = 4;
    private static final int MAX_PARTICIPANTES = 32;

    private String nome;
    private Jogo jogo;
    private FormatoTorneio formato;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private StatusTorneio status;
    private double premioTotal;
    private List<Participante> participantes;
    private List<Partida> partidas;

    public Torneio(String nome, Jogo jogo, FormatoTorneio formato, LocalDate dataInicio, 
                   LocalDate dataFim, double premioTotal) {
        validarCampos(nome, jogo, formato, dataInicio, dataFim, premioTotal);
        
        this.nome = nome;
        this.jogo = jogo;
        this.formato = formato;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.premioTotal = premioTotal;
        this.status = StatusTorneio.INSCRICOES_ABERTAS;
        this.participantes = new ArrayList<>();
        this.partidas = new ArrayList<>();
    }

    private void validarCampos(String nome, Jogo jogo, FormatoTorneio formato, 
                               LocalDate dataInicio, LocalDate dataFim, double premioTotal) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do torneio não pode ser vazio");
        }
        if (jogo == null) {
            throw new IllegalArgumentException("Jogo não pode ser nulo");
        }
        if (formato == null) {
            throw new IllegalArgumentException("Formato não pode ser nulo");
        }
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }
        if (dataFim.isBefore(dataInicio)) {
            throw new IllegalArgumentException("Data de fim não pode ser antes da data de início");
        }
        if (premioTotal < 0) {
            throw new IllegalArgumentException("Prêmio total não pode ser negativo");
        }
    }

    /**
     * Inscreve um participante no torneio.
     * Valida todas as regras de negócio.
     * 
     * @param participante o participante a ser inscrito
     * @throws InscricaoFechadaException se as inscrições não estão abertas
     * @throws TorneioLotadoException se o torneio já está lotado
     * @throws JogadorBanidoException se algum jogador está banido
     * @throws TimeIncompletoException se o time não atende aos requisitos
     */
    public void inscrever(Participante participante) {
        validarInscricao(participante);
        
        if (participantes.contains(participante)) {
            throw new IllegalArgumentException(
                String.format("Participante '%s' já está inscrito", participante.getNome())
            );
        }
        
        participantes.add(participante);
    }

    private void validarInscricao(Participante participante) {
        if (status != StatusTorneio.INSCRICOES_ABERTAS) {
            throw new InscricaoFechadaException(nome);
        }
        
        if (participantes.size() >= MAX_PARTICIPANTES) {
            throw new TorneioLotadoException(nome, MAX_PARTICIPANTES);
        }
        
        // Validar jogadores
        for (Jogador jogador : participante.getMembros()) {
            if (jogador.isBanido()) {
                throw new JogadorBanidoException(
                    jogador.getNickname(), 
                    "participar de torneios"
                );
            }
            if (!jogador.isAtivo()) {
                throw new JogadorBanidoException(
                    jogador.getNickname(), 
                    "participar de torneios (jogador inativo)"
                );
            }
        }
        
        // Validar time
        if (participante instanceof Time time) {
            if (!time.todosAtivos()) {
                throw new TimeIncompletoException(
                    time.getNome(), 
                    "nem todos os membros estão ativos"
                );
            }
            
            if (time.getMembros().size() > jogo.getMaxJogadoresPorTime()) {
                throw new TimeIncompletoException(
                    time.getNome(), 
                    String.format("time tem mais jogadores que o permitido para %s (%d)", 
                        jogo.getNome(), jogo.getMaxJogadoresPorTime())
                );
            }
        }
    }

    /**
     * Inicia o torneio se houver participantes suficientes.
     * 
     * @throws IllegalStateException se não houver participantes suficientes
     */
    public void iniciar() {
        if (status != StatusTorneio.INSCRICOES_ABERTAS) {
            throw new IllegalStateException("Torneio não está com inscrições abertas");
        }
        
        if (participantes.size() < MIN_PARTICIPANTES) {
            throw new IllegalStateException(
                String.format("Torneio precisa de no mínimo %d participantes (atual: %d)", 
                    MIN_PARTICIPANTES, participantes.size())
            );
        }
        
        this.status = StatusTorneio.EM_ANDAMENTO;
    }

    /**
     * Finaliza o torneio.
     */
    public void finalizar() {
        if (status != StatusTorneio.EM_ANDAMENTO) {
            throw new IllegalStateException("Torneio não está em andamento");
        }
        
        this.status = StatusTorneio.FINALIZADO;
    }

    /**
     * Cancela o torneio.
     */
    public void cancelar() {
        if (status == StatusTorneio.FINALIZADO) {
            throw new IllegalStateException("Não é possível cancelar um torneio finalizado");
        }
        
        this.status = StatusTorneio.CANCELADO;
    }

    /**
     * Retorna as colocações do torneio.
     * Implementação simplificada - retorna mapa vazio por enquanto.
     * 
     * @return mapa com participante e colocação
     */
    public Map<Participante, Integer> getColocacoes() {
        // TODO: Implementar lógica de colocações baseada no formato do torneio
        return new HashMap<>();
    }

    public void adicionarPartida(Partida partida) {
        if (partida == null) {
            throw new IllegalArgumentException("Partida não pode ser nula");
        }
        partidas.add(partida);
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public FormatoTorneio getFormato() {
        return formato;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public StatusTorneio getStatus() {
        return status;
    }

    public double getPremioTotal() {
        return premioTotal;
    }

    public List<Participante> getParticipantes() {
        return new ArrayList<>(participantes);
    }

    public List<Partida> getPartidas() {
        return new ArrayList<>(partidas);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Torneio torneio = (Torneio) o;
        return Objects.equals(nome, torneio.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%s) - %d/%d participantes - Prêmio: R$ %.2f", 
            status, nome, jogo.getNome(), formato, participantes.size(), MAX_PARTICIPANTES, premioTotal);
    }
}
