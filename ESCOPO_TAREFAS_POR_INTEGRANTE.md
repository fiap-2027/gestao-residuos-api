# Escopo de Tarefas por Integrante — Projeto Gestão de Resíduos API

O código da API está **100% implementado**. Cada integrante agora precisa:
1. **Estudar e dominar** os arquivos da sua frente.
2. **Testar na prática** os endpoints e funcionalidades.
3. **Validar o comportamento** esperado.
4. **Preparar a apresentação/defesa** da sua parte.

---

## 👤 PESSOA 1 — Infraestrutura, Banco de Dados e Migrações (Flyway)

### 📂 Arquivos sob sua responsabilidade
```
pom.xml
src/main/resources/application.yml
src/main/resources/db/migration/V1__create_schema.sql
src/main/resources/db/migration/V2__seed_data.sql
src/main/resources/db/migration/V3__create_security.sql
docs/triggers_fase_anterior_referencia.sql
src/main/java/com/esg/residuos/GestaoResiduosApplication.java
src/test/resources/application.yml
```

### ✅ Tarefas a fazer

#### 1. Ambiente e Build (1-2h)
- [ ] Instalar Java 17 JDK (se ainda não tiver).
- [ ] Instalar Maven 3.9+ (ou usar o wrapper `mvnw` se configurado).
- [ ] Abrir o projeto `gestao-residuos-api` na IDE (IntelliJ IDEA/Eclipse/VSCode).
- [ ] Rodar `mvn clean install` e verificar que compila sem erros.
- [ ] Rodar `mvn test` e confirmar que os 2 testes passam.

#### 2. Banco de dados Oracle local (2h)
- [ ] Instalar Docker Desktop (se ainda não tiver).
- [ ] Copiar `.env.example` para `.env` e preencher as senhas:
  ```bash
  ORACLE_SYS_PASSWORD=OracleAdmin2024
  DB_USERNAME=residuos_user
  DB_PASSWORD=Residuos@123
  JWT_SECRET=ChaveSecretaUltraLongaParaJWT2024ProjetoESG
  ```
- [ ] Subir só o Oracle primeiro: `docker compose up oracle-db`
- [ ] Aguardar o healthcheck passar (pode demorar 1-2 minutos).
- [ ] Conectar no Oracle via DBeaver/SQL Developer:
  - Host: `localhost:1521`
  - Service: `XEPDB1`
  - User: `residuos_user`
  - Pass: `Residuos@123`

#### 3. Validar migrações Flyway (1h)
- [ ] Com o Oracle rodando, executar a aplicação local: `mvn spring-boot:run`
- [ ] Observar os logs do Flyway aplicando as 3 migrações (V1, V2, V3).
- [ ] No banco, conferir:
  - [ ] Tabela `flyway_schema_history` tem 3 registros (V1, V2, V3).
  - [ ] Existe a tabela `TIPO_RESIDUO` com 5 registros (Papel, Plástico, Metal, Vidro, Orgânico).
  - [ ] Existe a tabela `PONTO_COLETA` com 5 registros.
  - [ ] Existe a tabela `USUARIO` com 2 registros (admin, usuario).
  - [ ] Rodar esta query e conferir os totais:
    ```sql
    SELECT 'TIPO_RESIDUO' AS TABELA, COUNT(*) AS TOTAL FROM TIPO_RESIDUO
    UNION ALL SELECT 'PONTO_COLETA', COUNT(*) FROM PONTO_COLETA
    UNION ALL SELECT 'DESCARTE', COUNT(*) FROM DESCARTE
    UNION ALL SELECT 'COLETA', COUNT(*) FROM COLETA
    UNION ALL SELECT 'NOTIFICACAO', COUNT(*) FROM NOTIFICACAO
    UNION ALL SELECT 'USUARIO', COUNT(*) FROM USUARIO;
    ```
    Esperado: 5, 5, 5, 5, 5, 2.

#### 4. Documentação e apresentação (1h)
- [ ] Abrir e revisar o `pom.xml`: saber explicar as dependências principais (spring-boot-starter-web, spring-boot-starter-data-jpa, flyway, ojdbc11, spring-security, jjwt).
- [ ] Revisar `application.yml`: explicar a configuração do datasource Oracle e as propriedades do Flyway (`baseline-on-migrate: true`).
- [ ] Preparar slides/anotações explicando:
  - O que é o Flyway e por que ele é obrigatório no projeto.
  - Como ele versiona o schema (arquivos `V1__`, `V2__`, `V3__`).
  - Como garantir que o schema do V1 reflete o modelo da fase anterior (mostrar uma tabela do DDL original vs o V1).

