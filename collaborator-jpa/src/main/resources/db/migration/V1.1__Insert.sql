INSERT INTO situacao (descricao, data_cadastro)
VALUES('ATIVO', now())
     ,('BLOQUEADO', now())
     ,('CANCELADO', now())
     ,('PENDENTE VALIDAÇÃO DE EMAIL',now());

INSERT INTO tipoacesso(descricao)
VALUES('ROLE_COLABORADOR')
     ,('ROLE_USUARIO')
     ,('ROLE_ADM');

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
