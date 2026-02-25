package com.arena.service;

import com.arena.enums.Categoria;
import com.arena.enums.StatusJogador;
import com.arena.enums.StatusTorneio;
import com.arena.exception.JogadorBanidoException;
import com.arena.exception.NicknameDuplicadoException;
import com.arena.exception.PenalidadeMaximaException;
import com.arena.model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Serviço central para gerenciamento de torneios, jogadores, times e partidas.
 */
public class TorneioService {
    private static final int MAX_PENALIDADES_ATIVAS = 3;

    private final List<Jogador> jogadores;
    private final List<Time> times;
    private final List<Jogo> jogos;
    private final List<Torneio> torneios;
    private final List<Partida> partidas;
    private final List<Penalidade> penalidades;
    private final List<Premiacao> premiacoes;
    private final Queue<Jogador> filaMatchmaking;

    public TorneioService() {
        this.jogadores = new ArrayList<>();
        this.times = new ArrayList<>();
        this.jogos = new ArrayList<>();
        this.torneios = new ArrayList<>();
        this.partidas = new ArrayList<>();
        this.penalidades = new ArrayList<>();
        this.premiacoes = new ArrayList<>();
        this.filaMatchmaking = new LinkedList<>();
    }

    // ==================== CADASTROS ====================

    /**
     * Cadastra um novo jogador no sistema.
     * 
     * @param jogador o jogador a ser cadastrado
     * @throws NicknameDuplicadoException se o nickname já existe
     */
    public void cadastrarJogador(Jogador jogador) {
        if (buscarJogadorPorNickname(jogador.getNickname()).isPresent()) {
            throw new NicknameDuplicadoException(jogador.getNickname());
        }
        jogadores.add(jogador);
    }

    /**
     * Cadastra um novo time no sistema.
     * 
     * @param time o time a ser cadastrado
     * @throws IllegalArgumentException se o nome ou tag já existe
     */
    public void cadastrarTime(Time time) {
        if (buscarTimePorNome(time.getNome()).isPresent()) {
            throw new IllegalArgumentException(
                String.format("Já existe um time com o nome '%s'", time.getNome())
            );
        }
        if (buscarTimePorTag(time.getTag()).isPresent()) {
            throw new IllegalArgumentException(
                String.format("Já existe um time com a tag '%s'", time.getTag())
            );
        }
        times.add(time);
    }

    /**
     * Cadastra um novo jogo no sistema.
     * 
     * @param jogo o jogo a ser cadastrado
     * @throws IllegalArgumentException se o nome já existe
     */
    public void cadastrarJogo(Jogo jogo) {
        if (buscarJogoPorNome(jogo.getNome()).isPresent()) {
            throw new IllegalArgumentException(
                String.format("Já existe um jogo com o nome '%s'", jogo.getNome())
            );
        }
        jogos.add(jogo);
    }

    /**
     * Cria um novo torneio.
     * 
     * @param torneio o torneio a ser criado
     * @throws IllegalArgumentException se o nome já existe
     */
    public void criarTorneio(Torneio torneio) {
        if (buscarTorneioPorNome(torneio.getNome()).isPresent()) {
            throw new IllegalArgumentException(
                String.format("Já existe um torneio com o nome '%s'", torneio.getNome())
            );
        }
        torneios.add(torneio);
    }

    // ==================== INSCRIÇÕES E PARTIDAS ====================

    /**
     * Inscreve um participante em um torneio.
     * 
     * @param nomeTorneio nome do torneio
     * @param participante participante a ser inscrito
     */
    public void inscreverParticipante(String nomeTorneio, Participante participante) {
        Torneio torneio = buscarTorneioPorNome(nomeTorneio)
            .orElseThrow(() -> new IllegalArgumentException("Torneio não encontrado"));
        
        torneio.inscrever(participante);
    }

    /**
     * Registra o resultado de uma partida e distribui ELO.
     * 
     * @param partida a partida
     * @param placar1 placar do participante 1
     * @param placar2 placar do participante 2
     */
    public void registrarResultado(Partida partida, int placar1, int placar2) {
        partida.finalizar(placar1, placar2);
    }

    /**
     * Registra um WO e aplica penalidade.
     * 
     * @param partida a partida
     * @param ausente o participante ausente
     */
    public void registrarWO(Partida partida, Participante ausente) {
        Penalidade penalidade = partida.darWO(ausente);
        aplicarPenalidade(penalidade);
    }

    // ==================== PENALIDADES ====================

    /**
     * Aplica uma penalidade a um jogador.
     * Se atingir 3 penalidades ativas, o jogador é banido automaticamente.
     * 
     * @param penalidade a penalidade a ser aplicada
     * @throws PenalidadeMaximaException se o jogador for banido
     */
    public void aplicarPenalidade(Penalidade penalidade) {
        penalidades.add(penalidade);
        
        long penalidadesAtivas = contarPenalidadesAtivas(penalidade.getJogador());
        
        if (penalidadesAtivas >= MAX_PENALIDADES_ATIVAS) {
            penalidade.getJogador().setStatus(StatusJogador.BANIDO);
            throw new PenalidadeMaximaException(
                penalidade.getJogador().getNickname(), 
                (int) penalidadesAtivas
            );
        }
    }

