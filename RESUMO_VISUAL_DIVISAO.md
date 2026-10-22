# 📊 Resumo Visual da Divisão de Responsabilidades

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                    PROJETO: GESTÃO DE RESÍDUOS E RECICLAGEM                 │
│                    API RESTful - Spring Boot + Oracle + Docker              │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────┬──────────────────────┬──────────────────────┬───────────────────────┐
│   PESSOA 1           │   PESSOA 2           │   PESSOA 3           │   PESSOA 4            │
│   Infraestrutura     │   Catálogo           │   Operação           │   Segurança + Docker  │
├──────────────────────┼──────────────────────┼──────────────────────┼───────────────────────┤
│                      │                      │                      │                       │
│ 🔧 RESPONSABILIDADES │ 📋 RESPONSABILIDADES │ ♻️ RESPONSABILIDADES │ 🔐 RESPONSABILIDADES  │
│                      │                      │                      │                       │
│ • pom.xml            │ • TipoResiduo        │ • Descarte           │ • Usuario             │
│ • application.yml    │   (entity/dto/repo)  │   (entity/dto/repo)  │   (entity/repo)       │
│ • Migrações Flyway   │ • PontoColeta        │ • Coleta             │ • Notificacao         │
│   - V1 (schema)      │   (entity/dto/repo)  │   (entity/dto/repo)  │   (entity/dto/repo)   │
│   - V2 (seed data)   │ • StatusPonto enum   │ • StatusColeta enum  │ • Prioridade enum     │
│   - V3 (security)    │ • SimNaoConverter    │ • DescarteService    │ • JwtService          │
│ • GestaoResiduos     │ • TipoResiduoService │ • ColetaService      │ • JwtAuthFilter       │
│   Application.java   │ • PontoColetaService │ • IndicadorService   │ • CustomUserDetails   │
│ • Triggers PL/SQL    │ • Controllers (2):   │ • Controllers (3):   │   Service             │
│   (referência)       │   - TipoResiduo      │   - Descarte         │ • SecurityConfig      │
│                      │   - PontoColeta      │   - Coleta           │ • OpenApiConfig       │
│                      │                      │   - Indicador        │ • AuthController      │
│                      │                      │ • DescarteServiceTest│ • NotificacaoController│
│                      │                      │                      │ • NotificacaoService  │
│                      │                      │                      │ • Dockerfile          │
│                      │                      │                      │ • docker-compose.yml  │
│                      │                      │                      │ • README.md           │
│                      │                      │                      │                       │
├──────────────────────┼──────────────────────┼──────────────────────┼───────────────────────┤
│                      │                      │                      │                       │
│ 🎯 ENDPOINTS         │ 🎯 ENDPOINTS         │ 🎯 ENDPOINTS         │ 🎯 ENDPOINTS          │
│                      │                      │                      │                       │
│ (nenhum direto)      │ GET/POST/PUT/DELETE  │ GET/POST             │ POST /api/auth/login  │
│                      │ /api/tipos-residuo   │ /api/descartes       │ GET /api/notificacoes │
│ Responsável por      │                      │                      │ PUT /api/notificacoes │
│ garantir que o       │ GET/POST/PUT/DELETE  │ GET/POST/PUT/DELETE  │ /{id}/marcar-lida     │
│ banco e migrações    │ /api/pontos-coleta   │ /api/coletas         │                       │
│ funcionem            │                      │                      │                       │
│                      │ GET /api/pontos-     │ GET /api/indicadores-│                       │
│                      │ coleta/{id}/         │ sustentabilidade     │                       │
│                      │ historico-descartes  │                      │                       │
│                      │                      │                      │                       │
├──────────────────────┼──────────────────────┼──────────────────────┼───────────────────────┤
│                      │                      │                      │                       │
│ ⚡ FOCO TÉCNICO      │ ⚡ FOCO TÉCNICO      │ ⚡ FOCO TÉCNICO      │ ⚡ FOCO TÉCNICO       │
│                      │                      │                      │                       │
│ • Flyway migrations  │ • Entidades JPA      │ • Regras de negócio  │ • Autenticação JWT    │
│ • Versionamento DB   │ • Bean Validation    │   (as 4 automações)  │ • Autorização (roles) │
│ • Configuração JDBC  │ • DTOs (Request/     │ • Triggers → Java    │ • Spring Security     │
│ • Sequences Oracle   │   Response)          │ • Testes unitários   │ • Docker multi-stage  │
│ • Dependências Maven │ • Repositories       │   (Mockito)          │ • docker-compose      │
│                      │   (Spring Data JPA)  │ • Cálculos (%)       │ • Variáveis ambiente  │
│                      │ • CRUD completo      │ • Indicadores ESG    │ • Health checks       │
│                      │                      │                      │                       │
├──────────────────────┼──────────────────────┼──────────────────────┼───────────────────────┤
│                      │                      │                      │                       │
│ 📚 ESTUDO OBRIGATÓRIO│ 📚 ESTUDO OBRIGATÓRIO│ 📚 ESTUDO OBRIGATÓRIO│ 📚 ESTUDO OBRIGATÓRIO │
│                      │                      │                      │                       │
│ • Como funciona o    │ • Diferença entre    │ • DescarteService:   │ • Fluxo JWT:          │
│   Flyway             │   Entity e DTO       │   método registrar() │   login → token →     │
│ • Por que validate   │ • @ManyToOne vs      │   (linhas 68-145)    │   validação → auth    │
│   em vez de create   │   @OneToMany         │ • ColetaService:     │ • SecurityConfig:     │
│ • Estrutura das      │ • Conversão Boolean  │   método             │   regras de           │
│   migrations (V1__,  │   ↔ 'S'/'N' (Oracle) │   finalizarColeta()  │   autorização         │
│   V2__, V3__)        │ • Validação de       │   (linha 109)        │ • JwtAuthFilter:      │
│ • flyway_schema_     │   duplicação         │ • 4 Atividades:      │   interceptação       │
│   history            │ • Cascade operations │   1: Validação       │ • UserDetails do      │
│                      │                      │   2: Atualização     │   Spring Security     │
│                      │                      │   3: Gerar coleta    │ • Dockerfile:         │
│                      │                      │   4: Finalizar       │   multi-stage build   │
│                      │                      │                      │                       │
├──────────────────────┼──────────────────────┼──────────────────────┼───────────────────────┤
│                      │                      │                      │                       │
│ 🧪 TESTES A FAZER    │ 🧪 TESTES A FAZER    │ 🧪 TESTES A FAZER    │ 🧪 TESTES A FAZER     │
│                      │                      │                      │                       │
│ 1. mvn clean install │ 1. Listar tipos      │ 1. Descarte correto  │ 1. Login admin        │
│ 2. mvn test          │ 2. Criar tipo novo   │ 2. Descarte incorreto│ 2. Login user         │
│ 3. Conectar no Oracle│ 3. Tentar duplicar   │ 3. Cruzar limite     │ 3. Login inválido     │
│ 4. Ver tabela        │ 4. Atualizar tipo    │    (gerar coleta)    │ 4. Sem token → 401    │
│    flyway_schema_    │ 5. Deletar tipo      │ 4. Concluir coleta   │ 5. USER tenta POST    │
│    history           │ 6. Listar pontos     │    (resetar ponto)   │    → 403              │
│ 5. Contar registros  │ 7. Criar ponto       │ 5. Cancelar coleta   │ 6. ADMIN faz POST     │
│    de cada tabela    │ 8. Filtrar por       │ 6. Buscar indicadores│    → 201              │
│ 6. Rodar queries de  │    status            │ 7. Rodar testes      │ 7. Marcar notificação │
│    diagnóstico       │ 9. Ver histórico     │    unitários         │    como lida          │
│                      │    descartes         │ 8. Cenário completo  │ 8. docker compose up  │
│                      │                      │    de ponta a ponta  │ 9. Verificar logs     │
│                      │                      │                      │ 10. README válido     │
│                      │                      │                      │                       │
├──────────────────────┼──────────────────────┼──────────────────────┼───────────────────────┤
│                      │                      │                      │                       │
│ ⏱️ TEMPO ESTIMADO    │ ⏱️ TEMPO ESTIMADO    │ ⏱️ TEMPO ESTIMADO    │ ⏱️ TEMPO ESTIMADO     │
│                      │                      │                      │                       │
│ Setup: 2h            │ Testes: 2-3h         │ Estudo lógica: 3h    │ Estudo JWT: 2h        │
│ Banco: 2h            │ Estudo: 1-2h         │ Testes: 2-3h         │ Testes: 2h            │
│ Apresentação: 1h     │ Apresentação: 1h     │ Apresentação: 2h     │ Docker: 2-3h          │
│ TOTAL: 5h            │ TOTAL: 4-6h          │ TOTAL: 7-8h          │ README: 1h            │
│                      │                      │                      │ Apresentação: 1h      │
│                      │                      │                      │ TOTAL: 8-9h           │
│                      │                      │                      │                       │
└──────────────────────┴──────────────────────┴──────────────────────┴───────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│                          PONTOS DE INTEGRAÇÃO                               │
│                     (Todos precisam conhecer e usar)                        │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│ • exception/GlobalExceptionHandler.java                                    │
│   → Tratamento centralizado de erros (usado por todos os controllers)      │
│                                                                             │
│ • exception/BusinessException.java                                         │
│   → Lançada quando regra de negócio é violada                             │
│                                                                             │
│ • exception/ResourceNotFoundException.java                                 │
│   → Lançada quando um recurso não é encontrado (ID inválido)              │
│                                                                             │
│ • exception/ErrorResponse.java                                             │
│   → Formato padrão de resposta de erro JSON                               │
│                                                                             │
│ • domain/converter/SimNaoConverter.java                                    │
│   → Conversão Boolean ↔ CHAR(1) 'S'/'N' (padrão Oracle)                   │
│   → Usado em todas as entidades com campos booleanos (ativo, lida, etc.)   │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│                     AS 4 AUTOMAÇÕES DO PROJETO ESG                          │
│         (Originalmente triggers PL/SQL, agora lógica Java)                  │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│ 🔴 ATIVIDADE 1 — Validação do Descarte                                     │
│    Arquivo: DescarteService.java (linhas 75-106)                           │
│    • Bloqueia descarte se ponto estiver inativo                            │
│    • Detecta descarte incorreto (tipo descartado ≠ tipo aceito)            │
│    • Gera notificação automática de destinação incorreta                   │
│    Responsável: PESSOA 3                                                   │
│                                                                             │
│ 🟡 ATIVIDADE 2 — Atualização do Ponto de Coleta                           │
│    Arquivo: DescarteService.java (linhas 108-126)                          │
│    • Atualiza quantidadeAtualKg += quantidadeDescartada                    │
│    • Recalcula percentualOcupacao = (atual / capacidade) * 100            │
│    • Atualiza status: DISPONIVEL → ALERTA → CHEIO (baseado no %)          │
│    Responsável: PESSOA 3                                                   │
│                                                                             │
│ 🟢 ATIVIDADE 3 — Geração Automática de Coleta                             │
│    Arquivo: DescarteService.java (linhas 128-145)                          │
│    • Detecta quando o percentual cruza o limiteAlertaPercentual            │
│    • Cria automaticamente uma Coleta com status=SOLICITADA                 │
│    • Gera notificação de "Limite de ocupacao atingido"                    │
│    Responsável: PESSOA 3                                                   │
│                                                                             │
│ 🔵 ATIVIDADE 4 — Finalização do Ciclo de Coleta                           │
│    Arquivo: ColetaService.java (linhas 109-128)                            │
│    • Ao mudar status para CONCLUIDA:                                       │
│      - Registra quantidadeColetadaKg = quantidadeAtualKg do ponto         │
│      - Reseta o ponto: quantidadeAtualKg=0, percentualOcupacao=0          │
│      - Muda status do ponto para DISPONIVEL                               │
│      - Atualiza dataUltimaColeta                                          │
│      - Gera notificação de "Coleta concluida"                             │
│    Responsável: PESSOA 3                                                   │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│                     FLUXO DE APRESENTAÇÃO SUGERIDO                          │
│                          (30-40 minutos no total)                           │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│ 1️⃣ Introdução ao Projeto (2-3 min) — QUALQUER PESSOA                       │
│    • Tema ESG: Gestão de Resíduos e Reciclagem                            │
│    • Objetivos: rastreamento, alertas automáticos, indicadores ESG         │
│    • Continuidade da fase anterior (DDL + triggers PL/SQL)                 │
│                                                                             │
│ 2️⃣ Infraestrutura e Banco (5-7 min) — PESSOA 1                            │
│    • Arquitetura: Spring Boot + Oracle + Flyway + Docker                   │
│    • Demonstrar: conectar no banco, mostrar tabelas, flyway_schema_history │
│    • Explicar o versionamento do schema (V1, V2, V3)                       │
│                                                                             │
│ 3️⃣ Módulo Catálogo (5-7 min) — PESSOA 2                                   │
│    • Entidades: TipoResiduo, PontoColeta                                   │
│    • Demonstrar: criar tipo de resíduo via Swagger                         │
│    • Mostrar validação de duplicação (tentar criar com nome repetido)      │
│    • Explicar: Entity → DTO → Service → Controller                         │
│                                                                             │
│ 4️⃣ Módulo Operação + AS 4 AUTOMAÇÕES (10-15 min) — PESSOA 3 ⭐             │
│    • Explicar a portabilidade: triggers PL/SQL → lógica Java               │
│    • Demonstrar AO VIVO o fluxo completo:                                  │
│      a) Criar ponto (capacidade 50kg, limite 80%)                          │
│      b) Descartar 20kg → ponto atualizado (40%)                            │
│      c) Descartar 25kg → cruza limite (90%) → COLETA GERADA               │
│      d) Mostrar notificação criada automaticamente                         │
│      e) Concluir coleta → ponto resetado (0kg, DISPONIVEL)                │
│    • Mostrar indicadores de sustentabilidade                               │
│    • Mostrar testes unitários (DescarteServiceTest)                        │
│                                                                             │
│ 5️⃣ Segurança e Deploy (5-7 min) — PESSOA 4                                │
│    • Autenticação JWT: login → token → validação                          │
│    • Autorização: ADMIN vs USER                                            │
│    • Demonstrar: USER tentando POST (403) vs ADMIN conseguindo (201)       │
│    • Docker: mostrar docker-compose.yml, subir os containers               │
│                                                                             │
│ 6️⃣ Conclusão e Perguntas (3-5 min) — TODOS                                │
│    • Recapitular os 13 endpoints implementados                             │
│    • Destacar: 4 automações + Spring Security + Flyway + Docker            │
│    • Abrir para perguntas                                                  │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘

