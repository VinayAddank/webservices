package org.rta.core.dao.payment.gateway;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.gateway.TransactionHistoryEntity;

public interface TransactionHistoryDAO extends GenericDAO<TransactionHistoryEntity> {

	public List<TransactionHistoryEntity> getAllByFromAndToDate(Long from, Long to);
}
