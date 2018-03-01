package org.rta.core.service.secondvehicle.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.application.rejection.RejectionHistoryDAO;
import org.rta.core.dao.secondvehicle.SecondVehicleRejectionDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.application.rejection.RejectionHistoryEntity;
import org.rta.core.entity.secondvehicle.SecondVehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.Status;
import org.rta.core.helper.secondvehicle.SecondVehicleDetailsHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.secondVechicle.SecondVehicleDetailsModel;
import org.rta.core.service.secondvehicle.SecondVehicleRejectionService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecondVehicleRejectionServiceImpl implements SecondVehicleRejectionService {

	private static final Logger log = Logger.getLogger(SecondVehicleRejectionServiceImpl.class);

    @Autowired
    private SecondVehicleRejectionDAO vehicleRejectionDao;

    @Autowired
    private SecondVehicleDetailsHelper secondVehicleHelper;

    @Autowired
    private RejectionHistoryDAO rejectionHistoryDao;

    @Autowired
    private UserDAO userDao;

    @Autowired
    private VehicleDAO vehicleDAO;
    
    @Override
    @Transactional
    public void saveDetails(SecondVehicleDetailsModel secondVehicleDeatils, Long vehicleRcId, String username,
            Long userId, Integer userType) {
        Long date = DateUtil.toCurrentUTCTimeStamp();
        Integer iteration = vehicleDAO.get(vehicleRcId).getIteration();

        secondVehicleDeatils.setVehicleRcId(vehicleRcId);
        secondVehicleDeatils.setUserId(userId);
        secondVehicleDeatils.setCreatedBy(username);
        secondVehicleDeatils.setModifiedBy(username);
        secondVehicleDeatils.setCreatedOn(date);
        secondVehicleDeatils.setModifiedOn(date);
        secondVehicleDeatils.setIteration(iteration);
        secondVehicleDeatils.setIsValidSecondVehicle(true);

        RejectionHistoryEntity rejectionHistory = new RejectionHistoryEntity();
        rejectionHistory.setVehicleRCId(vehicleDAO.get(vehicleRcId));
        rejectionHistory.setTitle("Second Vehicle Search");
        rejectionHistory.setUserId(userDao.findByUserId(userId));
        rejectionHistory.setComment(secondVehicleDeatils.getComment());
        rejectionHistory.setCreatedBy(username);
        rejectionHistory.setModifiedBy(username);
        rejectionHistory.setCreatedOn(date);
        rejectionHistory.setModifiedOn(date);
        rejectionHistory.setStatus(Status.REJECTED.getValue());
        rejectionHistory.setUserType(userType);
        rejectionHistory.setIteration(iteration);
        rejectionHistoryDao.save(rejectionHistory);

        SecondVehicleDetailsEntity secondVehicleDeatilsEntity =
                secondVehicleHelper.convertToEntity(secondVehicleDeatils);
        secondVehicleDeatilsEntity.setRejectionHistory(rejectionHistory);
        vehicleRejectionDao.save(secondVehicleDeatilsEntity);
    }

    @Override
    @Transactional
    public SecondVehicleDetailsModel get(Long secondVehicleId) {
        return secondVehicleHelper
                .convertToModel(vehicleRejectionDao.getSecondVehicleDetailsByVehicleRcId(secondVehicleId));
    }

    @Override
    @Transactional
    public SecondVehicleDetailsModel get(Long secondVehicleId, Integer iteration) {
        return secondVehicleHelper
                .convertToModel(vehicleRejectionDao.getSecondVehicleDetails(secondVehicleId, iteration));
    }
    
    @Override
    @Transactional
    public SaveUpdateResponse secondVehiclePaymentSkip(Long vehicleRcID, Boolean isNocApplicable) {
        VehicleRCEntity vehicleRcEntity = vehicleDAO.get(vehicleRcID);
        SaveUpdateResponse response = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS);
        if (ObjectsUtil.isNotNull(vehicleRcEntity)) {
            Integer iteration = vehicleRcEntity.getIteration();
            SecondVehicleDetailsEntity secondVehicleDetailsEntity = vehicleRejectionDao.getSecondVehicleDetails(vehicleRcID, iteration);
            if (ObjectsUtil.isNotNull(secondVehicleDetailsEntity)) {
                if(ObjectsUtil.isNotNull(secondVehicleDetailsEntity.getPaidTax()) && secondVehicleDetailsEntity.getPaidTax()){
                    response.setStatus(SaveUpdateResponse.FAILURE);
                    response.setMessage("Second Vehicle Tax Already Paid !!!");
                } else {
                    secondVehicleDetailsEntity.setIsValidSecondVehicle(isNocApplicable);
                    vehicleRejectionDao.saveOrUpdate(secondVehicleDetailsEntity);
                    response.setMessage("Updated Successfully.");
                }
            } else {
                response.setStatus(SaveUpdateResponse.FAILURE);
                response.setMessage("Second Vehicle Not Found !!!");
                log.info("Second vehicle data not found wrt to vehicleRcId : " + vehicleRcID);
            }
        } else {
            response.setStatus(SaveUpdateResponse.FAILURE);
            response.setMessage("Invalid Vehicle RC Id !!!");
            log.info("Data not found wrt to vehicleRcId : " + vehicleRcID);
        }
        return response;
    }
}
