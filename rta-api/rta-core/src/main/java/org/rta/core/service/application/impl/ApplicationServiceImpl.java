/**
 * 
 */
package org.rta.core.service.application.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.rta.core.dao.application.rejection.RejectionHistoryDAO;
import org.rta.core.dao.certificate.FcDetailsDAO;
import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.docs.AttachmentDetailsDAO;
import org.rta.core.dao.event.EventLogDAO;
import org.rta.core.dao.finance.FinanceTokenDAO;
import org.rta.core.dao.finance.FinancerAppStatusDAO;
import org.rta.core.dao.finance.FinancerFreshRcDAO;
import org.rta.core.dao.finance.VehicleFinanceDtlsDAO;
import org.rta.core.dao.insurance.InsuranceDetailsDAO;
import org.rta.core.dao.master.DocTypesDAO;
import org.rta.core.dao.master.RegistrationCategoryDAO;
import org.rta.core.dao.master.registration.PrSeriesMasterDAO;
import org.rta.core.dao.master.registration.SpecialNumberDAO;
import org.rta.core.dao.master.registration.SpecialNumberRtaOfficeDAO;
import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.dao.payment.gateway.TransactionDetailDAO;
import org.rta.core.dao.payment.invoice.DealerInvoiceDAO;
import org.rta.core.dao.payment.tax.LifeTaxDAO;
import org.rta.core.dao.payment.tax.TaxDetailDAO;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.dao.user.DealerDAO;
import org.rta.core.dao.user.RTAEmployeeDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.dao.vehicle.ApplicationSlotsDAO;
import org.rta.core.dao.vehicle.SuspendedRCNumberDAO;
import org.rta.core.dao.vehicle.VahanDAO;
import org.rta.core.dao.vehicle.VehicleAlterationDAO;
import org.rta.core.dao.vehicle.VehicleBillingDetailsDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.dao.vehicle.VehiclePRReleaseDAO;
import org.rta.core.dao.vehicle.VehicleRCChangeHistoryDAO;
import org.rta.core.dao.vehicle.VehicleRCHistoryDAO;
import org.rta.core.dao.vehicle.VehicleRCMigrationDAO;
import org.rta.core.dao.vehicle.cms.VehicleClassDescDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.application.VehicleRCHistoryEntity;
import org.rta.core.entity.application.rejection.RejectionHistoryEntity;
import org.rta.core.entity.certificate.FitnessCertificateEntity;
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.entity.event.EventLogEntity;
import org.rta.core.entity.finance.FinanceTokenEntity;
import org.rta.core.entity.finance.FinancerAppStatusEntity;
import org.rta.core.entity.finance.FinancerFreshRcEntity;
import org.rta.core.entity.finance.VehicleFinanceDtlstEntity;
import org.rta.core.entity.insurance.InsuranceDetailsEntity;
import org.rta.core.entity.master.CountryEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.master.registration.PrSeriesMasterEntity;
import org.rta.core.entity.master.registration.SpecialNumberRtaOfficeEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.entity.payment.tax.LifeTaxEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.user.RTAEmployeeEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.ApplicationSlotsEntity;
import org.rta.core.entity.vehicle.SuspendedRCNumbersEntity;
import org.rta.core.entity.vehicle.VahanEntity;
import org.rta.core.entity.vehicle.VehicleAlterationEntity;
import org.rta.core.entity.vehicle.VehicleBillingDetailsEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehiclePRReleaseEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.entity.vehicle.VehicleRCMigrationEntity;
import org.rta.core.enums.AlterationCategory;
import org.rta.core.enums.DocTypes;
import org.rta.core.enums.NotificationType;
import org.rta.core.enums.OfficeType;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.PRType;
import org.rta.core.enums.PaymentType;
import org.rta.core.enums.RegistrationCategoryType;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.enums.VehicleCategory;
import org.rta.core.enums.VehicleSubClassType;
import org.rta.core.enums.citizen.KeyType;
import org.rta.core.exception.ConflictException;
import org.rta.core.exception.InvalidPrNumberException;
import org.rta.core.exception.InvalidStatusException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.exception.PrNumberExistException;
import org.rta.core.exception.PrSeriesMismatchException;
import org.rta.core.exception.UserNotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.exceptioncode.InvalidPrNumberExceptionCode;
import org.rta.core.exceptioncode.PrNumberExistExceptionCode;
import org.rta.core.exceptioncode.PrSeriesMismatchExceptionCode;
import org.rta.core.helper.application.rejection.RejectionHistoryHelper;
import org.rta.core.helper.master.DocTypesHelper;
import org.rta.core.helper.office.RTAOfficeHelper;
import org.rta.core.helper.user.UserHelper;
import org.rta.core.model.OwnerConscent;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.VehicleReassignmentModel;
import org.rta.core.model.application.ApplicationModel;
import org.rta.core.model.application.ApplicationUserStatusModel;
import org.rta.core.model.application.CommentModel;
import org.rta.core.model.application.GeneralDetails;
import org.rta.core.model.application.SpecialNumberApplicationModel;
import org.rta.core.model.application.rejection.LogsModel;
import org.rta.core.model.application.rejection.RejectionHistoryModel;
import org.rta.core.model.certificate.FcDetailsModel;
import org.rta.core.model.citizen.CitizenApplicationModel;
import org.rta.core.model.citizen.SlotModel;
import org.rta.core.model.customer.CustMsgModel;
import org.rta.core.model.customer.CustomerDetailsRequestModel;
import org.rta.core.model.docs.ApplicantDocsModel;
import org.rta.core.model.docs.ApprovalDocModel;
import org.rta.core.model.docs.CommentHistoryModel;
import org.rta.core.model.export.DealerDetails;
import org.rta.core.model.export.SignatureDetails;
import org.rta.core.model.finance.FinanceModel;
import org.rta.core.model.finance.FreshRcAppStatusDetailsModel;
import org.rta.core.model.finance.FreshRcModel;
import org.rta.core.model.master.DocTypesModel;
import org.rta.core.model.master.RegistrationCategoryModel;
import org.rta.core.model.office.RTAOfficeModel;
import org.rta.core.model.secondVechicle.SecondVehicleDetailsModel;
import org.rta.core.model.user.UserModel;
import org.rta.core.model.vehicle.BillingDetailsModel;
import org.rta.core.model.vehicle.SuspendedRCNumberModel;
import org.rta.core.model.vehicle.UnregisteredVehicleModel;
import org.rta.core.model.vehicle.VehicleBaseModel;
import org.rta.core.model.vehicle.VehicleDetailsModel;
import org.rta.core.model.vehicle.VehicleDetailsRequestModel;
import org.rta.core.service.MessagingService;
import org.rta.core.service.application.ApplicationService;
import org.rta.core.service.certificate.FcDetailsService;
import org.rta.core.service.certificate.impl.FcDetailsServiceImpl;
import org.rta.core.service.citizen.CitizenService;
import org.rta.core.service.citizen.ResponseModel;
import org.rta.core.service.customer.CustomerService;
import org.rta.core.service.finance.FinanceApprovalService;
import org.rta.core.service.finance.FinanceDtlsService;
import org.rta.core.service.finance.FinanceFreshRcService;
import org.rta.core.service.insurance.InsuranceDetailsService;
import org.rta.core.service.master.RegistrationCategoryService;
import org.rta.core.service.payment.gateway.TransactionDetailService;
import org.rta.core.service.secondvehicle.SecondVehicleRejectionService;
import org.rta.core.service.vehicle.VehicleDetailsService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.NumberParser;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author arun.verma
 *
 */

@Service
public class ApplicationServiceImpl implements ApplicationService {

	private static final Logger log = Logger.getLogger(ApplicationServiceImpl.class);

	@Autowired
	private VehicleDAO vehicleDAO;

	@Autowired
	private VehicleRCMigrationDAO vehicleRCMigrationDAO;

	@Autowired
	private VehicleDetailsDAO vehicleDetailsDao;

	@Autowired
	private VehicleRCHistoryDAO vehicleRcHistoryDAO;

	@Autowired
	private UserDAO userDao;

	@Autowired
	private AddressDAO addressDao;

	@Autowired
	private RegistrationCategoryDAO registraionCategoryDAO;

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private VehicleBillingDetailsDAO vehicleBillingDetailsDAO;

	@Autowired
	private RTAEmployeeDAO rtaEmployeeDAO;

	@Autowired
	private InsuranceDetailsDAO insuranceDao;

	@Autowired
	private AttachmentDetailsDAO attachmentDetailsDAO;

	@Autowired
	private RejectionHistoryDAO rejectionHistoryDAO;

	@Autowired
	private RejectionHistoryHelper rejectionHistoryHelper;

	@Autowired
	private DealerInvoiceDAO dealerInvoidDao;

	@Autowired
	private UserHelper userHelper;

	@Autowired
	private DocTypesDAO docTypeDao;

	@Autowired
	private TransactionDetailService transactionDetailsService;

	@Autowired
	private PrSeriesMasterDAO prSeriesMasterDAO;

	@Autowired
	private EventLogDAO eventDAO;

	@Autowired
	private SecondVehicleRejectionService secondVehicleRejectionService;

	@Autowired
	private FinanceFreshRcService financeFreshRcService;

	@Autowired
	private FinancerFreshRcDAO financerFreshRcDAO;

	@Value("${rta.step.review}")
	private Integer reviewStep;

	@Autowired
	private DocTypesHelper docTypesHelper;

	@Autowired
	private CustomerCorporateDAO customerCorporateDao;

	@Autowired
	private TransactionDetailDAO transactionDetailDAO;

	@Autowired
	private SpecialNumberDAO specialNoDAO;

	@Autowired
	private TaxDetailDAO taxDetailDAO;

	@Autowired
	private DealerDAO dealerDAO;

	@Autowired
	private RegistrationCategoryDAO registrationCategoryDAO;

	@Autowired
	private RTAOfficeHelper rTAOfficeHelper;

	@Autowired
	private CitizenService citizenService;

	@Autowired
	private ApplicationSlotsDAO applicationSlotsDAO;

	@Autowired
	private VehicleAlterationDAO vehicleAlterationDAO;

	@Autowired
	private VehicleClassDescDAO vehicleClassDescDAO;

	@Autowired
	private RegistrationCategoryService registrationCategoryService;

	@Autowired
	private FcDetailsService fcDetailsService;

	@Autowired
	private VehicleRCChangeHistoryDAO vehicleRCChangeHistoryDAO;

	@Autowired
	private SpecialNumberRtaOfficeDAO specialNumberRtaOfficeDAO;
	
	@Autowired
	private VehiclePRReleaseDAO vehiclePRReleaseDAO;

	/*
	 * @Autowired private VehicleDetailsDAO vehicleDetailsDAO;
	 */

	@Value("${rta.step.aadhar}")
	private Integer adhaarStep;

	@Value("${rta.step.vehicle}")
	private Integer vehicleStep;

	@Value("${rta.step.insurance}")
	private Integer insuranceStep;

	@Value("${rta.step.finance}")
	private Integer financeStep;

	@Value("${rta.step.docs}")
	private Integer docsStep;

	@Value("${rta.step.taxpay}")
	private Integer taxStep;

	@Value("${pr.series.notification.threshold}")
	private Integer prSeriesNotificationThreshold;

	@Value("${pr.series.notification.interval}")
	private Integer prSeriesNotificationInterval;

	@Value("${rta.TAXPERCENTAAGE14}")
	private Integer taxPerc;

	@Value("${rta.pr.validty}")
	Integer prValidty;

	@Value("${rta.pr.validty.transport}")
	Integer prValidtyTrasnport;

	@Autowired
	private RtaOfficeDAO rtaOfficeDAO;

	@Autowired
	private SuspendedRCNumberDAO suspendedRcNumberDAO;

	@Autowired
	private FinanceApprovalService financeApprovalService;

	@Autowired
	private MessagingService messagingService;

	@Autowired
	private FinanceDtlsService financeDtlsService;

	@Autowired
	private InsuranceDetailsService insuranceDetailsService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private VehicleDetailsService vehicleDetailsService;

	@Autowired
	private VahanDAO vahanDAO;

	@Autowired
	private VehicleFinanceDtlsDAO vehicleFinanceDAO;

	@Value("${pr.series.subject}")
	private String prSubject;

	@Value("${pr.series.receiver}")
	private String prSeriesReceiver;

	@Value("${pr.series.cc}")
	private String prSeriesCC;

	@Value("${pr.series.mail.content}")
	private String prNotificationMailContent;

	@Autowired
	private VehicleFinanceDtlsDAO vehicleFinanceDetailsDAO;

	@Autowired
	private FinanceTokenDAO financeTokenDAO;

	@Autowired
	private FinancerAppStatusDAO financerAppStatusDAO;
    @Autowired
    private LifeTaxDAO lifeTaxDAO;

	private final static Integer CCO_MVI_Limit = 1;
	private final static Integer AO_RTO_Limit = 1;
	private static final Integer MAX_ALLOWED_SERIES_NUMBER = 9999;
	private static final String TRACTOR_FOR_COMMERCIAL_SUB_CLASS = "TRTT";

