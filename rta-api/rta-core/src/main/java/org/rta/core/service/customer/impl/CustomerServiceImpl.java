package org.rta.core.service.customer.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.rta.core.dao.customer.CustCorporateDetailsHistoryDAO;
import org.rta.core.dao.customer.CustIndividualDetailsHistoryDAO;
import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.customer.CustomerVehicleRCDAO;
import org.rta.core.dao.customer.PAddressNotMatchedDAO;
import org.rta.core.dao.docs.AttachmentDetailsDAO;
import org.rta.core.dao.docs.AttachmentDetailsHistoryDAO;
import org.rta.core.dao.finance.FinancerFreshContactDetailsDao;
import org.rta.core.dao.finance.FinancerSeizedVehiclesDAO;
import org.rta.core.dao.finance.VehicleFinanceDtlsDAO;
import org.rta.core.dao.legalhier.LegalHierDetailsDAO;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.master.MandalDAO;
import org.rta.core.dao.master.OwnershipDAO;
import org.rta.core.dao.master.QualificationDAO;
import org.rta.core.dao.master.RegistrationCategoryDAO;
import org.rta.core.dao.master.StateDAO;
import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.dao.permit.PermitDetailsMappingDAO;
import org.rta.core.dao.permit.PermitHeaderDAO;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.dao.user.AddressHistoryDAO;
import org.rta.core.dao.user.PermanentAddressDAO;
import org.rta.core.dao.user.PermanentAddressHistoryDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.dao.vehicle.RCLockDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleRCChangeHistoryDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.applicant.PAddressNotMatchedEntity;
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.entity.finance.FinancerSeizedVehiclesEntity;
import org.rta.core.entity.finance.VehicleFinanceDtlstEntity;
import org.rta.core.entity.legalhier.LegalHierDetailsEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.master.OwnershipTypeEntity;
import org.rta.core.entity.master.RegistrationCategoryEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.entity.permit.PermitDetailsMappingEntity;
import org.rta.core.entity.permit.PermitHeaderEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.user.PermanentAddressEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.RCLockEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.AddressType;
import org.rta.core.enums.OfficeType;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.PermitOptionType;
import org.rta.core.enums.RegistrationCategoryType;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.enums.master.Qualification;
import org.rta.core.exception.DataMismatchException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.AadharModel;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.customer.CustomerDetailsRequestModel;
import org.rta.core.model.customer.CustomerModel;
import org.rta.core.model.docs.DocSyncModel;
import org.rta.core.model.legalhier.LegalHierDetailsModel;
import org.rta.core.model.master.DistrictModel;
import org.rta.core.model.master.MandalModel;
import org.rta.core.model.master.StateModel;
import org.rta.core.model.user.AddressChangeModel;
import org.rta.core.model.vehicle.RCListModel;
import org.rta.core.service.aadhar.AadharTCSInfoService;
import org.rta.core.service.customer.CustomerService;
import org.rta.core.service.master.DistrictService;
import org.rta.core.service.master.MandalService;
import org.rta.core.service.master.StateService;
import org.rta.core.service.vehicle.VehicleService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for the AadharInfoService. Class calls aadhar api and get response.
 * 
 * @author Sriram
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = Logger.getLogger(CustomerServiceImpl.class);
    
    private final CustomerDAO customerDAO;

    @SuppressWarnings("unused")
    private final CustomerVehicleRCDAO customerVehicleRCDAO;

    private final AddressDAO addressDAO;

    @Autowired
    private MandalDAO mandalDAO;

    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private PermanentAddressDAO permanentAddressDAO;
    
    @Autowired
    private PermanentAddressHistoryDAO permanentAddressHistoryDAO;


    @Autowired
    private CustomerCorporateDAO customerCorporateDAO;

    @Autowired
    private OwnershipDAO ownershipDAO;

    @Autowired
    private RegistrationCategoryDAO registrationCategoryDAO;

    @Autowired
    private QualificationDAO qualificationDAO;
    
    @Autowired
    private StateDAO stateDao;
    
    @Autowired
    private DistrictDAO districtDAO;
    
    @Autowired
    private MandalService mandalService;
    
    @Autowired
    private StateService stateService;
    
    @Autowired
    private DistrictService districtService;
    
    @Autowired
    private PAddressNotMatchedDAO pAddressNotMatchedDAO;
    
    @Autowired
    private AddressHistoryDAO addressHistoryDAO;

    @Autowired
    private CustCorporateDetailsHistoryDAO custCorporateDetailsHistoryDAO;

    @Autowired
    private CustIndividualDetailsHistoryDAO custIndividualDetailsHistoryDAO;

    @Autowired
    private VehicleRCChangeHistoryDAO vehicleRCChangeHistoryDAO;

    @Autowired
    private AadharTCSInfoService aadharTCSInfoService;
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private FinancerSeizedVehiclesDAO financerSeizedVehiclesDAO;
    
    @Autowired
    private VehicleFinanceDtlsDAO vehicleFinanceDAO;
    
    @Autowired
    private RtaOfficeDAO rtaOfficeDAO;
    
    @Autowired
    private PermitHeaderDAO permitHeaderDAO;
    
    @Autowired
    private PermitDetailsMappingDAO permitDetailsMappingDAO;
    
    @Autowired
    private AttachmentDetailsDAO attachmentDetailsDAO;

    @Autowired
    private AttachmentDetailsHistoryDAO attachmentDetailsHistoryDAO;
    
    @Autowired
    private FinancerFreshContactDetailsDao financerFreshContactDetailsDao;
    
    @Autowired
    private RCLockDAO rCLockDAO;
    
    @Autowired
    private VehicleService vehicleService;
    
    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO, CustomerVehicleRCDAO customerVehicleRCDAO,
            AddressDAO addressDAO) {
        this.customerDAO = customerDAO;
        this.customerVehicleRCDAO = customerVehicleRCDAO;
        this.addressDAO = addressDAO;
    }
    
    @Autowired
    private LegalHierDetailsDAO legalHierDetailsDAO;

    @Override
    @Transactional
    public Long update(Long userId, CustomerDetailsRequestModel customerModel, String userName, Integer currentStep) {
        Long vehicleRcId = customerModel.getVehicleRcId();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        VehicleRCEntity vehicleRCEntity = null;
        CustIndividualDetailsEntity cde = null;
        PermanentAddressEntity permAddressEntity = new PermanentAddressEntity();
        Boolean isSaveUpdatePermanentAdrs = true;
        AddressEntity tempAddressEntity = null;

        if (StringsUtil.isNullOrEmpty(customerModel.getOwnershipType())
                || StringsUtil.isNullOrEmpty(customerModel.getRegCategory())) {
            log.error("Ownership Type OR Registration category is empty or null !!!");
            throw new IllegalArgumentException("OwnershipType or Registration category missing.");
        }
        OwnershipTypeEntity ownership = ownershipDAO.getByCode(customerModel.getOwnershipType());
        if (ownership.getName().equalsIgnoreCase(OwnershipType.COMPANY.getLabel()) || ownership.getName().equalsIgnoreCase(OwnershipType.POLICE.getLabel()) 
        		|| ownership.getName().equalsIgnoreCase(OwnershipType.STU_VEHICLES.getLabel()) || ownership.getName().equalsIgnoreCase(OwnershipType.ORGANIZATION.getLabel())) {
        	customerModel.setOwnershipType(ownership.getName());
            return saveUpdateCompany(userId, customerModel, userName, currentStep);
        }
        if (!Objects.isNull(vehicleRcId)) {
            log.info("::::::: UPDATING customer data using vehicleRcId :" + vehicleRcId);
            cde = customerDAO.getByVehicleRcId(vehicleRcId);
            // Should change, once other ownership type introduced
            permAddressEntity = permanentAddressDAO.findByUserId(cde.getCustIndDtlId(), AddressType.AADHAR);
            tempAddressEntity = addressDAO.findByUserIdAndType(cde.getCustIndDtlId(), "T");
            vehicleRCEntity = cde.getVehicleRcId();
            if (userEntity.getUserId().compareTo(vehicleRCEntity.getUserId().getUserId()) != 0) {
                log.error("Customer is getting updated by another dealer !!!");
                throw new IllegalArgumentException("Invalid Customer Update.");
            }
            if(vehicleRCEntity.isAadharVerified()){
                isSaveUpdatePermanentAdrs = false;
            }
        } else {
            log.info("::::::: SAVING customer data :::::::::");
            // SETUP vehicle rc entity
            vehicleRCEntity = new VehicleRCEntity();
            vehicleRCEntity.setUserId(userEntity);
            vehicleRCEntity.setAadharNo(customerModel.getUid());
            
            if(ObjectsUtil.isNull(customerModel.getIsAadharVerified()) || customerModel.getIsAadharVerified()){
            	if(StringsUtil.isNullOrEmpty(customerModel.getUid())){
                    vehicleRCEntity.setAadharVerified(false);
                } else {
                    vehicleRCEntity.setAadharVerified(true);
                }
            } else {
                vehicleRCEntity.setAadharVerified(false);
            }
            
            vehicleRCEntity.setCurrentStep(currentStep);
            vehicleRCEntity.setProcessStatus(Status.PENDING.getValue());
            vehicleRCEntity.setTrStatus(Status.PENDING.getValue());
            vehicleRCEntity.setPrStatus(0);
            vehicleRCEntity.setCreatedBy(userName);
            vehicleRCEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());

            // SETUP Customer entity
            cde = new CustIndividualDetailsEntity();
            cde.setVehicleRcId(vehicleRCEntity);
            cde.setAadharNo(customerModel.getUid());
            cde.setCreatedBy(userName);
            cde.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
            cde.setDateOfBirth(customerModel.getDob());

            // SETUP customer present address
            tempAddressEntity = new AddressEntity();
            tempAddressEntity.setType("T");
            tempAddressEntity.setStatus(true);
            tempAddressEntity.setCreatedBy(userName);
            tempAddressEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
        }
        if(isSaveUpdatePermanentAdrs){
         // SETUP customer permanent address
            permAddressEntity.setDoorNo(customerModel.getPerm_buildingName());
            permAddressEntity.setStreet(customerModel.getPerm_street());
            permAddressEntity.setStatus(true);
            permAddressEntity.setCity(customerModel.getPerm_town());
            permAddressEntity.setCountry(customerModel.getPerm_country());
            permAddressEntity.setState(customerModel.getPerm_statecode());
            permAddressEntity.setDistrict(customerModel.getPerm_district_name());
            permAddressEntity.setMandal(customerModel.getPerm_mandal_name());
            permAddressEntity.setPostOffice(customerModel.getPerm_po());
            permAddressEntity.setPinCode(customerModel.getPerm_pincode());
            permAddressEntity.setCreatedBy(userName);
            permAddressEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
            permAddressEntity.setModifiedBy(userName);
            permAddressEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
            permAddressEntity.setAddressType(AddressType.AADHAR.getValue());
        }
       
        // TODO Should change, once other ownership type introduced
        vehicleRCEntity.setOwnershipType(OwnershipType.INDIVIDUAL.getId());
        RegistrationCategoryEntity regCat = registrationCategoryDAO.getByCode(customerModel.getRegCategory());
        vehicleRCEntity.setRegCategory(regCat);

        // set rta-office w.r.t. present address mandal
        MandalEntity tempMandalEntity = mandalDAO.getByMandalCode(Integer.valueOf(customerModel.getTemp_mandal_code()));
        vehicleRCEntity.setRtaOfficeId(getRtaOffice(tempMandalEntity.getRtaOfficeEntity(),regCat.getRegistrationCategoryId()));
        vehicleRCEntity.setChassisNumber(StringsUtil.isNullOrEmpty(vehicleRCEntity.getChassisNumber()) ? ""
                : vehicleRCEntity.getChassisNumber());
        vehicleRCEntity.setModifiedBy(userName);
        vehicleRCEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        vehicleDAO.saveOrUpdate(vehicleRCEntity);

        cde.setAlternateEmail(customerModel.getEmailid());
        cde.setIsDisabled(customerModel.getPhysically_challenged());
        cde.setIsInvalidCarriage(customerModel.isInvalidCarriage());
        cde.setDisplayName(customerModel.getRc_name());
        cde.setEmailId(customerModel.getEmailid());
        cde.setEmergencyNo(customerModel.getPhoneNo());
        cde.setFatherName(customerModel.getFather_name());
        cde.setFirstName(customerModel.getFirst_name());
        //cde.setGender(("M".equalsIgnoreCase(customerModel.getGender()) || "Male".equalsIgnoreCase(customerModel.getGender())) ? "Male" : "Female");
        if("M".equalsIgnoreCase(customerModel.getGender()) || "Male".equalsIgnoreCase(customerModel.getGender())) {
            cde.setGender("Male");
        } else if("NOT_RECORDED".equalsIgnoreCase(customerModel.getGender())) {
            cde.setGender("NOT_RECORDED");
        } else {
            cde.setGender("Female");
        }
        cde.setMobileNo(customerModel.getPhoneNo());
        cde.setModifiedBy(userName);
        cde.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        cde.setPassportNo(customerModel.getPassportnum());
        cde.setPassportValidUpto(customerModel.getPassportvalidupto());
        cde.setPermanentPhoneNo(customerModel.getPhoneNo());
        cde.setAlternateMobileNo(customerModel.getAlternate_mobile());
        cde.setQualificationId(Long.valueOf(qualificationDAO.getByCode(customerModel.getQualification()).getCode()));
        cde.setIsSameAsAddrAadhar(customerModel.getSameAsAddAadhar());
