# ✅ Checklist de Entrega Final

## 📋 ANTES DE ENTREGAR O PROJETO

### 🔧 Validação Técnica

#### Compilação e Testes
- [ ] `mvn clean install` → executa sem erros
- [ ] `mvn test` → todos os 2 testes passam (GestaoResiduosApplicationTests, DescarteServiceTest)
- [ ] `mvn clean package` → gera o JAR em `target/gestao-residuos-api.jar`
- [ ] Não há warnings críticos de compilação

#### Docker
- [ ] `docker compose up --build` → sobe Oracle + API sem erros
- [ ] Oracle passa no healthcheck (aguardar 1-2 minutos)
- [ ] API conecta no Oracle e aplica as 3 migrações Flyway
- [ ] Logs da API não mostram erros de conexão ou exceções
- [ ] `docker compose down` → para os containers sem erros

#### Banco de Dados
- [ ] Conectar no Oracle via DBeaver/SQL Developer (localhost:1521/XEPDB1)
- [ ] Tabela `flyway_schema_history` existe e tem 3 registros (V1, V2, V3)
- [ ] Todas as 6 tabelas existem: TIPO_RESIDUO, PONTO_COLETA, DESCARTE, COLETA, NOTIFICACAO, USUARIO
- [ ] Dados de seed foram inseridos (5 registros por tabela + 2 usuários)
- [ ] Query de contagem retorna os totais esperados:
  ```sql
  SELECT 'TIPO_RESIDUO' AS TABELA, COUNT(*) AS TOTAL FROM TIPO_RESIDUO
  UNION ALL SELECT 'PONTO_COLETA', COUNT(*) FROM PONTO_COLETA
  UNION ALL SELECT 'DESCARTE', COUNT(*) FROM DESCARTE
  UNION ALL SELECT 'COLETA', COUNT(*) FROM COLETA
  UNION ALL SELECT 'NOTIFICACAO', COUNT(*) FROM NOTIFICACAO
  UNION ALL SELECT 'USUARIO', COUNT(*) FROM USUARIO;
  ```
  Resultado esperado: 5, 5, 5, 5, 5, 2

#### API em Funcionamento
- [ ] API responde em `http://localhost:8080/actuator/health` → `{"status":"UP"}`
- [ ] Swagger acessível em `http://localhost:8080/swagger-ui.html`
- [ ] Login funciona: `POST /api/auth/login` com admin/admin123 → retorna token
- [ ] Login funciona: `POST /api/auth/login` com usuario/user123 → retorna token
- [ ] Pelo menos 1 endpoint GET testado com sucesso (ex: `/api/tipos-residuo`)
- [ ] Pelo menos 1 endpoint POST testado com sucesso (ex: criar tipo de resíduo)

### 🧪 Validação Funcional

#### Endpoints REST (mínimo 4 testados)
- [ ] `GET /api/tipos-residuo` → retorna lista
- [ ] `POST /api/tipos-residuo` → cria novo tipo
- [ ] `GET /api/pontos-coleta` → retorna lista
- [ ] `POST /api/descartes` → registra descarte
- [ ] `GET /api/coletas` → retorna lista
- [ ] `PUT /api/coletas/{id}` → atualiza coleta
- [ ] `GET /api/notificacoes` → retorna lista
- [ ] `GET /api/indicadores-sustentabilidade` → retorna indicadores

#### Segurança (Spring Security + JWT)
- [ ] Requisição sem token → retorna 401 Unauthorized
- [ ] Login com credenciais inválidas → retorna 401
- [ ] Token de USER em endpoint de escrita → retorna 403 Forbidden
- [ ] Token de ADMIN em endpoint de escrita → funciona (200/201)
- [ ] Swagger exige autenticação (cadeado)

#### Automações (4 Atividades portadas das triggers PL/SQL)
- [ ] **Atividade 1**: Descarte em ponto inativo → lança BusinessException
- [ ] **Atividade 1**: Descarte incorreto (tipo diferente) → descarteCorreto=false, notificação criada
- [ ] **Atividade 2**: Descarte correto → ponto atualizado (quantidade, percentual, status)
- [ ] **Atividade 3**: Descarte que cruza limite → coleta criada automaticamente + notificação
- [ ] **Atividade 4**: Concluir coleta → ponto resetado (0kg, DISPONIVEL) + notificação

#### Validações e Tratamento de Exceções
- [ ] Criar tipo com nome duplicado → BusinessException (422)
- [ ] Criar ponto com tipo inexistente → ResourceNotFoundException (404)
- [ ] Criar descarte com dados inválidos (quantidade negativa) → erro de validação (400)
- [ ] Atualizar coleta já concluída → BusinessException (422)
- [ ] Mensagens de erro em português e informativas

