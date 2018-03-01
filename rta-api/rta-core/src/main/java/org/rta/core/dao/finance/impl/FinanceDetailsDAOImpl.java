package org.rta.core.dao.finance.impl;
 
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.finance.FinanceDetailsDAO;
import org.rta.core.entity.finance.FinanceDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.springframework.stereotype.Repository;

@Repository
public class FinanceDetailsDAOImpl extends BaseDAO<FinanceDetailsEntity> implements FinanceDetailsDAO {

	Criteria criteria ;
	public FinanceDetailsDAOImpl() {
		super(FinanceDetailsEntity.class);
		
		// TODO Auto-generated constructor stub
	}

    public FinanceDetailsEntity getFinanceDetailsOfVehicleRcd(Long vehicleId) {
		FinanceDetailsEntity entity =null;

		criteria = getSession().createCriteria(getPersistentClass());
		try{
            entity = (FinanceDetailsEntity) criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleId))
                    .uniqueResult();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return entity;
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<FinanceDetailsEntity> getFinanceDetailsByVehicleRCEntities(List<VehicleRCEntity> vehicleRCEntities) {
        criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.in("vehicleRcId", vehicleRCEntities));
                    
        return criteria.list();
}

}
