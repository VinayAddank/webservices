package org.rta.core.model.cardprinter;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * update print date model and return to response
 * 
 * @Author sohan.maurya created on Sep 20, 2016.
 */

@JsonInclude(Include.NON_NULL)
public class PrintStatusModel {

    @NotNull(message = "Vehicle Registration Number is missing")
    private String vehicleRegNo;
    @NotNull(message = "RC Card Print Date is missing")
    private String rcPrintDate;
    @NotNull(message = "is Printed(true) is missing")
    private Boolean isPrinted;

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public String getRcPrintDate() {
        return rcPrintDate;
    }

    public void setRcPrintDate(String rcPrintDate) {
        this.rcPrintDate = rcPrintDate;
    }

    public Boolean getIsPrinted() {
        return isPrinted;
    }

    public void setIsPrinted(Boolean isPrinted) {
        this.isPrinted = isPrinted;
    }


}
