# 🎮 Arena Nexus

**Arena Nexus** é um sistema robusto de gerenciamento de torneios de e-sports, desenvolvido com uma arquitetura full‑stack moderna, utilizando **Java/Spring Boot** no back‑end e **Next.js/TypeScript** no front‑end.

A aplicação conta com autenticação segura via JWT, ranking de jogadores, gestão de times, organização de torneios e um protótipo funcional de fila de matchmaking.

---

## 🛠️ Tecnologias Utilizadas

### **Back-end**
* **Java 17** & **Spring Boot 3.2** 🍃
* **Spring Security** (Autenticação JWT) 🔐
* **Spring Data JPA** (Persistência de dados)
* **H2 Database** (Desenvolvimento em memória) / MySQL Driver 🗄️
* **Springdoc OpenAPI** (Documentação Swagger) 📝
* **Lombok** (Produtividade e código limpo)

### **Front-end**
* **React 18** & **Next.js 14** (App Router) ⚛️
* **TypeScript** 📘
* **Tailwind CSS** (Estilização) 🎨
* **Framer Motion** (Animações fluidas) ✨
* **Lucide React** (Ícones) 🧩
* **React Query** (Tanstack) para gerenciamento de cache 🔄

---

## 🚀 Como Executar o Projeto

Para rodar a aplicação completa, você precisará de dois terminais abertos:

### 1️⃣ Back-end (API)
Na raiz do repositório, execute:

```powershell
mvn spring-boot:run
```

Servidor: [http://localhost:8080](http://localhost:8080)

Console H2: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

### 2️⃣ Front-end (Interface)
Navegue até a pasta do front-end e inicie o servidor de desenvolvimento:

```powershell
cd frontend
npm install
npm run dev
```

Interface: [http://localhost:3000](http://localhost:3000)

💡 Dica: Se o front-end exibir erros de conexão, verifique a variável NEXT_PUBLIC_API_URL no arquivo frontend/.env.local.

---

## 🕹️ Fluxo Básico de Uso
- **Cadastro**: Crie um novo jogador ou use as contas de teste:
  - 👤 Usuário: `aspas` | Senha: `senha123`
  - 👤 Usuário: `Less` | Senha: `senha123`
- **Login**: O sistema retorna um JWT que é gravado em cookie e localStorage.
- **Navegação**: Utilize o menu para acessar as áreas de `/rankings`, `/times`, `/torneios` e `/matchmaking`.
- **API**: Explore os endpoints REST sob o prefixo `/api/*`.

---

## 📂 Estrutura de Pastas
- `src/main/java/com/arena`: Código-fonte Java (controllers, modelos, segurança, etc.).
- `frontend/`: Código React/Next.js (páginas, componentes e hooks).
- `frontend/lib/api.ts`: Cliente HTTP centralizado para chamadas à API.

---

## 🧪 Testes
O projeto inclui testes automatizados para o back-end que validam o carregamento do contexto e as rotas principais. Para executá-los:

```powershell
mvn test
```

---

## 📖 Documentação da API (Swagger)
Com o back-end em execução, acesse a documentação interativa para testar todos os endpoints (jogadores, times, torneios, etc.):

👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## ⚠️ Observações
- **Persistência**: Os dados são mantidos em memória (H2). Reiniciar a aplicação apagará todos os registros. Utilize o DataLoader ou as rotas POST para criar dados iniciais.
- **CORS**: A API está configurada para aceitar requisições de [http://localhost:3000](http://localhost:3000).
