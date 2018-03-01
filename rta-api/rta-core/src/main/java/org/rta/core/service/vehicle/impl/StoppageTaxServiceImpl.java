/**
 * 
 */
package org.rta.core.service.vehicle.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.constant.SomeConstants;
import org.rta.core.dao.vehicle.StoppageApplicationDAO;
import org.rta.core.dao.vehicle.StoppageInspectionDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleRCChangeHistoryDAO;
import org.rta.core.entity.vehicle.StoppageApplicationEntity;
import org.rta.core.entity.vehicle.StoppageInspectionEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.FormCodeType;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.exception.DataMismatchException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.sync.ApplicationFormDataModel;
import org.rta.core.model.vehicle.StoppageTaxDetailsModel;
import org.rta.core.model.vehicle.StoppageTaxReportModel;
import org.rta.core.service.sync.impl.SyncServiceImpl;
import org.rta.core.service.vehicle.StoppageTaxService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author sohan.maurya
 *
 */

@Service
public class StoppageTaxServiceImpl implements StoppageTaxService{

	private static final Logger log = Logger.getLogger(SyncServiceImpl.class);
	
	@Autowired
	private VehicleDAO vehicleDAO;
	
	@Autowired
	private VehicleRCChangeHistoryDAO vehicleRCChangeHistoryDAO;
	
	@Autowired
	private StoppageInspectionDAO stoppageInspectionDAO;
	
	@Autowired
	private StoppageApplicationDAO stoppageApplicationDAO;
	
