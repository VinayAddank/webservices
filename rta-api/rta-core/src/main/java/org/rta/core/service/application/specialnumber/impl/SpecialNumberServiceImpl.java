package org.rta.core.service.application.specialnumber.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.rta.core.dao.master.registration.PrSeriesMasterDAO;
import org.rta.core.dao.master.registration.SpecialNumberDAO;
import org.rta.core.dao.master.registration.SpecialNumberRtaOfficeDAO;
import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.dao.specialnumber.SpecialNumberFeeDetailsDAO;
import org.rta.core.dao.user.RTAEmployeeDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.master.registration.PrSeriesMasterEntity;
import org.rta.core.entity.master.registration.SpecialNumberEntity;
import org.rta.core.entity.master.registration.SpecialNumberRtaOfficeEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.entity.specialnumber.SpecialNumberFeeDetailsEntity;
import org.rta.core.entity.user.RTAEmployeeEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.PRType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.exception.InvalidPrNumberException;
import org.rta.core.exception.PrNumberExistException;
import org.rta.core.exceptioncode.InvalidPrNumberExceptionCode;
import org.rta.core.exceptioncode.PrNumberExistExceptionCode;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.master.registration.SpecialNumberModel;
import org.rta.core.model.specialnumber.SpecialNumberFeeModel;
import org.rta.core.service.application.specialnumber.SpecialNumberService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.NumberParser;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpecialNumberServiceImpl implements SpecialNumberService {
	private static final Logger logger = Logger.getLogger(SpecialNumberServiceImpl.class);
	@Autowired
	private VehicleDAO vehicleDAO;

	@Autowired
	private SpecialNumberFeeDetailsDAO specialNumberFeeDetailsDAO;

	@Autowired
	private SpecialNumberDAO specialNumberDAO;
	@Autowired
	private RtaOfficeDAO rtaOfficeDAO;
	@Autowired
	private PrSeriesMasterDAO prSeriesMasterDAO;
	@Autowired
	private SpecialNumberRtaOfficeDAO specialNumberRtaOfficeDAO;
	@Autowired
	private RTAEmployeeDAO rTAEmployeeDAO;
	
	// piyush.singh
	// this method for save fee for special number
	@Transactional
	@Override
	public SaveUpdateResponse vehicleSpecialNumberFee(SpecialNumberFeeModel specialNumberFeeModel) {

		// this should be not null
		VehicleRCEntity vehicleRcEntity = vehicleDAO.get(specialNumberFeeModel.getVehicalRcId().longValue());
		if (ObjectsUtil.isNull(vehicleRcEntity)) {
			logger.error("validating Vehical Rc - Rc does not exists : " + vehicleRcEntity);
			throw new PrNumberExistException(PrNumberExistExceptionCode.RC_EXIST, "RC does not exists, please check");

		}
		if(specialNumberFeeModel.getTransactionId()==null && specialNumberFeeModel.getDdNumber()==null){
			logger.error(" Either DD number OR Transaction Id is required ");
			throw new PrNumberExistException(PrNumberExistExceptionCode.DD_NUMBER_OR_TRANSACTION_ID, " Either DD number OR Transaction Id is required");
		}else if(specialNumberFeeModel.getTransactionId().length()==0 && specialNumberFeeModel.getDdNumber().length()==0){
			throw new PrNumberExistException(PrNumberExistExceptionCode.DD_NUMBER_OR_TRANSACTION_ID, " Either DD number OR Transaction Id is required");

		}

		/*
		 * // this should be null otherwise throw exception VehicleRCEntity vrc
		 * = vehicleDAO.getVehicleRc4Pr(specialNumberFeeModel.getPrNumber()); if
		 * (ObjectsUtil.isNotNull(vrc)) { logger.error(
		 * "validating prNumber - pr already exists : " + vrc); throw new
		 * PrNumberExistException(PrNumberExistExceptionCode.
		 * PRNUMBER_ALREADY_EXIST, "pr already exists, please check"); }
		 */

		SpecialNumberFeeDetailsEntity specialNumberFeeDetailsEntity = new SpecialNumberFeeDetailsEntity();
		specialNumberFeeDetailsEntity.setAmount(specialNumberFeeModel.getAmount());
		specialNumberFeeDetailsEntity.setBankName(specialNumberFeeModel.getBankName());
		specialNumberFeeDetailsEntity.setDdNumber(specialNumberFeeModel.getDdNumber());
		specialNumberFeeDetailsEntity.setVehicleRcId(specialNumberFeeModel.getVehicalRcId());
		specialNumberFeeDetailsEntity.setPermanentRegistration(specialNumberFeeModel.getPrNumber());
		specialNumberFeeDetailsEntity.setTransactionId(specialNumberFeeModel.getTransactionId());
		specialNumberFeeDetailsDAO.save(specialNumberFeeDetailsEntity);

		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Saved Successfully.",
				vehicleRcEntity.getVehicleRcId().toString());
	}

	// piyush.singh
	// this method to get special number list on basis of RTO office id
	@Transactional
	@Override
	public List<SpecialNumberModel> specialNumberList(UserType userRole, Long userID, String rtaOfcId) {
	    RtaOfficeEntity rtaOfficeEntity = null;
	    List<SpecialNumberModel> specialNumberList = new ArrayList<SpecialNumberModel>();
	    
		if(!StringsUtil.isNullOrEmpty(rtaOfcId)) {
		    rtaOfficeEntity = rtaOfficeDAO.getRtaOfficeDetailsByCode(rtaOfcId);
		}

		if(null == rtaOfficeEntity) {
		    if(null != userID && userRole != UserType.ROLE_DEALER) {
		        RTAEmployeeEntity rtaEmployee = rTAEmployeeDAO.getByUserId(userID);
		        if(null != rtaEmployee) {
		            rtaOfficeEntity = rtaEmployee.getRtaOfficeEntity();
		        }
		    } else {
		        logger.info("RETURN empty list as No current PR series found for rtaoffice:"+rtaOfcId +" OR user id"+userID);
		        return specialNumberList;
		    }
		}
		
		List<PrSeriesMasterEntity> list = prSeriesMasterDAO.getAllPresentSeries(rtaOfficeEntity.getRtaOfficeId());
		/*if (userRole == UserType.ROLE_DEALER) {
			list = prSeriesMasterDAO.getAllPresentSeries(rtaOfficeEntity.getRtaOfficeId());
		} else if (userRole.equals(UserType.ROLE_CCO)) {
			list = prSeriesMasterDAO.getAllPresentPastSeries(rtaOfficeEntity.getRtaOfficeId());
		}*/
		
		List<SpecialNumberEntity> numberList = specialNumberDAO.getAllSpecialNoEntity();
		for (PrSeriesMasterEntity prMaster : list) {
		    Integer limit = prMaster.getUsedNumber() + 1000;
			for (SpecialNumberEntity number : numberList) {
			    if(number.getNumber() > limit) {
			        break;
			    }
				SpecialNumberModel specialNumberModel = new SpecialNumberModel();
				String formattedString = NumberParser.getFormatedNumber(number.getNumber(), "xxxx",'x', '0');
				specialNumberModel.setNumber(prMaster.getSeries() + formattedString);
				SpecialNumberRtaOfficeEntity specialnum = specialNumberRtaOfficeDAO.getUniqueSpecialNumber(prMaster.getSeries() + formattedString);
				if (specialnum != null) {
					specialNumberModel.setStatus(true);
					specialNumberModel.setIsLocked(true);
				} else {
					specialNumberModel.setStatus(false);
					specialNumberModel.setIsLocked(false);
				}
				specialNumberModel.setRegType(prMaster.getRegType());
				specialNumberList.add(specialNumberModel);
			}
		}

		return specialNumberList;
	}
	
    public List<String> specialNumberList(String rtaOfcId) {
        List<String> specialNumberList = new ArrayList<String>();
        if (!StringsUtil.isNullOrEmpty(rtaOfcId)) {
            RtaOfficeEntity rtaOfficeEntity = rtaOfficeDAO.getRtaOfficeDetailsByCode(rtaOfcId);
            if (null != rtaOfficeEntity) {
                List<PrSeriesMasterEntity> list =
                        prSeriesMasterDAO.getAllPresentSeries(rtaOfficeEntity.getRtaOfficeId());
                List<SpecialNumberEntity> numberList = specialNumberDAO.getAllSpecialNoEntity();
                for (PrSeriesMasterEntity prMaster : list) {
                    Integer limit = prMaster.getUsedNumber() + 1000;
                    for (SpecialNumberEntity number : numberList) {
                        if (number.getNumber() > limit) {
                            break;
                        }
                        String formattedString = NumberParser.getFormatedNumber(number.getNumber(), "xxxx", 'x', '0');
                        specialNumberList.add(prMaster.getSeries() + formattedString);
                    }
                }
            }
        }

        return specialNumberList;
    }
	
	// piyush.singh
	// this method is for lock special number
	// check validation of tr number and special number
	@Transactional
	@Override
	public SaveUpdateResponse lockVehicleSpecialNumber(Long userId,String userName, String trNumber, String specialNumber) {
		RtaOfficeEntity rtaOfficeEntity = null;
		if(specialNumber.length()<=4){
			return new SaveUpdateResponse(SaveUpdateResponse.FAILURE, " invalid special number",
					specialNumber);
		}
		
		VehicleRCEntity vehicleRC = vehicleDAO.getVehicleRc4Pr(specialNumber);
		if (vehicleRC != null) {
            return new SaveUpdateResponse(SaveUpdateResponse.FAILURE, "  PR number already generated for this special number:"+specialNumber, specialNumber);
        }
		RTAEmployeeEntity rtaEmployee = rTAEmployeeDAO.getByUserId(userId);
        if (null != rtaEmployee) {
            rtaOfficeEntity = rtaEmployee.getRtaOfficeEntity();
            List<String> validSpecialNumber = specialNumberList(rtaOfficeEntity.getCode());
            if(!ObjectsUtil.isNullOrEmpty(validSpecialNumber)) {
                if(!validSpecialNumber.contains(specialNumber)) {
                    return new SaveUpdateResponse(SaveUpdateResponse.FAILURE, " Special number "+specialNumber+" is not in valid special number list of rta office:"+rtaOfficeEntity.getCode(),specialNumber);
                }
            } else {
                return new SaveUpdateResponse(SaveUpdateResponse.FAILURE, " No special number series available for Rta Office:"+rtaOfficeEntity.getCode(),
                        specialNumber);
            }
        }
	        
        VehicleRCEntity vehicleRCEntity = vehicleDAO.getVehicleRCByTRNumber(trNumber);
		if (vehicleRCEntity == null) {
			return new SaveUpdateResponse(SaveUpdateResponse.FAILURE, "  TR number does not exist", trNumber);
		} else if (!PRType.getPRType(vehicleRCEntity.getPrType()).equals(PRType.SPECIAL)) {
			return new SaveUpdateResponse(SaveUpdateResponse.FAILURE, " TR number is not opt for special number",
					specialNumber);
		}else if(!vehicleRCEntity.getRtaOfficeId().getRtaOfficeId().equals(rtaOfficeEntity.getRtaOfficeId())){
        	return new SaveUpdateResponse(SaveUpdateResponse.FAILURE, "  TR number does not match with approver RTA office", trNumber);
        }
		
		if(vehicleRCEntity.getPrStatus().equals(Status.APPROVED.getValue())){
			return new SaveUpdateResponse(SaveUpdateResponse.FAILURE, " PR number approved ",
					specialNumber);
		}
		
	    List<Integer> specialNoList=specialNumberDAO.getAllSpecialNo();
        int sequence = Integer.parseInt(StringsUtil.getLastChars(specialNumber, 4));
        if (specialNoList != null) {
            if (!specialNoList.contains(sequence)) {
                logger.error("validating special number - not special number : " + specialNumber);
                throw new InvalidPrNumberException(InvalidPrNumberExceptionCode.INVALID_PR_NUMBER_EXCEPTION, specialNumber+" not special number");
            }
        }
        
        PrSeriesMasterEntity prcode=prSeriesMasterDAO.getRegTypeBySeries(StringsUtil.getStartingChars(specialNumber, 6));
		if(prcode==null){
			return new SaveUpdateResponse(SaveUpdateResponse.FAILURE, "Special number series does not belong to our special series",
					specialNumber);
		}else if(!vehicleRCEntity.getRegCategory().getRegistrationCategoryId().equals(prcode.getRegType())){
			return new SaveUpdateResponse(SaveUpdateResponse.FAILURE, "TR and Special number Registration type mismatch",
					specialNumber);
		}
        
		SpecialNumberRtaOfficeEntity specialnum = specialNumberRtaOfficeDAO.getSpecialNumberByTrNo(trNumber);
		if (specialnum != null) {
			return new SaveUpdateResponse(SaveUpdateResponse.FAILURE, " Special number " +specialnum.getSpecialNumber()+" has already been locked for this TR:"+trNumber,specialNumber);
		} 
		SpecialNumberRtaOfficeEntity special = specialNumberRtaOfficeDAO.getUniqueSpecialNumber(specialNumber);
		if(special != null){
            return new SaveUpdateResponse(SaveUpdateResponse.FAILURE, " Special number had already been locked by TR: "+trNumber, specialNumber);
        }
	
		SpecialNumberRtaOfficeEntity specialNumberRtaOfficeEntity = new SpecialNumberRtaOfficeEntity();
		specialNumberRtaOfficeEntity.setTrNumber(trNumber);
		specialNumberRtaOfficeEntity.setSpecialNumber(specialNumber);
		specialNumberRtaOfficeEntity.setStatus(Status.LOCKED.getValue());
		specialNumberRtaOfficeEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		specialNumberRtaOfficeEntity.setCreatedBy(userName);
		specialNumberRtaOfficeDAO.save(specialNumberRtaOfficeEntity);

		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Locked Successfully.", specialNumber);
	}
}
