package org.rta.core.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.rta.core.utils.ObjectsUtil;

public enum VehicleSubClassType {

	MMCN("MMCN", "MMCN"), MCYN("MCYN", "MCYN"), OBPN("OBPN", "OBPN"), MCRN("MCRN", "MCRN");


    private static final Map<String, VehicleSubClassType> labelToVehicleSubClass = new HashMap<String, VehicleSubClassType>();
    private static final Map<String, VehicleSubClassType> valueToVehicleSubClass = new HashMap<String, VehicleSubClassType>();

    static {

        for (VehicleSubClassType vehicleSubClass : EnumSet.allOf(VehicleSubClassType.class)) {
            labelToVehicleSubClass.put(vehicleSubClass.getLabel(), vehicleSubClass);
        }
        for (VehicleSubClassType vehicleSubClass : EnumSet.allOf(VehicleSubClassType.class)) {
        	valueToVehicleSubClass.put(vehicleSubClass.getValue(), vehicleSubClass);
        }
    }

    private String value;
    private String label;

    private VehicleSubClassType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static VehicleSubClassType getVehicleclass(String label) {
        if(ObjectsUtil.isNull(label))
            return null;
        else if (labelToVehicleSubClass.containsKey(label.toUpperCase()))
            return ObjectsUtil.isNull(label) ? null : labelToVehicleSubClass.get(label.toUpperCase());
        else
            return null;
    }

    public static VehicleSubClassType getVehicleSubClass(String value) {
        return valueToVehicleSubClass.get(value);
    }

}
