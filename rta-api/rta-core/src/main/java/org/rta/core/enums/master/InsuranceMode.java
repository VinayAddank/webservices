package org.rta.core.enums.master;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * InsuranceMode enum </br>
 * MANUAL(1, "Manual"), ONLINE(2, "Online")
 * 
 * @author rahul.sharma
 *
 */
public enum InsuranceMode {

    MANUAL(1, "Manual"), ONLINE(2, "Online");

    private Integer value;
    private String label;

    private InsuranceMode(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    private static Map<Integer, String> map;

    static {
        Integer count = InsuranceMode.values().length;
        map = new LinkedHashMap<>(count);
        for (InsuranceMode taxType : InsuranceMode.values()) {
            map.put(taxType.value, taxType.label);
        }
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static InsuranceMode getTaxType(String label) {
        if (!Objects.isNull(label)) {
            if (InsuranceMode.MANUAL.label.equals(label)) {
                return InsuranceMode.MANUAL;
            }
            if (InsuranceMode.ONLINE.label.equals(label)) {
                return InsuranceMode.ONLINE;
            }
        }
        return null;
    }

    public static InsuranceMode getTaxType(Integer value) {
        if (!Objects.isNull(value)) {
            if (InsuranceMode.MANUAL.value == value) {
                return InsuranceMode.MANUAL;
            }
            if (InsuranceMode.ONLINE.value == value) {
                return InsuranceMode.ONLINE;
            }
        }
        return null;
    }

    public static Map<Integer, String> getAll() {
        return map;
    }
}
