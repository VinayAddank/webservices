package org.rta.core.dao.docs;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.UserType;

public interface AttachmentDetailsDAO extends GenericDAO<AttachmentDetailsEntity> {

    public List<AttachmentDetailsEntity> getAllAttachments(VehicleRCEntity vehicleId, UserType userType);

    public List<Integer> getDocTypesIds(Long vehicleRcId, UserType userType);
    
    public List<AttachmentDetailsEntity> getAllAttachments(Long vehicleId, UserType userType);
    
    public List<AttachmentDetailsEntity> getAllAttachmentsForvehicleRCId(Long vehicleId, UserType userType);

    /**
     * get attachmentId for update the attatchments Details
     * 
     * @param vehicleRcId
     * @param docId
     * @return
     */

    public Long getAttatchDltIdByDocId(Long vehicleRcId, Integer docId);

    public Boolean getIsAttatchDltIdByDocId(Long vehicleRcId, Integer docId);
    
    public AttachmentDetailsEntity getAttatchDltIdByDocIdVehId(Long vehicleRcId, Integer docId);


    public List<AttachmentDetailsEntity> getAttachmentDetails(List<VehicleRCEntity> vehicleRCEntities);

    /**
     * to delete attachment
     * 
     * @param vehicleRcId
     * @param docTypeId
     */
    public Integer deleteAttachment(Long vehicleRcId, Integer docTypeId);

    /**
     * Get Dealer And Owner Signature
     * 
     * @param vehicleRcId
     * @param docTypesIds
     * @return
     */
    public List<AttachmentDetailsEntity> getDealerAndOwnerSign(Long vehicleRcId, List<Integer> docTypesIds);
}