### 📁 Arquivos e Documentação

#### Estrutura do Projeto
- [ ] Pasta `src/main/java` contém todos os arquivos .java organizados por pacote
- [ ] Pasta `src/main/resources` contém `application.yml` e migrations
- [ ] Pasta `src/test/java` contém os testes
- [ ] `pom.xml` está presente e válido
- [ ] `Dockerfile` e `docker-compose.yml` estão presentes
- [ ] `.env.example` está presente (sem senhas reais)
- [ ] `.gitignore` está presente e ignora `target/`, `.env`, etc.

#### Documentação
- [ ] `README.md` está completo e atualizado
- [ ] `DIVISAO_DE_TAREFAS.md` existe e divide em 4 frentes equilibradas
- [ ] `ESCOPO_TAREFAS_POR_INTEGRANTE.md` existe com tarefas detalhadas para cada pessoa
- [ ] `GUIA_RAPIDO_COMANDOS.md` existe com comandos úteis
- [ ] `EXEMPLOS_REQUISICOES.md` existe com exemplos de uso da API
- [ ] `CHECKLIST_ENTREGA.md` (este arquivo) existe
- [ ] `docs/triggers_fase_anterior_referencia.sql` existe (script PL/SQL da fase anterior)

#### Segurança e Boas Práticas
- [ ] Arquivo `.env` NÃO está commitado (só o `.env.example`)
- [ ] Não há senhas hardcoded no código (apenas em `.env.example` como placeholders)
- [ ] Senhas do seed (admin123, user123) são apenas para desenvolvimento
- [ ] README avisa para trocar senhas em produção

### 👥 Divisão de Trabalho

#### Pessoa 1 — Infraestrutura/Banco
- [ ] Testou as migrações Flyway localmente
- [ ] Consegue explicar o que cada migration faz
- [ ] Sabe conectar no Oracle e rodar queries de diagnóstico
- [ ] Preparou apresentação sobre Flyway e versionamento de schema

#### Pessoa 2 — Catálogo (Tipos + Pontos)
- [ ] Testou todos os endpoints de tipos de resíduo (GET, POST, PUT, DELETE)
- [ ] Testou todos os endpoints de pontos de coleta
- [ ] Sabe explicar a validação de duplicação em TipoResiduoService
- [ ] Preparou apresentação sobre entidades JPA, DTOs e conversor SimNao

#### Pessoa 3 — Operação (Descartes + Coletas + Indicadores)
- [ ] Testou o fluxo completo de descarte (correto, incorreto, que gera coleta)
- [ ] Testou o fluxo completo de coleta (criar, atualizar, concluir, cancelar)
- [ ] Rodou os testes unitários (DescarteServiceTest) e entendeu cada um
- [ ] Sabe explicar em detalhes as 4 automações (linhas específicas do código)
- [ ] Preparou apresentação/demo ao vivo do cenário de geração automática de coleta

#### Pessoa 4 — Segurança/Docker
- [ ] Testou autenticação (login admin, login user, token inválido)
- [ ] Testou autorização (USER tentando POST, ADMIN conseguindo POST)
- [ ] Subiu o projeto com Docker e validou que funciona
- [ ] Testou os endpoints de notificações
- [ ] Revisou e validou o README.md
- [ ] Preparou apresentação sobre JWT e Docker

### 🎯 Teste Integrado Final (TODOS juntos)

- [ ] Todos os 4 integrantes presentes em uma sessão
- [ ] Subir o projeto com `docker compose up --build`
- [ ] Executar o cenário completo de teste (descrito em EXEMPLOS_REQUISICOES.md):
  1. Login
  2. Criar tipo de resíduo
  3. Criar ponto de coleta
  4. Registrar descartes até gerar coleta automática
  5. Concluir coleta
  6. Verificar indicadores
- [ ] Todos os passos funcionaram sem erros
- [ ] Cada pessoa consegue explicar sua parte do fluxo
- [ ] Bugs encontrados foram documentados e corrigidos

### 📦 Entrega

#### Arquivos a Entregar
- [ ] Pasta completa do projeto `gestao-residuos-api/`
- [ ] OU arquivo `.zip` do projeto (SEM a pasta `target/` e SEM `.env` real)
- [ ] Documento PDF com prints/evidências dos testes (opcional mas recomendado)
- [ ] Vídeo de apresentação/demo (se solicitado)

#### Formato do .zip (se aplicável)
```
gestao-residuos-api.zip
├── src/
├── docs/
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── .env.example
├── README.md
├── DIVISAO_DE_TAREFAS.md
├── ESCOPO_TAREFAS_POR_INTEGRANTE.md
├── GUIA_RAPIDO_COMANDOS.md
├── EXEMPLOS_REQUISICOES.md
├── CHECKLIST_ENTREGA.md
└── .gitignore
```

