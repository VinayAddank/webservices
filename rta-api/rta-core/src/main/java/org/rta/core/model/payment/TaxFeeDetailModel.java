package org.rta.core.model.payment;

import org.rta.core.model.payment.registfee.RegFeeDetailModel;
import org.rta.core.model.payment.tax.DifferentialTaxFeeModel;
import org.rta.core.model.payment.tax.TaxDetailModel;

public class TaxFeeDetailModel {

    private TaxDetailModel taxDetailModel;
    private RegFeeDetailModel regFeeDetailModel;
    private DifferentialTaxFeeModel differentialTaxFeeModel;
    private String hsrpAmount;
    private String vehicleRcId;
    private String grandTotal;


    public TaxDetailModel getTaxDetailModel() {
        return taxDetailModel;
    }
    public void setTaxDetailModel(TaxDetailModel taxDetailModel) {
        this.taxDetailModel = taxDetailModel;
    }
    public RegFeeDetailModel getRegFeeDetailModel() {
        return regFeeDetailModel;
    }
    public void setRegFeeDetailModel(RegFeeDetailModel regFeeDetailModel) {
        this.regFeeDetailModel = regFeeDetailModel;
    }
    public String getHsrpAmount() {
        return hsrpAmount;
    }
    public void setHsrpAmount(String hsrpAmount) {
        this.hsrpAmount = hsrpAmount;
    }
    public String getVehicleRcId() {
        return vehicleRcId;
    }
    public void setVehicleRcId(String vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }
    public String getGrandTotal() {
        return grandTotal;
    }
    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }
	public DifferentialTaxFeeModel getDifferentialTaxFeeModel() {
		return differentialTaxFeeModel;
	}
	public void setDifferentialTaxFeeModel(DifferentialTaxFeeModel differentialTaxFeeModel) {
		this.differentialTaxFeeModel = differentialTaxFeeModel;
	}

}
