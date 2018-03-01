package org.rta.core.entity.vehicle.cms;

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
@Table(name = "maker_master")
public class MakerMasterEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "maker_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "maker_master_gen")
    @SequenceGenerator(name = "maker_master_gen", sequenceName = "maker_master_sequence", allocationSize = 1)
    private Integer makerId;

    @NotNull
    @Column(name = "maker_name", length = 200)
    private String makerName;

    @NotNull
    @Column(name = "status")
    private Boolean status;

    public MakerMasterEntity() {}

    public MakerMasterEntity(Integer makerId) {
        this.makerId = makerId;
    }

    public Integer getMakerId() {
        return makerId;
    }

    public void setMakerId(Integer makerId) {
        this.makerId = makerId;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }



}
