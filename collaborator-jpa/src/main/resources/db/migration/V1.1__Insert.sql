INSERT INTO situacao (descricao, data_cadastro)
VALUES('ATIVO', now())
     ,('BLOQUEADO', now())
     ,('CANCELADO', now())
     ,('FINALIZADO', now())
     ,('EM PROCESSAMENTO', now());


INSERT INTO seguimento (descricao, data_cadastro)
VALUES('Pizzaria e Esfiharia', now())
     ,('Padaria e Lanchonetes', now())
     ,('Mercado', now())
     ,('Lojas de Utensilios domestico', now());

INSERT INTO tiposistema(descricao)
VALUES('BASIC')
     ,('PROD')
     ,('MID')
     ,('DESKTOP');

INSERT INTO authorization(application_name,client_id,client_secret,data_cadastro,sigla_app,cod_situacao)
VALUES('Desktop Develop Corporation'
      ,'89e7b6e5-a61d-4f67-b688-7b7449db096b'
      ,'343a5f53-6797-4930-82b7-1a96cb416ead'
      ,now()
      ,'DESKTOP_WINDOWS_APP'
      , 1
      );


INSERT INTO authorization(application_name,client_id,client_secret,data_cadastro,sigla_app,cod_situacao)
VALUES('br.com.developcorporation.management.client'
      ,'65a6d731-c72b-4304-8f0f-5a85b2bb0ccb'
      ,'43682c1d-8553-4c58-8a57-bb4f9bbd7524'
      ,now()
      ,'API_CLIENTE'
      , 1
      );

INSERT INTO authorization(application_name,client_id,client_secret,data_cadastro,sigla_app,cod_situacao)
VALUES('web-service-management-company'
      ,'c5fd2eeb-3f6e-4da7-b65f-0742f69f122b'
      ,'37d96516-48e6-4d37-8e1d-032e273cfc8c'
      ,now()
      ,'SITE_COMPANY'
      , 1
      );

INSERT INTO empresa(razao_social,nome_fantasia,cnpj,inscricao_estadual,data_fundacao,telefone_principal,telefone,cep,logradouro,complemento,bairro,localidade,uf,numero,data_cadastro,cod_seguimento,cod_situacao,aceita_os_termos_de_uso, cod_tiposistema )
VALUES('Rede Espaço Saboroso'
      ,'Rede Espaço Saboroso Unidade Epaminondas'
      ,'37738811000102'
      , null
      ,'2020-08-10'
      ,'1126194315'
      ,'1126194318'

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
      ,true
      ,1
      );
