package org.rta.core.service.cardprinter.impl;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.rta.core.constant.SomeConstants;
import org.rta.core.dao.cardprinter.RcCardEmployeeDAO;
import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.docs.AttachmentDetailsDAO;
import org.rta.core.dao.finance.FinanceDetailsDAO;
import org.rta.core.dao.finance.VehicleFinanceDtlsDAO;
import org.rta.core.dao.insurance.InsuranceDetailsDAO;
import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.dao.payment.tax.PeriodicTaxDAO;
import org.rta.core.dao.payment.tax.VehicleCurrentTaxDAO;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.dao.user.RTAEmployeeDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.dao.vehicle.VahanDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.dao.vehicle.cms.VehicleClassDescDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.entity.finance.FinanceDetailsEntity;
import org.rta.core.entity.finance.VehicleFinanceDtlstEntity;
import org.rta.core.entity.insurance.InsuranceDetailsEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.entity.payment.tax.VehicleCurrentTaxEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.VahanEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.DocTypes;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.enums.master.TaxType;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.cardprinter.PrintStatusModel;
import org.rta.core.model.cardprinter.RcCardDetailsModel;
import org.rta.core.model.cardprinter.RtyFromtoModel;
import org.rta.core.service.cardprinter.RcCardDetailsService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.NumberParser;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author sohan.maurya created on Sep 12, 2016.
 */
@Service("rcCardDetailsService")
public class RcCardDetailsServiceImpl implements RcCardDetailsService {

    private static final Logger logger = Logger.getLogger(RcCardDetailsServiceImpl.class);

    @Autowired
    private RtaOfficeDAO rtaOfficeDAO;

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
    private VehicleCurrentTaxDAO currentTaxDAO;
    
    @Autowired
    private PeriodicTaxDAO periodicTaxDAO;

    @Autowired
    private FinanceDetailsDAO financeDetailsDAO;

    @Autowired
    private AttachmentDetailsDAO attachmentDetailsDAO;

    @Autowired
    private RTAEmployeeDAO rtaEmployeeDAO;

    @Autowired
    private RcCardEmployeeDAO rcCardEmployeeDAO;

    @Autowired
    private VehicleDetailsDAO vehicleDetailsDAO;

    @Autowired
    private VehicleClassDescDAO vehicleClassDescDAO;
    
    @Autowired
    private VehicleFinanceDtlsDAO vehicleOnlineFinanceDAO;
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private AddressDAO addressDAO;
    
    @Value("${base.url}")
    private String baseURL;

    @Value("${attachments.downloaded.path}")
    private String attachmentDocPath;

    @Value("${rta.employee.signature.path}")
    private String signaturePath;

    @Value("${rta.pr.validty}")
    private Integer prValidty;
    
    @Value("${base.url.citizen.doc}")
    private String baseURLCitizenDoc;
    
    private static final int MAX_ADDRESS_LINE_STRING_LENGTH = 20;
    private static final int MAX_FATHERS_NAME_STRING_LENGTH = 20;

