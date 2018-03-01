/**
 * 
 */
package org.rta.core.dao.insurance;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.insurance.InsuranceDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

/**
 * @author arun.verma
 *
 */
public interface InsuranceDetailsDAO extends GenericDAO<InsuranceDetailsEntity>{

    /*
     * Get InsuranceDetailsId
     * 
     * @Param Long vehicleRcId
     * @Return Long InsuranceDetailsId
     */
    public Long getInsuranceDtlsIdByVehicleRcId(Long vehicleRcId);
    
    /*
     * Get InsuranceDetailsEntity
     * 
     * @Param Long vehicleRcId
     * @Return InsuranceDetailsEntity
     */
    public InsuranceDetailsEntity getInsuranceDtlsEntityByVehicleRcId(Long vehicleRcId);

    /**
     * Get all InsuranceDetailsEntities for multiple vehicleRCEntities
     * 
     * @param vehicleRCEntities
     * @return
     */
    public List<InsuranceDetailsEntity> getInsuranceDtlsEntitiesByVehicleRCEntities(
            List<VehicleRCEntity> vehicleRCEntities);
    
    public InsuranceDetailsEntity getInsuranceDtlsByPolicyNo(String policyNo);
}
