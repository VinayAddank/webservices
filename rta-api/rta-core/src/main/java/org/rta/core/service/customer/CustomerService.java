package org.rta.core.service.customer;

import java.util.List;

import org.rta.core.exception.DataMismatchException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.AadharModel;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.customer.CustomerDetailsRequestModel;
import org.rta.core.model.customer.CustomerModel;
import org.rta.core.model.user.AddressChangeModel;
import org.rta.core.model.vehicle.RCListModel;

/**
 * AadharInfoService provides details for aadhar
 * 
 * @author Sriram
 *
 */
public interface CustomerService {

	public abstract Long update(Long userId, CustomerDetailsRequestModel customerModel,
			String token,
			Integer currentStep);

	public abstract CustomerDetailsRequestModel getCustomerInfoStepOne(String vehicleRcId);

    public Long saveUpdateCompany(Long userId, CustomerDetailsRequestModel customerModel, String userName,
            Integer currentStep);

    public SaveUpdateResponse updatePermanentAdrsWithAadhar(Long userId, String userName, Long vehicleRcId,
            AadharModel aadhar);

    public CustomerModel getCustomerIndvDetail(Long vehicleRcId);

    public SaveUpdateResponse updateCustomerDetails(AddressChangeModel model, String userName)throws DataMismatchException;
    
    public SaveUpdateResponse lockRC(String authToken, RCListModel rcList) throws VehicleRcNotFoundException;
    
    public SaveUpdateResponse unLockRC(String authToken, RCListModel rcList, Long dateTime) throws VehicleRcNotFoundException;

    /**
     * Move Aadhar pending application to RTA end
     * 
     * @param vehicleRcId
     * @param aadharNo
     * @return
     */
    public SaveUpdateResponse moveAadharPendingToRTA(Long vehicleRcId, String aadharNo);

}
