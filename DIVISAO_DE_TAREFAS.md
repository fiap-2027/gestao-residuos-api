# Divisão de tarefas — Grupo de 4 pessoas
## Projeto: API de Gestão de Resíduos e Reciclagem (ESG)

O código-base completo do serviço RESTful (Spring Boot + Oracle + Flyway +
Spring Security + Docker) já foi implementado na pasta `gestao-residuos-api/`.
A divisão abaixo distribui a **responsabilidade de revisão, testes,
aprofundamento e eventual evolução** de cada módulo entre 4 integrantes, de
forma equilibrada, para que cada um domine e consiga apresentar/defender a
sua parte. Os módulos foram organizados para não haver sobreposição de
arquivos entre as pessoas.

---

### Pessoa 1 — Infraestrutura, Banco de Dados e Migrações (Flyway)
**Pacotes/arquivos sob sua responsabilidade:**
- `pom.xml`, `src/main/resources/application.yml`
- `src/main/resources/db/migration/V1__create_schema.sql` (tabelas, sequences, índices)
- `src/main/resources/db/migration/V2__seed_data.sql` (dados iniciais)
- `docs/triggers_fase_anterior_referencia.sql` (script PL/SQL da fase anterior)
- `GestaoResiduosApplication.java`

**O que fazer/estudar:**
1. Validar que o schema do Flyway reflete fielmente o modelo entregue na fase anterior (tabelas `TIPO_RESIDUO`, `PONTO_COLETA`, `DESCARTE`, `COLETA`, `NOTIFICACAO`).
2. Subir um Oracle local (ou usar o `docker-compose`) e confirmar que `mvn spring-boot:run` aplica as migrações sem erro.
3. Preparar a explicação de como o Flyway versiona e controla o histórico de alterações do banco (tabela `flyway_schema_history`).
4. Revisar e, se necessário, complementar os dados de seed (mínimo 5 registros por tabela, já presentes).

---

### Pessoa 2 — Módulo "Catálogo": Tipos de Resíduo e Pontos de Coleta
**Pacotes/arquivos sob sua responsabilidade:**
- `domain/entity/TipoResiduo.java`, `domain/entity/PontoColeta.java`
- `domain/enums/StatusPonto.java`
- `repository/TipoResiduoRepository.java`, `repository/PontoColetaRepository.java`
- `dto/request|response/TipoResiduoRequest|Response.java`, `PontoColetaRequest|Response.java`
- `service/TipoResiduoService.java`, `service/PontoColetaService.java`
- `controller/TipoResiduoController.java`, `controller/PontoColetaController.java`

**Endpoints desta frente:**
`GET/POST/PUT/DELETE /api/tipos-residuo`, `GET/POST/PUT/DELETE /api/pontos-coleta`,
`GET /api/pontos-coleta/{id}/historico-descartes`.

**O que fazer/estudar:**
1. Explicar as validações de Bean Validation (`@NotBlank`, `@DecimalMin`, etc.) aplicadas nos DTOs de request.
2. Testar os fluxos de CRUD completos via Swagger/Postman.
3. Entender e demonstrar a regra que impede cadastro de tipo de resíduo duplicado (`BusinessException`).

---

### Pessoa 3 — Módulo "Operação": Descarte, Coleta e Indicadores
**Pacotes/arquivos sob sua responsabilidade:**
- `domain/entity/Descarte.java`, `domain/entity/Coleta.java`
- `domain/enums/StatusColeta.java`
- `repository/DescarteRepository.java`, `repository/ColetaRepository.java`
- `dto/request|response` de Descarte, Coleta e `IndicadoresResponse.java`
- `service/DescarteService.java`, `service/ColetaService.java`, `service/IndicadorService.java`
- `controller/DescarteController.java`, `controller/ColetaController.java`, `controller/IndicadorController.java`
- `src/test/java/.../DescarteServiceTest.java`

**Endpoints desta frente:**
`POST/GET /api/descartes`, `POST/PUT/DELETE/GET /api/coletas`,
`GET /api/indicadores-sustentabilidade`.

**O que fazer/estudar:**
1. Esta é a frente que concentra a **automação herdada das triggers PL/SQL**
   da fase anterior: validação do descarte, atualização do percentual de
   ocupação do ponto, geração automática de coleta ao atingir o limite, e
   finalização do ciclo ao concluir a coleta. Precisa saber explicar essa
   lógica em detalhes (está toda comentada em `DescarteService` e `ColetaService`).
2. Rodar `mvn test` e entender os testes unitários já escritos (Mockito) para esse módulo.
3. Testar manualmente o cenário de "alerta automático": registrar descartes até o ponto atingir o limite configurado e verificar se a coleta e a notificação são criadas.

---

### Pessoa 4 — Segurança (Spring Security/JWT), Notificações e Docker
**Pacotes/arquivos sob sua responsabilidade:**
- `domain/entity/Notificacao.java`, `domain/entity/Usuario.java`
- `repository/NotificacaoRepository.java`, `repository/UsuarioRepository.java`
- `security/*` (JwtService, JwtAuthenticationFilter, CustomUserDetailsService)
- `config/SecurityConfig.java`, `config/OpenApiConfig.java`
- `controller/AuthController.java`, `controller/NotificacaoController.java`
- `service/NotificacaoService.java`
- `src/main/resources/db/migration/V3__create_security.sql`
- `Dockerfile`, `docker-compose.yml`, `.env.example`, `.dockerignore`, `README.md`

**Endpoints desta frente:**
`POST /api/auth/login`, `GET /api/notificacoes`, `PUT /api/notificacoes/{id}/marcar-lida`.

**O que fazer/estudar:**
1. Explicar o fluxo de autenticação JWT (login → geração do token → filtro que valida o token em cada requisição) e a regra de autorização (perfil `ADMIN` para escrita, `USER`/`ADMIN` para leitura).
2. Executar `docker compose up --build` e validar a subida do Oracle + da API via container.
3. Revisar o `README.md` e garantir que os passos de execução (local e via Docker) estão corretos e completos.

---

## Pontos de integração (atenção de todos)

- `exception/GlobalExceptionHandler.java`, `exception/BusinessException.java`,
  `exception/ResourceNotFoundException.java`, `exception/ErrorResponse.java`:
  usados por todos os módulos — qualquer alteração deve ser combinada entre o grupo.
- `domain/converter/SimNaoConverter.java`: conversor compartilhado (Boolean ↔ `'S'/'N'` do Oracle).

## Sugestão de fluxo de trabalho em grupo

1. Cada pessoa cria uma branch com o nome do seu módulo (ex.: `modulo-seguranca`).
2. Após testar sua parte isoladamente, abrir um merge/pull request para a branch principal.
3. Reservar um horário comum para rodar o `docker compose up` juntos e testar a API integrada de ponta a ponta antes da entrega final.
4. Dividir a apresentação/defesa do trabalho pelas mesmas 4 frentes acima.
