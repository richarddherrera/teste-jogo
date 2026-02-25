# Como Executar o Backend (API REST)

## Pré-requisitos

- Java 17 ou superior instalado
- Maven instalado

## Verificar Instalação

```cmd
java -version
mvn -version
```

## Executar a API

```cmd
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

## Endpoints Disponíveis

### Jogadores

- `GET /api/jogadores` - Lista todos os jogadores
- `GET /api/jogadores/{nickname}` - Busca jogador por nickname
- `POST /api/jogadores` - Cria novo jogador
- `GET /api/jogadores/ranking` - Retorna top 10
- `PATCH /api/jogadores/{nickname}/elo` - Atualiza ELO
- `DELETE /api/jogadores/{nickname}` - Remove jogador

## Dados Iniciais

O sistema carrega automaticamente 5 jogadores de exemplo:

- aspas (ELO 1600 - DIAMANTE)
- pANcada (ELO 1800 - DIAMANTE)
- Less (ELO 1400 - OURO)
- tuyz (ELO 1200 - OURO)
- Sacy (ELO 1000 - PRATA)

## Console H2 Database

Acesse: `http://localhost:8080/h2-console`

Configuração:
- **JDBC URL:** `jdbc:h2:mem:arena_nexus`
- **Username:** `sa`
- **Password:** (deixe vazio)

## Troubleshooting

### Porta 8080 em uso

```cmd
netstat -ano | findstr :8080
taskkill /PID <numero_do_pid> /F
```

### Erro de compilação

```cmd
mvn clean install
```
