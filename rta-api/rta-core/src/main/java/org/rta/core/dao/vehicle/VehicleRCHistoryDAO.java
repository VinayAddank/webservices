package org.rta.core.dao.vehicle;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.application.VehicleRCHistoryEntity;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;

public interface VehicleRCHistoryDAO extends GenericDAO<VehicleRCHistoryEntity> {
	/**
	 * /** Returns last status of this vehicle, user and its type
	 * 
	 * @param userId
	 * @param userRole
	 * @param vehicleRcId
	 * @return VehicleRCHistoryEntity
	 */
	public abstract VehicleRCHistoryEntity getLastAction(Long userId, UserType userRole, Long vehicleRcId,
			Integer iteratino);

	/**
	 * Get Pending Applications for CCO, MVI, AO, RTO
	 * 
	 * @param userId
	 * @param userType
	 * @param List<Long>
	 *            appList
	 * @return
	 */
	public List<VehicleRCHistoryEntity> getAppFromHistoryForPending(Long userId, UserType userType, List<Long> appList);

	/**
	 * Get last action of user in history
	 * 
	 * @param Long
	 *            userId
	 * @return VehicleRCHistoryEntity
	 */
	public VehicleRCHistoryEntity getLastActionApp(Long userId);

	public List<Long> getActionedApp(UserType userType, List<Long> vehicleRcIds);

	VehicleRCHistoryEntity getLastAction(UserType userRole, Long vehicleRcId, Integer iteration);

	/**
	 * Get last action on vehicleRcId for given userType in history
	 * 
	 * @param Long
	 *            vehicleRcId
	 * @param UserType
	 *            userType
	 * @return VehicleRCHistoryEntity
	 */
	public List<VehicleRCHistoryEntity> getHistforVehicleByUserType(Long vehicleRcId, UserType userType);

	/**
	 * Get last action on vehicleRcId for given iteration in history
	 * 
	 * @param Long
	 *            vehicleRcId
	 * @param Integer
	 *            iteration
	 * @return VehicleRCHistoryEntity
	 */
	public VehicleRCHistoryEntity getLastActionOnVehicleRcId(Long vehicleRcId, Integer iteration, UserType userType);

	/***
	 * Get all action on vehicleRcId for given iteration in history
	 * 
	 * @param vehicleRcId
	 * @param iteration
	 * @return
	 */
	public List<VehicleRCHistoryEntity> getHistryOfVehicleRcId(Long vehicleRcId, Integer iteration);

	/**
	 * Get All Vehicle RC History
	 * 
	 * @param vehicleRcId
	 * @return
	 */

	public List<VehicleRCHistoryEntity> getAppFromHistoryByVehicleId(List<Long> vehicleRcId);

	public VehicleRCHistoryEntity getLastVehicleRcHistory(Long vehicleRcId, Integer iteration, UserType userType,
			Status status);

	public List<VehicleRCHistoryEntity> getApplicationWithUserStatus(Long userId, Status status, String query,
            Long from, Long to, Integer perPageRecords, Integer pageNumber, Integer regCategory);

	Long countApplicationsWithUserStatus(Long userId, Status status, String query, Long from, Long to,
            Integer perPageRecords, Integer pageNumber, boolean count, Integer regCategory);

	
	public List<VehicleRCHistoryEntity> getApprovedAppByFromAndToDate(Long from, Long to);

	
	public List<VehicleRCHistoryEntity> getRejetedByFromAndToDate(Long from, Long to);

	/**
	 * Get all rejected application entries.
	 * Applications are returned in descending order or modifiedOn.
	 * @param vehicleRcId
	 * @return List
	 */
    public List<VehicleRCHistoryEntity> getRejectionHistory(Long vehicleRcId);


    /**
     * Get PR Pending Vehicle RC IDs
     * 
     * @param userId
     * @param status
     * @return
     */
    public List<Long> getVehicleRcIdsByUserId(Long userId, Status status);

    /**
     * Get last action according to user type
     * 
     * @param vehicleRcId
     * @param userType
     * @return
     */
    public VehicleRCHistoryEntity getLastActionOnVehicleRcId(Long vehicleRcId, UserType userType);
    
    public List<VehicleRCHistoryEntity> getOpenedApplications();

    List<VehicleRCHistoryEntity> getActionedEntries(Long vehicleRCId, UserType userType);

    VehicleRCHistoryEntity getOpenedApplications(Long userId, Long vehicleRcId, Integer iteration);

    List<VehicleRCHistoryEntity> migrateScript();

}
