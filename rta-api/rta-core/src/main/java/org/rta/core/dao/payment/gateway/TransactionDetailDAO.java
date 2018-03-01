package org.rta.core.dao.payment.gateway;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.enums.PaymentType;
import org.rta.core.enums.Status;

public interface TransactionDetailDAO extends GenericDAO<TransactionDetailEntity> {

	/* public TransactionDetailEntity getByVehicleRcId(long vehicleRcId); */

	public TransactionDetailEntity getByTransNoNdVehicleRcId(String transactionNo, long vehicleRcId);

    public TransactionDetailEntity getByVehicleRcIdNdPaymentType(long vehicleRcId, Integer payType);

	public TransactionDetailEntity getByVehicleRcNdStatus(long vehicleRcId, PaymentType payType, Status status);

	public List<TransactionDetailEntity> getAllByVehicleRcNdPayType(long vehicleRcId, Integer payType);

	public List<TransactionDetailEntity> getAllByFromAndToDate(Long from, Long to);

}
