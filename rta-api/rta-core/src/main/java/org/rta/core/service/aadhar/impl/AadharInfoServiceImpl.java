package org.rta.core.service.aadhar.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.rta.aadhar.api.AadharService;
import org.rta.aadhar.api.model.AadharResponseModel;
import org.rta.aadhar.api.model.PIDBlockFingerPrintAuthServiceResponseModel;
import org.rta.aadhar.api.model.ResponseType;
import org.rta.core.dao.aadhar.AadhaarLogDAO;
import org.rta.core.dao.aadhar.AadharDAO;
import org.rta.core.dao.customer.PAddressNotMatchedDAO;
import org.rta.core.entity.applicant.AadharEntity;
import org.rta.core.enums.AadhaarProvider;
import org.rta.core.helper.applicant.AadhaarLogHelper;
import org.rta.core.model.AadhaarLogModel;
import org.rta.core.model.AadharModel;
import org.rta.core.model.BiometricresponseModel;
import org.rta.core.model.master.DistrictModel;
import org.rta.core.model.master.MandalModel;
import org.rta.core.model.master.StateModel;
import org.rta.core.service.aadhar.AadharInfoService;
import org.rta.core.service.master.DistrictService;
import org.rta.core.service.master.MandalService;
import org.rta.core.service.master.StateService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biometricauthenticationwebservice.BiometricresponseBean;


/**
 * Implementation for the AadharInfoService. Class calls aadhar api and get
 * response.
 * 
 * @author Sriram
 *
 */
@Service
public class AadharInfoServiceImpl implements AadharInfoService {

	private final AadharService aadharService;
	private final AadharDAO aadharDAO;
	private final ConversionService conversionService;
	private static final Logger log = Logger.getLogger(AadharInfoServiceImpl.class);
	
	@Autowired
	private MandalService mandalService;
	@Autowired
	private StateService stateService;
	@Autowired
	private DistrictService districtService;
	@Autowired
    PAddressNotMatchedDAO pAddressNotMatchedDAO;
	@Autowired
	private AadhaarLogDAO aadhaarLogDAO;
	@Autowired
	private AadhaarLogHelper aadhaarLogHelper;

	@Autowired
	public AadharInfoServiceImpl(final AadharService aadharService, AadharDAO aadharDAO,
			final ConversionService conversionService) {
		this.aadharService = aadharService;
		this.aadharDAO = aadharDAO;
		this.conversionService = conversionService;
	}

	@Override
	public BiometricresponseModel PIDBlockFingerPrintAuthService(String uid_num, String agency_name, String agency_code,
			String encryptedPid, String encSessionKey, String encHmac, String certificateIdentifier, String dataType) {
		PIDBlockFingerPrintAuthServiceResponseModel abar = aadharService.PIDBlockFingerPrintAuthService(uid_num,
				agency_name, agency_code, encryptedPid, encSessionKey, encHmac, certificateIdentifier, dataType);
		if (Objects.isNull(abar)) {
			return null;
		}
		BiometricresponseModel am = null;
		if (abar.getResponseType() == ResponseType.SUCCESS) {
			am = new BiometricresponseModel();
			BiometricresponseBean aadharDetails = abar.getBiometricresponseBean();
			am.setAuth_date(aadharDetails.getAuth_date());
			am.setAuth_reason(aadharDetails.getAuth_reason());
			am.setAuth_status(aadharDetails.getAuth_status());
			am.setAuth_transaction_code(aadharDetails.getAuth_transaction_code());
		}
		return am;
	}

	@Override
	@Transactional
	public Long save(AadharModel aadharModel) {
		Long generatedId = (Long) aadharDAO.save(conversionService.convert(aadharModel, AadharEntity.class));
		return ObjectsUtil.isNull(generatedId) ? null : generatedId;
	}
	@Override
	@Transactional
	public void saveOrUpdate(AadharModel model, String userName) {
		AadharEntity entity = new AadharEntity();
		entity.setKSA_KUA_Txn(model.getKSA_KUA_Txn());
		entity.setAuth_date(model.getAuth_date());
		entity.setAuth_status(model.getAuth_status());
		entity.setAuth_transaction_code(model.getAuth_transaction_code());
		entity.setCo(model.getCo());
		entity.setDistrict(model.getDistrict());
		entity.setDistrict_name(model.getDistrict_name());
		entity.setDob(model.getDob());
		entity.setGender(model.getGender());
		entity.setHouse(model.getHouse());
		entity.setLc(model.getLc());
		entity.setMandal(model.getMandal());
		entity.setMandal_name(model.getMandal_name());
		entity.setName(model.getName());
		entity.setPincode(model.getPincode());
		entity.setPo(model.getPo());
		entity.setStatecode(model.getStatecode());
		entity.setStreet(model.getStreet());
		entity.setSubdist(model.getSubdist());
		entity.setUid(Long.valueOf(model.getUid()));
		entity.setVillage(model.getVillage());
		entity.setVillage_name(model.getVillage_name());
		entity.setVtc(model.getVtc());
		entity.setCreatedBy(userName);
		entity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		entity.setModifiedBy(userName);
		entity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		aadharDAO.saveOrUpdate(entity);
	}

