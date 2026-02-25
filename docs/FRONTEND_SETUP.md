# Frontend Setup - Arena Nexus UI

## Pré-requisitos

- Node.js 18+ instalado
- npm ou yarn

## Instalação

```bash
cd frontend
npm install
```

## Executar em Desenvolvimento

```bash
npm run dev
```

A aplicação estará disponível em `http://localhost:3000`

## Build para Produção

```bash
npm run build
npm start
```

## Estrutura do Projeto

```
frontend/
├── app/                    # App Router do Next.js
│   ├── globals.css        # Estilos globais e classes utilitárias
│   ├── layout.tsx         # Layout raiz
│   ├── page.tsx           # Página inicial
│   └── providers.tsx      # Providers (React Query, etc)
├── components/            # Componentes reutilizáveis
│   └── RankCard.tsx       # Card de exibição de rank
├── lib/                   # Utilitários e helpers
│   └── utils.ts           # Funções auxiliares
├── types/                 # Definições TypeScript
│   └── index.ts           # Tipos baseados no backend Java
├── tailwind.config.ts     # Configuração do Tailwind
└── package.json           # Dependências
```

## Design System

### Cores

O Arena Nexus UI utiliza o "Pro-Circuit Aesthetic" com as seguintes cores:

- **Background:** `#0f172a` (Azul Marinho Profundo)
- **Neon Blue:** `#3b82f6` / `#00d2ff`
- **Neon Purple:** `#a855f7` / `#bf5af2`
- **Ranks:**
  - Bronze: `#cd7f32`
  - Prata: `#c0c0c0`
  - Ouro: `#ffd700`
  - Diamante: `#00ffff`
  - Mestre: `#7c3aed`

### Classes Utilitárias

```tsx
// Glass morphism
<div className="glass" />
<div className="glass-strong" />

// Neon glow
<div className="glow-blue" />
<div className="glow-cyan" />
<div className="glow-purple" />

// Botões
<button className="btn-primary" />
<button className="btn-secondary" />
<button className="btn-danger" />

// Cards
<div className="card" />
<div className="card-rank" />

// Text gradients
<span className="text-gradient-blue" />
<span className="text-gradient-purple" />
```

## Componentes Disponíveis

### RankCard

Exibe informações do jogador com rank, ELO e progresso:

```tsx
import { RankCard } from "@/components/RankCard";

<RankCard 
  jogador={jogadorData} 
  showProgress={true}
/>
```

## Integração com Backend

O frontend está preparado para consumir a API REST do backend Java (quando implementada).

### Configuração da API

Crie um arquivo `.env.local`:

```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

### Exemplo de Hook com React Query

```tsx
import { useQuery } from "@tanstack/react-query";

function useRankings() {
  return useQuery({
    queryKey: ["rankings"],
    queryFn: async () => {
      const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/rankings`);
      return res.json();
    },
  });
}
```

## Animações

O projeto utiliza Framer Motion para animações suaves:

```tsx
import { motion } from "framer-motion";

<motion.div
  initial={{ opacity: 0, y: 20 }}
  animate={{ opacity: 1, y: 0 }}
  transition={{ duration: 0.5 }}
>
  Conteúdo animado
</motion.div>
```

## Próximos Passos

- [ ] Criar página de Rankings
- [ ] Criar página de Perfil do Jogador
- [ ] Criar página de Times
- [ ] Criar página de Torneios
- [ ] Criar sistema de Matchmaking
- [ ] Implementar WebSockets para real-time
- [ ] Criar Dashboard Admin
- [ ] Adicionar gráficos de ELO (Recharts)

## Troubleshooting

### Erro de compilação TypeScript

Certifique-se de que todas as dependências estão instaladas:

```bash
rm -rf node_modules package-lock.json
npm install
```

### Tailwind não está aplicando estilos

Verifique se o `tailwind.config.ts` está configurado corretamente e se o `globals.css` está importado no `layout.tsx`.

### Framer Motion não funciona

Certifique-se de usar `"use client"` no topo dos componentes que usam Framer Motion.
