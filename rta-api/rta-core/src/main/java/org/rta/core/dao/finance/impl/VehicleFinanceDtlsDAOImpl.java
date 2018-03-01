package org.rta.core.dao.finance.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.finance.VehicleFinanceDtlsDAO;
import org.rta.core.entity.finance.VehicleFinanceDtlstEntity;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.stereotype.Repository;


@Repository
public class VehicleFinanceDtlsDAOImpl extends BaseDAO<VehicleFinanceDtlstEntity>implements VehicleFinanceDtlsDAO {

	public VehicleFinanceDtlsDAOImpl(Class<VehicleFinanceDtlstEntity> persistentClass) {
		super(persistentClass);
		// TODO Auto-generated constructor stub
	}
	
	public VehicleFinanceDtlsDAOImpl(){
		super(VehicleFinanceDtlstEntity.class);
	}
	
	@Override
	public List<VehicleFinanceDtlstEntity> getByVehicleRcId(Long vehicleRcId){
        Criteria criteria = getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
        return (List<VehicleFinanceDtlstEntity>) criteria.list();
    }
	
	public VehicleFinanceDtlstEntity getRcrdForVehicleRc(Long vehicleRcId){
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId))
//				.add(Restrictions.eq("financeTerminated",false))
				.addOrder(Order.desc("createdOn"))
				.setMaxResults(1);
		VehicleFinanceDtlstEntity financeDtlsEntity = (VehicleFinanceDtlstEntity) criteria.uniqueResult();
		return financeDtlsEntity;
	}
	
	public VehicleFinanceDtlstEntity getVehicleFinanceRcr(Long vehicleRcId,Integer status){
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
				if(!ObjectsUtil.isNull(status)){
				    criteria.add(Restrictions.eq("rtoApproved",status));
				}
				criteria.addOrder(Order.desc("createdOn"))
				.setMaxResults(1);
		VehicleFinanceDtlstEntity financeDtlsEntity = (VehicleFinanceDtlstEntity) criteria.uniqueResult();
		return financeDtlsEntity;
	}

}
