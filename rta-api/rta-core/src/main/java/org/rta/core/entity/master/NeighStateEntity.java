package org.rta.core.entity.master;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.rta.core.entity.base.SerializableEntity;

@Entity
@Table(name = "neigh_state_master")
public class NeighStateEntity extends SerializableEntity{

	private static final long serialVersionUID = 5896213718945544268L;

    @Id
    @Column(name = "neigh_state_id")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "neigh_state_seq")
//    @SequenceGenerator(name = "neigh_state_seq", sequenceName = "neigh_state_seq", allocationSize = 1)
    private Long neighStateId;
	
    @Column(name="created_by", length=80)
    private String createdBy;

    @Column(name="created_on")
    private Timestamp createdOn;

    @Column(name="modified_by", length=80)
    private String modifiedBy;

    @Column(name="modified_on")
    private Timestamp modifiedOn;

    @Column(name="state_id")
    private Long stateId;

    public Long getNeighStateId() {
        return this.neighStateId;
    }

    public void setNeighStateId(Long neighStateId) {
        this.neighStateId = neighStateId;
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

    public Long getStateId() {
        return this.stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }
    
}
