package org.rta.core.entity.licence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the learners_permit_dtls database table.
 * 
 */
@Embeddable
public class LearnersPermitDtlPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="licence_holder_id")
	private Long licenceHolderId;

	@Column(name="llr_sequence_id")
	private Integer llrSequenceId;

	@Column(name="llr_vehicle_class_code")
	private String llrVehicleClassCode;

    public LearnersPermitDtlPK() {}
    
	public LearnersPermitDtlPK(Long licenceHolderId, Integer llrSequenceId, String llrVehicleClassCode) {
		this.licenceHolderId = licenceHolderId;
		this.llrSequenceId = llrSequenceId;
		this.llrVehicleClassCode = llrVehicleClassCode;
	}

	public Long getLicenceHolderId() {
		return this.licenceHolderId;
	}
	public void setLicenceHolderId(Long licenceHolderId) {
		this.licenceHolderId = licenceHolderId;
	}
	public Integer getLlrSequenceId() {
		return this.llrSequenceId;
	}
	public void setLlrSequenceId(Integer llrSequenceId) {
		this.llrSequenceId = llrSequenceId;
	}
	public String getLlrVehicleClassCode() {
		return this.llrVehicleClassCode;
	}
	public void setLlrVehicleClassCode(String llrVehicleClassCode) {
		this.llrVehicleClassCode = llrVehicleClassCode;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LearnersPermitDtlPK)) {
			return false;
		}
		LearnersPermitDtlPK castOther = (LearnersPermitDtlPK)other;
		return 
			this.licenceHolderId.equals(castOther.licenceHolderId)
			&& this.llrSequenceId.equals(castOther.llrSequenceId)
			&& this.llrVehicleClassCode.equals(castOther.llrVehicleClassCode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.licenceHolderId.hashCode();
		hash = hash * prime + this.llrSequenceId.hashCode();
		hash = hash * prime + this.llrVehicleClassCode.hashCode();
		
		return hash;
	}
}