    @SuppressWarnings("incomplete-switch")
    @Transactional
    @Override
    public RcCardDetailsModel getRcCardDetails(String prNumber, String rtaOfficeCode) {

        RtaOfficeEntity rtaOfficeEntity = rtaOfficeDAO.getRtaOfficeDetailsByCode(rtaOfficeCode);
        if (ObjectsUtil.isNull(rtaOfficeEntity)) {
            throw new IllegalArgumentException("Invalid RTA Code .....!! ");
        }
        VehicleRCEntity vehicleRCEntity = vehicleDAO.getVehicleRCByPRNumber(prNumber, rtaOfficeEntity.getRtaOfficeId(), true);
        if (ObjectsUtil.isNull(vehicleRCEntity)) {
            throw new IllegalArgumentException("Invalid RCNo .....!! ");
        }
        VahanEntity vahanEntity = vahanDAO.getByChassisNumber(vehicleRCEntity.getChassisNumber());
        VehicleCurrentTaxEntity taxDetailEntity =
        		currentTaxDAO.getEntityByVehicleRcId(vehicleRCEntity.getVehicleRcId());

        AddressEntity addressEntity = new AddressEntity();
        RcCardDetailsModel rcCardDetailsModel = new RcCardDetailsModel();
        rcCardDetailsModel.setVehRegNo(vehicleRCEntity.getPrNumber());
        rcCardDetailsModel.setCategory(vehicleRCEntity.getRegCategory().getName());
        rcCardDetailsModel.setDateofRegistration(DateUtil.getDateAsString(vehicleRCEntity.getPrIssueTime()));
        rcCardDetailsModel.setRegistrationValidUpto(
                DateUtil.getDateAsString(DateUtil.addYears(vehicleRCEntity.getPrIssueTime(), prValidty)));
        rcCardDetailsModel = getRtaOfficeDetails(rcCardDetailsModel, rtaOfficeEntity);
        rcCardDetailsModel = getInsuranceDetails(rcCardDetailsModel, vehicleRCEntity.getVehicleRcId());
        rcCardDetailsModel = getVehicleDetails(rcCardDetailsModel, vahanEntity, vehicleRCEntity.getVehicleRcId());
        rcCardDetailsModel = getTaxDetails(rcCardDetailsModel, taxDetailEntity, vehicleRCEntity.getTrIssueTime());
        FinanceDetailsEntity financeDetailsEntity =
                financeDetailsDAO.getFinanceDetailsOfVehicleRcd(vehicleRCEntity.getVehicleRcId());
        if(null == financeDetailsEntity) {
            VehicleFinanceDtlstEntity vehicleOnlineDetails = vehicleOnlineFinanceDAO.getVehicleFinanceRcr(vehicleRCEntity.getVehicleRcId(), Status.APPROVED.getValue());
            rcCardDetailsModel = getOnlineFinanceDetails(rcCardDetailsModel, vehicleOnlineDetails);
        } else {
            rcCardDetailsModel = getFinanceDetails(rcCardDetailsModel, financeDetailsEntity);
        }

        switch (OwnershipType.getOwnershipType(vehicleRCEntity.getOwnershipType())) {
        	case POLICE :
        	case COMPANY:
            case STU_VEHICLES :
            case ORGANIZATION:
                CustCorporateDetailsEntity custCorporateDetailsEntity =
                        customerCorporateDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                addressEntity = customerCorporateDAO
                        .getAddressDetailsByUserId(custCorporateDetailsEntity.getCustCorpDtlsId(), "T");
                rcCardDetailsModel =
                        getCustomerCorpDetails(addressEntity, custCorporateDetailsEntity, rcCardDetailsModel);
                break;
            case INDIVIDUAL:
                CustIndividualDetailsEntity custIndividualDetailsEntity =
                        customerDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                addressEntity =
                        customerDAO.getAddressDetailsByUserId(custIndividualDetailsEntity.getCustIndDtlId(), "T");
                rcCardDetailsModel =
                        getCustomerIndiDetails(addressEntity, custIndividualDetailsEntity, rcCardDetailsModel);
                break;
        }

        rcCardDetailsModel.setOwnerSign(getOwnerSignature(vehicleRCEntity.getVehicleRcId()));
        rcCardDetailsModel.setRegisteringAuthority(getEmployeeSignature(vehicleRCEntity.getVehicleRcId()));
        if (ObjectsUtil.isNull(vehicleRCEntity.getRcPrintDate())) {
            rcCardDetailsModel.setRCPrintDate(null);
            rcCardDetailsModel.setIsPrinted("false");
        }else{
            rcCardDetailsModel.setRCPrintDate(DateUtil.getDateAsString(vehicleRCEntity.getRcPrintDate()));
            rcCardDetailsModel.setIsPrinted(String.valueOf(vehicleRCEntity.getIsPrinted()));
        }

        return rcCardDetailsModel;
    }

