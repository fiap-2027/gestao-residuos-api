# 📮 Exemplos de Requisições HTTP

Use estes exemplos no **Postman**, **Insomnia**, **Thunder Client** (VSCode) ou via **curl**.

## 🔐 1. Autenticação

### 1.1. Login como Admin
```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Resposta esperada (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "username": "admin",
  "role": "ADMIN"
}
```

**➡️ COPIE O TOKEN RETORNADO!** Use-o nas próximas requisições.

---

### 1.2. Login como Usuário Regular
```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "usuario",
  "password": "user123"
}
```

---

## 📋 2. Tipos de Resíduo (Pessoa 2)

### 2.1. Listar todos os tipos
```http
GET http://localhost:8080/api/tipos-residuo
Authorization: Bearer SEU_TOKEN_AQUI
```

### 2.2. Buscar tipo específico
```http
GET http://localhost:8080/api/tipos-residuo/1
Authorization: Bearer SEU_TOKEN_AQUI
```

### 2.3. Criar novo tipo (⚠️ requer ADMIN)
```http
POST http://localhost:8080/api/tipos-residuo
Authorization: Bearer SEU_TOKEN_ADMIN
Content-Type: application/json

{
  "nome": "Pilhas e Baterias",
  "categoria": "PERIGOSO",
  "corIdentificacao": "LARANJA",
  "destinacaoCorreta": "Levar a postos de coleta especializados em resíduos perigosos",
  "impactoAmbiental": "Contém metais pesados altamente tóxicos ao solo e água",
  "ativo": true
}
```

### 2.4. Atualizar tipo (⚠️ requer ADMIN)
```http
PUT http://localhost:8080/api/tipos-residuo/1
Authorization: Bearer SEU_TOKEN_ADMIN
Content-Type: application/json

{
  "nome": "PAPEL E PAPELAO",
  "categoria": "RECICLAVEL",
  "corIdentificacao": "AZUL",
  "destinacaoCorreta": "Encaminhar para coleta seletiva de papel e posterior reciclagem",
  "impactoAmbiental": "O descarte inadequado aumenta o volume de resíduos enviados para aterros",
  "ativo": true
}
```

### 2.5. Excluir tipo (⚠️ requer ADMIN)
```http
DELETE http://localhost:8080/api/tipos-residuo/6
Authorization: Bearer SEU_TOKEN_ADMIN
```

---

## 📍 3. Pontos de Coleta (Pessoa 2)

### 3.1. Listar todos os pontos
```http
GET http://localhost:8080/api/pontos-coleta
Authorization: Bearer SEU_TOKEN_AQUI
```

### 3.2. Filtrar por status
```http
GET http://localhost:8080/api/pontos-coleta?status=DISPONIVEL
Authorization: Bearer SEU_TOKEN_AQUI
```

Valores possíveis: `DISPONIVEL`, `ALERTA`, `CHEIO`, `EM_COLETA`, `INATIVO`

### 3.3. Buscar ponto específico
```http
GET http://localhost:8080/api/pontos-coleta/1
Authorization: Bearer SEU_TOKEN_AQUI
```

### 3.4. Ver histórico de descartes de um ponto
```http
GET http://localhost:8080/api/pontos-coleta/1/historico-descartes
Authorization: Bearer SEU_TOKEN_AQUI
```

### 3.5. Criar novo ponto (⚠️ requer ADMIN)
```http
POST http://localhost:8080/api/pontos-coleta
Authorization: Bearer SEU_TOKEN_ADMIN
Content-Type: application/json

{
  "idTipoResiduo": 1,
  "nome": "Ponto Campus Principal",
  "descricao": "Ponto de coleta localizado no campus universitário",
  "endereco": "Av. Paulista, 1000",
  "bairro": "Bela Vista",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01310-100",
  "capacidadeKg": 150.00,
  "limiteAlertaPercentual": 75.00
}
```

### 3.6. Atualizar ponto (⚠️ requer ADMIN)
```http
PUT http://localhost:8080/api/pontos-coleta/1
Authorization: Bearer SEU_TOKEN_ADMIN
Content-Type: application/json

{
  "idTipoResiduo": 1,
  "nome": "Ponto Central - ATUALIZADO",
  "descricao": "Descrição atualizada",
  "endereco": "Rua Nova, 123",
  "bairro": "Centro",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01000-000",
  "capacidadeKg": 200.00,
  "limiteAlertaPercentual": 80.00
}
```

### 3.7. Excluir ponto (⚠️ requer ADMIN)
```http
DELETE http://localhost:8080/api/pontos-coleta/6
Authorization: Bearer SEU_TOKEN_ADMIN
```

---

## ♻️ 4. Descartes (Pessoa 3)

### 4.1. Listar todos os descartes
```http
GET http://localhost:8080/api/descartes
Authorization: Bearer SEU_TOKEN_AQUI
```

