# 🚀 Quick Start - Arena Nexus

Guia rápido para colocar o projeto rodando em 5 minutos!

---

## ✅ Pré-requisitos

- ☕ Java 17+ instalado
- 📦 Maven instalado
- 🟢 Node.js 18+ instalado
- 💻 Terminal/CMD aberto

### Verificar Instalações

```bash
java -version    # Deve mostrar Java 17 ou superior
mvn -version     # Deve mostrar Maven
node -version    # Deve mostrar Node 18+
npm -version     # Deve mostrar npm
```

---

## 🎯 Passo a Passo

### 1️⃣ Iniciar o Backend (API)

Abra um terminal na raiz do projeto:

```bash
mvn spring-boot:run
```

Aguarde até ver:

```
🎮 Arena Nexus API está rodando!
📍 API: http://localhost:8080
📍 H2 Console: http://localhost:8080/h2-console
```

✅ **Backend rodando!**

### 2️⃣ Iniciar o Frontend

Abra **OUTRO** terminal e navegue para a pasta frontend:

```bash
cd frontend

# Se for a primeira vez, instale as dependências:
npm install

# Executar o frontend
npm run dev
```

Aguarde até ver:

```
✓ Ready in 2.5s
○ Local: http://localhost:3000
```

✅ **Frontend rodando!**

### 3️⃣ Acessar a Aplicação

Abra o navegador em: **http://localhost:3000**

Você deve ver a página inicial do Arena Nexus! 🎮

---

## 🧪 Testar a Integração

### Verificar API

Abra: http://localhost:8080/api/jogadores

Deve retornar um JSON com os jogadores de exemplo.

### Verificar Frontend

1. Clique em "Ver Rankings"
2. Você deve ver os jogadores carregados da API
3. Clique em um jogador para ver o perfil

---

## 🎮 Dados de Exemplo

O sistema já vem com 5 jogadores pré-cadastrados:

| Nickname | ELO | Categoria |
|----------|-----|-----------|
| aspas | 1600 | DIAMANTE |
| pANcada | 1800 | DIAMANTE |
| Less | 1400 | OURO |
| tuyz | 1200 | OURO |
| Sacy | 1000 | PRATA |

---

## 📡 Endpoints da API

### Jogadores

```bash
# Listar todos
GET http://localhost:8080/api/jogadores

# Buscar por nickname
GET http://localhost:8080/api/jogadores/aspas

# Ranking top 10
GET http://localhost:8080/api/jogadores/ranking

# Criar jogador
POST http://localhost:8080/api/jogadores
Content-Type: application/json

{
  "nickname": "novoJogador",
  "nomeReal": "Nome Completo",
  "email": "email@example.com",
  "dataNascimento": "2000-01-15"
}
```

---

## 🛠️ Comandos Úteis

### Backend

```bash
# Compilar sem executar
mvn clean compile

# Executar
mvn spring-boot:run

# Gerar JAR
mvn clean package

# Executar JAR
java -jar target/esports-tournament-system-1.0.0.jar
```

### Frontend

```bash
# Desenvolvimento
npm run dev

# Build para produção
npm run build

# Executar build de produção
npm start

# Verificar erros
npm run lint
```

---

## 🔍 Acessar o Banco de Dados

### H2 Console (Desenvolvimento)

1. Abra: http://localhost:8080/h2-console
2. Configure:
   - **JDBC URL:** `jdbc:h2:mem:arena_nexus`
   - **Username:** `sa`
   - **Password:** (vazio)
3. Clique em "Connect"

Agora você pode executar queries SQL diretamente!

```sql
SELECT * FROM jogadores ORDER BY elo DESC;
```

---

## 🐛 Problemas Comuns

### Backend não inicia

**Erro:** "Port 8080 already in use"

**Solução:** Outra aplicação está usando a porta 8080.

```bash
# Windows - Encontrar processo na porta 8080
netstat -ano | findstr :8080

# Matar o processo (substitua PID)
taskkill /PID <numero_do_pid> /F
```

### Frontend não carrega dados

**Erro:** "Failed to fetch" ou "Network Error"

**Solução:** Verifique se o backend está rodando em http://localhost:8080

```bash
# Testar API diretamente
curl http://localhost:8080/api/jogadores
```

### Dependências do Frontend

**Erro:** "Module not found"

**Solução:** Reinstale as dependências

```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

---

## 🔄 Reiniciar do Zero

### Limpar Backend

```bash
mvn clean
```

### Limpar Frontend

```bash
cd frontend
rm -rf node_modules .next
npm install
```

### Resetar Banco de Dados

O H2 reseta automaticamente ao reiniciar a aplicação.

---

## 📚 Próximos Passos

Agora que está tudo funcionando:

1. ✅ Explore a página de Rankings
2. ✅ Veja o perfil de um jogador
3. ✅ Teste a API com Postman ou curl
4. ✅ Acesse o H2 Console e veja os dados
5. 📖 Leia a [documentação completa](README.md)

---

## 🆘 Precisa de Ajuda?

- 📖 [Documentação do Backend](BACKEND_SPEC.md)
- 🎨 [Documentação do Frontend](FRONTEND_SPEC.md)
- 💾 [Configuração do Banco de Dados](DATABASE_SETUP.md)

---

**Divirta-se desenvolvendo! 🎮✨**
