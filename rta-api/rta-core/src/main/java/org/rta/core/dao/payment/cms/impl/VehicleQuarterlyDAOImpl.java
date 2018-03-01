package org.rta.core.dao.payment.cms.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.payment.cms.VehicleQuarterlyDAO;
import org.rta.core.entity.payment.cms.VehicleQuarterlyEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleQuarterlyDAOImpl extends BaseDAO<VehicleQuarterlyEntity> implements VehicleQuarterlyDAO {

    public VehicleQuarterlyDAOImpl() {
        super(VehicleQuarterlyEntity.class);
    }
    
    public List<String> getAllCodes(){
    	Criteria criteria = getSession().createCriteria(getPersistentClass());
    	criteria.setProjection(Projections.property("vehicleCode"));
    	return (List<String>) criteria.list();
    }


}