	@Transactional
	public AadharModel getAadhaarDemographicDataBySRDHSecuredeKYC(byte[] encPID, byte[] encSessionKey, byte[] encHmac,
			String uidNum, String agencyName, String agencyCode, String eKYCOption, String timeStamp, String udc,
			String deviceName) throws FileNotFoundException, UnsupportedEncodingException {

		AadhaarLogModel aadhaarLogModel = new AadhaarLogModel();
		aadhaarLogModel.setReqUid(uidNum);
		aadhaarLogModel.setReqProvider(AadhaarProvider.ECENTRIC.getLabel());
		aadhaarLogModel.setReqAgencyCode(agencyCode);
		aadhaarLogModel.setReqAgencyName(agencyName);
		aadhaarLogModel.setReqDeviceCode(udc);
		aadhaarLogModel.setReqDeviceName(deviceName);
		aadhaarLogModel.setReqTime(timeStamp);

		AadharResponseModel arm = aadharService.getAadhaarDemographicDataBySRDHSecuredeKYC(encPID, encSessionKey,
				encHmac, uidNum, agencyName, agencyCode, eKYCOption, timeStamp, udc, deviceName);
		if (Objects.isNull(arm)) {
			return null;
		}
		com.ecentric.bean.BiometricresponseBean aadharDetails = arm.getBiometricresponseBean();
		AadharModel am = null;
		if (arm.getResponseType() == ResponseType.SUCCESS) {
			am = new AadharModel();
			aadharDetails = arm.getBiometricresponseBean();
			am.setKSA_KUA_Txn(aadharDetails.getKSA_KUA_Txn());
			am.setAuth_err_code(aadharDetails.getAuth_err_code());
			am.setAuth_date(aadharDetails.getAuth_date());
			am.setAuth_status(aadharDetails.getAuth_status());
			am.setAuth_transaction_code(aadharDetails.getAuth_transaction_code());
			am.setCo(aadharDetails.getCo());
			am.setDistrict(aadharDetails.getDistrict());
			am.setDistrict_name(aadharDetails.getDistrict_name());
			am.setDob(aadharDetails.getDob());
			am.setGender(aadharDetails.getGender());
			am.setHouse(aadharDetails.getHouse());
			am.setLc(aadharDetails.getLc());
			am.setMandal(aadharDetails.getMandal());
			am.setMandal_name(aadharDetails.getMandal_name());
			am.setName(aadharDetails.getName());
			am.setPincode(aadharDetails.getPincode());
			am.setPo(aadharDetails.getPo());
			am.setStatecode(aadharDetails.getStatecode());
			am.setStreet(aadharDetails.getStreet());
			am.setSubdist(aadharDetails.getSubdist());
			am.setUid(Long.valueOf(aadharDetails.getUid()));
			am.setVillage(aadharDetails.getVillage());
			am.setVillage_name(aadharDetails.getVillage_name());
			am.setVtc(aadharDetails.getVtc());
		}
		aadhaarLogModel.setRespName(aadharDetails.getName());
		aadhaarLogModel.setRespStatecode(aadharDetails.getStatecode());
		aadhaarLogModel.setRespMandal(aadharDetails.getMandal());
		aadhaarLogModel.setRespKsaKuaTxn(aadharDetails.getKSA_KUA_Txn());
		aadhaarLogModel.setRespAuthErrorCode(aadharDetails.getAuth_err_code());
		aadhaarLogModel.setRespAuthStatus(aadharDetails.getAuth_status());
		aadhaarLogModel.setRespAuthDate(aadharDetails.getAuth_date());
		aadhaarLogModel.setRespAuthTransactionCode(aadharDetails.getAuth_transaction_code());
		aadhaarLogDAO.saveOrUpdate(aadhaarLogHelper.convertToEntity(aadhaarLogModel));
		return am;
	}

	@Override
	public AadharModel setInsideApAndMandal(AadharModel am){
	    
		/*
		 * am.setStatecode("Andhra Pradesh"); am.setDistrict("ANANTAPUR");
		 * am.setMandal_name("AGALI");
		 */
	    
	    log.info("from Aadhar=> state : " + am.getStatecode() + " district : " + am.getDistrict() + " mandal : " + am.getMandal() + " and Mandal NAME: " + am.getMandal_name());
	    if(am.getStatecode().equalsIgnoreCase("AP") || am.getStatecode().equalsIgnoreCase("Andhra Pradesh")){
	        am.setInsideAP(true);
	    } else {
	        am.setInsideAP(false);
	    }
	    
	    StateModel state = stateService.getStateByName(am.getStatecode());
	    if(ObjectsUtil.isNull(state)){
	        am.setStateMatched(false);
	        am.setDistrictMatched(false);
	        am.setMandalMatched(false);
	    } else {
	        am.setStateMatched(true);
	        am.setStateMatchedCode(state.getCode());
	    }
	    
	    DistrictModel district = districtService.getDistrictByName(am.getDistrict());
	    if(ObjectsUtil.isNull(district)){
            am.setDistrictMatched(false);
            am.setMandalMatched(false);
        } else if(am.isStateMatched()){
            am.setDistrictMatched(true);
            am.setDistrictMatchedCode(district.getCode());
        }
	    
	    if(am.getMandal_name() == null) {
	        am.setMandalMatched(false);
	    } else {
	        MandalModel mandal = mandalService.getMandal(am.getMandal_name(), am.getDistrict());
	        if(am.getMandal_name() == null || am.getMandal_name().trim().equals("") || ObjectsUtil.isNull(mandal)){
	            am.setMandalMatched(false);
	        } else if(am.isDistrictMatched()){
	            am.setMandalMatched(true);
	            am.setMandalMatchedCode(mandal.getCode().toString());
	        }
	    }
	    return am;
	}
}
