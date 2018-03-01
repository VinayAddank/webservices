package org.rta.core.dao.docs;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.docs.VehicleAlterationAttachmentEntity;

/**
 *	@Author sohan.maurya created on Jan 4, 2017.
 */

public interface VehicleAlterationAttachmentDAO extends GenericDAO<VehicleAlterationAttachmentEntity> {

    /**
     * To get all attachment Alteration of Vehicle for vehicle
     * 
     * @param vehicleRcId
     * @return
     */
    public List<VehicleAlterationAttachmentEntity> getAllVehicleAlterationAttachmentDetails(Long vehicleRcId);

    /**
     * To get one attachment Alteration of Vehicle for vehicle by document Id
     * 
     * @param vehicleRcId
     * @param docId
     * @return
     */
    public VehicleAlterationAttachmentEntity getVehicleAlterationAttachmentDetails(Long vehicleRcId, Integer docId);
}
