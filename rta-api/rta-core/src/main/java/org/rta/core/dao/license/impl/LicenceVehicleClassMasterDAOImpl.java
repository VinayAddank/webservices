package org.rta.core.dao.license.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.license.LicenceVehicleClassMasterDAO;
import org.rta.core.entity.licence.LicenseVehicleClassMasterEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LicenceVehicleClassMasterDAOImpl extends BaseDAO<LicenseVehicleClassMasterEntity>
		implements LicenceVehicleClassMasterDAO {
	public LicenceVehicleClassMasterDAOImpl() {
		super(LicenseVehicleClassMasterEntity.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<LicenseVehicleClassMasterEntity> getVehicleTransportType(String covcode) {
		Criteria criteria = null;
		criteria = getSession().createCriteria(LicenseVehicleClassMasterEntity.class);
		return (List<LicenseVehicleClassMasterEntity>) criteria.list();

	}
}