#### 5. Pontos de atenção para a defesa
- Saber explicar **por que não usamos `ddl-auto: create`** (usamos `validate` porque o Flyway gerencia o schema).
- Saber mostrar no banco a tabela `flyway_schema_history` e explicar cada coluna (`installed_rank`, `version`, `checksum`, `success`).

---

## 👤 PESSOA 2 — Módulo "Catálogo" (Tipos de Resíduo e Pontos de Coleta)

### 📂 Arquivos sob sua responsabilidade
```
src/main/java/com/esg/residuos/domain/entity/TipoResiduo.java
src/main/java/com/esg/residuos/domain/entity/PontoColeta.java
src/main/java/com/esg/residuos/domain/enums/StatusPonto.java
src/main/java/com/esg/residuos/repository/TipoResiduoRepository.java
src/main/java/com/esg/residuos/repository/PontoColetaRepository.java
src/main/java/com/esg/residuos/dto/request/TipoResiduoRequest.java
src/main/java/com/esg/residuos/dto/response/TipoResiduoResponse.java
src/main/java/com/esg/residuos/dto/request/PontoColetaRequest.java
src/main/java/com/esg/residuos/dto/response/PontoColetaResponse.java
src/main/java/com/esg/residuos/service/TipoResiduoService.java
src/main/java/com/esg/residuos/service/PontoColetaService.java
src/main/java/com/esg/residuos/controller/TipoResiduoController.java
src/main/java/com/esg/residuos/controller/PontoColetaController.java
src/main/java/com/esg/residuos/domain/converter/SimNaoConverter.java
```

### ✅ Tarefas a fazer

#### 1. Ambiente (30min)
- [ ] Confirmar que a aplicação sobe sem erros (`mvn spring-boot:run`).
- [ ] Acessar o Swagger: `http://localhost:8080/swagger-ui.html`

#### 2. Testar endpoints de Tipos de Resíduo (1h)
- [ ] **Login como admin**:
  ```
  POST http://localhost:8080/api/auth/login
  Body: {"username": "admin", "password": "admin123"}
  ```
  Copiar o `token` retornado.
  
- [ ] **Listar tipos de resíduo**:
  ```
  GET http://localhost:8080/api/tipos-residuo
  Header: Authorization: Bearer <token>
  ```
  Deve retornar 5 registros (Papel, Plástico, Metal, Vidro, Orgânico).
  
- [ ] **Buscar tipo específico**:
  ```
  GET http://localhost:8080/api/tipos-residuo/1
  ```
  Deve retornar o primeiro tipo cadastrado.
  
- [ ] **Criar novo tipo**:
  ```
  POST http://localhost:8080/api/tipos-residuo
  Body:
  {
    "nome": "Pilhas e Baterias",
    "categoria": "PERIGOSO",
    "corIdentificacao": "LARANJA",
    "destinacaoCorreta": "Levar a postos de coleta especializados",
    "impactoAmbiental": "Altamente toxico ao meio ambiente"
  }
  ```
  Deve retornar HTTP 201 e o novo registro com ID gerado.
  
- [ ] **Tentar criar tipo duplicado**:
  Repetir o POST acima → Deve retornar HTTP 422 (BusinessException: "Ja existe um tipo de residuo cadastrado...").
  
- [ ] **Atualizar tipo**:
  ```
  PUT http://localhost:8080/api/tipos-residuo/6
  Body: {...} (alterar algum campo)
  ```
  
- [ ] **Excluir tipo**:
  ```
  DELETE http://localhost:8080/api/tipos-residuo/6
  ```
  HTTP 204 (sem conteúdo).

#### 3. Testar endpoints de Pontos de Coleta (1-2h)
- [ ] **Listar todos os pontos**:
  ```
  GET http://localhost:8080/api/pontos-coleta
  ```
  Deve retornar 5 pontos cadastrados.
  
- [ ] **Filtrar por status**:
  ```
  GET http://localhost:8080/api/pontos-coleta?status=DISPONIVEL
  ```
  
