package org.rta.core.entity.licence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the license_idp_dtls database table.
 * 
 */
@Embeddable
public class LicenseIdpDtlPK implements Serializable {

    private static final long serialVersionUID = -3489791778171538476L;

    @Column(name="licence_holder_id")
	private Long licenceHolderId;

	@Column(name="idp_sequence_id")
	private Integer idpSequenceId;

    public LicenseIdpDtlPK() {}
    
    public LicenseIdpDtlPK(Long licenceHolderId, Integer idpSequenceId) {
		this.licenceHolderId = licenceHolderId;
		this.idpSequenceId = idpSequenceId;
	}
	
	public Long getLicenceHolderId() {
		return this.licenceHolderId;
	}
	public void setLicenceHolderId(Long licenceHolderId) {
		this.licenceHolderId = licenceHolderId;
	}
	public Integer getIdpSequenceId() {
		return this.idpSequenceId;
	}
	public void setIdpSequenceId(Integer idpSequenceId) {
		this.idpSequenceId = idpSequenceId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LicenseIdpDtlPK)) {
			return false;
		}
		LicenseIdpDtlPK castOther = (LicenseIdpDtlPK)other;
		return 
			this.licenceHolderId.equals(castOther.licenceHolderId)
			&& this.idpSequenceId.equals(castOther.idpSequenceId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.licenceHolderId.hashCode();
		hash = hash * prime + this.idpSequenceId.hashCode();
		
		return hash;
	}
}