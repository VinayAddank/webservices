package org.rta.core.service.license.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.constant.SomeConstants;
import org.rta.core.dao.aadhar.AadhaTCSDAO;
import org.rta.core.dao.license.DriversLicenceDetailsDAO;
import org.rta.core.dao.license.LearnersPermitDtlDAO;
import org.rta.core.dao.license.LicenseHolderDtlsDAO;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.master.MandalDAO;
import org.rta.core.dao.master.QualificationDAO;
import org.rta.core.dao.master.StateDAO;
import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.entity.applicant.AadharEntity;
import org.rta.core.entity.licence.DriversLicenceDetailsEntity;
import org.rta.core.entity.licence.DriversLicenseDtlPK;
import org.rta.core.entity.licence.LearnersPermitDtlEntity;
import org.rta.core.entity.licence.LearnersPermitDtlPK;
import org.rta.core.entity.licence.LicenseHolderDtlsEntity;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.license.LicenceVehicleClass;
import org.rta.core.exception.DataMismatchException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.helper.license.DriversLicenceDetailsHelper;
import org.rta.core.helper.license.LearnersPermitDtlHelper;
import org.rta.core.helper.license.LicenseHolderDtlsHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.license.DriversLicenceDetailsModel;
import org.rta.core.model.license.ForgetLicenseModel;
import org.rta.core.model.license.LearnersPermitDtlModel;
import org.rta.core.model.license.LicenseHolderDtlsModel;
import org.rta.core.model.license.LicenseHolderPermitDetails;
import org.rta.core.service.license.LicenseHolderDtlsService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenseHolderDtlsServiceImpl implements LicenseHolderDtlsService {

	private static final Logger logger = Logger.getLogger(LicenseHolderDtlsServiceImpl.class);

	@Autowired
	private LicenseHolderDtlsDAO licenseHolderDtlsDAO;

	@Autowired
	private LicenseHolderDtlsHelper licenseHolderDtlsHelper;

	@Autowired
	private LearnersPermitDtlDAO learnersPermitDtlDAO;

	@Autowired
	private LearnersPermitDtlHelper learnersPermitDtlHelper;

	@Autowired
	private DriversLicenceDetailsHelper driversLicenceDetailsHelper;

	@Autowired
	private DriversLicenceDetailsDAO driversLicenceDetailsDAO;

	@Autowired
	private DistrictDAO districtDAO;

	@Autowired
	private MandalDAO mandalDAO;

	@Autowired
	private StateDAO stateDAO;

	@Autowired
	private AadhaTCSDAO aadharDAO;

	@Autowired
	private RtaOfficeDAO rtaOfficeDAO;

	@Autowired
	private QualificationDAO qualificationDAO;

	@Override
	@Transactional
	public LicenseHolderPermitDetails getLicenseHolderDtls(String aadharNo, Long licenceHolderId, String uniqueKey)
			throws NotFoundException {
		LicenseHolderPermitDetails licenseHolderPermitDetails = new LicenseHolderPermitDetails();

		if (!StringsUtil.isNullOrEmpty(aadharNo) && !StringsUtil.isNullOrEmpty(uniqueKey)) {
			LicenseHolderDtlsModel model = licenseHolderDtlsHelper
					.convertToModel(licenseHolderDtlsDAO.getLicenseHolderDtls(aadharNo));
			licenseHolderPermitDetails.setLicenseHolderDetails(model);
			licenseHolderPermitDetails.setLearnersPermitDetailsList(learnersPermitDtlHelper
					.convertToModelList(learnersPermitDtlDAO.getLearnersPermitDetails(uniqueKey)));
		} else if (!StringsUtil.isNullOrEmpty(aadharNo)) {
			LicenseHolderDtlsModel model = licenseHolderDtlsHelper
					.convertToModel(licenseHolderDtlsDAO.getLicenseHolderDtls(aadharNo));
			licenseHolderPermitDetails.setLicenseHolderDetails(model);
			licenseHolderPermitDetails.setLearnersPermitDetailsList(learnersPermitDtlHelper
					.convertToModelList(learnersPermitDtlDAO.getLearnersPermitDtl(model.getLicenceHolderId())));
			List<DriversLicenceDetailsModel> driversPermitDetailsList = driversLicenceDetailsHelper
					.convertToModelList(driversLicenceDetailsDAO.getDriverPermitDtl(model.getLicenceHolderId()));
			licenseHolderPermitDetails.setDriversPermitDetailsList(driversPermitDetailsList);

		} else if (!ObjectsUtil.isNull(licenceHolderId)) {
			LicenseHolderDtlsModel model = licenseHolderDtlsHelper
					.convertToModel(licenseHolderDtlsDAO.getEntity(LicenseHolderDtlsEntity.class, licenceHolderId));
			licenseHolderPermitDetails.setLicenseHolderDetails(model);
			licenseHolderPermitDetails.setLearnersPermitDetailsList(learnersPermitDtlHelper
					.convertToModelList(learnersPermitDtlDAO.getLearnersPermitDtl(licenceHolderId)));
		} else if (!StringsUtil.isNullOrEmpty(uniqueKey)) {
			licenseHolderPermitDetails.setLearnersPermitDetailsList(learnersPermitDtlHelper
					.convertToModelList(learnersPermitDtlDAO.getLearnersPermitDetails(uniqueKey)));
		}
		if (ObjectsUtil.isNull(licenseHolderPermitDetails)) {
			throw new NotFoundException();
		}

		return licenseHolderPermitDetails;
	}

	@Override
	@Transactional
	public SaveUpdateResponse saveLicenseHolderDtls(LicenseHolderDtlsModel model, String userName) {
		LicenseHolderDtlsEntity entity = licenseHolderDtlsDAO.getLicenseHolderDtls(model.getAadhaarNo());
		String msg = "";
		try {
			if (ObjectsUtil.isNull(entity)) {
				entity = licenseHolderDtlsHelper.convertToEntity(model);
				entity.setCreatedBy(userName);
				entity.setCreatedOn(new Date());
				msg = " Successfully save data";
			} else {
				entity = licenseHolderDtlsHelper.convertToEntity(model);
				entity.setCreatedBy(entity.getCreatedBy());
				entity.setCreatedOn(entity.getCreatedOn());
				entity.setLicenceHolderId(entity.getLicenceHolderId());
				msg = " Successfully update data";
			}
			entity.setModifiedBy(userName);
			entity.setModifiedOn(new Date());

			licenseHolderDtlsDAO.saveOrUpdate(entity);

			logger.info("Getting  in request response  ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, model.getAadhaarNo());
	}

	@Override
	@Transactional
	public SaveUpdateResponse saveOnlyLicenseHolderDtlsAndPermitDetails(LicenseHolderPermitDetails models,
			String userName) throws Exception {
		LicenseHolderDtlsModel holder = models.getLicenseHolderDetails();
		List<LearnersPermitDtlModel> permits = models.getLearnersPermitDetailsList();
		List<DriversLicenceDetailsModel> detailsModels = models.getDriversPermitDetailsList();
		LicenseHolderDtlsEntity entity = licenseHolderDtlsDAO.getLicenseHolderDtls(holder.getAadhaarNo());
		String msg = "";
		if (ObjectsUtil.isNull(entity)) {
			entity = licenseHolderDtlsHelper.convertToEntity(holder);
			entity = getPermanentAddress(entity, holder.getIsSameAsAadhaar());
			entity.setCreatedBy(userName);
			entity.setCreatedOn(new Date());
			msg = " Successfully save data";
		}
		entity.setModifiedBy(userName);
		entity.setModifiedOn(new Date());
		licenseHolderDtlsDAO.saveOrUpdate(entity);
		logger.info("Getting  in request LearnersPermitDtlEntity  " + entity.getLicenceHolderId());
		if (!ObjectsUtil.isNull(permits) && permits.size() > 0) {
			List<LearnersPermitDtlEntity> permitData = learnersPermitDtlDAO
					.getLearnersPermitDtl(entity.getLicenceHolderId());
			Integer count = permitData.size();
			for (LearnersPermitDtlModel permit : permits) {
				count++;

				LearnersPermitDtlEntity permitEntity = learnersPermitDtlHelper.convertToEntity(permit);
				permitEntity.setCreatedBy(userName);
				permitEntity.setCreatedOn(new Date());
				permitEntity.setModifiedBy(userName);
				permitEntity.setModifiedOn(new Date());
				permitEntity.setReferenceId(getReferenceId(entity.getLicenceHolderId(),
						entity.getRtaOfficeId().getCode(), permitEntity.getLlrNoType()));
				permitEntity.setId(
						new LearnersPermitDtlPK(entity.getLicenceHolderId(), count, permit.getLlrVehicleClassCode()));
				msg = " Successfully save data";
				learnersPermitDtlDAO.saveOrUpdate(permitEntity);
			}
		}
		if (!ObjectsUtil.isNull(detailsModels) && detailsModels.size() > 0) {
			Integer count = SomeConstants.ONE;
			for (DriversLicenceDetailsModel driverPermit : detailsModels) {
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
							|| LicenceVehicleClass.OTHER.getCode()
									.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
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
					if (driverPermit.getDlType().equals(ServiceType.DL_FRESH.getCode())
							|| driverPermit.getDlType().equals(ServiceType.DL_RETEST.getCode())) {
						if (LicenceVehicleClass.MOTOR_CAB.getCode()
								.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())
								|| LicenceVehicleClass.AUTORICKSHAW_TRANSPORT.getCode()
										.equalsIgnoreCase(driverPermit.getDlVehicleClassCode())) {
							driverEntity.setBadgeIssuedDate(new Date());
							driverEntity.setBadgeRtaOfficeCode(driverPermit.getRtaOfficeDetails().getCode());
							String badgeNumber = driverPermit.getRtaOfficeDetails().getCode();
							if (driverEntity.getRtaOfficeId().getRtaOfficeId() != null) {
								driverEntity.setDlBadgeNo(badgeNumber + driverEntity.getRtaOfficeId().getRtaOfficeId());
							}
						}
					}
				}
				driverEntity.setCreatedBy(userName);
				driverEntity.setCreatedOn(new Date());
				driverEntity.setModifiedBy(userName);
				driverEntity.setModifiedOn(new Date());
				driverEntity.setReferenceId(getReferenceId(entity.getLicenceHolderId(),
						entity.getRtaOfficeId().getCode(), driverPermit.getDlType()));
				driverEntity.setId(new DriversLicenseDtlPK(entity.getLicenceHolderId(), count));
				driversLicenceDetailsDAO.saveOrUpdate(driverEntity);
				count++;
			}
		}
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, holder.getAadhaarNo());
	}

	@Override
	@Transactional
	public LicenseHolderPermitDetails getDriverLicenseHolderDetails(String aadharNo, String uniqueKey)
			throws NotFoundException {
		LicenseHolderPermitDetails licenseHolderPermitDetails = new LicenseHolderPermitDetails();

		if (!StringsUtil.isNullOrEmpty(aadharNo) && !StringsUtil.isNullOrEmpty(uniqueKey)) {

			LicenseHolderDtlsModel model = licenseHolderDtlsHelper
					.convertToModel(licenseHolderDtlsDAO.getLicenseHolderDtls(aadharNo));
			licenseHolderPermitDetails.setLicenseHolderDetails(model);
			licenseHolderPermitDetails.setLearnersPermitDetailsList(learnersPermitDtlHelper
					.convertToModelList(learnersPermitDtlDAO.getLearnersPermitDtl(model.getLicenceHolderId())));
			licenseHolderPermitDetails.setDriversPermitDetailsList(driversLicenceDetailsHelper
					.convertToModelList(driversLicenceDetailsDAO.getDriversLicenceDetails(uniqueKey)));
		} else if (!StringsUtil.isNullOrEmpty(aadharNo)) {

			LicenseHolderDtlsModel model = licenseHolderDtlsHelper
					.convertToModel(licenseHolderDtlsDAO.getLicenseHolderDtls(aadharNo));
			licenseHolderPermitDetails.setLicenseHolderDetails(model);
			licenseHolderPermitDetails.setLearnersPermitDetailsList(learnersPermitDtlHelper
					.convertToModelList(learnersPermitDtlDAO.getLearnersPermitDtl(model.getLicenceHolderId())));
			licenseHolderPermitDetails.setDriversPermitDetailsList(driversLicenceDetailsHelper
					.convertToModelList(driversLicenceDetailsDAO.getDriverPermitDtl(model.getLicenceHolderId())));
		} else if (!StringsUtil.isNullOrEmpty(uniqueKey)) {
			licenseHolderPermitDetails.setDriversPermitDetailsList(driversLicenceDetailsHelper
					.convertToModelList(driversLicenceDetailsDAO.getDriversLicenceDetails(uniqueKey)));
		}
		if (ObjectsUtil.isNull(licenseHolderPermitDetails)) {
			throw new NotFoundException();
		}

		return licenseHolderPermitDetails;
	}

	@Override
	@Transactional
	public SaveUpdateResponse updateLicenseHodlerDetails(LicenseHolderDtlsModel model, String userName)
			throws DataMismatchException {

		LicenseHolderDtlsEntity entity = licenseHolderDtlsDAO.getLicenseHolderDtls(model.getAadhaarNo());
		if (ObjectsUtil.isNull(entity)) {
			throw new DataMismatchException();
		}
		if (!ObjectsUtil.isNull(model.getQualificationDetails().getCode())) {
			entity.setQualificationId(qualificationDAO.getByCode(model.getQualificationDetails().getCode()));
		}
		entity.setEmail(model.getEmail());
		entity.setMobileNo(model.getMobileNo());
		entity.setPresAddrDoorNo(model.getPresAddrDoorNo());
		entity.setPresAddrStreet(model.getPresAddrStreet());
		entity.setPresAddrTown(model.getPresAddrTown());
		entity.setPresAddrMandalId(mandalDAO.getByMandalCode(model.getMandalDetails().getCode()));
		entity.setPresAddrDistrictId(districtDAO.getDistrictByCode(model.getDistrictDetails().getCode()));
		entity.setPresAddrPinCode(model.getPresAddrPinCode());
		entity.setPresAddrStateId(stateDAO.getStateByCode(model.getStateDetails().getCode()));
		entity.setPresAddrCountryId(1l);
		entity.setRtaOfficeId(rtaOfficeDAO.getRtaOfficeDetailsByCode(model.getRtaOfficeDetails().getCode()));
		entity.setModifiedBy(userName);
		entity.setModifiedOn(new Date());
		entity.setSameAsAadhaar(model.getIsSameAsAadhaar());
		licenseHolderDtlsDAO.saveOrUpdate(entity);
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "update Successfully..", null);
	}

	@Override
	@Transactional
	public List<String> getLicensePermitVehicle(String aadharNumber, String showVehicleClass) throws NotFoundException {
		List<String> vehicleList = new ArrayList<>();
		Set<String> set = new HashSet<String>();
		LicenseHolderDtlsEntity holderDtlsEntity = licenseHolderDtlsDAO.getLicenseHolderDtls(aadharNumber);
		if (StringsUtil.isNullOrEmpty(showVehicleClass)) {
			List<LearnersPermitDtlEntity> learnersPermitDtlEntities = learnersPermitDtlDAO
					.getLearnersPermitDtl(holderDtlsEntity.getLicenceHolderId());
			if (!ObjectsUtil.isNull(learnersPermitDtlEntities)) {
				for (LearnersPermitDtlEntity lDtlEntity : learnersPermitDtlEntities) {
					set.add(lDtlEntity.getId().getLlrVehicleClassCode());
				}
			} else {
				return vehicleList;
			}
			List<DriversLicenceDetailsEntity> driversLicenceDetailsEntities = driversLicenceDetailsDAO
					.getDriverPermitDtl(holderDtlsEntity.getLicenceHolderId());
			if (!ObjectsUtil.isNull(driversLicenceDetailsEntities)) {
				for (DriversLicenceDetailsEntity lDtlEntity : driversLicenceDetailsEntities) {
					set.add(lDtlEntity.getDlVehicleClassCode());
				}
			}
		} else if ("P".equalsIgnoreCase(showVehicleClass)) {
			List<LearnersPermitDtlEntity> learnersPermitDtlEntities = learnersPermitDtlDAO
					.getLearnersPermitDtl(holderDtlsEntity.getLicenceHolderId());
			if (!ObjectsUtil.isNull(learnersPermitDtlEntities)) {
				for (LearnersPermitDtlEntity lDtlEntity : learnersPermitDtlEntities) {
					if ("P".equalsIgnoreCase(lDtlEntity.getTestResult())) {
						set.add(lDtlEntity.getId().getLlrVehicleClassCode());
					}
				}
			}
			List<DriversLicenceDetailsEntity> driversLicenceDetailsEntities = driversLicenceDetailsDAO
					.getDriverPermitDtl(holderDtlsEntity.getLicenceHolderId());
			if (!ObjectsUtil.isNull(driversLicenceDetailsEntities)) {
				for (DriversLicenceDetailsEntity lDtlEntity : driversLicenceDetailsEntities) {
					if ("P".equalsIgnoreCase(lDtlEntity.getTestResult())) {
						set.add(lDtlEntity.getDlVehicleClassCode());
					}
				}
			}
		} else if ("F".equalsIgnoreCase(showVehicleClass)) {
			List<LearnersPermitDtlEntity> learnersPermitDtlEntities = learnersPermitDtlDAO
					.getLearnersPermitDtl(holderDtlsEntity.getLicenceHolderId());
			if (!ObjectsUtil.isNull(learnersPermitDtlEntities)) {
				for (LearnersPermitDtlEntity lDtlEntity : learnersPermitDtlEntities) {
					if ("F".equalsIgnoreCase(lDtlEntity.getTestResult())) {
						set.add(lDtlEntity.getId().getLlrVehicleClassCode());
					}
				}
			}
			List<DriversLicenceDetailsEntity> driversLicenceDetailsEntities = driversLicenceDetailsDAO
					.getDriverPermitDtl(holderDtlsEntity.getLicenceHolderId());
			if (!ObjectsUtil.isNull(driversLicenceDetailsEntities)) {
				for (DriversLicenceDetailsEntity lDtlEntity : driversLicenceDetailsEntities) {
					if ("F".equalsIgnoreCase(lDtlEntity.getTestResult())) {
						set.add(lDtlEntity.getDlVehicleClassCode());
					}
				}
			}
		} else {
			return vehicleList;
		}

		vehicleList = new ArrayList<>(set);
		return vehicleList;
	}

	private LicenseHolderDtlsEntity getPermanentAddress(LicenseHolderDtlsEntity entity, Boolean isSameAsAadhaar) {
		try {
			AadharEntity aadhaarEntity = aadharDAO.getAadharDetails(Long.valueOf(entity.getAadhaarNo()));
			if (ObjectsUtil.isNull(isSameAsAadhaar) || !isSameAsAadhaar) {
				entity.setPermAddrDoorNo(aadhaarEntity.getHouse());
				entity.setPermAddrStreet(aadhaarEntity.getStreet());
				entity.setPermAddrTown(aadhaarEntity.getVillage_name());
				entity.setPermAddrMandal(aadhaarEntity.getMandal_name());
				entity.setPermAddrDistrict(aadhaarEntity.getDistrict_name());
				entity.setPermAddrState(aadhaarEntity.getStatecode());
				entity.setPermAddrPinCode(aadhaarEntity.getPincode());
			}
			entity.setGuardianName(aadhaarEntity.getCo());
			entity.setGender(aadhaarEntity.getGender());

		} catch (Exception e) {
			logger.error("Getting error in getPermanentAddress  " + entity.getAadhaarNo(), e);
		}
		return entity;
	}

	@Override
	public List<DriversLicenceDetailsModel> getDriverLicensePermitDetails(String dlNo, String rtaOfficeCode)
			throws NotFoundException {

		return driversLicenceDetailsHelper
				.convertToModelList(driversLicenceDetailsDAO.getDriverPermitDtl(dlNo, rtaOfficeCode));
	}

	@Override
	@Transactional
	public LicenseHolderPermitDetails getDriverLicenseHolderDetails(String uniqueKey) throws NotFoundException {
		LicenseHolderPermitDetails licenseHolderPermitDetails = new LicenseHolderPermitDetails();
		List<DriversLicenceDetailsEntity> driversLicenceDetailsEntities = driversLicenceDetailsDAO
				.getDriversLicenceDetails(uniqueKey);
		List<DriversLicenceDetailsModel> driversPermitDetailsList = driversLicenceDetailsHelper
				.convertToModelList(driversLicenceDetailsEntities);
		licenseHolderPermitDetails.setDriversPermitDetailsList(driversPermitDetailsList);
		licenseHolderPermitDetails.setLicenseHolderDetails(
				licenseHolderDtlsHelper.convertToModel(licenseHolderDtlsDAO.getEntity(LicenseHolderDtlsEntity.class,
						driversLicenceDetailsEntities.get(0).getId().getLicenceHolderId())));

		if (ObjectsUtil.isNull(licenseHolderPermitDetails)) {
			throw new NotFoundException();
		}

		return licenseHolderPermitDetails;

	}

	@Override
	@Transactional
	public LicenseHolderDtlsModel getLicenseHolderDetails(String aadhaarNumber, String passportNumber)
			throws NotFoundException {
		LicenseHolderDtlsModel model = null;
		if (!StringsUtil.isNullOrEmpty(aadhaarNumber)) {
			model = licenseHolderDtlsHelper.convertToModel(licenseHolderDtlsDAO.getLicenseHolderDtls(aadhaarNumber));
		} else {
			model = licenseHolderDtlsHelper
					.convertToModel(licenseHolderDtlsDAO.getLicenseHolderDtlsByPassportNumber(passportNumber));
		}
		if (ObjectsUtil.isNull(model)) {
			throw new NotFoundException();
		}
		return model;
	}

	@Override
	public String getReferenceId(Long licenceHolderId, String rtaOfficeCode, String dlType) {

		return dlType + rtaOfficeCode + licenceHolderId + DateUtil.getYear(new Date());
	}

	// service code will use in history
	@Override
	@Transactional
	public String updateInLicenceHodlerDetails(LicenseHolderDtlsModel model, String userName, String serviceCode)
			throws DataMismatchException {

		LicenseHolderDtlsEntity entity = licenseHolderDtlsDAO.getLicenseHolderDtls(model.getAadhaarNo());
		if (ObjectsUtil.isNull(entity)) {
			throw new DataMismatchException();
		}
		if (!StringsUtil.isNullOrEmpty(model.getEmail())) {
			entity.setEmail(model.getEmail());
		}
		if (!StringsUtil.isNullOrEmpty(model.getMobileNo())) {
			entity.setMobileNo(model.getMobileNo());
		}
		if (!StringsUtil.isNullOrEmpty(model.getBloodGrp())) {
			entity.setBloodGrp(model.getBloodGrp());
		}
		if (!ObjectsUtil.isNull(model.getQualificationDetails())) {
			entity.setQualificationId(qualificationDAO.getByCode(model.getQualificationDetails().getCode()));
		}
		entity.setModifiedBy(userName);
		entity.setModifiedOn(new Date());
		licenseHolderDtlsDAO.saveOrUpdate(entity);
		return SaveUpdateResponse.SUCCESS;
	}

	@Override
	@Transactional
	public LicenseHolderPermitDetails forgotLicenceNumber(ForgetLicenseModel forgetLicenseModel)
			throws NotFoundException, ParseException {

		LicenseHolderDtlsEntity entity = licenseHolderDtlsDAO.getLicenseHolderDtls(forgetLicenseModel);
		if (ObjectsUtil.isNull(entity)) {
			throw new NotFoundException();
		}
		LicenseHolderPermitDetails licenseHolderPermitDetails = getDriverLicenseHolderDetails(
				forgetLicenseModel.getAadharNumber(), null);

		logger.info("forgotLicenceNumber for aadhaar number " + forgetLicenseModel.getAadharNumber() + " dob "
				+ forgetLicenseModel.getDob());
		return licenseHolderPermitDetails;

	}

}
