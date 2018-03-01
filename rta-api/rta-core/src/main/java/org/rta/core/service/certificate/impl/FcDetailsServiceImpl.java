package org.rta.core.service.certificate.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.constant.SomeConstants;
import org.rta.core.dao.certificate.CFXNotesDAO;
import org.rta.core.dao.certificate.FcDetailsDAO;
import org.rta.core.dao.certificate.impl.FcDetailsDAOImpl;
import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.dao.payment.registfee.FitnessFeeDAO;
import org.rta.core.dao.payment.registfee.FitnessFeeHistoryDAO;
import org.rta.core.dao.user.RTAEmployeeDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.dao.vehicle.VehicleRCHistoryDAO;
import org.rta.core.entity.certificate.CFXNotesEntity;
import org.rta.core.entity.certificate.FitnessCertificateEntity;
import org.rta.core.entity.payment.registfee.FitnessFeeDetailsEntity;
import org.rta.core.entity.payment.registfee.FitnessFeeHistoryEntity;
import org.rta.core.entity.user.RTAEmployeeEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.FormCodeType;
import org.rta.core.enums.SerialNumberType;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.enums.citizen.FitnessCategory;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.UserActionModel;
import org.rta.core.model.certificate.FcDetailsModel;
import org.rta.core.model.certificate.FitnessCategoryModel;
import org.rta.core.model.sync.ApplicationFormDataModel;
import org.rta.core.model.sync.SyncDataModel;
import org.rta.core.service.application.ApplicationService;
import org.rta.core.service.certificate.FcDetailsService;
import org.rta.core.service.master.serialnumber.SerialNumberService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author sohan.maurya created on Nov 11, 2016.
 */

@Service("fcDetailsService")
public class FcDetailsServiceImpl implements FcDetailsService {

    private static final Logger logger = Logger.getLogger(FcDetailsDAOImpl.class);

    @Autowired
    private FcDetailsDAO fcDetailsDAO;

    @Autowired
    private VehicleRCHistoryDAO vehicleRCHistoryDAO;

    @Value("${rta.fc.validity}")
    private Integer fcValidity;

    @Value("${rta.fc.text}")
    private String fcText;

    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private VehicleDetailsDAO vehicleDetailsDAO;
    @Autowired
    private FitnessFeeDAO fitnessFeeDAO;
    @Autowired
    private FitnessFeeHistoryDAO fitnessFeeHistoryDAO;

    @Autowired
    private RtaOfficeDAO rtaOfficeDAO;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CFXNotesDAO cfxNotesDAO;

    @Autowired
    private RTAEmployeeDAO rTAEmployeeDAO;

    @Value("${attachments.downloaded.path}")
    private String attachmentDocPath;

    @Value("${rta.employee.signature.path}")
    private String signaturePath;

    @Value("${base.url}")
    private String baseURL;

    @Autowired
    private SerialNumberService serialNumberService;

