# 📦 Smart Inventory API

> **Solução inteligente para gestão de inventário e controle de ativos em tempo real.**

[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.x-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?logo=docker)](https://www.docker.com/)
[![Deploy](https://img.shields.io/badge/Deploy-Render-46E3B7?logo=render)](https://smartinventory-api-m3gr.onrender.com/swagger-ui/index.html)

**🌐 API em Produção:** [smartinventory-api-m3gr.onrender.com](https://smartinventory-api-m3gr.onrender.com/swagger-ui/index.html)

---

## 🚀 Sobre o Projeto

O **SmartInventory** é uma API REST robusta desenvolvida para otimizar o controle de estoque de empresas que buscam agilidade e confiabilidade. O sistema centraliza a gestão de produtos, movimentações e auditoria, permitindo uma visão clara e em tempo real do patrimônio.

---

## ✨ Funcionalidades Principais

### 🛒 Gestão Completa de Produtos (CRUD)
- Registro detalhado de itens com SKU único, preço, custo e descrição
- Busca e filtros por nome
- Paginação e ordenação customizável
- Validação completa de dados com Bean Validation

### 📉 Controle de Estoque em Tempo Real
- Registro preciso de entradas e saídas
- Histórico completo de movimentações por produto
- Atualização automática de saldo
- Alerta de estoque mínimo

### 🔐 Segurança e Autorização
- Autenticação JWT stateless
- Controle de acesso baseado em roles (RBAC)
- Senhas criptografadas com BCrypt
- Proteção de rotas sensíveis

### 📜 Auditoria de Dados
- Migrations versionadas com Flyway
- Histórico completo de evolução do banco
- Rastreabilidade e integridade das tabelas

### 🐳 Deploy Facilitado com Docker
- Infraestrutura como código
- Multi-stage build otimizado
- Ambiente de desenvolvimento idêntico ao de produção
- Orquestração com Docker Compose

### 📖 Documentação Autogerada
- Interface interativa via Swagger/OpenAPI
- Exemplos de request/response
- Teste direto dos endpoints

---

## 🛠 Tecnologias Utilizadas

| Tecnologia | Finalidade |
| :--- | :--- |
| **Java 21 (LTS)** | Linguagem base com features modernas de performance |
| **Spring Boot 4.0.x** | Framework para construção de microsserviços |
| **Spring Security** | Autenticação e autorização |
| **JWT** | Tokens stateless para autenticação |
| **PostgreSQL 16** | Banco de dados relacional de alta performance |
| **Flyway** | Versionamento e migrations de banco de dados |
| **Docker & Compose** | Orquestração de containers |
| **Swagger/OpenAPI** | Documentação interativa de endpoints |
| **JUnit 5 + Mockito** | Testes unitários |
| **Maven** | Gerenciamento de dependências |

---

## 📂 Estrutura do Projeto

```
src/main/java/com/bentorangel/smartinventory/
├── controllers/          # Endpoints REST
│   ├── AuthenticationController.java
│   └── ProductController.java
├── services/            # Lógica de negócio
│   ├── AuthorizationService.java
│   ├── ProductService.java
│   └── TokenService.java
├── repositories/        # Camada de dados (JPA)
│   ├── ProductRepository.java
│   ├── StockMovementRepository.java
│   └── UserRepository.java
├── domain/             # Entidades JPA
│   ├── Product.java
│   ├── StockMovement.java
│   ├── User.java
│   └── UserRole.java
├── dtos/               # Data Transfer Objects
│   ├── AuthenticationDTO.java
│   ├── LoginResponseDTO.java
│   ├── ProductRequestDTO.java
│   ├── ProductResponseDTO.java
│   ├── RegisterDTO.java
│   └── StockMovementResponseDTO.java
└── infra/              # Configurações e infraestrutura
    ├── exceptions/     # Tratamento global de exceções
    │   └── GlobalExceptionHandler.java
    ├── security/       # Configurações de segurança
    │   ├── SecurityConfigurations.java
    │   └── SecurityFilter.java
    └── springdoc/      # Configurações do Swagger
        └── SpringDocConfigurations.java
```

---

## 🔌 Principais Endpoints

### 🔐 Autenticação

**Registrar usuário:**
```http
POST /auth/register
Content-Type: application/json

{
  "login": "usuario@email.com",
  "password": "senha123",
  "role": "USER"
}
```

**Login:**
```http
POST /auth/login
Content-Type: application/json

{
  "login": "usuario@email.com",
  "password": "senha123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 📦 Produtos

**Listar produtos (com paginação):**
```http
GET /api/products?page=0&size=10&sort=name,asc
Authorization: Bearer {token}

Response: {
  "content": [...],
  "pageable": {...},
  "totalElements": 50,
  "totalPages": 5
}
```

**Buscar por nome:**
```http
GET /api/products/search?name=Notebook&page=0&size=10
Authorization: Bearer {token}
```

**Criar produto:**
```http
POST /api/products
Authorization: Bearer {token}
Content-Type: application/json

{
  "sku": "PROD-001",
  "name": "Notebook Dell",
  "price": 3500.00,
  "cost": 2800.00,
  "currentStock": 15,
  "minStockAlert": 5
}
```

**Atualizar estoque:**
```http
PATCH /api/products/{id}/stock?quantity=10&reason=Entrada de mercadoria
Authorization: Bearer {token}
```

**Histórico de movimentações:**
```http
GET /api/products/{id}/history
Authorization: Bearer {token}

Response: [
  {
    "id": "uuid",
    "type": "ENTRADA",
    "quantity": 10,
    "reason": "Compra de fornecedor",
    "createdAt": "2024-03-16T10:30:00"
  }
]
```

**Deletar produto:**
```http
DELETE /api/products/{id}
Authorization: Bearer {token}

Response: 204 No Content
```

---

## 🚀 Como Executar o Projeto

### Opção 1: Com Docker (Recomendado)

Você só precisa do **Docker** instalado para rodar a aplicação completa.

1. **Clone o projeto:**
   ```bash
   git clone https://github.com/bentors/smart-inventory-api.git
   cd smart-inventory-api
   ```

2. **Configure variáveis de ambiente:**
   
   Crie um arquivo `.env` na raiz do projeto:
   ```env
   DB_USER=seu_usuario_aqui
   DB_PASS=sua_senha_segura_aqui
   ```

3. **Gere o artefato da aplicação:**
   ```bash
   ./mvnw clean package -DskipTests
   ```

4. **Suba o ecossistema com Docker Compose:**
   ```bash
   docker-compose up --build
   ```

A API estará rodando em: `http://localhost:8080`

### Opção 2: Sem Docker (Manual)

**Pré-requisitos:**
- Java 21+
- Maven 3.8+
- PostgreSQL 16+

1. **Clone e configure:**
   ```bash
   git clone https://github.com/bentors/smart-inventory-api.git
   cd smart-inventory-api
   ```

2. **Crie o banco de dados:**
   ```sql
   CREATE DATABASE smart_inventory_db;
   ```

3. **Configure o `application.properties`:**
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/smart_inventory_db
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```

4. **Execute a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 📖 Documentação da API

Acesse a documentação interativa completa para testar todos os endpoints:

**🔗 Produção:** [smartinventory-api-m3gr.onrender.com/swagger-ui/index.html](https://smartinventory-api-m3gr.onrender.com/swagger-ui/index.html)

---

## 🧪 Testes

Para garantir a qualidade e estabilidade das regras de negócio:

**Rodar todos os testes:**
```bash
./mvnw test
```

**Rodar testes unitários específicos:**
```bash
./mvnw test -Dtest=ProductServiceTest
```

**Cobertura de testes:**
```bash
./mvnw test jacoco:report
```

---

## 📊 Modelagem do Banco de Dados

### Tabela: users
```sql
CREATE TABLE users (
    id UUID PRIMARY KEY,
    login VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Tabela: products
```sql
CREATE TABLE products (
    id UUID PRIMARY KEY,
    sku VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    cost DECIMAL(10,2) NOT NULL,
    current_stock INTEGER NOT NULL DEFAULT 0,
    min_stock_alert INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Tabela: stock_movements
```sql
CREATE TABLE stock_movements (
    id UUID PRIMARY KEY,
    product_id UUID REFERENCES products(id),
    type VARCHAR(50) NOT NULL,
    quantity INTEGER NOT NULL,
    reason TEXT,
    user_id UUID REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 🌐 Deploy

A aplicação está deployada no **Render** com deploy contínuo:

- **API Base:** https://smartinventory-api-m3gr.onrender.com
- **Swagger UI:** https://smartinventory-api-m3gr.onrender.com/swagger-ui/index.html

---

## 🔒 Segurança

- ✅ Senhas criptografadas com BCrypt
- ✅ Autenticação stateless com JWT
- ✅ Proteção CSRF desabilitada (API REST stateless)
- ✅ Controle de acesso baseado em roles (RBAC)
- ✅ Validação de entrada com Bean Validation
- ✅ Tratamento global de exceções
- ✅ Variáveis de ambiente para credenciais sensíveis

---

## 📈 Próximas Melhorias

- [ ] Implementar paginação no histórico de movimentações
- [ ] Adicionar filtros avançados (por data, tipo de movimentação)
- [ ] Implementar soft delete para produtos
- [ ] Adicionar categorias de produtos
- [ ] Criar relatórios em PDF/Excel
- [ ] Implementar cache com Redis
- [ ] Adicionar rate limiting
- [ ] Notificações quando estoque atingir nível mínimo
- [ ] Aumentar cobertura de testes para >80%
- [ ] Adicionar logs estruturados

---

## 👨‍💻 Autor

**Bento Rangel**
- GitHub: [@bentors](https://github.com/bentors)
- LinkedIn: [Bento Rangel](https://www.linkedin.com/in/bento-rangel)
- Email: bento.rangel05@gmail.com

---

⭐ Se este projeto te ajudou de alguma forma, considere dar uma estrela!

**Desenvolvido com ☕ e Java por Bento Rangel**