    @SuppressWarnings("incomplete-switch")
    @Transactional
    @Override
    public List<RcCardDetailsModel> getRcCardDetailsByDate(String date, String rtaOfficeCode) {
        logger.info(":::::::::getRcCardDetailsByDate::::::::::start::::::");
        RtaOfficeEntity rtaOfficeEntity = rtaOfficeDAO.getRtaOfficeDetailsByCode(rtaOfficeCode);
        if (ObjectsUtil.isNull(rtaOfficeEntity)) {
            throw new IllegalArgumentException("Invalid RTA Office Code  !!!");
        }
        List<VehicleRCEntity> vehicleRCEntities =
                vehicleDAO.getVehicleRcByFromToDate(DateUtil.getDateInLong(date, "00:00:00"),
                        DateUtil.getDateInLong(date, "23:59:59"), rtaOfficeEntity.getRtaOfficeId(), false, true);
        List<RcCardDetailsModel> rcCardDetailsModels = new ArrayList<RcCardDetailsModel>();

        for (VehicleRCEntity vehicleRCEntity : vehicleRCEntities) {
            logger.info(":::::::::getRcCardDetailsByDate::::::::::start in Loop::::::");
            VahanEntity vahanEntity = vahanDAO.getByChassisNumber(vehicleRCEntity.getChassisNumber());
            VehicleCurrentTaxEntity currentTaxEntity =
                    currentTaxDAO.getEntityByVehicleRcId(vehicleRCEntity.getVehicleRcId());
            FinanceDetailsEntity financeDetailsEntity =
                    financeDetailsDAO.getFinanceDetailsOfVehicleRcd(vehicleRCEntity.getVehicleRcId());

            AddressEntity addressEntity = new AddressEntity();
            RcCardDetailsModel rcCardDetailsModel = new RcCardDetailsModel();
            rcCardDetailsModel.setVehRegNo(vehicleRCEntity.getPrNumber());
            rcCardDetailsModel.setCategory(vehicleRCEntity.getRegCategory().getName());
            rcCardDetailsModel.setDateofRegistration(DateUtil.getDateAsString(vehicleRCEntity.getPrIssueTime()));
            rcCardDetailsModel.setRegistrationValidUpto(
                    DateUtil.getDateAsString(DateUtil.addYears(vehicleRCEntity.getPrIssueTime(), prValidty)));
            rcCardDetailsModel = getRtaOfficeDetails(rcCardDetailsModel, rtaOfficeEntity);
            rcCardDetailsModel = getInsuranceDetails(rcCardDetailsModel, vehicleRCEntity.getVehicleRcId());
            rcCardDetailsModel = getVehicleDetails(rcCardDetailsModel, vahanEntity, vehicleRCEntity.getVehicleRcId());
            rcCardDetailsModel = getTaxDetails(rcCardDetailsModel, currentTaxEntity, vehicleRCEntity.getTrIssueTime());
            if(null == financeDetailsEntity) {
                VehicleFinanceDtlstEntity vehicleOnlineDetails = vehicleOnlineFinanceDAO.getVehicleFinanceRcr(vehicleRCEntity.getVehicleRcId(), Status.APPROVED.getValue());
                rcCardDetailsModel = getOnlineFinanceDetails(rcCardDetailsModel, vehicleOnlineDetails);
            } else {
                rcCardDetailsModel = getFinanceDetails(rcCardDetailsModel, financeDetailsEntity);
            }

            switch (OwnershipType.getOwnershipType(vehicleRCEntity.getOwnershipType())) {
            	case POLICE :
            	case COMPANY:
            	case STU_VEHICLES :
            	case ORGANIZATION:
                    CustCorporateDetailsEntity custCorporateDetailsEntity =
                            customerCorporateDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                    addressEntity = customerCorporateDAO
                            .getAddressDetailsByUserId(custCorporateDetailsEntity.getCustCorpDtlsId(), "T");
                    rcCardDetailsModel =
                            getCustomerCorpDetails(addressEntity, custCorporateDetailsEntity, rcCardDetailsModel);
                    break;
                case INDIVIDUAL:
                    CustIndividualDetailsEntity custIndividualDetailsEntity =
                            customerDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                    addressEntity =
                            customerDAO.getAddressDetailsByUserId(custIndividualDetailsEntity.getCustIndDtlId(), "T");
                    rcCardDetailsModel =
                            getCustomerIndiDetails(addressEntity, custIndividualDetailsEntity, rcCardDetailsModel);
                    break;
            }
            rcCardDetailsModel.setOwnerSign(getOwnerSignature(vehicleRCEntity.getVehicleRcId()));
            rcCardDetailsModel.setRegisteringAuthority(getEmployeeSignature(vehicleRCEntity.getVehicleRcId()));

            if (ObjectsUtil.isNull(vehicleRCEntity.getRcPrintDate())) {
                rcCardDetailsModel.setRCPrintDate(null);
                rcCardDetailsModel.setIsPrinted("false");
            } else {
                rcCardDetailsModel.setRCPrintDate(DateUtil.getDateAsString(vehicleRCEntity.getRcPrintDate()));
                rcCardDetailsModel.setIsPrinted(String.valueOf(vehicleRCEntity.getIsPrinted()));
            }
            rcCardDetailsModels.add(rcCardDetailsModel);
            logger.info(":::::::::getRcCardDetailsByDate::::::::::end in Loop::::::");
        }
        logger.info(":::::::::getRcCardDetailsByDate::::::::::End::::::");
        return rcCardDetailsModels;
    }

