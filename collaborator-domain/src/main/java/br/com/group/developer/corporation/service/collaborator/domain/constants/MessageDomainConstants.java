package br.com.group.developer.corporation.service.collaborator.domain.constants;


public final class MessageDomainConstants {

    private MessageDomainConstants(){
    }

    public static final String USERNAME_INFORMADO_NAO_EXISTE_CADASTRADO = "O username informa não existe cadastrado.";
    public static final String USERNAME_E_OBRIGATORIO = "O campo username é obrigatório.";

    public static final String USERNAME_ALTERADO_COM_SUCESSO = "Senha alterada com sucesso! Em breve sua senha será enviada para o email cadastrado.";

    public static final String TIVEMOS_UM_ERRO_AO_CHAMAR_O_SERVICE_DOCUMENT_POR_FAVOR_TENTE_NOVAMENTE_MAIS_TARDE = "Tivemos um erro ao chamar o service--document por favor tente novamente mais tarde!";
    public static final String TIVEMOS_UM_ERRO_AO_CHAMAR_O_SERVICE_COMPANY_POR_FAVOR_TENTE_NOVAMENTE_MAIS_TARDE = "Tivemos um erro ao chamar o service--company por favor tente novamente mais tarde!";
    public static final String USUARIO_OU_SENHA_INVALIDO = "Usuario ou senha inválido.";
    public static final String TOKEN_TWT_INVALIDO_MENSAGEM = "Token JWT inválido -> Mensagem: {}";
    public static final String NAO_E_POSSIVEL_DEFINIR_A_AUTENTICACAO_DO_USUARIO_MENSAGEM = "NãO é possível definir a autenticação do usuário -> Mensagem: {}";
    public static final String CODIGO_DEVE_CONTER_NO_MINIMO_1_CARACTERE_E_NO_MAXIMO_8 = "Código deve ser um número válido e conter no minimo 1 caracter e no máximo 8.";
    public static final String DATA_NASCIMENTO_E_OBRIGATORIO = "Data nascimento é obrigatório.";
    public static final String DATA_NASCIMENTO_DEVE_ESTA_NO_FORMATO_ANO_MES_DIA_OU_ANO_MES_DIA_HORAS_MINUTOS_E_SEGUNDO = "Data nascimento deve está no formato (ano mes dia) ou (ano mes dia horas minutos e segundo).";
    public static final String PASSWORD_DEVE_SER_NULLO = "Password deve ser nulo.";
    public static final String PREENCHA_OS_DADOS_DE_CONTATO = "Por favor preencha os dados de contato.";
    public static final String USUARIO_CADASTRODO_COM_SUCESSO = "Colaborador cadastrado com sucesso.";
    public static final String TIPO_DE_COLABORADOR_INVALIDO = "Tipo de colaborador inválido.";
    public static final String ID_COMPANY_NAO_DEVE_SER_PREENCHIDO = "O codigo_empresa não deve ser preenchido.";
    public static final String CODIGO_E_OBRIGATORIO = "Codigo é obrigatório.";
    public static final String CODIGO_DA_SITUACAO_NAO_EXISTE_CADASTRADO = "Código da situação não existe cadastrado.";
    public static final String PASSWORD_NAO_PODE_SER_NULO = "Password não pode ser nulo.";
    public static final String PASSWORD_DEVE_CONTER_NO_MINIMO_8_CARACTERES_E_MAXIMO_15_CARACTERES = "Password deve conter no mínimo 8 caracteres e máximo 15 caracteres.";
    public static final String COLLABORADOR_NAO_EXISTE_CADASTRO = "Não existe um colaborador para o código informado.";
    public static final String PREENCHA_O_FORMULARIO_DE_CADASTRO_DA_NOVO_COLABORADOR = "Preencha o formulário de cadastro da novo colaborador.";
    public static final String PREENCHA_O_FORMULARIO_DE_ALTERACAO_DO_COLABORADOR = "Preencha o formulário de alteração do colaborador.";
    public static final String CODIGO_NAO_DEVE_SER_PREENCHIDO_NO_FORMULARIO_DE_CADASTRO = "Código não deve ser preenchido no formulario de cadastro.";
    public static final String OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE = "Ocorreu um erro interno tente novamente mais tarde!";
    public static final String USUARIO_NAO_AUTORIZADO_AGUARDANDO_VALIDACAO_DE_EMAIL_VERIFIQUEI_CAIXA_DE_ENTRADA_E_SPAN = "Usuario não autorizado. Aguardando validação do email! Verifiquei sua caixa de entrada ou spam no email cadastrado.";
    public static final String COLABORADOR_ATUALIZADA_COM_SUCESSO = "Colaborador atualizada com sucesso.";
    public static final String NAO_AUTORIZADO = "Não autorizado.";
    public static final String DATA_DE_NASCIMENTO_E_OBRIGATORIO = "Data de nascimento é obrigatório.";
    public static final String EMAIL_DEVE_SER_ENVIADO_NO_FORMATO_VALIDO = "Email deve ser enviado no formato válido.";
    public static final String CODIGO_EMPRESA_INFORMADO_NAO_EXISTE_CADASTRADO = "O código empresa informado não existe.";
    public static final String CODIGO_COLABORADOR_INFORMADO_NAO_EXISTE_CADASTRADO = "O código colaborador informado não existe.";
    public static final String EMAIL_INFORMADO_JA_EXISTE_CADASTRADO = "O Email informado já existe cadastrado.";
    public static final String TELEFONE_DEVE_SER_NUMERO_VALIDO = "Telefone deve ser número válido.";
    public static final String TELEFONE_PRINCIPAL_DEVE_SER_NUMERO_VALIDO = "Telefone principal deve ser número válido.";
    public static final String CEP_DEVE_SER_INFORMADO_NO_FORMATO_DE_8_CARACTERES = "Cep deve ser informado no formato de 8 caracteres.";
    public static final String LOGRADOURO_DEVE_SER_INFORMADO_COM_NO_MAXIMO_90_CARACTERES = "Logradouro deve ser informado com no máximo 90 caracteres.";
    public static final String LOGRADOURO_DEVE_SER_INFORMADO = "Logradouro deve ser informado.";
    public static final String COMPLEMENTO_DEVE_CONTER_NO_MAXIMO_90_CARACTERES = "Complemento deve conter no máximo 90 caracteres.";
    public static final String BAIRRO_DEVE_SER_INFORMADO_COM_NO_MAXIMO_90_CARACTERES = "Bairro deve ser informado com no máximo 90 caracteres.";
    public static final String BAIRRO_DEVE_SER_INFORMADO = "Bairro deve ser informado.";
    public static final String LOCALIDADE_CIDADE_DEVE_SER_INFORMADO = "Localidade - Cidade deve ser informado.";
    public static final String LOCALIDADE_CIDADE_DEVE_SER_INFORMADO_COM_NO_MAXIMO_90_CARACTERES = "Localidade - Cidade deve ser informado com no máximo 90 caracteres.";
    public static final String UF_DEVE_SER_INFORMADO_COM_BASE_NOS_ESTADOS_BRASILEIROS = "Uf deve ser informado com base nos estados brasileiros.";
    public static final String NUMERO_DEVE_SER_INFORMADO = "Número deve ser informado.";
    public static final String POR_FAVOR_INFORME_OS_DADOS_DO_COLABORADOR = "Por favor informe os dados do colaborador.";
    public static final String CAHVE_DE_AUTENTICACAO_DE_EMAIL_INVALIDA = "Chave de autenticação de email inválida.";
    public static final String ATIVACAO_DO_PERFIL_REALIZADO_COM_SUCESSO = "Ativação do perfil realizado com sucesso!";
    public static final String CHAVE_ATIVACAO_PERFIL_E_OBRIGATORIO_E_DEVE_SER_VALIDO = "O campo '" + FieldDomainConstants.CHAVE_ATIVACAO_PERFIL + "' é obrigatória e deve ser válida.";
    public static final String NUMERO_E_OBRIGATORIO_OU_INVALIDO = "Número é obrigatório ou inválido.";
    public static final String UF_E_OBRIGATORIO = "UF é obrigatório.";
    public static final String LOCALIDADE_E_OBRIGATORIO = "Localidade é obrigatório.";
    public static final String BAIRRO_E_OBRIGATORIO = "Bairro é obrigatório.";
    public static final String LOGRADOURO_E_OBRIGATORIO = "Logradouro é obrigatório.";
    public static final String TELEFONE_INVALIDO = "Telefone inválido.";
    public static final String EMAIL_E_OBRIGATORIO_OU_ESTA_INVALIDO = "Email é obrigatório ou está inválido.";
    public static final String ENDERECO_E_OBRIGATORIO = "Endereço é obrigatório.";
    public static final String CEP_E_OBRIGATORIO = "Cep é obrigatório.";
    public static final String CEP_INVALIDO = "Cep inválido.";
    public static final String CNPJ_CPF_E_OBRIGATORIO = "CNPJ - CPF é obrigatório.";
    public static final String CNPJ_E_INVALIDO = "CNPJ é inválido.";
    public static final String CPF_E_INVALIDO = "CPF é inválido.";
    public static final String CNPJ_DEVE_CONTER_NO_MINIMO_11_CARACTERES_E_NO_MAXIMO_14_NA_AUSENCIA_DO_CNPJ_PODE_INFORMAR_O_CPF = "Cnpj deve conter no mínimo 11 caracteres e no máximo 14. Na ausência do cnpj informar o CPF.";
    public static final String CODIGO_TIPO_USUARIO_DEVE_SER_UM_NUMERO_VALIDO = "Código tipo usuario deve ser informado um número válido.";
    public static final String TIPO_USUARIO_DEVE_SER_INFORMADO = "Tipo usuário é obrigatório.";
    public static final String TELEFONE_PRINCIPAL_E_OBRIGATORIO = "Telefone principal é obrigatório.";
    public static final String DADOS_DE_CONTATO_E_OBRIGATORIO = "Dados de contato é obrigatório.";
    public static final String CODIGO_DA_EMPRESA_E_OBRIGATORIO = "Codigo da empresa é obrigatório.";
    public static final String EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR = "Existe erro(s) no(s) campo(s) do colaborador.";
    public static final String INFORME_USUARIO_E_SENHA = "Informe usuario e senha.";
    public static final String CODIGO_NAO_DEVE_SER_PREENCHIDO = "Codigo não deve ser preenchido.";
    public static final String INFORME_O_USUARIO_DE_ACESSO = "Informe o usuário de acesso.";
    public static final String INFORME_A_SENHA_DE_ACESSO = "Informe a senha de acesso.";
    public static final String USUARIO_DEVE_SER_INFORMADO_UM_NUMERO_DE_TELEFONE_OU_UM_EMAIL = "Usuario deve ser informado um número de telefone ou um email.";
    public static final String SENHA_DEVE_CONTER_NO_MINIMO_5_CARACTERES_E_NO_MAXIMO_150 = "Senha deve conter no mínimo 5 caracteres e no máximo 150.";
    public static final String O_CAMPO_PAGE_E_OBRIGATORIO = "O campo page é obrigatório.";
    public static final String O_CAMPO_SIZE_E_OBRIGATORIO = "O campo size é obrigatório.";
    public static final String O_CAMPO_SEARCHTERM_DEVE_CONTER_NO_MAXIMO_50_CARACTERES = "O campo searchTerm deve conter no máximo 50 caracteres.";
    public static final String USUARIO_OU_SENHA_NAO_ENCONTRADO = "Usuario ou senha não encontrado.";
}