### 4.2. Buscar descarte específico
```http
GET http://localhost:8080/api/descartes/1
Authorization: Bearer SEU_TOKEN_AQUI
```

### 4.3. Registrar descarte CORRETO (⚠️ requer ADMIN)
```http
POST http://localhost:8080/api/descartes
Authorization: Bearer SEU_TOKEN_ADMIN
Content-Type: application/json

{
  "idPontoColeta": 1,
  "idTipoResiduo": 1,
  "quantidadeKg": 10.5,
  "origem": "Departamento Administrativo"
}
```

**O que acontece:**
- `descarteCorreto: true` (tipo descartado = tipo aceito pelo ponto)
- `quantidadeAtualKg` do ponto aumenta
- `percentualOcupacao` é recalculado
- Se cruzar o `limiteAlertaPercentual`, gera coleta automática + notificação

### 4.4. Registrar descarte INCORRETO (⚠️ requer ADMIN)
```http
POST http://localhost:8080/api/descartes
Authorization: Bearer SEU_TOKEN_ADMIN
Content-Type: application/json

{
  "idPontoColeta": 1,
  "idTipoResiduo": 2,
  "quantidadeKg": 5.0,
  "origem": "Teste Descarte Incorreto"
}
```

**O que acontece:**
- `descarteCorreto: false` (tipo descartado ≠ tipo aceito pelo ponto)
- Ponto NÃO é atualizado
- Notificação de "Descarte incorreto" é criada automaticamente

### 4.5. Descarte que GERA COLETA AUTOMÁTICA (⚠️ requer ADMIN)
Primeiro, consulte o ponto 1 para ver a capacidade e ocupação atual:
```http
GET http://localhost:8080/api/pontos-coleta/1
Authorization: Bearer SEU_TOKEN_AQUI
```

Exemplo: se `capacidadeKg=100`, `limiteAlertaPercentual=80` e `quantidadeAtualKg=70`,
então ao descartar mais 10kg ou mais, cruzará o limite.

```http
POST http://localhost:8080/api/descartes
Authorization: Bearer SEU_TOKEN_ADMIN
Content-Type: application/json

{
  "idPontoColeta": 1,
  "idTipoResiduo": 1,
  "quantidadeKg": 15.0,
  "origem": "Teste Cruzar Limite"
}
```

**O que acontece:**
- Ponto atualizado: `quantidadeAtualKg=85`, `percentualOcupacao=85`, `status=ALERTA`
- **Coleta criada automaticamente** com `status=SOLICITADA`
- Notificação de "Limite de ocupacao atingido" criada

---

## 🚚 5. Coletas (Pessoa 3)

### 5.1. Listar todas as coletas
```http
GET http://localhost:8080/api/coletas
Authorization: Bearer SEU_TOKEN_AQUI
```

### 5.2. Buscar coleta específica
```http
GET http://localhost:8080/api/coletas/1
Authorization: Bearer SEU_TOKEN_AQUI
```

### 5.3. Agendar nova coleta (⚠️ requer ADMIN)
```http
POST http://localhost:8080/api/coletas
Authorization: Bearer SEU_TOKEN_ADMIN
Content-Type: application/json

{
  "idPontoColeta": 2,
  "dataAgendada": "2026-11-15",
  "destinoFinal": "Central de Reciclagem Municipal",
  "observacao": "Coleta agendada pelo gestor do sistema"
}
```

### 5.4. Atualizar coleta (mudar status) (⚠️ requer ADMIN)
```http
PUT http://localhost:8080/api/coletas/1
Authorization: Bearer SEU_TOKEN_ADMIN
Content-Type: application/json

{
  "status": "AGENDADA",
  "dataAgendada": "2026-11-20",
  "destinoFinal": "Cooperativa de Reciclagem XYZ"
}
```

Valores de status: `SOLICITADA`, `AGENDADA`, `EM_ANDAMENTO`, `CONCLUIDA`, `CANCELADA`

### 5.5. CONCLUIR coleta (⚠️ requer ADMIN)
```http
PUT http://localhost:8080/api/coletas/1
Authorization: Bearer SEU_TOKEN_ADMIN
Content-Type: application/json

{
  "status": "CONCLUIDA"
}
```

**O que acontece (Atividade 4 - finalização do ciclo):**
- `dataColeta` preenchida com data atual
- `quantidadeColetadaKg` preenchida com a quantidade que estava no ponto
- Ponto resetado: `quantidadeAtualKg=0`, `percentualOcupacao=0`, `status=DISPONIVEL`
- `dataUltimaColeta` do ponto atualizada
- Notificação de "Coleta concluida" criada

### 5.6. Cancelar coleta (⚠️ requer ADMIN)
```http
DELETE http://localhost:8080/api/coletas/1
Authorization: Bearer SEU_TOKEN_ADMIN
```

