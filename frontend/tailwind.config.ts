import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
    "./app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        // Pro-Circuit Aesthetic Colors
        background: {
          primary: "#0f172a",
          secondary: "#111827",
          glass: "rgba(15, 23, 42, 0.7)",
        },
        neon: {
          blue: "#3b82f6",
          cyan: "#00d2ff",
          purple: "#a855f7",
          violet: "#bf5af2",
        },
        rank: {
          bronze: "#cd7f32",
          silver: "#c0c0c0",
          gold: "#ffd700",
          diamond: "#00ffff",
          master: "#7c3aed",
        },
        status: {
          danger: "#991b1b",
          warning: "#eab308",
          success: "#10b981",
        },
      },
      backgroundImage: {
        "gradient-radial": "radial-gradient(var(--tw-gradient-stops))",
        "gradient-conic": "conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))",
        "glass-gradient": "linear-gradient(135deg, rgba(255,255,255,0.1) 0%, rgba(255,255,255,0.05) 100%)",
      },
      boxShadow: {
        "neon-blue": "0 0 20px rgba(59, 130, 246, 0.5)",
        "neon-cyan": "0 0 20px rgba(0, 210, 255, 0.5)",
        "neon-purple": "0 0 20px rgba(168, 85, 247, 0.5)",
        "glass": "0 8px 32px 0 rgba(31, 38, 135, 0.37)",
      },
      backdropBlur: {
        xs: "2px",
      },
      animation: {
        "pulse-slow": "pulse 3s cubic-bezier(0.4, 0, 0.6, 1) infinite",
        "glow": "glow 2s ease-in-out infinite alternate",
        "scan": "scan 2s linear infinite",
      },
      keyframes: {
        glow: {
          "0%": { boxShadow: "0 0 5px rgba(59, 130, 246, 0.5)" },
          "100%": { boxShadow: "0 0 20px rgba(59, 130, 246, 1)" },
        },
        scan: {
          "0%": { transform: "translateX(-100%)" },
          "100%": { transform: "translateX(100%)" },
        },
      },
    },
  },
  plugins: [],
};

export default config;
