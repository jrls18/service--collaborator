INSERT INTO situacao (descricao, data_cadastro)
VALUES('ATIVO', now())
     ,('BLOQUEADO', now())
     ,('CANCELADO', now())
     ,('PENDENTE VALIDAÇÃO DE EMAIL',now());

INSERT INTO tipoacesso(descricao)
VALUES('ROLE_COLABORADOR')
     ,('ROLE_USUARIO')
     ,('ROLE_ADM');

INSERT INTO AUTHENTICATED(application_name,client_id,client_secret,data_cadastro,sigla_app,cod_situacao)
VALUES('Desktop Develop Corporation'
      ,'89e7b6e5-a61d-4f67-b688-7b7449db096b'
      ,'343a5f53-6797-4930-82b7-1a96cb416ead'
      ,now()
      ,'DESKTOP_WINDOWS_APP'
      , 1
      );

INSERT INTO AUTHENTICATED(application_name,client_id,client_secret,data_cadastro,sigla_app,cod_situacao)
VALUES('br.com.developcorporation.management.client'
      ,'65a6d731-c72b-4304-8f0f-5a85b2bb0ccb'
      ,'43682c1d-8553-4c58-8a57-bb4f9bbd7524'
      ,now()
      ,'API_CLIENTE'
      , 1
      );

INSERT INTO AUTHENTICATED(application_name,client_id,client_secret,data_cadastro,sigla_app,cod_situacao)
VALUES('web-service-management-company'
      ,'c5fd2eeb-3f6e-4da7-b65f-0742f69f122b'
      ,'37d96516-48e6-4d37-8e1d-032e273cfc8c'
      ,now()
      ,'SITE_COMPANY'
      , 1
      );

INSERT INTO colaborador(nome,cpf_cnpj,data_nascimento,telefone_principal,telefone,email,senha,cep,logradouro,complemento,bairro,localidade,uf,numero,data_cadastro,cod_situacao,cod_empresa, ativacao )
VALUES('John Lenon Reis Santos'
      ,'39729649863'
      ,'2020-08-10'
      ,'11971210124'
      ,null
      ,'john.john16@hotmail.com'
      ,'$2a$10$UkxXFx37reoybrC9hbCYvu1doOvu4mQQFWL2QlKFy1QgjjNMcWYZO'
      ,'02542000'
      ,'Rua Epaminondas Melo do Amaral'
      ,null
      ,'Sítio do Mandaqui'
      ,'São Paulo'
      ,'SP'
      ,315
      ,now()
      ,1
      ,1
      ,'a751020d-7d7c-4849-9011-6d81bc6b4005'
      );

INSERT INTO colaboradoracesso (cod_colaborador , cod_tipoacesso)
                   VALUES (1,1);
