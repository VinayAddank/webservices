package org.rta.core.dao.vehicle.cms.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.cms.ModelDetailsDAO;
import org.rta.core.entity.vehicle.cms.ModelDetailsEntity;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Sep 9, 2016.
 */
@Repository("modelDetailsDAO")
public class ModelDetailsDAOImpl extends BaseDAO<ModelDetailsEntity> implements ModelDetailsDAO {

    public ModelDetailsDAOImpl() {
        super(ModelDetailsEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ModelDetailsEntity> getModelByMakerId(Integer makerId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("makerMasterId.makerId", makerId));
        return criteria.list();
    }

}
