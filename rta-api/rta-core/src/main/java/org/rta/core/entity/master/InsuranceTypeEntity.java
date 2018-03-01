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
@Table(name = "insurance_type")
public class InsuranceTypeEntity extends BaseMasterEntity {

    private static final long serialVersionUID = 5924716360032710159L;

    @Id
    @Column(name = "insurance_type_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insurance_type_seq")
    @SequenceGenerator(name = "insurance_type_seq", sequenceName = "insurance_type_seq", allocationSize = 1)
    private Long insuranceTypeId;

    @Column(name = "code", length = 50, unique = true)
    private String code;

    public InsuranceTypeEntity() {}

    public InsuranceTypeEntity(Long insuranceTypeId) {
        this.insuranceTypeId = insuranceTypeId;
    }

    public Long getInsuranceTypeId() {
        return insuranceTypeId;
    }

    public void setInsuranceTypeId(Long insuranceTypeId) {
        this.insuranceTypeId = insuranceTypeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
