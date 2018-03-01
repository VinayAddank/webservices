package org.rta.core.service.export.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.rta.core.dao.certificate.FcDetailsDAO;
import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.docs.AttachmentDetailsDAO;
import org.rta.core.dao.finance.FinanceDetailsDAO;
import org.rta.core.dao.finance.VehicleFinanceDtlsDAO;
import org.rta.core.dao.insurance.InsuranceDetailsDAO;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.master.PermitGoodsDAO;
import org.rta.core.dao.master.PermitRouteDAO;
import org.rta.core.dao.master.StateDAO;
import org.rta.core.dao.payment.registfee.RegFeeDetailDAO;
import org.rta.core.dao.payment.tax.TaxDetailDAO;
import org.rta.core.dao.payment.tax.VehicleCurrentTaxDAO;
import org.rta.core.dao.permit.PermitAuthCardDAO;
import org.rta.core.dao.permit.PermitDetailsMappingDAO;
import org.rta.core.dao.permit.PermitHeaderDAO;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.dao.user.PermanentAddressDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.dao.vehicle.VahanDAO;
import org.rta.core.dao.vehicle.VehicleBillingDetailsDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.dao.vehicle.cms.VehicleClassDescDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.certificate.FitnessCertificateEntity;
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.entity.finance.FinanceDetailsEntity;
import org.rta.core.entity.finance.VehicleFinanceDtlstEntity;
import org.rta.core.entity.insurance.InsuranceDetailsEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.PermitGoodsMasterEntity;
import org.rta.core.entity.master.PermitRouteConditionsMasterEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.entity.payment.registfee.RegFeeDetailsEntity;
import org.rta.core.entity.payment.tax.TaxDetailEntity;
import org.rta.core.entity.payment.tax.VehicleCurrentTaxEntity;
import org.rta.core.entity.permit.PermitAuthCardDetailsEntity;
import org.rta.core.entity.permit.PermitDetailsMappingEntity;
import org.rta.core.entity.permit.PermitHeaderEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.user.PermanentAddressEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.VahanEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.entity.vehicle.cms.VehicleClassDescriptionEntity;
import org.rta.core.enums.AddressType;
import org.rta.core.enums.DocTypes;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.PermitClassType;
import org.rta.core.enums.PermitDetailsType;
import org.rta.core.enums.Status;
import org.rta.core.model.docs.AttachmentDetailsModel;
import org.rta.core.model.export.DealerDetails;
import org.rta.core.model.export.FinancerDetails;
import org.rta.core.model.export.FitnessDetails;
import org.rta.core.model.export.FormsModel;
import org.rta.core.model.export.IibExportModel;
import org.rta.core.model.export.InsuranceDetails;
import org.rta.core.model.export.OwnerDetails;
import org.rta.core.model.export.OwnerPermAddressDetails;
import org.rta.core.model.export.PermitDetails;
import org.rta.core.model.export.RegistrationFeeDetails;
import org.rta.core.model.export.RtaOfficeDetails;
import org.rta.core.model.export.SignatureDetails;
import org.rta.core.model.export.SinkAllData;
import org.rta.core.model.export.SinkTaxDetails;
import org.rta.core.model.export.TaxDetails;
import org.rta.core.model.export.VehicleDetails;
import org.rta.core.model.export.VehicleRegistrationDetails;
import org.rta.core.service.export.ExportDataService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *	@Author sohan.maurya created on Sep 16, 2016.
 */

@Service("exportDataService")
public class ExportDataServiceImpl implements ExportDataService {

    private static final Logger logger = Logger.getLogger(ExportDataServiceImpl.class);


    @Autowired
    private InsuranceDetailsDAO insuranceDetailsDAO;

    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private VahanDAO vahanDAO;

    @Autowired
    private CustomerCorporateDAO customerCorporateDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private TaxDetailDAO taxDetailDAO;

    @Autowired
    private FinanceDetailsDAO financeDetailsDAO;

    @Autowired
    private AddressDAO addressDao;

    @Autowired
    private VehicleDetailsDAO vehicleDetailsDAO;

    @Autowired
    private VehicleClassDescDAO vehicleClassDescDAO;

    @Autowired
    private RegFeeDetailDAO regFeeDetailDAO;

    @Autowired
    private AttachmentDetailsDAO attachmentDetailsDAO;

    @Autowired
    private PermanentAddressDAO permanentAddressDAO;

    @Autowired
    private VehicleBillingDetailsDAO vehicleBillingDetailsDAO;
    
    @Autowired
    private FcDetailsDAO fcDetailsDAO;
    
    @Autowired
    private PermitHeaderDAO permitHeaderDAO;

    @Autowired
    private VehicleCurrentTaxDAO vehicleCurrentTaxDAO;
    
    @Autowired
    private PermitDetailsMappingDAO permitDetailsMappingDAO;
    
    @Autowired
    private PermitAuthCardDAO permitAuthCardDAO;
    
    @Autowired
    private PermitRouteDAO permitRouteDAO;
    
    @Autowired
    private DistrictDAO districtDAO;
    
    @Autowired
    private PermitGoodsDAO permitGoodsDAO;
    
    @Autowired
    private UserDAO UserDAO;
    
    @Autowired
    private VehicleFinanceDtlsDAO vehicleOnlineFinanceDAO;
    
    @Autowired
    private StateDAO stateDAO;

    private Long customerId = null;
    private Boolean isIndivCustomer = false;
    private String makersName;
    private String makersClass;
    private String ownerDateOfBirth = null;
    private String rlw;

    @Value("${rta.vehicle.registration.type}")
    private String registrationType;

    @Value("${rta.pr.validty}")
    private Integer prValidty;

    @Transactional
    @Override
    public List<SinkAllData> getAllVehicleDetailsBetweenADay(String from, String to, Boolean byTrIssueTime) {
        logger.debug(":::::::::getAllVehicleDetailsBetweenADay::::::::::start::::::");
        List<VehicleRCEntity> vehicleRCEntities = null;
        if(byTrIssueTime) {
            vehicleRCEntities = vehicleDAO.getTrDetailsVehicleRc(DateUtil.getLongDate(from, "00:00:00"),
                    DateUtil.getLongDate(to, "23:59:59"));
        } else {
            vehicleRCEntities = vehicleDAO.getVehicleRcByFromToDate(DateUtil.getLongDate(from, "00:00:00"),
                        DateUtil.getLongDate(to, "23:59:59"));
        }
        if (ObjectsUtil.isNull(vehicleRCEntities) || vehicleRCEntities.size() <= 0) {
            logger.error(":::::::::getAllVehicleDetailsBetweenADay::::::::::Vehicle Details Not Found::::::");
            throw new IllegalArgumentException("Vehicle Details Not Found .....!! ");
        }
        Map<Long, OwnerDetails> ownerDetailsMap = getOwnerDetails(vehicleRCEntities);
        Map<Long, VehicleRegistrationDetails> vehicleRegistrationDetailsMap =
                getVehicleRegistrationDetails(vehicleRCEntities);
        Map<Long, DealerDetails> dealerDetailsMap = getDealerDetails(vehicleRCEntities);
        Map<Long, InsuranceDetails> insuranceDetailsMap = getInsuranceDetails(vehicleRCEntities);
        Map<Long, VehicleDetails> vehicleDetailsMap = getVehicleDetails(vehicleRCEntities);
        Map<Long, TaxDetails> taxDetailsMap = getTaxDetails(vehicleRCEntities);
        Map<Long, FinancerDetails> financerDetailsMap = getFinancerDetails(vehicleRCEntities);
        Map<Long, List<AttachmentDetailsModel>> attachDltMap = getAttachmentDetails(vehicleRCEntities);
        
        List<SinkAllData> sinkAllDataList = new ArrayList<SinkAllData>();
        SinkAllData sinkAllData=null;
        for (VehicleRCEntity vehicleRCEntity : vehicleRCEntities) {
            sinkAllData=new SinkAllData();
            sinkAllData.setApplication(String.valueOf(vehicleRCEntity.getVehicleRcId()));
            sinkAllData.setOwnerDetails(ownerDetailsMap.get(vehicleRCEntity.getVehicleRcId()));
            sinkAllData
                    .setVehicleRegistrationDetails(vehicleRegistrationDetailsMap.get(vehicleRCEntity.getVehicleRcId()));
            sinkAllData.setDealerDetails(dealerDetailsMap.get(vehicleRCEntity.getVehicleRcId()));
            sinkAllData.setInsuranceDetails(insuranceDetailsMap.get(vehicleRCEntity.getVehicleRcId()));
            sinkAllData.setVehicleDetails(vehicleDetailsMap.get(vehicleRCEntity.getVehicleRcId()));
            sinkAllData.setTaxDetails(taxDetailsMap.get(vehicleRCEntity.getVehicleRcId()));
            sinkAllData.setAttachmentDetails(attachDltMap.get(vehicleRCEntity.getVehicleRcId()));
            if (financerDetailsMap.get(vehicleRCEntity.getVehicleRcId()) == null) {
                sinkAllData.setFinancerDetails(null);
            } else {
                sinkAllData.setFinancerDetails(financerDetailsMap.get(vehicleRCEntity.getVehicleRcId()));
            }

            sinkAllDataList.add(sinkAllData);
        }
        logger.debug(":::::::::getAllVehicleDetailsBetweenADay::::::::::end::::::");
        return sinkAllDataList;
    }

