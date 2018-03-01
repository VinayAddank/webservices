package org.rta.core.entity.master.registration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "special_number_rta_office")
public class SpecialNumberRtaOfficeEntity extends BaseEntity {

	private static final long serialVersionUID = -7274419569581833463L;

	@Id
	@Column(name = "special_no_rta_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "special_no_rtaseq")
	@SequenceGenerator(name = "special_no_rtaseq", sequenceName = "special_no_rtaseq", allocationSize = 1)
	private Long specialNoRtaId;

	@Column(name = "status")
	private Integer status;

	@Column(name = "tr_number", unique=true)
	private String trNumber;

	@Column(name = "special_number")
	private String specialNumber;

	public Long getSpecialNoRtaId() {
		return specialNoRtaId;
	}

	public void setSpecialNoRtaId(Long specialNoRtaId) {
		this.specialNoRtaId = specialNoRtaId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTrNumber() {
		return trNumber;
	}

	public void setTrNumber(String trNumber) {
		this.trNumber = trNumber;
	}

	public String getSpecialNumber() {
		return specialNumber;
	}

	public void setSpecialNumber(String specialNumber) {
		this.specialNumber = specialNumber;
	}

}
