package org.rta.core.service.docs.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.docs.AttachmentDetailsDAO;
import org.rta.core.dao.docs.AttachmentDetailsHistoryDAO;
import org.rta.core.dao.docs.LicenceAttachmentDetailsDAO;
import org.rta.core.dao.docs.UserAttachmentDetailsDAO;
import org.rta.core.dao.docs.VehicleAlterationAttachmentDAO;
import org.rta.core.dao.license.LicenseHolderDtlsDAO;
import org.rta.core.dao.master.DocTypesDAO;
import org.rta.core.dao.secondvehicle.SecondVehicleRejectionDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.dao.vehicle.VehicleAlterationDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.entity.docs.LicenceAttachmentDetailsEntity;
import org.rta.core.entity.docs.UserAttachmentDetailsEntity;
import org.rta.core.entity.docs.VehicleAlterationAttachmentEntity;
import org.rta.core.entity.licence.LicenseHolderDtlsEntity;
import org.rta.core.entity.master.DocTypesEntity;
import org.rta.core.entity.secondvehicle.SecondVehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleAlterationEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.AttachmentFrom;
import org.rta.core.enums.DocTypes;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.exception.NotFoundException;
import org.rta.core.helper.docs.AttachmentDetailsHelper;
import org.rta.core.helper.docs.UserAttachmentDetailsHelper;
import org.rta.core.helper.docs.VehicleAlterationAttachmentHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.docs.AttachmentDetailsModel;
import org.rta.core.service.docs.AttachmentDetailsService;
import org.rta.core.service.docs.DocPermissionService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

@Service("attachmentDetailsService")
public class AttachmentDetailsServiceImpl implements AttachmentDetailsService {

    private static final Logger log = Logger.getLogger(AttachmentDetailsServiceImpl.class);
    
    @Autowired
    private AttachmentDetailsDAO attachmentDetailsDAO;

    @Autowired
    private AttachmentDetailsHistoryDAO attachmentDetailsHistoryDAO;
    
    @Autowired
    private UserAttachmentDetailsDAO userAttachmentDetailsDAO;

    @Autowired
    private VehicleAlterationAttachmentDAO vehicleAlterationAttachmentDAO;

    @Autowired
    private DocPermissionService docPermissionService;

    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private VehicleAlterationDAO vehicleAlterationDAO;

    @Autowired
    private AttachmentDetailsHelper attachmentDetailsHelper;

    @Autowired
    private UserAttachmentDetailsHelper userAttachmentDetailsHelper;

    @Autowired
    private VehicleAlterationAttachmentHelper vehicleAlterationAttachmentHelper;
    
	@Autowired
	private LicenseHolderDtlsDAO licenseHolderDtlsDAO;

	@Autowired
	private LicenceAttachmentDetailsDAO licenceAttachmentDetailsDAO;
	
	@Autowired
	private SecondVehicleRejectionDAO secondVehicleRejectionDAO;
	
	@Autowired
	private DocTypesDAO docTypesDAO;
	
