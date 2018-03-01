/**
 * 
 */
package org.rta.core.dao.master;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.office.RtaOfficeEntity;

/**
 * @author arun.verma
 *
 */
public interface MandalDAO extends GenericDAO<MandalEntity> {
    public MandalEntity getByMandalName(String mandalName);
    
    public MandalEntity getByMandalCode(Integer mandalCode);

    public List<MandalEntity> getByDistrictCode(String code);
    
    public MandalEntity getMandal(String mandalName, String districtName);
    
    public RtaOfficeEntity getRTAOfficeByMandalCode(Integer code);
    
    public Object getRtaMandalMapping(Integer mandalId);

	public MandalEntity getMandalByCode(Integer mandalCode);

}