- [ ] **Buscar ponto específico**:
  ```
  GET http://localhost:8080/api/pontos-coleta/1
  ```
  
- [ ] **Criar novo ponto**:
  ```
  POST http://localhost:8080/api/pontos-coleta
  Body:
  {
    "idTipoResiduo": 1,
    "nome": "Ponto Teste Campus",
    "descricao": "Ponto experimental",
    "endereco": "Av. Paulista, 1000",
    "bairro": "Bela Vista",
    "cidade": "Sao Paulo",
    "estado": "SP",
    "cep": "01310-100",
    "capacidadeKg": 150.00,
    "limiteAlertaPercentual": 75.00
  }
  ```
  Deve criar com `status=DISPONIVEL`, `quantidadeAtualKg=0`, `percentualOcupacao=0`.
  
- [ ] **Ver histórico de descartes de um ponto**:
  ```
  GET http://localhost:8080/api/pontos-coleta/1/historico-descartes
  ```
  Deve retornar os descartes já cadastrados no seed (V2).

#### 4. Validar regras de negócio (30min)
- [ ] Abrir `TipoResiduoService.java` e ler o método `criar()`: entender onde está a validação de duplicação (linha `if (repository.existsByNomeIgnoreCase...)`).
- [ ] Abrir `PontoColeta.java` e conferir as validações Bean Validation (`@NotNull`, `@DecimalMin`, etc.).
- [ ] Abrir `SimNaoConverter.java` e entender por que ele existe (Oracle usa `CHAR(1) 'S'/'N'` para boolean).

#### 5. Documentação e apresentação (1h)
- [ ] Preparar slides/anotações explicando:
  - A diferença entre entidade JPA (`@Entity`) e DTO (`Request`/`Response`).
  - O papel do Repository (extends `JpaRepository`).
  - O papel do Service (regras de negócio).
  - O papel do Controller (expor endpoints REST).
- [ ] Demonstrar ao vivo: criar um tipo de resíduo novo via Swagger e depois tentar criar outro com o mesmo nome (mostrar o erro).

---

## 👤 PESSOA 3 — Módulo "Operação" (Descarte, Coleta e Indicadores)

### 📂 Arquivos sob sua responsabilidade
```
src/main/java/com/esg/residuos/domain/entity/Descarte.java
src/main/java/com/esg/residuos/domain/entity/Coleta.java
src/main/java/com/esg/residuos/domain/enums/StatusColeta.java
src/main/java/com/esg/residuos/repository/DescarteRepository.java
src/main/java/com/esg/residuos/repository/ColetaRepository.java
src/main/java/com/esg/residuos/dto/request/DescarteRequest.java
src/main/java/com/esg/residuos/dto/response/DescarteResponse.java
src/main/java/com/esg/residuos/dto/request/ColetaRequest.java
src/main/java/com/esg/residuos/dto/request/ColetaUpdateRequest.java
src/main/java/com/esg/residuos/dto/response/ColetaResponse.java
src/main/java/com/esg/residuos/dto/response/IndicadoresResponse.java
src/main/java/com/esg/residuos/service/DescarteService.java
src/main/java/com/esg/residuos/service/ColetaService.java
src/main/java/com/esg/residuos/service/IndicadorService.java
src/main/java/com/esg/residuos/controller/DescarteController.java
src/main/java/com/esg/residuos/controller/ColetaController.java
src/main/java/com/esg/residuos/controller/IndicadorController.java
src/test/java/com/esg/residuos/DescarteServiceTest.java
```

### ✅ Tarefas a fazer

#### 1. Estudar a lógica das automações (2-3h) ⚠️ CRÍTICO
Esta é a frente **MAIS IMPORTANTE** do projeto, porque concentra as **4 automações** da fase anterior (que eram triggers PL/SQL) agora implementadas em Java.

- [ ] Abrir `DescarteService.java` e ler **TODO O CÓDIGO** do método `registrar()`:
  - **Atividade 1 (linhas ~75-80)**: Validação — bloqueia descarte se o ponto estiver inativo.
  - **Atividade 1 (linhas ~95-106)**: Notificação de descarte incorreto quando o tipo descartado ≠ tipo aceito pelo ponto.
  - **Atividade 2 (linhas ~108-126)**: Atualização da `quantidadeAtualKg` e `percentualOcupacao` do ponto; mudança de status (`DISPONIVEL` → `ALERTA` → `CHEIO`).
  - **Atividade 3 (linhas ~128-145)**: Geração automática de coleta quando o percentual cruza o `limiteAlertaPercentual` pela primeira vez + notificação.

