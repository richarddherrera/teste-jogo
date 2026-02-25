# Configuração do Banco de Dados - Arena Nexus

## 📊 Visão Geral

O Arena Nexus suporta dois modos de banco de dados:

1. **H2 Database (Padrão)** - Banco em memória, não precisa instalar nada
2. **MySQL** - Banco de dados persistente para produção

---

## 🚀 Modo 1: H2 Database (Desenvolvimento)

### Vantagens
✅ Não precisa instalar nada
✅ Funciona imediatamente
✅ Perfeito para desenvolvimento e testes
✅ Console web integrado

### Como Usar

O H2 já está configurado por padrão. Basta executar:

```bash
mvn spring-boot:run
```

### Acessar o Console H2

1. Abra o navegador em: `http://localhost:8080/h2-console`
2. Configure a conexão:
   - **JDBC URL:** `jdbc:h2:mem:arena_nexus`
   - **Username:** `sa`
   - **Password:** (deixe em branco)
3. Clique em "Connect"

### ⚠️ Limitação

Os dados são perdidos quando você para a aplicação (banco em memória).

---

## 💾 Modo 2: MySQL (Produção)

### Passo 1: Instalar o MySQL

#### Windows

1. Baixe o MySQL Installer: https://dev.mysql.com/downloads/installer/
2. Execute o instalador
3. Escolha "Developer Default"
4. Configure a senha do root (anote essa senha!)
5. Finalize a instalação

#### Verificar Instalação

```cmd
mysql --version
```

### Passo 2: Criar o Banco de Dados

Abra o MySQL Command Line Client ou MySQL Workbench e execute:

```sql
-- Criar o banco de dados
CREATE DATABASE arena_nexus CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Criar usuário específico para a aplicação (SEGURANÇA)
CREATE USER 'arena_user'@'localhost' IDENTIFIED BY 'arena_password_2024';

-- Dar permissões ao usuário
GRANT ALL PRIVILEGES ON arena_nexus.* TO 'arena_user'@'localhost';

-- Aplicar as mudanças
FLUSH PRIVILEGES;

-- Verificar
SHOW DATABASES;
```

### Passo 3: Configurar a Aplicação

A configuração do MySQL já está pronta em `application-mysql.properties`.

Para usar MySQL, execute a aplicação com o profile `mysql`:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

Ou configure no IntelliJ/Eclipse:
- **VM Options:** `-Dspring.profiles.active=mysql`

### Passo 4: Verificar Conexão

Ao iniciar a aplicação, você deve ver no console:

```
✅ Conectado ao MySQL
📊 Banco de dados: arena_nexus
```

---

## 🔒 Segurança do Banco de Dados

### Boas Práticas Implementadas

✅ **Usuário Dedicado:** Não usa o usuário root
✅ **Senha Forte:** Senha complexa configurada
✅ **Permissões Limitadas:** Usuário só acessa o banco arena_nexus
✅ **Prepared Statements:** JPA usa prepared statements (previne SQL Injection)
✅ **Validação de Dados:** Bean Validation nas entidades

### Configuração de Senha Segura

**NUNCA** commite senhas reais no Git!

#### Para Desenvolvimento Local

Crie um arquivo `application-local.properties` (já está no .gitignore):

```properties
spring.datasource.password=SUA_SENHA_AQUI
```

Execute com:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql,local
```

#### Para Produção

Use variáveis de ambiente:

```bash
export DB_PASSWORD=sua_senha_super_secreta
```

E no `application-mysql.properties`:
```properties
spring.datasource.password=${DB_PASSWORD}
```

---

## 📋 Estrutura do Banco de Dados

### Tabelas Criadas Automaticamente

O Hibernate cria as tabelas automaticamente baseado nas entidades JPA:

```
arena_nexus/
├── jogadores          # Dados dos jogadores
├── times              # Dados dos times
├── jogos              # Jogos disponíveis
├── torneios           # Torneios criados
├── partidas           # Histórico de partidas
├── penalidades        # Penalidades aplicadas
└── premiacoes         # Premiações distribuídas
```

### Visualizar Estrutura

```sql
USE arena_nexus;
SHOW TABLES;
DESCRIBE jogadores;
```

---

## 🔄 Migração de H2 para MySQL

### Exportar Dados do H2

1. Acesse o H2 Console
2. Execute:
```sql
SCRIPT TO 'backup.sql';
```

### Importar no MySQL

```bash
mysql -u arena_user -p arena_nexus < backup.sql
```

---

## 🛠️ Comandos Úteis

### Backup do MySQL

```bash
mysqldump -u arena_user -p arena_nexus > backup_$(date +%Y%m%d).sql
```

### Restaurar Backup

```bash
mysql -u arena_user -p arena_nexus < backup_20260224.sql
```

### Limpar Banco de Dados

```sql
USE arena_nexus;
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE jogadores;
TRUNCATE TABLE times;
-- ... outras tabelas
SET FOREIGN_KEY_CHECKS = 1;
```

### Ver Dados

```sql
SELECT * FROM jogadores ORDER BY elo DESC LIMIT 10;
SELECT COUNT(*) FROM jogadores;
```

---

## ⚙️ Configurações Avançadas

### Aumentar Performance

No `application-mysql.properties`:

```properties
# Connection Pool
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5

# Cache de segundo nível
spring.jpa.properties.hibernate.cache.use_second_level_cache=true
```

### Logs de SQL

Para debug, ative os logs:

```properties
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

---

## 🐛 Troubleshooting

### Erro: "Access denied for user"

**Solução:** Verifique usuário e senha no `application-mysql.properties`

```bash
mysql -u arena_user -p
# Digite a senha: arena_password_2024
```

### Erro: "Unknown database 'arena_nexus'"

**Solução:** Crie o banco de dados:

```sql
CREATE DATABASE arena_nexus;
```

### Erro: "Communications link failure"

**Solução:** Verifique se o MySQL está rodando:

```bash
# Windows
net start MySQL80

# Ou verifique no Services (services.msc)
```

### Porta 3306 em uso

**Solução:** Altere a porta no `application-mysql.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/arena_nexus...
```

---

## 📊 Monitoramento

### Ver Conexões Ativas

```sql
SHOW PROCESSLIST;
```

### Ver Tamanho do Banco

```sql
SELECT 
    table_schema AS 'Database',
    ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS 'Size (MB)'
FROM information_schema.tables
WHERE table_schema = 'arena_nexus'
GROUP BY table_schema;
```

---

## 🎯 Resumo

### Para Desenvolvimento (Recomendado)
```bash
# Usa H2 (padrão)
mvn spring-boot:run
```

### Para Produção
```bash
# 1. Instale o MySQL
# 2. Crie o banco e usuário
# 3. Execute com profile mysql
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

---

**Dúvidas?** Consulte a documentação oficial:
- MySQL: https://dev.mysql.com/doc/
- Spring Data JPA: https://spring.io/projects/spring-data-jpa
