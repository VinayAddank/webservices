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
@Table(name = "state")
public class StateEntity extends BaseMasterEntity {

    private static final long serialVersionUID = 7163665318974594869L;

    @Id
    @Column(name = "state_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "state_id_seq")
    @SequenceGenerator(name = "state_id_seq", sequenceName = "state_id_seq", allocationSize = 1)
    private Long stateId;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private CountryEntity countryEntity; 
    
    @Column(name = "code", length = 50, unique = true)
    private String code;

    public Long getStateId() {
        return stateId;
    }


    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public CountryEntity getCountryEntity() {
        return countryEntity;
    }


    public void setCountryEntity(CountryEntity countryEntity) {
        this.countryEntity = countryEntity;
    }


}
