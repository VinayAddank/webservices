package org.rta.core.enums.master;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Nationality types </br>
 * 0 - INDIAN, 1 - NRI and 2 - FOREIGNER
 * 
 * @author rahul.sharma
 *
 */
public enum Nationality {

    INDIAN(0, "INDIAN"), NRI(1, "NRI"), FOREIGNER(2, "FOREIGNER");

    private Integer value;
    private String label;

    private Nationality(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
    
    private static Map<Integer, String> map;

    static {
        Integer count = Nationality.values().length;
        map = new LinkedHashMap<>(count);
        for (Nationality nationality : Nationality.values()) {
            map.put(nationality.value, nationality.label);
        }
    }

    /**
     * @return value of {@code this} Nationality type
     */
    public Integer getValue() {
        return value;
    }

    /**
     * @return label of {@code this} Nationality type
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get nationality from {@code label}
     * 
     * @param label
     * @return Nationality
     */
    public static Nationality getNationality(String label) {
        if (!Objects.isNull(label)) {
            if (label.equals(INDIAN.label)) {
                return INDIAN;
            }
            if (label.equals(FOREIGNER.label)) {
                return FOREIGNER;
            }
            if (label.equals(NRI.label)) {
                return NRI;
            }
        }
        return null;
    }

    /**
     * Get nationality from {@code value}
     * 
     * @param value
     * @return Nationality
     */
    public static Nationality getNationality(Integer value) {
        if (!Objects.isNull(value)) {
            if (value == INDIAN.value) {
                return INDIAN;
            }
            if (value == FOREIGNER.value) {
                return FOREIGNER;
            }
            if (value == NRI.value) {
                return NRI;
            }
        }
        return null;
    }

    /**
     * Checks {@code nationality} belongs to any of the available nationality. 
     * @param nationality
     * @return {@code true}, if belongs else {@code false}
     */
    public static boolean isValidNationality(Nationality nationality) {
        if (nationality == Nationality.INDIAN || nationality == Nationality.FOREIGNER || nationality == NRI) {
            return true;
        }
        return false;
    }

}
