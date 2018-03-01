package org.rta.core.service.fcfx.impl;

import java.io.File;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rta.core.dao.certificate.CFXNotesDAO;
import org.rta.core.dao.certificate.FcDetailsDAO;
import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.user.RTAEmployeeDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.certificate.CFXNotesEntity;
import org.rta.core.entity.certificate.FitnessCertificateEntity;
import org.rta.core.entity.master.CountryEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.user.RTAEmployeeEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.RegistrationCategoryType;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.enums.citizen.KeyType;
import org.rta.core.exception.UserNotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.certificate.CFXModel;
import org.rta.core.model.certificate.CFXNoticeModel;
import org.rta.core.model.certificate.FcDetailsModel;
import org.rta.core.model.citizen.CitizenServiceResponseModel;
import org.rta.core.service.certificate.FcDetailsService;
import org.rta.core.service.citizen.CitizenService;
import org.rta.core.service.citizen.ResponseModel;
import org.rta.core.service.citizen.model.AuthenticationModel;
import org.rta.core.service.citizen.model.CitizenTokenModel;
import org.rta.core.service.fcfx.FCFXService;
import org.rta.core.service.fcfx.exception.FitnessExpiredException;
import org.rta.core.service.fcfx.exception.FitnessNotFoundException;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class FCFXServiceImpl implements FCFXService {
    private static final Log log = LogFactory.getLog(FCFXServiceImpl.class);

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CFXNotesDAO cfxNotesDAO;

    @Override
    @Transactional
    public ResponseModel<?> createAppInCitizen(String prNumber, Long userId, String token)
            throws VehicleRcNotFoundException, FitnessNotFoundException,FitnessExpiredException {
        VehicleRCEntity vrc = vehicleDAO.getVehicleRc4Pr(prNumber);
        if (vrc == null) {
            log.error("vehicle rc not found for prnumber : " + prNumber);
            throw new VehicleRcNotFoundException("vehicle rc not found");
        }
        if (RegistrationCategoryType.getRegistrationCategoryType(
                vrc.getRegCategory().getRegistrationCategoryId()) != RegistrationCategoryType.TRANSPORT) {
            log.error("vehicle is non-transport, pr_number : " + prNumber);
            throw new IllegalArgumentException("vehicle is non-transport");
        }
        FitnessCertificateEntity entity = fcDetailsDAO.getFcDetails(vrc.getVehicleRcId(), "V");
        if (entity == null) {
            log.error("fitness not found for this vehicle, pr_number : " + prNumber);
            throw new FitnessNotFoundException("fitness not found for this vehicle");
        }
       
        //*** For Fitness Certificate Expiry 
		if (null != entity) {
			if (entity.getExpiryDate().getTime() / 1000 < DateUtil.toCurrentUTCTimeStamp()) {
				log.error("fitness is Expired for this vehicle, pr_number : " + prNumber);
				throw new FitnessExpiredException("fitness is Expired for this vehicle.");
			}
		}
        AuthenticationModel authenticationModel = new AuthenticationModel();
        authenticationModel.setPrNumber(prNumber);
        authenticationModel.setUid_num(userDAO.findByUserId(userId).getAadharNumber());
        authenticationModel.setKeyType(KeyType.PR);
        UserEntity userEntity = userDAO.getEntity(UserEntity.class, userId);
        authenticationModel.setAadhaarNumber(userEntity.getAadharNumber());
        CitizenServiceResponseModel<ResponseModel<CitizenTokenModel>> application = null;
        try {
            application = citizenService.login(authenticationModel, ServiceType.FC_ISSUE_CFX, token);
            return application.getResponseBody();
        } catch (HttpClientErrorException e) {
            log.error("unable to create Fitness - Issue of CFX note application");
            if (!ObjectsUtil.isNull(application)) {
                return application.getResponseBody();
            } else {
                throw e;
            }
        }
    }

    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private VehicleDetailsDAO vehicleDetailsDAO;

    @Autowired
    private FcDetailsService fcDetailsService;

    @Autowired
    private FcDetailsDAO fcDetailsDAO;

    @Override
    @Transactional
    public void saveCFXNoteDetails(CFXModel cfxModel, Long userId)
            throws UserNotFoundException, VehicleRcNotFoundException {
        UserEntity userEntity = userDAO.getEntity(UserEntity.class, cfxModel.getUserId());
        if (userEntity == null) {
            log.error("invalid user for userId : " + cfxModel.getUserId());
            throw new UserNotFoundException("user not found");
        }

        VehicleRCEntity vrc = vehicleDAO.getVehicleRCByPRNumber(cfxModel.getPrNumber());
        if (vrc == null) {
            log.error("invalid user for userId : " + userId);
            throw new VehicleRcNotFoundException("user not found");
        }

        Date currentTime = DateUtil.toCurrentUTCDate();
        CFXNotesEntity cfx = cfxNotesDAO.getByPrNumber(cfxModel.getPrNumber(), Status.APPROVED);
        if (cfx == null) {
            cfx = new CFXNotesEntity();
        }
        FitnessCertificateEntity entity = fcDetailsDAO.getFcDetails(vrc.getVehicleRcId(), "V");
        if (entity != null) {
            entity.setModifiedBy(userEntity.getUserName());
            entity.setModifiedOn(DateUtil.toCurrentUTCDate());
            entity.setStatusCode(Status.SUSPENDED.getValue());
            fcDetailsDAO.saveOrUpdate(entity);
        }
        cfx.setCfxEndorseInRC(cfxModel.getCfxEndorseInRC() == null ? false : cfxModel.getCfxEndorseInRC());
        cfx.setCfxNumber(getCFXNumber(cfxModel, userId));
        cfx.setCreatedBy(userEntity.getUserName());
        cfx.setCreatedOn(currentTime);
        cfx.setDefectNoticed(cfxModel.getDefectNoticed());
        cfx.setDestination(cfxModel.getDestination());
        cfx.setDlNumber(cfxModel.getDlNumber());
        cfx.setDriverName(cfxModel.getDriverName());
        cfx.setIsAccident(cfxModel.getIsAccident() == null ? false : cfxModel.getIsAccident());
        cfx.setMaximumSpeed(cfxModel.getMaximumSpeed());
        cfx.setModifiedBy(userEntity.getUserName());
        cfx.setPlaceOfChecking(cfxModel.getPlaceOfChecking());
        cfx.setPrNumber(cfxModel.getPrNumber());
        cfx.setVcrNumber(cfxModel.getVcrNumber());
        cfx.setApplicationNumber(cfxModel.getApplicationNumber());
        cfx.setStatus(Status.APPROVED.getValue());
        cfx.setUserId(cfxModel.getUserId());
        cfx.setTimeOfChecking(cfxModel.getTimeOfChecking());
        cfxNotesDAO.saveOrUpdate(cfx);
    }

    @Autowired
    private RTAEmployeeDAO rtaEmployeeDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private CustomerCorporateDAO customerCorporateDAO;

    @Override
    @Transactional
    public CFXNoticeModel getCFXNoteDetails(String prNumber) throws UserNotFoundException, VehicleRcNotFoundException {
        VehicleRCEntity vrc = vehicleDAO.getVehicleRCByPRNumber(prNumber);
        if (vrc == null) {
            log.error("prNumber : " + prNumber + " not found");
            throw new VehicleRcNotFoundException("user not found");
        }
        CFXNotesEntity cfx = cfxNotesDAO.getByPrNumber(prNumber, Status.APPROVED);
        CFXNoticeModel cfxNoticeModel = null;
        CFXModel cfxModel = null;
        if (cfx != null) {
            cfxNoticeModel = new CFXNoticeModel();
            cfxModel = new CFXModel();
            cfxModel.setApplicationNumber(cfx.getApplicationNumber());
            cfxModel.setCfxEndorseInRC(cfx.getCfxEndorseInRC());
            cfxModel.setCfxNoteId(cfx.getCfxNoteId());
            cfxModel.setCfxNumber(cfx.getCfxNumber());
            cfxModel.setDefectNoticed(cfx.getDefectNoticed());
            cfxModel.setDestination(cfx.getDestination());
            cfxModel.setDlNumber(cfx.getDlNumber());
            cfxModel.setDriverName(cfx.getDriverName());
            cfxModel.setIsAccident(cfx.getIsAccident());
            cfxModel.setMaximumSpeed(cfx.getMaximumSpeed());
            cfxModel.setPlaceOfChecking(cfx.getPlaceOfChecking());
            cfxModel.setPrNumber(cfx.getPrNumber());
            cfxModel.setStatus(Status.getStatus(cfx.getStatus()));
            cfxModel.setUserId(cfx.getUserId());
            cfxModel.setVcrNumber(cfx.getVcrNumber());
            cfxModel.setTimeOfChecking(cfx.getTimeOfChecking());
            if(null != cfx.getUserId()){
				UserEntity userEntity = userDAO.getEntity(UserEntity.class, cfx.getUserId());
				cfxModel.setOfficerName(userEntity.getFirstName());
				RTAEmployeeEntity employee = rtaEmployeeDAO.getByUserId(userEntity.getUserId());
				cfxModel.setOfficerSignature(employee.getSignFileName());
            } else {
            	cfxModel.setOfficerSignature("");
            }
            cfxNoticeModel.setCfx(cfxModel);

            VehicleDetailsEntity vde = vehicleDetailsDAO.getByVehicleRcId(vrc.getVehicleRcId());
            if (vde == null) {
                log.error("vehicleDetails for : " + prNumber + " not found");
                throw new VehicleRcNotFoundException("vehicleDetails not found");
            }
            cfxNoticeModel.setMake(vde.getMakerName());
            cfxNoticeModel.setModel(vde.getMakerClass());
            String signatureFile = new StringBuilder(baseURL).append(File.separator).append(signaturePath)
                    .append(File.separator).append("ahkhan_sig.JPG").toString();
            cfxNoticeModel.setUserSignature(signatureFile);
            FcDetailsModel fcDetailsModel = fcDetailsService.getSuspendedFcCertificate(vrc.getVehicleRcId());
            cfxNoticeModel.setFitnessIssueDate(fcDetailsModel.getIssueDate());
            cfxNoticeModel.setFitnessOfficerName(fcDetailsModel.getMviName());
            AddressEntity addressEntity = null;
            String ownerName = null;
            switch (OwnershipType.getOwnershipType(vrc.getOwnershipType())) {
            case INDIVIDUAL:
                CustIndividualDetailsEntity custIndividualDetailsEntity = customerDAO
                        .getByVehicleRcId(vrc.getVehicleRcId());
                addressEntity = customerDAO.getAddressDetailsByUserId(custIndividualDetailsEntity.getCustIndDtlId(),
                        "T");
                ownerName = custIndividualDetailsEntity.getFirstName();
                break;
            case POLICE:
            case COMPANY:
            case STU_VEHICLES:
            case ORGANIZATION:
                CustCorporateDetailsEntity custCorporateDetailsEntity = customerCorporateDAO
                        .getByVehicleRcId(vrc.getVehicleRcId());
                addressEntity = customerCorporateDAO
                        .getAddressDetailsByUserId(custCorporateDetailsEntity.getCustCorpDtlsId(), "T");
                ownerName = custCorporateDetailsEntity.getCompanyName();
                break;
            }
            if (addressEntity != null) {
                MandalEntity mandalEntity = addressEntity.getMandal();
                DistrictEntity districtEntity = mandalEntity.getDistrictEntity();
                StateEntity stateEntity = districtEntity.getStateEntity();
                CountryEntity countryEntity = stateEntity.getCountryEntity();
                StringBuilder sb = new StringBuilder(addressEntity.getDoorNo());
                StringsUtil.appendIfNotNull(sb, addressEntity.getStreet());
                StringsUtil.appendIfNotNull(sb, addressEntity.getCity());
                StringsUtil.appendIfNotNull(sb, mandalEntity.getName());
                StringsUtil.appendIfNotNull(sb, districtEntity.getName());
                StringsUtil.appendIfNotNull(sb, stateEntity.getName());
                StringsUtil.appendIfNotNull(sb, countryEntity.getName());
                cfxNoticeModel.setOwnerAddress(sb.toString());
                cfxNoticeModel.setOwnerName(ownerName);
            }
        }
        return cfxNoticeModel;
    }

    @Value("${rta.employee.signature.path}")
    private String signaturePath;

    @Value("${base.url}")
    private String baseURL;

    private String getCFXNumber(CFXModel cfxModel, Long userId) {
        return cfxModel.getApplicationNumber();
    }

}
