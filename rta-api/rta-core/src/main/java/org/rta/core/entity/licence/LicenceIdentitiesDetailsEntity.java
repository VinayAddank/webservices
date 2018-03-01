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
import org.rta.core.entity.office.NocAddressEntity;


/**
 * The persistent class for the license_identities_details database table.
 * 
 */
@Entity
@Table(name="license_identities_details")
public class LicenceIdentitiesDetailsEntity extends BaseCommonEntity implements Serializable {

    private static final long serialVersionUID = -130642670105807312L;

    @Id
	@Column(name = "license_identity_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "license_identities_seq")
	@SequenceGenerator(name = "license_identities_seq", sequenceName = "license_identities_seq", allocationSize = 1)
	private Long licenseIdentityId;

	@Column(name = "passport_number", unique = true)
	private String passportNumber;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "passport_valid_from")
	private Date passportValidFrom;

	@Temporal(TemporalType.DATE)
	@Column(name = "passport_valid_to")
	private Date passportValidTo;

	@Temporal(TemporalType.DATE)
	@Column(name = "visa_valid_from")
	private Date visaValidFrom;

	@Temporal(TemporalType.DATE)
	@Column(name = "visa_valid_to")
	private Date visaValidTo;

	@Column(name = "dl_number", unique = true)
	private String dlNumber;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dl_valid_from")
	private Date dlValidFrom;

	@Temporal(TemporalType.DATE)
	@Column(name = "dl_valid_to")
	private Date dlValidTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rta_office_address_id", nullable= true)
    private NocAddressEntity rtaOfficeAddressId;

	public Long getLicenseIdentityId() {
		return licenseIdentityId;
	}

	public void setLicenseIdentityId(Long licenseIdentityId) {
		this.licenseIdentityId = licenseIdentityId;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Date getPassportValidFrom() {
		return passportValidFrom;
	}

	public void setPassportValidFrom(Date passportValidFrom) {
		this.passportValidFrom = passportValidFrom;
	}

	public Date getPassportValidTo() {
		return passportValidTo;
	}

	public void setPassportValidTo(Date passportValidTo) {
		this.passportValidTo = passportValidTo;
	}

	public Date getVisaValidFrom() {
		return visaValidFrom;
	}

	public void setVisaValidFrom(Date visaValidFrom) {
		this.visaValidFrom = visaValidFrom;
	}

	public Date getVisaValidTo() {
		return visaValidTo;
	}

	public void setVisaValidTo(Date visaValidTo) {
		this.visaValidTo = visaValidTo;
	}

	public String getDlNumber() {
		return dlNumber;
	}

	public void setDlNumber(String dlNumber) {
		this.dlNumber = dlNumber;
	}

	public Date getDlValidFrom() {
		return dlValidFrom;
	}

	public void setDlValidFrom(Date dlValidFrom) {
		this.dlValidFrom = dlValidFrom;
	}

	public Date getDlValidTo() {
		return dlValidTo;
	}

	public void setDlValidTo(Date dlValidTo) {
		this.dlValidTo = dlValidTo;
	}

	public NocAddressEntity getRtaOfficeAddressId() {
		return rtaOfficeAddressId;
	}

	public void setRtaOfficeAddressId(NocAddressEntity rtaOfficeAddressId) {
		this.rtaOfficeAddressId = rtaOfficeAddressId;
	}
    
}