    private RcCardDetailsModel getRtaOfficeDetails(RcCardDetailsModel rcCardDetailsModel,
            RtaOfficeEntity rtaOfficeEntity) {
        rcCardDetailsModel.setOfficeDesc(rtaOfficeEntity.getName());
        rcCardDetailsModel.setOfficeCode(rtaOfficeEntity.getCode());
        return rcCardDetailsModel;
    }

    private RcCardDetailsModel getInsuranceDetails(RcCardDetailsModel rcCardDetailsModel, Long vehicleRcId) {
        logger.info(":::::::::getCustomerIndiDetails::::::::::start::::::");
        InsuranceDetailsEntity insuranceDetailsEntity =
                insuranceDetailsDAO.getInsuranceDtlsEntityByVehicleRcId(vehicleRcId);
        if (!ObjectsUtil.isNull(insuranceDetailsEntity)) {
            rcCardDetailsModel.setInsuranceCompanyName(insuranceDetailsEntity.getProvider());
            rcCardDetailsModel.setInsuranceInsType(insuranceDetailsEntity.getInsuranceTypeEntity().getName());
            rcCardDetailsModel.setInsurancePolicyNo(insuranceDetailsEntity.getPolicyNo());
            rcCardDetailsModel.setInsuranceValidUpto(DateUtil.getDateAsString(insuranceDetailsEntity.getEndDate()));
        }
        logger.info(":::::::::getCustomerIndiDetails::::::::::End::::::");
        return rcCardDetailsModel;
    }

    private RcCardDetailsModel getCustomerIndiDetails(AddressEntity addressEntity,
            CustIndividualDetailsEntity custIndividualDetailsEntity, RcCardDetailsModel rcCardDetailsModel) {
        logger.info(":::::::::getCustomerIndiDetails::::::::::start::::::");
        String displayName = custIndividualDetailsEntity.getDisplayName();
        if (!StringsUtil.isNullOrEmpty(displayName)) {
        	rcCardDetailsModel.setOwnerName(displayName.toUpperCase());
        }
        String fathersName = custIndividualDetailsEntity.getFatherName();
        if (!StringsUtil.isNullOrEmpty(fathersName)) {
        	if (fathersName.length() > MAX_FATHERS_NAME_STRING_LENGTH) {
        		rcCardDetailsModel.setParentsName(fathersName.substring(0, MAX_FATHERS_NAME_STRING_LENGTH));
        	} else {
        		rcCardDetailsModel.setParentsName(fathersName);
        	}
        }
        rcCardDetailsModel.setMobileNo(custIndividualDetailsEntity.getMobileNo());
        rcCardDetailsModel.setDob(custIndividualDetailsEntity.getDateOfBirth());
        rcCardDetailsModel.setGender(custIndividualDetailsEntity.getGender());
        Map<String, String> map = adjustAddressLines(addressEntity.getDoorNo(), addressEntity.getStreet(), MAX_ADDRESS_LINE_STRING_LENGTH);
        rcCardDetailsModel.setOwnerAdd1(map.get("ADD1"));
        rcCardDetailsModel.setOwnerAdd2(map.get("ADD2"));
        rcCardDetailsModel.setOwnerCity(addressEntity.getCity());
        rcCardDetailsModel.setOwnerMandal(addressEntity.getMandal().getName());
        rcCardDetailsModel.setOwnerDist(addressEntity.getMandal().getDistrictEntity().getName());
        rcCardDetailsModel.setOwnerState(addressEntity.getMandal().getDistrictEntity().getStateEntity().getName());
        rcCardDetailsModel.setOwnerCountry(
                addressEntity.getMandal().getDistrictEntity().getStateEntity().getCountryEntity().getName());
        rcCardDetailsModel.setPinCode(addressEntity.getPinCode());
        logger.info(":::::::::getCustomerIndiDetails::::::::::end::::::");
        return rcCardDetailsModel;
    }

    /**
     * limit add1 size to only 20 characters
     * @param add1
     * @param add2
     * @param lineMaxLength TODO
     * @return
     */
    public static Map<String, String> adjustAddressLines(String add1, String add2, int lineMaxLength) {
        if (!StringsUtil.isNullOrEmpty(add1) && !StringsUtil.isNullOrEmpty(add2)) {
            String temp = add1;
            if (!StringsUtil.isNullOrEmpty(temp) && temp.length() > lineMaxLength) {
                add1 = temp.substring(0, lineMaxLength);
                add2 = new StringBuilder(temp.substring(lineMaxLength, temp.length())).append(" ").append(add2).toString();
            }
        }
        Map<String,String> map = new HashMap<>();
        map.put("ADD1", add1);
        map.put("ADD2", add2);
        return map;
    }
    
