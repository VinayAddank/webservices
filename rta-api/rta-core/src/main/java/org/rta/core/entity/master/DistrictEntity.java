package org.rta.core.entity.master;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "district")
public class DistrictEntity extends BaseMasterEntity {


    private static final long serialVersionUID = -2899471260224751917L;

    @Id
    @Column(name = "district_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "district_id_seq")
    @SequenceGenerator(name = "district_id_seq", sequenceName = "district_id_seq", allocationSize = 1)
    private Long districtId;
    @Column(name = "code", length = 50, unique = true)
    private String code;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private StateEntity stateEntity;

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public StateEntity getStateEntity() {
        return stateEntity;
    }

    public void setStateEntity(StateEntity stateEntity) {
        this.stateEntity = stateEntity;
    }

}
