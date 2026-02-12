# SQL Catalog API

API REST para gerenciamento de scripts SQL, desenvolvida com Java e Spring Boot. Permite catalogar, organizar e buscar rapidamente queries, procedures, functions e outros scripts SQL.

## 📋 Sobre o Projeto

O SQL Catalog resolve um problema comum entre desenvolvedores e DBAs: a organização de scripts SQL. Quantas vezes você precisou daquela query que usa frequentemente, mas não lembrava onde havia salvo?

Com o SQL Catalog você pode:
- Armazenar scripts SQL com título, descrição e conteúdo
- Categorizar por banco de dados (Oracle, MySQL, PostgreSQL, SQL Server)
- Classificar por tipo (Query, Procedure, Function, Trigger, DDL, DML)
- Adicionar tags personalizadas para facilitar buscas
- Buscar por texto em título, descrição e conteúdo

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 4.1.0**
- **Spring Security** - Autenticação e autorização
- **JWT (JSON Web Token)** - Tokens de acesso
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados
- **Maven** - Gerenciamento de dependências
- **Bean Validation** - Validação de dados

## 📁 Estrutura do Projeto
```
src/main/java/com/registradorSQL/SQLCatalog/
├── config/                 # Configurações (Security, CORS)
├── controller/             # Endpoints da API
├── dto/                    # Objetos de transferência de dados
├── enu/                    # Enumerações (Role, BancoDados, Categoria)
├── exception/              # Tratamento de exceções
├── model/                  # Entidades JPA
├── repository/             # Repositórios de dados
├── security/               # JWT e filtros de segurança
└── service/                # Regras de negócio
```

## ⚙️ Configuração e Instalação

### Pré-requisitos

- Java 21 ou superior
- PostgreSQL 12 ou superior
- Maven 3.9 ou superior

### Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL:
```sql
CREATE DATABASE SQLCatalog;
```

2. Configure as credenciais no arquivo `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/SQLCatalog
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### Executando a Aplicação

1. Clone o repositório:
```bash
git clone https://github.com/otniel123/SQLCatalog.git
cd SQLCatalog
```

2. Execute com Maven:
```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`

## 🔐 Autenticação

A API utiliza JWT (JSON Web Token) para autenticação. O fluxo é:

1. Registre um usuário em `POST /api/auth/registrar`
2. Faça login em `POST /api/auth/login` para receber o token
3. Envie o token no header de todas as requisições protegidas:
```
Authorization: Bearer seu_token_aqui
```

### Roles de Usuário

| Role | Permissões |
|------|------------|
| `USER` | CRUD dos próprios scripts |
| `ADMIN` | Tudo que USER pode + gerenciar usuários + ver estatísticas + ver todos os scripts |

## 📚 Endpoints

### Autenticação (`/api/auth`)

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/api/auth/registrar` | Registrar novo usuário | Não |
| POST | `/api/auth/registrar/admin` | Registrar administrador | Não |
| POST | `/api/auth/login` | Fazer login | Não |

#### Registrar Usuário
```http
POST /api/auth/registrar
Content-Type: application/json

{
    "nome": "Gabriel",
    "email": "gabriel@email.com",
    "senha": "123456"
}
```

**Resposta (201 Created):**
```json
{
    "id": 1,
    "nome": "Gabriel",
    "email": "gabriel@email.com",
    "role": "USER",
    "dataCriacao": "2026-01-28T10:30:00",
    "ativo": true
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
    "email": "gabriel@email.com",
    "senha": "123456"
}
```

**Resposta (200 OK):**
```json
{
    "token": "eyJhbGciOiJIUzM4NCJ9...",
    "tipo": "Bearer",
    "expiraEm": 86400,
    "usuario": {
        "id": 1,
        "nome": "Gabriel",
        "email": "gabriel@email.com",
        "role": "USER",
        "dataCriacao": "2026-01-28T10:30:00",
        "ativo": true
    }
}
```

### Scripts (`/api/scripts`)

Todos os endpoints de scripts requerem autenticação. Cada usuário só tem acesso aos seus próprios scripts.

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/scripts` | Listar scripts do usuário |
| GET | `/api/scripts/{id}` | Buscar script por ID |
| POST | `/api/scripts` | Criar novo script |
| PUT | `/api/scripts/{id}` | Atualizar script |
| DELETE | `/api/scripts/{id}` | Excluir script |

#### Criar Script
```http
POST /api/scripts
Authorization: Bearer seu_token
Content-Type: application/json

