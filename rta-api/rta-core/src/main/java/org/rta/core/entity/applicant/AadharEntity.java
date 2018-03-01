package org.rta.core.entity.applicant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "aadhaar_master")
public class AadharEntity extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8792785877069068336L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aadhar_master_seq")
	@SequenceGenerator(name = "aadhar_master_seq", sequenceName = "aadhar_master_seq", allocationSize = 1)
	private Long custIndDtlId;
	@Column(name = "KSAKUATxn")
	private String KSA_KUA_Txn;
	@Column(name = "AuthDate")
	private String auth_date;
	@Column(name = "AuthStatus")
	private String auth_status;
	@Column(name = "AuthtransactionCode")
	private String auth_transaction_code;
	@Column(name = "FatherName")
	private String co;
	@Column(name = "District")
	private String district;
	@Column(name = "DistrictName")
	private String district_name;
	@Column(name = "DateOfBirth")
	private String dob;
	@Column(name = "Gender")
	private String gender;
	@Column(name = "House")
	private String house;
	@Column(name = "Lc")
	private String lc;
	@Column(name = "Mandal")
	private String mandal;
	@Column(name = "MandalName")
	private String mandal_name;
	@Column(name = "Name")
	private String name;
	@Column(name = "Pincode")
	private String pincode;
	@Column(name = "po")
	private String po;
	@Column(name = "StateCode")
	private String statecode;
	@Column(name = "Street")
	private String street;
	@Column(name = "SubDist")
	private String subdist;
	@Column(name = "UID")
	private Long uid;
	@Column(name = "Village")
	private String village;
	@Column(name = "VillageName")
	private String village_name;
	@Column(name = "VTC")
	private String vtc;


	public String getKSA_KUA_Txn() {
		return KSA_KUA_Txn;
	}

	public void setKSA_KUA_Txn(String kSA_KUA_Txn) {
		KSA_KUA_Txn = kSA_KUA_Txn;
	}

	public String getAuth_date() {
		return auth_date;
	}

	public void setAuth_date(String auth_date) {
		this.auth_date = auth_date;
	}

	public String getAuth_status() {
		return auth_status;
	}

	public void setAuth_status(String auth_status) {
		this.auth_status = auth_status;
	}

	public String getAuth_transaction_code() {
		return auth_transaction_code;
	}

	public void setAuth_transaction_code(String auth_transaction_code) {
		this.auth_transaction_code = auth_transaction_code;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDistrict_name() {
		return district_name;
	}

	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getLc() {
		return lc;
	}

	public void setLc(String lc) {
		this.lc = lc;
	}

	public String getMandal() {
		return mandal;
	}

	public void setMandal(String mandal) {
		this.mandal = mandal;
	}

	public String getMandal_name() {
		return mandal_name;
	}

	public void setMandal_name(String mandal_name) {
		this.mandal_name = mandal_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPo() {
		return po;
	}

	public void setPo(String po) {
		this.po = po;
	}

	public String getStatecode() {
		return statecode;
	}

	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSubdist() {
		return subdist;
	}

	public void setSubdist(String subdist) {
		this.subdist = subdist;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getVillage_name() {
		return village_name;
	}

	public void setVillage_name(String village_name) {
		this.village_name = village_name;
	}

	public String getVtc() {
		return vtc;
	}

	public void setVtc(String vtc) {
		this.vtc = vtc;
	}

}
