package org.rta.core.enums;

import java.util.HashMap;
import java.util.Map;

public enum OfficeType {
    MVI("MVI"), RLA("RLA"), ASST_RTO_A("ASST.RTO (A)"), RTA("RTA"), ADDL_RTO("ADDL.RTO"), ASST_RTO("ASST.RTO"),
    DT("DT"), UNI("UNI"), SECRETARY("SECRETARY"), DTO("DTO"), RTO("RTO"), DY_DIRECTOR("DY.DIRECTOR"), STA("STA"),
    ASST_DTO("ASST.DTO"), CP("CP");

    private static Map<String, OfficeType> labelToOfficeType = new HashMap<>();

    private String label;

    static {
        for (OfficeType officeType : OfficeType.values()) {
            labelToOfficeType.put(officeType.getLabel(), officeType);
        }
    }

    private OfficeType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static OfficeType getOfficeType(String label) {
        return labelToOfficeType.get(label);
    }
}
