package org.rta.core.dao.payment.tax;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.tax.GreenTaxDetailsEntity;

public interface GreenTaxDetailsDAO extends GenericDAO<GreenTaxDetailsEntity> {

    public GreenTaxDetailsEntity getByVehicleRcId(long vehicleRcId);


}