    @Transactional
    public List<AttachmentDetailsModel> getAllAttachments(String chassisNumber, Long vehicleRcId, UserType userType) {
        VehicleRCEntity vehicleRcEntity = vehicleDAO.getChassisNoByVehicleRc(chassisNumber);

        if (ObjectsUtil.isNull(vehicleRcEntity) || ObjectsUtil.isNull(vehicleRcId)) {
            throw new IllegalArgumentException("Invalid chessisNumber or vehicleRcId");
        }
        if (!(vehicleRcEntity.getVehicleRcId().equals(vehicleRcId))) {
            throw new IllegalArgumentException("Invalid vehicleRcId !");
        }
        List<AttachmentDetailsEntity> attachmentDetailsEntities =
                attachmentDetailsDAO.getAllAttachments(vehicleDAO.getChassisNoByVehicleRc(chassisNumber), userType);
        if(UserType.ROLE_DEALER == userType){
        	boolean isSecondVehicleNOC = false;
        	for(AttachmentDetailsEntity entity : attachmentDetailsEntities){
        		if((entity.getDocTypes().getDocTypeId() == DocTypes.DOC_SECOND_VEHICLE_NOC.getValue())){
                	isSecondVehicleNOC = true;
                }
        	}
        	if(!isSecondVehicleNOC){
        		SecondVehicleDetailsEntity secondVehicleDetailsEntity = secondVehicleRejectionDAO.getSecondVehicleDetailsByVehicleRcId(vehicleRcId);
            	if(!ObjectsUtil.isNull(secondVehicleDetailsEntity) && (!ObjectsUtil.isNull(secondVehicleDetailsEntity.getIsValidSecondVehicle())) 
            			&& (!secondVehicleDetailsEntity.getIsValidSecondVehicle())){
            		AttachmentDetailsEntity entity = new AttachmentDetailsEntity();
            		entity.setDocTypes(docTypesDAO.getEntity(DocTypesEntity.class, DocTypes.DOC_SECOND_VEHICLE_NOC.getValue()));
            		entity.setStatus(Status.REJECTED.getValue());
            	    entity.setAttachmentFrom(AttachmentFrom.MOBILE.getValue()); 
            	    entity.setUserRole(UserType.ROLE_DEALER.getValue());
            	    entity.setVehicle(vehicleRcEntity);
            	    attachmentDetailsEntities.add(entity);
            	}	
        	}
        	
        }
        return (List<AttachmentDetailsModel>) attachmentDetailsHelper.convertToModelList(attachmentDetailsEntities);
    }

    @Transactional
    public SaveUpdateResponse saveOrUpdate(AttachmentDetailsModel model, String userName, UserType userType)
            throws DataIntegrityViolationException, InvalidDataAccessApiUsageException {

        VehicleRCEntity vehicleRcEntity = vehicleDAO.getChassisNoByVehicleRc(model.getChassisNumber());
        Long vehicleRcId = Long.valueOf(model.getVehicleRcId());
        if (ObjectsUtil.isNull(vehicleRcEntity) || ObjectsUtil.isNull(vehicleRcId)) {
            throw new IllegalArgumentException("Invalid chessisNumber or vehicleRcId");
        }
        if (!(vehicleRcEntity.getVehicleRcId().equals(vehicleRcId))) {
            throw new IllegalArgumentException("Invalid vehicleRcId !");
        }
        Long attachmentId = attachmentDetailsDAO.getAttatchDltIdByDocId(vehicleRcId, model.getId());

        SaveUpdateResponse response = null;
        String message = "";
        Long timeStamp = DateUtil.toCurrentUTCTimeStamp();
        AttachmentDetailsEntity attachEntity = null;
        if (attachmentId != null) {
                // update Attachment Documents
                message = "Update Successfully";
                 attachEntity = attachmentDetailsDAO.getEntity(AttachmentDetailsEntity.class,attachmentId);
			if ((DocTypes.DOC_FORM20DO.getValue() == attachEntity.getDocTypes().getDocTypeId())
                    || (DocTypes.DOC_FORM_21.getValue() == attachEntity.getDocTypes().getDocTypeId())
                            && userType == UserType.ROLE_DEALER) {
				if ((attachEntity.getStatus() == Status.PENDING.getValue())
						|| (attachEntity.getStatus() == Status.APPROVED.getValue())) {
					attachEntity.setStatus(attachEntity.getStatus());
				} else {
					attachEntity.setStatus(Status.UPLOADED.getValue());
				}
			} else {
                if (userType == UserType.ROLE_DEALER)
                    attachEntity.setStatus(Status.UPLOADED.getValue());
                else
                    attachEntity.setStatus(Status.PENDING.getValue());
			}
            attachEntity.setAttachmentFrom(model.getAttachmentFrom().getValue());
            attachEntity.setSource(model.getSource());
            attachEntity.setDocTypes(new DocTypesEntity(model.getId()));
            attachEntity.setAttachmentTitle(model.getAttachmentTitle());
            attachEntity.setFileName(model.getFileName());
            attachEntity.setModifiedBy(userName);
            attachEntity.setModifiedOn(timeStamp);
            attachEntity.setUserRole(userType.getValue());

        } else {

                // save Attachment Documents
        	attachEntity = attachmentDetailsHelper.convertToEntity(model);
            if (userType == UserType.ROLE_DEALER)
                attachEntity.setStatus(Status.UPLOADED.getValue());
            else
                attachEntity.setStatus(Status.PENDING.getValue());
        	attachEntity.setVehicle(vehicleDAO.get(Long.valueOf(model.getVehicleRcId())));
            message = "Save Successfully";
            attachEntity.setCreatedBy(userName);
            attachEntity.setCreatedOn(timeStamp);
            attachEntity.setModifiedBy(userName);
            attachEntity.setModifiedOn(timeStamp);
            attachEntity.setUserRole(userType.getValue());
        }        
        attachmentDetailsDAO.saveOrUpdate(attachEntity);
        response = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, message, model.getVehicleRcId());

