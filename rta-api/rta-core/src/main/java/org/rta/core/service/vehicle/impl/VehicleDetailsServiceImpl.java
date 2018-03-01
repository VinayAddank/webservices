package org.rta.core.service.vehicle.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.rta.core.constant.SomeConstants;
import org.rta.core.dao.docs.AttachmentDetailsDAO;
import org.rta.core.dao.master.TaxTypeDAO;
import org.rta.core.dao.vehicle.VahanDAO;
import org.rta.core.dao.vehicle.VehicleAlterationDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.dao.vehicle.VehicleDetailsHistoryDAO;
import org.rta.core.dao.vehicle.VehicleRCChangeHistoryDAO;
import org.rta.core.dao.vehicle.cms.VehicleClassDescDAO;
import org.rta.core.entity.vehicle.VahanEntity;
import org.rta.core.entity.vehicle.VehicleAlterationEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.AlterationCategory;
import org.rta.core.enums.PRType;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.enums.master.TaxType;
import org.rta.core.exception.DataMismatchException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.UserActionModel;
import org.rta.core.model.VehicleReassignmentModel;
import org.rta.core.model.citizen.CitizenServiceResponseModel;
import org.rta.core.model.payment.tax.DifferentialTaxFeeModel;
import org.rta.core.model.vehicle.SeatingCovValueModel;
import org.rta.core.model.vehicle.UnregisteredVehicleModel;
import org.rta.core.model.vehicle.VehicleAlterationUpdateModel;
import org.rta.core.model.vehicle.VehicleBodyModel;
import org.rta.core.model.vehicle.VehicleDetailsModel;
import org.rta.core.model.vehicle.VehicleDetailsRequestModel;
import org.rta.core.service.application.ApplicationService;
import org.rta.core.service.citizen.CitizenService;
import org.rta.core.service.citizen.ResponseModel;
import org.rta.core.service.docs.DocPermissionService;
import org.rta.core.service.payment.tax.TaxDetailService;
import org.rta.core.service.vehicle.VehicleDetailsService;
import org.rta.core.service.vehicle.VehicleService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleDetailsServiceImpl implements VehicleDetailsService {

	private static final Logger logger = Logger.getLogger(VehicleDetailsServiceImpl.class);

    private final ConversionService conversionService;
    private final VehicleDetailsDAO vehicleDetailsDao;
    private final VehicleClassDescDAO vehicleClassDescDAO;
    private final VahanDAO vahanDAO;
    private final AttachmentDetailsDAO attachmentDetailsDAO;
    private final DocPermissionService docPermissionService;
    private final VehicleDAO vehicleDAO;
    private final VehicleAlterationDAO vehicleAlterationDAO;
    private final CitizenService citizenService;
    private final VehicleDetailsHistoryDAO vehicleDetailsHistoryDAO;
    private final TaxDetailService taxDetailService;
    private final VehicleRCChangeHistoryDAO vehicleRCChangeHistoryDAO;
    
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private TaxTypeDAO taxTypeDAO;
    
    @Autowired
    private VehicleService vehicleService;
    
    @Autowired
    public VehicleDetailsServiceImpl(final VehicleDetailsDAO vehicleDetailsDao,
            final ConversionService conversionService, final VehicleClassDescDAO vehicleClassDescDAO,
            final VehicleDAO vehicleDAO, final VahanDAO vahanDAO, final AttachmentDetailsDAO attachmentDetailsDAO,
            final DocPermissionService docPermissionService, final VehicleAlterationDAO vehicleAlterationDAO,
            final CitizenService citizenService, final VehicleDetailsHistoryDAO vehicleDetailsHistoryDAO,
            final TaxDetailService taxDetailService, final VehicleRCChangeHistoryDAO vehicleRCChangeHistoryDAO) {
        this.vehicleDetailsDao = vehicleDetailsDao;
        this.conversionService = conversionService;
        this.vehicleClassDescDAO = vehicleClassDescDAO;
        this.vahanDAO = vahanDAO;
        this.attachmentDetailsDAO = attachmentDetailsDAO;
        this.docPermissionService = docPermissionService;
        this.vehicleDAO = vehicleDAO;
        this.vehicleAlterationDAO = vehicleAlterationDAO;
        this.citizenService = citizenService;
        this.vehicleDetailsHistoryDAO = vehicleDetailsHistoryDAO;
        this.taxDetailService = taxDetailService;
        this.vehicleRCChangeHistoryDAO = vehicleRCChangeHistoryDAO;
    }

    @Override
    public UnregisteredVehicleModel get(Long vehicleRcId) {
        VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicleRcId);
        return conversionService.convert(vde, UnregisteredVehicleModel.class);
    }

    @Override
    public String getMakersClass(Long vehicleRcId) {

        return vehicleDetailsDao.getMakersClass(vehicleRcId);
    }

    @Transactional
    @Override
    public VehicleDetailsModel getVehicleDetails(Long vehicleRcId) throws VehicleRcNotFoundException {

        VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicleRcId);
        if (ObjectsUtil.isNull(vde)) {
            throw new VehicleRcNotFoundException();
        }
        VahanEntity vahanEntity = vahanDAO.getByChassisNumber(vde.getChassisNo());
        if (ObjectsUtil.isNull(vahanEntity)) {
            throw new IllegalArgumentException("chassis number is invalid");
        }
        if (ObjectsUtil.isNull(vde)) {
            throw new VehicleRcNotFoundException();
        }
        VehicleDetailsModel model = new VehicleDetailsModel();
        UnregisteredVehicleModel uvm = conversionService.convert(vde, UnregisteredVehicleModel.class);
        
        uvm.setVahanId(vahanEntity.getId());
		if(ObjectsUtil.isNull(uvm.getBodyTypeDesc())){
			uvm.setBodyTypeDesc(vahanEntity.getBodyTypeDesc());
		}
		if(ObjectsUtil.isNull(uvm.getEnginePower())){
			uvm.setEnginePower(vahanEntity.getEnginePower());
		}
		if(ObjectsUtil.isNull(uvm.getFrontAxleDesc())){
			uvm.setFrontAxleDesc(vahanEntity.getFrontAxleDesc());
		}
		if(ObjectsUtil.isNull(uvm.getFrontAxleWeight())){
			uvm.setFrontAxleWeight(vahanEntity.getFrontAxleWeight());
		}
		if(ObjectsUtil.isNull(uvm.getHeight())){
			uvm.setHeight(vahanEntity.getHeight());
		}
		if(ObjectsUtil.isNull(uvm.getLength())){
			uvm.setLength(vahanEntity.getLength());
		}
		if(ObjectsUtil.isNull(uvm.getNoCyl())){
			uvm.setNoCyl(vahanEntity.getNoCyl());
		}
		if(ObjectsUtil.isNull(uvm.getO1AxleDesc())){
			uvm.setO1AxleDesc(vahanEntity.getO1AxleDesc());
		}
		if(ObjectsUtil.isNull(uvm.getO1AxleWeight())){
			uvm.setO1AxleWeight(vahanEntity.getO1AxleWeight());
		}
		if(ObjectsUtil.isNull(uvm.getO2AxleDesc())){
			uvm.setO2AxleDesc(vahanEntity.getO2AxleDesc());
		}
		if(ObjectsUtil.isNull(uvm.getO2AxleWeight())){
			uvm.setO2AxleWeight(vahanEntity.getO2AxleWeight());
		}
		if(ObjectsUtil.isNull(uvm.getO3AxleDesc())){
			uvm.setO3AxleDesc(vahanEntity.getO3AxleDesc());
		}
		if(ObjectsUtil.isNull(uvm.getO3AxleWeight())){
			uvm.setO3AxleWeight(vahanEntity.getO3AxleWeight());
		}
		if(ObjectsUtil.isNull(uvm.getO4AxleDesc())){
			uvm.setO4AxleDesc(vahanEntity.getO4AxleDesc());
		}
		if(ObjectsUtil.isNull(uvm.getO4AxleWeight())){
			uvm.setO4AxleWeight(vahanEntity.getO4AxleWeight());
		}
		if(ObjectsUtil.isNull(uvm.getO5AxleDesc())){
			uvm.setO5AxleDesc(vahanEntity.getO5AxleDesc());
		}
		if(ObjectsUtil.isNull(uvm.getO5AxleWeight())){
			uvm.setO5AxleWeight(vahanEntity.getO5AxleWeight());
		}
		if(ObjectsUtil.isNull(uvm.getPollutionNormsDesc())){
			uvm.setPollutionNormsDesc(vahanEntity.getPollutionNormsDesc());
		}
		if(ObjectsUtil.isNull(uvm.getRearAxleDesc())){
			uvm.setRearAxleDesc(vahanEntity.getRearAxleDesc());
		}
		if(ObjectsUtil.isNull(uvm.getRearAxleWeight())){
			uvm.setRearAxleWeight(vahanEntity.getRearAxleWeight());
		}
		if(ObjectsUtil.isNull(uvm.getRlw())){
			uvm.setRlw(vahanEntity.getRlw());
		}
		if(ObjectsUtil.isNull(uvm.getStandCapacity())){
			uvm.setStandCapacity(vahanEntity.getStandCapacity());
		}
		if(ObjectsUtil.isNull(uvm.getTandemAxelDescp())){
			uvm.setTandemAxelDescp(vahanEntity.getTandemAxelDescp());
		}
		if(ObjectsUtil.isNull(uvm.getTandemAxelWeight())){
			uvm.setTandemAxelWeight(vahanEntity.getTandemAxelWeight());
		}
		if(ObjectsUtil.isNull(uvm.getCubicCapacity())){
			uvm.setCubicCapacity(vahanEntity.getCubicCap());
		}
		if(ObjectsUtil.isNull(uvm.getUlw())){
			uvm.setUlw(vahanEntity.getUlw());
		}
		if(ObjectsUtil.isNull(uvm.getWheelbase())){
			uvm.setWheelbase(vahanEntity.getWheelbase());
		}
		if(ObjectsUtil.isNull(uvm.getWidth())){
			uvm.setWidth(vahanEntity.getWidth());
		}
		uvm.setManufacturingMonthYear(vde.getMfgDate());
		uvm.setVehicleSubClass(vde.getVehicleSubClass());
        
        if (!StringsUtil.isNullOrEmpty(vde.getVehicleSubClass())) {
            uvm.setVehicleClassDes(
                    vehicleClassDescDAO.getByVehiclClassDesCode(vde.getVehicleSubClass()).getDescription());
        }
        model.setVehicleDetails(uvm);
        model.setRegistrationCategory(vde.getVehicleRcId().getRegCategory().getCode());
        model.setVehicleRcId(vde.getVehicleRcId().getVehicleRcId());
        model.setPrNumber(vde.getVehicleRcId().getPrNumber());
        model.setVehicleCategory(vde.getVehicleCategory());
        logger.info(":::::::::::::::::getVehicleDetails::::::::::::End::::::::");
        return model;
    }

    @Transactional
	@Override
	public SaveUpdateResponse saveOrUpdateVehicleAlteration(VehicleBodyModel model, String userName, UserType userType,
			Boolean isOldVehicle, String token)
			throws DataIntegrityViolationException, InvalidDataAccessApiUsageException, DataMismatchException {

		VehicleRCEntity vehicleRCEntity = vehicleDAO.get(model.getVehicleRcId());
		if (!isOldVehicle) {
			if (!(model.getChassisNumber().equals(vehicleRCEntity.getChassisNumber()))) {
				logger.error(":::::::::getVehicleDetails:::::::::Exception::::: Invalid chessisNumber or vehicleRcId");
				throw new InvalidDataAccessApiUsageException("Invalid chessisNumber or vehicleRcId");
			}
		}
		
		VehicleDetailsRequestModel vehicleModel = null;
		SaveUpdateResponse response = new SaveUpdateResponse();
		try {
			vehicleModel = vehicleService.getVehicleDetails(model.getVehicleRcId(), userType);
			boolean isValidSeatingCapacityByCov = isCovAlterationValidation(model, vehicleModel);
			logger.info("validate seating with cov:" +isValidSeatingCapacityByCov);
			if (!isValidSeatingCapacityByCov) {
				String msg = "Seating capacity with this class of vehicle can not be altered.";
				response.setStatus(ResponseModel.FAILED);
				response.setMessage(msg);
				return response;
			}
		} catch (VehicleRcNotFoundException e) {
			logger.error("vehicleRc not found : " + model.getVehicleRcId());
			throw new IllegalArgumentException("vehicleRc not found!");
		}

		Integer maxRequestIdValue = 0;
		if (userType == UserType.ROLE_CITIZEN) {
			try {
				maxRequestIdValue = vehicleAlterationDAO.getMaxRequestIdValueByRcId(model.getVehicleRcId());
				if (maxRequestIdValue == null)
					maxRequestIdValue = 0;
			} catch (Exception e) {
				logger.error(":::::::::getVehicleAlteration max request ID:::::::::::::::");
			}
			maxRequestIdValue = maxRequestIdValue + SomeConstants.ONE;
		}
		for (AlterationCategory alterationCategory : model.getAlterationCategory()) {
			VehicleAlterationEntity entity = vehicleAlterationDAO.getVehicleAlterationDetailByAlterCat(
					model.getVehicleRcId(), alterationCategory.getValue(), Status.FRESH);
			if (ObjectsUtil.isNull(entity)) {
				entity = new VehicleAlterationEntity();
				entity.setVehicleRcId(vehicleDAO.get(model.getVehicleRcId()));
				entity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
				entity.setCreatedBy(userName);
			}
			entity.setRequestId(maxRequestIdValue);
			entity.setLengthUpdated(model.getLengthUpdated());
			entity.setHeightUpdated(model.getHeightUpdated());
			entity.setWidthUpdated(model.getWidthUpdated());
			entity.setCompletionDate(model.getCompletionDate());
			entity.setVehicleSubClass(model.getVehicleSubClass());
			entity.setUlwUpdated(model.getUlwUpdated());
			// entity.setRlwUpdated(model.getRlwUpdated());
			entity.setColorUpdated(model.getColorUpdated());
			entity.setApplicationNumber(model.getApplicationNumber());
			if (alterationCategory == AlterationCategory.SEATING_CAPACITY) {
				entity.setSeatingCapacity(model.getSeatingCapacity());
			} else if (alterationCategory == AlterationCategory.BODY_TYPE) {
				entity.setBodyTypeUpdated(model.getBodyTypeUpdated());
			} else if (alterationCategory == AlterationCategory.NEW_VEHICLE_ALTERATION) {
				entity.setSeatingCapacity(model.getSeatingCapacity());
				entity.setBodyTypeUpdated(model.getBodyTypeUpdated());
			}
			entity.setAlterationCategory(alterationCategory.getValue());
			entity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			entity.setModifiedBy(userName);
			if (userType == UserType.ROLE_BODY_BUILDER) {
				entity.setStatus(Status.DIFFERENTIAL_TAX.getValue());
			} else if (userType == UserType.ROLE_CITIZEN && (alterationCategory == AlterationCategory.VEHICLE_TYPE
					|| alterationCategory == AlterationCategory.ENGINE_ALTERNATION
					|| alterationCategory == AlterationCategory.FUEL_TYPE)) {
				entity.setStatus(Status.DIFFERENTIAL_TAX.getValue());
			} else {
				entity.setStatus(Status.FRESH.getValue());
			}

			vehicleAlterationDAO.saveOrUpdate(entity);
		}
		if (isOldVehicle && userType == UserType.ROLE_BODY_BUILDER) {
			CitizenServiceResponseModel<String> res = citizenService
					.saveOrUpdateByBodyBuilder(model.getApplicationNumber(), token);
			if (res.getHttpStatus() == HttpStatus.OK) {
				logger.info(
						"Data sync with Activiti by body builder:: application number " + model.getApplicationNumber());
			} else {
				logger.error("getting error in data sync with Activiti by body builder:: application number "
						+ model.getApplicationNumber());
				throw new DataMismatchException();
			}
		}
		if (!isOldVehicle) {
			if (vehicleRCEntity.getPrStatus() != Status.APPROVED.getValue()) {
				Boolean isNextStep = attachmentDetailsDAO.getDocTypesIds(vehicleRCEntity.getVehicleRcId(), userType)
						.containsAll(docPermissionService.getMandatoryDocTypesId(vehicleRCEntity.getVehicleRcId(),
								userType));
				if (isNextStep) {
					vehicleRCEntity.setModifiedBy(userName);
					vehicleRCEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
					vehicleRCEntity.setProcessStatus(Status.DIFFERENTIAL_TAX.getValue());
					vehicleDAO.update(vehicleRCEntity);
				} else {
					logger.error(
							"Some documents are missing.... for VehicleRcId : " + vehicleRCEntity.getVehicleRcId());
					throw new DataMismatchException(
							"Invalid chessisNumber or vehicleRcId " + String.valueOf(model.getVehicleRcId()));
				}
			}
		}
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Vehicle Alteration details update Successfully !!",
				String.valueOf(model.getVehicleRcId()));
	}

	@Transactional
	@Override
	public SaveUpdateResponse saveOrUpdateVehicleAlteration(VehicleAlterationUpdateModel alterationUpdateModel, String userName)
			throws DataIntegrityViolationException, InvalidDataAccessApiUsageException {
		VehicleBodyModel model = alterationUpdateModel.getVehicleBodyModel();
		DifferentialTaxFeeModel differentialTaxFeeModel = alterationUpdateModel.getDifferentialTaxFeeModel();
		VehicleDetailsEntity veEntity = vehicleDetailsDao.getByVehicleRcId(model.getVehicleRcId());
		try{
			vehicleDetailsHistoryDAO.saveData(veEntity.getVehicleDtlId(), userName, DateUtil.toCurrentUTCTimeStamp(), ServiceType.VEHICLE_ATLERATION);
		}catch (Exception e) {
			logger.error("Exception while saving vehicle details history");
		}
		String previousRegCat = veEntity.getVehicleRcId().getRegCategory().getCode();
		String prNumber = veEntity.getVehicleRcId().getPrNumber();
		String  alterationCategoryStr = ""; 
		for (AlterationCategory alterationCategory : model.getAlterationCategory()) {
			if (alterationCategory == AlterationCategory.BODY_TYPE
					|| alterationCategory == AlterationCategory.SEATING_CAPACITY
					|| alterationCategory == AlterationCategory.NEW_VEHICLE_ALTERATION ) {
				VehicleAlterationEntity entity = vehicleAlterationDAO.getVehicleAlterationDetailByAlterCat(model.getVehicleRcId(), alterationCategory.getValue(), Status.DIFFERENTIAL_TAX);
				if(ObjectsUtil.isNotNull(entity.getLengthUpdated())){
					veEntity.setLengthUpdated(entity.getLengthUpdated());
				}
				if(ObjectsUtil.isNotNull(entity.getHeightUpdated())){
					veEntity.setHeightUpdated(entity.getHeightUpdated());
				}
				if(ObjectsUtil.isNotNull(entity.getWidthUpdated())){
					veEntity.setWidthUpdated(entity.getWidthUpdated());
				}
				if(ObjectsUtil.isNotNull(entity.getCompletionDate())){
					veEntity.setCompletionDate(entity.getCompletionDate());
				}
				if(ObjectsUtil.isNotNull(entity.getVehicleSubClass())){
					veEntity.setVehicleSubClass(entity.getVehicleSubClass());
				}
				if(ObjectsUtil.isNotNull(entity.getUlwUpdated())){
					veEntity.setUlw(entity.getUlwUpdated());
				}
				if(ObjectsUtil.isNotNull(entity.getColorUpdated())){
					veEntity.setColor(entity.getColorUpdated());
				}
				if (alterationCategory == AlterationCategory.SEATING_CAPACITY) {
					veEntity.setSeatingCapacity(entity.getSeatingCapacity());
				} else if (alterationCategory == AlterationCategory.BODY_TYPE) {
					veEntity.setBodyTypeUpdated(entity.getBodyTypeUpdated());
				} else if(alterationCategory == AlterationCategory.NEW_VEHICLE_ALTERATION){
					veEntity.setSeatingCapacity(entity.getSeatingCapacity());
					veEntity.setBodyTypeUpdated(entity.getBodyTypeUpdated());	
				}
			} else if (alterationCategory == AlterationCategory.VEHICLE_TYPE) {
				veEntity.setVehicleSubClass(model.getVehicleSubClass());
				if (model.getPrType() == PRType.ORDINARY) {
					veEntity.getVehicleRcId().setPrType(PRType.ORDINARY.getId());
				}
				if (model.getPrType() == PRType.SPECIAL) {
					veEntity.getVehicleRcId().setPrType(PRType.SPECIAL.getId());
				}
			} else if (alterationCategory == AlterationCategory.FUEL_TYPE) {
				veEntity.setFuelUsed(model.getFuelType());
				veEntity.setGasKitNumber(model.getGasKitNumber());
			} else if (alterationCategory == AlterationCategory.ENGINE_ALTERNATION) {
				veEntity.setEngineNo(model.getEngineNo());
			}
			veEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			veEntity.setModifiedBy(userName);
			alterationCategoryStr = alterationCategoryStr + alterationCategory.getValue();
		}
		TaxType newTaxType = TaxType.getTaxTypeByCode(differentialTaxFeeModel.getTaxType());
		if(ObjectsUtil.isNotNull(newTaxType)){
			veEntity.setTaxTypeId(taxTypeDAO.getByCode(differentialTaxFeeModel.getTaxType()));
		}
		veEntity.setAlterationCategory(Integer.parseInt(alterationCategoryStr));
		String result = taxDetailService.saveOrUpdateDifferentialTaxFeeDetails(differentialTaxFeeModel, model.getVehicleRcId(), userName, alterationUpdateModel.getVehicleBodyModel().getVehicleSubClass());
		logger.info("Differential Tax Fee :::::::::: "+result);
		vehicleDetailsDao.update(veEntity);
		vehicleAlterationDAO.updateStatus(model.getVehicleRcId(), Status.APP_COMPLETED);
		if ((null != model.getRegistrationCategoryCode()) && (null != previousRegCat) 
				&& !previousRegCat.equals(model.getRegistrationCategoryCode())) {
			try {
				vehicleRCChangeHistoryDAO.saveData(model.getVehicleRcId(), userName, DateUtil.toCurrentUTCTimeStamp(),
						ServiceType.VEHICLE_ATLERATION);
			} catch (Exception e) {

			}
			VehicleReassignmentModel reassignmentModel = new VehicleReassignmentModel();
			reassignmentModel.setApplicationNo(model.getApplicationNumber());
			reassignmentModel.setPrType(model.getPrType());
			reassignmentModel.setPrNumber(prNumber);
			reassignmentModel.setSpecialPrNo(model.getSpecialPrNo());
			reassignmentModel.setRegCategoryCode(model.getRegistrationCategoryCode());
			reassignmentModel.setServiceCode(ServiceType.VEHICLE_ATLERATION.getCode());
			reassignmentModel.setCov(model.getVehicleSubClass());
			for (UserActionModel actionModel : alterationUpdateModel.getUserActionModel()) {
				if (UserType.ROLE_MVI == actionModel.getUserType()) {
					reassignmentModel.setUserActionModel(actionModel);
					break;
				}
			}
			try {
				applicationService.generateReassignmentVehicle(reassignmentModel);
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}
	
		vehicleService.updateRcApproverUserId(alterationUpdateModel.getUserActionModel(), model.getVehicleRcId());
		
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Vehicle details update Successfully !!",
				String.valueOf(model.getVehicleRcId()));
	}

	@Transactional
	@Override
	public SaveUpdateResponse vehicleAlterationSyncWithVehicleDetails(Long vehicleRcId, String userName, String token) {
		try{
			DifferentialTaxFeeModel differentialTaxFeeModel = null;
			String alterationCategory = "";
			VehicleDetailsEntity veEntity = vehicleDetailsDao.getByVehicleRcId(vehicleRcId);
			
			List<VehicleAlterationEntity> alterationEntities = vehicleAlterationDAO.getVehicleAlterationDetails(vehicleRcId, Status.DIFFERENTIAL_TAX);
			if(ObjectsUtil.isNull(alterationEntities) || alterationEntities.size() == SomeConstants.ZERO){
				return new SaveUpdateResponse("NO Need to alteration Vehicle details syncing");
			}
			/*try{
				vehicleDetailsHistoryDAO.saveData(veEntity.getVehicleDtlId(), userName, DateUtil.toCurrentUTCTimeStamp(), ServiceType.VEHICLE_ATLERATION);
			}catch (Exception e) {
				
			}*/
			VehicleRCEntity vehicleRCEntity= veEntity.getVehicleRcId();
			CitizenServiceResponseModel<DifferentialTaxFeeModel> response = citizenService.getDiffertialTaxFeeDetails(vehicleRCEntity.getTrNumber(), token);
			if(response.getHttpStatus() == HttpStatus.OK){
				differentialTaxFeeModel = response.getResponseBody();
				String result = taxDetailService.saveOrUpdateDifferentialTaxFeeDetails(differentialTaxFeeModel, vehicleRcId, userName, veEntity.getVehicleSubClass());
				TaxType newTaxType = TaxType.getTaxType(differentialTaxFeeModel.getTaxType());
				if(ObjectsUtil.isNotNull(newTaxType)){
					veEntity.setTaxTypeId(taxTypeDAO.getByCode(differentialTaxFeeModel.getTaxType()));
				}
				logger.info("Differential Tax Fee :::::::::: "+result);
			}			
			for(VehicleAlterationEntity alterationEntity : alterationEntities ){
				if(ObjectsUtil.isNotNull(alterationEntity.getLengthUpdated())){
					veEntity.setLengthUpdated(alterationEntity.getLengthUpdated());
				}
				if(ObjectsUtil.isNotNull(alterationEntity.getHeightUpdated())){
					veEntity.setHeightUpdated(alterationEntity.getHeightUpdated());
				}
				if(ObjectsUtil.isNotNull(alterationEntity.getWidthUpdated())){
					veEntity.setWidthUpdated(alterationEntity.getWidthUpdated());
				}
				if(ObjectsUtil.isNotNull(alterationEntity.getCompletionDate())){
					veEntity.setCompletionDate(alterationEntity.getCompletionDate());
				}
				if(ObjectsUtil.isNotNull(alterationEntity.getVehicleSubClass())){
					veEntity.setVehicleSubClass(alterationEntity.getVehicleSubClass());
				}
				if(ObjectsUtil.isNotNull(alterationEntity.getColorUpdated())){
					veEntity.setColor(alterationEntity.getColorUpdated());
				}
				if(ObjectsUtil.isNotNull(alterationEntity.getUlwUpdated())){
					veEntity.setUlw(alterationEntity.getUlwUpdated());
				}
				//veEntity.setRlw(alterationEntity.getRlwUpdated());
				if (alterationEntity.getAlterationCategory() == AlterationCategory.SEATING_CAPACITY.getValue()) {
					veEntity.setSeatingCapacity(alterationEntity.getSeatingCapacity());
				} else if (alterationEntity.getAlterationCategory() == AlterationCategory.BODY_TYPE.getValue()) {
					veEntity.setBodyTypeUpdated(alterationEntity.getBodyTypeUpdated());
				} else if (alterationEntity.getAlterationCategory() == AlterationCategory.NEW_VEHICLE_ALTERATION.getValue()){
					veEntity.setBodyTypeUpdated(alterationEntity.getBodyTypeUpdated());
					veEntity.setSeatingCapacity(alterationEntity.getSeatingCapacity());
				}
				alterationCategory = alterationCategory + alterationEntity.getAlterationCategory();
			}
			veEntity.setAlterationCategory(Integer.parseInt(alterationCategory));
			veEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			veEntity.setModifiedBy(userName);
			vehicleDetailsDao.update(veEntity);
			vehicleAlterationDAO.updateStatus(vehicleRcId, Status.APP_COMPLETED);
			
			return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
	}
	
    @Transactional
    @Override
    public VehicleBodyModel getVehicleAlterationDetails(Long vehicleRcId, UserType userType) throws VehicleRcNotFoundException {
    	
		List<VehicleAlterationEntity> alterationEntitieList = null;
		if( userType == UserType.ROLE_BODY_BUILDER || userType == UserType.ROLE_ONLINE_FINANCER){
			List<Integer>  list = new ArrayList<>();
			list.add(AlterationCategory.SEATING_CAPACITY.getValue());
			list.add(AlterationCategory.BODY_TYPE.getValue());
			list.add(AlterationCategory.NEW_VEHICLE_ALTERATION.getValue());
			alterationEntitieList = vehicleAlterationDAO.getVehicleAlterationDetails(vehicleRcId, Status.FRESH, list);
		}else{
			alterationEntitieList = vehicleAlterationDAO.getVehicleAlterationDetails(vehicleRcId, Status.DIFFERENTIAL_TAX);	
		}
		VehicleAlterationEntity alterationEntity=null;
		if (alterationEntitieList.size()==0) {
			throw new VehicleRcNotFoundException("Alteration of Vehicle Not Found");
		}
		ArrayList<AlterationCategory> alterationCategorieList = new ArrayList<AlterationCategory>();
		VehicleBodyModel model = new VehicleBodyModel();
		for (VehicleAlterationEntity entity : alterationEntitieList) {
			alterationEntity = entity;
			alterationCategorieList.add(AlterationCategory.getAlterationCategory(entity.getAlterationCategory()));
			if (entity.getAlterationCategory() == AlterationCategory.SEATING_CAPACITY.getValue()) {
				model.setCompletionDate(entity.getCompletionDate());
				model.setSeatingCapacity(entity.getSeatingCapacity());
			} else if (entity.getAlterationCategory() == AlterationCategory.BODY_TYPE.getValue()) {
				if (ObjectsUtil.isNull(model.getBodyTypeUpdated())) {
					model.setBodyTypeUpdated(entity.getBodyTypeUpdated());
				}
				model.setCompletionDate(entity.getCompletionDate());
			}else if( entity.getAlterationCategory() == AlterationCategory.NEW_VEHICLE_ALTERATION.getValue()){
				if (ObjectsUtil.isNull(model.getSeatingCapacity())) {
					model.setSeatingCapacity(entity.getSeatingCapacity());
				}
				if (ObjectsUtil.isNull(model.getBodyTypeUpdated())) {
					model.setBodyTypeUpdated(entity.getBodyTypeUpdated());
				}
				model.setCompletionDate(entity.getCompletionDate());
			}
			if (ObjectsUtil.isNull(model.getLengthUpdated())) {
				model.setLengthUpdated(entity.getLengthUpdated());
			}
			if (ObjectsUtil.isNull(model.getHeightUpdated())) {
				model.setHeightUpdated(entity.getHeightUpdated());
			}
			if (ObjectsUtil.isNull(model.getWidthUpdated())) {
				model.setWidthUpdated(entity.getWidthUpdated());
			}
			if(ObjectsUtil.isNull(model.getVehicleSubClass())){
				model.setVehicleSubClass(entity.getVehicleSubClass());
			}
			if (ObjectsUtil.isNull(model.getUlwUpdated())) {
				model.setUlwUpdated(entity.getUlwUpdated());
			}
//			if (ObjectsUtil.isNull(model.getRlwUpdated())) {
//				model.setRlwUpdated(entity.getRlwUpdated());
//			}
			if (ObjectsUtil.isNull(model.getColorUpdated())) {
				model.setColorUpdated(entity.getColorUpdated());
			}
		}
		model.setAlterationCategory(alterationCategorieList);
		model.setChassisNumber(alterationEntity.getVehicleRcId().getChassisNumber());
		if(ObjectsUtil.isNull(model.getVehicleSubClass())){
			VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicleRcId);
			model.setVehicleSubClass(vde.getVehicleSubClass());
		}
		if (!StringsUtil.isNullOrEmpty(model.getVehicleSubClass())) {
			model.setVehicleSubClassDecs(
					vehicleClassDescDAO.getByVehiclClassDesCode(model.getVehicleSubClass()).getDescription());
		}
		return model;
	}

    /* This is temporary code for logic of seating capacity until complete requirement */
    @Transactional
    @Override
    public Integer getMinimumSeatingCapacity(Long vehicleRcId) {

        Integer wheelbase = null;

        VehicleDetailsEntity entity = vehicleDetailsDao.getByVehicleRcId(vehicleRcId);
        if (ObjectsUtil.isNull(entity)) {

            throw new IllegalArgumentException("Invalid vehicleRcId....!!!");
        }
        wheelbase = entity.getWheelbase();
        if (ObjectsUtil.isNull(wheelbase)) {
            throw new IllegalArgumentException("Invalid wheelbase....!!!");
        }
        wheelbase = wheelbase / 10;

        if(wheelbase >= 254 && wheelbase <= 293){
            return 16;
        }

        else if(wheelbase >= 294 && wheelbase <= 305){
            return 20;
        }

        else if (wheelbase >= 306 && wheelbase <= 343) {
            return 25;
        }

        else if (wheelbase >= 344 && wheelbase <= 407) {
            return 30;
        }

        else if (wheelbase >= 408 && wheelbase <= 432) {
            return 35;
        }

        else if (wheelbase >= 433 && wheelbase <= 496) {
            return 45;
        }

        else if (wheelbase >= 497 && wheelbase <= 534) {
            return 50;
        }
        
        else if (wheelbase >= 535) {
            return 55;
        }

        return null;
    }
    
    // this method is used to validate the seating capacity with cov.
    // this method is also available in citizen end.
    private boolean isCovAlterationValidation(VehicleBodyModel bodyModel,
			VehicleDetailsRequestModel vehicleDetailsModel) {
		boolean isValid = true;
		List<AlterationCategory> alterationCategorieList = bodyModel.getAlterationCategory();
		if (alterationCategorieList.contains(AlterationCategory.SEATING_CAPACITY)
				|| alterationCategorieList.contains(AlterationCategory.VEHICLE_TYPE)) {
			if (!ObjectsUtil.isNull(bodyModel.getSeatingCapacity())) {
				if (!ObjectsUtil.isNull(bodyModel.getVehicleSubClass())) {
					isValid = isSeatingCapacityExits(bodyModel.getVehicleSubClass(), bodyModel.getSeatingCapacity());
				} else {
					isValid = isSeatingCapacityExits(vehicleDetailsModel.getVehicle().getVehicleSubClass(),
							bodyModel.getSeatingCapacity());
				}
			}else{
				if(!ObjectsUtil.isNull(bodyModel.getVehicleSubClass())){
				isValid = isSeatingCapacityExits(bodyModel.getVehicleSubClass(),
						vehicleDetailsModel.getVehicle().getSeatingCapacity());
				}
			}
		}
		return isValid;
	}

	private boolean isSeatingCapacityExits(String cov, int seatingCapacity) {
		switch (cov) {
		case "MAXT":
			if (seatingCapacity >= 8 && seatingCapacity <= 13)
				return true;
			break;
		case "OBPN":
			if (seatingCapacity >= 8 && seatingCapacity <= 13)
				return true;
			break;
		case "MCRN":
			if (seatingCapacity >= 2 && seatingCapacity <= 7)
				return true;
			break;
		case "MTLT":
			if (seatingCapacity >= 4 && seatingCapacity <= 7)
				return true;
			break;
		case "LTCT":
			if (seatingCapacity >= 4 && seatingCapacity <= 7)
				return true;
			break;
		case "PSVT":
			if (seatingCapacity >= 8 && seatingCapacity <= 99)
				return true;
			break;
		case "OBT":
			if (seatingCapacity >= 14 && seatingCapacity <= 99)
				return true;
			break;
		case "COCT":
			if (seatingCapacity >= 14 && seatingCapacity <= 99)
				return true;
			break;
		case "SCRT":
			if (seatingCapacity >= 14 && seatingCapacity <= 99)
				return true;
			break;
		case "TOVT":
			if (seatingCapacity >= 14 && seatingCapacity <= 99)
				return true;
			break;
		case "STCT":
			if (seatingCapacity >= 4 && seatingCapacity <= 7)
				return true;
			break;
		default:
			return true;
		}
		return false;
	}

	@Override
	public SeatingCovValueModel getSeatingCovValidateValues(String cov) {
		SeatingCovValueModel covValueModel = new SeatingCovValueModel();
		covValueModel.setValid(false);
		covValueModel.setMinSeat(1);
		covValueModel.setMaxSeat(60);
		covValueModel.setVehicleSubClass(cov);
		switch (cov) {
		case "MAXT":
			covValueModel.setValid(true);
			covValueModel.setMinSeat(8);
			covValueModel.setMaxSeat(13);
			return covValueModel;
		case "OBPN":
			covValueModel.setValid(true);
			covValueModel.setMinSeat(8);
			covValueModel.setMaxSeat(13);
			return covValueModel;
		case "MCRN":
			covValueModel.setValid(true);
			covValueModel.setMinSeat(2);
			covValueModel.setMaxSeat(7);
			return covValueModel;
		case "MTLT":
			covValueModel.setValid(true);
			covValueModel.setMinSeat(4);
			covValueModel.setMaxSeat(7);
			return covValueModel;
		case "LTCT":
			covValueModel.setValid(true);
			covValueModel.setMinSeat(4);
			covValueModel.setMaxSeat(7);
			return covValueModel;
		case "PSVT":
			covValueModel.setValid(true);
			covValueModel.setMinSeat(8);
			covValueModel.setMaxSeat(99);
			return covValueModel;
		case "OBT":
			covValueModel.setValid(true);
			covValueModel.setMinSeat(14);
			covValueModel.setMaxSeat(99);
			return covValueModel;
		case "COCT":
			covValueModel.setValid(true);
			covValueModel.setMinSeat(14);
			covValueModel.setMaxSeat(99);
			return covValueModel;
		case "SCRT":
			covValueModel.setValid(true);
			covValueModel.setMinSeat(14);
			covValueModel.setMaxSeat(99);
			return covValueModel;
		case "TOVT":
			covValueModel.setValid(true);
			covValueModel.setMinSeat(14);
			covValueModel.setMaxSeat(99);
			return covValueModel;
		case "STCT":
			covValueModel.setValid(true);
			covValueModel.setMinSeat(4);
			covValueModel.setMaxSeat(7);
			return covValueModel;
		default:
			return covValueModel;
		}
	}
}
