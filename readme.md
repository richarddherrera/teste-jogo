## Testes

O projeto contém alguns testes básicos do backend que garantem que o contexto do
Spring carrega e que as rotas de `jogadores` e `times` retornam OK. Para executar
os testes basta rodar:

```powershell
mvn test
```

Os resultados aparecem na saída e o Maven exibirá `BUILD SUCCESS` quando todos
passarem (atualmente são 3 testes simples).

## Documentação de API (Swagger)

Após iniciar o back-end, abra `http://localhost:8080/swagger-ui/index.html` para
explorar todos os endpoints (autenticação, jogadores, times, torneios...). A UI
é gerada automaticamente pelo `springdoc-openapi` que já está incluído no
`pom.xml`.
# Arena Nexus

Arena Nexus é um sistema de gerenciamento de torneios de e-sports, desenvolvido
como projeto full‑stack com **Java/Spring Boot** no back‑end e **Next.js/TypeScript**
no front‑end. A aplicação mantém autenticação via JWT, ranking de jogadores,
times, torneios e um protótipo de fila de matchmaking.

## Tecnologias

- Java 17
- Spring Boot 3.2
  - Web MVC, Data JPA (com H2 em memória), Validation, Security
  - Springdoc OpenAPI para documentação Swagger
- Lombok (geração automatizada de getters/setters)
- H2 Database (desenvolvimento) / MySQL driver
- React 18 / Next.js 14 (App Router)
- TypeScript
- Tailwind CSS, Framer Motion, Lucide para ícones
- React Query (tanstack) para cache de dados

## Como rodar

Abra dois terminais:

1. **Back-end** (na raiz do repositório):

	```powershell
	mvn spring-boot:run
	```

	O servidor inicializa em `http://localhost:8080` e disponibiliza o console H2
	em `http://localhost:8080/h2-console`.

2. **Front-end**:

	```powershell
	cd frontend
	npm install      # se ainda não tiver instalado
	npm run dev
	```

	O site ficará disponível em `http://localhost:3000`.

> Se a API não estiver disponível o front exibirá erros como "Failed to fetch"
> no console. Verifique a variável `NEXT_PUBLIC_API_URL` em
> `frontend/.env.local` ou veja o log `[api] base URL:` no console do navegador.

## Fluxo básico

1. Cadastre um jogador ou use as contas de teste (`aspas`/`senha123`, `Less`/`senha123`).
2. Faça login; o sistema retorna um JWT que é gravado em cookie e localStorage.
3. Navegue pelo menu em `/rankings`, `/times`, `/torneios` e `/matchmaking`.
4. A API expõe endpoints REST sob `/api/*`; veja a documentação Swagger em
	`http://localhost:8080/swagger-ui/index.html`.

## Estrutura de pastas

- `src/main/java/com/arena`: código Java (controllers, modelos, segurança, etc.)
- `frontend`: código React/Next.js com as páginas e componentes
- `frontend/lib/api.ts`: cliente HTTP para a API

## Documentação de API (Swagger)

Após iniciar o back-end, abra `http://localhost:8080/swagger-ui/index.html` para
explorar todos os endpoints (autenticação, jogadores, times, torneios...).

## Observações

- Todos os dados são mantidos em memória (H2 e listas internas); reiniciar a
  aplicação apaga os registros. Use `DataLoader` ou as rotas POST para criar
  dados de exemplo.
- CORS está configurado para `http://localhost:3000`.

---

_Apresentação e documentação criadas automaticamente por gitHub Copilot AI durante
o desenvolvimento do projeto._
