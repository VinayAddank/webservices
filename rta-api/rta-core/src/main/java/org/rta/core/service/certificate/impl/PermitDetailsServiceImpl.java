package org.rta.core.service.certificate.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.master.PermitConditionsDAO;
import org.rta.core.dao.master.PermitGoodsDAO;
import org.rta.core.dao.master.PermitRouteDAO;
import org.rta.core.dao.master.PermitTypeDAO;
import org.rta.core.dao.master.StateDAO;
import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.dao.payment.registfee.PermitFeeDAO;
import org.rta.core.dao.payment.tax.TaxDetailDAO;
import org.rta.core.dao.permit.PermitDetailsMappingDAO;
import org.rta.core.dao.permit.PermitHeaderDAO;
import org.rta.core.dao.user.RTAEmployeeDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.PermitConditionsEntity;
import org.rta.core.entity.master.PermitGoodsMasterEntity;
import org.rta.core.entity.master.PermitRouteConditionsMasterEntity;
import org.rta.core.entity.master.PermitTypeEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.entity.payment.registfee.PermitFeeEntity;
import org.rta.core.entity.payment.tax.TaxDetailEntity;
import org.rta.core.entity.permit.PermitDetailsMappingEntity;
import org.rta.core.entity.permit.PermitHeaderEntity;
import org.rta.core.entity.user.RTAEmployeeEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.FormCodeType;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.PermitClassType;
import org.rta.core.enums.PermitDetailsType;
import org.rta.core.enums.PermitType;
import org.rta.core.enums.SerialNumberType;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.exception.NotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.CodeNameModel;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.certificate.PermitDetailsModel;
import org.rta.core.model.permit.PermitHeaderModel;
import org.rta.core.model.permit.PermitNewRequestModel;
import org.rta.core.model.sync.ApplicationFormDataModel;
import org.rta.core.model.sync.SyncDataModel;
import org.rta.core.service.application.ApplicationService;
import org.rta.core.service.certificate.PermitAuthCardService;
import org.rta.core.service.certificate.PermitDetailsService;
import org.rta.core.service.master.serialnumber.SerialNumberService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *	@Author sohan.maurya created on Nov 10, 2016.
 */
@Service("permitDetailsService")
public class PermitDetailsServiceImpl implements PermitDetailsService {
	
	private static final Logger logger = Logger.getLogger(PermitDetailsServiceImpl.class);

    @Autowired
    private PermitTypeDAO permitTypeDAO;

    @Value("${rta.permit.validity}")
    private Integer permitValidity;

    @Value("${rta.pc.expire.month}")
    private String permitExpireMonth;

    @Value("${rta.pc.000000}")
    private String permit000000;

    @Value("${rta.pc.235959}")
    private String permit235959;

    @Value("${rta.permit.noofyear}")
    private Integer noOfYear;

    @Value("${rta.permit.education}")
    private String educationPermitCode;
    @Autowired
    private PermitHeaderDAO permitHeaderDAO;
    @Autowired
	private CustomerCorporateDAO custCorpDAO;
    @Autowired
	private CustomerDAO customerDAO;
    @Autowired 
    private PermitDetailsMappingDAO permitDetailsMappingDAO;
    @Autowired
    private PermitFeeDAO permitFeeDAO;
    
    @Autowired
    private PermitAuthCardService permitAuthCardService;
    @Autowired
    private VehicleDAO vehicleDAO;
    
    @Autowired
    private RtaOfficeDAO rtaOfficeDAO;
    
    @Autowired
    private PermitRouteDAO permitRouteDAO;
    
    @Autowired
    private PermitConditionsDAO permitConditionsDAO;
    
    @Autowired
    private PermitGoodsDAO permitGoodsDAO;
    
    @Autowired
    private VehicleDetailsDAO vehicleDetailsDAO;
    
    @Autowired
    private ApplicationService applicationService;
    
    @Autowired
    private RTAEmployeeDAO rTAEmployeeDAO;
    
    @Autowired
    private StateDAO stateDAO;
    
    @Autowired
    private DistrictDAO districtDAO;
    
    @Value("${attachments.downloaded.path}")
    private String attachmentDocPath;

    @Value("${rta.employee.signature.path}")
    private String signaturePath;

    @Value("${base.url}")
    private String baseURL;
    
    @Autowired
    private SerialNumberService serialNumberService;
    @Autowired
    private TaxDetailDAO taxDetailDAO;
    
    @Transactional
    @Override
    public SaveUpdateResponse saveOrUpdate(PermitDetailsModel permitDetailsModel, String userName)
            throws DataIntegrityViolationException, InvalidDataAccessApiUsageException {
        //TODO method is not in used
        
        /*String msg = "";
        PermitDetailsEntity entity = permitDetailsDAO.getPermitDetaits(permitDetailsModel.getVehicleRcId());
        if (ObjectsUtil.isNull(entity)) {
            msg = "Successfully Save";
            entity = new PermitDetailsEntity();
            entity.setVehicleRcId(new VehicleRCEntity(permitDetailsModel.getVehicleRcId(), null));
            entity.setStatus(Boolean.FALSE);
            entity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
            entity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
            entity.setCreatedBy(userName);
            entity.setModifiedBy(userName);
        }else {
            msg = "Successfully Update";
            entity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
            entity.setModifiedBy(userName);
            if (permitDetailsModel.getRouteAreaPermitCode() == null) {
                entity.setRouteAreaPermit(null);
            } else {
                entity.setRouteAreaPermit(
                        permitSubTypeDAO.getPermitSubTypeDetails(permitDetailsModel.getRouteAreaPermitCode()));
            }
            if (permitDetailsModel.getStateUnionPermitCode() == null) {
                entity.setStateUnionPermit(null);
            } else {
                entity.setStateUnionPermit(
                        permitSubTypeDAO.getPermitSubTypeDetails(permitDetailsModel.getStateUnionPermitCode()));
            }
            if (permitDetailsModel.getNatureGoodsCarriedCode() == null) {
                entity.setNatureGoodsCarried(null);
            } else {
                entity.setNatureGoodsCarried(
                        permitSubTypeDAO.getPermitSubTypeDetails(permitDetailsModel.getNatureGoodsCarriedCode()));
            }
            if (permitDetailsModel.getAnyOtherConditionsCode() == null) {
                entity.setAnyOtherConditions(null);
            } else {
                entity.setAnyOtherConditions(
                        permitSubTypeDAO.getPermitSubTypeDetails(permitDetailsModel.getAnyOtherConditionsCode()));
            }
            if (permitDetailsModel.getPurposeForVehicleCode() == null) {
                entity.setPurposeForVehicle(null);
            } else {
                entity.setPurposeForVehicle(
                        permitSubTypeDAO.getPermitSubTypeDetails(permitDetailsModel.getPurposeForVehicleCode()));
            }
            logger.info("::::::::::::::::::saveOrUpdate::::::::::::: Successfully Update");
        }
        entity.setPermitTypeId(permitTypeDAO.getPermitTyp(permitDetailsModel.getCode()));
        permitDetailsDAO.saveOrUpdate(entity);

        logger.info("::::::::::::::::::saveOrUpdate:::::::::::::== End ");
        return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, permitDetailsModel.getVehicleRcId().toString());*/
        return null;
    }

