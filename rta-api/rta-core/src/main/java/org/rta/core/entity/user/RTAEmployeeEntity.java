package org.rta.core.entity.user;

import javax.persistence.CascadeType;
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

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.office.RtaOfficeEntity;

@Entity
@Table(name = "rta_employee")
public class RTAEmployeeEntity extends BaseEntity {

    private static final long serialVersionUID = 237036376694172425L;
    @Id
    @Column(name = "rta_employee_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rta_employee_seq")
    @SequenceGenerator(name = "rta_employee_seq", sequenceName = "rta_employee_seq", allocationSize = 1)
    private Long rtaEmployeeId;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rta_office_id")
    private RtaOfficeEntity rtaOfficeEntity;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    
    @Column(name = "dept_name")
    private String departmentName;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "mobile")
    private String mobile;
    
    @Column(name = "fax")
    private String fax;
    
    @Column(name = "emailId")
    private String emailId;
    
    @Column(name = "status")
    private Boolean status;
    
    @Column(name = "employee_sign_file_name")
    private String signFileName;
    
    
    public Long getRtaEmployeeId() {
        return rtaEmployeeId;
    }
    public void setRtaEmployeeId(Long rtaEmployeeId) {
        this.rtaEmployeeId = rtaEmployeeId;
    }
    public RtaOfficeEntity getRtaOfficeEntity() {
        return rtaOfficeEntity;
    }
    public void setRtaOfficeEntity(RtaOfficeEntity rtaOfficeEntity) {
        this.rtaOfficeEntity = rtaOfficeEntity;
    }
    public UserEntity getUserEntity() {
        return userEntity;
    }
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getFax() {
        return fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
	public String getSignFileName() {
		return signFileName;
	}
	public void setSignFileName(String signFileName) {
		this.signFileName = signFileName;
	}
    
}
