"use client";

import { createContext, useContext, useState, useEffect, ReactNode } from "react";
import { Jogador } from "@/types";
import { api, getAuthToken } from "@/lib/api";

interface AuthContextType {
  jogador: Jogador | null;
  isLoading: boolean;
  isAuthenticated: boolean;
  login: (nickname: string, senha: string) => Promise<void>;
  register: (data: {
    nickname: string;
    nomeReal: string;
    email: string;
    senha: string;
    dataNascimento: string;
  }) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [jogador, setJogador] = useState<Jogador | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const getTokenFromCookie = () => {
      if (typeof document === "undefined") return null;
      const match = document.cookie
        .split("; ")
        .find((c) => c.startsWith("authToken="));
      return match ? match.split("=")[1] : null;
    };

    const validateToken = async () => {
      let token = getAuthToken();
      // if not in localStorage, try cookie (could be set by middleware)
      if (!token) {
        token = getTokenFromCookie();
        if (token) {
          setAuthToken(token);
        }
      }

      if (token) {
        try {
          const jogadorData = await api.auth.validateToken();
          setJogador(jogadorData);
        } catch (error) {
          api.auth.logout();
        }
      }
      setIsLoading(false);
    };

    validateToken();
  }, []);

  const login = async (nickname: string, senha: string) => {
    const response = await api.auth.login({ nickname, senha });
    setJogador(response.jogador);
  };

  const register = async (data: {
    nickname: string;
    nomeReal: string;
    email: string;
    senha: string;
    dataNascimento: string;
  }) => {
    const response = await api.auth.register(data);
    setJogador(response.jogador);
  };

  const logout = () => {
    api.auth.logout();
    setJogador(null);
  };

  return (
    <AuthContext.Provider
      value={{
        jogador,
        isLoading,
        isAuthenticated: !!jogador,
        login,
        register,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
}
