package br.com.developcorporation.lib.commons.service;

import br.com.developcorporation.lib.commons.util.Convert;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;

public final class CryptographyService {

    private CryptographyService(){}


    private static final String[] asterisk = {
            "token",
            "access_token",
            "password",
            "senha",
            "telefoneCelular",
            "telefone",
            "telefone_principal",
            "cnpj",
            "cpf",
            "cnpj_cpf",
            "cpf_cnpj",
            "cpfCnpj",
            "rg",
            "cep",
            "zipCode",
            "mainPhone",
            "telephone",
            "mainPhoneCommercial",
            "telephoneCommercial",
            "emailRepresentative",
            "nameRepresentative",
            "telephoneCellRepresentative",
            "telefoneComercialPrincipal",
            "telefoneComercial",
            "telefoneCelularRepresentante",
            "emailRepresentante",
            "nomeRepresentante",
            "nomeFantasia",
            "razaoSocial",
            "stateRegistration",
            "fantasyName",
            "corporateName",
            "inscricaoEstadual",
            "number",
            "numero",
            "image",
            "imagem",
            "nome",
            "name",
            "username",
            "token_type",
            "type",
            "endereco",
            "logradouro",
            "publicPlace",
            "address",
            "email",
            "clientId",
            "clientSecret",
            "client_id",
            "client_secret"
    };

    private static final String[] noCrypt = {
            "data",
            "date",
            "expires",
            "expires_in"
    };

    @SneakyThrows
    public static Object mapCryptography(final Object value) {
        if(Objects.isNull(value))
            return null;

        Map<String, Object> map = convertToMap(value);

        if(Objects.nonNull(map)){
            try{
                for (Map.Entry<String,Object> line: map.entrySet()) {

                    Object ob = line.getValue();

                    if (ob instanceof LinkedHashMap) {

                        Map<String, Object> mapHashLink = convertToMap(ob);

                        if(Objects.nonNull(mapHashLink)){

                            for (Map.Entry<String, Object> lineHash: mapHashLink.entrySet()) {
                                encrypt(mapHashLink, lineHash);
                            }

                            map.put(line.getKey(), mapHashLink);
                        }

                    }else {
                        encrypt(map, line);
                    }
                }
            }catch (Exception ex){
                throw new Exception("Houve uma falha na criptografia dos dados. Detalhes: " + ex.getMessage());
            }
        }

        return Convert.convertMapToObject(map);
    }

    private static Map<String, Object> convertToMap(Object value) throws JsonProcessingException {
        if(Objects.isNull(value))
           return new HashMap<>();

        return Convert.convertJsonToMap(Convert.toJson(value));
    }

    private static void encrypt(Map<String, Object> map, Map.Entry<String, Object> line) {
        if(Objects.nonNull(map) && Objects.nonNull(line)){
            if(!(Arrays.stream(asterisk).filter(x -> x.contains(line.getKey())).count() == 0)){
                map.put(line.getKey(), "*************");
            }
            else{
                if(Arrays.stream(noCrypt).filter(x -> x.contains(line.getKey())).count() == 0)
                    map.put(line.getKey(), line.getValue());
            }
        }
    }
}
