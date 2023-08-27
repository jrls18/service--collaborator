## Web Service Rest Gestão de Colaborador
#### Microservice responsável por gerenciar o cadastro de colaborador, com esse cadastro é possível realizar a consulta, alteração e consulta paginadas.

### Microservice Spring boot Java.
Abaixo encontra-se o fluxo da API Rest.  <br />
![desenhoMacro.png](documents%2Fimg%2FdesenhoMacro.png) <br /> <br />

### Informações da api.
| APP                   | Java  | Base de Dados [MYSQL] |
|-----------------------|-------|-----------------------|
| SERVICE--COLLABORATOR | 20    | dbCollaborator        | 

### Ambientes
| Ambiente | URL Base                                  | Client Id | Client Secret | Correlation Id |
|----------|-------------------------------------------|-----------|---------------|----------------|
| LOCAL    | http://localhost:5000                     | [S]       | [S]           | [S]            |
| DEV      | http://cloud.dev.develop.corporation.com  | [S]       | [S]           | [S]            |
| HML      | http://cloud.hml.develop.corporation.com  | [S]       | [S]           | [S]            |
| PROD     | http://cloud.prd.develop.corporation.com  | [S]       | [S]           | [S]            |

Para todos os ambientes o requisitante deve informar a **CLIENT_ID** e **CLIENT_SECRET** e deve possuir o cadastro na tabela de autorização.

### Chaves de Acesso (HEADERs).
| Chaves de acesso   | Tipo                 | Obrigatório |
| ----------------   | -------------------- | ----------- |
| client_id          | UUID (String)        |     [S]     | 
| client_secret      | UUID (String)        |     [S]     |
| correlation_id     | UUID (String)        |     [N]     |

### Status Code
* **500 - INTERNAL SERVER ERROR**
* **200 - OK** <br />
  Utilizado somente para os casos de consulta.
* **202 - ACCEPTED** <br />
  Utilizado somente para os casos de alteração
* **201 - CREATED** <br />
* **401 - SC UNAUTHORIZED** <br />
* **400 - BAD REQUEST** <br />
  Utilizado somente para os casos de erros de campos com dados incorretos. Ex: Código é um inteiro e você manda um alfanúmerico resultado erro.
* **422 - UNPROCESSABLE ENTITY** <br />
  Utilizado somente para casos de erros de campos com dados de negocio não válidos. Ex: Código Situação aceita 1,2 e 3 e você me manda um 4 resultado erro.

### Exemplo de mensagens de erros

* **500 - INTERNAL SERVER ERROR**
```json
   {
      "codigo": "500",
      "mensagem": "Houve um erro interno tente novamente mais tarde."
   }
```
* **401 - SC UNAUTHORIZED** <br />
```json
   {
      "timestamp": "2021-08-22T15:21:24.850+00:00",
      "status": 401,
      "error": "Unauthorized",
      "message": "",
      "path": "/api/develop_corporation/gestao_empresarial/situacao/v1/alter"
   }
```
* **400 - BAD REQUEST** <br />
* **422 - UNPROCESSABLE ENTITY** <br />
  Para os códigos 400 e 422 a estrutura é a mesma.
```json
   {
      "codigo": "",
      "mensagem": "",
      "detalhes": [
         {
           "campo": "",
           "mensagem": "",
           "valor": ""
         },
         {
           "campo": "",
           "mensagem": "",
           "valor": ""
         }
      ]
   }
```

### Chamadas das Api's (Collection de Exemplo)
Em **collaborator-api-rest/resources/postman** existe 3 arquivos um chamado **service--collaborator.postman_collection.json**
, **service--collaborator-env-dev.postman_environment.json** e o **service--collaborator-env-local.postman_environment.json**.


### Environment
| Name                  | Value                                                                           |
|-----------------------|---------------------------------------------------------------------------------|
| Java                  | 20.0.2                                                                          |
| -cp                   | service--collaborator-starter                                                   |
| Main Class            | br.com.group.developer.corporation.service.collaborator.starter.App             |
| VM options            | -Dspring.profiles.active=local -Dserver.port=5000 -Dmanagement.server.port=1000 |
| Environment Variables | ASYNC=true                                                                      | 
**OBS** Para liberar a controller deve-se deixar o  **ASYNC** com false ex: **ASYNC=false**


