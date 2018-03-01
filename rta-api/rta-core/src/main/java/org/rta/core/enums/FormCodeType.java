package org.rta.core.enums;

import java.util.HashMap;
import java.util.Map;

public enum FormCodeType {
	//citizen service
    VA_FORM("va_form"), NOC_FORM("noc_form"), CC_FORM("cc_form"), AC_FORM("ac_form"), RR_FORM("rr_form"), HPT_FORM("hpt_form"), HPA_FORM("hpa_form"),
    OTA_FORM("ota_form"), OTD_FORM("otd_form"), OTS_FORM("ots_form"), DR_FORM("dr_form"), TI_FORM("ti_form"), RC_FORM("rc_form"),RSC_FORM("rsc_form"), SR_FORM("sr_form"),
    VR_FORM("vr_form"),
    //user service
    FINREG_FORM("finreg_form"), DEALERREG_FORM("dealerreg_form"), BBREG_FORM("bbreg_form"), AAREG_FORM("aareg_form"),PUCREG_FORM("pucreg_form"),
    HAZVEHTRINSTREG_FORM("hazvehtrinstreg_form"), DRIVINGINSTREG_FORM("drivinginstreg_form"), MEDPRTSNRREG_FORM("medprtsnrreg_form"),
    //LL/DL service
    LLF_MEDICAL_FORM("llf_medical_form"), LLF_DETAIL_FORM("llf_detail_form"), LLD_MEDICAL_FORM("lld_medical_form"), LLD_DETAIL_FORM("lld_detail_form"),
    LLE_MEDICAL_FORM("lle_medical_form"), LLE_DETAIL_FORM("lle_detail_form"), PERMANENT_ADDRESS_FORM("permanent_address_form"),
    //permit fitness form codes
    PCF_FORM("pcf_form"), PCRAC_FORM("pcrac_form"), PCV_FORM("pcv_form"), FCF_MVI_FORM("fcf_mvi_form"),PCS_FORM("pcs_form"), FRF_FORM("frf_form"),
    // Tax related
    ST_FORM("st_form"),ST_MVI_FORM("st_mvi_form");
   
	
	private static Map<String, FormCodeType> labelToFormCodeType = new HashMap<>();

    private String label;

    static {
        for (FormCodeType formCodeType : FormCodeType.values()) {
            labelToFormCodeType.put(formCodeType.getLabel(), formCodeType);
        }
    }

    private FormCodeType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static FormCodeType getFormCodeType(String label) {
        return labelToFormCodeType.get(label);
    }
}
