package org.rta.core.entity.licence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.rta.core.entity.base.BaseCommonEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.master.QualificationEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.office.RtaOfficeEntity;

/**
 * The persistent class for the license_holder_txns database table.
 * 
 */
@Entity
@Table(name = "license_holder_txns")
public class LicenseHolderTxnEntity extends BaseCommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LicenseHolderTxnPK id;

	@Column(name = "application_id")
	private String applicationId;

	@Column(name = "blood_donor")
	private String bloodDonor;// 3

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	private String email; // 8

	private String firstaidcertified; // 10

	@Column(name = "foreign_military")
	private String foreignMilitary; // 11

	@Column(name = "guardian_name")
	private String guardianName;// 12

	@Column(name = "handicap_dtls")
	private String handicapDtls; // 13

	@Column(name = "is_handicapped")
	private String isHandicapped; // 16

	@Column(name = "mobile_no")
	private String mobileNo; // 18

	private String nationality; // 19

	@Column(name = "old_sequence_id")
	private Integer oldSequenceId;

	@Column(name = "old_value")
	private String oldValue;

	@Column(name = "organ_donor")
	private String organDonor;// 20

	@Column(name = "passport_no")
	private String passportNo;

	@Temporal(TemporalType.DATE)
	@Column(name = "passport_valid_to")
	private Date passportValidTo;

	@Column(name = "reference_number")
	private String referenceNumber;

	@Column(name = "transaction_reason")
	private String transactionReason;

	@Column(name = "transaction_status")
	private String transactionStatus;

	@Column(name = "transaction_type")
	private String transactionType;

	// new entry
	@Column(name = "aadhaar_no")
	private String aadhaarNo;// 2

	@Column(name = "blood_grp")
	private String bloodGrp;// 4

	@Column(name = "cfst_applicant_id")
	private String cfstApplicantId; // 5

	@Column(name = "display_name")
	private String displayName; // 6

	@Column(name = "electoral_number")
	private String electoralNumber; // 7

	@Column(name = "first_name")
	private String firstName; // 9

	@Column(name = "is_active")
	private String isActive;// 14

	@Column(name = "is_adhar_verify")
	private String isAdharVerify; // 15

	@Column(name = "last_name")
	private String lastName; // 17

	@Column(name = "pan_no")
	private String panNo;// 23

	@Column(name = "perm_addr_country")
	private String permAddrCountry;// 24

	@Column(name = "perm_addr_door_no")
	private String permAddrDoorNo;// 25

	@Column(name = "perm_addr_pin_code")
	private String permAddrPinCode; // 26

	@Column(name = "perm_addr_street")
	private String permAddrStreet;// 27

	@Column(name = "perm_addr_town")
	private String permAddrTown;// 28

	@Column(name = "pres_addr_country_id")
	private String presAddrCountryId;// 29

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pres_addr_district_id")
	private DistrictEntity presAddrDistrictId; // 30

	@Column(name = "pres_addr_door_no")
	private String presAddrDoorNo; // 31

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pres_addr_mandal_id")
	private MandalEntity presAddrMandalId;// 32

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pres_addr_state_id")
	private StateEntity presAddrStateId; // 33

	@Column(name = "pres_addr_pin_code")
	private String presAddrPinCode; // 34

	@Column(name = "pres_addr_street")
	private String presAddrStreet; // 35

	@Column(name = "pres_addr_town")
	private String presAddrTown;// 36

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id", nullable = true)
	private QualificationEntity qualificationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rta_office_code")
	private RtaOfficeEntity rtaOfficeId;

	@Column(name = "ticket_details")
	private String ticketDetails;// 39

	@Column(name = "perm_addr_district")
	private String permAddrDistrict;// 40

	@Column(name = "perm_addr_state")
	private String permAddrState;// 41

	@Column(name = "perm_addr_mandal")
	private String permAddrMandal; // 42

	@Column(name = "otherstate_cd")
	private String otherstateCd;// 22

	private String gender;

	public LicenseHolderTxnPK getId() {
		return this.id;
	}

	public void setId(LicenseHolderTxnPK id) {
		this.id = id;
	}

	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getBloodDonor() {
		return this.bloodDonor;
	}

	public void setBloodDonor(String bloodDonor) {
		this.bloodDonor = bloodDonor;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstaidcertified() {
		return this.firstaidcertified;
	}

	public void setFirstaidcertified(String firstaidcertified) {
		this.firstaidcertified = firstaidcertified;
	}

	public String getForeignMilitary() {
		return this.foreignMilitary;
	}

	public void setForeignMilitary(String foreignMilitary) {
		this.foreignMilitary = foreignMilitary;
	}

	public String getGuardianName() {
		return this.guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getHandicapDtls() {
		return this.handicapDtls;
	}

	public void setHandicapDtls(String handicapDtls) {
		this.handicapDtls = handicapDtls;
	}

	public String getIsHandicapped() {
		return this.isHandicapped;
	}

	public void setIsHandicapped(String isHandicapped) {
		this.isHandicapped = isHandicapped;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Integer getOldSequenceId() {
		return this.oldSequenceId;
	}

	public void setOldSequenceId(Integer oldSequenceId) {
		this.oldSequenceId = oldSequenceId;
	}

	public String getOldValue() {
		return this.oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getOrganDonor() {
		return this.organDonor;
	}

	public void setOrganDonor(String organDonor) {
		this.organDonor = organDonor;
	}

	public String getPassportNo() {
		return this.passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public Date getPassportValidTo() {
		return this.passportValidTo;
	}

	public void setPassportValidTo(Date passportValidTo) {
		this.passportValidTo = passportValidTo;
	}

	public String getReferenceNumber() {
		return this.referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getTransactionReason() {
		return this.transactionReason;
	}

	public void setTransactionReason(String transactionReason) {
		this.transactionReason = transactionReason;
	}

	public String getTransactionStatus() {
		return this.transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getAadhaarNo() {
		return aadhaarNo;
	}

	public void setAadhaarNo(String aadhaarNo) {
		this.aadhaarNo = aadhaarNo;
	}

	public String getBloodGrp() {
		return bloodGrp;
	}

	public void setBloodGrp(String bloodGrp) {
		this.bloodGrp = bloodGrp;
	}

	public String getCfstApplicantId() {
		return cfstApplicantId;
	}

	public void setCfstApplicantId(String cfstApplicantId) {
		this.cfstApplicantId = cfstApplicantId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getElectoralNumber() {
		return electoralNumber;
	}

	public void setElectoralNumber(String electoralNumber) {
		this.electoralNumber = electoralNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsAdharVerify() {
		return isAdharVerify;
	}

	public void setIsAdharVerify(String isAdharVerify) {
		this.isAdharVerify = isAdharVerify;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getPermAddrCountry() {
		return permAddrCountry;
	}

	public void setPermAddrCountry(String permAddrCountry) {
		this.permAddrCountry = permAddrCountry;
	}

	public String getPermAddrDoorNo() {
		return permAddrDoorNo;
	}

	public void setPermAddrDoorNo(String permAddrDoorNo) {
		this.permAddrDoorNo = permAddrDoorNo;
	}

	public String getPermAddrPinCode() {
		return permAddrPinCode;
	}

	public void setPermAddrPinCode(String permAddrPinCode) {
		this.permAddrPinCode = permAddrPinCode;
	}

	public String getPermAddrStreet() {
		return permAddrStreet;
	}

	public void setPermAddrStreet(String permAddrStreet) {
		this.permAddrStreet = permAddrStreet;
	}

	public String getPermAddrTown() {
		return permAddrTown;
	}

	public void setPermAddrTown(String permAddrTown) {
		this.permAddrTown = permAddrTown;
	}

	public String getPresAddrCountryId() {
		return presAddrCountryId;
	}

	public void setPresAddrCountryId(String presAddrCountryId) {
		this.presAddrCountryId = presAddrCountryId;
	}

	public String getPresAddrDoorNo() {
		return presAddrDoorNo;
	}

	public void setPresAddrDoorNo(String presAddrDoorNo) {
		this.presAddrDoorNo = presAddrDoorNo;
	}

	public String getPresAddrPinCode() {
		return presAddrPinCode;
	}

	public void setPresAddrPinCode(String presAddrPinCode) {
		this.presAddrPinCode = presAddrPinCode;
	}

	public String getPresAddrStreet() {
		return presAddrStreet;
	}

	public void setPresAddrStreet(String presAddrStreet) {
		this.presAddrStreet = presAddrStreet;
	}

	public String getPresAddrTown() {
		return presAddrTown;
	}

	public void setPresAddrTown(String presAddrTown) {
		this.presAddrTown = presAddrTown;
	}

	public String getTicketDetails() {
		return ticketDetails;
	}

	public void setTicketDetails(String ticketDetails) {
		this.ticketDetails = ticketDetails;
	}

	public String getPermAddrDistrict() {
		return permAddrDistrict;
	}

	public void setPermAddrDistrict(String permAddrDistrict) {
		this.permAddrDistrict = permAddrDistrict;
	}

	public String getPermAddrState() {
		return permAddrState;
	}

	public void setPermAddrState(String permAddrState) {
		this.permAddrState = permAddrState;
	}

	public String getPermAddrMandal() {
		return permAddrMandal;
	}

	public void setPermAddrMandal(String permAddrMandal) {
		this.permAddrMandal = permAddrMandal;
	}

	public DistrictEntity getPresAddrDistrictId() {
		return presAddrDistrictId;
	}

	public void setPresAddrDistrictId(DistrictEntity presAddrDistrictId) {
		this.presAddrDistrictId = presAddrDistrictId;
	}

	public MandalEntity getPresAddrMandalId() {
		return presAddrMandalId;
	}

	public void setPresAddrMandalId(MandalEntity presAddrMandalId) {
		this.presAddrMandalId = presAddrMandalId;
	}

	public StateEntity getPresAddrStateId() {
		return presAddrStateId;
	}

	public void setPresAddrStateId(StateEntity presAddrStateId) {
		this.presAddrStateId = presAddrStateId;
	}

	public QualificationEntity getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(QualificationEntity qualificationId) {
		this.qualificationId = qualificationId;
	}

	public RtaOfficeEntity getRtaOfficeId() {
		return rtaOfficeId;
	}

	public void setRtaOfficeId(RtaOfficeEntity rtaOfficeId) {
		this.rtaOfficeId = rtaOfficeId;
	}

	public String getOtherstateCd() {
		return otherstateCd;
	}

	public void setOtherstateCd(String otherstateCd) {
		this.otherstateCd = otherstateCd;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}