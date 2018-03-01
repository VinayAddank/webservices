package org.rta.core.enums.master;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


public enum TaxType {

    FIRST_QUARTERLY(1, "Quarterly Tax" , "FQ"), LIFE_TAX(2, "Life Tax" , "LT") , HALFYEARLY_TAX(6, "HALFYEARLY Tax" , "HY") , ANNUAL_TAX(12, "ANNUAL Tax" , "AT");

    private Integer value;
    private String label;
    private String code;

    private TaxType(Integer value, String label  ,String code) {
        this.value = value;
        this.label = label;
        this.code  = code;
    }

    private static Map<Integer, String> map;

    static {
        Integer count = TaxType.values().length;
        map = new LinkedHashMap<>(count);
        for (TaxType taxType : TaxType.values()) {
            map.put(taxType.value, taxType.label);
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

    public static TaxType getTaxType(String label) {
        if (!Objects.isNull(label)) {
            if (TaxType.LIFE_TAX.label.equalsIgnoreCase(label)) {
                return TaxType.LIFE_TAX;
            }
            if (TaxType.FIRST_QUARTERLY.label.equalsIgnoreCase(label)) {
                return TaxType.FIRST_QUARTERLY;
            }
            if (TaxType.HALFYEARLY_TAX.label.equalsIgnoreCase(label)) {
                return TaxType.HALFYEARLY_TAX;
            }
            if (TaxType.ANNUAL_TAX.label.equalsIgnoreCase(label)) {
                return TaxType.ANNUAL_TAX;
            }
        }
        return null;
    }

    public static TaxType getTaxType(Integer value) {
        if (!Objects.isNull(value)) {
            if (TaxType.LIFE_TAX.value == value) {
                return TaxType.LIFE_TAX;
            }
            if (TaxType.FIRST_QUARTERLY.value == value) {
                return TaxType.FIRST_QUARTERLY;
            }
            if (TaxType.HALFYEARLY_TAX.value == value ) {
                return TaxType.HALFYEARLY_TAX;
            }
            if (TaxType.ANNUAL_TAX.value == value) {
                return TaxType.ANNUAL_TAX;
            }
        }
        return null;
    }
    
    public static TaxType getTaxTypeByCode(String code) {
        if (!Objects.isNull(code)) {
            if (TaxType.LIFE_TAX.code.equalsIgnoreCase(code)) {
                return TaxType.LIFE_TAX;
            }
            if (TaxType.FIRST_QUARTERLY.code.equalsIgnoreCase(code)) {
                return TaxType.FIRST_QUARTERLY;
            }
            if (TaxType.HALFYEARLY_TAX.code.equalsIgnoreCase(code)) {
                return TaxType.HALFYEARLY_TAX;
            }
            if (TaxType.ANNUAL_TAX.code.equalsIgnoreCase(code)) {
                return TaxType.ANNUAL_TAX;
            }
        }
        return null;
    }

    public static Map<Integer, String> getAll() {
        return map;
    }
}