    // for test only
    public static void main(String[] args) {
        System.out.println(adjustAddressLines("49-54-7/8, Flat No-201, Sri Sai Vihar Apartment", "sdafl", 20));
        String a = "49-54-7/8, Flat No-2";
        if (a.length() > 20) {
        	System.out.println(a.substring(0, 20)); 
        }
    }
    
    private RcCardDetailsModel getCustomerCorpDetails(AddressEntity addressEntity,
            CustCorporateDetailsEntity custCorporateDetailsEntity, RcCardDetailsModel rcCardDetailsModel) {
        logger.info(":::::::::getCustomerCorpDetails::::::::::start::::::");
        String displayName = custCorporateDetailsEntity.getDisplayName();
        if (!StringsUtil.isNullOrEmpty(displayName)) {
        	rcCardDetailsModel.setOwnerName(displayName.toUpperCase());
        }
        if (ObjectsUtil.isNotNull(custCorporateDetailsEntity.getRepresentedBy())) {
            rcCardDetailsModel.setRepresentedBy(custCorporateDetailsEntity.getRepresentedBy());
        }
        rcCardDetailsModel.setMobileNo(custCorporateDetailsEntity.getMobile());
        rcCardDetailsModel.setParentsName("");
        rcCardDetailsModel.setDob("");
        rcCardDetailsModel.setGender("");
        Map<String, String> map = adjustAddressLines(addressEntity.getDoorNo(), addressEntity.getStreet(), MAX_ADDRESS_LINE_STRING_LENGTH);
        rcCardDetailsModel.setOwnerAdd1(map.get("ADD1"));
        rcCardDetailsModel.setOwnerAdd2(map.get("ADD2"));
        rcCardDetailsModel.setOwnerCity(addressEntity.getCity());
        rcCardDetailsModel.setOwnerMandal(addressEntity.getMandal().getName());
        rcCardDetailsModel.setOwnerDist(addressEntity.getMandal().getDistrictEntity().getName());
        rcCardDetailsModel.setOwnerState(addressEntity.getMandal().getDistrictEntity().getStateEntity().getName());
        rcCardDetailsModel.setOwnerCountry(
                addressEntity.getMandal().getDistrictEntity().getStateEntity().getCountryEntity().getName());
        rcCardDetailsModel.setPinCode(addressEntity.getPinCode());
        logger.info(":::::::::getCustomerCorpDetails::::::::::end::::::");
        return rcCardDetailsModel;
    }

    private RcCardDetailsModel getVehicleDetails(RcCardDetailsModel rcCardDetailsModel, VahanEntity vahanEntity,
            Long vehicleRcId) {
        try {
            logger.info(":::::::::getVehicleDetails::::::::::start::::::");
            rcCardDetailsModel.setChassisNo(vahanEntity.getChassisNumber());
            rcCardDetailsModel.setColor(vahanEntity.getColor());
            rcCardDetailsModel.setCubicCapacity(vahanEntity.getCubicCap().toString() + " CC");
            rcCardDetailsModel.setEngineNo(vahanEntity.getEngineNumber());
            rcCardDetailsModel.setFuelUsed(vahanEntity.getFuelUsed());
            rcCardDetailsModel.setMakersClass(vahanEntity.getMakersClass());
            VehicleDetailsEntity vde = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
            rcCardDetailsModel.setMthYearofMfg(getMonthYearofMfg(vde.getMfgDate()));
            rcCardDetailsModel.setSeatingCapacity(String.valueOf(vahanEntity.getSeatingCapacity()));
            rcCardDetailsModel.setTypeofBody(vahanEntity.getBodyTypeDesc());
            rcCardDetailsModel.setUnladenWeight(String.valueOf(vahanEntity.getUlw()) + " Kg");
            rcCardDetailsModel.setVehicleClass(getVehicleClass(vehicleRcId));
            rcCardDetailsModel.setWheelBase(String.valueOf(vahanEntity.getWheelbase()) + " mm");
            logger.info(":::::::::getVehicleDetails::::::::::end::::::");
        } catch (Exception e) {
            logger.error(":::::::::Error in getVehicleDetails:::::::::::::" + e.getMessage());
        }
        return rcCardDetailsModel;
    }

