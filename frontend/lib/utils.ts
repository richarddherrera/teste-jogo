import { type ClassValue, clsx } from "clsx";
import { twMerge } from "tailwind-merge";
import { Categoria } from "@/types";

/**
 * Utility function to merge Tailwind CSS classes
 */
export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

/**
 * Get rank color based on category
 */
export function getRankColor(categoria: Categoria): string {
  const colors = {
    [Categoria.BRONZE]: "text-rank-bronze",
    [Categoria.PRATA]: "text-rank-silver",
    [Categoria.OURO]: "text-rank-gold",
    [Categoria.DIAMANTE]: "text-rank-diamond",
    [Categoria.MESTRE]: "text-rank-master",
  };
  return colors[categoria];
}

/**
 * Get rank glow effect based on category
 */
export function getRankGlow(categoria: Categoria): string {
  const glows = {
    [Categoria.BRONZE]: "shadow-[0_0_20px_rgba(205,127,50,0.5)]",
    [Categoria.PRATA]: "shadow-[0_0_20px_rgba(192,192,192,0.5)]",
    [Categoria.OURO]: "shadow-[0_0_20px_rgba(255,215,0,0.5)]",
    [Categoria.DIAMANTE]: "shadow-neon-cyan",
    [Categoria.MESTRE]: "shadow-neon-purple",
  };
  return glows[categoria];
}

/**
 * Get ELO range for category
 */
export function getEloRange(categoria: Categoria): { min: number; max: number } {
  const ranges = {
    [Categoria.BRONZE]: { min: 0, max: 999 },
    [Categoria.PRATA]: { min: 1000, max: 1499 },
    [Categoria.OURO]: { min: 1500, max: 1999 },
    [Categoria.DIAMANTE]: { min: 2000, max: 2499 },
    [Categoria.MESTRE]: { min: 2500, max: Infinity },
  };
  return ranges[categoria];
}

/**
 * Calculate progress percentage within current rank
 */
export function calculateRankProgress(elo: number, categoria: Categoria): number {
  const range = getEloRange(categoria);
  if (range.max === Infinity) return 100;
  
  const progress = ((elo - range.min) / (range.max - range.min)) * 100;
  return Math.min(Math.max(progress, 0), 100);
}

/**
 * Format date to Brazilian format
 */
export function formatDate(date: string | Date): string {
  const d = typeof date === "string" ? new Date(date) : date;
  return d.toLocaleDateString("pt-BR", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  });
}

/**
 * Format date and time to Brazilian format
 */
export function formatDateTime(date: string | Date): string {
  const d = typeof date === "string" ? new Date(date) : date;
  return d.toLocaleString("pt-BR", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
}

/**
 * Format currency to Brazilian Real
 */
export function formatCurrency(value: number): string {
  return new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  }).format(value);
}

/**
 * Format percentage
 */
export function formatPercentage(value: number): string {
  return `${(value * 100).toFixed(1)}%`;
}

/**
 * Get medal emoji for placement
 */
export function getMedalEmoji(colocacao: number): string {
  const medals: Record<number, string> = {
    1: "🥇",
    2: "🥈",
    3: "🥉",
  };
  return medals[colocacao] || "";
}

/**
 * Debounce function
 */
export function debounce<T extends (...args: any[]) => any>(
  func: T,
  wait: number
): (...args: Parameters<T>) => void {
  let timeout: NodeJS.Timeout;
  return (...args: Parameters<T>) => {
    clearTimeout(timeout);
    timeout = setTimeout(() => func(...args), wait);
  };
}
