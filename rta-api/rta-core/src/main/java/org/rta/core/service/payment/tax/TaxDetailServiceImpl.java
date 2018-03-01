package org.rta.core.service.payment.tax;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.constant.SomeConstants;
import org.rta.core.dao.certificate.FcDetailsDAO;
import org.rta.core.dao.certificate.PucDetailsDAO;
import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.docs.AttachmentDetailsDAO;
import org.rta.core.dao.master.TaxTypeDAO;
import org.rta.core.dao.payment.gateway.TransactionDetailDAO;
import org.rta.core.dao.payment.registfee.CessFeeDetailDAO;
import org.rta.core.dao.payment.registfee.FitnessFeeDAO;
import org.rta.core.dao.payment.registfee.RegFeeDetailDAO;
import org.rta.core.dao.payment.tax.GreenTaxDetailsDAO;
import org.rta.core.dao.payment.tax.LifeTaxDAO;
import org.rta.core.dao.payment.tax.PeriodicTaxDAO;
import org.rta.core.dao.payment.tax.VehicleCurrentTaxDAO;
import org.rta.core.dao.permit.PermitAuthCardDAO;
import org.rta.core.dao.permit.PermitHeaderDAO;
import org.rta.core.dao.secondvehicle.SecondVehicleRejectionDAO;
import org.rta.core.dao.vehicle.VehicleAlterationDAO;
import org.rta.core.dao.vehicle.VehicleBillingDetailsDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.dao.vehicle.cms.VehicleClassDescDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.certificate.FitnessCertificateEntity;
import org.rta.core.entity.certificate.PucDetailsEntity;
import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.entity.payment.registfee.CessFeeDetailsEntity;
import org.rta.core.entity.payment.registfee.FitnessFeeDetailsEntity;
import org.rta.core.entity.payment.registfee.RegFeeDetailsEntity;
import org.rta.core.entity.payment.tax.GreenTaxDetailsEntity;
import org.rta.core.entity.payment.tax.LifeTaxEntity;
import org.rta.core.entity.payment.tax.PeriodicTaxEntity;
import org.rta.core.entity.payment.tax.VehicleCurrentTaxEntity;
import org.rta.core.entity.permit.PermitAuthCardDetailsEntity;
import org.rta.core.entity.permit.PermitHeaderEntity;
import org.rta.core.entity.vehicle.VehicleAlterationEntity;
import org.rta.core.entity.vehicle.VehicleBillingDetailsEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.entity.vehicle.cms.VehicleClassDescriptionEntity;
import org.rta.core.enums.AlterationCategory;
import org.rta.core.enums.DocTypes;
import org.rta.core.enums.MonthType;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.PaymentGatewayType;
import org.rta.core.enums.PaymentType;
import org.rta.core.enums.RegistrationCategoryType;
import org.rta.core.enums.Status;
import org.rta.core.enums.master.TaxType;
import org.rta.core.helper.payment.registfee.FitnessFeeHelper;
import org.rta.core.helper.payment.registfee.RegFeeDetailHelper;
import org.rta.core.helper.payment.tax.LifeTaxHelper;
import org.rta.core.helper.payment.tax.PeriodicTaxHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.certificate.FcDetailsModel;
import org.rta.core.model.insurance.InsuranceDetailsModel;
import org.rta.core.model.master.TaxTypeModel;
import org.rta.core.model.payment.registfee.FeeRuleModel;
import org.rta.core.model.payment.registfee.FitnessFeeModel;
import org.rta.core.model.payment.registfee.RegFeeDetailModel;
import org.rta.core.model.payment.tax.ApplicationTaxModel;
import org.rta.core.model.payment.tax.CessFeeModel;
import org.rta.core.model.payment.tax.DifferentialTaxFeeModel;
import org.rta.core.model.payment.tax.TaxDetailModel;
import org.rta.core.model.payment.tax.TaxModel;
import org.rta.core.model.payment.tax.TaxRuleModel;
import org.rta.core.model.permit.PermitHeaderModel;
import org.rta.core.service.insurance.InsuranceDetailsService;
import org.rta.core.service.vehicle.VehicleService;
import org.rta.core.service.vehicle.cms.VehicleMappingService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.NumberParser;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TaxDetailServiceImpl implements TaxDetailService {


   private static final Logger log = Logger.getLogger(TaxDetailServiceImpl.class);

   	@Value("${rta.lifetax.validty}")
   	int lifetaxValidty;
   	@Value("${rta.quartelytax.validty}")
   	int quartelyTaxValidty;
   	@Value("${rta.isuzo}")
    String isuzoVehicle;
    @Value("${rta.isuzo.startdate}")
    String isuzoVehicleStartDate;
    @Value("${rta.isuzo.enddate}")
    String isuzoVehicleEndDate;
    @Value("${rta.step.taxpay}")
    Integer currentStep;
    @Autowired
    private VehicleDetailsDAO vehicleDetailsDAO;
    @Autowired
    private VehicleBillingDetailsDAO vehicleBillingDetailsDAO;
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private CustomerCorporateDAO cusCorpDAO;
    @Autowired
    private AttachmentDetailsDAO attachmentDetailsDAO;
    @Autowired
    private TransactionDetailDAO transactionDetailDAO;
    @Autowired
    private SecondVehicleRejectionDAO secondVehicleRejectionDAO;
    @Autowired
    private VehicleDAO vehicleDAO;
    @Autowired
    private CustomerCorporateDAO custCorpDAO;
    @Autowired
    private PucDetailsDAO pucDetailsDAO;
    @Autowired
    private FcDetailsDAO fcDetailsDAO;
    @Autowired
    private PermitHeaderDAO permitHeaderDAO;
    @Autowired
    private InsuranceDetailsService insuranceDetailsService;
    @Autowired
    private PermitAuthCardDAO permitAuthCardDAO;
    @Autowired
    private RegFeeDetailDAO regFeeDetailDAO;

    @Autowired
    private RegFeeDetailHelper regFeeDetailHelper;
    @Autowired
    private PeriodicTaxDAO periodicTaxDAO;
    @Autowired
    private PeriodicTaxHelper periodicTaxHelper;
    @Autowired
    private LifeTaxDAO lifeTaxDAO;
    @Autowired
    private LifeTaxHelper lifeTaxHelper;
    @Autowired
    private VehicleCurrentTaxDAO vehicleCurrentTaxDAO;
    @Autowired
    private VehicleAlterationDAO vehicleAlterationDAO;
    @Autowired
    private CessFeeDetailDAO cessFeeDetailDAO;
    @Autowired
    private TaxTypeDAO taxTypeDAO;
    @Autowired
    private FitnessFeeDAO fitnessFeeDAO;
    @Autowired
    private FitnessFeeHelper fitnessFeeHelper;
    @Autowired
    private GreenTaxDetailsDAO greenTaxDetailsDAO;
    @Autowired
    private VehicleMappingService vehicleMappingService;
    
    @Autowired
    private VehicleClassDescDAO vehicleClassDescDAO;

    @Autowired
    private VehicleService vehicleService;
    
    @Override
    @Transactional
    public TaxRuleModel getTaxRuleModel(long vehicleRcId, String userName, TaxRuleModel taxRuleModel ,VehicleDetailsEntity vehicleDetailEntity, VehicleBillingDetailsEntity vehicleBillingDetailsEntity) {
        taxRuleModel = getTaxRuleDetails(vehicleDetailEntity, vehicleBillingDetailsEntity, taxRuleModel);
        return taxRuleModel;

    }

    public TaxRuleModel getTaxRuleDetails(VehicleDetailsEntity vehicleDetailEntity,
            VehicleBillingDetailsEntity vehicleBillingDetailsEntity, TaxRuleModel taxRuleModel) {
             taxRuleModel.setRegCategory(vehicleDetailEntity.getVehicleRcId().getRegCategory().getRegistrationCategoryId());
        taxRuleModel.setStateCode("AP");
        taxRuleModel.setFuelType(vehicleDetailEntity.getFuelUsed());
        taxRuleModel.setVehicleClass(vehicleDetailEntity.getVehicleClass());
        taxRuleModel.setVehicleClassCategory(vehicleDetailEntity.getVehicleSubClass());
        taxRuleModel.setMonthType(calculateQuarterlyTax());
        taxRuleModel.setUlw(vehicleDetailEntity.getUlw());
        taxRuleModel.setGvw(vehicleDetailEntity.getRlw());
        taxRuleModel.setSeatingCapacity(vehicleDetailEntity.getSeatingCapacity());
        taxRuleModel.setInvoiceAmount(vehicleBillingDetailsEntity.getInvoiceValue());
        taxRuleModel.setTaxType(
                TaxType.getTaxType(vehicleDetailEntity.getTaxTypeId().getTaxTypeId().intValue()).getCode());
        taxRuleModel.setOwnerType(
                OwnershipType.getOwnershipType(vehicleDetailEntity.getVehicleRcId().getOwnershipType()).getCode());
        switch (RegistrationCategoryType.getRegistrationCategoryType(taxRuleModel.getRegCategory())) {
        case NON_TRANSPORT:
                switch (OwnershipType.getOwnershipType(vehicleDetailEntity.getVehicleRcId().getOwnershipType())) {
                case INDIVIDUAL:
                    CustIndividualDetailsEntity custIndividualDetailsEntity = customerDAO
                            .getByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId());
                    if (custIndividualDetailsEntity.getIsSecondVehicle())
                        taxRuleModel.setNoOfVehicle(2);
                    if (custIndividualDetailsEntity.getIsDisabled()
                            && custIndividualDetailsEntity.getIsInvalidCarriage()) {
                        taxRuleModel.setIsDisabled(custIndividualDetailsEntity.getIsDisabled());
                        taxRuleModel.setIsInvalidCarriage(custIndividualDetailsEntity.getIsInvalidCarriage());
                        boolean isPHCertificate = attachmentDetailsDAO.getIsAttatchDltIdByDocId(
                                vehicleDetailEntity.getVehicleRcId().getVehicleRcId(), DocTypes.DOC_DL.getValue());
                        boolean isPHDriverLicense = attachmentDetailsDAO.getIsAttatchDltIdByDocId(
                                vehicleDetailEntity.getVehicleRcId().getVehicleRcId(),
                                DocTypes.DOC_DIFFERENTLY_ABLED_CERTIFICATE.getValue());
                        log.debug(":::isPHCertificate:: " + isPHCertificate + " :::::isPHDriverLicense::: "
                                + isPHDriverLicense);
                        taxRuleModel.setIsPHCertificate(isPHCertificate);
                        taxRuleModel.setIsPHDriverLicense(isPHDriverLicense);
                    }
                    break;
                }
            break;
        }
        
        if(taxRuleModel.getRegCategory() == RegistrationCategoryType.TRANSPORT.getValue() && taxRuleModel.getTaxType().equalsIgnoreCase(TaxType.FIRST_QUARTERLY.getCode())){
        	taxRuleModel.setIsCessFeeValid(true);
        }
//To Do Sandeep (logic put on role files)
        if (ifVehicleISUZO(vehicleDetailEntity)) {
            taxRuleModel.setISuzo(true);
        }
        return taxRuleModel;
    }
