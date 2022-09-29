package br.com.developcorporation.collaborator.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum RoleName {
    ROLE_COLABORADOR(1,"ROLE_COLABORADOR","COLABORADOR"),
    ROLE_USUARIO(2,"ROLE_USUARIO","USUARIO"),
    ROLE_ADM(3,"ROLE_ADM","ADM");

    private final int  id;

    private final String version;

    private final String description;

    private static final Map<String, RoleName> map = new HashMap<>();

    static {
        for (final RoleName e : EnumSet.allOf(RoleName.class)) {
            map.put(e.description, e);
        }
    }

    public static boolean exisDescription(final String description) {
        return map.containsKey(description.toUpperCase());
    }

    public static int getId(final String description){
        if(description.equalsIgnoreCase(ROLE_ADM.getDescription()))
            return ROLE_ADM.getId();
        else if(description.equalsIgnoreCase(ROLE_COLABORADOR.getDescription()))
            return ROLE_COLABORADOR.getId();
        else
            return ROLE_USUARIO.getId();
    }
}