**⚠️ NÃO incluir:**
- Pasta `target/` (gerada pelo Maven)
- Arquivo `.env` real (com senhas)
- Pastas `.idea/`, `.vscode/`, `.settings/` (configurações de IDE)

### 🎤 Apresentação

#### Preparação Individual
- [ ] Pessoa 1: Slides/anotações sobre infraestrutura (5-10 min)
- [ ] Pessoa 2: Slides/anotações sobre catálogo (5-10 min)
- [ ] Pessoa 3: Slides/anotações sobre operação + demo (10-15 min)
- [ ] Pessoa 4: Slides/anotações sobre segurança + Docker (5-10 min)

#### Apresentação em Grupo
- [ ] Ordem de apresentação definida
- [ ] Tempo total controlado (30-40 minutos no máximo)
- [ ] Demo ao vivo preparada (com fallback de vídeo se der problema)
- [ ] Cada pessoa sabe responder perguntas sobre sua frente
- [ ] Todos sabem responder perguntas gerais sobre o projeto

---

## 🚨 Problemas Comuns e Soluções

### ❌ Erro: "Failed to configure a DataSource"
**Solução:** Oracle não está rodando ou conexão está errada. Verificar `.env` e `docker compose up oracle-db`.

### ❌ Erro: "Flyway failed to initialize"
**Solução:** Resetar o banco com `docker compose down -v` e subir novamente.

### ❌ Erro: "Port 8080 is already in use"
**Solução:** Matar o processo que está usando a porta ou mudar a porta no `application.yml`.

### ❌ Erro: "401 Unauthorized" em todos os endpoints
**Solução:** Token não foi incluído no header ou está expirado. Fazer login novamente.

### ❌ Erro: "403 Forbidden" ao criar/atualizar/deletar
**Solução:** Usuário não tem perfil ADMIN. Fazer login com admin/admin123.

### ❌ Testes falhando
**Solução:** Verificar que `src/test/resources/application.yml` está configurado para usar H2 (não Oracle). Se persistir, rodar `mvn clean test`.

---

## ✨ Critérios de Sucesso

### ⭐ Entrega Básica (Mínimo para Aprovação)
- ✅ Projeto compila sem erros
- ✅ Pelo menos 4 endpoints funcionam
- ✅ Spring Security configurado (mesmo que básico)
- ✅ Flyway aplica as migrações
- ✅ Banco Oracle conecta
- ✅ Docker funciona
- ✅ README explica como rodar

### ⭐⭐ Entrega Completa (Nota Boa)
- ✅ Todos os itens da entrega básica
- ✅ Todos os 13 endpoints funcionam
- ✅ As 4 automações estão implementadas e testadas
- ✅ Validações e tratamento de exceções funcionam
- ✅ Testes automatizados passam
- ✅ Documentação completa e clara
- ✅ Divisão de tarefas equilibrada e documentada

### ⭐⭐⭐ Entrega Excelente (Nota Máxima)
- ✅ Todos os itens da entrega completa
- ✅ Demo ao vivo funciona perfeitamente
- ✅ Cada integrante domina sua frente e consegue explicar em detalhes
- ✅ Código limpo, bem comentado e seguindo boas práticas
- ✅ Comparação clara entre triggers PL/SQL (fase anterior) e código Java (fase atual)
- ✅ Evidências de testes (prints, vídeos, logs)
- ✅ Apresentação profissional e bem ensaiada

---

## 📅 Cronograma Sugerido (Última Semana Antes da Entrega)

### Segunda-feira
- Pessoa 1: Setup ambiente + validar migrações
- Pessoa 2: Testar endpoints do catálogo
- Pessoa 3: Testar endpoints de operação
- Pessoa 4: Testar autenticação + Docker

### Terça-feira
- Todos: Revisão cruzada do código (cada um lê a frente do outro)
- Identificar e documentar bugs

### Quarta-feira
- Correção de bugs encontrados
- Cada pessoa prepara sua apresentação individual

### Quinta-feira
- Teste integrado final (todos juntos)
- Ensaio da apresentação em grupo

### Sexta-feira
- Gerar o .zip final
- Revisar este checklist uma última vez
- **ENTREGAR!**

---

## 🎉 Boa sorte na entrega!

Lembre-se: este projeto já está **100% implementado e funcional**. O trabalho agora é **validar, testar, entender e apresentar**. Se surgirem dúvidas, consultem a documentação e façam perguntas no grupo. Vocês conseguem! 💪
