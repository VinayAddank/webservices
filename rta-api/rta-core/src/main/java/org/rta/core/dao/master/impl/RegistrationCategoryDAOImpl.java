package org.rta.core.dao.master.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.RegistrationCategoryDAO;
import org.rta.core.entity.master.RegistrationCategoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationCategoryDAOImpl extends BaseDAO<RegistrationCategoryEntity>
        implements RegistrationCategoryDAO {

    public RegistrationCategoryDAOImpl() {
        super(RegistrationCategoryEntity.class);
    }
    
    @Override
    public RegistrationCategoryEntity getByCode(String code) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", code));
        return (RegistrationCategoryEntity) criteria.uniqueResult();
    }
}