    private long contarPenalidadesAtivas(Jogador jogador) {
        return penalidades.stream()
            .filter(p -> p.getJogador().equals(jogador))
            .filter(Penalidade::isAtiva)
            .count();
    }

    // ==================== MATCHMAKING ====================

    /**
     * Adiciona um jogador à fila de matchmaking.
     * 
     * @param jogador o jogador
     * @throws JogadorBanidoException se o jogador está banido
     */
    public void entrarNaFila(Jogador jogador) {
        if (jogador.isBanido()) {
            throw new JogadorBanidoException(jogador.getNickname(), "entrar na fila de matchmaking");
        }
        if (!filaMatchmaking.contains(jogador)) {
            filaMatchmaking.add(jogador);
        }
    }

    /**
     * Processa a fila de matchmaking e cria partidas quando possível.
     * Jogadores são pareados por categoria compatível (mesma ou ±1).
     */
    public void processarMatchmaking() {
        List<Jogador> jogadoresNaFila = new ArrayList<>(filaMatchmaking);
        
        for (int i = 0; i < jogadoresNaFila.size() - 1; i++) {
            Jogador j1 = jogadoresNaFila.get(i);
            
            for (int j = i + 1; j < jogadoresNaFila.size(); j++) {
                Jogador j2 = jogadoresNaFila.get(j);
                
                if (saoCategoriasCompativeis(j1.getCategoria(), j2.getCategoria())) {
                    // Criar partida rápida (sem torneio)
                    Partida partida = new Partida(null, j1, j2, 1);
                    partidas.add(partida);
                    
                    // Remover da fila
                    filaMatchmaking.remove(j1);
                    filaMatchmaking.remove(j2);
                    
                    break;
                }
            }
        }
    }

    private boolean saoCategoriasCompativeis(Categoria c1, Categoria c2) {
        int diff = Math.abs(c1.ordinal() - c2.ordinal());
        return diff <= 1;
    }

    // ==================== FINALIZAÇÃO DE TORNEIO ====================

    /**
     * Finaliza um torneio e distribui premiações.
     * 
     * @param nomeTorneio nome do torneio
     */
    public void finalizarTorneio(String nomeTorneio) {
        Torneio torneio = buscarTorneioPorNome(nomeTorneio)
            .orElseThrow(() -> new IllegalArgumentException("Torneio não encontrado"));
        
        torneio.finalizar();
        distribuirPremiacoes(torneio);
    }

    private void distribuirPremiacoes(Torneio torneio) {
        Map<Participante, Integer> colocacoes = torneio.getColocacoes();
        
        // Distribuição: 1º = 50%, 2º = 30%, 3º = 20%
        Map<Integer, Double> percentuais = Map.of(
            1, 0.50,
            2, 0.30,
            3, 0.20
        );
        
        colocacoes.forEach((participante, colocacao) -> {
            if (colocacao <= 3) {
                double valor = torneio.getPremioTotal() * percentuais.get(colocacao);
                Premiacao premiacao = new Premiacao(torneio, participante, colocacao, valor);
                premiacoes.add(premiacao);
            }
        });
    }

    // ==================== BUSCAS ====================

    public Optional<Jogador> buscarJogadorPorNickname(String nickname) {
        return jogadores.stream()
            .filter(j -> j.getNickname().equalsIgnoreCase(nickname))
            .findFirst();
    }

    public Optional<Time> buscarTimePorNome(String nome) {
        return times.stream()
            .filter(t -> t.getNome().equalsIgnoreCase(nome))
            .findFirst();
    }

    public Optional<Time> buscarTimePorTag(String tag) {
        return times.stream()
            .filter(t -> t.getTag().equalsIgnoreCase(tag))
            .findFirst();
    }

    public Optional<Jogo> buscarJogoPorNome(String nome) {
        return jogos.stream()
            .filter(j -> j.getNome().equalsIgnoreCase(nome))
            .findFirst();
    }

    public Optional<Torneio> buscarTorneioPorNome(String nome) {
        return torneios.stream()
            .filter(t -> t.getNome().equalsIgnoreCase(nome))
            .findFirst();
    }

    public List<Torneio> listarTorneiosAtivos() {
        return torneios.stream()
            .filter(t -> t.getStatus() == StatusTorneio.INSCRICOES_ABERTAS || 
                        t.getStatus() == StatusTorneio.EM_ANDAMENTO)
            .collect(Collectors.toList());
    }

    // ==================== GETTERS ====================

    public List<Jogador> getJogadores() {
        return new ArrayList<>(jogadores);
    }

    public List<Time> getTimes() {
        return new ArrayList<>(times);
    }

    public List<Jogo> getJogos() {
        return new ArrayList<>(jogos);
    }

    public List<Torneio> getTorneios() {
        return new ArrayList<>(torneios);
    }

    public List<Partida> getPartidas() {
        return new ArrayList<>(partidas);
    }

    public List<Penalidade> getPenalidades() {
        return new ArrayList<>(penalidades);
    }

    public List<Premiacao> getPremiacoes() {
        return new ArrayList<>(premiacoes);
    }

    public Queue<Jogador> getFilaMatchmaking() {
        return new LinkedList<>(filaMatchmaking);
    }
}
