/**
 * 
 */
package org.rta.core.service.appversion;

import org.rta.core.model.appversion.AppVersionModel;

/**
 * @author arun.verma
 *
 */
public interface AppVersionService {

    public AppVersionModel getAppVersion(Integer major, Integer minor, Integer revision);
    
}
