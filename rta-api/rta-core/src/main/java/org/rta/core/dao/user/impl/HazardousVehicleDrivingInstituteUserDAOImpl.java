package org.rta.core.dao.user.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.HazardousVehicleDrivingInstituteUserDAO;
import org.rta.core.entity.user.HazardousVehicleDrivingInstituteUserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class HazardousVehicleDrivingInstituteUserDAOImpl extends BaseDAO<HazardousVehicleDrivingInstituteUserEntity> implements HazardousVehicleDrivingInstituteUserDAO {

	public HazardousVehicleDrivingInstituteUserDAOImpl() {
		super(HazardousVehicleDrivingInstituteUserEntity.class);
	}

    @Override
    public HazardousVehicleDrivingInstituteUserEntity getInstituteByUserId(Long userId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("user.userId", userId));
        return (HazardousVehicleDrivingInstituteUserEntity) criteria.uniqueResult();
    }

	
}
