package com.arena.service;

import com.arena.enums.Categoria;
import com.arena.enums.StatusPartida;
import com.arena.model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Serviço para geração de relatórios e rankings.
 */
public class RelatorioService {
    private final TorneioService torneioService;

    public RelatorioService(TorneioService torneioService) {
        this.torneioService = torneioService;
    }

    /**
     * Gera o ranking global dos jogadores por ELO (top 10).
     * 
     * @return lista dos top 10 jogadores ordenados por ELO
     */
    public List<Jogador> gerarRankingGlobal() {
        return torneioService.getJogadores().stream()
            .sorted(Comparator.comparingInt(Jogador::getElo).reversed())
            .limit(10)
            .collect(Collectors.toList());
    }

    /**
     * Retorna os jogadores com mais vitórias (top 10).
     * 
     * @return lista dos top 10 jogadores com mais vitórias
     */
    public List<Map.Entry<Jogador, Long>> gerarTopVitorias() {
        Map<Jogador, Long> vitoriasPorJogador = new HashMap<>();
        
        // Contar vitórias de cada jogador
        torneioService.getPartidas().stream()
            .filter(p -> p.getStatus() == StatusPartida.FINALIZADA || p.getStatus() == StatusPartida.WO)
            .filter(p -> p.getVencedor() != null)
            .forEach(partida -> {
                for (Jogador jogador : partida.getVencedor().getMembros()) {
                    vitoriasPorJogador.merge(jogador, 1L, Long::sum);
                }
            });
        
        return vitoriasPorJogador.entrySet().stream()
            .sorted(Map.Entry.<Jogador, Long>comparingByValue().reversed())
            .limit(10)
            .collect(Collectors.toList());
    }

    /**
     * Calcula o win rate dos times (vitórias/total de partidas).
     * 
     * @return mapa com time e seu win rate
     */
    public Map<Time, Double> calcularWinRateTimes() {
        Map<Time, Integer> vitorias = new HashMap<>();
        Map<Time, Integer> totalPartidas = new HashMap<>();
        
        torneioService.getPartidas().stream()
            .filter(p -> p.getStatus() == StatusPartida.FINALIZADA || p.getStatus() == StatusPartida.WO)
            .forEach(partida -> {
                // Contar participação
                if (partida.getParticipante1() instanceof Time time1) {
                    totalPartidas.merge(time1, 1, Integer::sum);
                }
                if (partida.getParticipante2() instanceof Time time2) {
                    totalPartidas.merge(time2, 1, Integer::sum);
                }
                
                // Contar vitórias
                if (partida.getVencedor() instanceof Time timeVencedor) {
                    vitorias.merge(timeVencedor, 1, Integer::sum);
                }
            });
        
        // Calcular win rate
        Map<Time, Double> winRates = new HashMap<>();
        totalPartidas.forEach((time, total) -> {
            int wins = vitorias.getOrDefault(time, 0);
            double winRate = (double) wins / total;
            winRates.put(time, winRate);
        });
        
        return winRates.entrySet().stream()
            .sorted(Map.Entry.<Time, Double>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }

    /**
     * Lista os torneios ativos com seus participantes.
     * 
     * @return mapa com torneio e lista de participantes
     */
    public Map<Torneio, List<Participante>> listarTorneiosAtivos() {
        return torneioService.listarTorneiosAtivos().stream()
            .collect(Collectors.toMap(
                torneio -> torneio,
                Torneio::getParticipantes,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }

    /**
     * Retorna os jogadores mais penalizados.
     * 
     * @return lista de jogadores com contagem de penalidades ativas
     */
    public List<Map.Entry<Jogador, Long>> gerarJogadoresMaisPenalizados() {
        return torneioService.getPenalidades().stream()
            .filter(Penalidade::isAtiva)
            .collect(Collectors.groupingBy(
                Penalidade::getJogador,
                Collectors.counting()
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<Jogador, Long>comparingByValue().reversed())
            .limit(10)
            .collect(Collectors.toList());
    }

    /**
     * Gera a distribuição de jogadores por categoria.
     * 
     * @return mapa com categoria e quantidade de jogadores
     */
    public Map<Categoria, Long> gerarDistribuicaoPorCategoria() {
        return torneioService.getJogadores().stream()
            .collect(Collectors.groupingBy(
                Jogador::getCategoria,
                Collectors.counting()
            ))
            .entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }

    /**
     * Gera estatísticas gerais do sistema.
     * 
     * @return mapa com estatísticas
     */
    public Map<String, Object> gerarEstatisticasGerais() {
        Map<String, Object> stats = new LinkedHashMap<>();
        
        stats.put("Total de Jogadores", torneioService.getJogadores().size());
        stats.put("Total de Times", torneioService.getTimes().size());
        stats.put("Total de Torneios", torneioService.getTorneios().size());
        stats.put("Torneios Ativos", torneioService.listarTorneiosAtivos().size());
        stats.put("Total de Partidas", torneioService.getPartidas().size());
        stats.put("Partidas Finalizadas", 
            torneioService.getPartidas().stream()
                .filter(p -> p.getStatus() == StatusPartida.FINALIZADA)
                .count()
        );
        stats.put("Jogadores na Fila", torneioService.getFilaMatchmaking().size());
        stats.put("Total de Penalidades", torneioService.getPenalidades().size());
        stats.put("Penalidades Ativas", 
            torneioService.getPenalidades().stream()
                .filter(Penalidade::isAtiva)
                .count()
        );
        
        // ELO médio
        OptionalDouble eloMedio = torneioService.getJogadores().stream()
            .mapToInt(Jogador::getElo)
            .average();
        stats.put("ELO Médio", eloMedio.isPresent() ? String.format("%.0f", eloMedio.getAsDouble()) : "N/A");
        
        return stats;
    }

    /**
     * Gera o histórico de partidas de um jogador.
     * 
     * @param jogador o jogador
     * @return lista de partidas do jogador
     */
    public List<Partida> gerarHistoricoJogador(Jogador jogador) {
        return torneioService.getPartidas().stream()
            .filter(p -> p.getParticipante1().getMembros().contains(jogador) ||
                        p.getParticipante2().getMembros().contains(jogador))
            .sorted(Comparator.comparing(Partida::getDataHora).reversed())
            .collect(Collectors.toList());
    }

    /**
     * Calcula o win rate de um jogador.
     * 
     * @param jogador o jogador
     * @return win rate (0.0 a 1.0)
     */
    public double calcularWinRateJogador(Jogador jogador) {
        List<Partida> partidas = gerarHistoricoJogador(jogador);
        
        if (partidas.isEmpty()) {
            return 0.0;
        }
        
        long vitorias = partidas.stream()
            .filter(p -> p.getStatus() == StatusPartida.FINALIZADA || p.getStatus() == StatusPartida.WO)
            .filter(p -> p.getVencedor() != null)
            .filter(p -> p.getVencedor().getMembros().contains(jogador))
            .count();
        
        return (double) vitorias / partidas.size();
    }
}
