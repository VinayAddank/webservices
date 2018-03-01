package org.rta.core.model.export;

import java.util.List;

import org.rta.core.model.docs.AttachmentDetailsModel;

/**
 *	@Author sohan.maurya created on Sep 16, 2016.
 */
public class SinkAllData {
    
    private String application;
    private OwnerDetails ownerDetails;
    private VehicleRegistrationDetails vehicleRegistrationDetails;
    private DealerDetails dealerDetails;
    private InsuranceDetails insuranceDetails;
    private VehicleDetails vehicleDetails;
    private TaxDetails taxDetails;
    private FinancerDetails financerDetails;
    private List<AttachmentDetailsModel> AttachmentDetails;
    private FitnessDetails fitnessDetails;
    private PermitDetails permitDetails;


    public OwnerDetails getOwnerDetails() {
        return ownerDetails;
    }

    public void setOwnerDetails(OwnerDetails ownerDetails) {
        this.ownerDetails = ownerDetails;
    }

    public VehicleRegistrationDetails getVehicleRegistrationDetails() {
        return vehicleRegistrationDetails;
    }

    public void setVehicleRegistrationDetails(VehicleRegistrationDetails vehicleRegistrationDetails) {
        this.vehicleRegistrationDetails = vehicleRegistrationDetails;
    }

    public DealerDetails getDealerDetails() {
        return dealerDetails;
    }

    public void setDealerDetails(DealerDetails dealerDetails) {
        this.dealerDetails = dealerDetails;
    }

    public InsuranceDetails getInsuranceDetails() {
        return insuranceDetails;
    }

    public void setInsuranceDetails(InsuranceDetails insuranceDetails) {
        this.insuranceDetails = insuranceDetails;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public TaxDetails getTaxDetails() {
        return taxDetails;
    }

    public void setTaxDetails(TaxDetails taxDetails) {
        this.taxDetails = taxDetails;
    }

    public FinancerDetails getFinancerDetails() {
        return financerDetails;
    }

    public void setFinancerDetails(FinancerDetails financerDetails) {
        this.financerDetails = financerDetails;
    }

    public List<AttachmentDetailsModel> getAttachmentDetails() {
        return AttachmentDetails;
    }

    public void setAttachmentDetails(List<AttachmentDetailsModel> attachmentDetails) {
        AttachmentDetails = attachmentDetails;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

	public FitnessDetails getFitnessDetails() {
		return fitnessDetails;
	}

	public void setFitnessDetails(FitnessDetails fitnessDetails) {
		this.fitnessDetails = fitnessDetails;
	}

	public PermitDetails getPermitDetails() {
		return permitDetails;
	}

	public void setPermitDetails(PermitDetails permitDetails) {
		this.permitDetails = permitDetails;
	}
}
