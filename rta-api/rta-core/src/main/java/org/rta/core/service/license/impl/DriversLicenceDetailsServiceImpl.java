package org.rta.core.service.license.impl;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.constant.SomeConstants;
import org.rta.core.dao.license.DriversLicenceDetailsDAO;
import org.rta.core.dao.license.LearnersPermitDtlDAO;
import org.rta.core.dao.license.LicenceIdpDtlsDAO;
import org.rta.core.dao.license.LicenseHolderDtlsDAO;
import org.rta.core.dao.user.RTAEmployeeDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.entity.licence.DriversLicenceDetailsEntity;
import org.rta.core.entity.licence.DriversLicenseDtlPK;
import org.rta.core.entity.licence.LearnersPermitDtlEntity;
import org.rta.core.entity.licence.LicenseHolderDtlsEntity;
import org.rta.core.entity.licence.LicenseIdpDtlEntity;
import org.rta.core.entity.licence.LicenseIdpDtlPK;
import org.rta.core.entity.user.RTAEmployeeEntity;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.enums.license.LicenceVehicleClass;
import org.rta.core.exception.NotFoundException;
import org.rta.core.helper.license.DriversLicenceDetailsHelper;
import org.rta.core.helper.license.LicenseIdpDtlHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.license.DriversLicenceDetailsModel;
import org.rta.core.model.license.LearnersPermitDtlModel;
import org.rta.core.model.license.LicenseHolderDtlsModel;
import org.rta.core.model.license.LicenseHolderPermitDetails;
import org.rta.core.model.license.LicenseIdpDtlModel;
import org.rta.core.model.license.SupensionCancellationModel;
import org.rta.core.model.license.SuspensionRevocationModel;
import org.rta.core.service.license.DriversLicenceDetailsService;
import org.rta.core.service.license.LicenseHolderDtlsService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DriversLicenceDetailsServiceImpl implements DriversLicenceDetailsService {

	private static final Logger logger = Logger.getLogger(DriversLicenceDetailsServiceImpl.class);

	@Autowired
	private DriversLicenceDetailsHelper driversLicenceDetailsHelper;

	@Autowired
	private LicenseIdpDtlHelper licenseIdpDtlHelper;

	@Autowired
	private DriversLicenceDetailsDAO driversLicenceDetailsDAO;

	@Autowired
	private LicenceIdpDtlsDAO licenceIdpDtlsDAO;

	@Autowired
	private LicenseHolderDtlsDAO licenseHolderDtlsDAO;

	@Autowired
	private LearnersPermitDtlDAO learnersPermitDtlDAO;

	@Autowired
	private LicenseHolderDtlsService licenseHolderDtlsService;

	@Autowired
	private RTAEmployeeDAO rtaEmployeeDAO;

	@Value("${rta.employee.signature.path}")
	private String signaturePath;

	@Value("${base.url}")
	private String baseURL;

	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional
	public List<DriversLicenceDetailsModel> getDriverDtls(String aadharNo, String llOrdlNumber)
			throws NotFoundException {

		List<DriversLicenceDetailsModel> model = driversLicenceDetailsHelper
				.convertToModelList(driversLicenceDetailsDAO.getDriversLicenceDetails(llOrdlNumber));
		if (ObjectsUtil.isNull(model)) {
			throw new NotFoundException();
		}
		return model;
	}

	@Override
	@Transactional
	public SaveUpdateResponse saveDriverPermitDetails(LicenseHolderPermitDetails licenseHolderPermitDetails,
			String aadharNumber, String userName) {

		LicenseHolderDtlsEntity entity = licenseHolderDtlsDAO.getLicenseHolderDtls(aadharNumber);
		String msg = "";
		String badgeNumber = "";
		logger.info("Getting  in request saveDriverPermitDetails  " + entity.getLicenceHolderId());
		List<LearnersPermitDtlEntity> learnerPermitList = learnersPermitDtlDAO
				.getLearnersPermitDtl(entity.getLicenceHolderId());
		List<DriversLicenceDetailsEntity> driverPermitList = driversLicenceDetailsDAO
				.getDriverPermitDtl(entity.getLicenceHolderId());
		for (DriversLicenceDetailsEntity driverPermitEntity : driverPermitList) {
			if (!StringsUtil.isNullOrEmpty(driverPermitEntity.getDlBadgeNo())) {
				badgeNumber = driverPermitEntity.getDlBadgeNo();
				break;
			}
		}
		Integer count = driverPermitList.size();
		Set<String> vehicleClass = new HashSet<String>();
		List<DriversLicenceDetailsModel> models = licenseHolderPermitDetails.getDriversPermitDetailsList();
		LicenseHolderDtlsModel licenseHolderDtlsModel = licenseHolderPermitDetails.getLicenseHolderDetails();
		for (DriversLicenceDetailsModel driverPermit : models) {
			count++;
			vehicleClass.add(driverPermit.getDlVehicleClassCode());
			DriversLicenceDetailsEntity driverEntity = driversLicenceDetailsHelper.convertToEntity(driverPermit);
			if (!StringsUtil.isNullOrEmpty(driverPermit.getDlNo())) {
				if (LicenceVehicleClass.HAZARDDOUS_GOODS_CARRIAGE.getCode()
						.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())) {
					driverEntity.setValidTo(DateUtil.addYearToDate(new Date(), SomeConstants.ONE));
				} else if (LicenceVehicleClass.AUTORICKSHAW_TRANSPORT.getCode()
						.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.LIGHT_MOTOR_VEHICLE_TRANSPORT.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.MEDIUM_PASSENGER_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.MEDIUM_GOODS_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.MOTOR_CAB.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.OTHER.getCode().equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.HEAVY_GOODS_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.HEAVY_PASSENGER_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.HEAVY_TRANSPORT_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())) {
					driverEntity.setValidTo(DateUtil.addYearToDate(new Date(), SomeConstants.THREE));
				} else {
					driverEntity.setValidTo(driverPermit.getValidTo());
				}
				if (LicenceVehicleClass.MOTOR_CAB.getCode().equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.AUTORICKSHAW_TRANSPORT.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())) {
					driverEntity.setBadgeIssuedDate(new Date());
					driverEntity.setBadgeRtaOfficeCode(driverPermit.getRtaOfficeDetails().getCode());
					if (!StringsUtil.isNullOrEmpty(badgeNumber)) {
						if (!DateUtil.extractYear(driverEntity.getDateOfFirstIssue())
								.equals(DateUtil.extractYear(new Date()))) {
							driverEntity.setDlBadgeNo(SomeConstants.ONE.toString());
						} else if (!driverEntity.getRtaOfficeId().getCode()
								.equals(licenseHolderDtlsModel.getRtaOfficeDetails().getCode())) {
							driverEntity.setDlBadgeNo(SomeConstants.ONE.toString());
						} else {
							driverEntity.setDlBadgeNo(badgeNumber);
						}
					} else {
						DriversLicenceDetailsEntity dlEntity = driversLicenceDetailsDAO.getLastAppliedBadgeDetails();
						if (ObjectsUtil.isNull(dlEntity)) {
							driverEntity.setDlBadgeNo(SomeConstants.ONE.toString());
						} else {
							driverEntity.setDlBadgeNo(String.valueOf(Integer.valueOf(dlEntity.getDlBadgeNo()) + 1));
						}
					}
				}
			}
			driverEntity.setCreatedBy(userName);
			driverEntity.setCreatedOn(new Date());
			driverEntity.setModifiedBy(userName);
			driverEntity.setModifiedOn(new Date());
			driverEntity.setReferenceId(licenseHolderDtlsService.getReferenceId(entity.getLicenceHolderId(),
					entity.getRtaOfficeId().getCode(), driverPermit.getDlType()));
			driverEntity.setId(new DriversLicenseDtlPK(entity.getLicenceHolderId(), count));
			driversLicenceDetailsDAO.saveOrUpdate(driverEntity);
		}
		if (!ObjectsUtil.isNull(licenseHolderDtlsModel)) {
			if (!StringsUtil.isNullOrEmpty(licenseHolderDtlsModel.getMobileNo())) {
				entity.setMobileNo(licenseHolderDtlsModel.getMobileNo());
			}
			if (!StringsUtil.isNullOrEmpty(licenseHolderDtlsModel.getEmail())) {
				entity.setEmail(licenseHolderDtlsModel.getEmail());
			}
			licenseHolderDtlsDAO.saveOrUpdate(entity);
		}
		if (!StringsUtil.isNullOrEmpty(models.get(0).getDlNo()) && !ObjectsUtil.isNullOrEmpty(learnerPermitList)) {
			for (LearnersPermitDtlEntity lPermitEntity : learnerPermitList) {
				if (!vehicleClass.contains(lPermitEntity.getId().getLlrVehicleClassCode())) {
					learnersPermitDtlDAO.delete(lPermitEntity);
				}
			}
		}
		msg = " Successfully save data";
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, null);
	}

	@Override
	@Transactional
	public SaveUpdateResponse addDriverPermitDetails(LicenseHolderPermitDetails model, String userName) {

		LicenseHolderDtlsModel holderDtlsModel = model.getLicenseHolderDetails();
		List<DriversLicenceDetailsModel> dlDetailsModels = model.getDriversPermitDetailsList();
		LicenseHolderDtlsEntity entity = licenseHolderDtlsDAO.getLicenseHolderDtls(holderDtlsModel.getAadhaarNo());
		if (!StringsUtil.isNullOrEmpty(holderDtlsModel.getMobileNo())
				|| !StringsUtil.isNullOrEmpty(holderDtlsModel.getEmail())) {
			if (!StringsUtil.isNullOrEmpty(holderDtlsModel.getMobileNo())) {
				entity.setMobileNo(holderDtlsModel.getMobileNo());
			}
			if (!StringsUtil.isNullOrEmpty(holderDtlsModel.getEmail())) {
				entity.setEmail(holderDtlsModel.getEmail());
			}
			entity.setModifiedBy(userName);
			entity.setModifiedOn(new Date());
			licenseHolderDtlsDAO.saveOrUpdate(entity);
		}

		String msg = "";
		Set<String> vehicleClass = new HashSet<String>();
		logger.info("Getting  in request addDriverPermitDetails  " + entity.getLicenceHolderId());
		List<LearnersPermitDtlEntity> learnerPermitList = learnersPermitDtlDAO
				.getLearnersPermitDtl(entity.getLicenceHolderId());
		List<DriversLicenceDetailsEntity> driverPermitList = driversLicenceDetailsDAO
				.getDriverPermitDtl(entity.getLicenceHolderId());
		Integer count = driverPermitList.size();
		for (DriversLicenceDetailsModel driverPermit : dlDetailsModels) {
			vehicleClass.add(driverPermit.getDlVehicleClassCode());
			count++;
			DriversLicenceDetailsEntity driverEntity = driversLicenceDetailsHelper.convertToEntity(driverPermit);
			driverEntity.setRtaOfficeId(driverPermitList.get(0).getRtaOfficeId());
			if (SomeConstants.PASS.equalsIgnoreCase(driverPermit.getTestResult())) {
				driverEntity.setDlNo(driverPermitList.get(0).getDlNo());
				driverEntity.setDateOfFirstIssue(driverPermitList.get(0).getDateOfFirstIssue());
				if (LicenceVehicleClass.HAZARDDOUS_GOODS_CARRIAGE.getCode()
						.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())) {
					driverEntity.setValidTo(DateUtil.addYearToDate(new Date(), SomeConstants.ONE));
				} else if (LicenceVehicleClass.AUTORICKSHAW_TRANSPORT.getCode()
						.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.LIGHT_MOTOR_VEHICLE_TRANSPORT.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.MEDIUM_PASSENGER_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.MEDIUM_GOODS_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.MOTOR_CAB.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.OTHER.getCode().equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.HEAVY_GOODS_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.HEAVY_PASSENGER_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.HEAVY_TRANSPORT_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())) {
					driverEntity.setValidTo(DateUtil.addYearToDate(new Date(), SomeConstants.THREE));
				} else {
					driverEntity.setValidTo(driverPermitList.get(0).getValidTo());
				}
			}
			driverEntity.setStatusCode(SomeConstants.VALID);
			driverEntity.setStatusDate(new Date());
			driverEntity.setCreatedBy(userName);
			driverEntity.setCreatedOn(new Date());
			driverEntity.setModifiedBy(userName);
			driverEntity.setModifiedOn(new Date());
			driverEntity.setReferenceId(licenseHolderDtlsService.getReferenceId(entity.getLicenceHolderId(),
					entity.getRtaOfficeId().getCode(), driverPermit.getDlType()));
			driverEntity.setId(new DriversLicenseDtlPK(entity.getLicenceHolderId(), count));
			driversLicenceDetailsDAO.saveOrUpdate(driverEntity);
		}
		if (!ObjectsUtil.isNullOrEmpty(learnerPermitList)) {
			for (LearnersPermitDtlEntity lPermitEntity : learnerPermitList) {
				if (!vehicleClass.contains(lPermitEntity.getId().getLlrVehicleClassCode())) {
					learnersPermitDtlDAO.delete(lPermitEntity);
				}
			}
		}
		msg = " Successfully save data";
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, null);
	}

	@Override
	@Transactional
	public SaveUpdateResponse addDriverPermitDetails(List<DriversLicenceDetailsModel> models, String aadharNumber,
			String userName) {
		String msg = "";
		String badgeNumber = "";
		Set<String> vehicleClass = new HashSet<String>();
		LicenseHolderDtlsEntity entity = licenseHolderDtlsDAO.getLicenseHolderDtls(aadharNumber);
		logger.info("Getting  in request addDriverPermitDetails  " + entity.getLicenceHolderId());
		List<LearnersPermitDtlEntity> learnerPermitList = learnersPermitDtlDAO
				.getLearnersPermitDtl(entity.getLicenceHolderId());
		List<DriversLicenceDetailsEntity> driverPermitList = driversLicenceDetailsDAO
				.getDriverPermitDtl(entity.getLicenceHolderId());
		for (DriversLicenceDetailsEntity driverPermitEntity : driverPermitList) {
			if (!StringsUtil.isNullOrEmpty(driverPermitEntity.getDlBadgeNo())) {
				badgeNumber = driverPermitEntity.getDlBadgeNo();
				break;
			}
		}
		for (DriversLicenceDetailsModel driverPermit : models) {
			DriversLicenceDetailsEntity driverEntity = driversLicenceDetailsDAO
					.getDriversLicenceDetails(driverPermit.getDlVehicleClassCode(), entity.getLicenceHolderId());
			driverEntity = getDriverEnitity(driverEntity, driverPermit);
			if (SomeConstants.PASS.equalsIgnoreCase(driverPermit.getTestResult())) {
				driverEntity.setDlNo(driverPermitList.get(0).getDlNo());
				driverEntity.setDlIssuedDate(new Date());
				driverEntity.setDateOfFirstIssue(driverPermitList.get(0).getDateOfFirstIssue());
				if (LicenceVehicleClass.HAZARDDOUS_GOODS_CARRIAGE.getCode()
						.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())) {
					driverEntity.setValidTo(DateUtil.addYearToDate(new Date(), SomeConstants.ONE));
				} else if (LicenceVehicleClass.AUTORICKSHAW_TRANSPORT.getCode()
						.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.LIGHT_MOTOR_VEHICLE_TRANSPORT.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.MEDIUM_PASSENGER_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.MEDIUM_GOODS_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.MOTOR_CAB.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.OTHER.getCode().equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.HEAVY_GOODS_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.HEAVY_PASSENGER_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
						|| LicenceVehicleClass.HEAVY_TRANSPORT_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())) {
					driverEntity.setValidTo(DateUtil.addYearToDate(new Date(), SomeConstants.THREE));
				}
			}
			if (driverPermit.getDlType().equals(ServiceType.DL_FRESH.getCode())
					&& (LicenceVehicleClass.MOTOR_CAB.getCode().equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
							|| LicenceVehicleClass.AUTORICKSHAW_TRANSPORT.getCode()
									.equalsIgnoreCase(driverPermit.getDlVehicleClassCode()))) {
				driverEntity.setBadgeIssuedDate(new Date());
				driverEntity.setBadgeRtaOfficeCode(driverPermit.getRtaOfficeDetails().getCode());
				if (!StringsUtil.isNullOrEmpty(badgeNumber)) {
					if (!DateUtil.extractYear(driverEntity.getDateOfFirstIssue())
							.equals(DateUtil.extractYear(new Date()))) {
						driverEntity.setDlBadgeNo(SomeConstants.ONE.toString());
					} else if (!driverEntity.getRtaOfficeId().getCode().equals(entity.getRtaOfficeId().getCode())) {
						driverEntity.setDlBadgeNo(SomeConstants.ONE.toString());
					} else {
						driverEntity.setDlBadgeNo(badgeNumber);
					}
				} else {
					DriversLicenceDetailsEntity dlEntity = driversLicenceDetailsDAO.getLastAppliedBadgeDetails();
					if (ObjectsUtil.isNull(dlEntity)) {
						driverEntity.setDlBadgeNo(SomeConstants.ONE.toString());
					} else {
						driverEntity.setDlBadgeNo(String.valueOf(Integer.valueOf(dlEntity.getDlBadgeNo()) + 1));
					}
				}
			}
			driverEntity.setStatusCode("V");
			driverEntity.setStatusDate(new Date());
			driverEntity.setCreatedBy(userName);
			driverEntity.setCreatedOn(new Date());
			driverEntity.setModifiedBy(userName);
			driverEntity.setModifiedOn(new Date());
			driverEntity.setDlType(ServiceType.DL_RETEST.getCode());
			driverEntity.setReferenceId(licenseHolderDtlsService.getReferenceId(entity.getLicenceHolderId(),
					entity.getRtaOfficeId().getCode(), driverPermit.getDlType()));
			driversLicenceDetailsDAO.saveOrUpdate(driverEntity);
		}
		if (SomeConstants.PASS.equalsIgnoreCase(models.get(0).getTestResult())
				&& !ObjectsUtil.isNullOrEmpty(learnerPermitList)) {
			for (LearnersPermitDtlEntity lPermitEntity : learnerPermitList) {
				if (!vehicleClass.contains(lPermitEntity.getId().getLlrVehicleClassCode())) {
					learnersPermitDtlDAO.delete(lPermitEntity);
				}
			}
		}
		msg = " Successfully save data";
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, null);
	}

	@Override
	@Transactional
	public SaveUpdateResponse updateDriverLicenceCommonService(String aadharNumber,
			List<DriversLicenceDetailsModel> models, String userName) {

		LicenseHolderDtlsEntity entity = licenseHolderDtlsDAO.getLicenseHolderDtls(aadharNumber);
		String msg = "";
		logger.info("Getting  in request updateDriverLicenceCommonService  " + entity.getLicenceHolderId());
		if (ServiceType.DL_BADGE.getCode().equalsIgnoreCase(models.get(0).getDlType())) {
			String badgeNumber = "";
			List<DriversLicenceDetailsEntity> driverPermitList = driversLicenceDetailsDAO
					.getDriverPermitDtl(entity.getLicenceHolderId());
			for (DriversLicenceDetailsEntity driverPermitEntity : driverPermitList) {
				if (!StringsUtil.isNullOrEmpty(driverPermitEntity.getDlBadgeNo())) {
					badgeNumber = driverPermitEntity.getDlBadgeNo();
					break;
				}
			}
			for (DriversLicenceDetailsEntity driverPermitEntity : driverPermitList) {
				if (LicenceVehicleClass.AUTORICKSHAW_TRANSPORT.getCode()
						.equalsIgnoreCase(driverPermitEntity.getDlVehicleClassCode())
						|| LicenceVehicleClass.HEAVY_PASSENGER_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermitEntity.getDlVehicleClassCode())
						|| LicenceVehicleClass.LIGHT_MOTOR_VEHICLE_TRANSPORT.getCode()
								.equalsIgnoreCase(driverPermitEntity.getDlVehicleClassCode())
						|| LicenceVehicleClass.MEDIUM_PASSENGER_VEHICLE.getCode()
								.equalsIgnoreCase(driverPermitEntity.getDlVehicleClassCode())
						|| LicenceVehicleClass.MOTOR_CAB.getCode()
								.equalsIgnoreCase(driverPermitEntity.getDlVehicleClassCode())) {
					driverPermitEntity.setBadgeIssuedDate(new Date());
					if (!StringsUtil.isNullOrEmpty(badgeNumber)) {
						if (!DateUtil.extractYear(driverPermitEntity.getDateOfFirstIssue())
								.equals(DateUtil.extractYear(new Date()))) {
							driverPermitEntity.setDlBadgeNo(SomeConstants.ONE.toString());
						} else if (!driverPermitEntity.getRtaOfficeId().getCode()
								.equals(entity.getRtaOfficeId().getCode())) {
							driverPermitEntity.setDlBadgeNo(SomeConstants.ONE.toString());
						} else {
							driverPermitEntity.setDlBadgeNo(badgeNumber);
						}
					} else {
						DriversLicenceDetailsEntity dlEntity = driversLicenceDetailsDAO.getLastAppliedBadgeDetails();
						if (ObjectsUtil.isNull(dlEntity)) {
							driverPermitEntity.setDlBadgeNo(SomeConstants.ONE.toString());
						} else {
							driverPermitEntity
									.setDlBadgeNo(String.valueOf(Integer.valueOf(dlEntity.getDlBadgeNo()) + 1));
						}
					}
					driverPermitEntity.setBadgeRtaOfficeCode(driverPermitEntity.getRtaOfficeId().getCode());
					driverPermitEntity.setModifiedBy(userName);
					driverPermitEntity.setModifiedOn(new Date());
					driversLicenceDetailsDAO.update(driverPermitEntity);
				}
			}
		} else {
			// DL RENEWAL/EXPIRRED BLOCK
			List<DriversLicenceDetailsEntity> entities = driversLicenceDetailsDAO
					.getDriverPermitDtl(entity.getLicenceHolderId());

			for (DriversLicenceDetailsEntity driverPermitEntity : entities) {
				// boolean exits = true;
				for (DriversLicenceDetailsModel model : models) {
					if (driverPermitEntity.getDlVehicleClassCode().equalsIgnoreCase(model.getDlVehicleClassCode())) {
						driverPermitEntity.setApplicationId(model.getApplicationId());
						driverPermitEntity.setDlType(model.getDlType());
						driverPermitEntity.setRenewalFlag(model.getRenewalFlag());
						driverPermitEntity.setRetestFlag(model.getRetestFlag());
						driverPermitEntity.setValidFlg(model.getValidFlg());
						driverPermitEntity.setValidTo(model.getValidTo());
						driverPermitEntity.setValidFrom(model.getValidFrom());
						driverPermitEntity.setStatusCode("V");
						driverPermitEntity.setStatusDate(new Date());
						// exits = false;
						break;
					}
				}
				// if (exits) {
				// driverPermitEntity.setStatusCode("I");
				// driverPermitEntity.setStatusDate(new Date());
				// }
				driverPermitEntity.setModifiedBy(userName);
				driverPermitEntity.setModifiedOn(new Date());
				driversLicenceDetailsDAO.update(driverPermitEntity);
			}
		}
		msg = " Successfully update data";
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, null);
	}

	@Override
	@Transactional
	public String getEmployeeSignature(Long userId) {
		RTAEmployeeEntity rtaEmployee = rtaEmployeeDAO.getByUserId(userId);
		if (rtaEmployee == null || StringsUtil.isNullOrEmpty(rtaEmployee.getSignFileName())) {
			return null;
		}
		return baseURL + File.separator + signaturePath + File.separator + rtaEmployee.getSignFileName();
	}

	@Override
	@Transactional
	public SaveUpdateResponse saveInternationalDriverPermitDetails(LicenseIdpDtlModel licenseIdpDtlModel,
			String aadharNumber, String userName) {
		LicenseHolderDtlsEntity licenseHolderDtlsEntity = licenseHolderDtlsDAO.getLicenseHolderDtls(aadharNumber);
		String msg = "";
		logger.info("Getting  in request saveInternationalDriverPermitDetails  "
				+ licenseHolderDtlsEntity.getLicenceHolderId());
		List<DriversLicenceDetailsEntity> driversLicenceDetailsList = driversLicenceDetailsDAO
				.getDriverPermitDtl(licenseHolderDtlsEntity.getLicenceHolderId());
		List<LicenseIdpDtlEntity> licenseIdpDtlList = licenceIdpDtlsDAO
				.getLicenseIdpDtl(licenseHolderDtlsEntity.getLicenceHolderId());
		Integer count = 0;
		if (!ObjectsUtil.isNull(licenseIdpDtlList) && licenseIdpDtlList.size() > 0) {
			for (LicenseIdpDtlEntity licenseIdpDtl : licenseIdpDtlList) {
				licenseIdpDtl.setStatus(Status.EXPIRED.getValue());
				licenceIdpDtlsDAO.saveOrUpdate(licenseIdpDtl);
			}
			count = licenseIdpDtlList.size();
		}
		for (DriversLicenceDetailsEntity driversLicenceDetails : driversLicenceDetailsList) {
			count++;
			Date date = new Date();
			licenseIdpDtlModel.setIdpVehicleClassCode(driversLicenceDetails.getDlVehicleClassCode());
			LicenseIdpDtlEntity licenseIdpDtlEntity = licenseIdpDtlHelper.convertToEntity(licenseIdpDtlModel);
			licenseIdpDtlEntity.setCreatedBy(userName);
			licenseIdpDtlEntity.setCreatedOn(date);
			licenseIdpDtlEntity.setModifiedBy(userName);
			licenseIdpDtlEntity.setModifiedOn(date);
			licenseIdpDtlEntity.setId(new LicenseIdpDtlPK(licenseHolderDtlsEntity.getLicenceHolderId(), count));
			licenceIdpDtlsDAO.saveOrUpdate(licenseIdpDtlEntity);
		}
		msg = " Successfully save data";
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, null);
	}

	private DriversLicenceDetailsEntity getDriverEnitity(DriversLicenceDetailsEntity entity,
			DriversLicenceDetailsModel source) {

		entity.setAppId(source.getAppId());
		entity.setApplicationId(source.getApplicationId());
		entity.setBadgeIssuedDate(source.getBadgeIssuedDate());
		entity.setBadgeRtaOfficeCode(source.getBadgeRtaOfficeCode());
		entity.setDateOfFirstIssue(source.getDateOfFirstIssue());
		entity.setDlIssuedDate(source.getDlIssuedDate());
		entity.setDlNo(source.getDlNo());
		entity.setDlType(source.getDlType());
		entity.setDlVehicleClassCode(source.getDlVehicleClassCode());
		entity.setDrivingSchoolLicenseNo(source.getDrivingSchoolLicenseNo());
		entity.setIsTrained(source.getIsTrained());
		entity.setLlrNo(source.getLlrNo());
		entity.setModuleCd(source.getModuleCd());
		entity.setObservation(source.getObservation());
		entity.setPhotoAttachmentId(source.getPhotoAttachmentId());
		entity.setPlannedValidFrom(source.getPlannedValidFrom());
		entity.setPlannedValidTo(source.getPlannedValidTo());
		entity.setReferenceId(source.getReferenceId());
		entity.setRenewalFlag(source.getRenewalFlag());
		entity.setRetestFlag(source.getRetestFlag());
		entity.setRetestReason(source.getRetestReason());
		entity.setSignAttachmentId(source.getSignAttachmentId());
		entity.setStatusCode(source.getStatusCode());
		entity.setStatusDate(source.getStatusDate());
		entity.setStatusRemarks(source.getStatusRemarks());
		entity.setStatusValidFrom(source.getStatusValidFrom());
		entity.setStatusUpdatedBy(source.getStatusUpdatedBy());
		entity.setStatusValidTo(source.getStatusValidTo());
		entity.setTestDate(source.getTestDate());
		entity.setTestExempted(source.getTestExempted());
		entity.setTestExemptedReason(source.getTestExemptedReason());
		entity.setTestId(source.getTestId());
		entity.setTestResult(source.getTestResult());
		entity.setTicketDetails(source.getTicketDetails());
		entity.setValidFlg(source.getValidFlg());
		entity.setValidFrom(source.getValidFrom());
		entity.setValidTo(source.getValidTo());
		if (!ObjectsUtil.isNull(source.getAoUserDetails())) {
			if (!ObjectsUtil.isNull(source.getAoUserDetails().getUserId())) {
				entity.setAoUserId(userDAO.findByUserId(source.getAoUserDetails().getUserId()));
			}
		}
		if (!ObjectsUtil.isNull(source.getMviUserDetails())) {
			if (!ObjectsUtil.isNull(source.getMviUserDetails().getUserId())) {
				entity.setMviUserId(userDAO.findByUserId(source.getAoUserDetails().getUserId()));
			}
		}
		return entity;
	}

	@Override
	@Transactional
	public SaveUpdateResponse suspendCancelLicense(SupensionCancellationModel supensionCancellationModel,
			String dLNumber, String userName) {
		String msg = "";
		logger.info("Getting  in request suspendCancelLicense  " + dLNumber);
		List<DriversLicenceDetailsEntity> driversLicenceDetailsList = driversLicenceDetailsDAO
				.getDriversLicenceDetails(dLNumber);
		LicenseHolderDtlsEntity licenseHolderDtlsEntity = licenseHolderDtlsDAO
				.getLicenseHolderDtlsByHolderId(driversLicenceDetailsList.get(0).getId().getLicenceHolderId());
		licenseHolderDtlsEntity.setIsActive("N");
		licenseHolderDtlsDAO.saveOrUpdate(licenseHolderDtlsEntity);
		for (DriversLicenceDetailsEntity driversLicenceDetails : driversLicenceDetailsList) {
			driversLicenceDetails.setStatusRemarks(supensionCancellationModel.getSuspensionType());
			driversLicenceDetails.setDlType(ServiceType.DL_SUSU_CANC.getCode());
			driversLicenceDetails.setValidFlg("N");
			driversLicenceDetails.setStatusUpdatedOn(new Date());
			if (supensionCancellationModel.getSuspensionType().equals("SUSPEND")) {
				driversLicenceDetails.setStatusValidFrom(supensionCancellationModel.getFromDate());
				driversLicenceDetails.setStatusValidTo(supensionCancellationModel.getToDate());
			}
			driversLicenceDetailsDAO.saveOrUpdate(driversLicenceDetails);
		}
		msg = " Successfully save data";
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, null);
	}

	@Override
	@Transactional
	public SaveUpdateResponse licenseRevokeSuspension(SuspensionRevocationModel suspensionRevocationModel,
			String dLNumber, String userName) {
		String msg = "";
		logger.info("Getting  in request licenseRevokeSuspension  " + dLNumber);
		List<DriversLicenceDetailsEntity> driversLicenceDetailsList = driversLicenceDetailsDAO
				.getDriversLicenceDetails(dLNumber);
		LicenseHolderDtlsEntity licenseHolderDtlsEntity = licenseHolderDtlsDAO
				.getLicenseHolderDtlsByHolderId(driversLicenceDetailsList.get(0).getId().getLicenceHolderId());
		licenseHolderDtlsEntity.setIsActive("Y");
		licenseHolderDtlsDAO.saveOrUpdate(licenseHolderDtlsEntity);
		for (DriversLicenceDetailsEntity driversLicenceDetails : driversLicenceDetailsList) {
			driversLicenceDetails.setStatusRemarks(suspensionRevocationModel.getRemarks());
			driversLicenceDetails.setDlType(ServiceType.DL_REVO_SUS.getCode());
			driversLicenceDetails.setValidFlg("Y");
			driversLicenceDetails.setStatusUpdatedOn(new Date());
			driversLicenceDetailsDAO.saveOrUpdate(driversLicenceDetails);
		}
		msg = " Successfully save data";
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, null);
	}

}
