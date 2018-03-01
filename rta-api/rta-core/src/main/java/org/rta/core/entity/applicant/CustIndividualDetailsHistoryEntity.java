package org.rta.core.entity.applicant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

@Entity
@Table(name = "cust_individual_details_history")
public class CustIndividualDetailsHistoryEntity extends BaseEntity {

	private static final long serialVersionUID = -4758646731734062869L;

    @Id
    @Column(name = "cust_ind_dtl_history_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_history_seq")
    @SequenceGenerator(name = "user_history_seq", sequenceName = "user_history_seq", allocationSize = 1)
    private Long custIndDtlHistoryId;

	@Column(name = "cust_ind_dtl_id")
	private Long custIndDtlId;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRcId;

    @NotNull
    @Column(name = "display_name")
	private String displayName;

    @NotNull
    @Column(name = "first_name")
	private String firstName;

    @NotNull
    @Column(name = "sur_name")
	private String surName;

    @NotNull
    @Column(name = "father_name")
	private String fatherName;

    @NotNull
    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @NotNull
	@Column(name = "gender")
	private String gender;

    @Column(name = "is_disabled")
	private boolean isDisabled;

    @Column(name = "is_invalid_carriage")
	private boolean isInvalidCarriage;

    @Column(name = "is_second_vehicle")
	private boolean isSecondVehicle;

    @Column(name = "aadhar_no")
	private String aadharNo;

    @Column(name = "passport_no")
	private String passportNo;

    @Column(name = "passport_valid_upto")
	private String passportValidUpto;

    @Column(name = "pan_no")
    private String panNo;

    @Column(name = "is_same_as_addr_aadhar")
	private boolean isSameAsAddrAadhar;

    @Column(name = "vechicle_reg_rta")
	private String vechicleRegRta;

    @Column(name = "vehicle_reg_rta_code")
	private String vehicleRegRtaCode;

	@Column(name = "alternate_mobile_no")
	private String alternateMobileNo;

	@NotNull
    @Column(name = "mobile_no")
    private String mobileNo;

    @NotNull
    @Column(name = "email_id")
    private String emailId;

    @Column(name = "emergency_no")
	private String emergencyNo;

    @Column(name = "permanent_phone_no")
	private String permanentPhoneNo;

    @Column(name = "alternate_email")
	private String alternateEmail;

    @Column(name = "status")
	private String status;

	@Column(name = "qualification_id")
	private Long qualificationId;
	
	@Column(name = "blood_group", length = 10)
    private String bloodGroup;
	
	@Column(name = "is_dependent_addrs", columnDefinition = "boolean default false")
	private boolean isDependentAddrs;
	
	@Column(name = "address_person", length = 50)
    private String addressPerson;
	
	@Column(name = "name_of_dependent")
    private String nameOfDependent;

    @Column(name = "service_code")
    private String serviceCode;
    
    @Column(name = "ownership_start_date")
    private Long ownershipStartDate;
    
    @Column(name = "ownership_end_date")
    private Long ownershipEndDate;
    
	public String getNameOfDependent() {
		return nameOfDependent;
	}

	public void setNameOfDependent(String nameOfDependent) {
		this.nameOfDependent = nameOfDependent;
	}

	public String getAlternateMobileNo() {
		return alternateMobileNo;
	}

	public void setAlternateMobileNo(String alternateMobileNo) {
		this.alternateMobileNo = alternateMobileNo;
	}

    public Long getCustIndDtlId() {
        return custIndDtlId;
    }

    public void setCustIndDtlId(Long custIndDtlId) {
        this.custIndDtlId = custIndDtlId;
    }

	public VehicleRCEntity getVehicleRcId() {
        return vehicleRcId;
    }

	public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

	public boolean getIsDisabled() {
        return isDisabled;
    }

	public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

	public boolean getIsInvalidCarriage() {
        return isInvalidCarriage;
    }

	public void setIsInvalidCarriage(boolean isInvalidCarriage) {
        this.isInvalidCarriage = isInvalidCarriage;
    }

	public boolean getIsSecondVehicle() {
        return isSecondVehicle;
    }

	public void setIsSecondVehicle(boolean isSecondVehicle) {
        this.isSecondVehicle = isSecondVehicle;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassportValidUpto() {
        return passportValidUpto;
    }

    public void setPassportValidUpto(String passportValidUpto) {
        this.passportValidUpto = passportValidUpto;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

	public boolean getIsSameAsAddrAadhar() {
        return isSameAsAddrAadhar;
    }

	public void setIsSameAsAddrAadhar(boolean isSameAsAddrAadhar) {
        this.isSameAsAddrAadhar = isSameAsAddrAadhar;
    }


    public String getVechicleRegRta() {
        return vechicleRegRta;
    }

    public void setVechicleRegRta(String vechicleRegRta) {
        this.vechicleRegRta = vechicleRegRta;
    }

    public String getVehicleRegRtaCode() {
        return vehicleRegRtaCode;
    }

    public void setVehicleRegRtaCode(String vehicleRegRtaCode) {
        this.vehicleRegRtaCode = vehicleRegRtaCode;
    }



    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmergencyNo() {
        return emergencyNo;
    }

    public void setEmergencyNo(String emergencyNo) {
        this.emergencyNo = emergencyNo;
    }

    public String getPermanentPhoneNo() {
        return permanentPhoneNo;
    }

    public void setPermanentPhoneNo(String permanentPhoneNo) {
        this.permanentPhoneNo = permanentPhoneNo;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public Long getQualificationId() {
        return qualificationId;
    }

	public void setQualificationId(Long qualificationId) {
        this.qualificationId = qualificationId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public boolean isDependentAddrs() {
        return isDependentAddrs;
    }

    public void setDependentAddrs(boolean isDependentAddrs) {
        this.isDependentAddrs = isDependentAddrs;
    }

    public String getAddressPerson() {
        return addressPerson;
    }

    public void setAddressPerson(String addressPerson) {
        this.addressPerson = addressPerson;
    }

    public Long getCustIndDtlHistoryId() {
        return custIndDtlHistoryId;
    }

    public void setCustIndDtlHistoryId(Long custIndDtlHistoryId) {
        this.custIndDtlHistoryId = custIndDtlHistoryId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Long getOwnershipStartDate() {
        return ownershipStartDate;
    }

    public void setOwnershipStartDate(Long ownershipStartDate) {
        this.ownershipStartDate = ownershipStartDate;
    }

    public Long getOwnershipEndDate() {
        return ownershipEndDate;
    }

    public void setOwnershipEndDate(Long ownershipEndDate) {
        this.ownershipEndDate = ownershipEndDate;
    }

}
