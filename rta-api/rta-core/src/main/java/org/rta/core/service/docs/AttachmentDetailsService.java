package org.rta.core.service.docs;

import java.util.List;
import java.util.Map;

import org.rta.core.enums.UserType;
import org.rta.core.exception.NotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.docs.AttachmentDetailsModel;


public interface AttachmentDetailsService {

    public List<AttachmentDetailsModel> getAllAttachments(String chessisNumber, Long vehicleRcId, UserType userType);

    public SaveUpdateResponse saveOrUpdate(AttachmentDetailsModel model, String userName, UserType userType);
    
    public SaveUpdateResponse saveCompleteAttachments(Long vehicleRcId, String userName, Integer currentStep,
            UserType userType);

    /**
     * get multiple documents URL
     * 
     * @param attachmentDetailsModels
     * @param userName
     * @return
     */
    public SaveUpdateResponse multipleSaveOrUpdate(List<AttachmentDetailsModel> attachmentDetailsModels,
            String userName, UserType userType);
    
    public Boolean isValidUpload(Long vehicleRcId, UserType userType, boolean isOldVehicle);
    public SaveUpdateResponse saveUploadConsent(Long vehicleRcId);


    public Boolean getIsAttatchDealerAndOwnerSignature(Long vehicleRcId);

    public Map<String, String> getDealerAndOwnerSignature(Long vehicleRcId);

    /**
     * To Get all user documents uploaded by citizen
     * 
     * @param userName
     * @return
     */
    public List<AttachmentDetailsModel> getAllUserAttachmentDetails(String userName) throws NotFoundException;

    /**
     * To Get a particular user document details
     * 
     * @param userName
     * @param docId
     * @return
     */
    public AttachmentDetailsModel getUserAttachmentDetails(String userName, Integer docId) throws NotFoundException;

    /**
     * single save and update documents URL for user attachments details
     * 
     * @param model
     * @param userName
     * @param userType
     * @return
     */
    public SaveUpdateResponse saveOrUpdateforUser(AttachmentDetailsModel model, String userName, UserType userType);

   
    /**
     * multiple save and update documents URL for user attachments details
     * 
     * @param models
     * @param userName
     * @param userType
     * @return
     */
    public SaveUpdateResponse multipleSaveOrUpdateForUser(List<AttachmentDetailsModel> models, String userName, UserType userType);
    
    /**
     * To Get all Alteration of Vehicle documents uploaded by Body Builder
     * 
     * @param vehicleRcId
     * @return
     */
    public List<AttachmentDetailsModel> getAllVehicleAlterationAttachmentDetails(String chassisNumber, Long vehicleRcId) throws NotFoundException;

    /**
     * To Get a particular Alteration of Vehicle document details
     * 
     * @param vehcileRcId
     * @param docId
     * @return
     */
    public AttachmentDetailsModel getVehicleAlterationAttachmentDetails(Long vehcileRcId, Integer docId) throws NotFoundException;

    /**
     * single save and update documents URL for Alteration of Vehicle attachments details
     * 
     * @param model
     * @param userName
     * @param userType
     * @return
     */
    public SaveUpdateResponse saveOrUpdateforVehicleAlteration(AttachmentDetailsModel model, String userName, UserType userType);

   
    /**
     * multiple save and update documents URL for Alteration of Vehicle attachments details
     * 
     * @param models
     * @param userName
     * @param userType
     * @return
     */
    public SaveUpdateResponse multipleSaveOrUpdateForVehicleAlteration(List<AttachmentDetailsModel> models, String userName, UserType userType);
    
    //########## using for license Module ########## 
    
    public SaveUpdateResponse multipleSaveOrUpdateForLicenceAttachments(List<AttachmentDetailsModel> models, String userName, String aadhaarNo);
    
}