- [ ] Abrir `ColetaService.java` e ler o método `finalizarColeta()` (linha ~109):
  - **Atividade 4**: Ao concluir uma coleta, zera a `quantidadeAtualKg` do ponto, reseta o status para `DISPONIVEL`, atualiza `dataUltimaColeta` e gera notificação de conclusão.

- [ ] Abrir `docs/triggers_fase_anterior_referencia.sql` e comparar com o código Java acima: confirmar que **a lógica é idêntica**, apenas portada da linguagem PL/SQL para Java.

#### 2. Testar o fluxo de descarte (2h)
- [ ] **Cenário 1: Descarte correto**
  ```
  POST http://localhost:8080/api/descartes
  Body:
  {
    "idPontoColeta": 1,
    "idTipoResiduo": 1,
    "quantidadeKg": 10.5,
    "origem": "Departamento Administrativo"
  }
  ```
  - Verificar que retorna `descarteCorreto: true`.
  - Buscar o ponto 1 novamente: `GET /api/pontos-coleta/1`
  - Confirmar que `quantidadeAtualKg` aumentou e `percentualOcupacao` foi recalculado.

- [ ] **Cenário 2: Descarte incorreto**
  ```
  POST http://localhost:8080/api/descartes
  Body:
  {
    "idPontoColeta": 1,
    "idTipoResiduo": 2,
    "quantidadeKg": 5.0,
    "origem": "Teste"
  }
  ```
  (Supondo que o ponto 1 aceita tipo 1, não tipo 2)
  - Verificar que retorna `descarteCorreto: false`.
  - Listar notificações: `GET /api/notificacoes?naoLidas=true`
  - Confirmar que foi gerada uma notificação de "Descarte incorreto identificado".
  - **IMPORTANTE**: O ponto NÃO deve ter sido atualizado (quantidade permanece a mesma).

- [ ] **Cenário 3: Cruzar o limite de alerta (GERA COLETA AUTOMÁTICA)**
  - Consultar o ponto 1: anotar a `capacidadeKg` e o `limiteAlertaPercentual` (ex: 100kg, 80%).
  - Calcular quanto falta para atingir 80% (ex: se já tem 70kg, precisa descartar mais 10kg).
  - Fazer um descarte que cruze esse limite:
    ```
    POST http://localhost:8080/api/descartes
    Body:
    {
      "idPontoColeta": 1,
      "idTipoResiduo": 1,
      "quantidadeKg": 15.0,
      "origem": "Teste limite"
    }
    ```
  - Buscar o ponto 1 novamente: confirmar que `status` mudou para `ALERTA`.
  - Listar coletas: `GET /api/coletas`
  - Confirmar que uma **nova coleta** foi criada automaticamente com `status=SOLICITADA` e `observacao` mencionando "gerada automaticamente".
  - Listar notificações: confirmar que foi gerada uma notificação de "Limite de ocupacao atingido".

#### 3. Testar o fluxo de coleta (1h)
- [ ] **Agendar coleta manualmente**:
  ```
  POST http://localhost:8080/api/coletas
  Body:
  {
    "idPontoColeta": 2,
    "dataAgendada": "2026-11-01",
    "destinoFinal": "Central de reciclagem XYZ",
    "observacao": "Coleta agendada pelo gestor"
  }
  ```
  Deve criar com `status=AGENDADA`.

- [ ] **Atualizar status de uma coleta para CONCLUIDA** (simula a conclusão):
  ```
  PUT http://localhost:8080/api/coletas/{id}
  Body:
  {
    "status": "CONCLUIDA"
  }
  ```
  - Verificar que `dataColeta` foi preenchida com a data atual.
  - Buscar o ponto da coleta: confirmar que `quantidadeAtualKg` foi zerada, `percentualOcupacao=0` e `status=DISPONIVEL`.
  - Confirmar que foi gerada uma notificação de "Coleta concluida".

- [ ] **Cancelar uma coleta**:
  ```
  DELETE http://localhost:8080/api/coletas/{id}
  ```
  Confirmar que `status` muda para `CANCELADA`.

