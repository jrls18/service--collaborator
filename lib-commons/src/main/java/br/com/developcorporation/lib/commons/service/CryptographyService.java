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
            "client_secret",
            "arquivo_base_64",
            "file"
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

        List<Map<String, Object>> mapListPrincipal = convertToMapList(value);
        List<Map<String, Object>> newMapListPrincipalCryptography = new ArrayList<>(mapListPrincipal.size());

        for (Map<String, Object> linePrincipal: mapListPrincipal) {

            for (Map.Entry<String,Object> lineSec: linePrincipal.entrySet()){
                  Object objLineSec = lineSec.getValue();

                if(objLineSec instanceof ArrayList){

                    List<Map<String, Object>> mapList = new ArrayList<>();

                    for (Object lineObj: ((ArrayList<?>) objLineSec).toArray()){
                        Map<String, Object> mapHashPrincipal = convertToMap(lineObj);

                        for (Map.Entry<String, Object> lineHashPrincipal: mapHashPrincipal.entrySet()) {

                            Object lineHashPrincipalObj = lineHashPrincipal.getValue();

                            crypt(mapHashPrincipal, lineHashPrincipal, lineHashPrincipalObj);
                        }
                        mapList.add(mapHashPrincipal);
                    }

                    linePrincipal.put(lineSec.getKey(), mapList);

                }else{
                    crypt(linePrincipal, lineSec, objLineSec);
                }
            }
            newMapListPrincipalCryptography.add(linePrincipal);
        }

        return convertToMapListToObject(newMapListPrincipalCryptography);
    }

    private static void crypt(Map<String, Object> linePrincipal,
                              Map.Entry<String, Object> lineSec,
                              Object objLineSec) throws JsonProcessingException {
        if (objLineSec instanceof LinkedHashMap){

              Map<String, Object> mapTer = Convert.convertJsonToMap(Convert.toJson(objLineSec));

              for(Map.Entry<String,Object> lineTer: mapTer.entrySet()){
                  encrypt(mapTer, lineTer);
              }

              linePrincipal.put(lineSec.getKey(), mapTer);

        }else {
            encrypt(linePrincipal, lineSec);
        }
    }

    private static Map<String, Object> convertToMap(Object value) throws JsonProcessingException {
        if(Objects.isNull(value))
           return new HashMap<>();

        return Convert.convertJsonToMap(Convert.toJson(value));
    }


    private static Object convertToMapListToObject(final List<Map<String,Object>> maps){
        if(Objects.isNull(maps))
            return null;

        if(maps.size() == 1)
           return Convert.convertMapToObject(maps.get(0));
        else
            return Convert.convertMapListToObject(maps);
    }

    private static List<Map<String, Object>> convertToMapList(final Object value) throws JsonProcessingException{
        if(Objects.isNull(value))
            return new ArrayList<>();

        List<Map<String, Object>> mapList = new ArrayList<>();

        if(value instanceof ArrayList)
            mapList.addAll(Convert.convertJsonToMapList(Convert.toJson(value)));
        else
            mapList.add(Convert.convertJsonToMap(Convert.toJson(value)));

        return mapList;
    }

    private static void encrypt(Map<String, Object> map, Map.Entry<String, Object> line) {
        if(Objects.nonNull(map) && Objects.nonNull(line)){
            if(!(Arrays.stream(asterisk).filter(x -> x.equalsIgnoreCase(line.getKey())).count() == 0)){
                map.put(line.getKey(), "*************");
            }
            else{
                if(Arrays.stream(noCrypt).filter(x -> x.equalsIgnoreCase(line.getKey())).count() == 0)
                    map.put(line.getKey(), line.getValue());
            }
        }
    }
}
