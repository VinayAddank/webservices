package org.rta.core.dao.payment.gateway;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.gateway.TransactionHistoryEntity;
import org.rta.core.enums.PaymentType;
import org.rta.core.enums.Status;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionHistoryDAOImpl extends BaseDAO<TransactionHistoryEntity> implements TransactionHistoryDAO {

	public TransactionHistoryDAOImpl() {
		super(TransactionHistoryEntity.class);
	}

	@Override
	public List<TransactionHistoryEntity> getAllByFromAndToDate(Long from, Long to) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		if (from != null && from.longValue() > 0l)
			criteria.add(Restrictions.ge("createdOn", from));
		if (to != null && to.longValue() > 0l)
			criteria.add(Restrictions.le("createdOn", to));
		criteria.add(Restrictions.eq("paymentType", PaymentType.PAYTAX.getId()));
		criteria.add(Restrictions.eq("status", Status.OPEN.getValue()));
		return (List<TransactionHistoryEntity>) criteria.list();
	}
}
