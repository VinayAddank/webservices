/**
 * 
 */
package org.rta.core.enums;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public enum VcrPaymentStatus {

    PAID(1, "Paid", "C"), NOT_PAID(2, "Not Paid", "O"), NOT_APPROVED(3, "Not Approved", "P");

    private Integer value;
    private String label;
    private String code;

    private VcrPaymentStatus(Integer value, String label, String code) {
        this.value = value;
        this.label = label;
        this.code = code;
    }

    private static Map<Integer, String> map;

    static {
        Integer count = VcrPaymentStatus.values().length;
        map = new LinkedHashMap<>(count);
        for (VcrPaymentStatus vcrStatus : VcrPaymentStatus.values()) {
            map.put(vcrStatus.value, vcrStatus.label);
        }
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public String getCode() {
        return code;
    }

    public static VcrPaymentStatus getVcrStatus(String label) {
        if (!Objects.isNull(label)) {
            if (VcrPaymentStatus.PAID.label.equals(label)) {
                return VcrPaymentStatus.PAID;
            }
            if (VcrPaymentStatus.NOT_PAID.label.equals(label)) {
                return VcrPaymentStatus.NOT_PAID;
            }
            if (VcrPaymentStatus.NOT_APPROVED.label.equals(label)) {
                return VcrPaymentStatus.NOT_APPROVED;
            }
        }
        return null;
    }

    public static VcrPaymentStatus getVcrStatus(Integer value) {
        if (!Objects.isNull(value)) {
            if (VcrPaymentStatus.PAID.value == value) {
                return VcrPaymentStatus.PAID;
            }
            if (VcrPaymentStatus.NOT_PAID.value == value) {
                return VcrPaymentStatus.NOT_PAID;
            }
            if (VcrPaymentStatus.NOT_APPROVED.value.equals(value)) {
                return VcrPaymentStatus.NOT_APPROVED;
            }
        }
        return null;
    }

    public static VcrPaymentStatus getVcrStatusByCode(String code) {
        if (!Objects.isNull(code)) {
            if (VcrPaymentStatus.PAID.code.equals(code)) {
                return VcrPaymentStatus.PAID;
            }
            if (VcrPaymentStatus.NOT_PAID.code.equals(code)) {
                return VcrPaymentStatus.NOT_PAID;
            }
            if (VcrPaymentStatus.NOT_APPROVED.code.equals(code)) {
                return VcrPaymentStatus.NOT_APPROVED;
            }
        }
        return null;
    }

    public static Map<Integer, String> getAll() {
        return map;
    }

}
