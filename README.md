# Reservas de Salas (Spring Boot)

Aplicação backend para cadastro de salas, usuários e criação/cancelamento de reservas, com validação de conflitos de horário.

## Stack e dependências

- Java: 25 (configurado em `pom.xml`)
- Build: Maven
- Framework: Spring Boot
- Web: Spring MVC (`spring-boot-starter-web`)
- Persistência: Spring Data JPA + Hibernate (`spring-boot-starter-data-jpa`)
- Banco (dev): PostgreSQL (configurado via `application-dev.properties`)
- Banco (test): H2 em memória (configurado via `application-test.properties`)

## Arquitetura do projeto

Organização por camadas:

- `controller/`: endpoints REST (HTTP)
- `services/`: regras de negócio e transações
- `repository/`: acesso ao banco (Spring Data JPA)
- `entities/`: entidades JPA (tabelas e relacionamentos)
- `dto/`: objetos de entrada/saída da API (Request/Response) e mapeadores
- `exception/`: exceções de domínio e tratamento centralizado
- `enums/`: enums de domínio

## Domínio

### Sala

- Entidade: `entities/Sala`
- Tabela: `salas`
- Campos principais:
  - `id` (PK)
  - `nome` (único)
  - `capacidade`
  - `ativa` (define se pode receber reservas)

### Usuário

- Entidade: `entities/Usuario`
- Tabela: `usuarios`
- Campos principais:
  - `id` (PK)
  - `nome`
  - `email` (único)

### Reserva

- Entidade: `entities/Reserva`
- Tabela: `reservas`
- Relacionamentos:
  - `Reserva -> Sala` (ManyToOne)
  - `Reserva -> Usuario` (ManyToOne)
- Campos principais:
  - `id` (PK)
  - `inicio` / `fim` (intervalo)
  - `status` (`ATIVA` / `CANCELADA`)

#### Regras de negócio relevantes

- Sala precisa estar ativa para reservar.
- `inicio` e `fim` não podem ser nulos.
- `inicio` precisa ser anterior a `fim`.
- Conflito de reserva:
  - duas reservas conflitam se forem da mesma sala, estiverem ativas e seus intervalos se sobrepõem.
- Cancelamento:
  - uma reserva só pode ser cancelada se estiver `ATIVA`.

## Repositórios (acesso a dados)

### ReservaRepository

- `buscarConflitos(salaId, inicio, fim, status)`
  - Query JPQL que retorna reservas ativas que conflitam com o intervalo informado.

### UsuarioRepository

- `existsByEmail(email)`
- `findByEmail(email)`

### SalaRepository

- Repositório padrão do `JpaRepository` (CRUD e paginação).

## Services (camada de regras)

### SalaService

- CRUD básico de sala.
- Ao buscar por id inexistente, lança `ResourceNotFoundException`.

### UsuarioService

- CRUD com validação de duplicidade de e-mail:
  - criação: bloqueia e-mail já cadastrado
  - atualização: bloqueia se o e-mail pertencer a outro usuário

### ReservaService

- Criação de reserva:
  - valida conflito via `ReservaRepository.buscarConflitos(...)`
  - cria com status `ATIVA`
- Cancelamento:
  - busca reserva por id e chama `cancelar()`

## API (endpoints)

Base path padrão:

- `/api/v1`

### Salas

- `POST /api/v1/salas`
  - Body: `SalaRequestDTO`
  - Response: `SalaResponseDTO`
- `GET /api/v1/salas/{id}`
  - Response: `SalaResponseDTO`
- `DELETE /api/v1/salas/{id}`
  - Sem body

Exemplo de request:

```bash
curl -X POST "http://localhost:8080/api/v1/salas" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Sala 101",
    "capacidade": 12,
    "ativa": true
  }'
```

### Usuários

- `POST /api/v1/usuarios`
  - Body: `UsuarioRequestDTO`
  - Response: `UsuarioResponseDTO`

Exemplo:

```bash
curl -X POST "http://localhost:8080/api/v1/usuarios" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Ada Lovelace",
    "email": "ada@exemplo.com"
  }'
```

### Reservas

- `POST /api/v1/reservas`
  - Body: `ReservaRequestDTO` (usa `salaId`, `usuarioId`, `inicio`, `fim`)
  - Response: `ReservaResponseDTO`
- `PATCH /api/v1/reservas/{id}/cancelar`
  - Sem body
- `GET /api/v1/reservas?page=0&size=20&sort=id,desc`
  - Response: `Page<Reserva>`

Exemplo:

```bash
curl -X POST "http://localhost:8080/api/v1/reservas" \
  -H "Content-Type: application/json" \
  -d '{
    "salaId": 1,
    "usuarioId": 1,
    "inicio": "2026-05-12T10:00:00",
    "fim": "2026-05-12T11:00:00"
  }'
```

## DTOs e Mappers

- `dto/Sala/*`
  - `SalaRequestDTO` / `SalaResponseDTO`
  - `SalaMapper` converte entre DTO e Entity
- `dto/Usuario/*`
  - `UsuarioRequestDTO` / `UsuarioResponseDTO`
  - `UsuarioMapper` converte entre DTO e Entity
- `dto/Reserva/*`
  - `ReservaRequestDTO` / `ReservaResponseDTO`
  - `ReservaMapper` monta a resposta exibindo `nome da sala` e `nome do usuário`

## Tratamento de erros

Tratamento centralizado via `@RestControllerAdvice`:

- `ResourceNotFoundException` -> HTTP 404
- `BusinessException` -> HTTP 400

Formato padrão de erro:

```json
{
  "message": "Mensagem de erro"
}
```

## Configuração por perfil (Spring Profiles)

Perfil ativo por padrão:

- `spring.profiles.active=dev` em `application.properties`

### Dev (PostgreSQL)

Arquivo: `application-dev.properties`

Configura:

- URL JDBC do PostgreSQL
- credenciais
- `spring.jpa.hibernate.ddl-auto=update` (gera/atualiza schema)
- `spring.jpa.show-sql=true` (log de SQL)

Recomendação de segurança: não versionar credenciais reais em repositórios. Prefira variáveis de ambiente ou secrets do ambiente.

### Test (H2)

Arquivo: `application-test.properties`

- `jdbc:h2:mem:testdb`
- `ddl-auto=create-drop`

## Como rodar

### Subir o banco (dev)

- Garanta um PostgreSQL disponível e um banco criado (ex.: `reservas_alura_DB`).
- Ajuste `application-dev.properties` com as credenciais do seu ambiente.

### Compilar

```bash
mvn -DskipTests compile
```

### Executar

```bash
mvn spring-boot:run
```

## Observações técnicas (dívidas/ajustes)

- `SalaController` possui dois métodos com o mesmo mapeamento `GET /api/v1/salas/{id}`. Em runtime isso tende a causar erro de rota duplicada.
- O endpoint `GET /api/v1/reservas` atualmente retorna `Page<Reserva>` (entidade) e não um DTO paginado. Se a intenção for expor DTO, é comum mapear para `Page<ReservaResponseDTO>`.

