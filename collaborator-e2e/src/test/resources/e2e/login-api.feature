# language: pt
@geral @login
Funcionalidade: Realizando a autenticação do usuário.

  Cenario: Ao fazer uma requisição de login com os dados válidos é sucesso.
    Dado usuario preencheu o formulario com as seguintes informacoes
      | username                | password    |
      | john.john16@hotmail.com | @@HMNXpT00  |
    E as credenciais do sistema sendo preenchida de forma automatica
    Quando o formulario foi preenchido por completo o usuario solicitou uma chamada de autenticacao
    Entao o status code da chamada deve ser "OK"
    E no body da resposta deve conter os seguintes atributos de autenticacao
      | access_token | eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huLmpvaG4xNkBob3RtYWlsLmNvbSIsImlhdCI6MTY3MDk1MjU3MiwiZXhwIjoxNjcxMDM4OTcyfQ.STr52-jOx9Vw-2k_UH3PbABBomCfsjECcnEFhyvC0Wb4XYsM4WBs7buwbUok577uL6dqG_1X8NAeAb5ofi0ucQ |
      | token_type   | Bearer                        |
      | expires_in   | 2022-12-14T17:29:32.427+00:00 |
