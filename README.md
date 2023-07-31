Documentação de uma API de cadastro de medicamentos, médicos, pacientes desenvolvida em Java 17, Spring Boot.

A API esta documentada (Ainda em desenvolvimento) via Swagger




A API é protegida por Spring Security (JWT Token), onde precisa adicionar um usuário no banco de dados (cadastre um usuário via postman ou Swagger), para então com um usuário cadastrado fazer o login e gerar um token para utiliza-lo para autenticação nos endpoints (Atualmente esta desabilitado o token, ou seja os endpoints estão funcionando sem a necessidade de um token, se houver a necessidade um método está comentado para voltar a funcionalidade correta).

As funcionalidades estõ descritas no Swagger para mais detalhes!

CRUD - Cadastro, Atualização, Exclusão, e Listar

CRUD de remédios;

* Adicionar ou remover medicamentos;
* Exclusão direto ou lógica dependendo da regra de negócio, o medicamento recebe uma variável "Boolean active", onde fica 'ativa' na criação e pode ser passada para 'desativada' ;

CRUD de médicos;

* Exclusão direto ou lógica dependendo da regra de negócio, o medicamento recebe uma variável "Boolean active", onde fica 'ativa' na criação e pode ser passada para 'desativada' ;


CRUD de pacientes;

* Pacientes podem ser listados normalmente ou listados via Paginação por ordem alfabética

Cadastro de Usuários;

* A senha 'password' é encriptografada via 'BCryptPasswordEncoder' visando a segurança no banco;
* Os dados são salvos usando mascaras para salvar CPF, CNPF, CRM, email, telefone via Regex;

Ferramentas e Dependências:

* Java 17
* Spring Boot 3.0.6
* TokenJWT (Desabilitado atualmente)
* PostgreSQL
* Swagger
* Spring Security
* BCryptPasswordEncoder
* Maven 4.0.0
* Lombok
* JUnit / Mockito para testes unitários