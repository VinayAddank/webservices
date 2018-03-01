package org.rta.core.entity.licence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
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
 * The persistent class for the license_holder_dtls database table.
 * 
 */
@Entity
@Table(name = "license_holder_dtls")
public class LicenseHolderDtlsEntity extends BaseCommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "licence_holder_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "licence_holder_seq")
	@SequenceGenerator(name = "licence_holder_seq", sequenceName = "licence_holder_seq", allocationSize = 1)
	private Long licenceHolderId;

	@Column(name = "aadhaar_no", unique = true)
	private String aadhaarNo;

	@Column(name = "blood_donor")
	private String bloodDonor;

	@Column(name = "blood_grp")
	private String bloodGrp;

	@Column(name = "cfst_applicant_id")
	private String cfstApplicantId;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@Column(name = "display_name")
	private String displayName;

	@Column(name = "electoral_number")
	private String electoralNumber;

	private String email;

	@Column(name = "first_name")
	private String firstName;

	private String firstaidcertified;

	@Column(name = "foreign_military")
	private String foreignMilitary;

	private String gender;

	@Column(name = "guardian_name")
	private String guardianName;

	@Column(name = "handicap_dtls")
	private String handicapDtls;

	@Column(name = "is_active")
	private String isActive;

	@Column(name = "is_adhar_verify")
	private String isAdharVerify;

	@Column(name = "is_handicapped")
	private String isHandicapped;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "mobile_no")
	private String mobileNo;

	private String nationality;

	@Column(name = "organ_donor")
	private String organDonor;

	@Column(name = "otherstate_cd")
	private String otherstateCd;

	@Column(name = "pan_no")
	private String panNo;

	@Column(name = "perm_addr_country")
	private String permAddrCountry;
	
	@Column(name = "perm_addr_door_no")
	private String permAddrDoorNo;

	@Column(name = "perm_addr_pin_code")
	private String permAddrPinCode;

	@Column(name = "perm_addr_street")
	private String permAddrStreet;

	@Column(name = "perm_addr_town")
	private String permAddrTown;

	@Column(name = "pres_addr_country_id")
	private Long presAddrCountryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pres_addr_district_id")
	private DistrictEntity presAddrDistrictId;

	@Column(name = "pres_addr_door_no")
	private String presAddrDoorNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pres_addr_mandal_id")
	private MandalEntity presAddrMandalId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pres_addr_state_id")
	private StateEntity presAddrStateId;

	@Column(name = "pres_addr_pin_code")
	private String presAddrPinCode;

	@Column(name = "pres_addr_street")
	private String presAddrStreet;

	@Column(name = "pres_addr_town")
	private String presAddrTown;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "qualification_id", nullable= true)
	private QualificationEntity qualificationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rta_office_code")
	private RtaOfficeEntity rtaOfficeId;

	@Column(name = "ticket_details")
	private String ticketDetails;

	private String twotire;

	@Column(name = "perm_addr_district")
	private String permAddrDistrict;

	@Column(name = "perm_addr_state")
	private String permAddrState;

	@Column(name = "perm_addr_mandal")
	private String permAddrMandal;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "licence_identity_id", nullable= true)
	private LicenceIdentitiesDetailsEntity licenceIdentityId;
	
	@Column(name = "is_same_as_aadhaar", columnDefinition = "boolean default false")
	private boolean isSameAsAadhaar;

	public Long getLicenceHolderId() {
		return this.licenceHolderId;
	}

	public void setLicenceHolderId(Long licenceHolderId) {
		this.licenceHolderId = licenceHolderId;
	}

	public String getAadhaarNo() {
		return this.aadhaarNo;
	}

	public void setAadhaarNo(String aadhaarNo) {
		this.aadhaarNo = aadhaarNo;
	}

	public String getBloodDonor() {
		return this.bloodDonor;
	}

	public void setBloodDonor(String bloodDonor) {
		this.bloodDonor = bloodDonor;
	}

	public String getBloodGrp() {
		return this.bloodGrp;
	}

	public void setBloodGrp(String bloodGrp) {
		this.bloodGrp = bloodGrp;
	}

	public String getCfstApplicantId() {
		return this.cfstApplicantId;
	}

	public void setCfstApplicantId(String cfstApplicantId) {
		this.cfstApplicantId = cfstApplicantId;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getElectoralNumber() {
		return this.electoralNumber;
	}

	public void setElectoralNumber(String electoralNumber) {
		this.electoralNumber = electoralNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsAdharVerify() {
		return this.isAdharVerify;
	}

	public void setIsAdharVerify(String isAdharVerify) {
		this.isAdharVerify = isAdharVerify;
	}

	public String getIsHandicapped() {
		return this.isHandicapped;
	}

	public void setIsHandicapped(String isHandicapped) {
		this.isHandicapped = isHandicapped;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getOrganDonor() {
		return this.organDonor;
	}

	public void setOrganDonor(String organDonor) {
		this.organDonor = organDonor;
	}

	public String getOtherstateCd() {
		return this.otherstateCd;
	}

	public void setOtherstateCd(String otherstateCd) {
		this.otherstateCd = otherstateCd;
	}

	public String getPanNo() {
		return this.panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getPermAddrCountry() {
		return this.permAddrCountry;
	}

	public void setPermAddrCountry(String permAddrCountry) {
		this.permAddrCountry = permAddrCountry;
	}

	public String getPermAddrDoorNo() {
		return this.permAddrDoorNo;
	}

	public void setPermAddrDoorNo(String permAddrDoorNo) {
		this.permAddrDoorNo = permAddrDoorNo;
	}

	public String getPermAddrPinCode() {
		return this.permAddrPinCode;
	}

	public void setPermAddrPinCode(String permAddrPinCode) {
		this.permAddrPinCode = permAddrPinCode;
	}

	public String getPermAddrStreet() {
		return this.permAddrStreet;
	}

	public void setPermAddrStreet(String permAddrStreet) {
		this.permAddrStreet = permAddrStreet;
	}

	public String getPermAddrTown() {
		return this.permAddrTown;
	}

	public void setPermAddrTown(String permAddrTown) {
		this.permAddrTown = permAddrTown;
	}

	public Long getPresAddrCountryId() {
		return this.presAddrCountryId;
	}

	public void setPresAddrCountryId(Long presAddrCountryId) {
		this.presAddrCountryId = presAddrCountryId;
	}

	public DistrictEntity getPresAddrDistrictId() {
		return this.presAddrDistrictId;
	}

	public void setPresAddrDistrictId(DistrictEntity presAddrDistrictId) {
		this.presAddrDistrictId = presAddrDistrictId;
	}

	public String getPresAddrDoorNo() {
		return this.presAddrDoorNo;
	}

	public void setPresAddrDoorNo(String presAddrDoorNo) {
		this.presAddrDoorNo = presAddrDoorNo;
	}

	public MandalEntity getPresAddrMandalId() {
		return this.presAddrMandalId;
	}

	public void setPresAddrMandalId(MandalEntity presAddrMandalId) {
		this.presAddrMandalId = presAddrMandalId;
	}

	public String getPresAddrPinCode() {
		return this.presAddrPinCode;
	}

	public void setPresAddrPinCode(String presAddrPinCode) {
		this.presAddrPinCode = presAddrPinCode;
	}

	public String getPresAddrStreet() {
		return this.presAddrStreet;
	}

	public void setPresAddrStreet(String presAddrStreet) {
		this.presAddrStreet = presAddrStreet;
	}

	public String getPresAddrTown() {
		return this.presAddrTown;
	}

	public void setPresAddrTown(String presAddrTown) {
		this.presAddrTown = presAddrTown;
	}

	public QualificationEntity getQualificationId() {
		return this.qualificationId;
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

	public String getTicketDetails() {
		return this.ticketDetails;
	}

	public void setTicketDetails(String ticketDetails) {
		this.ticketDetails = ticketDetails;
	}

	public String getTwotire() {
		return this.twotire;
	}

	public void setTwotire(String twotire) {
		this.twotire = twotire;
	}

	public StateEntity getPresAddrStateId() {
		return presAddrStateId;
	}

	public void setPresAddrStateId(StateEntity presAddrStateId) {
		this.presAddrStateId = presAddrStateId;
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

	public LicenceIdentitiesDetailsEntity getLicenceIdentityId() {
		return licenceIdentityId;
	}
	
	public void setLicenceIdentityId(LicenceIdentitiesDetailsEntity licenceIdentityId) {
		this.licenceIdentityId = licenceIdentityId;
	}

	public boolean isSameAsAadhaar() {
		return isSameAsAadhaar;
	}

	public void setSameAsAadhaar(boolean isSameAsAadhaar) {
		this.isSameAsAadhaar = isSameAsAadhaar;
	}
	
}