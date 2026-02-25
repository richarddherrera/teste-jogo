"use client";

import { motion } from "framer-motion";
import { Trophy, TrendingUp, TrendingDown } from "lucide-react";
import { Jogador } from "@/types";
import { cn, getRankColor, getRankGlow, calculateRankProgress } from "@/lib/utils";

interface RankCardProps {
  jogador: Jogador;
  showProgress?: boolean;
  className?: string;
}

export function RankCard({ jogador, showProgress = true, className }: RankCardProps) {
  const progress = calculateRankProgress(jogador.elo, jogador.categoria);
  const rankColor = getRankColor(jogador.categoria);
  const rankGlow = getRankGlow(jogador.categoria);

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.5 }}
      className={cn("card-rank", rankGlow, className)}
    >
      {/* Background gradient effect */}
      <div className="absolute inset-0 bg-gradient-to-br from-white/5 to-transparent rounded-xl" />
      
      <div className="relative z-10">
        {/* Header */}
        <div className="flex items-center justify-between mb-4">
          <div className="flex items-center gap-3">
            <div className={cn("p-3 rounded-lg glass", rankGlow)}>
              <Trophy className={cn("w-6 h-6", rankColor)} />
            </div>
            <div>
              <h3 className="text-lg font-bold text-white">{jogador.nickname}</h3>
              <p className="text-sm text-gray-400">{jogador.nomeReal}</p>
            </div>
          </div>
          
          <div className="text-right">
            <div className={cn("text-3xl font-bold", rankColor)}>
              {jogador.elo}
            </div>
            <div className="text-xs text-gray-400 uppercase tracking-wider">
              ELO
            </div>
          </div>
        </div>

        {/* Rank Badge */}
        <div className="flex items-center justify-between mb-4">
          <div className={cn(
            "px-4 py-2 rounded-lg font-bold text-lg uppercase tracking-wider",
            "glass border-2",
            rankColor
          )}>
            {jogador.categoria}
          </div>
          
          <div className="flex items-center gap-2 text-sm">
            {progress > 50 ? (
              <>
                <TrendingUp className="w-4 h-4 text-green-400" />
                <span className="text-green-400">Subindo</span>
              </>
            ) : (
              <>
                <TrendingDown className="w-4 h-4 text-yellow-400" />
                <span className="text-yellow-400">Estável</span>
              </>
            )}
          </div>
        </div>

        {/* Progress Bar */}
        {showProgress && (
          <div className="space-y-2">
            <div className="flex justify-between text-xs text-gray-400">
              <span>Progresso no Rank</span>
              <span>{progress.toFixed(0)}%</span>
            </div>
            <div className="h-2 bg-background-secondary rounded-full overflow-hidden">
              <motion.div
                initial={{ width: 0 }}
                animate={{ width: `${progress}%` }}
                transition={{ duration: 1, delay: 0.3 }}
                className={cn("h-full rounded-full", rankColor.replace("text-", "bg-"))}
                style={{
                  boxShadow: `0 0 10px currentColor`,
                }}
              />
            </div>
          </div>
        )}

        {/* Status Badge */}
        <div className="mt-4 flex items-center gap-2">
          <div className={cn(
            "w-2 h-2 rounded-full",
            jogador.status === "ATIVO" ? "bg-green-400 animate-pulse" :
            jogador.status === "BANIDO" ? "bg-red-500" :
            "bg-gray-500"
          )} />
          <span className="text-xs text-gray-400 uppercase">
            {jogador.status}
          </span>
        </div>
      </div>
    </motion.div>
  );
}
