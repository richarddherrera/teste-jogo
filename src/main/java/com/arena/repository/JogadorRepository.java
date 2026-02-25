package com.arena.repository;

import com.arena.enums.Categoria;
import com.arena.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações de banco de dados com Jogador.
 */
@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    
    Optional<Jogador> findByNickname(String nickname);
    
    Optional<Jogador> findByEmail(String email);
    
    boolean existsByNickname(String nickname);
    
    List<Jogador> findByCategoria(Categoria categoria);
    
    @Query("SELECT j FROM Jogador j ORDER BY j.elo DESC")
    List<Jogador> findTopByOrderByEloDesc();
    
    @Query("SELECT j FROM Jogador j WHERE j.categoria = :categoria ORDER BY j.elo DESC")
    List<Jogador> findByCategoriaOrderByEloDesc(Categoria categoria);
}
