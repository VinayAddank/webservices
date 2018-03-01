package org.rta.core.entity.permit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.SerializableEntity;

@Entity
@Table(name = "permit_allowed")
public class PermitAllowedEntity extends SerializableEntity{

	private static final long serialVersionUID = 6853950175288304926L;

    @Id
    @Column(name = "permit_allowed_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permit_allowed_seq")
    @SequenceGenerator(name = "permit_allowed_seq", sequenceName = "permit_allowed_seq", allocationSize = 1)
    private Long permitAllowedId;
	
    @Column(name="allowed_count")
    private Integer allowedCount;

    @Column(name="current_count")
    private Integer currentCount;

    @Column(name="rta_office_cd")
    private Integer rtaOfficeCd;

    @Column(name="state_id")
    private Integer stateId;

    private Integer year;

    public Long getPermitAllowedId() {
        return permitAllowedId;
    }

    public void setPermitAllowedId(Long permitAllowedId) {
        this.permitAllowedId = permitAllowedId;
    }

    public Integer getAllowedCount() {
        return this.allowedCount;
    }

    public void setAllowedCount(Integer allowedCount) {
        this.allowedCount = allowedCount;
    }

    public Integer getCurrentCount() {
        return this.currentCount;
    }

    public void setCurrentCount(Integer currentCount) {
        this.currentCount = currentCount;
    }

    public Integer getRtaOfficeCd() {
        return this.rtaOfficeCd;
    }

    public void setRtaOfficeCd(Integer rtaOfficeCd) {
        this.rtaOfficeCd = rtaOfficeCd;
    }

    public Integer getStateId() {
        return this.stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
