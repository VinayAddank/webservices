package org.rta.core.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @Author sohan.maurya created on Jul 11, 2016.
 */

@Entity
@Table(name = "address_proof_type")
public class AddressProofTypeEntity extends BaseMasterEntity {

    private static final long serialVersionUID = -6823475917009704316L;

    @Id
    @Column(name = "address_proof_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_proof_seq")
    @SequenceGenerator(name = "address_proof_seq", sequenceName = "address_proof_seq", allocationSize = 1)
    private Long addressProofId;

    @Column(name = "code", length = 50, unique = true)
    private String code;

    public Long getAddressProofId() {
        return addressProofId;
    }

    public void setAddressProofId(Long addressProofId) {
        this.addressProofId = addressProofId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
