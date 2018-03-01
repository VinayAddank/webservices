package org.rta.core.model;

import org.rta.core.model.base.BaseModel;

/**
 * AadharModel provides details for aadhar
 * 
 * @author Sriram
 *
 */

public class AadharModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String KSA_KUA_Txn;

	public String getAuth_err_code() {
		return auth_err_code;
	}

	public void setAuth_err_code(String auth_err_code) {
		this.auth_err_code = auth_err_code;
	}

	private String auth_err_code;
	private String auth_date;
	private String auth_status;
	private String auth_transaction_code;
	private String base64file;
	private String co;
	private String district;
	private String district_name;
	private String dob;
	private String gender;
	private String house;
	private String lc;
	private String mandal;
	private String mandal_name;
	private String name;
	private String pincode;
	private String po;
	private String statecode;
	private String street;
	private String subdist;
	private Long uid;
	private String village;
	private String village_name;
	private String vtc;
	//for new feature
    private boolean isInsideAP;
    private boolean isStateMatched;
    private boolean isDistrictMatched;
    private boolean isMandalMatched;
    private String stateMatchedCode;
    private String districtMatchedCode;
    private String mandalMatchedCode;
    private Integer age;
    private String applicationNumber;
    private String firstName;
    private String lastName;
    private String nationality;
    private String doorNo;
    private String country; 
    

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
	public String getBase64file() {
		return base64file;
	}
	public void setBase64file(String base64file) {
		this.base64file = base64file;
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

    public boolean isInsideAP() {
        return isInsideAP;
    }

    public void setInsideAP(boolean isInsideAP) {
        this.isInsideAP = isInsideAP;
    }

    public boolean isStateMatched() {
        return isStateMatched;
    }

    public void setStateMatched(boolean isStateMatched) {
        this.isStateMatched = isStateMatched;
    }

    public boolean isDistrictMatched() {
        return isDistrictMatched;
    }

    public void setDistrictMatched(boolean isDistrictMatched) {
        this.isDistrictMatched = isDistrictMatched;
    }

    public boolean isMandalMatched() {
        return isMandalMatched;
    }

    public void setMandalMatched(boolean isMandalMatched) {
        this.isMandalMatched = isMandalMatched;
    }

    public String getStateMatchedCode() {
        return stateMatchedCode;
    }

    public void setStateMatchedCode(String stateMatchedCode) {
        this.stateMatchedCode = stateMatchedCode;
    }

    public String getDistrictMatchedCode() {
        return districtMatchedCode;
    }

    public void setDistrictMatchedCode(String districtMatchedCode) {
        this.districtMatchedCode = districtMatchedCode;
    }

    public String getMandalMatchedCode() {
        return mandalMatchedCode;
    }

    public void setMandalMatchedCode(String mandalMatchedCode) {
        this.mandalMatchedCode = mandalMatchedCode;
    }

    public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getDoorNo() {
		return doorNo;
	}

	public void setDoorNo(String doorNo) {
		this.doorNo = doorNo;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
    public String toString() {
        return "AadharModel [KSA_KUA_Txn=" + KSA_KUA_Txn + ", auth_err_code=" + auth_err_code + ", auth_date="
                + auth_date + ", auth_status=" + auth_status + ", auth_transaction_code=" + auth_transaction_code
                + ", base64file=" + base64file + ", co=" + co + ", district=" + district + ", district_name="
                + district_name + ", dob=" + dob + ", gender=" + gender + ", house=" + house + ", lc=" + lc
                + ", mandal=" + mandal + ", mandal_name=" + mandal_name + ", name=" + name + ", pincode=" + pincode
                + ", po=" + po + ", statecode=" + statecode + ", street=" + street + ", subdist=" + subdist + ", uid="
                + uid + ", village=" + village + ", village_name=" + village_name + ", vtc=" + vtc + ", isInsideAP="
                + isInsideAP + ", isStateMatched=" + isStateMatched + ", isDistrictMatched=" + isDistrictMatched
                + ", isMandalMatched=" + isMandalMatched + "]";
    }

}
