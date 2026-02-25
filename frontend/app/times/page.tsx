"use client";

import { motion } from "framer-motion";
import { Users } from "lucide-react";
import { useQuery } from "@tanstack/react-query";
import { api } from "@/lib/api";
import { Time } from "@/types";

export default function TimesPage() {
  const { data: times, isLoading, error } = useQuery<Time[]>({
    queryKey: ["times"],
    queryFn: () => api.times.getAll(),
  });

  if (isLoading) {
    return <p className="text-center mt-12">Carregando times...</p>;
  }

  if (error) {
    const msg = error instanceof Error ? error.message : "Erro ao carregar times";
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
          <h1 className="text-4xl font-bold mb-4 neon-text">Times</h1>
          <p className="text-gray-400">
            Gerencie seus times e participe de competições em equipe
          </p>
        </motion.div>

        {/* Action Buttons */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.1 }}
          className="flex gap-4 mb-8"
        >
          <button className="btn-primary flex items-center gap-2">
            <Users className="w-5 h-5" />
            Criar Time
          </button>
          <button className="btn-secondary flex items-center gap-2">
            <Users className="w-5 h-5" />
            Buscar Times
          </button>
        </motion.div>

        {/* Meus Times */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.2 }}
          className="glass-card p-8 rounded-2xl mb-8"
        >
          <h2 className="text-2xl font-bold mb-6 flex items-center gap-2">
            <Users className="w-6 h-6 text-cyan-400" />
            Meus Times
          </h2>
          <div className="text-center py-12">
            <Users className="w-16 h-16 text-gray-600 mx-auto mb-4" />
            {times && times.length > 0 ? (
              <>
                <p className="text-gray-400 mb-4">
                  Você participa de {times.length} time{times.length > 1 ? "s" : ""}
                </p>
                <button className="btn-primary">Ver meus times</button>
              </>
            ) : (
              <>
                <p className="text-gray-400 mb-4">
                  Você ainda não faz parte de nenhum time
                </p>
                <button className="btn-primary">Criar Meu Primeiro Time</button>
              </>
            )}
          </div>
        </motion.div>

        {/* Times Populares */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ delay: 0.3 }}
          className="glass-card p-8 rounded-2xl"
        >
          <h2 className="text-2xl font-bold mb-6 flex items-center gap-2">
            <Users className="w-6 h-6 text-yellow-400" />
            Times Populares
          </h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {times && times.length > 0 ? (
              times.map((t) => (
                <div
                  key={t.tag}
                  className="glass rounded-lg p-6 hover:bg-white/10 transition-colors"
                >
                  <div className="flex items-center gap-3 mb-4">
                    <div className="w-12 h-12 rounded-lg bg-gradient-to-br from-cyan-500 to-purple-500 flex items-center justify-center">
                      <Users className="w-6 h-6" />
                    </div>
                    <div>
                      <h3 className="font-bold">{t.nome}</h3>
                      <p className="text-sm text-gray-400">
                        {t.membros.length} membro{t.membros.length > 1 ? "s" : ""}
                      </p>
                    </div>
                  </div>
                  <div className="flex items-center justify-between text-sm">
                    <button className="text-cyan-400 hover:text-cyan-300">
                      Ver Detalhes
                    </button>
                  </div>
                </div>
              ))
            ) : (
              <p className="text-gray-400">Nenhum time cadastrado ainda.</p>
            )}
          </div>
        </motion.div>
      </div>
    </div>
  );
}
