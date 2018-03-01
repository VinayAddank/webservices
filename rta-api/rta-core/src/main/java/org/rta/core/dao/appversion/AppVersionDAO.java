/**
 * 
 */
package org.rta.core.dao.appversion;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.appversion.AppVersionEntity;

/**
 * @author arun.verma
 *
 */
public interface AppVersionDAO extends GenericDAO<AppVersionEntity>{

    public AppVersionEntity getAppVersion(Integer major, Integer minor, Integer revision);
}
