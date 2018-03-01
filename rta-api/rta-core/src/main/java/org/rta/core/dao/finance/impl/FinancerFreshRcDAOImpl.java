package org.rta.core.dao.finance.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.finance.FinancerFreshRcDAO;
import org.rta.core.entity.finance.FinancerFreshRcEntity;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.utils.DateUtil;
import org.springframework.stereotype.Repository;

@Repository
public class FinancerFreshRcDAOImpl extends BaseDAO<FinancerFreshRcEntity> implements FinancerFreshRcDAO {

	public FinancerFreshRcDAOImpl() {
		super(FinancerFreshRcEntity.class);
	}

	public FinancerFreshRcEntity getFreshRcFromVehicleRc(Long vehicleRc) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRc)).addOrder(Order.desc("modifiedOn"));
		FinancerFreshRcEntity entity = (FinancerFreshRcEntity) criteria.setMaxResults(1).uniqueResult();
		return entity;
	}

	public List<FinancerFreshRcEntity> getFreshRcList(UserType approver, Status status) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		if (approver == UserType.ROLE_CCO)
			criteria = criteria.add(Restrictions.eq("ccoStatus", status.getValue()));
		if (approver == UserType.ROLE_DTC)
			criteria = criteria.add(Restrictions.eq("dtcStatus", status.getValue()));
		criteria = criteria.addOrder(Order.desc("modifiedOn"));
		List<FinancerFreshRcEntity> entities = (List<FinancerFreshRcEntity>) criteria.list();
		return entities;
	}

	public FinancerFreshRcEntity getFreshRcFromApplicationNumber(String applicationNumber) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("applicationNumber", applicationNumber));
		FinancerFreshRcEntity entity = (FinancerFreshRcEntity) criteria.uniqueResult();
		return entity;
	}

	@Override
	public List<FinancerFreshRcEntity> getOpenedApplications() {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.isNull("ownerConsent"));
		List<FinancerFreshRcEntity> entity = (List<FinancerFreshRcEntity>) criteria.list();
		return entity;
	}
}
