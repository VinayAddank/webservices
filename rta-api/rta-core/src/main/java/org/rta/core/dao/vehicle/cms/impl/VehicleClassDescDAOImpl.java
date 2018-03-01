package org.rta.core.dao.vehicle.cms.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.cms.VehicleClassDescDAO;
import org.rta.core.entity.vehicle.cms.VehicleClassDescriptionEntity;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleClassDescDAOImpl extends BaseDAO<VehicleClassDescriptionEntity> implements VehicleClassDescDAO {

	public VehicleClassDescDAOImpl() {
		super(VehicleClassDescriptionEntity.class);
	}

	public VehicleClassDescDAOImpl(Class<VehicleClassDescriptionEntity> persistentClass) {
		super(persistentClass);
	}

	@SuppressWarnings("unchecked")
	@Override
    public List<VehicleClassDescriptionEntity> getAllClassDes4VehicleCateg(String regCategoryCode,
            String vehicleClassCode, Integer alterationCategory, boolean isNewVehicle) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.createAlias("vehicleClassId", "vcId");
		if(!ObjectsUtil.isNull(vehicleClassCode)){
		    criteria.add(Restrictions.eq("vcId.vehicleClassCode", vehicleClassCode));
		}
		if(!ObjectsUtil.isNull(alterationCategory)){
            criteria.add(Restrictions.eq("alterationCategory", alterationCategory));
        }
		if(isNewVehicle){
            criteria.add(Restrictions.isNotNull("alterationCategory"));
        }
        criteria.add(Restrictions.eq("vcId.vehicleCateg", regCategoryCode));
		return (List<VehicleClassDescriptionEntity>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<String> getAllClassDesCode4VehicleCateg(String regCategoryCode, String vehicleClassCode) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.createAlias("vehicleClassId", "vcId");
		criteria.add(Restrictions.eq("vcId.vehicleClassCode", vehicleClassCode));
        criteria.add(Restrictions.eq("vcId.vehicleCateg", regCategoryCode));
		criteria.setProjection(Projections.property("code"));
		return (List<String>) criteria.list();
	}

    @Override
    public VehicleClassDescriptionEntity getByVehiclClassDesCode(String vehicleDescClassCode) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", vehicleDescClassCode));
        criteria.setMaxResults(1);
        return (VehicleClassDescriptionEntity) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleClassDescriptionEntity> getByVehiclClassDescriptoin(Set<String> vehiclClassCodes) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.in("code", vehiclClassCodes));
        return criteria.list();
    }

	@Override
	public List<VehicleClassDescriptionEntity> getByVehiclClassDesCodeByRegCatCode(Set<String> vehicleDescClassCodeList,
			String regCategoryCode) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.createAlias("vehicleClassId", "vcId");
		criteria.add(Restrictions.in("code", vehicleDescClassCodeList));
		criteria.add(Restrictions.eq("vcId.vehicleCateg", regCategoryCode));
		return criteria.list();
	}
}
