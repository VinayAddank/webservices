package org.rta.core.entity.master.registration;

import javax.persistence.CascadeType;
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
import javax.persistence.Version;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.office.RtaOfficeEntity;

@Entity
@Table(name = "pr_series_master")
public class PrSeriesMasterEntity extends BaseEntity {

    private static final long serialVersionUID = -820808025510819251L;

    @Id
    @Column(name = "pr_series_master_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pr_series_master_seq")
    @SequenceGenerator(name = "pr_series_master_seq", sequenceName = "pr_series_master_seq", allocationSize = 1)
    private Long prSeriesMasterId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "rta_office_id")
    private RtaOfficeEntity rtaOffice;

    @Column(name = "series")
    private String series;

    @Column(name = "use_number")
    private Integer usedNumber;

    @Column(name = "start_number")
    private Integer startNumber;

    @Column(name = "end_number")
    private Integer endNumber;

    @Column(name = "status")
    private Integer status;
    
    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;
    
    @Column(name = "reg_type")
    private Integer regType;

    
    public Long getTrSeriesMasterId() {
        return prSeriesMasterId;
    }

    public void setPrSeriesMasterId(Long prSeriesMasterId) {
        this.prSeriesMasterId = prSeriesMasterId;
    }

    public RtaOfficeEntity getRtaOffice() {
        return rtaOffice;
    }

    public void setRtaOffice(RtaOfficeEntity rtaOffice) {
        this.rtaOffice = rtaOffice;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Integer getUsedNumber() {
        return usedNumber;
    }

    public void setUsedNumber(Integer usedNumber) {
        this.usedNumber = usedNumber;
    }

    public Integer getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(Integer startNumber) {
        this.startNumber = startNumber;
    }

    public Integer getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(Integer endNumber) {
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

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}

}
