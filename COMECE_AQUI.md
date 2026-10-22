# 🎯 COMECE AQUI — Projeto Gestão de Resíduos API

Bem-vindo ao projeto **API de Gestão de Resíduos e Reciclagem (ESG)**!

Este é um projeto **Spring Boot + Oracle + Flyway + Spring Security + Docker** completo e funcional, dividido igualmente entre **4 integrantes**.

---

## 📌 O QUE FOI ENTREGUE

✅ **API RESTful 100% implementada** com 13 endpoints funcionais  
✅ **Spring Security com JWT** (autenticação e autorização por perfis)  
✅ **Flyway** para migrações de banco de dados  
✅ **Docker** configurado (Dockerfile + docker-compose.yml com Oracle)  
✅ **4 automações** (antigas triggers PL/SQL → lógica Java testável)  
✅ **Testes unitários** (JUnit + Mockito)  
✅ **Documentação completa** (7 arquivos markdown)  
✅ **Divisão de tarefas equilibrada** entre 4 pessoas (sem sobreposição)

---

## 🚀 INÍCIO RÁPIDO (5 MINUTOS)

### 1️⃣ Requisitos
- Java 17+
- Maven 3.9+
- Docker Desktop

### 2️⃣ Configurar variáveis
```bash
cp .env.example .env
# Edite o arquivo .env com suas senhas
```

### 3️⃣ Subir a aplicação
```bash
docker compose up --build
```

Aguarde 2-3 minutos e acesse:
- **API**: http://localhost:8080
- **Swagger**: http://localhost:8080/swagger-ui.html

