package org.rta.core.dao.vehicle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.enums.VehicleCategory;

public interface VehicleDAO extends GenericDAO<VehicleRCEntity> {

    public abstract VehicleRCEntity get(Long vehicleRcId);

    /**
     * Get Applications that have TR and PR status PENDING APPROVED, REJECTED. </br>
     * 
     * @param userId
     * @param type
     * @param status
     * @param regCatId
     * @return
     */
    public List<VehicleRCEntity> getApplicationStatus(Long userId, String appType, Integer status, Integer regCatId);

    /**
     * Get Application count that have TR and PR status PENDING APPROVED, REJECTED. </br>
     * 
     * @param userId
     * @param type
     * @param status
     * @return
     */
    public abstract Long getApplicationCount(Long userId, String type, Status status, Integer regCategory);

    /**
     * Get List<VehicleRCEntity> for Pending Application
     * 
     * @param Long userId,
     * @param UserType userRole
     * @param RegistrationType type
     * @param Status status
     */
    public abstract List<VehicleRCEntity> getVehicleRcEntityPending(Long userId, UserType userRole, Integer regCatId);

    /**
     * Get Application that has PR status APPROVED or REJECTED. </br>
     * Note : This method doesn't return application with status PENDING.
     * 
     * @param rtaOfficeId
     * @param status
     * @return
     */
    public HashMap<String, Object> getApplications(Long rtaOfficeId, Status status, String query, Long from, Long to,
            Integer perPageRecords, Integer pageNumber, Integer regCatId);

    /**
     * Get applications count that has PR status APPROVED or REJECTED. </br>
     * Note : This method doesn't return applications count with status PENDING.
     * 
     * @param rtaOfficeId
     * @param status
     * @return
     */
    public abstract Long countApplications(Long rtaOfficeId, Status status, Integer regCatId);

    /**
     * Get vehicle RC Id
     * 
     * @param ChassisNo
     * @return
     */
    public abstract VehicleRCEntity getChassisNoByVehicleRc(String ChassisNo);

    /**
     * Get vehicleRCEntity
     * 
     * @param prNumber
     * @param rtaOfficeId
     * @return
     */

    public VehicleRCEntity getVehicleRCByPRNumber(String prNumber, Long rtaOfficeId, Boolean skipTransport);

    /**
     * get VehicleRCEntity list between two date
     * 
     * @param from
     * @param to
     * @param rtaOfficeId
     * @param isPrinted
     * @return
     */

    public List<VehicleRCEntity> getVehicleRcByFromToDate(Long from, Long to, Long rtaOfficeId, Boolean isPrinted, Boolean skipTransport);

    /**
     * Get VehicleRCEntities one Date
     * 
     * @param from
     * @param to
     * @return
     */

    public List<VehicleRCEntity> getVehicleRcByFromToDate(Long from, Long to);

    /**
     * update print status in VehicleRCEntity
     * 
     * @param printStatusMap
     * @return
     */

    public Integer updatePrintStatusAndPrintDate(Map<String, Object> printStatusMap);

    HashMap<String, Object> getUserApplications(Long rtaOfficeId, String query, Long from, Long to, Integer perPageRecords,
            Integer pageNumber);
    
    public List<VehicleRCEntity> getApplicationStatus4Users(List<Long> userIds, String appType, Integer status,
            Integer regCatId);
  
    /**
     * To return all rcs between from and to date, If given 0 value will not consider the dates
     * @param from
     * @param to
     * @return
     */
    public List<VehicleRCEntity> getVehicleRCCreatedBtwDate(Long from, Long to);
    
    /**
     * Get pr pending applications
     * @param userType TODO
     * @param trNumber
     * @param perPageRecords
     * @param pageNumber
     * @param vehicleRcIds
     * 
     * @return
     */
    public Map<String, Object> getPrPendingApplications(Long userId, UserType userType,
            String trNumber, Integer perPageRecords, Integer pageNumber, Integer regCatId);

    /**
     * Get PR Pending count application
     * @param userType TODO
     * @param vehicleRcIds
     * 
     * @return
     */
    public Integer getPrPendingApplicationsCount(Long userId, UserType userType, Integer regCatId);
    
    /**
     * Vehicle belongs to user or its child
     * @param vehicleRc
     * @param userId
     * @return
     */
    public VehicleRCEntity vehicleBelongToUserOrItsChild(Long vehicleRc,Long dealerId);
    
    /**
     * Vehicle with PR number
     * @param vehicleRc
     * @return
     */
    public VehicleRCEntity getVehicleRc4Pr(String prNumber);

    public VehicleRCEntity getVehicleRCByPRNumber(String prNumber);

    public VehicleRCEntity getVehicleRCByTRNumber(String trNumber);
    
    public VehicleRCEntity getByPROrTrNumber(String prOrTrNumber);

    public List<VehicleRCEntity> getVehicleRcEntityPending(Long rtaOfficeId, UserType userRole, Integer regCatId,
            Long currentTime);
    public List<VehicleRCEntity> getByTRApprovedNdFromToDate(Long from, Long to);

	public List<VehicleRCEntity> getTrDetailsVehicleRc(Long from, Long to);
	
    public VehicleRCEntity getOpenApplicationByUser(Long userId, UserType userRole);

    public Long getPendingApplicationsCount(Long rtaOfficeId, UserType userRole, Integer regType);

    public VehicleRCEntity getPendingApplication(Long rtaOfficeId, UserType userRole, Integer regType, VehicleCategory vc);
    
    public List<VehicleRCEntity> getPrPendingApplications();

    public List<VehicleRCEntity> getOpenedApplications();

	public Map<String, Object> getApplicationStatusSearchBasis(List<Long> userIds, String appType, Integer status,
			String regCat, Long from, Long to, Integer perPageRecords, Integer pageNumber);

    List<VehicleRCEntity> getSuspensionExpiredVehicle(Long currentTimestamp);
}
