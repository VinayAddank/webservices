/**
 * 
 */
package org.rta.core.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.enums.UserType;

/**
 * @author sohan.maurya
 *
 */

@Entity
@Table(name = "permanent_address_history")
public class PermanentAddressHistoryEntity extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 8156494329868347514L;

    @Id
    @Column(name = "p_address_history_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "p_address_history_seq")
    @SequenceGenerator(name = "p_address_history_seq", sequenceName = "p_address_history_seq", allocationSize = 1)
    private Long pAddressHistoryId;
    
    @Column(name = "p_address_id")
    private Long pAddressId;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "door_no")
    private String doorNo;

    @Column(name = "street")
    private String street;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "address_type", columnDefinition = "int default 1")
    private Integer addressType;

    @Column(name = "user_type", length = 50)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "post_office")
    private String postOffice;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "mandal")
    private String mandal;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "service_code")
    private String serviceCode;

   
    public Long getpAddressId() {
        return pAddressId;
    }

    public void setpAddressId(Long pAddressId) {
        this.pAddressId = pAddressId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getMandal() {
        return mandal;
    }

    public void setMandal(String mandal) {
        this.mandal = mandal;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

	public Long getpAddressHistoryId() {
		return pAddressHistoryId;
	}

	public void setpAddressHistoryId(Long pAddressHistoryId) {
		this.pAddressHistoryId = pAddressHistoryId;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
    
    
}
