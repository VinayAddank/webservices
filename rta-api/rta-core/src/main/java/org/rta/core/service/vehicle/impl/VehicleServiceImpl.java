package org.rta.core.service.vehicle.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.docs.AttachmentDetailsDAO;
import org.rta.core.dao.master.AlterationCovMappingDAO;
import org.rta.core.dao.master.BodyTypeMasterDAO;
import org.rta.core.dao.master.RegistrationCategoryDAO;
import org.rta.core.dao.master.TaxTypeDAO;
import org.rta.core.dao.payment.tax.GreenTaxDetailsDAO;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.dao.user.DealerDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.dao.vehicle.DuplicateRegistrationDAO;
import org.rta.core.dao.vehicle.SuspendedRCNumberDAO;
import org.rta.core.dao.vehicle.VahanDAO;
import org.rta.core.dao.vehicle.VehicleAlterationDAO;
import org.rta.core.dao.vehicle.VehicleBharatStageDAO;
import org.rta.core.dao.vehicle.VehicleBillingDetailsDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.dao.vehicle.VehiclePRReleaseDAO;
import org.rta.core.dao.vehicle.VehicleRCChangeHistoryDAO;
import org.rta.core.dao.vehicle.VehicleRCHistoryDAO;
import org.rta.core.dao.vehicle.cms.VehicleClassDescDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.entity.master.DocTypesEntity;
import org.rta.core.entity.master.TaxTypeEntity;
import org.rta.core.entity.payment.tax.GreenTaxDetailsEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.user.DealerEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.DuplicateRegistrationEntity;
import org.rta.core.entity.vehicle.SuspendedRCNumbersEntity;
import org.rta.core.entity.vehicle.VahanEntity;
import org.rta.core.entity.vehicle.VehicleBharatStageEntity;
import org.rta.core.entity.vehicle.VehicleBillingDetailsEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehiclePRReleaseEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.entity.vehicle.cms.VehicleClassDescriptionEntity;
import org.rta.core.enums.AttachmentFrom;
import org.rta.core.enums.DocTypes;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.PRType;
import org.rta.core.enums.RegistrationType;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.enums.TheftIntSusType;
import org.rta.core.enums.UserType;
import org.rta.core.enums.VehicleCategory;
import org.rta.core.exception.DataMismatchException;
import org.rta.core.exception.InvalidVehicleSubClassException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.exception.TaxTypeNotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.helper.master.RegistrationCategoryHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.TheftIntimationRevocationModel;
import org.rta.core.model.UserActionModel;
import org.rta.core.model.application.ApplicationModel;
import org.rta.core.model.application.SortApplicationModel;
import org.rta.core.model.customer.CustomerDetailsRequestModel;
import org.rta.core.model.master.RegistrationCategoryModel;
import org.rta.core.model.vehicle.BillingDetailsModel;
import org.rta.core.model.vehicle.CommonServiceModel;
import org.rta.core.model.vehicle.DuplicateRegistrationModel;
import org.rta.core.model.vehicle.PurchaseDetailsModel;
import org.rta.core.model.vehicle.UnregisteredVehicleModel;
import org.rta.core.model.vehicle.VehicleBaseModel;
import org.rta.core.model.vehicle.VehicleDetailsRequestModel;
import org.rta.core.model.vehicle.cms.VehicleClassDescModel;
import org.rta.core.service.application.ApplicationService;
import org.rta.core.service.master.RegistrationCategoryService;
import org.rta.core.service.vehicle.VehicleDetailsService;
import org.rta.core.service.vehicle.VehicleService;
import org.rta.core.service.vehicle.cms.VehicleMappingService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private static final Logger log = Logger.getLogger(VehicleServiceImpl.class);

	private final ConversionService conversionService;
	private final VehicleDAO vehicleDao;
	private final VahanDAO vahanDao;
	private final VehicleBillingDetailsDAO vehicleBillingDetailsDAO;
	private final RegistrationCategoryService registrationCategoryService;
	private final RegistrationCategoryHelper registrationCategoryHelper;
	//private final VehicleDetailsService vehicleDetailsService;
	private final VehicleDetailsDAO vehicleDetailsDAO;
	private final TaxTypeDAO taxTypeDao;
	private final RegistrationCategoryDAO registrationCategoryDAO;
	private final VehicleClassDescDAO vehicleClassDescDAO; 

	@Value("${rta.step.totalstep}")
	private Integer totalStep;

	@Value("${rta.registration.renewal.year}")
	private Integer rcRenewalYear;

	@Value("${rta.step.registnumber}")
	private Integer prTypeStep;

	@Value("${rta.vehicle.chassisonly.nontransport}")
	private String chassisOnlyNonTransport;

	@Value("${rta.vehicle.chassisonly.transport}")
	private String chassisOnlyTransport;

	@Value("${rta.pancard.notrequired.maxamount}")
	private Double panCardInvoiceAmt;

	@Value("${rta.ISTWOFOURWHEELER}")
	private String ISTWOFOURWHEELER;

	@Value("${rta.registration.vr.otherstate.code}")
	private String otherStateCode;

	@Autowired
	private VehicleRCHistoryDAO vehicleRCHistoryDAO;

	@Autowired
	private CustomerDAO custIndvDAO;

	@Autowired
	private CustomerCorporateDAO customerCorporateDAO;

	@Autowired
	private AttachmentDetailsDAO attachmentDetailsDAO;

	@Autowired
	private DealerDAO dealerDAO;

	@Autowired
	private AddressDAO addressDAO;

	@Autowired
	private DuplicateRegistrationDAO duplicateRegistrationDAO;

	@Autowired
	private VehicleRCChangeHistoryDAO vehicleRCChangeHistoryDAO;

	@Autowired
	private VehiclePRReleaseDAO vehiclePRReleaseDAO;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private SuspendedRCNumberDAO suspendedRCNumberDAO;
	
	@Autowired
    private VehicleBharatStageDAO VehicleBharatStageDAO;
	
	@Autowired
	private AlterationCovMappingDAO alterationCovMappingDAO;
	
	@Autowired
	private VehicleMappingService vehicleMappingService;
	
	@Autowired
	private VehicleAlterationDAO vehicleAlterationDAO;
	
	@Autowired
	private GreenTaxDetailsDAO greenTaxDetailsDAO;
	
	@Autowired
	private BodyTypeMasterDAO bodyTypeMasterDAO;
	
	@Autowired
	VehicleDetailsService vehicleDetailsService;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	public VehicleServiceImpl(final ConversionService conversionService, final VehicleDAO vehicleDao,
			final VahanDAO vahanDao, final VehicleBillingDetailsDAO vehicleBillingDetailsDAO,
			final RegistrationCategoryService registrationCategoryService,
			final RegistrationCategoryHelper registrationCategoryHelper,
			final VehicleDetailsDAO vehicleDetailsDAO,
			final TaxTypeDAO taxTypeDao, final RegistrationCategoryDAO registrationCategoryDAO,
			final VehicleClassDescDAO vehicleClassDescDAO) {
		this.conversionService = conversionService;
		this.vahanDao = vahanDao;
		this.vehicleDao = vehicleDao;
		this.vehicleBillingDetailsDAO = vehicleBillingDetailsDAO;
		this.registrationCategoryService = registrationCategoryService;
		this.registrationCategoryHelper = registrationCategoryHelper;
		this.vehicleDetailsDAO = vehicleDetailsDAO;
		this.taxTypeDao = taxTypeDao;
		this.registrationCategoryDAO = registrationCategoryDAO;
		this.vehicleClassDescDAO = vehicleClassDescDAO;
	}

	/*
	 * @Override public Long save(Integer registrationId,
	 * UnregisteredVehicleModel vehicleModel) { return null; }
	 * 
	 * @Override public Long save(UnregisteredVehicleModel vehicleModel) {
	 * return null; }
	 */

	public static final Set<String> INVALID_VEHICLE_SUB_CLASS_FOR_SEATING_CAPACITY_ONE =  Collections.unmodifiableSet(new HashSet<>(Arrays.asList("COCT","LTCT","MAXT","MTLT","OBT","PSVT","SCRT","TOVT","ARKT","EIBT")));
	
	@Override
	@Transactional
	public void update(Long userId, VehicleDetailsRequestModel vehicleModel, String userName, Integer currentStep,
			UserType userType) throws VehicleRcNotFoundException, TaxTypeNotFoundException, InvalidVehicleSubClassException {
		Long vehicleRcId = Long.parseLong(vehicleModel.getVehicleRcId());
		UnregisteredVehicleModel uvm = vehicleModel.getVehicle();
		if(null != uvm && uvm.getRlw() < 1501 && "ARVT".equalsIgnoreCase(uvm.getVehicleSubClass())){
			throw new IllegalArgumentException("Vehicle can't be registered as ARVT. RLW is less than 1501");
		}
		TaxTypeEntity tte = taxTypeDao.getByCode(vehicleModel.getVehicle().getTaxType());
		if (ObjectsUtil.isNull(tte)) {
			throw new TaxTypeNotFoundException("tax code is invalid");
		}
		if (ObjectsUtil.isNull(vehicleModel.getBillModel())) {
			throw new IllegalArgumentException("vehicle model can't be null");
		}

		// select Vehicle RC by vehicleRcId
		VehicleRCEntity ve = vehicleDao.get(vehicleRcId);

		// check for existence
		if (ObjectsUtil.isNull(ve)) {
			throw new VehicleRcNotFoundException();
		}

		ve.setChassisNumber(vehicleModel.getVehicle().getChassisNumber());

		// in current
		// ve.setProcessStatus(Status.PENDING.getValue());
		// ve.setCreatedBy(null);
		// ve.setCreatedOn(null);
		ve.setModifiedBy(userName);
		ve.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());

		/*
		 * UserEntity user = new UserEntity(); user.setUserId(userId);
		 * 
		 * ve.setUserId(user);
		 */

		/*
		 * RegistrationCategoryEntity rce = registrationCategoryHelper
		 * .convertToEntity(registrationCategoryService.get(vehicleModel.
		 * getRegistrationCategoryId() )); ve.setRegCategory(rce);
		 */

		// RtaOfficeEntity rtaOffice = new RtaOfficeEntity();
		// rtaOffice.setRtaOfficeId(vehicleModel.getDealerRTA());
		// ve.setRtaOfficeId(rtaOffice);

		/*
		 * TaxTypeModel tteModel =
		 * taxTypeService.getByCode(vehicleModel.getVehicle().getTaxType()); if
		 * (ObjectsUtil.isNull(tteModel)) { throw new TaxTypeNotFoundException(
		 * "tax code is invalid"); }
		 */

		/*
		 * VehicleDetailsEntity vde =
		 * conversionService.convert(vehicleDetailsService.get(vehicleModel.
		 * getVehicleRcId()), VehicleDetailsEntity.class);
		 */
		VehicleDetailsEntity vde = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
		if (ObjectsUtil.isNull(vde)) {
			vde = new VehicleDetailsEntity();
			vde.setCreatedBy(userName);
			vde.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			ve.setCurrentStep(currentStep);
			// vde.setVehicleDtlId(uvm.getVehicleDetailsId());
		}

		vde.setTaxTypeId(tte);
		vde.setVehicleRcId(ve);
		vde.setChassisNo(uvm.getChassisNumber());
		vde.setColor(uvm.getColor());
		vde.setEngineNo(uvm.getEngineNumber());
		vde.setFuelUsed(uvm.getFuelUsed());
		vde.setMakerClass(uvm.getMakersClass());
		vde.setMakerName(uvm.getMakersName());
		vde.setMfgDate(uvm.getManufacturingMonthYear());
		vde.setRlw(uvm.getRlw());
		vde.setUlw(uvm.getUlw());
		vde.setVehicleClass(uvm.getVehicleClass());
		Integer seatingCapacity = uvm.getSeatingCapacity();
		String vehicleSubClass = uvm.getVehicleSubClass();
		if (ObjectsUtil.isNull(seatingCapacity)) {
		    log.info("seatingCapacity - " + seatingCapacity + ", is invalid");
            throw new IllegalArgumentException("invalid seatingCapacity");
		}
		if (StringsUtil.isNullOrEmpty(vehicleSubClass)) {
		    log.info("vehicleSubClass - " + vehicleSubClass + ", is invalid");
		    throw new IllegalArgumentException("vehicleSubClass is invalid");
		}
		if (seatingCapacity.intValue() == Integer.valueOf(1) && INVALID_VEHICLE_SUB_CLASS_FOR_SEATING_CAPACITY_ONE.contains(vehicleSubClass)) {
		    log.info("vehicleSubClass - " + vehicleSubClass + ", not allowed when seating capacity is 1");
		    throw new InvalidVehicleSubClassException("444");
		} else {
		    vde.setSeatingCapacity(seatingCapacity);
		    vde.setVehicleSubClass(vehicleSubClass);
		}
		if (vehicleSubClass.equalsIgnoreCase(chassisOnlyNonTransport)
				|| vehicleSubClass.equalsIgnoreCase(chassisOnlyTransport)) {
			vde.setVehicleCategory(VehicleCategory.CHASSIS_ONLY);
		} else {
			vde.setVehicleCategory(VehicleCategory.FULLY_BUILD);
		}
		vde.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		vde.setLengthUpdated(uvm.getLength());
		vde.setHeightUpdated(uvm.getHeight());
		vde.setWidthUpdated(uvm.getWidth());
		vde.setBodyTypeUpdated(uvm.getBodyTypeDesc());
		vde.setWheelbase(uvm.getWheelbase());

		VehicleBillingDetailsEntity vbde = vehicleBillingDetailsDAO.getByVehicleDetailId(vde);
		if (ObjectsUtil.isNull(vbde)) {
			vbde = new VehicleBillingDetailsEntity();
			vbde.setCreatedBy(userName);
			vbde.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		}
		vbde.setInvoiceDate(vehicleModel.getBillModel().getInvoiceDate());
		if(ObjectsUtil.isNull(ve.getTrStatus()) || ve.getTrStatus() != Status.APPROVED.getValue()){
		    vbde.setInvoiceValue(vehicleModel.getBillModel().getInvoiceValue());
		}
		vbde.setInvoiceNumber(vehicleModel.getBillModel().getInvoiceNumber());
		vbde.setHelmetInvoiceAmount(vehicleModel.getBillModel().getHelmetInvoiceAmount());
		vbde.setCreatedBy(userName);
		vbde.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		vbde.setModifiedBy(userName);
		vbde.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		vbde.setVehicleDtlId(vde);

		// save/update attachment details for model name(maker class)
		Long stamp = DateUtil.toCurrentUTCTimeStamp();
		AttachmentDetailsEntity attachmentEntity = attachmentDetailsDAO.getAttatchDltIdByDocIdVehId(ve.getVehicleRcId(),
				DocTypes.MODEL_NAME.getValue());
		log.info("::::::: Setting model Attachment in step 2 ::::::::");
		if (ObjectsUtil.isNull(attachmentEntity)) {
			log.info("::::::: Creating New model Attachment ::::::::");
			attachmentEntity = new AttachmentDetailsEntity();
			attachmentEntity.setAttachmentFrom(AttachmentFrom.MOBILE.getValue());
			attachmentEntity.setAttachmentTitle("Model Name"); // should not be
																// changed
			attachmentEntity.setCreatedBy(userName);
			attachmentEntity.setCreatedOn(stamp);
			attachmentEntity.setFileName(DocTypes.MODEL_NAME.getLabel());
			DocTypesEntity docType = new DocTypesEntity();
			docType.setDocTypeId(DocTypes.MODEL_NAME.getValue());
			attachmentEntity.setDocTypes(docType);
			attachmentEntity.setSource("");
			attachmentEntity.setStatus(Status.UPLOADED.getValue());
			attachmentEntity.setUserRole(userType.getValue());
		} else if (attachmentEntity.getStatus() != Status.PENDING.getValue()) {
			log.info("::::::: Setting model Attachment status to Uploaded ::::::::");
			attachmentEntity.setStatus(Status.UPLOADED.getValue());
		}
		attachmentEntity.setModifiedBy(userName);
		attachmentEntity.setModifiedOn(stamp);
		attachmentEntity.setVehicle(ve);
		attachmentDetailsDAO.saveOrUpdate(attachmentEntity);

		vehicleBillingDetailsDAO.saveOrUpdate(vbde);

		// ---save customer details -----------------
		if (OwnershipType.getOwnershipType(ve.getOwnershipType()) == OwnershipType.INDIVIDUAL) {
			CustIndividualDetailsEntity cde = custIndvDAO.getByVehicleRcId(vehicleRcId);
			if (ObjectsUtil.isNull(cde)) {
				log.error("Customer Individual details does not exist with given vehicleRCID : " + vehicleRcId);
				throw new IllegalArgumentException("Customer details does not exist with given vehicle.");
			}
			cde.setPanNo(ObjectsUtil.isNotNull(vehicleModel.getPanNumber()) ? vehicleModel.getPanNumber().toUpperCase() : vehicleModel.getPanNumber());
			if (ObjectsUtil.isNull(vehicleModel.getIsSecondVehicle())) {
				throw new IllegalArgumentException("isSecondVehicle is missing");
			}
			cde.setIsSecondVehicle(vehicleModel.getIsSecondVehicle());
			custIndvDAO.saveOrUpdate(cde);
		} else if (OwnershipType.getOwnershipType(ve.getOwnershipType()) == OwnershipType.COMPANY) {
			CustCorporateDetailsEntity ccd = customerCorporateDAO.getByVehicleRcId(vehicleRcId);
			if (ObjectsUtil.isNull(ccd)) {
				log.error("Customer Corporate details does not exist with given vehicleRCID : " + vehicleRcId);
				throw new IllegalArgumentException("Customer details does not exist with given vehicle.");
			}
			ccd.setPanNo(ObjectsUtil.isNotNull(vehicleModel.getPanNumber()) ? vehicleModel.getPanNumber().toUpperCase() : vehicleModel.getPanNumber());
			customerCorporateDAO.saveOrUpdate(ccd);
		}
	}

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public Map<String, Object> getApplicationStatus(Long userId, UserType userType, String appType, Integer status,
            String regCat, Long from, Long to, String query, Integer perPageRecords, Integer pageNumber) {
        List<ApplicationModel> applicationModelList = new ArrayList<ApplicationModel>();
        Map<String, Object> mapObject = new HashMap<String, Object>();
     
        Map<Long,DealerEntity> dealersForUserId=getDealer4UserId(userType,userId);
        List<Long> users=new ArrayList<>(dealersForUserId.keySet());

        Map<String, Object> vehicleRcEntityMap = null;
        if(StringsUtil.isNullOrEmpty(query)){
            vehicleRcEntityMap = vehicleDao.getApplicationStatusSearchBasis(users, appType, status, regCat, from, to, perPageRecords, pageNumber);
        }else{
            vehicleRcEntityMap = vehicleDao.getApplicationStatusSearchBasis(users, appType, status, regCat, from, to, null, null);
        }
        
        List<VehicleRCEntity> vehicleRcEntityList = (List<VehicleRCEntity>) vehicleRcEntityMap.get("vehicleRCEntities");
        if (vehicleRcEntityList.size() == 0) {
            log.error(":::::::::::No Data Found:::::::::::");
            return null;
        }
        List<Long> ids = new ArrayList<Long>();
        List<Long> ids4CustInd = new ArrayList<Long>();
        List<Long> ids4CustCorp = new ArrayList<Long>();
        Map<Long, Map<UserType, Status>> appStatus = new HashMap<Long, Map<UserType, Status>>();
        for (VehicleRCEntity vehicleRCEntity : vehicleRcEntityList) {
            ids.add(vehicleRCEntity.getVehicleRcId());
            if (vehicleRCEntity.getOwnershipType() == OwnershipType.INDIVIDUAL.getId()){
                ids4CustInd.add(vehicleRCEntity.getVehicleRcId());
            }
            else{
                ids4CustCorp.add(vehicleRCEntity.getVehicleRcId());
            }
            if (appType.equalsIgnoreCase(RegistrationType.TR.getLabel()) && status == Status.APPROVED.getValue()) {
                Map<UserType, Status> vehicleRcMap = new HashMap<UserType, Status>();
                vehicleRcMap.put(UserType.ROLE_CCO, (vehicleRCEntity.getCcoActionStatus() == null) ? Status.PENDING : Status.getStatus(vehicleRCEntity.getCcoActionStatus()));
                vehicleRcMap.put(UserType.ROLE_MVI, (vehicleRCEntity.getMviActionStatus() == null) ? Status.PENDING : Status.getStatus(vehicleRCEntity.getMviActionStatus()));
                vehicleRcMap.put(UserType.ROLE_AO, (vehicleRCEntity.getAoActionStatus() == null) ? Status.PENDING : Status.getStatus(vehicleRCEntity.getAoActionStatus()));
                vehicleRcMap.put(UserType.ROLE_RTO, (vehicleRCEntity.getRtoActionStatus() == null) ? Status.PENDING : Status.getStatus(vehicleRCEntity.getRtoActionStatus()));
                appStatus.put(vehicleRCEntity.getVehicleRcId(), vehicleRcMap);
            }
        }
        /*if (appType.equalsIgnoreCase(RegistrationType.TR.getLabel()) && status == Status.APPROVED.getValue()) {
            
            List<VehicleRCHistoryEntity> vehicleRCHistoryEntities =
                    vehicleRCHistoryDAO.getAppFromHistoryByVehicleId(ids);
            for (VehicleRCHistoryEntity vehicleRCHistoryEntity : vehicleRCHistoryEntities) {
                if (appStatus.containsKey(vehicleRCHistoryEntity.getVehicleRc().getVehicleRcId())) {

                    if (!appStatus.get(vehicleRCHistoryEntity.getVehicleRc().getVehicleRcId()).keySet()
                            .contains(UserType.getUserType(vehicleRCHistoryEntity.getRtaEmployeeType()))) {
                        appStatus.get(vehicleRCHistoryEntity.getVehicleRc().getVehicleRcId()).put(
                                UserType.getUserType(vehicleRCHistoryEntity.getRtaEmployeeType()),
                                Status.getStatus(vehicleRCHistoryEntity.getStatus()));
                    }
                } else {
                    Map<UserType, Status> vehicleRcMap = new HashMap<UserType, Status>();
                    vehicleRcMap.put(UserType.getUserType(vehicleRCHistoryEntity.getRtaEmployeeType()),
                            Status.getStatus(vehicleRCHistoryEntity.getStatus()));
                    appStatus.put(vehicleRCHistoryEntity.getVehicleRc().getVehicleRcId(), vehicleRcMap);
                }
            }
            for (Long vehicleId : ids) {
                if (!appStatus.containsKey(vehicleId)) {
                    Map<UserType, Status> vehicleRcMap = new HashMap<UserType, Status>();
                    vehicleRcMap.put(UserType.ROLE_CCO, Status.PENDING);
                    vehicleRcMap.put(UserType.ROLE_MVI, Status.PENDING);
                    vehicleRcMap.put(UserType.ROLE_AO, Status.PENDING);
                    vehicleRcMap.put(UserType.ROLE_RTO, Status.PENDING);
                    appStatus.put(vehicleId, vehicleRcMap);
                } else {
                    if (!(appStatus.get(vehicleId).containsKey(UserType.ROLE_CCO))) {
                        appStatus.get(vehicleId).put(UserType.ROLE_CCO, Status.PENDING);
                    }
                    if (!(appStatus.get(vehicleId).containsKey(UserType.ROLE_MVI))) {
                        appStatus.get(vehicleId).put(UserType.ROLE_MVI, Status.PENDING);
                    }
                    if (!(appStatus.get(vehicleId).containsKey(UserType.ROLE_AO))) {
                        appStatus.get(vehicleId).put(UserType.ROLE_AO, Status.PENDING);
                    }
                    if (!(appStatus.get(vehicleId).containsKey(UserType.ROLE_RTO))) {
                        appStatus.get(vehicleId).put(UserType.ROLE_RTO, Status.PENDING);
                    }
                }
            }
        }*/
        Map<Long, VehicleDetailsEntity> vehicleDetailMap = new HashMap<Long, VehicleDetailsEntity>();
        List<VehicleDetailsEntity> vehicleDetailsList = new ArrayList<VehicleDetailsEntity>();
        vehicleDetailsList = vehicleDetailsDAO.getByVehicleRcIds(ids);
        List<Long> idsVehicleDetails = new ArrayList<Long>();
        if (vehicleDetailsList != null && vehicleDetailsList.size() > 0) {
            for (VehicleDetailsEntity vehicleDetailsEntity : vehicleDetailsList) {
                idsVehicleDetails.add(vehicleDetailsEntity.getVehicleDtlId());
                vehicleDetailMap.put(vehicleDetailsEntity.getVehicleRcId().getVehicleRcId(), vehicleDetailsEntity);
            }
        }

        Map<Long, VehicleBillingDetailsEntity> VehicleBillingDetailsMap =
                new HashMap<Long, VehicleBillingDetailsEntity>();
        List<VehicleBillingDetailsEntity> vehicleBillingDetailsEntitieList =
                new ArrayList<VehicleBillingDetailsEntity>();
        if (idsVehicleDetails != null && idsVehicleDetails.size() > 0) {
            vehicleBillingDetailsEntitieList = vehicleBillingDetailsDAO.getByVehicleBillingIds(idsVehicleDetails);
            for (VehicleBillingDetailsEntity VehicleBillingDetailsEntity : vehicleBillingDetailsEntitieList) {
                VehicleBillingDetailsMap.put(
                        VehicleBillingDetailsEntity.getVehicleDtlId().getVehicleRcId().getVehicleRcId(),
                        VehicleBillingDetailsEntity);
            }
        }
        Map<String, Object> custMapObject = new HashMap<String, Object>();
        Map<String, Object> custCorpMapObject = new HashMap<String, Object>();
        Integer noOfIndiApplication = 0, noOfCorpApplication = 0, totalRecords = 0;
        if (ids4CustInd != null && ids4CustInd.size() != 0) {
            custMapObject = getCustIndivDetails(ids4CustInd, vehicleDetailMap, VehicleBillingDetailsMap, appStatus,
                    query,dealersForUserId, userId);
        }
        if (ids4CustCorp != null && ids4CustCorp.size() != 0) {
            custCorpMapObject = getCustCorpDetails(ids4CustCorp, vehicleDetailMap,
                    VehicleBillingDetailsMap, appStatus, query,dealersForUserId, userId);
        }
        if (custMapObject != null && custMapObject.size() != 0) {
            applicationModelList.addAll((List<ApplicationModel>) custMapObject.get("applicationModelIndivLIst"));
            noOfIndiApplication = (Integer) custMapObject.get("totalCustIndiRecords");
        }
        if (custCorpMapObject != null && custCorpMapObject.size() != 0) {
            applicationModelList.addAll((List<ApplicationModel>) custCorpMapObject.get("applicationModelCorpLIst"));
            noOfCorpApplication = (Integer) custCorpMapObject.get("totalCustCorpRecords");
        }
        Collections.sort(applicationModelList, new SortApplicationModel());
        if(StringsUtil.isNullOrEmpty(query)){
            totalRecords = (Integer) vehicleRcEntityMap.get("totalRecords");
            mapObject.put("applicationModels", applicationModelList);
            mapObject.put("totalRecords", totalRecords);
        }else{
            totalRecords = noOfIndiApplication + noOfCorpApplication;
            List<ApplicationModel> subApplicationModelList = new ArrayList<ApplicationModel>();
            if (ObjectsUtil.isNull(applicationModelList) || applicationModelList.size() == 0) {
                subApplicationModelList = applicationModelList;
            } else if ((pageNumber - 1) * perPageRecords > applicationModelList.size()) {
                subApplicationModelList = null;
            }else if (pageNumber * perPageRecords <= applicationModelList.size()) {
                subApplicationModelList =
                        applicationModelList.subList((pageNumber - 1) * perPageRecords, pageNumber * perPageRecords);
            } else {
                subApplicationModelList =
                        applicationModelList.subList((pageNumber - 1) * perPageRecords, applicationModelList.size());
            }
            mapObject.put("applicationModels", subApplicationModelList);
            mapObject.put("totalRecords", totalRecords);
        }
        
        log.info(":::::::getApplicationStatus::mapObject:::End::: application Size:::" + mapObject.size());
        return mapObject;
    }
    
    private Map<Long, DealerEntity> getDealer4UserId(UserType userType, Long userId) {
        DealerEntity dealerEntity = dealerDAO.findByUserId(userId);
        if (userType == UserType.ROLE_DEALER && dealerEntity == null)
            throw new IllegalArgumentException("invalid DealerId");
        List<DealerEntity> dealers = dealerDAO.getAllChildDealers(dealerEntity.getDealerId());
        Map<Long, DealerEntity> dealerForUserIdMp = new HashMap<>();

        if (dealers == null || dealers.size() <= 0)
            return null;
        dealers.forEach(dealer -> dealerForUserIdMp.put(dealer.getUser().getUserId(), dealer));
        return dealerForUserIdMp;
    }

	@Override
	@Transactional
	public VehicleDetailsRequestModel getVehicleDetails(Long vehicleRcId, UserType userType) throws VehicleRcNotFoundException {
		VehicleDetailsEntity vde = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
		if (ObjectsUtil.isNull(vde)) {
			throw new VehicleRcNotFoundException();
		}
		VahanEntity vahanEntity = vahanDao.getByChassisNumber(vde.getChassisNo());
		if (ObjectsUtil.isNull(vahanEntity)) {
			throw new IllegalArgumentException("chassis number is invalid");
		}
		if (ObjectsUtil.isNull(vde)) {
			throw new VehicleRcNotFoundException();
		}
		VehicleBillingDetailsEntity vehicleBillingDetailsEntity = null;
		vehicleBillingDetailsEntity = vehicleBillingDetailsDAO.getByVehicleDetailId(vde);
		UnregisteredVehicleModel uvm = conversionService.convert(vde, UnregisteredVehicleModel.class);
		uvm.setVahanId(vahanEntity.getId());
		if(ObjectsUtil.isNull(uvm.getBodyTypeDesc())){
			uvm.setBodyTypeDesc(vahanEntity.getBodyTypeDesc());
		}
		if(ObjectsUtil.isNull(uvm.getEnginePower())){
			uvm.setEnginePower(vahanEntity.getEnginePower());
		}
		if(ObjectsUtil.isNull(uvm.getFrontAxleDesc())){
			uvm.setFrontAxleDesc(vahanEntity.getFrontAxleDesc());
		}
		if(ObjectsUtil.isNull(uvm.getFrontAxleWeight())){
			uvm.setFrontAxleWeight(vahanEntity.getFrontAxleWeight());
		}
		if(ObjectsUtil.isNull(uvm.getHeight())){
			uvm.setHeight(vahanEntity.getHeight());
		}
		if(ObjectsUtil.isNull(uvm.getLength())){
			uvm.setLength(vahanEntity.getLength());
		}
		if(ObjectsUtil.isNull(uvm.getNoCyl())){
			uvm.setNoCyl(vahanEntity.getNoCyl());
		}
		if(ObjectsUtil.isNull(uvm.getO1AxleDesc())){
			uvm.setO1AxleDesc(vahanEntity.getO1AxleDesc());
		}
		if(ObjectsUtil.isNull(uvm.getO1AxleWeight())){
			uvm.setO1AxleWeight(vahanEntity.getO1AxleWeight());
		}
		if(ObjectsUtil.isNull(uvm.getO2AxleDesc())){
			uvm.setO2AxleDesc(vahanEntity.getO2AxleDesc());
		}
		if(ObjectsUtil.isNull(uvm.getO2AxleWeight())){
			uvm.setO2AxleWeight(vahanEntity.getO2AxleWeight());
		}
		if(ObjectsUtil.isNull(uvm.getO3AxleDesc())){
			uvm.setO3AxleDesc(vahanEntity.getO3AxleDesc());
		}
		if(ObjectsUtil.isNull(uvm.getO3AxleWeight())){
			uvm.setO3AxleWeight(vahanEntity.getO3AxleWeight());
		}
		if(ObjectsUtil.isNull(uvm.getO4AxleDesc())){
			uvm.setO4AxleDesc(vahanEntity.getO4AxleDesc());
		}
		if(ObjectsUtil.isNull(uvm.getO4AxleWeight())){
			uvm.setO4AxleWeight(vahanEntity.getO4AxleWeight());
		}
		if(ObjectsUtil.isNull(uvm.getO5AxleDesc())){
			uvm.setO5AxleDesc(vahanEntity.getO5AxleDesc());
		}
		if(ObjectsUtil.isNull(uvm.getO5AxleWeight())){
			uvm.setO5AxleWeight(vahanEntity.getO5AxleWeight());
		}
		if(ObjectsUtil.isNull(uvm.getPollutionNormsDesc())){
			uvm.setPollutionNormsDesc(vahanEntity.getPollutionNormsDesc());
		}
		if(ObjectsUtil.isNull(uvm.getRearAxleDesc())){
			uvm.setRearAxleDesc(vahanEntity.getRearAxleDesc());
		}
		if(ObjectsUtil.isNull(uvm.getRearAxleWeight())){
			uvm.setRearAxleWeight(vahanEntity.getRearAxleWeight());
		}
		if(ObjectsUtil.isNull(uvm.getRlw())){
			uvm.setRlw(vahanEntity.getRlw());
		}
		if(ObjectsUtil.isNull(uvm.getStandCapacity())){
			uvm.setStandCapacity(vahanEntity.getStandCapacity());
		}
		if(ObjectsUtil.isNull(uvm.getTandemAxelDescp())){
			uvm.setTandemAxelDescp(vahanEntity.getTandemAxelDescp());
		}
		if(ObjectsUtil.isNull(uvm.getTandemAxelWeight())){
			uvm.setTandemAxelWeight(vahanEntity.getTandemAxelWeight());
		}
		if(ObjectsUtil.isNull(uvm.getCubicCapacity())){
			uvm.setCubicCapacity(vahanEntity.getCubicCap());
		}
		if(ObjectsUtil.isNull(uvm.getUlw())){
			uvm.setUlw(vahanEntity.getUlw());
		}
		if(ObjectsUtil.isNull(uvm.getWheelbase())){
			uvm.setWheelbase(vahanEntity.getWheelbase());
		}
		if(ObjectsUtil.isNull(uvm.getWidth())){
			uvm.setWidth(vahanEntity.getWidth());
		}
		uvm.setManufacturingMonthYear(vde.getMfgDate());
		uvm.setVehicleSubClass(vde.getVehicleSubClass());
		if (!StringsUtil.isNullOrEmpty(vde.getVehicleSubClass())) {
			uvm.setVehicleClassDes(
					vehicleClassDescDAO.getByVehiclClassDesCode(vde.getVehicleSubClass()).getDescription());
		}
		if(vde.getVehicleCategory() == VehicleCategory.CHASSIS_ONLY 
        		&& vde.getVehicleRcId().getPrStatus() == Status.PENDING.getValue() ){
			try{
				uvm.setAlterationDetails(vehicleDetailsService.getVehicleAlterationDetails(vehicleRcId, userType));
			}catch (Exception e) {
				log.error("Getting Error in vehicle Alteration details..."+vehicleRcId);
			}
			
		}
		VehicleDetailsRequestModel vdrm = new VehicleDetailsRequestModel();
		vdrm.setVehicle(uvm);
		PurchaseDetailsModel billModel = new PurchaseDetailsModel();
		if(vehicleBillingDetailsEntity != null){
			billModel.setHelmetInvoiceAmount(vehicleBillingDetailsEntity.getHelmetInvoiceAmount());
			billModel.setInvoiceDate(vehicleBillingDetailsEntity.getInvoiceDate());
			billModel.setInvoiceNumber(vehicleBillingDetailsEntity.getInvoiceNumber());
			billModel.setInvoiceValue(vehicleBillingDetailsEntity.getInvoiceValue());
			billModel.setCreatedBy(vehicleBillingDetailsEntity.getCreatedBy());
			billModel.setCreatedOn(vehicleBillingDetailsEntity.getCreatedOn());
			billModel.setModifiedBy(vehicleBillingDetailsEntity.getModifiedBy());
			billModel.setModifiedOn(vehicleBillingDetailsEntity.getModifiedOn());
		}	
		VehicleRCEntity ve = vehicleDao.get(vehicleRcId);
		DealerEntity userEntity = dealerDAO.findByUserId(ve.getUserId().getUserId());
		billModel.setDealerName(userEntity.getName());
		billModel.setDealerUserName(userEntity.getUser().getUserName());
		billModel.setOriginalRegAuthority(ve.getRtaOfficeId().getName());
		billModel.setOnRoadAmt(0.0);// TODO : on road amount is not in db
		AddressEntity addressEntity = addressDAO.findByUserIdAndType(userEntity.getUser().getUserId(), "T");
		if (!ObjectsUtil.isNull(addressEntity)) {
			billModel.setCityName(addressEntity.getCity());
			billModel.setDistrictName(addressEntity.getMandal().getDistrictEntity().getName());
		}
		vdrm.setBillModel(billModel);
		// vdrm.setRegistrationCategory(vde.getVehicleRcId().getRegCategory().getRegistrationCategoryId());
		vdrm.setDealerRTA(vde.getVehicleRcId().getRtaOfficeId().getRtaOfficeId());
		vdrm.setVehicleRcId(String.valueOf(vehicleRcId));

		// ------ get pan card number -------------
		String panNumber = null;
		Boolean isSecondVehicle = null;
		if (OwnershipType.getOwnershipType(ve.getOwnershipType()) == OwnershipType.INDIVIDUAL) {
			CustIndividualDetailsEntity cde = custIndvDAO.getByVehicleRcId(vehicleRcId);
			if (ObjectsUtil.isNull(cde)) {
				log.error("Customer Individual details does not exist with given vehicleRCID : " + vehicleRcId);
				throw new IllegalArgumentException("Customer details does not exist with given vehicle.");
			}
			panNumber = cde.getPanNo();
			isSecondVehicle = cde.getIsSecondVehicle();
		} else if (OwnershipType.getOwnershipType(ve.getOwnershipType()) == OwnershipType.COMPANY) {
			CustCorporateDetailsEntity ccd = customerCorporateDAO.getByVehicleRcId(vehicleRcId);
			if (ObjectsUtil.isNull(ccd)) {
				log.error("Customer Corporate details does not exist with given vehicleRCID : " + vehicleRcId);
				throw new IllegalArgumentException("Customer details does not exist with given vehicle.");
			}
			panNumber = ccd.getPanNo();
		}
		vdrm.setPanNumber(panNumber);
		vdrm.setIsSecondVehicle(isSecondVehicle);
		vdrm.setRegCategoryDetails(new RegistrationCategoryModel());
		vdrm.getRegCategoryDetails().setCode(ve.getRegCategory().getCode());
		vdrm.getRegCategoryDetails().setName(ve.getRegCategory().getName());
		vdrm.setPrNumber(ve.getPrNumber());
		vdrm.setPrExpireDate(ve.getPrExpireDate());
		vdrm.setPrIssueDate(ve.getPrIssueTime());
		return vdrm;
	}

	@Override
	@Transactional
	public Long getApplicationCount(Long userId, String type, Status status, String regCatogry) {
		Map<Long, DealerEntity> dealers = getDealer4UserId(UserType.ROLE_DEALER, userId);
		if (dealers == null || dealers.size() == 0)
			return vehicleDao.getApplicationCount(userId, type, status, getRegistrationCategoryId(regCatogry));

		List<Long> userIds = new ArrayList(dealers.keySet());
		List<VehicleRCEntity> applications = vehicleDao.getApplicationStatus4Users(userIds, type, status.getValue(),
				getRegistrationCategoryId(regCatogry));
		return applications == null ? 0l : applications.size();
	}

	@Override
	@Transactional
	public SaveUpdateResponse updateStepSeven(Long vehicleRcId) {
		log.info("::::::::updateStepSeven:::::::");
		SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
		VehicleRCEntity vRcEntity = vehicleDao.get(vehicleRcId);
		if (ObjectsUtil.isNull(vRcEntity)) {
			log.error("::::::::updateStepSeven::::Exception:::::" + vRcEntity);
			saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
		} else {
			vRcEntity.setCurrentStep(prTypeStep);
			vehicleDao.saveOrUpdate(vRcEntity);
			saveUpdateResponse.setStatus(SaveUpdateResponse.SUCCESS);
		}
		saveUpdateResponse.setCurrentStep(prTypeStep);

		return saveUpdateResponse;
	}

	@Override
	@Transactional
	public SaveUpdateResponse updatePRNumberType(String userName, Long vehicleRcId, PRType numberType) {
		SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
		VehicleRCEntity vRcEntity = vehicleDao.get(vehicleRcId);
		if (ObjectsUtil.isNull(vRcEntity)) {
			log.info("vehicleRcId not found" + vehicleRcId);
			saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
		} else {
			vRcEntity.setModifiedBy(userName);
			vRcEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			if (vRcEntity.getCurrentStep() < prTypeStep) {
				vRcEntity.setCurrentStep(prTypeStep);
			}
			vRcEntity.setPrType(numberType.getId());
			vehicleDao.saveOrUpdate(vRcEntity);
			saveUpdateResponse.setStatus(SaveUpdateResponse.SUCCESS);
		}
		saveUpdateResponse.setVehicleRcId(String.valueOf(vehicleRcId));
		return saveUpdateResponse;
	}

	@Override
	@Transactional
	public Map<String, String> getIsPanCardMandatory(Long vehicleRcId, String vehicleClass, Double amount) {
		Map<String, String> map = new HashMap<String, String>();
		boolean isfourWheeler = false;
		VehicleRCEntity ve = vehicleDao.get(vehicleRcId);
		if (ObjectsUtil.isNull(ve)) {
			log.error("Invalid VehicleRcId : " + vehicleRcId);
			throw new IllegalArgumentException("Invalid VehicleRcId !!!");
		}
		if (amount <= 0.0) {
			log.error("Invalid Amount : " + amount);
			throw new IllegalArgumentException("Invalid Invoice Amount !!!");
		}

		if (!StringsUtil.isNullOrEmpty(vehicleClass) && !vehicleClass.startsWith(ISTWOFOURWHEELER)) {
			isfourWheeler = true;
		}

		if ((OwnershipType.getOwnershipType(ve.getOwnershipType()) == OwnershipType.INDIVIDUAL
				&& amount >= panCardInvoiceAmt)
				|| (OwnershipType.getOwnershipType(ve.getOwnershipType()) == OwnershipType.COMPANY)
				|| (isfourWheeler && !(OwnershipType.getOwnershipType(ve.getOwnershipType()) == OwnershipType.POLICE)
						|| (OwnershipType.getOwnershipType(ve.getOwnershipType()) == OwnershipType.STU_VEHICLES)
						|| (OwnershipType.getOwnershipType(ve.getOwnershipType()) == OwnershipType.ORGANIZATION))) {
			map.put("panCardMandatory", "Yes");
		} else {
			map.put("panCardMandatory", "No");
		}
		return map;
	}

	@Override
	@Transactional
	public PRType getPRType(Long vehicleRcId) throws VehicleRcNotFoundException {
		VehicleRCEntity ve = vehicleDao.get(vehicleRcId);
		if (ObjectsUtil.isNull(ve)) {
			log.error("Invalid VehicleRcId : " + vehicleRcId);
			throw new VehicleRcNotFoundException("Invalid VehicleRcId!");
		}
		return PRType.getPRType(ve.getPrType());
	}

	@Override
	@Transactional
	public VehicleDetailsRequestModel getVehicleDetailsByChassisNo(String chassisNumber) {
		VehicleDetailsEntity vde = vehicleDetailsDAO.getByChassisNo(chassisNumber);
		if (ObjectsUtil.isNull(vde)) {
			return null;
		}
		UnregisteredVehicleModel uvm = conversionService.convert(vde, UnregisteredVehicleModel.class);
		VehicleDetailsRequestModel vm = new VehicleDetailsRequestModel();
		vm.setVehicleRcId(vde.getVehicleRcId().getVehicleRcId().toString());
		vm.setVehicle(uvm);
		return vm;
	}

	private Integer getRegistrationCategoryId(String registrationCategoryId) {

		if (StringsUtil.isNullOrEmpty(registrationCategoryId)) {
			return null;
		}
		return registrationCategoryDAO.getByCode(registrationCategoryId) == null ? null
				: registrationCategoryDAO.getByCode(registrationCategoryId).getRegistrationCategoryId();

	}

	@Override
	@Transactional
	public SaveUpdateResponse update(DuplicateRegistrationModel model, String userName) throws DataMismatchException {
		VehicleRCEntity rcEntity = vehicleDao.getVehicleRCByPRNumber(model.getPrNumber());
		if (ObjectsUtil.isNull(rcEntity)) {
			throw new DataMismatchException();
		}
		try {
			String result = vehicleRCChangeHistoryDAO.saveData(rcEntity.getVehicleRcId(), userName,
					DateUtil.toCurrentUTCTimeStamp(), ServiceType.DUPLICATE_REGISTRATION);
			log.info("One entity in history table staus " + result);
		} catch (Exception e) {
		}
		rcEntity.setIsPrinted(false);
		rcEntity.setRcPrintDate(null);
		rcEntity.setModifiedBy(userName);
		rcEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		vehicleDao.update(rcEntity);
		DuplicateRegistrationEntity entity = new DuplicateRegistrationEntity();
		entity.setDuplicateReason(model.getReason());
		entity.setComments(model.getComment());
		entity.setVehicleRcId(vehicleDao.get(rcEntity.getVehicleRcId()));
		entity.setModifiedBy(userName);
		entity.setCreatedBy(userName);
		entity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		entity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		duplicateRegistrationDAO.save(entity);
		
		updateRcApproverUserId(model.getActionModelList(), rcEntity.getVehicleRcId());
		
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Update Successfully",
				String.valueOf(rcEntity.getVehicleRcId()));
	}

	@Override
	@Transactional /// In-completed
	public SaveUpdateResponse update(CommonServiceModel model, String userName) throws DataMismatchException, NotFoundException {
		log.debug("Updating VehicleRcEntity : " + model);
		VehicleRCEntity rcEntity = vehicleDao.getVehicleRc4Pr(model.getPrNumber());
		if (ObjectsUtil.isNull(rcEntity)) {
			throw new DataMismatchException();
		}
		try {
			String result = vehicleRCChangeHistoryDAO.saveData(rcEntity.getVehicleRcId(), userName,
					DateUtil.toCurrentUTCTimeStamp(), model.getServiceType());
			log.info("One entity in history table staus " + result);
		} catch (Exception e) {
		}
		if (model.getServiceType() == ServiceType.REGISTRATION_RENEWAL) {
			rcEntity.setPrIssueTime(model.getApprovedDate());
			rcEntity.setPrExpireDate(DateUtil.addYears(model.getApprovedDate(), rcRenewalYear));
		} else if (model.getServiceType() == ServiceType.REGISTRATION_CANCELLATION) {
			String prReleaseStatus = cancelRC(rcEntity, userName, model);
			log.info("One entity in PR Release table " + prReleaseStatus);
			rcEntity.setPrStatus(Status.CANCELLED.getValue());
			rcEntity.setPrNumber(null);
		} else if (model.getServiceType() == ServiceType.REGISTRATION_SUS_CANCELLATION) {
			Status suspensionType = model.getSuspensionType();
			if (suspensionType == Status.SUSPENDED) {
				suspendPRNumber(model, userName, rcEntity);
				rcEntity.setPrStatus(Status.SUSPENDED.getValue());
			} else if (suspensionType == Status.OBJECTION) {
			    objectPRNumber(model, userName, rcEntity);
                rcEntity.setPrStatus(Status.OBJECTION.getValue());
            } else if (suspensionType == Status.CANCELLED) {
				cancelRC(rcEntity, userName, model);
				rcEntity.setPrStatus(Status.CANCELLED.getValue());
				rcEntity.setPrNumber(null);
			}
		} else if (model.getServiceType() == ServiceType.SUSPENSION_REVOCATION) {
			rcEntity.setPrStatus(Status.APPROVED.getValue());
			revocateSuspObjRC(rcEntity);
		}/*
		#TODO remove this code
		 else if (model.getServiceType() == ServiceType.VEHICLE_REASSIGNMENT) {
            String prReleaseStatus = cancelRC(rcEntity, userName);
            log.info("One entity in PR Release table " + prReleaseStatus);
            if (!otherStateCode.equalsIgnoreCase(model.getCode())) {
                rcEntity.setRegCategory(registrationCategoryDAO.getByCode(model.getCode()));
            }
            rcEntity.setPrNumber(applicationService.getPrSeriesNo(rcEntity));
            log.info("PR Number is getting in case of VEHICLE_REASSIGNMENT prNumber is " + rcEntity.getPrNumber());
            // TODO : update reg_category in all tax and fee details tables
            // (transaction_detail,dealer_invoice,reg_fee_detail,tax_detail)
        }*/
		rcEntity.setModifiedBy(userName);
		rcEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		vehicleDao.saveOrUpdate(rcEntity);
		log.debug("::Renewal Of registration:::::");
		try {
			if(model.getTaxModel() != null && model.getTaxModel().getGreenTaxValidTo() != null){
				GreenTaxDetailsEntity greenTaxDetailsEntity= null;
				greenTaxDetailsEntity = greenTaxDetailsDAO.getByVehicleRcId(rcEntity.getVehicleRcId());
				if(greenTaxDetailsEntity != null){
					greenTaxDetailsEntity.setGreenTaxValidTo(model.getTaxModel().getGreenTaxValidTo());
					greenTaxDetailsEntity.setGreenTaxAmt(Double.parseDouble(model.getTaxModel().getGreenTaxAmt()));
					greenTaxDetailsEntity.setModifiedBy("CITIZEN1");
					greenTaxDetailsEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
				}else{
					greenTaxDetailsEntity = new GreenTaxDetailsEntity();
					greenTaxDetailsEntity.setGreenTaxValidTo(model.getTaxModel().getGreenTaxValidTo());
					greenTaxDetailsEntity.setGreenTaxAmt(Double.parseDouble(model.getTaxModel().getGreenTaxAmt()));
					greenTaxDetailsEntity.setVehicleRc(rcEntity);
					greenTaxDetailsEntity.setCreatedBy("CITIZEN1");
					greenTaxDetailsEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
				}
				greenTaxDetailsDAO.saveOrUpdate(greenTaxDetailsEntity);
			}
		} catch (Exception e) {
			log.error(":::Renewal of registration Green Tax Sync::::::: " + e.getMessage());
			e.printStackTrace();
		}
		
		updateRcApproverUserId(model.getActionModelList(), rcEntity.getVehicleRcId());
		
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Update Successfully",
				String.valueOf(rcEntity.getVehicleRcId()));
	}

	public void suspendPRNumber(CommonServiceModel model, String userName, VehicleRCEntity rcEntity) {
		Long currentTime = DateUtil.toCurrentUTCTimeStamp();
		SuspendedRCNumbersEntity entity = suspendedRCNumberDAO.getDetails(rcEntity.getPrNumber(), Boolean.FALSE, Status.SUSPENDED);
		if (entity == null) {
			entity = new SuspendedRCNumbersEntity();
			entity.setCreatedBy(userName);
			entity.setCreatedOn(currentTime);
		}
		entity.setStartDate(model.getStartTime());
		entity.setEndDate(DateUtil.addMonthsNew(model.getStartTime(), model.getSuspensionTime()));
		entity.setIsRevoked(Boolean.FALSE);
		entity.setModifiedBy(userName);
		entity.setModifiedOn(currentTime);
		entity.setPrNumber(rcEntity.getPrNumber());
		entity.setStatus(Status.SUSPENDED.getValue());
		entity.setComment(model.getComment());
		entity.setDescription(model.getReason());
		suspendedRCNumberDAO.saveOrUpdate(entity);
	}
	
	public void objectPRNumber(CommonServiceModel model, String userName, VehicleRCEntity rcEntity) {
        Long currentTime = DateUtil.toCurrentUTCTimeStamp();
        SuspendedRCNumbersEntity entity = suspendedRCNumberDAO.getDetails(rcEntity.getPrNumber(), Boolean.FALSE, Status.OBJECTION);
        if (entity == null) {
            entity = new SuspendedRCNumbersEntity();
            entity.setCreatedBy(userName);
            entity.setCreatedOn(currentTime);
        }
        entity.setIsRevoked(Boolean.FALSE);
        entity.setModifiedBy(userName);
        entity.setModifiedOn(currentTime);
        entity.setPrNumber(rcEntity.getPrNumber());
        entity.setStatus(Status.OBJECTION.getValue());
        entity.setComment(model.getComment());
        entity.setDescription(model.getReason());
        entity.setRaisedBy(model.getRaisedBy());
        suspendedRCNumberDAO.saveOrUpdate(entity);
    }

	public void revocateSuspObjRC(VehicleRCEntity rcEntity){
	    //-------revocate suspended RC---------------------------
	    SuspendedRCNumbersEntity entity = suspendedRCNumberDAO.getDetails(rcEntity.getPrNumber(), Boolean.FALSE, Status.SUSPENDED);
	    if(ObjectsUtil.isNotNull(entity)){
	        try{
	            entity.setIsRevoked(Boolean.TRUE);
	            entity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
	            suspendedRCNumberDAO.update(entity);
	        } catch(Exception ex){
	            log.error("Error while updatnng is revoked for suspended RC");
	        }
	    }
	    //-------revocate Objected RC---------------------------
	    entity = suspendedRCNumberDAO.getDetails(rcEntity.getPrNumber(), Boolean.FALSE, Status.OBJECTION);
        if(ObjectsUtil.isNotNull(entity)){
            try{
                entity.setIsRevoked(Boolean.TRUE);
                entity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
                suspendedRCNumberDAO.update(entity);
            } catch(Exception ex){
                log.error("Error while updatnng is revoked for objected RC");
            }
        }
	}
	
	@Override
	@Transactional
	public SaveUpdateResponse syncTI(TheftIntimationRevocationModel theftModel, String prNumber, String userName)
			throws DataMismatchException {
		VehicleRCEntity rcEntity = vehicleDao.getVehicleRc4Pr(prNumber);
		if (ObjectsUtil.isNull(rcEntity)) {
			throw new DataMismatchException();
		}
		try {
			String result = vehicleRCChangeHistoryDAO.saveData(rcEntity.getVehicleRcId(), userName,
					DateUtil.toCurrentUTCTimeStamp(), ServiceType.THEFT_INTIMATION);
			log.info("One entity in history table staus " + result);
		} catch (Exception e) {
		}
		if (theftModel.getTheftStatus() == TheftIntSusType.FRESH) {
			rcEntity.setPrStatus(Status.THEFT_INTIMATION.getValue());
		} else if (theftModel.getTheftStatus() == TheftIntSusType.REVOCATION) {
			rcEntity.setPrStatus(Status.APPROVED.getValue());
		}
		vehicleDao.saveOrUpdate(rcEntity);
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Saved/Update Successfully", null);
	}

	private String cancelRC(VehicleRCEntity rcEntity, String userName, CommonServiceModel model) {
		Long currentTime = DateUtil.toCurrentUTCTimeStamp();
        SuspendedRCNumbersEntity entity = suspendedRCNumberDAO.getDetails(rcEntity.getPrNumber(), Boolean.FALSE, Status.CANCELLED);
        if (entity == null) {
            entity = new SuspendedRCNumbersEntity();
            entity.setCreatedBy(userName);
            entity.setCreatedOn(currentTime);
        }
        entity.setStartDate(currentTime);
        entity.setIsRevoked(Boolean.FALSE);
        entity.setModifiedBy(userName);
        entity.setModifiedOn(currentTime);
        entity.setPrNumber(rcEntity.getPrNumber());
        entity.setStatus(Status.CANCELLED.getValue());
        entity.setComment(model.getComment());
        entity.setDescription(model.getReason());
        suspendedRCNumberDAO.saveOrUpdate(entity);
        
        //----------save in vehicle pr release for history ----------------
        try{
        	VehiclePRReleaseEntity prReleaseEntity = new VehiclePRReleaseEntity();
        	prReleaseEntity.setPrNumber(entity.getPrNumber());
        	prReleaseEntity.setCreatedOn(currentTime);
        	prReleaseEntity.setServiceType(model.getServiceType().getCode());
        	prReleaseEntity.setVehicleRcId(rcEntity.getVehicleRcId());
        	vehiclePRReleaseDAO.save(prReleaseEntity);
        } catch(Exception ex){
        }
        
		return SaveUpdateResponse.SUCCESS;
	}

	@Override
	@Transactional
	public VehicleDetailsRequestModel getVehicleClass(String prNumber) {
	VehicleRCEntity vehiclerc = vehicleDao.getVehicleRc4Pr(prNumber);
	VehicleDetailsEntity vehicledetails = vehicleDetailsDAO.getByVehicleRcId(vehiclerc.getVehicleRcId());
	UnregisteredVehicleModel model = conversionService.convert(vehicledetails, UnregisteredVehicleModel.class);
	if (!StringsUtil.isNullOrEmpty(vehicledetails.getVehicleSubClass())) {
		model.setVehicleClassDes(
				vehicleClassDescDAO.getByVehiclClassDesCode(vehicledetails.getVehicleSubClass()).getDescription());
	}
	VehicleDetailsRequestModel vehicledetailsqequestmodel = new VehicleDetailsRequestModel();
	vehicledetailsqequestmodel.setVehicle(model);
	vehicledetailsqequestmodel.setVehicleCode(vehicledetails.getVehicleSubClass());
	return vehicledetailsqequestmodel;
	}
	
	@SuppressWarnings("unchecked")
    public Map<String, Object> getCustIndivDetails(List<Long> vehicleRcIds,
            Map<Long, VehicleDetailsEntity> vehicleDetailMap,
            Map<Long, VehicleBillingDetailsEntity> VehicleBillingDetailsMap, Map<Long, Map<UserType, Status>> appStatus,
            String query,Map<Long,DealerEntity> dealerForUserIdMp, Long myUserId) {
        log.info(":::::::getCustIndivDetails start::::::::::");
        List<ApplicationModel> applicationModelIndivLIst = new ArrayList<ApplicationModel>();
        Map<String, Object> mapObject = new HashMap<String, Object>();
        ApplicationModel applicationModelIndiv = null;
        VehicleBaseModel vehicleBaseModel = null;
        BillingDetailsModel billingDetailsModel = null;
        CustomerDetailsRequestModel customerDetailsRequestModel = null;
        mapObject = custIndvDAO.getByVehicleRcIds(vehicleRcIds, null, null, query);
        List<CustIndividualDetailsEntity> custDtlsEntityList =
                (List<CustIndividualDetailsEntity>) mapObject.get("custIndividualDetailsEntity");
        Integer totalCustIndiRecords = (Integer) mapObject.get("totalCustIndiRecords");
        for (CustIndividualDetailsEntity custDtlsEntity : custDtlsEntityList) {
            applicationModelIndiv = new ApplicationModel();
            customerDetailsRequestModel = new CustomerDetailsRequestModel();
            vehicleBaseModel = new VehicleBaseModel();
            billingDetailsModel = new BillingDetailsModel();
            customerDetailsRequestModel.setFirst_name(custDtlsEntity.getFirstName());
            customerDetailsRequestModel.setSurName(custDtlsEntity.getSurName());
            if (vehicleDetailMap.get(custDtlsEntity.getVehicleRcId().getVehicleRcId()) != null) {
                vehicleBaseModel.setChassisNumber(
                        vehicleDetailMap.get(custDtlsEntity.getVehicleRcId().getVehicleRcId()).getChassisNo());
                vehicleBaseModel.setMakersClass(
                        vehicleDetailMap.get(custDtlsEntity.getVehicleRcId().getVehicleRcId()).getMakerClass());
                vehicleBaseModel.setVehicleSubClass(vehicleDetailMap.get(custDtlsEntity.getVehicleRcId().getVehicleRcId()).getVehicleSubClass());
            }
            if (VehicleBillingDetailsMap.get(custDtlsEntity.getVehicleRcId().getVehicleRcId()) != null) {
                billingDetailsModel.setInvoiceNumber(VehicleBillingDetailsMap
                        .get(custDtlsEntity.getVehicleRcId().getVehicleRcId()).getInvoiceNumber());
            }
            applicationModelIndiv.setCustomerDetails(customerDetailsRequestModel);
            applicationModelIndiv.setVehicleModel(vehicleBaseModel);
            applicationModelIndiv.setBillingDetails(billingDetailsModel);
            applicationModelIndiv.setAppStatus(appStatus.get(custDtlsEntity.getVehicleRcId().getVehicleRcId()));
            applicationModelIndiv.setApplicationDate(custDtlsEntity.getCreatedOn());
            if(custDtlsEntity.getVehicleRcId().getTrStatus() == Status.APPROVED.getValue()){
                applicationModelIndiv.setCompStatus(100);
            } else {
                applicationModelIndiv.setCompStatus(100 * custDtlsEntity.getVehicleRcId().getCurrentStep() / totalStep);
            }
            applicationModelIndiv.setVehicleRcId(custDtlsEntity.getVehicleRcId().getVehicleRcId());
            applicationModelIndiv.setCurrentStep(custDtlsEntity.getVehicleRcId().getCurrentStep());
            applicationModelIndiv.setUserId(custDtlsEntity.getVehicleRcId().getUserId().getUserName());
            
            if(dealerForUserIdMp!=null && dealerForUserIdMp.size()>0 && dealerForUserIdMp.containsKey(custDtlsEntity.getVehicleRcId().getUserId().getUserId())){
                if(myUserId.compareTo(custDtlsEntity.getVehicleRcId().getUserId().getUserId()) == 0){
                    applicationModelIndiv.setPaymentAllow(dealerForUserIdMp.get(custDtlsEntity.getVehicleRcId().getUserId().getUserId()).getEligibleToPay());
                } else {
                    applicationModelIndiv.setPaymentAllow(!dealerForUserIdMp.get(custDtlsEntity.getVehicleRcId().getUserId().getUserId()).getEligibleToPay());
                }
            }
            
            applicationModelIndivLIst.add(applicationModelIndiv);

        }
        mapObject = new HashMap<String, Object>();
        mapObject.put("applicationModelIndivLIst", applicationModelIndivLIst);
        mapObject.put("totalCustIndiRecords", totalCustIndiRecords);
        log.info(":::::::getCustIndivDetails end::::::::::");
        return mapObject;
    }


    @SuppressWarnings("unchecked")
    public Map<String, Object> getCustCorpDetails(List<Long> vehicleRcIds,
            Map<Long, VehicleDetailsEntity> vehicleDetailMap,
            Map<Long, VehicleBillingDetailsEntity> VehicleBillingDetailsMap, Map<Long, Map<UserType, Status>> appStatus,
            String query,Map<Long,DealerEntity> dealerForUserIdMp, Long myUserId) {
        log.info(":::::::getCustCorpDetails start::::::::::");
        List<ApplicationModel> applicationModelCorpLIst = new ArrayList<ApplicationModel>();
        Map<String, Object> mapObject = new HashMap<String, Object>();
        ApplicationModel applicationModelCorp = null;
        VehicleBaseModel vehicleBaseModel = null;
        BillingDetailsModel billingDetailsModel = null;
        CustomerDetailsRequestModel customerDetailsRequestModel = null;
        mapObject = customerCorporateDAO.getByVehicleRcIds(vehicleRcIds, null, null, query);
        List<CustCorporateDetailsEntity> custDtlsEntityList =
                (List<CustCorporateDetailsEntity>) mapObject.get("custCorporateDetailsEntity");
        Integer totalCustCorpRecords = (Integer) mapObject.get("totalCustCorpRecords");
        for (CustCorporateDetailsEntity custDtlsEntity : custDtlsEntityList) {
            applicationModelCorp = new ApplicationModel();
            customerDetailsRequestModel = new CustomerDetailsRequestModel();
            vehicleBaseModel = new VehicleBaseModel();
            billingDetailsModel = new BillingDetailsModel();
            customerDetailsRequestModel.setFirst_name(custDtlsEntity.getCompanyName());
            // customerDetailsRequestModel.setSurName(custDtlsEntity.getCompanyName());
            if (vehicleDetailMap.get(custDtlsEntity.getVehicleRcId().getVehicleRcId()) != null) {
                vehicleBaseModel.setChassisNumber(
                        vehicleDetailMap.get(custDtlsEntity.getVehicleRcId().getVehicleRcId()).getChassisNo());
                vehicleBaseModel.setMakersClass(
                        vehicleDetailMap.get(custDtlsEntity.getVehicleRcId().getVehicleRcId()).getMakerClass());
            }
            if (VehicleBillingDetailsMap.get(custDtlsEntity.getVehicleRcId().getVehicleRcId()) != null) {
                billingDetailsModel.setInvoiceNumber(VehicleBillingDetailsMap
                        .get(custDtlsEntity.getVehicleRcId().getVehicleRcId()).getInvoiceNumber());
            }
            applicationModelCorp.setCustomerDetails(customerDetailsRequestModel);
            applicationModelCorp.setVehicleModel(vehicleBaseModel);
            applicationModelCorp.setBillingDetails(billingDetailsModel);
            applicationModelCorp.setAppStatus(appStatus.get(custDtlsEntity.getVehicleRcId().getVehicleRcId()));
            applicationModelCorp.setApplicationDate(custDtlsEntity.getCreatedOn());
            if(custDtlsEntity.getVehicleRcId().getTrStatus() == Status.APPROVED.getValue()){
                applicationModelCorp.setCompStatus(100);
            } else {
                applicationModelCorp.setCompStatus(100 * custDtlsEntity.getVehicleRcId().getCurrentStep() / totalStep);
            }
            applicationModelCorp.setVehicleRcId(custDtlsEntity.getVehicleRcId().getVehicleRcId());
            applicationModelCorp.setCurrentStep(custDtlsEntity.getVehicleRcId().getCurrentStep());
            
            applicationModelCorp.setUserId(custDtlsEntity.getVehicleRcId().getUserId().getUserName());
            
            if(dealerForUserIdMp!=null && dealerForUserIdMp.size()>0 && dealerForUserIdMp.containsKey(custDtlsEntity.getVehicleRcId().getUserId().getUserId())){
                if(myUserId.compareTo(custDtlsEntity.getVehicleRcId().getUserId().getUserId()) == 0){
                    applicationModelCorp.setPaymentAllow(dealerForUserIdMp.get(custDtlsEntity.getVehicleRcId().getUserId().getUserId()).getEligibleToPay());
                } else {
                    applicationModelCorp.setPaymentAllow(!dealerForUserIdMp.get(custDtlsEntity.getVehicleRcId().getUserId().getUserId()).getEligibleToPay());
                }
            }
            applicationModelCorpLIst.add(applicationModelCorp);

        }
        mapObject = new HashMap<String, Object>();
        mapObject.put("applicationModelCorpLIst", applicationModelCorpLIst);
        mapObject.put("totalCustCorpRecords", totalCustCorpRecords);
        log.info(":::::::getCustCorpDetails end::::::::::");
        return mapObject;
    }
    
    @Override
    @Transactional
    public Boolean getIsPrGenerated(Long vehicleRcId){
        VehicleRCEntity ve = vehicleDao.get(vehicleRcId);
        if (ObjectsUtil.isNull(ve)) {
            return Boolean.FALSE;
        }else if(ve.getPrStatus() == Status.APPROVED.getValue()){
            return Boolean.TRUE;
        }
       return Boolean.FALSE;
    }
    
    @Override
    @Transactional
    public Boolean getIsBharatStageThreeVehicle(String chassisNumber) {
        VehicleBharatStageEntity vde = VehicleBharatStageDAO.getByChassisNumber(chassisNumber);
        if (ObjectsUtil.isNull(vde)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

	@Override
	@Transactional
	public List<VehicleClassDescModel> getAlterationCovList(String prNumber,String regCatCode) {
		List<VehicleClassDescModel> classDescModelList=new ArrayList<>();
		List<VehicleClassDescModel> classDescModelListReturn=new ArrayList<>();
		String cov = vehicleDetailsDAO.getVehicleDtlsByPrNumber(prNumber).getVehicleSubClass();
		
		List<String> alterationCovMappingListA = new ArrayList<String>();
		List<String> alterationCovMappingListB = new ArrayList<String>();
		
		alterationCovMappingListA = alterationCovMappingDAO.getMappedAlterationCovListByCode(cov, true);
		alterationCovMappingListB = alterationCovMappingDAO.getMappedAlterationCovListByCode(cov, false);
		alterationCovMappingListA.addAll(alterationCovMappingListB);
		Set<String> setAlterationCovMapping = new HashSet<String>(alterationCovMappingListA);
		List<VehicleClassDescriptionEntity> vehicleClassEntityList = vehicleClassDescDAO
				.getByVehiclClassDesCodeByRegCatCode(setAlterationCovMapping, regCatCode);
		for (VehicleClassDescriptionEntity vehicleClassEntity : vehicleClassEntityList)
			if (vehicleClassEntity != null) {
				VehicleClassDescModel classDescModel = new VehicleClassDescModel();
				classDescModel.setVehicleCode(vehicleClassEntity.getCode());
				classDescModel.setVehicleDesc(vehicleClassEntity.getDescription());
				classDescModelList.add(classDescModel);
			}
		List<String> covList = new ArrayList<String>();
		for (VehicleClassDescModel classDescModel : classDescModelList) {
			if (!covList.contains(classDescModel.getVehicleCode())) {
				VehicleClassDescModel model = new VehicleClassDescModel();
				model.setVehicleCode(classDescModel.getVehicleCode());
				model.setVehicleDesc(classDescModel.getVehicleDesc());
				classDescModelListReturn.add(model);
				covList.add(classDescModel.getVehicleCode());
			}
		}
		return classDescModelListReturn;
	}
	
	@Transactional
	public Boolean isMatchedSavedAadhar(String aadharNumber, Long vehicleRcId) {
		VehicleRCEntity vehicleRc = vehicleDao.get(vehicleRcId);
		if(ObjectsUtil.isNotNull(vehicleRc) && ObjectsUtil.isNotNull(vehicleRc.getAadharNo()) && vehicleRc.getAadharNo().equalsIgnoreCase(aadharNumber)){
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public List<String> getBodyTypeList() {
		List<String> bodyTypeList = bodyTypeMasterDAO.getActiveBodyTypeList();
		return bodyTypeList;
	}
	
	@Override
	@Transactional
	public void updateRcApproverUserId(List<UserActionModel> userActionList, Long vehicleRcId){
		VehicleRCEntity rcEntity = vehicleDao.get(vehicleRcId);
		if(ObjectsUtil.isNotNull(userActionList) && userActionList.size() > 0 && ObjectsUtil.isNotNull(rcEntity)){
			for (UserActionModel actionModel : userActionList) {
				try{
					UserEntity userEntity = userDAO.findByUserId(Long.parseLong(actionModel.getUserId()));
					if(UserType.ROLE_CCO == actionModel.getUserType()) {
						rcEntity.setCcoUserId(userEntity);
						rcEntity.setCcoActionStatus(Status.APPROVED.getValue());
					} else if(UserType.ROLE_MVI == actionModel.getUserType()){
						rcEntity.setMviUserId(userEntity);
						rcEntity.setMviActionStatus(Status.APPROVED.getValue());
					} else if(UserType.ROLE_AO == actionModel.getUserType()){
						rcEntity.setAoUserId(userEntity);
						rcEntity.setAoActionStatus(Status.APPROVED.getValue());
					} else if(UserType.ROLE_RTO == actionModel.getUserType()){
						rcEntity.setRtoUserId(userEntity);
						rcEntity.setRtoActionStatus(Status.APPROVED.getValue());
					}
				} catch(Exception ex){
					log.error("Exception occured in updateRcApproverUserId for : " + actionModel);
				}
			}
		} else {
			log.info("No User Found in userActionList for vehicleRcId : " + vehicleRcId);
		}
	}
}
