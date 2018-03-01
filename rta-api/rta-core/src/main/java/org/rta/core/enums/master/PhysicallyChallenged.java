package org.rta.core.enums.master;

import java.util.LinkedHashMap;
import java.util.Map;

public enum PhysicallyChallenged {

    NO(0, "NO"), YES(1, "YES");
    
    private static Map<Integer, String> map;
    
    static {
        Integer count = PhysicallyChallenged.values().length;
        map = new LinkedHashMap<>(count);
        for (PhysicallyChallenged physicallyChallenged : PhysicallyChallenged.values()) {
            map.put(physicallyChallenged.value, physicallyChallenged.label);
        }
    }

    private Integer value;
    private String label;

    private PhysicallyChallenged(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static Map<Integer, String> getAll() {
        return map;
    }

}
