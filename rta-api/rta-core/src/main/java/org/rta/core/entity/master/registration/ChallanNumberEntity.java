package org.rta.core.entity.master.registration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "challan_number")
public class ChallanNumberEntity extends BaseEntity {


    private static final long serialVersionUID = -7453669064286674445L;

    @Id
    @Column(name = "challan_number_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "challan_number_seq")
    @SequenceGenerator(name = "challan_number_seq", sequenceName = "challan_number_seq", allocationSize = 1)
    private Long challanNumberId;

    @Column(name = "treasury_code")
    private String treasuryCode;

    @Column(name = "series")
    private Long series;

    @Column(name = "consume_number")
    private Long consumeNumber;

    @Column(name = "start_number")
    private Long startNumber;

    @Column(name = "end_number")
    private Long endNumber;

    @Column(name = "status")
    private Integer status;
    
    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;

    public Long getChallanNumberId() {
        return challanNumberId;
    }

    public void setChallanNumberId(Long challanNumberId) {
        this.challanNumberId = challanNumberId;
    }

    public String getTreasuryCode() {
        return treasuryCode;
    }

    public void setTreasuryCode(String treasuryCode) {
        this.treasuryCode = treasuryCode;
    }

    public Long getSeries() {
        return series;
    }

    public void setSeries(Long series) {
        this.series = series;
    }

    public Long getConsumeNumber() {
        return consumeNumber;
    }

    public void setConsumeNumber(Long consumeNumber) {
        this.consumeNumber = consumeNumber;
    }

    public Long getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(Long startNumber) {
        this.startNumber = startNumber;
    }

    public Long getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(Long endNumber) {
        this.endNumber = endNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