{
    "titulo": "Buscar funcionários ativos",
    "descricao": "Lista todos os funcionários com status ativo",
    "conteudo": "SELECT * FROM funcionarios WHERE status = 'ATIVO'",
    "bancoDados": "ORACLE",
    "categoria": "QUERY",
    "tags": ["funcionarios", "rh", "consulta"]
}
```

**Resposta (201 Created):**
```json
{
    "id": 1,
    "titulo": "Buscar funcionários ativos",
    "descricao": "Lista todos os funcionários com status ativo",
    "conteudo": "SELECT * FROM funcionarios WHERE status = 'ATIVO'",
    "bancoDados": "ORACLE",
    "categoria": "QUERY",
    "tags": ["funcionarios", "rh", "consulta"],
    "dataCriacao": "2026-01-28T10:30:00",
    "dataAtualizacao": "2026-01-28T10:30:00"
}
```

#### Listar Scripts com Filtros
```http
GET /api/scripts?banco=ORACLE&categoria=QUERY&texto=funcionario&tag=rh&page=0&size=20
Authorization: Bearer seu_token
```

**Parâmetros de Query:**

| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `banco` | String | Filtrar por banco de dados |
| `categoria` | String | Filtrar por categoria |
| `texto` | String | Buscar em título, descrição e conteúdo |
| `tag` | String | Filtrar por tag |
| `page` | Integer | Número da página (padrão: 0) |
| `size` | Integer | Itens por página (padrão: 20) |

### Domínios (`/api/dominios`)

Endpoints públicos para listar valores disponíveis.

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/dominios/bancos` | Listar bancos de dados disponíveis |
| GET | `/api/dominios/categorias` | Listar categorias disponíveis |

**Bancos de Dados Disponíveis:**
- `ORACLE`
- `MYSQL`
- `POSTGRESQL`
- `SQLSERVER`
- `OUTROS`

**Categorias Disponíveis:**
- `QUERY`
- `PROCEDURE`
- `FUNCTION`
- `TRIGGER`
- `DDL`
- `DML`
- `OUTROS`

### Administração (`/api/admin`)

Endpoints exclusivos para usuários com role `ADMIN`.

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/admin/usuarios` | Listar todos os usuários |
| GET | `/api/admin/usuarios/{id}` | Buscar usuário por ID |
| PATCH | `/api/admin/usuarios/{id}/role?role=ADMIN` | Alterar role do usuário |
| PATCH | `/api/admin/usuarios/{id}/desativar` | Desativar usuário |
| PATCH | `/api/admin/usuarios/{id}/ativar` | Ativar usuário |
| GET | `/api/admin/scripts` | Listar todos os scripts do sistema |
| GET | `/api/admin/usuarios/{id}/scripts` | Listar scripts de um usuário |
| GET | `/api/admin/estatisticas` | Ver estatísticas do sistema |

#### Estatísticas
```http
GET /api/admin/estatisticas
Authorization: Bearer token_do_admin
```

**Resposta (200 OK):**
```json
{
    "totalUsuarios": 10,
    "usuariosAtivos": 8,
    "totalScripts": 45,
    "scriptsPorBanco": {
        "ORACLE": 20,
        "POSTGRESQL": 15,
        "MYSQL": 10
    },
    "scriptsPorCategoria": {
        "QUERY": 25,
        "PROCEDURE": 12,
        "FUNCTION": 8
    }
}
```

### Health Check (`/api/health`)
```http
GET /api/health
```

**Resposta (200 OK):**
```json
{
    "status": "online",
    "aplicacao": "SQL Catalog"
}
```

## ❌ Tratamento de Erros

A API retorna erros em formato padronizado:
```json
{
    "timestamp": "2026-01-28T10:30:00",
    "status": 400,
    "erro": "Mensagem descritiva do erro",
    "path": "/api/scripts"
}
```

### Códigos de Status

| Código | Descrição |
|--------|-----------|
| 200 | Sucesso |
| 201 | Criado com sucesso |
| 204 | Sucesso sem conteúdo (DELETE) |
| 400 | Requisição inválida (validação) |
| 401 | Não autenticado |
| 403 | Acesso negado |
| 404 | Recurso não encontrado |
| 409 | Conflito (ex: email duplicado) |
| 500 | Erro interno do servidor |

## 🔒 Segurança

- Senhas são criptografadas com BCrypt
- Tokens JWT expiram em 24 horas
- Cada usuário só acessa seus próprios scripts
- Endpoints administrativos protegidos por role
- CORS configurado para ambientes de desenvolvimento

## 📝 Validações

### Usuário

| Campo | Regras |
|-------|--------|
| nome | Obrigatório |
| email | Obrigatório, formato válido, único |
| senha | Obrigatório, mínimo 6 caracteres |

### Script

| Campo | Regras |
|-------|--------|
| titulo | Obrigatório |
| conteudo | Obrigatório |
| bancoDados | Obrigatório |
| categoria | Obrigatório |
| descricao | Máximo 500 caracteres |

## 🛠️ Desenvolvimento

### Executar Testes
```bash
./mvnw test
```

### Gerar Build
```bash
./mvnw clean package
```

O JAR será gerado em `target/SQLCatalog-0.0.1-SNAPSHOT.jar`

### Executar JAR
```bash
java -jar target/SQLCatalog-0.0.1-SNAPSHOT.jar
```

## 📄 Licença

Este projeto está sob a licença MIT.

## 👤 Autor

**Otniel Marques**

- GitHub: [@otniel123](https://github.com/otniel123)

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no repositório!