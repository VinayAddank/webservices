package org.rta.core.entity.licence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the drivers_license_dtls database table.
 * 
 */
@Embeddable
public class DriversLicenseDtlPK implements Serializable {
    private static final long serialVersionUID = -5061634027439495427L;

    @Column(name="licence_holder_id")
	private Long licenceHolderId;

	@Column(name="dl_sequence_id")
	private Integer dlSequenceId;

    public DriversLicenseDtlPK() {}
	
	public DriversLicenseDtlPK(Long licenceHolderId, Integer dlSequenceId) {
		this.licenceHolderId = licenceHolderId;
		this.dlSequenceId = dlSequenceId;
	}

	public Long getLicenceHolderId() {
		return this.licenceHolderId;
	}
	public void setLicenceHolderId(Long licenceHolderId) {
		this.licenceHolderId = licenceHolderId;
	}
	public Integer getDlSequenceId() {
		return this.dlSequenceId;
	}
	public void setDlSequenceId(Integer dlSequenceId) {
		this.dlSequenceId = dlSequenceId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DriversLicenseDtlPK)) {
			return false;
		}
		DriversLicenseDtlPK castOther = (DriversLicenseDtlPK)other;
		return 
			this.licenceHolderId.equals(castOther.licenceHolderId)
			&& this.dlSequenceId.equals(castOther.dlSequenceId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.licenceHolderId.hashCode();
		hash = hash * prime + this.dlSequenceId.hashCode();
		
		return hash;
	}
}