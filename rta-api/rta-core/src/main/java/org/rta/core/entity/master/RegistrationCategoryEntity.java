package org.rta.core.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "registration_category")
public class RegistrationCategoryEntity extends BaseMasterEntity {


    private static final long serialVersionUID = -903384714330937873L;

    @Id
    @Column(name = "registration_category_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registration_category_seq")
    @SequenceGenerator(name = "registration_category_seq", sequenceName = "registration_category_seq",
            allocationSize = 1)
    private Integer registrationCategoryId;

    @Column(name = "code", length = 50, unique = true)
    private String code;

    public Integer getRegistrationCategoryId() {
        return registrationCategoryId;
    }

    public void setRegistrationCategoryId(Integer registrationCategoryId) {
        this.registrationCategoryId = registrationCategoryId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
