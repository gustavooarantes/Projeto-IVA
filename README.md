# Social Network Backend

Este é um backend de rede social similar ao X (antigo Twitter) desenvolvido em Java com Spring Boot.

## Desenvolvedores
Gustavo Oliveira Arantes, Leandro Carrijo, Luciana Freitas e Beatriz Freitas

## Tecnologias Utilizadas

- Java 17
- Mockito (para testes unitários)
- Spring Boot 3.2.3
- Spring Security e Security Test
- Spring Data JPA
- Spring Starter AMQP (para RabbitMQ)
- JWT para autenticação
- H2 Database (para desenvolvimento)
- Maven
- Lombok
- Devtools

## Estrutura do Projeto

O projeto está dividido em 5 módulos principais:

1. `common`: Contém entidades e utilitários compartilhados
2. `auth-service`: Gerencia autenticação e autorização
3. `user-service`: Gerencia operações relacionadas a usuários
4. `message-service`: Gerencia as configurações, segurança e endpoints referentes ao producer e consumer das mensagens
5. `contract-service`: DTO para garantir baixo acoplamento entre os serviços Producer e Consumer

## Funcionalidades

- Cadastro de usuários
- Login com JWT
- Refresh Token
- Perfil de usuário
- Busca de usuários
- Edição de informações do usuário
- Serviço de Mensagens entre usuários

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

### Mensagens
- `POST /api/messages`: Envia mensagens para o RabbitMQ
- `GET /api/messages/chat/{user1}/{user2}`: Busca o chat entttre os dois usuários
- `RabbitMQ`: uso de exhanges e rounting keys para controle e direcionamento correto de mensagens nas filas.
- `SecurityConfig`: proteção contra CSRF e exigência de autenticação para enviar as requisições HTTP destes serviços especificamente estão desativadas.

## Segurança

- Autenticação JWT
- Senhas criptografadas com BCrypt
- Proteção contra CSRF
- Validação de dados
- Controle de acesso baseado em roles

## Testes
Os testes unitários foram implementados (em quase sua totalidade) utilizando Mockito para testes de cenários. Nenhum erro foi retornado (possível verificar isto ao executar o comando `mvn clean install` no diretório pai do projeto.

## Banco de Dados

O projeto utiliza H2 Database em memória para desenvolvimento. Para produção, recomenda-se configurar um banco de dados mais robusto como PostgreSQL ou MySQL.

## Contribuição
### Lembre-se: utilize a boa prática de commits atômicos, criando uma branch para cada funcionalidade e/ou correção de erros que for implementada.

1. Faça um fork do projeto
2. Clone o repositório para o seu computador (`git clone <url_do_fork>`)
3. Entre na main branch (`git checkout main`)
4. Atualize a main branch do repositório antes de iniciar (`git pull origin main`)
5. Crie uma branch para sua feature e mude para ela (`git checkout -b feature/sua-feature`)
6. Prepare suas mudanças (`git add .`)
7. Commit suas mudanças com uma boa descrição (`git commit -m 'Descrição`)
8. Mude para a main branch novamente (`git checkout main`)
9. Atualize-a novamente para evitar possíveis conflitos (`git pull origin main`)
10. Mude para a sua branch novamente (`git checkout feature/sua-feature`)
11. Faça o merge da main branch com a sua branch (`git rebase main`)
12. Push para a branch (`git push origin feature/sua-feature`)
13. Abra um Pull Request e atualize o status da task para "In Review" no JIRA

=======
# Projeto-IVA
Projeto Integrador IV-A - Análise e Desenvolvimento de Sistemas (PUC Goiás) 2025/1
>>>>>>> f87527eff1ce98da4ac7fe218a5bd701f5ad8994
