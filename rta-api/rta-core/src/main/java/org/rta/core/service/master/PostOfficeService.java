/**
 * 
 */
package org.rta.core.service.master;

import java.util.List;

import org.rta.core.model.master.PostOfficeModel;

/**
 * @author arun.verma
 *
 */
public interface PostOfficeService {

    public List<PostOfficeModel> getAll();
    
    public PostOfficeModel getByPinCode(String pinCode);
    
    public PostOfficeModel getByPostOfficeName(String postOfficeName);
}
