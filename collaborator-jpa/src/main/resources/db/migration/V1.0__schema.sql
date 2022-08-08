CREATE TABLE IF NOT EXISTS situacao(
     cod_situacao INT AUTO_INCREMENT NOT NULL
    ,descricao VARCHAR(150) UNIQUE NOT NULL
    ,data_cadastro DATETIME NOT NULL
    ,PRIMARY KEY (cod_situacao)
);

CREATE TABLE IF NOT EXISTS authorization(
     cod_authorization BIGINT AUTO_INCREMENT NOT NULL
    ,application_name VARCHAR(150) UNIQUE NOT NULL
    ,sigla_app VARCHAR(20) UNIQUE NOT NULL
    ,client_id VARCHAR(40) UNIQUE  NOT NULL
    ,client_secret VARCHAR(40) UNIQUE  NOT NULL
    ,data_cadastro DATETIME NOT NULL
    ,cod_situacao INT NOT NULL
    ,FOREIGN KEY (cod_situacao) REFERENCES situacao(cod_situacao)
    ,PRIMARY KEY (cod_authorization )
);

CREATE TABLE IF NOT EXISTS tipoacesso(
     cod_tipoacesso INT AUTO_INCREMENT NOT NULL
    ,descricao VARCHAR(255) UNIQUE NOT NULL
    ,PRIMARY KEY (cod_tipoacesso)
);

CREATE TABLE IF NOT EXISTS colaborador(
   cod_colaborador BIGINT AUTO_INCREMENT NOT NULL
  ,nome VARCHAR(150) NOT NULL
  ,data_nascimento DATE NOT NULL
  ,senha VARCHAR(350)  NOT NULL
  ,tipo_usuario VARCHAR(150) NOT NULL
  ,cod_empresa BIGINT NOT NULL
  ,cpf_cnpj VARCHAR(14) NULL
  ,email VARCHAR(150) UNIQUE NOT NULL
	,telefone_principal  VARCHAR(11) NOT NULL
	,telefone VARCHAR(11) NULL
	,cep  VARCHAR(8)  NOT NULL

	,logradouro VARCHAR(255) NOT NULL
	,complemento VARCHAR(150) NULL
	,bairro VARCHAR(150) NOT NULL
	,localidade VARCHAR(150) NOT NULL
	,uf VARCHAR(2) NOT NULL
	,numero INT NOT NULL
	,data_cadastro DATETIME  NULL
	,cod_situacao INT NOT NULL
	,nome_image VARCHAR(250) NULL
	,FOREIGN KEY (cod_situacao) REFERENCES situacao(cod_situacao)
    ,PRIMARY KEY (cod_colaborador)
);

CREATE TABLE IF NOT EXISTS colaboradoracesso (
	 cod_colaborador BIGINT  NOT NULL
	,cod_tipoacesso INT  NOT NULL
	,PRIMARY KEY (cod_colaborador, cod_tipoacesso)
	,FOREIGN KEY (cod_colaborador) REFERENCES colaborador(cod_colaborador)
	,FOREIGN KEY (cod_tipoacesso) REFERENCES tipoacesso(cod_tipoacesso)
);