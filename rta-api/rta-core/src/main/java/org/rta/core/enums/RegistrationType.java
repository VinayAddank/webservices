/**
 * 
 */
package org.rta.core.enums;

import java.util.HashMap;
import java.util.Map;

import org.rta.core.utils.ObjectsUtil;

/**
 * @author arun.verma
 *
 */
public enum RegistrationType {

    TR(1, "tr"), PR(2, "pr");
    
    private static Map<String, RegistrationType> labelToType = new HashMap<>();
    
    static {
        for (RegistrationType rt : RegistrationType.values()) {
            labelToType.put(rt.getLabel(), rt);
        }
    }
    
    private Integer value;
    private String label;
    
    private RegistrationType(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }
    
    public String getLabel() {
        return label;
    }
    
    public static RegistrationType getType(String label) {
        return ObjectsUtil.isNull(label) ? null : labelToType.get(label.toUpperCase());
    }
    
}