# 📋 TodoList Pro API

![Java](https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

API REST profissional para gerenciamento de tarefas (TodoList) desenvolvida com **Spring Boot**, **Spring Security**, **JWT** e **MySQL**.

---

## 🎯 Sobre o Projeto

Este projeto foi desenvolvido para demonstrar habilidades em desenvolvimento backend com Java e Spring Boot, incluindo:

- ✅ API RESTful com boas práticas
- 🔐 Autenticação e autorização com JWT
- 🗄️ Persistência de dados com JPA/Hibernate
- 📊 Arquitetura em camadas (Controller, Service, Repository)
- ✨ Validações e tratamento de exceções
- 📚 Documentação interativa com Swagger/OpenAPI
- 🔒 Segurança com Spring Security
- 🌐 Configuração CORS para integração com frontend

---

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 25**
- **Spring Boot 4.0.0**
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **JWT (JSON Web Token)** - Tokens de autenticação
- **MySQL 8.0** - Banco de dados relacional
- **Lombok** - Redução de código boilerplate
- **Springdoc OpenAPI** - Documentação da API (Swagger)

### Ferramentas
- **Maven** - Gerenciamento de dependências
- **Docker** - Containerização do MySQL
- **IntelliJ IDEA** - IDE de desenvolvimento

---

## 🚀 Funcionalidades

### 🔐 Autenticação
- [x] Registro de novos usuários
- [x] Login com geração de token JWT
- [x] Senha criptografada com BCrypt

### 📝 Gerenciamento de Tarefas
- [x] Criar tarefa
- [x] Listar todas as tarefas do usuário autenticado
- [x] Buscar tarefa por ID
- [x] Buscar tarefas por status (PENDING, IN_PROGRESS, COMPLETED)
- [x] Buscar tarefas por título (pesquisa textual)
- [x] Atualizar tarefa
- [x] Deletar tarefa

### 🔒 Segurança
- [x] Rotas protegidas (apenas usuários autenticados)
- [x] Cada usuário acessa apenas suas próprias tarefas
- [x] Validação de dados de entrada
- [x] Tratamento de exceções personalizado

---

## 📦 Instalação e Execução

### Pré-requisitos

- Java JDK 25
- Docker (para o MySQL)
- Maven 3.x
- IntelliJ IDEA ou outra IDE

### Passo 1: Clonar o Repositório
```bash
git clone https://github.com/rxzcodes/todolist-pro.git
cd todolist-pro
```

### Passo 2: Subir o Banco de Dados MySQL com Docker
```bash
docker run --name mysql-todolist \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=todolist \
  -p 3306:3306 \
  -d mysql:8.0
```

### Passo 3: Configurar as Variáveis de Ambiente (Opcional)

Edite o arquivo `src/main/resources/application.properties` se necessário:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todolist
spring.datasource.username=root
spring.datasource.password=root

jwt.secret=minhachavesecretasuperseguradoprojeto2024todolistpro123456789
jwt.expiration=86400000
```

### Passo 4: Compilar e Executar
```bash
mvn clean install
mvn spring-boot:run
```

Ou execute diretamente pela IDE (IntelliJ IDEA).

### Passo 5: Acessar a Documentação Swagger

Abra no navegador:
```
http://localhost:8080/swagger-ui.html
```

---

## 📚 Documentação da API

### Endpoints Públicos (Não requerem autenticação)

#### Registrar Usuário
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "joao",
  "email": "joao@email.com",
  "password": "senha123"
}
```

**Resposta (201 Created):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "username": "joao",
  "email": "joao@email.com"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "joao",
  "password": "senha123"
}
```

**Resposta (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "username": "joao",
  "email": "joao@email.com"
}
```

---

### Endpoints Protegidos (Requerem token JWT)

**Para todas as requisições abaixo, adicione o header:**
```
Authorization: Bearer {seu_token_aqui}
```

#### Criar Tarefa
```http
POST /api/tasks
Content-Type: application/json
Authorization: Bearer {token}

{
  "title": "Estudar Spring Boot",
  "description": "Revisar conceitos de segurança"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "title": "Estudar Spring Boot",
  "description": "Revisar conceitos de segurança",
  "status": "PENDING",
  "createdAt": "2024-12-11T15:30:00",
  "updatedAt": "2024-12-11T15:30:00"
}
```

#### Listar Todas as Tarefas
```http
GET /api/tasks
Authorization: Bearer {token}
```

#### Buscar Tarefa por ID
```http
GET /api/tasks/{id}
Authorization: Bearer {token}
```

