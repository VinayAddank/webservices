/**
 * 
 */
package org.rta.core.entity.permit;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.SerializableEntity;

/**
 * @author arun.verma
 *
 */

@Entity
@Table(name = "permit_details_mapping")
public class PermitDetailsMappingEntity extends SerializableEntity implements Cloneable{

    private static final long serialVersionUID = 9163675447264723197L;
    
    @Id
    @Column(name = "permit_details_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permit_details_mapping_seq")
    @SequenceGenerator(name = "permit_details_mapping_seq", sequenceName = "permit_details_mapping_seq",
            allocationSize = 1)
    private Long permitDetailsMapId;
    
    @Column(name="created_by", length=50)
    private String createdBy;

    @Column(name="created_on")
    private Timestamp createdOn;

    @Column(name="detail_type", length=80)
    private String detailType;

    @Column(name="detail_value")
    private String detailValue;

    @Column(name="modified_by", length=50)
    private String modifiedBy;

    @Column(name="modified_on")
    private Timestamp modifiedOn;

    @Column(name="permit_dtl_seq_id")
    private Integer permitDtlSeqId;

    @Column(name="permit_no", length=80)
    private String permitNo;

    @Column(name="permit_sequence_id")
    private Integer permitSequenceId;

    private Integer status;

    public Long getPermitDetailsMapId() {
        return permitDetailsMapId;
    }

    public void setPermitDetailsMapId(Long permitDetailsMapId) {
        this.permitDetailsMapId = permitDetailsMapId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getDetailValue() {
        return detailValue;
    }

    public void setDetailValue(String detailValue) {
        this.detailValue = detailValue;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Timestamp modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Integer getPermitDtlSeqId() {
        return permitDtlSeqId;
    }

    public void setPermitDtlSeqId(Integer permitDtlSeqId) {
        this.permitDtlSeqId = permitDtlSeqId;
    }

    public String getPermitNo() {
        return permitNo;
    }

    public void setPermitNo(String permitNo) {
        this.permitNo = permitNo;
    }

    public Integer getPermitSequenceId() {
        return permitSequenceId;
    }

    public void setPermitSequenceId(Integer permitSequenceId) {
        this.permitSequenceId = permitSequenceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
