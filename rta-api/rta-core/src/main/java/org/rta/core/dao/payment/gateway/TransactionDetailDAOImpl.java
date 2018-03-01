package org.rta.core.dao.payment.gateway;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.enums.PaymentType;
import org.rta.core.enums.Status;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDetailDAOImpl extends BaseDAO<TransactionDetailEntity> implements TransactionDetailDAO {

	public TransactionDetailDAOImpl() {
		super(TransactionDetailEntity.class);
	}

    public TransactionDetailEntity getByVehicleRcIdNdPaymentType(long vehicleRcId, Integer payType) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
        criteria.add(Restrictions.eq("paymentType", payType));
		criteria.add(Restrictions.disjunction().add(Restrictions.eq("status", Status.OPEN.getValue()))
				.add(Restrictions.eq("status", Status.PENDING.getValue())));
		return (TransactionDetailEntity) criteria.uniqueResult();
	}

	@Override
	public TransactionDetailEntity getByTransNoNdVehicleRcId(String transactionNo, long vehicleRcId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("transactionNo", transactionNo));
		criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
		return (TransactionDetailEntity) criteria.uniqueResult();
	}

	@Override
	public TransactionDetailEntity getByVehicleRcNdStatus(long vehicleRcId, PaymentType payType, Status status) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
		criteria.add(Restrictions.eq("paymentType", payType.getId()));
		criteria.add(Restrictions.eq("status", status.getValue()));
		return (TransactionDetailEntity) criteria.uniqueResult();
	}

	public List<TransactionDetailEntity> getAllByVehicleRcNdPayType(long vehicleRcId, Integer payType) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
		criteria.add(Restrictions.eq("paymentType", payType));
		return (List<TransactionDetailEntity>) criteria.list();
	}

	@Override
	public List<TransactionDetailEntity> getAllByFromAndToDate(Long from, Long to) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		if (from != null && from.longValue() > 0l)
			criteria.add(Restrictions.ge("createdOn", from));
		if (to != null && to.longValue() > 0l)
			criteria.add(Restrictions.le("createdOn", to));
		return (List<TransactionDetailEntity>) criteria.list();
	}

	

}
