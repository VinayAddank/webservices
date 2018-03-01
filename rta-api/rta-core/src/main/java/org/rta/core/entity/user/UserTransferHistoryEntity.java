package org.rta.core.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.SerializableEntity;
import org.rta.core.enums.UserType;

@Entity
@Table(name = "user_transfer_history")
public class UserTransferHistoryEntity extends SerializableEntity {
	
	private static final long serialVersionUID = 183996599479418472L;
	
	@Id
    @Column(name = "user_transfer_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_transfer_sequence")
    @SequenceGenerator(name = "user_transfer_sequence", sequenceName = "user_transfer_sequence", allocationSize = 1)
	private Long userTransferId;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserType userRole;
	
	@Column(name = "transfer_from")
	private String transferFrom;
	
	@Column(name = "transfer_to")
	private String transferTo;
	
	@Column(name = "transfer_date")
	private Long transferDate;
	
	@Column(name = "transfer_by")
	private String transferBy;
	
	
	public Long getUserTransferId() {
		return userTransferId;
	}
	public void setUserTransferId(Long userTransferId) {
		this.userTransferId = userTransferId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
    public UserType getUserRole() {
		return userRole;
	}

    public void setUserRole(UserType userRole) {
		this.userRole = userRole;
	}
	public String getTransferFrom() {
		return transferFrom;
	}
	public void setTransferFrom(String transferFrom) {
		this.transferFrom = transferFrom;
	}
	public String getTransferTo() {
		return transferTo;
	}
	public void setTransferTo(String transferTo) {
		this.transferTo = transferTo;
	}
	public Long getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(Long transferDate) {
		this.transferDate = transferDate;
	}
	public String getTransferBy() {
		return transferBy;
	}
	public void setTransferBy(String transferBy) {
		this.transferBy = transferBy;
	}
	
}