#### 4. Testar indicadores (30min)
- [ ] **Buscar indicadores consolidados**:
  ```
  GET http://localhost:8080/api/indicadores-sustentabilidade
  ```
  Deve retornar um JSON com:
  - Total de pontos, quantos estão disponíveis/em alerta/cheios.
  - Total de descartes, quantos foram corretos/incorretos.
  - Taxa de descarte correto (%).
  - Total reciclado em kg (soma dos descartes corretos).
  - Coletas solicitadas e concluídas.
  - Notificações não lidas.

- [ ] Validar os números: conferir manualmente no banco se batem (ex: `SELECT COUNT(*) FROM DESCARTE WHERE DESCARTE_CORRETO = 'S'`).

#### 5. Rodar os testes automatizados (30min)
- [ ] Executar: `mvn test`
- [ ] Abrir `DescarteServiceTest.java` e entender cada teste:
  - `deveBloquearDescarteEmPontoInativo`: tenta registrar descarte em ponto com `ativo=false` → deve lançar `BusinessException`.
  - `deveMarcarDescarteIncorretoEGerarNotificacao`: descarta tipo errado → `descarteCorreto=false` e notificação criada.
  - `deveAtualizarPontoEGerarColetaAoCruzarLimite`: descarte que cruza o limite → status vira ALERTA, coleta e notificação são criadas.

#### 6. Documentação e apresentação (1-2h)
- [ ] Preparar slides/anotações explicando **as 4 automações** (1, 2, 3 e 4) e onde cada uma está implementada (mostrar o código).
- [ ] Demonstrar ao vivo: fazer um descarte que gera coleta automática (o cenário 3 acima).
- [ ] Mostrar o teste `deveAtualizarPontoEGerarColetaAoCruzarLimite` e explicar como ele valida a Atividade 3 sem precisar de banco de dados real (usando Mockito).

---

## 👤 PESSOA 4 — Segurança (Spring Security/JWT), Notificações e Docker

### 📂 Arquivos sob sua responsabilidade
```
src/main/java/com/esg/residuos/domain/entity/Notificacao.java
src/main/java/com/esg/residuos/domain/entity/Usuario.java
src/main/java/com/esg/residuos/domain/enums/Prioridade.java
src/main/java/com/esg/residuos/repository/NotificacaoRepository.java
src/main/java/com/esg/residuos/repository/UsuarioRepository.java
src/main/java/com/esg/residuos/security/JwtService.java
src/main/java/com/esg/residuos/security/JwtAuthenticationFilter.java
src/main/java/com/esg/residuos/security/CustomUserDetailsService.java
src/main/java/com/esg/residuos/config/SecurityConfig.java
src/main/java/com/esg/residuos/config/OpenApiConfig.java
src/main/java/com/esg/residuos/controller/AuthController.java
src/main/java/com/esg/residuos/controller/NotificacaoController.java
src/main/java/com/esg/residuos/service/NotificacaoService.java
src/main/java/com/esg/residuos/dto/request/LoginRequest.java
src/main/java/com/esg/residuos/dto/response/LoginResponse.java
src/main/java/com/esg/residuos/dto/response/NotificacaoResponse.java
Dockerfile
docker-compose.yml
.env.example
.dockerignore
README.md
```

### ✅ Tarefas a fazer

#### 1. Estudar o fluxo de autenticação JWT (2h)
- [ ] Abrir `SecurityConfig.java` e entender a configuração:
  - **Endpoints públicos** (linha ~72): `/api/auth/**`, Swagger, actuator/health.
  - **Endpoints autenticados** (linha ~74): todos os `GET /api/**` exigem autenticação (perfil USER ou ADMIN).
  - **Endpoints restritos a ADMIN** (linhas ~75-77): todos os `POST`, `PUT`, `DELETE` exigem perfil ADMIN.
  
- [ ] Abrir `JwtService.java`:
  - Método `generateToken()`: cria o token JWT com subject (username), role e expiração.
  - Método `isTokenValid()`: valida se o token é válido e não expirou.
  
- [ ] Abrir `JwtAuthenticationFilter.java`:
  - Intercepta todas as requisições.
  - Extrai o token do header `Authorization: Bearer <token>`.
  - Valida o token e autentica o usuário no `SecurityContext`.
  
