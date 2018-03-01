package org.rta.core.dao.master.registration.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.registration.PrSeriesMasterDAO;
import org.rta.core.entity.master.registration.PrSeriesMasterEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.RegistrationCategoryType;
import org.rta.core.enums.Status;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.stereotype.Repository;

@Repository
public class PrSeriesMasterDAOImpl extends BaseDAO<PrSeriesMasterEntity> implements PrSeriesMasterDAO {

    public PrSeriesMasterDAOImpl() {
        super(PrSeriesMasterEntity.class);
    }

    public PrSeriesMasterEntity getByRTAOffice(RtaOfficeEntity rtaOfficeEntity , VehicleRCEntity vehicleRCEntity, RegistrationCategoryType regCat) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("status", Status.PRESENT.getValue()));
        criteria.add(Restrictions.eq("rtaOffice.rtaOfficeId", rtaOfficeEntity.getRtaOfficeId()));
        if(ObjectsUtil.isNull(regCat)){
        	criteria.add(Restrictions.eq("regType", vehicleRCEntity.getRegCategory().getRegistrationCategoryId()));
        } else {
        	criteria.add(Restrictions.eq("regType", regCat.getValue()));
        }
        return (PrSeriesMasterEntity) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PrSeriesMasterEntity> getFuturePrSeries(RtaOfficeEntity rtaOfficeEntity , VehicleRCEntity vehicleRCEntity, RegistrationCategoryType regCat) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("rtaOffice.rtaOfficeId", rtaOfficeEntity.getRtaOfficeId()));
        criteria.add(Restrictions.eq("status", Status.FUTURE.getValue()));
        if(ObjectsUtil.isNull(regCat)){
        	criteria.add(Restrictions.eq("regType", vehicleRCEntity.getRegCategory().getRegistrationCategoryId()));
        } else {
        	criteria.add(Restrictions.eq("regType", regCat.getValue()));
        }
        return (List<PrSeriesMasterEntity>) criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<String> getAllSeriesByRTAOffice(VehicleRCEntity vehicleRCEntity) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("rtaOffice.rtaOfficeId", vehicleRCEntity.getRtaOfficeId().getRtaOfficeId()));
        criteria.add(Restrictions.eq("regType", vehicleRCEntity.getRegCategory().getRegistrationCategoryId()));
        criteria.setProjection(Projections.property("series"));
        return criteria.list();
    }
    
	// created by piyush.singh
	@Override
	public List<PrSeriesMasterEntity> getAllPresentSeries(Long rtoOfcId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("status", Status.PRESENT.getValue()));
		criteria.add(Restrictions.eq("rtaOffice.rtaOfficeId", rtoOfcId));
		return (List<PrSeriesMasterEntity>) criteria.list();
	}

	@Override
	public List<PrSeriesMasterEntity> getAllPresentPastSeries(Long rtoOfcId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.or(Restrictions.eq("status", Status.PRESENT.getValue()),
				Restrictions.eq("status", Status.PAST.getValue())));
		criteria.add(Restrictions.eq("rtaOffice.rtaOfficeId", rtoOfcId));
		return (List<PrSeriesMasterEntity>) criteria.list();
	}

	@Override
	public PrSeriesMasterEntity getRegTypeBySeries(String seriesCode) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		 criteria.add(Restrictions.eq("series", seriesCode));
		 criteria.add(Restrictions.eq("status", Status.PRESENT.getValue()));
		return (PrSeriesMasterEntity) criteria.uniqueResult();
	}
}
