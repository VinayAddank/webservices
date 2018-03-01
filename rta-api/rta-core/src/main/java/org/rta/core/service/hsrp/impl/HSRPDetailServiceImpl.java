package org.rta.core.service.hsrp.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.hsrp.AffixationCenterMasterDAO;
import org.rta.core.dao.hsrp.HSRPDetailDAO;
import org.rta.core.dao.hsrp.HSRPDetailHistoryDAO;
import org.rta.core.dao.hsrp.HSRPFeeDetailsDAO;
import org.rta.core.dao.hsrp.HSRPTransactionDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.dao.vehicle.cms.VehicleClassDescDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.hsrp.HSRPDetailEntity;
import org.rta.core.entity.hsrp.HSRPDetailHistoryEntity;
import org.rta.core.entity.hsrp.HSRPFeeDetailsEntity;
import org.rta.core.entity.hsrp.HSRPTransactionEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.entity.vehicle.cms.VehicleClassDescriptionEntity;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.RTAHSRPStatus;
import org.rta.core.enums.RegistrationCategoryType;
import org.rta.core.enums.Status;
import org.rta.core.helper.hsrp.HSRPDetailHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.hsrp.Data;
import org.rta.core.model.hsrp.HSRPDetailModel;
import org.rta.core.model.hsrp.HSRPRTADetailModel;
import org.rta.core.model.payment.registfee.FeeRuleModel;
import org.rta.core.model.payment.registfee.HSRPModel;
import org.rta.core.service.hsrp.HSRPDetailService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HSRPDetailServiceImpl implements HSRPDetailService {
	private static final Logger log = Logger.getLogger(HSRPDetailServiceImpl.class);

	@Autowired
	private HSRPDetailDAO hsrpDetailDAO;
	@Autowired
	private VehicleDetailsDAO vehicleDetailsDAO;
	@Autowired
	private HSRPTransactionDAO hsrpTransactionDAO;
	@Autowired
	private HSRPDetailHistoryDAO hsrpDetailHistoryDAO;
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private CustomerCorporateDAO CustomerCorporateDAO;
	@Value("${hsrp.securitykey}")
	String securitykey;
	@Autowired
	private VehicleDAO vehicleDAO;
	@Autowired
	private VehicleClassDescDAO vehicleClassDescDAO;
	@Autowired
	private AffixationCenterMasterDAO affixationCenterMasterDAO;
	@Autowired
	private HSRPDetailHelper hsrpDetailHelper;
	@Autowired
	private HSRPFeeDetailsDAO hsrpFeeDetailsDAO;

	@Override
	@Transactional
	public SaveUpdateResponse confirmPaymentOfHSRP(HSRPDetailModel hsrpDetailModel) {
		log.info(":::::::confirmPaymentOfHSRP::::start:::::::");
		SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
		saveUpdateResponse.setStatus("1");
		if (hsrpDetailModel == null) {
			saveUpdateResponse.setMessage("No Data Found !");
			return saveUpdateResponse;
		}
		HSRPDetailEntity persistHSRPDetailEntity = hsrpDetailDAO.getByAuthRefNo(hsrpDetailModel);
		if (persistHSRPDetailEntity == null) {
			saveUpdateResponse.setMessage("Invalid AuthRefNo !");
			return saveUpdateResponse;
		}
		
		if (!hsrpDetailModel.getTransactionNo().equalsIgnoreCase(persistHSRPDetailEntity.getTransactionNo())) {
			saveUpdateResponse.setMessage("Invalid Transaction Number !");
			return saveUpdateResponse;
		}
		if (StringsUtil.isNullOrEmpty(hsrpDetailModel.getOrderNo())) {
			saveUpdateResponse.setMessage("Order No is required !");
			return saveUpdateResponse;
		}
		if (hsrpDetailModel.getTransactionStatus().equalsIgnoreCase("PR")) {
			persistHSRPDetailEntity.setHsrp_status(RTAHSRPStatus.PAYMENT_RECIEVE.getValue());
		} else {
			persistHSRPDetailEntity.setHsrp_status(RTAHSRPStatus.PAYMENT_DECLINED.getValue());
			persistHSRPDetailEntity.setStatus(Status.PENDING.getValue());
		}
		if (StringsUtil.isNullOrEmpty(hsrpDetailModel.getOrderDate())) {
			saveUpdateResponse.setMessage("Order date is required !");
			return saveUpdateResponse;
		}
		if (StringsUtil.isNullOrEmpty(hsrpDetailModel.getPaymentReceivedDate())
				|| (!DateUtil.isSameOrGreaterDate(DateUtil.toCurrentUTCTimeStamp(),
						DateUtil.dateFormater(hsrpDetailModel.getPaymentReceivedDate())))) {
			saveUpdateResponse.setMessage("Payment Received Date is Incorrect !");
			return saveUpdateResponse;
		}
		persistHSRPDetailEntity.setTransactionNo(hsrpDetailModel.getTransactionNo());
		persistHSRPDetailEntity.setTransactionStatus(hsrpDetailModel.getTransactionStatus());
		persistHSRPDetailEntity.setPaymentRecieveDate(DateUtil.dateFormater(hsrpDetailModel.getPaymentReceivedDate()));
		persistHSRPDetailEntity.setOrderNo(hsrpDetailModel.getOrderNo());
		persistHSRPDetailEntity.setOrderDate(DateUtil.dateFormater(hsrpDetailModel.getOrderDate()));
		persistHSRPDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		hsrpDetailDAO.saveOrUpdate(persistHSRPDetailEntity);

		HSRPDetailHistoryEntity hsrpDetailHistoryEntity = new HSRPDetailHistoryEntity();
		hsrpDetailHistoryEntity.setAmount(hsrpDetailModel.getAmount());
		hsrpDetailHistoryEntity.setAuthRefNo(persistHSRPDetailEntity.getAuthRefNo());
		hsrpDetailHistoryEntity.setHsrp_detail(persistHSRPDetailEntity);
		hsrpDetailHistoryEntity.setTransactionNo(hsrpDetailModel.getTransactionNo());
		hsrpDetailHistoryEntity.setVehicleRc(persistHSRPDetailEntity.getVehicleRcId());
		hsrpDetailHistoryEntity.setTransactionStatus(hsrpDetailModel.getTransactionStatus());
		hsrpDetailHistoryEntity.setOrderNo(hsrpDetailModel.getOrderNo());
		hsrpDetailHistoryEntity.setStatus(persistHSRPDetailEntity.getHsrp_status());
		hsrpDetailHistoryDAO.save(hsrpDetailHistoryEntity);
		saveUpdateResponse.setStatus("0");
		saveUpdateResponse.setMessage("Save successfully");
		log.debug(":::::::confirmAffixation::::end:::::::");
		return saveUpdateResponse;
	}

	@Override
	@Transactional
	public SaveUpdateResponse updateHSRPLaserCodes(HSRPDetailModel hsrpDetailModel) {
		log.info(":::::::updateHSRPLaserCodes::::start:::::::");
		SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
		saveUpdateResponse.setStatus("1");
		if (hsrpDetailModel == null) {
			saveUpdateResponse.setMessage("Data Not Found !");
			return saveUpdateResponse;
		}
		HSRPDetailEntity persistHSRPDetailEntity = hsrpDetailDAO.getByAuthRefNo(hsrpDetailModel);
		if (persistHSRPDetailEntity == null) {
			saveUpdateResponse.setMessage("Invalid AuthRefNo !");
			return saveUpdateResponse;
		}
		if (persistHSRPDetailEntity.getHsrp_status() == null
				|| persistHSRPDetailEntity.getHsrp_status() == RTAHSRPStatus.PAYMENT_DECLINED.getValue()) {
			saveUpdateResponse.setMessage("Payment Confirmation Pending for this AuthRefno !");
			return saveUpdateResponse;
		}
		if (StringsUtil.isNullOrEmpty(hsrpDetailModel.getFrontLaserCode())
				|| StringsUtil.isNullOrEmpty(hsrpDetailModel.getRearLaserCode())) {
			saveUpdateResponse.setMessage("Front Lase Code Or Rear Laser Code Not Found !");
			return saveUpdateResponse;
		}
		if (StringsUtil.isNullOrEmpty(hsrpDetailModel.getEmbossingDate())
				|| (DateUtil.isSameOrGreaterDate(DateUtil.toCurrentUTCTimeStamp(),
						DateUtil.dateFormater(hsrpDetailModel.getEmbossingDate())))) {
			saveUpdateResponse.setMessage("Embossing Date Incorrect !");
			return saveUpdateResponse;
		}
		persistHSRPDetailEntity.setFrontLaserCode(hsrpDetailModel.getFrontLaserCode());
		persistHSRPDetailEntity.setRearLaserCode(hsrpDetailModel.getRearLaserCode());
		persistHSRPDetailEntity.setEmbossingDate(DateUtil.dateFormater(hsrpDetailModel.getEmbossingDate()));
		persistHSRPDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		persistHSRPDetailEntity.setHsrp_status(RTAHSRPStatus.EMB_DATE_CREATE.getValue());
		hsrpDetailDAO.saveOrUpdate(persistHSRPDetailEntity);

		HSRPDetailHistoryEntity hsrpDetailHistoryEntity = new HSRPDetailHistoryEntity();
		hsrpDetailHistoryEntity.setAuthRefNo(persistHSRPDetailEntity.getAuthRefNo());
		hsrpDetailHistoryEntity.setHsrp_detail(persistHSRPDetailEntity);
		hsrpDetailHistoryEntity.setVehicleRc(persistHSRPDetailEntity.getVehicleRcId());
		hsrpDetailHistoryEntity.setStatus(RTAHSRPStatus.EMB_DATE_CREATE.getValue());
		hsrpDetailHistoryDAO.save(hsrpDetailHistoryEntity);
		saveUpdateResponse.setStatus("0");
		saveUpdateResponse.setMessage("Save successfully");
		log.debug(":::::::updateHSRPLaserCodes::::end:::::::");
		return saveUpdateResponse;
	}

	@Override
	@Transactional
	public SaveUpdateResponse notifyAffixation(HSRPDetailModel hsrpDetailModel) {
		log.info(":::::::notifyAffixation::::start:::::::");
		SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
		saveUpdateResponse.setStatus("1");
		if (hsrpDetailModel == null) {
			saveUpdateResponse.setMessage("Data Not Found !");
			return saveUpdateResponse;
		}
		HSRPDetailEntity persistHSRPDetailEntity = hsrpDetailDAO.getByAuthRefNo(hsrpDetailModel);
		if (persistHSRPDetailEntity == null) {
			saveUpdateResponse.setMessage("Invalid AuthRefNo !");
			return saveUpdateResponse;
		}
		if (persistHSRPDetailEntity.getHsrp_status() == null
				|| persistHSRPDetailEntity.getHsrp_status() == RTAHSRPStatus.PAYMENT_DECLINED.getValue()) {
			saveUpdateResponse.setMessage("Payment Confirmation Pending for this AuthRefno !");
			return saveUpdateResponse;
		}
		if (StringsUtil.isNullOrEmpty(hsrpDetailModel.getHsrpAvailabilityDate())
				|| (DateUtil.isSameOrGreaterDate(DateUtil.toCurrentUTCTimeStamp(),
						DateUtil.dateFormater(hsrpDetailModel.getHsrpAvailabilityDate())))) {
			saveUpdateResponse.setMessage("Hsrp Availability Date is Incorrect !");
			return saveUpdateResponse;
		}
		if (StringsUtil.isNullOrEmpty(hsrpDetailModel.getAffixationCenterCode())) {
			saveUpdateResponse.setMessage("Affixation Center Code Not Found !");
			return saveUpdateResponse;
		}
		persistHSRPDetailEntity
				.setHsrpAvailabilityDate(DateUtil.dateFormater(hsrpDetailModel.getHsrpAvailabilityDate()));
		persistHSRPDetailEntity.setAffixationCenterCode(hsrpDetailModel.getAffixationCenterCode());
		persistHSRPDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		persistHSRPDetailEntity.setHsrp_status(RTAHSRPStatus.PLATE_AVAIL.getValue());
		hsrpDetailDAO.saveOrUpdate(persistHSRPDetailEntity);

		HSRPDetailHistoryEntity hsrpDetailHistoryEntity = new HSRPDetailHistoryEntity();
		hsrpDetailHistoryEntity.setAuthRefNo(persistHSRPDetailEntity.getAuthRefNo());
		hsrpDetailHistoryEntity.setHsrp_detail(persistHSRPDetailEntity);
		hsrpDetailHistoryEntity.setVehicleRc(persistHSRPDetailEntity.getVehicleRcId());
		hsrpDetailHistoryEntity.setStatus(RTAHSRPStatus.PLATE_AVAIL.getValue());
		hsrpDetailHistoryDAO.save(hsrpDetailHistoryEntity);
		saveUpdateResponse.setStatus("0");
		saveUpdateResponse.setMessage("Save successfully");
		log.debug(":::::::notifyAffixation::::end:::::::");
		return saveUpdateResponse;
	}

	@Override
	@Transactional
	public SaveUpdateResponse confirmAffixation(HSRPDetailModel hsrpDetailModel) {
		log.info(":::::::confirmAffixation::::start:::::::");
		SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
		saveUpdateResponse.setStatus("1");
		if (hsrpDetailModel == null) {
			saveUpdateResponse.setMessage("Data Not Found !");
			return saveUpdateResponse;
		}
		HSRPDetailEntity persistHSRPDetailEntity = hsrpDetailDAO.getByAuthRefNo(hsrpDetailModel);
		if (persistHSRPDetailEntity == null) {
			saveUpdateResponse.setMessage("Invalid AuthRefNo !");
			return saveUpdateResponse;
		}
		if (persistHSRPDetailEntity.getHsrp_status() == null
				|| persistHSRPDetailEntity.getHsrp_status() == RTAHSRPStatus.PAYMENT_DECLINED.getValue()) {
			saveUpdateResponse.setMessage("Payment Confirmation Pending for this AuthRefno !");
			return saveUpdateResponse;
		}
		if (StringsUtil.isNullOrEmpty(hsrpDetailModel.getAffixationDate())
				|| (!DateUtil.isSameOrGreaterDate(DateUtil.toCurrentUTCTimeStamp(),
						DateUtil.dateFormater(hsrpDetailModel.getAffixationDate())))) {
			saveUpdateResponse.setMessage("Affixation Date is Incorrect !");
			return saveUpdateResponse;
		}
		if (StringsUtil.isNullOrEmpty(hsrpDetailModel.getAffixationCenterCode())) {
			saveUpdateResponse.setMessage("Affixation Center Code Not Found !");
			return saveUpdateResponse;
		}
		persistHSRPDetailEntity.setAffixationDate(DateUtil.dateFormater(hsrpDetailModel.getAffixationDate()));
		persistHSRPDetailEntity.setAffixationCenterCode(hsrpDetailModel.getAffixationCenterCode());
		persistHSRPDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		persistHSRPDetailEntity.setHsrp_status(RTAHSRPStatus.CLOSED.getValue());
		hsrpDetailDAO.saveOrUpdate(persistHSRPDetailEntity);

		HSRPDetailHistoryEntity hsrpDetailHistoryEntity = new HSRPDetailHistoryEntity();
		hsrpDetailHistoryEntity.setAuthRefNo(persistHSRPDetailEntity.getAuthRefNo());
		hsrpDetailHistoryEntity.setHsrp_detail(persistHSRPDetailEntity);
		hsrpDetailHistoryEntity.setVehicleRc(persistHSRPDetailEntity.getVehicleRcId());
		hsrpDetailHistoryEntity.setStatus(RTAHSRPStatus.CLOSED.getValue());
		hsrpDetailHistoryDAO.save(hsrpDetailHistoryEntity);
		saveUpdateResponse.setStatus("0");
		saveUpdateResponse.setMessage("Save successfully");
		log.info(":::::::confirmAffixation::::end:::::::");
		return saveUpdateResponse;
	}

	@Override
	@Transactional
	public void saveHSRPDetail(Long vehicleRcId, String userName) {
		log.debug("::::::::::::saveHSRPDetail:::::start:::::::" + vehicleRcId);
		VehicleDetailsEntity vehicleDetailEntity = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
		HSRPTransactionEntity hsrpTransactionEntity = hsrpTransactionDAO.getByVehicleRcId(vehicleRcId);
		if (vehicleDetailEntity == null && hsrpTransactionEntity == null)
			throw new IllegalArgumentException("Invalid vehicleRcId !");
		HSRPDetailEntity hsrpDetailEntity = new HSRPDetailEntity();
		hsrpDetailEntity = hsrpDetailDAO.getByVehicleRcId(vehicleRcId);
		if (hsrpDetailEntity != null) {
			hsrpDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			hsrpDetailEntity.setModifiedBy(userName);
		} else {
			hsrpDetailEntity = new HSRPDetailEntity();
			hsrpDetailEntity.setHsrpTransaction(hsrpTransactionEntity.getHsrpTransactionId());
			hsrpDetailEntity.setVehicleRcId(vehicleDetailEntity.getVehicleRcId());
			hsrpDetailEntity.setAuthRefNo(getAuthRefNo(hsrpTransactionEntity));
			hsrpDetailEntity.setAffixationCenterCode(getAffixationCenterCode(vehicleDetailEntity.getVehicleRcId()));
			hsrpDetailEntity.setRta_status(Status.OPEN.getValue());
			hsrpDetailEntity.setStatus(Status.OPEN.getValue());
			hsrpDetailEntity.setTransactionNo(hsrpTransactionEntity.getOtherChargesTID());
			hsrpDetailEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			hsrpDetailEntity.setCreatedBy(userName);
			hsrpDetailEntity.setIteration(0);
		}
		hsrpDetailDAO.saveOrUpdate(hsrpDetailEntity);
	}

    public String getAuthRefNo(HSRPTransactionEntity hsrpTransactionEntity) {
		log.info(":::::::getAuthRefNo::::::start::");
		Long currentDate = DateUtil.toCurrentUTCTimeStamp();
        String authRefNo = "HSRPRTA" + hsrpTransactionEntity.getHsrpTransactionId() + currentDate.toString();
		log.info(":::::::getAuthRefNo::::::ens::" + authRefNo);
		return authRefNo;
	}

	public Data convertEntityToRTADetailModel(VehicleDetailsEntity vehicleDetailEntity,
			HSRPTransactionEntity hsrpTransactionEntity, HSRPDetailModel hsrpDetailModel,
			VehicleClassDescriptionEntity vehicleClassDescriptionEntity) {
		log.info(":::::::::convertEntityToRTADetailModel::::::::::start::::::");
		Data data = new Data();
		data.setRtoCode(vehicleDetailEntity.getVehicleRcId().getRtaOfficeId().getCode());
		data.setRtoName(vehicleDetailEntity.getVehicleRcId().getRtaOfficeId().getName());
		data.setAffixationCenterCode(hsrpDetailModel.getAffixationCenterCode());
		data.setTransactionNo(hsrpTransactionEntity.getOtherChargesTID());
		data.setTransactionDate(DateUtil.extractDateAsString(hsrpTransactionEntity.getCreatedOn()));
		data.setEngineNo(vehicleDetailEntity.getEngineNo());
		data.setChassisNo(vehicleDetailEntity.getVehicleRcId().getChassisNumber());
		data.setVehicleType(vehicleClassDescriptionEntity.getDescription());// MOTOR CAR
		switch(RegistrationCategoryType.getRegistrationCategoryType(vehicleDetailEntity.getVehicleRcId().getRegCategory().getRegistrationCategoryId())){
		case NON_TRANSPORT:
			data.setVehicleClassType("N");
			break;
		case TRANSPORT:
			data.setVehicleClassType("T");
			break;
		}
		data.setMfrsName(vehicleDetailEntity.getMfgDate());
		data.setModelName(vehicleDetailEntity.getMakerClass());
		data.setHsrpFee(String.valueOf(hsrpTransactionEntity.getAmount()));
		data.setGovtVehicleTag("");
		data.setTransType("NB");
		data.setOldNewFlag("N");
		data.setTimeStamp(DateUtil.extractDateAsString(DateUtil.toCurrentUTCTimeStamp()));
		data.setTrNumber(vehicleDetailEntity.getVehicleRcId().getTrNumber());
		data.setPrNumber("");
		data.setDealerMail(vehicleDetailEntity.getVehicleRcId().getUserId().getEmail());
		data.setDealerName(vehicleDetailEntity.getVehicleRcId().getUserId().getFirstName() + " "
				+ vehicleDetailEntity.getVehicleRcId().getUserId().getLastName());
		data.setDealerRtoCode(vehicleDetailEntity.getVehicleRcId().getUserId().getUserName());
		data.setAuthorizationRefNo(hsrpDetailModel.getAuthRefNo());
		data.setAuthorizationDate(DateUtil.extractDateAsString(hsrpDetailModel.getCreatedOn()));
		log.info(":::::::::convertEntityToRTADetailModel::::::::::end::::::" + data);
		return data;
	}

	public Data convertCustomerIndEntityToModel(AddressEntity addressEntity,
			CustIndividualDetailsEntity custIndividualDetailsEntity, Data data) {
		log.info(":::::::::convertCustomerIndEntityToModel::::::::::start::::::");
		data.setOwnerName(custIndividualDetailsEntity.getFirstName() + " " + custIndividualDetailsEntity.getSurName());
		data.setOwnerEmailId(custIndividualDetailsEntity.getEmailId());
		data.setMobileNo(custIndividualDetailsEntity.getMobileNo());
		data.setOwnerAddress(getAddress(addressEntity));
		data.setOwnerPinCode(addressEntity.getPinCode());
		log.info(":::::::::convertCustomerIndEntityToModel::::::::::end::::::");
		return data;
	}

	public Data convertCustomerCorpEntityToModel(AddressEntity addressEntity,
			CustCorporateDetailsEntity custCorporateDetailsEntity, Data data) {
		log.info(":::::::::convertCustomerCorpEntityToModel::::::::::start::::::");
		data.setOwnerName(custCorporateDetailsEntity.getCompanyName());
		data.setOwnerEmailId(custCorporateDetailsEntity.getEmail());
		data.setMobileNo(custCorporateDetailsEntity.getMobile());
		data.setOwnerAddress(getAddress(addressEntity));
		data.setOwnerPinCode(addressEntity.getPinCode());
		log.info(":::::::::convertCustomerCorpEntityToModel::::::::::end::::::");
		return data;
	}

	public String getAddress(AddressEntity addressEntity) {
		log.info("::::::::::::address start:::::::::::::");
		MandalEntity mandalEntity = addressEntity.getMandal();
		DistrictEntity districtEntity = mandalEntity.getDistrictEntity();
		StateEntity stateEntity = districtEntity.getStateEntity();
		log.info("::::mandalEntity" + mandalEntity + "-districtEntity-" + districtEntity + "-stateEntity-" + stateEntity
				+ "addressEntity.getCity() " + addressEntity.getCity());
		StringBuilder sb = new StringBuilder(addressEntity.getDoorNo());
		StringsUtil.appendIfNotNull(sb, addressEntity.getStreet());
		StringsUtil.appendIfNotNull(sb, addressEntity.getCity());
		StringsUtil.appendIfNotNull(sb, mandalEntity.getName());
		StringsUtil.appendIfNotNull(sb, districtEntity.getName());
		StringsUtil.appendIfNotNull(sb, stateEntity.getName());
		log.info("::::::::::::address end:::::::::::::" + sb.toString());
		return sb.toString().replace(",", "");
	}

	@Override
	@Transactional
	public void updateHSRPTRStatus(Long vehicleRcId, Integer status, String message) {
		log.debug("::::::::::updateHSRPTRStatus::::::start:::::::::");
		HSRPDetailEntity hsrpDetailEntity = hsrpDetailDAO.getByVehicleRcId(vehicleRcId);
		if (status == 0) {
			hsrpDetailEntity.setRta_status(RTAHSRPStatus.TR_POST.getValue());
			hsrpDetailEntity.setIteration(0);
		} else {
			hsrpDetailEntity.setRta_status(Status.OPEN.getValue());
			hsrpDetailEntity.setIteration(hsrpDetailEntity.getIteration() + 1);
		}
		hsrpDetailEntity.setMessage(message);
		hsrpDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		hsrpDetailDAO.saveOrUpdate(hsrpDetailEntity);
		log.debug("::::::::::updateHSRPTRStatus::::::end:::::::::");
	}

	@Override
	@Transactional
	public void updateHSRPPRStatus(Long vehicleRcId, Integer status, String message) {
		log.info("::::::::::updateHSRPPRStatus::::start:::::::::::");
		HSRPDetailEntity hsrpDetailEntity = hsrpDetailDAO.getByVehicleRcId(vehicleRcId);
		if (status == 0) {
			hsrpDetailEntity.setRta_status(RTAHSRPStatus.PR_POST.getValue());
			hsrpDetailEntity.setIteration(0);
		} else {
			hsrpDetailEntity.setRta_status(RTAHSRPStatus.TR_POST.getValue());
			hsrpDetailEntity.setIteration(hsrpDetailEntity.getIteration() + 1);
		}
		hsrpDetailEntity.setMessage(message);
		hsrpDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		hsrpDetailDAO.saveOrUpdate(hsrpDetailEntity);
		log.info("::::::::::updateHSRPPRStatus::::end:::::::::::");
	}

	private String getAffixationCenterCode(VehicleRCEntity vehicleRCEntity) {
		log.info("::::::::::getAffixationCenterCode::::::::::::" + vehicleRCEntity.getVehicleRcId() + " ownershipType "
				+ vehicleRCEntity.getOwnershipType());
		AddressEntity addressEntity = new AddressEntity();
		MandalEntity mandalEntity = new MandalEntity();
		String affixationCenterCode = null;
		switch (OwnershipType.getOwnershipType(vehicleRCEntity.getOwnershipType())) {
		case POLICE :
		case COMPANY:
		case STU_VEHICLES :
		case ORGANIZATION:
			log.info(":::getAffixationCenterCode::::Company:::::");
			CustCorporateDetailsEntity custCorp = CustomerCorporateDAO
					.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
			addressEntity = CustomerCorporateDAO.getAddressDetailsByUserId(custCorp.getCustCorpDtlsId(), "T");
			mandalEntity = addressEntity.getMandal();
			if(addressEntity.getDistrict() != null)
			affixationCenterCode = affixationCenterMasterDAO.getAffixationCenterMaster(mandalEntity.getCode(),addressEntity.getDistrict().intValue());
			break;
		case INDIVIDUAL:
			log.info(":::getAffixationCenterCode::::Indiv:::::");
			CustIndividualDetailsEntity custIndi = customerDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
			addressEntity = customerDAO.getAddressDetailsByUserId(custIndi.getCustIndDtlId(), "T");
			mandalEntity = addressEntity.getMandal();
			if(addressEntity.getDistrict() != null)
			affixationCenterCode = affixationCenterMasterDAO.getAffixationCenterMaster(mandalEntity.getCode(),addressEntity.getDistrict().intValue());
			break;
		}
		if (affixationCenterCode == null)
			affixationCenterCode = vehicleRCEntity.getRtaOfficeId().getCode();
		log.info(":::getAffixationCenterCode::::end::::: " + affixationCenterCode);
		return affixationCenterCode;
	}

	@Override
	@Transactional
	public HSRPRTADetailModel getTRData(HSRPDetailModel hsrpDetailModel) {
		log.info("::::::::::::getTRData:::::start:::::::" + hsrpDetailModel);
		Data data = new Data();
		HSRPRTADetailModel hsrprtaDetailModel = new HSRPRTADetailModel();
		VehicleDetailsEntity vehicleDetailEntity = vehicleDetailsDAO.getByVehicleRcId(hsrpDetailModel.getVehicleRcId());
		HSRPTransactionEntity hsrpTransactionEntity = hsrpTransactionDAO
				.getByVehicleRcId(hsrpDetailModel.getVehicleRcId());
		if (vehicleDetailEntity == null && hsrpTransactionEntity == null)
			throw new IllegalArgumentException("Invalid vehicleRcId !");
		// sandeep
		VehicleClassDescriptionEntity vehicleClassDescriptionEntity = vehicleClassDescDAO
				.getByVehiclClassDesCode(vehicleDetailEntity.getVehicleSubClass());
		log.info("::::HSRP:::vehicleClassDescriptionEntity::::" + vehicleClassDescriptionEntity);

		data = convertEntityToRTADetailModel(vehicleDetailEntity, hsrpTransactionEntity, hsrpDetailModel,
				vehicleClassDescriptionEntity);
		AddressEntity addressEntity = new AddressEntity();
		switch (OwnershipType.getOwnershipType(vehicleDetailEntity.getVehicleRcId().getOwnershipType())) {
		case POLICE :
		case COMPANY:
		case STU_VEHICLES :
		case ORGANIZATION:
			CustCorporateDetailsEntity custCorporateDetailsEntity = CustomerCorporateDAO
					.getByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId());
			addressEntity = CustomerCorporateDAO
					.getAddressDetailsByUserId(custCorporateDetailsEntity.getCustCorpDtlsId(), "T");
			data = convertCustomerCorpEntityToModel(addressEntity, custCorporateDetailsEntity, data);
			data.setAffixationCenterCode(checkAffixiationCenterOffice(addressEntity,hsrpDetailModel));
			break;
		case INDIVIDUAL:
			CustIndividualDetailsEntity custIndividualDetailsEntity = customerDAO
					.getByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId());
			addressEntity = customerDAO.getAddressDetailsByUserId(custIndividualDetailsEntity.getCustIndDtlId(), "T");
			data = convertCustomerIndEntityToModel(addressEntity, custIndividualDetailsEntity, data);
			data.setAffixationCenterCode(checkAffixiationCenterOffice(addressEntity,hsrpDetailModel));
			break;
		}
		List<Data> dataList = new ArrayList<Data>();
		log.info("::::HSRP DATA:::: " + data);
		dataList.add(data);
		hsrprtaDetailModel.setData(dataList);
		hsrprtaDetailModel.setSecurityKey(securitykey);
		log.info(":::::::::::::getTRData:::::end:::::::::" + hsrprtaDetailModel);
		return hsrprtaDetailModel;
	}
	
	private String checkAffixiationCenterOffice(AddressEntity addressEntity,HSRPDetailModel hsrpDetailModel){
		log.info("::::checkAffixiationOfficeUpdate:::start::: ");
		String affixationCenterCode = null;
		String persistAffixationCenterCode = hsrpDetailModel.getAffixationCenterCode();
		if(addressEntity.getDistrict() != null)
		affixationCenterCode = affixationCenterMasterDAO.getAffixationCenterMaster(addressEntity.getMandal().getCode(),addressEntity.getDistrict().intValue());
		if(affixationCenterCode != null && !affixationCenterCode.equalsIgnoreCase(hsrpDetailModel.getAffixationCenterCode())){
			HSRPDetailEntity hsrpDetailEntity = hsrpDetailDAO.getByVehicleRcId(hsrpDetailModel.getVehicleRcId());
			hsrpDetailEntity.setAffixationCenterCode(affixationCenterCode);
			hsrpDetailDAO.saveOrUpdate(hsrpDetailEntity);
			persistAffixationCenterCode = affixationCenterCode;
			log.info("::::checkAffixiationOfficeUpdate:::Mismatch::: ");
		}
		log.info("::::checkAffixiationOfficeUpdate:::start::: ");
		return persistAffixationCenterCode;
	}

	@Override
	@Transactional
	public List<HSRPDetailModel> getAllOpenStatusData(Long from , Long to) {
		log.info("::::::::::getAllOpenStatusData:::::::::::");
		List<HSRPDetailEntity> hsrpDetailList = hsrpDetailDAO.getAllOpenStatusData(from,to);
		return (List<HSRPDetailModel>) hsrpDetailHelper.convertToModelList(hsrpDetailList);
	}

	@Override
	@Transactional
	public List<HSRPDetailModel> getAllTRPostData(Long from , Long to) {
		log.info("::::::::::getAllTRPostData:::::::::::");
		List<HSRPDetailEntity> hsrpDetailList = hsrpDetailDAO.getAllTRPostData(from ,to);
		return (List<HSRPDetailModel>) hsrpDetailHelper.convertToModelList(hsrpDetailList);
	}

	@Override
	@Transactional
	public HSRPRTADetailModel getPRData(HSRPDetailModel hsrpDetailModel) {
		log.info(":::::::::::::getPRData::::::start:::::::");
		VehicleRCEntity vehicleRcEntity = vehicleDAO.get(hsrpDetailModel.getVehicleRcId());
		HSRPRTADetailModel hsrprtaDetailModel = new HSRPRTADetailModel();
		Data data = new Data();
		data.setPrNumber(vehicleRcEntity.getPrNumber());
		data.setRegDate(DateUtil.extractDateAsString(vehicleRcEntity.getPrIssueTime()));
		data.setAuthorizationRefNo(hsrpDetailModel.getAuthRefNo());
		List<Data> dataList = new ArrayList<Data>();
		log.info("::::HSRP PR DATA:::: " + data);
		dataList.add(data);
		hsrprtaDetailModel.setData(dataList);
		hsrprtaDetailModel.setSecurityKey(securitykey);
		log.info(":::::::::::::getPRData::::::end:::::::");
		return hsrprtaDetailModel;
	}

	@Override
	public HSRPModel saveHSRP(String userName, FeeRuleModel feeRuleModel, VehicleDetailsEntity vehicleDetailEntity) {
		log.info("::saveHSRP::: "+ feeRuleModel.getHsrpAmount());
		HSRPFeeDetailsEntity hsrpFeeDetailsEntity = null;
		hsrpFeeDetailsEntity = hsrpFeeDetailsDAO.getByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId());
		if(hsrpFeeDetailsEntity != null){
			hsrpFeeDetailsEntity.setHSRPFee(feeRuleModel.getHsrpAmount());
			hsrpFeeDetailsEntity.setModifiedBy(userName);
			hsrpFeeDetailsEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		}else{
			hsrpFeeDetailsEntity = new HSRPFeeDetailsEntity();
			hsrpFeeDetailsEntity.setCreatedBy(userName);
			hsrpFeeDetailsEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			hsrpFeeDetailsEntity.setHSRPFee(feeRuleModel.getHsrpAmount());
			hsrpFeeDetailsEntity.setRegType(vehicleDetailEntity.getVehicleRcId().getRegCategory().getRegistrationCategoryId());
			hsrpFeeDetailsEntity.setVehicleRc(vehicleDetailEntity.getVehicleRcId());
		}
		hsrpFeeDetailsDAO.saveOrUpdate(hsrpFeeDetailsEntity);
		HSRPModel hsrpModel = new HSRPModel();
		hsrpModel.setHsrpAmount(hsrpFeeDetailsEntity.getHSRPFee());
		return hsrpModel;
	}

	
	@Override
	public HSRPModel viewHSRP(String userName, VehicleDetailsEntity vehicleDetailEntity) {
		HSRPFeeDetailsEntity hsrpFeeDetailsEntity = null;
		HSRPModel hsrpModel = new HSRPModel();
		hsrpFeeDetailsEntity = hsrpFeeDetailsDAO.getByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId());
		if(hsrpFeeDetailsEntity != null)
		hsrpModel.setHsrpAmount(hsrpFeeDetailsEntity.getHSRPFee());
		return hsrpModel;
	}

}
