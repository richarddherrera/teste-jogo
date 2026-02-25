"use client";

import { motion } from "framer-motion";
import { Gamepad2, Trophy, Users, Calendar } from "lucide-react";
import { useQuery } from "@tanstack/react-query";
import { api } from "@/lib/api";
import { Torneio } from "@/types";

export default function TorneiosPage() {
  const { data: torneios, isLoading, error } = useQuery<Torneio[]>({
    queryKey: ["torneios"],
    queryFn: () => api.torneios.getAll(),
  });

  if (isLoading) {
    return <p className="text-center mt-12">Carregando torneios...</p>;
  }

  if (error) {
    const msg = error instanceof Error ? error.message : "Erro ao carregar torneios";
    return <p className="text-center mt-12 text-red-400">{msg}</p>;
  }
  return (
    <div className="min-h-screen">
      <div className="container mx-auto px-4 py-8">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="mb-8"
        >
          <h1 className="text-4xl font-bold mb-4 neon-text">Torneios</h1>
          <p className="text-gray-400">
            Participe de torneios competitivos e ganhe prêmios
          </p>
        </motion.div>

        {/* Filters */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.1 }}
          className="flex gap-4 mb-8"
        >
          <button className="btn-primary">Todos</button>
          <button className="btn-secondary">Inscrições Abertas</button>
          <button className="btn-secondary">Em Andamento</button>
          <button className="btn-secondary">Finalizados</button>
        </motion.div>

        {/* Torneios Ativos */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.2 }}
          className="glass-card p-8 rounded-2xl mb-8"
        >
          <h2 className="text-2xl font-bold mb-6 flex items-center gap-2">
            <Gamepad2 className="w-6 h-6 text-cyan-400" />
            Torneios com Inscrições Abertas
          </h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {torneios && torneios.length > 0 ? (
              torneios.map((t, idx) => (
                <div key={idx} className="glass rounded-lg p-6">
                  <div className="flex items-start justify-between mb-4">
                    <div>
                      <h3 className="text-xl font-bold mb-2">{t.nome}</h3>
                      <p className="text-sm text-gray-400">
                        Formato: {t.formato.replace(/_/g, " ")}
                      </p>
                    </div>
                    <span className="px-3 py-1 bg-green-500/20 text-green-400 rounded-full text-xs">
                      {t.status.replace(/_/g, " ")}
                    </span>
                  </div>
                  <div className="space-y-2 mb-4">
                    <div className="flex items-center gap-2 text-sm">
                      <Calendar className="w-4 h-4 text-cyan-400" />
                      <span>Início: {new Date(t.dataInicio).toLocaleDateString()}</span>
                    </div>
                    <div className="flex items-center gap-2 text-sm">
                      <Users className="w-4 h-4 text-purple-400" />
                      <span>
                        {t.participantes.length}/{/* assumimos 32 */}32 participantes
                      </span>
                    </div>
                    <div className="flex items-center gap-2 text-sm">
                      <Trophy className="w-4 h-4 text-yellow-400" />
                      <span>Prêmio: R$ {t.premioTotal}</span>
                    </div>
                  </div>
                  <button className="w-full btn-primary">Inscrever-se</button>
                </div>
              ))
            ) : (
              <p className="text-gray-400">Nenhum torneio disponível</p>
            )}
          </div>
        </motion.div>

        {/* Meus Torneios */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.3 }}
          className="glass-card p-8 rounded-2xl"
        >
          <h2 className="text-2xl font-bold mb-6 flex items-center gap-2">
            <Trophy className="w-6 h-6 text-yellow-400" />
            Meus Torneios
          </h2>
          <div className="text-center py-12">
            <Gamepad2 className="w-16 h-16 text-gray-600 mx-auto mb-4" />
            <p className="text-gray-400 mb-4">Você ainda não está inscrito em nenhum torneio</p>
            <button className="btn-primary">
              Explorar Torneios
            </button>
          </div>
        </motion.div>
      </div>
    </div>
  );
}
