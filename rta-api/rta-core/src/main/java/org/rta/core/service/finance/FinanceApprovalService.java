package org.rta.core.service.finance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.exception.FoundException;
import org.rta.core.exception.UserNotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.customer.CustMsgModel;
import org.rta.core.model.finance.FinanceApp;
import org.rta.core.model.finance.FinanceApproveRejectModel;
import org.rta.core.model.finance.FinanceModel;
import org.rta.core.model.finance.FinanceOtherServiceModel;
import org.rta.core.model.finance.FinancerModel;
import org.rta.core.model.sync.ApplicationFormDataModel;
import org.rta.core.model.sync.HPAHPTSyncModel;

import javassist.NotFoundException;

public interface FinanceApprovalService {
	/**
	 * Generate TokenID by dealer
	 * 
	 * @param vehiceRc
	 * @param dealerId
	 * @return
	 */
	public Map<String,String> getTokenForVehicle(Long vehiceRc, Long dealerId,Double quotationPrice);

	/**
	 * Get VehicleRcId For token
	 * 
	 * @param tokenId
	 * @return
	 */
	public HashMap<String, String> getVehicleForToken(String tokenId, Long financeId);

	/**
	 * Saves Financers reponse on receiving initial request.
	 * 
	 * @param tokenId
	 * @param status
	 * @param financerId
	 */
	public String approveRejectFinanceRequest(String tokenId, Status status, Long financerId);

	/**
	 * Get list of financer who accepted the customers request for Finances.
	 * 
	 * @param tokenId
	 * @param status
	 * @param financerId
	 */
	public List<FinancerModel> getFinancersListForVehicle(Long vehicleRcId);
	
	
	public List<FinancerModel> getFinancersListForToken(String tokenId);

	
	/**
	 * Dealer will get list of financer who accepted Customer's financing
	 * request. Dealer need to save one out of those options This api saves
	 * Dealers response.
	 * 
	 * @param tokenId
	 * @param status
	 * @param dealerId
	 */
	public void saveDealerResponse(Long vehicleRcId, Long financer, Long dealerId);

	/**
	 * Citizen will get list of financer who accepted Its financing
	 * request. Dealer need to save one out of those options This api saves
	 * Citizen response.
	 * 
	 * @param tokenId
	 * @param status
	 * @param dealerId
	 * @throws FoundException 
	 */
	public void saveCitizenResponse(String appNum, Long financerId, Long dealerId) throws FoundException;
	/**
	 * List of Applications with specified Status
	 * 
	 * @param tokenId
	 * @param financeId
	 * @param status
	 * @return
	 */
	public HashMap getAppList4Financer(Long financeId, String tokenId, List<Integer> status,ServiceType service,String rtaOffice, Integer perPageRecords,
			Integer pageNumber);

	/**
	 * Final approval/rejection by financer
	 * 
	 * @param tokenId
	 * @param status
	 * @param financerId
	 */
	public String approveRejectFinalFinances(String tokenId, Status status, Long financerId,
			FinanceModel financeModel);

	/**
	 * Get application on financier end for search
	 * 
	 * @param financeId
	 * @param status
	 * @param tokenId
	 * @return
	 */
	public FinanceApp getApp4Financer(Long financeId, List<Integer> status, String tokenId);

	/**
	 * When citizen apply for HPT This service is called to add request to
	 * respective financer
	 *
	 * @param tokenId
	 * @param vehicleRcId
	 * @param requester
	 * @return
	 */
//	public String applyForHPTByCitizenForFinancerEnd(HPTFormModel hptFormModel);

	/**
	 * Save Financer response for HPT request
	 * 
	 * @param financModel
	 * @return
	 * @throws NotFoundException 
	 */
	public String approveRejectHPTRequest(FinanceApproveRejectModel financModel) throws NotFoundException;

	/**
	 * getHPT status form financer
	 * 
	 * @param 
	 * @return
	 * @throws UserNotFoundException 
	 * @throws NotFoundException 
	 * @throws VehicleRcNotFoundException 
	 */
	public FinanceOtherServiceModel getHPTStatusOfVehicle(String prNumber,String appNumber) throws VehicleRcNotFoundException, NotFoundException, UserNotFoundException;

	/**
	 * token generated at citizen portal needed to be saved at registration end
	 * of financier
	 * 
	 * @param tokenIdfromCitizen
	 * @param vehiclercId
	 */
	public void saveTokenForHPA(String tokenIdfromCitizen, String prNumber,Double quotationPrice);

	/**
	 * get details for sanction letter
	 * @param tokenId
	 * @param financerId
	 * @return
	 */
	public FinanceApp getDetailsForSanctionLetterGeneration(String tokenId,Long financerId);
	/**
	 * 
	 * @param tokenId
	 * @param financerId
	 * @param urlSanctionLetter
	 * @return
	 */
	public String saveSanctionLetterURL(String tokenId,Long financerId,String urlSanctionLetter);
	
	/**
	 * getVehicleRc from Pr number
	 * @param prNum
	 * @return
	 */
	public Long getVehicleRcIdfromPr(String prNum);
	
	/**
	 * Finance related details are returned by this API
	 * @param tokenId
	 * @param financerId
	 * @return
	 */
	public FinanceModel getCustomerFinanceDtls(String tokenId,Long financerId);
	
	public Boolean isHPAAppliedForPRnum(String prNum) throws VehicleRcNotFoundException;
	
	public Boolean isHPAAppliedForPRnum(Long vehicleRcId) throws VehicleRcNotFoundException;

	public FinanceOtherServiceModel applyForFinanceApprovalOtherService(FinanceOtherServiceModel model,Long requester)throws VehicleRcNotFoundException, NotFoundException, FoundException;

	public String approveRejectOtherService(FinanceApproveRejectModel model) throws NotFoundException;

//	public HashMap listOfAppOnServiceType(Status status, String rtaOffice,ServiceType service, Long financierId, Integer perPageRecords, Integer pageNumber);

	public HashMap<String,List> listOfRtoService4Financer(Long financierId, List<Integer> status);
	
	public Boolean isOnlineFinanced(String prNum) ;
	
	public void financeApproveRejectByRTO(String userName,Status status, HPAHPTSyncModel hPAHPTSyncModel) ;
	
	public CustMsgModel setMsgModel(String financeToken, Status statusType, Long financer);

    public void actionHPAByRTA(String userName, Status status, Long vehicleRcId);

    HashMap<String, Object> getAppList4FinancerNew(Long financeId, String tokenId, List<Integer> status,
            ServiceType service, String rtaOffice, Integer perPageRecords, Integer pageNumber);

    public SaveUpdateResponse reIterateAppFinanceStep(String appNo);

    CustMsgModel getCustomerMessageModel(Long financerId, Long dealerId, Long vehicleRcId);

    public FinanceModel getFinanceDeatils(String prNum, Long vehicleRcId) throws VehicleRcNotFoundException;

    public CustMsgModel setMsgModelOTD(String appNum, Status status, Long financerId, ApplicationFormDataModel applicationFormDataModel, String tokenHeader) throws Exception;

    public Integer getServiceCode(String appNum);

}
