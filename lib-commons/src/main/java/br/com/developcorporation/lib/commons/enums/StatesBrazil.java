package br.com.developcorporation.lib.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum StatesBrazil {

    ACRE("AC", "Acre"),
    ALAGOAS("AL", "Alagoas"),
    AMAPA("AP", "Amapá"),
    AMAZONAS("AM", "Amazonas"),
    BAHIA("BA", "Bahia"),
    CEARA("CE", "Ceará"),
    DISTRITO_FEDERAL("DF", "Distrito Federal"),
    ESPIRITO_SANTO("ES", "Espírito Santo"),
    GOIAS("GO", "Goiás"),
    MARANHAO("MA", "Maranhão"),
    MATO_GROSSO("MT", "Mato Grosso"),
    MATO_GROSSO_DO_SUL("MS", "Mato Grosso do Sul"),
    MINAS_GERAIS("MG", "Minas Gerais"),
    PARA("PA", "Pará"),
    PARAIBA("PB", "Paraíba"),
    PARANA("PR", "Paraná"),
    PERNAMBUCO("PE", "Pernambuco"),
    PIAUI("PI", "Piauí"),
    RIO_DE_JANEIRO("RJ", "Rio de Janeiro"),
    RIO_GRANDE_DO_NORTE("RN", "Rio Grande do Norte"),
    RIO_GRANDE_DO_SUL("RS", " Rio Grande do Sul"),
    RONDONIA("RO", "Rondônia"),
    RORAIMA("RR", "Roraima"),
    SANTA_CATARINA("SC", "Santa Catarina"),
    SAO_PAULO("SP", "São Paulo"),
    SERGIPE("SE", "Sergipe"),
    TOCANTINS("TO", "Tocantins");

    private final String uf;

    private final String estado;

    private static final Map<String, StatesBrazil> map = new HashMap<>();

    static {
        for (final StatesBrazil e : EnumSet.allOf(StatesBrazil.class)) {
            map.put(e.uf, e);
        }
    }

    public static boolean exisByUF(final String uf) {
        boolean isNull = true;

        if(Objects.nonNull(uf))
          isNull = map.containsKey(uf.toUpperCase());

        return isNull;
    }

    public static StatesBrazil getUfBrazil(final String uf) {
        StatesBrazil brazil = null;

        if(Objects.nonNull(uf))
            brazil = map.get(uf);

        return brazil;
    }
}
