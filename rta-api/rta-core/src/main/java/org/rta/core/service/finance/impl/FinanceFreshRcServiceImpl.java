package org.rta.core.service.finance.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.finance.FinanceApprovalHistoryDAO;
import org.rta.core.dao.finance.FinanceYardDAO;
import org.rta.core.dao.finance.FinancerFreshRcDAO;
import org.rta.core.dao.finance.FinancerSeizedVehiclesDAO;
import org.rta.core.dao.finance.VehicleFinanceDtlsDAO;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.finance.FinanceApprovalHistoryEntity;
import org.rta.core.entity.finance.FinanceYardEntity;
import org.rta.core.entity.finance.FinancerFreshRcEntity;
import org.rta.core.entity.finance.FinancerSeizedVehiclesEntity;
import org.rta.core.entity.finance.VehicleFinanceDtlstEntity;
import org.rta.core.entity.master.CountryEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.enums.citizen.KeyType;
import org.rta.core.exception.NotFoundException;
import org.rta.core.exception.UserNotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.helper.office.RTAOfficeHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.citizen.CitizenServiceResponseModel;
import org.rta.core.model.finance.FinanceYardModel;
import org.rta.core.model.finance.FreshRcModel;
import org.rta.core.model.finance.ShowcaseInfoRequestModel;
import org.rta.core.model.finance.ShowcaseNoticeInfoModel;
import org.rta.core.model.user.AddressModel;
import org.rta.core.service.cardprinter.RcCardDetailsService;
import org.rta.core.service.citizen.CitizenService;
import org.rta.core.service.citizen.ResponseModel;
import org.rta.core.service.citizen.model.AuthenticationModel;
import org.rta.core.service.citizen.model.CitizenTokenModel;
import org.rta.core.service.finance.FinanceFreshRcService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class FinanceFreshRcServiceImpl implements FinanceFreshRcService {

	private static final Logger log = Logger.getLogger(FinanceFreshRcServiceImpl.class);

	@Autowired
	private FinancerFreshRcDAO financeFreshDAO;

	@Autowired
	private VehicleDAO vehicleDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private FinanceApprovalHistoryDAO appHistoryDAO;

	@Autowired
	private VehicleFinanceDtlsDAO vehicleFinanceDAO;

	@Autowired
	private FinanceYardDAO yardDAO;

	@Autowired
	private CitizenService citizenService;

	@Autowired
	private AddressDAO addressDAO;

	@Autowired
	private RTAOfficeHelper rtaOfficeHelper;

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private CustomerCorporateDAO customerCorporateDAO;

	@Autowired
	private RcCardDetailsService rcCardDetailsService;
	
	final private int FINAL_FRESH_RC_STEP = 4;

	@Autowired
	private FinancerSeizedVehiclesDAO financerSeizedVehiclesDAO;

	@Transactional
	public ResponseModel<CitizenTokenModel> saveFinanceFreshRCDtl(String token, FreshRcModel freshRcModel)
			throws VehicleRcNotFoundException, UserNotFoundException, NotFoundException {
		VehicleRCEntity vehclRc = vehicleDAO.get(freshRcModel.getVehicleRcId());
		if (vehclRc == null)
			throw new VehicleRcNotFoundException();

		UserEntity user = userDAO.findByUserId(freshRcModel.getFinancerId());
		if (user == null || user.getUserType() != UserType.ROLE_ONLINE_FINANCER)
			throw new UserNotFoundException();

		FinancerSeizedVehiclesEntity fsve = financerSeizedVehiclesDAO.getSeizedVehicle(vehclRc.getVehicleRcId());
		if (ObjectsUtil.isNotNull(fsve) && fsve.getStatus() == Status.APPROVED.getValue()) {
			ResponseModel<CitizenTokenModel> res = new ResponseModel<>();
			res.setStatus(ResponseModel.FAILED);
			res.setStatusCode(305);
			res.setMessage("vehicle already seized");
			return res;
		}

		AddressEntity localAddress = addressDAO.findByUserIdAndType(user.getUserId(), "T");
		if (ObjectsUtil.isNull(localAddress)) {
			log.debug("local address not found for financer userId : " + user.getUserId());
			throw new NotFoundException("local address not found for this financer");
		}

		VehicleFinanceDtlstEntity vehicleFinance = vehicleFinanceDAO.getRcrdForVehicleRc(vehclRc.getVehicleRcId());
		if (vehicleFinance == null || vehicleFinance.getFinanceTerminated())
			throw new IllegalArgumentException("Vehicle Financed records are not found in system.");

		if (vehicleFinance.getRtoApproved() != Status.APPROVED.getValue())
			throw new IllegalArgumentException("Vehicle Finances is not yet approved from RTO end! .");

		if (!vehicleFinance.getFinancerId().equals(freshRcModel.getFinancerId()))
			throw new IllegalArgumentException(
					"The financer with username " + user.getUserName() + " is not authorized Financier.");

		AuthenticationModel authenticationModel = new AuthenticationModel();
		if (vehclRc.getPrIssueTime() != null) {
			authenticationModel.setPrNumber(vehclRc.getPrNumber());
			authenticationModel.setKeyType(KeyType.PR);
		} else {
			authenticationModel.setTrNumber(vehclRc.getTrNumber());
			authenticationModel.setKeyType(KeyType.TR);
		}
		authenticationModel.setAadhaarNumber(user.getAadharNumber());
		CitizenServiceResponseModel<ResponseModel<CitizenTokenModel>> application = citizenService
				.login(authenticationModel, ServiceType.FRESH_RC_FINANCIER, token);
		if (application.getHttpStatus() != HttpStatus.OK) {
			log.error("unable to create Registration - Fresh RC application");
			if (!ObjectsUtil.isNull(application)) {
				return application.getResponseBody();
			}
			log.error("unable to save application for Fresh RC for vehicleRcId : " + freshRcModel.getVehicleRcId());
			throw new IllegalArgumentException("unable to submit");
		} 
		String appNumber = application.getResponseBody().getData().getAppNumber();
		if(!StringUtils.isEmpty(appNumber)){
			FinancerFreshRcEntity freshRcEntity = financeFreshDAO.getFreshRcFromApplicationNumber(appNumber);
			log.info("freshRcEntity : " + freshRcEntity);
			if (freshRcEntity == null)
				freshRcEntity = new FinancerFreshRcEntity();

			freshRcEntity.setAmountDue(freshRcModel.getAmountDue());
			freshRcEntity.setCreatedBy(user.getUserName());
			freshRcEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			freshRcEntity.setCurrentStep(freshRcModel.getCurrentStep());
			freshRcEntity.setModifiedBy(user.getUserName());
			freshRcEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			freshRcEntity.setOverDueSince(freshRcModel.getOverDueSince());
			freshRcEntity.setDefaultedAmount(freshRcModel.getDefaultedAmount());
			freshRcEntity.setNoOfEmi(freshRcModel.getNoOfEmi());

			// *****needs to be removed from here as per new changes
			freshRcEntity.setOwnerConsent(freshRcModel.getOwnerConsent());
			freshRcEntity.setOwnerComment(freshRcModel.getOwnerComment());
			// freshRcEntity.setVehicleUnderPossession(freshRcModel.getVehicleUnderPossession());
			freshRcEntity.setVehicleRcId(vehclRc);

			FinanceYardModel yardModel = freshRcModel.getYard();
			if (!ObjectsUtil.isNull(yardModel)) {
				freshRcEntity.setYard(yardModel.getYardId());
			}
			freshRcEntity.setCcoStatus(Status.PENDING.getValue());
			freshRcEntity.setDtcStatus(Status.PENDING.getValue());

			freshRcEntity.setApplicationNumber(appNumber);
			financeFreshDAO.saveOrUpdate(freshRcEntity);
		}
		return application.getResponseBody();
	}

	@Transactional
	public FreshRcModel getFinanceFreshRCDtlByFinancerId(Long vehicleRc, Long financerId)
			throws VehicleRcNotFoundException, UserNotFoundException {
		VehicleRCEntity vehclRc = vehicleDAO.get(vehicleRc);
		if (vehclRc == null)
			throw new VehicleRcNotFoundException();

		UserEntity user = userDAO.findByUserId(financerId);
		if (user == null || user.getUserType() != UserType.ROLE_ONLINE_FINANCER)
			throw new UserNotFoundException();

		FinancerFreshRcEntity freshRcEntity = financeFreshDAO.getFreshRcFromVehicleRc(vehicleRc);
		if (freshRcEntity == null)
			throw new IllegalArgumentException("Fresh Rc application for vehcile" + vehicleRc + " not found.!!");
		FreshRcModel freshRcModel = new FreshRcModel();
		freshRcModel.setAmountDue(freshRcEntity.getAmountDue());
		freshRcModel.setCurrentStep(freshRcEntity.getCurrentStep());
		freshRcModel.setOverDueSince(freshRcEntity.getOverDueSince());
		freshRcModel.setOwnerConsent(freshRcEntity.getOwnerConsent());
		freshRcModel.setVehicleRcId(vehclRc.getVehicleRcId());
		freshRcModel.setCreatedBy(freshRcEntity.getCreatedBy());
		freshRcModel.setVehicleUnderPossession(freshRcEntity.getVehicleUnderPossession());
		if (ObjectsUtil.isNotNull(freshRcEntity.getYard())) {
			FinanceYardEntity yard = yardDAO.getEntity(FinanceYardEntity.class, freshRcEntity.getYard());
			if (!ObjectsUtil.isNull(yard)) {
				FinanceYardModel yardModel = new FinanceYardModel();
				yardModel.setActiveStatus(yard.getActiveStatus());
				yardModel.setHeadFinancerId(yard.getHeadFinancer().getUserId());
				// yardModel.setYardAddress(yard.getYardAddress());
				yardModel.setYardArea(yard.getYardArea());
				yardModel.setYardId(yard.getFinanceYardId());
				yardModel.setYardName(yard.getName());
				freshRcModel.setYard(yardModel);
			}
		}
		// freshRcModel.setFinancerId(freshRcEntity.mo.getUserId());
		return freshRcModel;

	}

	@Override
	@Transactional
	public FreshRcModel getFinanceFreshRCDtl(Long vehicleRc, String financerAadharNumber)
			throws VehicleRcNotFoundException, UserNotFoundException {
		VehicleRCEntity vehclRc = vehicleDAO.get(vehicleRc);
		if (vehclRc == null)
			throw new VehicleRcNotFoundException();

		VehicleFinanceDtlstEntity vehicelDtls = vehicleFinanceDAO.getVehicleFinanceRcr(vehclRc.getVehicleRcId(),
				Status.APPROVED.getValue());
		if (ObjectsUtil.isNull(vehicelDtls)) {
			log.error("no finance found for vehicleRcId : " + vehicelDtls.getVehicleRcId());
			throw new VehicleRcNotFoundException();
		}
		Long financerId = vehicelDtls.getFinancerId();
		UserEntity userEntity = userDAO.findByUserId(financerId);
		if (userEntity == null || userEntity.getUserType() != UserType.ROLE_ONLINE_FINANCER) {
			log.error("user not found");
			throw new UserNotFoundException();
		}

		FinancerFreshRcEntity freshRcEntity = financeFreshDAO.getFreshRcFromVehicleRc(vehicleRc);
		if (freshRcEntity == null)
			throw new IllegalArgumentException("Fresh Rc application for vehcile" + vehicleRc + " not found.!!");
		FreshRcModel freshRcModel = new FreshRcModel();
		freshRcModel.setAmountDue(freshRcEntity.getAmountDue());
		freshRcModel.setCurrentStep(freshRcEntity.getCurrentStep());
		freshRcModel.setOverDueSince(freshRcEntity.getOverDueSince());
		freshRcModel.setOwnerConsent(freshRcEntity.getOwnerConsent());
		freshRcModel.setVehicleRcId(vehclRc.getVehicleRcId());
		freshRcModel.setVehicleUnderPossession(freshRcEntity.getVehicleUnderPossession());
		Long yardId = freshRcEntity.getYard();
		if (yardId != null) {
			FinanceYardEntity yard = yardDAO.getEntity(FinanceYardEntity.class, freshRcEntity.getYard());
			FinanceYardModel yardModel = new FinanceYardModel();
			yardModel.setActiveStatus(yard.getActiveStatus());
			yardModel.setHeadFinancerId(yard.getHeadFinancer().getUserId());
			// yardModel.setYardAddress(yard.getYardAddress());
			yardModel.setYardArea(yard.getYardArea());
			yardModel.setYardId(yard.getFinanceYardId());
			yardModel.setYardName(yard.getName());
			freshRcModel.setYard(yardModel);
		}
		// freshRcModel.setFinancerId(freshRcEntity.mo.getUserId());
		return freshRcModel;

	}

	@Transactional
	public String updateFreshRcStep(int step, Long vehicleRc) {
		FinancerFreshRcEntity freshRcEntity = financeFreshDAO.getFreshRcFromVehicleRc(vehicleRc);
		if (freshRcEntity == null)
			throw new IllegalArgumentException("Fresh Rc application for vehcile " + vehicleRc + " not found.!!");
		if (step > freshRcEntity.getCurrentStep())
			freshRcEntity.setCurrentStep(step);
		return "Success";
	}

	@Transactional
	public List<FreshRcModel> freshRcAppListForApproverType(Long userId, Status status) {

		FreshRcModel freshRcModel = null;
		List<FreshRcModel> freshRcModels = new ArrayList<FreshRcModel>();
		UserEntity user = userDAO.findByUserId(userId);
		if (user == null)
			throw new IllegalArgumentException("Invalid UserId ");
		if (!(user.getUserType() == UserType.ROLE_CCO || user.getUserType() == UserType.ROLE_DTC))
			throw new IllegalArgumentException("FreshRC applications must be either CCO or DTC only");
		if (!(status == Status.PENDING || status == Status.APPROVED || status == Status.REJECTED))
			throw new IllegalArgumentException("Invalid status");
		List<FinancerFreshRcEntity> freshRcs = financeFreshDAO.getFreshRcList(user.getUserType(), status);

		if (freshRcs == null || freshRcs.isEmpty())
			return freshRcModels;
		for (FinancerFreshRcEntity freshRcEntity : freshRcs) {
			if (freshRcEntity.getCurrentStep() < FINAL_FRESH_RC_STEP)
				continue;
			freshRcModel = new FreshRcModel();
			freshRcModel.setAmountDue(freshRcEntity.getAmountDue());
			freshRcModel.setCurrentStep(freshRcEntity.getCurrentStep());
			freshRcModel.setOverDueSince(freshRcEntity.getOverDueSince());
			freshRcModel.setOwnerConsent(freshRcEntity.getOwnerConsent());
			freshRcModel.setVehicleRcId(freshRcEntity.getVehicleRcId().getVehicleRcId());
			freshRcModel.setVehicleUnderPossession(freshRcEntity.getVehicleUnderPossession());
			FinanceYardEntity yard = yardDAO.getEntity(FinanceYardEntity.class, freshRcEntity.getYard());
			FinanceYardModel yardModel = new FinanceYardModel();
			yardModel.setActiveStatus(yard.getActiveStatus());
			yardModel.setHeadFinancerId(yard.getHeadFinancer().getUserId());
			// yardModel.setYardAddress(yard.getYardAddress());
			yardModel.setYardArea(yard.getYardArea());
			yardModel.setYardId(yard.getFinanceYardId());
			yardModel.setYardName(yard.getName());
			freshRcModel.setYard(yardModel);
			// freshRcModel.setFinancerId(freshRcEntity.getFinancerId().getUserId());
			freshRcModels.add(freshRcModel);
		}
		return freshRcModels;
	}

	@Transactional
	public FreshRcModel freshRcApproveReject(String prOrTr, Long userId, Status status) {
		VehicleRCEntity vehicle = vehicleDAO.getByPROrTrNumber(prOrTr);
		if (vehicle == null)
			throw new IllegalArgumentException("incorrect tr Or pr Number");
		UserEntity user = userDAO.findByUserId(userId);
		if (user == null || !(user.getUserType() == UserType.ROLE_DTC || user.getUserType() == UserType.ROLE_CCO))
			throw new IllegalArgumentException("Invalid userType");
		FinancerFreshRcEntity financeFreshRc = financeFreshDAO.getFreshRcFromVehicleRc(vehicle.getVehicleRcId());
		if (financeFreshRc == null)
			throw new IllegalArgumentException("No request for Fresh Rc found!!!!");
		if (financeFreshRc.getCurrentStep() < FINAL_FRESH_RC_STEP)
			throw new IllegalArgumentException("This Fresh Rc request hasnt complete all steps yet!!!");
		switch (user.getUserType()) {
		case ROLE_CCO:
			if (financeFreshRc.getCcoStatus() != Status.PENDING.getValue())
				throw new IllegalArgumentException("Response for CCO already recorded. Cant change the response.!!!");
			financeFreshRc.setCcoStatus(status.getValue());
			break;
		case ROLE_DTC:
			if (financeFreshRc.getDtcStatus() != Status.PENDING.getValue())
				throw new IllegalArgumentException("Response for DTC already recorded. Cant change the response.!!!");
			financeFreshRc.setDtcStatus(status.getValue());
			break;

		}
		FinanceApprovalHistoryEntity rcHistory = createFinanceAppHistryEntry(user, status, financeFreshRc);
		financeFreshDAO.saveOrUpdate(financeFreshRc);
		appHistoryDAO.saveOrUpdate(rcHistory);
		FreshRcModel freshRcModel = null;

		freshRcModel = new FreshRcModel();
		freshRcModel.setAmountDue(financeFreshRc.getAmountDue());
		freshRcModel.setCurrentStep(financeFreshRc.getCurrentStep());
		freshRcModel.setOverDueSince(financeFreshRc.getOverDueSince());
		freshRcModel.setOwnerConsent(financeFreshRc.getOwnerConsent());
		freshRcModel.setVehicleRcId(financeFreshRc.getVehicleRcId().getVehicleRcId());
		freshRcModel.setVehicleUnderPossession(financeFreshRc.getVehicleUnderPossession());
		FinanceYardEntity yard = yardDAO.getEntity(FinanceYardEntity.class, financeFreshRc.getYard());
		FinanceYardModel yardModel = new FinanceYardModel();
		yardModel.setActiveStatus(yard.getActiveStatus());
		yardModel.setHeadFinancerId(yard.getHeadFinancer().getUserId());
		// yardModel.setYardAddress(yard.getYardAddress());
		yardModel.setYardArea(yard.getYardArea());
		yardModel.setYardId(yard.getFinanceYardId());
		yardModel.setYardName(yard.getName());
		freshRcModel.setYard(yardModel);
		// freshRcModel.setFinancerId(freshRcEntity.getFinancerId().getUserId());
		return freshRcModel;
	}

	private FinanceApprovalHistoryEntity createFinanceAppHistryEntry(UserEntity user, Status status,
			FinancerFreshRcEntity freshRc) {
		UserEntity financerId = userDAO.findByUserName(freshRc.getCreatedBy());
		if (financerId == null)
			throw new IllegalArgumentException("invalid financier");
		FinanceApprovalHistoryEntity history = new FinanceApprovalHistoryEntity();
		history.setApprover_id(user);
		history.setApproverStatus(status.getValue());
		history.setComments("");
		history.setCreatedBy(user.getUserName());
		history.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		history.setFinancerId(financerId.getUserId());
		history.setModifiedBy(user.getUserName());
		history.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		history.setServiceType(ServiceType.FRESH_RC_FINANCIER.getValue());
		history.setVehicleRcId(freshRc.getVehicleRcId());
		return history;

	}

	@Override
	@Transactional
	public ShowcaseNoticeInfoModel getShowcaseNoticeInfoModel(ShowcaseInfoRequestModel request)
			throws VehicleRcNotFoundException, UserNotFoundException {
		ShowcaseNoticeInfoModel showcaseNoticeModel = null;
		VehicleRCEntity vehclRc = vehicleDAO.get(request.getVehicleRcId());
		if (request.getVehicleRcId() == null) {
			log.error("vehicleRcId not found" + request.getVehicleRcId());
			throw new VehicleRcNotFoundException("vehicleRcId not found");
		}
		showcaseNoticeModel = new ShowcaseNoticeInfoModel();
		showcaseNoticeModel.setRtaOffice(rtaOfficeHelper.convertToModel(vehclRc.getRtaOfficeId()));
		if (vehclRc.getPrStatus() == Status.APPROVED.getValue()) {
			showcaseNoticeModel.setRegistrationNumber(vehclRc.getPrNumber());
		} else {
			showcaseNoticeModel.setRegistrationNumber(vehclRc.getTrNumber());
		}
		VehicleFinanceDtlstEntity vehicelDtls = vehicleFinanceDAO.getVehicleFinanceRcr(vehclRc.getVehicleRcId(),
				Status.APPROVED.getValue());
		if (ObjectsUtil.isNull(vehicelDtls)) {
			log.error("no finance found for vehicleRcId : " + vehicelDtls.getVehicleRcId());
			throw new VehicleRcNotFoundException("vehicleRcId not found");
		}
		Long financerId = vehicelDtls.getFinancerId();
		UserEntity userEntity = userDAO.findByUserId(financerId);
		if (ObjectsUtil.isNull(userEntity) || userEntity.getUserType() != UserType.ROLE_ONLINE_FINANCER) {
			log.error("user not found for vehicleRcId : " + vehicelDtls.getVehicleRcId() + " and financerId : "
					+ financerId);
			throw new UserNotFoundException();
		}

		AddressEntity address = addressDAO.findByUserIdAndType(userEntity.getUserId(), "T");
		MandalEntity mandalEntity = address.getMandal();
		DistrictEntity districtEntity = mandalEntity.getDistrictEntity();
		StateEntity stateEntity = districtEntity.getStateEntity();
		CountryEntity countryEntity = stateEntity.getCountryEntity();

		showcaseNoticeModel.setFinancerName(userEntity.getFirstName());
		AddressModel financerAddress = new AddressModel();
		financerAddress.setCity(address.getCity());
		financerAddress.setCountryName(countryEntity.getName() != null ? countryEntity.getName() : "");
		financerAddress.setDistrictName(districtEntity.getName() != null ? districtEntity.getName() : "");
		financerAddress.setDoorNo(address.getDoorNo());
		showcaseNoticeModel.setFinancerAdress(financerAddress);

		AddressModel ownerAddress = new AddressModel();

		switch (OwnershipType.getOwnershipType(vehclRc.getOwnershipType())) {
		case INDIVIDUAL:
			CustIndividualDetailsEntity custIndividualDetailsEntity = customerDAO
					.getByVehicleRcId(vehclRc.getVehicleRcId());
			address = addressDAO.findByUserIdAndType(custIndividualDetailsEntity.getCustIndDtlId(), "T");
			mandalEntity = address.getMandal();
			districtEntity = mandalEntity.getDistrictEntity();
			stateEntity = districtEntity.getStateEntity();
			countryEntity = stateEntity.getCountryEntity();
			showcaseNoticeModel.setCurrentOwnerName(custIndividualDetailsEntity.getDisplayName());
			ownerAddress.setCity(address.getCity());
			ownerAddress.setCountryName(countryEntity.getName() != null ? countryEntity.getName() : "");
			ownerAddress.setDistrictName(districtEntity.getName() != null ? districtEntity.getName() : "");
			ownerAddress.setDoorNo(address.getDoorNo());
			showcaseNoticeModel.setOwnerAdress(ownerAddress);
			break;
		case POLICE:
		case COMPANY:
		case STU_VEHICLES:
		case ORGANIZATION:
			CustCorporateDetailsEntity custCorporateDetailsEntity = customerCorporateDAO
					.getByVehicleRcId(vehclRc.getVehicleRcId());
			showcaseNoticeModel.setCurrentOwnerName(custCorporateDetailsEntity.getDisplayName());
			address = addressDAO.findByUserIdAndType(custCorporateDetailsEntity.getCustCorpDtlsId(), "T");
			mandalEntity = address.getMandal();
			districtEntity = mandalEntity.getDistrictEntity();
			stateEntity = districtEntity.getStateEntity();
			countryEntity = stateEntity.getCountryEntity();
			ownerAddress.setAddressId(address.getAddressId());
			ownerAddress.setCity(address.getCity());
			ownerAddress.setCountryName(countryEntity.getName() != null ? countryEntity.getName() : "");
			ownerAddress.setDistrictName(districtEntity.getName() != null ? districtEntity.getName() : "");
			ownerAddress.setDoorNo(address.getDoorNo());
			showcaseNoticeModel.setOwnerAdress(ownerAddress);
			break;
		case DIPLOMATIC_OFFICER:
			break;
		case GOVERNMENT:
			break;
		default:
			break;
		}
		FinancerFreshRcEntity financerFreshRc = financeFreshDAO
				.getFreshRcFromVehicleRc(vehicelDtls.getVehicleRcId().getVehicleRcId());
		showcaseNoticeModel.setGenerationDate(financerFreshRc.getShowCauseDate());
		String signature = rcCardDetailsService.getCcoSignature(vehicelDtls.getVehicleRcId().getVehicleRcId());
		log.debug("<<<<<<<<<<<<<<<<<<<Signature>>>>>>>>>>>>>>>>>>>>>>>>>>>"+signature);
		if(!StringUtils.isEmpty(signature)){
			showcaseNoticeModel.setCcoSignature(signature);
		}
		return showcaseNoticeModel;
	}

	@Override
	@Transactional
	public FreshRcModel getFinanceFreshRCDtlByRc(Long vehicleRc) throws VehicleRcNotFoundException {
		VehicleRCEntity vehclRc = vehicleDAO.get(vehicleRc);
		if (vehclRc == null)
			throw new VehicleRcNotFoundException();
		FinancerFreshRcEntity freshRcEntity = financeFreshDAO.getFreshRcFromVehicleRc(vehicleRc);
		if (freshRcEntity == null)
			throw new IllegalArgumentException("Fresh Rc application for vehcile" + vehicleRc + " not found.!!");
		FreshRcModel freshRcModel = new FreshRcModel();
		freshRcModel.setAmountDue(freshRcEntity.getAmountDue());
		freshRcModel.setCurrentStep(freshRcEntity.getCurrentStep());
		freshRcModel.setOverDueSince(freshRcEntity.getOverDueSince());
		freshRcModel.setOwnerConsent(freshRcEntity.getOwnerConsent());
		freshRcModel.setVehicleRcId(vehclRc.getVehicleRcId());
		freshRcModel.setVehicleUnderPossession(freshRcEntity.getVehicleUnderPossession());
		freshRcModel.setCreatedBy(freshRcEntity.getCreatedBy());
		if (!ObjectsUtil.isNull(freshRcEntity.getYard())) {
			FinanceYardEntity yard = yardDAO.getEntity(FinanceYardEntity.class, freshRcEntity.getYard());
			if (!ObjectsUtil.isNull(yard)) {
				FinanceYardModel yardModel = new FinanceYardModel();
				yardModel.setActiveStatus(yard.getActiveStatus());
				yardModel.setHeadFinancerId(yard.getHeadFinancer().getUserId());
				// yardModel.setYardAddress(yard.getYardAddress());
				yardModel.setYardArea(yard.getYardArea());
				yardModel.setYardId(yard.getFinanceYardId());
				yardModel.setYardName(yard.getName());
				freshRcModel.setYard(yardModel);
			}

		}
		// freshRcModel.setFinancerId(freshRcEntity.mo.getUserId());
		freshRcModel.setShowCauseDate(freshRcEntity.getShowCauseDate());
		freshRcModel.setShowCauseIssuedBy(freshRcEntity.getShowCauseIssuedBy());
		freshRcModel.setShowCauseDoc(freshRcEntity.getShowCauseDoc());
		return freshRcModel;
	}

	@Override
	@Transactional
	public SaveUpdateResponse saveFinanceShowCauseDetails(String applicationNumber, FreshRcModel freshRcModel) {
		SaveUpdateResponse response = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS);
		if (freshRcModel == null) {
			throw new IllegalArgumentException("Data not received");
		}

		if (ObjectsUtil.isNotNull(freshRcModel)) {
			FinancerFreshRcEntity freshRcEntity = financeFreshDAO.getFreshRcFromApplicationNumber(applicationNumber);
			if (ObjectsUtil.isNotNull(freshRcEntity)) {
				freshRcEntity.setShowCauseDate(freshRcModel.getShowCauseDate());
				freshRcEntity.setShowCauseIssuedBy(freshRcModel.getShowCauseIssuedBy());
				freshRcEntity.setShowCauseDoc(freshRcModel.getShowCauseDoc());
				financeFreshDAO.saveOrUpdate(freshRcEntity);
			} else {
				response.setStatus(SaveUpdateResponse.FAILURE);
				response.setMessage("No data Exist for it.");
			}
		} else {
			response.setStatus(SaveUpdateResponse.FAILURE);
			response.setMessage("Nothing recieved in show cause");
		}

		return response;
	}

	@Override
	@Transactional
	public FreshRcModel getFinanceFreshRCDtlByAppNumber(String appNumber) {
		FinancerFreshRcEntity freshRcEntity = financeFreshDAO.getFreshRcFromApplicationNumber(appNumber);
		if (freshRcEntity == null)
			throw new IllegalArgumentException(
					"Fresh Rc application for application number" + appNumber + " not found.!!");
		FreshRcModel freshRcModel = new FreshRcModel();
		freshRcModel.setAmountDue(freshRcEntity.getAmountDue());
		freshRcModel.setCurrentStep(freshRcEntity.getCurrentStep());
		freshRcModel.setOverDueSince(freshRcEntity.getOverDueSince());
		freshRcModel.setOwnerConsent(freshRcEntity.getOwnerConsent());
		freshRcModel.setVehicleRcId(freshRcEntity.getVehicleRcId().getVehicleRcId());
		freshRcModel.setCreatedBy(freshRcEntity.getCreatedBy());
		freshRcModel.setVehicleUnderPossession(freshRcEntity.getVehicleUnderPossession());
		if (ObjectsUtil.isNotNull(freshRcEntity.getYard())) {
			FinanceYardEntity yard = yardDAO.getEntity(FinanceYardEntity.class, freshRcEntity.getYard());
			if (!ObjectsUtil.isNull(yard)) {
				FinanceYardModel yardModel = new FinanceYardModel();
				yardModel.setActiveStatus(yard.getActiveStatus());
				yardModel.setHeadFinancerId(yard.getHeadFinancer().getUserId());
				// yardModel.setYardAddress(yard.getYardAddress());
				yardModel.setYardArea(yard.getYardArea());
				yardModel.setYardId(yard.getFinanceYardId());
				yardModel.setYardName(yard.getName());
				freshRcModel.setYard(yardModel);
			}
		}
		freshRcModel.setShowCauseDate(freshRcEntity.getShowCauseDate());
		freshRcModel.setShowCauseDoc(freshRcEntity.getShowCauseDoc());
		freshRcModel.setOwnerConscentDate(freshRcEntity.getOwnerConscentDate());
		freshRcModel.setOwnerComment(freshRcEntity.getOwnerComment());
		freshRcModel.setPrNumber(freshRcEntity.getVehicleRcId().getPrNumber());
		freshRcModel.setApplicationNumber(freshRcEntity.getApplicationNumber());
		// freshRcModel.setFinancerId(freshRcEntity.mo.getUserId());
		return freshRcModel;
	}

	@Override
	@Transactional
	public List<FreshRcModel> getOpenedFreshRc() {
		List<FinancerFreshRcEntity> freshRcs = financeFreshDAO.getOpenedApplications();
		FreshRcModel freshRcModel = null;
		List<FreshRcModel> freshRcModels = new ArrayList<FreshRcModel>();  
		for (FinancerFreshRcEntity freshRcEntity : freshRcs) {
			freshRcModel = new FreshRcModel();
			freshRcModel.setAmountDue(freshRcEntity.getAmountDue());
			freshRcModel.setCurrentStep(freshRcEntity.getCurrentStep());
			freshRcModel.setOverDueSince(freshRcEntity.getOverDueSince());
			freshRcModel.setOwnerConsent(freshRcEntity.getOwnerConsent());
			freshRcModel.setVehicleRcId(freshRcEntity.getVehicleRcId().getVehicleRcId());
			freshRcModel.setCreatedBy(freshRcEntity.getCreatedBy());
			freshRcModel.setCreatedOn(freshRcEntity.getCreatedOn());
			freshRcModel.setVehicleUnderPossession(freshRcEntity.getVehicleUnderPossession());
			if (ObjectsUtil.isNotNull(freshRcEntity.getYard())) {
				FinanceYardEntity yard = yardDAO.getEntity(FinanceYardEntity.class, freshRcEntity.getYard());
				if (!ObjectsUtil.isNull(yard)) {
					FinanceYardModel yardModel = new FinanceYardModel();
					yardModel.setActiveStatus(yard.getActiveStatus());
					yardModel.setHeadFinancerId(yard.getHeadFinancer().getUserId());
					// yardModel.setYardAddress(yard.getYardAddress());
					yardModel.setYardArea(yard.getYardArea());
					yardModel.setYardId(yard.getFinanceYardId());
					yardModel.setYardName(yard.getName());
					freshRcModel.setYard(yardModel);
				}
			}
			freshRcModel.setShowCauseDate(freshRcEntity.getShowCauseDate());
			freshRcModel.setShowCauseDoc(freshRcEntity.getShowCauseDoc());
			freshRcModel.setOwnerConscentDate(freshRcEntity.getOwnerConscentDate());
			freshRcModel.setOwnerComment(freshRcEntity.getOwnerComment());
			freshRcModel.setPrNumber(freshRcEntity.getVehicleRcId().getPrNumber());
			freshRcModel.setApplicationNumber(freshRcEntity.getApplicationNumber());
			freshRcModels.add(freshRcModel);
		}
		
		return freshRcModels;
	}
	
}
