"use client";

import { useState } from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { motion } from "framer-motion";
import { Swords, Users, Gamepad2, Clock } from "lucide-react";
import { api } from "@/lib/api";

export default function MatchmakingPage() {
  const [isSearching, setIsSearching] = useState(false);
  const queryClient = useQueryClient();

  const { data: queue = [] } = useQuery<string[]>({
    queryKey: ["matchmaking", "queue"],
    queryFn: () => api.matchmaking.viewQueue(),
    refetchInterval: 5000,
  });

  const joinMutation = useMutation({
    mutationFn: (nickname: string) => api.matchmaking.join(nickname),
    onSuccess: () => queryClient.invalidateQueries(["matchmaking", "queue"]),
  });

  const leaveMutation = useMutation({
    mutationFn: (nickname: string) => api.matchmaking.leave(nickname),
    onSuccess: () => queryClient.invalidateQueries(["matchmaking", "queue"]),
  });

  return (
    <div className="min-h-screen">
      <div className="container mx-auto px-4 py-8">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="mb-8"
        >
          <h1 className="text-4xl font-bold mb-4 neon-text">Matchmaking</h1>
          <p className="text-gray-400">
            Encontre partidas equilibradas baseadas no seu ELO
          </p>
        </motion.div>

        {/* Search Panel */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.1 }}
          className="glass-card p-8 rounded-2xl mb-8"
        >
          <div className="text-center">
            {!isSearching ? (
              <>
                <Swords className="w-24 h-24 text-cyan-400 mx-auto mb-6" />
                <h2 className="text-2xl font-bold mb-4">Pronto para Competir?</h2>
                <p className="text-gray-400 mb-8">
                  Clique no botão abaixo para entrar na fila de matchmaking
                </p>
                <button
                  onClick={() => {
                    setIsSearching(true);
                    // for now we just simulate with a dummy nickname
                    joinMutation.mutate("usuario-exemplo");
                  }}
                  className="btn-primary text-lg px-8 py-4"
                >
                  Buscar Partida
                </button>
              </>
            ) : (
              <>
                <div className="w-24 h-24 border-4 border-cyan-500 border-t-transparent rounded-full animate-spin mx-auto mb-6" />
                <h2 className="text-2xl font-bold mb-4">Procurando Adversários...</h2>
                <p className="text-gray-400 mb-8">
                  Aguarde enquanto encontramos jogadores do seu nível
                </p>
                <button
                  onClick={() => {
                    setIsSearching(false);
                    leaveMutation.mutate("usuario-exemplo");
                  }}
                  className="btn-secondary"
                >
                  Cancelar Busca
                </button>
              </>
            )}
          </div>
        </motion.div>

        {/* Stats */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.2 }}
          className="grid grid-cols-1 md:grid-cols-3 gap-6"
        >
          <div className="glass-card p-6 rounded-xl text-center">
            <Users className="w-8 h-8 text-cyan-400 mx-auto mb-3" />
            <div className="text-3xl font-bold mb-2">24</div>
            <div className="text-sm text-gray-400">Jogadores Online</div>
          </div>
          <div className="glass-card p-6 rounded-xl text-center">
            <Gamepad2 className="w-8 h-8 text-purple-400 mx-auto mb-3" />
            <div className="text-3xl font-bold mb-2">8</div>
            <div className="text-sm text-gray-400">Partidas em Andamento</div>
          </div>
          <div className="glass-card p-6 rounded-xl text-center">
            <Clock className="w-8 h-8 text-yellow-400 mx-auto mb-3" />
            <div className="text-3xl font-bold mb-2">~2min</div>
            <div className="text-sm text-gray-400">Tempo Médio de Espera</div>
          </div>
        </motion.div>
        {/* fila atual (demonstração) */}
        <div className="mt-6">
          <h3 className="text-lg font-medium text-white mb-2">Fila atual</h3>
          {queue.length > 0 ? (
            <ul className="space-y-1 text-gray-300">
              {queue.map((n) => (
                <li key={n}>{n}</li>
              ))}
            </ul>
          ) : (
            <p className="text-gray-400">Fila vazia</p>
          )}
        </div>
      </div>
    </div>
  );
}
