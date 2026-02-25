"use client";

import { motion } from "framer-motion";
import { Users, ArrowLeft } from "lucide-react";
import Link from "next/link";

export default function TimesPage() {
  return (
    <div className="min-h-screen bg-background-primary">
      <div className="container mx-auto px-4 py-20">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="text-center"
        >
          <Users className="w-24 h-24 text-neon-purple mx-auto mb-6" />
          <h1 className="text-5xl font-bold text-gradient-purple mb-4">
            Times
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
