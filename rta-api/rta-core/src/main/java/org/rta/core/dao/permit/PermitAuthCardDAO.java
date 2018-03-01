/**
 * 
 */
package org.rta.core.dao.permit;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.permit.PermitAuthCardDetailsEntity;
import org.rta.core.enums.Status;

/**
 * @author arun.verma
 *
 */
public interface PermitAuthCardDAO extends GenericDAO<PermitAuthCardDetailsEntity>{

    public PermitAuthCardDetailsEntity getActiveAuthCard(String permitNumber);

	PermitAuthCardDetailsEntity getAuthCard(String permitNumber, Status status);
    
}
