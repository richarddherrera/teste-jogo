"use client";

import { useQuery } from "@tanstack/react-query";
import { motion } from "framer-motion";
import { useParams } from "next/navigation";
import { ArrowLeft, Mail, Calendar, Shield, TrendingUp } from "lucide-react";
import Link from "next/link";
import { api } from "@/lib/api";
import { RankCard } from "@/components/RankCard";
import { formatDate } from "@/lib/utils";

export default function JogadorPage() {
  const params = useParams();
  const nickname = params.nickname as string;

  const { data: jogador, isLoading, error } = useQuery({
    queryKey: ["jogador", nickname],
    queryFn: () => api.jogadores.getByNickname(nickname),
  });

  if (isLoading) {
    return (
      <div className="min-h-screen bg-background-primary flex items-center justify-center">
        <div className="text-center">
          <div className="w-16 h-16 border-4 border-neon-blue border-t-transparent rounded-full animate-spin mx-auto mb-4" />
          <p className="text-gray-400">Carregando perfil...</p>
        </div>
      </div>
    );
  }

  if (error || !jogador) {
    return (
      <div className="min-h-screen bg-background-primary flex items-center justify-center">
        <div className="text-center">
          <p className="text-red-400 mb-4">Jogador não encontrado</p>
          <Link href="/rankings" className="btn-primary">
            <ArrowLeft className="w-4 h-4 inline mr-2" />
            Voltar para Rankings
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-background-primary">
      <div className="container mx-auto px-4 py-8">
        {/* Back Button */}
        <Link href="/rankings" className="btn-secondary inline-flex items-center gap-2 mb-8">
          <ArrowLeft className="w-4 h-4" />
          Voltar
        </Link>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Main Profile Card */}
          <div className="lg:col-span-1">
            <motion.div
              initial={{ opacity: 0, x: -20 }}
              animate={{ opacity: 1, x: 0 }}
            >
              <RankCard jogador={jogador} showProgress={true} />
            </motion.div>
          </div>

          {/* Details */}
          <div className="lg:col-span-2 space-y-6">
            {/* Personal Info */}
            <motion.div
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: 0.1 }}
              className="card"
            >
              <h2 className="text-2xl font-bold text-white mb-6">
                Informações Pessoais
              </h2>
              <div className="space-y-4">
                <div className="flex items-center gap-3 text-gray-300">
                  <Mail className="w-5 h-5 text-neon-blue" />
                  <span>{jogador.email}</span>
                </div>
                <div className="flex items-center gap-3 text-gray-300">
                  <Calendar className="w-5 h-5 text-neon-cyan" />
                  <span>Nascimento: {formatDate(jogador.dataNascimento)}</span>
                </div>
                <div className="flex items-center gap-3 text-gray-300">
                  <Shield className="w-5 h-5 text-neon-purple" />
                  <span>Status: {jogador.status}</span>
                </div>
              </div>
            </motion.div>

            {/* Stats */}
            <motion.div
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: 0.2 }}
              className="card"
            >
              <h2 className="text-2xl font-bold text-white mb-6 flex items-center gap-2">
                <TrendingUp className="w-6 h-6 text-neon-blue" />
                Estatísticas
              </h2>
              <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                <div className="glass rounded-lg p-4 text-center">
                  <div className="text-3xl font-bold text-gradient-blue mb-1">
                    {jogador.elo}
                  </div>
                  <div className="text-xs text-gray-400">ELO Atual</div>
                </div>
                <div className="glass rounded-lg p-4 text-center">
                  <div className="text-3xl font-bold text-gradient-purple mb-1">
                    {jogador.categoria}
                  </div>
                  <div className="text-xs text-gray-400">Categoria</div>
                </div>
                <div className="glass rounded-lg p-4 text-center">
                  <div className="text-3xl font-bold text-green-400 mb-1">
                    0
                  </div>
                  <div className="text-xs text-gray-400">Vitórias</div>
                </div>
                <div className="glass rounded-lg p-4 text-center">
                  <div className="text-3xl font-bold text-red-400 mb-1">
                    0
                  </div>
                  <div className="text-xs text-gray-400">Derrotas</div>
                </div>
              </div>
            </motion.div>

            {/* ELO History Chart Placeholder */}
            <motion.div
              initial={{ opacity: 0, x: 20 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ delay: 0.3 }}
              className="card"
            >
              <h2 className="text-2xl font-bold text-white mb-6">
                Histórico de ELO
              </h2>
              <div className="h-64 flex items-center justify-center glass rounded-lg">
                <p className="text-gray-500">
                  Gráfico de histórico será implementado em breve
                </p>
              </div>
            </motion.div>
          </div>
        </div>
      </div>
    </div>
  );
}
