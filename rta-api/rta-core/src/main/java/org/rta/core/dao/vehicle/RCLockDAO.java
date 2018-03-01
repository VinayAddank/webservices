/**
 * 
 */
package org.rta.core.dao.vehicle;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.RCLockEntity;
import org.rta.core.enums.Status;

/**
 * @author arun.verma
 *
 */
public interface RCLockDAO extends GenericDAO<RCLockEntity>{

	public RCLockEntity getLastEntity(String prNumber, Status currStatus);
}
