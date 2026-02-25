import { Jogador, Time, Torneio } from "@/types";

// build the base URL once; strip any trailing slash so callers can safely append paths
// if the environment variable is set incorrectly (for example without the `/api` segment)
// the default will still point to the correct location.
let API_URL = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080/api";
API_URL = API_URL.replace(/\/+$/g, "");

// ensure we always have the `/api` prefix on the URL, because the controllers are
// mounted under that path. This guards against situations in which the developer
// accidentally sets NEXT_PUBLIC_API_URL=http://localhost:8080 in .env (a common
// mistake) and then every request ends up at the wrong endpoint.
if (!API_URL.endsWith("/api")) {
  API_URL = API_URL + "/api";
}

// expose the computed base URL during development so it's easier to spot
// configuration mistakes in the browser console.
if (process.env.NODE_ENV === "development") {
  console.debug("[api] base URL:", API_URL);
}

// Token management
let authToken: string | null = null;

export const setAuthToken = (token: string | null) => {
  authToken = token;
  if (token) {
    localStorage.setItem("authToken", token);
    // also set a cookie so SSR/middleware can read it; expires in 1 day
    if (typeof document !== "undefined") {
      document.cookie = `authToken=${token}; path=/; max-age=${60 * 60 * 24}`;
    }
  } else {
    localStorage.removeItem("authToken");
    if (typeof document !== "undefined") {
      document.cookie = "authToken=; path=/; max-age=0";
    }
  }
};

export const getAuthToken = (): string | null => {
  if (typeof window !== "undefined" && !authToken) {
    authToken = localStorage.getItem("authToken");
  }
  return authToken;
};

const getHeaders = () => {
  const headers: HeadersInit = {
    "Content-Type": "application/json",
  };
  const token = getAuthToken();
  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }
  return headers;
};

export interface LoginRequest {
  nickname: string;
  senha: string;
}

export interface RegisterRequest {
  nickname: string;
  nomeReal: string;
  email: string;
  senha: string;
  dataNascimento: string;
}

export interface AuthResponse {
  token: string;
  jogador: Jogador;
}

/**
 * API client para comunicação com o backend
 */
export const api = {
  auth: {
    /**
     * Faz login
     */
    async login(data: LoginRequest): Promise<AuthResponse> {
      try {
        const res = await fetch(`${API_URL}/auth/login`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(data),
        });

        if (!res.ok) {
          // try to parse error message from backend; if the response is not json
          // or the network failed, a generic message is used instead.
          let msg = "Erro ao fazer login";
          try {
            const error = await res.json();
            msg = error.message || msg;
          } catch {}
          throw new Error(msg);
        }

        const response = await res.json();
        setAuthToken(response.token);
        return response;
      } catch (err: any) {
        // fetch throws a TypeError when the network fails, e.g. backend not reachable.
        if (err instanceof TypeError) {
          throw new Error(
            "Não foi possível conectar ao servidor. Verifique se a API está rodando em http://localhost:8080"
          );
        }
        throw err;
      }
    },

    /**
     * Registra novo jogador
     */
    async register(data: RegisterRequest): Promise<AuthResponse> {
      try {
        const res = await fetch(`${API_URL}/auth/register`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(data),
        });

        if (!res.ok) {
          let msg = "Erro ao registrar";
          try {
            const error = await res.json();
            msg = error.message || msg;
          } catch {}
          throw new Error(msg);
        }

        const response = await res.json();
        setAuthToken(response.token);
        return response;
      } catch (err: any) {
        if (err instanceof TypeError) {
          throw new Error(
            "Não foi possível conectar ao servidor. Verifique se a API está rodando em http://localhost:8080"
          );
        }
        throw err;
      }
    },

    /**
     * Valida token
     */
    async validateToken(): Promise<Jogador> {
      const token = getAuthToken();
      if (!token) throw new Error("Token não encontrado");

      const res = await fetch(`${API_URL}/auth/validate`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (!res.ok) {
        setAuthToken(null);
        throw new Error("Token inválido");
      }
      return res.json();
    },

    /**
     * Faz logout
     */
    logout() {
      setAuthToken(null);
    },
  },

  jogadores: {
    /**
     * Lista todos os jogadores
     */
    async getAll(): Promise<Jogador[]> {
      const res = await fetch(`${API_URL}/jogadores`, {
        headers: getHeaders(),
      });
      if (!res.ok) throw new Error("Erro ao buscar jogadores");
      return res.json();
    },

    /**
     * Busca jogador por nickname
     */
    async getByNickname(nickname: string): Promise<Jogador> {
      const res = await fetch(`${API_URL}/jogadores/${nickname}`, {
        headers: getHeaders(),
      });
      if (!res.ok) throw new Error("Jogador não encontrado");
      return res.json();
    },

    /**
     * Retorna o ranking global
     */
    async getRanking(limit: number = 10): Promise<Jogador[]> {
      const res = await fetch(`${API_URL}/jogadores/ranking?limit=${limit}`, {
        headers: getHeaders(),
      });
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
        {
          method: "PATCH",
          headers: getHeaders(),
        }
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
        headers: getHeaders(),
      });
      if (!res.ok) throw new Error("Erro ao deletar jogador");
    },
  },
  times: {
    async getAll(): Promise<Time[]> {
      const res = await fetch(`${API_URL}/times`, { headers: getHeaders() });
      if (!res.ok) throw new Error("Erro ao buscar times");
      return res.json();
    },
    async create(time: Time): Promise<Time> {
      const res = await fetch(`${API_URL}/times`, {
        method: "POST",
        headers: getHeaders(),
        body: JSON.stringify(time),
      });
      if (!res.ok) throw new Error("Erro ao criar time");
      return res.json();
    },
  },
  torneios: {
    async getAll(): Promise<Torneio[]> {
      const res = await fetch(`${API_URL}/torneios`, { headers: getHeaders() });
      if (!res.ok) throw new Error("Erro ao buscar torneios");
      return res.json();
    },
    async create(t: Torneio): Promise<Torneio> {
      const res = await fetch(`${API_URL}/torneios`, {
        method: "POST",
        headers: getHeaders(),
        body: JSON.stringify(t),
      });
      if (!res.ok) throw new Error("Erro ao criar torneio");
      return res.json();
    },
  },
  matchmaking: {
    async viewQueue(): Promise<string[]> {
      const res = await fetch(`${API_URL}/matchmaking/fila`, {
        headers: getHeaders(),
      });
      if (!res.ok) throw new Error("Erro ao consultar fila de matchmaking");
      return res.json();
    },
    async join(nickname: string): Promise<void> {
      const res = await fetch(`${API_URL}/matchmaking/entrar?nickname=${encodeURIComponent(
        nickname
      )}`, {
        method: "POST",
        headers: getHeaders(),
      });
      if (!res.ok) throw new Error("Erro ao entrar na fila de matchmaking");
    },
    async leave(nickname: string): Promise<void> {
      const res = await fetch(`${API_URL}/matchmaking/sair?nickname=${encodeURIComponent(
        nickname
      )}`, {
        method: "POST",
        headers: getHeaders(),
      });
      if (!res.ok) throw new Error("Erro ao sair da fila de matchmaking");
    },
  },
};
