CREATE TABLE IF NOT EXISTS situacao(
     cod_situacao INT AUTO_INCREMENT NOT NULL
    ,descricao VARCHAR(150) UNIQUE NOT NULL
    ,data_cadastro DATETIME NOT NULL
    ,PRIMARY KEY (cod_situacao)
);

CREATE TABLE IF NOT EXISTS seguimento(
     cod_seguimento INT AUTO_INCREMENT NOT NULL
    ,descricao VARCHAR(150) UNIQUE NOT NULL
    ,data_cadastro DATETIME NOT NULL
    ,PRIMARY KEY (cod_seguimento)
);

CREATE TABLE IF NOT EXISTS grupo(
     cod_grupo BIGINT AUTO_INCREMENT NOT NULL
    ,descricao VARCHAR(150) UNIQUE NOT NULL
    ,data_cadastro DATETIME NOT NULL
    ,PRIMARY KEY (cod_grupo)
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

CREATE TABLE IF NOT EXISTS tiposistema(
     cod_tiposistema INT AUTO_INCREMENT NOT NULL
    ,descricao VARCHAR(255) UNIQUE NOT NULL
    ,PRIMARY KEY (cod_tiposistema)
);

CREATE TABLE IF NOT EXISTS empresa(
     cod_empresa BIGINT AUTO_INCREMENT NOT NULL
    ,razao_social VARCHAR(150) UNIQUE NOT NULL
    ,nome_fantasia VARCHAR(150) UNIQUE NOT NULL
	,cnpj VARCHAR(14) UNIQUE NOT NULL
	,inscricao_estadual VARCHAR(30) NULL
	,data_fundacao DATE NOT NULL
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
	,cod_grupo BIGINT NULL
	,cod_seguimento INT NOT NULL
	,cod_situacao INT NOT NULL
	,aceita_os_termos_de_uso bit default 0
	,nome_logotipo VARCHAR(250) NULL
	,cod_tiposistema INT NOT NULL
	,FOREIGN KEY (cod_grupo) REFERENCES grupo (cod_grupo)
	,FOREIGN KEY (cod_seguimento) REFERENCES seguimento(cod_seguimento)
	,FOREIGN KEY (cod_situacao) REFERENCES situacao(cod_situacao)
	,FOREIGN KEY (cod_tiposistema) REFERENCES tiposistema(cod_tiposistema)
    ,PRIMARY KEY (cod_empresa)
);
