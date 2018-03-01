package org.rta.core.entity.licence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the license_holder_txns database table.
 * 
 */
@Embeddable
public class LicenseHolderTxnPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="licence_holder_id")
	private Long licenceHolderId;

	@Column(name="sequence_id")
	private Integer sequenceId;

    public LicenseHolderTxnPK() {}
	
	public Long getLicenceHolderId() {
		return this.licenceHolderId;
	}
	public void setLicenceHolderId(Long licenceHolderId) {
		this.licenceHolderId = licenceHolderId;
	}
	public Integer getSequenceId() {
		return this.sequenceId;
	}
	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LicenseHolderTxnPK)) {
			return false;
		}
		LicenseHolderTxnPK castOther = (LicenseHolderTxnPK)other;
		return 
			this.licenceHolderId.equals(castOther.licenceHolderId)
			&& this.sequenceId.equals(castOther.sequenceId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.licenceHolderId.hashCode();
		hash = hash * prime + this.sequenceId.hashCode();
		
		return hash;
	}
}