package org.rta.core.dao.master.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.OwnershipDAO;
import org.rta.core.entity.master.OwnershipTypeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class OwnershipDAOImpl extends BaseDAO<OwnershipTypeEntity> implements OwnershipDAO {
    public OwnershipDAOImpl() {
        super(OwnershipTypeEntity.class);
    }

    @Override
    public OwnershipTypeEntity getByCode(String code) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", code));
        return (OwnershipTypeEntity) criteria.uniqueResult();
    }

    @Override
    public OwnershipTypeEntity getById(Long id) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("ownershipTypeId", id));
        return (OwnershipTypeEntity) criteria.uniqueResult();
    }
}
