/**
 * 
 */
package org.rta.core.service.application;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.DocTypes;
import org.rta.core.enums.NotificationType;
import org.rta.core.enums.RegistrationCategoryType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.enums.VehicleCategory;
import org.rta.core.exception.ConflictException;
import org.rta.core.exception.InvalidStatusException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.exception.UserNotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.OwnerConscent;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.VehicleReassignmentModel;
import org.rta.core.model.application.ApplicationModel;
import org.rta.core.model.application.CommentModel;
import org.rta.core.model.application.GeneralDetails;
import org.rta.core.model.application.rejection.RejectionHistoryModel;
import org.rta.core.model.citizen.CitizenApplicationModel;
import org.rta.core.model.customer.CustMsgModel;
import org.rta.core.model.docs.ApplicantDocsModel;
import org.rta.core.model.finance.FreshRcAppStatusDetailsModel;
import org.rta.core.model.finance.FreshRcModel;
import org.rta.core.model.vehicle.SuspendedRCNumberModel;
import org.rta.core.service.citizen.ResponseModel;

/**
 * @author arun.verma
 *
 */
public interface ApplicationService {

    /**
     * Changes the application status to {@code status} </br>
     * If comment is given the comment is added to the application.
     * 
     * @param vehicleRcId
     * @param status
     * @param comment
     * @return generated id
     * @throws VehicleRcNotFoundException 
     * @throws NotFoundException 
     */
    public Map<Long, Status> changeStatus(Long userId, String userName, UserType userRole, Long vehicleRcId, Status status, CommentModel comment, String prNumber) throws InvalidStatusException, VehicleRcNotFoundException, NotFoundException;
    
    //public Map<Long, Status> changeStatus(Long userId, String userName, UserType userRole, Long vehicleRcId, Status status, CommentModel comment, String ip) throws InvalidStatusException, VehicleRcNotFoundException;
    
    public List<ApplicationModel> getApplication(Long userId, UserType userRole, Status status, Integer numberOfApplications);
    
    public HashMap<String, Object> getApprovedRejectedApps(Long userId, String query, Long from, Long to,
            Integer perPageRecords, Integer pageNumber, Status status, String regCatId);
    
    /*
     * public List<ApplicationModel> getRejectedApplications(Long userId, Long query, Long from,
     * Long to);
     */
    
    public Long countApplications(Long userId, Status status, String regCategory);

    public Long countApplicationsByRTAOffice(Long rtaOfficeId, Status status);
    
    public Boolean openApplication(Long vehicleRcId,Long userId,String ip, boolean newService) throws ConflictException, AccessDeniedException;

    public Map<String, String> getInfo(Long vehicleRcId, DocTypes docTypes, UserType userType) throws VehicleRcNotFoundException;
    
    /**
     * save action on document taken by Dealer, CCO, MVI, AO, RTO
     * 
     * @param applicantDocModel
     * @param userId
     * @param userName
     * @param userType
     * @param ipAddress
     * @param rejected
     * @return
     */
    public SaveUpdateResponse saveDocsAction(ApplicantDocsModel applicantDocModel, Long userId, String userName, UserType userType, String ipAddress, boolean rejected);
    
    /**
     * Documents fetching for vehicle for approval purpose 
     * @param vehicleRcId
     * @param userId
     * @param docForUserType
     * @return
     */
     public ApplicantDocsModel getDocsForApplicant(Long vehicleRcId,Long  userId,UserType docForUserType);
    
    /**
     * Returns rejection history for an application.
     * @param vehicleRcId
     * @return List<RejectionHistoryModel>
     * @throws VehicleRcNotFoundException 
     */
    public Map<Integer, Map<String, List<RejectionHistoryModel>>> rejectionHistory(Long vehicleRcId) throws VehicleRcNotFoundException;
    
    /**
     * Get isSecondVehicle by vehicleRcId
     * 
     * @param vehicleRcId
     * @return Boolean
     */
    public Boolean doNotRequireSecondVehicle(Long vehicleRcId);

    public CustMsgModel getCustDtlsForVehicleRCPR(Long vehicleRcId,NotificationType notificationType);
    
    
    /**
     * Status of all users allowed for 'usertype' for application wd vehicleRcId
     * @param vehicleRcId
     * @param userType
     * @return
     */
    public Map<UserType, Status> getStatusOfUsers(Long vehicleRcId, UserType userType) ;
    
    /**
     * Method to return Application info for vehicleRcId
     * @param vehicleRcId
     * @return
     */
    public ApplicationModel applicationForVehicleRc(Long vehicleRcId);

