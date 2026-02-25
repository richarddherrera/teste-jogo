"use client";

import { motion } from "framer-motion";
import { Trophy, Users, Gamepad2, Swords, TrendingUp } from "lucide-react";
import Link from "next/link";

export default function Home() {
  const features = [
    {
      icon: Trophy,
      title: "Rankings Globais",
      description: "Sistema de ELO dinâmico com 5 categorias competitivas",
      href: "/rankings",
      color: "text-neon-blue",
      glow: "glow-blue",
    },
    {
      icon: Users,
      title: "Gestão de Times",
      description: "Monte seu time e conquiste torneios",
      href: "/times",
      color: "text-neon-purple",
      glow: "glow-purple",
    },
    {
      icon: Gamepad2,
      title: "Torneios",
      description: "Participe de competições oficiais",
      href: "/torneios",
      color: "text-neon-cyan",
      glow: "glow-cyan",
    },
    {
      icon: Swords,
      title: "Matchmaking",
      description: "Encontre adversários do seu nível",
      href: "/matchmaking",
      color: "text-neon-blue",
      glow: "glow-blue",
    },
  ];

  return (
    <div className="min-h-screen bg-background-primary">
      {/* Hero Section */}
      <section className="relative overflow-hidden">
        {/* Animated background */}
        <div className="absolute inset-0 bg-gradient-to-br from-neon-blue/10 via-transparent to-neon-purple/10" />
        <div className="absolute inset-0">
          <div className="absolute top-1/4 left-1/4 w-96 h-96 bg-neon-blue/20 rounded-full blur-3xl animate-pulse-slow" />
          <div className="absolute bottom-1/4 right-1/4 w-96 h-96 bg-neon-purple/20 rounded-full blur-3xl animate-pulse-slow" />
        </div>

        <div className="relative container mx-auto px-4 py-20">
          <motion.div
            initial={{ opacity: 0, y: 30 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8 }}
            className="text-center max-w-4xl mx-auto"
          >
            {/* Logo/Title */}
            <motion.div
              initial={{ scale: 0.9 }}
              animate={{ scale: 1 }}
              transition={{ duration: 0.5 }}
              className="mb-8"
            >
              <h1 className="text-7xl font-bold mb-4">
                <span className="text-gradient-blue">ARENA</span>
                {" "}
                <span className="text-gradient-purple">NEXUS</span>
              </h1>
              <div className="h-1 w-32 mx-auto bg-gradient-to-r from-neon-blue via-neon-cyan to-neon-purple rounded-full" />
            </motion.div>

            <motion.p
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              transition={{ delay: 0.3, duration: 0.8 }}
              className="text-xl text-gray-300 mb-8 max-w-2xl mx-auto"
            >
              Sistema profissional de gerenciamento de torneios de e-sports.
              Ranqueamento ELO, matchmaking inteligente e competições oficiais.
            </motion.p>

            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.5, duration: 0.8 }}
              className="flex gap-4 justify-center"
            >
              <Link href="/rankings" className="btn-primary">
                <TrendingUp className="w-5 h-5 inline mr-2" />
                Ver Rankings
              </Link>
              <Link href="/torneios" className="btn-secondary">
                <Trophy className="w-5 h-5 inline mr-2" />
                Torneios Ativos
              </Link>
            </motion.div>
          </motion.div>
        </div>
      </section>

      {/* Features Grid */}
      <section className="container mx-auto px-4 py-20">
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.8, duration: 0.8 }}
          className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6"
        >
          {features.map((feature, index) => (
            <motion.div
              key={feature.title}
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.9 + index * 0.1, duration: 0.5 }}
            >
              <Link href={feature.href}>
                <div className={cn(
                  "card group cursor-pointer hover:scale-105 transition-transform duration-300",
                  feature.glow
                )}>
                  <div className={cn(
                    "w-12 h-12 rounded-lg glass flex items-center justify-center mb-4",
                    feature.glow
                  )}>
                    <feature.icon className={cn("w-6 h-6", feature.color)} />
                  </div>
                  <h3 className="text-xl font-bold text-white mb-2">
                    {feature.title}
                  </h3>
                  <p className="text-gray-400 text-sm">
                    {feature.description}
                  </p>
                </div>
              </Link>
            </motion.div>
          ))}
        </motion.div>
      </section>

      {/* Stats Section */}
      <section className="container mx-auto px-4 py-20">
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 1.2, duration: 0.8 }}
          className="glass-strong rounded-2xl p-8"
        >
          <div className="grid grid-cols-2 md:grid-cols-4 gap-8">
            {[
              { label: "Jogadores Ativos", value: "1,234", icon: Users },
              { label: "Torneios", value: "56", icon: Trophy },
              { label: "Partidas Hoje", value: "892", icon: Swords },
              { label: "Times", value: "234", icon: Gamepad2 },
            ].map((stat, index) => (
              <motion.div
                key={stat.label}
                initial={{ opacity: 0, scale: 0.9 }}
                animate={{ opacity: 1, scale: 1 }}
                transition={{ delay: 1.3 + index * 0.1, duration: 0.5 }}
                className="text-center"
              >
                <stat.icon className="w-8 h-8 text-neon-blue mx-auto mb-2" />
                <div className="text-3xl font-bold text-gradient-blue mb-1">
                  {stat.value}
                </div>
                <div className="text-sm text-gray-400">{stat.label}</div>
              </motion.div>
            ))}
          </div>
        </motion.div>
      </section>
    </div>
  );
}

function cn(...classes: string[]) {
  return classes.filter(Boolean).join(" ");
}
