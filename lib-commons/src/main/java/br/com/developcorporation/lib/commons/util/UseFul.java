package br.com.developcorporation.lib.commons.util;

import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class UseFul {

    private UseFul(){}

    public static String replaceAll(final String value) {

        if (Objects.nonNull(value)) {
            return value.replace(".", "").replace(",", "").replace("(", "").replace(")", "").replace("-", "").replace("/", "")
                    .replaceAll("^ +| +$|( )+", "$1").trim();
        } else {
            return removeEmptyReturnNull(value);
        }
    }

    public static String toUpper(final String value) {
        if (Objects.nonNull(value))
            return replaceAll(removeAccents(value.toUpperCase()));
        else
            return value;
    }

    public static String toLowerCase(final String value) {
        if (Objects.nonNull(value)) {
            return value.toLowerCase();
        } else {
            return value;
        }
    }

    public static String removeAccents(final String value) {
        if (Objects.nonNull(value)) {
            return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        } else {
            return null;
        }
    }

    public static Date toDate(final String value) throws Exception {
        return new SimpleDateFormat("dd/MM/yyyy").parse(value);
    }

    public static String removeEmptyReturnNull(final String value) {
        if (!StringUtils.isEmpty(value))
            return value;
        else
            return null;
    }

    public static <T> List<Object> removingDuplicationFromList(final List<T> values, final Function<T, ?> function) {
        final List<Object> allValues = values
                .stream()
                .map(value -> function.apply(value))
                .collect(Collectors.toList());

        final Set<Object> match = new HashSet<>();

        final List<Object> duplicateValues = allValues
                .stream()
                .filter(value -> !match.add(value))
                .collect(Collectors.toList());

        return duplicateValues.stream().distinct().collect(Collectors.toList());
    }

    public static <T> List<T> toList(Optional<T> opt) {
        return opt
                .map(Collections::singletonList)
                .orElseGet(Collections::emptyList);
    }

    public static <T> List<T> removeNullToList(List<T> list) {
        return list.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }
}
