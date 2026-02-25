# 🚀 Como Rodar o Arena Nexus

## Passo 1: Abrir Dois Terminais

Você vai precisar de **2 terminais abertos** ao mesmo tempo.

---

## Terminal 1 - Backend (API)

### Na raiz do projeto, execute:

```bash
mvn spring-boot:run
```

### Aguarde ver esta mensagem:

```
🎮 Arena Nexus API está rodando!
📍 API: http://localhost:8080
📍 H2 Console: http://localhost:8080/h2-console
```

✅ **Deixe este terminal aberto!**

---

## Terminal 2 - Frontend

### Entre na pasta frontend:

```bash
cd frontend
```

### Execute:

```bash
npm run dev
```

### Aguarde ver:

```
✓ Ready in 2.5s
○ Local: http://localhost:3000
```

✅ **Deixe este terminal aberto também!**

---

## 🌐 Acessar no Navegador

Abra seu navegador em:

### **http://localhost:3000**

Você verá a página inicial do Arena Nexus! 🎮

---

## 🎯 O Que Você Pode Fazer

1. **Ver Rankings** - Clique em "Ver Rankings" para ver o top 10
2. **Ver Perfil** - Clique em um jogador para ver detalhes
3. **Acessar H2 Console** - http://localhost:8080/h2-console para ver o banco de dados

> **🛠 Dica de depuração:** se algum botão de login/cadastro/ranking mostrar
> "Failed to fetch" ou a mensagem de erro da captura de tela, provavelmente o
> backend não está ativo ou o endereço base está incorreto. Abra o console do
> navegador e verifique a linha `[api] base URL:` para ver qual URL o front-end
> está tentando acessar. Ele deve ser `http://localhost:8080/api` (ou a URL que
> você definiu em `NEXT_PUBLIC_API_URL`).

> **🔐 Observação sobre autenticação:** as páginas de **Times**, **Torneios** e
> **Matchmaking** agora são públicas para navegação, mas algumas operações
> continuam exigindo login. Ao fazer login ou registro, um cookie chamado
> `authToken` é criado automaticamente; sem ele o middleware redireciona para
> `/login`. Se você vir o formulário novamente depois de logar, tente limpar os
> cookies do site e refazer o login (o console deve indicar `[api] base URL:` e
> `authToken` na aba Application > Cookies).

---

## 🛑 Para Parar

- **Backend:** Pressione `Ctrl + C` no Terminal 1
- **Frontend:** Pressione `Ctrl + C` no Terminal 2

---

## 🐛 Problemas?

### Backend não inicia

**Erro:** "Port 8080 already in use"

```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <numero> /F
```

### Frontend não carrega

Verifique se o backend está rodando em http://localhost:8080/api/jogadores

---

**Pronto! Agora é só usar! 🎮✨**