    @Transactional
    @Override
    public PermitDetailsModel getPermitDetails(Long vehicleRcId) {

        //TODO method not in used
        
        /*PermitDetailsEntity entity = permitDetailsDAO.getPermitDetaits(vehicleRcId);
        if(ObjectsUtil.isNull(entity)){
            throw new IllegalArgumentException("There is not permit details for vehicleRcId=" + vehicleRcId);
        }
        PermitDetailsModel model = new PermitDetailsModel();
        model.setCode(entity.getPermitTypeId().getCode());
        model.setVehicleRcId(entity.getVehicleRcId().getVehicleRcId());
        model.setName(entity.getPermitTypeId().getName());
        model.setPermitDetailsId(entity.getPermitDetailsId());
        model.setExpiryDate(entity.getExpiryDate());
        model.setIssueDate(entity.getIssueDate());
        PermitSubTypeEntity permitSubTypeEntity = entity.getAnyOtherConditions();
        if (!ObjectsUtil.isNull(permitSubTypeEntity)) {
            model.setAnyOtherConditionsCode(permitSubTypeEntity.getCode());
            model.setAnyOtherConditions(permitSubTypeEntity.getName());
        }
        PermitSubTypeEntity natureGoodsCarriedEntity = entity.getNatureGoodsCarried();
        if (!ObjectsUtil.isNull(natureGoodsCarriedEntity)) {
            model.setNatureGoodsCarried(natureGoodsCarriedEntity.getName());
            model.setNatureGoodsCarriedCode(natureGoodsCarriedEntity.getCode());
        }
        model.setPermitDetailsId(entity.getPermitDetailsId());
        RtaOfficeEntity rtaOffice = entity.getVehicleRcId().getRtaOfficeId();
        model.setPermitNumber(getPermitNumber(rtaOffice.getCode(), entity.getPermitDetailsId(), entity.getPermitTypeId().getCode(), entity.getIssueDate()));
        PermitSubTypeEntity purposeForVehicle = entity.getPurposeForVehicle();
        if (!ObjectsUtil.isNull(purposeForVehicle)) {
            model.setPurposeForVehicle(purposeForVehicle.getName());
            model.setPurposeForVehicleCode(purposeForVehicle.getCode());
        }
        PermitSubTypeEntity routeAreaPermit = entity.getPurposeForVehicle();
        if (!ObjectsUtil.isNull(routeAreaPermit)) {
            model.setRouteAreaPermit(routeAreaPermit.getName());
            model.setRouteAreaPermitCode(routeAreaPermit.getCode());
        }
        model.setRtaOfficeName(rtaOffice.getName());
        PermitSubTypeEntity stateUnionPermitEntity = entity.getStateUnionPermit();
        if (!ObjectsUtil.isNull(stateUnionPermitEntity)) {
            model.setStateUnionPermit(stateUnionPermitEntity.getName());
            model.setStateUnionPermitCode(stateUnionPermitEntity.getCode());
        }
        model.setStatus(entity.getStatus());
        // VehicleDetailsEntity entity1 = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
        // VehicleBaseModel vehicleDetails = new VehicleBaseModel();
        // vehicleDetails.setChassisNumber(entity1.getChassisNo());
        // vehicleDetails.setEngineNumber(entity1.getEngineNo());
        // vehicleDetails.setColor(entity1.getColor());
        // vehicleDetails.setFuelUsed(entity1.getFuelUsed());
        // vehicleDetails.setMakersName(entity1.getMakerName());
        // vehicleDetails.setMakersClass(entity1.getMakerClass());
        // vehicleDetails.setManufacturingMonthYear(entity1.getMfgDate());
        // vehicleDetails.setSeatingCapacity(entity1.getSeatingCapacity());
        // vehicleDetails.setRlw(entity1.getRlw());
        // vehicleDetails.setUlw(entity1.getUlw());
        // vehicleDetails.setBodyTypeDesc(entity1.getBodyTypeUpdated());
        // vehicleDetails.setVehicleClass(
        // vehicleClassDescDAO.getByVehiclClassDesCode(entity1.getVehicleSubClass()).getDescription());
        // model.setVehicleDetails(vehicleDetails);
        model.setVehicleRcId(vehicleRcId);
        logger.info("::::::::::::::::::getPermitDetails::::::::::::: End");
        return model;*/
        return null;
    }

    @Override
    @Transactional
    public PermitDetailsModel getPermitCertificate(Long vehicleRcId, Long mviUserId) {
        logger.info("::::::::::::::::::::::: getPermitCertificate :::::::::::: start ");
        PermitDetailsModel model = getPermitCertifcate(vehicleRcId, mviUserId);
        logger.info("::::::::::::::::::::::: getPermitCertificate :::::::::::: end ");
        return model;
    }

    @Override
    public PermitDetailsModel getPermitCertifcate(Long vehicleRcId, Long mviUserId){
        VehicleRCEntity vehicleRcEntity = vehicleDAO.get(vehicleRcId);
        if(ObjectsUtil.isNull(vehicleRcEntity)){
            logger.error("No pr found for vehicleRcId : " + vehicleRcId);
            return null;
        }
        String prNumber = vehicleRcEntity.getPrNumber();
        logger.info("get permit details by pr : " + prNumber);
        PermitDetailsModel model = new PermitDetailsModel();
        model.setIsTempPermit(false);
        PermitHeaderEntity entity = permitHeaderDAO.getPrimaryPermitActiveByPr(prNumber);
        if (ObjectsUtil.isNull(entity)) {
            logger.info("There is not any permit regarding pr : " + prNumber);
            return model;
        }
        model.setIssueDate(DateUtil.convertToRunningTimeStamp(entity.getIssueDate()));
        model.setValidFromDate(DateUtil.convertToRunningTimeStamp(entity.getValidFromDate()));
        model.setCode(entity.getPermitType());
        model.setPermitType(entity.getPermitType());
        model.setPermitSubType(entity.getPermitSubType());
        model.setPermitNumber(entity.getPermitNo());
        model.setExpiryDate(DateUtil.convertToRunningTimeStamp(entity.getValidToDate()));
        model.setPurposeForVehicle(entity.getPurpose());
        model.setCov(entity.getVehicleClass());
        RtaOfficeEntity rtaEntity = rtaOfficeDAO.getRtaOfficeById(entity.getRtaOfficeId());
        if(!ObjectsUtil.isNull(rtaEntity)){
            model.setRtaOfficeName(rtaEntity.getName());
        }
        model.setName(entity.getHolderNm());
        
        PermitDetailsMappingEntity routEntityMapping = permitDetailsMappingDAO.getActiveDetails(entity.getPermitNo(), PermitDetailsType.ROUTE.getLabel());
        if (!ObjectsUtil.isNull(routEntityMapping)) {
            PermitRouteConditionsMasterEntity routEntity = permitRouteDAO.getPermitRoute(routEntityMapping.getDetailValue());
            if (ObjectsUtil.isNotNull(routEntity)) {
            	model.setRouteAreaPermitCode(routEntity.getCode());
            	model.setRouteAreaPermit(routEntity.getPerRouteDesc());
            }
        }
        PermitDetailsMappingEntity stateEntityMapping = permitDetailsMappingDAO.getActiveDetails(entity.getPermitNo(), PermitDetailsType.STATE.getLabel());
        if (!ObjectsUtil.isNull(stateEntityMapping)) {
            StateEntity state = stateDAO.getStateByCode(stateEntityMapping.getDetailValue());
            if(ObjectsUtil.isNotNull(state)){
                model.setStateUnionPermitCode(state.getCode());
                model.setStateUnionPermit(state.getName());
            }
        }
        PermitDetailsMappingEntity goodsEntityMapping = permitDetailsMappingDAO.getActiveDetails(entity.getPermitNo(), PermitDetailsType.GOODS.getLabel());
        if (!ObjectsUtil.isNull(goodsEntityMapping)) {
            PermitGoodsMasterEntity goodsEntity = permitGoodsDAO.getPermitGood(goodsEntityMapping.getDetailValue());
            if (ObjectsUtil.isNotNull(goodsEntity)) {
            	model.setNatureGoodsCarriedCode(goodsEntity.getCode());
            	model.setNatureGoodsCarried(goodsEntity.getPerGoodsDesc());
            }
        }
        PermitDetailsMappingEntity conditionsEntityMapping = permitDetailsMappingDAO.getActiveDetails(entity.getPermitNo(), PermitDetailsType.CONDITION.getLabel());
        if (!ObjectsUtil.isNull(conditionsEntityMapping)) {
            PermitConditionsEntity condEntity = permitConditionsDAO.getPermitCondition(conditionsEntityMapping.getDetailValue());
            if (ObjectsUtil.isNotNull(condEntity)) {
            	model.setAnyOtherConditionsCode(condEntity.getCode());
            	model.setAnyOtherConditions(condEntity.getPermitCondDesc());
            }
        }
        List<PermitDetailsMappingEntity> neighbouringDistricts = permitDetailsMappingDAO.getActiveDetailList(entity.getPermitNo(), PermitDetailsType.DISTRICT.getLabel());
        if (!ObjectsUtil.isNullOrEmpty(neighbouringDistricts)) {
            List<CodeNameModel> districtList = new ArrayList<>();
            for(PermitDetailsMappingEntity mapping : neighbouringDistricts){
                DistrictEntity district = districtDAO.getDistrictByCode(mapping.getDetailValue());
                if(ObjectsUtil.isNotNull(district)){
                    CodeNameModel nameCode = new CodeNameModel();
                    nameCode.setCode(district.getCode());
                    nameCode.setName(district.getName());
                    districtList.add(nameCode);
                }
            }
            model.setNeighbouringDistricts(districtList);
        }
        // ---- set RTO signature----------------------
        if(ObjectsUtil.isNotNull(mviUserId)){
            RTAEmployeeEntity rtaEmployee = rTAEmployeeDAO.getByUserId(mviUserId);
            if(ObjectsUtil.isNotNull(rtaEmployee)){
                String fileName = rtaEmployee.getSignFileName();
                String signatureFile = baseURL + File.separator + signaturePath + File.separator + fileName;
                model.setRtoSignFilePath(signatureFile);
            } else {
                logger.error("Error Signature not found for User Id : " + mviUserId);
            }
        } else {
            try {
                String fileName = applicationService.getRtoSignFileName(rtaEntity.getCode());
                String signatureFile = baseURL + File.separator + signaturePath + File.separator + fileName;
                model.setRtoSignFilePath(signatureFile);
            } catch (Exception ex) {
                logger.error("Exception While reading signature in permit auth card ......");
                logger.debug("Exception : ", ex);
            }
        }
        return model;
    }
    