### Falando sobre os arquivos:
* **service--collaborator.postman_collection.json** <br />
  Arquivo representa todas as api's com seus respectivos objetos para fins de testes.

* **service--collaborator-env-dev.postman_environment.json** e **service--collaborator-env-local.postman_environment.json** <br />
  Arquivo de configuração dos ambientes (LOCAL,DES,HOM e PROD).

### Imports dos arquivos no POSTMAN
Para importar os arquivos de teste segue o link do WIKI do projeto:
[Wiki de configuração do Postman](https://gitlab.com/software-engineer2/web-service-rest-api-management-company/-/wikis/Import-Postman)
<br />

### Log de entrada e saída (Monitoração)
Todas as entradas e saídas são logadas para monitoração de request (500, 200, 201, 400, 404, e outros). <br />
Estrutura do log.
```json
  {
  "request": {
    "correlationId": "eb3a4630-cbf6-4e2a-8bf9-5355ad6e5a3b",
    "applicationName": "service--collaborator",
    "method": "POST",
    "requestUri": "/service--collaborator/api/colaborador/v1/save",
    "requestUriParameters": {},
    "requestUriHeaders": {
      "content-length": "603",
      "postman-token": "dd9e464e-e458-4fd2-95b5-474cc9a67ab6",
      "client_id": "##########",
      "accept": "*/*",
      "authorization": "##########",
      "host": "localhost:5000",
      "correlation_id": "eb3a4630-cbf6-4e2a-8bf9-5355ad6e5a3b",
      "content-type": "application/json",
      "connection": "keep-alive",
      "client_secret": "##########",
      "cache-control": "no-cache",
      "accept-encoding": "gzip, deflate, br",
      "user-agent": "PostmanRuntime/7.32.3"
    },
    "requestPathVariable": {},
    "payload": {
      "codigo": null,
      "nome": "Paulo ###### filho",
      "cpf_cnpj": "291###87###60",
      "data_nascimento": "2020-08-10",
      "contato": {
        "telefone": null,
        "email": "##########",
        "telefone_principal": "116###1##14"
      },
      "endereco": {
        "uf": "SP",
        "complemento": null,
        "numero": "##########",
        "logradouro": "Rua ########### #### ## Amaral",
        "bairro": "Sítio do Mandaqui",
        "localidade": "São Paulo",
        "cep": "0254####"
      },
      "password": null,
      "tipo_usuario": {
        "codigo": "3"
      },
      "situacao": null,
      "imagem": null,
      "codigo_empresa": "##########"
    },
    "exception": "",
    "exceptionDetails": null,
    "instance": "John",
    "info": null,
    "statusCode": null,
    "message": null,
    "date": "2023-08-27 16:36:37"
  }
}
```

### Para acessar a base de dados local h2

| URL                              | DataBase       | Usuário | Senha  | JDBC_URL                                          | Driver        |
|----------------------------------|----------------|---------|--------|---------------------------------------------------|-------------- |
| http://localhost:5000/h2-console | dbCollaborator | sa      | 171513 | jdbc:h2:mem:dbCollaborator;DB_CLOSE_ON_EXIT=FALSE | org.h2.Driver |


### Deployment Services
kubectl delete deploy service--collaborator -n services-develop-corporation
kubectl delete deploy service--collaborator-consumer -n services-develop-corporation


### Features Finalizadas
- Collaborator Synchronous
- [x] Save
- [x] Update
- [x] Get By I'd
- [x] Get Profile
- [x] Put Validated Email
- [x] Get All Type Collaborator
- [x] Login
- [x] Get Collaborator Pagination
- [x] Produce Message Notification
- [x] Produce Message Documents


- Collaborator Asynchronous
- [x] Save Consumer
- [x] Produce Retry
- [x] Produce dlt
- [x] Dlt Consumer
- [x] Produce Message Notification


- Status Synchronous
- [x] Get All Status


- Authorization
- [x] Get All Authorization
- [x] Save New Authorization
- [x] Get Authorization by App
- [x] Update Authorization
- [ ]  Create Service--authorization


- Technical Debts
- [x] Sonar Correction
- [x] Logs
- [ ] Remove EndPoints Authorization
- [ ] Unit Test
- [ ] BDD Automated Test
