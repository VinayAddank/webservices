package org.rta.core.dao.finance.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.finance.FinancerSeizedVehiclesDAO;
import org.rta.core.entity.finance.FinancerSeizedVehiclesEntity;
import org.springframework.stereotype.Repository;

@Repository
public class FinancerSeizedVehiclesDAOImpl extends BaseDAO<FinancerSeizedVehiclesEntity> implements FinancerSeizedVehiclesDAO {

    public FinancerSeizedVehiclesDAOImpl() {
        super(FinancerSeizedVehiclesEntity.class);
    }

    @Override
    public FinancerSeizedVehiclesEntity getSeizedVehicle(Long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
        criteria.addOrder(Order.desc("createdOn"));
        criteria.setMaxResults(1);
        return (FinancerSeizedVehiclesEntity) criteria.uniqueResult();
    }

}
