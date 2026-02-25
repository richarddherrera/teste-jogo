import { Jogador, CreateJogadorRequest } from "@/types";

const API_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080/api";

/**
 * API client para comunicação com o backend
 */
export const api = {
  jogadores: {
    /**
     * Lista todos os jogadores
     */
    async getAll(): Promise<Jogador[]> {
      const res = await fetch(`${API_URL}/jogadores`);
      if (!res.ok) throw new Error("Erro ao buscar jogadores");
      return res.json();
    },

    /**
     * Busca jogador por nickname
     */
    async getByNickname(nickname: string): Promise<Jogador> {
      const res = await fetch(`${API_URL}/jogadores/${nickname}`);
      if (!res.ok) throw new Error("Jogador não encontrado");
      return res.json();
    },

    /**
     * Cria um novo jogador
     */
    async create(data: CreateJogadorRequest): Promise<Jogador> {
      const res = await fetch(`${API_URL}/jogadores`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
      });
      if (!res.ok) {
        const error = await res.json();
        throw new Error(error.message || "Erro ao criar jogador");
      }
      return res.json();
    },

    /**
     * Retorna o ranking global
     */
    async getRanking(limit: number = 10): Promise<Jogador[]> {
      const res = await fetch(`${API_URL}/jogadores/ranking?limit=${limit}`);
      if (!res.ok) throw new Error("Erro ao buscar ranking");
      return res.json();
    },

    /**
     * Atualiza ELO do jogador
     */
    async updateElo(
      nickname: string,
      pontos: number,
      acao: "ganhar" | "perder"
    ): Promise<Jogador> {
      const res = await fetch(
        `${API_URL}/jogadores/${nickname}/elo?pontos=${pontos}&acao=${acao}`,
        { method: "PATCH" }
      );
      if (!res.ok) throw new Error("Erro ao atualizar ELO");
      return res.json();
    },

    /**
     * Deleta um jogador
     */
    async delete(nickname: string): Promise<void> {
      const res = await fetch(`${API_URL}/jogadores/${nickname}`, {
        method: "DELETE",
      });
      if (!res.ok) throw new Error("Erro ao deletar jogador");
    },
  },
};

// Type para CreateJogadorRequest
export interface CreateJogadorRequest {
  nickname: string;
  nomeReal: string;
  email: string;
  dataNascimento: string;
}
