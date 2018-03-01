package org.rta.core.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.rta.core.utils.ObjectsUtil;

/**
 * this enum use to identify vehicle Type like two wheelers, four wheelers
 * 
 * @Author sohan.maurya created on Aug 25, 2016.
 */
public enum VehicleType {

    ALL_VEHICLE(100, "ALL_VEHICLE"), TWO_WHEELERS(2, "L"), FOUR_WHEELERS(4, "M"), OTHER_VEHICLE(99, "OTHER_VEHICLE");


    private static final Map<String, VehicleType> labelToVehicleType = new HashMap<String, VehicleType>();
    private static final Map<Integer, VehicleType> valueToVehicleType = new HashMap<Integer, VehicleType>();

    static {

        for (VehicleType vehicleType : EnumSet.allOf(VehicleType.class)) {
            labelToVehicleType.put(vehicleType.getLabel(), vehicleType);
        }
        for (VehicleType vehicleClass : EnumSet.allOf(VehicleType.class)) {
            valueToVehicleType.put(vehicleClass.getValue(), vehicleClass);
        }
    }

    private Integer value;
    private String label;

    private VehicleType(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static VehicleType getVehicleType(String label) {
        if(ObjectsUtil.isNull(label))
            return null;
        else if (labelToVehicleType.containsKey(label.toUpperCase()))
            return ObjectsUtil.isNull(label) ? null : labelToVehicleType.get(label.toUpperCase());
        else
            return VehicleType.OTHER_VEHICLE;
    }

    public static VehicleType getVehicleType(Integer value) {
        return valueToVehicleType.get(value);
    }

}