- [ ] Abrir `CustomUserDetailsService.java`:
  - Busca o usuário no banco pela tabela `USUARIO`.
  - Retorna um `UserDetails` do Spring Security com a role `ROLE_ADMIN` ou `ROLE_USER`.

#### 2. Testar autenticação e autorização (1-2h)
- [ ] **Login com admin**:
  ```
  POST http://localhost:8080/api/auth/login
  Body: {"username": "admin", "password": "admin123"}
  ```
  Deve retornar HTTP 200 com `{"token": "eyJ...", "tipo": "Bearer", "username": "admin", "role": "ADMIN"}`.
  
- [ ] **Login com user**:
  ```
  POST http://localhost:8080/api/auth/login
  Body: {"username": "usuario", "password": "user123"}
  ```
  Deve retornar token com `role: "USER"`.
  
- [ ] **Login com credenciais inválidas**:
  ```
  POST http://localhost:8080/api/auth/login
  Body: {"username": "admin", "password": "senhaerrada"}
  ```
  Deve retornar HTTP 401 (Unauthorized): "Usuario ou senha invalidos".
  
- [ ] **Testar acesso SEM token**:
  ```
  GET http://localhost:8080/api/tipos-residuo
  (sem header Authorization)
  ```
  Deve retornar HTTP 401 (Unauthorized).
  
- [ ] **Testar acesso com token de USER em endpoint de escrita**:
  - Fazer login como `usuario`, copiar o token.
  - Tentar criar um tipo de resíduo:
    ```
    POST http://localhost:8080/api/tipos-residuo
    Header: Authorization: Bearer <token_do_usuario>
    Body: {...}
    ```
    Deve retornar HTTP 403 (Forbidden): "Voce nao tem permissao para executar esta acao".
  
- [ ] **Testar acesso com token de ADMIN em endpoint de leitura**:
  ```
  GET http://localhost:8080/api/tipos-residuo
  Header: Authorization: Bearer <token_do_admin>
  ```
  Deve retornar HTTP 200 com a lista.
  
- [ ] **Testar acesso com token de ADMIN em endpoint de escrita**:
  ```
  POST http://localhost:8080/api/tipos-residuo
  Header: Authorization: Bearer <token_do_admin>
  Body: {...}
  ```
  Deve retornar HTTP 201 (Created).

#### 3. Testar endpoints de Notificações (30min)
- [ ] **Listar todas as notificações**:
  ```
  GET http://localhost:8080/api/notificacoes
  Header: Authorization: Bearer <token>
  ```
  Deve retornar as notificações cadastradas no seed + as geradas pelos testes de descarte/coleta.
  
- [ ] **Filtrar notificações não lidas**:
  ```
  GET http://localhost:8080/api/notificacoes?naoLidas=true
  ```
  
- [ ] **Marcar notificação como lida**:
  ```
  PUT http://localhost:8080/api/notificacoes/{id}/marcar-lida
  ```
  Buscar a notificação novamente: confirmar que `lida: true`.

#### 4. Testar execução com Docker (2-3h)
- [ ] Instalar Docker Desktop (se ainda não tiver).
- [ ] Copiar `.env.example` para `.env` e preencher as senhas (mesmas da Pessoa 1).
- [ ] Parar a aplicação local (se estiver rodando).
- [ ] Executar:
  ```
  docker compose up --build
  ```
  - Aguardar a imagem da API ser buildada (pode demorar 3-5 min na primeira vez).
  - Aguardar o Oracle subir e passar no healthcheck.
  - Aguardar a API conectar no Oracle e aplicar as migrações Flyway.
  
- [ ] Verificar que a API está acessível em `http://localhost:8080/swagger-ui.html`.
- [ ] Fazer login e testar alguns endpoints (repetir os testes de autenticação acima).
- [ ] Verificar os logs da API:
  ```
  docker compose logs -f api
  ```
  Confirmar que não há erros.
  
- [ ] Conectar no Oracle via DBeaver/SQL Developer (porta 1521) e conferir que as tabelas existem e estão populadas.
- [ ] Derrubar os containers:
  ```
  docker compose down
  ```
  
- [ ] Subir novamente (sem `--build`):
  ```
  docker compose up
  ```
  Confirmar que sobe mais rápido (usa as imagens em cache) e que os dados permanecem (volume `oracle-data`).

