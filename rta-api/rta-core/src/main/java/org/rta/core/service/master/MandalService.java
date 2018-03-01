/**
 * 
 */
package org.rta.core.service.master;

import java.util.List;

import org.rta.core.model.master.MandalModel;
import org.rta.core.model.office.RTAOfficeModel;

/**
 * @author arun.verma
 *
 */

public interface MandalService {

    public List<MandalModel> getAll();
    
    public void save(MandalModel mandal);
    
    public MandalModel getMandal(String mandalName, String districtName);
    
    public MandalModel getByMandalCode(Integer mandalCode);

    public List<MandalModel> getByDistrictCode(String code);
    
    public RTAOfficeModel getRTAOfficeByMandalCode(Integer code, String registrationCategory);
}