package br.com.developcorporation.collaborator.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum CoreEnum {
    CREATED("201", "CREATED","Utilizado para criação de um objeto no banco de dados"),
    ACCEPTED("202", "ACCEPTED","Utilizado para atualizar um objeto no banco de dados"),
    UNAUTHORIZED("401", "UNAUTHORIZED","Não autorizado."),
    UNPROCESSABLE_ENTITY("422", "UNPROCESSABLE ENTITY", "Usado para informar um erro de negócio."),
    INTERNAL_SERVER_ERROR("500", "INTERNAL SERVER ERROR", "Erro inesperado aconteceu no sistema.");

    private final String code;

    private final String description;

    private final String observation;

    private static final Map<String, CoreEnum> map = new HashMap<>();

    static {
        for (final CoreEnum e : EnumSet.allOf(CoreEnum.class)) {
            map.put(e.code, e);
        }
    }
}
