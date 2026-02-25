package com.arena.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa um time de jogadores.
 */
public class Time extends Participante {
    private String nome;
    private String tag;
    private Jogador capitao;
    private List<Jogador> membros;
    private Jogo jogoPrincipal;

    public Time(String nome, String tag, Jogador capitao, Jogo jogoPrincipal) {
        validarCampos(nome, tag, capitao, jogoPrincipal);
        
        this.nome = nome;
        this.tag = tag.toUpperCase();
        this.capitao = capitao;
        this.jogoPrincipal = jogoPrincipal;
        this.membros = new ArrayList<>();
        this.membros.add(capitao);
    }

    private void validarCampos(String nome, String tag, Jogador capitao, Jogo jogoPrincipal) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do time não pode ser vazio");
        }
        if (tag == null || tag.length() < 3 || tag.length() > 5) {
            throw new IllegalArgumentException("Tag deve ter entre 3 e 5 caracteres");
        }
        if (capitao == null) {
            throw new IllegalArgumentException("Capitão não pode ser nulo");
        }
        if (jogoPrincipal == null) {
            throw new IllegalArgumentException("Jogo principal não pode ser nulo");
        }
    }

    /**
     * Adiciona um membro ao time.
     * Valida se o limite de jogadores do jogo não foi excedido.
     * 
     * @param jogador o jogador a ser adicionado
     * @throws IllegalStateException se o time já está completo
     * @throws IllegalArgumentException se o jogador já está no time
     */
    public void adicionarMembro(Jogador jogador) {
        if (jogador == null) {
            throw new IllegalArgumentException("Jogador não pode ser nulo");
        }
        
        if (membros.contains(jogador)) {
            throw new IllegalArgumentException("Jogador já está no time");
        }
        
        if (membros.size() >= jogoPrincipal.getMaxJogadoresPorTime()) {
            throw new IllegalStateException(
                String.format("Time já atingiu o limite de %d jogadores para %s", 
                    jogoPrincipal.getMaxJogadoresPorTime(), 
                    jogoPrincipal.getNome())
            );
        }
        
        membros.add(jogador);
    }

    /**
     * Remove um membro do time.
     * O capitão não pode ser removido sem antes designar um novo capitão.
     * 
     * @param jogador o jogador a ser removido
     * @throws IllegalArgumentException se tentar remover o capitão
     */
    public void removerMembro(Jogador jogador) {
        if (jogador == null) {
            throw new IllegalArgumentException("Jogador não pode ser nulo");
        }
        
        if (jogador.equals(capitao)) {
            throw new IllegalArgumentException("Não é possível remover o capitão. Designe um novo capitão primeiro.");
        }
        
        if (!membros.remove(jogador)) {
            throw new IllegalArgumentException("Jogador não está no time");
        }
    }

    /**
     * Verifica se todos os membros do time estão ativos.
     * 
     * @return true se todos os membros estão ativos
     */
    public boolean todosAtivos() {
        return membros.stream().allMatch(Jogador::isAtivo);
    }

    /**
     * Designa um novo capitão para o time.
     * O novo capitão deve ser um dos membros.
     * 
     * @param novoCapitao o novo capitão
     * @throws IllegalArgumentException se o jogador não é membro do time
     */
    public void designarCapitao(Jogador novoCapitao) {
        if (!membros.contains(novoCapitao)) {
            throw new IllegalArgumentException("O capitão deve ser um dos membros do time");
        }
        this.capitao = novoCapitao;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public List<Jogador> getMembros() {
        return new ArrayList<>(membros);
    }

    public String getTag() {
        return tag;
    }

    public Jogador getCapitao() {
        return capitao;
    }

    public Jogo getJogoPrincipal() {
        return jogoPrincipal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return Objects.equals(nome, time.nome) || Objects.equals(tag, time.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, tag);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %d membros - Capitão: %s - Jogo: %s", 
            tag, nome, membros.size(), capitao.getNickname(), jogoPrincipal.getNome());
    }
}