    private RcCardDetailsModel getTaxDetails(RcCardDetailsModel rcCardDetailsModel, VehicleCurrentTaxEntity currentTaxEntity,
            Long trIssueDate) {
        logger.info(":::::::::getTaxDetails::::::::::start::::::");
        TaxType taxType = TaxType.getTaxType(currentTaxEntity.getTaxTypeId().intValue());
        rcCardDetailsModel.setTax("Rs." + String.valueOf(NumberParser.getRoundOff(currentTaxEntity.getTaxAmt())) + "( "
                + taxType.getLabel() + " )");
        rcCardDetailsModel.setTaxFine("Rs. " + String.valueOf(NumberParser.getRoundOff(
        		getPenality(currentTaxEntity.getVehicleRc().getVehicleRcId(), taxType.getValue()))));
        // rcCardDetailsModel.setTaxBackendUpdateFlag("Y");/// pending
        // rcCardDetailsModel.setTaxExemptionNo("");/// pending
        rcCardDetailsModel.setTaxValidFrom(DateUtil.getDateAsString(trIssueDate));
        rcCardDetailsModel.setTaxValidUpto(DateUtil.getDateAsString(currentTaxEntity.getTaxValidUpto()));
        rcCardDetailsModel.setVehicleTaxPaidUpto(DateUtil.getDateAsString(currentTaxEntity.getTaxValidUpto()));
        logger.info(":::::::::getTaxDetails::::::::::end::::::");

        return rcCardDetailsModel;
    }

    private RcCardDetailsModel getFinanceDetails(RcCardDetailsModel rcCardDetailsModel,
            FinanceDetailsEntity financeDetailsEntity) {
        logger.info(":::::::::getFinanceDetails::::::::::start::::::");
        if (ObjectsUtil.isNull(financeDetailsEntity)) {
            rcCardDetailsModel.setHypothecatedTo("Self");
            return rcCardDetailsModel;
        }

        rcCardDetailsModel.setHypothecatedTo(financeDetailsEntity.getName());
        rcCardDetailsModel
                .setHypoAddress(financeDetailsEntity.getCity() + ", " + financeDetailsEntity.getMandalEntity().getName()
                        + ", " + financeDetailsEntity.getDistrictEntity().getName() + ", "
                        + financeDetailsEntity.getStateEntity().getName());
        rcCardDetailsModel.setHypoHypoFrom(DateUtil.getDateAsString(financeDetailsEntity.getDateOfAgreement()));
        logger.info(":::::::::getFinanceDetails::::::::::end::::::");
        return rcCardDetailsModel;
    }

    private RcCardDetailsModel getOnlineFinanceDetails(RcCardDetailsModel rcCardDetailsModel,
            VehicleFinanceDtlstEntity financeDetailsEntity) {
        logger.info(":::::::::getOnlineFinanceDetails::::::::::start::::::");
        if (ObjectsUtil.isNull(financeDetailsEntity)) {
            rcCardDetailsModel.setHypothecatedTo("Self");
            return rcCardDetailsModel;
        }
        UserEntity user = userDAO.findByUserId(financeDetailsEntity.getFinancerId());
        if(ObjectsUtil.isNull(user)) {
            rcCardDetailsModel.setHypothecatedTo("Self");
            return rcCardDetailsModel;
        }
        
        rcCardDetailsModel.setHypothecatedTo(user.getInstitutionName());
        
        AddressEntity address = addressDAO.findByUserIdAndType(user.getUserId(), "T");
        if(!ObjectsUtil.isNull(address)) {
            StringBuilder sb = new StringBuilder(address.getCity());
            if (address.getMandal() != null) {
                sb.append(", ").append(address.getMandal().getName());
                DistrictEntity district = address.getMandal().getDistrictEntity();
                if(null != district) {
                    sb.append(", ").append(district.getName());
                    StateEntity state = district.getStateEntity();
                    if(state != null) {
                        sb.append(", ").append(state.getName());
                    }
                }
            }
            rcCardDetailsModel.setHypoAddress(sb.toString());
        }
        if(null != financeDetailsEntity.getDateOfAgreement()){
            rcCardDetailsModel.setHypoHypoFrom(DateUtil.getDateAsString(financeDetailsEntity.getDateOfAgreement()));
        }
        logger.info(":::::::::getOnlineFinanceDetails::::::::::end::::::");
        return rcCardDetailsModel;
    }

