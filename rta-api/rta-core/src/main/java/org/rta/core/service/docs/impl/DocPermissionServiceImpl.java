package org.rta.core.service.docs.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.docs.AttachmentDetailsDAO;
import org.rta.core.dao.secondvehicle.SecondVehicleRejectionDAO;
import org.rta.core.dao.vehicle.VehicleAlterationDAO;
import org.rta.core.dao.vehicle.VehicleBillingDetailsDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.dao.vehicle.VehicleRCHistoryDAO;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.application.VehicleRCHistoryEntity;
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.entity.secondvehicle.SecondVehicleDetailsEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.VehicleAlterationEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.AlterationCategory;
import org.rta.core.enums.DocTypes;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.model.docs.DocPermissionModel;
import org.rta.core.model.finance.FinanceModel;
import org.rta.core.model.master.DocTypesModel;
import org.rta.core.model.user.UserModel;
import org.rta.core.service.docs.DocPermissionService;
import org.rta.core.service.finance.FinanceDtlsService;
import org.rta.core.service.rules.RuleEngineService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *	@Author sohan.maurya created on Aug 25, 2016.
 */
@Service("docPermissionService")
public class DocPermissionServiceImpl implements DocPermissionService {

	private static final Logger logger = Logger.getLogger(DocPermissionServiceImpl.class);
	
    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private VehicleDetailsDAO vehicleDetailsDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private VehicleBillingDetailsDAO vehicleBillingDetailsDAO;

    @Autowired
    private AttachmentDetailsDAO attachmentDetailsDAO;

    @Autowired
    private VehicleRCHistoryDAO vehicleRCHistoryDAO;

    @Autowired
    private FinanceDtlsService financeDtlsService;

    @Autowired
    private RuleEngineService ruleEngineService;
    
    @Autowired
    private VehicleAlterationDAO vehicleAlterationDAO;
    
    @Autowired
    private SecondVehicleRejectionDAO secondVehicleRejectionDAO;
    
