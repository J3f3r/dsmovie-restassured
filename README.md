# DSMovie - Testes de Integração com RestAssured

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![RestAssured](https://img.shields.io/badge/RestAssured-5.x-blue?style=flat-square)
![JUnit 5](https://img.shields.io/badge/JUnit-5-red?style=flat-square&logo=junit5)
![H2 Database](https://img.shields.io/badge/Database-H2-blueviolet?style=flat-square)

## 📌 Sobre o Projeto

O **DSMovie RestAssured** é uma aplicação Spring Boot focada na gestão e avaliação de filmes. O principal objetivo deste projeto é a implementação de uma suíte completa de **testes de integração automatizados para a API REST**, utilizando o framework **RestAssured** e o motor de testes **JUnit 5**.

A suíte de testes valida endpoints protegidos por autenticação/autorização (OAuth2/JWT), regras de negócio, tratamento de exceções e integridade de dados (operações de consulta, inserção e avaliação).

---

## 📐 Camada de Testes Implementada

A cobertura de testes foi dividida em duas suítes principais para garantir a validação rigorosa dos controladores REST:

### 1. `MovieControllerRA`
* **`GET /movies`**: Valida a listagem paginada de filmes com e sem parâmetros de busca por título (`HTTP 200`).
* **`GET /movies/{id}`**: Valida a busca por ID para cenários com ID existente (`HTTP 200`) e inexistente (`HTTP 404`).
* **`POST /movies`**:
  * Valida a criação de filmes quando autenticado com perfil `ADMIN`.
  * Valida erro de validação sintática ao enviar título em branco (`HTTP 422 Unprocessable Entity`).
  * Valida a restrição de acesso para perfil `CLIENT` (`HTTP 403 Forbidden`).
  * Valida o bloqueio de requisições sem autenticação ou com token inválido (`HTTP 401 Unauthorized`).

### 2. `ScoreControllerRA`
* **`PUT /scores`**:
  * Valida o lançamento de nota de avaliação de um filme por usuário autenticado.
  * Valida a tentativa de inserção de avaliação para um filme inexistente (`HTTP 404 Not Found`).
  * Valida erros de validação quando o ID do filme não é fornecido (`HTTP 422 Unprocessable Entity`).
  * Valida erros de validação quando a nota informada é menor que zero (`HTTP 422 Unprocessable Entity`).

---

## 🛠️ Tecnologias e Ferramentas

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3
* **Persistência de Dados:** Spring Data JPA / H2 Database (Banco em memória)
* **Segurança & Autenticação:** Spring Security / OAuth2 / JWT
* **Testes de Integração:** RestAssured, JUnit 5
* **Gerenciador de Dependências:** Maven (via Maven Wrapper)

---

## 🚀 Como Executar o Projeto Localmente

### Pré-requisitos
* Java Development Kit (JDK) 21 instalado.
* Git instalado.

### Passo a Passo

```bash
 1. Clonar o repositório
   git clone [https://github.com/J3f3r/dsmovie-restassured.git]      (https://github.com/J3f3r/dsmovie-restassured.git)

 2. Acessar a pasta do projeto
   cd dsmovie-restassured

 3. Executar a suíte completa de testes (Escolha o comando conforme seu terminal):

    Opção A: Linux / macOS / Git Bash
      ./mvnw clean test

    Opção B: Windows (Prompt de Comando - CMD)
      mvnw clean test

    Opção C: Windows (PowerShell)
      .\mvnw clean test

 4. Executar a aplicação localmente (Opcional - Acessível em http://localhost:8080)
    ./mvnw spring-boot:run