#### Buscar por Status
```http
GET /api/tasks/status/{status}
Authorization: Bearer {token}
```
*Status possíveis: PENDING, IN_PROGRESS, COMPLETED*

#### Buscar por Título
```http
GET /api/tasks/search?title=estudar
Authorization: Bearer {token}
```

#### Atualizar Tarefa
```http
PUT /api/tasks/{id}
Content-Type: application/json
Authorization: Bearer {token}

{
  "title": "Estudar Spring Boot - Atualizado",
  "description": "Já terminei!",
  "status": "COMPLETED"
}
```

#### Deletar Tarefa
```http
DELETE /api/tasks/{id}
Authorization: Bearer {token}
```

---

## 🏗️ Arquitetura do Projeto
```
src/main/java/com/portfolio/todolist/
├── 📁 config/              # Configurações (Security, CORS, Swagger)
├── 📁 controller/          # Controllers REST
├── 📁 dto/                 # Data Transfer Objects
├── 📁 exception/           # Exceções personalizadas e handlers
├── 📁 mapper/              # Conversores entre entidades e DTOs
├── 📁 model/               # Entidades JPA
├── 📁 repository/          # Repositories (acesso ao banco)
├── 📁 security/            # Configurações de segurança e JWT
└── 📁 service/             # Lógica de negócio
```

### Fluxo de uma Requisição
```
Cliente (Postman/Frontend)
        ↓
   Controller (recebe requisição)
        ↓
   Service (aplica regras de negócio)
        ↓
   Repository (acessa banco de dados)
        ↓
   MySQL (persiste dados)
```

---

## 🔐 Segurança

### Autenticação JWT

1. Usuário faz login/registro
2. API retorna um token JWT válido por 24 horas
3. Cliente envia o token no header `Authorization: Bearer {token}` em cada requisição
4. API valida o token e identifica o usuário

### Proteções Implementadas

- ✅ Senhas criptografadas com BCrypt
- ✅ Tokens JWT com assinatura HMAC256
- ✅ Rotas protegidas por autenticação
- ✅ Cada usuário acessa apenas suas próprias tarefas
- ✅ Validação de dados de entrada
- ✅ Tratamento seguro de exceções
- ✅ CORS configurado

---

## 🧪 Testando a API

### Com Swagger (Recomendado)

1. Acesse: `http://localhost:8080/swagger-ui.html`
2. Registre um usuário em `/api/auth/register`
3. Copie o token retornado
4. Clique em **Authorize** (canto superior direito)
5. Cole o token e clique em **Authorize**
6. Agora você pode testar todos os endpoints!

### Com Postman

Importe a collection ou teste manualmente seguindo a documentação acima.

---

## 📊 Modelo de Dados

### User
```java
{
  "id": Long,
  "username": String (único),
  "email": String (único),
  "password": String (criptografada),
  "createdAt": LocalDateTime
}
```

### Task
```java
{
  "id": Long,
  "title": String,
  "description": String,
  "status": Enum (PENDING, IN_PROGRESS, COMPLETED),
  "user": User (relacionamento ManyToOne),
  "createdAt": LocalDateTime,
  "updatedAt": LocalDateTime
}
```

---

## 🎓 Aprendizados

Este projeto foi desenvolvido para consolidar conhecimentos em:

- ✅ Desenvolvimento de APIs RESTful
- ✅ Spring Boot e ecossistema Spring
- ✅ Autenticação e autorização com JWT
- ✅ Persistência de dados com JPA/Hibernate
- ✅ Boas práticas de código (Clean Code, SOLID)
- ✅ Arquitetura em camadas
- ✅ Tratamento de exceções
- ✅ Documentação de APIs
- ✅ Segurança de aplicações

---

## 🚀 Próximos Passos

- [ ] Adicionar paginação nas listagens
- [ ] Implementar filtros avançados
- [ ] Adicionar roles (USER, ADMIN)
- [ ] Criar testes unitários e de integração
- [ ] Deploy na nuvem (AWS, Heroku, Railway)
- [ ] Desenvolver frontend (React/Angular/Vue)
- [ ] Adicionar notificações por email
- [ ] Implementar cache com Redis

---

## 👨‍💻 Autor

**Rafael Mello**

- GitHub: [@rxzcodes](https://github.com/rxzcodes)
- LinkedIn: [Rafael Mello](https://www.linkedin.com/in/rafael-mello-503891393/)
- Email: rafaelf.mello07@gmail.com

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 🙏 Agradecimentos

Projeto desenvolvido como parte do portfólio de demonstração de habilidades em desenvolvimento backend com Java e Spring Boot.

---

**⭐ Se este projeto foi útil, considere dar uma estrela no repositório!**