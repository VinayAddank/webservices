package org.rta.core.entity.hsrp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;

/**
 *	@Author sohan.maurya created on Sep 9, 2016.
 */

@Entity
@Table(name = "affixation_center_master")
public class AffixationCenterMasterEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "affixation_center_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "affixation_center_master_gen")
    @SequenceGenerator(name = "affixation_center_master_gen", sequenceName = "affixation_center_master_sequence",
            allocationSize = 1)
    private Integer affixationCenterId;

    @NotNull
    @Column(name = "mandal_code", length = 10)
    private Integer mandalCode;

    @NotNull
    @Column(name = "affixation_center_code", length = 20)
    private String affixationCenterCode;

    @NotNull
    @Column(name = "status")
    private Boolean status;

    
    @Column(name = "district_id", length = 10)
    private int districtId;

    public Integer getAffixationCenterId() {
        return affixationCenterId;
    }

    public void setAffixationCenterId(Integer affixationCenterId) {
        this.affixationCenterId = affixationCenterId;
    }

    public Integer getMandalCode() {
        return mandalCode;
    }

    public void setMandalCode(Integer mandalCode) {
        this.mandalCode = mandalCode;
    }

    public String getAffixationCenterCode() {
        return affixationCenterCode;
    }

    public void setAffixationCenterCode(String affixationCenterCode) {
        this.affixationCenterCode = affixationCenterCode;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}



}
