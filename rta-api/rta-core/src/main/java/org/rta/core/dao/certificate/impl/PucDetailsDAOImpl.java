package org.rta.core.dao.certificate.impl;

import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.certificate.PucDetailsDAO;
import org.rta.core.entity.certificate.PucDetailsEntity;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Dec 15, 2016.
 */
@Repository("pucDetailsDAO")
public class PucDetailsDAOImpl extends BaseDAO<PucDetailsEntity> implements PucDetailsDAO {

    public PucDetailsDAOImpl() {
        super(PucDetailsEntity.class);
    }

    @Override
    public PucDetailsEntity getPucDetails(Long vehicleRcId) {
        
        return (PucDetailsEntity) getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId)).uniqueResult();
    }


}