    Integer rejectionHistoryCount(Long vehicleRcId) throws VehicleRcNotFoundException;
    
    public Map<String, Object> getRejectionHistory4Correction(Long vehicleRcId, UserType userType)
            throws VehicleRcNotFoundException;

    /**
     * returns the file name in db for the userID
     * @param userId
     * @return
     */
    public String getEmployeeSignFileName(Long userId) ;
    
    
    /**
     * To log Event on  generation of PR or TR for saving attachments
     * @param notificationType
     * @param vehicleRcId
     * @param attachment
     * @return
     */
    public Boolean saveEventLogForAttachments(int notificationType,Long vehicleRcId,String attachment);

    public Map<String, Object> getApplicationsProcessedByUser(Long userId, String query, Long from, Long to,
            Integer perPageRecords, Integer pageNumber, Status status, UserType userType, String regCategory);

//    public Long countProcessedApplications(Long userId, Status status);

    Long countProcessedApplications(Long userId, String query, Long from, Long to, Integer perPageRecords,
            Integer pageNumber, Status status, UserType userType, String regCategory);

    /**
     * Get Pending Appliications for CCO, MVI, AO, RTO
     * 
     * @param userId
     * @param userRole
     * @param count
     * @param limit
     * @return
     */
    List<ApplicationModel> getPendingApplication(Long userId, UserType userType, boolean limit, String regCategory);

    public Map<Long, Status> changeStatus(Long userId, String userName, UserType userRole, Long vehicleRcId, Status status,
            CommentModel comment, String ip, String prNumber, Boolean skipSpecial) throws InvalidStatusException, VehicleRcNotFoundException, NotFoundException;

    public ApplicationModel applicationForVehicleRc(Long vehicleRcId, UserType userType);

    /**
     * Get Pr pending application ( i.e special Number application)
     * 
     * @param userId
     * @param userType TODO
     * @param trNumber
     * @param perPageRecords
     * @param pageNumber
     * @return
     */
    public Map<String, Object> getPrPendingApplications(Long userId, UserType userType, String trNumber,
            Integer perPageRecords, Integer pageNumber, String regCategory) throws Exception;

    /**
     * Get PR Pending Application Count
     * 
     * @param userId
     * @param userType TODO
     * @return
     * @throws Exception
     */
    public Integer getPrPendingApplicationsCount(Long userId, UserType userType, String regCategory) throws Exception;
    
    
    /**
     * Returns true if calling userId has this vehicleRc permission.
     * @param vehicleRc
     * @param userId
     * @return
     */
    public Boolean isValidCallerForVehicleRc(Long vehicleRc,Long userId);
    
    public ApplicationModel getPRDetails(String prNumber) throws NotFoundException;

    ApplicationModel getTRDetails(String prNumber) throws NotFoundException;
    
    public Long getPROrTRDetails(String number) throws NotFoundException;

    List<ApplicationModel> getPendingApplication(Long userId, UserType userType, boolean limit, String regCategory,
            VehicleCategory vehicleCategory);

    SaveUpdateResponse saveApplicationSlots(CitizenApplicationModel citizenApplicationModel) throws NotFoundException;
    
    SaveUpdateResponse generateReassignmentVehicle(VehicleReassignmentModel vehicleReassignmentModel) throws NotFoundException;
    
    public String getPrSeriesNo4Other(Long vehicleRCId) throws NotFoundException;

	public String getPrSeriesNo(VehicleRCEntity vehicleRCEntity, RegistrationCategoryType regType) throws org.rta.core.exception.NotFoundException;

    public Integer countPendingApplications(Long userId, UserType userRole, String regType);

    public ApplicationModel getOpenORPendingApplications(Long userId, UserType userType, String regType, Boolean openAction, VehicleCategory vc);

	public void updateOpenedApplications();
	void updateOpenedApplications1();
    public void movePrPendingAppActionHistory();

    void migrateScript();

    public GeneralDetails getGeneralDetails(Long vehicleRcId, UserType userType) throws NotFoundException;

    public String getRtoSignFileName(String officeCode);

    public String getMviSignFileName(String officeCode);

    public SuspendedRCNumberModel getSuspendededDetailsByRC(Long vehicleRcId);

    void revocateSuspensionExpiredVehicles();

	public FreshRcAppStatusDetailsModel getApplicationStatusDetails(Long vehicleRcId) throws VehicleRcNotFoundException, UserNotFoundException;

	public SaveUpdateResponse submitOwnerConscent(OwnerConscent ownerConscent, String appNumber);

	public ResponseModel<FreshRcModel> updateOwnerConscent(OwnerConscent ownerConscent, String appNumber);
}