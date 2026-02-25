"use client";

import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import { Trophy, Users, Gamepad2, Swords, Home, User, LogOut } from "lucide-react";
import { motion } from "framer-motion";
import { cn } from "@/lib/utils";
import { useAuth } from "@/contexts/AuthContext";

export function Navbar() {
  const pathname = usePathname();
  const router = useRouter();
  const { jogador, isAuthenticated, logout } = useAuth();

  const links = [
    { href: "/", label: "Home", icon: Home },
    { href: "/rankings", label: "Rankings", icon: Trophy },
    { href: "/times", label: "Times", icon: Users },
    { href: "/torneios", label: "Torneios", icon: Gamepad2 },
    { href: "/matchmaking", label: "Matchmaking", icon: Swords },
  ];

  const handleLogout = () => {
    logout();
    router.push("/login");
  };

  return (
    <nav className="glass-strong border-b border-white/10 sticky top-0 z-50">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between h-16">
          {/* Logo */}
          <Link href="/" className="flex items-center gap-2">
            <div className="w-10 h-10 rounded-lg bg-gradient-to-br from-neon-blue to-neon-purple flex items-center justify-center">
              <Trophy className="w-6 h-6 text-white" />
            </div>
            <span className="text-xl font-bold">
              <span className="text-gradient-blue">ARENA</span>
              {" "}
              <span className="text-gradient-purple">NEXUS</span>
            </span>
          </Link>

          {/* Navigation Links */}
          <div className="hidden md:flex items-center gap-1">
            {links.map((link) => {
              const isActive = pathname === link.href;
              const Icon = link.icon;

              return (
                <Link
                  key={link.href}
                  href={link.href}
                  className={cn(
                    "relative px-4 py-2 rounded-lg transition-all duration-300 flex items-center gap-2",
                    isActive
                      ? "text-neon-blue"
                      : "text-gray-400 hover:text-white hover:bg-white/5"
                  )}
                >
                  <Icon className="w-4 h-4" />
                  <span className="font-medium">{link.label}</span>
                  {isActive && (
                    <motion.div
                      layoutId="navbar-indicator"
                      className="absolute bottom-0 left-0 right-0 h-0.5 bg-neon-blue"
                      transition={{ type: "spring", stiffness: 380, damping: 30 }}
                    />
                  )}
                </Link>
              );
            })}
          </div>

          {/* User Menu */}
          <div className="flex items-center gap-3">
            {isAuthenticated && jogador ? (
              <>
                <Link
                  href={`/jogador/${jogador.nickname}`}
                  className="flex items-center gap-2 px-3 py-2 rounded-lg bg-white/5 hover:bg-white/10 transition-colors"
                >
                  <User className="w-4 h-4 text-cyan-400" />
                  <span className="text-sm font-medium">{jogador.nickname}</span>
                  <span className="text-xs text-gray-400">{jogador.categoria}</span>
                </Link>
                <button
                  onClick={handleLogout}
                  className="p-2 rounded-lg bg-red-500/10 hover:bg-red-500/20 text-red-400 transition-colors"
                  title="Sair"
                >
                  <LogOut className="w-4 h-4" />
                </button>
              </>
            ) : (
              <Link
                href="/login"
                className="px-4 py-2 rounded-lg bg-gradient-to-r from-cyan-500 to-purple-500 hover:opacity-90 transition-opacity font-medium"
              >
                Entrar
              </Link>
            )}
          </div>

          {/* Mobile Menu Button */}
          <button className="md:hidden p-2 text-gray-400 hover:text-white">
            <svg
              className="w-6 h-6"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M4 6h16M4 12h16M4 18h16"
              />
            </svg>
          </button>
        </div>
      </div>
    </nav>
  );
}
