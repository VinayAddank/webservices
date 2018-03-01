package org.rta.core.service.citizen;

import java.util.List;

import org.rta.core.enums.ServiceType;
import org.rta.core.enums.citizen.SlotServiceType;
import org.rta.core.model.citizen.CitizenApplicationModel;
import org.rta.core.model.citizen.CitizenServiceResponseModel;
import org.rta.core.model.payment.tax.DifferentialTaxFeeModel;
import org.rta.core.model.sync.ApplicationFormDataModel;
import org.rta.core.service.citizen.model.AuthenticationModel;
import org.rta.core.service.citizen.model.CitizenTokenModel;


public interface CitizenService {

    public CitizenServiceResponseModel<List<CitizenApplicationModel>> getApplications(String token, ServiceType st, Long timestamp, SlotServiceType slotType); 
    

    public CitizenServiceResponseModel<String> saveOrUpdateByBodyBuilder(String appnumber, String token);


	public CitizenServiceResponseModel<ResponseModel<CitizenTokenModel>> login(AuthenticationModel authenticationModel,
			ServiceType st, String token);


	public CitizenServiceResponseModel<DifferentialTaxFeeModel> getDiffertialTaxFeeDetails(String trNumber, String token);


	public CitizenServiceResponseModel<ApplicationFormDataModel> getApplicantDetails(String applicationNo, String token);
}
