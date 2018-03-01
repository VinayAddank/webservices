/**
 * 
 */
package org.rta.core.model.application;

import org.rta.core.model.customer.CustomerDetailsRequestModel;
import org.rta.core.model.export.DealerDetails;
import org.rta.core.model.export.SignatureDetails;
import org.rta.core.model.finance.FinanceModel;
import org.rta.core.model.insurance.InsuranceDetailsModel;
import org.rta.core.model.payment.tax.ApplicationTaxModel;
import org.rta.core.model.vehicle.VehicleDetailsRequestModel;

/**
 * @author arun.verma
 *
 */
public class GeneralDetails {

    private CustomerDetailsRequestModel customerDetails;
    private VehicleDetailsRequestModel vehicleDetails;
    private ApplicationTaxModel taxDetails;
    private FinanceModel financeDetails;
    private InsuranceDetailsModel insuranceDetails;
    private SignatureDetails signatureDetails;
    private DealerDetails dealerDetails;

    public CustomerDetailsRequestModel getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetailsRequestModel customerDetails) {
        this.customerDetails = customerDetails;
    }

    public VehicleDetailsRequestModel getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(VehicleDetailsRequestModel vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public ApplicationTaxModel getTaxDetails() {
        return taxDetails;
    }

    public void setTaxDetails(ApplicationTaxModel taxDetails) {
        this.taxDetails = taxDetails;
    }

    public FinanceModel getFinanceDetails() {
        return financeDetails;
    }

    public void setFinanceDetails(FinanceModel financeDetails) {
        this.financeDetails = financeDetails;
    }

    public InsuranceDetailsModel getInsuranceDetails() {
        return insuranceDetails;
    }

    public void setInsuranceDetails(InsuranceDetailsModel insuranceDetails) {
        this.insuranceDetails = insuranceDetails;
    }

    public SignatureDetails getSignatureDetails() {
        return signatureDetails;
    }

    public void setSignatureDetails(SignatureDetails signatureDetails) {
        this.signatureDetails = signatureDetails;
    }

    public DealerDetails getDealerDetails() {
        return dealerDetails;
    }

    public void setDealerDetails(DealerDetails dealerDetails) {
        this.dealerDetails = dealerDetails;
    }

}
