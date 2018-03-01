package org.rta.core.entity.payment.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "sbi_ddo")
public class SBIDDOMasterEntity extends BaseEntity {

    private static final long serialVersionUID = 6599717124904929144L;

    @Id
    @Column(name = "sbi_ddo_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sbi_ddo_seq")
    @SequenceGenerator(name = "sbi_ddo_seq", sequenceName = "sbi_ddo_seq", allocationSize = 1)
    private Long sbiDDOId;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "district_code")
    private String districtCode;

    @Column(name = "treasury_name")
    private String treasuryName;

    @Column(name = "ddo_code")
    private String ddoCode;

    public Long getSbiDDOId() {
        return sbiDDOId;
    }

    public void setSbiDDOId(Long sbiDDOId) {
        this.sbiDDOId = sbiDDOId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getTreasuryName() {
        return treasuryName;
    }

    public void setTreasuryName(String treasuryName) {
        this.treasuryName = treasuryName;
    }

    public String getDdoCode() {
        return ddoCode;
    }

    public void setDdoCode(String ddoCode) {
        this.ddoCode = ddoCode;
    }

}
