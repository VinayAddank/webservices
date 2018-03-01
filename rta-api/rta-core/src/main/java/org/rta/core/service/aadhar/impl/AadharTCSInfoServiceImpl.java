package org.rta.core.service.aadhar.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.rta.aadhar.api.AadhaTCSService;
import org.rta.aadhar.api.model.AadharTCSResponse;
import org.rta.aadhar.api.model.AadharTCSResponseModel;
import org.rta.aadhar.api.model.ResponseType;
import org.rta.core.dao.aadhar.AadhaTCSDAO;
import org.rta.core.dao.aadhar.AadhaarLogDAO;
import org.rta.core.dao.customer.PAddressNotMatchedDAO;
import org.rta.core.enums.AadhaarProvider;
import org.rta.core.exception.NotFoundException;
import org.rta.core.helper.applicant.AadhaarLogHelper;
import org.rta.core.helper.applicant.AadharHelper;
import org.rta.core.model.AadhaarLogModel;
import org.rta.core.model.AadharModel;
import org.rta.core.model.master.DistrictModel;
import org.rta.core.model.master.MandalModel;
import org.rta.core.model.master.StateModel;
import org.rta.core.service.aadhar.AadharTCSInfoService;
import org.rta.core.service.master.DistrictService;
import org.rta.core.service.master.MandalService;
import org.rta.core.service.master.StateService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementation for the AadharInfoService. Class calls aadhar api and get
 * response.
 * 
 * @author RaviTeja
 *
 */
@Service
public class AadharTCSInfoServiceImpl implements AadharTCSInfoService {

	private final AadhaTCSService aadhaTCSService;
	private final ConversionService conversionService;
	private static final Logger log = Logger.getLogger(AadharTCSInfoServiceImpl.class);
	
	@Autowired
    private PAddressNotMatchedDAO pAddressNotMatchedDAO;

	@Autowired
	private AadhaarLogDAO aadhaarLogDAO;

	@Autowired
	private AadhaarLogHelper aadhaarLogHelper;

    @Autowired
    private AadharHelper aadharHelper;

    @Autowired
    private AadhaTCSDAO aadharTCSDAO;

    @Autowired
    private MandalService mandalService;

    @Autowired
    private StateService stateService;

    @Autowired
    private DistrictService districtService;

	@Autowired
	public AadharTCSInfoServiceImpl(final AadhaTCSService aadharService,
			final ConversionService conversionService) {
		this.aadhaTCSService = aadharService;
		this.conversionService = conversionService;
	}

