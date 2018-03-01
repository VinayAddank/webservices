package org.rta.core.helper.reports.impl;

import org.apache.log4j.Logger;
import org.rta.core.dao.vehicle.cms.VehicleClassDescDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.insurance.InsuranceDetailsEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.entity.payment.invoice.DealerInvoiceEntity;
import org.rta.core.entity.payment.registfee.RegFeeDetailsEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.vehicle.VahanEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.cms.VehicleClassDescriptionEntity;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.RegistrationType;
import org.rta.core.enums.VehicleCategory;
import org.rta.core.helper.payment.registfee.RegFeeDetailHelper;
import org.rta.core.helper.payment.tax.TaxDetailHelper;
import org.rta.core.model.customer.CustomerDetailsRequestModel;
import org.rta.core.model.finance.FinanceModel;
import org.rta.core.model.payment.tax.TaxDetailModel;
import org.rta.core.model.reports.invoice.VehicleInvoiceModel;
import org.rta.core.model.vehicle.VehicleBaseModel;
import org.rta.core.model.vehicle.VehicleBodyModel;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.NumberParser;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VehicleTRInvoiceHelper {

	private static final Logger log = Logger.getLogger(VehicleTRInvoiceHelper.class);
	
	@Autowired
	private TaxDetailHelper taxDetailHelper;
	
	@Autowired
	private RegFeeDetailHelper regFeeDetailHelper;
	
	@Autowired
	private VehicleClassDescDAO vehicleClassDescDAO;

	@Value("${rta.pr.validty}")
	Integer prValidty;
	@Value("${rta.days}")
	Integer days;
	@Value("${rta.months}")
	Integer months;

	public VehicleInvoiceModel getModelByCustIndivEntity(VehicleDetailsEntity vehicleDetailEntity,
			/* CustIndividualDetailsEntity custIndividualDetailsEntity, */ 
			RegFeeDetailsEntity regFeeDetailEntity, DealerInvoiceEntity dealerInvoiceEntity, AddressEntity addressEntity,
			InsuranceDetailsEntity insuranceDetailsEntity, FinanceModel financeModel,
			TransactionDetailEntity transactionDetailEntity, String invoiceType,
			VehicleClassDescriptionEntity vehicleClassDescriptionEntity, VahanEntity vahanEntity , TaxDetailModel taxDetailModel) {
		log.debug("::::::::::::::::START getModelByCustIndivEntity:::::::");

		VehicleInvoiceModel vehicleTRInvoiceModel = new VehicleInvoiceModel();
		VehicleBaseModel vehicleBaseModel = new VehicleBaseModel();
		CustomerDetailsRequestModel customerDetailsRequestModel = new CustomerDetailsRequestModel();
		vehicleBaseModel.setColor(vehicleDetailEntity.getColor());
		vehicleBaseModel.setEngineNumber(vehicleDetailEntity.getEngineNo());
		vehicleBaseModel.setChassisNumber(vehicleDetailEntity.getChassisNo());
		vehicleBaseModel.setFuelUsed(vehicleDetailEntity.getFuelUsed());
		vehicleBaseModel.setMakersClass(vehicleDetailEntity.getMakerClass());
		vehicleBaseModel.setMakersName(vehicleDetailEntity.getMakerName());
		vehicleBaseModel.setUlw(vehicleDetailEntity.getUlw());
		vehicleBaseModel.setSeatingCapacity(vehicleDetailEntity.getSeatingCapacity());
		vehicleBaseModel.setManufacturingMonthYear(vehicleDetailEntity.getMfgDate());
		vehicleBaseModel.setVehicleClass(vehicleDetailEntity.getVehicleClass());
		VehicleBodyModel bodyModel = new VehicleBodyModel();
		bodyModel.setBodyTypeUpdated(vehicleDetailEntity.getBodyTypeUpdated());
		bodyModel.setCompletionDate(vehicleDetailEntity.getCompletionDate());
		bodyModel.setGasKitNumber(vehicleDetailEntity.getGasKitNumber());
		bodyModel.setHeightUpdated(vehicleDetailEntity.getHeightUpdated());
		bodyModel.setLengthUpdated(vehicleDetailEntity.getLengthUpdated());
		bodyModel.setWidthUpdated(vehicleDetailEntity.getWidthUpdated());
		if(!StringsUtil.isNullOrEmpty(vehicleDetailEntity.getVehicleSubClass())){
			bodyModel.setVehicleSubClassDecs(vehicleClassDescDAO.getByVehiclClassDesCode(vehicleDetailEntity.getVehicleSubClass()).getDescription());
		}
		vehicleBaseModel.setAlterationDetails(bodyModel);
		log.debug("::::::::::::address start:::::::::::::");
		MandalEntity mandalEntity = addressEntity.getMandal();
		DistrictEntity districtEntity = mandalEntity.getDistrictEntity();
		StateEntity stateEntity = districtEntity.getStateEntity();
		log.debug("::::mandalEntity" + mandalEntity + "-districtEntity-" + districtEntity + "-stateEntity-" + stateEntity
				+ "addressEntity.getCity() " + addressEntity.getCity());
		StringBuilder sb = new StringBuilder(addressEntity.getDoorNo());
		StringsUtil.appendIfNotNull(sb, addressEntity.getStreet());
		StringsUtil.appendIfNotNull(sb, addressEntity.getCity());
		StringsUtil.appendIfNotNull(sb, mandalEntity.getName());
		StringsUtil.appendIfNotNull(sb, districtEntity.getName());
		StringsUtil.appendIfNotNull(sb, stateEntity.getName());
		vehicleTRInvoiceModel.setAddress(sb.toString());
		vehicleTRInvoiceModel.setNationality("Indian");
		vehicleTRInvoiceModel.setVehicleBaseModel(vehicleBaseModel);
		vehicleTRInvoiceModel.setCustomerDetailsRequestModel(customerDetailsRequestModel);
		vehicleTRInvoiceModel.setTaxDetailModel(taxDetailModel);
		if(regFeeDetailEntity != null)
			vehicleTRInvoiceModel.setRegFeeDetailModel(regFeeDetailHelper.convertToModel(regFeeDetailEntity));
		vehicleTRInvoiceModel.setDescOfVehicle("");
		vehicleTRInvoiceModel.setValidFrom(vehicleDetailEntity.getVehicleRcId().getTrIssueTime());
		if (RegistrationType.PR.getLabel().equalsIgnoreCase(invoiceType)) {
			vehicleTRInvoiceModel
					.setValidTo(vehicleDetailEntity.getVehicleRcId().getPrExpireDate());
			vehicleTRInvoiceModel.setPrNo(vehicleDetailEntity.getVehicleRcId().getPrNumber());
			vehicleTRInvoiceModel.setValidFrom(vehicleDetailEntity.getVehicleRcId().getPrIssueTime());
			vehicleTRInvoiceModel.setPrIssueDate(vehicleDetailEntity.getVehicleRcId().getTrIssueTime());
			vehicleTRInvoiceModel.setFronAxle(NumberParser.numberFormat(vahanEntity.getFrontAxleWeight()));
			vehicleTRInvoiceModel.setCubicCapacity(NumberParser.numberFormat(vahanEntity.getCubicCap()));
			vehicleTRInvoiceModel.setHorsePower(NumberParser.numberFormat(vahanEntity.getEnginePower()));
			vehicleTRInvoiceModel.setNoOfCylinder(NumberParser.numberFormat(vahanEntity.getNoCyl()));
			vehicleTRInvoiceModel.setAnyOtherAxle(NumberParser.numberFormat(vahanEntity.getO1AxleWeight()));
			vehicleTRInvoiceModel.setRearAxle(NumberParser.numberFormat(vahanEntity.getRearAxleWeight()));
			vehicleTRInvoiceModel.setTandemAxle(NumberParser.numberFormat(vahanEntity.getTandemAxelWeight()));
			vehicleTRInvoiceModel.setWheelBase(NumberParser.numberFormat(vahanEntity.getWheelbase()));
			vehicleTRInvoiceModel.setGrossVehicleWgt(NumberParser.numberFormat(vehicleDetailEntity.getRlw()));
		} else {
			vehicleTRInvoiceModel.setTrIssueDate(vehicleDetailEntity.getVehicleRcId().getTrIssueTime());
			vehicleTRInvoiceModel.setTrNo(vehicleDetailEntity.getVehicleRcId().getTrNumber());
			vehicleTRInvoiceModel
					.setValidTo(DateUtil.addDays(vehicleDetailEntity.getVehicleRcId().getTrIssueTime(), days));
			vehicleTRInvoiceModel.setValidFrom(vehicleDetailEntity.getVehicleRcId().getTrIssueTime());
		}
		if(!vehicleDetailEntity.getVehicleRcId().isMigrated()){
		vehicleTRInvoiceModel.setTransactionNo(transactionDetailEntity.getSbiRefNo());
		}
		if (financeModel != null)
			vehicleTRInvoiceModel.setFinanceModel(financeModel);
		if(dealerInvoiceEntity != null){
			vehicleTRInvoiceModel.setInvoiceAmt(NumberParser.numberFormat(dealerInvoiceEntity.getInvoiceAmt()));
			vehicleTRInvoiceModel.setHsrp(NumberParser.numberFormat(dealerInvoiceEntity.getHsrpFee()));
		}
		log.debug(":::vehicleClassDescriptionEntity:::::::::::" + vehicleClassDescriptionEntity.getDescription());
		vehicleTRInvoiceModel.setVehicleDiscription(vehicleClassDescriptionEntity.getDescription());
		if(VehicleCategory.CHASSIS_ONLY == vehicleDetailEntity.getVehicleCategory()){
			vehicleTRInvoiceModel.setBodyType(vehicleDetailEntity.getBodyTypeUpdated());
		}else{
			vehicleTRInvoiceModel.setBodyType(vahanEntity.getBodyTypeDesc());
		}
		log.debug("::::::::::::::::END getModelByCustIndivEntity:::::::");
		return vehicleTRInvoiceModel;
	}

	public VehicleInvoiceModel setCustIndivDtlsToModel(VehicleInvoiceModel vehicleTRInvoiceModel,
			CustIndividualDetailsEntity custIndividualDetailsEntity) {
		if (vehicleTRInvoiceModel == null)
			vehicleTRInvoiceModel = new VehicleInvoiceModel();
		CustomerDetailsRequestModel customerDetailsRequestModel = new CustomerDetailsRequestModel();
		customerDetailsRequestModel.setDob(custIndividualDetailsEntity.getDateOfBirth());
		customerDetailsRequestModel.setMobileNumber(custIndividualDetailsEntity.getMobileNo());
		customerDetailsRequestModel.setRc_name(custIndividualDetailsEntity.getDisplayName());
		customerDetailsRequestModel.setFather_name(custIndividualDetailsEntity.getFatherName());
		customerDetailsRequestModel.setFirst_name(custIndividualDetailsEntity.getFirstName());
		customerDetailsRequestModel.setLast_name(custIndividualDetailsEntity.getSurName());
		customerDetailsRequestModel.setVehregrta(custIndividualDetailsEntity.getVechicleRegRta());
		customerDetailsRequestModel.setVehregrtacode(custIndividualDetailsEntity.getVehicleRegRtaCode());
		customerDetailsRequestModel.setOwnershipType(OwnershipType.INDIVIDUAL.getLabel());
		customerDetailsRequestModel.setOwnershipTypeName(OwnershipType.INDIVIDUAL.getLabel());
		vehicleTRInvoiceModel.setRegistOffice(custIndividualDetailsEntity.getVechicleRegRta() + "-"
				+ custIndividualDetailsEntity.getVehicleRegRtaCode());
		vehicleTRInvoiceModel.setCustomerDetailsRequestModel(customerDetailsRequestModel);
		return vehicleTRInvoiceModel;
	}

	@SuppressWarnings("incomplete-switch")
    public VehicleInvoiceModel setCustCorpDtlsToModel(VehicleInvoiceModel vehicleTRInvoiceModel,
			CustCorporateDetailsEntity custCorpDtlsEntity) {
		if (vehicleTRInvoiceModel == null)
			vehicleTRInvoiceModel = new VehicleInvoiceModel();
		CustomerDetailsRequestModel customerDetailsRequestModel = new CustomerDetailsRequestModel();
		// customerDetailsRequestModel.setDob(custCorpDtlsEntity.getDateOfBirth());
		customerDetailsRequestModel.setMobileNumber(custCorpDtlsEntity.getMobile());
		customerDetailsRequestModel.setRc_name(custCorpDtlsEntity.getDisplayName());
		// customerDetailsRequestModel.setFather_name(custCorpDtlsEntity.getFatherName());
		customerDetailsRequestModel.setFirst_name(custCorpDtlsEntity.getCompanyName());
		// customerDetailsRequestModel.setLast_name(custCorpDtlsEntity.getSurName());
		customerDetailsRequestModel.setVehregrta(custCorpDtlsEntity.getVehicleRegRta());
		customerDetailsRequestModel.setVehregrtacode(custCorpDtlsEntity.getVehicleRegRtaCode());

		switch (OwnershipType.getOwnershipType(custCorpDtlsEntity.getVehicleRcId().getOwnershipType())) {
            case POLICE:
                customerDetailsRequestModel.setOwnershipType(OwnershipType.POLICE.getLabel());
                customerDetailsRequestModel.setOwnershipTypeName(OwnershipType.POLICE.getLabel());
                break;
            case COMPANY:
                customerDetailsRequestModel.setOwnershipType(OwnershipType.COMPANY.getLabel());
                customerDetailsRequestModel.setOwnershipTypeName(OwnershipType.COMPANY.getLabel());
                break;
            case STU_VEHICLES:
                customerDetailsRequestModel.setOwnershipType(OwnershipType.STU_VEHICLES.getLabel());
                customerDetailsRequestModel.setOwnershipTypeName(OwnershipType.STU_VEHICLES.getLabel());
                break;
            case ORGANIZATION:
                customerDetailsRequestModel.setOwnershipType(OwnershipType.ORGANIZATION.getLabel());
                customerDetailsRequestModel.setOwnershipTypeName(OwnershipType.ORGANIZATION.getLabel());
                break;
        }
		
		customerDetailsRequestModel.setRepresentedBy(custCorpDtlsEntity.getRepresentedBy());
		vehicleTRInvoiceModel.setRegistOffice(
				custCorpDtlsEntity.getVehicleRegRta() + "-" + custCorpDtlsEntity.getVehicleRegRtaCode());
		vehicleTRInvoiceModel.setCustomerDetailsRequestModel(customerDetailsRequestModel);
		return vehicleTRInvoiceModel;
	}
}
