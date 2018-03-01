package org.rta.core.dao.master.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.InsuranceTypeDAO;
import org.rta.core.entity.master.InsuranceTypeEntity;
import org.springframework.stereotype.Repository;

/**
 * @Author sohan.maurya created on Jul 11, 2016.
 */

@Repository("insuranceTypeDAO")
public class InsuranceTypeDAOImpl extends BaseDAO<InsuranceTypeEntity> implements InsuranceTypeDAO {

    public InsuranceTypeDAOImpl() {
        super(InsuranceTypeEntity.class);
    }

    @Override
    public InsuranceTypeEntity getByCode(String code) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", code));
        return (InsuranceTypeEntity) criteria.uniqueResult();
    }

}
