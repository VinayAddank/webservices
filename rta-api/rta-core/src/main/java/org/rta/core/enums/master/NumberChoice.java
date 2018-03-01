package org.rta.core.enums.master;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * NumberChoice enum </br>
 * REGULAR_NO(1, "Regular No"), FANCY_NO(2, "Fancy No")
 * 
 * @author rahul.sharma
 *
 */
public enum NumberChoice {

    REGULAR_NO(1, "Regular No"), FANCY_NO(2, "Fancy No");

    private Integer value;
    private String label;

    private NumberChoice(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    private static Map<Integer, String> map;

    static {
        Integer count = NumberChoice.values().length;
        map = new LinkedHashMap<>(count);
        for (NumberChoice taxType : NumberChoice.values()) {
            map.put(taxType.value, taxType.label);
        }
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static NumberChoice getTaxType(String label) {
        if (!Objects.isNull(label)) {
            if (NumberChoice.REGULAR_NO.label.equals(label)) {
                return NumberChoice.REGULAR_NO;
            }
            if (NumberChoice.FANCY_NO.label.equals(label)) {
                return NumberChoice.FANCY_NO;
            }
        }
        return null;
    }

    public static NumberChoice getTaxType(Integer value) {
        if (!Objects.isNull(value)) {
            if (NumberChoice.REGULAR_NO.value == value) {
                return NumberChoice.REGULAR_NO;
            }
            if (NumberChoice.FANCY_NO.value == value) {
                return NumberChoice.FANCY_NO;
            }
        }
        return null;
    }

    public static Map<Integer, String> getAll() {
        return map;
    }
}