	@Override
	@Transactional
	public void migrateScript() {
		List<VehicleRCHistoryEntity> history = vehicleRcHistoryDAO.migrateScript();
		Map<Long, Map<UserEntity, Integer>> actionMap = new HashMap<>();
		for (VehicleRCHistoryEntity his : history) {
			if (actionMap.containsKey(his.getVehicleRc().getVehicleRcId())) {
				Map<UserEntity, Integer> map = actionMap.get(his.getVehicleRc().getVehicleRcId());
				Iterator it = map.entrySet().iterator();
				boolean isUpdate = true;
				while (it.hasNext()) {
					Map.Entry pair = (Map.Entry) it.next();
					UserEntity user = (UserEntity) pair.getKey();
					if (user.getUserType() == his.getUserEntity().getUserType()
							&& pair.getValue() == Status.APPROVED.getValue()
							&& his.getStatus() != Status.APPROVED.getValue()) {
						isUpdate = false;
						log.info("More Than one action for vehicleRcId : " + his.getVehicleRc().getVehicleRcId()
								+ " with Role : " + user.getUserType());
					}
				}
				if (isUpdate) {
					map.put(his.getUserEntity(), his.getStatus());
				}
			} else {
				Map<UserEntity, Integer> map = new HashMap<>();
				map.put(his.getUserEntity(), his.getStatus());
				actionMap.put(his.getVehicleRc().getVehicleRcId(), map);
			}
		}
		String reportPath = "/opt/RTA/reports";
		File dir = new File(reportPath);
		if (!dir.exists())
			dir.mkdirs();
		String FileNm = reportPath + File.separator + "migration_test.csv";
		String eol = System.getProperty("line.separator");
		Writer writer = null;
		try {
			writer = new FileWriter(FileNm);
			writer.append("VehicleRcId").append(',').append("Dealer Action").append(',').append("Dealer Id").append(',')
					.append("CCO Action").append(',').append("CCO Id").append(',').append("Mvi Action").append(',')
					.append("Mvi Id").append(',').append("Ao Action").append(',').append("Ao Id").append(',')
					.append("RTO Action").append(',').append("RTO Id").append(eol);

			Iterator it = actionMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				Long vehId = (Long) pair.getKey();
				Map<UserEntity, Integer> map = (Map<UserEntity, Integer>) pair.getValue();
				Iterator it1 = map.entrySet().iterator();
				String dealerAction = "";
				String dealerId = "";
				String ccoAction = "";
				String ccoId = "";
				String mviAction = "";
				String mviId = "";
				String aoAction = "";
				String aoId = "";
				String rtoAction = "";
				String rtoId = "";
				while (it1.hasNext()) {
					Map.Entry pair1 = (Map.Entry) it1.next();
					UserEntity user = (UserEntity) pair1.getKey();
					if (user.getUserType() == UserType.ROLE_DEALER) {
						dealerId = user.getUserId() + "";
						dealerAction = pair1.getValue() + "";
					} else if (user.getUserType() == UserType.ROLE_CCO) {
						ccoId = user.getUserId() + "";
						ccoAction = pair1.getValue() + "";
					} else if (user.getUserType() == UserType.ROLE_MVI) {
						mviId = user.getUserId() + "";
						mviAction = pair1.getValue() + "";
					} else if (user.getUserType() == UserType.ROLE_AO) {
						aoId = user.getUserId() + "";
						aoAction = pair1.getValue() + "";
					} else if (user.getUserType() == UserType.ROLE_RTO) {
						rtoId = user.getUserId() + "";
						rtoAction = pair1.getValue() + "";
					}
				}
				writer.append(vehId + "").append(',').append(dealerAction).append(',').append(dealerId).append(',')
						.append(ccoAction).append(',').append(ccoId).append(',').append(mviAction).append(',')
						.append(mviId).append(',').append(aoAction).append(',').append(aoId).append(',')
						.append(rtoAction).append(',').append(rtoId).append(eol);
			}
			writer.flush();
			writer.close();
		} catch (IOException ex) {
			log.error("IOException in migrateScript : " + ex.getMessage());
			log.debug("Exception" + ex);
		}
	}

	@Override
	@Transactional
	public Map<Long, Status> changeStatus(Long userId, String userName, UserType userRole, Long vehicleRcId,
			Status status, CommentModel comment, String prNumber) throws InvalidStatusException, NotFoundException {
		return changeStatus(userId, userName, userRole, vehicleRcId, status, comment, null, prNumber, false);
	}

	@Override
	@Transactional
	public List<ApplicationModel> getPendingApplication(Long userId, UserType userType, boolean limit,
			String regCategory) {

		// get VehicleRc List
		log.info("fetching form vehicleRcEntityList VehicleRcId : ");
		List<VehicleRCEntity> vehicleRcEntityList = vehicleDAO.getVehicleRcEntityPending(getRTAOfficeId(userId),
				userType, getRegistrationCategoryId(regCategory));
		List<Long> sendAppNoList = new ArrayList<Long>();
		vehicleRcEntityList.stream().forEach(ve -> {
			sendAppNoList.add(ve.getVehicleRcId());
		});
		if (ObjectsUtil.isNullOrEmpty(sendAppNoList)) {
			return null;
		}
		// get last action of user in history
		VehicleRCHistoryEntity vehicleRcHistory = vehicleRcHistoryDAO.getLastActionApp(userId);
		Long lastOpenedRcId = null;

		if (vehicleRcHistory != null && vehicleRcHistory.getStatus() == Status.OPEN.getValue()) {
			lastOpenedRcId = vehicleRcHistory.getVehicleRc().getVehicleRcId();
		}
		// get actioned Application of same user type and group it
		List<Long> actionedAppList = vehicleRcHistoryDAO.getActionedApp(userType, sendAppNoList);
		List<Long> groupedActionedList = new ArrayList<>();
		if (!ObjectsUtil.isNull(actionedAppList)) {
			for (Long no : actionedAppList) {
				if (lastOpenedRcId != null && no.compareTo(lastOpenedRcId) == 0) {
					continue;
				}

				if (!groupedActionedList.contains(no)) {
					groupedActionedList.add(no);
				}

			}
		}
		ApplicationModel lastApp = null;
		List<ApplicationModel> appModelList = new ArrayList<>();
		if (userType == UserType.ROLE_CCO || userType == UserType.ROLE_MVI) {
			for (VehicleRCEntity vRcE : vehicleRcEntityList) {
				if (groupedActionedList.contains(vRcE.getVehicleRcId())) {
					continue;
				}
				ApplicationModel app = new ApplicationModel();
				app.setVehicleRcId(vRcE.getVehicleRcId());
				app.setIteration(vRcE.getIteration());
				if (lastOpenedRcId != null && (vRcE.getVehicleRcId().compareTo(lastOpenedRcId) == 0)) {
					app.setStatus(Status.OPEN);
					lastApp = app;
				} else {
					appModelList.add(app);
				}
			}
		} else {
			List<VehicleRCHistoryEntity> vehicleRcHistoryList = vehicleRcHistoryDAO.getAppFromHistoryForPending(userId,
					userType, sendAppNoList);
			sendAppNoList.clear();
			// group by vehicle Rc Id on the basis of last modified time
			List<Long> ccoClosedRCList = new ArrayList<>();
			List<Long> mviClosedRCList = new ArrayList<>();
			List<Long> aoClosedRCList = new ArrayList<>();
			for (VehicleRCHistoryEntity vh : vehicleRcHistoryList) {
				if (vh.getRtaEmployeeType() == UserType.ROLE_CCO.getValue()
						&& !ccoClosedRCList.contains(vh.getVehicleRc().getVehicleRcId())
						&& vh.getStatus() != Status.OPEN.getValue()) {
					ccoClosedRCList.add(vh.getVehicleRc().getVehicleRcId());
				} else if (vh.getRtaEmployeeType() == UserType.ROLE_MVI.getValue()
						&& !mviClosedRCList.contains(vh.getVehicleRc().getVehicleRcId())
						&& vh.getStatus() != Status.OPEN.getValue()) {
					mviClosedRCList.add(vh.getVehicleRc().getVehicleRcId());
				} else if (vh.getRtaEmployeeType() == UserType.ROLE_AO.getValue()
						&& !aoClosedRCList.contains(vh.getVehicleRc().getVehicleRcId())
						&& vh.getStatus() != Status.OPEN.getValue()) {
					aoClosedRCList.add(vh.getVehicleRc().getVehicleRcId());
				}
			}
			for (VehicleRCHistoryEntity vh : vehicleRcHistoryList) {
				if ((userType == UserType.ROLE_AO && ccoClosedRCList.contains(vh.getVehicleRc().getVehicleRcId())
						&& mviClosedRCList.contains(vh.getVehicleRc().getVehicleRcId()))
						|| (userType == UserType.ROLE_RTO
								&& aoClosedRCList.contains(vh.getVehicleRc().getVehicleRcId()))) {
					Long vrcid = vh.getVehicleRc().getVehicleRcId();
					log.info(vrcid);
					sendAppNoList.add(vrcid);
				}
			}

			for (VehicleRCEntity vRcE : vehicleRcEntityList) {
				if (sendAppNoList.contains(vRcE.getVehicleRcId())) {
					if (groupedActionedList.contains(vRcE.getVehicleRcId())) {
						continue;
					}
					ApplicationModel app = new ApplicationModel();
					// *** set applicationdetails***
					app.setVehicleRcId(vRcE.getVehicleRcId());
					app.setIteration(vRcE.getIteration());
					/*
					 * if (userType == UserType.ROLE_AO || userType ==
					 * UserType.ROLE_RTO) {
					 * app.setPrType(PRType.getPRType(vRcE.getPrType())); }
					 */
					if (lastOpenedRcId != null && (vRcE.getVehicleRcId().compareTo(lastOpenedRcId) == 0)) {
						app.setStatus(Status.OPEN);
						lastApp = app;
					} else {
						appModelList.add(app);
					}
				}
			}
		}
		if (lastApp != null) {
			appModelList.add(0, lastApp);
		}

		// limit the applications for specific type of user
		if (limit) {
			appModelList = limitApplicationsForUser(appModelList, userType);
			/*
			 * if (userType == UserType.ROLE_AO || userType ==
			 * UserType.ROLE_RTO) { appModelList.stream().forEach(app->{ //
			 * check only for applications with PRType = SPECIAL if
			 * (app.getPrType() == PRType.SPECIAL) {
			 * app.setCanBeApproved(matchPRGenerationRule(app.getVehicleRcId(),
			 * userType)); } }); }
			 */
		}
		return appModelList;
	}

	@Override
	@Transactional
	public List<ApplicationModel> getPendingApplication(Long userId, UserType userType, boolean limit,
			String regCategory, VehicleCategory vehicleCategory) {
		// get VehicleRc List
		log.info("fetching form vehicleRcEntityList VehicleRcId : ");
		List<VehicleRCEntity> vehicleRcEntityList = vehicleDAO.getVehicleRcEntityPending(getRTAOfficeId(userId),
				userType, getRegistrationCategoryId(regCategory), DateUtil.toCurrentUTCTimeStamp());
		List<Long> sendAppNoList = new ArrayList<Long>();
		vehicleRcEntityList.stream().forEach(ve -> {
			log.info(ve.getVehicleRcId() + " ");
			sendAppNoList.add(ve.getVehicleRcId());
		});
		if (ObjectsUtil.isNullOrEmpty(sendAppNoList)) {
			return null;
		}
		// get last action of user in history
		VehicleRCHistoryEntity vehicleRcHistory = vehicleRcHistoryDAO.getLastActionApp(userId);
		log.info("getLastActionApp for userId : " + userId + " => " + vehicleRcHistory);
		Long lastOpenedRcId = null;

		if (vehicleRcHistory != null && vehicleRcHistory.getStatus() == Status.OPEN.getValue()) {
			lastOpenedRcId = vehicleRcHistory.getVehicleRc().getVehicleRcId();
		}
		// get actioned Application of same user type and group it
		List<Long> actionedAppList = vehicleRcHistoryDAO.getActionedApp(userType, sendAppNoList);
		List<Long> groupedActionedList = new ArrayList<>();
		log.info("getOpenedAppByOther : ");
		if (!ObjectsUtil.isNull(actionedAppList)) {
			for (Long no : actionedAppList) {
				if (lastOpenedRcId != null && no.compareTo(lastOpenedRcId) == 0) {
					continue;
				}

				if (!groupedActionedList.contains(no)) {
					log.info(no + " ");
					groupedActionedList.add(no);
				}

			}
		}
		ApplicationModel lastApp = null;
		List<ApplicationModel> appModelList = new ArrayList<>();
		if (userType == UserType.ROLE_CCO || userType == UserType.ROLE_MVI) {
			for (VehicleRCEntity vRcE : vehicleRcEntityList) {
				if (groupedActionedList.contains(vRcE.getVehicleRcId())) {
					continue;
				}
				ApplicationModel app = new ApplicationModel();
				app.setVehicleRcId(vRcE.getVehicleRcId());
				app.setIteration(vRcE.getIteration());
				List<ApplicationSlotsEntity> appSlotsEntityList = applicationSlotsDAO
						.getApplicationsByVehicleRcId(vRcE.getVehicleRcId());
				if (!ObjectsUtil.isNull(appSlotsEntityList)) {
					List<SlotModel> slots = new ArrayList<>();
					appSlotsEntityList.stream().forEach(appSlots -> {
						SlotModel slot = new SlotModel();
						slot.setApplicationNumber(appSlots.getApplicationNumber());
						slot.setEndTime(appSlots.getEndTime());
						slot.setScheduledDate(appSlots.getScheduledDate());
						slot.setScheduledTime(appSlots.getScheduledTime());
						slot.setSlotId(appSlots.getSlotId());
						slot.setSlotStatus(appSlots.getSlotStatus());
						slot.setStartTime(appSlots.getStartTime());
						slot.setType(appSlots.getSlotServiceType());
						slots.add(slot);
					});
					app.setSlots(slots);
				}
				if (lastOpenedRcId != null && (vRcE.getVehicleRcId().compareTo(lastOpenedRcId) == 0)) {
					app.setStatus(Status.OPEN);
					lastApp = app;
					log.info("VehicleRcId : " + lastOpenedRcId + ", status : Open");
				} else {
					appModelList.add(app);
					log.info("VehicleRcId : " + lastOpenedRcId + ", status : Pending");
				}
			}
		} else {
			List<VehicleRCHistoryEntity> vehicleRcHistoryList = vehicleRcHistoryDAO.getAppFromHistoryForPending(userId,
					userType, sendAppNoList);
			sendAppNoList.clear();
			// group by vehicle Rc Id on the basis of last modified time
			log.info("getAppFromHistoryForPending => ");
			List<Long> ccoClosedRCList = new ArrayList<>();
			List<Long> mviClosedRCList = new ArrayList<>();
			List<Long> aoClosedRCList = new ArrayList<>();
			for (VehicleRCHistoryEntity vh : vehicleRcHistoryList) {
				if (vh.getRtaEmployeeType() == UserType.ROLE_CCO.getValue()
						&& !ccoClosedRCList.contains(vh.getVehicleRc().getVehicleRcId())
						&& vh.getStatus() != Status.OPEN.getValue()) {
					ccoClosedRCList.add(vh.getVehicleRc().getVehicleRcId());
				} else if (vh.getRtaEmployeeType() == UserType.ROLE_MVI.getValue()
						&& !mviClosedRCList.contains(vh.getVehicleRc().getVehicleRcId())
						&& vh.getStatus() != Status.OPEN.getValue()) {
					mviClosedRCList.add(vh.getVehicleRc().getVehicleRcId());
				} else if (vh.getRtaEmployeeType() == UserType.ROLE_AO.getValue()
						&& !aoClosedRCList.contains(vh.getVehicleRc().getVehicleRcId())
						&& vh.getStatus() != Status.OPEN.getValue()) {
					aoClosedRCList.add(vh.getVehicleRc().getVehicleRcId());
				}
			}
			for (VehicleRCHistoryEntity vh : vehicleRcHistoryList) {
				if ((userType == UserType.ROLE_AO && ccoClosedRCList.contains(vh.getVehicleRc().getVehicleRcId())
						&& mviClosedRCList.contains(vh.getVehicleRc().getVehicleRcId()))
						|| (userType == UserType.ROLE_RTO
								&& aoClosedRCList.contains(vh.getVehicleRc().getVehicleRcId()))) {
					Long vrcid = vh.getVehicleRc().getVehicleRcId();
					log.info(vrcid);
					sendAppNoList.add(vrcid);
				}
			}

			for (VehicleRCEntity vRcE : vehicleRcEntityList) {
				if (sendAppNoList.contains(vRcE.getVehicleRcId())) {
					if (groupedActionedList.contains(vRcE.getVehicleRcId())) {
						continue;
					}
					ApplicationModel app = new ApplicationModel();
					// *** set applicationdetails***
					app.setVehicleRcId(vRcE.getVehicleRcId());
					app.setIteration(vRcE.getIteration());
					List<ApplicationSlotsEntity> appSlotsEntityList = applicationSlotsDAO
							.getApplicationsByVehicleRcId(vRcE.getVehicleRcId());
					if (!ObjectsUtil.isNull(appSlotsEntityList)) {
						List<SlotModel> slots = new ArrayList<>();
						appSlotsEntityList.stream().forEach(appSlots -> {
							SlotModel slot = new SlotModel();
							slot.setApplicationNumber(appSlots.getApplicationNumber());
							slot.setEndTime(appSlots.getEndTime());
							slot.setScheduledDate(appSlots.getScheduledDate());
							slot.setScheduledTime(appSlots.getScheduledTime());
							slot.setSlotId(appSlots.getSlotId());
							slot.setSlotStatus(appSlots.getSlotStatus());
							slot.setStartTime(appSlots.getStartTime());
							slot.setType(appSlots.getSlotServiceType());
							slots.add(slot);
						});
						app.setSlots(slots);
					}
					/*
					 * if (userType == UserType.ROLE_AO || userType ==
					 * UserType.ROLE_RTO) {
					 * app.setPrType(PRType.getPRType(vRcE.getPrType())); }
					 */
					if (lastOpenedRcId != null && (vRcE.getVehicleRcId().compareTo(lastOpenedRcId) == 0)) {
						app.setStatus(Status.OPEN);
						lastApp = app;
						log.info("VehicleRcId : " + lastOpenedRcId + ", status : Open");
					} else {
						appModelList.add(app);
						log.info("VehicleRcId : " + lastOpenedRcId + ", status : Pending");
					}
				}
			}
		}
		if (lastApp != null) {
			appModelList.add(0, lastApp);
		}

		// limit the applications for specific type of user
		if (limit) {
			appModelList = limitApplicationsForUser(appModelList, userType);
			/*
			 * if (userType == UserType.ROLE_AO || userType ==
			 * UserType.ROLE_RTO) { appModelList.stream().forEach(app->{ //
			 * check only for applications with PRType = SPECIAL if
			 * (app.getPrType() == PRType.SPECIAL) {
			 * app.setCanBeApproved(matchPRGenerationRule(app.getVehicleRcId(),
			 * userType)); } }); }
			 */
		}
		return appModelList;
	}

	@Override
	@Transactional
	public ApplicationModel getOpenORPendingApplications(Long userId, UserType userRole, String regType,
			Boolean openAction, VehicleCategory vc) {
		ApplicationModel app = new ApplicationModel();
		VehicleRCEntity vehicleRc = vehicleDAO.getOpenApplicationByUser(userId, userRole);
		if (!ObjectsUtil.isNull(vehicleRc) && (openAction || StringsUtil.isNullOrEmpty(regType)
				|| vehicleRc.getRegCategory().getCode().equalsIgnoreCase(regType))) {
			app.setVehicleRcId(vehicleRc.getVehicleRcId());
			app.setIteration(vehicleRc.getIteration());
			app.setStatus(Status.OPEN);
			VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicleRc.getVehicleRcId());
			if (ObjectsUtil.isNotNull(vde)) {
				app.setVehicleCategory(vde.getVehicleCategory());
			}
			return app;
		}

		log.debug("Fetching pending applications from VehicleRcEntity");
		VehicleRCEntity vehicle = vehicleDAO.getPendingApplication(getRTAOfficeId(userId), userRole,
				getRegistrationCategoryId(regType), vc);
		if (null == vehicle) {
			return null;
		}
		app.setVehicleRcId(vehicle.getVehicleRcId());
		app.setIteration(vehicle.getIteration());
		VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicle.getVehicleRcId());
		if (ObjectsUtil.isNotNull(vde)) {
			app.setVehicleCategory(vde.getVehicleCategory());
		}
		return app;
	}

	private boolean matchPRGenerationRule(Long vehicleRcId, UserType userRole) {
		VehicleRCEntity vehicleRcEntity = vehicleDAO.get(vehicleRcId);
		VehicleRCHistoryEntity mviAction = vehicleRcHistoryDAO.getLastAction(UserType.ROLE_MVI, vehicleRcId,
				vehicleRcEntity.getIteration());
		// AO can approve Application if approved with CCO and MVI
		return (UserType.ROLE_RTO == userRole)
				|| (UserType.ROLE_AO == userRole && (Status.APPROVED == Status.getStatus(mviAction.getStatus())));
	}

	private List<ApplicationModel> limitApplicationsForUser(List<ApplicationModel> applications, UserType userType) {
		if ((userType == UserType.ROLE_CCO || userType == UserType.ROLE_MVI) && applications.size() > CCO_MVI_Limit) {
			applications = applications.subList(0, CCO_MVI_Limit);
		} else if ((userType == UserType.ROLE_AO || userType == UserType.ROLE_RTO)
				&& applications.size() > AO_RTO_Limit) {
			applications = applications.subList(0, AO_RTO_Limit);
		}
		return applications;
	}

	private boolean isValidPR(String pr, VehicleRCEntity vehicleRCEntity)
			throws InvalidPrNumberException, PrNumberExistException, PrSeriesMismatchException {
		RtaOfficeEntity rtaOffice = vehicleRCEntity.getRtaOfficeId();
		if (pr.length() != 10) {
			log.error("Invalid PR. Length is less/more than 10 digits." + pr);
			throw new InvalidPrNumberException(InvalidPrNumberExceptionCode.INVALID_PR_NUMBER_EXCEPTION,
					"Invalid PR number. Wrong number of digits.");
		}
		List<Integer> specialNoList = specialNoDAO.getAllSpecialNo();
		int sequence = Integer.parseInt(StringsUtil.getLastChars(pr, 4));
		if (specialNoList != null) {
			if (!specialNoList.contains(sequence)) {
				log.error("validating prNumber - not special number : " + pr);
				throw new InvalidPrNumberException(InvalidPrNumberExceptionCode.INVALID_PR_NUMBER_EXCEPTION,
						"Pr not special number");
			}
		}
        SpecialNumberRtaOfficeEntity specialnum = specialNumberRtaOfficeDAO.getUniqueSpecialNumber(pr);
        if (specialnum != null && !vehicleRCEntity.getTrNumber().equalsIgnoreCase(specialnum.getTrNumber())) {
            throw new InvalidPrNumberException(InvalidPrNumberExceptionCode.INVALID_PR_NUMBER_EXCEPTION,
                    " Special number " + specialnum.getSpecialNumber() + " has already been locked for another TR:"
                            + specialnum.getTrNumber());
        }
		if (!StringsUtil.getStartingChars(pr, 4).equals(rtaOffice.getSeriesMapid())) {
			log.error("This pr series doesn't belongs to your RTA office.");
			throw new PrSeriesMismatchException(PrSeriesMismatchExceptionCode.PR_SERIES_MISMATCH,
					"This pr series doesn't belongs to your RTA office.");
		}

		List<String> prseries = prSeriesMasterDAO.getAllSeriesByRTAOffice(vehicleRCEntity);
		if (!prseries.contains(StringsUtil.getStartingChars(pr, 6))) {
			log.error("This pr series doesn't belongs to your RTA office.");
			throw new PrSeriesMismatchException(PrSeriesMismatchExceptionCode.PR_SERIES_MISMATCH,
					"This pr series doesn't belongs to your RTA office.");
		}

		VehicleRCEntity vrc = vehicleDAO.getVehicleRc4Pr(pr);
		if (ObjectsUtil.isNotNull(vrc)) {
			log.error("validating prNumber - pr already exists : " + pr);
			throw new PrNumberExistException(PrNumberExistExceptionCode.PRNUMBER_ALREADY_EXIST,
					"RC already exists, please select another no");

		}
		return true;
	}

	private Map<Long, Status> changeApplicationStatus(Long userId, String userName, UserType userRole, Long vehicleRcId,
			Status status, CommentModel comment, String ip, String prNumber, Boolean skipSpecial)
			throws InvalidStatusException, NotFoundException, PrNumberExistException, InvalidPrNumberException,
			PrSeriesMismatchException {
		Map<Long, Status> map = new HashMap<>();
		UserEntity userEntity = userDao.getEntity(UserEntity.class, userId);
		VehicleRCEntity vehicleRcEntity = vehicleDAO.get(vehicleRcId);
		PRType prType = PRType.getPRType(vehicleRcEntity.getPrType());
		String pr;
		/*
		 * VehicleRCHistoryEntity mviAction =
		 * vehicleRcHistoryDAO.getLastAction(UserType.ROLE_MVI, vehicleRcId,
		 * vehicleRcEntity.getIteration());
		 */
		if (!skipSpecial && (Status.APPROVED == Status.getStatus(vehicleRcEntity.getMviActionStatus())
				&& (UserType.ROLE_AO == userRole) || UserType.ROLE_RTO == userRole)) {
			if (status == Status.APPROVED && prType == PRType.SPECIAL) {
				try {
					if (StringsUtil.isNullOrEmpty(prNumber) || !isValidPR(prNumber, vehicleRcEntity)) {
						log.info("Status is APPROVED but PR number not Provided/Invalid.");
						throw new PrNumberExistException(PrNumberExistExceptionCode.PRNUMBER_ALREADY_EXIST,
								"RC already exists, please select another no");
					}
				} catch (PrNumberExistException e) {
					log.info("Status is APPROVED but PR number not Provided/Invalid.");
					throw new PrNumberExistException(PrNumberExistExceptionCode.PRNUMBER_ALREADY_EXIST,
							"RC already exists, please select another no");

				} catch (PrSeriesMismatchException ex) {
					log.info("This pr series doesn't belong to your RTA office.");
					throw new PrSeriesMismatchException(PrSeriesMismatchExceptionCode.PR_SERIES_MISMATCH,
							"This pr series doesn't belongs to your RTA office.");
				}

			}
			if (status == Status.PR_PENDING && prType != PRType.SPECIAL) {
				log.info("Status is PR_PENDING but PR number is not special.");
				throw new InvalidPrNumberException(InvalidPrNumberExceptionCode.INVALID_PR_NUMBER_EXCEPTION,
						"either pr is not present or not valid!");
				// throw new IllegalArgumentException("either pr is not present
				// or not valid!");
			}
		}

		isValidStatus(userId, userRole, vehicleRcId, status, vehicleRcEntity.getIteration());
		boolean isInValid = true;
		ApplicationModel newApp = null;
		if (prType == PRType.SPECIAL && vehicleRcEntity.getProcessStatus() == Status.PR_PENDING.getValue()) {
			isInValid = false;
		} else {
			newApp = getOpenORPendingApplications(userId, userRole, null, false, null);
			if (newApp != null && newApp.getVehicleRcId() != null
					&& vehicleRcId.compareTo(newApp.getVehicleRcId()) == 0) {
				if (newApp.getStatus() != null && newApp.getStatus() == Status.OPEN) {
					isInValid = false;
				}
			}
		}

		if (isInValid) {
			log.error("Invalid action on change status. VehicleRcId: " + vehicleRcId);
			throw new IllegalArgumentException("Invalid Action.");
		}
		VehicleRCHistoryEntity historyEntity = new VehicleRCHistoryEntity();
		historyEntity.setUserEntity(userEntity);
		historyEntity.setRtaEmployeeType(userRole.getValue());
		historyEntity.setStatus(status.getValue());
		historyEntity.setVehicleRc(vehicleRcEntity);
		historyEntity.setCreatedBy(userName);
		historyEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		historyEntity.setModifiedBy(userName);
		historyEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		historyEntity.setIteration(vehicleRcEntity.getIteration());
		historyEntity.setIpAddress(ip);
		if (!ObjectsUtil.isNull(comment) && !StringsUtil.isNullOrEmpty(comment.getComment())) {
			historyEntity.setComment(comment.getComment());
		}
		Long id = (Long) vehicleRcHistoryDAO.save(historyEntity);

		boolean isApplicationApproved = false;
		boolean isFinalAction = false;
		boolean isGenerateFitnessCert = true;
		log.info("Processing changeApplicationStatus with vehicleRcId : " + vehicleRcId);
		log.info("MVI Action : " + Status.getStatus(vehicleRcEntity.getMviActionStatus()) + ".  Is MVI accepted : "
				+ (Status.APPROVED == Status.getStatus(vehicleRcEntity.getMviActionStatus())));
		log.info("AO Action : " + status + ".  IS AO Accepted : " + (UserType.ROLE_AO == userRole));
		log.info("is approved called : " + (status == Status.APPROVED));
		// AO can approve Application if approved with CCO and MVI
		if (UserType.ROLE_AO == userRole) {
			if ((Status.APPROVED == Status.getStatus(vehicleRcEntity.getMviActionStatus()))
					&& status == Status.APPROVED) {
				isFinalAction = true;
				vehicleRcEntity.setPrStatus(Status.APPROVED.getValue());
				vehicleRcEntity.setProcessStatus(Status.APP_COMPLETED.getValue());
				vehicleRcEntity.setPrIssueTime(DateUtil.toCurrentUTCTimeStamp());
				switch (RegistrationCategoryType
						.getRegistrationCategoryType(vehicleRcEntity.getRegCategory().getRegistrationCategoryId())) {
				case NON_TRANSPORT:
					vehicleRcEntity.setPrExpireDate(DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), prValidty));
					break;
				case TRANSPORT:
					// --- for transport vehicle pr expiry date ad fitness
					// expiry date should be same
					VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicleRcEntity.getVehicleRcId());
					if (TRACTOR_FOR_COMMERCIAL_SUB_CLASS.equalsIgnoreCase(vde.getVehicleSubClass())) {
						isGenerateFitnessCert = false;
						vehicleRcEntity.setPrExpireDate(DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), prValidty));
					} else if (vde.getVehicleSubClass().equalsIgnoreCase("EIBT")) {
						vehicleRcEntity.setPrExpireDate(FcDetailsServiceImpl
								.getTwoYearFitnessExpiryDate(vde.getVehicleSubClass(), DateUtil.toCurrentUTCDate())
								.getTime() / 1000);
					} else {
						vehicleRcEntity.setPrExpireDate(
								DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), prValidtyTrasnport));
					}
					break;
				}

				isApplicationApproved = true;
			} else if (status == Status.PR_PENDING) {
				log.info("application " + vehicleRcId + " moved to PR_PENDING !");
				vehicleRcEntity.setProcessStatus(Status.PR_PENDING.getValue());
			}
			vehicleRcEntity.setAoActionStatus(status.getValue());
			vehicleRcEntity.setAoUserId(userEntity);
			log.info("1. PR generated for vehiclercid : " + vehicleRcId);
		} else if (UserType.ROLE_RTO == userRole) {
			/*
			 * If RTO then he can APPROVE or REJECT the application
			 */
			vehicleRcEntity.setRtoActionStatus(status.getValue());
			vehicleRcEntity.setRtoUserId(userEntity);
			isFinalAction = true;
			if (status == Status.APPROVED) {
				vehicleRcEntity.setPrStatus(Status.APPROVED.getValue());
				vehicleRcEntity.setProcessStatus(Status.APP_COMPLETED.getValue());
				vehicleRcEntity.setPrIssueTime(DateUtil.toCurrentUTCTimeStamp());
				switch (RegistrationCategoryType
						.getRegistrationCategoryType(vehicleRcEntity.getRegCategory().getRegistrationCategoryId())) {
				case NON_TRANSPORT:
					vehicleRcEntity.setPrExpireDate(DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), prValidty));
					break;
				case TRANSPORT:
					// --- for transport vehicle pr expiry date ad fitness
					// expiry date should be same
					VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicleRcEntity.getVehicleRcId());
					if (TRACTOR_FOR_COMMERCIAL_SUB_CLASS.equalsIgnoreCase(vde.getVehicleSubClass())) {
						isGenerateFitnessCert = false;
						vehicleRcEntity.setPrExpireDate(DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), prValidty));
					} else if (vde.getVehicleSubClass().equalsIgnoreCase("EIBT")) {
						vehicleRcEntity.setPrExpireDate(FcDetailsServiceImpl
								.getTwoYearFitnessExpiryDate(vde.getVehicleSubClass(), DateUtil.toCurrentUTCDate())
								.getTime() / 1000);
					} else {
						vehicleRcEntity.setPrExpireDate(
								DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), prValidtyTrasnport));
					}
					break;
				}
				isApplicationApproved = true;
				log.info("2. PR generated for vehiclercid " + vehicleRcId);
			} else if (status == Status.REJECTED) {
				vehicleRcEntity.setPrStatus(Status.REJECTED.getValue());
				log.info("2.5. PR rejected for vehiclercid " + vehicleRcId);
			} else if (status == Status.PR_PENDING) {
				log.info("application " + vehicleRcId + " moved to PR_PENDING !");
				vehicleRcEntity.setProcessStatus(Status.PR_PENDING.getValue());
			}
		}

		if (isApplicationApproved) {
			if (!StringsUtil.isNullOrEmpty(prNumber)
					&& PRType.getPRType(vehicleRcEntity.getPrType()) == PRType.SPECIAL) {
				pr = prNumber;
				// Lock Special Number
				try {
					SpecialNumberRtaOfficeEntity specialNumber = specialNumberRtaOfficeDAO
							.getUniqueSpecialNumber(prNumber);
					if (specialNumber == null) {
						log.info("Locking Special Number:" + prNumber);
						SpecialNumberRtaOfficeEntity specialNumberRtaOfficeEntity = new SpecialNumberRtaOfficeEntity();
						specialNumberRtaOfficeEntity.setTrNumber(vehicleRcEntity.getTrNumber());
						specialNumberRtaOfficeEntity.setSpecialNumber(prNumber);
						specialNumberRtaOfficeEntity.setStatus(Status.LOCKED.getValue());
						specialNumberRtaOfficeEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
						specialNumberRtaOfficeEntity.setCreatedBy(userEntity.getUserName());
						specialNumberRtaOfficeDAO.save(specialNumberRtaOfficeEntity);
						log.info("Special Number " + prNumber + " locked successfully");
					} else {
						log.info("Special Number:" + prNumber + " already Locked.");
					}
				} catch (Exception e) {
					log.error("Not able to Lock Special Number:" + prNumber);
				}
			} else {
				pr = getPrSeriesNo(vehicleRcEntity, null);
			}
			if (skipSpecial)
				vehicleRcEntity.setPrType(PRType.ORDINARY.getId());
			vehicleRcEntity.setPrNumber(pr);
			log.info("3. PR generated for vehiclercid" + vehicleRcId);
		} else {
			log.info("4. PR rejected for vehiclercid " + vehicleRcId);
		}
		map.put(id, status);
		log.info(map.toString());
		if (isFinalAction) {
			VehicleFinanceDtlstEntity vehicelFinDtls = vehicleFinanceDAO.getVehicleFinanceRcr(vehicleRcId, null);
			if (ObjectsUtil.isNotNull(vehicelFinDtls)) {
				try {
					if (status == Status.REJECTED) {
						financeApprovalService.actionHPAByRTA(userName, status, vehicleRcId);
					} else {
						// --- status can be approved/pr_pending
						financeApprovalService.actionHPAByRTA(userName, Status.APPROVED, vehicleRcId);
					}
				} catch (Exception ex) {
					log.error("Error While Updating Finance as RTA Final Action......");
				}
			}

			try {
				log.info(":Fitness start::::::: ");
				RegistrationCategoryModel registrationCategoryModel = registrationCategoryService
						.getRegistrationCategoryDetails(vehicleRcId);
				if (!ObjectsUtil.isNull(registrationCategoryModel) && isGenerateFitnessCert) {
					if (RegistrationCategoryType.TRANSPORT.getValue() == registrationCategoryModel
							.getRegistrationCategoryId() && status == Status.APPROVED) {
						log.info("Generating FC for vehicleRcId : " + vehicleRcId);
						String fcMsg = fcDetailsService.saveOrUpdate(vehicleRcId, userName);
						log.info("fc has been generated for VehicleRcId : " + vehicleRcId + " is " + fcMsg);
					}
				} else {
					log.error("RegistrationCategoryModel is null");
				}
			} catch (Exception e) {
				log.error("getting error in generate FC : " + vehicleRcId);
			}
		}
		log.debug(map.toString());
		return map;
	}

	private void isValidStatus(Long userId, UserType userRole, Long vehicleRcId, Status newStatus, Integer iteration)
			throws InvalidStatusException {
		VehicleRCHistoryEntity entity = vehicleRcHistoryDAO.getLastAction(userId, userRole, vehicleRcId, iteration);
		if (ObjectsUtil.isNull(entity)) {
			log.error("invalid status found : " + vehicleRcId);
			throw new InvalidStatusException("invalid status found");
		}
		Status existingStatus = Status.getStatus(entity.getStatus());
		if (existingStatus == newStatus && existingStatus != Status.OPEN) {
			log.error("invalid status found : " + vehicleRcId);
			throw new InvalidStatusException("invalid status found");
		}
	}

	@Override
	@Transactional
	public Map<Long, Status> changeStatus(Long userId, String userName, UserType userRole, Long vehicleRcId,
			Status status, CommentModel comment, String ip, String prNumber, Boolean skipSpecial)
			throws InvalidStatusException, NotFoundException {
		Map<Long, Status> map = null;
		if (status == Status.CANCELLED && userRole == UserType.ROLE_DEALER) {
			map = new HashMap<>();
			cancelApplication(userId, vehicleRcId);
			map.put(vehicleRcId, Status.CANCELLED);
			return map;
		} else if ((status == Status.APPROVED || status == Status.PR_PENDING || status == Status.REJECTED)
				&& (userRole == UserType.ROLE_AO || userRole == UserType.ROLE_RTO)) {
			map = changeApplicationStatus(userId, userName, userRole, vehicleRcId, status, comment, ip, prNumber,
					skipSpecial);
			return map;
		}
		throw new InvalidStatusException();
	}

	@Override
	public List<ApplicationModel> getApplication(Long userId, UserType userRole, Status status,
			Integer numberOfApplications) {
		// vehicleDAO.getEntity(VehicleRCEntity.class, null);
		return null;
	}

	@Override
	@Transactional
	public Long countApplications(Long userId, Status status, String regCategory) {
		return vehicleDAO.countApplications(getRTAOfficeId(userId), status, getRegistrationCategoryId(regCategory));
	}

	@Override
	@Transactional
	public Integer countPendingApplications(Long userId, UserType userRole, String regType) {
		Integer count = 0;
		VehicleRCEntity vehicleRc = vehicleDAO.getOpenApplicationByUser(userId, userRole);
		if (!ObjectsUtil.isNull(vehicleRc) && (StringsUtil.isNullOrEmpty(regType)
				|| vehicleRc.getRegCategory().getCode().equalsIgnoreCase(regType)))
			count++;
		count = count + vehicleDAO
				.getPendingApplicationsCount(getRTAOfficeId(userId), userRole, getRegistrationCategoryId(regType))
				.intValue();
		return count;
	}

	@Override
	@Transactional
	public Long countProcessedApplications(Long userId, String query, Long from, Long to, Integer perPageRecords,
			Integer pageNumber, Status status, UserType userType, String regCategory) {
		/*
		 * if (userType == UserType.ROLE_AO || userType == UserType.ROLE_RTO) {
		 * return vehicleRcHistoryDAO.countApplicationsWithUserStatus(userId,
		 * status); } else if (userType == UserType.ROLE_CCO || userType ==
		 * UserType.ROLE_MVI) { return
		 * vehicleRcHistoryDAO.countApplicationsWithUserStatus(userId, status);
		 * if (status == Status.APPROVED) { } else if (status ==
		 * Status.REJECTED) { return
		 * rejectionHistoryDAO.countApplicationsWithUserStatus(userId, status);
		 * } }
		 */
		return vehicleRcHistoryDAO.countApplicationsWithUserStatus(userId, status, query, from, to, perPageRecords,
				pageNumber, true, getRegistrationCategoryId(regCategory));
	}

	@Override
	@Transactional
	public Long countApplicationsByRTAOffice(Long rtaOfficeId, Status status) {
		return vehicleDAO.countApplications(rtaOfficeId, status, null);
	}

	@Override
	@Transactional
	public HashMap<String, Object> getApprovedRejectedApps(Long userId, String query, Long from, Long to,
			Integer perPageRecords, Integer pageNumber, Status status, String regCategory) {
		HashMap<String, Object> mapObject = new HashMap<String, Object>();
		mapObject = getApplications(userId, status, query, from, to, perPageRecords, pageNumber, regCategory);
		return mapObject;
	}

	private HashMap<String, Object> getApplications(Long userId, Status status, String query, Long from, Long to,
			Integer perPageRecords, Integer pageNumber, String regCategory) {
		HashMap<String, Object> mapObject = new HashMap<String, Object>();
		List<VehicleRCEntity> vre = new ArrayList<VehicleRCEntity>();
		log.info(":::::::::getApplications start:::::::::::::::: " + mapObject);
		mapObject = vehicleDAO.getApplications(getRTAOfficeId(userId), status, query, from, to, perPageRecords,
				pageNumber, getRegistrationCategoryId(regCategory));
		List<VehicleRCEntity> list = (List<VehicleRCEntity>) mapObject.get("VehicleRCEntity");
		Integer totalRecords = (Integer) mapObject.get("totalRecords");
		log.info(":::::::::getApplications end:::::::::::::::: " + list);
		vre = list;
		if (ObjectsUtil.isNullOrEmpty(vre)) {
			return null;
		}
		CustomerDetailsRequestModel custModel = null;
		List<ApplicationModel> aml = new ArrayList<>();
		for (VehicleRCEntity entity : vre) {
			ApplicationModel am = new ApplicationModel();
			OwnershipType ownershipType = OwnershipType.getOwnershipType(entity.getOwnershipType());
			custModel = getCustomerDetails(entity.getVehicleRcId(), ownershipType);
			am.setCustomerDetails(custModel);
			am.setIteration(entity.getIteration());
			// am.setPrStatus(entity.getPrStatus());
			if (!ObjectsUtil.isNull(entity.getPrStatus())) {
				am.setPrIssueTime(entity.getPrIssueTime());
				am.setStatus(Status.getStatus(entity.getPrStatus()));
			}
			am.setTrNumber(entity.getTrNumber());
			am.setTrIssueTime(entity.getTrIssueTime());
			am.setVehicleRcId(entity.getVehicleRcId());
			VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(entity.getVehicleRcId());
			VehicleBaseModel vehicleBaseModel = new VehicleBaseModel();
			vehicleBaseModel.setVehicleClass(vde.getVehicleClass());
			vehicleBaseModel.setChassisNumber(vde.getChassisNo());
			vehicleBaseModel.setMakersClass(vde.getMakerClass() == null ? "" : vde.getMakerClass());
			/*
			 * vehicleBaseModel.setBodyTypeDesc(null);
			 * vehicleBaseModel.setColor(vde.getColor());
			 * vehicleBaseModel.setCreatedBy(vde.getCreatedBy());
			 * vehicleBaseModel.setCreatedOn(vde.getCreatedOn());
			 * vehicleBaseModel.setCubicCapacity(null);
			 * vehicleBaseModel.setEngineNumber(vde.getEngineNo());
			 * vehicleBaseModel.setFuelUsed(vde.getFuelUsed());
			 * vehicleBaseModel.setMakersName(vde.getMakerName());
			 * vehicleBaseModel.setManufacturingMonthYear(vde.getMfgDate());
			 * vehicleBaseModel.setModifiedBy(vde.getModifiedBy());
			 * vehicleBaseModel.setModifiedOn(vde.getModifiedOn());
			 * vehicleBaseModel.setRlw(vde.getRlw());
			 * vehicleBaseModel.setSeatingCapacity(vde.getSeatingCapacity());
			 * vehicleBaseModel.setStandCapacity(null);
			 * vehicleBaseModel.setTaxType(vde.getTaxTypeId().getCode());
			 * vehicleBaseModel.setUlw(vde.getUlw());
			 * vehicleBaseModel.setVahanId(null);
			 * vehicleBaseModel.setVehicleDetailsId(vde.getVehicleDtlId());
			 */
			am.setVehicleModel(vehicleBaseModel);
			aml.add(am);
		}
		mapObject = new HashMap<String, Object>();
		mapObject.put("applicationModels", aml);
		mapObject.put("totalRecords", totalRecords);
		return mapObject;
	}

	/*
	 * @Override
	 * 
	 * @Transactional public List<ApplicationModel> getRejectedApplications(Long
	 * userId, Long query, Long from, Long to) { return getApplications(userId,
	 * Status.REJECTED, query, from, to); }
	 */

	public CustomerDetailsRequestModel getCustomerDetails(Long vehicleRcId, OwnershipType ownershipType) {
		CustomerDetailsRequestModel model = null;
		if (OwnershipType.INDIVIDUAL == ownershipType) {
			CustIndividualDetailsEntity cide = customerDAO.getByVehicleRcId(vehicleRcId);
			model = new CustomerDetailsRequestModel();
			model.setFather_name(cide.getFatherName());
			model.setFirst_name(cide.getFirstName());
			model.setLast_name(cide.getSurName());
		} else if (OwnershipType.COMPANY == ownershipType || OwnershipType.POLICE == ownershipType
				|| OwnershipType.STU_VEHICLES == ownershipType || OwnershipType.ORGANIZATION == ownershipType) {
			CustCorporateDetailsEntity cide = customerCorporateDao.getByVehicleRcId(vehicleRcId);
			model = new CustomerDetailsRequestModel();
			model.setFirst_name(cide.getCompanyName());
		}
		/*
		 * Long mandalId = address.getMandal(); List<AddressEntity> add = ue.get
		 * if (null != add && add.size() > 0) { AddressEntity address =
		 * add.get(0); dm.setCity(address.getCity()); StateEntity state;
		 * dm.setState((state = address.getState()) != null ? state.getName() :
		 * ""); DistrictEntity dist; dm.setDistrict((dist =
		 * address.getDistrict()) != null ? dist.getName() : ""); MandalEntity
		 * me = address.getMandal(); if (me != null) { RtaOfficeEntity roe;
		 * dm.setRtaOffice((roe = me.getRtaOffices().get(0)) != null ?
		 * roe.getName() : ""); } }
		 */

		return model;
	}

	private Long getRTAOfficeId(Long userId) {
		RTAEmployeeEntity empEntity = rtaEmployeeDAO.getByUserId(userId);
		if (ObjectsUtil.isNull(empEntity)) {
			log.info("RTAEmployee not found for userId : " + userId);
			throw new IllegalArgumentException("RTAEmployee not found for provided userId !!");
		}
		return empEntity.getRtaOfficeEntity().getRtaOfficeId();
	}

	@Transactional
	synchronized public Boolean openApplication(Long vehicleRcId, Long userId, String ip, boolean newService)
			throws ConflictException, AccessDeniedException {
		VehicleRCEntity vehicleEntity = vehicleDAO.get(vehicleRcId);
		if (vehicleEntity == null)
			throw new IllegalArgumentException("Invalid VehicleRcId!");

		UserEntity userEntity = userDao.getEntity(UserEntity.class, userId);
		if (userEntity == null)
			throw new IllegalArgumentException("Incorrect userId");

		boolean isInValid = true;
		if (newService) {
			log.error("###### NEW SERVICE, Open Action on VehicleRcId: " + vehicleRcId);
			ApplicationModel openApp  = null;
			if(UserType.ROLE_MVI == userEntity.getUserType()) {
		         VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicleRcId);
		            openApp = getOpenORPendingApplications(userEntity.getUserId(), userEntity.getUserType(),
		                    vehicleEntity.getRegCategory().getCode(), true, vde.getVehicleCategory());
			} else{
			    openApp = getOpenORPendingApplications(userEntity.getUserId(), userEntity.getUserType(),
                        vehicleEntity.getRegCategory().getCode(), true, null);
			}
			if (null != openApp) {
				if (vehicleRcId.compareTo(openApp.getVehicleRcId()) == 0) {
					if (openApp.getStatus() != null && openApp.getStatus() == Status.OPEN) {
						return true;
					} else {
						isInValid = false;
					}
				} else if (openApp.getStatus() != null && openApp.getStatus() == Status.OPEN) {
					// already opened another app.
					throw new ConflictException("Invalid Action (Another application is already in view).");
				}
			}
		} else {
			// #TODO remove this block
			List<ApplicationModel> applicationList = getPendingApplication(userEntity.getUserId(),
					userEntity.getUserType(), false, null, null);

			if (!ObjectsUtil.isNull(applicationList)) {
				for (ApplicationModel app : applicationList) {
					if (vehicleRcId.compareTo(app.getVehicleRcId()) == 0) {
						if (app.getStatus() != null && app.getStatus() == Status.OPEN) {
							return true;
						} else {
							isInValid = false;
						}
					} else if (app.getStatus() != null && app.getStatus() == Status.OPEN) {
						// already opened another app.
						throw new ConflictException("Invalid Action (Another application is already in view).");
					}
				}
			}
		}

		if (isInValid) {
			throw new AccessDeniedException("Invalid Action (This application is not in your pending list).");
		}

		switch (userEntity.getUserType()) {

		case ROLE_MVI:
			if (vehicleEntity.getProcessStatus() == Status.PENDING.getValue())
				vehicleEntity.setProcessStatus(Status.MVI.getValue());
			else if (vehicleEntity.getProcessStatus() == Status.CCO.getValue())
				vehicleEntity.setProcessStatus(Status.CCO_MVI.getValue());

			vehicleEntity.setMviActionStatus(Status.OPEN.getValue());
			vehicleEntity.setMviUserId(userEntity);
			break;
		case ROLE_CCO:
			if (vehicleEntity.getProcessStatus() == Status.PENDING.getValue())
				vehicleEntity.setProcessStatus(Status.CCO.getValue());
			else if (vehicleEntity.getProcessStatus() == Status.MVI.getValue())
				vehicleEntity.setProcessStatus(Status.CCO_MVI.getValue());

			vehicleEntity.setCcoActionStatus(Status.OPEN.getValue());
			vehicleEntity.setCcoUserId(userEntity);
			break;
		case ROLE_AO:
			vehicleEntity.setProcessStatus(Status.AO.getValue());
			vehicleEntity.setAoActionStatus(Status.OPEN.getValue());
			vehicleEntity.setAoUserId(userEntity);
			break;
		case ROLE_RTO:
			vehicleEntity.setProcessStatus(Status.RTO.getValue());
			vehicleEntity.setRtoActionStatus(Status.OPEN.getValue());
			vehicleEntity.setRtoUserId(userEntity);
			break;

		}

		// RTAEmployeeEntity rtaEmployeeEntity =
		// rtaEmployeeDAO.getByUserId(userId);
		VehicleRCHistoryEntity vehicleRCEntityNew = new VehicleRCHistoryEntity();
		vehicleRCEntityNew.setCreatedBy(userEntity.getUserName());
		vehicleRCEntityNew.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		vehicleRCEntityNew.setIpAddress(ip);
		vehicleRCEntityNew.setIteration(vehicleEntity.getIteration());
		vehicleRCEntityNew.setModifiedBy(userEntity.getUserName());
		vehicleRCEntityNew.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		vehicleRCEntityNew.setRtaEmployeeType(userEntity.getUserType().getValue());
		vehicleRCEntityNew.setStatus(Status.OPEN.getValue());
		vehicleRCEntityNew.setUserEntity(userEntity);
		vehicleRCEntityNew.setVehicleRc(vehicleEntity);
		vehicleRcHistoryDAO.saveOrUpdate(vehicleRCEntityNew);
		vehicleDAO.update(vehicleEntity);
		return true;
	}

	@Override
	@Transactional
	public Map<String, String> getInfo(Long vehicleRcId, DocTypes docTypes, UserType userType)
			throws VehicleRcNotFoundException {
		VehicleRCEntity vehicleRcEntity = vehicleDAO.getEntity(VehicleRCEntity.class, vehicleRcId);
		if (ObjectsUtil.isNull(vehicleRcEntity)) {
			throw new VehicleRcNotFoundException("vehicle rc not found");
		}
		Map<String, String> info = new HashMap<>();
		switch (docTypes) {
		case DOC_CHASSIS_NO: {
			String chassisNumber = vehicleRcEntity.getChassisNumber();
			info.put("chassisNumber", chassisNumber);
			break;
		}
		case DOC_CHASSIS_NO_DISTANT_VIEW:
			break;
		case DOC_ENGINE_NO: {
			VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicleRcId);
			info.put("engineNumber", vde.getEngineNo());
			break;
		}
		case DOC_INSURANCE_POLICY_COPY: {
			InsuranceDetailsEntity ide = insuranceDao.getInsuranceDtlsEntityByVehicleRcId(vehicleRcId);
			info.put("policyNo", ide.getPolicyNo());
			info.put("mode", ide.getMode());
			info.put("provider", ide.getProvider());
			info.put("startDate", ide.getStartDate().toString());
			info.put("endDate", ide.getEndDate().toString());
			info.put("tenure", ide.getTenure().toString());
			info.put("status", Status.getStatus(ide.getStatus()).getLabel());
			break;
		}
		case DOC_OWNER_PHOTO:
			break;
		case DOC_PAN_CARD: {
			Integer ownershipTypeValue = vehicleRcEntity.getOwnershipType();
			OwnershipType ownershipType = OwnershipType.getOwnershipType(ownershipTypeValue);
			switch (ownershipType) {
			case POLICE:
			case COMPANY:
			case STU_VEHICLES:
			case ORGANIZATION:
				break;
			case INDIVIDUAL:
				CustIndividualDetailsEntity entity = customerDAO.getByVehicleRcId(vehicleRcId);
				if (ObjectsUtil.isNull(entity.getPanNo())) {
					return info;
				}
				info.put("pannumber", entity.getPanNo());
				break;
			}
			break;
		}
		case DOC_PHOTO_ID_PROOF:
			break;
		case DOC_PRESENT_ADDRESS_PROOF:
		case DOC_PRESENT_ADDRESS_PROOF_BACKVIEW: {
			StringBuilder sb = null;
			Integer ownershipTypeValue = vehicleRcEntity.getOwnershipType();
			OwnershipType ownershipType = OwnershipType.getOwnershipType(ownershipTypeValue);
			switch (ownershipType) {
			case POLICE:
			case COMPANY:
			case STU_VEHICLES: 
			case ORGANIZATION: {
				CustCorporateDetailsEntity entity = customerCorporateDao.getByVehicleRcId(vehicleRcId);
				AddressEntity address = customerCorporateDao.getAddressDetailsByUserId(entity.getCustCorpDtlsId(), "T");
				MandalEntity mandalEntity = address.getMandal();
				DistrictEntity districtEntity = mandalEntity.getDistrictEntity();
				StateEntity stateEntity = districtEntity.getStateEntity();
				CountryEntity countryEntity = stateEntity.getCountryEntity();
				sb = new StringBuilder(address.getDoorNo());
				StringsUtil.appendIfNotNull(sb, address.getStreet());
				StringsUtil.appendIfNotNull(sb, address.getCity());
				StringsUtil.appendIfNotNull(sb, mandalEntity.getName());
				StringsUtil.appendIfNotNull(sb, districtEntity.getName());
				StringsUtil.appendIfNotNull(sb, stateEntity.getName());
				StringsUtil.appendIfNotNull(sb, countryEntity.getName());
				info.put("address", sb.toString());
				break;
			}
			case INDIVIDUAL: {
				CustIndividualDetailsEntity entity = customerDAO.getByVehicleRcId(vehicleRcId);
				info.put("dependentAddrs", ObjectsUtil.isNull(entity.isDependentAddrs()) ? "false"
						: String.valueOf(entity.isDependentAddrs()));
				info.put("addressPerson", entity.getAddressPerson());
				info.put("personName", entity.getNameOfDependent());
				AddressEntity address = customerDAO.getAddressDetailsByUserId(entity.getCustIndDtlId(), "T");
				MandalEntity mandalEntity = address.getMandal();
				DistrictEntity districtEntity = mandalEntity.getDistrictEntity();
				StateEntity stateEntity = districtEntity.getStateEntity();
				CountryEntity countryEntity = stateEntity.getCountryEntity();
				sb = new StringBuilder(address.getDoorNo());
				StringsUtil.appendIfNotNull(sb, address.getStreet());
				StringsUtil.appendIfNotNull(sb, address.getCity());
				StringsUtil.appendIfNotNull(sb, mandalEntity.getName());
				StringsUtil.appendIfNotNull(sb, districtEntity.getName());
				StringsUtil.appendIfNotNull(sb, stateEntity.getName());
				StringsUtil.appendIfNotNull(sb, countryEntity.getName());
				info.put("address", sb.toString());
			}
			}
			break;
		}
		case DOC_USER_SIGNATURE:
			break;
		case DOC_VEHICLE_FRONT: {
			VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicleRcId);
			if (!ObjectsUtil.isNull(vde)) {
				info.put("modelName", vde.getMakerClass());
				info.put("color", vde.getColor());
			}
			break;
		}
		case DOC_VEHICLE_LEFT:
			break;
		case DOC_VEHICLE_REAR: {
			VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicleRcId);
			if (!ObjectsUtil.isNull(vde)) {
				info.put("modelName", vde.getMakerClass());
				info.put("color", vde.getColor());
			}
			break;
		}
		case DOC_VEHICLE_RIGHT:
			break;
		case DOC_COPY_OF_PROCEEDINGS:
			break;
		case DOC_DIFFERENTLY_ABLED_CERTIFICATE:
			break;
		case DOC_EMERGENCY_EXIT_IMAGE:
			break;
		case DOC_EXTRA_1:
			break;
		case DOC_EXTRA_2:
			break;
		case DOC_FOOT_BOARD_IMAGE_OF_VEHICLE:
			break;
		case DOC_FORM20DO:
			info.put("message", "Is Form20 signed by Owner ?");
			info.put("isSystemGenerated", "true");
			break;
		case DOC_FORM22: {
			VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicleRcId);
			info.put("chassisNo", vde.getChassisNo());
			info.put("engineNo", vde.getEngineNo());
			info.put("message", "Is Form22 contains same chassis and engine number as mentioned above ?");
			break;
		}
		case DOC_INTERIOR_VEHICLE_IMAGE_1:
			break;
		case DOC_INTERIOR_VEHICLE_IMAGE_2:
			break;
		case DOC_INTERIOR_VEHICLE_IMAGE_3:
			break;
		case DOC_LETTER_FROM_DISTRICT_EDUCATIONAL_OFFICER:
			break;
		case DOC_LETTER_OF_AUTHORIZATION:
			break;
		case DOC_PASSPORT:
			break;
		case DOC_REGISTRATION_CERTIFICATE_EDUCATIONAL_INSTITUTE:
			break;
		case DOC_ROC:
			break;
		case DOC_RTA_APPROVAL_LETTER:
			break;
		case DOC_VEHICLE_FRONT_AFTER_MODIFICATION:
			break;
		case DOC_VEHICLE_LEFT_AFTER_MODIFICATION:
			break;
		case DOC_VEHICLE_REAR_AFTER_MODIFICATION:
			break;
		case DOC_VEHICLE_SIDE_AFTER_MODIFICATION: {
			List<VehicleAlterationEntity> entities = vehicleAlterationDAO.getVehicleAlterationDetails(vehicleRcId, Status.DIFFERENTIAL_TAX);
			VehicleAlterationEntity entity =null;
			try{
				
				for(VehicleAlterationEntity alterationEnitity : entities ){
					AlterationCategory alterationCategory = AlterationCategory
							.getAlterationCategory(alterationEnitity.getAlterationCategory());
					if (alterationCategory == AlterationCategory.BODY_TYPE) {
						info.put("bodyTypeUpdated", alterationEnitity.getBodyTypeUpdated());
						info.put("rlwUpdated", String.valueOf(alterationEnitity.getUlwUpdated()));
						info.put("ulwUpdated", String.valueOf(alterationEnitity.getRlwUpdated()));
					} else if (alterationCategory == AlterationCategory.SEATING_CAPACITY){
						info.put("seatingCapacity", String.valueOf(alterationEnitity.getSeatingCapacity()));
					}
					info.put("alterationCategory", alterationCategory.getLabel());
					entity = alterationEnitity;
				}
				info.put("lengthUpdated", String.valueOf(entity.getLengthUpdated()));
				info.put("heightUpdated", String.valueOf(entity.getHeightUpdated()));
				info.put("widthUpdated", String.valueOf(entity.getWidthUpdated()));
				info.put("completionDate", String.valueOf(entity.getCompletionDate()));
				info.put("vehicleSubClass", entity.getVehicleSubClass());
				info.put("colorUpdated", entity.getColorUpdated());
				if (!StringsUtil.isNullOrEmpty(entity.getVehicleSubClass())) {
					info.put("vehicleSubClassDecs",
							vehicleClassDescDAO.getByVehiclClassDesCode(entity.getVehicleSubClass()).getDescription());
				}
			}catch (Exception e) {
			}
			
			break;
		}
		case DOC_VEHICLE_SIDE_BEFORE_MODIFICATION:
			break;
		case DOC_VEHICLE_FRONT_BEFORE_MODIFICATION:
			break;
		case DOC_EXTRA1_AFTER_MODIFICATION:
			break;
		case DOC_EXTRA2_AFTER_MODIFICATION:
			break;
		case DOC_STOP_SIGN_BOARD:
			break;
		case DOC_VISA_COPY:
			break;
		case DOC_INDIVIDUAL_INVOICE:
		case DOC_TAX_INDIVIDUAL_INVOICE: {
			VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicleRcId);
			if (ObjectsUtil.isNull(vde)) {
				return info;
			}
			VehicleBillingDetailsEntity vbde = vehicleBillingDetailsDAO.getByVehicleDetailId(vde);
			info.put("invoiceNumber", vbde.getInvoiceNumber());
			info.put("helmetInvoiceAmount", String.valueOf(vbde.getHelmetInvoiceAmount()));
			info.put("invoiceDate", vbde.getInvoiceDate().toString());
			info.put("invoiceValue", String.valueOf(NumberParser.numberFormat(vbde.getInvoiceValue())));
			break;
		}
		case DOC_MINOR_INDIVIDUAL:
			break;
		case DOC_FORM_21:
			info.put("message", "Is Form21 signed by Dealer?");
			info.put("isSystemGenerated", "true");
			break;
		case MODEL_NAME:
			VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicleRcId);
			if (!ObjectsUtil.isNull(vde)) {
				info.put("makerName", vde.getMakerName());
				info.put("modelName", vde.getMakerClass());
				info.put("modelDisclaimer", "Is Vehicle model approved under AP RTA ?");
				info.put("isSystemGenerated", "true");
			}
			break;
		case DOC_DL:
			break;
		case DOC_FORM20DO_COPY:
			break;
		case DOC_HELMET_INVOICE_COPY:
			break;
		}
		return info;
	}

	@Override
	@Transactional
	public SaveUpdateResponse saveDocsAction(ApplicantDocsModel applicantDocModel, Long userId, String userName,
			UserType userType, String ipAddress, boolean rejected) {
		List<ApprovalDocModel> docs = applicantDocModel.getAppDocs();
		Long timeStamp = DateUtil.toCurrentUTCTimeStamp();
		Status appStatus = Status.APPROVED;
		UserEntity userEntity = new UserEntity(userId);
		VehicleRCEntity vehicleRcEntity = vehicleDAO.get(applicantDocModel.getVehicleRcId());
		if (ObjectsUtil.isNull(vehicleRcEntity)) {
			throw new IllegalArgumentException("Invalid VehicleRcId !!");
		}

		// Current step changed to 6 if vehicles current step in Entity is less
		// than 6(save case)
		if (userType == UserType.ROLE_DEALER && vehicleRcEntity.getCurrentStep() < reviewStep) {
			vehicleRcEntity.setCurrentStep(reviewStep);
		}

		// Dealer is taking action on rejected application
		if (userType == UserType.ROLE_DEALER) {
			if (rejected && vehicleRcEntity.getPrStatus() == Status.REJECTED.getValue()) {
				// if not rejected because of second vehicle then send app to
				// rta
				Boolean isSecondVehicleRejection = false;
				SecondVehicleDetailsModel secondVehicle = secondVehicleRejectionService
						.get(vehicleRcEntity.getVehicleRcId(), vehicleRcEntity.getIteration());
				log.info("In saveDocAction for " + vehicleRcEntity.getVehicleRcId() + " with second vehicle details => "
						+ secondVehicle);
				if (!ObjectsUtil.isNull(secondVehicle)
						&& ((secondVehicle.getPaidTax() == null || !secondVehicle.getPaidTax()) && secondVehicle.getIsValidSecondVehicle())) {
					isSecondVehicleRejection = true;
				}
				if (!isSecondVehicleRejection) {
					log.info("Sending Application " + vehicleRcEntity.getVehicleRcId() + " for RTA.");
					// set application again for RTA approval
					vehicleRcEntity.setPrStatus(Status.PENDING.getValue());
					vehicleRcEntity.setPrIssueTime(null);
					vehicleRcEntity.setProcessStatus(Status.PENDING.getValue());
					vehicleRcEntity.setIteration(vehicleRcEntity.getIteration() + 1);
					vehicleRcEntity.setModifiedBy(userName);
					vehicleRcEntity.setModifiedOn(timeStamp);
					vehicleRcEntity.setCcoUserId(null);
					vehicleRcEntity.setMviUserId(null);
					vehicleRcEntity.setAoUserId(null);
					vehicleRcEntity.setRtoUserId(null);
					vehicleRcEntity.setCcoActionStatus(Status.PENDING.getValue());
					vehicleRcEntity.setMviActionStatus(Status.PENDING.getValue());
					vehicleRcEntity.setAoActionStatus(Status.PENDING.getValue());
					vehicleRcEntity.setRtoActionStatus(Status.PENDING.getValue());
				}
			} else if (rejected && vehicleRcEntity.getPrStatus() != Status.REJECTED.getValue()) {
				log.error("Invalid Action (application yet not rejected by RTA.) for vehicleRcId: "
						+ vehicleRcEntity.getVehicleRcId());
				throw new IllegalArgumentException("Invalid Action (application yet not rejected by RTA.)");
			} else if (!rejected && vehicleRcEntity.getPrStatus() == Status.REJECTED.getValue()) {
				log.error("Invalid Action (application has been rejected from RTA.) for vehicleRcId: "
						+ vehicleRcEntity.getVehicleRcId());
				throw new IllegalArgumentException("Invalid Action (application has been rejected from RTA.)");
			}
		}

		for (ApprovalDocModel doc : docs) {
			AttachmentDetailsEntity attachmentDetailsEntity = attachmentDetailsDAO
					.getEntity(AttachmentDetailsEntity.class, doc.getAttachmentDlId());
			if (ObjectsUtil.isNull(attachmentDetailsEntity) || applicantDocModel.getVehicleRcId()
					.compareTo(attachmentDetailsEntity.getVehicle().getVehicleRcId()) != 0) {
				log.info("Invalid VehicleRcId : " + applicantDocModel.getVehicleRcId() + " found for AttachmentDlId : "
						+ doc.getAttachmentDlId());
				throw new IllegalArgumentException("Invalid AttachmentDlId " + doc.getAttachmentDlId());
			}
			attachmentDetailsEntity.setStatus(doc.getStatus().getValue());
			attachmentDetailsEntity.setModifiedBy(userName);
			attachmentDetailsEntity.setModifiedOn(timeStamp);
			attachmentDetailsDAO.update(attachmentDetailsEntity);
			if (doc.getStatus() == Status.REJECTED) {
				appStatus = Status.REJECTED;
				RejectionHistoryEntity rejectionHistoryEntity = new RejectionHistoryEntity();
				rejectionHistoryEntity.setAttachmentDetailsId(attachmentDetailsEntity);
				rejectionHistoryEntity.setComment(doc.getComments());

				rejectionHistoryEntity.setStatus(doc.getStatus().getValue());
				rejectionHistoryEntity.setUserId(userEntity);
				rejectionHistoryEntity.setUserType(userType.getValue());
				rejectionHistoryEntity.setTitle(attachmentDetailsEntity.getAttachmentTitle());
				rejectionHistoryEntity.setVehicleRCId(vehicleRcEntity);
				rejectionHistoryEntity.setCreatedOn(timeStamp);
				rejectionHistoryEntity.setCreatedBy(userName);
				rejectionHistoryEntity.setModifiedBy(userName);
				rejectionHistoryEntity.setModifiedOn(timeStamp);
				rejectionHistoryEntity.setIteration(vehicleRcEntity.getIteration());
				rejectionHistoryDAO.save(rejectionHistoryEntity);
			}
		}
		if (userType == UserType.ROLE_CCO && appStatus != Status.REJECTED) {
			// if cco has rejected second vehicle reject the application.
			SecondVehicleDetailsModel secondVehicle = secondVehicleRejectionService
					.get(vehicleRcEntity.getVehicleRcId(), vehicleRcEntity.getIteration());
			if (!ObjectsUtil.isNull(secondVehicle)
					&& ((secondVehicle.getPaidTax() == null || !secondVehicle.getPaidTax()) && secondVehicle.getIsValidSecondVehicle())) {
				appStatus = Status.REJECTED;
			}
		}

		if (userType != UserType.ROLE_AO && userType != UserType.ROLE_RTO) {
			if (userType == UserType.ROLE_CCO) {
				vehicleRcEntity.setCcoActionStatus(appStatus.getValue());
				vehicleRcEntity.setCcoUserId(userEntity);
			} else if (userType == UserType.ROLE_MVI) {
				vehicleRcEntity.setMviActionStatus(appStatus.getValue());
				vehicleRcEntity.setMviUserId(userEntity);
			}
			vehicleDAO.update(vehicleRcEntity);

			VehicleRCHistoryEntity vehicleRcHistoryEntity = new VehicleRCHistoryEntity();
			vehicleRcHistoryEntity.setIpAddress(ipAddress);
			vehicleRcHistoryEntity.setIteration(vehicleRcEntity.getIteration());
			vehicleRcHistoryEntity.setVehicleRc(vehicleRcEntity);
			vehicleRcHistoryEntity.setUserEntity(userEntity);
			vehicleRcHistoryEntity.setStatus(appStatus.getValue());
			vehicleRcHistoryEntity.setRtaEmployeeType(userType.getValue());
			vehicleRcHistoryEntity.setModifiedBy(userName);
			vehicleRcHistoryEntity.setModifiedOn(timeStamp);
			vehicleRcHistoryEntity.setCreatedBy(userName);
			vehicleRcHistoryEntity.setCreatedOn(timeStamp);
			vehicleRcHistoryDAO.save(vehicleRcHistoryEntity);
		}
		SaveUpdateResponse response = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Saved Successfully.",
				applicantDocModel.getVehicleRcId().toString());
		return response;
	}

	@Override
	@Transactional
	public ApplicantDocsModel getDocsForApplicant(Long vehicleRcId, Long userId, UserType docForUserType) {
		Map<String, String> info = null;
		ApprovalDocModel approvalModel = null;
		List<RejectionHistoryEntity> rejectionHistories = null;
		List<VehicleRCHistoryEntity> vehicleHistories = null;
		List<AttachmentDetailsEntity> attachmentDocs = null;
		CommentHistoryModel commentsHistry = null;

		UserEntity userEntity = userDao.findByUserId(userId);
		if (userEntity == null)
			throw new IllegalArgumentException("The Provided userId does not exists");

		if (docForUserType == UserType.ROLE_DEALER) {
			attachmentDocs = attachmentDetailsDAO.getAllAttachmentsForvehicleRCId(vehicleRcId, docForUserType);
		} else if (docForUserType == UserType.ROLE_AO || docForUserType == UserType.ROLE_RTO) {
			attachmentDocs = attachmentDetailsDAO.getAllAttachments(vehicleRcId, UserType.ROLE_CCO);
			attachmentDocs.addAll(attachmentDetailsDAO.getAllAttachments(vehicleRcId, UserType.ROLE_MVI));
		} else
			attachmentDocs = attachmentDetailsDAO.getAllAttachments(vehicleRcId, docForUserType);

		List<ApprovalDocModel> approvalDocs = new ArrayList<>(attachmentDocs.size() + 1);

		if ((userEntity.getUserType() == UserType.ROLE_MVI && docForUserType != UserType.ROLE_MVI)
				|| (userEntity.getUserType() == UserType.ROLE_CCO && docForUserType != UserType.ROLE_CCO)) {
			throw new IllegalArgumentException(
					"The user is not allowed to get documents for user" + docForUserType.getLabel());
		}

		if (docForUserType == UserType.ROLE_AO) {
			approvalModel = new ApprovalDocModel();
			vehicleHistories = vehicleRcHistoryDAO.getHistforVehicleByUserType(vehicleRcId, docForUserType);

			if (vehicleHistories == null)
				throw new IllegalArgumentException("AO hasnt perfomed any Action");

			for (VehicleRCHistoryEntity vehicleHistory : vehicleHistories) {
				if (vehicleHistory.getStatus() == Status.APPROVED.getValue()
						|| vehicleHistory.getStatus() == Status.REJECTED.getValue()) {
					commentsHistry = new CommentHistoryModel(Status.getStatus(vehicleHistory.getStatus()),
							vehicleHistory.getComment());
					approvalModel.getCommentHistory().put(vehicleHistory.getModifiedOn(), commentsHistry);
				}
			}
			approvalDocs.add(approvalModel);
		}

		for (AttachmentDetailsEntity attachmentDoc : attachmentDocs) {
			if (attachmentDoc.getDocTypes().getDocTypeId() == DocTypes.DOC_DEALER_SIGNATURE.getValue()
					&& docForUserType != UserType.ROLE_DEALER)
				continue;
			approvalModel = new ApprovalDocModel();

			approvalModel.setDocType(attachmentDoc.getDocTypes().getDocTypeId(), attachmentDoc.getDocTypes().getName(),
					attachmentDoc.getDocTypes().getIsMandatory(), attachmentDoc.getDocTypes().getRoleName());

			if (attachmentDoc.getStatus() == Status.REJECTED.getValue()) {
				rejectionHistories = rejectionHistoryDAO.get(attachmentDoc,
						UserType.getUserType(attachmentDoc.getDocTypes().getRoleName()));
				if (rejectionHistories != null && !rejectionHistories.isEmpty())
					for (RejectionHistoryEntity rejHistory : rejectionHistories) {
						commentsHistry = new CommentHistoryModel(Status.getStatus(rejHistory.getStatus()),
								rejHistory.getComment());
						approvalModel.getCommentHistory().put(rejHistory.getModifiedOn(), commentsHistry);
					}
			}
			try {
				info = getInfo(vehicleRcId, DocTypes.getDocTypes(attachmentDoc.getDocTypes().getDocTypeId()),
						docForUserType);
			} catch (VehicleRcNotFoundException exc) {
				exc.printStackTrace();

			}

			approvalModel.setAttachmentDlId(attachmentDoc.getAttachmentDlId());
			approvalModel.setAttachmentUrl(attachmentDoc.getSource());
			approvalModel.setDocRelatedInfo(info);
			approvalModel.setStatus(Status.getStatus(attachmentDoc.getStatus()));
			approvalDocs.add(approvalModel);
		}
		ApplicantDocsModel appDocsModel = new ApplicantDocsModel();
		appDocsModel.setAppDocs(approvalDocs);
		appDocsModel.setVehicleRcId(vehicleRcId);
		return appDocsModel;
	}

	@Override
	@Transactional
	public Map<String, Object> getRejectionHistory4Correction(Long vehicleRcId, UserType userType)
			throws VehicleRcNotFoundException {
		VehicleRCEntity vehicleRcEntity = vehicleDAO.get(vehicleRcId);
		if (ObjectsUtil.isNull(vehicleRcEntity)) {
			throw new VehicleRcNotFoundException("vehicleRcId not found");
		}
		if (vehicleRcEntity.getPrStatus() != Status.REJECTED.getValue()) {
			throw new IllegalArgumentException("This application has not been rejected by RTA.");
		}
		Map<String, Object> map = new HashMap<>();
		Set<Integer> rejAttachmentSet = new HashSet<Integer>();
		List<AttachmentDetailsEntity> rList = attachmentDetailsDAO.getAllAttachmentsForvehicleRCId(vehicleRcId,
				userType);
		for (AttachmentDetailsEntity rhe : rList) {
			if (rhe.getStatus() == Status.REJECTED.getValue()) {
				setStep(DocTypes.getDocTypes(rhe.getDocTypes().getDocTypeId()), rejAttachmentSet);
			}
		}
		// add tax step if tax pay required
		if (vehicleRcEntity.getOwnershipType() == OwnershipType.INDIVIDUAL.getId()) {
			boolean isPH_InvalidCarridge = customerDAO.getIsCustomerDisabled(vehicleRcId);
			TransactionDetailEntity txnEntity = transactionDetailDAO.getByVehicleRcNdStatus(vehicleRcId,
					PaymentType.PAYTAX, Status.CLOSED);
			if (!isPH_InvalidCarridge && ObjectsUtil.isNull(txnEntity)) {
				rejAttachmentSet.add(taxStep);
			}
		}
		// add review step
		VehicleRCHistoryEntity history = vehicleRcHistoryDAO.getLastVehicleRcHistory(vehicleRcId, null, null, null);
		if (!ObjectsUtil.isNull(history) && !(history.getUserEntity().getUserType() == UserType.ROLE_DEALER
				&& history.getStatus() == Status.APPROVED.getValue())) {
			rejAttachmentSet.add(reviewStep);
		}
		Boolean isSecondVehicleRejection = false;
		SecondVehicleDetailsModel secondVehicle = secondVehicleRejectionService.get(vehicleRcId,
				vehicleRcEntity.getIteration());
		if (!ObjectsUtil.isNull(secondVehicle) && (secondVehicle.getPaidTax() == null || !secondVehicle.getPaidTax())) {
			if (secondVehicle.getIsValidSecondVehicle()) {
				isSecondVehicleRejection = true;
			} else {
				rejAttachmentSet.add(docsStep);
			}
		}
		map.put("rejectedSteps", rejAttachmentSet);
		map.put("isRejectedSecondVehicle", isSecondVehicleRejection);

		// CASE: Rejection of Model
		AttachmentDetailsEntity attachmentEntity = attachmentDetailsDAO.getAttatchDltIdByDocIdVehId(vehicleRcId,
				DocTypes.MODEL_NAME.getValue());
		if (!ObjectsUtil.isNull(attachmentEntity)) {
			attachmentEntity.setStatus(Status.UPLOADED.getValue());
		}
		return map;
	}

	private void setStep(DocTypes docType, Set<Integer> rejAttachmentSet) {
		switch (docType) {
		case DOC_CHASSIS_NO:
			rejAttachmentSet.add(vehicleStep);
			rejAttachmentSet.add(docsStep);
		case DOC_CHASSIS_NO_DISTANT_VIEW:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_ENGINE_NO:
			rejAttachmentSet.add(vehicleStep);
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_INSURANCE_POLICY_COPY:
			rejAttachmentSet.add(insuranceStep);
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_OWNER_PHOTO:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_PAN_CARD:
			rejAttachmentSet.add(vehicleStep);
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_PHOTO_ID_PROOF:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_PRESENT_ADDRESS_PROOF:
		case DOC_PRESENT_ADDRESS_PROOF_BACKVIEW:
			rejAttachmentSet.add(adhaarStep);
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_USER_SIGNATURE:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_VEHICLE_FRONT:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_VEHICLE_LEFT:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_VEHICLE_REAR:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_VEHICLE_RIGHT:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_COPY_OF_PROCEEDINGS:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_DIFFERENTLY_ABLED_CERTIFICATE:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_EMERGENCY_EXIT_IMAGE:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_EXTRA_1:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_EXTRA_2:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_FOOT_BOARD_IMAGE_OF_VEHICLE:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_FORM20DO:
			rejAttachmentSet.add(docsStep);
			rejAttachmentSet.add(financeStep);
			break;
		case DOC_FORM22:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_INTERIOR_VEHICLE_IMAGE_1:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_INTERIOR_VEHICLE_IMAGE_2:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_INTERIOR_VEHICLE_IMAGE_3:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_LETTER_FROM_DISTRICT_EDUCATIONAL_OFFICER:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_LETTER_OF_AUTHORIZATION:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_PASSPORT:
			rejAttachmentSet.add(adhaarStep);
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_REGISTRATION_CERTIFICATE_EDUCATIONAL_INSTITUTE:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_ROC:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_RTA_APPROVAL_LETTER:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_VEHICLE_SIDE_AFTER_MODIFICATION:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_VEHICLE_FRONT_AFTER_MODIFICATION:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_VEHICLE_LEFT_AFTER_MODIFICATION:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_VEHICLE_REAR_AFTER_MODIFICATION:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_VEHICLE_SIDE_BEFORE_MODIFICATION:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_VEHICLE_FRONT_BEFORE_MODIFICATION:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_EXTRA1_AFTER_MODIFICATION:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_EXTRA2_AFTER_MODIFICATION:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_STOP_SIGN_BOARD:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_VISA_COPY:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_INDIVIDUAL_INVOICE:
		case DOC_TAX_INDIVIDUAL_INVOICE:
			rejAttachmentSet.add(vehicleStep);
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_MINOR_INDIVIDUAL:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_FORM_21:
			rejAttachmentSet.add(docsStep);
			break;
		case MODEL_NAME:
			break;
		case DOC_HELMET_INVOICE_COPY:
			rejAttachmentSet.add(docsStep);
			break;
		case DOC_SECOND_VEHICLE_NOC:
		    rejAttachmentSet.add(docsStep);
            break;
		default:
			break;
		}
	}

	@Override
	@Transactional
	public Map<Integer, Map<String, List<RejectionHistoryModel>>> rejectionHistory(Long vehicleRcId)
			throws VehicleRcNotFoundException {
		VehicleRCEntity vehicleRcEntity = vehicleDAO.get(vehicleRcId);
		if (ObjectsUtil.isNull(vehicleRcEntity)) {
			throw new VehicleRcNotFoundException("vehicleRcId not found");
		}
		Map<Integer, Map<String, List<RejectionHistoryModel>>> mapWithIteration = new TreeMap<>();// treemap
																									// for
																									// order
																									// of
																									// iteration
		Integer iteration = vehicleRcEntity.getIteration();
		if (ObjectsUtil.isNull(iteration) || iteration.intValue() <= Integer.valueOf(0)) {
			return mapWithIteration;
		}
		List<RejectionHistoryEntity> rjeList = rejectionHistoryDAO.getRejectionHistory(vehicleRcId);
		Map<Integer, List<RejectionHistoryEntity>> groupByIterationRejectionHistoryEntity = groupByIterationForRejectionHistoryEntity(
				rjeList);
		List<VehicleRCHistoryEntity> vehicleRcHistoryEntityList = vehicleRcHistoryDAO.getRejectionHistory(vehicleRcId);
		Map<Integer, List<VehicleRCHistoryEntity>> groupByIterationVehicleRcHistoryEntity = groupByIterationForVehicleRCHistoryEntity(
				vehicleRcHistoryEntityList);
		for (int i = 1; i <= iteration; i++) {
			List<RejectionHistoryEntity> rjeNewList = groupByIterationRejectionHistoryEntity.get(i);
			Map<String, List<RejectionHistoryModel>> map = new HashMap<>();
			if (!ObjectsUtil.isNull(rjeNewList)) {
				for (RejectionHistoryEntity rje : rjeNewList) {
					String dateKey = DateUtil.extractDateAsString(rje.getModifiedOn());
					List<RejectionHistoryModel> list = map.get(dateKey);
					if (ObjectsUtil.isNull(list)) {
						List<RejectionHistoryModel> rjModelList = new ArrayList<>();
						UserEntity user = rje.getUserId();
						RejectionHistoryModel rjModel = rejectionHistoryHelper.convertToModel(rje);
						rjModel.setRejectionHistId(null);
						rjModel.setVehicleRCId(null);
						UserModel userModel = new UserModel();
						userModel.setUserId(user.getUserId());
						userModel.setUserName(user.getUserName());
						userModel.setFirstName(user.getFirstName());
						userModel.setUserType(UserType.getUserType(rje.getUserType()));
						rjModel.setUser(userModel);
						rjModel.setTime(DateUtil.extractTimeAsString(rjModel.getModifiedOn()));
						if (rje.getAttachmentDetailsId() != null) {
							rjModel.setDocType(
									docTypesHelper.convertToModel(rje.getAttachmentDetailsId().getDocTypes()));
						} else {
							DocTypesModel doc = new DocTypesModel();
							doc.setName(rje.getTitle());
							rjModel.setDocType(doc);
						}
						rjModelList.add(rjModel);
						map.put(dateKey, rjModelList);
					} else {
						UserEntity user = rje.getUserId();
						RejectionHistoryModel rjModel = rejectionHistoryHelper.convertToModel(rje);
						rjModel.setVehicleRCId(null);
						rjModel.setRejectionHistId(null);
						UserModel userModel = new UserModel();
						userModel.setUserId(user.getUserId());
						userModel.setUserName(user.getUserName());
						userModel.setFirstName(user.getFirstName());
						userModel.setUserType(UserType.getUserType(rje.getUserType()));
						rjModel.setUser(userModel);
						rjModel.setTime(DateUtil.extractTimeAsString(rjModel.getModifiedOn()));
						if (rje.getAttachmentDetailsId() != null) {
							rjModel.setDocType(
									docTypesHelper.convertToModel(rje.getAttachmentDetailsId().getDocTypes()));
						} else {
							DocTypesModel doc = new DocTypesModel();
							doc.setName(rje.getTitle());
							rjModel.setDocType(doc);
						}
						list.add(rjModel);
					}
				}
			}
			List<VehicleRCHistoryEntity> vrceNewList = groupByIterationVehicleRcHistoryEntity.get(i);
			if (!ObjectsUtil.isNull(vrceNewList)) {
				for (VehicleRCHistoryEntity vrce : vrceNewList) {
					if ((vrce.getUserEntity().getUserType() == UserType.ROLE_CCO)
							|| (vrce.getUserEntity().getUserType() == UserType.ROLE_MVI)) {
						continue;
					}
					String dateKey = DateUtil.extractDateAsString(vrce.getModifiedOn());
					List<RejectionHistoryModel> list = map.get(dateKey);
					if (ObjectsUtil.isNull(list)) {
						List<RejectionHistoryModel> rjModelList = new ArrayList<>();
						UserEntity user = vrce.getUserEntity();
						RejectionHistoryModel rjModel = new RejectionHistoryModel();
						rjModel.setVehicleRCId(null);
						// rjModel.setRejectionHistId(vrce.getVehicleRcHistoryId());
						rjModel.setStatus(Status.getStatus(vrce.getStatus()));
						rjModel.setComment(vrce.getComment());
						// rjModel.setTitle(source.getTitle());
						rjModel.setCreatedBy(vrce.getCreatedBy());
						rjModel.setCreatedOn(vrce.getCreatedOn());
						rjModel.setModifiedBy(vrce.getModifiedBy());
						rjModel.setModifiedOn(vrce.getModifiedOn());
						UserModel userModel = new UserModel();
						userModel.setUserId(user.getUserId());
						userModel.setUserName(user.getUserName());
						userModel.setFirstName(user.getFirstName());
						userModel.setUserType(user.getUserType());
						rjModel.setUser(userModel);
						rjModel.setTime(DateUtil.extractTimeAsString(rjModel.getModifiedOn()));
						rjModelList.add(rjModel);
						map.put(dateKey, rjModelList);
					} else {
						UserEntity user = vrce.getUserEntity();
						RejectionHistoryModel rjModel = new RejectionHistoryModel();
						rjModel.setVehicleRCId(null);
						// rjModel.setRejectionHistId(vrce.getVehicleRcHistoryId());
						rjModel.setStatus(Status.getStatus(vrce.getStatus()));
						rjModel.setComment(vrce.getComment());
						// rjModel.setTitle(source.getTitle());
						rjModel.setCreatedBy(vrce.getCreatedBy());
						rjModel.setCreatedOn(vrce.getCreatedOn());
						rjModel.setModifiedBy(vrce.getModifiedBy());
						rjModel.setModifiedOn(vrce.getModifiedOn());
						UserModel userModel = new UserModel();
						userModel.setUserId(user.getUserId());
						userModel.setUserName(user.getUserName());
						userModel.setFirstName(user.getFirstName());
						userModel.setUserType(user.getUserType());
						rjModel.setUser(userModel);
						rjModel.setTime(DateUtil.extractTimeAsString(rjModel.getModifiedOn()));
						list.add(rjModel);
					}
				}
			}
			mapWithIteration.put(i, map);
		}
		return mapWithIteration;
	}

	private Map<Integer, List<RejectionHistoryEntity>> groupByIterationForRejectionHistoryEntity(
			List<RejectionHistoryEntity> rjeList) {
		if (ObjectsUtil.isNullOrEmpty(rjeList)) {
			return new HashMap<>();
		}
		Map<Integer, List<RejectionHistoryEntity>> map = new HashMap<>();
		for (RejectionHistoryEntity rje : rjeList) {
			Integer itr = rje.getIteration();
			List<RejectionHistoryEntity> list = map.get(itr);
			if (ObjectsUtil.isNull(list)) {
				List<RejectionHistoryEntity> newRjeList = new ArrayList<>();
				newRjeList.add(rje);
				map.put(itr, newRjeList);
			} else {
				list.add(rje);
			}
		}
		return map;
	}

	private Map<Integer, List<VehicleRCHistoryEntity>> groupByIterationForVehicleRCHistoryEntity(
			List<VehicleRCHistoryEntity> rjeList) {
		if (ObjectsUtil.isNullOrEmpty(rjeList)) {
			return new HashMap<>();
		}
		Map<Integer, List<VehicleRCHistoryEntity>> map = new HashMap<>();
		for (VehicleRCHistoryEntity rje : rjeList) {
			Integer itr = rje.getIteration();
			List<VehicleRCHistoryEntity> list = map.get(itr);
			if (ObjectsUtil.isNull(list)) {
				List<VehicleRCHistoryEntity> newRjeList = new ArrayList<>();
				newRjeList.add(rje);
				map.put(itr, newRjeList);
			} else {
				list.add(rje);
			}
		}
		return map;
	}

	@Override
	@Transactional
	public Integer rejectionHistoryCount(Long vehicleRcId) throws VehicleRcNotFoundException {
		VehicleRCEntity vehicleRcEntity = vehicleDAO.get(vehicleRcId);
		if (ObjectsUtil.isNull(vehicleRcEntity)) {
			throw new VehicleRcNotFoundException("vehicleRcId not found");
		}
		List<RejectionHistoryEntity> rjeList = rejectionHistoryDAO.getRejectionHistory(
				vehicleRcId/* , vehicleRcEntity.getIteration() */);// changes
																	// for
																	// allowing
																	// all
																	// vehicle
																	// History
		List<VehicleRCHistoryEntity> vrceList = vehicleRcHistoryDAO.getRejectionHistory(vehicleRcId);
		List<VehicleRCHistoryEntity> vrceListNew = new ArrayList<>();
		for (VehicleRCHistoryEntity vrce : vrceList) {
			if ((vrce.getUserEntity().getUserType() == UserType.ROLE_CCO)
					|| (vrce.getUserEntity().getUserType() == UserType.ROLE_MVI)) {
				continue;
			}
			vrceListNew.add(vrce);
		}
		int totalCount = 0;
		if (!ObjectsUtil.isNullOrEmpty(rjeList)) {
			totalCount = totalCount + rjeList.size();
		}
		if (!ObjectsUtil.isNullOrEmpty(vrceListNew)) {
			totalCount = totalCount + vrceListNew.size();
		}
		return totalCount;
	}

	@Override
	@Transactional
	public Boolean doNotRequireSecondVehicle(Long vehicleRcId) {

		VehicleRCEntity vehicleRc = vehicleDAO.get(vehicleRcId);
		if (vehicleRc == null)
			throw new IllegalArgumentException("invalid vehicleRc Id");
		if (vehicleRc.getOwnershipType() == OwnershipType.COMPANY.getId()
				|| vehicleRc.getOwnershipType() == OwnershipType.POLICE.getId()
				|| vehicleRc.getOwnershipType() == OwnershipType.STU_VEHICLES.getId()
				|| vehicleRc.getOwnershipType() == OwnershipType.ORGANIZATION.getId())
			return true;
		VehicleDetailsEntity vehicleDtls = vehicleDetailsDao.getByVehicleRcId(vehicleRcId);

		/*
		 * // This will be notnull if subclass is among(MMCN,MCYN,OBPN,MCRN),
		 * and that vehicle is not second vehicle if (vehicleDtls!= null &&
		 * VehicleSubClassType.getVehicleSubClass(vehicleDtls.getVehicleSubClass
		 * ()) != null) return true;
		 */

		// This will be notnull if subclass is among(MMCN,MCYN,OBPN,MCRN), and
		// that vehicle is required for second vehicle
		if (vehicleDtls != null && VehicleSubClassType.getVehicleSubClass(vehicleDtls.getVehicleSubClass()) == null)
			return true;

		if (vehicleRc.getTrStatus() == Status.APPROVED.getValue()) {
			LifeTaxEntity lifeTaxEntity = lifeTaxDAO.getEntityByVehicleRcId(vehicleRcId);
			if (lifeTaxEntity == null)
				throw new IllegalArgumentException("No Tax details for vehilceRc");
			if (lifeTaxEntity.getTaxPercent() >= taxPerc)
				return true;
		}
		CustIndividualDetailsEntity custIndDtlEntity = customerDAO.getByVehicleRcId(vehicleRcId);
		if (ObjectsUtil.isNull(custIndDtlEntity)) {
			throw new IllegalArgumentException("Customer Details not found for provided vehicleRcId..");
		}
		return custIndDtlEntity.getIsSecondVehicle();
	}

	@Override
	@Transactional
	public CustMsgModel getCustDtlsForVehicleRCPR(Long vehicleRcId, NotificationType notificationType) {
		VehicleRCEntity vehicleEntity = vehicleDAO.get(vehicleRcId);
		CustIndividualDetailsEntity custDtlsEntity = null;
		CustCorporateDetailsEntity custCorporateDetailsEntity = null;
		CustMsgModel custMsgModel = null;
		if (vehicleEntity == null)
			throw new IllegalArgumentException("No vehicle with vehicleRcId " + vehicleRcId + " Exist");
		if (vehicleEntity.getOwnershipType() == null)
			throw new IllegalArgumentException("No customerId found");

		if (vehicleEntity.getOwnershipType() == OwnershipType.INDIVIDUAL.getId()) {
			custDtlsEntity = customerDAO.getByVehicleRcId(vehicleRcId);
			if (custDtlsEntity == null) {
				throw new IllegalArgumentException(
						"No customer is regestered with vehicleRcId " + vehicleRcId + " Exist");
			}
			custMsgModel = new CustMsgModel();
			custMsgModel.setCustName(custDtlsEntity.getDisplayName());
			custMsgModel.setOwnershipType(OwnershipType.INDIVIDUAL.getId());
			// TODO : message content changes
			custMsgModel
					.setMailContent(getMailContent(custDtlsEntity.getDisplayName(), vehicleEntity, notificationType));
			custMsgModel.setBcc("");
			custMsgModel.setCc(vehicleEntity.getUserId().getEmail());
			custMsgModel.setSubject(getMailSubject(notificationType, vehicleEntity));
			custMsgModel.setTo(custDtlsEntity.getEmailId());
			custMsgModel.setMobileNo(custDtlsEntity.getMobileNo());
			custMsgModel.setSmsMsg(getMsgContent(custDtlsEntity.getDisplayName(), vehicleEntity, notificationType));
		} else if (vehicleEntity.getOwnershipType() == OwnershipType.COMPANY.getId()
				|| vehicleEntity.getOwnershipType() == OwnershipType.POLICE.getId()
				|| vehicleEntity.getOwnershipType() == OwnershipType.STU_VEHICLES.getId()
				|| vehicleEntity.getOwnershipType() == OwnershipType.ORGANIZATION.getId()) {
			custCorporateDetailsEntity = customerCorporateDao.getByVehicleRcId(vehicleRcId);
			if (custCorporateDetailsEntity == null) {
				throw new IllegalArgumentException(
						"No customer is regestered with vehicleRcId " + vehicleRcId + " Exist");
			}
			custMsgModel = new CustMsgModel();
			custMsgModel.setCustName(custCorporateDetailsEntity.getDisplayName());
			if (custCorporateDetailsEntity.getCompanyName().equalsIgnoreCase(OwnershipType.COMPANY.getLabel())) {
				custMsgModel.setOwnershipType(OwnershipType.COMPANY.getId());
			} else if (custCorporateDetailsEntity.getCompanyName().equalsIgnoreCase(OwnershipType.POLICE.getLabel())) {
				custMsgModel.setOwnershipType(OwnershipType.POLICE.getId());
			} else if (custCorporateDetailsEntity.getCompanyName().equalsIgnoreCase(OwnershipType.STU_VEHICLES.getLabel())) {
				custMsgModel.setOwnershipType(OwnershipType.STU_VEHICLES.getId());
			} else if (custCorporateDetailsEntity.getCompanyName().equalsIgnoreCase(OwnershipType.ORGANIZATION.getLabel())) {
				custMsgModel.setOwnershipType(OwnershipType.ORGANIZATION.getId());
			}
			custMsgModel.setMailContent(
					getMailContent(custCorporateDetailsEntity.getDisplayName(), vehicleEntity, notificationType));
			custMsgModel.setBcc("");
			custMsgModel.setCc(vehicleEntity.getUserId().getEmail());
			custMsgModel.setSubject(getMailSubject(notificationType, vehicleEntity));
			custMsgModel.setTo(custCorporateDetailsEntity.getEmail());
			custMsgModel.setMobileNo(custCorporateDetailsEntity.getMobile());
			custMsgModel.setSmsMsg(
					getMsgContent(custCorporateDetailsEntity.getDisplayName(), vehicleEntity, notificationType));
		}

		if (StringsUtil.isNullOrEmpty(custMsgModel.getCustName()))
			log.info("ERROR ::::::: Customer DisplayName  empty");
		if (StringsUtil.isNullOrEmpty(custMsgModel.getTo()))
			log.info("ERROR ::::::: Customer EmailID empty");
		if (StringsUtil.isNullOrEmpty(custMsgModel.getSubject()))
			log.info("ERROR ::::::: Customer Subject for email empty");
		if (StringsUtil.isNullOrEmpty(custMsgModel.getMobileNo()))
			log.info("ERROR ::::::: Customer MobileNo empty");
		if (StringsUtil.isNullOrEmpty(custMsgModel.getSmsMsg()))
			log.info("ERROR ::::::: Customer SMS msg empty");

		return custMsgModel;
	}

	public String getMailContent(String displayname, VehicleRCEntity vehicleEntity, NotificationType notificationType) {
		StringBuilder mailContent = new StringBuilder("");

		VehicleDetailsEntity vhcDtlsEntity = null;
		switch (notificationType) {
		case NOTIFICATION_FOR_PR_GEN:
			if (StringsUtil.isNullOrEmpty(vehicleEntity.getPrNumber()))
				throw new IllegalArgumentException("PR number not generated");
			mailContent = new StringBuilder("<table><tr><td>Dear ").append(displayname).append(",</td></tr>")
					.append("<tr><td>Your Vehicle Permanent Registration has been generated.Your PR Number is ")
					.append(vehicleEntity.getPrNumber())
					.append(".</td></tr><tr>Thank You</tr><tr>AP_Road Transport</tr></table>");
			break;
		case NOTIFICATION_FOR_APP_REJECTION:

			if (StringsUtil.isNullOrEmpty(vehicleEntity.getTrNumber()))
				throw new IllegalArgumentException("TR number not generated");

			vhcDtlsEntity = vehicleDetailsDao.getByVehicleRcId(vehicleEntity.getVehicleRcId());
			if (vhcDtlsEntity == null)
				throw new IllegalArgumentException("incorrect vehicleRc");

			mailContent = new StringBuilder("<table><tr><td>Dear ").append(displayname).append(",</td></tr>")
					.append("<tr><td>TR Number " + vehicleEntity.getTrNumber() + " registered on the name of "
							+ displayname + " has been rejected by RTO.")
					.append("Please find below details.")
					.append("</td></tr><tr>Date of TR Request: "
							+ DateUtil.getDateAsString(vehicleEntity.getTrIssueTime()) + " </tr>")
					.append("<tr>Chassis No.: " + vhcDtlsEntity.getChassisNo() + " </tr>")
					.append("<tr>Makers Class: " + vhcDtlsEntity.getMakerClass() + " </tr>")
					.append("</td></tr><tr>Thank You</tr><tr>AP_Road Transport</tr></table>");
			break;
		// for Financer fresh if owner consent is true
		// TODO : message content changes
		case NOTIFICATION_FOR_FINANCER_FRESH:
			if (StringsUtil.isNullOrEmpty(vehicleEntity.getTrNumber()))
				throw new IllegalArgumentException("TR number not generated");
			mailContent = new StringBuilder("<table><tr><td>Dear ").append(displayname).append(",</td></tr>")
					.append("<tr><td>Your Financer has revoked the request to possess the vehicle owned by you.")
					.append("</td></tr><tr>Thank You</tr><tr>AP_Road Transport</tr></table>");
			break;
		default:
			break;

		}

		return mailContent.toString();
	}

	public String getMailSubject(NotificationType notificationType, VehicleRCEntity vehicleRcEntity) {
		String mailSubject = "";
		switch (notificationType) {
		case NOTIFICATION_FOR_PR_GEN:
			mailSubject = "AP_RTD_Permanent Registration Number (" + vehicleRcEntity.getPrNumber() + ")";
			break;
		case NOTIFICATION_FOR_APP_REJECTION:
			mailSubject = "AP_RTD_" + vehicleRcEntity.getTrNumber() + " rejected by RTO";
			break;
		// TODO : message content changes
		case NOTIFICATION_FOR_FINANCER_FRESH:
			mailSubject = "Financer raised issue for : " + (!vehicleRcEntity.getPrNumber().isEmpty()
					? vehicleRcEntity.getPrNumber() : vehicleRcEntity.getTrNumber());
			break;
		default:
			break;
		}
		return mailSubject;
	}

	public String getMsgContent(String displayName, VehicleRCEntity vehicleEntity, NotificationType notificationType) {
		String msgContent = "";
		switch (notificationType) {
		case NOTIFICATION_FOR_PR_GEN:
			if (StringsUtil.isNullOrEmpty(vehicleEntity.getPrNumber()))
				throw new IllegalArgumentException("PR number not generated");
			msgContent = "Dear  " + displayName
					+ " your Vehicle Permanent Registration has been generated.PR Number for the Vehicle is "
					+ vehicleEntity.getPrNumber() + " .Soft copy of PR will be sent to your registered email address";

			break;
		case NOTIFICATION_FOR_APP_REJECTION:
			if (StringsUtil.isNullOrEmpty(vehicleEntity.getTrNumber()))
				throw new IllegalArgumentException("TR number not generated");
			msgContent = "Dear  " + displayName + " , TR Number " + vehicleEntity.getTrNumber()
					+ " registered on the name of " + displayName + " has been rejected by RTO.";
			break;

		case NOTIFICATION_FOR_UPLOAD_CONSENT:
			msgContent = "Dear  " + displayName
					+ " , Your consent for uploading of documents for Vehicle Registration has been saved.";
			break;
		// TODO : message content changes
		case NOTIFICATION_FOR_FINANCER_FRESH:
			msgContent = "Dear  " + displayName + " , Your financer has raised issue for the vehicle owned by you.";
			break;
		}

		return msgContent;
	}

	/*
	 * @Override
	 * 
	 * @Transactional public String getPrSeriesNo(Long vehicleRcId) {
	 * RtaOfficeEntity userEntity =
	 * vehicleDAO.get(vehicleRcId).getRtaOfficeId(); try { return
	 * getPrSeriesNo(userEntity); } catch (Exception e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } return null; }
	 */

	@Transactional
	@Override
	public String getPrSeriesNo(VehicleRCEntity vehicleRCEntity, RegistrationCategoryType regType) throws NotFoundException {
		RtaOfficeEntity rtaOfficeEntity = null;
		if(ObjectsUtil.isNull(regType)){
			regType = RegistrationCategoryType.getRegistrationCategoryType(vehicleRCEntity.getRegCategory().getRegistrationCategoryId());
		}
		switch (regType) {
		case NON_TRANSPORT:
			rtaOfficeEntity = vehicleRCEntity.getRtaOfficeId();
			break;
		case TRANSPORT:
			if (OfficeType.RTA.getLabel().equalsIgnoreCase(vehicleRCEntity.getRtaOfficeId().getOfficeType())) {
				rtaOfficeEntity = vehicleRCEntity.getRtaOfficeId();
			} else {
				if (vehicleRCEntity.getRtaOfficeId().getCode()
						.equalsIgnoreCase(vehicleRCEntity.getRtaOfficeId().getParentOfficeCode())) {
					rtaOfficeEntity = new RtaOfficeEntity();
					rtaOfficeEntity = vehicleRCEntity.getRtaOfficeId();
				} else {
					rtaOfficeEntity = rtaOfficeDAO
							.getRtaOfficeDetailsByCode(vehicleRCEntity.getRtaOfficeId().getParentOfficeCode());
				}
			}
			break;
		}

		PrSeriesMasterEntity presentSeries = prSeriesMasterDAO.getByRTAOffice(rtaOfficeEntity, vehicleRCEntity, regType);
		Integer useNumber = presentSeries.getUsedNumber();
		useNumber = getNonSpecialNo(useNumber);
		PrSeriesMasterEntity activeSeries = presentSeries;
		Integer threshold = MAX_ALLOWED_SERIES_NUMBER - prSeriesNotificationThreshold;
		if ((useNumber >= threshold) && (useNumber % prSeriesNotificationInterval == 0)) {
			List<PrSeriesMasterEntity> freshSeries = prSeriesMasterDAO.getFuturePrSeries(rtaOfficeEntity,
					vehicleRCEntity, regType);
			boolean hasFutureSeries = true;
			if (ObjectsUtil.isNullOrEmpty(freshSeries)) {
				log.warn("pr-series about to end for RTA : " + rtaOfficeEntity.getCode() + ", current used number : "
						+ useNumber);
				CustMsgModel csm = new CustMsgModel();
				csm.setSubject(prSubject);
				csm.setTo(prSeriesReceiver);
				csm.setCc(prSeriesCC);
				String template = prNotificationMailContent;
				StringBuilder sb = new StringBuilder(prNotificationMailContent);
				String rtaOfficeCodeKey = "#code";
				String usedNumberKey = "#number";
				sb.replace(template.indexOf(rtaOfficeCodeKey),
						template.indexOf(rtaOfficeCodeKey) + rtaOfficeCodeKey.length(), rtaOfficeEntity.getCode());
				sb.replace(template.indexOf(usedNumberKey), template.indexOf(usedNumberKey) + usedNumberKey.length(),
						String.valueOf(useNumber));
				csm.setMailContent(sb.toString());
				messagingService.sendEmail(csm);
				hasFutureSeries = false;
			}
			if (checkSeriesExpiration(useNumber)) {
				if (hasFutureSeries) {
					PrSeriesMasterEntity presentSeriesNew = freshSeries.get(0);
					presentSeriesNew.setStatus(Status.PRESENT.getValue());
					useNumber = presentSeriesNew.getUsedNumber();
					useNumber = getNonSpecialNo(useNumber);
					prSeriesMasterDAO.update(presentSeriesNew);
					presentSeries.setStatus(Status.PAST.getValue());
					activeSeries = presentSeriesNew;
				} else {
					log.error("no new pr series found for RTA  : " + rtaOfficeEntity.getCode());
					throw new NotFoundException("pr-series not found");
				}
			}
		}
		String formattedString = NumberParser.getFormatedNumber(useNumber, "xxxx", 'x', '0');
		String number = activeSeries.getSeries() + formattedString;
		activeSeries.setUsedNumber(1 + useNumber);
		prSeriesMasterDAO.update(activeSeries);
		return number;
	}

	private boolean checkSeriesExpiration(Integer useNumber) {
		return useNumber.intValue() > MAX_ALLOWED_SERIES_NUMBER;
	}

	private Integer getNonSpecialNo(int number) {
		List<Integer> specialNoList = specialNoDAO.getAllSpecialNo();
		while (specialNoList.contains(number))
			number = number + 1;
		return number;
	}

	@Transactional
	public Map<UserType, Status> getStatusOfUsers(Long vehicleRcId, UserType userType) {
		Map<UserType, Status> appStatus4Usertype = new HashMap<>();
		Map<UserType, Status> recentUserStatus = new HashMap<>();
		VehicleRCEntity vehicleEntity = vehicleDAO.get(vehicleRcId);
		if (vehicleEntity == null) {
			throw new IllegalArgumentException("Invalid vehicleRcId!");
		}

		recentUserStatus.put(UserType.ROLE_CCO, (vehicleEntity.getCcoActionStatus() == null) ? Status.PENDING
				: Status.getStatus(vehicleEntity.getCcoActionStatus()));
		recentUserStatus.put(UserType.ROLE_MVI, (vehicleEntity.getMviActionStatus() == null) ? Status.PENDING
				: Status.getStatus(vehicleEntity.getMviActionStatus()));
		recentUserStatus.put(UserType.ROLE_AO, (vehicleEntity.getAoActionStatus() == null) ? Status.PENDING
				: Status.getStatus(vehicleEntity.getAoActionStatus()));
		recentUserStatus.put(UserType.ROLE_RTO, (vehicleEntity.getRtoActionStatus() == null) ? Status.PENDING
				: Status.getStatus(vehicleEntity.getRtoActionStatus()));

		/*
		 * List<VehicleRCHistoryEntity> vehicleHistoryLst =
		 * vehicleRcHistoryDAO.getHistryOfVehicleRcId(vehicleRcId,
		 * vehicleEntity.getIteration()); if (vehicleHistoryLst != null) for
		 * (VehicleRCHistoryEntity vehicleHist : vehicleHistoryLst) { if
		 * (!latestActionByUser.containsKey(vehicleHist.getRtaEmployeeType())) {
		 * latestActionByUser.put(vehicleHist.getRtaEmployeeType(),
		 * vehicleHist); recentUserStatus.put(UserType.getUserType(vehicleHist.
		 * getRtaEmployeeType()), Status.getStatus(vehicleHist.getStatus())); }
		 * 
		 * else if (latestActionByUser.get(vehicleHist.getRtaEmployeeType()).
		 * getModifiedOn() < vehicleHist .getModifiedOn()) {
		 * latestActionByUser.put(vehicleHist.getRtaEmployeeType(),
		 * vehicleHist); recentUserStatus.put(UserType.getUserType(vehicleHist.
		 * getRtaEmployeeType()), Status.getStatus(vehicleHist.getStatus())); }
		 * }
		 */

		switch (userType) {
		case ROLE_DEALER:

		case ROLE_RTO:
			appStatus4Usertype.put(UserType.ROLE_RTO, recentUserStatus.containsKey(UserType.ROLE_RTO)
					? recentUserStatus.get(UserType.ROLE_RTO) : Status.PENDING);
			appStatus4Usertype.put(UserType.ROLE_AO, recentUserStatus.containsKey(UserType.ROLE_AO)
					? recentUserStatus.get(UserType.ROLE_AO) : Status.PENDING);
			appStatus4Usertype.put(UserType.ROLE_CCO, recentUserStatus.containsKey(UserType.ROLE_CCO)
					? recentUserStatus.get(UserType.ROLE_CCO) : Status.PENDING);
			appStatus4Usertype.put(UserType.ROLE_MVI, recentUserStatus.containsKey(UserType.ROLE_MVI)
					? recentUserStatus.get(UserType.ROLE_MVI) : Status.PENDING);
			break;
		case ROLE_AO:
			appStatus4Usertype.put(UserType.ROLE_AO, recentUserStatus.containsKey(UserType.ROLE_AO)
					? recentUserStatus.get(UserType.ROLE_AO) : Status.PENDING);
			appStatus4Usertype.put(UserType.ROLE_CCO, recentUserStatus.containsKey(UserType.ROLE_CCO)
					? recentUserStatus.get(UserType.ROLE_CCO) : Status.PENDING);
			appStatus4Usertype.put(UserType.ROLE_MVI, recentUserStatus.containsKey(UserType.ROLE_MVI)
					? recentUserStatus.get(UserType.ROLE_MVI) : Status.PENDING);
			break;
		}

		return appStatus4Usertype;

	}

	@Override
	@Transactional
	public ApplicationModel applicationForVehicleRc(Long vehicleRcId) {

		VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);
		if (vehicleRCEntity == null)
			throw new IllegalArgumentException("vehicle with this Id doesnt exist");
		return toApplicationModel(vehicleRCEntity);
	}

	@Override
	@Transactional
	public ApplicationModel applicationForVehicleRc(Long vehicleRcId, UserType userType) {
		ApplicationModel am = applicationForVehicleRc(vehicleRcId);
		if (userType == UserType.ROLE_AO || userType == UserType.ROLE_RTO) {
			// check only for applications with PRType = SPECIAL
			if (am.getPrType() == PRType.SPECIAL) {
				am.setCanBeApproved(matchPRGenerationRule(am.getVehicleRcId(), userType));
			}
		}
		return am;
	}

	@Transactional
	public String getEmployeeSignFileName(Long vehicleRcId) {
		log.info("::getEmployeeSignFileName:::start:");
		if (vehicleRcId == null)
			throw new IllegalArgumentException("Invalid vehicleRcId");

		VehicleRCEntity vehicleRcEntity = vehicleDAO.get(vehicleRcId);
		if (vehicleRcEntity == null)
			throw new IllegalArgumentException("Invalid vehicleRcId");

		String signature = null;
		//---- get rto officer signature based on vehicle Rc table----------------------------
		if(ObjectsUtil.isNotNull(vehicleRcEntity.getRtoUserId()) && vehicleRcEntity.getRtoUserId().getStatus() 
				&& ObjectsUtil.isNotNull(vehicleRcEntity.getRtoActionStatus()) && Status.getStatus(vehicleRcEntity.getRtoActionStatus()) == Status.APPROVED){
			RTAEmployeeEntity rtaEmployee = rtaEmployeeDAO.getByUserId(vehicleRcEntity.getRtoUserId().getUserId());
			if (!(rtaEmployee == null || StringsUtil.isNullOrEmpty(rtaEmployee.getSignFileName()))){
				signature = rtaEmployee.getSignFileName();
			}
		}
		// ---------if rto signature not found get ao signature based on Rc table--------------
		if(StringsUtil.isNullOrEmpty(signature) && ObjectsUtil.isNotNull(vehicleRcEntity.getAoUserId()) && vehicleRcEntity.getAoUserId().getStatus() 
				&& ObjectsUtil.isNotNull(vehicleRcEntity.getAoActionStatus()) && Status.getStatus(vehicleRcEntity.getAoActionStatus()) == Status.APPROVED){
			RTAEmployeeEntity rtaEmployee = rtaEmployeeDAO.getByUserId(vehicleRcEntity.getAoUserId().getUserId());
			if (!(rtaEmployee == null || StringsUtil.isNullOrEmpty(rtaEmployee.getSignFileName()))){
				signature = rtaEmployee.getSignFileName();
			}
		}
		//------- if still signature not found get user id from history table and get signature of that...........
		if(StringsUtil.isNullOrEmpty(signature)){
			List<VehicleRCHistoryEntity> vehiceRcHistEntities = vehicleRcHistoryDAO.getHistryOfVehicleRcId(vehicleRcId,
					vehicleRcEntity.getIteration());

			if (vehiceRcHistEntities == null || vehiceRcHistEntities.size() <= 0)
				throw new IllegalArgumentException("No Actions performed on vehicleRcId" + vehicleRcId);

			VehicleRCHistoryEntity lastActionOnVhcl = vehiceRcHistEntities.get(0);
			if (lastActionOnVhcl.getStatus() != Status.APPROVED.getValue())
				throw new IllegalArgumentException("vehicleRcId " + vehicleRcId + " is not approved as yet");

			Long userId = lastActionOnVhcl.getUserEntity().getUserId();
			RTAEmployeeEntity rtaEmployee = rtaEmployeeDAO.getByUserId(userId);
			if (rtaEmployee == null || StringsUtil.isNullOrEmpty(rtaEmployee.getSignFileName()))
				throw new IllegalArgumentException("Employee Signature File Not found.");
			else
				signature = rtaEmployee.getSignFileName();
		}
		
		return signature;
	}

	@Transactional
	public Boolean saveEventLogForAttachments(int notificationType, Long vehicleRcId, String attachment) {

		EventLogEntity eventLog = eventDAO.getVehicleRcsInLog(vehicleRcId);
		VehicleRCEntity vehicle = vehicleDAO.get(vehicleRcId);
		if (vehicle == null)
			throw new IllegalArgumentException("Invalid vehicle");
		if (eventLog == null) {
			eventLog = new EventLogEntity();
			eventLog.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		}
		eventLog.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		attachment = attachment.replace("\"", "");

		eventLog.setVehicleRcId(vehicleRcId);
		if (notificationType == NotificationType.NOTIFICATION_FOR_PR_GEN.getValue())
			eventLog.setPrAttachement(attachment);
		if (notificationType == NotificationType.NOTIFICATION_FOR_TR_GEN.getValue())
			eventLog.setTrAttachement(attachment);
		eventDAO.saveOrUpdate(eventLog);
		return true;
	}

	@Override
	@Transactional
	public Map<String, Object> getApplicationsProcessedByUser(Long userId, String query, Long from, Long to,
			Integer perPageRecords, Integer pageNumber, Status status, UserType userType, String regCategory) {
		Map<String, Object> map = new HashMap<>();
		List<LogsModel> appList = new ArrayList<LogsModel>();
		Long totalRecords = Long.valueOf(0L);
		List<VehicleRCHistoryEntity> vehicleRcHistoryEntity = vehicleRcHistoryDAO.getApplicationWithUserStatus(userId,
				status, query, from, to, perPageRecords, pageNumber, getRegistrationCategoryId(regCategory));
		totalRecords = vehicleRcHistoryDAO.countApplicationsWithUserStatus(userId, status, query, from, to,
				perPageRecords, pageNumber, false, getRegistrationCategoryId(regCategory));
		if (!ObjectsUtil.isNullOrEmpty(vehicleRcHistoryEntity)) {
			for (VehicleRCHistoryEntity rejectionHistoryEntity : vehicleRcHistoryEntity) {
				LogsModel am = new LogsModel();
				am.setIteration(rejectionHistoryEntity.getIteration());
				am.setComment(rejectionHistoryEntity.getComment());
				am.setVehicleRCId(rejectionHistoryEntity.getVehicleRc().getVehicleRcId());
				am.setModifiedOn(rejectionHistoryEntity.getModifiedOn());
				appList.add(am);
			}
		}
		map.put("logs", appList);
		map.put("totalRecords", totalRecords);
		return map;
	}

	private void cancelApplication(Long userId, Long vehicleRcId)
			throws VehicleRcNotFoundException, InvalidStatusException {
		// Get application
		Long currentTimeStamp = DateUtil.toCurrentUTCTimeStamp();
		VehicleRCEntity vehicleRcEntity = vehicleDAO.get(vehicleRcId);
		if (ObjectsUtil.isNull(vehicleRcEntity)) {
			log.error("vehicle rc id not present : " + vehicleRcId);
			throw new VehicleRcNotFoundException("vehicle rc id not present");
		}
		// If TR approved then can't cancel
		if (Status.getStatus(vehicleRcEntity.getTrStatus()) == Status.APPROVED) {
			log.error("Can't cancel TR approved application");
			throw new InvalidStatusException("Can't cancel TR approved application");
		}

		// Cancel application
		// only PENDING applications can be cancelled
		if (Status.getStatus(vehicleRcEntity.getTrStatus()) == Status.PENDING) {
			vehicleRcEntity.setPrStatus(Status.CANCELLED.getValue());
			vehicleRcEntity.setTrStatus(Status.CANCELLED.getValue());
			UserEntity userEntity = userDao.getEntity(UserEntity.class, userId);
			if (!ObjectsUtil.isNull(userEntity)) {
				vehicleRcEntity.setModifiedBy(userEntity.getUserName());
				vehicleRcEntity.setModifiedOn(currentTimeStamp);
			}
			String postfix = getCancelPostfix(currentTimeStamp, String.valueOf(vehicleRcEntity.getVehicleRcId()));
			String cancelledChassisNumber = makeCancelledString(vehicleRcEntity.getChassisNumber(), postfix);
			vehicleRcEntity.setChassisNumber(cancelledChassisNumber);
			vehicleDAO.update(vehicleRcEntity);
			VehicleDetailsEntity vehicleDetailsEntity = vehicleDetailsDao.getByVehicleRcId(vehicleRcId);
			if (!ObjectsUtil.isNull(vehicleDetailsEntity)) {
				vehicleDetailsEntity.setChassisNo(cancelledChassisNumber);
				vehicleDetailsEntity.setEngineNo(makeCancelledString(vehicleDetailsEntity.getEngineNo(), postfix));
				if (!ObjectsUtil.isNull(userEntity)) {
					vehicleDetailsEntity.setModifiedBy(userEntity.getUserName());
					vehicleDetailsEntity.setModifiedOn(currentTimeStamp);
				}
				vehicleDetailsDao.update(vehicleDetailsEntity);
				// policy number can't be same next time so need to update
				// policy number
				InsuranceDetailsEntity insuranceDetailsEntity = insuranceDao
						.getInsuranceDtlsEntityByVehicleRcId(vehicleRcId);
				if (!ObjectsUtil.isNull(insuranceDetailsEntity)) {
					insuranceDetailsEntity
							.setPolicyNo(makeCancelledString(insuranceDetailsEntity.getPolicyNo(), postfix));
					if (!ObjectsUtil.isNull(userEntity)) {
						insuranceDetailsEntity.setModifiedBy(userEntity.getUserName());
						insuranceDetailsEntity.setModifiedOn(currentTimeStamp);
					}
					insuranceDao.update(insuranceDetailsEntity);
				}
			}

			// canceling finance for vehicleRcId
			log.info("canceling all finance info for vehicleRcId : " + vehicleRcId);
			List<VehicleFinanceDtlstEntity> vfdeList = vehicleFinanceDetailsDAO.getByVehicleRcId(vehicleRcId);
			if (!ObjectsUtil.isNullOrEmpty(vfdeList)) {
				for (VehicleFinanceDtlstEntity vfde : vfdeList) {
					vfde.setRtoApproved(Status.CANCELLED.getValue());
					vehicleFinanceDetailsDAO.update(vfde);
					log.info("cancelled vehicle_finance_details");
				}
			}
			List<FinanceTokenEntity> financeTokenEntityList = financeTokenDAO.getFinanceTokenByVehicleRcId(vehicleRcId);
			if (!ObjectsUtil.isNullOrEmpty(financeTokenEntityList)) {
				for (FinanceTokenEntity financeTokenEntity : financeTokenEntityList) {
					List<FinancerAppStatusEntity> finAppStatusList = financerAppStatusDAO
							.getAppStatusForTokens(financeTokenEntity.getTokenId());
					if (!ObjectsUtil.isNullOrEmpty(finAppStatusList)) {
						for (FinancerAppStatusEntity finAppStatus : finAppStatusList) {
							finAppStatus.setFinanceStatus(Status.CANCELLED.getValue());
							financerAppStatusDAO.update(finAppStatus);
							log.info("cancelled financer_app_status ");
						}
					}
					financeTokenEntity.setAppStatus(Status.CANCELLED.getValue());
					financeTokenDAO.update(financeTokenEntity);
					log.info("cancelled finance_token ");
				}
			}
			return;
		}
		log.error("Application " + vehicleRcId + " is not pending or already cancelled!!");
		throw new InvalidStatusException("Application is not pending or already cancelled!!");
	}

	/**
	 * pattern : _CANCELLED_currentTimestamp </br>
	 * e.g., _CANCELLED_145784595
	 * 
	 * @return String
	 */
	private static String getCancelPostfix(Long timestamp, String vehicleRcId) {
		return new StringBuilder(vehicleRcId).append("_").append(Status.CANCELLED).append("_").append(timestamp).toString();
	}

	private static String makeCancelledString(String str, String postfix) {
		return new StringBuilder(str).append(postfix).toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Object> getPrPendingApplications(Long userId, UserType userType, String trNumber,
			Integer perPageRecords, Integer pageNumber, String regCategory) throws Exception {
		Map<String, Object> mapObject = new HashMap<String, Object>();
		log.debug("::::::::::::::::::::::::getPrPendingApplications ::::::::::::: start::::::::::" + userId);
		Map<String, Object> map = vehicleDAO.getPrPendingApplications(userId, userType, trNumber, perPageRecords,
				pageNumber, getRegistrationCategoryId(regCategory));
		log.debug("::::::::::::::::::::::::getPrPendingApplications ::::::::::::: middle::::::::::" + userId);
		List<VehicleRCEntity> VehicleRCEntities = (List<VehicleRCEntity>) map.get("vehicleRCEntity");
		SpecialNumberApplicationModel model = null;
		List<SpecialNumberApplicationModel> applicationList = new ArrayList<SpecialNumberApplicationModel>();

		for (VehicleRCEntity vehicleRCEntity : VehicleRCEntities) {
			model = new SpecialNumberApplicationModel();
			model.setVehicleRcId(vehicleRCEntity.getVehicleRcId());
			model.setTrNumber(vehicleRCEntity.getTrNumber());
			if (OwnershipType.INDIVIDUAL.getId() == vehicleRCEntity.getOwnershipType()) {
				CustIndividualDetailsEntity cide = customerDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
				model.setCustomerName(cide.getFirstName() + " " + cide.getSurName());
				model.setCustomerMobile(cide.getMobileNo());
			} else if (OwnershipType.COMPANY.getId() == vehicleRCEntity.getOwnershipType()
					|| OwnershipType.POLICE.getId() == vehicleRCEntity.getOwnershipType()
					|| OwnershipType.STU_VEHICLES.getId() == vehicleRCEntity.getOwnershipType()
					|| OwnershipType.ORGANIZATION.getId() == vehicleRCEntity.getOwnershipType()) {
				CustCorporateDetailsEntity ccde = customerCorporateDao
						.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
				model.setCustomerName(ccde.getCompanyName());
				model.setCustomerMobile(ccde.getMobile());
			}
			applicationList.add(model);
		}
		mapObject.put("applicationModels", applicationList);
		mapObject.put("totalApplictions", map.get("totalRecords"));
		log.debug("::::::::::::::::::::::::getPrPendingApplications ::::::::::::: end ::::::::::" + userId);
		return mapObject;
	}

	@Override
	@Transactional
	public Integer getPrPendingApplicationsCount(Long userId, UserType userType, String regCategory) throws Exception {
		log.debug("::::::::::::::::::::::::getPrPendingApplicationsCount ::::::::::::: start::::::::::" + userId);
		Integer count = vehicleDAO.getPrPendingApplicationsCount(userId, userType,
				getRegistrationCategoryId(regCategory));

		log.debug("::::::::::::::::::::::::getPrPendingApplicationsCount ::::::::::::: end ::::::::::" + userId);
		return count;
	}

	@Override
	@Transactional
	public Boolean isValidCallerForVehicleRc(Long vehiclercId, Long userId) {
		/*
		 * DealerEntity dealerEntity=dealerDAO.findByUserId(userId);
		 * if(dealerEntity==null) throw new
		 * IllegalArgumentException("No dealer with this ID exists!!!");
		 */
		return ObjectsUtil.isNull(vehicleDAO.vehicleBelongToUserOrItsChild(vehiclercId, userId)) ? Boolean.FALSE
				: Boolean.TRUE;
	}

	@Override
	@Transactional
	public Long getPROrTRDetails(String number) throws NotFoundException {
		VehicleRCEntity vehicleRcEntity = vehicleDAO.getVehicleRCByPRNumber(number);
		if (vehicleRcEntity == null)
			vehicleRcEntity = vehicleDAO.getVehicleRCByTRNumber(number);
		if (ObjectsUtil.isNull(vehicleRcEntity)) {
			log.debug("Neither Pr or Tr found : " + number);
			throw new NotFoundException("Neither Pr or Tr found");
		}
		return vehicleRcEntity.getVehicleRcId();
	}

	@Override
	@Transactional
	public ApplicationModel getPRDetails(String prNumber) throws NotFoundException {
		VehicleRCEntity vehicleRcEntity = vehicleDAO.getVehicleRCByPRNumber(prNumber);
		if (ObjectsUtil.isNull(vehicleRcEntity)) {
			log.debug("pr number not found : " + prNumber);
			throw new NotFoundException("pr number not found");
		}
		return toApplicationModel(vehicleRcEntity);
	}

	@Override
	@Transactional
	public ApplicationModel getTRDetails(String trNumber) throws NotFoundException {
		VehicleRCEntity vehicleRcEntity = vehicleDAO.getVehicleRCByTRNumber(trNumber);
		if (ObjectsUtil.isNull(vehicleRcEntity)) {
			log.debug("tr number not found : " + trNumber);
			throw new NotFoundException("tr number not found");
		}
		return toApplicationModel(vehicleRcEntity);
	}

	@Override
	@Transactional
	public SaveUpdateResponse saveApplicationSlots(CitizenApplicationModel citizenApplicationModel)
			throws NotFoundException {
		if (citizenApplicationModel.getKeyType() == KeyType.TR) {
			VehicleRCEntity vehicleRcEntity = vehicleDAO.getVehicleRCByTRNumber(citizenApplicationModel.getUniqueKey());
			if (ObjectsUtil.isNull(vehicleRcEntity)) {
				log.debug("tr number not found : " + citizenApplicationModel.getUniqueKey());
				throw new NotFoundException("tr number not found");
			}
			Long currentTime = DateUtil.toCurrentUTCTimeStamp();
			List<SlotModel> applicationSlots = citizenApplicationModel.getSlot();
			if (!ObjectsUtil.isNullOrEmpty(applicationSlots)) {
				applicationSlots.stream().forEach(appSlot -> {
					ApplicationSlotsEntity applicationSlotsEntity = new ApplicationSlotsEntity();
					applicationSlotsEntity.setApplicationNumber(citizenApplicationModel.getApplicationNumber());
					applicationSlotsEntity.setApplicationSlotId(appSlot.getSlotId());
					applicationSlotsEntity.setCreatedBy(vehicleRcEntity.getAadharNo());
					applicationSlotsEntity.setCreatedOn(currentTime);
					applicationSlotsEntity.setEndTime(appSlot.getEndTime());
					applicationSlotsEntity.setIsEnabled(Boolean.TRUE);
					applicationSlotsEntity.setModifiedBy(vehicleRcEntity.getAadharNo());
					applicationSlotsEntity.setModifiedOn(currentTime);
					applicationSlotsEntity.setScheduledDate(appSlot.getScheduledDate());
					applicationSlotsEntity.setScheduledTime(appSlot.getScheduledTime());
					// applicationSlotsEntity.setServiceCode(appSlot.get);
					applicationSlotsEntity.setSlotId(appSlot.getSlotId());
					applicationSlotsEntity.setSlotServiceType(appSlot.getType());
					applicationSlotsEntity.setStartTime(appSlot.getStartTime());
					applicationSlotsEntity.setSlotStatus(appSlot.getSlotStatus());
					applicationSlotsEntity.setVehicleRc(vehicleRcEntity);
					applicationSlotsEntity.setServiceCode(citizenApplicationModel.getServiceType().getCode());
					applicationSlotsDAO.save(applicationSlotsEntity);
				});
			}
			vehicleRcEntity.setProcessStatus(Status.PENDING.getValue());
			
	        VehicleDetailsEntity vehicleDetails = vehicleDetailsDao.getByVehicleRcId(vehicleRcEntity.getVehicleRcId());
	        if (!ObjectsUtil.isNull(vehicleDetails) && vehicleDetails.getVehicleCategory() == VehicleCategory.CHASSIS_ONLY) {
	            vehicleRcEntity.setCcoActionStatus(Status.PENDING.getValue());
	            vehicleRcEntity.setMviActionStatus(Status.PENDING.getValue());
	            vehicleRcEntity.setAoActionStatus(Status.PENDING.getValue());
	            vehicleRcEntity.setRtoActionStatus(Status.PENDING.getValue());   
	        }
			vehicleDAO.saveOrUpdate(vehicleRcEntity);
			return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Saved Successfully.",
					vehicleRcEntity.getVehicleRcId().toString());
		}
		return null;
	}

	private ApplicationModel toApplicationModel(VehicleRCEntity vehicleRCEntity) {

		ApplicationModel applModel = new ApplicationModel();
		applModel.setVehicleRcId(vehicleRCEntity.getVehicleRcId());
		applModel.setIteration(vehicleRCEntity.getIteration());
		applModel.setCurrentStep(vehicleRCEntity.getCurrentStep());
		applModel.setTrStatus(Status.getStatus(vehicleRCEntity.getTrStatus()));
		applModel.setProcessStatus(Status.getStatus(vehicleRCEntity.getProcessStatus()));
		applModel.setIncompleteData(vehicleRCEntity.isIncompleteData());
		applModel.setIncompleteReason(vehicleRCEntity.getIncompleteReason());
		Status prStatus = Status.getStatus(vehicleRCEntity.getPrStatus());
		applModel.setPrStatus(prStatus);
		SuspendedRCNumberModel suspendedPrModel = null;
		if (prStatus != null && prStatus == Status.SUSPENDED) {
			SuspendedRCNumbersEntity suspendedPrEntity = suspendedRcNumberDAO.getDetails(vehicleRCEntity.getPrNumber(),
					Boolean.FALSE, Status.SUSPENDED);
			if (suspendedPrEntity != null) {
				suspendedPrModel = new SuspendedRCNumberModel();
				suspendedPrModel.setComment(suspendedPrEntity.getComment());
				suspendedPrModel.setCreatedBy(suspendedPrEntity.getCreatedBy());
				suspendedPrModel.setEndDate(suspendedPrEntity.getEndDate());
				suspendedPrModel.setIsRevoked(suspendedPrEntity.getIsRevoked());
				suspendedPrModel.setModifiedBy(suspendedPrEntity.getModifiedBy());
				suspendedPrModel.setModifiedOn(suspendedPrEntity.getModifiedOn());
				suspendedPrModel.setPrNumber(suspendedPrEntity.getPrNumber());
				suspendedPrModel.setStartDate(suspendedPrEntity.getStartDate());
				suspendedPrModel.setStatus(suspendedPrEntity.getStatus());
				suspendedPrModel.setSuspendedRcNumbersId(suspendedPrEntity.getSuspendedRcNumbersId());
			}
		}
		applModel.setSuspensionDetails(suspendedPrModel);
		applModel.setAadharVerified(vehicleRCEntity.isAadharVerified());
		applModel.setDocUploadConsent(vehicleRCEntity.isDocUploadConsent());
		applModel.setAadharNumber(vehicleRCEntity.getAadharNo());
		CustomerDetailsRequestModel cdrm = new CustomerDetailsRequestModel();
		OwnershipType ownershipType = OwnershipType.getOwnershipType(vehicleRCEntity.getOwnershipType());
		if (ownershipType == OwnershipType.INDIVIDUAL) {
			CustIndividualDetailsEntity tAe = customerDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
			AddressEntity addressEntity = customerDAO.getAddressDetailsByUserId(tAe.getCustIndDtlId(), "T");
			cdrm.setTemp_pincode(addressEntity.getPinCode());
			cdrm.setFirst_name(tAe.getFirstName());
			cdrm.setLast_name(tAe.getSurName());
			cdrm.setTemp_mandal_name(addressEntity.getMandal().getName());
			cdrm.setTemp_district_name(addressEntity.getMandal().getDistrictEntity().getName());
			cdrm.setFather_name(tAe.getFatherName());
			cdrm.setDob(tAe.getDateOfBirth());
			cdrm.setGender(tAe.getGender());
		} else if (ownershipType == OwnershipType.COMPANY || ownershipType == OwnershipType.POLICE
				|| ownershipType == OwnershipType.STU_VEHICLES || ownershipType == OwnershipType.ORGANIZATION) {
			CustCorporateDetailsEntity tAe = customerCorporateDao.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
			AddressEntity addressEntity = customerCorporateDao.getAddressDetailsByUserId(tAe.getCustCorpDtlsId(), "T");
			cdrm.setFirst_name(tAe.getCompanyName());
			cdrm.setTemp_pincode(addressEntity.getPinCode());
			cdrm.setRepresentedBy(tAe.getRepresentedBy());
			cdrm.setTemp_mandal_name(addressEntity.getMandal().getName());
			cdrm.setTemp_district_name(addressEntity.getMandal().getDistrictEntity().getName());
			cdrm.setFather_name(null);
			cdrm.setDob(null);
			cdrm.setGender(null);
		}
		cdrm.setOwnershipType(ownershipType.getLabel());
		cdrm.setOwnershipTypeName(ownershipType.getLabel());
		RtaOfficeEntity officeEntity = vehicleRCEntity.getRtaOfficeId();
		RTAOfficeModel officeModel = rTAOfficeHelper.convertToModel(officeEntity);
		applModel.setRtaOffice(officeModel);
		applModel.setCustomerDetails(cdrm);
		if (vehicleRCEntity.getPrStatus() == null)
			applModel.setStatus(Status.PENDING);
		else
			applModel.setStatus(Status.getStatus(vehicleRCEntity.getPrStatus()));

		if (StringsUtil.isNullOrEmpty(vehicleRCEntity.getTrNumber()))
			applModel.setTrNumber("PENDING");
		else
			applModel.setTrNumber(vehicleRCEntity.getTrNumber());

		if (StringsUtil.isNullOrEmpty(vehicleRCEntity.getPrNumber()))
			applModel.setPrNumber("PENDING");
		else
			applModel.setPrNumber(vehicleRCEntity.getPrNumber());
		applModel.setRegistrationCategory(new RegistrationCategoryModel());
		applModel.getRegistrationCategory()
				.setId(Long.valueOf(vehicleRCEntity.getRegCategory().getRegistrationCategoryId()));
		applModel.getRegistrationCategory().setCode(vehicleRCEntity.getRegCategory().getCode());
		applModel.getRegistrationCategory().setName(vehicleRCEntity.getRegCategory().getName());
		applModel.setPrType(PRType.getPRType(vehicleRCEntity.getPrType()));
		applModel.setTrIssueTime(vehicleRCEntity.getTrIssueTime());
		applModel.setPrIssueTime(vehicleRCEntity.getPrIssueTime());
		applModel.setVehicleModel(new VehicleBaseModel());
		applModel.getVehicleModel().setChassisNumber(vehicleRCEntity.getChassisNumber());
		applModel.setPrValidUpto(vehicleRCEntity.getPrExpireDate());
		VehicleDetailsEntity vehicleDetails = vehicleDetailsDao.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
		if (!ObjectsUtil.isNull(vehicleDetails)) {
			applModel.getVehicleModel().setRlw(vehicleDetails.getRlw());
			applModel.getVehicleModel().setUlw(vehicleDetails.getUlw());
			applModel.getVehicleModel().setVehicleSubClass(vehicleDetails.getVehicleSubClass());
			applModel.setVehicleCategory(vehicleDetails != null ? vehicleDetails.getVehicleCategory() : null);
			applModel.getVehicleModel().setFuelUsed(vehicleDetails.getFuelUsed());
		}

		// special number
		if (vehicleRCEntity.getPrType() != null && vehicleRCEntity.getPrType() == PRType.SPECIAL.getId()) {
			SpecialNumberRtaOfficeEntity SpecialNumberRtaOfficeEntity = specialNumberRtaOfficeDAO
					.getSpecialNumberByTrNo(vehicleRCEntity.getTrNumber());
			if (ObjectsUtil.isNotNull(SpecialNumberRtaOfficeEntity)) {
				applModel.setSpecialNumber(SpecialNumberRtaOfficeEntity.getSpecialNumber());
			} else {
				applModel.setSpecialNumber("");
			}
		}

		// fitness details
		FitnessCertificateEntity fde = fcDetailsDAO.getAnyFcDetails(vehicleRCEntity.getVehicleRcId(), "V");
		if (ObjectsUtil.isNotNull(fde)) {
			FcDetailsModel fdm = new FcDetailsModel();
			fdm.setExpiryDate(ObjectsUtil.isNotNull(fde.getExpiryDate()) ? fde.getExpiryDate().getTime() / 1000 : null);
			fdm.setFcNumber(fde.getFcNo());
			fdm.setIssueDate(ObjectsUtil.isNotNull(fde.getIssueDate()) ? fde.getIssueDate().getTime() / 1000 : null);
			fdm.setSuspended(
					fde.getStatusCode() != null && fde.getStatusCode() == Status.SUSPENDED.getValue() ? true : false);
			applModel.setFitnessCertificateDetails(fdm);
		}
		/*try {
			TaxDetailEntity taxDetailEntity = taxDetailDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
			if (taxDetailEntity != null && taxDetailEntity.getGreenTaxValidTo() != null)
				applModel.setGreenTaxValidTo(taxDetailEntity.getGreenTaxValidTo());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return applModel;
	}

	@Autowired
	private FcDetailsDAO fcDetailsDAO;

	private Integer getRegistrationCategoryId(String registrationCategoryId) {

		if (StringsUtil.isNullOrEmpty(registrationCategoryId)) {
			return null;
		}
		return registrationCategoryDAO.getByCode(registrationCategoryId) == null ? null
				: registrationCategoryDAO.getByCode(registrationCategoryId).getRegistrationCategoryId();
	}

	@Override
	@Transactional
	public void updateOpenedApplications() {
		List<VehicleRCHistoryEntity> openedApplications = vehicleRcHistoryDAO.getOpenedApplications();
		if (!ObjectsUtil.isNullOrEmpty(openedApplications)) {
			for (VehicleRCHistoryEntity app : openedApplications) {
				VehicleRCEntity vehicleRc = app.getVehicleRc();
				Long vehicleRcId = vehicleRc.getVehicleRcId();
				UserType userType = app.getUserEntity().getUserType();
				List<VehicleRCHistoryEntity> actionedHistory = vehicleRcHistoryDAO.getActionedEntries(vehicleRcId,
						userType);
				if (ObjectsUtil.isNullOrEmpty(actionedHistory)) {
					long currentTime = DateUtil.toCurrentUTCTimeStamp();
					vehicleRc.setModifiedOn(currentTime);
					if (userType == UserType.ROLE_CCO) {
						vehicleRc.setCcoActionStatus(Status.PENDING.getValue());
						vehicleRc.setCcoUserId(null);
					} else if (userType == UserType.ROLE_AO) {
						vehicleRc.setAoActionStatus(Status.PENDING.getValue());
						vehicleRc.setAoUserId(null);
					} else if (userType == UserType.ROLE_MVI) {
						vehicleRc.setMviActionStatus(Status.PENDING.getValue());
						vehicleRc.setMviUserId(null);
					} else if (userType == UserType.ROLE_RTO) {
						vehicleRc.setRtoActionStatus(Status.PENDING.getValue());
						vehicleRc.setRtoUserId(null);
					}
					app.setModifiedOn(currentTime);
					app.setStatus(Status.EXPIRED.getValue());
					vehicleRcHistoryDAO.update(app);
				}
			}
		}
	}

	@Override
	@Transactional
	public void updateOpenedApplications1() {
		List<VehicleRCEntity> openedApplications = vehicleDAO.getOpenedApplications();
		if (!ObjectsUtil.isNullOrEmpty(openedApplications)) {
			long currentTime = DateUtil.toCurrentUTCTimeStamp();
			for (VehicleRCEntity vrc : openedApplications) {
				Integer processStatus = vrc.getProcessStatus();
				boolean ccoExpired = false;
				boolean mviExpired = false;
				if (Status.getStatus(vrc.getCcoActionStatus()) == Status.OPEN) {
					long userId = vrc.getCcoUserId().getUserId();
					VehicleRCHistoryEntity vrh = vehicleRcHistoryDAO.getOpenedApplications(userId, vrc.getVehicleRcId(),
							vrc.getIteration());
					vrh.setStatus(Status.EXPIRED.getValue());
					vrh.setModifiedOn(currentTime);
					vehicleRcHistoryDAO.update(vrh);
					vrc.setCcoActionStatus(Status.PENDING.getValue());
					vrc.setCcoUserId(null);
					vrc.setModifiedOn(currentTime);
					ccoExpired = true;
					vehicleDAO.update(vrc);
				}
				if (Status.getStatus(vrc.getMviActionStatus()) == Status.OPEN) {
					long userId = vrc.getMviUserId().getUserId();
					VehicleRCHistoryEntity vrh = vehicleRcHistoryDAO.getOpenedApplications(userId, vrc.getVehicleRcId(),
							vrc.getIteration());
					vrh.setStatus(Status.EXPIRED.getValue());
					vrh.setModifiedOn(currentTime);
					vehicleRcHistoryDAO.update(vrh);
					vrc.setMviActionStatus(Status.PENDING.getValue());
					vrc.setMviUserId(null);
					vrc.setModifiedOn(currentTime);
					mviExpired = true;
					vehicleDAO.update(vrc);
				}
				if (processStatus.intValue() == Status.CCO_MVI.getValue()) {
					if (ccoExpired && mviExpired) {
						processStatus = Status.PENDING.getValue();
					} else if (ccoExpired) {
						processStatus = Status.MVI.getValue();
					} else if (mviExpired) {
						processStatus = Status.CCO.getValue();
					}
				} else if (processStatus.intValue() == Status.CCO.getValue()
						|| processStatus.intValue() == Status.MVI.getValue()) {
					processStatus = Status.PENDING.getValue();
				}
				vrc.setProcessStatus(processStatus);
				if (Status.getStatus(vrc.getAoActionStatus()) == Status.OPEN) {
					long userId = vrc.getAoUserId().getUserId();
					VehicleRCHistoryEntity vrh = vehicleRcHistoryDAO.getOpenedApplications(userId, vrc.getVehicleRcId(),
							vrc.getIteration());
					vrh.setModifiedOn(currentTime);
					vrh.setStatus(Status.EXPIRED.getValue());
					vehicleRcHistoryDAO.update(vrh);
					vrc.setAoActionStatus(Status.PENDING.getValue());
					vrc.setAoUserId(null);
					vrc.setModifiedOn(currentTime);
					vrc.setProcessStatus(Status.CCO_MVI.getValue());
					vehicleDAO.update(vrc);
				}
				if (Status.getStatus(vrc.getRtoActionStatus()) == Status.OPEN) {
					long userId = vrc.getRtoUserId().getUserId();
					VehicleRCHistoryEntity vrh = vehicleRcHistoryDAO.getOpenedApplications(userId, vrc.getVehicleRcId(),
							vrc.getIteration());
					vrh.setStatus(Status.EXPIRED.getValue());
					vrh.setModifiedOn(currentTime);
					vehicleRcHistoryDAO.update(vrh);
					vrc.setRtoActionStatus(Status.PENDING.getValue());
					vrc.setRtoUserId(null);
					vrc.setModifiedOn(currentTime);
					vrc.setProcessStatus(Status.AO.getValue());
					vehicleDAO.update(vrc);
				}
			}
		}
	}

	@Override
	@Transactional
	public String getPrSeriesNo4Other(Long vehicleRCId) throws NotFoundException {
		log.info(":getPrSeriesNo4Other::::: " + vehicleRCId);
		VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRCId);
		if (vehicleRCEntity == null)
			throw new IllegalArgumentException("Vehicle Rc No not found");
		return getPrSeriesNo(vehicleRCEntity, null);
	}

	public void movePrPendingAppActionHistory() {
		for (VehicleRCEntity vehicle : vehicleDAO.getPrPendingApplications()) {
			/*
			 * if (null != vehicle.getCcoActionStatus() || null !=
			 * vehicle.getMviActionStatus()) {
			 * log.info("**************** SKIPPED App with VehicleRcId:" +
			 * vehicle.getVehicleRcId()); continue; }
			 */
			VehicleRCMigrationEntity vehicleMigrate = new VehicleRCMigrationEntity(vehicle.getVehicleRcId());
			vehicleMigrate.setCreatedBy("MIGRATION");
			vehicleMigrate.setModifiedBy("MIGRATION");
			vehicleMigrate.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			/*
			 * log.info("**************** Start Moving App with Vehicle Rc ID:"
			 * + vehicle.getVehicleRcId()); log.
			 * info("**************** STATE TR/PR BEFORE Moving. 1.TRNumber:" +
			 * vehicle.getTrNumber() + " 2.PRNumber:" + vehicle.getPrNumber() +
			 * " 3.TRNumber:" + vehicle.getTrNumber());
			 * log.info("**************** STATUS BEFORE Moving. 1.CCO:" +
			 * vehicle.getCcoActionStatus() + " 2.MVI:" +
			 * vehicle.getMviActionStatus() + " 3.AO:" +
			 * vehicle.getAoActionStatus() + " 4.RTO:" +
			 * vehicle.getRtoActionStatus());
			 */
			List<VehicleRCHistoryEntity> vrh = vehicleRcHistoryDAO.getHistryOfVehicleRcId(vehicle.getVehicleRcId(),
					vehicle.getIteration());
			if (null != vrh) {
				ApplicationUserStatusModel ausm = new ApplicationUserStatusModel();

				for (VehicleRCHistoryEntity vh : vrh) {
					try {
						/*
						 * log.info("**************** HISTORY: 1.RTA EMP Type:"
						 * + vh.getRtaEmployeeType() + " 2.USERID:" +
						 * vh.getUserEntity().getUserId() + " 3.STATUS:" +
						 * vh.getStatus());
						 */
						if (UserType.ROLE_CCO.getValue() == vh.getRtaEmployeeType()) {
							Map<Long, Integer> status = ausm.getCcoActionStatus();
							if (null == status) {
								Map<Long, Integer> newStatus = new HashMap<Long, Integer>();
								newStatus.put(vh.getUserEntity().getUserId(), vh.getStatus());
								ausm.setCcoActionStatus(newStatus);
							} else if (status.containsKey(vh.getUserEntity().getUserId())) {
								if (status.get(vh.getUserEntity().getUserId()) > vh.getStatus()) {
									Map<Long, Integer> newStatus = new HashMap<Long, Integer>();
									newStatus.put(vh.getUserEntity().getUserId(), vh.getStatus());
									ausm.setCcoActionStatus(newStatus);
								}
							} else if (!status.containsValue(vh.getStatus())
									&& ((Integer) ausm.getCcoActionStatus().values().toArray()[0]) > vh.getStatus()) {
								Map<Long, Integer> newStatus = new HashMap<Long, Integer>();
								newStatus.put(vh.getUserEntity().getUserId(), vh.getStatus());
								ausm.setCcoActionStatus(newStatus);
							}
						} else if (UserType.ROLE_MVI.getValue() == vh.getRtaEmployeeType()) {
							Map<Long, Integer> status = ausm.getMviActionStatus();
							if (null == status) {
								Map<Long, Integer> newStatus = new HashMap<Long, Integer>();
								newStatus.put(vh.getUserEntity().getUserId(), vh.getStatus());
								ausm.setMviActionStatus(newStatus);
							} else if (status.containsKey(vh.getUserEntity().getUserId())) {
								if (status.get(vh.getUserEntity().getUserId()) > vh.getStatus()) {
									Map<Long, Integer> newStatus = new HashMap<Long, Integer>();
									newStatus.put(vh.getUserEntity().getUserId(), vh.getStatus());
									ausm.setMviActionStatus(newStatus);
								}
							} else if (!status.containsValue(vh.getStatus())
									&& ((Integer) ausm.getMviActionStatus().values().toArray()[0]) > vh.getStatus()) {
								Map<Long, Integer> newStatus = new HashMap<Long, Integer>();
								newStatus.put(vh.getUserEntity().getUserId(), vh.getStatus());
								ausm.setMviActionStatus(newStatus);
							}
						} else if (UserType.ROLE_AO.getValue() == vh.getRtaEmployeeType()) {
							Map<Long, Integer> status = ausm.getAoActionStatus();
							if (null == status) {
								Map<Long, Integer> newStatus = new HashMap<Long, Integer>();
								newStatus.put(vh.getUserEntity().getUserId(), vh.getStatus());
								ausm.setAoActionStatus(newStatus);
							} else if (status.containsKey(vh.getUserEntity().getUserId())) {
								if (status.get(vh.getUserEntity().getUserId()) > vh.getStatus()) {
									Map<Long, Integer> newStatus = new HashMap<Long, Integer>();
									newStatus.put(vh.getUserEntity().getUserId(), vh.getStatus());
									ausm.setAoActionStatus(newStatus);
								}
							} else if (!status.containsValue(vh.getStatus())
									&& ((Integer) ausm.getAoActionStatus().values().toArray()[0]) > vh.getStatus()) {
								Map<Long, Integer> newStatus = new HashMap<Long, Integer>();
								newStatus.put(vh.getUserEntity().getUserId(), vh.getStatus());
								ausm.setAoActionStatus(newStatus);
							}
						} else if (UserType.ROLE_RTO.getValue() == vh.getRtaEmployeeType()) {
							Map<Long, Integer> status = ausm.getRtoActionStatus();
							if (null == status) {
								Map<Long, Integer> newStatus = new HashMap<Long, Integer>();
								newStatus.put(vh.getUserEntity().getUserId(), vh.getStatus());
								ausm.setRtoActionStatus(newStatus);
							} else if (status.containsKey(vh.getUserEntity().getUserId())) {
								if (status.get(vh.getUserEntity().getUserId()) > vh.getStatus()) {
									Map<Long, Integer> newStatus = new HashMap<Long, Integer>();
									newStatus.put(vh.getUserEntity().getUserId(), vh.getStatus());
									ausm.setRtoActionStatus(newStatus);
								}
							} else if (!status.containsValue(vh.getStatus())
									&& ((Integer) ausm.getRtoActionStatus().values().toArray()[0]) > vh.getStatus()) {
								Map<Long, Integer> newStatus = new HashMap<Long, Integer>();
								newStatus.put(vh.getUserEntity().getUserId(), vh.getStatus());
								ausm.setRtoActionStatus(newStatus);
							}
						} else if (UserType.ROLE_DEALER.getValue() == vh.getRtaEmployeeType()) {
							vehicleMigrate.setDealerActionStatus(vh.getStatus());
							vehicleMigrate.setDealerId(vh.getUserEntity().getUserId());
						}
					} catch (Exception e) {
						log.error("**************** ERROR Start Moving App with Vehicle Rc ID:"
								+ vehicle.getVehicleRcId());
					}
				}

				vehicleMigrate.setCcoActionStatus(Status.PENDING.getValue());
				vehicleMigrate.setMviActionStatus(Status.PENDING.getValue());
				vehicleMigrate.setAoActionStatus(Status.PENDING.getValue());
				vehicleMigrate.setRtoActionStatus(Status.PENDING.getValue());
				if (null != ausm.getCcoActionStatus()) {
					vehicleMigrate.setCcoUserId((Long) ausm.getCcoActionStatus().keySet().toArray()[0]);
					vehicleMigrate.setCcoActionStatus((Integer) ausm.getCcoActionStatus().values().toArray()[0]);
				}
				if (null != ausm.getMviActionStatus()) {
					vehicleMigrate.setMviUserId((Long) ausm.getMviActionStatus().keySet().toArray()[0]);
					vehicleMigrate.setMviActionStatus((Integer) ausm.getMviActionStatus().values().toArray()[0]);
				}
				if (null != ausm.getAoActionStatus()) {
					vehicleMigrate.setAoUserId((Long) ausm.getAoActionStatus().keySet().toArray()[0]);
					vehicleMigrate.setAoActionStatus((Integer) ausm.getAoActionStatus().values().toArray()[0]);
				}
				if (null != ausm.getRtoActionStatus()) {
					vehicleMigrate.setRtoUserId((Long) ausm.getRtoActionStatus().keySet().toArray()[0]);
					vehicleMigrate.setRtoActionStatus((Integer) ausm.getRtoActionStatus().values().toArray()[0]);
				}
			} else {
				vehicleMigrate.setCcoActionStatus(Status.PENDING.getValue());
				vehicleMigrate.setMviActionStatus(Status.PENDING.getValue());
				vehicleMigrate.setAoActionStatus(Status.PENDING.getValue());
				vehicleMigrate.setRtoActionStatus(Status.PENDING.getValue());
			}
			vehicleMigrate.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			vehicleRCMigrationDAO.saveOrUpdate(vehicleMigrate);
			/*
			 * log.info("**************** STATUS AFTER Moving. 1.CCO:" +
			 * vehicleMigrate.getCcoActionStatus() + " 2.MVI:" +
			 * vehicleMigrate.getMviActionStatus() + " 3.AO:" +
			 * vehicleMigrate.getAoActionStatus() + " 4.RTO:" +
			 * vehicleMigrate.getRtoActionStatus());
			 */
		}
	}

	@Override
	@Transactional
	public GeneralDetails getGeneralDetails(Long vehicleRcId, UserType userType) throws NotFoundException {
		VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);
		if (ObjectsUtil.isNull(vehicleRCEntity)) {
			throw new NotFoundException("Invalid Vehicle RC Id !!!");
		}
		GeneralDetails generalDetails = new GeneralDetails();
		generalDetails.setCustomerDetails(customerService.getCustomerInfoStepOne(String.valueOf(vehicleRcId)));
		generalDetails.setFinanceDetails(financeDtlsService.getFinanceDtlsByVehicleRcId(vehicleRcId, userType));
		generalDetails.setInsuranceDetails(insuranceDetailsService.getInsuranceDtlsByVehicleRcId(vehicleRcId));
		generalDetails.setVehicleDetails(getVehicleDetails(vehicleRCEntity));
		generalDetails.setSignatureDetails(getSignature(vehicleRcId));
		generalDetails.setDealerDetails(getDealerDetails(vehicleRCEntity));
		return generalDetails;
	}

	public VehicleDetailsRequestModel getVehicleDetails(VehicleRCEntity vehicleRCEntity)
			throws VehicleRcNotFoundException {
		VehicleDetailsRequestModel vehicleDetails = new VehicleDetailsRequestModel();
		UnregisteredVehicleModel vehicle = vehicleDetailsService.get(vehicleRCEntity.getVehicleRcId());
		VehicleDetailsEntity vde = vehicleDetailsDao.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
		if (ObjectsUtil.isNull(vde)) {
			log.error("Vehicle Details Not Found RcId : " + vehicleRCEntity.getVehicleRcId());
			throw new VehicleRcNotFoundException("Vehicle Details Not Found !!!");
		}
		VahanEntity vahanEntity = vahanDAO.getByChassisNumber(vehicleRCEntity.getChassisNumber());
		vehicle.setEnginePower(vahanEntity.getEnginePower());
		vehicle.setCubicCapacity(vahanEntity.getCubicCap());
		vehicle.setNoCyl(vahanEntity.getNoCyl());
		vehicle.setBodyTypeDesc(vahanEntity.getBodyTypeDesc());
		vehicle.setFrontAxleWeight(vahanEntity.getFrontAxleWeight());
		vehicle.setFrontAxleDesc(vahanEntity.getFrontAxleDesc());
		vehicle.setRearAxleWeight(vahanEntity.getRearAxleWeight());
		vehicle.setRearAxleDesc(vahanEntity.getRearAxleDesc());
		vehicle.setTandemAxelWeight(vahanEntity.getTandemAxelWeight());
		vehicle.setTandemAxelDescp(vahanEntity.getTandemAxelDescp());
		vehicle.setVehicleSubClass(vde.getVehicleSubClass());
		if (!StringsUtil.isNullOrEmpty(vde.getVehicleSubClass())) {
			vehicle.setVehicleClassDes(
					vehicleClassDescDAO.getByVehiclClassDesCode(vde.getVehicleSubClass()).getDescription());
		}
		vehicleDetails.setVehicle(vehicle);

		VehicleBillingDetailsEntity billingEntity = vehicleBillingDetailsDAO
				.getByVehicleDetailId(vehicleDetailsDao.getByVehicleRcId(vehicleRCEntity.getVehicleRcId()));
		BillingDetailsModel billModel = new BillingDetailsModel();
		billModel.setInvoiceDate(billingEntity.getInvoiceDate());
		billModel.setInvoiceNumber(billingEntity.getInvoiceNumber());
		billModel.setInvoiceValue(billingEntity.getInvoiceValue());
		vehicleDetails.setBillModel(billModel);
		return vehicleDetails;
	}

	private SignatureDetails getSignature(Long vehicleRcId) {
		List<Integer> docTypesIds = new ArrayList<Integer>();
		docTypesIds.add(DocTypes.DOC_USER_SIGNATURE.getValue());
		docTypesIds.add(DocTypes.DOC_DEALER_SIGNATURE.getValue());
		List<AttachmentDetailsEntity> attachDtlEntities = attachmentDetailsDAO.getDealerAndOwnerSign(vehicleRcId,
				docTypesIds);
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

	private DealerDetails getDealerDetails(VehicleRCEntity vehicleRCEntity) {

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
			log.error(":::::::::getDealerDetails::::::::::Exception::::::" + ex.getMessage());
			log.debug("Exception : " + ex);
		}
		return dealerDetails;
	}

	@Transactional
	@Override
	public String getRtoSignFileName(String officeCode) {
		RTAEmployeeEntity rtaEmployee = rtaEmployeeDAO.getRTAEmp(officeCode, UserType.ROLE_RTO, true);
		if (rtaEmployee == null) {
			String parentOfficeCode = rtaOfficeDAO.getRtaOfficeDetailsByCode(officeCode).getParentOfficeCode();
			rtaEmployee = rtaEmployeeDAO.getRTAEmp(parentOfficeCode, UserType.ROLE_RTO, true);
		}
		if (rtaEmployee == null || StringsUtil.isNullOrEmpty(rtaEmployee.getSignFileName())) {
			log.error("Employee Signature File Not found. officeCode : " + officeCode);
			throw new IllegalArgumentException("Employee Signature File Not found.");
		} else {
			return rtaEmployee.getSignFileName();
		}
	}

	@Transactional
	@Override
	public String getMviSignFileName(String officeCode) {
		RTAEmployeeEntity rtaEmployee = rtaEmployeeDAO.getRTAEmp(officeCode, UserType.ROLE_MVI, true);
		if (rtaEmployee == null) {
			String parentOfficeCode = rtaOfficeDAO.getRtaOfficeDetailsByCode(officeCode).getParentOfficeCode();
			rtaEmployee = rtaEmployeeDAO.getRTAEmp(parentOfficeCode, UserType.ROLE_MVI, true);
		}
		if (rtaEmployee == null || StringsUtil.isNullOrEmpty(rtaEmployee.getSignFileName())) {
			log.error("Employee Signature File Not found. officeCode : " + officeCode);
			throw new IllegalArgumentException("Employee Signature File Not found.");
		} else {
			return rtaEmployee.getSignFileName();
		}
	}

	@Transactional
	@Override
	public SuspendedRCNumberModel getSuspendededDetailsByRC(Long vehicleRcId) {
		VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);
		if (vehicleRCEntity == null) {
			log.error("Vehicle Rc No not found : " + vehicleRcId);
			throw new IllegalArgumentException("Vehicle RC not found !!!");
		}
		Status prStatus = Status.getStatus(vehicleRCEntity.getPrStatus());
		SuspendedRCNumberModel suspendedPrModel = null;
		if (prStatus != null) {
			SuspendedRCNumbersEntity suspendedPrEntity = null;
			if (prStatus == Status.SUSPENDED) {
				suspendedPrEntity = suspendedRcNumberDAO.getDetails(vehicleRCEntity.getPrNumber(), Status.SUSPENDED);
			} else if (prStatus == Status.OBJECTION) {
				suspendedPrEntity = suspendedRcNumberDAO.getDetails(vehicleRCEntity.getPrNumber(), Status.OBJECTION);
			}
			if (suspendedPrEntity != null) {
				suspendedPrModel = new SuspendedRCNumberModel();
				suspendedPrModel.setComment(suspendedPrEntity.getComment());
				suspendedPrModel.setCreatedBy(suspendedPrEntity.getCreatedBy());
				suspendedPrModel.setEndDate(suspendedPrEntity.getEndDate());
				suspendedPrModel.setIsRevoked(suspendedPrEntity.getIsRevoked());
				suspendedPrModel.setModifiedBy(suspendedPrEntity.getModifiedBy());
				suspendedPrModel.setModifiedOn(suspendedPrEntity.getModifiedOn());
				suspendedPrModel.setPrNumber(suspendedPrEntity.getPrNumber());
				suspendedPrModel.setStartDate(suspendedPrEntity.getStartDate());
				suspendedPrModel.setStatus(suspendedPrEntity.getStatus());
				suspendedPrModel.setSuspendedRcNumbersId(suspendedPrEntity.getSuspendedRcNumbersId());
				suspendedPrModel.setReason(suspendedPrEntity.getDescription());
				suspendedPrModel.setRaisedBy(suspendedPrEntity.getRaisedBy());
			}
		}
		return suspendedPrModel;
	}

	@Override
	@Transactional
	public void revocateSuspensionExpiredVehicles() {
		long currentTime = DateUtil.toCurrentUTCTimeStamp();
		List<VehicleRCEntity> vrcList = vehicleDAO.getSuspensionExpiredVehicle(currentTime);
		if (ObjectsUtil.isNotNull(vrcList)) {
			for (VehicleRCEntity vrc : vrcList) {
				vrc.setPrStatus(Status.APPROVED.getValue());
				vrc.setModifiedOn(currentTime);
				vrc.setModifiedBy("admin");
				vehicleDAO.update(vrc);
				SuspendedRCNumbersEntity suspendedRCEntity = suspendedRcNumberDAO.getDetails(vrc.getPrNumber(),
						Status.SUSPENDED);
				suspendedRCEntity.setIsRevoked(Boolean.TRUE);
				suspendedRCEntity.setModifiedOn(currentTime);
				suspendedRCEntity.setModifiedBy("admin");
				suspendedRcNumberDAO.update(suspendedRCEntity);
			}
		}
	}

	@Override
	@Transactional
	public SaveUpdateResponse generateReassignmentVehicle(VehicleReassignmentModel vehicleReassignmentModel)
			throws NotFoundException {
		Map<Long, Status> map = new HashMap<>();
		String prNumber = vehicleReassignmentModel.getPrNumber();
		VehicleRCEntity vehicleRcEntity = vehicleDAO.getVehicleRCByPRNumber(prNumber);
		PRType prType = vehicleReassignmentModel.getPrType();
		RegistrationCategoryType regType = null;
		log.info("For Alteration going to generate PR with category : " + vehicleReassignmentModel.getRegCategoryCode());
		if(!StringsUtil.isNullOrEmpty(vehicleReassignmentModel.getRegCategoryCode())){
			regType = RegistrationCategoryType.getRegistrationCategoryTypeByCode(vehicleReassignmentModel.getRegCategoryCode());
			vehicleRcEntity.setRegCategory(registraionCategoryDAO.getByCode(regType.getCode()));
		} else {
			regType = RegistrationCategoryType.getRegistrationCategoryType(vehicleRcEntity.getRegCategory().getRegistrationCategoryId());
		}
		String pr;
		if (prType.equals(PRType.ORDINARY)) {
			pr = getPrSeriesNo(vehicleRcEntity, regType);
			vehicleRcEntity.setPrType(PRType.ORDINARY.getId());
		} else {
			pr = vehicleReassignmentModel.getSpecialPrNo();
			vehicleRcEntity.setPrType(PRType.SPECIAL.getId());
		}
		vehicleRcEntity.setPrNumber(pr);
		vehicleRcEntity.setModifiedBy(pr);
		vehicleRcEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		// pr issue date should not be changed while vehicle alteration......RTA-4161
		//vehicleRcEntity.setPrIssueTime(DateUtil.toCurrentUTCTimeStamp());
		vehicleRcEntity.setPrStatus(Status.APPROVED.getValue());
		switch (regType) {
			case NON_TRANSPORT:
				vehicleRcEntity.setPrExpireDate(DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), prValidty));
				break;
			case TRANSPORT:
				vehicleRcEntity.setPrExpireDate(DateUtil.addYears(DateUtil.toCurrentUTCTimeStamp(), prValidtyTrasnport));
				break;
		}
		String result = vehicleRCChangeHistoryDAO.saveData(vehicleRcEntity.getVehicleRcId(), "CITIZEN1",
				DateUtil.toCurrentUTCTimeStamp(), ServiceType.VEHICLE_REASSIGNMENT);
		log.info("one row Inserted In Address history Table status : " + result);
		vehicleDAO.update(vehicleRcEntity);
		if(ObjectsUtil.isNotNull(vehicleReassignmentModel.getServiceCode()) && vehicleReassignmentModel.getServiceCode().equalsIgnoreCase(ServiceType.VEHICLE_ATLERATION.getCode())
				&& regType == RegistrationCategoryType.TRANSPORT){
			//------generate fitness certificate --------------------
			fcDetailsService.generateFinessForVA(vehicleRcEntity, vehicleReassignmentModel.getCov(), vehicleReassignmentModel.getUserActionModel().getUserId());
		}
		log.info(map.toString());
		log.debug(map.toString());
		//----------save in vehicle pr release for history ----------------
        try{
        	VehiclePRReleaseEntity prReleaseEntity = new VehiclePRReleaseEntity();
        	prReleaseEntity.setPrNumber(prNumber);
        	prReleaseEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
        	prReleaseEntity.setServiceType(ServiceType.VEHICLE_ATLERATION.getCode());
        	prReleaseEntity.setVehicleRcId(vehicleRcEntity.getVehicleRcId());
        	vehiclePRReleaseDAO.save(prReleaseEntity);
        } catch(Exception ex){
        }
		SaveUpdateResponse response = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS,
				"New PR Generated Successfully.", prNumber);
		return response;
	}

	@Override
	@Transactional
	public FreshRcAppStatusDetailsModel getApplicationStatusDetails(Long vehicleRcId)
			throws VehicleRcNotFoundException, UserNotFoundException {
		VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);
		FinanceModel financeDetails = financeDtlsService.getFinanceDtlsByVehicleRcId(vehicleRcId, null);
		VehicleDetailsModel vehicleDetails = vehicleDetailsService.getVehicleDetails(vehicleRcId);
		FreshRcModel financerFreshRc = financeFreshRcService.getFinanceFreshRCDtlByFinancerId(vehicleRcId, financeDetails.getFinancerId());

		FreshRcAppStatusDetailsModel freshRcAppStatusDetailsModel = new FreshRcAppStatusDetailsModel();
		freshRcAppStatusDetailsModel.setFinanceModel(financeDetails);
		freshRcAppStatusDetailsModel.setVehicleDetailsModel(vehicleDetails);
		freshRcAppStatusDetailsModel.setFreshRcModel(financerFreshRc);
		
		return freshRcAppStatusDetailsModel;
	}

	@Override
	@Transactional
	public SaveUpdateResponse submitOwnerConscent(OwnerConscent ownerConscent, String appNumber) {
		FinancerFreshRcEntity financerFreshRcEntity = financerFreshRcDAO.getFreshRcFromApplicationNumber(appNumber);
		if (ObjectsUtil.isNotNull(financerFreshRcEntity)) {
			financerFreshRcEntity.setOwnerConsent(ownerConscent.getOwnerConscent());
			financerFreshRcEntity.setOwnerComment(ownerConscent.getOwnerComment());
			financerFreshRcEntity.setOwnerConscentDate(DateUtil.toCurrentUTCTimeStamp());
			financerFreshRcDAO.saveOrUpdate(financerFreshRcEntity);
		}
		SaveUpdateResponse response = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Saved Successfully.",
				appNumber);
		response.setVehicleRcId(financerFreshRcEntity.getVehicleRcId().getVehicleRcId().toString());
		return response;
	}
	
	@Override
	@Transactional
	public ResponseModel<FreshRcModel> updateOwnerConscent(OwnerConscent ownerConscent, String appNumber) {
		FinancerFreshRcEntity financerFreshRcEntity = financerFreshRcDAO.getFreshRcFromApplicationNumber(appNumber);
		ResponseModel<FreshRcModel> responseModel = new ResponseModel<FreshRcModel>();
		responseModel.setStatus(ResponseModel.SUCCESS);
		if (ObjectsUtil.isNotNull(financerFreshRcEntity)) {
			if(ObjectsUtil.isNull(financerFreshRcEntity.getOwnerConsent())){
				financerFreshRcEntity.setOwnerConsent(ownerConscent.getOwnerConscent());
				financerFreshRcEntity.setOwnerComment(ownerConscent.getOwnerComment());
				financerFreshRcEntity.setOwnerConscentDate(DateUtil.toCurrentUTCTimeStamp());
				financerFreshRcDAO.saveOrUpdate(financerFreshRcEntity);
			}
		} else {
			responseModel.setStatus(ResponseModel.FAILED);
			return responseModel;
		}
		FreshRcModel freshrc = null;
		if(ObjectsUtil.isNotNull(financerFreshRcEntity)){
			freshrc = new FreshRcModel();
			freshrc.setVehicleRcId(financerFreshRcEntity.getVehicleRcId().getVehicleRcId());
			freshrc.setVehicleUnderPossession(financerFreshRcEntity.getVehicleUnderPossession());
			freshrc.setOwnerConsent(financerFreshRcEntity.getOwnerConsent());
			freshrc.setAmountDue(financerFreshRcEntity.getAmountDue());
			freshrc.setOverDueSince(financerFreshRcEntity.getOverDueSince());
			responseModel.setData(freshrc);
		}
		return responseModel;
	}
}
