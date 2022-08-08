package br.com.developcorporation.lib.commons.constants;



public final class RegexConstants {

    private RegexConstants(){}

    public static final String REGEX_UUID = "[0-9a-fA-F]{8}(?:-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}";
    public static final String REGEX_ALPHANUMERIC = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ& ]+$";
    public static final String REGEX_NUMBER_8 = "^[0-9]{1,8}$";
    public static final String REGEX_NUMBER_PHONE = "^[0-9]{10,11}$";
    public static final String REGEX_CPF = "[0-9]{3}[0-9]{3}[0-9]{3}[0-9]{2}";
    public static final String REGEX_CNPJ = "[0-9]{2}[0-9]{3}[0-9]{3}[0-9]{4}[0-9]{2}";
    public static final String REGEX_EMAIL = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
    public static final String REGEX_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    public static final String REGEX_YYYY_MM_DD = "(\\d{4}-\\d{2}-\\d{2})";
    public static final String REGEX_YYYY_MM_DD_HH_MM_SS = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) ([2][0-3]|[0-1][0-9]|[1-9]):[0-5][0-9]:([0-5][0-9]|[6][0])$";



}