	@Override
	@Transactional
	public SaveUpdateResponse saveOrUpdateStoppageTax(List<ApplicationFormDataModel> models, String prNumber, String userName) throws DataMismatchException, JsonParseException, JsonMappingException, IOException {
		VehicleRCEntity rcEntity = vehicleDAO.getVehicleRCByPRNumber(prNumber);
		if (ObjectsUtil.isNull(rcEntity)) {
			throw new DataMismatchException();
		}
		try {
			String result = vehicleRCChangeHistoryDAO.saveData(rcEntity.getVehicleRcId(), userName,
					DateUtil.toCurrentUTCTimeStamp(), ServiceType.STOPPAGE_TAX);
			log.info("One entity in history table staus " + result);
		} catch (Exception e) {
		}
		rcEntity.setPrStatus(Status.STOPPAGE_TAX.getValue());
		vehicleDAO.update(rcEntity);
		for(ApplicationFormDataModel form : models){
			ObjectMapper mapper = new ObjectMapper();
			if(FormCodeType.ST_FORM.getLabel().equalsIgnoreCase(form.getFormCode())){
				StoppageTaxDetailsModel model = mapper.readValue(form.getFormData(), StoppageTaxDetailsModel.class);
				saveStoppageTaxApplicationDetails(model, form.getApplicaionNumber(), rcEntity, userName);
//			} else if(FormCodeType.ST_MVI_FORM.getLabel().equalsIgnoreCase(form.getFormCode())){
//				StoppageTaxReportModel model = mapper.readValue(form.getFormData(), StoppageTaxReportModel.class);
//				saveStoppageTaxReportDetails(model, form.getApplicaionNumber(), rcEntity, userName);
			}
		}
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS);
	}
	
	private void saveStoppageTaxApplicationDetails(StoppageTaxDetailsModel model, String applicationNumber, 
			VehicleRCEntity vehicleRCEntity, String userName){
		
		StoppageApplicationEntity entity= new StoppageApplicationEntity();
		entity.setStoppageNo(applicationNumber);
		entity.setApproved(SomeConstants.YES);
		entity.setInspectedBy(userName);
		entity.setInspectedDt(DateUtil.toCurrentUTCTimeStamp());
		entity.setRegnNo(vehicleRCEntity.getPrNumber());
		entity.setVehicleRcId(vehicleRCEntity); 
		entity.setRcValidity(vehicleRCEntity.getPrExpireDate());
		entity.setFcValidity(null);
		entity.setPermitValidity(null);
		entity.setTaxValidity(null);
		entity.setTaxAmt(0);
		entity.setStoppageOn(model.getStoppageDate());
		entity.setStoppageReason(model.getStoppageReason());
		entity.setStatus(Status.APPROVED.getValue());
		entity.setStoppageArea(null);
		entity.setStoppageAddr1(model.getDoorNo());
		entity.setStoppageAddr2(model.getStreet());
		entity.setStoppageAddr3(model.getMandalName());
		entity.setCity(model.getCity());
		entity.setDistrictName(model.getDistrictName());
		entity.setPincode(Long.parseLong(model.getPostOffice()));
		entity.setStateName(model.getStateName());
		entity.setApprovedBy(null);
		entity.setApprovedOn(DateUtil.toCurrentUTCTimeStamp());
		entity.setValidFlag(SomeConstants.YES);
		entity.setAssignedMvi(null);
		entity.setMviRemarks(null);
		entity.setOfficeCode(vehicleRCEntity.getRtaOfficeId().getCode());
		entity.setVehicleCov(null);// TODO
		entity.setRemarks(model.getStoppageReason());
		entity.setPmsRemarks(null);
		entity.setTaxExemMonths(null);
		entity.setCreatedBy(userName);
		entity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		entity.setModifiedBy(userName);
		entity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		stoppageApplicationDAO.save(entity);
	}
	
	@Override
	@Transactional
	public SaveUpdateResponse saveStoppageTaxReportDetails(StoppageTaxReportModel model, String prNumber, String userName){
		
		VehicleRCEntity vehicleRCEntity = vehicleDAO.getVehicleRCByPRNumber(prNumber);
		StoppageInspectionEntity entity = new StoppageInspectionEntity();
		entity.setStoppageNo(model.getApplicationNo());
		entity.setInspectedBy(userName);
		entity.setInspectedDt(DateUtil.toCurrentUTCTimeStamp());
		entity.setRegnNo(prNumber);
		entity.setVehicleRcId(vehicleRCEntity);
		entity.setQuarterEndDt(null);
		entity.setQuarterFromDt(null);
		entity.setOwner(model.getOwner());
		entity.setOther(model.getOther());
		entity.setGaragedPlaceReported(model.getGaragedPlaceReported());
		entity.setShiftedComment(model.getShiftedComment());
		entity.setMechanicsStatements(model.getMechanicsStatements());
		entity.setRepairsComments(model.getRepairsComments());
		entity.setIsRepairsCondemned(model.getIsRepairsCondemned());
		entity.setRepairsCondemnedComments(model.getRepairsCondemnedComments());
		entity.setIsVerifiedGVR(model.getIsVerifiedGVR());
		entity.setOilBunks(model.getOilBunks());
		entity.setAllCheckPosts(model.getAllCheckPosts());
		entity.setOverAllComments(model.getOverAllComments());
		entity.setCreatedBy(userName);
		entity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		entity.setModifiedBy(userName);
		entity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		stoppageInspectionDAO.save(entity);
		
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS);
	}

	@Override
	@Transactional
	public StoppageTaxDetailsModel getStoppageApplication(String prNumber) {
		
		StoppageApplicationEntity entity = stoppageApplicationDAO.getStoppageApplication(prNumber);
		if(ObjectsUtil.isNull(entity)){
			return null;
		}
		StoppageTaxDetailsModel model = new StoppageTaxDetailsModel();
		model.setDoorNo(entity.getStoppageAddr1());
		model.setStreet(entity.getStoppageAddr2());
		model.setMandalName(entity.getStoppageAddr3());
		model.setCity(entity.getCity());
		model.setDistrictName(entity.getDistrictName());
		model.setPostOffice(String.valueOf(entity.getPincode()));
		model.setStateName(entity.getStateName());
		model.setStoppageDate(entity.getStoppageOn());
		model.setStoppageReason(entity.getStoppageReason());
		
		return model;
	}

	@Override
	@Transactional
	public List<StoppageTaxReportModel> getStoppageInspections(String applicationNo) {
		
		List<StoppageInspectionEntity> entities = stoppageInspectionDAO.getStoppageInspections(applicationNo);
		if(!ObjectsUtil.isNull(entities) && entities.size()>0){
			StoppageTaxReportModel model = null;
			List<StoppageTaxReportModel> models = new ArrayList<StoppageTaxReportModel>();
			for( StoppageInspectionEntity entity : entities){
				model = new StoppageTaxReportModel();
				model.setStoppageTaxReportId(entity.getStoppageInspectionId());
				model.setApplicationNo(entity.getStoppageNo());
				model.setOwner(entity.getOwner());
				model.setOther(entity.getOther());
				model.setGaragedPlaceReported(entity.getGaragedPlaceReported());
				model.setShiftedComment(entity.getShiftedComment());
				model.setMechanicsStatements(entity.getMechanicsStatements());
				model.setRepairsComments(entity.getRepairsComments());
				model.setIsRepairsCondemned(entity.getIsRepairsCondemned());
				model.setRepairsCondemnedComments(entity.getRepairsCondemnedComments());
				model.setIsVerifiedGVR(entity.getIsVerifiedGVR());
				model.setOilBunks(entity.getOilBunks());
				model.setAllCheckPosts(entity.getAllCheckPosts());
				model.setOverAllComments(entity.getOverAllComments());	
				models.add(model);
			}
			return models;
		}
		return null;
	}

	@Override
	@Transactional
	public StoppageTaxReportModel getStoppageInspection(Long stoppageReportId) {
		StoppageInspectionEntity entity = stoppageInspectionDAO.getEntity(StoppageInspectionEntity.class, stoppageReportId);
		if(!ObjectsUtil.isNull(entity)){
			StoppageTaxReportModel model = new StoppageTaxReportModel();
			model.setStoppageTaxReportId(entity.getStoppageInspectionId());
			model.setApplicationNo(entity.getStoppageNo());
			model.setOwner(entity.getOwner());
			model.setOther(entity.getOther());
			model.setGaragedPlaceReported(entity.getGaragedPlaceReported());
			model.setShiftedComment(entity.getShiftedComment());
			model.setMechanicsStatements(entity.getMechanicsStatements());
			model.setRepairsComments(entity.getRepairsComments());
			model.setIsRepairsCondemned(entity.getIsRepairsCondemned());
			model.setRepairsCondemnedComments(entity.getRepairsCondemnedComments());
			model.setIsVerifiedGVR(entity.getIsVerifiedGVR());
			model.setOilBunks(entity.getOilBunks());
			model.setAllCheckPosts(entity.getAllCheckPosts());
			model.setOverAllComments(entity.getOverAllComments());	
			return model;
		}
		return null;
	}
}
