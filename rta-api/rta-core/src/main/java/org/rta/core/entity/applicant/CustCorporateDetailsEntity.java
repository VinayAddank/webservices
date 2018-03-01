package org.rta.core.entity.applicant;

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
import javax.persistence.Version;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

@Entity
@Table(name = "cust_corporate_details")
public class CustCorporateDetailsEntity extends BaseEntity {

	private static final long serialVersionUID = -1153112318466058659L;

	@Id
	@Column(name = "cust_corp_dtls_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
	private long custCorpDtlsId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRcId;
	
	//Name on RC
	@Column(name = "display_name")
	private String displayName;

	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "represented_By")
	private String representedBy;

	@Column(name = "aadhar_no")
	private String aadharNo;

	@Column(name = "pan_no")
	private String panNo;

	@Column(name = "vehicle_reg_rta")
	private String vehicleRegRta;

	@Column(name = "vehicle_reg_rta_code")
	private String vehicleRegRtaCode;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "mobile_no")
	private String mobile;

	@Column(name = "email")
	private String email;

	@Column(name = "emergency_no")
	private String emergencyNo;

	@Column(name = "permanent_phone_no")
	private String permanentPhoneNo;

	@Column(name = "aternate_email")
	private String aternateEmail;
	
	@Column(name = "aternate_phone_no")
	private String aternatePhoneNo;

	@Column(name = "status")
	private String status;
	
	@Column(name = "blood_group", length = 10)
    private String bloodGroup;
	
	@Version
	@Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
	private Integer version;
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRepresentedBy() {
        return representedBy;
    }

    public void setRepresentedBy(String representedBy) {
        this.representedBy = representedBy;
    }

    public String getAadharNo() {
		return aadharNo;
	}

	public void setAadharNo(String aadharNo) {
		this.aadharNo = aadharNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getVehicleRegRta() {
		return vehicleRegRta;
	}

	public void setVehicleRegRta(String vehicleRegRta) {
		this.vehicleRegRta = vehicleRegRta;
	}

	public String getVehicleRegRtaCode() {
		return vehicleRegRtaCode;
	}

	public void setVehicleRegRtaCode(String vehicleRegRtaCode) {
		this.vehicleRegRtaCode = vehicleRegRtaCode;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getAternateEmail() {
		return aternateEmail;
	}

	public void setAternateEmail(String aternateEmail) {
		this.aternateEmail = aternateEmail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public long getCustCorpDtlsId() {
        return custCorpDtlsId;
    }

    public void setCustCorpDtlsId(long custCorpDtlsId) {
        this.custCorpDtlsId = custCorpDtlsId;
    }

    public VehicleRCEntity getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public String getAternatePhoneNo() {
        return aternatePhoneNo;
    }

    public void setAternatePhoneNo(String aternatePhoneNo) {
        this.aternatePhoneNo = aternatePhoneNo;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    
}
