package org.rta.core.model.user;

import org.rta.core.enums.UserType;
import org.rta.core.model.base.BaseModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AddressModel extends BaseModel {

    private static final long serialVersionUID = -5029020753765272406L;
    private Long addressId;
    private String type;
    private Boolean status;
    private String doorNo;
    private String street;
    private Long user;
    private UserType userType;
    private String postOffice;
    private Long mandal;
    private String city;
    private Long district;
    private Long state;
    private Long country;
    private Integer mandalCode;
    private String districtCode;
    private String stateCode;
    private String mandalName;
    private String districtName;
    private String stateName;
    private String countryName;
    private Boolean isSameAadhar;
    
    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public Long getMandal() {
        return mandal;
    }

    public void setMandal(Long mandal) {
        this.mandal = mandal;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getDistrict() {
        return district;
    }

    public void setDistrict(Long district) {
        this.district = district;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Long getCountry() {
        return country;
    }

    public String getMandalName() {
		return mandalName;
	}

	public void setMandalName(String mandalName) {
		this.mandalName = mandalName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public void setCountry(Long country) {
        this.country = country;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public Integer getMandalCode() {
        return mandalCode;
    }

    public void setMandalCode(Integer mandalCode) {
        this.mandalCode = mandalCode;
    }

	public Boolean getIsSameAadhar() {
		return isSameAadhar;
	}

	public void setIsSameAadhar(Boolean isSameAadhar) {
		this.isSameAadhar = isSameAadhar;
	}

}
