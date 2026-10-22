# ⚡ Guia Rápido de Comandos

## 🔧 Setup Inicial (TODOS devem fazer)

```bash
# 1. Verificar versões necessárias
java -version          # Deve ser 17 ou superior
mvn -version           # Deve ser 3.9 ou superior
docker --version       # Qualquer versão recente

# 2. Clonar/baixar o projeto
cd gestao-residuos-api

# 3. Configurar variáveis de ambiente
cp .env.example .env
# Editar .env com suas senhas

# 4. Compilar o projeto
mvn clean install

# 5. Rodar os testes
mvn test
```

## 🐳 Docker (Pessoa 4 — principal responsável)

```bash
# Subir Oracle + API (primeira vez)
docker compose up --build

# Subir (rápido, usa cache)
docker compose up

# Subir em background
docker compose up -d

# Ver logs da API
docker compose logs -f api

# Ver logs do Oracle
docker compose logs -f oracle-db

# Parar os containers
docker compose down

# Parar E REMOVER os volumes (⚠️ perde os dados)
docker compose down -v

# Reconstruir só a API (após mudar código)
docker compose up --build api
```

## 🚀 Execução Local (sem Docker)

```bash
# 1. Garantir que o Oracle está rodando (Docker ou instalação local)
docker compose up oracle-db

# 2. Configurar variáveis de ambiente (PowerShell no Windows)
$env:DB_URL="jdbc:oracle:thin:@//localhost:1521/XEPDB1"
$env:DB_USERNAME="residuos_user"
$env:DB_PASSWORD="Residuos@123"
$env:JWT_SECRET="ChaveSecretaUltraLongaParaJWT2024ProjetoESG"

# 3. Rodar a aplicação
mvn spring-boot:run

# Ou compilar e rodar o JAR
mvn clean package
java -jar target/gestao-residuos-api.jar
```

## 🧪 Testes (Pessoa 3 — principal responsável)

```bash
# Rodar todos os testes
mvn test

# Rodar um teste específico
mvn test -Dtest=DescarteServiceTest

# Rodar com verbose
mvn test -X

# Pular testes ao compilar
mvn clean package -DskipTests
```

## 🗄️ Comandos SQL Úteis (Pessoa 1)

```sql
-- Ver histórico de migrações Flyway
SELECT * FROM flyway_schema_history ORDER BY installed_rank;

-- Contar registros de cada tabela
SELECT 'TIPO_RESIDUO' AS TABELA, COUNT(*) AS TOTAL FROM TIPO_RESIDUO
UNION ALL SELECT 'PONTO_COLETA', COUNT(*) FROM PONTO_COLETA
UNION ALL SELECT 'DESCARTE', COUNT(*) FROM DESCARTE
UNION ALL SELECT 'COLETA', COUNT(*) FROM COLETA
UNION ALL SELECT 'NOTIFICACAO', COUNT(*) FROM NOTIFICACAO
UNION ALL SELECT 'USUARIO', COUNT(*) FROM USUARIO;

-- Ver pontos com status
SELECT ID_PONTO_COLETA, NOME, STATUS, PERCENTUAL_OCUPACAO 
FROM PONTO_COLETA 
ORDER BY PERCENTUAL_OCUPACAO DESC;

-- Ver descartes recentes
SELECT D.ID_DESCARTE, P.NOME AS PONTO, T.NOME AS TIPO, 
       D.QUANTIDADE_KG, D.DESCARTE_CORRETO, D.DATA_DESCARTE
FROM DESCARTE D
JOIN PONTO_COLETA P ON D.ID_PONTO_COLETA = P.ID_PONTO_COLETA
JOIN TIPO_RESIDUO T ON D.ID_TIPO_RESIDUO = T.ID_TIPO_RESIDUO
ORDER BY D.DATA_DESCARTE DESC
FETCH FIRST 10 ROWS ONLY;

-- Ver coletas pendentes
SELECT C.ID_COLETA, P.NOME AS PONTO, C.STATUS, C.DATA_SOLICITACAO
FROM COLETA C
JOIN PONTO_COLETA P ON C.ID_PONTO_COLETA = P.ID_PONTO_COLETA
WHERE C.STATUS IN ('SOLICITADA', 'AGENDADA')
ORDER BY C.DATA_SOLICITACAO;

-- Ver notificações não lidas
SELECT N.ID_NOTIFICACAO, N.TITULO, N.PRIORIDADE, N.DATA_NOTIFICACAO, P.NOME AS PONTO
FROM NOTIFICACAO N
JOIN PONTO_COLETA P ON N.ID_PONTO_COLETA = P.ID_PONTO_COLETA
WHERE N.LIDA = 'N'
ORDER BY N.DATA_NOTIFICACAO DESC;

-- Resetar um ponto para testes (⚠️ só em desenvolvimento)
UPDATE PONTO_COLETA 
SET QUANTIDADE_ATUAL_KG = 0, 
    PERCENTUAL_OCUPACAO = 0, 
    STATUS = 'DISPONIVEL'
WHERE ID_PONTO_COLETA = 1;
COMMIT;
```

## 🌐 Endpoints Essenciais

```bash
# Aplicação
http://localhost:8080

# Swagger UI (documentação interativa)
http://localhost:8080/swagger-ui.html

# OpenAPI JSON
http://localhost:8080/v3/api-docs

# Health check
http://localhost:8080/actuator/health
```

## 🔍 Troubleshooting

```bash
# Erro: "porta 8080 já está em uso"
# Windows: encontrar e matar o processo
netstat -ano | findstr :8080
taskkill /PID <número> /F

# Erro: "porta 1521 já está em uso"
docker compose down
# Ou mudar a porta no docker-compose.yml: "1522:1521"

# Erro: Flyway "Validate failed"
# Resetar o banco (⚠️ perde os dados)
docker compose down -v
docker compose up

# Erro: "OutOfMemoryError" ao compilar
export MAVEN_OPTS="-Xmx1024m"
mvn clean install

# Limpar cache do Maven
mvn clean
rm -rf ~/.m2/repository/com/esg/gestao-residuos-api

# Ver logs detalhados do Spring Boot
mvn spring-boot:run -Dspring-boot.run.arguments=--logging.level.com.esg.residuos=DEBUG
```

## 📦 Build para Produção

```bash
# Compilar JAR otimizado
mvn clean package -DskipTests

# Arquivo gerado
# target/gestao-residuos-api.jar

# Rodar o JAR em produção
java -jar target/gestao-residuos-api.jar \
  --server.port=8080 \
  --spring.datasource.url=jdbc:oracle:thin:@//PROD_HOST:1521/XEPDB1 \
  --spring.datasource.username=residuos_user \
  --spring.datasource.password=SENHA_PRODUCAO \
  --jwt.secret=CHAVE_SECRETA_PRODUCAO_MUITO_LONGA
```

## 🔐 Credenciais Padrão (Desenvolvimento)

```
Oracle (container):
- Host: localhost:1521
- Service: XEPDB1
- User SYS: ORACLE_SYS_PASSWORD (definir no .env)
- App User: residuos_user
- App Pass: DB_PASSWORD (definir no .env)

API - Usuário Admin:
- Username: admin
- Password: admin123
- Role: ADMIN

API - Usuário Regular:
- Username: usuario
- Password: user123
- Role: USER
```

**⚠️ IMPORTANTE: Trocar todas as senhas em ambiente de produção!**