---

## 🔔 6. Notificações (Pessoa 4)

### 6.1. Listar todas as notificações
```http
GET http://localhost:8080/api/notificacoes
Authorization: Bearer SEU_TOKEN_AQUI
```

### 6.2. Listar apenas notificações não lidas
```http
GET http://localhost:8080/api/notificacoes?naoLidas=true
Authorization: Bearer SEU_TOKEN_AQUI
```

### 6.3. Buscar notificação específica
```http
GET http://localhost:8080/api/notificacoes/1
Authorization: Bearer SEU_TOKEN_AQUI
```

### 6.4. Marcar notificação como lida (⚠️ requer ADMIN)
```http
PUT http://localhost:8080/api/notificacoes/1/marcar-lida
Authorization: Bearer SEU_TOKEN_ADMIN
```

---

## 📊 7. Indicadores de Sustentabilidade (Pessoa 3)

### 7.1. Buscar indicadores consolidados
```http
GET http://localhost:8080/api/indicadores-sustentabilidade
Authorization: Bearer SEU_TOKEN_AQUI
```

**Resposta esperada:**
```json
{
  "totalPontosColeta": 5,
  "pontosDisponiveis": 3,
  "pontosEmAlerta": 1,
  "pontosCheios": 1,
  "totalDescartes": 12,
  "descartesCorretos": 10,
  "descartesIncorretos": 2,
  "taxaDescarteCorretoPercentual": 83.33,
  "totalRecicladoKgAcumulado": 245.50,
  "coletasSolicitadas": 3,
  "coletasConcluidas": 2,
  "notificacoesNaoLidas": 5
}
```

---

## 🧪 8. Cenário de Teste Completo (TODOS)

Execute na ordem para testar o fluxo de ponta a ponta:

```bash
# 1. Login
POST /api/auth/login
{"username": "admin", "password": "admin123"}
# Copiar o token retornado

# 2. Criar tipo de resíduo "Eletrônicos"
POST /api/tipos-residuo
{
  "nome": "Eletronicos",
  "categoria": "PERIGOSO",
  "corIdentificacao": "CINZA",
  "destinacaoCorreta": "Levar a postos de coleta especializados",
  "impactoAmbiental": "Contem componentes toxicos"
}
# Anotar o ID retornado (ex: 6)

# 3. Criar ponto de coleta para eletrônicos (capacidade 50kg, limite 80%)
POST /api/pontos-coleta
{
  "idTipoResiduo": 6,
  "nome": "Ponto Eletronicos Teste",
  "capacidadeKg": 50.00,
  "limiteAlertaPercentual": 80.00
}
# Anotar o ID retornado (ex: 6)

# 4. Descartar 20kg (40% de 50kg - ainda abaixo do limite)
POST /api/descartes
{"idPontoColeta": 6, "idTipoResiduo": 6, "quantidadeKg": 20.0}

# 5. Consultar o ponto - deve mostrar percentualOcupacao=40, status=DISPONIVEL
GET /api/pontos-coleta/6

# 6. Descartar mais 25kg (total 45kg = 90% - cruza o limite de 80%)
POST /api/descartes
{"idPontoColeta": 6, "idTipoResiduo": 6, "quantidadeKg": 25.0}

# 7. Consultar o ponto - deve mostrar percentualOcupacao=90, status=ALERTA
GET /api/pontos-coleta/6

# 8. Listar coletas - deve ter uma NOVA coleta com status=SOLICITADA para o ponto 6
GET /api/coletas

# 9. Listar notificações não lidas - deve ter notificação "Limite de ocupacao atingido"
GET /api/notificacoes?naoLidas=true

# 10. Concluir a coleta (anotar o ID da coleta criada, ex: 6)
PUT /api/coletas/6
{"status": "CONCLUIDA"}

# 11. Consultar o ponto - deve mostrar quantidadeAtualKg=0, percentualOcupacao=0, status=DISPONIVEL
GET /api/pontos-coleta/6

# 12. Buscar indicadores - os números devem refletir todas as operações
GET /api/indicadores-sustentabilidade
```

---

## 💡 Dicas

1. **Postman**: Crie uma "Collection" e adicione uma variável `{{token}}` no Authorization. Depois do login, salve o token nessa variável.

2. **Thunder Client (VSCode)**: Salve o token em "Env" e referencie como `{{token}}`.

3. **curl**: Exporte o token para uma variável:
   ```bash
   TOKEN="eyJhbGciOiJIUzI1NiJ9..."
   curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/tipos-residuo
   ```

4. **Swagger UI**: Mais fácil para quem está começando! Acesse `http://localhost:8080/swagger-ui.html`, clique em "Authorize" (cadeado) e cole o token.
