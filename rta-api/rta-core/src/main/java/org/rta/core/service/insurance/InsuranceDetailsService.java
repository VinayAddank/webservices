/**
 * 
 */
package org.rta.core.service.insurance;

import java.util.List;

import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.insurance.IibResponseModel;
import org.rta.core.model.insurance.InsuranceDetailsModel;
import org.rta.core.model.user.UserModel;

/**
 * @author arun.verma
 *
 */
public interface InsuranceDetailsService {

    /*
     * Save InsuranceDetails
     * 
     * @Param InsuranceDetailsModel
     * 
     * @Param UserModel
     * 
     * @Return SaveUpdateResponse
     */
    public SaveUpdateResponse saveUpdate(InsuranceDetailsModel insuranceDetails, UserModel user);

    /*
     * Get InsuranceDetails By VehicleRcId
     * 
     * @Param Long vehicleRcId
     * 
     * @Return InsuranceDetailsModel
     */
    public InsuranceDetailsModel getInsuranceDtlsByVehicleRcId(Long vehicleRcId);

    /*
     * Get All InsuranceDetails
     * 
     * @Return List<InsuranceDetailsModel>
     */
    public List<InsuranceDetailsModel> getAll();
    
    public InsuranceDetailsModel getInsuranceDtlsByPolicyNo(String policyNo);
    
    /**
     * @author Gautam.kumar
     * @description To validate insurance policy by iib
     * @param vehicleRcId
     * @return
     */
    public boolean isValidInsurancePolicy(String vehicleRcId);
    
    /**
     * @author Gautam.kumar
     * @description Get insurance details from iib
     * @param vehicleRcId
     * @return
     */
    public InsuranceDetailsModel getIibInsuranceDetailsByVehicleRcId(Long vehicleRcId);
}