    public Long getExpireDate(Long currentDate) {

        Long mayMonthDate = DateUtil.getLongDate(permitExpireMonth + DateUtil.getYear(currentDate), permit000000);
        Integer expireYear = null;
        if (currentDate < mayMonthDate) {
            expireYear = Integer.valueOf(DateUtil.getYear(currentDate));
        } else {
            expireYear = Integer.valueOf(DateUtil.getYear(currentDate)) + noOfYear;
        }
        return DateUtil.getLongDate(permitExpireMonth + expireYear, permit235959);
    }

    @Override
	public SaveUpdateResponse syncData(SyncDataModel syncDataModel, VehicleRCEntity vehicleRCEntity) {
		logger.info("::Permit :Service:syncData::::start::");
		SaveUpdateResponse saveUpdateResponse = null;
		switch(ServiceType.getServiceType(syncDataModel.getServiceType())){
		case PERMIT_FRESH:
			saveUpdateResponse = syncPermitNew(syncDataModel , vehicleRCEntity);
			break;
		case PERMIT_RENEWAL:
			saveUpdateResponse = syncPermitRenewal(syncDataModel , vehicleRCEntity);
			break;
		case PERMIT_RENEWAL_AUTH_CARD:
			saveUpdateResponse = permitAuthCardService.renewPermitAuthCard(syncDataModel);
			break;
		case PERMIT_SURRENDER:
			saveUpdateResponse = syncPermitSurrender(syncDataModel , vehicleRCEntity);
			break;
		case PERMIT_REPLACEMENT_VEHICLE:
			saveUpdateResponse=syncPermitVehicleReplacement(syncDataModel,vehicleRCEntity);
			break;
		case PERMIT_VARIATIONS:
			saveUpdateResponse = syncPermitVariations(syncDataModel , vehicleRCEntity);
			break;	
		}
		logger.info("::Permit :Service :syncData::::end::");
		return saveUpdateResponse;
	}
   
