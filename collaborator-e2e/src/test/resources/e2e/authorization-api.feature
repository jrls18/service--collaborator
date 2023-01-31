# language: pt
@geral @authorization
Funcionalidade: Executando os testes da autorização.

  Cenario: 01 - Ao fazer uma requisição de cadastro de uma nova autorização com um usuário logado com todos os campos de autorização preenchida de forma correta é sucesso.
    Dado usuario preencheu o formulario com as seguintes informacoes
      | username                | password    |
      | john.john16@hotmail.com | @@HMNXpT00  |
    E as credenciais do sistema sendo preenchida de forma automatica
    Quando o formulario foi preenchido por completo o usuario solicitou uma chamada de autenticacao
    Entao o status code da chamada deve ser "OK"
    E apos fazer o login deve-se recuperar o token gerado no campo "access_token"
    E o usuario preencheu o formulado de "autenticacao" com os seguintes atributos
      | application_name                             | sigla_app   |
      | br.com.developcorporation.management.client-RandomText | API CLIENTE-RandomText |
    E adicionou nos headers base o autorization com o token
    Quando o fomulario foi preenchido por completo o usuriario solicitou uma chamada de "cadastrar" de uma nova autorizacao
    Entao o status code da chamada deve ser "created"
    E no body da resposta deve conter as seguintes informacoes
      | codigo   | 201                                 |
      | mensagem | Autorização cadastrada com sucesso. |
