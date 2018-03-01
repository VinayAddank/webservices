package org.rta.core.dao.master.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.PrimaryTempPermitMappingDAO;
import org.rta.core.entity.master.PrimaryTempPermitMappingEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PrimaryTempPermitMappingDAOImpl extends BaseDAO<PrimaryTempPermitMappingEntity> implements PrimaryTempPermitMappingDAO {

    public PrimaryTempPermitMappingDAOImpl() {
        super(PrimaryTempPermitMappingEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PrimaryTempPermitMappingEntity> getList(String primaryPermitType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("primaryPermitCode", primaryPermitType));
        return criteria.list();
    }

}