    @Transactional
    @Override
    public List<SinkTaxDetails> getAllTaxDetailsBetweenADay(String from, String to, Boolean byTrIssueTime) {
        logger.debug(":::::::::getAllTaxDetailsBetweenADay::::::::::start::::::");
        List<VehicleRCEntity> vehicleRCEntities = null;
        if(byTrIssueTime) {
            vehicleRCEntities = vehicleDAO
            .getTrDetailsVehicleRc(DateUtil.getLongDate(from, "00:00:00"), DateUtil.getLongDate(to, "23:59:59"));
        } else {
            vehicleRCEntities = vehicleDAO
                .getVehicleRcByFromToDate(DateUtil.getLongDate(from, "00:00:00"), DateUtil.getLongDate(to, "23:59:59"));
        }
        if (ObjectsUtil.isNull(vehicleRCEntities) || vehicleRCEntities.size() <= 0) {
            logger.error(":::::::::getAllTaxDetailsBetweenADay::::::::::Vehicle Details Not Found::::::");
            throw new IllegalArgumentException("Vehicle Details Not Found .....!! ");
        }
        List<TaxDetailEntity> taxDetailEntities = taxDetailDAO.getByVehicleRcEntities(vehicleRCEntities);
        if (ObjectsUtil.isNull(taxDetailEntities) || taxDetailEntities.size() <= 0) {
            logger.error(":::::::::getAllTaxDetailsBetweenADay::::::::::Tax Details Not Found::::::");
            throw new IllegalArgumentException("Tax Details Not Found .....!! ");
        }
        Map<Long, RegistrationFeeDetails> registrationFeeDetailsMap = getRegistrationFeeDetails(vehicleRCEntities);
        List<SinkTaxDetails> sinkTaxDetails = new ArrayList<SinkTaxDetails>();
        SinkTaxDetails taxDetails = null;
        for (TaxDetailEntity taxDtlEntity : taxDetailEntities) {
            taxDetails = new SinkTaxDetails();
            taxDetails.setApplication(String.valueOf(taxDtlEntity.getVehicleRcId().getVehicleRcId()));
            taxDetails.setPurpose(null);
            taxDetails.setOfficeCode(taxDtlEntity.getVehicleRcId().getRtaOfficeId().getCode());
            taxDetails.setDistrictCode(taxDtlEntity.getVehicleRcId().getRtaOfficeId().getDistrictCode());
            if (!ObjectsUtil.isNull(taxDtlEntity.getCreatedOn())) {
                taxDetails.setTransactionDate(DateUtil.extractDateAsString(taxDtlEntity.getCreatedOn()));
            }
            taxDetails.setTaxType(taxDtlEntity.getTaxType().getName());
            taxDetails.setPenalty(String.valueOf(taxDtlEntity.getPenalty()));
            taxDetails.setSecondVehicleTax(String.valueOf(taxDtlEntity.getSecondVehicleTaxAmt()));
            taxDetails.setTrFee(String.valueOf(taxDtlEntity.getTrFee()));
            taxDetails.setHpaFee(String.valueOf(taxDtlEntity.getHpaFee()));
            taxDetails.setPaidAmount(String.valueOf(taxDtlEntity.getTotalAmt()));
            taxDetails.setTaxAmount(String.valueOf(taxDtlEntity.getTaxAmt()));
            taxDetails.setCrtddt(null);
            taxDetails.setTrServiceCharge(String.valueOf(taxDtlEntity.getTrServiceCharge()));
            RegistrationFeeDetails regFeeDetals =
                    registrationFeeDetailsMap.get(taxDtlEntity.getVehicleRcId().getVehicleRcId());
            if (!ObjectsUtil.isNull(regFeeDetals)) {
                taxDetails.setRegistrationFees(regFeeDetals.getRegistrationFees());
                taxDetails.setServiceCharge(regFeeDetals.getServiceCharge());
                taxDetails.setSmartCardFees(regFeeDetals.getSmartCardFees());
                taxDetails.setPostalCharge(regFeeDetals.getPostalCharge());
            } else {
                taxDetails.setRegistrationFees(null);
                taxDetails.setServiceCharge(null);
                taxDetails.setSmartCardFees(null);
                taxDetails.setPostalCharge(null);
            }
            sinkTaxDetails.add(taxDetails);
            }
        logger.debug(":::::::::getAllTaxDetailsBetweenADay::::::::::end::::::");
        return sinkTaxDetails;
    }


    @SuppressWarnings("incomplete-switch")
    private Map<Long, OwnerDetails> getOwnerDetails(List<VehicleRCEntity> vehicleRCEntities) {
        logger.debug(":::::::::getOwnerDetails::::::::::start::::::");
        Map<Long, OwnerDetails> ownerDetailsMap = new HashMap<Long, OwnerDetails>();
        for (VehicleRCEntity vehicleRCEntity : vehicleRCEntities) {

            AddressEntity addressEntity = new AddressEntity();
            OwnerDetails ownerDetails=new OwnerDetails();
            switch (OwnershipType.getOwnershipType(vehicleRCEntity.getOwnershipType())) {

                case INDIVIDUAL:
                    CustIndividualDetailsEntity custIndividualDetailsEntity =
                            customerDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                    addressEntity =
                            customerDAO.getAddressDetailsByUserId(custIndividualDetailsEntity.getCustIndDtlId(), "T");
                    ownerDetails = getCustomerIndiDetails(addressEntity, custIndividualDetailsEntity, ownerDetails);
                    ownerDetailsMap.put(vehicleRCEntity.getVehicleRcId(), ownerDetails);
                    break;
                case POLICE:    
                case COMPANY:
                case STU_VEHICLES:
                case ORGANIZATION:
                    CustCorporateDetailsEntity custCorporateDetailsEntity =
                            customerCorporateDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                    addressEntity = customerCorporateDAO
                            .getAddressDetailsByUserId(custCorporateDetailsEntity.getCustCorpDtlsId(), "T");
                    ownerDetails = getCustomerCorpDetails(addressEntity, custCorporateDetailsEntity, ownerDetails);
                    ownerDetailsMap.put(vehicleRCEntity.getVehicleRcId(), ownerDetails);
                    break;
            }
        }
        logger.debug(":::::::::getOwnerDetails::::::::::end::::::");
        return ownerDetailsMap;
    }

    private OwnerDetails getCustomerIndiDetails(AddressEntity addressEntity, CustIndividualDetailsEntity custIndiDtl,
            OwnerDetails ownerDetails) {
        logger.debug(":::::::::getCustomerIndiDetails::::::::::start::::::");

        ownerDetails.setOwnershipType(OwnershipType.INDIVIDUAL.getLabel());
        ownerDetails.setOwnerFirstName(custIndiDtl.getFirstName());
        ownerDetails.setOwnerMiddileName("");
        ownerDetails.setOwnerLastName(custIndiDtl.getSurName());
        ownerDetails.setOwnerFatherName(custIndiDtl.getFatherName());
        ownerDetails.setOwnerMobileNo(custIndiDtl.getMobileNo());
        ownerDetails.setOwnerPanNo(custIndiDtl.getPanNo());
        ownerDetails.setOwnerVoterId(null);
        ownerDetails.setOwnerAddress1(addressEntity.getDoorNo());
        ownerDetails.setOwnerAddress2(addressEntity.getStreet());
        ownerDetails.setOwnerAddress3(null);
        ownerDetails.setOwnerCity(addressEntity.getCity());
        ownerDetails.setOwnerState(addressEntity.getMandal().getDistrictEntity().getStateEntity().getName());
        ownerDetails.setOwnerPin(addressEntity.getPinCode());
        if (!StringsUtil.isNullOrEmpty(custIndiDtl.getDateOfBirth())) {
            try {
                ownerDetails.setOwnerAge(DateUtil.getCurrentAge(custIndiDtl.getDateOfBirth()));
            } catch (Exception ex) {
                logger.error(":::::::::getCustomerIndiDetails::::::::::Exception::::::" + ex.getMessage());
                logger.debug(ex);
            }
        }
        logger.debug(":::::::::getCustomerIndiDetails::::::::::end::::::");

        return ownerDetails;
    }

    private OwnerDetails getCustomerCorpDetails(AddressEntity addressEntity, CustCorporateDetailsEntity custCorpDtl,
            OwnerDetails ownerDetails) {
        logger.info(":::::::::getCustomerCorpDetails::::::::::start::::::");
        
        if(custCorpDtl.getCompanyName().equalsIgnoreCase(OwnershipType.COMPANY.getLabel())) {
        	ownerDetails.setOwnershipType(OwnershipType.COMPANY.getLabel());
        } else if(custCorpDtl.getCompanyName().equalsIgnoreCase(OwnershipType.POLICE.getLabel())) {
            ownerDetails.setOwnershipType(OwnershipType.POLICE.getLabel());
        } else if(custCorpDtl.getCompanyName().equalsIgnoreCase(OwnershipType.STU_VEHICLES.getLabel())) {
            ownerDetails.setOwnershipType(OwnershipType.STU_VEHICLES.getLabel());
        } else if(custCorpDtl.getCompanyName().equalsIgnoreCase(OwnershipType.ORGANIZATION.getLabel())) {
            ownerDetails.setOwnershipType(OwnershipType.ORGANIZATION.getLabel());
        }
        ownerDetails.setOwnerFirstName(custCorpDtl.getCompanyName());
        ownerDetails.setOwnerMiddileName(null);
        ownerDetails.setOwnerLastName(null);
        ownerDetails.setOwnerAge(null);/// pending
        ownerDetails.setOwnerFatherName(null);
        ownerDetails.setOwnerMobileNo(custCorpDtl.getMobile());
        ownerDetails.setOwnerPanNo(custCorpDtl.getPanNo());
        ownerDetails.setOwnerVoterId(null);
        ownerDetails.setOwnerAddress1(addressEntity.getDoorNo());
        ownerDetails.setOwnerAddress2(addressEntity.getStreet());
        ownerDetails.setOwnerAddress3(null);
        ownerDetails.setOwnerCity(addressEntity.getCity());
        ownerDetails.setOwnerState(addressEntity.getMandal().getDistrictEntity().getStateEntity().getName());
        ownerDetails.setOwnerPin(addressEntity.getPinCode());
        logger.debug(":::::::::getCustomerCorpDetails::::::::::end::::::");

        return ownerDetails;
    }

