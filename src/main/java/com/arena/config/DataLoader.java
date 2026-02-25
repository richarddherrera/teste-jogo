package com.arena.config;

import com.arena.model.Jogador;
import com.arena.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Carrega dados iniciais no banco de dados.
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (jogadorRepository.count() == 0) {
            System.out.println("📦 Carregando dados iniciais...");

            // Jogadores de exemplo com senha "senha123"
            String senhaPadrao = passwordEncoder.encode("senha123");

            Jogador aspas = new Jogador("aspas", "Erick Santos", "aspas@email.com", senhaPadrao,
                LocalDate.of(2003, 5, 15));
            aspas.ganharElo(600); // DIAMANTE
            aspas.setTotalPartidas(50);
            aspas.setVitorias(35);
            aspas.setDerrotas(15);
            aspas.setKills(1250);
            aspas.setDeaths(450);
            aspas.setAssists(320);
            aspas.setTempoJogoMinutos(3000);
            aspas.setModoFavorito("Competitivo");
            jogadorRepository.save(aspas);

            Jogador less = new Jogador("Less", "Felipe Basso", "less@email.com", senhaPadrao,
                LocalDate.of(2005, 3, 20));
            less.ganharElo(400); // OURO
            less.setTotalPartidas(40);
            less.setVitorias(25);
            less.setDerrotas(15);
            less.setKills(980);
            less.setDeaths(520);
            less.setAssists(280);
            less.setTempoJogoMinutos(2400);
            less.setModoFavorito("Ranked");
            jogadorRepository.save(less);

            Jogador sacy = new Jogador("Sacy", "Gustavo Rossi", "sacy@email.com", senhaPadrao,
                LocalDate.of(1997, 4, 4));
            sacy.setTotalPartidas(30);
            sacy.setVitorias(15);
            sacy.setDerrotas(15);
            sacy.setKills(720);
            sacy.setDeaths(680);
            sacy.setAssists(450);
            sacy.setTempoJogoMinutos(1800);
            sacy.setModoFavorito("Casual");
            jogadorRepository.save(sacy); // PRATA

            Jogador pancada = new Jogador("pANcada", "Bryan Luna", "pancada@email.com", senhaPadrao,
                LocalDate.of(2002, 8, 18));
            pancada.ganharElo(800); // DIAMANTE
            pancada.setTotalPartidas(60);
            pancada.setVitorias(42);
            pancada.setDerrotas(18);
            pancada.setKills(1450);
            pancada.setDeaths(380);
            pancada.setAssists(520);
            pancada.setTempoJogoMinutos(3600);
            pancada.setModoFavorito("Competitivo");
            jogadorRepository.save(pancada);

            Jogador tuyz = new Jogador("tuyz", "Arthur Andrade", "tuyz@email.com", senhaPadrao,
                LocalDate.of(2004, 11, 10));
            tuyz.ganharElo(200); // OURO
            tuyz.setTotalPartidas(35);
            tuyz.setVitorias(20);
            tuyz.setDerrotas(15);
            tuyz.setKills(850);
            tuyz.setDeaths(600);
            tuyz.setAssists(310);
            tuyz.setTempoJogoMinutos(2100);
            tuyz.setModoFavorito("Ranked");
            jogadorRepository.save(tuyz);

            System.out.println("✅ Dados iniciais carregados: " + jogadorRepository.count() + " jogadores");
            System.out.println("🔑 Senha padrão para todos: senha123");
        }
    }
}
