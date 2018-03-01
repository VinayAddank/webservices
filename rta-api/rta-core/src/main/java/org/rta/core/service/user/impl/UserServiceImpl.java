package org.rta.core.service.user.impl;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.customer.PAddressNotMatchedDAO;
import org.rta.core.dao.finance.FinancerDAO;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.master.MandalDAO;
import org.rta.core.dao.master.StateDAO;
import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.dao.user.AlterationAgencyUserDAO;
import org.rta.core.dao.user.BodyBuilderUserDAO;
import org.rta.core.dao.user.DealerDAO;
import org.rta.core.dao.user.DrivingInstituteUserDAO;
import org.rta.core.dao.user.DrivingInstituteUserInfoDAO;
import org.rta.core.dao.user.HazardousVehicleDrivingInstituteUserDAO;
import org.rta.core.dao.user.MedicalPractitionerUserDAO;
import org.rta.core.dao.user.PUCUserDAO;
import org.rta.core.dao.user.PermanentAddressDAO;
import org.rta.core.dao.user.RTAEmployeeDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.dao.user.UserTransferHistoryDAO;
import org.rta.core.entity.applicant.PAddressNotMatchedEntity;
import org.rta.core.entity.finance.FinancerMasterEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.user.AlterationAgencyUserEntity;
import org.rta.core.entity.user.BodyBuilderUserEntity;
import org.rta.core.entity.user.DealerEntity;
import org.rta.core.entity.user.DrivingInstituteUserEntity;
import org.rta.core.entity.user.DrivingInstituteUserInfoEntity;
import org.rta.core.entity.user.HazardousVehicleDrivingInstituteUserEntity;
import org.rta.core.entity.user.MedicalPractitionerUserEntity;
import org.rta.core.entity.user.PUCUserEntity;
import org.rta.core.entity.user.PermanentAddressEntity;
import org.rta.core.entity.user.RTAEmployeeEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.user.UserTransferHistoryEntity;
import org.rta.core.enums.AddressType;
import org.rta.core.enums.DrivingInstituteUserInfoType;
import org.rta.core.enums.UserType;
import org.rta.core.helper.user.UserHelper;
import org.rta.core.model.GeolocationModel;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.customer.CustomerDetailsRequestModel;
import org.rta.core.model.master.DistrictModel;
import org.rta.core.model.master.MandalModel;
import org.rta.core.model.master.StateModel;
import org.rta.core.model.user.UserModel;
import org.rta.core.model.user.UserSignupModel;
import org.rta.core.repository.UserRepository;
import org.rta.core.service.master.DistrictService;
import org.rta.core.service.master.MandalService;
import org.rta.core.service.master.StateService;
import org.rta.core.service.user.UserService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private DealerDAO dealerDao;

    @Autowired
    private RTAEmployeeDAO rtaEmpDao;

    @Autowired
    private AddressDAO addressDao;

    @Autowired
    private StateDAO stateDao;

    @Autowired
    private DistrictDAO distDao;
    
    @Autowired
    private RtaOfficeDAO rtaOfficeDAO;
    
    @Autowired
    private MandalDAO mandalDAO;
    
    @Autowired
    private MandalService mandalService;
    
    @Autowired
    private StateService stateService;
    
    @Autowired
    private DistrictService districtService;
    
    @Autowired
    private UserTransferHistoryDAO userTransferHistoryDAO;
    
    @Autowired
    PAddressNotMatchedDAO pAddressNotMatchedDAO;
    
    @Autowired
    PermanentAddressDAO permanentAddressDAO;
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private BodyBuilderUserDAO bodyBuilderUserDAO;
    
    @Autowired
    private FinancerDAO financerDAO;
    
    @Autowired
    private PUCUserDAO pucUserDAO;
    
    @Autowired
    private HazardousVehicleDrivingInstituteUserDAO hazardousVehicleDrivingInstituteUserDAO;
    
    @Autowired
    private MedicalPractitionerUserDAO medicalPractitionerUserDAO;
    
    @Autowired
    private AlterationAgencyUserDAO alterationAgencyUserDAO;
    
    @Autowired
    private DrivingInstituteUserDAO drivingInstituteUserDAO;
    
    @Autowired
    private DrivingInstituteUserInfoDAO drivingInstituteUserInfoDAO;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Value("${rta.user.changerequest.office}")
    private String office;
    
    @Value("${rta.user.changerequest.role}")
    private String role;
    
    @Value("${rta.user.changerequest.password}")
    private String password;
   
    @Value("${rta.user.changerequest.status}")
    private String status;

    @Transactional
    public UserModel findByUserId(Long id) {
        UserEntity userEntity = userRepo.findByUserId(id);
        if (null != userEntity) {
            UserModel um = userHelper.convertToModel(userEntity);
            if(UserType.ROLE_DEALER == userEntity.getUserType()){
                DealerEntity dealer = dealerDao.findByUserId(id);
                if (null != dealer) {
                    um.setShowRoomName(dealer.getName());
                    um.setFax(dealer.getFax());
                    um.setEligibleToPay(dealer.getEligibleToPay());
                    if(ObjectsUtil.isNull(dealer.getParentId())){
                        um.setType(UserModel.PARENT);
                    } else {
                        um.setParentId(dealer.getParentId());
                        um.setType(UserModel.CHILD);
                    }
                }
                AddressEntity address = addressDao.findByUserIdAndType(id, "T");
                if (null != address) {
                    um.setDoorNo(address.getDoorNo());
                    um.setStreet(address.getStreet());
                    um.setCity(address.getCity());
                    StateEntity state = stateDao.getEntity(StateEntity.class, address.getState());
                    um.setState(state != null ? state.getName() : "");
                    DistrictEntity dist = distDao.getEntity(DistrictEntity.class, address.getDistrict());
                    um.setDistrict(dist != null ? dist.getName() : "");
                    MandalEntity me = address.getMandal();
                    if (me != null) {
                        um.setMandalId(me.getMandalId());
                        um.setMandalName(me.getName());
                    }
                }
            } else {
                RTAEmployeeEntity rtaEmployee = rtaEmpDao.getByUserId(id);
                if (null != rtaEmployee) {
                    um.setDepartmentName(rtaEmployee.getDepartmentName());
                    um.setRtaOfficeCodeFrom(rtaEmployee.getDepartmentName());
                    RtaOfficeEntity roe = rtaEmployee.getRtaOfficeEntity();
                    um.setRtaOffice(roe != null ? roe.getName() : "");
                    um.setUserName(userEntity.getUserName());
                }
            }
            return um;
        }
        return null;
    }  
        @Transactional
    public UserModel findByUserName(String userName) {
        UserEntity userEntity = userRepo.findByUserName(userName.toUpperCase());
        if (null != userEntity) {
            if (UserType.ROLE_DEALER == userEntity.getUserType()) {
                return userHelper.convertToModel(userEntity);
            } else if (UserType.ROLE_CCO == userEntity.getUserType() || UserType.ROLE_MVI == userEntity.getUserType()
                    || UserType.ROLE_AO == userEntity.getUserType() || UserType.ROLE_RTO == userEntity.getUserType()
                    || UserType.ROLE_DTC == userEntity.getUserType()) {
                UserModel model = userHelper.convertToModel(userEntity);
                RTAEmployeeEntity rtaEmployee = rtaEmpDao.getByUserId(userEntity.getUserId());
                if (null != rtaEmployee) {
                    model.setDepartmentName(rtaEmployee.getDepartmentName());
                    model.setRtaOfficeCodeFrom(rtaEmployee.getDepartmentName());
                    RtaOfficeEntity roe = rtaEmployee.getRtaOfficeEntity();
                    if (!ObjectsUtil.isNull(roe)) {
                        model.setRtaOffice(roe.getName());
                        model.setRtaOfficeCode(roe.getCode());
                    }
                }
                return model;
            } else {
                return userHelper.convertToModel(userEntity);
            }
        }
        return null;
    }

    public List<UserModel> findAllUsers(String userType) {
        List<UserEntity> ue = new ArrayList<UserEntity>();
        if (!StringsUtil.isNullOrEmpty(userType)) {
            userRepo.findByUserType(UserType.getUserType(userType.toUpperCase())).iterator().forEachRemaining(ue::add);
        }else{
            userRepo.findAll().iterator().forEachRemaining(ue::add);
       }
       return (List<UserModel>) userHelper.convertToModelList(ue);
    }
    
	@Override
	public List<UserType> getUserRoles(String userDept) {
		if("RTA".equalsIgnoreCase(userDept)){
			List<UserType> userType = new ArrayList<UserType>();
			userType.add(UserType.ROLE_CCO);
			userType.add(UserType.ROLE_MVI);
			userType.add(UserType.ROLE_AO);
			userType.add(UserType.ROLE_RTO);
			return userType;
		}
		return Arrays.asList(UserType.values());
	}
	
    @Transactional
    @Override
    public SaveUpdateResponse changePswd(Long userId, String oldPswd, String newPswd){
        UserEntity userEntity = userRepo.findByUserId(userId);
        SaveUpdateResponse response = new SaveUpdateResponse();
        if (!ObjectsUtil.isNull(userEntity)) {
            if(passwordEncoder.matches(oldPswd, userEntity.getPassword())){
                userEntity.setPassword(passwordEncoder.encode(newPswd));
                userRepo.save(userEntity);
                response.setStatus(SaveUpdateResponse.SUCCESS);
                response.setMessage("Password changed successfully.");
            } else {
                throw new IllegalArgumentException("Invalid UserId or Password !!");
            }
        } else {
            throw new IllegalArgumentException("Invalid UserId !!");
        }
        return response;
    }
    
    @Transactional
    @Override
    public SaveUpdateResponse changeRequest(UserModel userModel, String requestType, String userName)
            throws DataIntegrityViolationException, InvalidDataAccessApiUsageException {
    	String msg=null;
    	UserTransferHistoryEntity entity = new UserTransferHistoryEntity();
    	UserEntity userEntity = userRepo.findByUserName(userModel.getUserName());
	    if (ObjectsUtil.isNull(userEntity)) {
	        throw new IllegalArgumentException("Invalid Request..!!");
        }
	    userEntity.setModifiedBy(userName);
	    userEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
	    if(requestType.equalsIgnoreCase(role)) {
	        msg="Successfully Transfer Role";
	        entity.setTransferFrom(userEntity.getUserType().getLabel());
	        userEntity.setUserType(userModel.getUserType());
	        userRepo.save(userEntity);
	        entity.setTransferTo(userModel.getUserType().getLabel());
    	}
	    else if(requestType.equalsIgnoreCase(office)) {
	        msg="Successfully Transfer User";
	    	RTAEmployeeEntity rtaEntity = rtaEmpDao.getByUserId(userEntity.getUserId());
	    	rtaEntity.setDepartmentName(userModel.getRtaOfficeCodeTo());
	        rtaEntity.setModifiedBy(userName);
	        rtaEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
	        rtaEntity.setRtaOfficeEntity(rtaOfficeDAO.getRtaOfficeDetailsByCode(userModel.getRtaOfficeCodeTo()));
	        rtaEmpDao.saveOrUpdate(rtaEntity);
	        entity.setTransferFrom(userModel.getRtaOfficeCodeFrom());
	        entity.setTransferTo(userModel.getRtaOfficeCodeTo());
    	logger.info(":::::::::::::::transferUser:::::::::::::::::End");
	    }
	    else if(requestType.equalsIgnoreCase(password)) {
	        msg="Successfully Reset Password";
	        entity.setTransferFrom(String.valueOf(userEntity.getPassword()));
            userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
            userRepo.save(userEntity);
          	entity.setTransferTo(String.valueOf(userModel.getPassword()));
	    }
	    else if(requestType.equalsIgnoreCase(status)) {
	        msg="Successfully Deactive User";
	        entity.setTransferFrom(String.valueOf(userEntity.getStatus()));
	        userEntity.setStatus(userModel.getStatus());
	        userRepo.save(userEntity);
	        entity.setTransferTo(String.valueOf(userModel.getStatus()));
        }
	    entity.setUserRole(userEntity.getUserType());
        entity.setUserName(userModel.getUserName());
        entity.setTransferBy(userName);
        entity.setTransferDate(DateUtil.toCurrentUTCTimeStamp());
        userTransferHistoryDAO.saveOrUpdate(entity);
    	return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, userModel.getUserName());
    }

	@Transactional
	@Override
	public SaveUpdateResponse registerUser(UserModel userModel, String userName) 
			throws DataIntegrityViolationException, InvalidDataAccessApiUsageException {
		
		RTAEmployeeEntity rtaEntity = new RTAEmployeeEntity();
		UserEntity userEntity = userHelper.convertToEntity(userModel);
		userEntity.setCreatedBy(userName);
		userEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		userEntity.setModifiedBy(userName);
		userEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
		rtaEntity.setUserEntity(userEntity);
		rtaEntity.setCreatedBy(userName);
		rtaEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		rtaEntity.setModifiedBy(userName);
		rtaEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		rtaEntity.setEmailId(userModel.getEmailId());
		rtaEntity.setFax(userModel.getFax());
		rtaEntity.setMobile(userModel.getMobile());
		rtaEntity.setPhone(userModel.getPhone());
		rtaEntity.setSignFileName(userModel.getUserSignature());
		rtaEntity.setStatus(true);
		rtaEntity.setDepartmentName(userModel.getDepartmentName());
		rtaEntity.setRtaOfficeEntity(rtaOfficeDAO.getRtaOfficeDetailsByCode(userModel.getDepartmentName()));
		rtaEmpDao.saveOrUpdate(rtaEntity);
		logger.info(":::::::::::::::registerUser:::::::::::::::::End"+userModel.getUserName());
        return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Registration Successfully Completed", userModel.getUserName());
	}
	
	@Transactional
    @Override
    public UserSignupModel registerUser(UserSignupModel user) {
	    if (user == null) {
	        return null;
	    }
	    Long currentTime = DateUtil.toCurrentUTCTimeStamp();
	    UserEntity userEntity = new UserEntity();
	    userEntity.setBusinessDescription(user.getBusinessDescription());
	    userEntity.setCompanyType(user.getCompanyType());
	    userEntity.setCreatedBy("admin");
	    userEntity.setCreatedOn(currentTime);
	    userEntity.setDateOfFirmRegistration(user.getDateOfFirmRegistration());
	    userEntity.setEmail(user.getOfficeEmailId());
	    CustomerDetailsRequestModel cdrm = user.getCustomerDetails();
	    userEntity.setFirstName(cdrm.getFirst_name());
	    userEntity.setInstitutionName(user.getInstitutionName());
	    userEntity.setLastName(cdrm.getLast_name());
	    userEntity.setMobile(cdrm.getMobileNumber());
	    userEntity.setModifiedBy("admin");
	    userEntity.setModifiedOn(currentTime);
	    userEntity.setPanNumber(user.getPanNumber());
	    userEntity.setPassword(passwordEncoder.encode(user.getLoginDetails().getPassword()));
	    userEntity.setPhone(user.getOfficeContactNumber());
	    userEntity.setRocNumber(user.getRocNumber());
	    userEntity.setStatus(Boolean.TRUE);
	    userEntity.setUserName(user.getLoginDetails().getUsername());
	    UserType userType = UserType.getUserType(user.getUserType());
	    userEntity.setUserType(userType);
	    userEntity.setConsent(user.getConsent());
	    userEntity.setAadharNumber(user.getAadharNumber());
	    userDAO.saveOrUpdate(userEntity);
	    if (userType == UserType.ROLE_DEALER) {
	        DealerEntity dealer = new DealerEntity();
	        dealer.setName(user.getInstitutionName());
	        dealer.setUser(userEntity);
	        // setting geo location in case of dealer
	        GeolocationModel geolocation = user.getGeolocation();
	        if (!ObjectsUtil.isNull(geolocation)) {
		        dealer.setLat(geolocation.getLat());
		        dealer.setLng(geolocation.getLng());
	        }
	        dealer.setEligibleToPay(Boolean.TRUE);
	        dealerDao.saveOrUpdate(dealer);
	    } else if (userType == UserType.ROLE_ONLINE_FINANCER) {
	    	FinancerMasterEntity financerUser = new FinancerMasterEntity();
	        financerUser.setName(user.getInstitutionName());
	        financerUser.setUserId(userEntity.getUserId());
	        financerUser.setFinancierPanNumber(user.getPanNumber());
	        financerDAO.saveOrUpdate(financerUser);
	    } else if (userType == UserType.ROLE_BODY_BUILDER) {
	    	BodyBuilderUserEntity bodyBuilder = new BodyBuilderUserEntity();
	        bodyBuilder.setName(user.getInstitutionName());
	        bodyBuilder.setUser(userEntity);
	        bodyBuilderUserDAO.saveOrUpdate(bodyBuilder);
        } else if (userType == UserType.ROLE_PUC) {
        	PUCUserEntity pucUser = new PUCUserEntity();
	        pucUser.setName(user.getCustomerDetails().getFirst_name());
	        pucUser.setUser(userEntity);
	        pucUser.setClassOfVehicle(user.getClassOfVehicle());
	        pucUserDAO.saveOrUpdate(pucUser);
        } else if (userType == UserType.ROLE_ALTERATION_AGENCY) {
        	AlterationAgencyUserEntity alterationAgencyUser = new AlterationAgencyUserEntity();
	        alterationAgencyUser.setName(user.getCustomerDetails().getFirst_name());
	        alterationAgencyUser.setUser(userEntity);
	        alterationAgencyUser.setClassOfVehicle(user.getClassOfVehicle());
	        alterationAgencyUserDAO.saveOrUpdate(alterationAgencyUser);
        } else if (userType == UserType.ROLE_DRIVING_INSTITUTE) {
            DrivingInstituteUserEntity alterationAgencyUser = new DrivingInstituteUserEntity();
            alterationAgencyUser.setName(user.getCustomerDetails().getFirst_name());
            alterationAgencyUser.setUser(userEntity);
            alterationAgencyUser.setNoOfCars(user.getNumberOfCars());
            alterationAgencyUser.setNoOfDrivingInstructors(user.getNoOfDrivingInstructors());
            for (String rcNumber : user.getCarsRCNumbers()) {
                DrivingInstituteUserInfoEntity info = new DrivingInstituteUserInfoEntity();
                info.setNumber(rcNumber);
                info.setCreatedBy("admin");
                info.setCreatedOn(currentTime);
                info.setDrivingInstituteUser(alterationAgencyUser);
                info.setModifiedBy("admin");
                info.setModifiedOn(currentTime);
                info.setType(DrivingInstituteUserInfoType.RC.getValue());
                drivingInstituteUserInfoDAO.saveOrUpdate(info);
            }
            for (String dlNumber : user.getDrivingInstructorsDLNumbers()) {
                DrivingInstituteUserInfoEntity info = new DrivingInstituteUserInfoEntity();
                info.setNumber(dlNumber);
                info.setCreatedBy("admin");
                info.setCreatedOn(currentTime);
                info.setDrivingInstituteUser(alterationAgencyUser);
                info.setModifiedBy("admin");
                info.setModifiedOn(currentTime);
                info.setType(DrivingInstituteUserInfoType.DL.getValue());
                drivingInstituteUserInfoDAO.saveOrUpdate(info);
            }
        } else if (userType == UserType.ROLE_HAZARDOUS_VEH_TRAIN_INST) {
            HazardousVehicleDrivingInstituteUserEntity alterationAgencyUser = new HazardousVehicleDrivingInstituteUserEntity();
            alterationAgencyUser.setName(user.getCustomerDetails().getFirst_name());
            alterationAgencyUser.setUser(userEntity);
            hazardousVehicleDrivingInstituteUserDAO.saveOrUpdate(alterationAgencyUser);
        } else if (userType == UserType.ROLE_MEDICAL_PRACTITIONER) {
            MedicalPractitionerUserEntity alterationAgencyUser = new MedicalPractitionerUserEntity();
            alterationAgencyUser.setName(user.getCustomerDetails().getFirst_name());
            alterationAgencyUser.setUser(userEntity);
            alterationAgencyUser.setMedicalLicenseNumber(user.getMedicalLicenseNumber());
            medicalPractitionerUserDAO.saveOrUpdate(alterationAgencyUser);
        }
	    saveAddress(currentTime, userEntity, cdrm);
	    user.setUserId(userEntity.getUserId());
        return user;
    }
	
	private void saveAddress(Long currentTime, UserEntity userEntity, CustomerDetailsRequestModel cdrm) {
		boolean withinAP = false;
		if (cdrm.getTemp_statecode().equalsIgnoreCase("AP") || cdrm.getTemp_statecode().equalsIgnoreCase("Andhra Pradesh")) {
			AddressEntity address = new AddressEntity();
			address.setCity(cdrm.getTemp_town());
			address.setCountry(Long.valueOf(1));// India always, will change in future
			address.setCreatedBy("admin");
			address.setCreatedOn(currentTime);
			address.setDistrict(distDao.getDistrictByCode(cdrm.getTemp_district_code()).getDistrictId());
			address.setDoorNo(cdrm.getTemp_buildingName());
			MandalEntity tempMandalEntity = mandalDAO.getByMandalCode(Integer.valueOf(cdrm.getTemp_mandal_code()));
			address.setMandal(tempMandalEntity);
			address.setModifiedBy("admin");
			address.setModifiedOn(currentTime);
			address.setState(stateDao.getStateByCode(cdrm.getTemp_statecode()).getStateId());
			address.setStatus(Boolean.TRUE);// true mean active
			address.setStreet(cdrm.getTemp_street());
			address.setType("T");
			address.setUserId(userEntity.getUserId());
			address.setUserType(userEntity.getUserType());
			address.setPinCode(cdrm.getTemp_pincode());
			addressDao.saveOrUpdate(address);
			withinAP = true;
		} else {
			PermanentAddressEntity permAddress = new PermanentAddressEntity();
		    permAddress.setAddressType(AddressType.AADHAR.getValue());
		    permAddress.setCity(cdrm.getTemp_town());
		    permAddress.setCountry(cdrm.getTemp_country());
		    permAddress.setCreatedBy("admin");
		    permAddress.setCreatedOn(currentTime);
		    permAddress.setDistrict(cdrm.getTemp_district_name());
		    permAddress.setDoorNo(cdrm.getTemp_buildingName());
		    permAddress.setMandal(cdrm.getTemp_mandal_name());
		    permAddress.setModifiedBy("admin");
		    permAddress.setModifiedOn(currentTime);
		    permAddress.setPinCode(cdrm.getTemp_pincode());
		    permAddress.setPostOffice(cdrm.getTemp_pincode());
		    permAddress.setState(cdrm.getTemp_statecode());
		    permAddress.setUserId(userEntity.getUserId());
		    permAddress.setUserType(userEntity.getUserType());
		    permanentAddressDAO.saveOrUpdate(permAddress);
		    withinAP = false;
		}
		
		if (cdrm.getSameAsAddAadhar() && withinAP) {
			AddressEntity perm = new AddressEntity();
			perm.setCity(cdrm.getTemp_town());
			perm.setCountry(Long.valueOf(1));// India always, will change in future
			perm.setCreatedBy("admin");
			perm.setCreatedOn(currentTime);
			perm.setDistrict(distDao.getDistrictByCode(cdrm.getTemp_district_code()).getDistrictId());
			perm.setDoorNo(cdrm.getTemp_buildingName());
			MandalEntity tempMandalEntity1 = mandalDAO.getByMandalCode(Integer.valueOf(cdrm.getTemp_mandal_code()));
			perm.setMandal(tempMandalEntity1);
			perm.setModifiedBy("admin");
			perm.setModifiedOn(currentTime);
			perm.setState(stateDao.getStateByCode(cdrm.getTemp_statecode()).getStateId());
			perm.setStatus(Boolean.TRUE);// true mean active
			perm.setStreet(cdrm.getTemp_street());
			perm.setType("P");
			perm.setUserId(userEntity.getUserId());
			perm.setUserType(userEntity.getUserType());
			perm.setPinCode(cdrm.getTemp_pincode());
			addressDao.saveOrUpdate(perm);
		} else {
			boolean insertInPermanent = true;
			if (cdrm.getPerm_statecode().equalsIgnoreCase("AP") || cdrm.getPerm_statecode().equalsIgnoreCase("Andhra Pradesh")) {
				try{
					AddressEntity address = new AddressEntity();
					address.setCity(cdrm.getPerm_town());
					address.setCountry(Long.valueOf(1));// India always, will change in future
					address.setCreatedBy("admin");
					address.setCreatedOn(currentTime);
					address.setDistrict(distDao.getDistrictByCode(cdrm.getPerm_district_name()).getDistrictId());
					address.setDoorNo(cdrm.getPerm_buildingName());
					MandalEntity tempMandalEntity = mandalDAO.getByMandalCode(Integer.valueOf(cdrm.getPerm_mandal_name()));
					address.setMandal(tempMandalEntity);
					address.setModifiedBy("admin");
					address.setModifiedOn(currentTime);
					address.setState(stateDao.getStateByCode(cdrm.getPerm_statecode()).getStateId());
					address.setStatus(Boolean.TRUE);// true mean active
					address.setStreet(cdrm.getPerm_street());
					address.setType("P");
					address.setUserId(userEntity.getUserId());
					address.setUserType(userEntity.getUserType());
					address.setPinCode(cdrm.getPerm_pincode());
					addressDao.saveOrUpdate(address);
					insertInPermanent = false;
				} catch(Exception ex){
					logger.error("Exception while saving permanent address in address...");
				}
			}
			if(insertInPermanent){
				PermanentAddressEntity permAddress = new PermanentAddressEntity();
				permAddress.setAddressType(AddressType.AADHAR.getValue());
				permAddress.setCity(cdrm.getPerm_town());
				permAddress.setCountry(cdrm.getPerm_country());
				permAddress.setCreatedBy("admin");
				permAddress.setCreatedOn(currentTime);
				permAddress.setDistrict(cdrm.getPerm_district_name());
				permAddress.setDoorNo(cdrm.getPerm_buildingName());
				permAddress.setMandal(cdrm.getPerm_mandal_name());
				permAddress.setModifiedBy("admin");
				permAddress.setModifiedOn(currentTime);
				permAddress.setPinCode(cdrm.getPerm_pincode());
				permAddress.setPostOffice(cdrm.getPerm_po());
				permAddress.setState(cdrm.getPerm_statecode());
				permAddress.setUserId(userEntity.getUserId());
				permAddress.setUserType(userEntity.getUserType());
				permAddress.setPinCode(cdrm.getPerm_pincode());
				permanentAddressDAO.saveOrUpdate(permAddress);
			}
		}
	}
	
   private CustomerDetailsRequestModel setInsideApAndMandal(CustomerDetailsRequestModel cm, boolean isSave, Long vehicleRcId, UserEntity userEntity){
       logger.info("CustomerDetailsRequestModel => getPerm_statecode():" + cm.getPerm_statecode() 
                + " getPerm_district_name():" + cm.getPerm_district_name() + " getPerm_mandal_name():" + cm.getPerm_mandal_name());
        
        if(cm.getPerm_statecode().equalsIgnoreCase("AP") || cm.getPerm_statecode().equalsIgnoreCase("Andhra Pradesh")){
            cm.setInsideAP(true);
        } else {
            cm.setInsideAP(false);
        }
        
        StateModel state = stateService.getStateByNameOrCode(cm.getPerm_statecode());
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
                logger.info("saving unmatched address data...." + vehicleRcId);
                Long currentTime = DateUtil.toCurrentUTCTimeStamp();
                
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
   public boolean isUserExists(String aadharNumber, UserType userType) {
       if (!StringsUtil.isNullOrEmpty(aadharNumber)) {
           List<UserEntity> users = userDAO.findUsersByAadhar(aadharNumber, userType);
           return !ObjectsUtil.isNullOrEmpty(users);
       }
       logger.info("user with aadhar : " + aadharNumber + " and userType : " + userType + " doesn't exist");
       return false;
   }

   
    @Override
    @Transactional
    public List<String> bcryptPasswordForUsers(String userType, String userName) {
        List<String> failedFor = new ArrayList<String>();
        List<UserEntity> ue = new ArrayList<UserEntity>();
        if (!StringsUtil.isNullOrEmpty(userName)) {
            ue.add(userRepo.findByUserName(userName));
        } else if (!StringsUtil.isNullOrEmpty(userType)) {
            userRepo.findByUserType(UserType.getUserType(userType.toUpperCase())).iterator().forEachRemaining(ue::add);
        } else {
            userRepo.findAll().iterator().forEachRemaining(ue::add);
        }
        logger.info(" :::::::: START ::::: ");
        for (UserEntity user : ue) {
            if (null != user.getUserName() && null != user.getPassword()) {
                try {
                    String url = "http://119.235.51.68/decrypt.php?password={pwd}";
                    URI expanded = new UriTemplate(url).expand(user.getPassword());
                    url = URLDecoder.decode(expanded.toString(), "UTF-8");
                    String password = restTemplate.getForObject(url, String.class);
                    if (null != password) {
                        user.setPassword(passwordEncoder.encode(password));
                        userDAO.saveOrUpdate(user);
                    } else {
                        failedFor.add(user.getUserName());
                        logger.info("FAILED. Password is coming null from php api for user :" + user.getUserName());
                    }
                } catch (UnsupportedEncodingException e) {
                    failedFor.add(user.getUserName());
                    logger.error("Error in URL decoding of method updateUserPasswordHash:" + e.getMessage());
                }
            }
        }
        logger.info(" :::::::: END ::::: ");
        return failedFor;
    }
   
}
