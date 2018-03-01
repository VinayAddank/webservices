package org.rta.core.dao.hsrp.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.hsrp.HSRPDetailDAO;
import org.rta.core.entity.hsrp.HSRPDetailEntity;
import org.rta.core.enums.RTAHSRPStatus;
import org.rta.core.enums.Status;
import org.rta.core.model.hsrp.HSRPDetailModel;
import org.springframework.stereotype.Repository;

@Repository
public class HSRPDetailDAOImpl extends BaseDAO<HSRPDetailEntity> implements HSRPDetailDAO {

	public HSRPDetailDAOImpl() {
		super(HSRPDetailEntity.class);
	}

	@Override
	public HSRPDetailEntity getByAuthRefNo(HSRPDetailModel hsrpDetailModel) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("authRefNo", hsrpDetailModel.getAuthRefNo()));
		return (HSRPDetailEntity) criteria.uniqueResult();
	}

	@Override
	public HSRPDetailEntity getByVehicleRcId(Long vehicleRcId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
		return (HSRPDetailEntity) criteria.uniqueResult();
	}

	@Override
	public List<HSRPDetailEntity> getAllOpenStatusData(Long from , Long to) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("rta_status", Status.OPEN.getValue()));
		criteria.add(Restrictions.eq("status", Status.OPEN.getValue()));
		criteria.add(Restrictions.le("iteration", 3));
		if (from != null && from.longValue() > 0l)
			criteria.add(Restrictions.ge("createdOn", from));
		if (to != null && to.longValue() > 0l)
			criteria.add(Restrictions.le("createdOn", to));
		return (List<HSRPDetailEntity>) criteria.list();
	}

	@Override
	public List<HSRPDetailEntity> getAllTRPostData(Long from , Long to) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("rta_status", RTAHSRPStatus.TR_POST.getValue()));
		criteria.add(Restrictions.eq("status", Status.OPEN.getValue()));
		criteria.createAlias("vehicleRcId", "vehRcId");
		criteria.add(Restrictions.eq("vehRcId.prStatus", Status.APPROVED.getValue()));
		criteria.add(Restrictions.le("iteration", 3));
		if (from != null && from.longValue() > 0l)
			criteria.add(Restrictions.ge("createdOn", from));
		if (to != null && to.longValue() > 0l)
			criteria.add(Restrictions.le("createdOn", to));
		return (List<HSRPDetailEntity>) criteria.list();
	}

}
