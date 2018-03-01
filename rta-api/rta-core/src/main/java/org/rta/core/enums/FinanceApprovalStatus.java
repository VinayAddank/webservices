package org.rta.core.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.rta.core.utils.ObjectsUtil;

public enum FinanceApprovalStatus {
	    STEP1_DEALER_REQUEST(31, "STEP1 DEALER REQUEST"),
	    STEP2_FINANCER_PENDING(32, "STEP2 FINANCER PENDING"),
	    STEP2_FINANCER_REJECTED(33, "STEP2 FINANCER REJECTED"),
	    STEP2_FINANCER_ACCEPTED(34, "STEP2 FINANCER ACCEPTED"),
	    STEP3_DEALER_CONFIRM(35, "STEP3 DEALER CONFIRM"),
	    STEP3_DEALER_REFUSE(36, "STEP3 DEALER REFUSE"),
	    STEP4_FINANCER_APPROVED(37, "STEP4 FINANCER APPROVED"),
	    STEP4_FINANCER_REJECTED(38, "STEP4 FINANCER REJECTED")  ;

	    private static final Map<Integer, String> lookup = new HashMap<Integer, String>();
	    private static final Map<String, FinanceApprovalStatus> labelToStatus = new HashMap<String, FinanceApprovalStatus>();
	    private static final Map<Integer, FinanceApprovalStatus> valueToStatus = new HashMap<Integer, FinanceApprovalStatus>();

	    static {
	        for (FinanceApprovalStatus status : EnumSet.allOf(FinanceApprovalStatus.class)) {
	            lookup.put(status.getValue(), status.getLabel());
	        }
	        for (FinanceApprovalStatus status : EnumSet.allOf(FinanceApprovalStatus.class)) {
	            labelToStatus.put(status.getLabel(), status); 
	        }
	        for (FinanceApprovalStatus status : EnumSet.allOf(FinanceApprovalStatus.class)) {
	            valueToStatus.put(status.getValue(), status); 
	        }
	    }

	    private Integer value;
	    private String label;

	    private FinanceApprovalStatus(Integer valuesd, String label) {
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
	    
	    public static FinanceApprovalStatus getStatus(String label) {
	        return ObjectsUtil.isNull(label) ? null : labelToStatus.get(label.toUpperCase());
	    }
	    
	    public static FinanceApprovalStatus getStatus(Integer value) {
	        return valueToStatus.get(value);
	    }
	    

}
