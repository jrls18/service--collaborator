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

CREATE TABLE IF NOT EXISTS usuario(
     cod_usuario BIGINT AUTO_INCREMENT NOT NULL
    ,nome VARCHAR(150) NOT NULL
	,cpf VARCHAR(14) NULL
	,rg VARCHAR(11) NULL
	,data_nascimento DATE NOT NULL
	,telefone_principal  VARCHAR(11) NOT NULL
	,telefone VARCHAR(11) NULL
	,email VARCHAR(150) UNIQUE NOT NULL
	,password VARCHAR(350)  NOT NULL
	,cep  VARCHAR(8)  NOT NULL
	,logradouro VARCHAR(255) NOT NULL
	,complemento VARCHAR(150) NULL
	,bairro VARCHAR(150) NOT NULL
	,localidade VARCHAR(150) NOT NULL
	,uf VARCHAR(2) NOT NULL
	,numero INT NOT NULL
	,data_cadastro DATETIME  NULL
	,cod_situacao INT NOT NULL
	,cod_empresa BIGINT NOT NULL
	,nome_image VARCHAR(250) NULL
	,FOREIGN KEY (cod_situacao) REFERENCES situacao(cod_situacao)
    ,PRIMARY KEY (cod_usuario)
);

CREATE TABLE IF NOT EXISTS usuarioacesso (
	 cod_usuario BIGINT  NOT NULL
	,cod_tipoacesso INT  NOT NULL
	,PRIMARY KEY (cod_usuario, cod_tipoacesso)
	,FOREIGN KEY (cod_usuario) REFERENCES usuario(cod_usuario)
	,FOREIGN KEY (cod_tipoacesso) REFERENCES tipoacesso(cod_tipoacesso)
);