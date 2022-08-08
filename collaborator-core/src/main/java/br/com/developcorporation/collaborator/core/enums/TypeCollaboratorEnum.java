package br.com.developcorporation.collaborator.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum TypeCollaboratorEnum   {

    ADM("ADM", "ADM"),
    USER("USER", "USER"),
    COLLABORATOR("COLABORADOR", "COLLABORATOR");

    private final String code;

    private final String description;

    private static final Map<String, TypeCollaboratorEnum> map = new HashMap<>();

    static {
        for (final TypeCollaboratorEnum e : EnumSet.allOf(TypeCollaboratorEnum.class)) {
            map.put(e.code, e);
        }
    }

    public static boolean existsCode(final String code) {
        if(StringUtils.isEmpty(code))
            return  false;
        return map.containsKey(code.toUpperCase());
    }
}