#### 5. Revisar e melhorar o README.md (1h)
- [ ] Abrir `README.md` e ler todo o conteúdo.
- [ ] Testar na prática os comandos de "Como executar com Docker" e "Como executar localmente" → se algum estiver errado ou incompleto, corrigir.
- [ ] Adicionar uma seção de "Troubleshooting" se encontrar algum problema comum (ex: porta 1521 já em uso, Oracle demorou muito a subir, etc.).

#### 6. Documentação e apresentação (1h)
- [ ] Preparar slides/anotações explicando:
  - O que é JWT e por que ele é usado (autenticação stateless, sem sessão no servidor).
  - O fluxo: login → geração do token → inclusão do token no header → validação do token → autorização por role.
  - A diferença entre **autenticação** (quem você é) e **autorização** (o que você pode fazer).
  - Por que usamos Docker (isola dependências, facilita deploy, garante que "funciona no meu ambiente" seja replicável).
  
- [ ] Demonstrar ao vivo:
  - Fazer login como USER e tentar criar um tipo de resíduo (mostrar o 403 Forbidden).
  - Fazer login como ADMIN e criar o mesmo tipo (mostrar o 201 Created).
  - Subir a aplicação com `docker compose up` e testar um endpoint (mostrar que funciona).

---

## 🔄 Tarefas Compartilhadas (TODOS os 4 integrantes)

### 1. Revisão cruzada (1h)
- [ ] Cada pessoa deve ler e entender o código das **outras 3 frentes** (não precisa decorar, mas saber onde está cada coisa).
- [ ] Fazer perguntas no grupo se algo não ficou claro.

### 2. Teste integrado final (1h)
- [ ] Marcar um horário comum para rodar o `docker compose up` juntos.
- [ ] Executar um **fluxo completo de ponta a ponta**:
  1. Login como admin.
  2. Criar um novo tipo de resíduo ("Eletrônicos").
  3. Criar um ponto de coleta para esse tipo (capacidade 50kg, limite 80%).
  4. Registrar descartes até o ponto atingir 40kg (80% de 50kg).
  5. Confirmar que uma coleta foi gerada automaticamente.
  6. Atualizar a coleta para status CONCLUIDA.
  7. Confirmar que o ponto voltou para 0kg e status DISPONIVEL.
  8. Buscar os indicadores e verificar que os números batem.
  
- [ ] Documentar qualquer bug encontrado e corrigir antes da entrega.

### 3. Apresentação/defesa (preparação individual, 1-2h cada)
- [ ] Cada pessoa deve preparar uma apresentação de **5-10 minutos** sobre a sua frente:
  - **Pessoa 1**: Arquitetura, Flyway, migrações.
  - **Pessoa 2**: Entidades, Repositórios, Serviços e Controllers do catálogo.
  - **Pessoa 3**: Lógica de negócio (as 4 automações) e testes.
  - **Pessoa 4**: Segurança, JWT, Docker.
  
- [ ] Treinar a apresentação (pode gravar um vídeo de si mesmo ou apresentar para um colega).

---

## 📊 Checklist Final (antes da entrega)

- [ ] `mvn clean test` → todos os testes passam.
- [ ] `mvn clean package` → gera o JAR sem erros.
- [ ] `docker compose up --build` → sobe Oracle + API sem erros.
- [ ] Swagger acessível em `http://localhost:8080/swagger-ui.html`.
- [ ] Login funciona (admin e usuario).
- [ ] Pelo menos 1 endpoint de cada módulo foi testado manualmente e funciona.
- [ ] README.md está completo e correto.
- [ ] Arquivo `.env` **NÃO** está commitado (só o `.env.example`).
- [ ] Todos os 4 integrantes sabem explicar a sua parte.

---

## 📞 Dúvidas?

Se qualquer integrante ficar travado em alguma tarefa:
1. Consultar o `README.md` e este `ESCOPO_TAREFAS_POR_INTEGRANTE.md`.
2. Revisar o código da sua frente (os comentários explicam a lógica).
3. Buscar no Swagger a documentação do endpoint.
4. Perguntar no grupo.
5. Se nada funcionar, documentar o problema e seguir para a próxima tarefa (não deixar tudo parado por causa de um erro pontual).

**Boa sorte! 🚀**
