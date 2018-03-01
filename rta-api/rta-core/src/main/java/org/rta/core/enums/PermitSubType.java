package org.rta.core.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PermitSubType {
    HOME_DISTRICT("HD", "Home district as per RC"), 
    NEIGHBOURING_DISTRICT("ND", "Neighbouring District"), 
    THROUGHOUT_STATE("TS", "Throughout State");

    private static final Map<String, PermitSubType> labelToType = new HashMap<String, PermitSubType>();
    private static final Map<String, PermitSubType> valueToType = new HashMap<String, PermitSubType>();
    
    static {
        for (PermitSubType permitSubType : PermitSubType.values()) {
            labelToType.put(permitSubType.getLabel(), permitSubType);
        }
        for (PermitSubType permitSubType : EnumSet.allOf(PermitSubType.class)) {
            valueToType.put(permitSubType.getValue(), permitSubType);
        }
    }
    
    private PermitSubType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private String value;
    private String label;
            
    @JsonValue
    public String toValue() {
        return this.label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
       
    public static PermitSubType getPermitSubType(String value) {
        return valueToType.get(value);
    }
}
