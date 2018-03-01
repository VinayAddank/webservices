package org.rta.core.entity.master.registration;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.master.DistrictEntity;

@Entity
@Table(name = "tr_series_master")
public class TrSeriesMasterEntity extends BaseEntity {

	private static final long serialVersionUID = -820808025510819251L;

	@Id
	@Column(name = "tr_series_master_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tr_series_master_seq")
	@SequenceGenerator(name = "tr_series_master_seq", sequenceName = "tr_series_master_seq", allocationSize = 1)
	private Long trSeriesMasterId;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "district_id")
	private DistrictEntity districtEntity;

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

	public Long getTrSeriesMasterId() {
		return trSeriesMasterId;
	}

	public void setTrSeriesMasterId(Long trSeriesMasterId) {
		this.trSeriesMasterId = trSeriesMasterId;
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

	public DistrictEntity getDistrictEntity() {
		return districtEntity;
	}

	public void setDistrictEntity(DistrictEntity districtEntity) {
		this.districtEntity = districtEntity;
	}
}
