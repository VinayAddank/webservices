/**
 * 
 */
package org.rta.core.dao.master;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.PostOfficeEntity;

/**
 * @author arun.verma
 *
 */
public interface PostOfficeDAO extends GenericDAO<PostOfficeEntity> {
    public PostOfficeEntity getByPinCode(String pinCode);
    
    public PostOfficeEntity getByPostOfficeName(String postOfficeName);
}