    private Map<Long, VehicleRegistrationDetails> getVehicleRegistrationDetails(
            List<VehicleRCEntity> vehicleRCEntities) {
        logger.debug(":::::::::getVehicleRegistrationDetails::::::::::start::::::");

        VehicleRegistrationDetails vehicleRegistrationDetails = null;
        Map<Long, VehicleRegistrationDetails> vehicleRegistrationDetailsMap = new HashMap<>();
        for (VehicleRCEntity vehicleRCEntity : vehicleRCEntities) {

            vehicleRegistrationDetails = new VehicleRegistrationDetails();
            vehicleRegistrationDetails.setVehicleNumber(vehicleRCEntity.getPrNumber());
            vehicleRegistrationDetails
                    .setVehicleRegisteredDate(DateUtil.extractDateAsString(vehicleRCEntity.getPrIssueTime()));
            vehicleRegistrationDetails.setVehicleRegistrationValidUpto(
                    DateUtil.extractDateAsString(DateUtil.addYears(vehicleRCEntity.getPrIssueTime(), prValidty)));
            vehicleRegistrationDetails.setVehicleIssuePlace(null);
            vehicleRegistrationDetails.setVehicleStatus("A");
            vehicleRegistrationDetails.setRemarks(null);
            vehicleRegistrationDetails.setVehicleOldNo(vehicleRCEntity.getTrNumber());
            vehicleRegistrationDetails.setPreviousRegisteredOfficeName(null);
            vehicleRegistrationDetails.setPreviousRegisteredOfficeState(null);
            vehicleRegistrationDetails.setGovernmentVehicle("N");
            vehicleRegistrationDetails.setVehicleIsRTC("N");
            vehicleRegistrationDetailsMap.put(vehicleRCEntity.getVehicleRcId(), vehicleRegistrationDetails);
        }
        logger.debug(":::::::::getVehicleRegistrationDetails::::::::::end::::::");
        return vehicleRegistrationDetailsMap;
    }

    private Map<Long, DealerDetails> getDealerDetails(List<VehicleRCEntity> vehicleRCEntities) {

        logger.debug(":::::::::getDealerDetails::::::::::start::::::");
        Map<Long, DealerDetails> dealerDetailsMap = new HashMap<Long, DealerDetails>();
        DealerDetails dealerDetails = null;
        AddressEntity address = null;
        String dealerName = null;
        for (VehicleRCEntity vehicleRCEntity : vehicleRCEntities) {

            dealerDetails = new DealerDetails();
            dealerName = vehicleRCEntity.getUserId().getFirstName();
            if (!StringsUtil.isNullOrEmpty(vehicleRCEntity.getUserId().getLastName())) {
                dealerName=dealerName+" "+ vehicleRCEntity.getUserId().getLastName();
            }
            dealerDetails.setDealerName(dealerName);
            try{
                address = addressDao.findByUserIdAndType(vehicleRCEntity.getUserId().getUserId(), "T");
                dealerDetails.setDealerAddress1(address.getDoorNo());
                dealerDetails.setDealerAddress2(address.getStreet());
                dealerDetails.setDealerAddress3(null);
                dealerDetails.setDealerCity(address.getCity());
                dealerDetails.setDealerState(address.getMandal().getDistrictEntity().getStateEntity().getName());
                dealerDetails.setDealerAddressPinCode(address.getPinCode());
            }catch(Exception ex){
                logger.error(":::::::::getDealerDetails::::::::::Exception::::::" + ex.getMessage());
                logger.debug(ex);
            }
            dealerDetailsMap.put(vehicleRCEntity.getVehicleRcId(), dealerDetails);
        }
        logger.debug(":::::::::getDealerDetails::::::::::end::::::");
        return dealerDetailsMap;
    }

    private Map<Long, InsuranceDetails> getInsuranceDetails(List<VehicleRCEntity> vehicleRCEntities) {

        logger.debug(":::::::::getInsuranceDetails::::::::::start::::::");
        List<InsuranceDetailsEntity> insuranceDetailsEntities = null;
        try{
            insuranceDetailsEntities= insuranceDetailsDAO.getInsuranceDtlsEntitiesByVehicleRCEntities(vehicleRCEntities);
        } catch (Exception ex) {
            logger.error(":::::::::getInsuranceDetails::::::::::Exception::::::" + ex.getMessage());
            logger.debug(ex);
        }
        Map<Long, InsuranceDetails> insuranceDetailsMap = new HashMap<Long, InsuranceDetails>();
        InsuranceDetails insuranceDetails = null;
        for (InsuranceDetailsEntity insuranceDetailsEntity : insuranceDetailsEntities) {
            insuranceDetails = new InsuranceDetails();
            insuranceDetails.setInsuranceCompanyName(insuranceDetailsEntity.getProvider());
            insuranceDetails.setInsuranceNo(insuranceDetailsEntity.getPolicyNo());
            insuranceDetails.setInsuranceValidFrom(DateUtil.extractDateAsString(insuranceDetailsEntity.getStartDate()));
            insuranceDetails.setInsuranceValidTo(DateUtil.extractDateAsString(insuranceDetailsEntity.getEndDate()));
            insuranceDetailsMap.put(insuranceDetailsEntity.getVehicleRcEntity().getVehicleRcId(), insuranceDetails);
        }
        logger.debug(":::::::::getInsuranceDetails::::::::::end::::::");
        return insuranceDetailsMap;
    }

    private Map<Long, VehicleDetails> getVehicleDetails(List<VehicleRCEntity> vehicleRCEntities) {
        
        logger.debug(":::::::::getVehicleDetails::::::::::start::::::");
        List<VahanEntity> vahanEntities = null;
        List<Long> vehicleRcList = new ArrayList<Long>();
        Map<String, Long> chassisNoMap = new HashMap<String, Long>();
        for (VehicleRCEntity vehicleRCEntity : vehicleRCEntities) {
            chassisNoMap.put(vehicleRCEntity.getChassisNumber(), vehicleRCEntity.getVehicleRcId()); 
            vehicleRcList.add(vehicleRCEntity.getVehicleRcId());
        }
        Map<Long, String> vehicleTypeCodes = getVehicleTypeCodes(vehicleRcList);
        Set<String> vehicleTypeAllCodes = new HashSet<>(vehicleTypeCodes.values());
        Map<String, String> vehicleTypeDescript = getVehicleClassDescription(vehicleTypeAllCodes);
        try{
            vahanEntities = vahanDAO.getVahanEntitiesByChassisNumber(chassisNoMap.keySet());
        }catch(Exception ex){
            logger.error(":::::::::getVehicleDetails::::::::::Exception::::::"+ex.getMessage());
            logger.debug(ex);
        }
        Map<Long, VehicleDetails> vehicleDetailsMap = new HashMap<Long, VehicleDetails>();
        VehicleDetails vehicleDetails = null;
        for (VahanEntity vahanEntity : vahanEntities) {
                    
            vehicleDetails = new VehicleDetails();
            Long vehicleRcId = chassisNoMap.get(vahanEntity.getChassisNumber());
            VehicleDetailsEntity vde = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
            vehicleDetails.setMakerClass(vahanEntity.getMakersClass());
            vehicleDetails.setMakerName(vahanEntity.getMakersDesc());
            vehicleDetails.setVehicleClass(
                    vehicleTypeDescript.get(vehicleTypeCodes.get(chassisNoMap.get(vahanEntity.getChassisNumber()))));
            vehicleDetails.setChassisNo(vahanEntity.getChassisNumber());
            vehicleDetails.setEngineNo(vahanEntity.getEngineNumber());
            vehicleDetails.setVehicleType(vehicleTypeCodes.get(chassisNoMap.get(vahanEntity.getChassisNumber())));
            vehicleDetails.setBodyType(vahanEntity.getBodyTypeDesc());
            vehicleDetails.setWheelBase(String.valueOf(vahanEntity.getWheelbase()));
            vehicleDetails.setFuel(vahanEntity.getFuelUsed());
            vehicleDetails.setVehicleCC(String.valueOf(vahanEntity.getCubicCap()));
            vehicleDetails.setCylenders(String.valueOf(vahanEntity.getNoCyl()));
            vehicleDetails.setSeatingCapacity(String.valueOf(vahanEntity.getSeatingCapacity()));
            vehicleDetails.setDriverSeatingCapacity(null);
            vehicleDetails.setStandingCapacity(String.valueOf(vahanEntity.getStandCapacity()));
            vehicleDetails.setColor(vahanEntity.getColor());
            vehicleDetails.setVehicleIsNewOld("N");
            vehicleDetails.setManufactureMonthYear(vde.getMfgDate());
            vehicleDetails.setHorsePower(String.valueOf(vahanEntity.getEnginePower()));
            vehicleDetails.setUnleadenWeight(String.valueOf(vahanEntity.getUlw()));
            vehicleDetails.setGrowssWeightCertificate(null);
            vehicleDetails.setGrowssWeightRegistrationTime(null);
            vehicleDetails.setFronAxelTyreSizes(String.valueOf(vahanEntity.getFrontAxleWeight()));
            vehicleDetails.setRearAxelTyreSizes(String.valueOf(vahanEntity.getRearAxleWeight()));
            vehicleDetails.setOtherAxelTyreSizes(null);
            vehicleDetails.setTandomAxelTyreSizes(String.valueOf(vahanEntity.getTandemAxelDescp()));
            vehicleDetails.setFronWeight(String.valueOf(vahanEntity.getFrontAxleWeight()));
            vehicleDetails.setRearWeight(String.valueOf(vahanEntity.getRearAxleWeight()));
            vehicleDetails.setOtherAxelWeight(null);
            vehicleDetails.setTandomAxelWeight(String.valueOf(vahanEntity.getTandemAxelWeight()));
            vehicleDetails.setAxelType(null);
            vehicleDetails.setLength(String.valueOf(vahanEntity.getLength()));
            vehicleDetails.setWidth(String.valueOf(vahanEntity.getWidth()));
            vehicleDetails.setHeight(String.valueOf(vahanEntity.getHeight()));
            vehicleDetails.setHangingCapacity(null);
            vehicleDetailsMap.put(vehicleRcId, vehicleDetails);
        }
        logger.debug(":::::::::getVehicleDetails::::::::::start::::::");
        return vehicleDetailsMap;
    }