    @SuppressWarnings("incomplete-switch")
    @Transactional
    @Override
    public List<RtyFromtoModel> getRcCardBetweenTwoDate(String from, String to, String rtaOfficeCode) {
        logger.info(":::::::::getRcCardBetweenTwoDate::::::::::start::::::");
        RtaOfficeEntity rtaOfficeEntity = rtaOfficeDAO.getRtaOfficeDetailsByCode(rtaOfficeCode);
        if (ObjectsUtil.isNull(rtaOfficeEntity)) {
            throw new IllegalArgumentException("Invalid RTA Office Code  !!!");
        }
        List<VehicleRCEntity> vehicleRCEntities =
                vehicleDAO.getVehicleRcByFromToDate(DateUtil.getDateInLong(from, "00:00:00"),
                        DateUtil.getDateInLong(to, "23:59:59"), rtaOfficeEntity.getRtaOfficeId(), false, true);
        RtyFromtoModel rtyFromtoModel = null;
        List<RtyFromtoModel> rtyFromtoModels = new ArrayList<RtyFromtoModel>();
        for (VehicleRCEntity veRcEntity : vehicleRCEntities) {
            rtyFromtoModel = new RtyFromtoModel();
            rtyFromtoModel.setVehRegNo(veRcEntity.getPrNumber());
            rtyFromtoModel.setDateofRegistration(DateUtil.getDateAsString(veRcEntity.getPrIssueTime()));
            switch (OwnershipType.getOwnershipType(veRcEntity.getOwnershipType())) {
            	case POLICE :
            	case COMPANY:
            	case STU_VEHICLES :
            	case ORGANIZATION:
                    CustCorporateDetailsEntity custCorpDetails =
                            customerCorporateDAO.getByVehicleRcId(veRcEntity.getVehicleRcId());
                    rtyFromtoModel.setOwnerName(custCorpDetails.getDisplayName());
                    break;
                case INDIVIDUAL:
                    CustIndividualDetailsEntity custIndiDetails =
                            customerDAO.getByVehicleRcId(veRcEntity.getVehicleRcId());
                    rtyFromtoModel.setOwnerName(custIndiDetails.getDisplayName());
                    break;
            }
            rtyFromtoModels.add(rtyFromtoModel);
        }
        logger.info(":::::::::getRcCardBetweenTwoDate::::::::::end::::::");
        return rtyFromtoModels;
    }

    private String getOwnerSignature(Long vehicleRcId) {
        logger.info(":::::::::getOwnerSignature::::::::::start::::::");
        ObjectsUtil.disableSSLVerification();
        AttachmentDetailsEntity attachmentDetailsEntity =
                attachmentDetailsDAO.getAttatchDltIdByDocIdVehId(vehicleRcId, DocTypes.DOC_USER_SIGNATURE.getValue());
        logger.info(":::::::::attachmentDetailsEntity::::::::::start::::::");

        String imageDataString =null;
        try {
            logger.info(":::::::::attachmentDetailsEntity::::::::::start::::::" + attachmentDetailsEntity.getSource());
            // http://59.162.46.199:8181
            String fileName = null;
            if(attachmentDetailsEntity.getUserRole() == UserType.ROLE_CITIZEN.getValue()){
            	fileName = baseURLCitizenDoc + attachmentDetailsEntity.getSource();
            } else {
            	fileName = baseURL + attachmentDetailsEntity.getSource();
            }
            URL url = new URL(fileName);
            BufferedImage bufferedImage = ImageIO.read(url.openStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            imageDataString = Base64.encodeBase64String(imageInByte);
        } catch (Exception ex) {
            logger.info(":::::::::getOwnerSignature::::::::::Exception::::::" + ex.getMessage());
            ex.printStackTrace();
        }
        logger.info(":::::::::getOwnerSignature::::::::::end::::::");
        return imageDataString;
    }
    
    private String getEmployeeSignature(Long vehicleRcId) {
        logger.info(":::::::::getEmployeeSignature::::::::::start::::::");
        String imageDataString = null;
        String fileName = null;
        try{
            logger.info(":::::::::getEmployeeSignature::::::::::start for get file::::::");
            VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);
            UserEntity userEntity = null;
            if(vehicleRCEntity.getRtoActionStatus() == Status.APPROVED.getValue()){
            	userEntity = vehicleRCEntity.getRtoUserId();
            }else{
            	userEntity = vehicleRCEntity.getAoUserId();
            }
            fileName = rtaEmployeeDAO.getByUserId(userEntity.getUserId()).getSignFileName();
        } catch (Exception ex) {
            return imageDataString;
        }
        try {
            if (!StringsUtil.isNullOrEmpty(fileName)) {
                String signatureFile = baseURL + File.separator + signaturePath + File.separator + fileName;
                URL url = new URL(signatureFile);
                BufferedImage bufferedImage = ImageIO.read(url);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                imageDataString = Base64.encodeBase64String(imageInByte);
            }
        } catch (Exception ex) {
            logger.info(":::::::::getEmployeeSignature::::::::::Exception::::::" + ex.getMessage());
            ex.printStackTrace();
        }
        logger.info(":::::::::getEmployeeSignature::::::::::end::::::");
        return imageDataString;
    }
    
