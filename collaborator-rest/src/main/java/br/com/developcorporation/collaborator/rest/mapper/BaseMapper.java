package br.com.developcorporation.collaborator.rest.mapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Map;

@Mapper
public abstract class BaseMapper {
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
        MAPPER.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        MAPPER.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        MAPPER.registerModule(new SimpleModule());
    }

    public <T> T from(final Map<String, Object> data, final Class<T> clazz) {
        return MAPPER.convertValue(data, clazz);
    }


    @Named("replaceAll")
    public  String replaceAll(final String value){
        String newValue = StringUtils.EMPTY;

        if(!StringUtils.isEmpty(value)) {
            newValue = value
                    .replace(".", "")
                    .replace(",", "")
                    .replace("(", "")
                    .replace(")", "")
                    .replace("-", "")
                    .replace("/", "")
                    .replace(" ", "")
                    .replaceAll("^ +| +$|( )+", "$1")
                    .trim();
        }

        return newValue;
    }
}