//        cde.setIsSecondVehicle(customerModel.getIs_second_vehicle());
        cde.setStatus(String.valueOf(1));// 1 for active, 0 for inactive
        cde.setSurName(customerModel.getLast_name());
        cde.setVechicleRegRta(customerModel.getVehregrta());
        cde.setVehicleRegRtaCode(customerModel.getVehregrtacode());
        cde.setBloodGroup(customerModel.getBloodGroup());
        cde.setDependentAddrs(customerModel.isDependentAddrs());
        cde.setAddressPerson(customerModel.getAddressPerson());
        cde.setNameOfDependent(customerModel.getNameOfDependent());
        customerDAO.saveOrUpdate(cde);

        // Should change, once other ownership type introduced
        permAddressEntity.setUserType(UserType.ROLE_CUSTOMER_IND);
        permAddressEntity.setUserId(cde.getCustIndDtlId());
        permAddressEntity.setAddressType(AddressType.AADHAR.getValue());
        permAddressEntity.setCountry(customerModel.getPerm_country());
        permanentAddressDAO.saveOrUpdate(permAddressEntity);

        tempAddressEntity.setDoorNo(customerModel.getTemp_buildingName());
        tempAddressEntity.setStreet(customerModel.getTemp_street());
        // Should change, once other ownership type introduced
        tempAddressEntity.setUserType(UserType.ROLE_CUSTOMER_IND);
        tempAddressEntity.setUserId(cde.getCustIndDtlId());
        tempAddressEntity.setCity(customerModel.getTemp_town());
        tempAddressEntity.setCountry(Long.valueOf(1)); // India always, will change in future
        // mandal not same as aadhar mandal
        tempAddressEntity.setState(stateDao.getStateByCode(customerModel.getTemp_statecode()).getStateId());
        tempAddressEntity.setDistrict(districtDAO.getDistrictByCode(customerModel.getTemp_district_code()).getDistrictId());
        tempAddressEntity.setMandal(tempMandalEntity);
        tempAddressEntity.setPinCode(customerModel.getTemp_pincode());
        tempAddressEntity.setModifiedBy(userName);
        tempAddressEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        addressDAO.saveOrUpdate(tempAddressEntity);

        try{
            setInsideApAndMandal(customerModel, true, vehicleRCEntity.getVehicleRcId());
        } catch(Exception ex){
            log.error("Exception Occured while saving not matched data");
            log.debug(ex);
        }
        
        return vehicleRCEntity.getVehicleRcId();
    }

    @Override
    @Transactional
    public Long saveUpdateCompany(Long userId, CustomerDetailsRequestModel customerModel, String userName,
            Integer currentStep) {
        Long vehicleRcId = customerModel.getVehicleRcId();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        VehicleRCEntity vehicleRCEntity = null;
        CustCorporateDetailsEntity ccd = null;
        AddressEntity tempAddressEntity = null;

        if (!Objects.isNull(vehicleRcId)) {
            log.info("::::::: UPDATING customer data using vehicleRcId :" + vehicleRcId);
            ccd = customerCorporateDAO.getByVehicleRcId(vehicleRcId);
            tempAddressEntity = addressDAO.findByUserIdAndType(ccd.getCustCorpDtlsId(), "T");
            vehicleRCEntity = ccd.getVehicleRcId();
            if (userEntity.getUserId().compareTo(vehicleRCEntity.getUserId().getUserId()) != 0) {
                log.error("Customer is getting updated by another dealer !!!");
                throw new IllegalArgumentException("Invalid Customer Update.");
            }
        } else {
            log.info("::::::: SAVING customer data :::::::::");
            // SETUP vehicle rc entity
            vehicleRCEntity = new VehicleRCEntity();
            vehicleRCEntity.setUserId(userEntity);
            vehicleRCEntity.setAadharNo(customerModel.getUid());
            
            if(ObjectsUtil.isNull(customerModel.getIsAadharVerified()) || customerModel.getIsAadharVerified()){
            	if(StringsUtil.isNullOrEmpty(customerModel.getUid())){
                    vehicleRCEntity.setAadharVerified(false);
                } else {
                    vehicleRCEntity.setAadharVerified(true);
                }
            } else {
                vehicleRCEntity.setAadharVerified(false);
            }
            
            vehicleRCEntity.setCurrentStep(currentStep);
            vehicleRCEntity.setProcessStatus(Status.PENDING.getValue());
            vehicleRCEntity.setTrStatus(Status.PENDING.getValue());
            vehicleRCEntity.setPrStatus(0);
            vehicleRCEntity.setCreatedBy(userName);
            vehicleRCEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());

            // SETUP Customer entity
            ccd = new CustCorporateDetailsEntity();
            ccd.setVehicleRcId(vehicleRCEntity);
            ccd.setAadharNo(customerModel.getUid());
            ccd.setCreatedBy(userName);
            ccd.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());

            // SETUP customer present address
            tempAddressEntity = new AddressEntity();
            tempAddressEntity.setType("T");
            tempAddressEntity.setStatus(true);
            tempAddressEntity.setCreatedBy(userName);
            tempAddressEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
        }

        if(customerModel.getOwnershipType().equalsIgnoreCase(OwnershipType.COMPANY.getLabel())) {
        	vehicleRCEntity.setOwnershipType(OwnershipType.COMPANY.getId());
        } else if(customerModel.getOwnershipType().equalsIgnoreCase(OwnershipType.POLICE.getLabel())) {
        	vehicleRCEntity.setOwnershipType(OwnershipType.POLICE.getId());
        } else if(customerModel.getOwnershipType().equalsIgnoreCase(OwnershipType.STU_VEHICLES.getLabel())) {
        	vehicleRCEntity.setOwnershipType(OwnershipType.STU_VEHICLES.getId());
        } else if(customerModel.getOwnershipType().equalsIgnoreCase(OwnershipType.ORGANIZATION.getLabel())) {
        	vehicleRCEntity.setOwnershipType(OwnershipType.ORGANIZATION.getId());
        }
        RegistrationCategoryEntity regCat = registrationCategoryDAO.getByCode(customerModel.getRegCategory());
        vehicleRCEntity.setRegCategory(regCat);
        // set rta-office w.r.t. present address mandal
        MandalEntity tempMandalEntity = mandalDAO.getByMandalCode(Integer.valueOf(customerModel.getTemp_mandal_code()));
        vehicleRCEntity.setRtaOfficeId(getRtaOffice(tempMandalEntity.getRtaOfficeEntity(),regCat.getRegistrationCategoryId()));
        vehicleRCEntity.setChassisNumber(StringsUtil.isNullOrEmpty(vehicleRCEntity.getChassisNumber()) ? ""
                : vehicleRCEntity.getChassisNumber());
        vehicleRCEntity.setModifiedBy(userName);
        vehicleRCEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        vehicleDAO.saveOrUpdate(vehicleRCEntity);

        ccd.setDisplayName(customerModel.getRc_name());
        ccd.setCompanyName(customerModel.getFirst_name());
        ccd.setRepresentedBy(customerModel.getRepresentedBy());
        ccd.setEmail(customerModel.getEmailid());
        ccd.setEmergencyNo(customerModel.getPhoneNo());
        ccd.setMobile(customerModel.getPhoneNo());
        ccd.setModifiedBy(userName);
        ccd.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        ccd.setPermanentPhoneNo(customerModel.getPhoneNo());
        ccd.setAternatePhoneNo(customerModel.getAlternate_mobile());
        // ccd.setIsSecondVehicle(customerModel.getIs_second_vehicle());
        ccd.setStatus(String.valueOf(currentStep));
        ccd.setVehicleRegRta(customerModel.getVehregrta());
        ccd.setVehicleRegRtaCode(customerModel.getVehregrtacode());
        ccd.setBloodGroup(customerModel.getBloodGroup());
        customerCorporateDAO.saveOrUpdate(ccd);

        tempAddressEntity.setDoorNo(customerModel.getTemp_buildingName());
        tempAddressEntity.setStreet(customerModel.getTemp_street());
        // Should change, once other ownership type introduced
        tempAddressEntity.setUserType(UserType.ROLE_CUSTOMER_CORP);
        tempAddressEntity.setUserId(ccd.getCustCorpDtlsId());
        tempAddressEntity.setCity(customerModel.getTemp_town());
        tempAddressEntity.setCountry(Long.valueOf(1)); // India always, will change in future
        // mandal not same as aadhar mandal
        tempAddressEntity.setState(stateDao.getStateByCode(customerModel.getTemp_statecode()).getStateId());
        tempAddressEntity.setDistrict(districtDAO.getDistrictByCode(customerModel.getTemp_district_code()).getDistrictId());
        tempAddressEntity.setMandal(tempMandalEntity);
        tempAddressEntity.setPinCode(customerModel.getTemp_pincode());
        tempAddressEntity.setModifiedBy(userName);
        tempAddressEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        addressDAO.saveOrUpdate(tempAddressEntity);
        
        try{
            setInsideApAndMandal(customerModel, true, vehicleRCEntity.getVehicleRcId());
        } catch(Exception ex){
            log.error("Exception Occured while saving not matched data");
        }

        return vehicleRCEntity.getVehicleRcId();
        
    }

    @Override
    @Transactional
    public CustomerDetailsRequestModel getCustomerInfoStepOne(String vehicleRcId) {
        CustomerDetailsRequestModel customerDetailsRequestModel = new CustomerDetailsRequestModel();
            
        VehicleRCEntity vrc = vehicleDAO.get(Long.valueOf(vehicleRcId));
        if(ObjectsUtil.isNull(vrc)){
            throw new IllegalArgumentException("Invalid VehicleRcId !!");
        } else if(vrc.getOwnershipType()==OwnershipType.COMPANY.getId() || vrc.getOwnershipType()==OwnershipType.POLICE.getId() 
        		|| vrc.getOwnershipType()==OwnershipType.STU_VEHICLES.getId() || vrc.getOwnershipType()==OwnershipType.ORGANIZATION.getId()) {
        	return getCustomerCorpInfo(vehicleRcId);
        }
        /* VehicleRC details based on the VechileRCID */
        customerDetailsRequestModel.setCurrentStep(String.valueOf(vrc.getCurrentStep()));
        OwnershipTypeEntity ownership = ownershipDAO.getById(Long.valueOf(vrc.getOwnershipType()));
        customerDetailsRequestModel.setOwnershipType(ownership.getCode());
        customerDetailsRequestModel.setOwnershipTypeName(ownership.getName());
        customerDetailsRequestModel.setRegCategory(vrc.getRegCategory().getCode());
        customerDetailsRequestModel.setRegCategoryName(vrc.getRegCategory().getName());
        customerDetailsRequestModel.setRtaOfficeId(vrc.getRtaOfficeId().getCode());
        customerDetailsRequestModel.setIsAadharVerified(vrc.isAadharVerified());
        /* CustIndividualDetailsEntity details based on the VechileRCID */
        CustIndividualDetailsEntity cide = customerDAO.getByVehicleRcId(Long.valueOf(vehicleRcId));
        customerDetailsRequestModel.setUid(cide.getAadharNo());
        customerDetailsRequestModel.setEmailid(cide.getEmailId());
        customerDetailsRequestModel.setDob(cide.getDateOfBirth());
        customerDetailsRequestModel.setRc_name(cide.getDisplayName());
        customerDetailsRequestModel.setPhoneNo(cide.getEmergencyNo());
        customerDetailsRequestModel.setFather_name(cide.getFatherName());
        customerDetailsRequestModel.setFirst_name(cide.getFirstName());
        customerDetailsRequestModel.setGender(cide.getGender());
        customerDetailsRequestModel.setPhysically_challenged(cide.getIsDisabled());
        customerDetailsRequestModel.setInvalidCarriage(cide.getIsInvalidCarriage());
        customerDetailsRequestModel.setSameAsAddAadhar(cide.getIsSameAsAddrAadhar());
//        customerDetailsRequestModel.setIs_second_vehicle(cide.getIsSecondVehicle());
        customerDetailsRequestModel.setMobileNumber(cide.getMobileNo());
        customerDetailsRequestModel.setPassportnum(cide.getPassportNo());
        customerDetailsRequestModel.setPassportvalidupto(cide.getPassportValidUpto());
        customerDetailsRequestModel.setPermanentPhoneNumber(cide.getPermanentPhoneNo());
        customerDetailsRequestModel.setStatus(cide.getStatus());
        customerDetailsRequestModel.setSurName(cide.getSurName());
        customerDetailsRequestModel.setVehregrta(cide.getVechicleRegRta());
        customerDetailsRequestModel.setVehregrtacode(cide.getVehicleRegRtaCode());
        customerDetailsRequestModel.setQualificationName(qualificationDAO.getByCode(cide.getQualificationId().intValue()).getName());
        customerDetailsRequestModel.setQualification(cide.getQualificationId().intValue());
        customerDetailsRequestModel.setAlternate_mobile(cide.getAlternateMobileNo());
        customerDetailsRequestModel.setBloodGroup(cide.getBloodGroup());
        customerDetailsRequestModel.setDependentAddrs(cide.isDependentAddrs());
        customerDetailsRequestModel.setAddressPerson(cide.getAddressPerson());
        customerDetailsRequestModel.setNameOfDependent(cide.getNameOfDependent());
        
        /* AddressEntity details based on the user_id(CustomerID) */
        PermanentAddressEntity pAe = permanentAddressDAO.findByUserId(cide.getCustIndDtlId(), AddressType.AADHAR);
        // customerDAO.getAddressDetailsByUserId(cide.getCustIndDtlId(), "P");
        if(ObjectsUtil.isNotNull(pAe)){
        	customerDetailsRequestModel.setPerm_pincode(pAe.getPinCode());
        	customerDetailsRequestModel.setPerm_po(pAe.getPostOffice());
        	customerDetailsRequestModel.setPerm_country(pAe.getCountry());
        	customerDetailsRequestModel.setPerm_statecode(pAe.getState());
        	customerDetailsRequestModel.setPermCity(pAe.getCity());
        	customerDetailsRequestModel.setPerm_district_name(pAe.getDistrict());
        	customerDetailsRequestModel.setPerm_mandal_name(pAe.getMandal());
        	customerDetailsRequestModel.setPerm_street(pAe.getStreet());
        	customerDetailsRequestModel.setPerm_buildingName(pAe.getDoorNo());
        }

        customerDetailsRequestModel.setSameAsAddAadhar(cide.getIsSameAsAddrAadhar());

        AddressEntity tAe = customerDAO.getAddressDetailsByUserId(cide.getCustIndDtlId(), "T");
        if(ObjectsUtil.isNotNull(tAe)){
        	MandalEntity mandalTAe = tAe.getMandal();
        	DistrictEntity districtTAe = mandalTAe == null ? null : mandalTAe.getDistrictEntity();
        	StateEntity stateTAe = districtTAe == null ? null : districtTAe.getStateEntity();
        	// changes relative to adding name to mandal, district,state
        	customerDetailsRequestModel.setTempCity(tAe.getCity());
        	customerDetailsRequestModel
        	.setTemp_country(stateTAe == null ? null : stateTAe.getCountryEntity().getName());
        	customerDetailsRequestModel.setTemp_district_name(districtTAe == null ? null : districtTAe.getName());
        	customerDetailsRequestModel.setTemp_district_code(districtTAe == null ? null : districtTAe.getCode());
        	customerDetailsRequestModel.setTemp_buildingName(String.valueOf(tAe.getDoorNo()));
        	customerDetailsRequestModel.setTemp_pincode(tAe.getPinCode());
        	customerDetailsRequestModel.setTemp_statecode(stateTAe == null ? null : stateTAe.getCode());
        	customerDetailsRequestModel.setTemp_statename(stateTAe == null ? null : stateTAe.getName());
        	
        	customerDetailsRequestModel.setTemp_street(String.valueOf(tAe.getStreet()));
        	customerDetailsRequestModel.setTemp_mandal_name(tAe.getMandal().getName());
        	customerDetailsRequestModel.setTemp_mandal_code(tAe.getMandal().getCode().toString());
        	customerDetailsRequestModel.setMandal(String.valueOf(tAe.getMandal().getMandalId()));
        	customerDetailsRequestModel.setDistrict(tAe.getMandal().getDistrictEntity().getName());
        	customerDetailsRequestModel.setState(tAe.getMandal().getDistrictEntity().getStateEntity().getName());
        	customerDetailsRequestModel.setStatus(String.valueOf(tAe.getStatus()));
        }
        
        //set ismatched
        customerDetailsRequestModel = setInsideApAndMandal(customerDetailsRequestModel, false, null);
        
        return customerDetailsRequestModel;
    }

    @Transactional
    public CustomerDetailsRequestModel getCustomerCorpInfo(String vehicleRcId) {
        CustomerDetailsRequestModel customerDetailsRequestModel = new CustomerDetailsRequestModel();
        
        VehicleRCEntity vrc = customerCorporateDAO.getVehicleRcIdUsingVehicleRcId(vehicleRcId);
        if(ObjectsUtil.isNull(vrc)){
            throw new IllegalArgumentException("Invalid VehicleRcId !!");
        }
        /* VehicleRC details based on the VechileRCID */
        customerDetailsRequestModel.setCurrentStep(String.valueOf(vrc.getCurrentStep()));
        OwnershipTypeEntity ownership = ownershipDAO.getById(Long.valueOf(vrc.getOwnershipType()));
        customerDetailsRequestModel.setOwnershipType(ownership.getCode());
        customerDetailsRequestModel.setOwnershipTypeName(ownership.getName());
        customerDetailsRequestModel.setRegCategory(vrc.getRegCategory().getCode());
        customerDetailsRequestModel.setRegCategoryName(vrc.getRegCategory().getName());
        customerDetailsRequestModel.setRtaOfficeId(vrc.getRtaOfficeId().getCode());
        customerDetailsRequestModel.setIsAadharVerified(vrc.isAadharVerified());
        /* CustIndividualDetailsEntity details based on the VechileRCID */
        CustCorporateDetailsEntity cide = customerCorporateDAO.getByVehicleRcId(Long.valueOf(vehicleRcId));

        customerDetailsRequestModel.setUid(cide.getAadharNo());
        customerDetailsRequestModel.setEmailid(cide.getEmail());
        customerDetailsRequestModel.setRc_name(cide.getDisplayName());
        customerDetailsRequestModel.setFirst_name(cide.getCompanyName());
        customerDetailsRequestModel.setRepresentedBy(cide.getRepresentedBy());
        customerDetailsRequestModel.setPhoneNo(cide.getEmergencyNo());
        // customerDetailsRequestModel.setIs_second_vehicle(cide.getIsSecondVehicle());
        customerDetailsRequestModel.setMobileNumber(cide.getMobile());
        customerDetailsRequestModel.setPermanentPhoneNumber(cide.getPermanentPhoneNo());
        customerDetailsRequestModel.setStatus(cide.getStatus());
        customerDetailsRequestModel.setVehregrta(cide.getVehicleRegRta());
        customerDetailsRequestModel.setVehregrtacode(cide.getVehicleRegRtaCode());
        customerDetailsRequestModel.setAlternate_mobile(cide.getAternatePhoneNo());
        customerDetailsRequestModel.setBloodGroup(cide.getBloodGroup());

        AddressEntity tAe = customerCorporateDAO.getAddressDetailsByUserId(cide.getCustCorpDtlsId(), "T");

        MandalEntity mandalTAe = tAe.getMandal();
        DistrictEntity districtTAe = mandalTAe == null ? null : mandalTAe.getDistrictEntity();
        StateEntity stateTAe = districtTAe == null ? null : districtTAe.getStateEntity();
        // changes relative to adding name to mandal, district,state
        customerDetailsRequestModel.setTempCity(tAe.getCity());
        customerDetailsRequestModel
                .setTemp_country(stateTAe == null ? null : stateTAe.getCountryEntity().getName());
        customerDetailsRequestModel.setTemp_district_name(districtTAe == null ? null : districtTAe.getName());
        customerDetailsRequestModel.setTemp_district_code(districtTAe == null ? null : districtTAe.getCode());
        customerDetailsRequestModel.setTemp_buildingName(String.valueOf(tAe.getDoorNo()));
        customerDetailsRequestModel.setTemp_pincode(tAe.getPinCode());
        customerDetailsRequestModel.setTemp_statecode(stateTAe == null ? null : stateTAe.getCode());
        customerDetailsRequestModel.setTemp_statename(stateTAe == null ? null : stateTAe.getName());
        customerDetailsRequestModel.setTemp_street(String.valueOf(tAe.getStreet()));
        customerDetailsRequestModel.setTemp_mandal_name(tAe.getMandal().getName());
        customerDetailsRequestModel.setTemp_mandal_code(tAe.getMandal().getCode().toString());
        
        customerDetailsRequestModel.setPermCity(tAe.getCity());
        customerDetailsRequestModel.setPerm_country(stateTAe == null ? null : stateTAe.getCountryEntity().getName());
        customerDetailsRequestModel.setPerm_district_name(districtTAe == null ? null : districtTAe.getName());
        customerDetailsRequestModel.setPerm_buildingName(String.valueOf(tAe.getDoorNo()));
        customerDetailsRequestModel.setPerm_pincode(tAe.getPinCode());
        customerDetailsRequestModel.setPerm_statecode(stateTAe == null ? null : stateTAe.getName());
        customerDetailsRequestModel.setPerm_street(String.valueOf(tAe.getStreet()));
        customerDetailsRequestModel.setPerm_mandal_name(tAe.getMandal().getName());
        
        customerDetailsRequestModel.setMandal(String.valueOf(tAe.getMandal().getMandalId()));
        customerDetailsRequestModel.setDistrict(tAe.getMandal().getDistrictEntity().getName());
        customerDetailsRequestModel.setState(tAe.getMandal().getDistrictEntity().getStateEntity().getName());
        customerDetailsRequestModel.setStatus(String.valueOf(tAe.getStatus()));
        
        //set ismatched
        customerDetailsRequestModel = setInsideApAndMandal(customerDetailsRequestModel, false, null);
        
        return customerDetailsRequestModel;
    }
    
    private CustomerDetailsRequestModel setInsideApAndMandal(CustomerDetailsRequestModel cm, boolean isSave, Long vehicleRcId){
        log.info("CustomerDetailsRequestModel => getPerm_statecode():" + cm.getPerm_statecode() 
                + " getPerm_district_name():" + cm.getPerm_district_name() + " getPerm_mandal_name():" + cm.getPerm_mandal_name());
        
        /*
         * am.setStatecode("Andhra Pradesh"); am.setDistrict("ANANTAPUR");
         * am.setMandal_name("AGALI");
         */
        
        if(ObjectsUtil.isNull(cm.getPerm_statecode())){
        	return cm;
        }
        if("AP".equalsIgnoreCase(cm.getPerm_statecode()) || "Andhra Pradesh".equalsIgnoreCase(cm.getPerm_statecode())){
            cm.setInsideAP(true);
        } else {
            cm.setInsideAP(false);
        }
        
        StateModel state = stateService.getStateByName(cm.getPerm_statecode());
        if(ObjectsUtil.isNull(state)){
            cm.setStateMatched(false);
            cm.setDistrictMatched(false);
            cm.setMandalMatched(false);
        } else {
            cm.setStateMatched(true);
            cm.setStateMatchedCode(state.getCode());
        }
        
        DistrictModel district = districtService.getDistrictByName(cm.getPerm_district_name());
        if(ObjectsUtil.isNull(district)){
            cm.setDistrictMatched(false);
            cm.setMandalMatched(false);
        } else if(cm.isStateMatched()){
            cm.setDistrictMatched(true);
            cm.setDistrictMatchedCode(district.getCode());
        }
        if(!StringsUtil.isNullOrEmpty(cm.getPerm_mandal_name())){
            MandalModel mandal = mandalService.getMandal(cm.getPerm_mandal_name(), cm.getPerm_district_name());
            if(ObjectsUtil.isNull(mandal)){
                cm.setMandalMatched(false);
            } else if(cm.isDistrictMatched()){
                cm.setMandalMatched(true);
                cm.setMandalMatchedCode(mandal.getCode().toString());
            }
        } else {
            cm.setMandalMatched(false);
        }
        
        if(isSave){
            if(!(cm.isStateMatched() && cm.isDistrictMatched() && cm.isMandalMatched())){
                log.info("saving unmatched address data...." + vehicleRcId);
                PAddressNotMatchedEntity entity = pAddressNotMatchedDAO.getPAddressNotMatchedByVehId(vehicleRcId);
                if(ObjectsUtil.isNull(entity)){
                    entity = new PAddressNotMatchedEntity();
                }
                entity.setVehicleRcId(vehicleRcId);
                entity.setAadharNo(cm.getUid());
                entity.setAadharState(cm.getPerm_statecode());
                entity.setAadharDistrict(cm.getPerm_district_name());
                entity.setAadharMandal(cm.getPerm_mandal_name());
                entity.setCustomerState(cm.getTemp_statecode());
                entity.setCustomerDistrict(cm.getTemp_district_code());
                entity.setCustomerMandal(cm.getTemp_mandal_code());
                entity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
                pAddressNotMatchedDAO.saveOrUpdate(entity);
            }
        }
        
        return cm;
    }
    
    @Override
    @Transactional
    public SaveUpdateResponse updatePermanentAdrsWithAadhar(Long userId, String userName, Long vehicleRcId, AadharModel aadhar) {
        VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);
        SaveUpdateResponse response = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Updated Successfully.", vehicleRcId.toString());
        if(ObjectsUtil.isNull(vehicleRCEntity)){
            log.info("Invalid VehicleRcId : " + vehicleRcId);
            response.setStatus(SaveUpdateResponse.FAILURE);
            response.setMessage("Invalid VehicleRcId.");
            return response;
        }
        if(ObjectsUtil.isNull(aadhar.getUid())){
            response.setStatus(SaveUpdateResponse.FAILURE);
            response.setMessage("Aadhar Number Not Found !");
            return response;
        }
        if(vehicleRCEntity.getTrStatus() != Status.APPROVED.getValue()){
            log.info("Invalid Action(TR yet not generated). vehicleRcId : " + vehicleRcId);
            response.setStatus(SaveUpdateResponse.FAILURE);
            response.setMessage("Invalid Action(TR yet not generated).");
            return response;
        }
        PermanentAddressEntity permAddressEntity = null;
        if(vehicleRCEntity.getOwnershipType() == OwnershipType.INDIVIDUAL.getId()){
            CustIndividualDetailsEntity customer = customerDAO.getByVehicleRcId(vehicleRcId);
            customer.setAadharNo(aadhar.getUid().toString());
            permAddressEntity = permanentAddressDAO.findByUserId(customer.getCustIndDtlId(), AddressType.AADHAR);
            permAddressEntity.setDoorNo(aadhar.getHouse());
            permAddressEntity.setStreet(aadhar.getStreet());
            permAddressEntity.setMandal(aadhar.getMandal_name());
           // permAddressEntity.setCity(aadhar.getSubdist());
            permAddressEntity.setDistrict(aadhar.getDistrict_name());
            permAddressEntity.setState(aadhar.getStatecode());
            permAddressEntity.setCountry("India");
            permAddressEntity.setPostOffice(aadhar.getPo());
            permAddressEntity.setPinCode(aadhar.getPincode());
            permAddressEntity.setModifiedBy(userName);
            permAddressEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
            permanentAddressDAO.saveOrUpdate(permAddressEntity);
        }
        vehicleRCEntity.setAadharNo(aadhar.getUid().toString());
        vehicleRCEntity.setAadharVerified(true);
        //send to RTA approval
        vehicleRCEntity.setCcoActionStatus(Status.PENDING.getValue());
        vehicleRCEntity.setMviActionStatus(Status.PENDING.getValue());
        vehicleRCEntity.setAoActionStatus(Status.PENDING.getValue());
        vehicleRCEntity.setRtoActionStatus(Status.PENDING.getValue());
        vehicleRCEntity.setProcessStatus(Status.PENDING.getValue());
        vehicleRCEntity.setModifiedBy(userName);
        vehicleRCEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        vehicleDAO.saveOrUpdate(vehicleRCEntity);
        return response;
    }

    @Override
    @Transactional
    public CustomerModel getCustomerIndvDetail(Long vehicleRcId) {
        CustIndividualDetailsEntity customer = customerDAO.getByVehicleRcId(vehicleRcId);
        CustomerModel customerModel = null;
        if (customer != null) {
            customerModel = new CustomerModel();
            customerModel.setDisplayName(customer.getDisplayName());
            customerModel.setFirstName(customer.getFirstName());
            customerModel.setLastName(customer.getSurName());
            customerModel.setFatherName(customer.getFatherName());
            customerModel.setGender(customer.getGender());
            customerModel.setDateOfBirth(customer.getDateOfBirth());
            customerModel.setQualificationId(customer.getQualificationId());
            customerModel.setBloodGroup(customer.getBloodGroup());
        }
        return customerModel;
    }

    @SuppressWarnings("incomplete-switch")
    @Override
    @Transactional
    public SaveUpdateResponse updateCustomerDetails(AddressChangeModel model, String userName) throws DataMismatchException{
        VehicleRCEntity vehicleRCEntity = vehicleDAO.get(model.getVehicleRcId());
        log.info("Getting vehicle Rc Details: " + model.getVehicleRcId());
        log.info(model);
        AddressEntity addressEntity = null;
        AadharModel aadharModel=null;
        RtaOfficeEntity rtaOfficeEntity = null;
        VehicleFinanceDtlstEntity vehicelDtls = null;
        CustIndividualDetailsEntity custIndividualDetailsEntity = null;
        String name = null;
        UserEntity userEntity = null;
        if (model.getServiceType() != ServiceType.OWNERSHIP_TRANSFER_DEATH){
            if (model.getServiceType() != ServiceType.FRESH_RC_FINANCIER) {
                rtaOfficeEntity = mandalDAO.getRTAOfficeByMandalCode(model.getMandalCode());
                if (ObjectsUtil.isNull(rtaOfficeEntity)) {
                    log.error("can't sync address because rta office for mandal code : " + model.getMandalCode() + " not found!");
                    throw new DataMismatchException("can't sync address because rta office not found!");
                }
            } else {
                vehicelDtls = vehicleFinanceDAO.getVehicleFinanceRcr(model.getVehicleRcId(), Status.APPROVED.getValue());
                if (ObjectsUtil.isNull(vehicelDtls)) {
                    log.error("no finance found for vehicleRcId : " + model.getVehicleRcId());
                    throw new DataMismatchException();
                }
                Long financerId = vehicelDtls.getFinancerId();
                userEntity = userDAO.findByUserId(financerId);
                if (ObjectsUtil.isNull(userEntity)) {
                    log.debug("unable to find user for userId " + financerId);
                    throw new DataMismatchException("unable to find user for userId");
                }
                model.setDisplayName(userEntity.getFirstName());
                model.setMobileNo(userEntity.getMobile());
                model.setEmailId(userEntity.getEmail());
            }
        }
        try {
            if (model.getServiceType() == ServiceType.ADDRESS_CHANGE) {

                switch (OwnershipType.getOwnershipType(vehicleRCEntity.getOwnershipType())) {
                    case INDIVIDUAL:
                        custIndividualDetailsEntity = customerDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                        addressEntity = customerDAO.getAddressDetailsByUserId(custIndividualDetailsEntity.getCustIndDtlId(), "T");
                        custIndividualDetailsEntity = getCustIndividualDetailsEntity(custIndividualDetailsEntity, aadharModel, model, userName);
                        customerDAO.update(custIndividualDetailsEntity);
                        break;
                    case POLICE:
                    case COMPANY:
                    case STU_VEHICLES:
                    case ORGANIZATION:
                        CustCorporateDetailsEntity custCorporateDetailsEntity = customerCorporateDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                        addressEntity = customerCorporateDAO.getAddressDetailsByUserId(custCorporateDetailsEntity.getCustCorpDtlsId(), "T");
                        custCorporateDetailsEntity = getCustCorporateDetailsEntity(custCorporateDetailsEntity, aadharModel, model, userName);
                        customerCorporateDAO.update(custCorporateDetailsEntity);
                        break;
                }
            }else{
                try{
                    aadharModel = aadharTCSInfoService.getAadharDetails(Long.valueOf(model.getAadharNumber()));
                } catch (Exception ex) {
                    log.error("Exception in Address Details:" + ex.getMessage());
                }
                if (ObjectsUtil.isNull(aadharModel)) {
                    throw new DataMismatchException();
                }else {
                	Long customerId=null;
                	if(ObjectsUtil.isNull(model.getOwnershipTypeCode()) || ObjectsUtil.isNull(vehicleRCEntity.getOwnershipType()) || 
                	        OwnershipType.getOwnershipType(vehicleRCEntity.getOwnershipType()).getCode().equalsIgnoreCase(model.getOwnershipTypeCode())){
                	    switch (OwnershipType.getOwnershipType(vehicleRCEntity.getOwnershipType())) {
                            case INDIVIDUAL:
                                custIndividualDetailsEntity = customerDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                                name = custIndividualDetailsEntity.getFirstName() + " " + custIndividualDetailsEntity.getSurName();
                                addressEntity = customerDAO.getAddressDetailsByUserId(custIndividualDetailsEntity.getCustIndDtlId(), "T");
                                custIndividualDetailsEntity = getCustIndividualDetailsEntity(custIndividualDetailsEntity, aadharModel, model, userName);
                                customerId = custIndividualDetailsEntity.getCustIndDtlId();
                                customerDAO.update(custIndividualDetailsEntity);
                                String status = updatePermanetAddressDetails(customerId, aadharModel, userName, model.getServiceType());
                                log.info("Update In Permanent Address Table status : " + status);
                                break;
                            case POLICE:
                            case COMPANY:
                            case STU_VEHICLES:
                            case ORGANIZATION:
                                CustCorporateDetailsEntity custCorporateDetailsEntity =  customerCorporateDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                                name = custCorporateDetailsEntity.getCompanyName();
                                addressEntity = customerCorporateDAO.getAddressDetailsByUserId(custCorporateDetailsEntity.getCustCorpDtlsId(), "T");
                                custCorporateDetailsEntity = getCustCorporateDetailsEntity(custCorporateDetailsEntity, aadharModel, model, userName);
                                customerId = custCorporateDetailsEntity.getCustCorpDtlsId();
                                customerCorporateDAO.update(custCorporateDetailsEntity);
                                break;
                        }
                	} else {
                	    // either user going individual to company or company to individual
                	    if(OwnershipType.getOwnershipType(model.getOwnershipTypeCode()) == OwnershipType.INDIVIDUAL){
                	        // user going from company, police, stu etc to individual
                	        CustCorporateDetailsEntity cce =  customerCorporateDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                	        try{
                	            String result = custCorporateDetailsHistoryDAO.saveData(cce.getCustCorpDtlsId(), userName, DateUtil.toCurrentUTCTimeStamp(), model.getServiceType());
                	            log.info("Update In Customer Corporate history Table status : " + result);
                	        } catch (Exception ex) {
                	            log.error("Exception while making history In Customer Corporate history");
                	        }
                	        Long custIndId = changeToCustInd(model, userName, vehicleRCEntity, aadharModel);
                	        name = aadharModel.getName();
                	        addressEntity = customerCorporateDAO.getAddressDetailsByUserId(cce.getCustCorpDtlsId(), "T");
                	        if(ObjectsUtil.isNotNull(addressEntity)){
                                addressEntity.setUserType(UserType.ROLE_CUSTOMER_IND);
                                addressEntity.setUserId(custIndId);
                            } else {
                                log.error("Address entity is null ...... cust corp id " + cce.getCustCorpDtlsId());
                            }
                	        String status = insertUpdatePermanetAddressDetails(custIndId, aadharModel, userName, model.getServiceType());
                            log.info("Update In Permanent Address Table status : " + status);
                            // delete from cust_corporate if user is going to be individual
                            customerCorporateDAO.delete(cce);
                	    } 
                	    else {
                	        //user going from individual to company, police, stu etc
                	        CustIndividualDetailsEntity cde = customerDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                	        try{
                	            String result = custIndividualDetailsHistoryDAO.saveData(cde.getCustIndDtlId(), userName, DateUtil.toCurrentUTCTimeStamp(), model.getServiceType());
                	            log.info("Update In Customer Individuals history Table status : " + result);
                	        } catch (Exception ex) {
                	            log.error("Exception while making history In Customer Individuals history Table");
                	        }
                	        addressEntity = customerDAO.getAddressDetailsByUserId(cde.getCustIndDtlId(), "T");
                	        name = model.getCompanyName();
                	        Long custCorpId = changeToCustCorp(model, userName, vehicleRCEntity);
                	        if(ObjectsUtil.isNotNull(addressEntity)){
                	            addressEntity.setUserType(UserType.ROLE_CUSTOMER_CORP);
                	            addressEntity.setUserId(custCorpId);
                	        } else {
                	            log.error("Address entity is null ...... cust ind id " + cde.getCustIndDtlId());
                	        }
                	        // delete permanent if user is going to be cust corporate. since in corporate no perm address
                	        PermanentAddressEntity pEntity = permanentAddressDAO.findByUserId(cde.getCustIndDtlId(), AddressType.AADHAR);
                	        if(ObjectsUtil.isNotNull(pEntity)){
                	            try{
                	                String result = permanentAddressHistoryDAO.saveData(pEntity.getpAddressId(), userName, DateUtil.toCurrentUTCTimeStamp(), model.getServiceType());
                	                log.info("one row inserted In Permanent Address history Table status : " + result);
                	            } catch (Exception ex) { }
                	            permanentAddressDAO.delete(pEntity);
                	        }
                	        // delete from cust_ind if user is going to be company
                	        customerDAO.delete(cde);
                	    }
                	}
                	if(!ObjectsUtil.isNull(model.getOwnershipTypeCode()) && !ObjectsUtil.isNull(OwnershipType.getOwnershipType(model.getOwnershipTypeCode()))){
                	    vehicleRCEntity.setOwnershipType(OwnershipType.getOwnershipType(model.getOwnershipTypeCode()).getId());
                	} else {
                	    log.error(" Ownership type not Found .....");
                	}
                
                	vehicleRCEntity.setAadharNo(model.getAadharNumber());
                    vehicleRCEntity.setModifiedBy(userName);
                    vehicleRCEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
                    vehicleRCEntity.setIsPrinted(false);
                    vehicleRCEntity.setRcPrintDate(null);
                    
                    try{
                    	String result = vehicleRCChangeHistoryDAO.saveData(model.getVehicleRcId(), userName, DateUtil.toCurrentUTCTimeStamp(), model.getServiceType());
                    	log.info("one row Inserted In Address history Table status : " + result);
                    }catch (Exception e) {}
                } /*
                   * else if (model.getServiceType() == ServiceType.OWNERSHIP_TRANSFER_AUCTION) {
                   * 
                   * } else if (model.getServiceType() == ServiceType.OWNERSHIP_TRANSFER_SALE) {
                   * 
                   * }
                   */
            }
            if(model.getServiceType() != ServiceType.OWNERSHIP_TRANSFER_DEATH){
                try {
                    String result = addressHistoryDAO.saveData(addressEntity.getAddressId(), userName,
                            DateUtil.toCurrentUTCTimeStamp(), model.getServiceType());
                    log.info("one row Inserted In Address history Table status : " + result);
                } catch (Exception ex) {
                }
            }
            
            if (model.getServiceType() == ServiceType.FRESH_RC_FINANCIER) {
                
                AddressEntity financerAddress = addressDAO.findByUserIdAndType(userEntity.getUserId(), "T");
                if (ObjectsUtil.isNull(financerAddress)) {
                    log.error("local address not found for this financer");
                    throw new DataMismatchException("no address found for financer within Andhra Pradesh");
                    // TODO 
                } else {
                    addressEntity.setDoorNo(financerAddress.getDoorNo());
                    addressEntity.setStreet(financerAddress.getStreet());
                    addressEntity.setCity(financerAddress.getCity());
                    addressEntity.setMandal(mandalDAO.getByMandalCode(financerAddress.getMandal().getCode()));
                    addressEntity.setDistrict(financerAddress.getMandal().getDistrictEntity().getDistrictId());
                    addressEntity.setState(financerAddress.getMandal().getDistrictEntity().getStateEntity().getStateId());
                    addressEntity.setPinCode(financerAddress.getPinCode());
                    addressEntity.setModifiedBy(userName);
                    addressEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
                    rtaOfficeEntity = mandalDAO.getRTAOfficeByMandalCode(financerAddress.getMandal().getCode());
//                    addressEntity.setUserType(model.getUserType());
                }
                FinancerSeizedVehiclesEntity seizedVehicleLog = new FinancerSeizedVehiclesEntity();
                seizedVehicleLog.setCreatedBy(userName);
                seizedVehicleLog.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
                seizedVehicleLog.setStatus(Status.APPROVED.getValue());
                seizedVehicleLog.setUser(userEntity);
                seizedVehicleLog.setVehicleRcId(vehicleRCEntity);
                financerSeizedVehiclesDAO.saveOrUpdate(seizedVehicleLog);
                
                // Terminate current finance on this vehicle
                vehicelDtls.setFinanceTerminated(Boolean.TRUE);
                vehicelDtls.setModifiedBy(userName);
                vehicelDtls.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
                vehicleFinanceDAO.saveOrUpdate(vehicelDtls);
                addressDAO.update(addressEntity);
            } else if(model.getServiceType() != ServiceType.OWNERSHIP_TRANSFER_DEATH){
                addressEntity.setDoorNo(model.getDoorNo());
                addressEntity.setStreet(model.getStreet());
                addressEntity.setCity(model.getCity());
                addressEntity.setMandal(mandalDAO.getByMandalCode(model.getMandalCode()));
                addressEntity.setDistrict(districtDAO.getDistrictByCode(model.getDistrictCode()).getDistrictId());
                addressEntity.setState(stateDao.getStateByCode(model.getStateCode()).getStateId());
                addressEntity.setPinCode(model.getPostOffice());
                addressEntity.setModifiedBy(userName);
                addressEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
                addressDAO.update(addressEntity);
            }
            if (model.getServiceType() != ServiceType.OWNERSHIP_TRANSFER_DEATH){
                vehicleRCEntity.setRtaOfficeId(getRtaOffice(rtaOfficeEntity,vehicleRCEntity.getRegCategory().getRegistrationCategoryId()));
            }
            vehicleDAO.update(vehicleRCEntity);
            
            log.info("Save Address Details:");
            
            if((model.getServiceType() == ServiceType.OWNERSHIP_TRANSFER_SALE || model.getServiceType() == ServiceType.OWNERSHIP_TRANSFER_DEATH || model.getServiceType() == ServiceType.OWNERSHIP_TRANSFER_AUCTION) && RegistrationCategoryType.getRegistrationCategoryType(vehicleRCEntity.getRegCategory().getRegistrationCategoryId()) == RegistrationCategoryType.TRANSPORT){
                log.info("Going to Transfer/Surrender permit ............................");
               
                if(model.getServiceType() == ServiceType.OWNERSHIP_TRANSFER_AUCTION){
                    model.setPermitTransferType(PermitOptionType.SURRENDER.getLabel());
                }
                updatePermitDetails(name,model,vehicleRCEntity.getPrNumber());
            }

            try{
            	syncDocCustomer(vehicleRCEntity.getVehicleRcId(), model);
            } catch(Exception ex){
            	ex.printStackTrace();
            }
            
            if( model.getServiceType() == ServiceType.OWNERSHIP_TRANSFER_DEATH){
                log.info(":::::::::::::::::::::::::::::::::: Saving legal hier's consents details ::::::::::::::::::::::::::::");
                List<LegalHierDetailsModel> legalHierDetailsModel = model.getLegalHierDetails().getLegalHierDetailsModel();// != null ? model.getLegalHierDetails().getLegalHierDetailsModel() : null;
                if (!ObjectsUtil.isNull(legalHierDetailsModel)) {

                    LegalHierDetailsEntity legalHierDetailsEntity = new LegalHierDetailsEntity();
                    for (LegalHierDetailsModel legHierModel : legalHierDetailsModel) {
                        legalHierDetailsEntity.setLegalHierAadharNumber(legHierModel.getAadharNumber());
                        legalHierDetailsEntity.setIsConsent(legHierModel.getIsConsent());
                        legalHierDetailsEntity.setVehicleRcId(model.getVehicleRcId());
                        legalHierDetailsEntity.setPrNumber(vehicleRCEntity.getPrNumber() != null ? vehicleRCEntity.getPrNumber() :"");
                        legalHierDetailsEntity.setModifiedBy(userName);
                        legalHierDetailsEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
                        legalHierDetailsEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
                        legalHierDetailsDAO.saveOrUpdate(legalHierDetailsEntity);
                    }
                }
            }
            if(model.getServiceType() == ServiceType.OWNERSHIP_TRANSFER_SALE || model.getServiceType() == ServiceType.OWNERSHIP_TRANSFER_AUCTION
            		|| model.getServiceType() == ServiceType.OWNERSHIP_TRANSFER_DEATH){
            	FinancerSeizedVehiclesEntity fsve = financerSeizedVehiclesDAO.getSeizedVehicle(vehicleRCEntity.getVehicleRcId());
            	if(ObjectsUtil.isNotNull(fsve)){
            		fsve.setStatus(Status.OPEN.getValue());
            		fsve.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
            		financerSeizedVehiclesDAO.update(fsve);
            	}
            }
            
            vehicleService.updateRcApproverUserId(model.getActionModelList(), vehicleRCEntity.getVehicleRcId());
            
        } catch (Exception ex) {
            log.error("Exception in Address Details:", ex);
            throw new DataMismatchException();
        }
        return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Update Successfully", null);
    }
    
    public void syncDocCustomer(Long vehicleRcId, AddressChangeModel model){
    	List<DocSyncModel> docList = model.getDocList();
    	if(!ObjectsUtil.isNullOrEmpty(docList)){
    		for(DocSyncModel doc : docList){
    			AttachmentDetailsEntity attachmentEntity = attachmentDetailsDAO.getAttatchDltIdByDocIdVehId(vehicleRcId, doc.getDocTypeId());
    			if(ObjectsUtil.isNotNull(attachmentEntity)){
    				Long timeStamp = DateUtil.toCurrentUTCTimeStamp();
    				//-----create attachment details history -------------------
    				try{
    					attachmentDetailsHistoryDAO.saveData(attachmentEntity.getAttachmentDlId(), UserType.ROLE_CITIZEN.getLabel(), timeStamp, model.getServiceType());
    				} catch(Exception ex){
    					log.error("Exception in syncDocCustomer for creating history attachment history id : " + attachmentEntity.getAttachmentDlId());
    				}
    				
    				attachmentEntity.setStatus(Status.APPROVED.getValue());
    				attachmentEntity.setAttachmentFrom(doc.getAttachmentFrom());
    				attachmentEntity.setSource(doc.getSource());
    				attachmentEntity.setAttachmentTitle(doc.getTitle());
    				attachmentEntity.setFileName(doc.getFilename());
    				attachmentEntity.setModifiedBy(UserType.ROLE_CITIZEN.getLabel());
    				attachmentEntity.setModifiedOn(timeStamp);
    				attachmentEntity.setUserRole(UserType.ROLE_CITIZEN.getValue());
    				attachmentDetailsDAO.update(attachmentEntity);
    			}
    		}
    	}
    }
    
    public Long changeToCustInd(AddressChangeModel model, String userName, VehicleRCEntity vehicleRc, AadharModel aadhar){
        log.info("in changeToCustInd ......");
        CustIndividualDetailsEntity cde = new CustIndividualDetailsEntity();
        cde.setVehicleRcId(vehicleRc);
        cde.setAadharNo(model.getAadharNumber());
        cde.setCreatedBy(userName);
        cde.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
        cde.setDateOfBirth(aadhar.getDob());
        cde.setAlternateEmail(model.getEmailId());
        cde.setIsDisabled(false);
        cde.setIsInvalidCarriage(false);
        cde.setDisplayName(model.getDisplayName());
        cde.setEmailId(model.getEmailId());
        cde.setEmergencyNo(model.getMobileNo());
        String[] name = aadhar.getName().split(" ", 2);
        if(name.length == 1){
            cde.setFirstName(name[0]);
            cde.setSurName(null);
        }else{
            cde.setFirstName(name[0]);
            cde.setSurName(name[1]);
        }
        cde.setFatherName(aadhar.getCo());
        //cde.setGender(("M".equalsIgnoreCase(customerModel.getGender()) || "Male".equalsIgnoreCase(customerModel.getGender())) ? "Male" : "Female");
        if("M".equalsIgnoreCase(aadhar.getGender()) || "Male".equalsIgnoreCase(aadhar.getGender())) {
            cde.setGender("Male");
        } else if("NOT_RECORDED".equalsIgnoreCase(aadhar.getGender())) {
            cde.setGender("NOT_RECORDED");
        } else {
            cde.setGender("Female");
        }
        cde.setMobileNo(model.getMobileNo());
        cde.setModifiedBy(userName);
        cde.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        cde.setPassportNo("NA");
        cde.setPassportValidUpto("NA");
        cde.setPermanentPhoneNo(model.getMobileNo());
        cde.setAlternateMobileNo(model.getMobileNo());
        cde.setQualificationId(Long.valueOf(Qualification.NA.getValue()));
        cde.setIsSameAsAddrAadhar(false);
        cde.setStatus(String.valueOf(1));// 1 for active, 0 for inactive
        cde.setVechicleRegRta(vehicleRc.getRtaOfficeId().getName());
        cde.setVehicleRegRtaCode(vehicleRc.getRtaOfficeId().getCode());
        cde.setBloodGroup("NA");
        cde.setDependentAddrs(false);
        customerDAO.save(cde);
        return cde.getCustIndDtlId();
    }
    
    public Long changeToCustCorp(AddressChangeModel model, String userName, VehicleRCEntity vehicleRc){
        CustCorporateDetailsEntity ccd = new CustCorporateDetailsEntity();
        ccd.setVehicleRcId(vehicleRc);
        ccd.setAadharNo(model.getAadharNumber());
        ccd.setCreatedBy(userName);
        ccd.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
        ccd.setDisplayName(model.getDisplayName());
        ccd.setCompanyName(model.getCompanyName());
        ccd.setRepresentedBy(model.getRepresentativeName());
        ccd.setEmail(model.getEmailId());
        ccd.setEmergencyNo("NA");
        ccd.setMobile(model.getMobileNo());
        ccd.setModifiedBy(userName);
        ccd.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        ccd.setPermanentPhoneNo(model.getMobileNo());
        ccd.setAternatePhoneNo(model.getMobileNo());
        ccd.setStatus(1+"");
        ccd.setVehicleRegRta(vehicleRc.getRtaOfficeId().getName());
        ccd.setVehicleRegRtaCode(vehicleRc.getRtaOfficeId().getCode());
        ccd.setBloodGroup("NA");
        customerCorporateDAO.save(ccd);
        return ccd.getCustCorpDtlsId();
    }
    
    private void updatePermitDetails(String name, AddressChangeModel addressChangeModel, String prNumber) {
        log.info("Going to transfer/surrender permit with name : " + name);
		List<PermitHeaderEntity> permitHeaderEntityList = permitHeaderDAO.getPermitActiveByPr(prNumber);
		if (addressChangeModel.getPermitTransferType() != null
				&& addressChangeModel.getPermitTransferType().equalsIgnoreCase(PermitOptionType.TRANSFER.getLabel())) {
			for (PermitHeaderEntity headerEntity : permitHeaderEntityList) {
				try {
					PermitHeaderEntity newPermitHeader = (PermitHeaderEntity) headerEntity.clone();
					headerEntity.setStatus(Status.CLOSED.getValue());
					headerEntity.setModifiedOn(new Timestamp(new Date().getTime()));
					headerEntity.setModifiedBy(addressChangeModel.getAadharNumber());
					permitHeaderDAO.update(headerEntity);
					
					newPermitHeader.setHolderAadharNo(addressChangeModel.getAadharNumber());
					newPermitHeader.setHolderMobileNo(addressChangeModel.getMobileNo());
					newPermitHeader.setHolderNm(name);
					permitHeaderDAO.save(newPermitHeader);
				} catch (CloneNotSupportedException e) {
					 log.error("clone not supported.", e);
				} 
			}
		} else if (addressChangeModel.getPermitTransferType() != null
				&& addressChangeModel.getPermitTransferType().equalsIgnoreCase(PermitOptionType.SURRENDER.getLabel())) {
		    if(!ObjectsUtil.isNull(permitHeaderEntityList)){
		        for (PermitHeaderEntity headerEntity : permitHeaderEntityList) {
		            headerEntity.setStatus(Status.SURRENDERED.getValue());
		            headerEntity.setModifiedOn(new Timestamp(new Date().getTime()));
		            headerEntity.setModifiedBy(addressChangeModel.getAadharNumber());
		            permitHeaderDAO.update(headerEntity);
		            
		            List<PermitDetailsMappingEntity> permitDetailsMappingEntityList = permitDetailsMappingDAO
		                    .getByPermitNoNdDetailType(headerEntity, Status.OPEN.getValue());
		            for (PermitDetailsMappingEntity entity : permitDetailsMappingEntityList) {
		                entity.setStatus(Status.SURRENDERED.getValue());
		                entity.setModifiedOn(new Timestamp(new Date().getTime()));
		                entity.setModifiedBy(prNumber);
		                permitDetailsMappingDAO.update(entity);
		            }
		        }
		    }
		}
	}
    
    private CustIndividualDetailsEntity getCustIndividualDetailsEntity(
            CustIndividualDetailsEntity custIndividualDetailsEntity, AadharModel aadharModel, AddressChangeModel model, String userName) {
        if(model.getServiceType() != ServiceType.ADDRESS_CHANGE){
        	String[] name = aadharModel.getName().split(" ", 2);
        	if(name.length == 1){
        		custIndividualDetailsEntity.setFirstName(name[0]);
        		custIndividualDetailsEntity.setSurName(null);
    		}else{
        		custIndividualDetailsEntity.setFirstName(name[0]);
        		custIndividualDetailsEntity.setSurName(name[1]);
    		}
            custIndividualDetailsEntity.setFatherName(aadharModel.getCo());
            custIndividualDetailsEntity.setDisplayName(model.getDisplayName());
            custIndividualDetailsEntity.setAadharNo(String.valueOf(aadharModel.getUid()));
            custIndividualDetailsEntity.setDateOfBirth(aadharModel.getDob());
            custIndividualDetailsEntity.setGender(aadharModel.getGender());
            custIndividualDetailsEntity.setMobileNo(ObjectsUtil.isNotNull(model.getMobileNo()) ? model.getMobileNo() : "");
            custIndividualDetailsEntity.setPermanentPhoneNo(ObjectsUtil.isNotNull(model.getMobileNo()) ? model.getMobileNo() : "");
            custIndividualDetailsEntity.setAlternateEmail(model.getEmailId());
            custIndividualDetailsEntity.setEmergencyNo("NA");
            custIndividualDetailsEntity.setPanNo(model.getPanNumber() != null ? model.getPanNumber() : "NA");
            custIndividualDetailsEntity.setAlternateMobileNo(model.getAlternateMobileNumber() != null ? model.getAlternateMobileNumber() : "NA");
            custIndividualDetailsEntity.setBloodGroup(model.getBloodGroup() != null ? model.getBloodGroup() : "NA");
            custIndividualDetailsEntity.setIsSecondVehicle(false);
            //----- set Qualification as N.A.
            try{
                custIndividualDetailsEntity.setQualificationId(Long.valueOf(Qualification.NA.getValue()));
            } catch(Exception e){
                log.error("Parsing error for casting Qualification NA");
            }
        }
//        if (model.getServiceType() != ServiceType.FRESH_RC_FINANCIER) {
        custIndividualDetailsEntity.setEmailId(model.getEmailId());
        custIndividualDetailsEntity.setMobileNo(model.getMobileNo());
//        }
        custIndividualDetailsEntity.setModifiedBy(userName);
        custIndividualDetailsEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        try{
            String result = custIndividualDetailsHistoryDAO.saveData(custIndividualDetailsEntity.getCustIndDtlId(),
                    userName, DateUtil.toCurrentUTCTimeStamp(), model.getServiceType());
            log.info("Update In Customer Individuals history Table status : " + result);
        } catch (Exception ex) {
        }
        return custIndividualDetailsEntity;
    }

    private CustCorporateDetailsEntity getCustCorporateDetailsEntity(
            CustCorporateDetailsEntity custCorporateDetailsEntity, AadharModel aadharModel, AddressChangeModel model, String userName) {

        if (model.getServiceType() != ServiceType.ADDRESS_CHANGE) {
            custCorporateDetailsEntity.setAadharNo(String.valueOf(aadharModel.getUid()));
            custCorporateDetailsEntity.setRepresentedBy(aadharModel.getName());
            custCorporateDetailsEntity.setDisplayName(model.getDisplayName());
            custCorporateDetailsEntity.setCompanyName(model.getDisplayName());
            custCorporateDetailsEntity.setPanNo(model.getPanNumber()!=null ? model.getPanNumber() : "NA");
            custCorporateDetailsEntity.setMobile(model.getMobileNo());
        }
//        if (model.getServiceType() != ServiceType.FRESH_RC_FINANCIER) {
        custCorporateDetailsEntity.setMobile(model.getMobileNo());
        custCorporateDetailsEntity.setEmail(model.getEmailId());
//        }
        custCorporateDetailsEntity.setModifiedBy(userName);
        
        custCorporateDetailsEntity.setBloodGroup(model.getBloodGroup() != null ? model.getBloodGroup() : "NA");
        custCorporateDetailsEntity.setAternatePhoneNo(model.getAlternateMobileNumber() != null ? model.getAlternateMobileNumber() : "NA");
        
        custCorporateDetailsEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        try{
            String result = custCorporateDetailsHistoryDAO.saveData(custCorporateDetailsEntity.getCustCorpDtlsId(),
                    userName, DateUtil.toCurrentUTCTimeStamp(), model.getServiceType());
            log.info("Update In Customer Corporate history Table status : " + result);
        } catch (Exception ex) {
        }
        return custCorporateDetailsEntity;
    }
    
    private String updatePermanetAddressDetails(Long customerId, AadharModel aadharModel, String userName, ServiceType serviceType) {

        PermanentAddressEntity entity = permanentAddressDAO.findByUserId(customerId, AddressType.AADHAR);
        if (entity == null) {
            return SaveUpdateResponse.FAILURE;
        }
        try{
            String result = permanentAddressHistoryDAO.saveData(entity.getpAddressId(), userName, DateUtil.toCurrentUTCTimeStamp(), serviceType);
            log.info("one row inserted In Permanent Address history Table status : " + result);
        } catch (Exception ex) { }
        entity.setDoorNo(aadharModel.getHouse());
        entity.setStreet(aadharModel.getStreet());
        entity.setMandal(aadharModel.getMandal_name());
        entity.setCity(aadharModel.getVillage_name());
        entity.setDistrict(aadharModel.getDistrict_name());
        entity.setState(aadharModel.getStatecode());
        entity.setCountry("India");
        entity.setPostOffice(aadharModel.getPo());
        entity.setPinCode(aadharModel.getPincode());
        entity.setModifiedBy(userName);
        entity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        permanentAddressDAO.saveOrUpdate(entity);
        
        return SaveUpdateResponse.SUCCESS;
    }
    
    private String insertUpdatePermanetAddressDetails(Long customerId, AadharModel aadharModel, String userName, ServiceType serviceType) {

        PermanentAddressEntity entity = permanentAddressDAO.findByUserId(customerId, AddressType.AADHAR);
        if (entity == null) {
            entity = new PermanentAddressEntity();
        } else {
            try{
                String result = permanentAddressHistoryDAO.saveData(entity.getpAddressId(), userName, DateUtil.toCurrentUTCTimeStamp(), serviceType);
                log.info("one row inserted In Permanent Address history Table status : " + result);
            } catch (Exception ex) { }
        }
        entity.setUserId(customerId);
        entity.setDoorNo(aadharModel.getHouse());
        entity.setStreet(aadharModel.getStreet());
        entity.setMandal(aadharModel.getMandal_name());
        entity.setAddressType(AddressType.AADHAR.getValue());
        entity.setCity(aadharModel.getVillage_name());
        entity.setDistrict(aadharModel.getDistrict_name());
        entity.setState(aadharModel.getStatecode());
        entity.setCountry("India");
        entity.setPostOffice(aadharModel.getPo());
        entity.setPinCode(aadharModel.getPincode());
        entity.setModifiedBy(userName);
        entity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        permanentAddressDAO.saveOrUpdate(entity);
        
        return SaveUpdateResponse.SUCCESS;
    }
    
    private RtaOfficeEntity getRtaOffice(RtaOfficeEntity rtaOffice, Integer regCategoryId) {
        RtaOfficeEntity rtaOfficeEntity = null;
        switch (RegistrationCategoryType.getRegistrationCategoryType(regCategoryId)) {
            case NON_TRANSPORT:
                rtaOfficeEntity = rtaOffice;
                break;
            case TRANSPORT:
                if (OfficeType.RTA.getLabel().equalsIgnoreCase(rtaOffice.getOfficeType())) {
                    rtaOfficeEntity = rtaOffice;
                } else {
                    if (rtaOffice.getCode().equalsIgnoreCase(rtaOffice.getParentOfficeCode())) {
                        rtaOfficeEntity = rtaOffice;
                    } else {
                        rtaOfficeEntity = rtaOfficeDAO.getRtaOfficeDetailsByCode(rtaOffice.getParentOfficeCode());
                    }
                }
                break;
        }
        
        return rtaOfficeEntity;
    }
    
    @Override
    @Transactional(rollbackFor = VehicleRcNotFoundException.class)
    public SaveUpdateResponse lockRC(String userName, RCListModel rcList) throws VehicleRcNotFoundException{
    	SaveUpdateResponse res = new SaveUpdateResponse();
    	res.setStatus(SaveUpdateResponse.SUCCESS);
    	String foundPR = "";
    	String notFoundPR = "";
    	for(String rc : rcList.getRcList()){
    		log.info("Locking rc : " + rc);
    		VehicleRCEntity rcEntity = vehicleDAO.getVehicleRCByPRNumber(rc);
    		if(ObjectsUtil.isNull(rcEntity)){
    			notFoundPR = notFoundPR + rc + ", ";
    			continue;
    		} else {
    			foundPR = foundPR + rc + ", ";
    		}
    		int preRCStatus = rcEntity.getPrStatus();
    		rcEntity.setPrStatus(Status.LOCKED.getValue());
    		rcEntity.setModifiedBy(userName);
    		rcEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
    		vehicleDAO.update(rcEntity);
    		
    		RCLockEntity rcLock = rCLockDAO.getLastEntity(rc, null);
    		if(ObjectsUtil.isNull(rcLock)){
    			rcLock = new RCLockEntity();
    			rcLock.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
        		rcLock.setCreatedBy(userName);
    		} else {
    			rcLock.setModifiedBy(userName);
    			rcLock.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
    		}
    		rcLock.setVehicleRcId(rcEntity.getVehicleRcId());
    		rcLock.setPrNumber(rcEntity.getPrNumber());
    		rcLock.setCurPrStatus(Status.LOCKED.getValue());
    		rcLock.setPrePrStatus(preRCStatus);
    		rcLock.setLockTime(DateUtil.toCurrentUTCTimeStamp());
    		rCLockDAO.save(rcLock);
    	}
    	if(!StringsUtil.isNullOrEmpty(notFoundPR)){
    		throw new VehicleRcNotFoundException("Vehicle not found with RCs : " + notFoundPR.substring(0, notFoundPR.length() - 1));
    	} else {
    		res.setMessage("PR Locked Successfully. PRs : " + foundPR.substring(0, foundPR.length() - 1));
    	}
    	return res;
    }
    
    @Override
    @Transactional(rollbackFor = VehicleRcNotFoundException.class)
    public SaveUpdateResponse unLockRC(String userName, RCListModel rcList, Long dateTime) throws VehicleRcNotFoundException{
    	SaveUpdateResponse res = new SaveUpdateResponse();
    	res.setStatus(SaveUpdateResponse.SUCCESS);
    	String foundPR = "";
    	String notFoundPR = "";
    	for(String rc : rcList.getRcList()){
    		log.info("Un-locking rc : " + rc);
    		VehicleRCEntity rcEntity = vehicleDAO.getVehicleRCByPRNumber(rc);
    		RCLockEntity rcLock = rCLockDAO.getLastEntity(rc, Status.LOCKED);
    		if(ObjectsUtil.isNull(rcEntity)){
    			notFoundPR = notFoundPR + rc + ", ";
    			continue;
    		}
    		if(ObjectsUtil.isNull(rcLock)){
    			log.error("Vehicle RC is not locked. RC : " + rc);
    		} else {
    			if(dateTime < rcLock.getLockTime()){
    				continue;
    			}
    			foundPR = foundPR + rc + ", ";
    			Status currStatus = Status.getStatus(rcLock.getPrePrStatus());
    			if(currStatus == Status.LOCKED){
    				currStatus = Status.APPROVED;
    			}
    			rcLock.setCurPrStatus(currStatus.getValue());
    			rcLock.setPrePrStatus(rcLock.getCurPrStatus());
    			rcLock.setModifiedBy(userName);
    			rcLock.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
    			rCLockDAO.update(rcLock);
    			
    			rcEntity.setPrStatus(currStatus.getValue());
    			rcEntity.setModifiedBy(userName);
    			rcEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
    			vehicleDAO.update(rcEntity);
    		}
    	}
    	if(!StringsUtil.isNullOrEmpty(notFoundPR)){
    		throw new VehicleRcNotFoundException("Vehicle not found with RCs : " + notFoundPR.substring(0, notFoundPR.length() - 1));
    	} else {
    		res.setMessage("Un-Locked Successfully. PRs : " + (StringsUtil.isNullOrEmpty(foundPR) ? " " : foundPR.substring(0, foundPR.length() - 1)));
    	}
    	return res;
    }
    
    @Override
    @Transactional
    public SaveUpdateResponse moveAadharPendingToRTA(Long vehicleRcId, String aadharNo) {
    	SaveUpdateResponse res = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS);
    	VehicleRCEntity rcEntity = vehicleDAO.get(vehicleRcId);
    	if(ObjectsUtil.isNull(rcEntity)){
    		res.setStatus(SaveUpdateResponse.FAILURE);
    		res.setMessage("Vehicle not found !!!");
    		return res;
    	}
    	if(StringsUtil.isNullOrEmpty(aadharNo)){
    		res.setStatus(SaveUpdateResponse.FAILURE);
    		res.setMessage("Aadhar number can not be null or empty !!!");
    		return res;
    	}
    	if(!aadharNo.equals(rcEntity.getAadharNo())){
    		res.setStatus(SaveUpdateResponse.FAILURE);
    		res.setMessage("Provided aadhar number not matched with existing aadhar number !!!");
    		return res;
    	}
    	if(Status.getStatus(rcEntity.getPrStatus()) != Status.PENDING ||
    			!StringsUtil.isNullOrEmpty(rcEntity.getPrNumber())){
    		res.setStatus(SaveUpdateResponse.FAILURE);
    		res.setMessage("RC is not in pending state !!!");
    		return res;
    	}
    	if(Status.getStatus(rcEntity.getProcessStatus()) != Status.AADHAR_PENDING){
    		res.setStatus(SaveUpdateResponse.FAILURE);
    		res.setMessage("Application is not in state of Aadhar Pending !!!");
    		return res;
    	}
    	rcEntity.setAadharVerified(true);
    	rcEntity.setCcoActionStatus(Status.PENDING.getValue());
    	rcEntity.setMviActionStatus(Status.PENDING.getValue());
    	rcEntity.setAoActionStatus(Status.PENDING.getValue());
    	rcEntity.setRtoActionStatus(Status.PENDING.getValue());
    	rcEntity.setProcessStatus(Status.PENDING.getValue());
    	rcEntity.setModifiedBy("ADMIN");
    	rcEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        vehicleDAO.saveOrUpdate(rcEntity);
        res.setMessage("Application moved to RTA end successfully !!!");
    	return res;
    }
}
