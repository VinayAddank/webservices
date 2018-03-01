package org.rta.core.dao.aadhar.impl;

import org.rta.core.dao.aadhar.AadharDAO;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.applicant.AadharEntity;
import org.rta.core.model.AadharModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AadharDAOImpl extends BaseDAO<AadharEntity> implements AadharDAO {

    public AadharDAOImpl() {
		super(AadharEntity.class);
    }

	@Override
	@Transactional
	public void saveOrUpdate(AadharModel aadharModel, String userName) {

	}
}
