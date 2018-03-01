package org.rta.core.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "designation")
public class DesignationEntity extends BaseMasterEntity {


    private static final long serialVersionUID = -6973517699043112237L;

    @Id
    @Column(name = "designation_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "designation_seq")
    @SequenceGenerator(name = "designation_seq", sequenceName = "designation_seq", allocationSize = 1)
    private Long designationId;

    @Column(name = "code", length = 50, unique = true)
    private String code;

    public Long getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Long designationId) {
        this.designationId = designationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
