package org.rta.core.entity.master.registration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "special_number")
public class SpecialNumberEntity extends BaseEntity {

    private static final long serialVersionUID = -7274419569581833463L;

    @Id
    @Column(name = "special_no_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "special_no_seq")
    @SequenceGenerator(name = "special_no_seq", sequenceName = "special_no_seq", allocationSize = 1)
    private Long specialNoId;

    @Column(name = "number")
    private Integer number;

    @Column(name = "cost")
    private Double cost;

    public Long getSpecialNoId() {
		return specialNoId;
	}

	public void setSpecialNoId(Long specialNoId) {
		this.specialNoId = specialNoId;
	}

	public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
