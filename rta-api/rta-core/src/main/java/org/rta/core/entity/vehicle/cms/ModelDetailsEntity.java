package org.rta.core.entity.vehicle.cms;

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
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;

/**
 *	@Author sohan.maurya created on Sep 9, 2016.
 */

@Entity
@Table(name = "model_details")
public class ModelDetailsEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "model_details_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "model_details_gen")
    @SequenceGenerator(name = "model_details_gen", sequenceName = "model_details_sequence", allocationSize = 1)
    private Integer modelDetailsId;

    @NotNull
    @Column(name = "model_name", length = 200)
    private String modelName;

    @NotNull
    @Column(name = "status")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "maker_master_id", nullable = false)
    private MakerMasterEntity makerMasterId;

    public Integer getModelDetailsId() {
        return modelDetailsId;
    }

    public void setModelDetailsId(Integer modelDetailsId) {
        this.modelDetailsId = modelDetailsId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public MakerMasterEntity getMakerMasterId() {
        return makerMasterId;
    }

    public void setMakerMasterId(MakerMasterEntity makerMasterId) {
        this.makerMasterId = makerMasterId;
    }

}
