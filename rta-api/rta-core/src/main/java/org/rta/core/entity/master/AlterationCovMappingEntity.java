package org.rta.core.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "alteration_cov_mapping")
public class AlterationCovMappingEntity extends BaseEntity {
	private static final long serialVersionUID = -2940020073628817826L;

	@Id
	@Column(name = "alteration_cov_mapping_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alteration_cov_mapping_id_seq")
	@SequenceGenerator(name = "alteration_cov_mapping_id_seq", sequenceName = "alteration_cov_mapping_id_seq", allocationSize = 1)
	private Long alterationCovMappingId;
	
	@Column(name = "non_transport_cov_code")
    private String nonTransportCovCode;

	@Column(name = "transport_cov_code")
    private String transportCovCode;

	public Long getAlterationCovMappingId() {
		return alterationCovMappingId;
	}

	public void setAlterationCovMappingId(Long alterationCovMappingId) {
		this.alterationCovMappingId = alterationCovMappingId;
	}

	public String getNonTransportCovCode() {
		return nonTransportCovCode;
	}

	public void setNonTransportCovCode(String nonTransportCovCode) {
		this.nonTransportCovCode = nonTransportCovCode;
	}

	public String getTransportCovCode() {
		return transportCovCode;
	}

	public void setTransportCovCode(String transportCovCode) {
		this.transportCovCode = transportCovCode;
	}
}
