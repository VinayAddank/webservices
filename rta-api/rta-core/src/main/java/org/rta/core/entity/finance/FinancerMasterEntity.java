package org.rta.core.entity.finance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "financer_master")
public class FinancerMasterEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1650642115355305557L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rta_financer_Master_seq")
	@SequenceGenerator(name = "rta_financer_Master_seq", sequenceName = "rta_financer_Master_seq", allocationSize = 1)
    private Long id;

	@Column(name  = "user_id")
    private Long userId;

	@Column(name = "name")
	private String name;
	
	@Column(name = "financier_pan_no")
	private String financierPanNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getFinancierPanNumber() {
		return financierPanNumber;
	}

	public void setFinancierPanNumber(String financierPanNumber) {
		this.financierPanNumber = financierPanNumber;
	}


}
