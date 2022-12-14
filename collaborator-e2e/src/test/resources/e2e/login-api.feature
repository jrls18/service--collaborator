# language: pt
@geral @login
Funcionalidade: Realizando a autenticação do usuário.

  Cenario: 01 - Ao fazer uma requisição de login com os dados válidos é sucesso.
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

  Cenario: 02 - Ao fazer uma requisição de login com o username do usuario que não existe cadastrado então deve retornar uma mensagem de erro.
    Dado usuario preencheu o formulario com as seguintes informacoes
      | username    | password    |
      | john.john16 | @@HMNXpT00  |
    E as credenciais do sistema sendo preenchida de forma automatica
    Quando o formulario foi preenchido por completo o usuario solicitou uma chamada de autenticacao
    Entao o status code da chamada deve ser "unauthorized"
    E no body da resposta deve conter as seguintes informacoes
      | codigo   | 401                        |
      | mensagem | Usuario ou senha inválido. |

  Cenario: 03 - Ao fazer uma requisição de login com o username do usuario com número de telefone inválido então deve retornar uma mensagem de erro.
    Dado usuario preencheu o formulario com as seguintes informacoes
      | username    | password    |
      | 11          | @@HMNXpT00  |
    E as credenciais do sistema sendo preenchida de forma automatica
    Quando o formulario foi preenchido por completo o usuario solicitou uma chamada de autenticacao
    Entao o status code da chamada deve ser "bad_request"
    E no body da resposta deve conter as seguintes informacoes
      | codigo   | 400                                       |
      | mensagem | Existe erro(s) no(s) campo(s) do usuario. |
      | campo0    | username                                                     |
      | mensagem0 | Usuario deve ser informado um número de telefone ou um email.|
      | valor0    | 11                                                           |

  Cenario: 04 - Ao fazer uma requisição de login com o password do usuario maior que 150 caracteres então deve retornar uma mensagem de erro.
    Dado usuario preencheu o formulario com as seguintes informacoes
      | username                | password    |
      | john.john16@hotmail.com | @@HMNXpT0021lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll  |
    E as credenciais do sistema sendo preenchida de forma automatica
    Quando o formulario foi preenchido por completo o usuario solicitou uma chamada de autenticacao
    Entao o status code da chamada deve ser "bad_request"
    E no body da resposta deve conter as seguintes informacoes
      | codigo    | 400                                       |
      | mensagem  | Existe erro(s) no(s) campo(s) do usuario. |
      | campo0    | password                                                     |
      | mensagem0 | Senha deve conter no mínimo 5 caracteres e no máximo 150.    |
      | valor0    | @@HMNXpT0021lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll                                                           |

  Cenario: 05 - Ao fazer uma requisição de login com o password do usuario menor que 05 caracteres então deve retornar uma mensagem de erro.
    Dado usuario preencheu o formulario com as seguintes informacoes
      | username                | password    |
      | john.john16@hotmail.com | @@3  |
    E as credenciais do sistema sendo preenchida de forma automatica
    Quando o formulario foi preenchido por completo o usuario solicitou uma chamada de autenticacao
    Entao o status code da chamada deve ser "bad_request"
    E no body da resposta deve conter as seguintes informacoes
      | codigo    | 400                                       |
      | mensagem  | Existe erro(s) no(s) campo(s) do usuario. |
      | campo0    | password                                                     |
      | mensagem0 | Senha deve conter no mínimo 5 caracteres e no máximo 150.    |
      | valor0    | @@3                                                          |

  Cenario: 06 - Ao fazer uma requisição de login com o password do usuário incorreto então deve retornar uma mensagem de erro.
    Dado usuario preencheu o formulario com as seguintes informacoes
      | username                | password      |
      | john.john16@hotmail.com | @@HMNXpT0021  |
    E as credenciais do sistema sendo preenchida de forma automatica
    Quando o formulario foi preenchido por completo o usuario solicitou uma chamada de autenticacao
    Entao o status code da chamada deve ser "unauthorized"
    E no body da resposta deve conter as seguintes informacoes
      | codigo   | 401                        |
      | mensagem | Usuario ou senha inválido. |

  Cenario: 07 - Ao fazer uma requisição de login com o username e password do usuário com espaço então deve retornar uma mensagem de erro.
    Dado usuario preencheu o formulario com as seguintes informacoes
      | username | password |
      | &nbsp:   | &nbsp:   |
    E as credenciais do sistema sendo preenchida de forma automatica
    Quando o formulario foi preenchido por completo o usuario solicitou uma chamada de autenticacao
    Entao o status code da chamada deve ser "bad_request"
    E no body da resposta deve conter as seguintes informacoes
      | codigo    | 400                                                           |
      | mensagem  | Existe erro(s) no(s) campo(s) do usuario.                     |
      | campo0    | username                                                      |
      | mensagem0 | Usuario deve ser informado um número de telefone ou um email. |
      | valor0    | &nbsp:                                                        |
      | campo1    | password                                                      |
      | mensagem1 | Senha deve conter no mínimo 5 caracteres e no máximo 150.     |
      | valor1    | &nbsp:                                                        |

  Cenario: 08 - Ao fazer uma requisição de login com o username e password do usuário com vazio então deve retornar uma mensagem de erro.
    Dado usuario preencheu o formulario com as seguintes informacoes
      | username | password |
      | empty    | empty   |
    E as credenciais do sistema sendo preenchida de forma automatica
    Quando o formulario foi preenchido por completo o usuario solicitou uma chamada de autenticacao
    Entao o status code da chamada deve ser "bad_request"
    E no body da resposta deve conter as seguintes informacoes
      | codigo    | 400                                                           |
      | mensagem  | Existe erro(s) no(s) campo(s) do usuario.                     |
      | campo0    | username                                                      |
      | mensagem0 | Informe o usuário de acesso.                                  |
      | campo1    | username                                                      |
      | mensagem1 | Usuario deve ser informado um número de telefone ou um email. |
      | campo2    | password                                                      |
      | mensagem2 | Informe a senha de acesso.                                    |
      | campo3    | password                                                      |
      | mensagem3 | Senha deve conter no mínimo 5 caracteres e no máximo 150.     |