    @Transactional
    @Override
    public String saveOrUpdate(Long vehicleRcId, String userName) {
        logger.info(":::::::Fitness:::Create::: start :::" + vehicleRcId);
        String msg = "";
        try {
            FitnessCertificateEntity entity = fcDetailsDAO.getFcDetails(vehicleRcId, "V");
            VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);
            if (vehicleRCEntity == null)
                throw new IllegalArgumentException("Invalid vehicleRcId!");
            if (ObjectsUtil.isNull(entity)) {
                entity = new FitnessCertificateEntity();
                entity.setVehicleRcId(vehicleRCEntity.getVehicleRcId());
            }
            entity.setVehicleRegdNo(vehicleRCEntity.getPrNumber());
            entity.setFcType("R");
            entity.setApplType(ServiceType.FC_FRESH.getCode());
            entity.setValidityFlag("V");
            entity.setStatusCode(Status.APPROVED.getValue());
            Date issueDate = DateUtil.toCurrentUTCDate();
            Date expiryDate = null;
            entity.setIssueDate(issueDate);
            VehicleDetailsEntity vde = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
            if (vde.getVehicleSubClass().equalsIgnoreCase("EIBT")) {
            	expiryDate = getTwoYearFitnessExpiryDate(vde.getVehicleSubClass(), issueDate);
            	entity.setExpiryDate(expiryDate);
            } else {
            	expiryDate = new Date(DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), fcValidity) * 1000);
                entity.setExpiryDate(expiryDate);
            }
            vehicleRCEntity.setPrExpireDate(expiryDate.getTime() / 1000);
            entity.setChassisNo(vehicleRCEntity.getChassisNumber());
            entity.setRtaOfficeId(vehicleRCEntity.getRtaOfficeId());
            entity.setInspRtaOfficeId(vehicleRCEntity.getRtaOfficeId());
            entity.setCreatedBy(userName);
            entity.setCreatedOn(DateUtil.toCurrentUTCDate());
            entity.setModifiedBy(userName);
            entity.setModifiedOn(DateUtil.toCurrentUTCDate());
            fcDetailsDAO.saveOrUpdate(entity);
            vehicleDAO.saveOrUpdate(vehicleRCEntity);
            entity.setFcNo(getFitnessNo(entity.getFitnessId(), vehicleRCEntity.getRtaOfficeId().getCode()));
            msg = SaveUpdateResponse.SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
            msg = SaveUpdateResponse.FAILURE;
        }
        logger.info(":::::::Fitness:::Create:::end::" + msg);
        return msg;
    }

    private String getFitnessNo(Long seq, String rtaOfficeCode) {
        long serialNumber = serialNumberService.getSerialNumber(rtaOfficeCode, SerialNumberType.FITNESS);
        logger.info("For fitness Setting serial No : " + serialNumber);
        return new StringBuilder(fcText).append("/").append(serialNumber).append("/")
                .append(DateUtil.getYear(DateUtil.toCurrentUTCTimeStamp())).append("/").append(rtaOfficeCode)
                .toString();
    }

    @Transactional
    @Override
    public FcDetailsModel getFcCertificate(Long vehicleRcId, Long mviUserId) {
        logger.info(":::::: getFcCertificate ::::: start ");
        FcDetailsModel model = new FcDetailsModel();
        FitnessCertificateEntity entity = fcDetailsDAO.getFcDetails(vehicleRcId, "V");
        if (ObjectsUtil.isNull(entity)) {
            logger.info("There is not any fc regarding vehicle no. " + vehicleRcId);
            return model;
        }
        model.setIssueDate(
                ObjectsUtil.isNotNull(entity.getIssueDate()) ? entity.getIssueDate().getTime() / 1000 : null);
        model.setFcNumber(entity.getFcNo());
        model.setRtaOfficeName(entity.getInspRtaOfficeId().getName());
        model.setExpiryDate(
                ObjectsUtil.isNotNull(entity.getExpiryDate()) ? entity.getExpiryDate().getTime() / 1000 : null);
        if (Status.getStatus(entity.getStatusCode()) == Status.SUSPENDED) {
            model.setSuspended(true);
        } else {
            model.setSuspended(false);
        }
        logger.info("::::::::::::::::::::::: getFcCertificate :::::::::::: end ");

        // ---- set RTO signature----------------------
        UserEntity mviUserEntity = null;
        if (ObjectsUtil.isNotNull(mviUserId)) {
            RTAEmployeeEntity rtaEmployee = rTAEmployeeDAO.getByUserId(mviUserId);
            if (ObjectsUtil.isNotNull(rtaEmployee)) {
                mviUserEntity = rtaEmployee.getUserEntity();
                String fileName = rtaEmployee.getSignFileName();
                String signatureFile = baseURL + File.separator + signaturePath + File.separator + fileName;
                model.setSignatureFilePath(signatureFile);
            } else {
                logger.error("Error Signature not found for User Id : " + mviUserId);
            }
        } else {
            try {
                VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);
                UserEntity mviUser = vehicleRCEntity.getMviUserId();
                if (ObjectsUtil.isNull(mviUser)) {
                    logger.error("Error No MVI Found for vehicleRcId : " + vehicleRcId);
                }
                Long approvedMviUserId = null;
                try{
                    approvedMviUserId = Long.valueOf(entity.getApprovedbyMVI());
                }catch(Exception ex){
                    approvedMviUserId = mviUser.getUserId();
                }
                RTAEmployeeEntity rtaEmployee = rTAEmployeeDAO.getByUserId(approvedMviUserId);
                mviUserEntity = rtaEmployee.getUserEntity();
                String fileName = rtaEmployee.getSignFileName();
                String signatureFile = baseURL + File.separator + signaturePath + File.separator + fileName;
                model.setSignatureFilePath(signatureFile);
            } catch (Exception ex) {
                logger.error("Exception While reading signature in permit auth card ......");
                logger.debug("Exception : ", ex);
            }
        }
        if (ObjectsUtil.isNotNull(mviUserEntity)) {
            model.setMviName(getMviName(vehicleRcId, mviUserEntity));
        } else {
            model.setMviName(getMviName(vehicleRcId, null));
        }

        return model;
    }

    @Transactional
    @Override
    public FcDetailsModel getSuspendedFcCertificate(Long vehicleRcId) {
        logger.info(":::::: getFcCertificate ::::: start ");
        FcDetailsModel model = new FcDetailsModel();
        FitnessCertificateEntity entity = fcDetailsDAO.getSuspendedFcDetails(vehicleRcId, "V");
        if (ObjectsUtil.isNull(entity)) {
            logger.info("There is not any fc regarding vehicle no. " + vehicleRcId);
            return model;
        }
        model.setIssueDate(
                ObjectsUtil.isNotNull(entity.getIssueDate()) ? entity.getIssueDate().getTime() / 1000 : null);
        model.setFcNumber(entity.getFcNo());
        model.setRtaOfficeName(entity.getRtaOfficeId().getName());
        model.setExpiryDate(
                ObjectsUtil.isNotNull(entity.getExpiryDate()) ? entity.getExpiryDate().getTime() / 1000 : null);
        logger.info("::::::::::::::::::::::: getFcCertificate :::::::::::: end ");

        // ---- set RTO signature----------------------
        UserEntity mviUserEntity = null;
        try {
            VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);
            UserEntity mviUser = vehicleRCEntity.getMviUserId();
            if (ObjectsUtil.isNull(mviUser)) {
                logger.error("Error No MVI Found for vehicleRcId : " + vehicleRcId);
            }
            RTAEmployeeEntity rtaEmployee = rTAEmployeeDAO.getByUserId(mviUser.getUserId());
            mviUserEntity = rtaEmployee.getUserEntity();
            String fileName = rtaEmployee.getSignFileName();
            String signatureFile = baseURL + File.separator + signaturePath + File.separator + fileName;
            model.setSignatureFilePath(signatureFile);
        } catch (Exception ex) {
            logger.error("Exception While reading signature in permit auth card ......");
            logger.debug("Exception : ", ex);
        }
        if (ObjectsUtil.isNotNull(mviUserEntity)) {
            model.setMviName(getMviName(vehicleRcId, mviUserEntity));
        } else {
            model.setMviName(getMviName(vehicleRcId, null));
        }

        return model;
    }

    private String getMviName(Long vehicleRcId, UserEntity entity) {
        logger.info(":::Fitness:::: getMviName ::::: Start ");
        String mviName = null;
        try {
            if (ObjectsUtil.isNull(entity)) {
                entity = vehicleRCHistoryDAO.getLastActionOnVehicleRcId(vehicleRcId, UserType.ROLE_MVI).getUserEntity();
            }
            StringBuilder sb = new StringBuilder();
            if (!StringsUtil.isNullOrEmpty(entity.getFirstName())) {
                sb.append(entity.getFirstName());
            }
            if (!StringsUtil.isNullOrEmpty(entity.getMiddleName())) {
                sb.append(" ");
                sb.append(entity.getMiddleName());
            }
            if (!StringsUtil.isNullOrEmpty(entity.getLastName())) {
                sb.append(" ");
                sb.append(entity.getLastName());
            }
            mviName = sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        logger.info("Fitness::: getMviName :::::: End " + mviName);
        return mviName;
    }

    @Override
    public SaveUpdateResponse syncData(SyncDataModel syncDataModel, VehicleRCEntity vehicleRCEntity) {
        logger.info("::Fitness :Service:syncData::::start::");
        SaveUpdateResponse saveUpdateResponse = null;
        switch (ServiceType.getServiceType(syncDataModel.getServiceType())) {
        case FC_FRESH:
            saveUpdateResponse = syncFitnessNew(syncDataModel, vehicleRCEntity);
            break;
        case FC_OTHER_STATION:
        case FC_RE_INSPECTION_SB:
        case FC_RENEWAL:
            saveUpdateResponse = syncFitnessRenewal(syncDataModel, vehicleRCEntity);
            break;
        case FC_REVOCATION_CFX:
            saveUpdateResponse = syncFitnessRevocation(syncDataModel, vehicleRCEntity);
            break;
        }
        logger.info("::Fitness :Service :syncData::::start::");
        return saveUpdateResponse;
    }

    public SaveUpdateResponse syncFitnessNew(SyncDataModel syncDataModel, VehicleRCEntity vehicleRCEntity) {
        logger.info(":::syncFitnessNew:::start::::::::");
        FitnessCertificateEntity entity = null;
        FitnessFeeDetailsEntity fitnessFeeEntity = null;
        SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        fitnessFeeEntity = fitnessFeeDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
        entity = fcDetailsDAO.getFcDetails(vehicleRCEntity.getVehicleRcId(), "V");
        if (entity != null && fitnessFeeEntity != null) {
            logger.info(":::Fitness Already Applied::::::");
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Fitness Already Applied");
        }
        try {
            try {
                List<ApplicationFormDataModel> formDataList = syncDataModel.getFormList();
                FitnessCategoryModel fitnessCategoryModel = null;
                for (ApplicationFormDataModel formModel : formDataList) {
                    ObjectMapper mapper = new ObjectMapper();
                    if (FormCodeType.FCF_MVI_FORM.getLabel().equalsIgnoreCase(formModel.getFormCode())) {
                        fitnessCategoryModel = mapper.readValue(formModel.getFormData(), FitnessCategoryModel.class);
                    }
                }
                entity = new FitnessCertificateEntity();
                entity.setVehicleRegdNo(syncDataModel.getPrNumber());
                entity.setChassisNo(vehicleRCEntity.getChassisNumber());
                entity.setRtaOfficeId(vehicleRCEntity.getRtaOfficeId());
                entity.setInspRtaOfficeId(vehicleRCEntity.getRtaOfficeId());
                entity.setEngineNo(null);
                entity.setStatusCode(Status.APPROVED.getValue());
                entity.setFcType("R");
                entity.setApplType(ServiceType.FC_FRESH.getCode());
                Date issueDate = DateUtil.toCurrentUTCDate();
                entity.setIssueDate(issueDate);
                VehicleDetailsEntity vde = vehicleDetailsDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                if (vde.getVehicleSubClass().equalsIgnoreCase("EIBT")) {
                    entity.setExpiryDate(getFitnessExpiryDate(vde.getVehicleSubClass(), issueDate));
                } else {
                    if (ObjectsUtil.isNotNull(fitnessCategoryModel.getGhatRoad()) ? fitnessCategoryModel.getGhatRoad()
                            : false) {
                        entity.setExpiryDate(
                                new Date(DateUtil.addMonthsNew(DateUtil.toCurrentUTCTimeStamp(), 6) * 1000));
                    } else if (FitnessCategory.NEW_VEHICLE_FC.getValue() == fitnessCategoryModel.getFitnessCategory()) {
                        entity.setExpiryDate(
                                new Date(DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), fcValidity) * 1000));
                    } else {
                        entity.setExpiryDate(new Date(DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), 1) * 1000));
                    }
                }
                List<UserActionModel> actionModelList = syncDataModel.getActionModelList();
				for (UserActionModel actionModel : actionModelList) {
					if (UserType.ROLE_MVI == actionModel.getUserType()) {
						entity.setApprovedbyMVI(actionModel.getUserId());
						break;
					}
				}
                entity.setCreatedBy("CITIZEN");
                entity.setCreatedOn(DateUtil.toCurrentUTCDate());
                entity.setApplId(syncDataModel.getApplicationNumber());
                fcDetailsDAO.saveOrUpdate(entity);
                entity.setFcNo(getFitnessNo(entity.getFitnessId(), vehicleRCEntity.getRtaOfficeId().getCode()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                fitnessFeeEntity = new FitnessFeeDetailsEntity();
                fitnessFeeEntity.setFitnessFee(Double.parseDouble(syncDataModel.getFeeModel().getApplicationFee()));
                fitnessFeeEntity
                        .setFitnessService(Double.parseDouble(syncDataModel.getFeeModel().getFitnessServiceCharge()));
                fitnessFeeEntity.setTotalFitnessFee(Double.parseDouble(syncDataModel.getFeeModel().getApplicationFee())
                        + Double.parseDouble(syncDataModel.getFeeModel().getFitnessServiceCharge()));
                fitnessFeeEntity.setVehicleRc(vehicleRCEntity);
                fitnessFeeEntity.setCreatedBy("CITIZEN");
                fitnessFeeEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
                fitnessFeeDAO.save(fitnessFeeEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Internal Server Error");
        }
        saveUpdateResponse.setStatus(SaveUpdateResponse.SUCCESS);
        logger.info(":::syncFitnessNew:::end:::::::");
        return saveUpdateResponse;
    }

    public SaveUpdateResponse syncFitnessRenewal(SyncDataModel syncDataModel, VehicleRCEntity vehicleRCEntity) {
        logger.info(":::syncFitnessRenewal::start::::::::");
        FitnessCertificateEntity oldFcEntity = null;
        FitnessFeeDetailsEntity fitnessFeeEntity = null;
        SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        try {
            fitnessFeeEntity = fitnessFeeDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
            oldFcEntity = fcDetailsDAO.getFcDetails(vehicleRCEntity.getVehicleRcId(), "V");
            FitnessCertificateEntity newFcEntity = null;
            if (oldFcEntity != null) {
                try {
                    try {
                        newFcEntity = (FitnessCertificateEntity) oldFcEntity.clone();
                    } catch (CloneNotSupportedException e1) {
                        e1.printStackTrace();
                    }
                    List<ApplicationFormDataModel> formDataList = syncDataModel.getFormList();
                    FitnessCategoryModel fitnessCategoryModel = null;
                    for (ApplicationFormDataModel formModel : formDataList) {
                        ObjectMapper mapper = new ObjectMapper();
                        if (FormCodeType.FCF_MVI_FORM.getLabel().equalsIgnoreCase(formModel.getFormCode())) {
                            fitnessCategoryModel = mapper.readValue(formModel.getFormData(), FitnessCategoryModel.class);
                            break;
                        }
                    }
                    oldFcEntity.setValidityFlag("I");
                    fcDetailsDAO.update(oldFcEntity);

                    newFcEntity.setFitnessId(null);
                    newFcEntity.setVehicleRegdNo(syncDataModel.getPrNumber());
                    newFcEntity.setChassisNo(vehicleRCEntity.getChassisNumber());
                    newFcEntity.setRtaOfficeId(vehicleRCEntity.getRtaOfficeId());
                    newFcEntity.setValidityFlag("V");
                    newFcEntity.setFcType("R");
                    newFcEntity.setStatusCode(Status.APPROVED.getValue());
                    newFcEntity.setApplType("RENEWAL");
                    Date issueDate = DateUtil.toCurrentUTCDate();
                    newFcEntity.setIssueDate(issueDate);
                    VehicleDetailsEntity vde = vehicleDetailsDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
                    newFcEntity.setEngineNo(vde.getEngineNo());
                    if (vde.getVehicleSubClass().equalsIgnoreCase("EIBT")) {
                        newFcEntity.setExpiryDate(getFitnessExpiryDate(vde.getVehicleSubClass(), issueDate));
                    } else {
                        if (ObjectsUtil.isNotNull(fitnessCategoryModel) && ObjectsUtil.isNotNull(fitnessCategoryModel.getGhatRoad()) && fitnessCategoryModel.getGhatRoad()) {
                            newFcEntity.setExpiryDate(
                                    new Date(DateUtil.addMonthsNew(DateUtil.toCurrentUTCTimeStamp(), 6) * 1000));
                        } else {
                            newFcEntity.setExpiryDate(new Date(
                                    DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), SomeConstants.ONE) * 1000));
                        }
                    }
                    String mviUserId=null;
                    List<UserActionModel> actionModelList = syncDataModel.getActionModelList();
    				for (UserActionModel actionModel : actionModelList) {
    					if (UserType.ROLE_MVI == actionModel.getUserType()) {
    						newFcEntity.setApprovedbyMVI(actionModel.getUserId());
    						mviUserId=actionModel.getUserId();
    						break;
    					}
    				}
                    newFcEntity.setApplId(syncDataModel.getApplicationNumber());
                    newFcEntity.setCreatedBy("CITIZEN");
                    newFcEntity.setCreatedOn(DateUtil.toCurrentUTCDate());
                    fcDetailsDAO.save(newFcEntity);
					RTAEmployeeEntity employee = rTAEmployeeDAO.getByUserId(Long.parseLong(mviUserId));
					String newRTAOfficeCode = employee.getRtaOfficeEntity().getCode();
					newFcEntity.setInspRtaOfficeId(employee.getRtaOfficeEntity());
					newFcEntity.setFcNo(getFitnessNo(newFcEntity.getFitnessId(), newRTAOfficeCode));
					fcDetailsDAO.saveOrUpdate(newFcEntity);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                
                if(ObjectsUtil.isNull(fitnessFeeEntity)){
                	fitnessFeeEntity = new FitnessFeeDetailsEntity();
            	} else {
            		try {
                        FitnessFeeHistoryEntity fitnessFeeEntityHistory = new FitnessFeeHistoryEntity();
                        fitnessFeeEntityHistory.setCreatedBy("CITIZEN");
                        fitnessFeeEntityHistory.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
                        fitnessFeeEntityHistory.setFitnessFee(fitnessFeeEntity.getFitnessFee());
                        fitnessFeeEntityHistory.setFitnessService(fitnessFeeEntity.getFitnessService());
                        fitnessFeeEntityHistory.setTotalFitnessFee(fitnessFeeEntity.getTotalFitnessFee());
                        fitnessFeeEntityHistory.setVehicleRcId(fitnessFeeEntity.getVehicleRc().getVehicleRcId());
                        fitnessFeeHistoryDAO.save(fitnessFeeEntityHistory);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
            	}

                try {
                    fitnessFeeEntity.setFitnessFee(Double.parseDouble(syncDataModel.getFeeModel().getApplicationFee()));
                    fitnessFeeEntity.setFitnessService(
                            Double.parseDouble(syncDataModel.getFeeModel().getFitnessServiceCharge()));
                    fitnessFeeEntity
                            .setTotalFitnessFee(Double.parseDouble(syncDataModel.getFeeModel().getApplicationFee())
                                    + Double.parseDouble(syncDataModel.getFeeModel().getFitnessServiceCharge()));
                    fitnessFeeEntity.setVehicleRc(vehicleRCEntity);
                    fitnessFeeEntity.setCreatedBy("CITIZEN");
                    fitnessFeeEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
                    fitnessFeeDAO.saveOrUpdate(fitnessFeeEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                logger.error(":::There is not any active fitness found::::::");
                saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
                saveUpdateResponse.setMessage("There is not any active fitness found !!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Internal Server Error");
        }
        saveUpdateResponse.setStatus(SaveUpdateResponse.SUCCESS);
        logger.info(":::syncFitnessNew:::end:::::::");
        return saveUpdateResponse;
    }

    public static Date getFitnessExpiryDate(String vehicleSubClass, Date issueDate) {
        Integer month = DateUtil.extractMonth(issueDate);
        Integer year;
        Integer date;
        if (month.intValue() == 5) {
            date = DateUtil.extractDate(issueDate);
            if (date.intValue() == 15) {
                date = 14;
                year = DateUtil.extractYear(issueDate) + 1;
            } else if (date.intValue() > 15) {
                date = 15;
                year = DateUtil.extractYear(issueDate) + 1;
            } else {
                date = 15;
                year = DateUtil.extractYear(issueDate);
            }
        } else {
            date = 15;
            if (month.intValue() > 5) {
                year = DateUtil.extractYear(issueDate) + 1;
            } else {
                year = DateUtil.extractYear(issueDate);
            }
        }
        month = 5;
        return DateUtil.createDate(date, month, year);
    }
    
    public static Date getTwoYearFitnessExpiryDate(String vehicleSubClass, Date issueDate) {
        Integer month = DateUtil.extractMonth(issueDate);
        Integer year;
        Integer date;
        if (month.intValue() == 5) {
            date = DateUtil.extractDate(issueDate);
            if (date.intValue() == 15) {
                date = 14;
                year = DateUtil.extractYear(issueDate) + 2;
            } else if (date.intValue() > 15) {
                date = 15;
                year = DateUtil.extractYear(issueDate) + 2;
            } else {
                date = 15;
                year = DateUtil.extractYear(issueDate)+1;
            }
        } else {
            date = 15;
            if (month.intValue() > 5) {
                year = DateUtil.extractYear(issueDate) + 2;
            } else {
                year = DateUtil.extractYear(issueDate)+1;
            }
        }
        month = 5;
        return DateUtil.createDate(date, month, year);
    }
    
    /*
      * public static void main(String[] args) { ObjectMapper mapper = new
      * ObjectMapper(); FitnessCategoryModel f = null; try { f =
      * mapper.readValue("{\"fitnessCategory\":1,\"ghatRoad\":3}",
      * FitnessCategoryModel.class); } catch (JsonParseException e) { // TODO
      * Auto-generated catch block e.printStackTrace(); } catch
      * (JsonMappingException e) { // TODO Auto-generated catch block
      * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
      * catch block e.printStackTrace(); } System.out.println(f.getGhatRoad());
      * }
      */

    public SaveUpdateResponse syncFitnessRevocation(SyncDataModel syncDataModel, VehicleRCEntity vehicleRCEntity) {
        logger.info(":::syncFitnessRevocation:::start::::::::");
        FitnessCertificateEntity entity = null;
        SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        entity = fcDetailsDAO.getSuspendedFcDetails(vehicleRCEntity.getVehicleRcId(), "V");
        if (entity != null) {
            entity.setStatusCode(Status.OPEN.getValue());
            entity.setModifiedBy("CITIZEN1");
            entity.setModifiedOn(DateUtil.toCurrentUTCDate());
            fcDetailsDAO.saveOrUpdate(entity);
        } else {
            logger.error("Error not found fitness certificate w.r.t " + vehicleRCEntity);
        }
        CFXNotesEntity cfx = cfxNotesDAO.getByPrNumber(vehicleRCEntity.getPrNumber(), Status.APPROVED);
        if (cfx != null) {
            cfx.setStatus(Status.CLOSED.getValue());
            cfx.setModifiedBy("CITIZEN1");
            cfx.setModifiedOn(DateUtil.toCurrentUTCDate());
            cfxNotesDAO.update(cfx);
        } else {
            logger.error("Error not found CFX Notes w.r.t " + vehicleRCEntity.getPrNumber());
        }
        saveUpdateResponse.setStatus(SaveUpdateResponse.SUCCESS);
        logger.info(":::syncFitnessNew:::end:::::::");
        return saveUpdateResponse;
    }

    @Override
    public void generateFinessForVA(VehicleRCEntity vehicleRcEntity, String cov, String mviUserId){
    	FitnessCertificateEntity entity = fcDetailsDAO.getFcDetails(vehicleRcEntity.getVehicleRcId(), "V");
    	if(ObjectsUtil.isNull(entity)){
    		entity = new FitnessCertificateEntity();
    	}
        entity.setVehicleRegdNo(vehicleRcEntity.getPrNumber());
        entity.setChassisNo(vehicleRcEntity.getChassisNumber());
        entity.setRtaOfficeId(vehicleRcEntity.getRtaOfficeId());
        entity.setVehicleRcId(vehicleRcEntity.getVehicleRcId());
        RTAEmployeeEntity employee = rTAEmployeeDAO.getByUserId(Long.parseLong(mviUserId));
        entity.setInspRtaOfficeId(employee.getRtaOfficeEntity());
        entity.setEngineNo(null);
        entity.setStatusCode(Status.APPROVED.getValue());
        entity.setFcType("R");
        entity.setApplType(ServiceType.VEHICLE_ATLERATION.getCode());
        Date issueDate = DateUtil.toCurrentUTCDate();
        entity.setIssueDate(issueDate);
        if (cov.equalsIgnoreCase("EIBT")) {
            entity.setExpiryDate(getFitnessExpiryDate(cov, issueDate));
        } else {
        	entity.setExpiryDate(new Date(DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), fcValidity) * 1000));
        }
        entity.setApprovedbyMVI(mviUserId);
        entity.setCreatedBy("CITIZEN");
        entity.setCreatedOn(DateUtil.toCurrentUTCDate());
        entity.setValidityFlag("V");
        fcDetailsDAO.saveOrUpdate(entity);
        entity.setFcNo(getFitnessNo(entity.getFitnessId(), vehicleRcEntity.getRtaOfficeId().getCode()));
    }
}
