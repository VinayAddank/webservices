package org.rta.core.dao.certificate.impl;

import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.certificate.NocDetailsDAO;
import org.rta.core.entity.certificate.NocDetailsEntity;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Dec 13, 2016.
 */

@Repository("nocDetailsDAO")
public class NocDetailsDAOImpl extends BaseDAO<NocDetailsEntity> implements NocDetailsDAO {

    public NocDetailsDAOImpl() {
        super(NocDetailsEntity.class);
    }

    @Override
    public NocDetailsEntity getNocDetails(Long vehicleRcId) {

        return (NocDetailsEntity) getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId)).uniqueResult();
    }

}
