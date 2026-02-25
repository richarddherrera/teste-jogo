"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import { useAuth } from "@/contexts/AuthContext";

export default function LoginPage() {
  const router = useRouter();
  const { login } = useAuth();
  const [nickname, setNickname] = useState("");
  const [senha, setSenha] = useState("");
  const [error, setError] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setIsLoading(true);

    try {
      // trim values to avoid accidental spaces
      await login(nickname.trim(), senha);
      router.push("/");
    } catch (err: any) {
      setError(err.message || "Erro ao fazer login");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center p-4">
      <div className="w-full max-w-md">
        <div className="glass-card p-8 rounded-2xl">
          <h1 className="text-4xl font-bold text-center mb-2 neon-text">
            Login Arena
          </h1>
          <p className="text-center text-gray-400 mb-8">
            Entre para acessar a plataforma
          </p>

          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <label htmlFor="nickname" className="block text-sm font-medium mb-2">
                Nickname
              </label>
              <input
                id="nickname"
                type="text"
                value={nickname}
                onChange={(e) => setNickname(e.target.value)}
                required
                className="w-full px-4 py-3 bg-black/30 border border-cyan-500/30 rounded-lg focus:outline-none focus:border-cyan-500 transition-colors"
                placeholder="Digite seu nickname"
              />
            </div>

            <div>
              <label htmlFor="senha" className="block text-sm font-medium mb-2">
                Senha
              </label>
              <input
                id="senha"
                type="password"
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
                required
                className="w-full px-4 py-3 bg-black/30 border border-cyan-500/30 rounded-lg focus:outline-none focus:border-cyan-500 transition-colors"
                placeholder="Digite sua senha"
              />
            </div>

            {error && (
              <div className="p-3 bg-red-500/10 border border-red-500/30 rounded-lg text-red-400 text-sm">
                {error}
              </div>
            )}

            <button
              type="submit"
              disabled={isLoading}
              className="w-full py-3 bg-gradient-to-r from-cyan-500 to-purple-500 rounded-lg font-semibold hover:opacity-90 transition-opacity disabled:opacity-50"
            >
              {isLoading ? "Entrando..." : "Entrar"}
            </button>
          </form>

          <div className="mt-6 text-center">
            <p className="text-gray-400">
              Não tem uma conta?{" "}
              <Link href="/register" className="text-cyan-400 hover:text-cyan-300">
                Registre-se
              </Link>
            </p>
          </div>

          <div className="mt-4 p-3 bg-cyan-500/10 border border-cyan-500/30 rounded-lg text-sm text-gray-300">
            <p className="font-semibold mb-1">Contas de teste:</p>
            <p>Nickname: aspas | Senha: senha123</p>
            <p>Nickname: Less | Senha: senha123</p>
          </div>
        </div>
      </div>
    </div>
  );
}