LEGENDA:
⭐ = Parte mais importante da apresentação (maior peso na avaliação)
🔴🟡🟢🔵 = As 4 Atividades/Automações do projeto
```

---

## 💡 Dicas Finais

1. **Comunicação no Grupo**: Criem um grupo no WhatsApp/Telegram e usem para tirar dúvidas rápidas.

2. **Reuniões Síncronas**: Marquem pelo menos 2 reuniões (uma para teste integrado, outra para ensaio da apresentação).

3. **Divisão Clara**: Cada pessoa é "dono" da sua frente, mas todos devem entender o básico das outras.

4. **Fallback**: Se algo der errado na demo ao vivo, tenham prints/vídeos de backup.

5. **Tempo**: Treinem a apresentação com cronômetro. 40 minutos passa muito rápido!

6. **Perguntas Esperadas**:
   - "Por que não usar triggers no banco?" → Resposta: portabilidade, testabilidade, controle na aplicação.
   - "Por que Oracle e não PostgreSQL/MySQL?" → Resposta: requisito do projeto.
   - "Qual a vantagem do JWT sobre sessões?" → Resposta: stateless, escalabilidade, microservices.
   - "Como garantir que as migrações não quebram em produção?" → Resposta: Flyway valida antes de aplicar, pode fazer rollback.

**Boa sorte! 🍀**
