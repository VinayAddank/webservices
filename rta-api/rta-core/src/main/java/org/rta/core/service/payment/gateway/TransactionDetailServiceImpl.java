package org.rta.core.service.payment.gateway;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.hsrp.HSRPFeeDetailsDAO;
import org.rta.core.dao.master.registration.ChallanNumberDAO;
import org.rta.core.dao.master.registration.TrSeriesMasterDAO;
import org.rta.core.dao.payment.gateway.TransactionDetailDAO;
import org.rta.core.dao.payment.invoice.DealerInvoiceDAO;
import org.rta.core.dao.payment.registfee.FitnessFeeDAO;
import org.rta.core.dao.payment.registfee.RegFeeDetailDAO;
import org.rta.core.dao.payment.tax.LifeTaxDAO;
import org.rta.core.dao.payment.tax.PeriodicTaxDAO;
import org.rta.core.dao.payment.tax.VehicleCurrentTaxDAO;
import org.rta.core.dao.secondvehicle.SecondVehicleRejectionDAO;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.master.registration.ChallanNumberEntity;
import org.rta.core.entity.master.registration.TrSeriesMasterEntity;
import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.entity.payment.invoice.DealerInvoiceEntity;
import org.rta.core.entity.payment.registfee.FitnessFeeDetailsEntity;
import org.rta.core.entity.payment.registfee.RegFeeDetailsEntity;
import org.rta.core.entity.payment.tax.LifeTaxEntity;
import org.rta.core.entity.payment.tax.PeriodicTaxEntity;
import org.rta.core.entity.payment.tax.VehicleCurrentTaxEntity;
import org.rta.core.entity.secondvehicle.SecondVehicleDetailsEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.MonthType;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.PaymentGatewayType;
import org.rta.core.enums.PaymentType;
import org.rta.core.enums.RegistrationCategoryType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.enums.VehicleCategory;
import org.rta.core.enums.master.TaxType;
import org.rta.core.exception.NotFoundException;
import org.rta.core.helper.payment.gateway.TransactionDetailHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.customer.CustMsgModel;
import org.rta.core.model.payment.gateway.PayUTransactionDetails;
import org.rta.core.model.payment.gateway.TransactionDetailModel;
import org.rta.core.model.payment.tax.CessFeeModel;
import org.rta.core.service.MessagingService;
import org.rta.core.service.application.ApplicationService;
import org.rta.core.service.payment.tax.TaxDetailService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.NumberParser;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {

	private static final Logger log = Logger.getLogger(TransactionDetailServiceImpl.class);
	@Value("${rta.lifetax.validty}")
	int lifetaxValidty;
	@Autowired
	private TransactionDetailDAO transactionDetailDAO;

	@Autowired
	private TransactionDetailHelper transactionDetailHelper;

	@Autowired
	private DealerInvoiceDAO dealerInvoiceDAO;

	@Value("${jwt.claim.secret}")
	private String claimSecret;

	@Value("${rta.step.tr}")
	Integer currentStep;

	@Value("${rta.step.taxpay}")
	Integer previousStep;

	@Autowired
	private VehicleDAO vehicleDAO;

	@Autowired
	private CustomerDAO custIndvDAO;

	@Autowired
	private CustomerCorporateDAO cusCorpDAO;

	@Autowired
	private TrSeriesMasterDAO trSeriesMasterDAO;

	@Autowired
	private ChallanNumberDAO challanNumberDAO;

	@Value("${rta.sbi.Dept_code}")
	public String Dept_code_value;

	@Autowired
	ApplicationService applicatioService;

	@Autowired
	private SecondVehicleRejectionDAO secondVehicleRejectionDAO;

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private AddressDAO addressDAO;

	@Autowired
	private RegFeeDetailDAO regFeeDetailDAO;

	@Autowired
	private VehicleDetailsDAO vehicleDetailsDAO;
	@Value("${tr.series.subject}")
	private String trSubject;

	@Value("${tr.series.receiver}")
	private String trSeriesReceiver;

	@Value("${tr.series.cc}")
	private String trSeriesCC;

	@Value("${tr.series.notification.threshold}")
	private Integer trSeriesNotificationThreshold;

	@Value("${tr.series.notification.interval}")
	private Integer trSeriesNotificationInterval;

	@Value("${tr.series.mail.content}")
	private String trNotificationMailContent;

	@Autowired
	private MessagingService messagingService;

	private static final Integer MAX_ALLOWED_SERIES_NUMBER = 9999;

	@Value("${tr.series.payment.stop.threshold}")
	private Integer paymentStopThresholdValue;

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private HSRPFeeDetailsDAO hsrpFeeDetailsDAO;
	@Autowired
	private LifeTaxDAO lifeTaxDAO;
	@Autowired
	private PeriodicTaxDAO periodicTaxDAO;
	@Autowired
	private FitnessFeeDAO fitnessFeeDAO;
	@Autowired
	private VehicleCurrentTaxDAO vehicleCurrentTaxDAO;
	@Autowired
	private TaxDetailService taxDetailService;
	@Override
	@Transactional
	public TransactionDetailModel createPaymentRequest(DealerInvoiceEntity dealerInvoiceEntity, String userName,
			String challanNo, PaymentGatewayType pgType) {
		log.debug("::::::::::::createPaymentRequest:::::::::: ");
		TransactionDetailEntity transactionDetailEntity = null;
		TransactionDetailModel transactionDetailModel = null;
		VehicleDetailsEntity vehicleDetailEntity = vehicleDetailsDAO
				.getByVehicleRcId(dealerInvoiceEntity.getVehicleRcId().getVehicleRcId());

		double feeAmt = 0.0d, serviceFee = 0.0d;
		CessFeeModel cessFeeModel=null;
		RegFeeDetailsEntity regFeeDetailEntity = regFeeDetailDAO
				.getByVehicleRcId(dealerInvoiceEntity.getVehicleRcId().getVehicleRcId());
		double taxAmount = 0.0d;
		
		VehicleCurrentTaxEntity vc = vehicleCurrentTaxDAO
				.getEntityByVehicleRcId(dealerInvoiceEntity.getVehicleRcId().getVehicleRcId());
		if (vc != null)
			taxAmount = vc.getTaxAmt();
		
		FitnessFeeDetailsEntity fitnessFeeDetailsEntity = fitnessFeeDAO
				.getByVehicleRcId(dealerInvoiceEntity.getVehicleRcId().getVehicleRcId());
		switch (RegistrationCategoryType.getRegistrationCategoryType(dealerInvoiceEntity.getVehicleRcId().getRegCategory().getRegistrationCategoryId())) {
		case NON_TRANSPORT:
			feeAmt = NumberParser.getRoundOff(
					(regFeeDetailEntity.getTrFee() + regFeeDetailEntity.getHpaFee() + regFeeDetailEntity.getPrFee()
							+ regFeeDetailEntity.getSmartCardFee() + regFeeDetailEntity.getSpecialNumberFee()));
			serviceFee = NumberParser
					.getRoundOff(regFeeDetailEntity.getTrServiceCharge() + regFeeDetailEntity.getPrServiceCharge());
			break;
		case TRANSPORT:
			feeAmt = NumberParser.getRoundOff((regFeeDetailEntity.getTrFee() + regFeeDetailEntity.getHpaFee()
					+ regFeeDetailEntity.getPrFee() + regFeeDetailEntity.getSmartCardFee()
					+ (fitnessFeeDetailsEntity == null ? 0 : fitnessFeeDetailsEntity.getFitnessFee())
					+ regFeeDetailEntity.getSpecialNumberFee()));
			serviceFee = NumberParser
					.getRoundOff(regFeeDetailEntity.getTrServiceCharge() + regFeeDetailEntity.getPrServiceCharge()
							+ (fitnessFeeDetailsEntity == null ? 0 : fitnessFeeDetailsEntity.getFitnessService()));
			cessFeeModel = taxDetailService.viewCessFeeDetails(vehicleDetailEntity);
			break;
		}

		transactionDetailEntity = transactionDetailDAO.getByVehicleRcIdNdPaymentType(
				dealerInvoiceEntity.getVehicleRcId().getVehicleRcId(), PaymentType.PAYTAX.getId());

		if (transactionDetailEntity != null) {
			transactionDetailEntity.setFeeAmt(feeAmt);
			transactionDetailEntity.setServiceCharge(serviceFee);
			transactionDetailEntity.setHsrpAmt(NumberParser.getRoundOff(dealerInvoiceEntity.getHsrpFee()));
			transactionDetailEntity.setPostalCharge(NumberParser.getRoundOff(regFeeDetailEntity.getPostalCharge()));
			transactionDetailEntity.setTaxAmt(NumberParser.getRoundOff(taxAmount));
			if(cessFeeModel!=null) {
				transactionDetailEntity.setCessFee(NumberParser.getRoundOff(cessFeeModel.getCessFee()));
			}
			transactionDetailEntity.setPayAmount(totalPayAmount(transactionDetailEntity));
			transactionDetailEntity.setModifiedBy(userName);
			transactionDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			transactionDetailEntity.setPgType(pgType.getLabel());
			transactionDetailDAO.update(transactionDetailEntity);
			transactionDetailModel = transactionDetailHelper.convertToModel(transactionDetailEntity);
			return transactionDetailModel;
		} else {
			transactionDetailEntity = new TransactionDetailEntity();
			transactionDetailEntity.setFeeAmt(feeAmt);
			transactionDetailEntity.setServiceCharge(serviceFee);
			transactionDetailEntity.setHsrpAmt(NumberParser.getRoundOff(dealerInvoiceEntity.getHsrpFee()));
			transactionDetailEntity.setPostalCharge(NumberParser.getRoundOff(regFeeDetailEntity.getPostalCharge()));
			transactionDetailEntity.setTaxAmt(NumberParser.getRoundOff(taxAmount));
			transactionDetailEntity.setAccountType("--");
			if(cessFeeModel!=null) {
				transactionDetailEntity.setCessFee(NumberParser.getRoundOff(cessFeeModel.getCessFee()));
			}
			transactionDetailEntity.setPayAmount(totalPayAmount(transactionDetailEntity));
			transactionDetailEntity.setPaymentType(PaymentType.PAYTAX.getId());
			transactionDetailEntity.setVehicleRcId(vehicleDetailEntity.getVehicleRcId());
			transactionDetailEntity.setCreatedBy(userName);
			transactionDetailEntity.setPaymentTime(DateUtil.toCurrentUTCTimeStamp());
			transactionDetailEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			transactionDetailEntity.setStatus(Status.OPEN.getValue());
			transactionDetailEntity.setTransactionNo(challanNo);
			transactionDetailEntity.setPgType(pgType.getLabel());
			log.info("::::transactionDetailEntity::::: " + transactionDetailEntity.getVehicleRcId().getVehicleRcId());
			try {
				transactionDetailDAO.save(transactionDetailEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			transactionDetailModel = transactionDetailHelper.convertToModel(transactionDetailEntity);
			return transactionDetailModel;
		}

	}

	@Override
	@Transactional
	public TransactionDetailModel secondVehiclePaymentRequest(DealerInvoiceEntity dealerInvoiceEntity, String userName,
			String challanNo, PaymentGatewayType pg) {
		TransactionDetailEntity transactionDetailEntity = null;
		TransactionDetailModel transactionDetailModel = null;
		transactionDetailEntity = transactionDetailDAO.getByVehicleRcIdNdPaymentType(
				dealerInvoiceEntity.getVehicleRcId().getVehicleRcId(), PaymentType.SECONDVEHICLETAXPAY.getId());
		LifeTaxEntity lifeTaxEntity = lifeTaxDAO
				.getEntityByVehicleRcId(dealerInvoiceEntity.getVehicleRcId().getVehicleRcId());
		if (transactionDetailEntity != null) {
			transactionDetailModel = new TransactionDetailModel();
			transactionDetailEntity.setModifiedBy(userName);
			transactionDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			transactionDetailEntity.setPayAmount(NumberParser.getRoundOff(lifeTaxEntity.getSecondVehicleTaxAmt()));
			transactionDetailEntity.setPaymentTime(DateUtil.toCurrentUTCTimeStamp());
			transactionDetailEntity.setPgType(pg.getLabel());
			if (dealerInvoiceEntity.getUser().getUserName().equalsIgnoreCase(userName))
				transactionDetailDAO.update(transactionDetailEntity);
			transactionDetailModel = transactionDetailHelper.convertToModel(transactionDetailEntity);
			return transactionDetailModel;
		} else {
			transactionDetailModel = new TransactionDetailModel();
			transactionDetailEntity = new TransactionDetailEntity();
			// transactionDetailEntity.setDealerInvcId(dealerInvoiceEntity);
			transactionDetailEntity.setAccountType("--");
			transactionDetailEntity.setPayAmount(NumberParser.getRoundOff(lifeTaxEntity.getSecondVehicleTaxAmt()));
			transactionDetailEntity.setPaymentType(PaymentType.SECONDVEHICLETAXPAY.getId());
			transactionDetailEntity.setVehicleRcId(dealerInvoiceEntity.getVehicleRcId());
			transactionDetailEntity.setCreatedBy(userName);
			transactionDetailEntity.setPaymentTime(DateUtil.toCurrentUTCTimeStamp());
			transactionDetailEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			transactionDetailEntity.setStatus(Status.OPEN.getValue());
			transactionDetailEntity.setPgType(pg.getLabel());
			if (dealerInvoiceEntity.getUser().getUserName().equalsIgnoreCase(userName)) {
				transactionDetailEntity.setTransactionNo(challanNo);
				transactionDetailDAO.save(transactionDetailEntity);
			}
			transactionDetailModel = transactionDetailHelper.convertToModel(transactionDetailEntity);
			return transactionDetailModel;
		}
	}

	public double totalPayAmount(TransactionDetailEntity transactionDetailEntity) {
		double payAmt = 0.0d;
		payAmt = NumberParser.getRoundOff(transactionDetailEntity.getFeeAmt()
				+ transactionDetailEntity.getServiceCharge() + transactionDetailEntity.getPostalCharge()
				+ transactionDetailEntity.getTaxAmt() + transactionDetailEntity.getHsrpAmt()
				+ transactionDetailEntity.getPermitAmt() + transactionDetailEntity.getCessFee());
		log.info("::::totalPayAmount::::: " + payAmt);
		return payAmt;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public SaveUpdateResponse gatewayResponse(TransactionDetailModel transactionDetailModel, String userName,
			Long vehicleRcId, boolean isSecondVehiclePay, UserType userType) {
		log.info("::::::::::::gatewayResponse:::::: start:::::::");
		SaveUpdateResponse saveUpdateModel = new SaveUpdateResponse();
		Map<String, Object> attr = new HashMap<String, Object>();
		TransactionDetailEntity transactionDetailEntity = transactionDetailDAO
				.getByTransNoNdVehicleRcId(transactionDetailModel.getTransactionNo(), vehicleRcId);
		if (transactionDetailEntity == null) {
			throw new IllegalArgumentException("Invalid vehicleRcId or Transaction no");
		} else {
			if (validateSBIResponse(transactionDetailEntity, transactionDetailModel)) {
				transactionDetailEntity.setModifiedBy(userName);
				transactionDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
				transactionDetailEntity.setStatus(Status.CLOSED.getValue());
				transactionDetailEntity.setSbiRefNo(transactionDetailModel.getSbiResponseMap().get("sbi_ref_no"));
				transactionDetailEntity.setStatusMessage(transactionDetailModel.getSbiResponseMap().get("Status_desc"));
				transactionDetailDAO.update(transactionDetailEntity);
				VehicleRCEntity vehicleRCEntity = transactionDetailEntity.getVehicleRcId();
				boolean sendToApproval = true;
				if (isSecondVehiclePay) {
					try {
						Map<String, Object> map = applicatioService.getRejectionHistory4Correction(vehicleRcId,
								userType);
						Set<Integer> rejAttachmentSet = (Set<Integer>) map.get("rejectedSteps");
						log.debug("payment for second vehicle in case of rejection with rejected attachment : "
								+ rejAttachmentSet.size());
						for (Integer in : rejAttachmentSet) {
							log.info(vehicleRcId + ": Rejected Steps" + in);
						}
						if (!rejAttachmentSet.isEmpty()) {
							sendToApproval = false;
						}
					} catch (Exception e) {
						log.error("Exception in gatewayResponse : " + e.getMessage());
						log.debug(e);
					}
					updateSecondVehiclePaidTax(vehicleRCEntity);
				} else {
					vehicleRCEntity.setCurrentStep(currentStep);
					vehicleRCEntity.setTrStatus(Status.PENDING.getValue());
					try {
						vehicleRCEntity.setTrNumber(getTrSeriesNo(vehicleRCEntity.getUserId()));
						vehicleRCEntity.setTrStatus(Status.APPROVED.getValue());
						vehicleRCEntity.setTrIssueTime(DateUtil.toCurrentUTCTimeStamp());
						attr.put("trGenerated", Boolean.TRUE);
						sendToApproval = true;
					} catch (NotFoundException e) {
						log.error("tr not generated for vehicleRcId " + vehicleRcId);
						attr.put("trGenerated", Boolean.FALSE);
						sendToApproval = false;
					}
				}
				if (sendToApproval) {

					if (getChassisOnlyVehicle(vehicleRcId)) {
						vehicleRCEntity.setProcessStatus(Status.BODY_BUILDER.getValue());
					} else if (vehicleRCEntity.isAadharVerified()) {
						vehicleRCEntity.setCcoUserId(null);
						vehicleRCEntity.setMviUserId(null);
						vehicleRCEntity.setAoUserId(null);
						vehicleRCEntity.setRtoUserId(null);
						vehicleRCEntity.setProcessStatus(Status.PENDING.getValue());
						vehicleRCEntity.setCcoActionStatus(Status.PENDING.getValue());
						vehicleRCEntity.setMviActionStatus(Status.PENDING.getValue());
						vehicleRCEntity.setAoActionStatus(Status.PENDING.getValue());
						vehicleRCEntity.setRtoActionStatus(Status.PENDING.getValue());
					} else {
						vehicleRCEntity.setProcessStatus(Status.AADHAR_PENDING.getValue());
					}

					vehicleRCEntity.setPrStatus(Status.PENDING.getValue());
					if (vehicleRCEntity.getIteration() == null)
						vehicleRCEntity.setIteration(0);
					vehicleRCEntity.setIteration(vehicleRCEntity.getIteration() + 1);
				}

				vehicleDAO.update(vehicleRCEntity);
				updateTaxDetails(transactionDetailEntity);

				saveUpdateModel = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS,
						transactionDetailModel.getSbiResponseMap().get("Status_desc"), String.valueOf(vehicleRcId),
						currentStep);
				saveUpdateModel.setAttributes(attr);
			} else {
				if (transactionDetailModel.getSbiResponseMap().get("Status").equalsIgnoreCase("PENDING")) {
					transactionDetailEntity.setModifiedBy(userName);
					transactionDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
					transactionDetailEntity.setStatus(Status.PENDING.getValue());
					try {
						transactionDetailEntity
								.setSbiRefNo(transactionDetailModel.getSbiResponseMap().get("sbi_ref_no"));
					} catch (Exception e) {
						log.error("Exception : " + e.getMessage());
						log.debug(e);
					}
					transactionDetailEntity
							.setStatusMessage(transactionDetailModel.getSbiResponseMap().get("Status_desc"));
					transactionDetailDAO.update(transactionDetailEntity);
					saveUpdateModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE,
							transactionDetailModel.getSbiResponseMap().get("Status_desc"), String.valueOf(vehicleRcId),
							previousStep);
				} else {
					transactionDetailEntity.setModifiedBy(userName);
					transactionDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
					transactionDetailEntity.setStatus(Status.FAILURE.getValue());
					try {
						transactionDetailEntity
								.setSbiRefNo(transactionDetailModel.getSbiResponseMap().get("sbi_ref_no"));
					} catch (Exception e) {
						log.error("Exception : " + e.getMessage());
						log.debug(e);
					}
					transactionDetailEntity
							.setStatusMessage(transactionDetailModel.getSbiResponseMap().get("Status_desc"));
					transactionDetailDAO.update(transactionDetailEntity);
					saveUpdateModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE,
							transactionDetailModel.getSbiResponseMap().get("Status_desc"), String.valueOf(vehicleRcId),
							previousStep);
				}
			}
		}
		return saveUpdateModel;
	}

	private void updateTaxDetails(TransactionDetailEntity transactionDetailEntity) {
		VehicleCurrentTaxEntity vehicleCurrentTaxEntity = vehicleCurrentTaxDAO
				.getEntityByVehicleRcId(transactionDetailEntity.getVehicleRcId().getVehicleRcId());
		vehicleCurrentTaxEntity.setTaxValidFrom(DateUtil.toCurrentUTCTimeStamp());
		if (TaxType.FIRST_QUARTERLY.getValue() == vehicleCurrentTaxEntity.getTaxTypeId().intValue()) {
			if(vehicleCurrentTaxEntity.getTaxAmt() > 0)
				vehicleCurrentTaxEntity.setTaxValidUpto(taxValidty());
			else
				vehicleCurrentTaxEntity
				.setTaxValidUpto(DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), lifetaxValidty));
		} else {
			vehicleCurrentTaxEntity
					.setTaxValidUpto(DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), lifetaxValidty));
		}
		vehicleCurrentTaxDAO.saveOrUpdate(vehicleCurrentTaxEntity);
	}

	public long taxValidty() {
		int monthType = calculateQuarterlyTax();
		long taxValid = DateUtil.toLastDayOfMonth(monthType - 1);
		return taxValid;
	}

	private int calculateQuarterlyTax() {
		int quartltyPart = 0;
		switch (MonthType.getMonthType(DateUtil.getMonth(DateUtil.toCurrentUTCTimeStamp()))) {
		case JANUARY:
			quartltyPart = 3;
			break;
		case FEBRUARY:
			quartltyPart = 2;
			break;
		case MARCH:
			quartltyPart = 1;
			break;
		case APRIL:
			quartltyPart = 3;
			break;
		case MAY:
			quartltyPart = 2;
			break;
		case JUNE:
			quartltyPart = 1;
			break;
		case JULY:
			quartltyPart = 3;
			break;
		case AUGUST:
			quartltyPart = 2;
			break;
		case SEPTEMBER:
			quartltyPart = 1;
			break;
		case OCTOBER:
			quartltyPart = 3;
			break;
		case NOVEMBER:
			quartltyPart = 2;
			break;
		case DECEMBER:
			quartltyPart = 1;
			break;
		}
		return quartltyPart;
	}

	private Boolean getChassisOnlyVehicle(Long vehicleRcId) {

		VehicleDetailsEntity vehicleDetailsEntity = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
		return ((vehicleDetailsEntity.getVehicleCategory() == VehicleCategory.CHASSIS_ONLY) ? true : false);
	}

	public Boolean validateSBIResponse(TransactionDetailEntity persistTransDtlEntity,
			TransactionDetailModel transactionDetailModel) {
		if (transactionDetailModel.getSbiResponseMap().get("Status").equalsIgnoreCase("SUCCESS"))
			return true;
		else
			return false;
	}

	@Transactional
	public CustMsgModel getCustDtlsForVehicleRC(Long vehicleRcId) {
		VehicleRCEntity vehicleEntity = vehicleDAO.get(vehicleRcId);
		CustIndividualDetailsEntity custDtlsEntity = null;
		CustCorporateDetailsEntity custCorpEntity = null;
		CustMsgModel custMsgModel = null;
		if (vehicleEntity == null)
			throw new IllegalArgumentException("No vehicle with vehicleRcId " + vehicleRcId + " Exist");

		if (StringsUtil.isNullOrEmpty(vehicleEntity.getTrNumber()))
			throw new IllegalArgumentException("TR no not generated for vehicleRc " + vehicleRcId);

		if (vehicleEntity.getOwnershipType() == null)
			throw new IllegalArgumentException("No customerId found");
		if (vehicleEntity.getOwnershipType() == OwnershipType.INDIVIDUAL.getId()) {
			custDtlsEntity = custIndvDAO.getByVehicleRcId(vehicleRcId);
			if (custDtlsEntity == null)
				throw new IllegalArgumentException(
						"No customer is regestered with vehicleRcId " + vehicleRcId + " Exist");
			custMsgModel = new CustMsgModel();
			custMsgModel.setCustName(custDtlsEntity.getDisplayName());
			custMsgModel.setOwnershipType(OwnershipType.INDIVIDUAL.getId());
			custMsgModel.setMailContent(getMailContent(custDtlsEntity.getDisplayName(), vehicleEntity));
			custMsgModel.setBcc("");
			custMsgModel.setCc(vehicleEntity.getUserId().getEmail());
			custMsgModel.setSubject("AP_RTD_Temporary Registration Number(" + vehicleEntity.getTrNumber() + ")");
			custMsgModel.setTo(custDtlsEntity.getEmailId());
			custMsgModel.setMobileNo(custDtlsEntity.getMobileNo());
			custMsgModel.setSmsMsg(getMsgContent(custDtlsEntity.getDisplayName(), vehicleEntity));
		} else if (vehicleEntity.getOwnershipType() == OwnershipType.COMPANY.getId()
				|| vehicleEntity.getOwnershipType() == OwnershipType.POLICE.getId()
				|| vehicleEntity.getOwnershipType() == OwnershipType.STU_VEHICLES.getId()
				|| vehicleEntity.getOwnershipType() == OwnershipType.ORGANIZATION.getId()) {
			custCorpEntity = cusCorpDAO.getByVehicleRcId(vehicleRcId);
			if (custCorpEntity == null)
				throw new IllegalArgumentException(
						"No customer is regestered with vehicleRcId " + vehicleRcId + " Exist");
			custMsgModel = new CustMsgModel();
			custMsgModel.setCustName(custCorpEntity.getDisplayName());
			if (custCorpEntity.getCompanyName().equalsIgnoreCase(OwnershipType.COMPANY.getLabel())) {
				custMsgModel.setOwnershipType(OwnershipType.COMPANY.getId());
			} else if (custCorpEntity.getCompanyName().equalsIgnoreCase(OwnershipType.POLICE.getLabel())) {
				custMsgModel.setOwnershipType(OwnershipType.POLICE.getId());
			} else if (custCorpEntity.getCompanyName().equalsIgnoreCase(OwnershipType.STU_VEHICLES.getLabel())) {
				custMsgModel.setOwnershipType(OwnershipType.STU_VEHICLES.getId());
			} else if (custCorpEntity.getCompanyName().equalsIgnoreCase(OwnershipType.ORGANIZATION.getLabel())) {
				custMsgModel.setOwnershipType(OwnershipType.ORGANIZATION.getId());
			}
			custMsgModel.setMailContent(getMailContent(custCorpEntity.getDisplayName(), vehicleEntity));
			custMsgModel.setBcc("");
			custMsgModel.setCc(vehicleEntity.getUserId().getEmail());
			custMsgModel.setSubject("AP_RTD_Temporary Registration Number(" + vehicleEntity.getTrNumber() + ")");
			custMsgModel.setTo(custCorpEntity.getEmail());
			custMsgModel.setMobileNo(custCorpEntity.getMobile());
			custMsgModel.setSmsMsg(getMsgContent(custCorpEntity.getDisplayName(), vehicleEntity));

		}
		return custMsgModel;
	}

	private boolean checkSeriesExpiration(Integer useNumber) {
		return useNumber.intValue() > MAX_ALLOWED_SERIES_NUMBER;
	}

	@Transactional
	@Override
	public String getChallanNumberForFreshRegist(long vehiclRcId , Integer payType) {
		TransactionDetailEntity transactionDetailEntity = null;
		transactionDetailEntity = transactionDetailDAO.getByVehicleRcIdNdPaymentType(vehiclRcId, payType);
		if (transactionDetailEntity != null) {
			return null;
		}
		return getChallanNumber();
	}
	
	
	@Transactional
	@Override
	public String getChallanNumber() {
		log.info("::::::::getChallanNumber:: start:::::::");
		Long challanNo = 0l;
		ChallanNumberEntity challanNumberEntity = challanNumberDAO.getBytreasuryCode(Dept_code_value);
		Long consumeNo = challanNumberEntity.getConsumeNumber();
		challanNo = ++consumeNo;
		challanNumberEntity.setConsumeNumber(challanNo);
		challanNumberEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		challanNumberDAO.saveOrUpdate(challanNumberEntity);
		String challanFormat = challanNumberEntity.getConsumeNumber().toString();
		log.debug("::::::::getChallanNumber:: end::::::: " + challanNo);
		return challanFormat;
	}

	private void updateSecondVehiclePaidTax(VehicleRCEntity vehicleRCEntity) {
		SecondVehicleDetailsEntity secondVehicleDetailsEntity = secondVehicleRejectionDAO
				.getSecondVehicleDetails(vehicleRCEntity.getVehicleRcId(), vehicleRCEntity.getIteration());
		secondVehicleDetailsEntity.setPaidTax(true);
		secondVehicleRejectionDAO.saveOrUpdate(secondVehicleDetailsEntity);
		if (vehicleRCEntity.getOwnershipType() == OwnershipType.INDIVIDUAL.getId()) {
			log.debug("Updating customer individual for second vehicle");
			CustIndividualDetailsEntity custIndEntity = customerDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
			custIndEntity.setIsSecondVehicle(true);
			customerDAO.saveOrUpdate(custIndEntity);
		}
		try {

			LifeTaxEntity lifeTaxEntity = lifeTaxDAO.getEntityByVehicleRcId(vehicleRCEntity.getVehicleRcId());
			lifeTaxEntity.setTotalTax(lifeTaxEntity.getSecondVehicleTaxAmt() + lifeTaxEntity.getTaxAmt());
			lifeTaxEntity.setTaxPercent(lifeTaxEntity.getTaxPercent() + lifeTaxEntity.getSecondVehicleTaxPercent());
			lifeTaxDAO.saveOrUpdate(lifeTaxEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	@Transactional
	public TransactionDetailModel paymentVerificationReq(Long vehiclRcId, String userName, Integer payType,
			PaymentGatewayType pgType) {
		log.debug("::::::::::::paymentVerificationReq::start::::::::");
		TransactionDetailEntity transactionDetailEntity = null;
		TransactionDetailModel transactionDetailModel = null;
		transactionDetailEntity = transactionDetailDAO.getByVehicleRcIdNdPaymentType(vehiclRcId, payType);
		if (transactionDetailEntity == null) {
			return null;
		}
		transactionDetailModel = new TransactionDetailModel();
		transactionDetailEntity.setModifiedBy(userName);
		transactionDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		transactionDetailEntity.setPgType(pgType.getLabel());
		transactionDetailDAO.update(transactionDetailEntity);
		transactionDetailModel = transactionDetailHelper.convertToModel(transactionDetailEntity);
		return transactionDetailModel;
	}

	@Override
	@Transactional
	public int[] isSBIVerificationForSecondVehicle(Long vehiclRcId) {
		int status[] = new int[2];
		List<TransactionDetailEntity> transactionDetailEntityList = transactionDetailDAO
				.getAllByVehicleRcNdPayType(vehiclRcId, PaymentType.SECONDVEHICLETAXPAY.getId());
		if (transactionDetailEntityList != null && transactionDetailEntityList.size() == 0) {
			status[0] = 0;
			status[1] = 0;
			return status;
		}
		TransactionDetailEntity transactionDetailEntity = transactionDetailDAO.getByVehicleRcIdNdPaymentType(vehiclRcId,
				PaymentType.SECONDVEHICLETAXPAY.getId());
		if (transactionDetailEntity != null) {
			status[0] = 1;
			if (transactionDetailEntity.getPgType() != null
					&& transactionDetailEntity.getPgType().equalsIgnoreCase(PaymentGatewayType.PAYU.getLabel())) {
				status[1] = 2;
			} else {
				status[1] = 1;
			}
		}
		log.debug(":::::::::::isSBIVerificationForSecondVehicle::::::end:::::: " + status);
		return status;
	}

	@Transactional
	public boolean isAppActive(Long vehicleRcId) {
		VehicleRCEntity vehicleRc = vehicleDAO.get(vehicleRcId);
		if (vehicleRc == null)
			throw new IllegalArgumentException("Invalid vehicleRcId");
		if (vehicleRc.getTrStatus() == Status.CANCELLED.getValue())
			return false;
		return true;

	}

	@Override
	@Transactional
	public List<TransactionDetailModel> getAllTransactionByAppNo(String applicationNo) {
		log.info(":::::::::getAllTransactionByAppNo::start::::::::: " + applicationNo);
		if (applicationNo.length() < 6) {
			throw new IllegalArgumentException("Application No Invalid");
		}
		Long vehicleRcId = Long.valueOf(applicationNo.substring(5, applicationNo.length()));
		List<TransactionDetailEntity> transactionDetailEntityList = transactionDetailDAO
				.getAllByVehicleRcNdPayType(vehicleRcId, PaymentType.PAYTAX.getId());
		if (transactionDetailEntityList != null && transactionDetailEntityList.size() == 0) {
			throw new IllegalArgumentException("No Records Found");
		}
		List<TransactionDetailModel> transactionDetailModels = (List<TransactionDetailModel>) transactionDetailHelper
				.convertToModelList(transactionDetailEntityList);
		log.debug(":::::::::getAllTransactionByAppNo::end::::::::: ");
		return transactionDetailModels;
	}

	public static String getMailContent(String displayName, VehicleRCEntity vehicleEntity) {
		String mailContent = "";
		mailContent = "<table><tr><td>Dear " + displayName + ",</td></tr>"
				+ "<tr><td>Your Vehicle Temporary Registration has been generated.Your TR Number is "
				+ vehicleEntity.getTrNumber()
				+ ".</tr></td><tr><td></td></tr><tr><td></td></tr><tr><td>Thank You</td></tr><tr>AP_Road Transport</tr></table>";
		return mailContent;
	}

	public static String getMsgContent(String displayName, VehicleRCEntity vehicleEntity) {

		String msgContent = "";
		msgContent = "Dear  " + displayName
				+ " your Vehicle Temporary Registration has been generated.TR Number for the Vehicle is "
				+ vehicleEntity.getTrNumber() + " .Soft copy of TR will be sent to your registered email address";
		return msgContent;
	}
	/*
	 * @Autowired private UserDAO userDAO;
	 */

	/*
	 * @Override
	 * 
	 * @Transactional public String getTrSeriesNo(Long dealerId) { UserEntity
	 * userEntity = userDAO.findByUserId(5L); try { return
	 * getTrSeriesNo(userEntity); } catch (NotFoundException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return null; }
	 */

	@Override
	@Transactional
	public boolean checkTRSeriesStatus(Long vehicleRcId) throws NotFoundException {
		VehicleRCEntity vrc = vehicleDAO.get(vehicleRcId);
		AddressEntity addressEntity = addressDAO.findByUserIdAndType(vrc.getUserId().getUserId(), "T");
		TrSeriesMasterEntity presentSeries = trSeriesMasterDAO.getByDistrict(addressEntity);
		if (ObjectsUtil.isNull(presentSeries)) {
			log.error("tr series not found for district  : " + addressEntity.getMandal().getDistrictEntity().getName());
			throw new NotFoundException("tr series not found");
		}
		Integer useNumber = presentSeries.getUsedNumber();
		List<TrSeriesMasterEntity> futureSeries = trSeriesMasterDAO.getFutureTrSeries(addressEntity);
		if (useNumber.intValue() > MAX_ALLOWED_SERIES_NUMBER.intValue() - paymentStopThresholdValue
				&& ObjectsUtil.isNullOrEmpty(futureSeries)) {
			log.error("tr series not found for district  : " + addressEntity.getMandal().getDistrictEntity().getName());
			throw new NotFoundException("tr series not found");
		}
		return true;
	}

	public String getTrSeriesNo(UserEntity userEntity) throws NotFoundException {
		log.info("::::::getTrSeriesNo:::::start::");
		AddressEntity addressEntity = addressDAO.findByUserIdAndType(userEntity.getUserId(), "T");
		TrSeriesMasterEntity activeSeries;
		TrSeriesMasterEntity presentSeries = trSeriesMasterDAO.getByDistrict(addressEntity);
		Integer useNumber = presentSeries.getUsedNumber();
		activeSeries = presentSeries;
		Integer threshold = MAX_ALLOWED_SERIES_NUMBER - trSeriesNotificationThreshold;
		if ((useNumber >= threshold) && (useNumber % trSeriesNotificationInterval == 0)) {
			List<TrSeriesMasterEntity> freshSeries = trSeriesMasterDAO.getFutureTrSeries(addressEntity);
			boolean hasFutureSeries = true;
			if (ObjectsUtil.isNullOrEmpty(freshSeries)) {
				log.warn("tr-series about to end for district : "
						+ addressEntity.getMandal().getDistrictEntity().getName() + ", current used number : "
						+ useNumber);
				CustMsgModel csm = new CustMsgModel();
				csm.setSubject(trSubject);
				csm.setTo(trSeriesReceiver);
				csm.setCc(trSeriesCC);
				String template = trNotificationMailContent;
				StringBuilder sb = new StringBuilder(template);
				// Alert: You are approaching the limit,Please add new series!!!
				// \n District : #districtName \n Current TR used Number -
				// #number \n Please add new series. \n\n This is a system
				// generated email, Please do not reply.
				String districtKey = "#districtName";
				String usedNumberKey = "#number";
				sb.replace(template.indexOf(districtKey), template.indexOf(districtKey) + districtKey.length(),
						addressEntity.getMandal().getDistrictEntity().getName());
				sb.replace(template.indexOf(usedNumberKey), template.indexOf(usedNumberKey) + usedNumberKey.length(),
						String.valueOf(useNumber));
				csm.setMailContent(sb.toString());
				messagingService.sendEmail(csm);
				hasFutureSeries = false;
			}
			if (checkSeriesExpiration(useNumber)) {
				if (hasFutureSeries) {
					TrSeriesMasterEntity presentSeriesNew = freshSeries.get(0);
					presentSeriesNew.setStatus(Status.PRESENT.getValue());
					useNumber = presentSeriesNew.getUsedNumber();
					trSeriesMasterDAO.update(presentSeriesNew);
					presentSeries.setStatus(Status.PAST.getValue());
					activeSeries = presentSeriesNew;
				} else {
					log.error("no new tr series found for district  : "
							+ addressEntity.getMandal().getDistrictEntity().getName());
					throw new NotFoundException("tr-series not found");
				}
			}
		}
		String formattedString = NumberParser.getFormatedNumber(useNumber, "xxxx", 'x', '0');
		String number = activeSeries.getSeries() + formattedString;
		activeSeries.setUsedNumber(++useNumber);
		trSeriesMasterDAO.update(activeSeries);
		log.debug("::::::getTrSeriesNo:::::end::");
		return number;
	}

	/*@Override
	@Transactional
	public void paymentStatusFailed(Long vehicleRcId, String userName, int paymentType) {
		log.info(":::::::::paymentStatusFailed::start:::::::::");
		TransactionDetailEntity transactionDetailEntity = transactionDetailDAO
				.getByVehicleRcIdNdPaymentType(vehicleRcId, paymentType);
		if (transactionDetailEntity != null && (transactionDetailEntity.getStatus() == Status.PENDING.getValue())) {
			transactionDetailEntity.setStatus(Status.FAILURE.getValue());
			transactionDetailEntity.setStatusMessage("RTA Marked Status Failed");
			transactionDetailEntity.setModifiedBy(userName);
			transactionDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			transactionDetailDAO.update(transactionDetailEntity);
			log.info(":::::::::paymentStatusFailed::end:::::::::");
		}

	}
*/
	@Override
	@Transactional
	public boolean checkPaymentDone(Long vehicleRcId, int payType) {
		TransactionDetailEntity transactionDetailEntity = null;
		transactionDetailEntity = transactionDetailDAO.getByVehicleRcNdStatus(vehicleRcId,
				PaymentType.getPaymentType(payType), Status.CLOSED);
		if (transactionDetailEntity != null)
			return true;
		else
			return false;
	}

	@Override
	@Transactional
	public SaveUpdateResponse gatewayPayUResponse(TransactionDetailModel transactionDetailModel, String userName,
			Long vehicleRcId, boolean isSecondVehiclePay, UserType userType) {
		log.debug("::::::::::::gatewayResponse:::::: start:::::::");
		SaveUpdateResponse saveUpdateModel = new SaveUpdateResponse();
		TransactionDetailEntity transactionDetailEntity = transactionDetailDAO
				.getByTransNoNdVehicleRcId(transactionDetailModel.getTransactionNo(), vehicleRcId);
		if (transactionDetailEntity == null) {
			throw new IllegalArgumentException("Invalid vehicleRcId : " + vehicleRcId + "or Transaction no:"
					+ transactionDetailModel.getTransactionNo());
		}
		PayUTransactionDetails payUTransactionDetails = transactionDetailModel.getPayUTransactionDetails();
		if (validatePayUResponse(transactionDetailEntity, transactionDetailModel)) {
			transactionDetailEntity.setModifiedBy(userName);
			transactionDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			transactionDetailEntity.setStatus(Status.CLOSED.getValue());
			transactionDetailEntity.setSbiRefNo(payUTransactionDetails.getBank_ref_num());
			transactionDetailEntity.setStatusMessage(payUTransactionDetails.getStatus());
			transactionDetailEntity.setPgType(PaymentGatewayType.PAYU.getLabel());
			transactionDetailDAO.update(transactionDetailEntity);
			VehicleRCEntity vehicleRCEntity = transactionDetailEntity.getVehicleRcId();
			boolean sendToApproval = true;
			if (isSecondVehiclePay) {
				try {
					Map<String, Object> map = applicatioService.getRejectionHistory4Correction(vehicleRcId, userType);
					Set<Integer> rejAttachmentSet = (Set<Integer>) map.get("rejectedSteps");
					log.debug("payment for second vehicle in case of rejection with rejected attachment : "
							+ rejAttachmentSet.size());
					if (!rejAttachmentSet.isEmpty()) {
						sendToApproval = false;
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}
				updateSecondVehiclePaidTax(vehicleRCEntity);
			} else {
				vehicleRCEntity.setCurrentStep(currentStep);
				vehicleRCEntity.setTrStatus(Status.APPROVED.getValue());
				vehicleRCEntity.setTrIssueTime(DateUtil.toCurrentUTCTimeStamp());
				try {
					vehicleRCEntity.setTrNumber(getTrSeriesNo(vehicleRCEntity.getUserId()));
				} catch (NotFoundException e) {
					e.printStackTrace();
				}
			}
			if (sendToApproval) {
				if (getChassisOnlyVehicle(vehicleRcId)) {
					vehicleRCEntity.setProcessStatus(Status.BODY_BUILDER.getValue());
				} else if (vehicleRCEntity.isAadharVerified()) {
					vehicleRCEntity.setCcoUserId(null);
					vehicleRCEntity.setMviUserId(null);
					vehicleRCEntity.setAoUserId(null);
					vehicleRCEntity.setRtoUserId(null);
					vehicleRCEntity.setProcessStatus(Status.PENDING.getValue());
					vehicleRCEntity.setCcoActionStatus(Status.PENDING.getValue());
					vehicleRCEntity.setMviActionStatus(Status.PENDING.getValue());
					vehicleRCEntity.setAoActionStatus(Status.PENDING.getValue());
					vehicleRCEntity.setRtoActionStatus(Status.PENDING.getValue());
				} else {
					vehicleRCEntity.setProcessStatus(Status.AADHAR_PENDING.getValue());
				}

				vehicleRCEntity.setPrStatus(Status.PENDING.getValue());
				if (vehicleRCEntity.getIteration() == null)
					vehicleRCEntity.setIteration(0);
				vehicleRCEntity.setIteration(vehicleRCEntity.getIteration() + 1);
			}
			vehicleDAO.update(vehicleRCEntity);
			updateTaxDetails(transactionDetailEntity);
			saveUpdateModel = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, payUTransactionDetails.getStatus(),
					String.valueOf(vehicleRcId), currentStep);
		} else {
			if ("pending".equalsIgnoreCase(payUTransactionDetails.getStatus())) {
				transactionDetailEntity.setModifiedBy(userName);
				transactionDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
				transactionDetailEntity.setStatus(Status.PENDING.getValue());
				transactionDetailEntity.setStatusMessage(payUTransactionDetails.getStatus());
				transactionDetailEntity.setPgType(PaymentGatewayType.PAYU.getLabel());
				transactionDetailDAO.update(transactionDetailEntity);
				saveUpdateModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE, payUTransactionDetails.getStatus(),
						String.valueOf(vehicleRcId), previousStep);
			} else {
				transactionDetailEntity.setModifiedBy(userName);
				transactionDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
				transactionDetailEntity.setStatus(Status.FAILURE.getValue());
				transactionDetailEntity.setStatusMessage(payUTransactionDetails.getStatus());
				transactionDetailEntity.setPgType(PaymentGatewayType.PAYU.getLabel());
				transactionDetailDAO.update(transactionDetailEntity);
				saveUpdateModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE, payUTransactionDetails.getStatus(),
						String.valueOf(vehicleRcId), previousStep);
			}
		}

		return saveUpdateModel;
	}

	public Boolean validatePayUResponse(TransactionDetailEntity persistTransDtlEntity,
			TransactionDetailModel transactionDetailModel) {
		log.info(":::::::::::validatePayUResponse:::::::::::");

		if ("success".equalsIgnoreCase(transactionDetailModel.getPayUTransactionDetails().getStatus())) {
			log.info(":::::::::::validatePayUResponse::::Status:::True::::");
			return true;
		} else {
			log.info(":::::::::::validatePayUResponse:::::::False::::");
			return false;
		}
	}

	@Override
	@Transactional
	public void updatePayUFaliureResponse(String txnNumber, Long vehicleRcId, String userName, String message) {
		TransactionDetailEntity transactionDetailEntity = transactionDetailDAO.getByTransNoNdVehicleRcId(txnNumber,
				vehicleRcId);
		if (transactionDetailEntity == null) {
			throw new IllegalArgumentException(
					"Invalid vehicleRcId : " + vehicleRcId + "or Transaction no:" + txnNumber);
		}
		transactionDetailEntity.setModifiedBy(userName);
		transactionDetailEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		transactionDetailEntity.setStatus(Status.FAILURE.getValue());
		transactionDetailEntity.setStatusMessage(message);
		transactionDetailEntity.setPgType(PaymentGatewayType.PAYU.getLabel());
		transactionDetailDAO.update(transactionDetailEntity);
	}
}
