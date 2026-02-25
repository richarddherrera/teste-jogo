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

### Backend (Java)

```bash
# Compilar e executar
mvn compile exec:java

# Ou executar o JAR
mvn clean package
java -jar target/esports-tournament-system-1.0.0.jar
```

### Frontend (Next.js)

```bash
cd frontend
npm install
npm run dev
```

## 📚 Documentação

- [Especificação do Backend](docs/BACKEND_SPEC.md) - Regras de negócio e arquitetura
- [Setup do Backend](docs/BACKEND_SETUP.md) - Como executar o backend
- [Progresso do Backend](docs/BACKEND_PROGRESS.md) - Status da implementação
- [Especificação do Frontend](docs/FRONTEND_SPEC.md) - Design system e UI/UX

## 🛠️ Tecnologias

### Backend
- Java 17+
- Maven
- Collections Framework
- Streams API
- java.time API

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
- [ ] Frontend UI (Em desenvolvimento)
- [ ] API REST com Spring Boot
- [ ] Integração com Discord Bot
- [ ] Sistema de temporadas
- [ ] Bracket visual ASCII

---

**Status:** Backend completo ✅ | Frontend em desenvolvimento 🚧
