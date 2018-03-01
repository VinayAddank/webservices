package org.rta.core.entity.office;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "rta_office")
public class RtaOfficeEntity extends BaseEntity {

    private static final long serialVersionUID = -407648634226313403L;

    @Id
    @Column(name = "rta_office_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rta_office_seq")
    @SequenceGenerator(name = "rta_office_seq", sequenceName = "rta_office_seq", allocationSize = 1)
    private Long rtaOfficeId;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "phone")
    private String phone;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "office_type")
    private String officeType;

    @Column(name = "series_map_id")
    private String seriesMapid;

    @Column(name = "parent_office_code")
    private String parentOfficeCode;

    @Column(name = "district_code")
    private String districtCode;

    public Long getRtaOfficeId() {
        return rtaOfficeId;
    }

    public void setRtaOfficeId(Long rtaOfficeId) {
        this.rtaOfficeId = rtaOfficeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getOfficeType() {
        return officeType;
    }

    public void setOfficeType(String officeType) {
        this.officeType = officeType;
    }

    public String getSeriesMapid() {
        return seriesMapid;
    }

    public void setSeriesMapid(String seriesMapid) {
        this.seriesMapid = seriesMapid;
    }

    public String getParentOfficeCode() {
        return parentOfficeCode;
    }

    public void setParentOfficeCode(String parentOfficeCode) {
        this.parentOfficeCode = parentOfficeCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

}
