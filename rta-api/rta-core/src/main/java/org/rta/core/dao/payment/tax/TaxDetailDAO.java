package org.rta.core.dao.payment.tax;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.tax.TaxDetailEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

public interface TaxDetailDAO extends GenericDAO<TaxDetailEntity> {

    public TaxDetailEntity getByVehicleRcId(long vehicleRcId);

    /**
     * Get Tax Details for multiple vehicleRCEntity
     * 
     * @param vehicleRCEntities
     * @return
     */
    public List<TaxDetailEntity> getByVehicleRcEntities(List<VehicleRCEntity> vehicleRCEntities);
}
