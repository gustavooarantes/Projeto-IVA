# Social Network Backend

Este é um backend de rede social similar ao X (antigo Twitter) desenvolvido em Java com Spring Boot.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.3
- Spring Security
- JWT para autenticação
- H2 Database (para desenvolvimento)
- Maven
- Lombok

## Estrutura do Projeto

O projeto está dividido em três módulos principais:

1. `common`: Contém entidades e utilitários compartilhados
2. `auth-service`: Gerencia autenticação e autorização
3. `user-service`: Gerencia operações relacionadas a usuários

## Funcionalidades

- Cadastro de usuários
- Login com JWT
- Refresh Token
- Perfil de usuário
- Busca de usuários
- Edição de informações do usuário

## Configuração

1. Clone o repositório
2. Configure o arquivo `application.yml` com suas configurações
3. Execute o projeto usando Maven:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Endpoints da API

### Autenticação

- `POST /api/auth/signup`: Cadastro de novo usuário
- `POST /api/auth/login`: Login
- `POST /api/auth/refresh`: Refresh token

### Usuários

- `GET /api/users/{id}`: Buscar usuário por ID
- `GET /api/users/search`: Buscar usuários por nome ou username
- `PUT /api/users/{id}`: Atualizar informações do usuário
- `DELETE /api/users/{id}`: Deletar usuário

## Segurança

- Autenticação JWT
- Senhas criptografadas com BCrypt
- Proteção contra CSRF
- Validação de dados
- Controle de acesso baseado em roles

## Banco de Dados

O projeto utiliza H2 Database em memória para desenvolvimento. Para produção, recomenda-se configurar um banco de dados mais robusto como PostgreSQL ou MySQL.

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request
