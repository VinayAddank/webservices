package org.rta.core.model.export;
/**
 *	@Author sohan.maurya created on Oct 4, 2016.
 */
public class FormsModel {

    private RtaOfficeDetails rtaOfficeDetails;
    private OwnerDetails ownerDetails;
    private String ownerDateOfBirth;
    private DealerDetails dealerDetails;
    private InsuranceDetails insuranceDetails;
    private VehicleDetails vehicleDetails;
    private String makersName;
    private String makersClass;
    private String rlw;
    private String registrationType;
    private Long invoiceDate;
    private FinancerDetails financerDetails;
    private SignatureDetails signatureDetails;
    private OwnerPermAddressDetails ownerPermAddDetails;

    public RtaOfficeDetails getRtaOfficeDetails() {
        return rtaOfficeDetails;
    }

    public void setRtaOfficeDetails(RtaOfficeDetails rtaOfficeDetails) {
        this.rtaOfficeDetails = rtaOfficeDetails;
    }

    public OwnerDetails getOwnerDetails() {
        return ownerDetails;
    }

    public void setOwnerDetails(OwnerDetails ownerDetails) {
        this.ownerDetails = ownerDetails;
    }

    public String getOwnerDateOfBirth() {
        return ownerDateOfBirth;
    }

    public void setOwnerDateOfBirth(String ownerDateOfBirth) {
        this.ownerDateOfBirth = ownerDateOfBirth;
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

    public String getMakersName() {
        return makersName;
    }

    public void setMakersName(String makersName) {
        this.makersName = makersName;
    }

    public String getMakersClass() {
        return makersClass;
    }

    public void setMakersClass(String makersClass) {
        this.makersClass = makersClass;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public String getRlw() {
        return rlw;
    }

    public void setRlw(String rlw) {
        this.rlw = rlw;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public Long getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Long invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public FinancerDetails getFinancerDetails() {
        return financerDetails;
    }

    public void setFinancerDetails(FinancerDetails financerDetails) {
        this.financerDetails = financerDetails;
    }

    public SignatureDetails getSignatureDetails() {
        return signatureDetails;
    }

    public void setSignatureDetails(SignatureDetails signatureDetails) {
        this.signatureDetails = signatureDetails;
    }

    public OwnerPermAddressDetails getOwnerPermAddDetails() {
        return ownerPermAddDetails;
    }

    public void setOwnerPermAddDetails(OwnerPermAddressDetails ownerPermAddDetails) {
        this.ownerPermAddDetails = ownerPermAddDetails;
    }
}
