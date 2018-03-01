package org.rta.core.dao.customer;

import java.util.List;
import java.util.Map;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

public interface CustomerDAO extends GenericDAO<CustIndividualDetailsEntity> {

    public CustIndividualDetailsEntity getByVehicleRcId(long vehicleRcId);

    public VehicleRCEntity getVehicleRcIdUsingAadharNo(long aadharNo);

    public VehicleRCEntity getVehicleRcIdUsingVehicleRcId(String vehicleRcId);

    public AddressEntity getAddressDetailsByUserId(Long CustIndDtlId, String addressType);

    public Map<String, Object> getByVehicleRcIds(List<Long> ids, Long from, Long to, String query);

    /**
     * get date of birth from aadhar card for attachment related documents
     * 
     * @param vehicleRcId
     * @return
     */
    public String getDateOfBirthByVehicleRcId(Long vehicleRcId);

    /**
     * to check customer is isInvalidCarriage and isDisabled
     * 
     * @param vehicleRcId
     * @return
     */
    public Boolean getIsCustomerDisabled(Long vehicleRcId);

    /**
     * to check customer is Physically Handicap
     * 
     * @param vehicleRcId
     * @return
     */
    public Boolean getIsCustomerPhysicallyHandicap(Long vehicleRcId);
}
