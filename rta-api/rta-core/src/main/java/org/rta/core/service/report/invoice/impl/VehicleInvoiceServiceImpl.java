package org.rta.core.service.report.invoice.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.insurance.InsuranceDetailsDAO;
import org.rta.core.dao.payment.gateway.TransactionDetailDAO;
import org.rta.core.dao.payment.invoice.DealerInvoiceDAO;
import org.rta.core.dao.payment.registfee.RegFeeDetailDAO;
import org.rta.core.dao.payment.tax.TaxDetailDAO;
import org.rta.core.dao.vehicle.VahanDAO;
import org.rta.core.dao.vehicle.VehicleBillingDetailsDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.dao.vehicle.cms.VehicleClassDescDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.insurance.InsuranceDetailsEntity;
import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.entity.payment.invoice.DealerInvoiceEntity;
import org.rta.core.entity.payment.registfee.RegFeeDetailsEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.vehicle.VahanEntity;
import org.rta.core.entity.vehicle.VehicleBillingDetailsEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.entity.vehicle.cms.VehicleClassDescriptionEntity;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.PaymentType;
import org.rta.core.enums.RegistrationCategoryType;
import org.rta.core.enums.Status;
import org.rta.core.enums.master.TaxType;
import org.rta.core.helper.reports.impl.VehicleTRInvoiceHelper;
import org.rta.core.model.finance.FinanceModel;
import org.rta.core.model.payment.tax.CessFeeModel;
import org.rta.core.model.payment.tax.TaxDetailModel;
import org.rta.core.model.reports.invoice.VehicleInvoiceModel;
import org.rta.core.service.finance.FinanceDtlsService;
import org.rta.core.service.payment.tax.TaxDetailService;
import org.rta.core.service.report.invoice.VehicleInvoiceService;
import org.rta.core.utils.NumberParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleInvoiceServiceImpl implements VehicleInvoiceService {

	private static final Logger log = Logger.getLogger(VehicleInvoiceServiceImpl.class);
	@Autowired
	private VehicleTRInvoiceHelper vehicleTRInvoiceHelper;
	@Autowired
	private VehicleDetailsDAO vehicleDetailsDAO;
	@Autowired
	private VehicleDAO vehicleDAO;
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private TaxDetailDAO taxDetailDAO;
	@Autowired
	private RegFeeDetailDAO regFeeDetailDAO;
	@Autowired
	private DealerInvoiceDAO dealerInvoiceDAO;
	@Autowired
	private InsuranceDetailsDAO insuranceDetailsDAO;
	@Autowired
	private FinanceDtlsService financeDtlsServices;
	@Autowired
	private TransactionDetailDAO transactionDetailDAO;
	@Autowired
	private CustomerCorporateDAO custCorpDAO;
	@Autowired
	private VehicleClassDescDAO vehicleClassDescDAO;
	@Autowired
	private VahanDAO vahanDAO;
	@Autowired
	private TaxDetailService taxDetailService;
	@Autowired
	private VehicleBillingDetailsDAO vehicleBillingDetailsDAO;

	@Override
	@Transactional
    public VehicleInvoiceModel getCustomerInvoice(long vehicleRcId, String invoiceType) {
		log.debug("::::::getCustomerInvoice::::::vehicleRcId::" + vehicleRcId);
		VehicleDetailsEntity vehicleDetailEntity = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
		VehicleRCEntity vehicleRcEntity = vehicleDAO.get(vehicleRcId);
		VehicleBillingDetailsEntity vehicleBillingDetailsEntity = vehicleBillingDetailsDAO.getByVehicleDetailId(vehicleDetailEntity);
        int regType = vehicleRcEntity.getRegCategory().getRegistrationCategoryId();
		CustIndividualDetailsEntity custIndDetailsEntity = null;
		CustCorporateDetailsEntity custCorpEntity = null;
		CessFeeModel cessFeeModel = new CessFeeModel();
		AddressEntity address = null;
		if (vehicleRcEntity != null) {
			switch (OwnershipType.getOwnershipType(vehicleRcEntity.getOwnershipType())) {
			case POLICE:
			case COMPANY:
			case STU_VEHICLES:
			case ORGANIZATION:
				custCorpEntity = custCorpDAO.getByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId());
				address = custCorpDAO.getAddressDetailsByUserId(custCorpEntity.getCustCorpDtlsId(), "T");
				break;
			case INDIVIDUAL:
				custIndDetailsEntity = customerDAO
						.getByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId());
				address = customerDAO.getAddressDetailsByUserId(custIndDetailsEntity.getCustIndDtlId(), "T");
				break;
			}
		}
		RegFeeDetailsEntity regFeeDetailEntity = regFeeDetailDAO
                .getByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId());
        DealerInvoiceEntity dealerInvoiceEntity = dealerInvoiceDAO
                .getByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId());
        TransactionDetailEntity transactionDetailEntity = new TransactionDetailEntity();
        if(!vehicleRcEntity.isMigrated()){
		transactionDetailEntity = transactionDetailDAO.getByVehicleRcNdStatus(vehicleDetailEntity.getVehicleRcId().getVehicleRcId(), PaymentType.PAYTAX, Status.CLOSED);
        }
		InsuranceDetailsEntity insuranceDetailsEntity = insuranceDetailsDAO
				.getInsuranceDtlsEntityByVehicleRcId(vehicleRcId);
		FinanceModel financeDetails = financeDtlsServices.getFinanceDtlsByVehicleRcId(vehicleRcId,null);

		VehicleClassDescriptionEntity vehicleClassDescriptionEntity = vehicleClassDescDAO
				.getByVehiclClassDesCode(vehicleDetailEntity.getVehicleSubClass());

		VahanEntity vahanEntity = vahanDAO.getByChassisNumber(vehicleRcEntity.getChassisNumber());
		
		TaxDetailModel taxDetailModel = taxDetailService.viewTaxDetails(vehicleRcId, null,  vehicleDetailEntity , vehicleBillingDetailsEntity);
		if(regFeeDetailEntity != null){
			taxDetailModel.setTrFee(NumberParser.numberFormat(regFeeDetailEntity.getTrFee()));
			taxDetailModel.setTrServiceCharge(NumberParser.numberFormat(regFeeDetailEntity.getTrServiceCharge()));
			taxDetailModel.setHpaFee(NumberParser.numberFormat(regFeeDetailEntity.getHpaFee()));
		}	
		taxDetailModel.setPenalty("0.00");
		
		double hpaFee = 0.0d;
		double cessFee = 0.0d;
        double trFee = 0.0d;
        double trServiceCharges = 0.0d;
        double taxAmt = 0.0d;
        double totalTrFee = 0.0d;
        
        double prFee = 0.0d;
        double postalFee = 0.0d;
        double cardFee = 0.0d;
        double prServiceCharges = 0.0d;
        double totalPrFee = 0.0d;

        cessFeeModel = taxDetailService.viewCessFeeDetails(vehicleDetailEntity);
        if(cessFeeModel != null)
        	cessFee = cessFeeModel.getCessFee();
        
        taxAmt = new Double(taxDetailModel.getTotalAmt());
        
        if(regFeeDetailEntity != null){
        	trServiceCharges = new Double(regFeeDetailEntity.getTrServiceCharge());
        	trFee = new Double(regFeeDetailEntity.getTrFee());
	        hpaFee = new Double(regFeeDetailEntity.getHpaFee());
	        totalTrFee = taxAmt + trFee  + trServiceCharges + hpaFee + cessFee ;
	        prFee = new Double(regFeeDetailEntity.getPrFee());
	        postalFee = new Double(regFeeDetailEntity.getPostalCharge());
	        cardFee = new Double(regFeeDetailEntity.getSmartCardFee());
	        prServiceCharges = new Double(regFeeDetailEntity.getPrServiceCharge());
	        totalPrFee = prFee + postalFee  + cardFee + prServiceCharges ;
	        regFeeDetailEntity.setTotalFee(totalPrFee );
        }   
        taxDetailModel.setGrandTotal(NumberParser
                .numberFormat(NumberParser.roundOff(totalTrFee, "########.##")));
        taxDetailModel.setCessFee(NumberParser.numberFormat(cessFee));
        

		
		log.debug("::::::::::vehicleDetailEntity " + vehicleDetailEntity
				+ (custCorpEntity == null ? ("-custIndiDetailsEntity-" + custIndDetailsEntity)
						: "-custCorpDetailsEntity-" + custCorpEntity)
				+ "-regFeeDetailEntity-" + regFeeDetailEntity
				+ "-dealerInvoiceEntity-" + dealerInvoiceEntity + "-address-" + address + "-insuranceDetailsEntity-"
				+ insuranceDetailsEntity + "-financeDetails-" + financeDetails + "-transactionDetailEntity"
				+ transactionDetailEntity + "-vehicleClassDescriptionEntity " + vehicleClassDescriptionEntity+" -vahanEntity " +vahanEntity + "taxDetailModel " + taxDetailModel);
		VehicleInvoiceModel vehicleTRInvoiceModel = vehicleTRInvoiceHelper.getModelByCustIndivEntity(
				vehicleDetailEntity, regFeeDetailEntity, dealerInvoiceEntity, address,
				insuranceDetailsEntity, financeDetails, transactionDetailEntity, invoiceType,
				vehicleClassDescriptionEntity,vahanEntity,taxDetailModel);
		if (vehicleRcEntity.getOwnershipType() == OwnershipType.COMPANY.getId() || vehicleRcEntity.getOwnershipType() == OwnershipType.POLICE.getId() || 
				vehicleRcEntity.getOwnershipType() == OwnershipType.STU_VEHICLES.getId() || vehicleRcEntity.getOwnershipType() == OwnershipType.ORGANIZATION.getId())
			return vehicleTRInvoiceHelper.setCustCorpDtlsToModel(vehicleTRInvoiceModel, custCorpEntity);

		if (vehicleRcEntity.getOwnershipType() == OwnershipType.INDIVIDUAL.getId())
			return vehicleTRInvoiceHelper.setCustIndivDtlsToModel(vehicleTRInvoiceModel, custIndDetailsEntity);
		else
			return vehicleTRInvoiceModel;
	}

}
