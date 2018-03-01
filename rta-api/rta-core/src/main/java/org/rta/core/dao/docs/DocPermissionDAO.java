package org.rta.core.dao.docs;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.docs.DocPermissionEntity;

/**
 *	@Author sohan.maurya created on Aug 25, 2016.
 */
public interface DocPermissionDAO extends GenericDAO<DocPermissionEntity> {

    /**
     * get documents type for upload for particular vehicle
     * 
     * @param ownershipType
     * @param regCategory
     * @param vehicleClass
     * @return
     */
    public List<DocPermissionEntity> getExactlyDocTypes(Integer ownershipType, Integer regCategory,
            Integer vehicleClass);

    /**
     * get Mandatory Doc Types Ids
     * 
     * @param ownershipType
     * @param regCategory
     * @param vehicleClass
     * @return
     */
    public List<Integer> getMandatoryDocTypesIds(Integer ownershipType, Integer regCategory,
            Integer vehicleClass);
}
