package org.rta.core.enums;

public enum DrivingInstituteUserInfoType {

    RC(1), DL(2);
    
    private Integer value;
    
    private DrivingInstituteUserInfoType(int value) {
        this.value = value;
    }
    
    public Integer getValue() {
        return this.value;
    }
    
}