	@Override
	@Transactional
	public AadharModel AADHAAR_EKYC_FINGER_AUTHENTICATION(URL endpoint, String uid, String tid, String udc, String rdsId,
			String ip, String srt, String crt, String skey, String pid, String hmac, String ci, String bt,
			String pincode, String version, String scheme, String department, String service, String dpId,
			String rdsVer, String consentme, String userName, String deviceName, String attemptType, String dc, String mi,String mc)
			throws java.rmi.RemoteException, FileNotFoundException, UnsupportedEncodingException, ServiceException,
			javax.xml.rpc.ServiceException {

		if (Objects.isNull(endpoint) || Objects.isNull(uid) || Objects.isNull(tid) || Objects.isNull(udc) || Objects.isNull(rdsId)
				|| Objects.isNull(ip) || Objects.isNull(srt) || Objects.isNull(crt) || Objects.isNull(skey)
				|| Objects.isNull(pid) || Objects.isNull(hmac) || Objects.isNull(ci) || Objects.isNull(bt)
				|| Objects.isNull(pincode) || Objects.isNull(version) || Objects.isNull(scheme)
				|| Objects.isNull(department) || Objects.isNull(service) || Objects.isNull(dpId)
				|| Objects.isNull(rdsVer) || Objects.isNull(consentme)|| Objects.isNull(attemptType)
				|| Objects.isNull(dc) || Objects.isNull(mi)|| Objects.isNull(mc)) {
			throw new IllegalArgumentException(
					" uid,  tid,  udc,  rdsId,  ip,  srt,  crt,  skey,  pid,hmac,  ci,  bt,  pincode,  version,  scheme,  department, service,  dpId, attemptType, rdsVer and consentme can't be null");
		}
		String requestTime = DateUtil.getDate("MM/dd/yyyy hh:mm:ss.SSS a", "IST", new Date());
		AadhaarLogModel aadhaarLogModel = new AadhaarLogModel();
		aadhaarLogModel.setReqEndPointUrl(endpoint.toString());
		aadhaarLogModel.setReqUid(uid);
		aadhaarLogModel.setReqTid(tid);
		aadhaarLogModel.setReqProvider(AadhaarProvider.APTONLINE.getLabel());
		aadhaarLogModel.setReqAgencyCode(department);
		aadhaarLogModel.setReqAgencyName(scheme);
		aadhaarLogModel.setReqDeviceCode(udc);
		aadhaarLogModel.setReqDeviceName(deviceName);
		aadhaarLogModel.setReqTime(requestTime);
		aadhaarLogModel.setCreatedBy(userName);
		aadhaarLogModel.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		aadhaarLogModel.setReqServerDateTime(srt);
		aadhaarLogModel.setReqClientDateTime(crt);
		aadhaarLogModel.setReqRdsVer(rdsVer);
		aadhaarLogModel.setReqConsentMe(consentme);
		aadhaarLogModel.setReqService(service);
		aadhaarLogModel.setReqAttemptType(attemptType);
        aadhaarLogModel.setReqAuthType(bt);
        aadhaarLogModel.setReqRdsId(rdsId);
        aadhaarLogModel.setReqDpId(dpId);
        aadhaarLogModel.setReqDc(dc);
        aadhaarLogModel.setReqMi(mi);
        aadhaarLogModel.setReqMc(mc);
        
	
		AadharModel am = null;
        try {
            AadharTCSResponseModel arm = aadhaTCSService.AADHAAR_EKYC_FINGER_AUTHENTICATION(endpoint, uid, tid, udc, rdsId, ip, srt,
                    crt, skey, pid, hmac, ci, bt, pincode, version, scheme, department, service, dpId, rdsVer,
                    consentme,attemptType, dc, mi, mc);

            if (!Objects.isNull(arm)) {
                am = new AadharModel();
                log.info("Aadhaar Response Type:" + arm.getResponseType().getLabel());
                if (ResponseType.SUCCESS.getLabel().equalsIgnoreCase(arm.getResponseType().getLabel())) {
                    AadharTCSResponse aadharDetails = arm.getAadharTCSResponse();
                    am.setKSA_KUA_Txn("-");
                    am.setAuth_date("-");
                    am.setAuth_status("SUCCESS");
                    am.setAuth_transaction_code("-");
                    am.setCo(aadharDetails.getCo());
                    am.setDistrict(aadharDetails.getDist());
                    am.setDistrict_name(aadharDetails.getDist());
                    am.setDob(aadharDetails.getDob());
                    am.setGender(aadharDetails.getGender());
                    am.setHouse(aadharDetails.getHouse());
                    am.setLc(aadharDetails.getLoc());
                    am.setMandal(aadharDetails.getSubdist());
                    am.setMandal_name(aadharDetails.getSubdist());
                    am.setName(aadharDetails.getName());
                    am.setPincode(aadharDetails.getPc());
                    am.setPo(aadharDetails.getPo());
                    am.setStatecode(aadharDetails.getState());
                    am.setStreet(aadharDetails.getStreet());
                    am.setSubdist(aadharDetails.getSubdist());
                    log.info("Uid number from Aadhaar:" + aadharDetails.getUid());
                    am.setUid(Long.valueOf(aadharDetails.getUid()));
                    am.setVillage(aadharDetails.getVtc());
                    am.setVillage_name(aadharDetails.getVtc());
                    am.setVtc(aadharDetails.getVtc());

                    aadhaarLogModel.setRespAuthStatus("SUCCESS");
                    aadhaarLogModel.setRespName(aadharDetails.getName());
                    aadhaarLogModel.setRespStatecode(aadharDetails.getState());
                    aadhaarLogModel.setRespMandal(aadharDetails.getSubdist());
                } else {
                    am.setAuth_status("FAILED");
                    am.setAuth_err_code(arm.getAadharTCSResponse().getMsg());
                    aadhaarLogModel.setRespAuthStatus("FAILED");
                    aadhaarLogModel.setRespAuthErrorCode(arm.getAadharTCSResponse().getMsg());
                }
            }
        } catch (Exception e) {
            aadhaarLogModel.setRespAuthStatus("FAILED");
            aadhaarLogModel.setRespAuthErrorCode(e.getMessage());
            log.error("Exception in AADHAAR_EKYC_FINGER_AUTHENTICATION " + e.getMessage());
            log.debug(e);
        }
	    String responseTime = DateUtil.getDate("MM/dd/yyyy hh:mm:ss.SSS a", "IST", new Date());
		aadhaarLogModel.setRespTime(responseTime);
		aadhaarLogDAO.saveOrUpdate(aadhaarLogHelper.convertToEntity(aadhaarLogModel));
		
		return am;
	}

    @Override
    @Transactional
    public AadharModel getAadharDetails(Long aadharNumber) throws NotFoundException {
        AadharModel model = aadharHelper.convertToModel(aadharTCSDAO.getAadharDetails(aadharNumber));
        if (ObjectsUtil.isNull(model)) {
            throw new NotFoundException();
        }
        if (model.getStatecode().equalsIgnoreCase("AP") || model.getStatecode().equalsIgnoreCase("Andhra Pradesh")) {
            model.setInsideAP(true);
        } else {
            model.setInsideAP(false);
        }

        StateModel state = stateService.getStateByName(model.getStatecode());
        if (ObjectsUtil.isNull(state)) {
            model.setStateMatched(false);
            model.setDistrictMatched(false);
            model.setMandalMatched(false);
        } else {
            model.setStateMatched(true);
            model.setStateMatchedCode(state.getCode());
        }

        DistrictModel district = districtService.getDistrictByName(model.getDistrict_name());
        if (ObjectsUtil.isNull(district)) {
            model.setDistrictMatched(false);
            model.setMandalMatched(false);
        } else if (model.isStateMatched()) {
            model.setDistrictMatched(true);
            model.setDistrictMatchedCode(district.getCode());
        }
        if (!StringsUtil.isNullOrEmpty(model.getMandal_name())) {
            MandalModel mandal = mandalService.getMandal(model.getMandal_name(), model.getDistrict_name());
            if (ObjectsUtil.isNull(mandal)) {
                model.setMandalMatched(false);
            } else if (model.isDistrictMatched()) {
                model.setMandalMatched(true);
                model.setMandalMatchedCode(mandal.getCode().toString());
            }
        } else {
            model.setMandalMatched(false);
        }
        return model;
    }

	
}