    private Map<Long, String> getVehicleTypeCodes(List<Long> vehicleRcIds) {
        logger.debug(":::::::::getVehicleTypeCodes::::::::::start::::::");
        Map<Long, String> vehicleTypeCodes = new HashMap<Long, String>();
        try {
            List<VehicleDetailsEntity> vehicleDetailsEntities = vehicleDetailsDAO.getByVehicleRcIds(vehicleRcIds);
            for (VehicleDetailsEntity vde : vehicleDetailsEntities) {
                vehicleTypeCodes.put(vde.getVehicleRcId().getVehicleRcId(), vde.getVehicleSubClass());
            }
        } catch (Exception ex) {
            logger.error(":::::::::getVehicleTypeCodes::::::::::Exception::::::" + ex.getMessage());
            logger.debug(ex);
        }
        logger.debug(":::::::::getVehicleTypeCodes::::::::::end::::::");
        return vehicleTypeCodes;
    }

    private Map<String, String> getVehicleClassDescription(Set<String> vehicleTypeCodes) {
        logger.debug(":::::::::getVehicleClassDescription::::::::::start::::::");
        Map<String, String> vehicleTypeDescript = new HashMap<String, String>();
        try {
            List<VehicleClassDescriptionEntity> vehicleClassDescriptionEntities =
                    vehicleClassDescDAO.getByVehiclClassDescriptoin(vehicleTypeCodes);
            for (VehicleClassDescriptionEntity vcd : vehicleClassDescriptionEntities) {
                vehicleTypeDescript.put(vcd.getCode(), vcd.getDescription());
            }
        } catch (Exception ex) {
            logger.error(":::::::::getVehicleClassDescription::::::::::Exception::::::" + ex.getMessage());
            logger.debug(ex);
        }
        logger.debug(":::::::::getVehicleClassDescription::::::::::end::::::");
        return vehicleTypeDescript;
    }

    private Map<Long, TaxDetails> getTaxDetails(List<VehicleRCEntity> vehicleRCEntities) {
        logger.debug(":::::::::getTaxDetails::::::::::start::::::");
        Map<Long, TaxDetails> taxDetailsMap = new HashMap<Long, TaxDetails>();
        TaxDetails taxDetails = null;
        try {
        	List<VehicleCurrentTaxEntity> currentTaxEntities = vehicleCurrentTaxDAO.getByVehicleRcEntities(vehicleRCEntities);
            for (VehicleCurrentTaxEntity currentTaxEntity : currentTaxEntities) {
                taxDetails = new TaxDetails();
                
                taxDetails.setTaxCollectedAmount(String.valueOf(currentTaxEntity.getTaxAmt()));
                taxDetails.setTaxDemandAmount(String.valueOf(currentTaxEntity.getTaxAmt()));
                taxDetails.setTaxDemandDate(DateUtil.extractDateAsString(currentTaxEntity.getCreatedOn()));
                taxDetails.setTaxExemption(String.valueOf(currentTaxEntity.getExemptionFlag()));
                taxDetails.setTaxPaidOfficeCode(currentTaxEntity.getVehicleRc().getRtaOfficeId().getCode());
                taxDetails.setTaxPaidStateCode(null);
                taxDetails.setTaxPaymentPeriod(null);
                taxDetails.setTaxPenaltyAmount("");;
                taxDetails.setTaxQuarterStartDate(DateUtil.extractDateAsString(currentTaxEntity.getTaxValidFrom()));
                taxDetails.setTaxValidUpto(DateUtil.extractDateAsString(currentTaxEntity.getTaxValidUpto()));
                taxDetailsMap.put(currentTaxEntity.getVehicleRc().getVehicleRcId(), taxDetails);
            }
        } catch (Exception ex) {
            logger.error(":::::::::getTaxDetails::::::::::end::::::" + ex.getMessage());
            logger.debug(ex);
        }
        logger.debug(":::::::::getTaxDetails::::::::::end::::::");
        return taxDetailsMap;
    }

    private Map<Long, FinancerDetails> getFinancerDetails(List<VehicleRCEntity> vehicleRCEntities) {
        logger.debug(":::::::::getFinancerDetails::::::::::start::::::");
        Map<Long, FinancerDetails> financerDetailsMap = new HashMap<Long, FinancerDetails>();
        List<FinanceDetailsEntity> financeDetailsEntities = null;
        FinancerDetails financerDetails = null;
        try {
            financeDetailsEntities = financeDetailsDAO.getFinanceDetailsByVehicleRCEntities(vehicleRCEntities);
            for (FinanceDetailsEntity financeDetailsEntity : financeDetailsEntities) {
                financerDetails = new FinancerDetails();
                financerDetails.setFinancerName(financeDetailsEntity.getName());
                financerDetails.setFinanceAggrementDate(
                        DateUtil.extractDateAsString(financeDetailsEntity.getDateOfAgreement()));
                financerDetails.setFinancerAddress1(null);
                financerDetails.setFinancerAddress2(null);
                financerDetails.setFinancerAddress3(null);
                financerDetails.setFinancerCity(financeDetailsEntity.getCity());
                financerDetails.setFinancerState(financeDetailsEntity.getStateEntity().getName());
                financerDetails.setFinancerPin(null);
                financerDetails.setFinancerFaxNo(null);
                financerDetails.setFinancerPhoneNO(null);
                financerDetails.setFinancerEmailId(null);
                financerDetails.setHypothecationType(null);
                financerDetailsMap.put(financeDetailsEntity.getVehicleRcId().getVehicleRcId(), financerDetails);
            }

        } catch (Exception ex) {
            logger.error(":::::::::getFinancerDetails::::::::::start::::::" + ex.getMessage());
            logger.debug(ex);
        }
        logger.debug(":::::::::getFinancerDetails::::::::::end::::::");
        return financerDetailsMap;
    }

    private Map<Long, RegistrationFeeDetails> getRegistrationFeeDetails(List<VehicleRCEntity> vehicleRCEntities) {
        logger.debug(":::::::::getRegistrationFeeDetails::::::::::start::::::");
        Map<Long, RegistrationFeeDetails> registrationFeeDetailsMap = new HashMap<Long, RegistrationFeeDetails>();
        List<RegFeeDetailsEntity> regFeeDetailEntities = null;
        RegistrationFeeDetails registrationFeeDetails = null;
        try {
            regFeeDetailEntities = regFeeDetailDAO.getByVehicleRCEntities(vehicleRCEntities);
            for (RegFeeDetailsEntity regFeeDetailEntity : regFeeDetailEntities) {
                registrationFeeDetails = new RegistrationFeeDetails();
                registrationFeeDetails.setRegistrationFees(String.valueOf(regFeeDetailEntity.getPrFee()));
                registrationFeeDetails.setPostalCharge(String.valueOf(regFeeDetailEntity.getPostalCharge()));
                registrationFeeDetails.setServiceCharge(String.valueOf(regFeeDetailEntity.getPrServiceCharge()));
                registrationFeeDetails.setSmartCardFees(String.valueOf(regFeeDetailEntity.getSmartCardFee()));
                registrationFeeDetailsMap.put(regFeeDetailEntity.getVehicleRc().getVehicleRcId(),
                        registrationFeeDetails);
            }
        } catch (Exception ex) {
            logger.error(":::::::::getRegistrationFeeDetails::::::::::start::::::" + ex.getMessage());
            logger.debug(ex);
        }
        logger.debug(":::::::::getRegistrationFeeDetails::::::::::end::::::");
        return registrationFeeDetailsMap;
    }

