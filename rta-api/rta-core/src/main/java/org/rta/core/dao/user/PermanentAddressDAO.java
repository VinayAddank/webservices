/**
 * 
 */
package org.rta.core.dao.user;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.user.PermanentAddressEntity;
import org.rta.core.enums.AddressType;

/**
 * @author arun.verma
 *
 */
public interface PermanentAddressDAO extends GenericDAO<PermanentAddressEntity>  {

    /**
     * Get PermanentAddressEntity by userId
     * 
     * @param id
     * @return
     */
    public PermanentAddressEntity findByUserId(Long id, AddressType addressType);
}
