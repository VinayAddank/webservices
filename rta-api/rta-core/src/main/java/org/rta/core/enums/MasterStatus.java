/**
 * 
 */
package org.rta.core.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.rta.core.utils.ObjectsUtil;

public enum MasterStatus {

    ACTIVE(1, "ACTIVE"), INACTIVE(2, "INACTIVE");

    private static final Map<Integer, String> lookup = new HashMap<Integer, String>();
    private static final Map<String, MasterStatus> labelToStatus = new HashMap<String, MasterStatus>();
    private static final Map<Integer, MasterStatus> valueToStatus = new HashMap<Integer, MasterStatus>();

    static {
        for (MasterStatus status : EnumSet.allOf(MasterStatus.class)) {
            lookup.put(status.getValue(), status.getLabel());
        }
        for (MasterStatus status : EnumSet.allOf(MasterStatus.class)) {
            labelToStatus.put(status.getLabel(), status); 
        }
        for (MasterStatus status : EnumSet.allOf(MasterStatus.class)) {
            valueToStatus.put(status.getValue(), status); 
        }
    }

    private Integer value;
    private String label;

    private MasterStatus(Integer valuesd, String label) {
        this.value = valuesd;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static String getLabel(Integer value) {
        return lookup.get(value);
    }
    
    public static MasterStatus getStatus(String label) {
        return ObjectsUtil.isNull(label) ? null : labelToStatus.get(label.toUpperCase());
    }
    
    public static MasterStatus getStatus(Integer value) {
        return valueToStatus.get(value);
    }
    
}
