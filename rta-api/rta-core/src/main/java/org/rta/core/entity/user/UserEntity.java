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
import org.rta.core.enums.CompanyType;
import org.rta.core.enums.UserType;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = 183996599479418472L;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long userId;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_type", length = 50)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile", length = 20)
    private String mobile;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "last_login")
    private Long lastLogin;
    
    @Column(name = "institution_name")
    private String institutionName;
    
    @Column(name = "roc_number")
    private String rocNumber;
    
    @Column(name = "firm_reg_date")
    private Long dateOfFirmRegistration;
    
    @Column(name = "pan_number")
    private String panNumber;
    
    @Column(name = "company_type")
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;
    
    @Column(name = "business_desc")
    private String businessDescription;
    
    @Column(name = "consent")
    private Boolean consent;
    
    @Column(name = "aadhar_number")
    private String aadharNumber;

    public UserEntity() {}

    public UserEntity(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getRocNumber() {
        return rocNumber;
    }

    public void setRocNumber(String rocNumber) {
        this.rocNumber = rocNumber;
    }

    public Long getDateOfFirmRegistration() {
        return dateOfFirmRegistration;
    }

    public void setDateOfFirmRegistration(Long dateOfFirmRegistration) {
        this.dateOfFirmRegistration = dateOfFirmRegistration;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public Boolean getConsent() {
        return consent;
    }

    public void setConsent(Boolean consent) {
        this.consent = consent;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }



}