//To DO Sandeep (logic put on role files)
    public boolean ifVehicleISUZO(VehicleDetailsEntity vehicleDetailEntity) {
        log.debug("::::ifVehicleISUZO::::::start:::");
        boolean ifISUZO = false;
        String month = StringsUtil.getStartingChars(vehicleDetailEntity.getMfgDate(),
                2/* first 2 character are considered as month */);
        String year = StringsUtil.getLastChars(vehicleDetailEntity.getMfgDate(),
                4/* last 4 characters are considered as year */);
        Date mfgDate = DateUtil.convertMMYYYYtoDate(month + "/" + year);
        Date startDate = DateUtil.convertMMYYYYtoDate(isuzoVehicleStartDate);
        Date endDate = DateUtil.convertMMYYYYtoDate(isuzoVehicleEndDate);
        log.debug("mfgDate " + mfgDate + " startDate " + startDate + " endDate " + endDate + " MakerName "
                + vehicleDetailEntity.getMakerClass());
        boolean isValidDate = DateUtil.compareInBetween(startDate, endDate, mfgDate);
        if (isuzoVehicle.contains(vehicleDetailEntity.getMakerClass()) && isValidDate)
            ifISUZO = true;
        log.debug("::::ifVehicleISUZO::::::end::: " + ifISUZO);
        return ifISUZO;
    }

    @Override
    @Transactional
    public TaxDetailModel saveTaxDetails(long vehicleRcId, String userName, TaxRuleModel taxRuleModel, FeeRuleModel feeRuleModel , VehicleDetailsEntity vehicleDetailEntity , VehicleBillingDetailsEntity vehiclbBillingDetailsEntity) {
    	log.debug("::saveTaxDetails::::::: " + vehicleRcId);
    	TaxDetailModel taxDetailModel = null;
    	switch(TaxType.getTaxTypeByCode(taxRuleModel.getTaxType())){

		case FIRST_QUARTERLY:
			taxDetailModel = savePeriodicTax(vehicleRcId, userName, taxRuleModel, feeRuleModel, vehicleDetailEntity);
			break;
		case LIFE_TAX:
			taxDetailModel = saveLifeTax(vehicleRcId, userName, taxRuleModel, feeRuleModel, vehicleDetailEntity);
			break;
		}
		saveVehicleCurrentTax(vehicleRcId, userName, taxRuleModel, feeRuleModel, vehicleDetailEntity);
		String custName = getCustomerInfoByVehicleRcId(vehicleDetailEntity);
		taxDetailModel = convertTaxModelWithBillingEntity(taxDetailModel, vehiclbBillingDetailsEntity);
		taxDetailModel.setCustName(custName);
		return taxDetailModel;
	}

	public TaxDetailModel savePeriodicTax(long vehicleRcId, String userName, TaxRuleModel taxRuleModel,
			FeeRuleModel feeRuleModel, VehicleDetailsEntity vehicleDetailsEntity) {
		TaxDetailModel taxDetailModel = new TaxDetailModel();
		PeriodicTaxEntity periodicTaxEntity = null;
		VehicleRCEntity vehicleRCEntity = null;
		periodicTaxEntity = periodicTaxDAO.getEntityByVehicleRcId(vehicleRcId);
		if (periodicTaxEntity == null) {
			periodicTaxEntity = new PeriodicTaxEntity();
			vehicleRCEntity = vehicleDetailsEntity.getVehicleRcId();
			vehicleRCEntity.setProcessStatus(currentStep);
			periodicTaxEntity.setVehicleRc(vehicleRCEntity);
			periodicTaxEntity.setCreatedBy(userName);
			periodicTaxEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			periodicTaxEntity.setRegType(taxRuleModel.getRegCategory());
			periodicTaxEntity.setRtaOfficeCode(vehicleDetailsEntity.getVehicleRcId().getRtaOfficeId().getCode());
			periodicTaxEntity.setStatus(Status.OPEN.getValue());
			periodicTaxEntity.setTaxAmt(taxRuleModel.getTaxAmount());
			periodicTaxEntity.setTaxTypeId(vehicleDetailsEntity.getTaxTypeId());
			periodicTaxEntity.setTotalAmt(taxRuleModel.getTotalAmt());
			periodicTaxEntity.setVehicleCov(vehicleDetailsEntity.getVehicleSubClass());
			periodicTaxEntity.setTaxValidUpto(taxValidty());
		} else {
			periodicTaxEntity.setModifiedBy(userName);
			periodicTaxEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			periodicTaxEntity.setTaxAmt(taxRuleModel.getTaxAmount());
			periodicTaxEntity.setTotalAmt(taxRuleModel.getTotalAmt());
			periodicTaxEntity.setTaxValidUpto(taxValidty());
		}
		periodicTaxDAO.saveOrUpdate(periodicTaxEntity);
		taxDetailModel = periodicTaxHelper.convertToModel(periodicTaxEntity);
		return taxDetailModel;
	}

	public TaxDetailModel saveLifeTax(long vehicleRcId, String userName, TaxRuleModel taxRuleModel,
			FeeRuleModel feeRuleModel, VehicleDetailsEntity vehicleDetailsEntity) {

		TaxDetailModel taxDetailModel = new TaxDetailModel();
		LifeTaxEntity lifeTaxEntity = null;
		VehicleRCEntity vehicleRCEntity = null;
		lifeTaxEntity = lifeTaxDAO.getEntityByVehicleRcId(vehicleRcId);
		if (lifeTaxEntity == null) {
			lifeTaxEntity = new LifeTaxEntity();
			vehicleRCEntity = vehicleDetailsEntity.getVehicleRcId();
			vehicleRCEntity.setProcessStatus(currentStep);
			lifeTaxEntity.setVehicleRc(vehicleRCEntity);
			lifeTaxEntity.setCreatedBy(userName);
			lifeTaxEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			lifeTaxEntity.setRegType(taxRuleModel.getRegCategory());
			lifeTaxEntity.setRtaOfficeCode(vehicleDetailsEntity.getVehicleRcId().getRtaOfficeId().getCode());
			lifeTaxEntity.setStatus(Status.OPEN.getValue());
			lifeTaxEntity.setTaxAmt(taxRuleModel.getTaxAmount());
			lifeTaxEntity.setTotalTax(taxRuleModel.getTotalAmt());
			lifeTaxEntity.setVehicleCov(vehicleDetailsEntity.getVehicleSubClass());
			lifeTaxEntity.setTaxPercent(taxRuleModel.getTax());
			lifeTaxEntity.setTaxValidUpto(DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), lifetaxValidty));
		} else {
			lifeTaxEntity.setModifiedBy(userName);
			lifeTaxEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			lifeTaxEntity.setTaxAmt(taxRuleModel.getTaxAmount());
			lifeTaxEntity.setTotalTax(taxRuleModel.getTotalAmt());
			lifeTaxEntity.setTaxPercent(taxRuleModel.getTax());
			lifeTaxEntity.setTaxValidUpto(DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), lifetaxValidty));
		}
		lifeTaxDAO.saveOrUpdate(lifeTaxEntity);
		taxDetailModel = lifeTaxHelper.convertToModel(lifeTaxEntity);
		return taxDetailModel;
	}

	private TaxDetailModel convertTaxModelWithBillingEntity(TaxDetailModel taxDetailModel,
			VehicleBillingDetailsEntity vehicleBillingDetailsEntity) {
		if(vehicleBillingDetailsEntity != null){
			taxDetailModel.setInvoiceAmt(NumberParser.numberFormat(vehicleBillingDetailsEntity.getInvoiceValue()));
			taxDetailModel.setInvoiceDate(vehicleBillingDetailsEntity.getInvoiceDate());
			taxDetailModel.setInvoiceNo(vehicleBillingDetailsEntity.getInvoiceNumber());
		}	
		return taxDetailModel;
	}

	private String getCustomerInfoByVehicleRcId(VehicleDetailsEntity vehicldDetailsEntity) {
		String custName = "";
		if (vehicldDetailsEntity.getVehicleRcId().getOwnershipType() == OwnershipType.INDIVIDUAL.getId()) {
			CustIndividualDetailsEntity custIndividualDetailsEntity = customerDAO
					.getByVehicleRcId(vehicldDetailsEntity.getVehicleRcId().getVehicleRcId());
			custName = custIndividualDetailsEntity.getFirstName() + " " + custIndividualDetailsEntity.getSurName();
		} else if (vehicldDetailsEntity.getVehicleRcId().getOwnershipType() == OwnershipType.COMPANY.getId()
				|| vehicldDetailsEntity.getVehicleRcId().getOwnershipType() == OwnershipType.POLICE.getId()
				|| vehicldDetailsEntity.getVehicleRcId().getOwnershipType() == OwnershipType.STU_VEHICLES.getId()
				|| vehicldDetailsEntity.getVehicleRcId().getOwnershipType() == OwnershipType.ORGANIZATION.getId()) {
			CustCorporateDetailsEntity custCorpEntity = cusCorpDAO
					.getByVehicleRcId(vehicldDetailsEntity.getVehicleRcId().getVehicleRcId());
			custName = custCorpEntity.getDisplayName();
		}
		return custName;
	}

	public void saveVehicleCurrentTax(long vehicleRcId, String userName, TaxRuleModel taxRuleModel,
			FeeRuleModel feeRuleModel, VehicleDetailsEntity vehicleDetailsEntity) {
		VehicleCurrentTaxEntity vehicleCurrentTaxEntity = null;
		vehicleCurrentTaxEntity = vehicleCurrentTaxDAO.getEntityByVehicleRcId(vehicleRcId);
		if (vehicleCurrentTaxEntity == null) {
			vehicleCurrentTaxEntity = new VehicleCurrentTaxEntity();
			vehicleCurrentTaxEntity.setCreatedBy(userName);
			vehicleCurrentTaxEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			vehicleCurrentTaxEntity.setStatus(Status.OPEN.getValue());
			vehicleCurrentTaxEntity.setTaxTypeId(vehicleDetailsEntity.getTaxTypeId().getTaxTypeId());
			vehicleCurrentTaxEntity.setVehicleCov(vehicleDetailsEntity.getVehicleSubClass());
			vehicleCurrentTaxEntity.setVehicleRc(vehicleDetailsEntity.getVehicleRcId());
			vehicleCurrentTaxEntity.setTaxAmt(taxRuleModel.getTaxAmount());
		} else {
			vehicleCurrentTaxEntity.setModifiedBy(userName);
			vehicleCurrentTaxEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			vehicleCurrentTaxEntity.setTaxTypeId(vehicleDetailsEntity.getTaxTypeId().getTaxTypeId());
			vehicleCurrentTaxEntity.setVehicleCov(vehicleDetailsEntity.getVehicleSubClass());
			vehicleCurrentTaxEntity.setTaxAmt(taxRuleModel.getTaxAmount());
		}
		vehicleCurrentTaxDAO.saveOrUpdate(vehicleCurrentTaxEntity);
	}

	@Override
	@Transactional
	public TaxDetailModel secondVehicleTaxCal(long vehicleRcId, String userName) {
		log.debug(":::secondVehicleTaxCal :::: " + vehicleRcId);
		if (secondVehicleRejectionDAO.getSecondVehicleDetailsByVehicleRcId(vehicleRcId) == null) {
			log.error(":::::secondVehicleTaxCal :::Invalid Process::::");
			throw new IllegalArgumentException("Invalid secondVehicleTaxCal Process along with this vehicleRcId !");
		}
		LifeTaxEntity lifeTaxEntity = null;
		VehicleDetailsEntity vehicleDetailEntity = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
		VehicleBillingDetailsEntity vehicleBillingDetailsEntity = vehicleBillingDetailsDAO
				.getByVehicleDetailId(vehicleDetailEntity);
		if (vehicleDetailEntity == null && vehicleBillingDetailsEntity == null)
			throw new IllegalArgumentException("Invalid vehicleRcId !");

		lifeTaxEntity = lifeTaxDAO.getEntityByVehicleRcId(vehicleRcId);
		if (lifeTaxEntity == null) {
			throw new IllegalArgumentException("Invalid vehicleRcId !");
		}

		lifeTaxEntity.setModifiedBy(userName);
		lifeTaxEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		lifeTaxEntity = getSecondVehicleTax(vehicleDetailEntity, vehicleBillingDetailsEntity, lifeTaxEntity);
		lifeTaxDAO.saveOrUpdate(lifeTaxEntity);
		TaxDetailModel taxDetailModel = lifeTaxHelper.convertToModel(lifeTaxEntity);
		return taxDetailModel;
	}

	private LifeTaxEntity getSecondVehicleTax(VehicleDetailsEntity vehicleDetailEntity,
			VehicleBillingDetailsEntity vehicleBillingDetailsEntity, LifeTaxEntity lifeTaxEntity) {
		double payTaxPercentage = lifeTaxEntity.getTaxPercent();
		double secondVehicleTax = 14 - payTaxPercentage;
		double secondVehicleTaxAmt = NumberParser
				.getRoundNextTen(vehicleBillingDetailsEntity.getInvoiceValue() * secondVehicleTax / 100);
		lifeTaxEntity.setSecondVehicleTaxPercent(secondVehicleTax);
		lifeTaxEntity.setSecondVehicleTaxAmt(secondVehicleTaxAmt);
		lifeTaxEntity.setSecondVehicleTaxOn(DateUtil.toCurrentUTCTimeStamp());
		return lifeTaxEntity;
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

	@Override
	@Transactional
	public TaxDetailModel isPaymentVerification(Long vehicleRcId, TaxDetailModel taxDetailModel) {
		log.debug(":::::::::::isSBIVerification::::::start::::::");
		List<TransactionDetailEntity> transactionDetailEntityList = transactionDetailDAO
				.getAllByVehicleRcNdPayType(vehicleRcId, PaymentType.PAYTAX.getId());
		if (transactionDetailEntityList != null && transactionDetailEntityList.size() == 0)
			taxDetailModel.setStatus(0);
		for (TransactionDetailEntity tde : transactionDetailEntityList) {
			if (tde.getStatus() == Status.OPEN.getValue() || tde.getStatus() == Status.PENDING.getValue()) {
				taxDetailModel.setStatus(1);
				if (tde.getPgType()!=null && tde.getPgType().equalsIgnoreCase(PaymentGatewayType.PAYU.getLabel())) {
					taxDetailModel.setIsPayUVerification(true);
				} else {
					taxDetailModel.setIsSBIVerification(true);
				}
			}
		}
		return taxDetailModel;
	}

	@Override
	@Transactional
	public ApplicationTaxModel getAllTaxDetails(String trOrPrNumber) {
		log.info("::getAllTaxDetails ::::start::");
		boolean isSeat = false, isWieght = false;
		VehicleRCEntity vehicleRCEntity = vehicleDAO.getByPROrTrNumber(trOrPrNumber);
		VehicleCurrentTaxEntity vehiclcCurrentTaxEntity = vehicleCurrentTaxDAO
				.getEntityByVehicleRcId(vehicleRCEntity.getVehicleRcId());
		VehicleDetailsEntity vehicleDetailEntity = vehicleDetailsDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
		VehicleBillingDetailsEntity vehicleBillingDetailsEntity = vehicleBillingDetailsDAO
				.getByVehicleDetailId(vehicleDetailEntity);
		List<VehicleAlterationEntity> vehicleAlterationEntities = vehicleAlterationDAO
				.getVehicleAlterationDetails(vehicleRCEntity.getVehicleRcId(), Status.DIFFERENTIAL_TAX);
		ApplicationTaxModel appTaxModel = new ApplicationTaxModel();
		appTaxModel.setOldVehicleSubClass(vehicleDetailEntity.getVehicleSubClass());
		appTaxModel.setVehicleSubClass(vehicleDetailEntity.getVehicleSubClass());
		if (vehicleDetailEntity.getTaxTypeId() != null) {
			appTaxModel.setTaxType(vehicleDetailEntity.getTaxTypeId().getTaxTypeId().intValue());
			appTaxModel.setOldTaxType(vehicleDetailEntity.getTaxTypeId().getTaxTypeId().intValue());
			appTaxModel.setTaxTypeName(vehicleDetailEntity.getTaxTypeId().getName());
		}
		
		if (!ObjectsUtil.isNull(vehicleAlterationEntities) && vehicleAlterationEntities.size() > 0) {
			for (VehicleAlterationEntity vehicleAlterationEntity : vehicleAlterationEntities) {
				if (vehicleAlterationEntity.getAlterationCategory() == AlterationCategory.SEATING_CAPACITY.getValue()
						|| vehicleAlterationEntity.getAlterationCategory() == AlterationCategory.NEW_VEHICLE_ALTERATION.getValue() ) {
					appTaxModel.setSeatingCapacity(vehicleAlterationEntity.getSeatingCapacity());
					appTaxModel.setUlw(vehicleAlterationEntity.getUlwUpdated());
					isSeat = true;
				}
				if (vehicleAlterationEntity.getAlterationCategory() == AlterationCategory.BODY_TYPE.getValue()) {
					appTaxModel.setUlw(vehicleAlterationEntity.getUlwUpdated());
					isWieght = true;
				}
				if(vehicleAlterationEntity.getVehicleSubClass()!=null){
					appTaxModel.setVehicleSubClass(vehicleAlterationEntity.getVehicleSubClass());
					TaxTypeModel taxTypeModel = vehicleMappingService.getTaxType4VehicleSubClass(appTaxModel.getVehicleSubClass(), appTaxModel.getSeatingCapacity());
					if (!ObjectsUtil.isNull(taxTypeModel)) {
						appTaxModel.setTaxType(taxTypeModel.getId().intValue());
						appTaxModel.setTaxTypeName(taxTypeModel.getName());
					}
				}
			}
			if (appTaxModel.getVehicleSubClass() == null) {
				appTaxModel.setVehicleSubClass(vehicleDetailEntity.getVehicleSubClass());
				if (vehicleDetailEntity.getTaxTypeId() != null) {
					appTaxModel.setTaxType(vehicleDetailEntity.getTaxTypeId().getTaxTypeId().intValue());
					appTaxModel.setTaxTypeName(vehicleDetailEntity.getTaxTypeId().getName());
				}
			}
		} else {
			appTaxModel.setVehicleSubClass(vehicleDetailEntity.getVehicleSubClass());
		}
		if (!isSeat) {
			appTaxModel.setSeatingCapacity(vehicleDetailEntity.getSeatingCapacity());
		}
		if (!isWieght && !isSeat) {
			appTaxModel.setUlw(vehicleDetailEntity.getUlw());
		}
		appTaxModel.setGvw(vehicleDetailEntity.getRlw());
		if(!vehicleRCEntity.isMigrated()){
			appTaxModel.setInvoiceAmt(vehicleBillingDetailsEntity.getInvoiceValue());
			appTaxModel.setInvoiceDate(vehicleBillingDetailsEntity.getInvoiceDate());
		}
		appTaxModel.setRegType(vehicleRCEntity.getRegCategory().getRegistrationCategoryId());
		appTaxModel.setVehicleRcId(vehicleRCEntity.getVehicleRcId());
		appTaxModel.setVehicleClass(vehicleDetailEntity.getVehicleClass());
		if(ObjectsUtil.isNotNull(vehicleDetailEntity.getVehicleSubClass())){
			try{
				VehicleClassDescriptionEntity vcde = vehicleClassDescDAO.getByVehiclClassDesCode(vehicleDetailEntity.getVehicleSubClass());
				if(ObjectsUtil.isNotNull(vcde)){
					appTaxModel.setVehicleSubClassDesc(vcde.getDescription());
				} else {
					log.error("VehicleClassDescriptionEntity  is null for cov : " + vehicleDetailEntity.getVehicleSubClass());
				}
			} catch(Exception ex){
				log.error("Exception in getting cov description " + ex.getMessage());
			}
		}
		if (vehicleRCEntity.getPrIssueTime() != null)
			appTaxModel.setPrIssueTime(vehicleRCEntity.getPrIssueTime());
		if (vehicleRCEntity.getPrExpireDate() != null)
			appTaxModel.setPrExpiryTime(vehicleRCEntity.getPrExpireDate());
		if(vehicleRCEntity.getTrIssueTime() != null)
		appTaxModel.setTrIssueTime(vehicleRCEntity.getTrIssueTime());
		if (!ObjectsUtil.isNull(vehiclcCurrentTaxEntity)) {
			appTaxModel.setTaxValidUpto(vehiclcCurrentTaxEntity.getTaxValidUpto());
			appTaxModel.setTaxAmt(vehiclcCurrentTaxEntity.getTaxAmt());
			log.info("::vehiclcCurrentTaxEntity.getTaxAmt()::::::::" + vehiclcCurrentTaxEntity.getTaxAmt());
		}
		appTaxModel.setChassisNo(vehicleDetailEntity.getChassisNo());
		appTaxModel.setColour(vehicleDetailEntity.getColor());
		appTaxModel.setEngineNo(vehicleDetailEntity.getEngineNo());
		appTaxModel.setMakerName(vehicleDetailEntity.getMakerName());
		CessFeeDetailsEntity cessFeeDetailsEntity = cessFeeDetailDAO
					.getByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId());
		if((cessFeeDetailsEntity != null) && (cessFeeDetailsEntity.getCessFeeValidUpto() != null)){
		appTaxModel.setCessFeeValidUpTo(cessFeeDetailsEntity.getCessFeeValidUpto());	
		}
		
		CustCorporateDetailsEntity custCorpEntity = null;
		CustIndividualDetailsEntity custIndDetailsEntity = null;

		switch (OwnershipType.getOwnershipType(vehicleRCEntity.getOwnershipType())) {
		case POLICE:
		case COMPANY:
		case STU_VEHICLES:
		case ORGANIZATION:
			custCorpEntity = custCorpDAO.getByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId());
			appTaxModel.setCitizenName(custCorpEntity.getCompanyName());
			appTaxModel.setEmailId(custCorpEntity.getEmail());
			appTaxModel.setContactNo(custCorpEntity.getMobile());
			break;
		case INDIVIDUAL:
			custIndDetailsEntity = customerDAO.getByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId());
			appTaxModel.setCitizenName(custIndDetailsEntity.getFirstName() + " " + custIndDetailsEntity.getSurName());
			appTaxModel.setEmailId(custIndDetailsEntity.getEmailId());
			appTaxModel.setContactNo(custIndDetailsEntity.getMobileNo());
			break;
		}
		// if not having puc after 6 months, then puc is required
		if (ObjectsUtil.isNull(vehicleRCEntity.getPrIssueTime())) {
			appTaxModel.setPucRequired(false);
		} else {
			if (DateUtil.addMonths(vehicleRCEntity.getPrIssueTime(), 6) < DateUtil.toCurrentUTCTimeStamp()) {
				appTaxModel.setPucRequired(true);
			} else {
				appTaxModel.setPucRequired(false);
			}
		}
		PucDetailsEntity pucDetailsEntity = pucDetailsDAO.getPucDetails(vehicleRCEntity.getVehicleRcId());
		if (pucDetailsEntity != null) {
			appTaxModel.setPucExpireDate(pucDetailsEntity.getExpiryDate());
		}
		// InsuranceDetailsEntity insuranceDetailsEntity =
		// insuranceDetailsDAO.getInsuranceDtlsEntityByVehicleRcId(vehicleRCEntity.getVehicleRcId());
		
		if(!vehicleRCEntity.isMigrated()){
			InsuranceDetailsModel insuranceDetails = null;
			try {
				insuranceDetails = insuranceDetailsService
						.getIibInsuranceDetailsByVehicleRcId(vehicleRCEntity.getVehicleRcId());
			} catch (Exception ex) {
				log.error("Exception while getting insurance details ...");
			}
			if (ObjectsUtil.isNotNull(insuranceDetails)) {
				appTaxModel.setInsuranceEndDate(insuranceDetails.getEndDate());
			}
		}
		FitnessCertificateEntity fde = fcDetailsDAO.getFcDetails(vehicleRCEntity.getVehicleRcId(), "V");
		if (ObjectsUtil.isNotNull(fde)) {
			FcDetailsModel fdm = new FcDetailsModel();
			fdm.setExpiryDate(ObjectsUtil.isNotNull(fde.getExpiryDate()) ? fde.getExpiryDate().getTime() / 1000 : null);
			fdm.setFcNumber(fde.getFcNo());
			fdm.setIssueDate(ObjectsUtil.isNotNull(fde.getIssueDate()) ? fde.getIssueDate().getTime() / 1000 : null);
			appTaxModel.setFitnessDetailsModel(fdm);
		}

		PermitHeaderEntity permitHeaderEntity = permitHeaderDAO.getPrimaryPermitActiveByPr(trOrPrNumber);
		if (ObjectsUtil.isNotNull(permitHeaderEntity)) {
			PermitHeaderModel phm = new PermitHeaderModel();
			phm.setPermitType(permitHeaderEntity.getPermitType());
			phm.setValidToDate(ObjectsUtil.isNotNull(permitHeaderEntity.getValidToDate())
					? permitHeaderEntity.getValidToDate().getTime() / 1000 : null);
			appTaxModel.setPermitHeaderModel(phm);
		}
		appTaxModel.setFuelType(vehicleDetailEntity.getFuelUsed());
		appTaxModel.setOwnerType(
				OwnershipType.getOwnershipType(vehicleDetailEntity.getVehicleRcId().getOwnershipType()).getCode());

		GreenTaxDetailsEntity greenTaxDetailsEntity = null;
		greenTaxDetailsEntity = greenTaxDetailsDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
		if (greenTaxDetailsEntity != null && greenTaxDetailsEntity.getGreenTaxValidTo() != null)
		appTaxModel.setGreenTaxValidUpTo(greenTaxDetailsEntity.getGreenTaxValidTo());
		 
		try {
			PermitAuthCardDetailsEntity pacde = permitAuthCardDAO.getActiveAuthCard(permitHeaderEntity.getPermitNo());
			appTaxModel.setPermitAuthValidTo(
					ObjectsUtil.isNotNull(pacde.getAuthCardVtdt()) ? pacde.getAuthCardVtdt().getTime() / 1000 : null);
		} catch (Exception e) {
			log.error("No authorization card found ........");
		}
		RegFeeDetailsEntity regFeeDetailEntity = regFeeDetailDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
		if (ObjectsUtil.isNotNull(regFeeDetailEntity)) {
			log.info("Registration Fee Detail " + regFeeDetailEntity.getTotalFee());
			RegFeeDetailModel regFeeDetailModel = regFeeDetailHelper.convertToModel(regFeeDetailEntity);
			FitnessFeeDetailsEntity fitnessFeeDetailsEntity = fitnessFeeDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
			if (ObjectsUtil.isNotNull(fitnessFeeDetailsEntity)) {
				try {
					FitnessFeeModel fitnessFeeModel = fitnessFeeHelper.convertToModel(fitnessFeeDetailsEntity);
					regFeeDetailModel.setFitnessFeeModel(fitnessFeeModel);
					appTaxModel.setRegFeeDetailModel(regFeeDetailModel);
				} catch (Exception e) {
					appTaxModel.setRegFeeDetailModel(regFeeDetailModel);
					e.printStackTrace();
				}
			}
			else{
				appTaxModel.setRegFeeDetailModel(regFeeDetailModel);
			}
		}
		
		PeriodicTaxEntity periodicTaxEntity = null;
		periodicTaxEntity = periodicTaxDAO.getEntityByVehicleRcId(vehicleRCEntity.getVehicleRcId());
		if(periodicTaxEntity != null)
			appTaxModel.setPeriodicTaxType(periodicTaxEntity.getTaxTypeId().getTaxTypeId().intValue());
		else
			appTaxModel.setPeriodicTaxType(0);
		
		log.info("::getAllTaxDetails ::::end:: "+ appTaxModel);
		return appTaxModel;
	}

	@Override
	@Transactional
	public SaveUpdateResponse syncPayTaxData(TaxModel taxModel) {
		log.debug("::syncPayTaxData::tax details update:: " + taxModel);
		log.debug("::syncPayTaxData::tax details update  compond fee:: " + taxModel.getCompoundFee());
		SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
		VehicleRCEntity vehicleRCEntity = null;
		try {
			vehicleRCEntity = vehicleDAO.getVehicleRc4Pr(taxModel.getPrNumber());
			if (vehicleRCEntity == null) {
				log.info(":::PayTaxSync::Invalid pr number::");
				saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
				saveUpdateResponse.setMessage("PayTaxSync::Invalid pr number");
				return saveUpdateResponse;
			}
			
			
			VehicleCurrentTaxEntity vehicleCurrentTaxEntity = null;
			vehicleCurrentTaxEntity = vehicleCurrentTaxDAO.getEntityByVehicleRcId(vehicleRCEntity.getVehicleRcId());			
			vehicleCurrentTaxEntity.setTaxValidUpto(taxModel.getTaxValidUpto());
			vehicleCurrentTaxEntity.setTaxAmt(Double.parseDouble(taxModel.getTaxAmt()));
			vehicleCurrentTaxEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			vehicleCurrentTaxDAO.saveOrUpdate(vehicleCurrentTaxEntity);			
			//sync cess fee
			if((taxModel.getCessFee() == null? 0 :  Double.parseDouble(taxModel.getCessFee()))>0 ){
				CessFeeDetailsEntity cessFeeDetailsEntity = cessFeeDetailDAO
						.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
				if (cessFeeDetailsEntity != null) {
					cessFeeDetailsEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
					cessFeeDetailsEntity.setCessFee(taxModel.getCessFee() == null ? 0 :  Double.parseDouble(taxModel.getCessFee()));
					cessFeeDetailsEntity.setModifiedBy("CITIZEN1");
					cessFeeDetailsEntity.setCessFeeValidUpto(taxModel.getCessFeeValidUpto());
				} else {
					cessFeeDetailsEntity = new CessFeeDetailsEntity();
					cessFeeDetailsEntity.setCreatedBy("CITIZEN1");
					cessFeeDetailsEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
					cessFeeDetailsEntity.setCessFee(taxModel.getCessFee() == null ? 0 :  Double.parseDouble(taxModel.getCessFee()));
					cessFeeDetailsEntity.setCessFeeValidUpto(DateUtil.getCessFeeValidUpto());
					cessFeeDetailsEntity.setVehicleRc(vehicleRCEntity);				
					}
				    cessFeeDetailDAO.saveOrUpdate(cessFeeDetailsEntity);
		  }
			
			try {
				PeriodicTaxEntity periodicTaxEntity = new PeriodicTaxEntity();
				periodicTaxEntity.setCreatedBy("CITIZEN1");
				periodicTaxEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
				periodicTaxEntity.setExemptionFlag(false);
				periodicTaxEntity.setPenalty(taxModel.getPenalty() == null ? 0 : Double.parseDouble(taxModel.getPenalty()));
				periodicTaxEntity.setPenaltyArrears(taxModel.getPenaltyArrears() == null ? 0 : Double.parseDouble(taxModel.getPenaltyArrears()));
				periodicTaxEntity.setServiceCharge(taxModel.getServiceFee() == null ? 0 : Double.parseDouble(taxModel.getServiceFee()));
				periodicTaxEntity.setStatus(Status.CLOSED.getValue());
				periodicTaxEntity.setTaxAmt(taxModel.getTaxAmt() == null ? 0 : Double.parseDouble(taxModel.getTaxAmt()));
				periodicTaxEntity.setTaxArrears(taxModel.getTaxArrears() == null ? 0 : Double.parseDouble(taxModel.getTaxArrears()));
				periodicTaxEntity.setTaxTypeId(taxTypeDAO.getByCode(taxModel.getTaxType()));
				periodicTaxEntity.setTaxValidFrom(DateUtil.toCurrentUTCTimeStamp());
				periodicTaxEntity.setTaxValidUpto(taxModel.getTaxValidUpto());
				double totalAmt = periodicTaxEntity.getTaxAmt() + periodicTaxEntity.getPenalty() + periodicTaxEntity.getTaxArrears() + periodicTaxEntity.getPenaltyArrears() + periodicTaxEntity.getServiceCharge();
				periodicTaxEntity.setTotalAmt(totalAmt);
				periodicTaxEntity.setVehicleRc(vehicleRCEntity);
				periodicTaxDAO.saveOrUpdate(periodicTaxEntity);
			} catch (Exception e) {
				log.error("::syncPayTaxData::tax details update: on periodic tax details");
				e.printStackTrace();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
			saveUpdateResponse.setMessage("PayTaxSync::Internal Server Error");
			return saveUpdateResponse;
		}
		saveUpdateResponse.setStatus(SaveUpdateResponse.SUCCESS);
		saveUpdateResponse.setMessage("Successfully Sync");
		
		vehicleService.updateRcApproverUserId(taxModel.getActionModelList(), vehicleRCEntity.getVehicleRcId());
		
		return saveUpdateResponse;
	}

	@Override
	@Transactional
	public TaxDetailModel viewTaxDetails(long vehicleRcId, String userName, VehicleDetailsEntity vehicleDetailEntity,
			VehicleBillingDetailsEntity vehiclbBillingDetailsEntity) {
		log.debug("::viewTaxDetails:::: " + vehicleRcId);
		TaxDetailModel taxDetailModel = null;
		switch (TaxType.getTaxTypeByCode(vehicleDetailEntity.getTaxTypeId().getCode())) {
		case FIRST_QUARTERLY:
			taxDetailModel = getPeriodicTax(vehicleRcId, userName, vehicleDetailEntity);
			break;
		case LIFE_TAX:
			taxDetailModel = getLifeTax(vehicleRcId, userName, vehicleDetailEntity);
			break;
		}
		String custName = getCustomerInfoByVehicleRcId(vehicleDetailEntity);
		taxDetailModel = convertTaxModelWithBillingEntity(taxDetailModel, vehiclbBillingDetailsEntity);
		taxDetailModel.setCustName(custName);
		return taxDetailModel;
	}

	public TaxDetailModel getPeriodicTax(long vehicleRcId, String userName, VehicleDetailsEntity vehicleDetailsEntity) {
		TaxDetailModel taxDetailModel = new TaxDetailModel();
		PeriodicTaxEntity periodicTaxEntity = null;
		periodicTaxEntity = periodicTaxDAO.getEntityByVehicleRcId(vehicleRcId);
		taxDetailModel = periodicTaxHelper.convertToModel(periodicTaxEntity);
		return taxDetailModel;
	}

	public TaxDetailModel getLifeTax(long vehicleRcId, String userName, VehicleDetailsEntity vehicleDetailsEntity) {
		TaxDetailModel taxDetailModel = new TaxDetailModel();
		LifeTaxEntity lifeTaxEntity = null;
		lifeTaxEntity = lifeTaxDAO.getEntityByVehicleRcId(vehicleRcId);
		taxDetailModel = lifeTaxHelper.convertToModel(lifeTaxEntity);
		return taxDetailModel;
	}
	
	@Override
	@Transactional
	public Boolean getLifeTax(long vehicleRcId) {
		boolean isLifeTax = false;
		LifeTaxEntity lifeTaxEntity = null;
		lifeTaxEntity = lifeTaxDAO.getEntityByVehicleRcId(vehicleRcId);
		if (lifeTaxEntity != null) {
			isLifeTax = true;
		}
		return isLifeTax;
	}

	public long taxValidty() {
		int monthType = calculateQuarterlyTax();
		long taxValid = DateUtil.toLastDayOfMonth(monthType - 1);
		return taxValid;
	}

	@Override
	@Transactional
	public CessFeeModel cessFeeDetails(VehicleDetailsEntity vehicleDetailEntity, String userName,
			TaxRuleModel taxRuleModel) {
		CessFeeDetailsEntity cessFeeDetailsEntity = cessFeeDetailDAO
				.getByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId());
		if (cessFeeDetailsEntity != null) {
			cessFeeDetailsEntity.setModifiedBy(userName);
			cessFeeDetailsEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			cessFeeDetailsEntity.setCessFee(taxRuleModel.getCessFee());
		} else {
			cessFeeDetailsEntity = new CessFeeDetailsEntity();
			cessFeeDetailsEntity.setCreatedBy(userName);
			cessFeeDetailsEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			cessFeeDetailsEntity.setCessFee(taxRuleModel.getCessFee());
			
			cessFeeDetailsEntity.setCessFeeValidUpto(DateUtil.getCessFeeValidUpto());
			cessFeeDetailsEntity.setVehicleRc(vehicleDetailEntity.getVehicleRcId());
		}
		cessFeeDetailDAO.saveOrUpdate(cessFeeDetailsEntity);
		CessFeeModel cessFeeModel = new CessFeeModel();
		cessFeeModel.setCessFee(cessFeeDetailsEntity.getCessFee());
		return cessFeeModel;
	}

	@Override
	public CessFeeModel viewCessFeeDetails(VehicleDetailsEntity vehicleDetailEntity) {
		CessFeeModel cessFeeModel = new CessFeeModel();
		CessFeeDetailsEntity cessFeeDetailsEntity = cessFeeDetailDAO
				.getByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId());
		if (cessFeeDetailsEntity != null) {
			cessFeeModel.setCessFee(cessFeeDetailsEntity.getCessFee());
		}
		return cessFeeModel;

	}


	@Override
	@Transactional
	public String saveOrUpdateDifferentialTaxFeeDetails(DifferentialTaxFeeModel differentialTaxFeeModel, Long vehicleRcId, String userName, String cov) {
		if(ObjectsUtil.isNull(differentialTaxFeeModel)){
			
			return "No need to sync data for Differential Tax and Fee";
		}
		try {
			if(differentialTaxFeeModel.getPrFee() > SomeConstants.ZERO || differentialTaxFeeModel.getTotalFee() > SomeConstants.ZERO){
				RegFeeDetailsEntity regFeeDetailsEntity = regFeeDetailDAO.getByVehicleRcId(vehicleRcId);
				if(ObjectsUtil.isNull(regFeeDetailsEntity)){
					regFeeDetailsEntity = new RegFeeDetailsEntity();
					regFeeDetailsEntity.setPrFee(differentialTaxFeeModel.getPrFee());
					regFeeDetailsEntity.setTotalFee(differentialTaxFeeModel.getTotalFee());
					regFeeDetailsEntity.setVehicleRc(vehicleDAO.get(vehicleRcId));
					regFeeDetailsEntity.setPostalCharge(differentialTaxFeeModel.getPostalFee());
					regFeeDetailsEntity.setSmartCardFee(differentialTaxFeeModel.getSmartCardFee());
					regFeeDetailsEntity.setCreatedBy(userName);
					regFeeDetailsEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
				}else{
					double prFee =  regFeeDetailsEntity.getPrFee() + differentialTaxFeeModel.getPrFee();
					double postalFee = regFeeDetailsEntity.getPostalCharge() + differentialTaxFeeModel.getPostalFee();
					double smartCardFee =  regFeeDetailsEntity.getSmartCardFee() + differentialTaxFeeModel.getSmartCardFee();
					double totalFee=  regFeeDetailsEntity.getTotalFee() + differentialTaxFeeModel.getTotalFee();
					regFeeDetailsEntity.setPrFee(prFee);
					regFeeDetailsEntity.setPostalCharge(postalFee);
					regFeeDetailsEntity.setSmartCardFee(smartCardFee);
					regFeeDetailsEntity.setTotalFee(totalFee);
				}
				regFeeDetailsEntity.setModifiedBy(userName);
				regFeeDetailsEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
				regFeeDetailDAO.saveOrUpdate(regFeeDetailsEntity);
			}
			if(differentialTaxFeeModel.getFitnessFee() > SomeConstants.ZERO){
				FitnessFeeDetailsEntity fitnessFeeDetailsEntity = fitnessFeeDAO.getByVehicleRcId(vehicleRcId);
				if(ObjectsUtil.isNull(fitnessFeeDetailsEntity)){
					fitnessFeeDetailsEntity = new FitnessFeeDetailsEntity();
					VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);
					fitnessFeeDetailsEntity.setFitnessFee(differentialTaxFeeModel.getFitnessFee());
					fitnessFeeDetailsEntity.setTotalFitnessFee(differentialTaxFeeModel.getFitnessFee());
					fitnessFeeDetailsEntity.setVehicleRc(vehicleRCEntity);
					fitnessFeeDetailsEntity.setRegType(vehicleRCEntity.getRegCategory().getRegistrationCategoryId());
					fitnessFeeDetailsEntity.setCreatedBy(userName);
					fitnessFeeDetailsEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
				}else{
					double fitnessFee =  fitnessFeeDetailsEntity.getFitnessFee() + differentialTaxFeeModel.getFitnessFee();
					double fitnessServiceFee =  fitnessFeeDetailsEntity.getFitnessService() + differentialTaxFeeModel.getFitnessServiceFee();
					double totalFitnessFee =  fitnessFeeDetailsEntity.getTotalFitnessFee() + differentialTaxFeeModel.getFitnessFee();
					fitnessFeeDetailsEntity.setFitnessFee(fitnessFee);
					fitnessFeeDetailsEntity.setFitnessService(fitnessServiceFee);
					fitnessFeeDetailsEntity.setTotalFitnessFee(totalFitnessFee);
				}
				fitnessFeeDetailsEntity.setModifiedBy(userName);
				fitnessFeeDetailsEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
				fitnessFeeDAO.saveOrUpdate(fitnessFeeDetailsEntity);
			}
			
			VehicleCurrentTaxEntity vehicleCurrentTaxEntity = vehicleCurrentTaxDAO.getEntityByVehicleRcId(vehicleRcId);
			if(ObjectsUtil.isNotNull(vehicleCurrentTaxEntity)){
				TaxType oldTaxType = TaxType.getTaxType(vehicleCurrentTaxEntity.getTaxTypeId().intValue());
				TaxType newTaxType = TaxType.getTaxTypeByCode(differentialTaxFeeModel.getTaxType());
				VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);
				if(oldTaxType == TaxType.LIFE_TAX && newTaxType == TaxType.FIRST_QUARTERLY
						|| oldTaxType == TaxType.FIRST_QUARTERLY && newTaxType == TaxType.FIRST_QUARTERLY){
					if(differentialTaxFeeModel.getTaxAmount() > 0){
						PeriodicTaxEntity periodicTaxEntity = new PeriodicTaxEntity();
						periodicTaxEntity.setVehicleRc(vehicleRCEntity);
						periodicTaxEntity.setTaxTypeId(taxTypeDAO.getByCode(differentialTaxFeeModel.getTaxType()));
						periodicTaxEntity.setRtaOfficeCode(vehicleRCEntity.getRtaOfficeId().getCode());
						periodicTaxEntity.setTaxAmt(differentialTaxFeeModel.getTaxAmount());
						periodicTaxEntity.setTotalAmt(differentialTaxFeeModel.getTaxAmount());
						periodicTaxEntity.setRegType(vehicleRCEntity.getRegCategory().getRegistrationCategoryId());
						periodicTaxEntity.setVehicleCov(cov);
						periodicTaxEntity.setVehicleRegNo(vehicleRCEntity.getPrNumber());
						periodicTaxEntity.setCreatedBy(userName);
						periodicTaxEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
						periodicTaxDAO.save(periodicTaxEntity);
					}
				} else if(oldTaxType == TaxType.FIRST_QUARTERLY && newTaxType == TaxType.LIFE_TAX){
					LifeTaxEntity lifeTaxEntity = lifeTaxDAO.getEntityByVehicleRcId(vehicleRcId);
					if(ObjectsUtil.isNotNull(lifeTaxEntity)){
						lifeTaxEntity.setVehicleCov(cov);
						lifeTaxEntity.setModifiedBy(userName);
						lifeTaxEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
						lifeTaxDAO.save(lifeTaxEntity);
					} else {
						lifeTaxEntity = new LifeTaxEntity();
						lifeTaxEntity.setVehicleRc(vehicleRCEntity);
						lifeTaxEntity.setRtaOfficeCode(vehicleRCEntity.getRtaOfficeId().getCode());
						lifeTaxEntity.setTaxAmt(differentialTaxFeeModel.getTaxAmount());
						lifeTaxEntity.setTotalTax(differentialTaxFeeModel.getTaxAmount());
						lifeTaxEntity.setVehicleCov(cov);
						lifeTaxEntity.setRegType(vehicleRCEntity.getRegCategory().getRegistrationCategoryId());
						lifeTaxEntity.setCreatedBy(userName);
						lifeTaxEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
						lifeTaxDAO.save(lifeTaxEntity);
					}
				}
				if(ObjectsUtil.isNotNull(oldTaxType) && ObjectsUtil.isNotNull(newTaxType)){
					vehicleCurrentTaxEntity.setTaxAmt(vehicleCurrentTaxEntity.getTaxAmt() + differentialTaxFeeModel.getTaxAmount());
					vehicleCurrentTaxEntity.setTaxTypeId(taxTypeDAO.getByCode(differentialTaxFeeModel.getTaxType()).getTaxTypeId());
					vehicleCurrentTaxEntity.setVehicleCov(cov);
					vehicleCurrentTaxEntity.setVehicleRegNo(vehicleRCEntity.getPrNumber());
					vehicleCurrentTaxEntity.setModifiedBy(userName);
					vehicleCurrentTaxEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
					vehicleCurrentTaxDAO.saveOrUpdate(vehicleCurrentTaxEntity);
				}
			}
		}catch (Exception e) {
			return SaveUpdateResponse.FAILURE;
		}
		return SaveUpdateResponse.SUCCESS;

	}
	
	@Override
	@Transactional
	public TaxModel getCurrentTaxDetails(Long vehicleRcId){
		VehicleCurrentTaxEntity currTaxEntity = vehicleCurrentTaxDAO.getEntityByVehicleRcId(vehicleRcId);
		TaxModel tax = new TaxModel();
		if(ObjectsUtil.isNotNull(currTaxEntity)){
			tax.setTaxValidUpto(currTaxEntity.getTaxValidUpto());
			try{
				tax.setTaxAmt(currTaxEntity.getTaxAmt() + "");
				//-- if tax type needed please read from vehicle details, not from VehicleCurrentTaxEntity ----
			}catch(Exception ex){
				log.error("Error while reading current tax details : " + vehicleRcId);
			}
		}
		return tax;
	}
}
