package org.rta.core.entity.finance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.user.UserEntity;


@Entity
@Table(name ="finance_branch_employee")
public class FinanceBranchEmployeeEntity extends BaseEntity{
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finance_branch_employee_seq")
    @SequenceGenerator(name = "finance_branch_employee_seq", sequenceName = "finance_branch_employee_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user_id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private FinanceBranchEntity branchId;
    
    @JoinColumn(name = "status")
    private Integer activeStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUser_id() {
		return user_id;
	}

	public void setUser_id(UserEntity user_id) {
		this.user_id = user_id;
	}

	public FinanceBranchEntity getBranchId() {
		return branchId;
	}

	public void setBranchId(FinanceBranchEntity branchId) {
		this.branchId = branchId;
	}

	public Integer getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}
    
    

}
