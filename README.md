# API de Vendas - Desafio X-Brain

Este projeto é uma API RESTful desenvolvida em Spring Boot como solução para o desafio técnico do processo seletivo da X-Brain. A aplicação gerencia o cadastro e o relatório de vendas, seguindo as melhores práticas de desenvolvimento de software, com foco em uma arquitetura limpa e alta cobertura de testes.

## O Desafio Proposto

> Criar uma API usando Spring Boot, que faz parte da nossa stack.
> Utilizar um banco de dados em memória (HSQLDB ou H2)
> Não é necessário desenvolver frontend, apenas o backend.
>
> **Teste:**
> Desenvolver um serviço que seja capaz de gerar uma venda.
> Uma venda é composta por id, data da venda, valor, vendedor id e vendedor nome.
> Sua tarefa é desenvolver os serviços REST abaixo:
>
> *   Criar uma nova venda
> *   Retornar a lista de vendedores com os campos: nome, total de vendas do vendedor e média de vendas diária, conforme o período informado por parâmetro.
>
> **Instruções:**
> Gostaríamos de ver o histórico de seus commits.
> Vamos avaliar a maneira que você escreveu seu código, a solução apresentada e a cobertura de testes automatizados.

## Tecnologias Utilizadas

*   **Java 17**
*   **Spring Boot 3**
*   **Spring Web:** Para a criação dos endpoints REST.
*   **Spring Data JPA:** Para a persistência de dados.
*   **H2 Database:** Banco de dados em memória.
*   **Lombok:** Para reduzir código boilerplate (getters, setters, construtores).
*   **Gradle:** Para gerenciamento de dependências e build do projeto.
*   **JUnit 5 & Mockito:** Para a criação dos testes unitários.

## Estrutura do Projeto

O projeto foi organizado utilizando uma **arquitetura orientada a domínios** (ou *feature-sliced*), onde cada funcionalidade principal da aplicação possui seu próprio pacote com todas as camadas necessárias. Essa abordagem promove alta coesão e baixo acoplamento entre as funcionalidades.

-   **`venda`**: Contém todas as classes relacionadas à funcionalidade de Vendas (`controller`, `service`, `repository`, `dto`, `entity`).
-   **`vendedor`**: Contém todas as classes relacionadas à funcionalidade de Vendedores (`controller`, `service`, `repository`, `dto`, `entity`).
-   **`shared`**: Contém componentes compartilhados entre os diferentes domínios, como o `GlobalExceptionHandler` e exceções customizadas.

## Acessando a Documentação da API (Swagger UI)

Para facilitar a exploração e o teste dos endpoints, a API conta com a documentação interativa do Swagger UI.

Após iniciar a aplicação, acesse o seguinte endereço no seu navegador:
-   **http://localhost:8080/swagger-ui/index.html**

## Endpoints da API

A URL base da API é `http://localhost:8080`. Todos os endpoints estão documentados e podem ser testados interativamente através do Swagger UI.

### Vendas

#### 1. Criar uma nova venda

-   **Método:** `POST`
-   **URL:** `/api/v1/venda`
-   **Descrição:** Cadastra uma nova venda no sistema.
-   **Request Body:**

    ```json
    {
      "valor": 150.75,
      "idVendedor": 1
    }
    ```

-   **Resposta (201 Created):**

    ```json
    {
      "id": 1,
      "data": "2023-10-27",
      "valor": 150.75,
      "vendedorId": 1,
      "nomeCompleto": "Douglas Santos"
    }
    ```

-   **Resposta de Erro (400 Bad Request - Validação):**

    ```json
    {
      "erro": "valor deve ser maior que 0.01"
    }
    ```

#### 2. Listar todas as vendas

-   **Método:** `GET`
-   **URL:** `/api/v1/venda/`
-   **Descrição:** Retorna uma lista com todas as vendas não canceladas.
-   **Resposta (200 OK):**

    ```json
    [
      {
        "id": 1,
        "data": "2023-10-27",
        "valor": 150.75,
        "vendedorId": 1,
        "nomeCompleto": "Douglas Santos"
      }
    ]
    ```

#### 3. Buscar venda por ID

-   **Método:** `GET`
-   **URL:** `/api/v1/venda/{id}`
-   **Descrição:** Retorna uma venda específica pelo seu ID.
-   **Resposta (200 OK):**

    ```json
    {
      "id": 1,
      "data": "2023-10-27",
      "valor": 150.75,
      "vendedorId": 1,
      "nomeCompleto": "Douglas Santos"
    }
    ```

-   **Resposta de Erro (404 Not Found):**

    ```json
    {
      "erro": "Venda não encontrada para o id: 99"
    }
    ```

#### 4. Atualizar uma venda

-   **Método:** `PUT`
-   **URL:** `/api/v1/venda/{id}`
-   **Descrição:** Atualiza o valor ou o vendedor de uma venda existente.
-   **Request Body:**

    ```json
    {
      "valor": 180.00,
      "idVendedor": 1
    }
    ```

-   **Resposta (200 OK):**

    ```json
    {
      "id": 1,
      "data": "2023-10-27",
      "valor": 180.00,
      "vendedorId": 1,
      "nomeCompleto": "Douglas Santos"
    }
    ```

#### 5. Cancelar uma venda

-   **Método:** `DELETE`
-   **URL:** `/api/v1/venda/{id}`
-   **Descrição:** Realiza o cancelamento lógico de uma venda.
-   **Resposta:** `204 No Content`

### Relatórios

#### 1. Relatório de Vendas por Vendedor

-   **Método:** `GET`
-   **URL:** `/api/v1/venda/relatorio/?inicio=dd/MM/yyyy&fim=dd/MM/yyyy`
-   **Descrição:** Retorna um relatório com o total de vendas e a média diária de vendas para cada vendedor, dentro de um período especificado.
-   **Exemplo de URL:** `/api/v1/venda/relatorio/?inicio=01/10/2023&fim=27/10/2023`
-   **Resposta (200 OK):**

    ```json
    [
      {
        "id": 1,
        "vendedor": "Douglas Santos",
        "quantidade": 50,
        "media": 1.8518
      }
    ]
    ```

-   **Resposta de Erro (400 Bad Request):**

    ```json
    {
      "erro": "Data inicial informada é posterior a data final"
    }
    ```

## Como Executar o Projeto

**Pré-requisitos:**
-   Java 17 ou superior

1.  **Clone o repositório:**
    
    ```sh
    git clone https://github.com/seu-usuario/seu-repositorio.git
    ```

2.  **Navegue até o diretório do projeto:**
    
    ```sh
    cd X-brain
    ```

3.  **Execute a aplicação com o Gradle:**
    
    ```sh
    ./gradlew bootRun
    ```

    A aplicação será iniciada. Acesse a documentação em http://localhost:8080/swagger-ui/index.html.

## Como Executar os Testes

Para garantir a qualidade e o correto funcionamento da aplicação, execute a suíte de testes unitários com o seguinte comando:

```sh
./gradlew test
```

Este comando executará todos os testes unitários do projeto, localizados em `src/test/java`, e gerará um relatório de cobertura no diretório `build/reports/tests/test/`.