### 4️⃣ Fazer login
```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

Copie o `token` retornado e use em todas as próximas requisições no header:
```
Authorization: Bearer <seu-token>
```

**Pronto!** Agora você pode testar os endpoints via Swagger.

---

## 📚 NAVEGAÇÃO DA DOCUMENTAÇÃO

Leia os documentos **nesta ordem**:

### 🔰 Para TODOS os 4 integrantes (ler primeiro)

1. **[README.md](README.md)** ← Visão geral do projeto, arquitetura e instruções de uso  
2. **[RESUMO_VISUAL_DIVISAO.md](RESUMO_VISUAL_DIVISAO.md)** ← Diagrama visual: quem faz o quê  
3. **[DIVISAO_DE_TAREFAS.md](DIVISAO_DE_TAREFAS.md)** ← Divisão completa em 4 frentes equilibradas

### 👤 Para cada integrante (ler sua parte)

4. **[ESCOPO_TAREFAS_POR_INTEGRANTE.md](ESCOPO_TAREFAS_POR_INTEGRANTE.md)** ← Tarefas detalhadas com checklists
   - **Pessoa 1**: Infraestrutura, Banco, Flyway
   - **Pessoa 2**: Catálogo (Tipos de Resíduo + Pontos de Coleta)
   - **Pessoa 3**: Operação (Descartes + Coletas + Indicadores) — **MAIS IMPORTANTE**
   - **Pessoa 4**: Segurança (JWT + Spring Security) + Docker + Documentação

### 🛠️ Guias de referência (consultar quando necessário)

5. **[GUIA_RAPIDO_COMANDOS.md](GUIA_RAPIDO_COMANDOS.md)** ← Comandos essenciais (Maven, Docker, SQL)  
6. **[EXEMPLOS_REQUISICOES.md](EXEMPLOS_REQUISICOES.md)** ← Exemplos prontos de requisições HTTP  
7. **[CHECKLIST_ENTREGA.md](CHECKLIST_ENTREGA.md)** ← Checklist final antes de entregar o projeto

---

## 🎯 QUAL É O SEU PAPEL?

### 👤 PESSOA 1 — Infraestrutura e Banco de Dados
**Seu foco**: Garantir que o projeto compila, as migrações Flyway funcionam e o banco Oracle está acessível.

📂 **Seus arquivos**: `pom.xml`, `application.yml`, migrations `V1__`, `V2__`, `V3__`  
⏱️ **Tempo estimado**: 5 horas  
📖 **Leia**: Seção "Pessoa 1" em [ESCOPO_TAREFAS_POR_INTEGRANTE.md](ESCOPO_TAREFAS_POR_INTEGRANTE.md)

---

### 👤 PESSOA 2 — Módulo Catálogo
**Seu foco**: Entidades JPA, DTOs, Repositories, Services e Controllers de **Tipos de Resíduo** e **Pontos de Coleta**.

📂 **Seus arquivos**: `TipoResiduo.*`, `PontoColeta.*`, `SimNaoConverter`, `StatusPonto`  
⏱️ **Tempo estimado**: 4-6 horas  
📖 **Leia**: Seção "Pessoa 2" em [ESCOPO_TAREFAS_POR_INTEGRANTE.md](ESCOPO_TAREFAS_POR_INTEGRANTE.md)

---

### 👤 PESSOA 3 — Módulo Operação (⭐ MAIS CRÍTICO)
**Seu foco**: Implementação das **4 automações** (triggers PL/SQL → Java), testes unitários e indicadores ESG.

📂 **Seus arquivos**: `Descarte.*`, `Coleta.*`, `DescarteService`, `ColetaService`, `IndicadorService`, testes  
⏱️ **Tempo estimado**: 7-8 horas  
📖 **Leia**: Seção "Pessoa 3" em [ESCOPO_TAREFAS_POR_INTEGRANTE.md](ESCOPO_TAREFAS_POR_INTEGRANTE.md)

**⚠️ IMPORTANTE**: Esta é a parte **mais importante** da apresentação! As 4 automações são o diferencial do projeto.

---

### 👤 PESSOA 4 — Segurança e Deploy
**Seu foco**: Autenticação JWT, autorização (Spring Security), Docker e revisão da documentação.

📂 **Seus arquivos**: `Usuario.*`, `Notificacao.*`, `security/*`, `SecurityConfig`, `Dockerfile`, `docker-compose.yml`, `README.md`  
⏱️ **Tempo estimado**: 8-9 horas  
📖 **Leia**: Seção "Pessoa 4" em [ESCOPO_TAREFAS_POR_INTEGRANTE.md](ESCOPO_TAREFAS_POR_INTEGRANTE.md)

---

## ✅ PRIMEIROS PASSOS (HOJE)

**Cada pessoa deve fazer agora:**

1. [ ] Ler este arquivo (`COMECE_AQUI.md`)
2. [ ] Ler o `README.md`
3. [ ] Ler o `RESUMO_VISUAL_DIVISAO.md` (entender a divisão completa)
4. [ ] Ler a seção da sua pessoa em `ESCOPO_TAREFAS_POR_INTEGRANTE.md`
5. [ ] Configurar o ambiente:
   - Instalar Java 17
   - Instalar Maven
   - Instalar Docker Desktop
6. [ ] Clonar/baixar o projeto
7. [ ] Copiar `.env.example` para `.env` e preencher senhas
8. [ ] Rodar `mvn clean install`
9. [ ] Rodar `docker compose up --build`
10. [ ] Acessar o Swagger e fazer login

**Tempo total**: 1-2 horas

---

## 🔴 AS 4 AUTOMAÇÕES (PESSOA 3 - DESTAQUE DA APRESENTAÇÃO)

Estas eram **triggers PL/SQL** na fase anterior e foram **portadas para Java**:

1. **Atividade 1 — Validação do Descarte**  
   Arquivo: `DescarteService.java` (linhas 75-106)  
   Bloqueia descarte em ponto inativo + detecta descarte incorreto + notifica.

2. **Atividade 2 — Atualização do Ponto**  
   Arquivo: `DescarteService.java` (linhas 108-126)  
   Atualiza quantidade, recalcula percentual, muda status (DISPONIVEL/ALERTA/CHEIO).

3. **Atividade 3 — Geração Automática de Coleta**  
   Arquivo: `DescarteService.java` (linhas 128-145)  
   Quando o ponto cruza o limite de alerta, cria coleta automaticamente + notifica.

4. **Atividade 4 — Finalização do Ciclo**  
   Arquivo: `ColetaService.java` (linhas 109-128)  
   Ao concluir coleta, reseta o ponto (0kg, DISPONIVEL) + atualiza data + notifica.

---

## 📊 ENDPOINTS IMPLEMENTADOS (13 NO TOTAL)

| Módulo | Método | Endpoint | Perfil Necessário |
|---|---|---|---|
| **Auth** | POST | `/api/auth/login` | Público |
| **Tipos** | GET/POST/PUT/DELETE | `/api/tipos-residuo` | USER / ADMIN |
| **Pontos** | GET/POST/PUT/DELETE | `/api/pontos-coleta` | USER / ADMIN |
| **Pontos** | GET | `/api/pontos-coleta/{id}/historico-descartes` | USER / ADMIN |
| **Descartes** | GET/POST | `/api/descartes` | USER / ADMIN |
| **Coletas** | GET/POST/PUT/DELETE | `/api/coletas` | USER / ADMIN |
| **Notificações** | GET | `/api/notificacoes` | USER / ADMIN |
| **Notificações** | PUT | `/api/notificacoes/{id}/marcar-lida` | ADMIN |
| **Indicadores** | GET | `/api/indicadores-sustentabilidade` | USER / ADMIN |

**Observação**: Operações de escrita (POST/PUT/DELETE) exigem perfil **ADMIN**.

---

## 🎤 PREPARAÇÃO PARA A APRESENTAÇÃO

### Cronograma Sugerido

**Segunda**: Setup ambiente + testes individuais (cada pessoa testa sua frente)  
**Terça**: Revisão cruzada (todos leem o código dos outros)  
**Quarta**: Correção de bugs + preparar apresentações individuais  
**Quinta**: Teste integrado final (todos juntos) + ensaio da apresentação  
**Sexta**: Revisar checklist → **ENTREGAR!**

### Duração da Apresentação
- **Total**: 30-40 minutos
- **Pessoa 1**: 5-7 min (infraestrutura)
- **Pessoa 2**: 5-7 min (catálogo)
- **Pessoa 3**: 10-15 min (operação + demo das 4 automações) ⭐
- **Pessoa 4**: 5-7 min (segurança + Docker)
- **Q&A**: 3-5 min

---

## 🆘 PROBLEMAS COMUNS

### ❌ "Failed to configure a DataSource"
**Solução**: Oracle não está rodando. Execute `docker compose up oracle-db` primeiro.

### ❌ "Port 8080 is already in use"
**Solução**: Matar o processo que está usando a porta ou mudar no `application.yml`.

### ❌ "401 Unauthorized" em todos os endpoints
**Solução**: Token não foi incluído no header. Fazer login e copiar o token.

### ❌ Testes falhando
**Solução**: `mvn clean test`. Se persistir, verificar `src/test/resources/application.yml`.

### ❌ Docker muito lento
**Solução**: Alocar mais memória ao Docker Desktop (Settings → Resources → Memory: 4GB+).

---

## 🎁 O QUE VOCÊ TEM EM MÃOS

```
gestao-residuos-api/
├── 📄 COMECE_AQUI.md                      ← Você está aqui!
├── 📄 README.md                            ← Visão geral do projeto
├── 📄 DIVISAO_DE_TAREFAS.md               ← Divisão em 4 frentes
├── 📄 ESCOPO_TAREFAS_POR_INTEGRANTE.md    ← Tarefas detalhadas
├── 📄 RESUMO_VISUAL_DIVISAO.md            ← Diagrama visual
├── 📄 GUIA_RAPIDO_COMANDOS.md             ← Comandos úteis
├── 📄 EXEMPLOS_REQUISICOES.md             ← Exemplos de uso da API
├── 📄 CHECKLIST_ENTREGA.md                ← Checklist final
├── 📦 pom.xml                              ← Dependências Maven
├── 🐳 Dockerfile                           ← Build da imagem Docker
├── 🐳 docker-compose.yml                   ← Orquestração (Oracle + API)
├── 📂 src/main/java/...                    ← 52 arquivos Java
├── 📂 src/main/resources/
│   ├── application.yml                     ← Configuração da API
│   └── db/migration/                       ← 3 migrações Flyway
├── 📂 src/test/java/...                    ← 2 testes automatizados
└── 📂 docs/
    └── triggers_fase_anterior_referencia.sql ← Triggers PL/SQL originais
```

**Total**: 7 documentos markdown + 52 classes Java + 3 migrations + Docker completo

---

## 🏆 CRITÉRIOS DE SUCESSO

### ⭐ Entrega Mínima (Nota Básica)
- Projeto compila
- 4+ endpoints funcionam
- Docker sobe
- Apresentação básica

### ⭐⭐ Entrega Completa (Nota Boa)
- Tudo acima +
- 13 endpoints funcionam
- 4 automações testadas
- Documentação completa
- Divisão equilibrada

### ⭐⭐⭐ Entrega Excelente (Nota Máxima)
- Tudo acima +
- Demo ao vivo perfeita
- Cada pessoa domina sua frente
- Código limpo e bem comentado
- Comparação clara: triggers PL/SQL → Java
- Apresentação profissional

---

## 💪 MENSAGEM FINAL

Vocês têm em mãos um projeto **completo, funcional e bem documentado**.

O trabalho agora é:
1. ✅ **Validar** que tudo funciona
2. 📚 **Estudar** e dominar sua parte
3. 🧪 **Testar** os endpoints e cenários
4. 🎤 **Apresentar** com confiança

**Tempo estimado total por pessoa**: 5-9 horas (depende da frente)

**Vocês conseguem!** 🚀

---

**Dúvidas?** Consulte os outros documentos markdown ou pergunte no grupo.

**Próximo passo**: Leia o [README.md](README.md) e configure seu ambiente.

**Boa sorte na entrega! 🍀**
