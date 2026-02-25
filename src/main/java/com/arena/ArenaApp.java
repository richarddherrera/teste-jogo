package com.arena;

import com.arena.enums.*;
import com.arena.model.*;
import com.arena.service.RelatorioService;
import com.arena.service.TorneioService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Aplicação principal do Sistema de Gerenciamento de Torneios de E-Sports.
 */
public class ArenaApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static TorneioService torneioService;
    private static RelatorioService relatorioService;

    public static void main(String[] args) {
        torneioService = new TorneioService();
        relatorioService = new RelatorioService(torneioService);
        
        carregarDadosIniciais();
        
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   🎮 SISTEMA DE GERENCIAMENTO DE TORNEIOS DE E-SPORTS 🎮  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        boolean continuar = true;
        while (continuar) {
            try {
                exibirMenuPrincipal();
                int opcao = lerInteiro("Escolha uma opção: ");
                
                continuar = processarOpcao(opcao);
            } catch (Exception e) {
                System.err.println("❌ Erro: " + e.getMessage());
            }
        }
        
        System.out.println("\n👋 Até logo!");
        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("MENU PRINCIPAL");
        System.out.println("=".repeat(60));
        System.out.println("1. 👤 Gerenciar Jogadores");
        System.out.println("2. 👥 Gerenciar Times");
        System.out.println("3. 🎮 Gerenciar Jogos");
        System.out.println("4. 🏆 Gerenciar Torneios");
        System.out.println("5. ⚔️  Gerenciar Partidas");
        System.out.println("6. 🎯 Matchmaking");
        System.out.println("7. 📊 Relatórios e Rankings");
        System.out.println("0. ❌ Sair");
        System.out.println("=".repeat(60));
    }

    private static boolean processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> menuJogadores();
            case 2 -> menuTimes();
            case 3 -> menuJogos();
            case 4 -> menuTorneios();
            case 5 -> menuPartidas();
            case 6 -> menuMatchmaking();
            case 7 -> menuRelatorios();
            case 0 -> {
                return false;
            }
            default -> System.out.println("❌ Opção inválida!");
        }
        return true;
    }

    // ==================== MENU JOGADORES ====================

    private static void menuJogadores() {
        System.out.println("\n--- 👤 GERENCIAR JOGADORES ---");
        System.out.println("1. Cadastrar Jogador");
        System.out.println("2. Listar Jogadores");
        System.out.println("3. Buscar Jogador");
        System.out.println("0. Voltar");
        
        int opcao = lerInteiro("Opção: ");
        
        switch (opcao) {
            case 1 -> cadastrarJogador();
            case 2 -> listarJogadores();
            case 3 -> buscarJogador();
        }
    }

    private static void cadastrarJogador() {
        System.out.println("\n--- CADASTRAR JOGADOR ---");
        String nickname = lerString("Nickname: ");
        String nomeReal = lerString("Nome Real: ");
        String email = lerString("Email: ");
        
        System.out.println("Data de Nascimento:");
        int ano = lerInteiro("Ano: ");
        int mes = lerInteiro("Mês: ");
        int dia = lerInteiro("Dia: ");
        
        try {
            Jogador jogador = new Jogador(nickname, nomeReal, email, LocalDate.of(ano, mes, dia));
            torneioService.cadastrarJogador(jogador);
            System.out.println("✅ Jogador cadastrado com sucesso!");
            System.out.println(jogador);
        } catch (Exception e) {
            System.err.println("❌ Erro ao cadastrar: " + e.getMessage());
        }
    }

    private static void listarJogadores() {
        System.out.println("\n--- LISTA DE JOGADORES ---");
        List<Jogador> jogadores = torneioService.getJogadores();
        
        if (jogadores.isEmpty()) {
            System.out.println("Nenhum jogador cadastrado.");
            return;
        }
        
        jogadores.forEach(System.out::println);
    }

    private static void buscarJogador() {
        String nickname = lerString("Nickname: ");
        torneioService.buscarJogadorPorNickname(nickname)
            .ifPresentOrElse(
                jogador -> System.out.println("✅ " + jogador),
                () -> System.out.println("❌ Jogador não encontrado")
            );
    }

    // ==================== MENU TIMES ====================

    private static void menuTimes() {
        System.out.println("\n--- 👥 GERENCIAR TIMES ---");
        System.out.println("1. Cadastrar Time");
        System.out.println("2. Listar Times");
        System.out.println("3. Adicionar Membro");
        System.out.println("0. Voltar");
        
        int opcao = lerInteiro("Opção: ");
        
        switch (opcao) {
            case 1 -> cadastrarTime();
            case 2 -> listarTimes();
            case 3 -> adicionarMembroTime();
        }
    }

    private static void cadastrarTime() {
        System.out.println("\n--- CADASTRAR TIME ---");
        String nome = lerString("Nome do Time: ");
        String tag = lerString("Tag (3-5 caracteres): ");
        String nicknameCapitao = lerString("Nickname do Capitão: ");
        String nomeJogo = lerString("Jogo Principal: ");
        
        try {
            Jogador capitao = torneioService.buscarJogadorPorNickname(nicknameCapitao)
                .orElseThrow(() -> new IllegalArgumentException("Capitão não encontrado"));
            
            Jogo jogo = torneioService.buscarJogoPorNome(nomeJogo)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));
            
            Time time = new Time(nome, tag, capitao, jogo);
            torneioService.cadastrarTime(time);
            System.out.println("✅ Time cadastrado com sucesso!");
            System.out.println(time);
        } catch (Exception e) {
            System.err.println("❌ Erro ao cadastrar: " + e.getMessage());
        }
    }

    private static void listarTimes() {
        System.out.println("\n--- LISTA DE TIMES ---");
        List<Time> times = torneioService.getTimes();
        
        if (times.isEmpty()) {
            System.out.println("Nenhum time cadastrado.");
            return;
        }
        
        times.forEach(System.out::println);
    }

    private static void adicionarMembroTime() {
        String nomeTime = lerString("Nome do Time: ");
        String nicknameJogador = lerString("Nickname do Jogador: ");
        
        try {
            Time time = torneioService.buscarTimePorNome(nomeTime)
                .orElseThrow(() -> new IllegalArgumentException("Time não encontrado"));
            
            Jogador jogador = torneioService.buscarJogadorPorNickname(nicknameJogador)
                .orElseThrow(() -> new IllegalArgumentException("Jogador não encontrado"));
            
            time.adicionarMembro(jogador);
            System.out.println("✅ Membro adicionado com sucesso!");
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }

    // ==================== MENU JOGOS ====================

    private static void menuJogos() {
        System.out.println("\n--- 🎮 GERENCIAR JOGOS ---");
        System.out.println("1. Cadastrar Jogo");
        System.out.println("2. Listar Jogos");
        System.out.println("0. Voltar");
        
        int opcao = lerInteiro("Opção: ");
        
        switch (opcao) {
            case 1 -> cadastrarJogo();
            case 2 -> listarJogos();
        }
    }

    private static void cadastrarJogo() {
        System.out.println("\n--- CADASTRAR JOGO ---");
        String nome = lerString("Nome do Jogo: ");
        
        System.out.println("Gêneros disponíveis:");
        for (GeneroJogo genero : GeneroJogo.values()) {
            System.out.println("- " + genero + " (" + genero.getDescricao() + ")");
        }
        String generoStr = lerString("Gênero: ").toUpperCase();
        
        int maxJogadores = lerInteiro("Máximo de jogadores por time: ");
        
        System.out.println("Plataformas disponíveis:");
        for (Plataforma plat : Plataforma.values()) {
            System.out.println("- " + plat);
        }
        String plataformaStr = lerString("Plataforma: ").toUpperCase();
        
        try {
            GeneroJogo genero = GeneroJogo.valueOf(generoStr);
            Plataforma plataforma = Plataforma.valueOf(plataformaStr);
            
            Jogo jogo = new Jogo(nome, genero, maxJogadores, plataforma);
            torneioService.cadastrarJogo(jogo);
            System.out.println("✅ Jogo cadastrado com sucesso!");
            System.out.println(jogo);
        } catch (Exception e) {
            System.err.println("❌ Erro ao cadastrar: " + e.getMessage());
        }
    }

    private static void listarJogos() {
        System.out.println("\n--- LISTA DE JOGOS ---");
        List<Jogo> jogos = torneioService.getJogos();
        
        if (jogos.isEmpty()) {
            System.out.println("Nenhum jogo cadastrado.");
            return;
        }
        
        jogos.forEach(System.out::println);
    }

    // ==================== MENU TORNEIOS ====================

    private static void menuTorneios() {
        System.out.println("\n--- 🏆 GERENCIAR TORNEIOS ---");
        System.out.println("1. Criar Torneio");
        System.out.println("2. Listar Torneios");
        System.out.println("3. Inscrever Participante");
        System.out.println("4. Iniciar Torneio");
        System.out.println("0. Voltar");
        
        int opcao = lerInteiro("Opção: ");
        
        switch (opcao) {
            case 1 -> criarTorneio();
            case 2 -> listarTorneios();
            case 3 -> inscreverParticipante();
            case 4 -> iniciarTorneio();
        }
    }

    private static void criarTorneio() {
        System.out.println("\n--- CRIAR TORNEIO ---");
        String nome = lerString("Nome do Torneio: ");
        String nomeJogo = lerString("Jogo: ");
        
        System.out.println("Formatos disponíveis:");
        for (FormatoTorneio formato : FormatoTorneio.values()) {
            System.out.println("- " + formato);
        }
        String formatoStr = lerString("Formato: ").toUpperCase();
        
        System.out.println("Data de Início:");
        int anoInicio = lerInteiro("Ano: ");
        int mesInicio = lerInteiro("Mês: ");
        int diaInicio = lerInteiro("Dia: ");
        
        System.out.println("Data de Fim:");
        int anoFim = lerInteiro("Ano: ");
        int mesFim = lerInteiro("Mês: ");
        int diaFim = lerInteiro("Dia: ");
        
        double premio = lerDouble("Prêmio Total (R$): ");
        
        try {
            Jogo jogo = torneioService.buscarJogoPorNome(nomeJogo)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado"));
            
            FormatoTorneio formato = FormatoTorneio.valueOf(formatoStr);
            LocalDate dataInicio = LocalDate.of(anoInicio, mesInicio, diaInicio);
            LocalDate dataFim = LocalDate.of(anoFim, mesFim, diaFim);
            
            Torneio torneio = new Torneio(nome, jogo, formato, dataInicio, dataFim, premio);
            torneioService.criarTorneio(torneio);
            System.out.println("✅ Torneio criado com sucesso!");
            System.out.println(torneio);
        } catch (Exception e) {
            System.err.println("❌ Erro ao criar: " + e.getMessage());
        }
    }

    private static void listarTorneios() {
        System.out.println("\n--- LISTA DE TORNEIOS ---");
        List<Torneio> torneios = torneioService.getTorneios();
        
        if (torneios.isEmpty()) {
            System.out.println("Nenhum torneio cadastrado.");
            return;
        }
        
        torneios.forEach(System.out::println);
    }

    private static void inscreverParticipante() {
        String nomeTorneio = lerString("Nome do Torneio: ");
        String tipoParticipante = lerString("Tipo (jogador/time): ").toLowerCase();
        
        try {
            Participante participante;
            
            if (tipoParticipante.equals("jogador")) {
                String nickname = lerString("Nickname: ");
                participante = torneioService.buscarJogadorPorNickname(nickname)
                    .orElseThrow(() -> new IllegalArgumentException("Jogador não encontrado"));
            } else {
                String nomeTime = lerString("Nome do Time: ");
                participante = torneioService.buscarTimePorNome(nomeTime)
                    .orElseThrow(() -> new IllegalArgumentException("Time não encontrado"));
            }
            
            torneioService.inscreverParticipante(nomeTorneio, participante);
            System.out.println("✅ Participante inscrito com sucesso!");
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }

    private static void iniciarTorneio() {
        String nomeTorneio = lerString("Nome do Torneio: ");
        
        try {
            Torneio torneio = torneioService.buscarTorneioPorNome(nomeTorneio)
                .orElseThrow(() -> new IllegalArgumentException("Torneio não encontrado"));
            
            torneio.iniciar();
            System.out.println("✅ Torneio iniciado com sucesso!");
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }

    // ==================== MENU PARTIDAS ====================

    private static void menuPartidas() {
        System.out.println("\n--- ⚔️ GERENCIAR PARTIDAS ---");
        System.out.println("1. Listar Partidas");
        System.out.println("0. Voltar");
        
        int opcao = lerInteiro("Opção: ");
        
        if (opcao == 1) {
            listarPartidas();
        }
    }

    private static void listarPartidas() {
        System.out.println("\n--- LISTA DE PARTIDAS ---");
        List<Partida> partidas = torneioService.getPartidas();
        
        if (partidas.isEmpty()) {
            System.out.println("Nenhuma partida registrada.");
            return;
        }
        
        partidas.forEach(System.out::println);
    }

    // ==================== MENU MATCHMAKING ====================

    private static void menuMatchmaking() {
        System.out.println("\n--- 🎯 MATCHMAKING ---");
        System.out.println("1. Entrar na Fila");
        System.out.println("2. Ver Fila");
        System.out.println("3. Processar Matchmaking");
        System.out.println("0. Voltar");
        
        int opcao = lerInteiro("Opção: ");
        
        switch (opcao) {
            case 1 -> entrarNaFila();
            case 2 -> verFila();
            case 3 -> processarMatchmaking();
        }
    }

    private static void entrarNaFila() {
        String nickname = lerString("Nickname: ");
        
        try {
            Jogador jogador = torneioService.buscarJogadorPorNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Jogador não encontrado"));
            
            torneioService.entrarNaFila(jogador);
            System.out.println("✅ Jogador adicionado à fila!");
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }

    private static void verFila() {
        System.out.println("\n--- FILA DE MATCHMAKING ---");
        var fila = torneioService.getFilaMatchmaking();
        
        if (fila.isEmpty()) {
            System.out.println("Fila vazia.");
            return;
        }
        
        fila.forEach(j -> System.out.println("- " + j.getNickname() + " (" + j.getCategoria() + ")"));
    }

    private static void processarMatchmaking() {
        torneioService.processarMatchmaking();
        System.out.println("✅ Matchmaking processado!");
    }

    // ==================== MENU RELATÓRIOS ====================

    private static void menuRelatorios() {
        System.out.println("\n--- 📊 RELATÓRIOS E RANKINGS ---");
        System.out.println("1. Ranking Global (Top 10 ELO)");
        System.out.println("2. Top 10 Vitórias");
        System.out.println("3. Win Rate dos Times");
        System.out.println("4. Distribuição por Categoria");
        System.out.println("5. Estatísticas Gerais");
        System.out.println("0. Voltar");
        
        int opcao = lerInteiro("Opção: ");
        
        switch (opcao) {
            case 1 -> exibirRankingGlobal();
            case 2 -> exibirTopVitorias();
            case 3 -> exibirWinRateTimes();
            case 4 -> exibirDistribuicaoCategoria();
            case 5 -> exibirEstatisticasGerais();
        }
    }

    private static void exibirRankingGlobal() {
        System.out.println("\n🏆 RANKING GLOBAL (TOP 10 ELO)");
        System.out.println("=".repeat(60));
        
        List<Jogador> ranking = relatorioService.gerarRankingGlobal();
        
        if (ranking.isEmpty()) {
            System.out.println("Nenhum jogador cadastrado.");
            return;
        }
        
        for (int i = 0; i < ranking.size(); i++) {
            Jogador j = ranking.get(i);
            System.out.printf("%2d. %s - ELO %d (%s)%n", 
                i + 1, j.getNickname(), j.getElo(), j.getCategoria());
        }
    }

    private static void exibirTopVitorias() {
        System.out.println("\n🎖️ TOP 10 VITÓRIAS");
        System.out.println("=".repeat(60));
        
        List<Map.Entry<Jogador, Long>> top = relatorioService.gerarTopVitorias();
        
        if (top.isEmpty()) {
            System.out.println("Nenhuma partida registrada.");
            return;
        }
        
        for (int i = 0; i < top.size(); i++) {
            var entry = top.get(i);
            System.out.printf("%2d. %s - %d vitórias%n", 
                i + 1, entry.getKey().getNickname(), entry.getValue());
        }
    }

    private static void exibirWinRateTimes() {
        System.out.println("\n📈 WIN RATE DOS TIMES");
        System.out.println("=".repeat(60));
        
        Map<Time, Double> winRates = relatorioService.calcularWinRateTimes();
        
        if (winRates.isEmpty()) {
            System.out.println("Nenhum time com partidas registradas.");
            return;
        }
        
        winRates.forEach((time, wr) -> 
            System.out.printf("%s - %.1f%%%n", time.getNome(), wr * 100)
        );
    }

    private static void exibirDistribuicaoCategoria() {
        System.out.println("\n📊 DISTRIBUIÇÃO POR CATEGORIA");
        System.out.println("=".repeat(60));
        
        Map<Categoria, Long> distribuicao = relatorioService.gerarDistribuicaoPorCategoria();
        
        if (distribuicao.isEmpty()) {
            System.out.println("Nenhum jogador cadastrado.");
            return;
        }
        
        distribuicao.forEach((cat, count) -> 
            System.out.printf("%s: %d jogadores%n", cat, count)
        );
    }

    private static void exibirEstatisticasGerais() {
        System.out.println("\n📈 ESTATÍSTICAS GERAIS");
        System.out.println("=".repeat(60));
        
        Map<String, Object> stats = relatorioService.gerarEstatisticasGerais();
        stats.forEach((key, value) -> System.out.printf("%s: %s%n", key, value));
    }

    // ==================== DADOS INICIAIS ====================

    private static void carregarDadosIniciais() {
        try {
            // Jogos
            Jogo valorant = new Jogo("Valorant", GeneroJogo.FPS, 5, Plataforma.PC);
            Jogo lol = new Jogo("League of Legends", GeneroJogo.MOBA, 5, Plataforma.PC);
            Jogo csgo = new Jogo("CS:GO", GeneroJogo.FPS, 5, Plataforma.PC);
            
            torneioService.cadastrarJogo(valorant);
            torneioService.cadastrarJogo(lol);
            torneioService.cadastrarJogo(csgo);
            
            // Jogadores
            Jogador aspas = new Jogador("aspas", "Erick Santos", "aspas@email.com", LocalDate.of(2003, 5, 15));
            Jogador less = new Jogador("Less", "Felipe Basso", "less@email.com", LocalDate.of(2005, 3, 20));
            Jogador sacy = new Jogador("Sacy", "Gustavo Rossi", "sacy@email.com", LocalDate.of(1997, 4, 4));
            Jogador pancada = new Jogador("pANcada", "Bryan Luna", "pancada@email.com", LocalDate.of(2002, 8, 18));
            
            torneioService.cadastrarJogador(aspas);
            torneioService.cadastrarJogador(less);
            torneioService.cadastrarJogador(sacy);
            torneioService.cadastrarJogador(pancada);
            
            // Ajustar ELO para variar categorias
            aspas.ganharElo(600); // DIAMANTE
            less.ganharElo(400);  // OURO
            
            // Time
            Time loud = new Time("LOUD", "LOUD", aspas, valorant);
            loud.adicionarMembro(less);
            loud.adicionarMembro(sacy);
            torneioService.cadastrarTime(loud);
            
            // Torneio
            Torneio champions = new Torneio(
                "Champions 2024",
                valorant,
                FormatoTorneio.ELIMINACAO_SIMPLES,
                LocalDate.now().plusDays(7),
                LocalDate.now().plusDays(14),
                100000.0
            );
            torneioService.criarTorneio(champions);
            
            System.out.println("✅ Dados iniciais carregados!");
        } catch (Exception e) {
            System.err.println("⚠️ Erro ao carregar dados iniciais: " + e.getMessage());
        }
    }

    // ==================== UTILITÁRIOS ====================

    private static String lerString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int lerInteiro(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.err.println("❌ Digite um número válido!");
            }
        }
    }

    private static double lerDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double valor = Double.parseDouble(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.err.println("❌ Digite um número válido!");
            }
        }
    }
}