    @Override
    @Transactional
    public List<DocTypesModel> getDocTypes(Long vehicleRcId, UserModel userModel, boolean isOldVehicle)
            throws HibernateException {
        List<DocTypesModel> docTypesModels = new ArrayList<DocTypesModel>();
        DocPermissionModel docPermissionModel = new DocPermissionModel();
        
        VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);
        if ( isOldVehicle  && userModel.getUserType() == UserType.ROLE_BODY_BUILDER) {
        	
        	List<VehicleAlterationEntity> vehicleAlterationEntities = vehicleAlterationDAO.getVehicleAlterationDetails(vehicleRcId, Status.FRESH);
        	docPermissionModel.setUserType(UserType.ROLE_BODY_BUILDER);
        	docPermissionModel.setIsOldVehicle(isOldVehicle);
        	docPermissionModel.setVehicleSubClass(vehicleAlterationEntities.get(0).getVehicleSubClass());
        	List<AlterationCategory> list = new ArrayList<AlterationCategory>();
        	for( VehicleAlterationEntity vehicleAlterationEntity : vehicleAlterationEntities){
        		list.add(AlterationCategory.getAlterationCategory(vehicleAlterationEntity.getAlterationCategory()));
        	}
        	docPermissionModel.setAlterationCategory(list);
            docTypesModels = ruleEngineService.getAttachments(docPermissionModel);
        }
        else if (vehicleRCEntity.getTrStatus() == Status.PENDING.getValue()
                && userModel.getUserType() == UserType.ROLE_DEALER) {
        	
        	docPermissionModel.setUserType(UserType.ROLE_DEALER);
        	docPermissionModel = getDocPermissionModel(docPermissionModel, vehicleRcId);
        	docTypesModels = ruleEngineService.getAttachments(docPermissionModel);
            
        } else if (vehicleRCEntity.getTrStatus() == Status.APPROVED.getValue()
                && vehicleRCEntity.getPrStatus() != Status.REJECTED.getValue()
                && userModel.getUserType() == UserType.ROLE_BODY_BUILDER) {

        	docPermissionModel.setUserType(UserType.ROLE_BODY_BUILDER);
            docTypesModels = ruleEngineService.getAttachments(docPermissionModel);

        } else if (vehicleRCEntity.getPrStatus() == Status.APPROVED.getValue()
                && userModel.getUserType() == UserType.ROLE_PUC) {

        	docPermissionModel.setUserType(UserType.ROLE_PUC);
            docTypesModels = ruleEngineService.getAttachments(docPermissionModel);
        } else if (vehicleRCEntity.getTrStatus() == Status.APPROVED.getValue()
                && userModel.getUserType() == UserType.ROLE_ONLINE_FINANCER) {

        	docPermissionModel.setUserType(UserType.ROLE_ONLINE_FINANCER);
            docTypesModels = ruleEngineService.getAttachments(docPermissionModel);
        } else {
            List<AttachmentDetailsEntity> entities = attachmentDetailsDAO.getAllAttachmentsForvehicleRCId(vehicleRcId, userModel.getUserType());
            DocTypesModel docTypesModel = null;
            boolean isSecondVehicleNOC = false;
            for (AttachmentDetailsEntity entity : entities) {
                docTypesModel = new DocTypesModel();
                if (!((entity.getDocTypes().getDocTypeId() == DocTypes.MODEL_NAME.getValue())
                        || (entity.getDocTypes().getDocTypeId() == DocTypes.DOC_FORM_21.getValue())
                        || (entity.getDocTypes().getDocTypeId() == DocTypes.DOC_FORM20DO.getValue()))) {
                    docTypesModel.setIsMandatory(true);
                    docTypesModel.setId(entity.getDocTypes().getDocTypeId());
                    docTypesModel.setName(entity.getDocTypes().getName());
                    docTypesModels.add(docTypesModel);
                }
                if((entity.getDocTypes().getDocTypeId() == DocTypes.DOC_SECOND_VEHICLE_NOC.getValue())){
                	isSecondVehicleNOC = true;
                }
            }
            if(UserType.ROLE_DEALER == userModel.getUserType() && (!isSecondVehicleNOC)){	
            	SecondVehicleDetailsEntity secondVehicleDetailsEntity = secondVehicleRejectionDAO.getSecondVehicleDetailsByVehicleRcId(vehicleRcId);
            	if(!ObjectsUtil.isNull(secondVehicleDetailsEntity) && (!ObjectsUtil.isNull(secondVehicleDetailsEntity.getIsValidSecondVehicle())) 
            			&& (!secondVehicleDetailsEntity.getIsValidSecondVehicle())){
            	         docTypesModel = new DocTypesModel();
            		 docTypesModel.setIsMandatory(true);
            		 docTypesModel.setId(DocTypes.DOC_SECOND_VEHICLE_NOC.getValue());
                     docTypesModel.setName(DocTypes.DOC_SECOND_VEHICLE_NOC.getLabel());
                     docTypesModels.add(docTypesModel);
            	}
            }
        }
        
