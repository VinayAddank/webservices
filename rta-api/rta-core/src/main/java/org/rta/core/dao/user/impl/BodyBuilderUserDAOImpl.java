package org.rta.core.dao.user.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.BodyBuilderUserDAO;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.user.BodyBuilderUserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class BodyBuilderUserDAOImpl extends BaseDAO<BodyBuilderUserEntity> implements BodyBuilderUserDAO {

	public BodyBuilderUserDAOImpl() {
		super(BodyBuilderUserEntity.class);
	}

    @Override
    public BodyBuilderUserEntity getBodyBuilderUserByUserId(Long id) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("user.userId", id));
        return (BodyBuilderUserEntity) criteria.uniqueResult();
    }
	
}
