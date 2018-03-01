package org.rta.core.dao.user.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.AlterationAgencyUserDAO;
import org.rta.core.entity.user.AlterationAgencyUserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class AlterationAgencyUserDAOImpl extends BaseDAO<AlterationAgencyUserEntity> implements AlterationAgencyUserDAO {

	public AlterationAgencyUserDAOImpl() {
		super(AlterationAgencyUserEntity.class);
	}

    @Override
    public AlterationAgencyUserEntity getUserByUserId(Long id) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("user.userId", id));
        return (AlterationAgencyUserEntity) criteria.uniqueResult();
    }
	
}
