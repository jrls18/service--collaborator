package br.com.developcorporation.lib.commons.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