	public SaveUpdateResponse syncPermitNew(SyncDataModel syncDataModel, VehicleRCEntity vehicleRCEntity){
		logger.info(":::syncPermitNew:::start:::::::: ");
		PermitHeaderEntity permitHeaderEntity = null;
		SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
		ObjectMapper mapper = new ObjectMapper();
		PermitNewRequestModel permitNewRequestModel = null;
		CustCorporateDetailsEntity  custCorpEntity = null;
		CustIndividualDetailsEntity custIndDetailsEntity = null;
		List<ApplicationFormDataModel> formList = syncDataModel.getFormList();
		for(ApplicationFormDataModel appFormModel : formList){
			if(appFormModel.getFormCode().equalsIgnoreCase(FormCodeType.PCF_FORM.getLabel())){
				try {
					permitNewRequestModel =  mapper.readValue(appFormModel.getFormData(), PermitNewRequestModel.class);
					logger.info(":::permitNewRequestModel:::::  " + permitNewRequestModel);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(permitNewRequestModel == null){
			saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
			saveUpdateResponse.setMessage("Permit records not founds");
			return saveUpdateResponse;
		}
		
		if(permitNewRequestModel.getPermitClass().equalsIgnoreCase(PermitClassType.PUKKA.getLabel())){
		    logger.info("Generating permit new ............. Pukka");
		    permitHeaderEntity = permitHeaderDAO.getActivePermit(syncDataModel.getPrNumber(), false);
		} else if(permitNewRequestModel.getPermitClass().equalsIgnoreCase(PermitClassType.TEMPORARY.getLabel())){
		    logger.info("Generating permit new ............. Temporary");
		    permitHeaderEntity = permitHeaderDAO.getActivePermit(syncDataModel.getPrNumber(), true);
		} else {
		    logger.error("Invalid Permit Class Found : " + permitNewRequestModel.getPermitClass());
		    saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Invalid Permit Class Found !!!");
            return saveUpdateResponse;
		}
		if(permitHeaderEntity != null){
		    permitHeaderEntity.setModifiedBy(vehicleRCEntity.getAadharNo());
		    permitHeaderEntity.setModifiedOn(new Timestamp(new Date().getTime()));
            permitHeaderEntity.setStatus(Status.CLOSED.getValue());
            permitHeaderDAO.update(permitHeaderEntity);
        }
		
		permitHeaderEntity = new PermitHeaderEntity();
		if(permitNewRequestModel.getPermitClass().equalsIgnoreCase(PermitClassType.PUKKA.getLabel())){
			permitHeaderEntity.setIsTempPermit(false);
			permitHeaderEntity.setPermitType(permitNewRequestModel.getPermitType());
			permitHeaderEntity.setValidFromDate(new Date());
			permitHeaderEntity.setValidToDate(DateUtil.addYearToDate(new Date(), 5));
		}else{
			permitHeaderEntity.setIsTempPermit(true);
			permitHeaderEntity.setPermitType(permitNewRequestModel.getTempPermitType());
			permitHeaderEntity.setValidFromDate(new Date(permitNewRequestModel.getFromDate() * 1000));
			permitHeaderEntity.setValidToDate(new Date(permitNewRequestModel.getToDate() * 1000));
			
			permitHeaderEntity.setForwardJourneyStartingStation(permitNewRequestModel.getFwdStartPlaceName());
			permitHeaderEntity.setForwardJourneyDestination(permitNewRequestModel.getFwdDestPlaceName());
			permitHeaderEntity.setReturnJourneyStartingStation(permitNewRequestModel.getRtnStartPlaceName());
            permitHeaderEntity.setReturnJourneyDestination(permitNewRequestModel.getRtnDestPlaceName());
            permitHeaderEntity.setForwardJourneyEnroute(permitNewRequestModel.getFwdPlacesCovering());
    		permitHeaderEntity.setReturnJourneyEnroute(permitNewRequestModel.getRtnPlacesCovering());
		}
		switch (OwnershipType.getOwnershipType(vehicleRCEntity.getOwnershipType())) {
		case POLICE:
		case COMPANY:
		case STU_VEHICLES:
		case ORGANIZATION:
			custCorpEntity = custCorpDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
			permitHeaderEntity.setHolderMobileNo(custCorpEntity.getMobile());
			permitHeaderEntity.setHolderNm(custCorpEntity.getDisplayName());
			break;
		case INDIVIDUAL:
			custIndDetailsEntity = customerDAO
					.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
			permitHeaderEntity.setHolderMobileNo(custIndDetailsEntity.getMobileNo());
			permitHeaderEntity.setHolderNm(custIndDetailsEntity.getFirstName()+" "+ custIndDetailsEntity.getSurName());
			break;
		}
		
		try{
		    VehicleDetailsEntity ve = vehicleDetailsDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
	        permitHeaderEntity.setVehicleClass(ve.getVehicleSubClass());
		} catch(Exception ex){
		    logger.error("Exception while getting vehicle details..............");
		}
		permitHeaderEntity.setPurpose(permitNewRequestModel.getPurpose());
		permitHeaderEntity.setStatus(Status.OPEN.getValue());
		permitHeaderEntity.setApplType(syncDataModel.getServiceType());
		permitHeaderEntity.setIssueDate(new Timestamp(new Date().getTime()));
		permitHeaderEntity.setCreatedBy(vehicleRCEntity.getAadharNo());
		permitHeaderEntity.setCreatedOn(new Timestamp(new Date().getTime()));
		permitHeaderEntity.setPermitIssuedTo("SELF");
		permitHeaderEntity.setVehicleRegdNo(vehicleRCEntity.getPrNumber());
		permitHeaderEntity.setHolderAadharNo(vehicleRCEntity.getAadharNo());
		permitHeaderEntity.setRtaOfficeId(vehicleRCEntity.getRtaOfficeId().getRtaOfficeId());
		permitHeaderEntity.setPermitSubType(permitNewRequestModel.getPermitSubType());
		permitHeaderEntity.setPurpose(permitNewRequestModel.getPurpose());
		permitHeaderEntity.setApplId(syncDataModel.getApplicationNumber());
		permitHeaderDAO.save(permitHeaderEntity);
		//#TODO get the seq number and generate the permit number : change the logic
		permitHeaderEntity.setPermitNo(getPermitNumber(vehicleRCEntity.getRtaOfficeId().getCode(), permitHeaderEntity.getPermitHeaderId(), permitHeaderEntity.getPermitType()));
		logger.info("::::Permit Header save:::::::");
		
		updatePermitDetailMapping( permitHeaderEntity , permitNewRequestModel);
		
		try {
			PermitFeeEntity permitFeeEntity = new PermitFeeEntity();
			permitFeeEntity = permitFeeDAO.getByVehicleRcIdNdStatus(vehicleRCEntity.getVehicleRcId(), Status.OPEN.getValue());
			if(permitFeeEntity != null){
				permitFeeEntity.setStatus(Status.CLOSED.getValue());
				permitFeeDAO.saveOrUpdate(permitFeeEntity);
			}	
			permitFeeEntity = new PermitFeeEntity();
			permitFeeEntity.setPermitFee(Double.parseDouble(syncDataModel.getFeeModel().getPermitFee()));
			permitFeeEntity.setPermitService(Double.parseDouble(syncDataModel.getFeeModel().getPermitServiceCharge()));
			permitFeeEntity.setVehicleRcId(vehicleRCEntity.getVehicleRcId());
			permitFeeEntity.setCreatedBy("CITIZEN1");
			permitFeeEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			permitFeeEntity.setStatus(Status.OPEN.getValue());
			permitFeeDAO.save(permitFeeEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			TaxDetailEntity taxDetailEntity = null;
			taxDetailEntity = taxDetailDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
			if(taxDetailEntity != null){
				if(syncDataModel.getTaxModel() != null && syncDataModel.getTaxModel().getTaxAmt() != null)
				logger.info("::::syncPermitNew::::tax:::: " + syncDataModel.getTaxModel().getTaxAmt());
				double totalTaxAmt =  taxDetailEntity.getTaxAmt() + Double.parseDouble(syncDataModel.getTaxModel().getTaxAmt());
				taxDetailEntity.setTaxAmt(totalTaxAmt);
				taxDetailEntity.setModifiedBy("CITIZEN1");
				taxDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
				taxDetailDAO.saveOrUpdate(taxDetailEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		saveUpdateResponse.setStatus(SaveUpdateResponse.SUCCESS);
		logger.info(":::syncPermitNew:::end:::::::");
		if(permitNewRequestModel.getPermitClass().equals(PermitClassType.PUKKA.getLabel()) && (permitNewRequestModel.getPermitType().equals("AITC") 
                || permitNewRequestModel.getPermitType().equals("AITP"))){
            permitAuthCardService.createPermitAuthCard(permitHeaderEntity);
        }
		return saveUpdateResponse;
	}
    
    public void updatePermitDetailMapping(PermitHeaderEntity permitHeaderEntity , PermitNewRequestModel permitNewRequestModel){
    	List<PermitDetailsMappingEntity> permitDetailsMappingEntitys = null;
		PermitDetailsMappingEntity permitDetailsMappingEntity = null;
		permitDetailsMappingEntitys =  permitDetailsMappingDAO.getByPermitNoNdDetailType(permitHeaderEntity, Status.OPEN.getValue());
		for(PermitDetailsMappingEntity persistPermitDetailsMapping : permitDetailsMappingEntitys){
			persistPermitDetailsMapping.setStatus(Status.CLOSED.getValue());
			persistPermitDetailsMapping.setModifiedOn(new Timestamp(new Date().getTime()));
			permitDetailsMappingDAO.update(persistPermitDetailsMapping);
		}
		if(!ObjectsUtil.isNullOrEmpty(permitNewRequestModel.getNeighbouringDistricts())){
		    for(CodeNameModel codeName : permitNewRequestModel.getNeighbouringDistricts()){
		        permitDetailsMappingEntity = new PermitDetailsMappingEntity();
                permitDetailsMappingEntity.setStatus(Status.OPEN.getValue());
                permitDetailsMappingEntity.setDetailValue(codeName.getCode());
                permitDetailsMappingEntity.setDetailType(PermitDetailsType.DISTRICT.getLabel());
                permitDetailsMappingEntity.setPermitSequenceId(permitHeaderEntity.getPermitSequenceId());
                permitDetailsMappingEntity.setPermitNo(permitHeaderEntity.getPermitNo());
                permitDetailsMappingEntity.setCreatedOn(new Timestamp(new Date().getTime()));
                permitDetailsMappingDAO.save(permitDetailsMappingEntity);
		    }
		}
		if(permitNewRequestModel.getNeighbouringStateCode() != null && !permitNewRequestModel.getNeighbouringStateCode().equals("")){
				permitDetailsMappingEntity = new PermitDetailsMappingEntity();
				permitDetailsMappingEntity.setStatus(Status.OPEN.getValue());
				permitDetailsMappingEntity.setDetailValue(permitNewRequestModel.getNeighbouringStateCode());
				permitDetailsMappingEntity.setDetailType(PermitDetailsType.STATE.getLabel());
				permitDetailsMappingEntity.setPermitSequenceId(permitHeaderEntity.getPermitSequenceId());
				permitDetailsMappingEntity.setPermitNo(permitHeaderEntity.getPermitNo());
				permitDetailsMappingEntity.setCreatedOn(new Timestamp(new Date().getTime()));
				permitDetailsMappingDAO.save(permitDetailsMappingEntity);
		}
		if(permitNewRequestModel.getPerGoods() != null && !permitNewRequestModel.getPerGoods().equals("")){
				permitDetailsMappingEntity = new PermitDetailsMappingEntity();
				permitDetailsMappingEntity.setStatus(Status.OPEN.getValue());
				permitDetailsMappingEntity.setDetailValue(permitNewRequestModel.getPerGoods());
				permitDetailsMappingEntity.setDetailType(PermitDetailsType.GOODS.getLabel());
				permitDetailsMappingEntity.setPermitSequenceId(permitHeaderEntity.getPermitSequenceId());
				permitDetailsMappingEntity.setPermitNo(permitHeaderEntity.getPermitNo());
				permitDetailsMappingEntity.setCreatedOn(new Timestamp(new Date().getTime()));
				permitDetailsMappingDAO.save(permitDetailsMappingEntity);
		}
		if(permitNewRequestModel.getPerRoute() != null && !permitNewRequestModel.getPerRoute().equals("")){
				permitDetailsMappingEntity = new PermitDetailsMappingEntity();
				permitDetailsMappingEntity.setStatus(Status.OPEN.getValue());
				permitDetailsMappingEntity.setDetailValue(permitNewRequestModel.getPerRoute());
				permitDetailsMappingEntity.setDetailType(PermitDetailsType.ROUTE.getLabel());
				permitDetailsMappingEntity.setPermitSequenceId(permitHeaderEntity.getPermitSequenceId());
				permitDetailsMappingEntity.setPermitNo(permitHeaderEntity.getPermitNo());
				permitDetailsMappingEntity.setCreatedOn(new Timestamp(new Date().getTime()));
				permitDetailsMappingDAO.save(permitDetailsMappingEntity);
		}
		if(permitNewRequestModel.getPermitCond() != null && !permitNewRequestModel.getPermitCond().equals("")){
				permitDetailsMappingEntity = new PermitDetailsMappingEntity();
				permitDetailsMappingEntity.setStatus(Status.OPEN.getValue());
				permitDetailsMappingEntity.setDetailValue(permitNewRequestModel.getPermitCond());
				permitDetailsMappingEntity.setDetailType(PermitDetailsType.CONDITION.getLabel());
				permitDetailsMappingEntity.setPermitDtlSeqId(permitHeaderEntity.getPermitSequenceId());
				permitDetailsMappingEntity.setPermitNo(permitHeaderEntity.getPermitNo());
				permitDetailsMappingEntity.setCreatedOn(new Timestamp(new Date().getTime()));
				permitDetailsMappingDAO.save(permitDetailsMappingEntity);
		}
    }
    
    public SaveUpdateResponse syncPermitRenewal(SyncDataModel syncDataModel, VehicleRCEntity vehicleRCEntity){
		logger.info(":::syncPermitRenewal:::start:::::::: ");
		PermitHeaderEntity persistPermitHeaderEntity = null;
		PermitHeaderEntity newPermitHeaderEntity = null;
		SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
		persistPermitHeaderEntity = permitHeaderDAO.getPrimaryPermitActiveByPr(vehicleRCEntity.getPrNumber());
		try {
			newPermitHeaderEntity = (PermitHeaderEntity)persistPermitHeaderEntity.clone();
		} catch (CloneNotSupportedException e1) {
			e1.printStackTrace();
		}
		
		persistPermitHeaderEntity.setStatus(Status.CLOSED.getValue());
		persistPermitHeaderEntity.setModifiedOn(new Timestamp(new Date().getTime()));
		permitHeaderDAO.update(persistPermitHeaderEntity);
		
		newPermitHeaderEntity.setStatus(Status.OPEN.getValue());
		newPermitHeaderEntity.setCreatedOn(new Timestamp(new Date().getTime()));
		newPermitHeaderEntity.setIssueDate(new Timestamp(new Date().getTime()));
		newPermitHeaderEntity.setApplType(syncDataModel.getServiceType());
		newPermitHeaderEntity.setValidFromDate(new Date());
		newPermitHeaderEntity.setValidToDate(DateUtil.addDaysToDate(DateUtil.addYearToDate(new Date(), 5), -1));
		newPermitHeaderEntity.setApplId(syncDataModel.getApplicationNumber());
		permitHeaderDAO.save(newPermitHeaderEntity);
		
		logger.info("::Renewal::Permit Header save:::::::");
		
		List<PermitDetailsMappingEntity> permitDetailsMappingEntitys = null;
		PermitDetailsMappingEntity newPermitDetailsMappingEntity = null;
		permitDetailsMappingEntitys =  permitDetailsMappingDAO.getByPermitNoNdDetailType(newPermitHeaderEntity, Status.OPEN.getValue());
		for(PermitDetailsMappingEntity peristPermitDetailsMappingEntity : permitDetailsMappingEntitys){
			try {
				newPermitDetailsMappingEntity = (PermitDetailsMappingEntity)peristPermitDetailsMappingEntity.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			peristPermitDetailsMappingEntity.setStatus(Status.CLOSED.getValue());
			peristPermitDetailsMappingEntity.setModifiedOn(new Timestamp(new Date().getTime()));
			permitDetailsMappingDAO.update(peristPermitDetailsMappingEntity);
			newPermitDetailsMappingEntity.setStatus(Status.OPEN.getValue());
			newPermitDetailsMappingEntity.setPermitDtlSeqId(newPermitDetailsMappingEntity.getPermitSequenceId());
			newPermitDetailsMappingEntity.setCreatedOn(new Timestamp(new Date().getTime()));
			permitDetailsMappingDAO.save(newPermitDetailsMappingEntity);
		}
		try {
			PermitFeeEntity permitFeeEntity = new PermitFeeEntity();
			permitFeeEntity = permitFeeDAO.getByVehicleRcIdNdStatus(vehicleRCEntity.getVehicleRcId(), Status.OPEN.getValue());
			if(permitFeeEntity != null){
				permitFeeEntity.setStatus(Status.CLOSED.getValue());
				permitFeeDAO.saveOrUpdate(permitFeeEntity);
			}	
			permitFeeEntity = new PermitFeeEntity();
			permitFeeEntity.setPermitFee(Double.parseDouble(syncDataModel.getFeeModel().getPermitFee()));
			permitFeeEntity.setPermitService(Double.parseDouble(syncDataModel.getFeeModel().getPermitServiceCharge()));
			permitFeeEntity.setVehicleRcId(vehicleRCEntity.getVehicleRcId());
			permitFeeEntity.setCreatedBy("CITIZEN1");
			permitFeeEntity.setStatus(Status.OPEN.getValue());
			permitFeeDAO.save(permitFeeEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveUpdateResponse.setStatus(SaveUpdateResponse.SUCCESS);
		logger.info(":::syncPermitRenewal:::end:::::::");
		return saveUpdateResponse;
	}
    public SaveUpdateResponse syncPrimaryPermitSurrender(SyncDataModel syncDataModel, VehicleRCEntity vehicleRCEntity){
    	logger.info("\n\n::::::::::Surrendering pucca permit:::::::::\n\n");
    	SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        List<PermitHeaderEntity> permitHeaderEntityList = permitHeaderDAO.getPermitActiveByPr(syncDataModel.getPrNumber());
        if (!ObjectsUtil.isNull(permitHeaderEntityList)) {
            for (PermitHeaderEntity phe : permitHeaderEntityList) {
                List<PermitDetailsMappingEntity> permitDetailsMappingEntitys = permitDetailsMappingDAO.getByPermitNoNdDetailType(phe, Status.OPEN.getValue());
                logger.info("\n\n::::::::::Inside PermitDetailsMappingEntity :::::::::\n\n");
                if (!ObjectsUtil.isNull(permitDetailsMappingEntitys)) {
                    for(PermitDetailsMappingEntity persistPermitDetailsMapping : permitDetailsMappingEntitys){
                        persistPermitDetailsMapping.setStatus(Status.SURRENDERED.getValue());
                        persistPermitDetailsMapping.setModifiedOn(new Timestamp(new Date().getTime()));
                        persistPermitDetailsMapping.setModifiedBy(vehicleRCEntity.getAadharNo());
                        permitDetailsMappingDAO.update(persistPermitDetailsMapping);
                    }
                }
                phe.setModifiedBy(vehicleRCEntity.getAadharNo());
                phe.setStatus(Status.SURRENDERED.getValue());
                phe.setApplId(syncDataModel.getApplicationNumber());
                permitHeaderDAO.saveOrUpdate(phe);
                logger.info("\n\n::::::::::Permit Sureendered :::::::::\n\n");

            }
        } else {
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            return saveUpdateResponse;    
        }
        saveUpdateResponse.setStatus(SaveUpdateResponse.SUCCESS);
        return saveUpdateResponse;
    }
    
	private SaveUpdateResponse syncPermitSurrender(SyncDataModel syncDataModel, VehicleRCEntity vehicleRCEntity) {
		logger.info("::::::::::Inside Permit Surrender Syncing 111:::::::::");
		List<ApplicationFormDataModel> formList = syncDataModel.getFormList();
		String permitSurrender = null;
		logger.info("::::::::::Inside Permit Surrender Syncing 22222:::::::::");
		for (ApplicationFormDataModel appFormModel : formList) {
			if (appFormModel.getFormCode().equalsIgnoreCase(FormCodeType.PCS_FORM.getLabel())) {
				try {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode jsonData = mapper.readTree(appFormModel.getFormData());
					permitSurrender = jsonData.get("permitSurrender").asText();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		logger.debug("::::::::::permit to surrender:::::::::" + permitSurrender);
		SaveUpdateResponse saveUpdateResponse = null;
		if (permitSurrender.equalsIgnoreCase(PermitClassType.PUKKA.toString())) {
			logger.info("\n\n::::::::::Surrender pucca permit:::::::::\n\n");
			saveUpdateResponse = syncPrimaryPermitSurrender(syncDataModel, vehicleRCEntity);
		} else if (permitSurrender.equalsIgnoreCase(PermitClassType.TEMPORARY.toString())) {
			logger.info("\n\n::::::::::Surrender Temporary permit:::::::::\n\n");
			saveUpdateResponse = syncTempPermitSurrender(syncDataModel, vehicleRCEntity);
		}
		return saveUpdateResponse;
	}
    
    
    public SaveUpdateResponse syncTempPermitSurrender(SyncDataModel syncDataModel, VehicleRCEntity vehicleRCEntity){
    	logger.info("\n\n::::::::::Surrendering temp pucca permit:::::::::\n\n");

    	SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        PermitHeaderEntity entity = permitHeaderDAO.getTempPermitActiveByPr(syncDataModel.getPrNumber());
        List<PermitDetailsMappingEntity> permitDetailsMappingEntitys = permitDetailsMappingDAO.getByPermitNoNdDetailType(entity, Status.OPEN.getValue());
        logger.info("\n\n::::::::::permitDetailsMappingEntitys HERE for temp permit:::::::::\n\n");
        if (!ObjectsUtil.isNull(permitDetailsMappingEntitys)) {
            for(PermitDetailsMappingEntity persistPermitDetailsMapping : permitDetailsMappingEntitys){
                persistPermitDetailsMapping.setStatus(Status.SURRENDERED.getValue());
                persistPermitDetailsMapping.setModifiedOn(new Timestamp(new Date().getTime()));
                persistPermitDetailsMapping.setModifiedBy(vehicleRCEntity.getAadharNo());
                permitDetailsMappingDAO.update(persistPermitDetailsMapping);
            }
        }
        entity.setModifiedBy(vehicleRCEntity.getAadharNo());
        entity.setStatus(Status.SURRENDERED.getValue());
        entity.setApplId(syncDataModel.getApplicationNumber());
        permitHeaderDAO.saveOrUpdate(entity);
        logger.info("\n\n::::::::::temp permit surrendered:::::::::\n\n");
        saveUpdateResponse.setStatus(SaveUpdateResponse.SUCCESS);
        return saveUpdateResponse;
    }
    
    
    
    /**
     * @author Gautam.kumar
     * @description To sync the permit vehicle replacement details
     * @param syncDataModel
     * @param vehicleRCEntity
     * @return
     */
    private SaveUpdateResponse syncPermitVehicleReplacement(SyncDataModel syncDataModel,
			VehicleRCEntity vehicleRCEntity) {
		logger.info("syncPermitVehicleReplacement::start::::::");
		
		return null;
	}

    public SaveUpdateResponse syncPermitVariations(SyncDataModel syncDataModel, VehicleRCEntity vehicleRCEntity){
    	logger.info(":::syncPermitVariations:::start:::::::: ");
		PermitHeaderEntity persistPermitHeaderEntity = null;
		SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
		ObjectMapper mapper = new ObjectMapper();
		PermitNewRequestModel permitNewRequestModel = null;
		List<ApplicationFormDataModel> formList = syncDataModel.getFormList();
		for (ApplicationFormDataModel appFormModel : formList) {
			if (appFormModel.getFormCode().equalsIgnoreCase(FormCodeType.PCV_FORM.getLabel())) {
				try {
					permitNewRequestModel = mapper.readValue(appFormModel.getFormData(), PermitNewRequestModel.class);
					logger.info(":::syncPermitVariations object:::::  " + permitNewRequestModel);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (permitNewRequestModel == null) {
			saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
			saveUpdateResponse.setMessage("Permit records not founds");
			return saveUpdateResponse;
		}
		persistPermitHeaderEntity = permitHeaderDAO.getPrimaryPermitActiveByPr(vehicleRCEntity.getPrNumber());
		String permitNo = persistPermitHeaderEntity.getPermitNo();
		String permitType = persistPermitHeaderEntity.getPermitType();
		VehicleDetailsEntity vehicleDetailEntity = vehicleDetailsDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
		String cov = vehicleDetailEntity.getVehicleSubClass();
		PermitDetailsMappingEntity permitDetailsMappingEntity = null;
		if (PermitType.EIB.getValue().equals(permitType) || PermitType.PSVP.getValue().equals(permitType)
				|| (PermitType.AITP.getValue().equals(permitType) && "TOVT".equals(cov))
				|| PermitType.CCP.getValue().equals(permitType)) {
			permitDetailsMappingEntity = permitDetailsMappingDAO.getActiveDetails(permitNo,
					PermitDetailsType.ROUTE.getLabel());
			if (permitDetailsMappingEntity == null) {
				permitDetailsMappingEntity = new PermitDetailsMappingEntity();
				permitDetailsMappingEntity.setStatus(Status.OPEN.getValue());
				permitDetailsMappingEntity.setDetailValue(permitNewRequestModel.getPerRoute());
				permitDetailsMappingEntity.setDetailType(PermitDetailsType.ROUTE.getLabel());
				permitDetailsMappingEntity.setPermitSequenceId(persistPermitHeaderEntity.getPermitSequenceId());
				permitDetailsMappingEntity.setPermitNo(persistPermitHeaderEntity.getPermitNo());
				permitDetailsMappingEntity.setCreatedOn(new Timestamp(new Date().getTime()));
				permitDetailsMappingDAO.save(permitDetailsMappingEntity);
			} else {
				permitDetailsMappingEntity.setStatus(Status.CLOSED.getValue());
				permitDetailsMappingEntity.setModifiedOn(new Timestamp(new Date().getTime()));
				permitDetailsMappingDAO.update(permitDetailsMappingEntity);
				PermitDetailsMappingEntity newDetailMapping;
				try {
					newDetailMapping = (PermitDetailsMappingEntity) permitDetailsMappingEntity.clone();
					newDetailMapping.setStatus(Status.OPEN.getValue());
					newDetailMapping.setDetailValue(permitNewRequestModel.getPerRoute());
					newDetailMapping.setCreatedOn(new Timestamp(new Date().getTime()));
					permitDetailsMappingDAO.save(newDetailMapping);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
			if (PermitType.CCP.getValue().equals(permitType)) {
				PermitHeaderEntity newPermitHeaderEntity = null;
				try {
					newPermitHeaderEntity = (PermitHeaderEntity) persistPermitHeaderEntity.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				persistPermitHeaderEntity.setStatus(Status.CLOSED.getValue());
				persistPermitHeaderEntity.setModifiedOn(new Timestamp(new Date().getTime()));
				permitHeaderDAO.update(persistPermitHeaderEntity);
				
				newPermitHeaderEntity.setStatus(Status.OPEN.getValue());
				newPermitHeaderEntity.setPermitSubType(permitNewRequestModel.getPermitSubType());
				newPermitHeaderEntity.setCreatedOn(new Timestamp(new Date().getTime()));
				permitHeaderDAO.save(newPermitHeaderEntity);
			}
		} else if (PermitType.NP.getValue().equals(permitType) || PermitType.GCP.getValue().equals(permitType)) {
			permitDetailsMappingEntity = permitDetailsMappingDAO.getActiveDetails(permitNo,
					PermitDetailsType.GOODS.getLabel());
			if (permitDetailsMappingEntity == null) {
				permitDetailsMappingEntity = new PermitDetailsMappingEntity();
				permitDetailsMappingEntity.setStatus(Status.OPEN.getValue());
				permitDetailsMappingEntity.setDetailValue(permitNewRequestModel.getPerGoods());
				permitDetailsMappingEntity.setDetailType(PermitDetailsType.GOODS.getLabel());
				permitDetailsMappingEntity.setPermitSequenceId(persistPermitHeaderEntity.getPermitSequenceId());
				permitDetailsMappingEntity.setPermitNo(persistPermitHeaderEntity.getPermitNo());
				permitDetailsMappingEntity.setCreatedOn(new Timestamp(new Date().getTime()));
				permitDetailsMappingDAO.save(permitDetailsMappingEntity);
			} else {
				permitDetailsMappingEntity.setStatus(Status.CLOSED.getValue());
				permitDetailsMappingEntity.setModifiedOn(new Timestamp(new Date().getTime()));
				permitDetailsMappingDAO.update(permitDetailsMappingEntity);
				PermitDetailsMappingEntity newDetailMapping;
				try {
					newDetailMapping = (PermitDetailsMappingEntity) permitDetailsMappingEntity.clone();
					newDetailMapping.setStatus(Status.OPEN.getValue());
					newDetailMapping.setDetailValue(permitNewRequestModel.getPerGoods());
					newDetailMapping.setCreatedOn(new Timestamp(new Date().getTime()));
					permitDetailsMappingDAO.save(newDetailMapping);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			TaxDetailEntity taxDetailEntity = null;
			taxDetailEntity = taxDetailDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
			if(taxDetailEntity != null){
				if(syncDataModel.getTaxModel() != null && syncDataModel.getTaxModel().getTaxAmt() != null)
				logger.info("::::syncPermitNew::::tax:::: " + syncDataModel.getTaxModel().getTaxAmt());
				taxDetailEntity.setTaxAmt(Double.parseDouble(syncDataModel.getTaxModel().getTaxAmt()));
				taxDetailEntity.setModifiedBy("CITIZEN1");
				taxDetailDAO.saveOrUpdate(taxDetailEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		saveUpdateResponse.setStatus(SaveUpdateResponse.SUCCESS);
		logger.info(":::syncPermitVariations:::end:::::::");
		return saveUpdateResponse;
    }
    
    @Override
    @Transactional
	public List<PermitHeaderModel> getPermitHeaderDetails(Long vehicleRcId, String permitType) throws VehicleRcNotFoundException, NotFoundException {
    	List<PermitHeaderModel> headerModels=getPermitHeaderDetails(vehicleRcId);
    	if(permitType!=null){
    		List<PermitHeaderModel> headerModelsNew = new ArrayList<>();
    		for (PermitHeaderModel phm : headerModels) {
    			if (permitType.equals("pukka")) {
    				if (!phm.getIsTempPermit()) {
    					headerModelsNew.add(phm);
    				}
    			} else if (permitType.equals("temp")) {
    				if (phm.getIsTempPermit()) {
    					headerModelsNew.add(phm);
    				}
    			}
    		}
    		headerModels = headerModelsNew;
    	}
    	return headerModels;
	}
    
    @Override
    @Transactional
    public List<PermitHeaderModel> getPermitHeaderDetails(Long vehicleRcId) throws VehicleRcNotFoundException, NotFoundException {
        VehicleRCEntity vehicleRcEntity = vehicleDAO.get(vehicleRcId);
        if (vehicleRcEntity == null) {
            logger.error("vehicleRcId not found .. " + vehicleRcId);
            throw new VehicleRcNotFoundException("vehicleRc not found");
        }
        String prNumber = vehicleRcEntity.getPrNumber();
        if (prNumber == null) {
            logger.error("prNumber not found .. " + vehicleRcId);
            throw new NotFoundException("prNumber not found");
        }
        List<PermitHeaderEntity> permitHeaderEntityList = permitHeaderDAO.getPermitActiveByPr(prNumber);
        List<PermitHeaderModel> permitHeaderModelList = null;
        if (!ObjectsUtil.isNullOrEmpty(permitHeaderEntityList)) {
            permitHeaderModelList = new ArrayList<>();
            for (PermitHeaderEntity phe : permitHeaderEntityList) {
                PermitHeaderModel phm = new PermitHeaderModel();
                phm.setApplId(phe.getApplId());
                phm.setApplOrigination(phe.getApplOrigination());
                phm.setApplType(phe.getApplType());
                phm.setApprovalRmks(phe.getApprovalRmks());
                phm.setApprovedBy(phe.getApprovedBy());
                phm.setApprovedDate(ObjectsUtil.isNotNull(phe.getApprovedDt()) ? phe.getApprovedDt().getTime() / 1000 : null);
                phm.setDaysPerTrip(phe.getDaysPerTrip());
                phm.setEntityCd(phe.getEntityCd());
                phm.setForwardJourneyDestination(phe.getForwardJourneyDestination());
                phm.setForwardJourneyEnroute(phe.getForwardJourneyEnroute());
                phm.setForwardJourneyStartingStation(phe.getForwardJourneyStartingStation());
                phm.setHolderAadharNo(phe.getHolderAadharNo());
                phm.setHolderMobileNo(phe.getHolderMobileNo());
                phm.setHolderNm(phe.getHolderNm());
                phm.setHomeStateAuthority(phe.getHomeStateAuthority());
                phm.setIssueDate(ObjectsUtil.isNotNull(phe.getIssueDate()) ? phe.getIssueDate().getTime() / 1000 : null);
                phm.setIsTempPermit(phe.getIsTempPermit());
                phm.setModuleCd(phe.getModuleCd());
                phm.setNoOfTrips(phe.getNoOfTrips());
                phm.setOsRecomLttrId(phe.getOsRecomLttrId());
                phm.setOtherStateNm(phe.getOtherStateNm());
                phm.setPassangerListEncl(phe.getPassangerListEnclId());
                phm.setPermitIssuedTo(phe.getPermitIssuedTo());
                phm.setPermitNo(phe.getPermitNo());
                phm.setPermitSubType(phe.getPermitSubType());
                phm.setPermitType(phe.getPermitType());
                phm.setPurpose(phe.getPurpose());
                phm.setReturnJourneyDestination(phe.getReturnJourneyDestination());
                phm.setReturnJourneyEnroute(phe.getReturnJourneyEnroute());
                phm.setReturnJourneyStartingStation(phe.getReturnJourneyStartingStation());
                phm.setRtaOfficeCd(vehicleRcEntity.getRtaOfficeId().getCode());
                phm.setStateEntryDt(ObjectsUtil.isNotNull(phe.getStateEntryDt()) ? phe.getStateEntryDt().getTime() / 1000 : null);
                phm.setStateNationalPermit(phe.getStateNationalPermit());
                phm.setStatus(Status.getStatus(phe.getStatus()).getLabel());
                phm.setSupportTicketRemarks(phe.getSupportTicketRemarks());
                phm.setValidFromDate(ObjectsUtil.isNotNull(phe.getValidFromDate()) ? phe.getValidFromDate().getTime() / 1000 : null);
                phm.setValidToDate(ObjectsUtil.isNotNull(phe.getValidToDate()) ? phe.getValidToDate().getTime() / 1000 : null);
                phm.setVehicleRegdNo(phe.getVehicleRegdNo());
                phm.setRtaOfficeName(vehicleRcEntity.getRtaOfficeId().getName());
                    PermitTypeEntity pte = permitTypeDAO.getPermitTypeByCode(phe.getPermitType());
                    String permitTypeName = pte.getName();
                    phm.setPermitTypeName(permitTypeName);
                permitHeaderModelList.add(phm);
            }
        }
        return permitHeaderModelList;
    }
    
    private String getPermitNumber(String rtaOfficeCode, Long permitHeaderId, String permitType) {
        Integer year = Integer.parseInt(DateUtil.getYear(DateUtil.toCurrentUTCTimeStamp()));
        long serialNumber = serialNumberService.getSerialNumber(rtaOfficeCode, SerialNumberType.PERMIT);
        return new StringBuilder(rtaOfficeCode).append("/").append(serialNumber).append("/").append(permitType).append("/").append(year).toString();
    }
    
    @Override
    @Transactional
    public PermitDetailsModel getTempPermitCertifcate(Long vehicleRcId, Long mviUserId){
        VehicleRCEntity vehicleRcEntity = vehicleDAO.get(vehicleRcId);
        if(ObjectsUtil.isNull(vehicleRcEntity)){
            logger.error("No pr found for vehicleRcId : " + vehicleRcId);
            return null;
        }
        String prNumber = vehicleRcEntity.getPrNumber();
        logger.info("get permit details by pr : " + prNumber);
        PermitDetailsModel model = new PermitDetailsModel();
        model.setIsTempPermit(true);
       
        PermitHeaderEntity entity = permitHeaderDAO.getTempPermitActiveByPr(prNumber);
        if (ObjectsUtil.isNull(entity)) {
            logger.info("There is not any temp permit regarding pr : " + prNumber);
            return model;
        }
        /* Age has already been provided via citizen services.
          if(OwnershipType.INDIVIDUAL.getId() == vehicleRcEntity.getOwnershipType()) {
            CustIndividualDetailsEntity custIndDetailsEntity = customerDAO.getByVehicleRcId(vehicleRcId);
            try {
                model.setAge(DateUtil.getCurrentAge(custIndDetailsEntity.getDateOfBirth()));
            } catch (Exception e) {
                logger.info("Can't fetch age from customer details : " + e.getMessage());
            }
        }*/
        model.setIssueDate(DateUtil.convertToRunningTimeStamp(entity.getIssueDate()));
        model.setValidFromDate(DateUtil.convertToRunningTimeStamp(entity.getValidFromDate()));
        model.setCode(entity.getPermitType());
        model.setPermitType(entity.getPermitType());
        model.setPermitNumber(entity.getPermitNo());
        model.setExpiryDate(DateUtil.convertToRunningTimeStamp(entity.getValidToDate()));
        model.setPurposeForVehicle(entity.getPurpose());
        RtaOfficeEntity rtaEntity = rtaOfficeDAO.getRtaOfficeById(entity.getRtaOfficeId());
        if(!ObjectsUtil.isNull(rtaEntity)){
            model.setRtaOfficeName(rtaEntity.getName());
        }
        model.setName(entity.getHolderNm());
        
        model.setDaysPerTrip(entity.getDaysPerTrip());
        model.setForwardJourneyDestination(entity.getForwardJourneyDestination());
        model.setForwardJourneyEnroute(entity.getForwardJourneyEnroute());
        model.setForwardJourneyStartingStation(entity.getForwardJourneyStartingStation());
        model.setNoOfTrips(entity.getNoOfTrips());
        model.setOtherStateNm(entity.getOtherStateNm());
        model.setReturnJourneyDestination(entity.getReturnJourneyDestination());
        model.setReturnJourneyEnroute(entity.getReturnJourneyEnroute());
        model.setReturnJourneyStartingStation(entity.getReturnJourneyStartingStation());
        
        PermitDetailsMappingEntity routEntityMapping = permitDetailsMappingDAO.getActiveDetails(entity.getPermitNo(), PermitDetailsType.ROUTE.getLabel());
        if (!ObjectsUtil.isNull(routEntityMapping)) {
            PermitRouteConditionsMasterEntity routEntity = permitRouteDAO.getPermitRoute(routEntityMapping.getDetailValue());
            if (ObjectsUtil.isNotNull(routEntity)) {
                model.setRouteAreaPermitCode(routEntity.getCode());
                model.setRouteAreaPermit(routEntity.getPerRouteDesc());
            }
        }
        PermitDetailsMappingEntity stateEntityMapping = permitDetailsMappingDAO.getActiveDetails(entity.getPermitNo(), PermitDetailsType.STATE.getLabel());
        if (!ObjectsUtil.isNull(stateEntityMapping)) {
            StateEntity state = stateDAO.getStateByCode(stateEntityMapping.getDetailValue());
            if(ObjectsUtil.isNotNull(state)){
                model.setStateUnionPermitCode(state.getCode());
                model.setStateUnionPermit(state.getName());
            }
        }
        PermitDetailsMappingEntity goodsEntityMapping = permitDetailsMappingDAO.getActiveDetails(entity.getPermitNo(), PermitDetailsType.GOODS.getLabel());
        if (!ObjectsUtil.isNull(goodsEntityMapping)) {
            PermitGoodsMasterEntity goodsEntity = permitGoodsDAO.getPermitGood(goodsEntityMapping.getDetailValue());
            if (ObjectsUtil.isNotNull(goodsEntity)) {
                model.setNatureGoodsCarriedCode(goodsEntity.getCode());
                model.setNatureGoodsCarried(goodsEntity.getPerGoodsDesc());
            }
        }
        PermitDetailsMappingEntity conditionsEntityMapping = permitDetailsMappingDAO.getActiveDetails(entity.getPermitNo(), PermitDetailsType.CONDITION.getLabel());
        if (!ObjectsUtil.isNull(conditionsEntityMapping)) {
            PermitConditionsEntity condEntity = permitConditionsDAO.getPermitCondition(conditionsEntityMapping.getDetailValue());
            if (ObjectsUtil.isNotNull(condEntity)) {
                model.setAnyOtherConditionsCode(condEntity.getCode());
                model.setAnyOtherConditions(condEntity.getPermitCondDesc());
            }
        }
        List<PermitDetailsMappingEntity> neighbouringDistricts = permitDetailsMappingDAO.getActiveDetailList(entity.getPermitNo(), PermitDetailsType.DISTRICT.getLabel());
        if (!ObjectsUtil.isNullOrEmpty(neighbouringDistricts)) {
            List<CodeNameModel> districtList = new ArrayList<>();
            for(PermitDetailsMappingEntity mapping : neighbouringDistricts){
                DistrictEntity district = districtDAO.getDistrictByCode(mapping.getDetailValue());
                if(ObjectsUtil.isNotNull(district)){
                    CodeNameModel nameCode = new CodeNameModel();
                    nameCode.setCode(district.getCode());
                    nameCode.setName(district.getName());
                    districtList.add(nameCode);
                }
            }
            model.setNeighbouringDistricts(districtList);
        }
        
        // ---- set RTO signature----------------------
        if(ObjectsUtil.isNotNull(mviUserId)){
            RTAEmployeeEntity rtaEmployee = rTAEmployeeDAO.getByUserId(mviUserId);
            if(ObjectsUtil.isNotNull(rtaEmployee)){
                String fileName = rtaEmployee.getSignFileName();
                String signatureFile = baseURL + File.separator + signaturePath + File.separator + fileName;
                model.setRtoSignFilePath(signatureFile);
            } else {
                logger.error("Error Signature not found for User Id : " + mviUserId);
            }
        } else {
            try {
                String fileName = applicationService.getRtoSignFileName(rtaEntity.getCode());
                String signatureFile = baseURL + File.separator + signaturePath + File.separator + fileName;
                model.setRtoSignFilePath(signatureFile);
            } catch (Exception ex) {
                logger.error("Exception While reading signature in permit auth card ......");
                logger.debug("Exception : ", ex);
            }
        }
        return model;
    }

}
