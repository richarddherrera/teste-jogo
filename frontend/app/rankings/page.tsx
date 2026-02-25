"use client";

import { useQuery } from "@tanstack/react-query";
import { motion } from "framer-motion";
import { Trophy, TrendingUp, Medal, Crown } from "lucide-react";
import { api } from "@/lib/api";
import { RankCard } from "@/components/RankCard";
import { getRankColor, getMedalEmoji } from "@/lib/utils";

export default function RankingsPage() {
  const { data: ranking, isLoading, error } = useQuery({
    queryKey: ["ranking"],
    queryFn: () => api.jogadores.getRanking(10),
    refetchInterval: 30000, // Atualiza a cada 30 segundos
  });

  if (isLoading) {
    return (
      <div className="min-h-screen bg-background-primary flex items-center justify-center">
        <div className="text-center">
          <div className="w-16 h-16 border-4 border-neon-blue border-t-transparent rounded-full animate-spin mx-auto mb-4" />
          <p className="text-gray-400">Carregando rankings...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-background-primary flex items-center justify-center">
        <div className="text-center">
          <p className="text-red-400 mb-4">Erro ao carregar rankings</p>
          <p className="text-gray-500 text-sm">
            Certifique-se de que a API está rodando em http://localhost:8080
          </p>
        </div>
      </div>
    );
  }

  const top3 = ranking?.slice(0, 3) || [];
  const restante = ranking?.slice(3) || [];

  return (
    <div className="min-h-screen bg-background-primary">
      {/* Header */}
      <div className="container mx-auto px-4 py-8">
        <motion.div
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          className="text-center mb-12"
        >
          <div className="flex items-center justify-center gap-3 mb-4">
            <Trophy className="w-12 h-12 text-neon-blue" />
            <h1 className="text-5xl font-bold text-gradient-blue">
              RANKING GLOBAL
            </h1>
          </div>
          <p className="text-gray-400 text-lg">
            Top jogadores por ELO - Atualizado em tempo real
          </p>
        </motion.div>

        {/* Top 3 Podium */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
          {/* 2º Lugar */}
          {top3[1] && (
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.2 }}
              className="md:order-1"
            >
              <div className="text-center mb-4">
                <div className="text-6xl mb-2">{getMedalEmoji(2)}</div>
                <div className="text-2xl font-bold text-gray-300">2º Lugar</div>
              </div>
              <RankCard jogador={top3[1]} />
            </motion.div>
          )}

          {/* 1º Lugar */}
          {top3[0] && (
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.1 }}
              className="md:order-2"
            >
              <div className="text-center mb-4">
                <Crown className="w-16 h-16 text-rank-gold mx-auto mb-2 animate-pulse" />
                <div className="text-3xl font-bold text-gradient-blue">
                  CAMPEÃO
                </div>
              </div>
              <div className="transform scale-105">
                <RankCard jogador={top3[0]} />
              </div>
            </motion.div>
          )}

          {/* 3º Lugar */}
          {top3[2] && (
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.3 }}
              className="md:order-3"
            >
              <div className="text-center mb-4">
                <div className="text-6xl mb-2">{getMedalEmoji(3)}</div>
                <div className="text-2xl font-bold text-gray-300">3º Lugar</div>
              </div>
              <RankCard jogador={top3[2]} />
            </motion.div>
          )}
        </div>

        {/* Restante do Ranking */}
        {restante.length > 0 && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ delay: 0.4 }}
            className="glass-strong rounded-2xl p-6"
          >
            <h2 className="text-2xl font-bold text-white mb-6 flex items-center gap-2">
              <TrendingUp className="w-6 h-6 text-neon-cyan" />
              Top 4-10
            </h2>
            <div className="space-y-4">
              {restante.map((jogador, index) => (
                <motion.div
                  key={jogador.nickname}
                  initial={{ opacity: 0, x: -20 }}
                  animate={{ opacity: 1, x: 0 }}
                  transition={{ delay: 0.5 + index * 0.1 }}
                  className="glass rounded-lg p-4 flex items-center gap-4 hover:glass-strong transition-all"
                >
                  <div className="text-3xl font-bold text-gray-500 w-12 text-center">
                    #{index + 4}
                  </div>
                  <div className="flex-1">
                    <div className="flex items-center justify-between">
                      <div>
                        <h3 className="text-xl font-bold text-white">
                          {jogador.nickname}
                        </h3>
                        <p className="text-sm text-gray-400">
                          {jogador.nomeReal}
                        </p>
                      </div>
                      <div className="text-right">
                        <div
                          className={`text-2xl font-bold ${getRankColor(
                            jogador.categoria
                          )}`}
                        >
                          {jogador.elo}
                        </div>
                        <div className="text-xs text-gray-400 uppercase">
                          {jogador.categoria}
                        </div>
                      </div>
                    </div>
                  </div>
                </motion.div>
              ))}
            </div>
          </motion.div>
        )}

        {/* Stats */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.6 }}
          className="mt-12 grid grid-cols-1 md:grid-cols-3 gap-6"
        >
          <div className="card text-center">
            <Medal className="w-8 h-8 text-neon-blue mx-auto mb-2" />
            <div className="text-3xl font-bold text-gradient-blue mb-1">
              {ranking?.[0]?.elo || 0}
            </div>
            <div className="text-sm text-gray-400">ELO Mais Alto</div>
          </div>
          <div className="card text-center">
            <TrendingUp className="w-8 h-8 text-neon-cyan mx-auto mb-2" />
            <div className="text-3xl font-bold text-gradient-blue mb-1">
              {ranking?.length || 0}
            </div>
            <div className="text-sm text-gray-400">Jogadores Ranqueados</div>
          </div>
          <div className="card text-center">
            <Trophy className="w-8 h-8 text-neon-purple mx-auto mb-2" />
            <div className="text-3xl font-bold text-gradient-purple mb-1">
              {Math.round(
                (ranking?.reduce((sum, j) => sum + j.elo, 0) || 0) /
                  (ranking?.length || 1)
              )}
            </div>
            <div className="text-sm text-gray-400">ELO Médio</div>
          </div>
        </motion.div>
      </div>
    </div>
  );
}