        return response;
    }
    @Transactional
    public SaveUpdateResponse multipleSaveOrUpdate(List<AttachmentDetailsModel> models, String userName, UserType userType)
            throws DataIntegrityViolationException, InvalidDataAccessApiUsageException {
        
        VehicleRCEntity vehicleRcEntity = vehicleDAO.getChassisNoByVehicleRc(models.get(0).getChassisNumber());
        Long vehicleRcId = Long.valueOf(models.get(0).getVehicleRcId());
        if (ObjectsUtil.isNull(vehicleRcEntity) || ObjectsUtil.isNull(vehicleRcId)) {
            throw new IllegalArgumentException("Invalid chessisNumber or vehicleRcId");
        }
        if (!(vehicleRcEntity.getVehicleRcId().equals(vehicleRcId))) {
            throw new IllegalArgumentException("Invalid vehicleRcId !");
        }
        SaveUpdateResponse response = null;
        String message = "";
        AttachmentDetailsEntity attachEntity = null;
        for (AttachmentDetailsModel model : models) {
            Long attachmentId = attachmentDetailsDAO.getAttatchDltIdByDocId(vehicleRcId, model.getId());

            Long timeStamp = DateUtil.toCurrentUTCTimeStamp();
            if (attachmentId != null) {
                    // update Attachment Documents
                message = "Update Successfully";
                attachEntity = attachmentDetailsDAO.getEntity(AttachmentDetailsEntity.class,attachmentId);
                attachEntity.setAttachmentFrom(model.getAttachmentFrom().getValue());
                attachEntity.setSource(model.getSource());
                attachEntity.setDocTypes(new DocTypesEntity(model.getId()));
                attachEntity.setAttachmentTitle(model.getAttachmentTitle());
                attachEntity.setFileName(model.getFileName());
                attachEntity.setModifiedBy(userName);
                attachEntity.setModifiedOn(timeStamp);
                attachEntity.setUserRole(userType.getValue());
                if (userType == UserType.ROLE_CITIZEN){
	                try{
	                	String result = attachmentDetailsHistoryDAO.saveData(attachmentId, userName, DateUtil.toCurrentUTCTimeStamp(), models.get(0).getServiecType());
	                	log.info("One row inserted in history table for VehicleRcId : " + vehicleRcId+" :: status:::"+result);
	                }catch (Exception e) {}
                }   
            } else {
        // save Attachment Documents
            	attachEntity = attachmentDetailsHelper.convertToEntity(model);
            	attachEntity.setVehicle(vehicleDAO.get(Long.valueOf(model.getVehicleRcId())));
                attachEntity.setCreatedBy(userName);
                attachEntity.setCreatedOn(timeStamp);
                attachEntity.setModifiedBy(userName);
                attachEntity.setModifiedOn(timeStamp);
                attachEntity.setUserRole(userType.getValue());
            }
            if (userType == UserType.ROLE_DEALER){
                attachEntity.setStatus(Status.UPLOADED.getValue());
            } else if (userType == UserType.ROLE_CITIZEN){
                attachEntity.setStatus(Status.APPROVED.getValue());
            } else {
                attachEntity.setStatus(Status.PENDING.getValue());
            }
            attachmentDetailsDAO.saveOrUpdate(attachEntity);
        }
        
        response = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, message, models.get(0).getVehicleRcId());
        return response;
    }

    @Transactional
    public SaveUpdateResponse saveCompleteAttachments(Long vehicleRcId, String userName, Integer currentStep,
            UserType userType)
            throws DataIntegrityViolationException, InvalidDataAccessApiUsageException {


        Boolean isNextStep =
                attachmentDetailsDAO.getDocTypesIds(vehicleRcId, userType)
                        .containsAll(docPermissionService.getMandatoryDocTypesId(vehicleRcId, userType));
        VehicleRCEntity vehicRcEntity =
                vehicleDAO.getEntity(VehicleRCEntity.class, vehicleRcId);
        

        if (vehicRcEntity.getTrStatus() == Status.PENDING.getValue() && userType == UserType.ROLE_DEALER) {
            if (isNextStep) {
                vehicRcEntity.setVehicleRcId(vehicleRcId);
                if (currentStep > vehicRcEntity.getCurrentStep()) {
                    vehicRcEntity.setCurrentStep(currentStep);
                }
                vehicRcEntity.setModifiedBy(userName);
                vehicRcEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
                vehicleDAO.update(vehicRcEntity);
            }
            else {
                log.error("Some documents are missing.... for VehicleRcId : " + vehicleRcId);
                throw new IllegalArgumentException("Some documents are missing, Please upload first !");
            }
        } else if (!isNextStep && userType == UserType.ROLE_ONLINE_FINANCER) {
            log.info("Some Attachements are missing.. for VehicleRcId : " + vehicleRcId);
            throw new IllegalArgumentException("Some Attachements are missing, Please upload first !");
        }
        else if (vehicRcEntity.getPrStatus() == Status.REJECTED.getValue()
                && getRejectedDocsStatus(vehicleRcId, userType)) {
            log.info("Some Rejected Attachements are missing.. for VehicleRcId : " + vehicleRcId);
            throw new IllegalArgumentException("Some Rejected Attachements are missing, Please upload first !");
        }
        return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Save Successfully.", String.valueOf(vehicleRcId),
                vehicRcEntity.getCurrentStep());
    }

    @Override
    @Transactional
    public Boolean isValidUpload(Long vehicleRcId, UserType userType, boolean isOldVehicle) {

        VehicleRCEntity vehicleRcEntity = vehicleDAO.get(vehicleRcId);
        if (ObjectsUtil.isNull(vehicleRcEntity) || StringsUtil.isNullOrEmpty(userType.getLabel())) {
            log.info("Invalid doc upload for VehicleRcId : " + vehicleRcId);
            throw new IllegalArgumentException("Invalid VehicleRcId or user role!");
        }
        
        if ( isOldVehicle && userType == UserType.ROLE_BODY_BUILDER) {
        	List<VehicleAlterationEntity> vehicleAlterationEntity = vehicleAlterationDAO.getVehicleAlterationDetails(vehicleRcId, Status.FRESH);
        	if(!ObjectsUtil.isNull(vehicleAlterationEntity)) {
        		log.info("doc upload (application pr is generated) for VehicleRcId : " + vehicleRcId);
        		return true;
        	}
            return false;
        }else  if (vehicleRcEntity.getPrStatus() == Status.APPROVED.getValue() && userType == UserType.ROLE_PUC) {
            log.info("doc upload (application pr is generated) for VehicleRcId : " + vehicleRcId);
            return true;
        } else if (vehicleRcEntity.getTrStatus() == Status.APPROVED.getValue()) {

            if (vehicleRcEntity.getPrStatus() == Status.REJECTED.getValue()
                    && userType == UserType.ROLE_DEALER) {
                log.info("doc upload (application tr is generated and pr rejected) for VehicleRcId : " + vehicleRcId);
                return true;
            } else if (vehicleRcEntity.getPrStatus() != Status.APPROVED.getValue()
                    && userType == UserType.ROLE_BODY_BUILDER && vehicleRcEntity.getProcessStatus() == Status.BODY_BUILDER.getValue()) {
                log.info("doc upload (application tr is generated and pr not approved) for VehicleRcId : " + vehicleRcId);
                return true;
            } else if (userType == UserType.ROLE_ONLINE_FINANCER) {
                log.info("doc upload (application tr is generated ) for VehicleRcId : " + vehicleRcId);
                return true;
            } else {
                return false;
            }
        }
        return ((userType == UserType.ROLE_DEALER) ? true : false);
    }

    @Override
    @Transactional
    public SaveUpdateResponse saveUploadConsent(Long vehicleRcId) {
        VehicleRCEntity vehicleRcEntity = vehicleDAO.get(vehicleRcId);
        if(ObjectsUtil.isNull(vehicleRcEntity)){
            log.info("Invalid VehicleRcId : " + vehicleRcId);
            throw new IllegalArgumentException("Invalid VehicleRcId !");
        }
        vehicleRcEntity.setDocUploadConsent(true);
        vehicleDAO.update(vehicleRcEntity);
        SaveUpdateResponse response = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Updated document consent successfully.", vehicleRcId.toString());
        return response;
    }

    @Transactional
    @Override
    public Boolean getIsAttatchDealerAndOwnerSignature(Long vehicleRcId) {
        Boolean isDealerSign = attachmentDetailsDAO.getIsAttatchDltIdByDocId(vehicleRcId, DocTypes.DOC_DEALER_SIGNATURE.getValue());
        Boolean isOwnerSing =
                attachmentDetailsDAO.getIsAttatchDltIdByDocId(vehicleRcId, DocTypes.DOC_USER_SIGNATURE.getValue());
        return isDealerSign && isOwnerSing;
    }

    @Transactional
    @Override
    public Map<String, String> getDealerAndOwnerSignature(Long vehicleRcId) {
        List<Integer> docTypesIds = new ArrayList<Integer>();
        docTypesIds.add(DocTypes.DOC_USER_SIGNATURE.getValue());
        docTypesIds.add(DocTypes.DOC_DEALER_SIGNATURE.getValue());
        List<AttachmentDetailsEntity> attachDtlEntities =
                attachmentDetailsDAO.getDealerAndOwnerSign(vehicleRcId, docTypesIds);
        Map<String, String> signMap = new HashMap<String, String>();
        if (!ObjectsUtil.isNull(attachDtlEntities)) {
            for (AttachmentDetailsEntity attachmentDetailsEntity : attachDtlEntities) {
                if (attachmentDetailsEntity.getDocTypes().getDocTypeId() == DocTypes.DOC_USER_SIGNATURE.getValue()) {
                    signMap.put("ownerSignature", attachmentDetailsEntity.getSource());
                } else {
                    signMap.put("dealerSignature", attachmentDetailsEntity.getSource());
                }
            }
        }
        return signMap;
    }

    private Boolean getRejectedDocsStatus(Long vehicleRcId, UserType userType) {
        Boolean rejectionStatus = false;
        List<AttachmentDetailsEntity> attachDtlEntities =
                attachmentDetailsDAO.getAllAttachmentsForvehicleRCId(vehicleRcId, userType);
        for (AttachmentDetailsEntity entity : attachDtlEntities) {
            if (entity.getStatus() == Status.REJECTED.getValue()
                    && !((entity.getDocTypes().getDocTypeId() == DocTypes.MODEL_NAME.getValue())
                            || (entity.getDocTypes().getDocTypeId() == DocTypes.DOC_FORM_21.getValue())
                            || (entity.getDocTypes().getDocTypeId() == DocTypes.DOC_FORM20DO.getValue()))) {
                rejectionStatus = true;
                break;
            }
        }
        return rejectionStatus;

    }

    @Transactional
    @Override
    public List<AttachmentDetailsModel> getAllUserAttachmentDetails(String userName) throws NotFoundException {

        return (List<AttachmentDetailsModel>) userAttachmentDetailsHelper
                .convertToModelList(userAttachmentDetailsDAO.getAllAttachmentDetails(userName));
    }

    @Transactional
    @Override
    public AttachmentDetailsModel getUserAttachmentDetails(String userName, Integer docId) throws NotFoundException {

        return userAttachmentDetailsHelper.convertToModel(userAttachmentDetailsDAO.getAttachmentDetails(userName, docId));
    }


    @Transactional
    @Override
    public SaveUpdateResponse saveOrUpdateforUser(AttachmentDetailsModel model, String userName, UserType userType)
            throws DataIntegrityViolationException, InvalidDataAccessApiUsageException {

        UserAttachmentDetailsEntity entity = userAttachmentDetailsDAO.getAttachmentDetails(model.getUserName(), model.getId());

        String message = "";
        Long timeStamp = DateUtil.toCurrentUTCTimeStamp();
        if (entity != null) {
            // update Attachment Documents
            message = "Update Successfully";

            entity.setStatus(Status.PENDING.getValue());
            entity.setAttachmentFrom(model.getAttachmentFrom().getValue());
            entity.setSource(model.getSource());
            entity.setDocTypes(new DocTypesEntity(model.getId()));
            entity.setAttachmentTitle(model.getAttachmentTitle());
            entity.setFileName(model.getFileName());
            entity.setModifiedBy(userName);
            entity.setModifiedOn(timeStamp);
            entity.setUserRole(userType.getValue());
        } else {

            // save Attachment Documents
            message = "Save Successfully";
            entity = userAttachmentDetailsHelper.convertToEntity(model);
            entity.setStatus(Status.PENDING.getValue());
            entity.setUserId(userDAO.findByUserName(model.getUserName()));
            entity.setCreatedBy(userName);
            entity.setCreatedOn(timeStamp);
            entity.setModifiedBy(userName);
            entity.setModifiedOn(timeStamp);
            entity.setUserRole(userType.getValue());
        }
        userAttachmentDetailsDAO.saveOrUpdate(entity);

        return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, message, null);
    }

    @Transactional
    @Override
    public SaveUpdateResponse multipleSaveOrUpdateForUser(List<AttachmentDetailsModel> models, String userName,
            UserType userType) throws DataIntegrityViolationException, InvalidDataAccessApiUsageException {

        String message = "";
        for (AttachmentDetailsModel model : models) {
            
            UserAttachmentDetailsEntity entity = userAttachmentDetailsDAO.getAttachmentDetails(model.getUserName(), model.getId());
            Long timeStamp = DateUtil.toCurrentUTCTimeStamp();
            if (entity != null) {
                // update Attachment Documents
                message = "Update Successfully";
                entity.setStatus(Status.PENDING.getValue());
                entity.setAttachmentFrom(model.getAttachmentFrom().getValue());
                entity.setSource(model.getSource());
                entity.setDocTypes(new DocTypesEntity(model.getId()));
                entity.setAttachmentTitle(model.getAttachmentTitle());
                entity.setFileName(model.getFileName());
                entity.setModifiedBy(userName);
                entity.setModifiedOn(timeStamp);
                entity.setUserRole(userType.getValue());
            } else {

                // save Attachment Documents
                message = "Save Successfully";
                entity = userAttachmentDetailsHelper.convertToEntity(model);
                entity.setStatus(Status.PENDING.getValue());
                entity.setUserId(userDAO.findByUserName(model.getUserName()));
                entity.setCreatedBy(userName);
                entity.setCreatedOn(timeStamp);
                entity.setModifiedBy(userName);
                entity.setModifiedOn(timeStamp);
                entity.setUserRole(userType.getValue());
            }
            userAttachmentDetailsDAO.saveOrUpdate(entity);
        }
        return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, message, null);
    }

    @Transactional
    @Override
    public List<AttachmentDetailsModel> getAllVehicleAlterationAttachmentDetails(String ChassisNo,Long vehicleRcId) throws NotFoundException {

        VehicleRCEntity vehicleRcEntity = vehicleDAO.getChassisNoByVehicleRc(ChassisNo);
        if (!(vehicleRcEntity.getVehicleRcId().equals(vehicleRcId))) {
            throw new NotFoundException("Invalid Vehicle RC Id");
        }
        return (List<AttachmentDetailsModel>) vehicleAlterationAttachmentHelper
                .convertToModelList(vehicleAlterationAttachmentDAO.getAllVehicleAlterationAttachmentDetails(vehicleRcId));
    }

    @Transactional
    @Override
    public AttachmentDetailsModel getVehicleAlterationAttachmentDetails(Long vehcileRcId, Integer docId) throws NotFoundException {
        
        return vehicleAlterationAttachmentHelper.convertToModel(
                vehicleAlterationAttachmentDAO.getVehicleAlterationAttachmentDetails(vehcileRcId, docId));
    }

    @Transactional
    @Override
    public SaveUpdateResponse saveOrUpdateforVehicleAlteration(AttachmentDetailsModel model, String userName,
            UserType userType) throws DataIntegrityViolationException, InvalidDataAccessApiUsageException {

        VehicleRCEntity vehicleRcEntity = vehicleDAO.getChassisNoByVehicleRc(model.getChassisNumber());
        Long vehicleRcId = Long.valueOf(model.getVehicleRcId());

        if (!(vehicleRcEntity.getVehicleRcId().equals(vehicleRcId))) {
            throw new InvalidDataAccessApiUsageException("Invalid Vehicle RC Id");
        }

        VehicleAlterationAttachmentEntity entity = vehicleAlterationAttachmentDAO
                .getVehicleAlterationAttachmentDetails(vehicleRcId, model.getId());

        String message = "";
        Long timeStamp = DateUtil.toCurrentUTCTimeStamp();
        if (entity != null) {
            // update Attachment Documents
            message = "Update Successfully";

            entity.setStatus(Status.PENDING.getValue());
            entity.setAttachmentFrom(model.getAttachmentFrom().getValue());
            entity.setSource(model.getSource());
            entity.setDocTypes(new DocTypesEntity(model.getId()));
            entity.setAttachmentTitle(model.getAttachmentTitle());
            entity.setFileName(model.getFileName());
            entity.setModifiedBy(userName);
            entity.setModifiedOn(timeStamp);
            entity.setUserRole(userType.getValue());
        } else {

            // save Attachment Documents
            message = "Save Successfully";
            entity = vehicleAlterationAttachmentHelper.convertToEntity(model);
            entity.setStatus(Status.PENDING.getValue());
            entity.setCreatedBy(userName);
            entity.setCreatedOn(timeStamp);
            entity.setModifiedBy(userName);
            entity.setModifiedOn(timeStamp);
            entity.setUserRole(userType.getValue());
        }
        vehicleAlterationAttachmentDAO.saveOrUpdate(entity);

        return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, message, model.getVehicleRcId());
    }

    @Transactional
    @Override
    public SaveUpdateResponse multipleSaveOrUpdateForVehicleAlteration(List<AttachmentDetailsModel> models,
            String userName, UserType userType) throws DataIntegrityViolationException, InvalidDataAccessApiUsageException {

        VehicleRCEntity vehicleRcEntity = vehicleDAO.getChassisNoByVehicleRc(models.get(0).getChassisNumber());
        Long vehicleRcId = Long.valueOf(models.get(0).getVehicleRcId());

        if (!(vehicleRcEntity.getVehicleRcId().equals(vehicleRcId))) {
            throw new InvalidDataAccessApiUsageException("Invalid Vehicle RC Id");
        }

        String message = "";
        for (AttachmentDetailsModel model : models) {

            VehicleAlterationAttachmentEntity entity =
                    vehicleAlterationAttachmentDAO.getVehicleAlterationAttachmentDetails(vehicleRcId, model.getId());
            Long timeStamp = DateUtil.toCurrentUTCTimeStamp();
            if (entity != null) {
                // update Attachment Documents
                message = "Update Successfully";
                entity.setStatus(Status.PENDING.getValue());
                entity.setAttachmentFrom(model.getAttachmentFrom().getValue());
                entity.setSource(model.getSource());
                entity.setDocTypes(new DocTypesEntity(model.getId()));
                entity.setAttachmentTitle(model.getAttachmentTitle());
                entity.setFileName(model.getFileName());
                entity.setModifiedBy(userName);
                entity.setModifiedOn(timeStamp);
                entity.setUserRole(userType.getValue());
            } else {

                // save Attachment Documents
                message = "Save Successfully";
                entity = vehicleAlterationAttachmentHelper.convertToEntity(model);
                entity.setStatus(Status.PENDING.getValue());
                entity.setCreatedBy(userName);
                entity.setCreatedOn(timeStamp);
                entity.setModifiedBy(userName);
                entity.setModifiedOn(timeStamp);
                entity.setUserRole(userType.getValue());
            }
            vehicleAlterationAttachmentDAO.saveOrUpdate(entity);
        }
        return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, message, models.get(0).getVehicleRcId());
    }

	@Override
	@Transactional
	public SaveUpdateResponse multipleSaveOrUpdateForLicenceAttachments(List<AttachmentDetailsModel> models,
			String userName, String aadhaarNo) {

        LicenseHolderDtlsEntity licenseHolderDtlsEntity = licenseHolderDtlsDAO.getLicenseHolderDtls(aadhaarNo);
                if (ObjectsUtil.isNull(licenseHolderDtlsEntity)) {
            throw new InvalidDataAccessApiUsageException("Invalid Aadhaar number");
        }
        String message = "";
        for (AttachmentDetailsModel model : models) {
            LicenceAttachmentDetailsEntity entity =
            		licenceAttachmentDetailsDAO.getAttachmentDetails(licenseHolderDtlsEntity.getLicenceHolderId(), model.getId());
            if (entity != null) {
                // update Attachment Documents
                message = "Update Successfully";
                entity.setStatus(Status.APPROVED.getValue());
                entity.setAttachmentFrom(model.getAttachmentFrom().getValue());
                entity.setSource(model.getSource());
                entity.setDocTypes(new DocTypesEntity(model.getId()));
                entity.setAttachmentTitle(model.getAttachmentTitle());
                entity.setFileName(model.getFileName());
                
            } else {

                // save Attachment Documents
                message = "Save Successfully";
                entity = new LicenceAttachmentDetailsEntity();
                entity.setDocTypes(new DocTypesEntity(model.getId()));
                entity.setAttachmentTitle(model.getAttachmentTitle());
                entity.setFileName(model.getFileName());
                entity.setSource(model.getSource());
                entity.setAttachmentFrom(model.getAttachmentFrom().getValue());
                entity.setLicenseHolderId(licenseHolderDtlsEntity);
                entity.setStatus(Status.APPROVED.getValue());
                entity.setCreatedBy(userName);
                entity.setCreatedOn(new Date());
               
            }
            entity.setModifiedBy(userName);
            entity.setModifiedOn(new Date());
            licenceAttachmentDetailsDAO.saveOrUpdate(entity);
        }
        return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, message, aadhaarNo);
	}

}