        if (!isOldVehicle && userModel.getUserType() == UserType.ROLE_BODY_BUILDER) {
            try {
                VehicleRCHistoryEntity entity = new VehicleRCHistoryEntity();
                entity.setComment("BODY BUILDER HAVING THIS APPLICATION");
                entity.setCreatedBy(userModel.getUserName());
                entity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
                entity.setIteration(vehicleRCEntity.getIteration());
                entity.setModifiedBy(userModel.getUserName());
                entity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
                entity.setStatus(Status.OPEN.getValue());
                entity.setUserEntity(new UserEntity(userModel.getUserId()));
                entity.setVehicleRc(vehicleRCEntity);
                entity.setRtaEmployeeType(UserType.ROLE_BODY_BUILDER.getValue());
                vehicleRCHistoryDAO.save(entity);

                vehicleRCEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
                vehicleRCEntity.setModifiedBy(userModel.getUserName());
                vehicleRCEntity.setProcessStatus(Status.BODY_BUILDER.getValue());
                vehicleDAO.saveOrUpdate(vehicleRCEntity);
            } catch (Exception e) {
            }
        }
        return docTypesModels;
    }

    @Override
    @Transactional
    public List<Integer> getMandatoryDocTypesId(Long vehicleRcId, UserType userType) throws HibernateException {
        List<Integer> mandatoryDocTypesIds = new ArrayList<Integer>();
        List<DocTypesModel> docTypesModels = new ArrayList<>();
        DocPermissionModel docPermissionModel = new DocPermissionModel();

        if (userType == UserType.ROLE_DEALER) {
        	
        	docPermissionModel.setUserType(UserType.ROLE_DEALER);
        	docPermissionModel = getDocPermissionModel(docPermissionModel, vehicleRcId);
        	docTypesModels = ruleEngineService.getAttachments(docPermissionModel);
        } else if (userType == UserType.ROLE_BODY_BUILDER) {
        	
        	docPermissionModel.setUserType(UserType.ROLE_BODY_BUILDER);
        	docTypesModels = ruleEngineService.getAttachments(docPermissionModel);
        } else if (userType == UserType.ROLE_PUC) {
        	
        	docPermissionModel.setUserType(UserType.ROLE_PUC);
        	docTypesModels = ruleEngineService.getAttachments(docPermissionModel);
        } else if (userType == UserType.ROLE_ONLINE_FINANCER) {
        	
        	docPermissionModel.setUserType(UserType.ROLE_ONLINE_FINANCER);
        	docTypesModels = ruleEngineService.getAttachments(docPermissionModel);
        }
        docTypesModels.forEach(model->{
        	if(model.getIsMandatory()){
        		mandatoryDocTypesIds.add(model.getId());	
        	}
        });
        return mandatoryDocTypesIds;
    }

    private Map<String, Object> getIsVehicleFinanced(Long vehicleRcId) {

    	Map<String, Object> map = new HashMap<>();
    			
        FinanceModel financeModel = financeDtlsService.getFinanceDtlsByVehicleRcId(vehicleRcId,null);
        if(ObjectsUtil.isNull(financeModel)){
        	map.put("isFinance", Boolean.FALSE);
        	map.put("financeMode", null);
           return map; 
        }
        map.put("isFinance", financeModel.getIsFinance());
    	map.put("financeMode", financeModel.getFinancerMode());
        return map;
    }

    @Transactional
    @Override
    public List<DocTypesModel> getFormDocs(Long vehicleRcId) {
        List<DocTypesModel> docTypesModels = new ArrayList<DocTypesModel>();
        DocTypesModel docTypesModel = null;
        Map<String, Object> mapFinance = getIsVehicleFinanced(vehicleRcId);
        Boolean isFinance = (Boolean) mapFinance.get("isFinance");
        if (isFinance) {
            docTypesModel = new DocTypesModel();
            docTypesModel.setIsMandatory(true);
            docTypesModel.setId(DocTypes.DOC_FORM20DO.getValue());
            docTypesModel.setName(DocTypes.DOC_FORM20DO.getLabel());
            docTypesModels.add(docTypesModel);
        }
        docTypesModel = new DocTypesModel();
        docTypesModel.setIsMandatory(true);
        docTypesModel.setId(DocTypes.DOC_FORM_21.getValue());
        docTypesModel.setName(DocTypes.DOC_FORM_21.getLabel());
        docTypesModels.add(docTypesModel);

        return docTypesModels;
    }

    private DocPermissionModel getDocPermissionModel( DocPermissionModel model, Long vehicleRcId){
    	
    	 VehicleDetailsEntity vehicleDetailsEntity = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
         VehicleRCEntity vehicleRCEntity = vehicleDetailsEntity.getVehicleRcId();
         Map<String, Object> mapFinance = getIsVehicleFinanced(vehicleRcId);
         Boolean isFinance = (Boolean) mapFinance.get("isFinance");
         Integer financeMode = (Integer) mapFinance.get("financeMode");
    	 model.setOwnershipType(OwnershipType.getOwnershipType(vehicleRCEntity.getOwnershipType()));
    	 model.setRegistrationCategoryType(vehicleRCEntity.getRegCategory().getRegistrationCategoryId());
         model.setVehicleClass(vehicleDetailsEntity.getVehicleClass());
         model.setInvoiceValue(vehicleBillingDetailsDAO.getByVehicleDetailId(vehicleDetailsEntity).getInvoiceValue());
         model.setIsFinance(isFinance);
         model.setVehicleSubClass(vehicleDetailsEntity.getVehicleSubClass());
         model.setFinanceMode(financeMode);
         if (model.getOwnershipType() == OwnershipType.INDIVIDUAL) {
             CustIndividualDetailsEntity custDetails = customerDAO.getByVehicleRcId(vehicleRcId);
             try {
            	 model.setCustomerAge(DateUtil.getCurrentAge(custDetails.getDateOfBirth()));
                 model.setIsCustomerDisabled(custDetails.getIsDisabled());
             } catch (Exception ex) {
            	 logger.error("Method getDocPermissionModel::: "+ex.getMessage());
            	 model.setCustomerAge(100);
                 model.setIsCustomerDisabled(false);
             }
         }else{
        	 model.setCustomerAge(100);
             model.setIsCustomerDisabled(false);
         }
         return model;
    }
}
