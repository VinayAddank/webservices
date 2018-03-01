package org.rta.core.entity.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "body_builder_users")
public class BodyBuilderUserEntity extends BaseEntity {

	private static final long serialVersionUID = -5540827268469465425L;

	@Id
    @Column(name = "body_builder_user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "body_builder_users_seq")
    @SequenceGenerator(name = "body_builder_users_seq", sequenceName = "body_builder_users_seq", allocationSize = 1)
    private Long body_builderUserId;
    
    @Column(name = "name")
    private String name;
	
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

	public Long getBody_builderUserId() {
		return body_builderUserId;
	}

	public void setBody_builderUserId(Long body_builderUserId) {
		this.body_builderUserId = body_builderUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

    
    
}
