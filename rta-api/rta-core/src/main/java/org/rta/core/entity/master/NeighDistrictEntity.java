package org.rta.core.entity.master;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.rta.core.entity.base.SerializableEntity;

@Entity
@Table(name = "neigh_district_master")
public class NeighDistrictEntity extends SerializableEntity{
	
	private static final long serialVersionUID = 5896213718945544268L;

    @Id
    @Column(name = "neigh_dist_id")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "neigh_district_seq")
//    @SequenceGenerator(name = "neigh_district_seq", sequenceName = "neigh_district_seq", allocationSize = 1)
    private Long neighDistId;
	
    @Column(name="created_by", length=80)
    private String createdBy;

    @Column(name="created_on")
    private Timestamp createdOn;

    @Column(name="dist_id")
    private Long distId;

    @Column(name="modified_by", length=80)
    private String modifiedBy;

    @Column(name="modified_on")
    private Timestamp modifiedOn;

    public Long getNeighDistId() {
        return this.neighDistId;
    }

    public void setNeighDistId(Long neighDistId) {
        this.neighDistId = neighDistId;
    }
    
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Long getDistId() {
        return this.distId;
    }

    public void setDistId(Long distId) {
        this.distId = distId;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getModifiedOn() {
        return this.modifiedOn;
    }

    public void setModifiedOn(Timestamp modifiedOn) {
        this.modifiedOn = modifiedOn;
    }	
}
