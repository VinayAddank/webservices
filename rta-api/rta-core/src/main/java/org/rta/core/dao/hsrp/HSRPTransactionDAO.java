package org.rta.core.dao.hsrp;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.hsrp.HSRPTransactionEntity;

public interface HSRPTransactionDAO extends GenericDAO<HSRPTransactionEntity> {

    public HSRPTransactionEntity getByVehicleRcId(long vehicleRcId);

    public HSRPTransactionEntity getByOtherChargesTID(String otherChargesTID);
}
