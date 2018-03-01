package org.rta.registration.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.master.registration.ChallanNumberDAO;
import org.rta.core.dao.payment.cms.SBIDDOMasterDAO;
import org.rta.core.dao.payment.cms.SBIDistributionDAO;
import org.rta.core.dao.payment.gateway.TransactionDetailDAO;
import org.rta.core.dao.payment.gateway.TransactionHistoryDAO;
import org.rta.core.dao.vcr.VcrTransactionDetailDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.master.registration.ChallanNumberEntity;
import org.rta.core.entity.payment.cms.SBIDDOMasterEntity;
import org.rta.core.entity.payment.cms.SBIDistributionEntity;
import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.entity.payment.gateway.TransactionHistoryEntity;
import org.rta.core.entity.vcr.VcrTransactionDetailEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.PaymentGatewayType;
import org.rta.core.enums.PaymentType;
import org.rta.core.enums.SBIHeadType;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.enums.master.TaxType;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.hsrp.HSRPTransactionModel;
import org.rta.core.model.payment.gateway.PayUProductInfo;
import org.rta.core.model.payment.gateway.PayuPaymentPart;
import org.rta.core.model.payment.gateway.TransactionDetailModel;
import org.rta.core.model.payment.gateway.TreasureHeadDetails;
import org.rta.core.model.user.DealerModel;
import org.rta.core.service.payment.gateway.TransactionDetailService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.HashingUtil;
import org.rta.core.utils.NumberParser;
import org.rta.core.utils.ObjectsUtil;
import org.rta.registration.service.PaymentService;
import org.rta.registration.service.vcr.VcrService;
import org.rta.security.sbi.AESEncrypt;
import org.rta.security.sbi.ChecksumMD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PaymentServiceImpl implements PaymentService {

	private static final Logger log = Logger.getLogger(PaymentServiceImpl.class);

	private static final RestTemplate restTemplate = new RestTemplate();
	@Value("${rta.gateway.key}")
	private String sbiKeyPath;
	@Value("${rta.sbi.Dept_code}")
	public String deptCodeValue;
	@Value("${rta.sbi.Dept_Name}")
	public String deptName;

	@Value("${rta.sbi.verification_URL}")
	public String verification_redirect_URL;

	@Value("${regist.tnt.sbi.url}")
	public String regist_tnt_sbi_url;
	@Value("${regist.tnt.sbi.verify.url}")
	public String regist_tnt_sbi_verify_url;

	@Value("${citizen.change_of_address.sbi.url}")
	public String citizen_change_of_address_sbi_url;
	@Value("${citizen.change_of_address.sbi.verify.url}")
	public String citizen_change_of_address_sbi_verify_url;

	@Value("${citizen.duplicate_regist.sbi.url}")
	public String citizen_duplicate_regist_sbi_url;
	@Value("${citizen.duplicate_regist.sbi.verify.url}")
	public String citizen_duplicate_regist_sbi_verify_url;

	@Value("${citizen.hire_purchase_agreement.sbi.url}")
	public String citizen_hire_purchase_agreement_sbi_url;
	@Value("${citizen.hire_purchase_agreement.sbi.verify.url}")
	public String citizen_hire_purchase_agreement_sbi_verify_url;

	@Value("${citizen.hire_purchase.termination.sbi.url}")
	public String citizen_hire_purchase_termination_sbi_url;
	@Value("${citizen.hire_purchase.termination.sbi.verify.url}")
	public String citizen_hire_purchase_termination_sbi_verify_url;

	@Value("${citizen.issue_of_cc_noc.sbi.url}")
	public String citizen_issue_of_cc_noc_sbi_url;
	@Value("${citizen.issue_of_cc_noc.sbi.verify.url}")
	public String citizen_issue_of_cc_noc_sbi_verify_url;

	@Value("${citizen.cancellation_of_noc_cc.sbi.url}")
	public String citizen_cancellation_of_noc_cc_sbi_url;
	@Value("${citizen.cancellation_of_noc_cc.sbi.verify.url}")
	public String citizen_cancellation_of_noc_cc_sbi_verify_url;

	@Value("${citizen.alteration_of_vehicle.sbi.url}")
	public String citizen_alteration_of_vehicle_sbi_url;
	@Value("${citizen.alteration_of_vehicle.sbi.verify.url}")
	public String citizen_alteration_of_vehicle_sbi_verify_url;

	@Value("${citizen.reassignment_of_vehicle.sbi.url}")
	public String citizen_reassignment_of_vehicle_sbi_url;
	@Value("${citizen.reassignment_of_vehicle.sbi.verify.url}")
	public String citizen_reassignment_of_vehicle_sbi_verify_url;

	@Value("${citizen.renewal_of_regist.sbi.url}")
	public String citizen_renewal_of_regist_sbi_url;
	@Value("${citizen.renewal_of_regist.sbi.verify.url}")
	public String citizen_renewal_of_regist_sbi_verify_url;

	@Value("${citizen.theft_intimation.sbi.url}")
	public String citizen_theft_intimation_sbi_url;
	@Value("${citizen.theft_intimation.sbi.verify.url}")
	public String citizen_theft_intimation_sbi_verify_url;

	@Value("${citizen.cancellation_of_regist.sbi.url}")
	public String citizen_cancellation_of_regist_sbi_url;
	@Value("${citizen.cancellation_of_regist.sbi.verify.url}")
	public String citizen_cancellation_of_regist_sbi_verify_url;

	@Value("${citizen.revocation_of_suspension.sbi.url}")
	public String citizen_revocation_of_suspension_sbi_url;
	@Value("${citizen.revocation_of_suspension.sbi.verify.url}")
	public String citizen_revocation_of_suspension_sbi_verify_url;

	@Value("${citizen.fresh_rc_financer.sbi.url}")
	public String citizen_fresh_rc_financer_sbi_url;
	@Value("${citizen.fresh_rc_financer.sbi.verify.url}")
	public String citizen_fresh_rc_financer_sbi_verify_url;

	@Value("${citizen.diff.tax.sbi.url}")
	public String citizen_diff_tax_sbi_url;
	@Value("${citizen.diff.tax.sbi.verify.url}")
	public String citizen_diff_tax_sbi_verify_url;

	@Value("${citizen.pay.tax.sbi.url}")
	public String citizen_pay_tax_sbi_url;
	@Value("${citizen.pay.tax.sbi.verify.url}")
	public String citizen_pay_tax_sbi_verify_url;

	@Value("${citizen.llf.sbi.url}")
	public String citizen_llf_sbi_url;
	@Value("${citizen.llf.sbi.verify.url}")
	public String citizen_llf_sbi_verify_url;

	@Value("${citizen.tow-death.sbi.url}")
	public String citizen_tow_death_sbi_url;
	@Value("${citizen.tow-death.sbi.verify.url}")
	public String citizen_tow_death_sbi_verify_url;

	@Value("${citizen.tow-auction.sbi.url}")
	public String citizen_tow_auction_sbi_url;
	@Value("${citizen.tow-auction.sbi.verify.url}")
	public String citizen_tow_auction_sbi_verify_url;

	@Value("${citizen.tow-sale.sbi.url}")
	public String citizen_tow_sale_sbi_url;
	@Value("${citizen.tow-sale.sbi.verify.url}")
	public String citizen_tow_sale_sbi_verify_url;

	@Value("${citizen.userreg.sbi.url}")
	public String citizen_userreg_sbi_url;
	@Value("${citizen.userreg.sbi.verify.url}")
	public String citizen_userreg_sbi_verify_url;

	@Value("${citizen.fitness.sbi.url}")
	public String citizen_fitness_sbi_url;
	@Value("${citizen.fitness.sbi.verify.url}")
	public String citizen_fitness_sbi_verify_url;

	@Value("${citizen.permit.sbi.url}")
	public String citizen_permit_sbi_url;
	@Value("${citizen.permit.sbi.verify.url}")
	public String citizen_permit_sbi_verify_url;

	@Value("${citizen.drivinglicense.sbi.url}")
	public String citizen_drivinglicense_sbi_url;
	@Value("${citizen.drivinglicense.sbi.verify.url}")
	public String citizen_drivinglicense_sbi_verify_url;

	
	// Stoppage Tax Service
	@Value("${citizen.stoppage.tax.sbi.url}")
	public String citizen_stoppage_tax_sbi_url;
	@Value("${citizen.stoppage.tax.sbi.verify.url}")
	public String citizen_stoppage_tax_sbi_verify_url;
	
	@Value("${citizen.stoppage.tax.revocation.sbi.url}")
	public String citizen_stoppage_tax_revocation_sbi_url ;
	@Value("${citizen.stoppage.tax.revocation.sbi.verify.url}")
	public String citizen_stoppage_tax_revocation_sbi_verify_url;
		
	

	@Value("${sbi.MODEOFPAYMENT}")
	public String MODEOFPAYMENT;
	@Value("${rta.payment.testAmount}")
	public Boolean testAmount;
	@Value("${rta.sbi.verification_URL}")
	public String verification_URL;

	public static String Dept_code;
	public static String Dept_Name;
	public static String Name_of_the_Remitter;
	public static String Department_TransID_1;
	public static String Challan_No_1;
	public static String HOA_1;
	public static String HOA_1_Description;
	public static String Amount_1;
	public static String Credit_Account_1;
	public static String Department_TransID_2;
	public static String Challan_No_2;
	public static String HOA_2;
	public static String HOA_2_Description;
	public static String Amount_2;
	public static String Credit_Account_2;
	public static String Department_TransID_3;
	public static String Challan_No_3;
	public static String HOA_3;
	public static String HOA_3_Description;
	public static String Amount_3;
	public static String Credit_Account_3;
	public static String Department_TransID_4;
	public static String Challan_No_4;
	public static String HOA_4;
	public static String HOA_4_Description;
	public static String Amount_4;
	public static String Credit_Account_4;
	public static String Department_TransID_5;
	public static String Challan_No_5;
	public static String HOA_5;
	public static String HOA_5_Description;
	public static String Amount_5;
	public static String Credit_Account_5;
	public static String Other_Charges_TID_1;
	public static String Other_charges_Description_1;
	public static String Amount_6;
	public static String Credit_Account_6;
	public static String Other_Charges_TID_2;
	public static String Other_charges_Description_2;
	public static String Amount_7;
	public static String Credit_Account_7;
	public static String Check_Total;
	public static String DDO_Code;
	public static String Mode_of_Transaction;
	public static String Redirect_URL;
	@Autowired
	private VehicleDAO vehicleDAO;
	@Autowired
	private CustomerDAO custIndvDAO;
	@Autowired
	private ChallanNumberDAO challanNumberDAO;
	@Autowired
	private CustomerCorporateDAO customerCorporateDAO;
	@Autowired
	private TransactionDetailDAO transactionDetailDAO;
	@Autowired
	private TransactionHistoryDAO transactionHistoryDAO;
	@Autowired
	private SBIDDOMasterDAO sbiDDOMasterDAO;
	@Autowired
	private VehicleDetailsDAO vehicleDetailsDAO;

	@Autowired
	private SBIDistributionDAO sbiDistributionDAO;

	@Autowired
	public TransactionDetailService transactionDetailService;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${payment.payu.key}")
	private String payUKey;
	@Value("${payment.payu.salt}")
	private String payUSalt;
	@Value("${payment.payu.pos}")
	private String payUPos;
	@Value("${payment.payu.treasure.merchantid}")
	private String payUTreasureMerchantId;
	@Value("${payment.payu.hsrp.merchantid}")
	private String payUHsrpMerchantId;
	@Autowired
	private VcrTransactionDetailDAO vcrTransactionDetailDAO;
	@Autowired
	private VcrService VcrService;
	
	@Value("${citizen.payment.payu.key}")
	private String payUKey4citizen;
	@Value("${citizen.payment.payu.salt}")
	private String payUSalt4citizen;
	@Value("${citizen.payment.payu.treasure.merchantid}")
	private String payUTreasureMerchantId4Citizen;

	@Transactional
	@Override
	public TransactionDetailModel encryptedSBIParameter(TransactionDetailModel transactionDetailModel,
			HSRPTransactionModel hsrpTransactionModel) {
		log.debug("::::::::::::encryptedSBIParameter:::start:::::::::: ");
		transactionDetailModel.setServiceCode("RTNT");
		transactionDetailModel = getRequestParameter(transactionDetailModel, hsrpTransactionModel.getOtherChargesTID(),
				hsrpTransactionModel.getAmount());
		transactionDetailModel.setEncryptRequest(transactionDetailModel.getEncryptRequest());
		TransactionHistoryEntity transactionHistoryEntity = new TransactionHistoryEntity();
		transactionHistoryEntity.setRequestParameter(transactionDetailModel.getEncryptRequest());
		transactionHistoryEntity.setTransactionNo(transactionDetailModel.getTransactionNo());
		transactionHistoryEntity.setVehicleRcId(Long.parseLong(transactionDetailModel.getVehicleRcId()));
		transactionHistoryEntity.setStatus(Status.OPEN.getValue());
		transactionHistoryEntity.setPaymentType(PaymentType.PAYTAX.getId());
		transactionHistoryEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		transactionHistoryDAO.save(transactionHistoryEntity);
		
		return transactionDetailModel;
	}

	@Override
	@Transactional
	public TransactionDetailModel decryptSBIResponse(TransactionDetailModel transactionDetailModel) {
		String reqString = transactionDetailModel.getEncryptRequest();
		TransactionHistoryEntity transactionHistoryEntity = new TransactionHistoryEntity();
		transactionHistoryEntity.setResponseParameter(reqString);
		transactionHistoryEntity.setTransactionNo(transactionDetailModel.getTransactionNo());
		transactionHistoryEntity.setStatus(Status.CLOSED.getValue());
		transactionHistoryEntity.setPaymentType(PaymentType.FRESH_RC_FINANCE.getId());
		transactionHistoryEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		transactionHistoryDAO.save(transactionHistoryEntity);
		return getSBIResponseData(transactionDetailModel);
		

	}

	public TransactionDetailModel getSBIResponseData(TransactionDetailModel transactionDetailModel) {
		log.info("::::getSBIResponseData:::");
		AESEncrypt encrpt = new AESEncrypt();
		encrpt.setSecretKey(sbiKeyPath);
		String dData = encrpt.decryptFile(transactionDetailModel.getEncryptRequest());
		log.info("::::SBI RESPONSE DATA:::::::: " + dData);
		HashMap<String, String> mapValue = getMapValue(dData);
		if (mapValue.get("checkSum").equalsIgnoreCase(getChcekSum(dData))) {
			transactionDetailModel.setStatus("SUCCESS");
			transactionDetailModel.setSbiResponseMap(mapValue);
			return transactionDetailModel;
		} else {
			transactionDetailModel.setStatus("FAILURE");
			return transactionDetailModel;
		}
	}

	public String getChcekSum(String sbiRequest) {
		String checkdata = "";
		try {
			String[] sbiData = sbiRequest.split("checkSum");
			String sbiDataWithoutChecksum = sbiData[0].substring(0, sbiData[0].lastIndexOf("|"));
			ChecksumMD5 checksum = new ChecksumMD5();
			checkdata = checksum.getValue(sbiDataWithoutChecksum);
		} catch (Exception e) {
			log.error("::::::Error on getChecksum:::::" + e.getMessage());
		}
		return checkdata;
	}

	public HashMap<String, String> getMapValue(String request) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String[] entries = request.split("\\|");
		for (String entry : entries) {
			String[] keyValue = entry.split("=");
			if (keyValue.length == 1)
				continue;
			hashMap.put(keyValue[0], keyValue[1]);
		}
		return hashMap;
	}

	public HashMap<String, String> getVerificationMapValue(String request) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String[] entries = request.split("\\|");
		for (String entry : entries) {
			String[] keyValue = entry.split("=");
			if (keyValue.length == 1)
				continue;
			if (keyValue[0].equalsIgnoreCase("sbi_ref_id"))
				hashMap.put(keyValue[0].replace("sbi_ref_id", "sbi_ref_no"), keyValue[1]);
			if (keyValue[0].equalsIgnoreCase("Transaction_amount"))
				hashMap.put(keyValue[0].replace("Transaction_amount", "Check_Total"), keyValue[1]);
			hashMap.put(keyValue[0], keyValue[1]);
		}
		return hashMap;
	}

	public Long getChallanNumber() {
		log.info("::::::::getChallanNumber:::::::::");
		Long challanNo = 0l;
		ChallanNumberEntity challanNumberEntity = challanNumberDAO.getBytreasuryCode(deptCodeValue);
		Long consumeNo = challanNumberEntity.getConsumeNumber();
		challanNo = ++consumeNo;
		challanNumberEntity.setConsumeNumber(challanNo);
		challanNumberDAO.saveOrUpdate(challanNumberEntity);
		return challanNo;
	}

	@Transactional
	public TransactionDetailModel getRequestParameter(TransactionDetailModel transactionDetailModel,
			String hsrpTransactionId, double hsrptAmount) {
		log.info("::::::::::::::getRequestParameter::::start::::::::::::: " + transactionDetailModel.getServiceCode());
		String pollingAcTaxRegFee = "";
		String feeHead = "";
		String feeDesc = "";
		String lifeTaxHead = "";
		String lifeTaxDesc = "";
		String quartelyHead = "";
		String quartelyDesc = "";
		String serviceHead = "";
		String serviceDesc = "";
		String postalHead = "";
		String postalDesc = "";
		String hsrpDesc = "";
		String pollingACHSRP = "";
		String permitDesc = "";
		String permitHead = "";
		String HOA_2 = "";
		String HOA_2_Description = "";
		String remitterName = null;
		String greenTaxHead = "";
		String greenTaxDesc = "";
		String pollingACCess = "";
		String cessFeeDesc = "";
		SBIDDOMasterEntity sbiddoMasterEntity = new SBIDDOMasterEntity();
		List<SBIDistributionEntity> sbiDistributionList = sbiDistributionDAO.getAll();

		for (SBIDistributionEntity sbiDistribution : sbiDistributionList) {
			switch (SBIHeadType.getSBIHeadType(sbiDistribution.getHeadType())) {
			case FEE:
				feeHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				feeDesc = sbiDistribution.getHoaDescription();
				pollingAcTaxRegFee = sbiDistribution.getCreditPollingAccount();
				break;
			case LIFETAX:
				lifeTaxHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				lifeTaxDesc = sbiDistribution.getHoaDescription();
				break;
			case QUATERLYTAX:
				quartelyHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				quartelyDesc = sbiDistribution.getHoaDescription();
				break;
			case SERVICEFEE:
				serviceHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				serviceDesc = sbiDistribution.getHoaDescription();
				break;
			case POSTALFEE:
				postalHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				postalDesc = sbiDistribution.getHoaDescription();
				break;
			case HSRP:
				hsrpDesc = sbiDistribution.getHoaDescription();
				pollingACHSRP = sbiDistribution.getCreditPollingAccount();
				break;
			case PERMITFEE:
				permitHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				permitDesc = sbiDistribution.getHoaDescription();
				break;
			case GREENTAX:
				greenTaxHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				greenTaxDesc = sbiDistribution.getHoaDescription();
				break;
			case CESSFEE:
				cessFeeDesc = sbiDistribution.getHoaDescription();
				pollingACCess = sbiDistribution.getCreditPollingAccount();
				break;
			case COMPOUNDINGFEE:
                feeHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
                + sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
                + sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
                + sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
                feeDesc = sbiDistribution.getHoaDescription();
                pollingAcTaxRegFee = sbiDistribution.getCreditPollingAccount();
                break;
			}
		}

		if (transactionDetailModel.getNewApplicantFlag() != null && transactionDetailModel.getNewApplicantFlag()) {
			remitterName = transactionDetailModel.getRemiterName();
			sbiddoMasterEntity = sbiDDOMasterDAO.getByDistrictCode(transactionDetailModel.getDistrictCode());
		} else {
			VehicleRCEntity vehicleEntity = vehicleDAO.get(Long.valueOf(transactionDetailModel.getVehicleRcId()));
			VehicleDetailsEntity vehicleDetailsEntity = vehicleDetailsDAO
					.getByVehicleRcId(vehicleEntity.getVehicleRcId());
			sbiddoMasterEntity = sbiDDOMasterDAO.getByDistrictCode(vehicleEntity.getRtaOfficeId().getDistrictCode());
			CustIndividualDetailsEntity custDtlsEntity = new CustIndividualDetailsEntity();
			CustCorporateDetailsEntity custCorporateDetailsEntity = new CustCorporateDetailsEntity();
			switch (OwnershipType.getOwnershipType(vehicleEntity.getOwnershipType())) {
			case POLICE:
			case COMPANY:
			case STU_VEHICLES:
			case ORGANIZATION:	
				custCorporateDetailsEntity = customerCorporateDAO.getByVehicleRcId(vehicleEntity.getVehicleRcId());
				remitterName = custCorporateDetailsEntity.getCompanyName();
				if (remitterName.length() > 30)
					remitterName = remitterName.substring(0, 29);
				break;
			case INDIVIDUAL:
				custDtlsEntity = custIndvDAO.getByVehicleRcId(vehicleEntity.getVehicleRcId());
				remitterName = custDtlsEntity.getFirstName() + " " + custDtlsEntity.getSurName();
				if (remitterName.length() > 30)
					remitterName = remitterName.substring(0, 29);
				break;
			}
			switch (TaxType.getTaxType(vehicleDetailsEntity.getTaxTypeId().getTaxTypeId().intValue())) {
			case LIFE_TAX:
				HOA_2 = lifeTaxHead;
				HOA_2_Description = lifeTaxDesc;
				break;
			case FIRST_QUARTERLY:
				HOA_2 = quartelyHead;
				HOA_2_Description = quartelyDesc;
				break;
			}
		}
		String ddoCodevalue = sbiddoMasterEntity.getDdoCode();

		String departmentTransID = transactionDetailModel.getTransactionId().toString();
		String challanNo1 = transactionDetailModel.getTransactionNo() + 1;
		int amount1 = (int) transactionDetailModel.getFeeAmt();
		if (amount1 <= 0) {
            amount1 = (int) transactionDetailModel.getCompoundFee();
        }

		String challanNo2 = transactionDetailModel.getTransactionNo() + 2;
		int amount2 = (int) transactionDetailModel.getTaxAmt();

		String challanNo3 = transactionDetailModel.getTransactionNo() + 3;
		int amount3 = (int) transactionDetailModel.getServiceCharge();

		String challanNo4 = transactionDetailModel.getTransactionNo() + 4;
		int amount4 = (int) transactionDetailModel.getPostalCharge();

		int amount5 = 0;
		String permitGreenDesc = "";
		String permitGreenHead = "";
		log.info(":::GrenTax Amount::::::: " + transactionDetailModel.getGreenTaxAmt());
		String challanNo5 = transactionDetailModel.getTransactionNo() + 5;
		if ((int) transactionDetailModel.getPermitAmt() > 0) {
			amount5 = (int) transactionDetailModel.getPermitAmt();
			permitGreenDesc = permitDesc;
			permitGreenHead = permitHead;
		}
		if ((int) transactionDetailModel.getGreenTaxAmt() > 0) {
			amount5 = (int) transactionDetailModel.getGreenTaxAmt();
			permitGreenDesc = greenTaxDesc;
			permitGreenHead = greenTaxHead;
		}
		String OtherChargesTID2 = hsrpTransactionId;
		int amount7 = 0;
		if (hsrptAmount != 0)
			amount7 = (int) hsrptAmount;

		double amount6 = transactionDetailModel.getCessFee();

		int checkTotal = 0;
		if (testAmount) {
			amount1 = 1;
			amount2 = 1;
			amount3 = 1;
			amount4 = 1;
			amount5 = 1;
			amount7 = 1;
			checkTotal = 1;
		} else {
			checkTotal = transactionDetailModel.getAmount().intValue();
			int grandTotal = (int) (amount1 + amount2 + amount3 + amount4 + amount5 + amount6 + amount7);
			if (grandTotal != checkTotal) {
				log.error("::::Check Total Mismatch::::");
				throw new IllegalArgumentException("::::Chcek Total Mismatch:::: ");
			}
		}

		log.info(" RemmiterName " + remitterName + ":Amount_1:" + amount1 + ":Amount_2:" + amount2 + ":Amount_3:"
				+ amount3 + ":Amount_4:" + amount4 + ":Amount_5:" + amount5 + ":Amount_7:" + amount7 + ":checkTotal:"
				+ checkTotal);

		String requestData = "Dept_code=" + deptCodeValue + "|Dept_Name=" + deptName + "|Name_of_the_Remitter="
				+ remitterName +

				"|Department_TransID_1=" + departmentTransID + "|Challan_No_1=" + challanNo1 + "|HOA_1=" + feeHead
				+ "|HOA_1_Description=" + feeDesc + "|Amount_1=" + amount1 + "|Credit_Account_1=" + pollingAcTaxRegFee +

				"|Department_TransID_2=" + departmentTransID + "|Challan_No_2=" + challanNo2 + "|HOA_2=" + HOA_2
				+ "|HOA_2_Description=" + HOA_2_Description + "|Amount_2=" + amount2 + "|Credit_Account_2="
				+ pollingAcTaxRegFee +

				"|Department_TransID_3=" + departmentTransID + "|Challan_No_3=" + challanNo3 + "|HOA_3=" + serviceHead
				+ "|HOA_3_Description=" + serviceDesc + "|Amount_3=" + amount3 + "|Credit_Account_3="
				+ pollingAcTaxRegFee +

				"|Department_TransID_4=" + departmentTransID + "|Challan_No_4=" + challanNo4 + "|HOA_4=" + postalHead
				+ "|HOA_4_Description=" + postalDesc + "|Amount_4=" + amount4 + "|Credit_Account_4="
				+ pollingAcTaxRegFee +

				"|Department_TransID_5=" + departmentTransID + "|Challan_No_5=" + challanNo5 + "|HOA_5="
				+ permitGreenHead + "|HOA_5_Description=" + permitGreenDesc + "|Amount_5=" + amount5
				+ "|Credit_Account_5=" + pollingAcTaxRegFee +

				"|Other_Charges_TID_1=" + departmentTransID + "|Other_charges_Description_1=" + cessFeeDesc
				+ "|Amount_6=" + amount6 + "|Credit_Account_6=" + pollingACCess +

				"|Other_Charges_TID_2=" + OtherChargesTID2 + "|Other_charges_Description_2=" + hsrpDesc + "|Amount_7="
				+ amount7 + "|Credit_Account_7=" + pollingACHSRP +

				"|Check_Total=" + checkTotal + "|DDO_Code=" + ddoCodevalue + "|Mode_of_Transaction=" + MODEOFPAYMENT
				+ "|Redirect_URL=" + getRedirectURL(transactionDetailModel,true);
		log.info("::::::::::::::getRequestParameter::::end::::::::::::: " + requestData);

		String checkdata = "";
		ChecksumMD5 checksum = new ChecksumMD5();
		try {
			checkdata = checksum.getValue(requestData);
			requestData = requestData + "|checkSum=" + checkdata;
		} catch (Exception e) {
			log.error(":::::::encryptedSBIParameter:::Error::::::: " + e.getMessage());
		}
		AESEncrypt encrpt = new AESEncrypt();
		encrpt.setSecretKey(sbiKeyPath);
		String eData = encrpt.encryptFile(requestData);
		transactionDetailModel.setEncryptRequest(eData);
		transactionDetailModel.setDecryptRequest(requestData);
		log.debug(":::::::encryptedSBIParameter::Object::::: " + eData);
		return transactionDetailModel;
	}

	private String getRedirectURL(TransactionDetailModel transactionDetailModel, boolean payFlag) {
		log.info("::::::getRedirectURL::::::::::: " + transactionDetailModel.getServiceCode());
		String redirectURL = "";

		switch (ServiceType.getServiceType(transactionDetailModel.getServiceCode())) {
		case REGISTRATION_TRANSNDNONTRANS:
			if (payFlag)
				redirectURL = regist_tnt_sbi_url;
			else
				redirectURL = regist_tnt_sbi_verify_url;
			break;
		case ADDRESS_CHANGE:
			if (payFlag)
				redirectURL = citizen_change_of_address_sbi_url;
			else
				redirectURL = citizen_change_of_address_sbi_verify_url;
			break;
		case DUPLICATE_REGISTRATION:
			if (payFlag)
				redirectURL = citizen_duplicate_regist_sbi_url;
			else
				redirectURL = citizen_duplicate_regist_sbi_verify_url;
			break;
		case FRESH_RC_FINANCIER:
			if (payFlag)
				redirectURL = citizen_fresh_rc_financer_sbi_url;
			else
				redirectURL = citizen_fresh_rc_financer_sbi_verify_url;
			break;
		case HPA:
			if (payFlag)
				redirectURL = citizen_hire_purchase_agreement_sbi_url;
			else
				redirectURL = citizen_hire_purchase_agreement_sbi_verify_url;
			break;
		case HPT:
			if (payFlag)
				redirectURL = citizen_hire_purchase_termination_sbi_url;
			else
				redirectURL = citizen_hire_purchase_termination_sbi_verify_url;
			break;
		case NOC_CANCELLATION:
			if (payFlag)
				redirectURL = citizen_cancellation_of_noc_cc_sbi_url;
			else
				redirectURL = citizen_cancellation_of_noc_cc_sbi_verify_url;
			break;
		case NOC_ISSUE:
			if (payFlag)
				redirectURL = citizen_issue_of_cc_noc_sbi_url;
			else
				redirectURL = citizen_issue_of_cc_noc_sbi_verify_url;
			break;
		case OWNERSHIP_TRANSFER_AUCTION:
			if (payFlag)
				redirectURL = citizen_tow_auction_sbi_verify_url;
			else
				redirectURL = citizen_tow_auction_sbi_verify_url;
			break;
		case OWNERSHIP_TRANSFER_DEATH:
			if (payFlag)
				redirectURL = citizen_tow_death_sbi_url;
			else
				redirectURL = citizen_tow_death_sbi_verify_url;
			break;
		case OWNERSHIP_TRANSFER_SALE:
			if (payFlag)
				redirectURL = citizen_tow_sale_sbi_url;
			else
				redirectURL = citizen_tow_sale_sbi_verify_url;
			break;
		case REGISTRATION_RENEWAL:
			if (payFlag)
				redirectURL = citizen_renewal_of_regist_sbi_url;
			else
				redirectURL = citizen_renewal_of_regist_sbi_verify_url;
			break;
		case REGISTRATION_SUS_CANCELLATION:
			if (payFlag)
				redirectURL = citizen_cancellation_of_regist_sbi_url;
			else
				redirectURL = citizen_cancellation_of_regist_sbi_verify_url;
			break;
		case SLOT_BOOKING:
			break;
		case SUSPENSION_REVOCATION:
			if (payFlag)
				redirectURL = citizen_revocation_of_suspension_sbi_url;
			else
				redirectURL = citizen_revocation_of_suspension_sbi_verify_url;
			break;
		case THEFT_INTIMATION:
			if (payFlag)
				redirectURL = citizen_theft_intimation_sbi_url;
			else
				redirectURL = citizen_theft_intimation_sbi_verify_url;
			break;
		case VEHICLE_ATLERATION:
			if (payFlag)
				redirectURL = citizen_alteration_of_vehicle_sbi_url;
			else
				redirectURL = citizen_alteration_of_vehicle_sbi_verify_url;
			break;
		case VEHICLE_REASSIGNMENT:
			if (payFlag)
				redirectURL = citizen_reassignment_of_vehicle_sbi_url;
			else
				redirectURL = citizen_reassignment_of_vehicle_sbi_verify_url;
			break;
		case DIFFERENTIAL_TAX:
			if (payFlag)
				redirectURL = citizen_diff_tax_sbi_url;
			else
				redirectURL = citizen_diff_tax_sbi_verify_url;
			break;
		case PAY_TAX:
			if (payFlag)
				redirectURL = citizen_pay_tax_sbi_url;
			else
				redirectURL = citizen_pay_tax_sbi_verify_url;
			break;

		case STOPPAGE_TAX:
			if(payFlag)
				redirectURL = citizen_stoppage_tax_sbi_url;
				else
				redirectURL = citizen_stoppage_tax_sbi_verify_url;
			break;	
		case STOPPAGE_TAX_REVOCATION:
			if(payFlag)
				redirectURL = citizen_stoppage_tax_revocation_sbi_url;
				else
				redirectURL = citizen_stoppage_tax_revocation_sbi_verify_url;
			break;	

		case DL_BADGE:
		case DL_CHANGE_ADDRESS:
		case DL_DLINFO:
		case DL_DUPLICATE:
		case DL_ENDORSMENT:
		case DL_EXPIRED:
		case DL_FRESH:
		case DL_INT_PERMIT:
		case DL_RENEWAL:
		case DL_RETEST:
		case DL_SURRENDER:
		case DL_REVO_SUS:
		case DL_CHANGEADDRS_OS:
		case DL_FOREIGN_CITIZEN:
		case DL_MILITRY:
		case DL_SUSU_CANC:
			if (payFlag)
				redirectURL = citizen_drivinglicense_sbi_url;
			else
				redirectURL = citizen_drivinglicense_sbi_verify_url;
			break;
		case LL_DUPLICATE:
		case LL_ENDORSEMENT:
		case LL_FRESH:
		case LL_RETEST:
			if (payFlag)
				redirectURL = citizen_llf_sbi_url;
			else
				redirectURL = citizen_llf_sbi_verify_url;
			break;
		case FINANCIER_SIGNUP:
			if (payFlag)
				redirectURL = citizen_userreg_sbi_url;
			else
				redirectURL = citizen_userreg_sbi_verify_url;
			break;
		case BODYBUILDER_SIGNUP:
			if (payFlag)
				redirectURL = citizen_userreg_sbi_url;
			else
				redirectURL = citizen_userreg_sbi_verify_url;
			break;
		case DEALER_SIGNUP:
			if (payFlag)
				redirectURL = citizen_userreg_sbi_url;
			else
				redirectURL = citizen_userreg_sbi_verify_url;
			break;
		case PUC_USER_SIGNUP:
			if (payFlag)
				redirectURL = citizen_userreg_sbi_url;
			else
				redirectURL = citizen_userreg_sbi_verify_url;
			break;
		case ALTERATION_AGENCY_SIGNUP:
			if (payFlag)
				redirectURL = citizen_userreg_sbi_url;
			else
				redirectURL = citizen_userreg_sbi_verify_url;
			break;
		case DRIVING_INSTITUTE:
			if (payFlag)
				redirectURL = citizen_userreg_sbi_url;
			else
				redirectURL = citizen_userreg_sbi_verify_url;
			break;
		case HAZARDOUS_VEH_TRAIN_INST:
			if (payFlag)
				redirectURL = citizen_userreg_sbi_url;
			else
				redirectURL = citizen_userreg_sbi_verify_url;
			break;
		case MEDICAL_PRACTITIONER:
			if (payFlag)
				redirectURL = citizen_userreg_sbi_url;
			else
				redirectURL = citizen_userreg_sbi_verify_url;
			break;
		case FC_FRESH:
		case FC_OTHER_STATION:
		case FC_RENEWAL:
		case FC_REVOCATION_CFX:
		case FC_RE_INSPECTION_SB:
			if (payFlag)
				redirectURL = citizen_fitness_sbi_url;
			else
				redirectURL = citizen_fitness_sbi_verify_url;
			break;
		case PERMIT_FRESH:
		case PERMIT_RENEWAL:
		case PERMIT_RENEWAL_AUTH_CARD:
		case PERMIT_SURRENDER:
		case PERMIT_REPLACEMENT_VEHICLE:
		case PERMIT_VARIATIONS:
			if (payFlag)
				redirectURL = citizen_permit_sbi_url;
			else
				redirectURL = citizen_permit_sbi_verify_url;
			break;
		}
		log.info(":::::::Redirect URL:::::: " + redirectURL);
		return redirectURL;
	}

	@Transactional
	@Override
	public TransactionDetailModel secondVehicleEncryptedRequest(TransactionDetailModel transactionDetailModel) {
		Long vehicleRcId = Long.parseLong(transactionDetailModel.getVehicleRcId());
		VehicleRCEntity vehicleEntity = vehicleDAO.get(vehicleRcId);
		String data = getSecondVehicleRequestParameter(transactionDetailModel, vehicleEntity);

		String checkdata = "";
		ChecksumMD5 checksum = new ChecksumMD5();
		try {
			checkdata = checksum.getValue(data);
			data = data + "|checkSum=" + checkdata;
		} catch (Exception e) {
			log.error(":::::::secondVehicleEncryptedRequest:::Error::::::: " + e.getMessage());
			log.debug(e);
		}
		AESEncrypt encrpt = new AESEncrypt();
		encrpt.setSecretKey(sbiKeyPath);
		String eData = encrpt.encryptFile(data);
		transactionDetailModel.setEncryptRequest(eData);
		TransactionHistoryEntity transactionHistoryEntity = new TransactionHistoryEntity();
		transactionHistoryEntity.setRequestParameter(eData);
		transactionHistoryEntity.setTransactionNo(transactionDetailModel.getTransactionNo());
		transactionHistoryEntity.setVehicleRcId(vehicleEntity.getVehicleRcId());
		transactionHistoryEntity.setStatus(Status.OPEN.getValue());
		transactionHistoryEntity.setPaymentType(PaymentType.SECONDVEHICLETAXPAY.getId());
		transactionHistoryEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		transactionHistoryDAO.save(transactionHistoryEntity);
		log.debug("::::::::::::secondVehicleEncryptedRequest:::end:::::::::: " + eData);
		return transactionDetailModel;
	}

	public String getSecondVehicleRequestParameter(TransactionDetailModel transactionDetailModel,
			VehicleRCEntity vehicleEntity) {
		log.info("::::::::::::::getSecondVehicleRequestParameter::::start:::::::::::::");
		CustIndividualDetailsEntity custDtlsEntity = new CustIndividualDetailsEntity();
		CustCorporateDetailsEntity custCorporateDetailsEntity = new CustCorporateDetailsEntity();
		String remitterName = "";
		if (vehicleEntity.getOwnershipType() == OwnershipType.INDIVIDUAL.getId()) {
			custDtlsEntity = custIndvDAO.getByVehicleRcId(vehicleEntity.getVehicleRcId());
			remitterName = custDtlsEntity.getFirstName() + " " + custDtlsEntity.getSurName();
			if (remitterName.length() > 30)
				remitterName = remitterName.substring(0, 29);
		} else {
			custCorporateDetailsEntity = customerCorporateDAO.getByVehicleRcId(vehicleEntity.getVehicleRcId());
			remitterName = custCorporateDetailsEntity.getCompanyName();
			if (remitterName.length() > 30)
				remitterName = remitterName.substring(0, 29);
		}
		SBIDDOMasterEntity sbiddoMasterEntity = sbiDDOMasterDAO
				.getByDistrictCode(vehicleEntity.getRtaOfficeId().getDistrictCode());
		log.info("::::::sbiddoMasterEntity:::::::: " + sbiddoMasterEntity);
		String DDO_Code_value = sbiddoMasterEntity.getDdoCode();

		List<SBIDistributionEntity> sbiDistributionList = sbiDistributionDAO.getAll();
		String HOA1 = "";
		String HOA1Description = "";
		String pollingAcTax = "";
		for (SBIDistributionEntity sbiDistribution : sbiDistributionList) {
			switch (SBIHeadType.getSBIHeadType(sbiDistribution.getHeadType())) {
			case LIFETAX:
				HOA1 = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				HOA1Description = sbiDistribution.getHoaDescription();
				pollingAcTax = sbiDistribution.getCreditPollingAccount();
				break;
			}
		}

		int checkTotal = 0;
		if (testAmount)
			checkTotal = 1;
		else
			checkTotal = (int) NumberParser.getRoundOff(transactionDetailModel.getAmount());
		log.info(":checkTotal:" + checkTotal);

		String challanNo = transactionDetailModel.getTransactionNo() + 1;
		String requestData = "Dept_code=" + deptCodeValue + "|Dept_Name=" + deptName + "|Name_of_the_Remitter="
				+ remitterName + "|Department_TransID_1=" + transactionDetailModel.getTransactionId() + "|Challan_No_1="
				+ challanNo + "|HOA_1=" + HOA1 + "|HOA_1_Description=" + HOA1Description + "|Amount_1=" + checkTotal
				+ "|Credit_Account_1=" + pollingAcTax + "|Department_TransID_2=" + Department_TransID_2
				+ "|Challan_No_2=" + Challan_No_2 + "|HOA_2=" + HOA_2 + "|HOA_2_Description=" + HOA_2_Description
				+ "|Amount_2=" + Amount_2 + "|Credit_Account_2=" + Credit_Account_2 + "|Department_TransID_3="
				+ Department_TransID_3 + "|Challan_No_3=" + Challan_No_3 + "|HOA_3=" + HOA_3 + "|HOA_3_Description="
				+ HOA_3_Description + "|Amount_3=" + Amount_3 + "|Credit_Account_3=" + Credit_Account_3
				+ "|Department_TransID_4=" + Department_TransID_4 + "|Challan_No_4=" + Challan_No_4 + "|HOA_4=" + HOA_4
				+ "|HOA_4_Description=" + HOA_4_Description + "|Amount_4=" + Amount_4 + "|Credit_Account_4="
				+ Credit_Account_4 + "|Department_TransID_5=" + Department_TransID_5 + "|Challan_No_5=" + Challan_No_5
				+ "|HOA_5=" + HOA_5 + "|HOA_5_Description=" + HOA_5_Description + "|Amount_5=" + Amount_5
				+ "|Credit_Account_5=" + Credit_Account_5 + "|Other_Charges_TID_1=" + Other_Charges_TID_1
				+ "|Other_charges_Description_1=" + Other_charges_Description_1 + "|Amount_6=" + Amount_6
				+ "|Credit_Account_6=" + Credit_Account_6 + "|Other_Charges_TID_2=" + Other_Charges_TID_2
				+ "|Other_charges_Description_2=" + Other_charges_Description_2 + "|Amount_7=" + Amount_7
				+ "|Credit_Account_7=" + Credit_Account_7 + "|Check_Total=" + checkTotal + "|DDO_Code=" + DDO_Code_value
				+ "|Mode_of_Transaction=" + MODEOFPAYMENT + "|Redirect_URL=" + regist_tnt_sbi_url;
		log.info("::::::::::::::getSecondVehicleRequestParameter::::end::::::::::::: " + requestData);
		return requestData;
	}

	@Override
	@Transactional
	public TransactionDetailModel decryptSecondVehicleSBIResponse(TransactionDetailModel transactionDetailModel) {
		String reqString = transactionDetailModel.getEncryptRequest();
		TransactionHistoryEntity transactionHistoryEntity = new TransactionHistoryEntity();
		transactionHistoryEntity.setResponseParameter(reqString);
		transactionHistoryEntity.setTransactionNo(transactionDetailModel.getTransactionNo());
		transactionHistoryEntity.setStatus(Status.CLOSED.getValue());
		transactionHistoryEntity.setPaymentType(PaymentType.SECONDVEHICLETAXPAY.getId());
		transactionHistoryEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		AESEncrypt encrpt = new AESEncrypt();
		encrpt.setSecretKey(sbiKeyPath);
		String dData = encrpt.decryptFile(reqString);
		log.debug("Second Vehcile eData " + dData);
		HashMap<String, String> mapValue = getMapValue(dData);
		if (mapValue.get("checkSum").equalsIgnoreCase(getChcekSum(dData))) {
			transactionDetailModel.setStatus("SUCCESS");
			transactionDetailModel.setSbiResponseMap(mapValue);
			return transactionDetailModel;
		} else {
			transactionDetailModel.setStatus("FAILURE");
			return transactionDetailModel;
		}
	}

	@Override
	@Transactional
	public TransactionDetailModel payVerification(Long vehicleRcId, TransactionDetailModel transactionDetailModel,
			Integer payType) {
		log.debug("::::::::::::payVerification:::start::::::::::" + transactionDetailModel.getAmount() + "--"
				+ transactionDetailModel.getTransactionNo() + " paytype " + payType);
		transactionDetailModel.setServiceCode("RTNT");
		transactionDetailModel = getVerificationEncrytData(transactionDetailModel);
		TransactionHistoryEntity transactionHistoryEntity = new TransactionHistoryEntity();
		transactionHistoryEntity.setRequestParameter(transactionDetailModel.getDecryptRequest());
		transactionHistoryEntity.setTransactionNo(transactionDetailModel.getTransactionNo());
		transactionHistoryEntity.setStatus(Status.OPEN.getValue());
		try {
			if (payType == PaymentType.PAYTAX.getId())
				transactionHistoryEntity.setPaymentType(PaymentType.PAYTAXVERIFICATION.getId());
			if (payType == PaymentType.SECONDVEHICLETAXPAY.getId())
				transactionHistoryEntity.setPaymentType(PaymentType.SECONDVEHICLETAXVERIFICATION.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		transactionHistoryEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		transactionHistoryDAO.save(transactionHistoryEntity);
		log.debug("::::encryptedSBIParameter:::end:::");
		return transactionDetailModel;

	}

	@Override
	public TransactionDetailModel getVerificationEncrytData(TransactionDetailModel transactionDetailModel) {

		switch(PaymentGatewayType.getPaymentGatewayType(transactionDetailModel.getPgType())){
		case PAYU:
			
			break;
		case SBI:
			transactionDetailModel = getSBIVerificationEncrytData(transactionDetailModel);
			break;
		}

		
		return transactionDetailModel;
	}

	public TransactionDetailModel getSBIVerificationEncrytData(TransactionDetailModel transactionDetailModel) {

		int checkTotal = 0;
		if (testAmount)
			checkTotal = 1;
		else
			checkTotal = (int) NumberParser.getRoundOff(transactionDetailModel.getAmount());
		log.debug(":checkTotal:" + checkTotal);

		String data = "Transaction_amount=" + checkTotal + "|Transaction_No="
				+ transactionDetailModel.getTransactionNo() + 1 + "|Redirect_URL="
				+ getRedirectURL(transactionDetailModel, false);
		log.debug("::::::::data verfication::::::::" + data);
		String checkdata = "";
		ChecksumMD5 checksum = new ChecksumMD5();
		try {
			checkdata = checksum.getValue(data);
			data = data + "|checkSum=" + checkdata;
		} catch (Exception e) {
			log.error(":::::::payVerification:::Error::::::: " + e.getMessage());
			log.debug(e);
		}
		AESEncrypt encrpt = new AESEncrypt();
		encrpt.setSecretKey(sbiKeyPath);
		String eData = encrpt.encryptFile(data);
		log.debug("::::::ENSDATA verification::::::: " + eData);
		transactionDetailModel.setEncryptRequest(eData);
		transactionDetailModel.setDecryptRequest(data);
		return transactionDetailModel;
	}

	
	
	@Override
	@Transactional
	public TransactionDetailModel decryptVerificationSBIResponse(TransactionDetailModel transactionDetailModel) {
		String reqString = transactionDetailModel.getEncryptRequest();
		TransactionHistoryEntity transactionHistoryEntity = new TransactionHistoryEntity();
		transactionHistoryEntity.setResponseParameter(reqString);
		transactionHistoryEntity.setTransactionNo(transactionDetailModel.getTransactionNo());
		transactionHistoryEntity.setStatus(Status.CLOSED.getValue());
		transactionHistoryEntity.setPaymentType(transactionDetailModel.getPayType());
		transactionHistoryEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		transactionHistoryDAO.save(transactionHistoryEntity);
		AESEncrypt encrpt = new AESEncrypt();
		encrpt.setSecretKey(sbiKeyPath);
		String dData = encrpt.decryptFile(reqString);
		log.debug("::::decryptVerificationSBIResponse RESPONSE DATA:::::::: " + dData);
		HashMap<String, String> mapValue = getVerificationMapValue(dData);
		if (mapValue.get("checkSum").equalsIgnoreCase(getChcekSum(dData))) {
			transactionDetailModel.setStatus("SUCCESS");
			transactionDetailModel.setSbiResponseMap(mapValue);
			return transactionDetailModel;
		} else {
			transactionDetailModel.setStatus("FAILURE");
			return transactionDetailModel;
		}
	}

	@Override
	public TransactionDetailModel adminSBIVerification(TransactionDetailModel transactionDetailModel) {
		log.debug("::::::::::::adminSBIVerification:::start::::::::::" + transactionDetailModel.getAmount() + "--"
				+ transactionDetailModel.getTransactionNo());

		// String data = "Transaction_amount=" +
		// Float.valueOf(transactionDetailModel.getAmount().toString())
		// + "|Transaction_No=" + transactionDetailModel.getTransactionNo() + 1
		// +
		// "|Redirect_URL=http://rta.dealer.dev/double_verification/gatewayresponse";

		String data = "Transaction_amount=" + Float.valueOf(transactionDetailModel.getAmount().toString())
				+ "|Transaction_No=" + transactionDetailModel.getTransactionNo() + 1
				+ "|Redirect_URL=https://119.235.51.68:444/double_verification/gatewayresponse";

		log.debug("::::::::data verfication::::::::" + data);
		String checkdata = "";
		ChecksumMD5 checksum = new ChecksumMD5();
		try {
			checkdata = checksum.getValue(data);
			log.debug(":::adminSBIVerification::checksumdata::::::::::" + checkdata);
			data = data + "|checkSum=" + checkdata;
		} catch (Exception e) {
			log.error(":::::::adminSBIVerification:::Error::::::: " + e.getMessage());
			log.debug(e);
		}
		AESEncrypt encrpt = new AESEncrypt();
		encrpt.setSecretKey(sbiKeyPath);
		String eData = encrpt.encryptFile(data);
		log.debug("::::::ENSDATA adminSBIVerification::::::: " + eData);
		transactionDetailModel.setEncryptRequest(eData);
		log.debug("::::::adminSBIVerification::::::encryptedSBIParameter:::end:::::::::: " + eData);
		return transactionDetailModel;

	}

	@Override
	public TransactionDetailModel adminDecryptVerificationSBI(TransactionDetailModel transactionDetailModel) {
		log.debug(":::::::::::::AdminDecryptVerificationSBI::::::::::::::: "
				+ transactionDetailModel.getEncryptRequest());
		String reqString = transactionDetailModel.getEncryptRequest();
		AESEncrypt encrpt = new AESEncrypt();
		encrpt.setSecretKey(sbiKeyPath);
		String dData = encrpt.decryptFile(reqString);
		// String dData = transactionDetailModel.getEncryptRequest();
		log.debug("::::decryptVerificationSBIResponse RESPONSE DATA:::::::: " + dData);
		HashMap<String, String> mapValue = getVerificationMapValue(dData);
		if (mapValue.get("checkSum").equalsIgnoreCase(getChcekSum(dData))) {
			transactionDetailModel.setStatus("SUCCESS");
			transactionDetailModel.setSbiResponseMap(mapValue);
			return transactionDetailModel;
		} else {
			transactionDetailModel.setStatus("FAILURE");
			return transactionDetailModel;
		}

	}

	@Override
	public String verificationWithScheduler(Double amount, String transactionNo) {
		log.info("::::::::::::verificationWithScheduler:::start::::::::::");
		String data = "Transaction_amount=" + Float.valueOf(amount.toString()) + "|Transaction_No=" + transactionNo + 1
				+ "|Redirect_URL=" + verification_redirect_URL;
		log.info("::::::::data verificationWithScheduler::::::::" + data);
		String checkdata = "";
		ChecksumMD5 checksum = new ChecksumMD5();
		try {
			checkdata = checksum.getValue(data);
			data = data + "|checkSum=" + checkdata;
		} catch (Exception e) {
			log.error(":::::::verificationWithScheduler:::Error::::::: " + e.getMessage());
		}
		AESEncrypt encrpt = new AESEncrypt();
		encrpt.setSecretKey(sbiKeyPath);
		String eData = encrpt.encryptFile(data);
		log.info("::::::::::::verificationWithScheduler:::start::::::::::");
		return eData;

	}

	@Override
	public TransactionDetailModel decryptVerificationScheduler(TransactionDetailModel transactionDetailModel) {
		log.info("::::decryptVerificationScheduler:::::::: ");
		String reqString = transactionDetailModel.getEncryptRequest();
		AESEncrypt encrpt = new AESEncrypt();
		encrpt.setSecretKey(sbiKeyPath);
		String dData = encrpt.decryptFile(reqString);
		log.info("::::decryptVerificationSBIResponse RESPONSE DATA:::::::: " + dData);
		HashMap<String, String> mapValue = getVerificationMapValue(dData);
		if (mapValue.get("checkSum").equalsIgnoreCase(getChcekSum(dData))) {
			transactionDetailModel.setStatus("SUCCESS");
			transactionDetailModel.setSbiResponseMap(mapValue);
			return transactionDetailModel;
		} else {
			transactionDetailModel.setStatus("FAILURE");
			return transactionDetailModel;
		}

	}

	@Override
	@Transactional
	public SaveUpdateResponse verifyPendingTxn(long vehicleRcId, Integer payType, String userName, UserType userType) {
		log.debug(":::::::verifyPendingTxn::::::::::");
		SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
		TransactionDetailEntity transactionDetailEntity = transactionDetailDAO
				.getByVehicleRcIdNdPaymentType(vehicleRcId, payType);
		if (transactionDetailEntity == null) {
			saveUpdateResponse.setStatus("NEW PAYEE");
			return saveUpdateResponse;
		}
		HttpHeaders headers = new HttpHeaders();
		MultiValueMap<String, String> map = null;
		HttpEntity<MultiValueMap<String, String>> entity = null;
		TransactionDetailModel transactionDetailModel = null;
		String encryptData = verificationWithScheduler(transactionDetailEntity.getPayAmount(),
				transactionDetailEntity.getTransactionNo());
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		map = new LinkedMultiValueMap<>();
		map.add("encdata", encryptData);
		map.add("merchant_code", "APT_GOVT");
		entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = restTemplate.exchange(verification_URL, HttpMethod.POST, entity,
				String.class);
		String decryptData = response.getBody().substring(response.getBody().indexOf("value=") + 7,
				response.getBody().indexOf("\"/>"));
		transactionDetailModel = new TransactionDetailModel();
		transactionDetailModel.setEncryptRequest(decryptData);
		transactionDetailModel.setTransactionNo(transactionDetailEntity.getTransactionNo());
		transactionDetailModel = decryptVerificationScheduler(transactionDetailModel);
		if (transactionDetailModel.getStatus().equalsIgnoreCase("SUCCESS")) {
			saveUpdateResponse = transactionDetailService.gatewayResponse(transactionDetailModel, userName, vehicleRcId,
					false, userType);
			saveUpdateResponse.setVehicleRcId(transactionDetailModel.getVehicleRcId());
		} else {
			saveUpdateResponse.setStatus("FAILURE");
			saveUpdateResponse.setMessage("interrupted checksum data");
		}
		return saveUpdateResponse;
	}

	@Override
	@Transactional
	public TransactionDetailModel getPaymentParameter(TransactionDetailModel transactionDetailModel) {
		log.info(":::getPaymentParameter:::::start::");
		switch(PaymentGatewayType.getPaymentGatewayType(transactionDetailModel.getPgType())){
		case PAYU:
			transactionDetailModel = encryptedPayUParameter(transactionDetailModel, new HSRPTransactionModel(), new DealerModel());
			break;
		case SBI:
			transactionDetailModel = getRequestParameter(transactionDetailModel, null, 0);
			break;
		}

		try {
			TransactionHistoryEntity transactionHistoryEntity = new TransactionHistoryEntity();
			transactionHistoryEntity.setRequestParameter(transactionDetailModel.getEncryptRequest());
			transactionHistoryEntity.setTransactionNo(transactionDetailModel.getTransactionNo());
			transactionHistoryEntity.setStatus(Status.OPEN.getValue());
			transactionHistoryEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			transactionHistoryDAO.save(transactionHistoryEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info(":::getPaymentParameter:::::end::");
		return transactionDetailModel;
	}

	/***
	 * PAYU integration
	 */
	@Transactional
	@Override
	public TransactionDetailModel encryptedPayUParameter(TransactionDetailModel transactionDetailModel,
			HSRPTransactionModel hsrpTransactionModel, DealerModel dealerModel) {
		log.debug("::::::::::::encryptedPayUParameter:::start::::::::::");
		Long vehicleRcId = Long.parseLong(transactionDetailModel.getVehicleRcId());
		VehicleRCEntity vehicleEntity = vehicleDAO.get(vehicleRcId);
		String productInfo = getPayURequestParameter(dealerModel, transactionDetailModel, vehicleEntity, hsrpTransactionModel);
		log.debug("productInfo:" + productInfo);
		transactionDetailModel.setProductInfo(productInfo);
		if(transactionDetailModel.getCitizenFlag()){
			transactionDetailModel.setKey(payUKey4citizen);
			transactionDetailModel.setSalt(payUSalt4citizen);
		}else{
			transactionDetailModel.setKey(payUKey);
			transactionDetailModel.setSalt(payUSalt);
		}
		generatePayUHash(transactionDetailModel);
		TransactionHistoryEntity transactionHistoryEntity = new TransactionHistoryEntity();
		transactionHistoryEntity.setRequestParameter(productInfo);
		transactionHistoryEntity.setTransactionNo(transactionDetailModel.getTransactionNo());
		transactionHistoryEntity.setVehicleRcId(Long.parseLong(transactionDetailModel.getVehicleRcId()));
		transactionHistoryEntity.setStatus(Status.OPEN.getValue());
		transactionHistoryEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		transactionHistoryEntity.setPgType(PaymentGatewayType.PAYU.getLabel());
		transactionHistoryDAO.save(transactionHistoryEntity);
		log.debug(":::split details PayU:::end:::::: " + transactionDetailModel.getDecryptRequest());
		return transactionDetailModel;
	}

	@Transactional
	@Override
	public TransactionDetailModel decryptPayUResponse(TransactionDetailModel transactionDetailModel) {
		log.info(":::::::::::::decrypPayUResponse::::::::::::::: " + transactionDetailModel.getTransactionNo());
		String reqString = getPayUResponseParams(transactionDetailModel);
		TransactionHistoryEntity transactionHistoryEntity = new TransactionHistoryEntity();
		transactionHistoryEntity.setResponseParameter(reqString);
		transactionHistoryEntity.setTransactionNo(transactionDetailModel.getTransactionNo());
		transactionHistoryEntity.setStatus(Status.CLOSED.getValue());
		if(transactionDetailModel.getPayType()==null || transactionDetailModel.getPayType()==PaymentType.PAYTAX.getId()) {
			transactionHistoryEntity.setPaymentType(PaymentType.PAYTAX.getId());
		}else {
			transactionHistoryEntity.setPaymentType(PaymentType.SECONDVEHICLETAXPAY.getId());	
		}
		transactionHistoryEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		transactionHistoryEntity.setPgType(PaymentGatewayType.PAYU.getLabel());
		transactionHistoryDAO.save(transactionHistoryEntity);
		log.info("::::::::PayU Response History Records::::End::::: " + transactionDetailModel.getPayUTransactionDetails());

		transactionDetailModel.setStatus(transactionDetailModel.getPayUTransactionDetails().getStatus());
		return transactionDetailModel;
	}

	@Override
	public MultiValueMap<String, String> getPayUVerifyReqParams(TransactionDetailModel transactionDetailModel) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		if(transactionDetailModel.getCitizenFlag())
			map.add("merchantKey", payUKey4citizen);
		else
			map.add("merchantKey", payUKey);
		map.add("merchantTransactionIds", transactionDetailModel.getTransactionNo());

		return map;
	}

	@Transactional
	@Override
	public TransactionDetailModel payUVerificationReq(Long vehicleRcId, TransactionDetailModel transactionDetailModel,
			Integer payType) {
		log.info("::::::::::::payVerification:::start::::::::::" + transactionDetailModel.getAmount() + "--"
				+ transactionDetailModel.getTransactionNo() + " paytype " + payType);

		VehicleRCEntity vehicleEntity = vehicleDAO.get(vehicleRcId);

		transactionDetailModel.setEncryptRequest("");
		TransactionDetailEntity transactionDetailEntity = transactionDetailDAO
				.getByTransNoNdVehicleRcId(transactionDetailModel.getTransactionNo(), vehicleEntity.getVehicleRcId());
		TransactionHistoryEntity transactionHistoryEntity = new TransactionHistoryEntity();
		transactionHistoryEntity.setRequestParameter("");
		transactionHistoryEntity.setTransactionNo(transactionDetailModel.getTransactionNo());
		// transactionHistoryEntity.setTransactionDetail(transactionDetailEntity);
		transactionHistoryEntity.setVehicleRcId(vehicleRcId);
		transactionHistoryEntity.setPgType(PaymentGatewayType.PAYU.getLabel());
		transactionHistoryEntity.setStatus(Status.OPEN.getValue());
		try {
			if (payType == PaymentType.PAYTAX.getId())
				transactionHistoryEntity.setPaymentType(PaymentType.PAYTAXVERIFICATION.getId());
			if (payType == PaymentType.SECONDVEHICLETAXPAY.getId())
				transactionHistoryEntity.setPaymentType(PaymentType.SECONDVEHICLETAXVERIFICATION.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		transactionHistoryEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		transactionHistoryDAO.save(transactionHistoryEntity);
		return transactionDetailModel;
	}

	public boolean checkResponsePayUHash(TransactionDetailModel transactionDetailModel) {
		
		log.info("::::PAYU Response::: " + transactionDetailModel.getPayUTransactionDetails());
		// JSON has truncating the .0 when double has .0 but toString()
		// returning .0
		// To solve the problem while hashing below check is implemented
		// String amount =
		// NumberParser.getDoubleToString(transactionDetailModel.getAmount());
		String reHash = null;
		//	This block recomended for test enviorement
		if(transactionDetailModel.getCitizenFlag()){
			transactionDetailModel.setKey(payUKey4citizen);
			transactionDetailModel.setSalt(payUSalt4citizen);
		}else{
			transactionDetailModel.setKey(payUKey);
			transactionDetailModel.setSalt(payUSalt);
		}
		
		
		if(transactionDetailModel.getPayUTransactionDetails().getAdditionalCharges() != null && !transactionDetailModel.getPayUTransactionDetails().getAdditionalCharges().isEmpty())
			reHash = transactionDetailModel.getPayUTransactionDetails().getAdditionalCharges() + "|" + transactionDetailModel.getSalt() + "|" + transactionDetailModel.getPayUTransactionDetails().getStatus() + "|||||||||||"
					+ transactionDetailModel.getPayUTransactionDetails().getEmail() + "|"
					+ transactionDetailModel.getPayUTransactionDetails().getFirstname() + "|"
					+ transactionDetailModel.getPayUTransactionDetails().getProductinfo() + "|"
					+ transactionDetailModel.getPayUTransactionDetails().getAmount() + "|"
					+ transactionDetailModel.getTransactionNo() + "|" + transactionDetailModel.getKey();
		//This block recomended for Production enviorement
		else{
			reHash =  transactionDetailModel.getSalt() + "|" + transactionDetailModel.getPayUTransactionDetails().getStatus() + "|||||||||||"
					+ transactionDetailModel.getPayUTransactionDetails().getEmail() + "|"
					+ transactionDetailModel.getPayUTransactionDetails().getFirstname() + "|"
					+ transactionDetailModel.getPayUTransactionDetails().getProductinfo() + "|"
					+ transactionDetailModel.getPayUTransactionDetails().getAmount() + "|"
					+ transactionDetailModel.getTransactionNo() + "|" + transactionDetailModel.getKey();
		}

		log.info("hash String vlaue" + reHash);
		log.info("PayU hash:" + transactionDetailModel.getPayUTransactionDetails().getHash());
		log.info("Hash value calculated:" + HashingUtil.sha512HashCal(reHash));

		return HashingUtil.sha512HashCal(reHash).equals(transactionDetailModel.getPayUTransactionDetails().getHash());
	}
	
	public String getPayURequestParameter(DealerModel dealerModel , TransactionDetailModel transactionDetailModel, VehicleRCEntity vehicleEntity,
			HSRPTransactionModel hsrpTransactionModel) {
		log.info("::::::::::::::getRequestParameter::::start:::::::::::::");
		CustIndividualDetailsEntity custDtlsEntity = new CustIndividualDetailsEntity();
		CustCorporateDetailsEntity custCorporateDetailsEntity = new CustCorporateDetailsEntity();
		String firstName = "";
		String mobileNo = "";
		String emailId = "";
		switch (OwnershipType.getOwnershipType(vehicleEntity.getOwnershipType())) {
		case COMPANY:
			custCorporateDetailsEntity = customerCorporateDAO.getByVehicleRcId(vehicleEntity.getVehicleRcId());
			firstName = custCorporateDetailsEntity.getCompanyName();
			mobileNo = custCorporateDetailsEntity.getMobile();
			emailId = custCorporateDetailsEntity.getEmail();
			break;
		case INDIVIDUAL:
			custDtlsEntity = custIndvDAO.getByVehicleRcId(vehicleEntity.getVehicleRcId());
			mobileNo = custDtlsEntity.getMobileNo();
			emailId = custDtlsEntity.getEmailId();
			firstName = custDtlsEntity.getFirstName();
			break;
		}
		
		transactionDetailModel.setEmail(emailId);
		transactionDetailModel.setPhoneNumber(mobileNo);
		transactionDetailModel.setFirstName(firstName);
		log.debug("::PayU Payment:::: " + emailId + " contactNo " + mobileNo);
		
		
		SBIDDOMasterEntity sbiddoMasterEntity = sbiDDOMasterDAO
				.getByDistrictCode(vehicleEntity.getRtaOfficeId().getDistrictCode());
		log.info("::::::sbiddoMasterEntity:::::::: " + sbiddoMasterEntity);
		String ddoCodevalue = sbiddoMasterEntity.getDdoCode();
		String pollingAcTaxRegFee = "";
		String feeHead = "";
		String feeDesc = "";
		String lifeTaxHead = "";
		String lifeTaxDesc = "";
		String quartelyHead = "";
		String quartelyDesc = "";
		String serviceHead = "";
		String serviceDesc = "";
		String postalHead = "";
		String postalDesc = "";
		String hsrpDesc = "";
		String pollingACHSRP = "";
		String greenTaxHead = "";
		String permitHead = "";
		String permitDesc="";
		String greenTaxDesc="";
		String cessFeeDesc="";
		String pollingACCess="";
		List<SBIDistributionEntity> sbiDistributionList = sbiDistributionDAO.getAll();
		for (SBIDistributionEntity sbiDistribution : sbiDistributionList) {
			switch (SBIHeadType.getSBIHeadType(sbiDistribution.getHeadType())) {
			case FEE:
				feeHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				feeDesc = sbiDistribution.getHoaDescription();
				pollingAcTaxRegFee = sbiDistribution.getCreditPollingAccount();
				break;
			case LIFETAX:
				lifeTaxHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				lifeTaxDesc = sbiDistribution.getHoaDescription();
				break;
			case QUATERLYTAX:
				quartelyHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				quartelyDesc = sbiDistribution.getHoaDescription();
				break;
			case SERVICEFEE:
				serviceHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				serviceDesc = sbiDistribution.getHoaDescription();
				break;
			case POSTALFEE:
				postalHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				postalDesc = sbiDistribution.getHoaDescription();
				break;
			case HSRP:
				hsrpDesc = sbiDistribution.getHoaDescription();
				pollingACHSRP = sbiDistribution.getCreditPollingAccount();
				break;
			case PERMITFEE:
				permitHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				permitDesc = sbiDistribution.getHoaDescription();
				break;
			case GREENTAX:
				greenTaxHead = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				greenTaxDesc = sbiDistribution.getHoaDescription();
				break;
			case CESSFEE:
				cessFeeDesc = sbiDistribution.getHoaDescription();
			    pollingACCess = sbiDistribution.getCreditPollingAccount();
				break;
			}
		}

		String HOA_2 = "";
		String HOA_2_Description = "";
		VehicleDetailsEntity vehicleDetailsEntity = vehicleDetailsDAO.getByVehicleRcId(vehicleEntity.getVehicleRcId());

		switch (TaxType.getTaxType(vehicleDetailsEntity.getTaxTypeId().getTaxTypeId().intValue())) {
		case LIFE_TAX:
			HOA_2 = lifeTaxHead;
			HOA_2_Description = lifeTaxDesc;
			break;
		case FIRST_QUARTERLY:
			HOA_2 = quartelyHead;
			HOA_2_Description = quartelyDesc;
			break;
		}
		
		
		String departmentTransID1 = transactionDetailModel.getTransactionId().toString();
		String challanNo1 = transactionDetailModel.getTransactionNo() + 1;
		int amount1 = (int) transactionDetailModel.getFeeAmt();
		String challanNo2 = transactionDetailModel.getTransactionNo() + 2;

		String departmentTransID2 = transactionDetailModel.getTransactionId().toString();
		int amount2 = (int) transactionDetailModel.getTaxAmt();
		String departmentTransID3 = transactionDetailModel.getTransactionId().toString();
		String challanNo3 = transactionDetailModel.getTransactionNo() + 3;

		int amount3 = (int) transactionDetailModel.getServiceCharge();
		String departmentTransID4 = transactionDetailModel.getTransactionId().toString();
		String challanNo4 = transactionDetailModel.getTransactionNo() + 4;
		int amount4 = (int) transactionDetailModel.getPostalCharge();

		String OtherChargesTID2 = hsrpTransactionModel.getOtherChargesTID();
		int amount7 = (int) transactionDetailModel.getHsrpAmt();
		
		int amount5 = 0;
		String permitGreenDesc = "";
		String permitGreenHead = "";
		log.info(":::GrenTax Amount::::::: " + transactionDetailModel.getGreenTaxAmt());
		String challanNo5 = transactionDetailModel.getTransactionNo() + 5;
		if ((int) transactionDetailModel.getPermitAmt() > 0) {
			amount5 = (int) transactionDetailModel.getPermitAmt();
			permitGreenDesc = permitDesc;
			permitGreenHead = permitHead;
		}
		if ((int) transactionDetailModel.getGreenTaxAmt() > 0) {
			amount5 = (int) transactionDetailModel.getGreenTaxAmt();
			permitGreenDesc = greenTaxDesc;
			permitGreenHead = greenTaxHead;
		}
		int amount6 = (int)transactionDetailModel.getCessFee();
		
		double checkTotal = 0;
		checkTotal = (int) NumberParser.getRoundOff(amount1 + amount2 + amount3 + amount4  + amount5 + amount6 + amount7);
		
		//log.info(" RemmiterName " + remitterName + ":Amount_1:" + amount1 + ":Amount_2:" + amount2 + ":Amount_3:"
		//		+ amount3 + ":Amount_4:" + amount4 + ":Amount_7:" + amount7 + ":checkTotal:" + checkTotal);

		transactionDetailModel.setAmount(checkTotal);
		
		List<TreasureHeadDetails> treasureHeads = new ArrayList<>(5);
		// Fee Head Details
		TreasureHeadDetails feeHeadDetails = new TreasureHeadDetails();
		feeHeadDetails.setAmount(amount1);
		feeHeadDetails.setChallanNo(challanNo1);
		feeHeadDetails.setDdoCode(ddoCodevalue);
		feeHeadDetails.setDeptCode(deptCodeValue);
		feeHeadDetails.setHeadOfAccount(feeHead);
		feeHeadDetails.setPos(payUPos);
		feeHeadDetails.setRemitterName(firstName);
		treasureHeads.add(feeHeadDetails);

		// Life Tax or Quarterly Head Details
		TreasureHeadDetails lifeTaxOrQuarterlyHeadDetails = new TreasureHeadDetails();
		lifeTaxOrQuarterlyHeadDetails.setAmount(amount2);
		lifeTaxOrQuarterlyHeadDetails.setChallanNo(challanNo2);
		lifeTaxOrQuarterlyHeadDetails.setDdoCode(ddoCodevalue);
		lifeTaxOrQuarterlyHeadDetails.setDeptCode(deptCodeValue);
		lifeTaxOrQuarterlyHeadDetails.setHeadOfAccount(serviceHead);
		lifeTaxOrQuarterlyHeadDetails.setPos(payUPos);
		lifeTaxOrQuarterlyHeadDetails.setRemitterName(firstName);
		treasureHeads.add(lifeTaxOrQuarterlyHeadDetails);

		// Service Head Details
		TreasureHeadDetails serviceHeadDetails = new TreasureHeadDetails();
		serviceHeadDetails.setAmount(amount3);
		serviceHeadDetails.setChallanNo(challanNo3);
		serviceHeadDetails.setDdoCode(ddoCodevalue);
		serviceHeadDetails.setDeptCode(deptCodeValue);
		serviceHeadDetails.setHeadOfAccount(serviceHead);
		serviceHeadDetails.setPos(payUPos);
		serviceHeadDetails.setRemitterName(firstName);
		treasureHeads.add(serviceHeadDetails);

		// Postal Head Details
		TreasureHeadDetails postalHeadDetails = new TreasureHeadDetails();
		postalHeadDetails.setAmount(amount4);
		postalHeadDetails.setChallanNo(challanNo4);
		postalHeadDetails.setDdoCode(ddoCodevalue);
		postalHeadDetails.setDeptCode(deptCodeValue);
		postalHeadDetails.setHeadOfAccount(postalHead);
		postalHeadDetails.setPos(payUPos);
		postalHeadDetails.setRemitterName(firstName);
		treasureHeads.add(postalHeadDetails);
		
		//Green tax
		TreasureHeadDetails permitGreenDetails = new TreasureHeadDetails();
		permitGreenDetails.setAmount(amount5);
		permitGreenDetails.setChallanNo(challanNo5);
		permitGreenDetails.setDdoCode(ddoCodevalue);
		permitGreenDetails.setDeptCode(deptCodeValue);
		permitGreenDetails.setHeadOfAccount(greenTaxHead);
		permitGreenDetails.setPos(payUPos);
		permitGreenDetails.setRemitterName(firstName);
		treasureHeads.add(permitGreenDetails);
		
		//cess fee
		TreasureHeadDetails cessFeeDetails = new TreasureHeadDetails();
		cessFeeDetails.setAmount(amount6);
		cessFeeDetails.setDdoCode(ddoCodevalue);
		cessFeeDetails.setDeptCode(deptCodeValue);
		cessFeeDetails.setPos(payUPos);
		cessFeeDetails.setRemitterName(firstName);
		treasureHeads.add(cessFeeDetails);
		
		// HSRP amouont details
		PayuPaymentPart hsrp = new PayuPaymentPart();
		// No commission for RTA
		hsrp.setCommission("0");
		hsrp.setDescription(hsrpDesc);
		hsrp.setMerchantId(payUHsrpMerchantId);
		hsrp.setName(OtherChargesTID2);
		hsrp.setValue(String.valueOf(NumberParser.getRoundOff(amount7)));

		// Treasure amount details
		PayuPaymentPart treasure = new PayuPaymentPart();
		// No commission for RTA
		treasure.setCommission("0");
		treasure.setDescription(feeDesc + HOA_2_Description + serviceDesc + postalDesc+greenTaxDesc+cessFeeDesc);
		if(transactionDetailModel.getCitizenFlag())
			treasure.setMerchantId(payUTreasureMerchantId4Citizen);
		else
			treasure.setMerchantId(payUTreasureMerchantId);
		treasure.setName(deptCodeValue);
		treasure.setValue(String.valueOf(NumberParser.getRoundOff(transactionDetailModel.getAmount())));

		

		PayUProductInfo payUProductInfo = new PayUProductInfo();
		payUProductInfo.setPaymentParts(Arrays.asList(treasure));
		payUProductInfo.setTreasureHeads(treasureHeads);

		log.info("treasure amount:" + treasure.getValue());
		log.info("hsrp amount:" + hsrp.getValue());

		return getPayUProductInfo(payUProductInfo);
	}

	private String getPayUProductInfo(PayUProductInfo payUProductInfo) {

		try {
			return objectMapper.writeValueAsString(payUProductInfo);
		} catch (JsonProcessingException e) {
			log.error("Unable to parse PayUProductInfo", e);
			return "";
		}
	}

	private void generatePayUHash(TransactionDetailModel transactionDetailModel) {

		// JSON has truncating the .0 when double has .0 but toString()
		// returning .0
		// To solve the problem while hashing below check is implemented
		String amount = NumberParser.getDoubleToString(transactionDetailModel.getAmount());
		
		String hash = transactionDetailModel.getKey() + "|" + transactionDetailModel.getTransactionNo() + "|" + amount
				+ "|" + transactionDetailModel.getProductInfo() + "|" + transactionDetailModel.getFirstName() + "|"
				+ transactionDetailModel.getEmail() + "|||||||||||" + transactionDetailModel.getSalt();

		transactionDetailModel.setHash(HashingUtil.sha512HashCal(hash));
	}
	private String getPayUResponseParams(TransactionDetailModel transactionDetailModel) {

		try {
			return objectMapper.writeValueAsString(transactionDetailModel.getPayUTransactionDetails());
		} catch (JsonProcessingException e) {
			log.error("Unable to parse PayU response transaction details", e);
			return "";
		}
	}

		
	@Override
	@Transactional
	public TransactionDetailModel payuSecondVehicleEncryptedRequest(TransactionDetailModel transactionDetailModel,DealerModel dealerModel) {
		Long vehicleRcId = Long.parseLong(transactionDetailModel.getVehicleRcId());
		VehicleRCEntity vehicleEntity = vehicleDAO.get(vehicleRcId);
		transactionDetailModel.setEmail(dealerModel.getEmail());
		transactionDetailModel.setPhoneNumber(dealerModel.getPhone());
		String productInfo = getPayuSecondVehicleRequestParameter(transactionDetailModel, vehicleEntity);
		transactionDetailModel.setProductInfo(productInfo);
		transactionDetailModel.setKey(payUKey);
		generatePayUHash(transactionDetailModel);
		TransactionHistoryEntity transactionHistoryEntity = new TransactionHistoryEntity();
		transactionHistoryEntity.setRequestParameter(productInfo);
		transactionHistoryEntity.setTransactionNo(transactionDetailModel.getTransactionNo());
		transactionHistoryEntity.setVehicleRcId(vehicleEntity.getVehicleRcId());
		transactionHistoryEntity.setStatus(Status.OPEN.getValue());
		transactionHistoryEntity.setPaymentType(PaymentType.SECONDVEHICLETAXPAY.getId());
		transactionHistoryEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		transactionHistoryEntity.setPgType(PaymentGatewayType.PAYU.getLabel());
		transactionHistoryDAO.save(transactionHistoryEntity);
		log.debug("::::::::::::secondVehicleEncryptedRequest:::end:::::::::: " + productInfo);
		return transactionDetailModel;
	}
	
	
	public String getPayuSecondVehicleRequestParameter(TransactionDetailModel transactionDetailModel,
			VehicleRCEntity vehicleEntity) {
		log.info("::::::::::::::getSecondVehicleRequestParameter::::start:::::::::::::");
		CustIndividualDetailsEntity custDtlsEntity = new CustIndividualDetailsEntity();
		CustCorporateDetailsEntity custCorporateDetailsEntity = new CustCorporateDetailsEntity();
		String remitterName = "";
		if (vehicleEntity.getOwnershipType() == OwnershipType.INDIVIDUAL.getId()) {
			custDtlsEntity = custIndvDAO.getByVehicleRcId(vehicleEntity.getVehicleRcId());
			remitterName = custDtlsEntity.getFirstName() + " " + custDtlsEntity.getSurName();
			if (remitterName.length() > 30)
				remitterName = remitterName.substring(0, 29);
		} else {
			custCorporateDetailsEntity = customerCorporateDAO.getByVehicleRcId(vehicleEntity.getVehicleRcId());
			remitterName = custCorporateDetailsEntity.getCompanyName();
			if (remitterName.length() > 30)
				remitterName = remitterName.substring(0, 29);
		}
		SBIDDOMasterEntity sbiddoMasterEntity = sbiDDOMasterDAO
				.getByDistrictCode(vehicleEntity.getRtaOfficeId().getDistrictCode());
		log.info("::::::sbiddoMasterEntity:::::::: " + sbiddoMasterEntity);
		String DDO_Code_value = sbiddoMasterEntity.getDdoCode();

		List<SBIDistributionEntity> sbiDistributionList = sbiDistributionDAO.getAll();
		String HOA1 = "";
		String HOA1Description = "";
		String pollingAcTax = "";
		for (SBIDistributionEntity sbiDistribution : sbiDistributionList) {
			switch (SBIHeadType.getSBIHeadType(sbiDistribution.getHeadType())) {
			case LIFETAX:
				HOA1 = sbiDistribution.getMajorHead() + sbiDistribution.getMajorSubHead()
						+ sbiDistribution.getMinorSubHead() + sbiDistribution.getGroupSubHead()
						+ sbiDistribution.getSubHead() + sbiDistribution.getDetailedHead()
						+ sbiDistribution.getSubDetailedHead() + sbiDistribution.getHeadEndCode();
				HOA1Description = sbiDistribution.getHoaDescription();
				pollingAcTax = sbiDistribution.getCreditPollingAccount();
				break;
			}
		}

		int checkTotal = 0;
		if (testAmount)
			checkTotal = 1;
		else
			checkTotal = (int) NumberParser.getRoundOff(transactionDetailModel.getAmount());
		log.info(":checkTotal:" + checkTotal);
		String challanNo = transactionDetailModel.getTransactionNo() + 1;
		
		List<TreasureHeadDetails> treasureHeads = new ArrayList<>(1);
		
		TreasureHeadDetails hoa1 = new TreasureHeadDetails();
		hoa1.setAmount((int) NumberParser.getRoundOff(transactionDetailModel.getAmount()));
		hoa1.setChallanNo(challanNo);
		hoa1.setDdoCode(DDO_Code_value);
		hoa1.setDeptCode(deptCodeValue);
		hoa1.setHeadOfAccount(HOA1);
		hoa1.setPos(payUPos);
		hoa1.setRemitterName(remitterName);
		treasureHeads.add(hoa1);


		// Treasure amount details
		PayuPaymentPart treasure = new PayuPaymentPart();
		// No commission for RTA
		treasure.setCommission("0");
		treasure.setDescription(HOA1Description);
		treasure.setMerchantId(payUTreasureMerchantId);
		treasure.setName(deptCodeValue);
		treasure.setValue(String.valueOf(NumberParser.getRoundOff(transactionDetailModel.getAmount())));

		PayUProductInfo payUProductInfo = new PayUProductInfo();
		payUProductInfo.setPaymentParts(Arrays.asList(treasure));
		payUProductInfo.setTreasureHeads(treasureHeads);

		// Update the owner of vehicle
		transactionDetailModel.setFirstName(remitterName);

		log.info("treasure amount:" + treasure.getValue());
		return getPayUProductInfo(payUProductInfo);
	}
	
	@Override
	@Transactional
	public TransactionDetailModel decryptPaymentResponse(TransactionDetailModel transactionDetailModel) {
		switch(PaymentGatewayType.getPaymentGatewayType(transactionDetailModel.getPgType())){
		case PAYU:
			transactionDetailModel = decryptPayUResponse(transactionDetailModel);
			log.info("response status PayU " + transactionDetailModel.getStatus());
			// Checking the payu response checksum
			if (!checkResponsePayUHash(transactionDetailModel)) {
				log.error("PayU response checksum validation is  failed for transactionNo:"
						+ transactionDetailModel.getTransactionNo());
				throw new IllegalArgumentException("PayU response checksum validation is failed.");
			}

			break;
		case SBI:
			transactionDetailModel = decryptVerificationSBIResponse(transactionDetailModel);
			break;
		}
		return transactionDetailModel;
	}
}
