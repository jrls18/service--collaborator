package br.com.developcorporation.lib.commons.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Convert {
    private Convert(){}

    public static String toJson(Object value)  {
        String valueConvert = null;

        if(Objects.nonNull(value)){
            try{
                ObjectMapper ob = new ObjectMapper();
                valueConvert = ob.writeValueAsString(value);
            }catch (JsonProcessingException ex){

            }

        }
        return valueConvert;
    }

    public static Map<String, Object> convertJsonToMap(final String value) throws JsonProcessingException {
        return new ObjectMapper().readValue(value, HashMap.class);
    }

    public static Object convertMapToObject(final Map map){
        ObjectMapper mapper = new ObjectMapper();
        return   mapper.convertValue(map, Object.class);
    }

}
