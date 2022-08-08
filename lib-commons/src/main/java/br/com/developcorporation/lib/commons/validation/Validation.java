package br.com.developcorporation.lib.commons.validation;

import br.com.developcorporation.lib.commons.constants.OtherConstants;
import br.com.developcorporation.lib.commons.constants.RegexConstants;
import br.com.developcorporation.lib.commons.enums.StatesBrazil;
import br.com.developcorporation.lib.commons.message.Message;
import br.com.developcorporation.lib.commons.util.UseFul;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Validation {

    private Validation(){}

    private static boolean validRegex(final String value, final String regex){
        if(StringUtils.isEmpty(value) || StringUtils.isEmpty(regex))
            return false;
        else {
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
    }

    private static Message.Details regex(String value, final String field, final boolean required, final String regex, final String message) {

        Message.Details details = null;

        if(Boolean.TRUE.equals(required)){
            if(StringUtils.isEmpty(value)){
                details = new Message.Details(field, message, value);
            }else{
                if (!validRegex(value, regex)) {
                    details = new Message.Details(field, message, value);
                }
            }
        }else {
            if(!StringUtils.isEmpty(value)){
                if (!validRegex(value, regex)) {
                    details = new Message.Details(field, message, value);
                }
            }
        }

        return details;
    }

    public static Message.Details validUUID(final String value, final String field, final String message) {
        return regex(value, field, false, RegexConstants.REGEX_UUID, message);
    }

    public static Message.Details notNullOrEmpty(final String field, final String value, final String message){
        Message.Details details = null;

        if(StringUtils.isEmpty(value)) {
            details = new Message.Details(field, message, value);
        }

        return details;
    }

    public static Message.Details stringMinAndMax(final String value, final String field, final int min, final int max,
                                                                  final String message) {
        Message.Details details = null;

        if(StringUtils.isEmpty(value)){
            details = new Message.Details(field, message, value);
        }else{
            if (value.length() < min || value.length() > max ) {
                details = new Message.Details(field, message, value);
            }
        }

        return details;
    }

    public static Message.Details mandatoryNull(final String value, final String field, final String message){
        Message.Details details = null;
        if(!StringUtils.isEmpty(value))
            details = new Message.Details(field, message, value);
        return details;
    }

    public static Message.Details mandatoryNull(final Object value, final String field, final String message){
        Message.Details details = null;
        if(Objects.nonNull(value))
            details = new Message.Details(field, message, null);
        return details;
    }

    public static Message.Details mandatoryAlphanumeric(final String value, final String field, final String message) {
        return regex(UseFul.replaceAll(value), field, true, RegexConstants.REGEX_ALPHANUMERIC, message);
    }

    public static Message.Details  mandatoryNumber8(final String value, final String field, final String message) {
        return regex(value, field, true, RegexConstants.REGEX_NUMBER_8, message);
    }

    public static Message.Details  mandatoryNumberPhone(final String value, final String field, final String message) {
        return regex(value, field, true, RegexConstants.REGEX_NUMBER_PHONE, message);
    }

    public static Message.Details mandatoryEmail(final String value,final String field, final String message) {
        return regex(value, field, true, RegexConstants.REGEX_EMAIL, message);
    }

    public static Message.Details  validCPF(final String value, final String field, final String message) {

        Message.Details  detailsResponse = regex(value, field, true, RegexConstants.REGEX_CPF, message);

        if (detailsResponse == null) {
            if (!isValidCPF(value))
                return new Message.Details (field, message, value);
            else
                return null;
        } else {
            return detailsResponse;
        }
    }

    public static Message.Details  validCNPJ(final String value, final String field, final String message) {

        Message.Details  detailsResponse = regex(value, field, true, RegexConstants.REGEX_CNPJ, message);

        if (detailsResponse == null && !value.isEmpty()) {
            if (!isValidCNPJ(value))
                return new Message.Details(field, message, value);
            else
                return null;
        } else {
            return detailsResponse;
        }
    }

    public static Message.Details validationNullOrEmptyMaxCaracter(final String value, final String field, final String messageNullOrEmpity, final String messageMax, int min, int max){

        Message.Details details = null;

        if(StringUtils.isEmpty(value)){
            details = new Message.Details(
                    field,
                    messageNullOrEmpity,
                    value

            );
        }else{

            details = Validation.stringMinAndMax(
                                    value,
                                    field,
                                    min,
                                    max,
                                    messageNullOrEmpity
            );
        }
        return  details;
    }

    public static Message.Details mandatoryUf(final String value, final String field, final String message){
        Message.Details details = null;

        if(!StatesBrazil.exisByUF(value))
            details = new Message.Details(
                    field,
                    message,
                    value
            );

        return details;
    }

    public static Message.Details mandatoryPassword(final String value, final String field, final String message) {
        return regex(value, field, true, RegexConstants.REGEX_PASSWORD, message);
    }

    public static Message.Details mandatoryDate(final String value, final String field, final String message) {
        String datePattern = "" ;
        String regex = "";

        if(value.length() == 10){
            datePattern = RegexConstants.REGEX_YYYY_MM_DD;
            regex = OtherConstants.YYYY_MM_DD;
        }else {
            datePattern = RegexConstants.REGEX_YYYY_MM_DD_HH_MM_SS;
            regex = OtherConstants.DATA_FORMAT;
        }

        Message.Details response = regex(value, field, true, datePattern, message);
        if (response == null) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(regex);
                simpleDateFormat.setLenient(false);
                simpleDateFormat.parse(value);
            } catch (ParseException e) {
                response = new Message.Details(field, message, value);
            }
        }
        return response;
    }




    public static Message.Details mandatoryCNPJorCPF(
            final String value,
            final String field,
            final String messageRequired,
            final String messageCNPJInvalid,
            final String messageCPFInvalid,

            final String messageGenericInvalid){
        Message.Details details = null;

        if(StringUtils.isEmpty(value)){
            details = new Message.Details(
                    field,
                    messageRequired,
                    value
            );
        }else{

            switch (value.length()){
                case 11:
                    details = validCPF(value, field, messageCPFInvalid);
                    break;
                case 14:
                    details = validCNPJ(value, field, messageCNPJInvalid);
                    break;
                default:
                    details = new Message.Details(field,messageGenericInvalid,value);
                    break;
            }
        }

        return details;
    }



    private static boolean isValidCNPJ(final String cnpj) {
        String numberCnpj = UseFul.replaceAll(cnpj);

        Integer digito1 = calculateDigit(numberCnpj.substring(0, 12), OtherConstants.pesoCNPJ);
        Integer digito2 = calculateDigit(numberCnpj.substring(0, 12) + digito1, OtherConstants.pesoCNPJ);
        return numberCnpj.equals(numberCnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }

    private static boolean isValidCPF(final String cpf) {
        String numberCpf = UseFul.replaceAll(cpf);
        for (int j = 0; j < 10; j++)
            if (padLeft(Integer.toString(j), Character.forDigit(j, 10)).equals(numberCpf))
                return false;

        Integer digito1 = calculateDigit(numberCpf.substring(0, 9), OtherConstants.pesoCPF);
        Integer digito2 = calculateDigit(numberCpf.substring(0, 9) + digito1, OtherConstants.pesoCPF);
        return numberCpf.equals(numberCpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    private static String padLeft(final String text, final char character) {
        return String.format("%11s", text).replace(' ', character);
    }

    private static int calculateDigit(final String str, final int[] peso) {
        int soma = 0;
        int digito;
        for (int indice = str.length() - 1; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }




}