    private Map<Long, List<AttachmentDetailsModel>> getAttachmentDetails(List<VehicleRCEntity> vehicleRCEntities) {
        logger.debug(":::::::::getAttachmentDetails::::::::::start::::::");
        Map<Long, List<AttachmentDetailsModel>> attachedMap = new HashMap<Long, List<AttachmentDetailsModel>>();
        List<AttachmentDetailsModel> models = null;
        AttachmentDetailsModel attachmentDetailsModel = null;
        try {
            List<AttachmentDetailsEntity> entities = attachmentDetailsDAO.getAttachmentDetails(vehicleRCEntities);
            for (AttachmentDetailsEntity entity : entities) {
                if (entity.getDocTypes().getDocTypeId() != DocTypes.MODEL_NAME.getValue()) {
                    attachmentDetailsModel = new AttachmentDetailsModel();
                    attachmentDetailsModel.setSource(entity.getSource());
                    attachmentDetailsModel.setAttachmentTitle(entity.getDocTypes().getName());
                    if (attachedMap.containsKey(entity.getVehicle().getVehicleRcId())) {
                        models = attachedMap.get(entity.getVehicle().getVehicleRcId());
                        models.add(attachmentDetailsModel);
                        attachedMap.put(entity.getVehicle().getVehicleRcId(), models);
                    } else {
                        models = new ArrayList<AttachmentDetailsModel>();
                        models.add(attachmentDetailsModel);
                        attachedMap.put(entity.getVehicle().getVehicleRcId(), models);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(":::::::::getAttachmentDetails::::::::::start::::::" + ex.getMessage());
            logger.debug(ex);
        }
        logger.debug(":::::::::getRegistrationFeeDetails::::::::::end::::::");
        return attachedMap;
    }

    @Transactional
    @Override
    public FormsModel getVehicleDetails(Long vehicleRcId) {
        logger.debug(":::::::::getVehicleDetails::::::::::start::::::");
        VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);

        if (ObjectsUtil.isNull(vehicleRCEntity)) {
            logger.error(":::::::::getVehicleDetails::::::::::Vehicle Details Not Found::::::");
            throw new IllegalArgumentException("Vehicle Details Not Found .....!! ");
        }
        FormsModel formsModel = new FormsModel();
        formsModel.setRtaOfficeDetails(getRtaOfficeDetails(vehicleRCEntity.getRtaOfficeId()));
        formsModel.setOwnerDetails(getOwnerDetails(vehicleRCEntity));
        formsModel.setDealerDetails(getDealerDetails(vehicleRCEntity));
        formsModel.setInsuranceDetails(getInsuranceDetails(vehicleRcId));
        formsModel.setVehicleDetails(getVehicleDetails(vehicleRCEntity));
        formsModel.setInvoiceDate(getInvoiceDate(vehicleRcId));
        formsModel.setFinancerDetails(getFinancerDetails(vehicleRcId));
        formsModel.setSignatureDetails(getSignature(vehicleRcId));
        formsModel.setMakersName(makersName);
        formsModel.setMakersClass(makersClass);
        formsModel.setRlw(rlw);
        formsModel.setRegistrationType(registrationType);
        formsModel.setOwnerDateOfBirth(ownerDateOfBirth);
        if (isIndivCustomer) {
            formsModel.setOwnerPermAddDetails(getOwnerPermAddressDetails(customerId));
        } else {
            formsModel.setOwnerPermAddDetails(null);
        }
        logger.debug(":::::::::getVehicleDetails::::::::::end::::::");
        return formsModel;
    }

    private RtaOfficeDetails getRtaOfficeDetails(RtaOfficeEntity rtaOfficeEntity) {
        RtaOfficeDetails rtaOfficeDetails = new RtaOfficeDetails();
        rtaOfficeDetails.setRtaOfficeName(rtaOfficeEntity.getName());
        return rtaOfficeDetails;
    }
    
    @Transactional
    @SuppressWarnings("incomplete-switch")
    @Override
    public OwnerDetails getOwnerDetails(VehicleRCEntity vehicleRCEntity) {
        logger.info(":::::::::getOwnerDetails::::::::::start::::::");
        OwnerDetails ownerDetails = new OwnerDetails();
        AddressEntity addressEntity = new AddressEntity();
        switch (OwnershipType.getOwnershipType(vehicleRCEntity.getOwnershipType())) {

            case INDIVIDUAL:
                CustIndividualDetailsEntity custIndividualDetailsEntity =
                        customerDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                addressEntity =
                        customerDAO.getAddressDetailsByUserId(custIndividualDetailsEntity.getCustIndDtlId(), "T");
                ownerDetails = getCustomerIndiDetailsForForms(addressEntity, custIndividualDetailsEntity, ownerDetails);
                break;
            case POLICE:
            case COMPANY:
            case STU_VEHICLES:
            case ORGANIZATION:
                CustCorporateDetailsEntity custCorporateDetailsEntity =
                        customerCorporateDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                addressEntity = customerCorporateDAO
                        .getAddressDetailsByUserId(custCorporateDetailsEntity.getCustCorpDtlsId(), "T");
                ownerDetails = getCustomerCorpDetailsForForms(addressEntity, custCorporateDetailsEntity, ownerDetails);
                break;
        }
        logger.debug(":::::::::getOwnerDetails::::::::::end::::::");
        return ownerDetails;
    }

    private DealerDetails getDealerDetails(VehicleRCEntity vehicleRCEntity) {

        logger.debug(":::::::::getDealerDetails::::::::::start::::::");

        DealerDetails dealerDetails = new DealerDetails();
        AddressEntity address = null;
        String dealerName = vehicleRCEntity.getUserId().getFirstName();
        if (!StringsUtil.isNullOrEmpty(vehicleRCEntity.getUserId().getLastName())) {
            dealerName = dealerName + " " + vehicleRCEntity.getUserId().getLastName();
        }
        dealerDetails.setDealerName(dealerName);
        try {
            address = addressDao.findByUserIdAndType(vehicleRCEntity.getUserId().getUserId(), "T");
            dealerDetails.setDealerAddress1(address.getDoorNo() + ", " + address.getStreet());
            dealerDetails.setDealerAddress2(address.getMandal().getName());
            dealerDetails.setDealerAddress3(address.getMandal().getDistrictEntity().getName());
            dealerDetails.setDealerCity(address.getCity());
            dealerDetails.setDealerState(address.getMandal().getDistrictEntity().getStateEntity().getName());
            dealerDetails.setDealerAddressPinCode(address.getPinCode());
        } catch (Exception ex) {
            logger.error(":::::::::getDealerDetails::::::::::Exception::::::" + ex.getMessage());
            logger.debug(ex);
        }
        logger.debug(":::::::::getDealerDetails::::::::::end::::::");
        return dealerDetails;
    }

    private InsuranceDetails getInsuranceDetails(Long vehicleRcId) {

        logger.debug(":::::::::getInsuranceDetails::::::::::start::::::");
        InsuranceDetailsEntity insuranceDetailsEntity = null;
        InsuranceDetails insuranceDetails = new InsuranceDetails();
        try {
            insuranceDetailsEntity = insuranceDetailsDAO.getInsuranceDtlsEntityByVehicleRcId(vehicleRcId);
            insuranceDetails.setInsuranceCompanyName(insuranceDetailsEntity.getProvider());
            insuranceDetails.setInsuranceNo(insuranceDetailsEntity.getPolicyNo());
            insuranceDetails.setInsuranceValidFrom(DateUtil.extractDateAsString(insuranceDetailsEntity.getStartDate()));
            insuranceDetails.setInsuranceValidTo(DateUtil.extractDateAsString(insuranceDetailsEntity.getEndDate()));
        } catch (Exception ex) {
            logger.error(":::::::::getInsuranceDetails::::::::::Exception::::::" + ex.getMessage());
            logger.debug(ex);
        }
        logger.debug(":::::::::getInsuranceDetails::::::::::end::::::");
        return insuranceDetails;
    }

    private VehicleDetails getVehicleDetails(VehicleRCEntity vehicleRCEntity) {

        logger.info(":::::::::getVehicleDetails::::::::::start::::::");
        VahanEntity vahanEntity = null;
        try {
            vahanEntity = vahanDAO.getByChassisNumber(vehicleRCEntity.getChassisNumber());
        } catch (Exception ex) {
            logger.error(":::::::::getVehicleDetails::::::::::Exception::::::" + ex.getMessage());
            logger.debug("Exception : ", ex);
        }
        VehicleDetailsEntity vde = vehicleDetailsDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
        makersName = vahanEntity.getMakersDesc();
        makersClass = vahanEntity.getMakersClass();
        rlw = String.valueOf(vahanEntity.getRlw());
        VehicleDetails vehicleDetails  = new VehicleDetails();
        vehicleDetails.setMakerClass(vahanEntity.getMakersClass());
        vehicleDetails.setMakerName(vahanEntity.getMakersDesc());
        vehicleDetails.setVehicleClass(vde.getVehicleSubClass());
        vehicleDetails.setChassisNo(vahanEntity.getChassisNumber());
        vehicleDetails.setEngineNo(vahanEntity.getEngineNumber());
        vehicleDetails.setVehicleType(vde.getVehicleSubClass());
        vehicleDetails.setBodyType(vahanEntity.getBodyTypeDesc());
        vehicleDetails.setWheelBase(String.valueOf(vahanEntity.getWheelbase()));
        vehicleDetails.setFuel(vahanEntity.getFuelUsed());
        vehicleDetails.setVehicleCC(String.valueOf(vahanEntity.getCubicCap()));
        vehicleDetails.setCylenders(String.valueOf(vahanEntity.getNoCyl()));
        vehicleDetails.setSeatingCapacity(String.valueOf(vahanEntity.getSeatingCapacity()));
        vehicleDetails.setDriverSeatingCapacity(null);
        vehicleDetails.setStandingCapacity(String.valueOf(vahanEntity.getStandCapacity()));
        vehicleDetails.setColor(vahanEntity.getColor());
        vehicleDetails.setVehicleIsNewOld("N");
        vehicleDetails.setManufactureMonthYear(vde.getMfgDate());
        vehicleDetails.setHorsePower(String.valueOf(vahanEntity.getEnginePower()));
        vehicleDetails.setUnleadenWeight(String.valueOf(vahanEntity.getUlw()));
        vehicleDetails.setGrowssWeightCertificate(null);
        vehicleDetails.setGrowssWeightRegistrationTime(null);
        vehicleDetails.setFronAxelTyreSizes(String.valueOf(vahanEntity.getFrontAxleWeight()));
        vehicleDetails.setRearAxelTyreSizes(String.valueOf(vahanEntity.getRearAxleWeight()));
        vehicleDetails.setOtherAxelTyreSizes(null);
        vehicleDetails.setTandomAxelTyreSizes(String.valueOf(vahanEntity.getTandemAxelDescp()));
        vehicleDetails.setFronWeight(String.valueOf(vahanEntity.getFrontAxleWeight()));
        vehicleDetails.setRearWeight(String.valueOf(vahanEntity.getRearAxleWeight()));
        vehicleDetails.setOtherAxelWeight(null);
        vehicleDetails.setTandomAxelWeight(String.valueOf(vahanEntity.getTandemAxelWeight()));
        vehicleDetails.setAxelType(null);
        vehicleDetails.setLength(String.valueOf(vahanEntity.getLength()));
        vehicleDetails.setWidth(String.valueOf(vahanEntity.getWidth()));
        vehicleDetails.setHeight(String.valueOf(vahanEntity.getHeight()));
        vehicleDetails.setHangingCapacity(null);
        logger.debug(":::::::::getVehicleDetails::::::::::start::::::");
        return vehicleDetails;
    }

    private FinancerDetails getFinancerDetails(Long vehicleRcId) {
        logger.debug(":::::::::getFinancerDetails::::::::::start::::::");
        FinanceDetailsEntity financeDetailsEntity = null;
        FinancerDetails financerDetails = new FinancerDetails();
        try {
            financeDetailsEntity = financeDetailsDAO.getFinanceDetailsOfVehicleRcd(vehicleRcId);
            if (null != financeDetailsEntity) {
                financerDetails.setFinancerName(financeDetailsEntity.getName());
                financerDetails
                        .setFinanceAggrementDate(DateUtil.extractDateAsString(financeDetailsEntity.getDateOfAgreement()));
                financerDetails.setFinancerCity(financeDetailsEntity.getCity());
                financerDetails.setFinancerState(financeDetailsEntity.getStateEntity().getName());
            } else {
              VehicleFinanceDtlstEntity vehicleOnlineDetails = vehicleOnlineFinanceDAO.getVehicleFinanceRcr(vehicleRcId, Status.APPROVED.getValue());
              if (!ObjectsUtil.isNull(vehicleOnlineDetails)) {
                  UserEntity user = UserDAO.findByUserId(vehicleOnlineDetails.getFinancerId());
                  if(!ObjectsUtil.isNull(user)) {
                      financerDetails.setFinancerName(user.getInstitutionName());
                      AddressEntity address = addressDao.findByUserIdAndType(user.getUserId(), "T");
                      if(!ObjectsUtil.isNull(address)) {
                          financerDetails.setFinancerCity(address.getCity());
                          if (null != address.getState()) {
                              StateEntity state = stateDAO.getEntity(StateEntity.class, address.getState());
                              financerDetails.setFinancerState(state != null ? state.getName() : "");
                          }
                      }
                      if(null != vehicleOnlineDetails.getDateOfAgreement()){
                          financerDetails.setFinanceAggrementDate(DateUtil.extractDateAsString(vehicleOnlineDetails.getDateOfAgreement()));
                      }
                  }
              }
            }
            /*financerDetails.setFinancerAddress1(null);
            financerDetails.setFinancerAddress2(null);
            financerDetails.setFinancerAddress3(null);
            financerDetails.setFinancerPin(null);
            financerDetails.setFinancerFaxNo(null);
            financerDetails.setFinancerPhoneNO(null);
            financerDetails.setFinancerEmailId(null);
            financerDetails.setHypothecationType(null);*/
        } catch (Exception ex) {
            logger.error(":::::::::getFinancerDetails::::::::::start::::::" + ex.getMessage());
            logger.debug(ex);
        }
        logger.debug(":::::::::getFinancerDetails::::::::::end::::::");
        return financerDetails;
    }

    private SignatureDetails getSignature(Long vehicleRcId) {
        List<Integer> docTypesIds = new ArrayList<Integer>();
        docTypesIds.add(DocTypes.DOC_USER_SIGNATURE.getValue());
        docTypesIds.add(DocTypes.DOC_DEALER_SIGNATURE.getValue());
        List<AttachmentDetailsEntity> attachDtlEntities =
                attachmentDetailsDAO.getDealerAndOwnerSign(vehicleRcId, docTypesIds);
        SignatureDetails signatureDetails = new SignatureDetails();
        if (!ObjectsUtil.isNull(attachDtlEntities)) {
            for (AttachmentDetailsEntity attachmentDetailsEntity : attachDtlEntities) {
                if (attachmentDetailsEntity.getDocTypes().getDocTypeId() == DocTypes.DOC_USER_SIGNATURE.getValue()) {
                    signatureDetails.setOwnerSignature(attachmentDetailsEntity.getSource());
                } else {
                    signatureDetails.setDealerSignature(attachmentDetailsEntity.getSource());
                }
            }
        }
        return signatureDetails;
    }

    private OwnerDetails getCustomerIndiDetailsForForms(AddressEntity addressEntity,
            CustIndividualDetailsEntity custIndiDtl,
            OwnerDetails ownerDetails) {
        logger.debug(":::::::::getCustomerIndiDetailsForForms::::::::::start::::::");
        customerId = custIndiDtl.getCustIndDtlId();
        isIndivCustomer = true;
        ownerDateOfBirth = custIndiDtl.getDateOfBirth();
        ownerDetails.setOwnershipType(OwnershipType.INDIVIDUAL.getLabel());
        ownerDetails.setOwnerFirstName(custIndiDtl.getFirstName());
        ownerDetails.setOwnerMiddileName("");
        ownerDetails.setOwnerLastName(custIndiDtl.getSurName());
        ownerDetails.setOwnerFatherName(custIndiDtl.getFatherName());
        ownerDetails.setOwnerMobileNo(custIndiDtl.getMobileNo());
        ownerDetails.setOwnerPanNo(custIndiDtl.getPanNo());
        ownerDetails.setOwnerVoterId(null);
        ownerDetails.setOwnerAddress1(addressEntity.getDoorNo() + ", " + addressEntity.getStreet());
        ownerDetails.setOwnerAddress2(addressEntity.getMandal().getName());
        ownerDetails.setOwnerAddress3(addressEntity.getMandal().getDistrictEntity().getName());
        ownerDetails.setOwnerCity(addressEntity.getCity());
        ownerDetails.setOwnerState(addressEntity.getMandal().getDistrictEntity().getStateEntity().getName());
        ownerDetails.setOwnerPin(addressEntity.getPinCode());
        ownerDetails.setCountryName("India");
        if (!StringsUtil.isNullOrEmpty(custIndiDtl.getDateOfBirth())) {
            try {
                ownerDetails.setOwnerAge(DateUtil.getCurrentAge(custIndiDtl.getDateOfBirth()));
            } catch (Exception ex) {
                logger.error(":::::::::getCustomerIndiDetailsForForms::::::::::Exception::::::" + ex.getMessage());
            }
        }
        logger.debug(":::::::::getCustomerIndiDetailsForForms::::::::::end::::::");

        return ownerDetails;
    }

    private OwnerDetails getCustomerCorpDetailsForForms(AddressEntity addressEntity,
            CustCorporateDetailsEntity custCorpDtl, OwnerDetails ownerDetails) {
        logger.debug(":::::::::getCustomerCorpDetailsForForms::::::::::start::::::");
        customerId = custCorpDtl.getCustCorpDtlsId();
        isIndivCustomer = false;
        ownerDateOfBirth = null;
        if(custCorpDtl.getCompanyName().equalsIgnoreCase(OwnershipType.COMPANY.getLabel())) {
        	ownerDetails.setOwnershipType(OwnershipType.COMPANY.getLabel());
        } else if(custCorpDtl.getCompanyName().equalsIgnoreCase(OwnershipType.POLICE.getLabel())) {
            ownerDetails.setOwnershipType(OwnershipType.POLICE.getLabel());
        } else if(custCorpDtl.getCompanyName().equalsIgnoreCase(OwnershipType.STU_VEHICLES.getLabel())) {
            ownerDetails.setOwnershipType(OwnershipType.STU_VEHICLES.getLabel());
        } else if(custCorpDtl.getCompanyName().equalsIgnoreCase(OwnershipType.ORGANIZATION.getLabel())) {
            ownerDetails.setOwnershipType(OwnershipType.ORGANIZATION.getLabel());
        }
        ownerDetails.setOwnerFirstName(custCorpDtl.getCompanyName());
        ownerDetails.setOwnerMiddileName(null);
        ownerDetails.setOwnerLastName(null);
        ownerDetails.setOwnerAge(null);/// pending
        ownerDetails.setOwnerFatherName(null);
        ownerDetails.setOwnerMobileNo(custCorpDtl.getMobile());
        ownerDetails.setOwnerPanNo(custCorpDtl.getPanNo());
        ownerDetails.setOwnerVoterId(null);
        ownerDetails.setOwnerAddress1(addressEntity.getDoorNo() + ", " + addressEntity.getStreet());
        ownerDetails.setOwnerAddress2(addressEntity.getMandal().getName());
        ownerDetails.setOwnerAddress3(addressEntity.getMandal().getDistrictEntity().getName());
        ownerDetails.setOwnerCity(addressEntity.getCity());
        ownerDetails.setOwnerState(addressEntity.getMandal().getDistrictEntity().getStateEntity().getName());
        ownerDetails.setOwnerPin(addressEntity.getPinCode());
        ownerDetails.setCountryName("India");
        logger.debug(":::::::::getCustomerCorpDetailsForForms::::::::::end::::::");

        return ownerDetails;
    }

    private OwnerPermAddressDetails getOwnerPermAddressDetails(Long indivCustomerId) {
        logger.debug(":::::::::getCustomerCorpDetailsForForms::::::::::end::::::");
        OwnerPermAddressDetails ownerPermAddressDetails = new OwnerPermAddressDetails();
        try {
            PermanentAddressEntity permanentAddressEntity =
                    permanentAddressDAO.findByUserId(indivCustomerId, AddressType.AADHAR);
            ownerPermAddressDetails.setDoorNo(permanentAddressEntity.getDoorNo());
            ownerPermAddressDetails.setStreet(permanentAddressEntity.getStreet());
            ownerPermAddressDetails.setCity(permanentAddressEntity.getCity());
            ownerPermAddressDetails.setMandal(permanentAddressEntity.getMandal());
            ownerPermAddressDetails.setDistrict(permanentAddressEntity.getDistrict());
            ownerPermAddressDetails.setState(permanentAddressEntity.getState());
            ownerPermAddressDetails.setPincode(permanentAddressEntity.getPinCode());
            if(ObjectsUtil.isNull(permanentAddressEntity.getCountry()) || permanentAddressEntity.getCountry().trim().equals("")){
                ownerPermAddressDetails.setCountry("India");
            } else {
                ownerPermAddressDetails.setCountry(permanentAddressEntity.getCountry());
            }
        } catch (Exception ex) {
            logger.error(":::::::::getCustomerCorpDetailsForForms::::::::::Exception::::::" + ex.getMessage());
            logger.debug(ex);
        }

        return ownerPermAddressDetails;
    }

    private Long getInvoiceDate(Long vehicleRcId) {
        logger.debug(":::::::::getInvoiceDate::::::::::start::::::");
        Long invoiceDate=null;
        try{
            invoiceDate =  vehicleBillingDetailsDAO.getByVehicleDetailId(vehicleDetailsDAO.getByVehicleRcId(vehicleRcId)).getInvoiceDate();
        } catch (Exception ex) {
            logger.error(":::::::::getInvoiceDate::::::::::Exception::::::" + ex.getMessage());
            logger.debug(ex);
        }
        logger.debug(":::::::::getInvoiceDate::::::::::end::::::");
        return invoiceDate;
    }

    @Transactional
    @Override
    public SinkAllData getVehicleDetailsInfo(Long vehicleRcId, String trOrPrNumber) {

        logger.debug(":::::::::getVehicleDetailsInfo::::::::::start::::::");
        VehicleRCEntity vehicleRCEntity = null;
        if(!ObjectsUtil.isNull(vehicleRcId)){
        	vehicleRCEntity = vehicleDAO.get(vehicleRcId);
        }else if(!StringsUtil.isNullOrEmpty(trOrPrNumber)) {
        	vehicleRCEntity = vehicleDAO.getByPROrTrNumber(trOrPrNumber);
        }
        if (ObjectsUtil.isNull(vehicleRCEntity)) {
            logger.error(":::::::::getVehicleDetailsInfo::::::::::Vehicle Details Not Found::::::");
            throw new IllegalArgumentException("Vehicle Details Not Found .....!! ");
        }
        SinkAllData cmsSyncingData = new SinkAllData();
        cmsSyncingData.setApplication(String.valueOf(vehicleRcId));
        cmsSyncingData.setOwnerDetails(getOwnerDetailsSyncCMS(vehicleRCEntity));
        cmsSyncingData.setVehicleRegistrationDetails(getVehicleRegistrationDetailsSyncCMS(vehicleRCEntity));
        cmsSyncingData.setDealerDetails(getDealerDetailsSyncCMS(vehicleRCEntity));
        cmsSyncingData.setInsuranceDetails(getInsuranceDetails(vehicleRcId));
        cmsSyncingData.setVehicleDetails(getVehicleDetails(vehicleRCEntity));
        cmsSyncingData.setTaxDetails(getTaxDetailsSyncCMS(vehicleRcId));
        cmsSyncingData.setFinancerDetails(getFinancerDetails(vehicleRcId));
        cmsSyncingData.setFitnessDetails(getFitnessDetails(vehicleRCEntity));
        cmsSyncingData.setPermitDetails(getPermitDetails(vehicleRCEntity));
        logger.debug(":::::::::getAllVehicleDetailsBetweenADay::::::::::end::::::");

        return cmsSyncingData;
    }

    @SuppressWarnings("incomplete-switch")
    private OwnerDetails getOwnerDetailsSyncCMS(VehicleRCEntity vehicleRCEntity) {
        logger.debug(":::::::::getOwnerDetailsSyncCMS::::::::::start::::::");
        OwnerDetails ownerDetails = new OwnerDetails();
        AddressEntity addressEntity = new AddressEntity();
        switch (OwnershipType.getOwnershipType(vehicleRCEntity.getOwnershipType())) {

            case INDIVIDUAL:
                CustIndividualDetailsEntity custIndividualDetailsEntity =
                        customerDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                addressEntity =
                        customerDAO.getAddressDetailsByUserId(custIndividualDetailsEntity.getCustIndDtlId(), "T");
                ownerDetails = getCustomerIndiDetails(addressEntity, custIndividualDetailsEntity, ownerDetails);
                break;
            case COMPANY:
                CustCorporateDetailsEntity custCorporateDetailsEntity =
                        customerCorporateDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                addressEntity = customerCorporateDAO
                        .getAddressDetailsByUserId(custCorporateDetailsEntity.getCustCorpDtlsId(), "T");
                ownerDetails = getCustomerCorpDetails(addressEntity, custCorporateDetailsEntity, ownerDetails);
                break;
        }

        logger.debug(":::::::::getOwnerDetailsSyncCMS::::::::::end::::::");
        return ownerDetails;
    }

    private VehicleRegistrationDetails getVehicleRegistrationDetailsSyncCMS(VehicleRCEntity vehicleRCEntity) {
        logger.debug(":::::::::getVehicleRegistrationDetailsSyncCMS::::::::::start::::::");

        VehicleRegistrationDetails vehicleRegistrationDetails  = new VehicleRegistrationDetails();
        vehicleRegistrationDetails.setVehicleNumber(vehicleRCEntity.getPrNumber());
        vehicleRegistrationDetails
                .setVehicleRegisteredDate(DateUtil.extractDateAsString(vehicleRCEntity.getPrIssueTime()));
        vehicleRegistrationDetails.setVehicleRegistrationValidUpto(
                DateUtil.extractDateAsString(DateUtil.addYears(vehicleRCEntity.getPrIssueTime(), prValidty)));
        vehicleRegistrationDetails.setVehicleIssuePlace(null);
        vehicleRegistrationDetails.setVehicleStatus("A");
        vehicleRegistrationDetails.setRemarks(null);
        vehicleRegistrationDetails.setVehicleOldNo(vehicleRCEntity.getTrNumber());
        vehicleRegistrationDetails.setPreviousRegisteredOfficeName(null);
        vehicleRegistrationDetails.setPreviousRegisteredOfficeState(null);
        vehicleRegistrationDetails.setGovernmentVehicle("N");
        vehicleRegistrationDetails.setVehicleIsRTC("N");
        vehicleRegistrationDetails.setNocOfficeCode(vehicleRCEntity.getRtaOfficeId().getCode());
        logger.debug(":::::::::getVehicleRegistrationDetailsSyncCMS::::::::::end::::::");
        return vehicleRegistrationDetails;
    }

    private FitnessDetails getFitnessDetails(VehicleRCEntity vehicleRCEntity) {
        logger.debug(":::::::::getFitnessDetails::::::::::start::::::");

        FitnessDetails fitnessDetails  = new FitnessDetails();
        FitnessCertificateEntity entity = fcDetailsDAO.getFcDetails(vehicleRCEntity.getVehicleRcId(), "V");
        if(!ObjectsUtil.isNull(entity)){

        	fitnessDetails.setFcNumber(entity.getFcNo());
        	fitnessDetails.setFcIssuedBy(vehicleRCEntity.getRtaOfficeId().getCode());
        	fitnessDetails.setFcIssuedDate(DateUtil.extractDateAsString(entity.getIssueDate()));
        	fitnessDetails.setFcValidFromDate(DateUtil.extractDateAsString(entity.getIssueDate()));
        	fitnessDetails.setFcValidToDate(DateUtil.extractDateAsString(entity.getExpiryDate()));
        	fitnessDetails.setFcApprovedBy(entity.getApprovedbyMVI());
        	fitnessDetails.setFcChallanNo("NA");
        	try{
        		UserEntity userEntity = UserDAO.findByUserId(Long.valueOf(entity.getApprovedbyMVI()));
        		fitnessDetails.setFcApprovedBy(userEntity.getFirstName()+" "+ userEntity.getLastName());
        	}catch (Exception e) {
        		if(!ObjectsUtil.isNull(vehicleRCEntity.getMviUserId())){
        			fitnessDetails.setFcApprovedBy(vehicleRCEntity.getMviUserId().getFirstName()+" "+ vehicleRCEntity.getMviUserId().getLastName());
        		}
        		logger.error(":::::::::getting error in user details :::::: "+e.getMessage());
			}
        }else{
        	fitnessDetails.setFcNumber(null);
        	fitnessDetails.setFcIssuedBy(null);
        	fitnessDetails.setFcIssuedDate(null);
        	fitnessDetails.setFcValidFromDate(null);
        	fitnessDetails.setFcValidToDate(null);
        	fitnessDetails.setFcApprovedBy(null);
        	fitnessDetails.setFcChallanNo(null);
        }
        
        logger.debug(":::::::::getFitnessDetails::::::::::end::::::");
        return fitnessDetails;
    }
    
    private PermitDetails getPermitDetails(VehicleRCEntity vehicleRCEntity) {
        logger.debug(":::::::::getPermitDetails::::::::::start::::::");

        PermitDetails permitDetails  = new PermitDetails();
        PermitHeaderEntity entity = permitHeaderDAO.getPrimaryPermitActiveByPr(vehicleRCEntity.getPrNumber());
        if(!ObjectsUtil.isNull(entity) ){
				permitDetails.setPermitNo(entity.getPermitNo());
				permitDetails.setPermitClass(PermitClassType.PUKKA.getLabel());
				permitDetails.setPermitType(entity.getPermitType());
				permitDetails.setPermitIssueDate(DateUtil.extractDateAsString(entity.getIssueDate()));
				permitDetails.setPermitValidFromDate(DateUtil.extractDateAsString(entity.getValidFromDate()));
				permitDetails.setPermitValidToDate(DateUtil.extractDateAsString(entity.getValidToDate()));
				permitDetails.setTransactionDate(DateUtil.extractDateAsString(entity.getCreatedOn()));
				PermitAuthCardDetailsEntity peCardDetailsEntity = permitAuthCardDAO.getActiveAuthCard(entity.getPermitNo());
				if(ObjectsUtil.isNotNull(peCardDetailsEntity)){
        			permitDetails.setAuthorizationNo(peCardDetailsEntity.getAuthCardId());
	        		permitDetails.setAuthFromDate(DateUtil.extractDateAsString(peCardDetailsEntity.getAuthCardVfdt()));
	        		permitDetails.setAuthToDate(DateUtil.extractDateAsString(peCardDetailsEntity.getAuthCardVtdt()));
				}
				PermitDetailsMappingEntity routEntityMapping = permitDetailsMappingDAO.getActiveDetails(entity.getPermitNo(), PermitDetailsType.ROUTE.getLabel());
		        if (!ObjectsUtil.isNull(routEntityMapping)) {
		            PermitRouteConditionsMasterEntity routEntity = permitRouteDAO.getPermitRoute(routEntityMapping.getDetailValue());
		            if (ObjectsUtil.isNotNull(routEntity)) {
		            	permitDetails.setRouteDescription(routEntity.getPerRouteDesc());
		            }
		        }
		        PermitDetailsMappingEntity goodsEntityMapping = permitDetailsMappingDAO.getActiveDetails(entity.getPermitNo(), PermitDetailsType.GOODS.getLabel());
		        if (!ObjectsUtil.isNull(goodsEntityMapping)) {
		            PermitGoodsMasterEntity goodsEntity = permitGoodsDAO.getPermitGood(goodsEntityMapping.getDetailValue());
		            if (ObjectsUtil.isNotNull(goodsEntity)) {
		            	permitDetails.setGoodsDescription(goodsEntity.getPerGoodsDesc());
		            }
		        }
		        List<PermitDetailsMappingEntity> neighbouringDistricts = permitDetailsMappingDAO.getActiveDetailList(entity.getPermitNo(), PermitDetailsType.DISTRICT.getLabel());
		        if (!ObjectsUtil.isNullOrEmpty(neighbouringDistricts)) {
		            String districtList = "";
		            int count = 0;
		            for(PermitDetailsMappingEntity mapping : neighbouringDistricts){
		                DistrictEntity district = districtDAO.getDistrictByCode(mapping.getDetailValue());
		                districtList = districtList + " "+ district.getName();
		                count++;
		            }
		            if(count < 2){
		            	permitDetails.setPermitRouteType("O");
		            	permitDetails.setOneDistrictPermit(districtList);
		            }else{
		            	permitDetails.setPermitRouteType("T");
		            	permitDetails.setTwoDistrictPermit(districtList);
		            }
		        }
	//        			permitDetails.setPermitLadenWeight("");
        }else{
        	permitDetails.setPermitNo(null);
			permitDetails.setPermitClass(null);
			permitDetails.setPermitType(null);
			permitDetails.setPermitIssueDate(null);
			permitDetails.setPermitValidFromDate(null);
			permitDetails.setPermitValidToDate(null);
			permitDetails.setAuthorizationNo(null);
			permitDetails.setAuthFromDate(null);
			permitDetails.setAuthToDate(null);
			permitDetails.setRouteDescription(null);
			permitDetails.setGoodsDescription(null);
			permitDetails.setPermitLadenWeight(null);
			permitDetails.setPermitRouteType(null);
			permitDetails.setOneDistrictPermit(null);
			permitDetails.setTwoDistrictPermit(null);
			permitDetails.setTransactionDate(null);
        }
        logger.debug(":::::::::getPermitDetails::::::::::end::::::");
        return permitDetails;
    }
    
    private DealerDetails getDealerDetailsSyncCMS(VehicleRCEntity vehicleRCEntity) {

        logger.debug(":::::::::getDealerDetails::::::::::start::::::");
        
        DealerDetails dealerDetails= new DealerDetails();;
        String dealerName = vehicleRCEntity.getUserId().getFirstName();
        if (!StringsUtil.isNullOrEmpty(vehicleRCEntity.getUserId().getLastName())) {
            dealerName = dealerName + " " + vehicleRCEntity.getUserId().getLastName();
        }
        dealerDetails.setDealerName(dealerName);
        try {
            AddressEntity address = addressDao.findByUserIdAndType(vehicleRCEntity.getUserId().getUserId(), "T");
            dealerDetails.setDealerAddress1(address.getDoorNo());
            dealerDetails.setDealerAddress2(address.getStreet());
            dealerDetails.setDealerAddress3(null);
            dealerDetails.setDealerCity(address.getCity());
            dealerDetails.setDealerState(address.getMandal().getDistrictEntity().getStateEntity().getName());
            dealerDetails.setDealerAddressPinCode(address.getPinCode());
        } catch (Exception ex) {
            logger.error(":::::::::getDealerDetails::::::::::Exception::::::" + ex.getMessage());
            logger.debug(ex);
        }
        logger.debug(":::::::::getDealerDetails::::::::::end::::::");
        return dealerDetails;
    }

    private TaxDetails getTaxDetailsSyncCMS(Long vehicleRcId) {
        logger.debug(":::::::::getTaxDetails::::::::::start::::::");

        TaxDetails taxDetails = new TaxDetails();
        try {
            VehicleCurrentTaxEntity currentTaxEntity = vehicleCurrentTaxDAO.getEntityByVehicleRcId(vehicleRcId);
            taxDetails.setTaxCollectedAmount(String.valueOf(currentTaxEntity.getTaxAmt()));
            taxDetails.setTaxDemandAmount(String.valueOf(currentTaxEntity.getTaxAmt()));
            taxDetails.setTaxDemandDate(DateUtil.extractDateAsString(currentTaxEntity.getCreatedOn()));
            taxDetails.setTaxExemption(String.valueOf(currentTaxEntity.getExemptionFlag()));
            taxDetails.setTaxPaidOfficeCode(currentTaxEntity.getVehicleRc().getRtaOfficeId().getCode());
            taxDetails.setTaxPaidStateCode(null);
            taxDetails.setTaxPaymentPeriod(null);
            taxDetails.setTaxPenaltyAmount("");;
            taxDetails.setTaxQuarterStartDate(DateUtil.extractDateAsString(currentTaxEntity.getTaxValidFrom()));
            taxDetails.setTaxValidUpto(DateUtil.extractDateAsString(currentTaxEntity.getTaxValidUpto()));

        } catch (Exception ex) {
            logger.error(":::::::::getTaxDetails::::::::::end::::::" + ex.getMessage());
            logger.debug(ex);
        }
        logger.debug(":::::::::getTaxDetails::::::::::end::::::");
        return taxDetails;
    }
    
    @Transactional
    @Override
    public List<IibExportModel> getVehicleRegistrationDetails(String keyType, String from, String to) {
        logger.info(":::::::::getVehicleRegistrationDetails::::::::::start::::::");
        List<VehicleRCEntity> vehicleRCEntities =  null;
        if(keyType.equalsIgnoreCase("PR")){
        	vehicleRCEntities = vehicleDAO.getVehicleRcByFromToDate(DateUtil.getLongDate(from, "00:00:00"), DateUtil.getLongDate(to, "23:59:59"));
        } else {
        	vehicleRCEntities = vehicleDAO.getTrDetailsVehicleRc(DateUtil.getLongDate(from, "00:00:00"), DateUtil.getLongDate(to, "23:59:59"));
        }
        if (ObjectsUtil.isNull(vehicleRCEntities) || vehicleRCEntities.size() <= 0) {
            logger.error(":::::::::getVehicleRegistrationDetails::::::::::Vehicle Details Not Found::::::");
            throw new IllegalArgumentException("Vehicle Details Not Found .....!! ");
        }
        Map<Long, VehicleDetails> vehicleDetailsMap = getVehicleDetails(vehicleRCEntities);
        Map<Long, FinancerDetails> financerDetailsMap = getFinancerDetails(vehicleRCEntities);
        List<IibExportModel> iibList = new ArrayList<IibExportModel>();
        IibExportModel iibModel=null;
        for (VehicleRCEntity vehicleRCEntity : vehicleRCEntities) {
        	iibModel=new IibExportModel();
        	VehicleDetails vehicleDetails = vehicleDetailsMap.get(vehicleRCEntity.getVehicleRcId());
        	iibModel.setRegType(keyType);
        	iibModel.setCc(vehicleDetails.getVehicleCC());
        	iibModel.setEngineNumber(vehicleDetails.getEngineNo());
        	iibModel.setChassisNumber(vehicleDetails.getChassisNo());
        	iibModel.setGvw(vehicleDetails.getUnleadenWeight());
        	iibModel.setModelName(vehicleDetails.getMakerClass());
        	iibModel.setMakerName(vehicleDetails.getMakerName());
        	iibModel.setRegNumber(vehicleRCEntity.getPrNumber());
        	iibModel.setRegEndDate(DateUtil.extractDateAsString(DateUtil.addYears(vehicleRCEntity.getPrIssueTime(), prValidty)));
        	iibModel.setRegStartDate(DateUtil.extractDateAsString(vehicleRCEntity.getPrIssueTime()));
            if (financerDetailsMap.get(vehicleRCEntity.getVehicleRcId()) == null) {
            	iibModel.setHypothecation("Y");
            } else {
            	iibModel.setHypothecation("N");
            }
            iibList.add(iibModel);
        }
        logger.info(":::::::::getVehicleRegistrationDetails::::::::::end::::::");
        return iibList;
    }

}