    @Transactional
    @Override
    public SaveUpdateResponse updatePrintStatus(PrintStatusModel printStatusModel) {
        logger.info(":::::::::updatePrintStatus::::::::::start::::::");
        Map<String, Object> printStatusMap = new HashMap<String, Object>();
        SaveUpdateResponse updateResponse = new SaveUpdateResponse();
        printStatusMap.put("rcPrintDate", DateUtil.getDateInLong(printStatusModel.getRcPrintDate(), "00:00:00"));
        printStatusMap.put("prNumber", printStatusModel.getVehicleRegNo());
        Integer updateStatus =0;
        try{
            updateStatus = vehicleDAO.updatePrintStatusAndPrintDate(printStatusMap);
        }catch(Exception ex){
            logger.error(":::::::::updatePrintStatus::::::::::Exception::::::" + ex.getMessage());
            logger.debug(ex);
        }

        if (updateStatus > 0) {
            updateResponse.setMessage("Update Successfully");
            updateResponse.setStatus("1");
        } else {
            updateResponse.setMessage("Update Failure");
            updateResponse.setStatus("0");
        }
        logger.info(":::::::::updatePrintStatus::::::::::return::::::" + updateResponse);
        return updateResponse;
    }

    @Transactional
    @Override
    public String getRtaOfficeCode(Long userId) {
        return rcCardEmployeeDAO.getRtaOfficeCode(userId);
    }

    private String getMonthYearofMfg(String mfgDate) {
        if(StringsUtil.isNullOrEmpty(mfgDate)){
            return null;
        }
        return mfgDate.substring(0, 2) + "-" + mfgDate.substring(2);
    }

    private String getVehicleClass(Long vehicleRcId) {
        logger.info(":::::::::getVehicleClass::::::::::start::::::");
        String vehicleClass=null;
        try{
            vehicleClass=vehicleClassDescDAO
                    .getByVehiclClassDesCode(vehicleDetailsDAO.getByVehicleRcId(vehicleRcId).getVehicleSubClass())
                    .getDescription();
        } catch (Exception ex) {
            logger.info(":::::::::getVehicleClass::::::::::Exception::::::" + ex.getMessage());
        }
        logger.info(":::::::::getVehicleClass::::::::::End::::::");
        return vehicleClass;
    }
    
    @Transactional
    @Override
    public String getCcoSignature(Long vehicleRcId) {
        logger.info(":::::::::getEmployeeSignature::::::::::start::::::");
        String imageDataString = null;
        String fileName = null;
        try{
            logger.info(":::::::::getEmployeeSignature::::::::::start for get file::::::");
            VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);
            UserEntity userEntity = null;
            userEntity = vehicleRCEntity.getCcoUserId();
            fileName = rtaEmployeeDAO.getByUserId(userEntity.getUserId()).getSignFileName();
        } catch (Exception ex) {
            return imageDataString;
        }
        try {
            if (!StringsUtil.isNullOrEmpty(fileName)) {
            	imageDataString= baseURL + File.separator + signaturePath + File.separator + fileName;
            }
        } catch (Exception ex) {
            logger.info(":::::::::getEmployeeSignature::::::::::Exception::::::" + ex.getMessage());
            ex.printStackTrace();
        }
        logger.info(":::::::::getEmployeeSignature::::::::::end::::::");
        return imageDataString;
    }
    
    private double getPenality(Long vehicleRcId, Integer TaxTypeId){
    	if(TaxTypeId != TaxType.LIFE_TAX.getValue()){
    		try{
    			return periodicTaxDAO.getEntityByVehicleRcId(vehicleRcId).getPenalty();
    		}catch (Exception e) {}
    	}
    	return SomeConstants.ZERO;
    }
}
