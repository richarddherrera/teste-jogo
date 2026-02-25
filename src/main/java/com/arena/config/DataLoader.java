package com.arena.config;

import com.arena.model.Jogador;
import com.arena.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Carrega dados iniciais no banco de dados.
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Override
    public void run(String... args) throws Exception {
        if (jogadorRepository.count() == 0) {
            System.out.println("📦 Carregando dados iniciais...");

            // Jogadores de exemplo
            Jogador aspas = new Jogador("aspas", "Erick Santos", "aspas@email.com", 
                LocalDate.of(2003, 5, 15));
            aspas.ganharElo(600); // DIAMANTE
            jogadorRepository.save(aspas);

            Jogador less = new Jogador("Less", "Felipe Basso", "less@email.com", 
                LocalDate.of(2005, 3, 20));
            less.ganharElo(400); // OURO
            jogadorRepository.save(less);

            Jogador sacy = new Jogador("Sacy", "Gustavo Rossi", "sacy@email.com", 
                LocalDate.of(1997, 4, 4));
            jogadorRepository.save(sacy); // PRATA

            Jogador pancada = new Jogador("pANcada", "Bryan Luna", "pancada@email.com", 
                LocalDate.of(2002, 8, 18));
            pancada.ganharElo(800); // DIAMANTE
            jogadorRepository.save(pancada);

            Jogador tuyz = new Jogador("tuyz", "Arthur Andrade", "tuyz@email.com", 
                LocalDate.of(2004, 11, 10));
            tuyz.ganharElo(200); // OURO
            jogadorRepository.save(tuyz);

            System.out.println("✅ Dados iniciais carregados: " + jogadorRepository.count() + " jogadores");
        }
    }
}
