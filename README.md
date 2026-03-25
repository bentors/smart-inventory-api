# 📦 SmartInventory API

> **Solução inteligente para gestão de inventário e controle de ativos.**

O **SmartInventory** é uma API robusta desenvolvida para otimizar o controle de estoque de empresas que buscam agilidade e confiabilidade. O sistema centraliza a gestão de produtos, movimentações e auditoria, permitindo uma visão clara e em tempo real do patrimônio.

---

### ✨ Funcionalidades Principais

* **🛒 Gestão Completa de Produtos (CRUD):** Registro detalhado de itens com suporte a categorias, descrições técnicas e identificadores únicos.
* **📉 Controle de Estoque em Tempo Real:** Registro preciso de entradas e saídas, garantindo que o saldo atualizado esteja sempre disponível.
* **🔐 Segurança e Autorização:** Camada de proteção de dados utilizando Spring Security, garantindo que apenas usuários autorizados gerenciem informações sensíveis.
* **📜 Auditoria de Dados (Flyway):** Histórico completo de evolução do banco de dados, permitindo rastreabilidade e integridade das tabelas.
* **🐳 Deploy Facilitado com Docker:** Infraestrutura como código para subir o banco de dados e a aplicação com um único comando, garantindo que o ambiente de desenvolvimento seja idêntico ao de produção.
* **📖 Documentação Autogerada:** Interface interativa via Swagger/OpenAPI para que desenvolvedores front-end consigam testar e integrar a API sem fricção.

---

### 🛠 Tecnologias Utilizadas

| Tecnologia | Finalidade |
| :--- | :--- |
| **Java 21 (LTS)** | Linguagem base com as últimas features de performance. |
| **Spring Boot 4.0.x** | Framework para construção de microsserviços rápidos. |
| **PostgreSQL 16** | Banco de dados relacional de alta performance. |
| **Flyway** | Gerenciamento de versionamento de banco de dados. |
| **Docker & Compose** | Orquestração de containers para ambiente isolado. |
| **Swagger UI** | Documentação interativa de endpoints. |

---

### 🚀 Como Executar o Projeto

Você só precisa do **Docker** instalado para rodar a aplicação completa.

1.  **Clone o projeto:**
    ```bash
    git clone [https://github.com/bentorangel/smartinventory.git](https://github.com/bentorangel/smartinventory.git)
    cd smartinventory
    ```

2.  **Gere o artefato da aplicação (Skip Tests):**
    ```bash
    # Usando o Maven Wrapper do projeto
    ./mvnw clean package -DskipTests
    ```

3.  **Suba o ecossistema com Docker Compose:**
    ```bash
    docker-compose up --build
    ```

A API estará rodando em: `http://localhost:8080`

---

### 🔑 Configuração de Ambiente

Crie um arquivo `.env` na raiz do projeto para que o Docker injete as credenciais de forma segura (este arquivo está no `.gitignore`):

```env
DB_USER=seu_usuario_aqui
DB_PASS=sua_senha_segura_aqui
```

### 📡 Endpoints Úteis (Swagger)

Acesse a documentação completa para testar os fluxos de produtos e estoque:

🔗 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

### 🧪 Testes

Para garantir a qualidade e estabilidade das regras de negócio, utilize os comandos abaixo:

* **Rodar todos os testes:**
    ```bash
    ./mvnw test
    ```
* **Rodar apenas testes unitários:**
    ```bash
    ./mvnw test -Dgroups="unit"
    ```

---
Desenvolvido por **Bento Rangel** 🚀