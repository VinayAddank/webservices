/**
 * 
 */
package org.rta.core.dao.vehicle;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.StoppageApplicationEntity;

/**
 * @author sohan.maurya
 *
 */
public interface StoppageApplicationDAO extends GenericDAO<StoppageApplicationEntity>{

	public StoppageApplicationEntity getStoppageApplication(String prNumber);
}
