package org.rta.core.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "qualification")
public class QualificationEntity extends BaseMasterEntity {

	private static final long serialVersionUID = -2345004934993801279L;

	@Id
	@Column(name = "qualification_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qualification_seq")
	@SequenceGenerator(name = "qualification_seq", sequenceName = "qualification_seq", allocationSize = 1)
	private Long qualificationId;

	@Column(name = "code", length = 50, unique = true)
	private Integer code;

	public Long getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(Long qualificationId) {
		this.qualificationId = qualificationId;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
