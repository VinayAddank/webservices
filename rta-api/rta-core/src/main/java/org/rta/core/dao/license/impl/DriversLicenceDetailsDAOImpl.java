package org.rta.core.dao.license.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.license.DriversLicenceDetailsDAO;
import org.rta.core.entity.licence.DriversLicenceDetailsEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DriversLicenceDetailsDAOImpl extends BaseDAO<DriversLicenceDetailsEntity>
		implements DriversLicenceDetailsDAO {

	public DriversLicenceDetailsDAOImpl() {
		super(DriversLicenceDetailsEntity.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DriversLicenceDetailsEntity> getDriverPermitDtl(Long holderId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("id.licenceHolderId", holderId)).addOrder(Order.asc("id.dlSequenceId"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DriversLicenceDetailsEntity> getDriversLicenceDetails(String llOrDlOrApplicationNumber) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		Criterion llrNumber = Restrictions.eq("llrNo", llOrDlOrApplicationNumber);
		Criterion ldNumber = Restrictions.eq("dlNo", llOrDlOrApplicationNumber);
		Criterion applicationNumber = Restrictions.eq("applicationId", llOrDlOrApplicationNumber);
		Disjunction LogicllOrDlOrApplicationNumber = Restrictions.or(llrNumber, ldNumber, applicationNumber);
		// LogicalExpression logicLlAndDlNumber=Restrictions.or(llrNumber,
		// ldNumber);
		criteria.add(LogicllOrDlOrApplicationNumber);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DriversLicenceDetailsEntity> getDriverPermitDtl(String dlNo, String rtaOfficeCode) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("dlNo", dlNo));
		criteria.add(Restrictions.eq("rtaOfficeId.code", rtaOfficeCode).ignoreCase());
		return criteria.list();
	}

	@Override
	public DriversLicenceDetailsEntity getDriversLicenceDetails(String vehicleClass, Long licenceHolderId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("id.licenceHolderId", licenceHolderId))
				.add(Restrictions.eq("dlVehicleClassCode", vehicleClass));
		return (DriversLicenceDetailsEntity) criteria.uniqueResult();
	}

	@Override
	@Transactional
	public DriversLicenceDetailsEntity getLastAppliedBadgeDetails() {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.isNotNull("dlBadgeNo"));
        criteria.addOrder(Order.desc("createdOn"));
        criteria.setMaxResults(1);
        return (DriversLicenceDetailsEntity) criteria.uniqueResult();
	}

}
