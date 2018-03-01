package org.rta.core.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.enums.UserType;

@Entity
@Table(name = "address_history")
public class AddressHistoryEntity extends BaseEntity {

    private static final long serialVersionUID = -1467169521786153003L;

    @Id
    @Column(name = "address_history_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_history_seq")
    @SequenceGenerator(name = "address_history_seq", sequenceName = "address_history_seq", allocationSize = 1)
    private Long addressHistoryId;

    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "door_no")
    private String doorNo;

    @Column(name = "street")
    private String street;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_type", length = 50)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "pin_code")
    private String pinCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mandal_id")
    private MandalEntity mandal;

    @Column(name = "city")
    private String city;

    @Column(name = "district_id")
    private Long district;

    @Column(name = "state_id")
    private Long state;

    @Column(name = "country_id")
    private Long country;

    @Column(name = "service_code")
    private String serviceCode;


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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long user) {
        this.userId = user;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public MandalEntity getMandal() {
        return mandal;
    }

    public void setMandal(MandalEntity mandal) {
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

    public void setCountry(Long country) {
        this.country = country;
    }

    public Long getAddressHistoryId() {
        return addressHistoryId;
    }

    public void setAddressHistoryId(Long addressHistoryId) {
        this.addressHistoryId = addressHistoryId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

}
