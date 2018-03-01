package org.rta.core.dao.license.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.license.LicenseVehicleClassRefDAO;
import org.rta.core.entity.licence.LicenseVehicleClassRefEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LicenseVehicleClassRefDAOImpl extends BaseDAO<LicenseVehicleClassRefEntity> implements LicenseVehicleClassRefDAO{

	public LicenseVehicleClassRefDAOImpl() {
		super(LicenseVehicleClassRefEntity.class);
		
	}
	
	@Override
	public LicenseVehicleClassRefEntity getLicenseVehicleClassRef(String vehicleClassCode) {
		Criteria criteria=getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("vehicleClassCode", vehicleClassCode));
		return (LicenseVehicleClassRefEntity) criteria.uniqueResult();
	}

	
}
