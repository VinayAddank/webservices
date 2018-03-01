package org.rta.core.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "body_type_master")
public class BodyTypeMasterEntity extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "body_type_master_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "body_type_master_id_seq")
	@SequenceGenerator(name = "body_type_master_id_seq", sequenceName = "body_type_master_id_seq", allocationSize = 1)
	private Long bodyTypeMasterId;
	
	@Column(name = "body_type")
    private String bodyType;
	
	@Column(name="is_active")
	private boolean isActive;

}
