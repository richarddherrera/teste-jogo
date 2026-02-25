"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import { useAuth } from "@/contexts/AuthContext";

export default function RegisterPage() {
  const router = useRouter();
  const { register } = useAuth();
  const [formData, setFormData] = useState({
    nickname: "",
    nomeReal: "",
    email: "",
    senha: "",
    confirmarSenha: "",
    dataNascimento: "",
  });
  const [error, setError] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    if (formData.senha !== formData.confirmarSenha) {
      setError("As senhas não coincidem");
      return;
    }

    if (formData.senha.length < 6) {
      setError("A senha deve ter no mínimo 6 caracteres");
      return;
    }

    setIsLoading(true);

    try {
      await register({
        nickname: formData.nickname.trim(),
        nomeReal: formData.nomeReal.trim(),
        email: formData.email.trim(),
        senha: formData.senha,
        dataNascimento: formData.dataNascimento,
      });
      router.push("/");
    } catch (err: any) {
      setError(err.message || "Erro ao registrar");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center p-4">
      <div className="w-full max-w-md">
        <div className="glass-card p-8 rounded-2xl">
          <h1 className="text-4xl font-bold text-center mb-2 neon-text">
            Criar Conta
          </h1>
          <p className="text-center text-gray-400 mb-8">
            Registre-se para começar a competir
          </p>

          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label htmlFor="nickname" className="block text-sm font-medium mb-2">
                Nickname
              </label>
              <input
                id="nickname"
                name="nickname"
                type="text"
                value={formData.nickname}
                onChange={handleChange}
                required
                className="w-full px-4 py-3 bg-black/30 border border-cyan-500/30 rounded-lg focus:outline-none focus:border-cyan-500 transition-colors"
                placeholder="Seu nickname único"
              />
            </div>

            <div>
              <label htmlFor="nomeReal" className="block text-sm font-medium mb-2">
                Nome Completo
              </label>
              <input
                id="nomeReal"
                name="nomeReal"
                type="text"
                value={formData.nomeReal}
                onChange={handleChange}
                required
                className="w-full px-4 py-3 bg-black/30 border border-cyan-500/30 rounded-lg focus:outline-none focus:border-cyan-500 transition-colors"
                placeholder="Seu nome completo"
              />
            </div>

            <div>
              <label htmlFor="email" className="block text-sm font-medium mb-2">
                Email
              </label>
              <input
                id="email"
                name="email"
                type="email"
                value={formData.email}
                onChange={handleChange}
                required
                className="w-full px-4 py-3 bg-black/30 border border-cyan-500/30 rounded-lg focus:outline-none focus:border-cyan-500 transition-colors"
                placeholder="seu@email.com"
              />
            </div>

            <div>
              <label htmlFor="dataNascimento" className="block text-sm font-medium mb-2">
                Data de Nascimento
              </label>
              <input
                id="dataNascimento"
                name="dataNascimento"
                type="date"
                value={formData.dataNascimento}
                onChange={handleChange}
                required
                className="w-full px-4 py-3 bg-black/30 border border-cyan-500/30 rounded-lg focus:outline-none focus:border-cyan-500 transition-colors"
              />
            </div>

            <div>
              <label htmlFor="senha" className="block text-sm font-medium mb-2">
                Senha
              </label>
              <input
                id="senha"
                name="senha"
                type="password"
                value={formData.senha}
                onChange={handleChange}
                required
                minLength={6}
                className="w-full px-4 py-3 bg-black/30 border border-cyan-500/30 rounded-lg focus:outline-none focus:border-cyan-500 transition-colors"
                placeholder="Mínimo 6 caracteres"
              />
            </div>

            <div>
              <label htmlFor="confirmarSenha" className="block text-sm font-medium mb-2">
                Confirmar Senha
              </label>
              <input
                id="confirmarSenha"
                name="confirmarSenha"
                type="password"
                value={formData.confirmarSenha}
                onChange={handleChange}
                required
                className="w-full px-4 py-3 bg-black/30 border border-cyan-500/30 rounded-lg focus:outline-none focus:border-cyan-500 transition-colors"
                placeholder="Digite a senha novamente"
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
              {isLoading ? "Registrando..." : "Criar Conta"}
            </button>
          </form>

          <div className="mt-6 text-center">
            <p className="text-gray-400">
              Já tem uma conta?{" "}
              <Link href="/login" className="text-cyan-400 hover:text-cyan-300">
                Faça login
              </Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
