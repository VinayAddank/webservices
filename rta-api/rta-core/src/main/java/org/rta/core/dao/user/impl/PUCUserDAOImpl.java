package org.rta.core.dao.user.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.PUCUserDAO;
import org.rta.core.entity.user.PUCUserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PUCUserDAOImpl extends BaseDAO<PUCUserEntity> implements PUCUserDAO {

	public PUCUserDAOImpl() {
		super(PUCUserEntity.class);
	}

    @Override
    public PUCUserEntity getUserByUserId(Long id) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("user.userId", id));
        return (PUCUserEntity) criteria.uniqueResult();
    }
	
}
