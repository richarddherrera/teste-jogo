# Arena Nexus - Sistema de Gerenciamento de Torneios de E-Sports

Sistema completo de gerenciamento de torneios de e-sports com backend Java e frontend React/Next.js.

## 🎮 Visão Geral

Arena Nexus é uma plataforma profissional para gerenciamento de torneios, jogadores, times e rankings no universo dos esportes eletrônicos. O sistema oferece:

- Sistema de ELO dinâmico com categorias automáticas
- Matchmaking inteligente por categoria
- Gestão completa de torneios e partidas
- Sistema de penalidades e moderação
- Rankings e estatísticas em tempo real
- Interface moderna com "Pro-Circuit Aesthetic"

## 📁 Estrutura do Projeto

```
arena-nexus/
├── src/main/java/          # Backend Java
│   └── com.arena/
│       ├── model/          # Entidades do domínio
│       ├── enums/          # Enumerações
│       ├── service/        # Lógica de negócio
│       ├── exception/      # Exceções customizadas
│       └── ArenaApp.java   # Aplicação CLI
├── frontend/               # Frontend Next.js (em desenvolvimento)
├── docs/                   # Documentação
│   ├── BACKEND_SPEC.md     # Especificação do backend
│   ├── BACKEND_SETUP.md    # Como executar o backend
│   ├── BACKEND_PROGRESS.md # Progresso da implementação
│   └── FRONTEND_SPEC.md    # Especificação do frontend
└── pom.xml                 # Configuração Maven
```

## 🚀 Quick Start

### Iniciar Backend (API)

```bash
mvn spring-boot:run
```

Acesse: `http://localhost:8080`

### Iniciar Frontend

```bash
cd frontend
npm install  # Primeira vez apenas
npm run dev
```

Acesse: `http://localhost:3000`

📖 **Guia completo:** [Quick Start Guide](docs/QUICK_START.md)

## 📚 Documentação

### 🚀 Início Rápido
- **[Quick Start Guide](docs/QUICK_START.md)** - Comece aqui! Guia de 5 minutos

### Backend
- [Especificação do Backend](docs/BACKEND_SPEC.md) - Regras de negócio e arquitetura
- [Setup do Backend](docs/BACKEND_SETUP.md) - Como executar o backend
- [Progresso do Backend](docs/BACKEND_PROGRESS.md) - Status da implementação

### Frontend
- [Especificação do Frontend](docs/FRONTEND_SPEC.md) - Design system e UI/UX
- [Setup do Frontend](docs/FRONTEND_SETUP.md) - Como executar o frontend

### Banco de Dados
- **[Database Setup](docs/DATABASE_SETUP.md)** - Configuração H2 e MySQL

## 🛠️ Tecnologias

### Backend
- Java 17+
- Spring Boot 3.2
- Spring Data JPA
- H2 Database (desenvolvimento)
- MySQL (produção)
- Maven
- Lombok

### Frontend
- Next.js 14+
- React 18+
- TypeScript
- Tailwind CSS v4
- Framer Motion
- TanStack Query
- Recharts

## ✨ Features Principais

### Sistema de ELO
- Categorias: Bronze, Prata, Ouro, Diamante, Mestre
- Cálculo automático baseado em vitórias/derrotas
- Distribuição de pontos para times

### Matchmaking
- Fila FIFO com filtro por categoria
- Pareamento inteligente (±1 categoria)
- Sistema de aceite de partida

### Torneios
- Múltiplos formatos (Eliminação Simples, Dupla, Pontos Corridos, Grupos)
- Sistema de inscrições com validações
- Premiação automática (50/30/20)

### Moderação
- Sistema de penalidades
- Ban automático após 3 penalidades
- Registro de WO (walkover)

## 👥 Contribuindo

Este é um projeto educacional para prática de Java OOP e desenvolvimento full-stack.

## 📄 Licença

Projeto educacional - Livre para uso e modificação.

## 🎯 Roadmap

- [x] Backend Core (Fases 1-7)
- [x] API REST com Spring Boot
- [x] Banco de Dados (H2 + MySQL)
- [x] Frontend UI Base
- [x] Página de Rankings
- [x] Página de Perfil do Jogador
- [x] Integração Frontend ↔ Backend
- [ ] Páginas de Times e Torneios
- [ ] Sistema de Matchmaking UI
- [ ] WebSockets para real-time
- [ ] Dashboard Admin
- [ ] Integração com Discord Bot
- [ ] Sistema de temporadas
- [ ] Bracket visual

---

**Status:** Backend completo ✅ | API REST ✅ | Frontend 40% 🚧
