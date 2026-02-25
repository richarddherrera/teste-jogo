# Como Executar o Projeto

## Pré-requisitos

- Java 17 ou superior instalado
- Maven instalado (ou usar o wrapper incluído)

## Verificar Instalação

```cmd
java -version
mvn -version
```

## Compilar o Projeto

```cmd
mvn clean compile
```

## Executar a Aplicação

```cmd
mvn exec:java -Dexec.mainClass="com.arena.ArenaApp"
```

Ou compile e execute manualmente:

```cmd
mvn clean package
java -cp target/esports-tournament-system-1.0.0.jar com.arena.ArenaApp
```

## Dados Iniciais

O sistema já vem com dados de exemplo carregados:

- 4 jogadores (aspas, Less, Sacy, pANcada)
- 1 time (LOUD)
- 3 jogos (Valorant, League of Legends, CS:GO)
- 1 torneio (Champions 2024)

## Estrutura do Menu

1. 👤 Gerenciar Jogadores
2. 👥 Gerenciar Times
3. 🎮 Gerenciar Jogos
4. 🏆 Gerenciar Torneios
5. ⚔️ Gerenciar Partidas
6. 🎯 Matchmaking
7. 📊 Relatórios e Rankings

## Exemplos de Uso

### Cadastrar um Novo Jogador

1. Menu Principal → 1 (Jogadores)
2. Opção 1 (Cadastrar)
3. Preencher dados

### Inscrever no Torneio

1. Menu Principal → 4 (Torneios)
2. Opção 3 (Inscrever Participante)
3. Nome do torneio: Champions 2024
4. Tipo: jogador ou time

### Ver Ranking

1. Menu Principal → 7 (Relatórios)
2. Opção 1 (Ranking Global)

## Troubleshooting

Se encontrar erro de compilação, certifique-se de estar usando Java 17+:

```cmd
java -version
```

Se o Maven não estiver no PATH, use o caminho completo ou instale via:
- Windows: Chocolatey (`choco install maven`)
- Ou baixe de: https://maven.apache.org/download.cgi
