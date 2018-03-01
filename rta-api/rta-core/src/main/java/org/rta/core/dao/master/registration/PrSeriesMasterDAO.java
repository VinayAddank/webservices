package org.rta.core.dao.master.registration;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.registration.PrSeriesMasterEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.RegistrationCategoryType;

public interface PrSeriesMasterDAO extends GenericDAO<PrSeriesMasterEntity> {
    public PrSeriesMasterEntity getByRTAOffice(RtaOfficeEntity rtaOfficeEntity , VehicleRCEntity vehicleRCEntity, RegistrationCategoryType regCat);
    
    public List<PrSeriesMasterEntity> getFuturePrSeries(RtaOfficeEntity rtaOfficeEntity , VehicleRCEntity vehicleRCEntity, RegistrationCategoryType regCat);

    public List<String> getAllSeriesByRTAOffice(VehicleRCEntity vehicleRCEntity);
    
    public PrSeriesMasterEntity getRegTypeBySeries(String rtoOfcId);
    public List<PrSeriesMasterEntity> getAllPresentSeries(Long rtoOfcId);
    public List<PrSeriesMasterEntity> getAllPresentPastSeries(Long rtoOfcId);

}
