"use client";

import { motion } from "framer-motion";
import { Gamepad2, ArrowLeft } from "lucide-react";
import Link from "next/link";

export default function TorneiosPage() {
  return (
    <div className="min-h-screen bg-background-primary">
      <div className="container mx-auto px-4 py-20">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="text-center"
        >
          <Gamepad2 className="w-24 h-24 text-neon-cyan mx-auto mb-6" />
          <h1 className="text-5xl font-bold text-gradient-blue mb-4">
            Torneios
          </h1>
          <p className="text-gray-400 text-xl mb-8">
            Página em desenvolvimento
          </p>
          <Link href="/" className="btn-primary inline-flex items-center gap-2">
            <ArrowLeft className="w-4 h-4" />
            Voltar para Home
          </Link>
        </motion.div>
      </div>
    </div>
  );
}
