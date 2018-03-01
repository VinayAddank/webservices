package org.rta.core.dao.permit.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.permit.PermitAllowedDAO;
import org.rta.core.entity.permit.PermitAllowedEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PermitAllowedDAOImpl extends BaseDAO<PermitAllowedEntity> implements PermitAllowedDAO {

	public PermitAllowedDAOImpl() {
		super(PermitAllowedEntity.class);
	}
}
