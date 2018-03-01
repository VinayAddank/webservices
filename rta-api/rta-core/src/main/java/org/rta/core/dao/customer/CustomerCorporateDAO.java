package org.rta.core.dao.customer;

import java.util.List;
import java.util.Map;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

public interface CustomerCorporateDAO extends GenericDAO<CustCorporateDetailsEntity> {

    public CustCorporateDetailsEntity getByVehicleRcId(long vehicleRcId);

    public VehicleRCEntity getVehicleRcIdUsingAadharNo(long aadharNo);

    public VehicleRCEntity getVehicleRcIdUsingVehicleRcId(String vehicleRcId);

    public AddressEntity getAddressDetailsByUserId(Long CustIndDtlId, String addressType);

    public Map<String, Object> getByVehicleRcIds(List<Long> ids, Long from, Long to, String query);
}
