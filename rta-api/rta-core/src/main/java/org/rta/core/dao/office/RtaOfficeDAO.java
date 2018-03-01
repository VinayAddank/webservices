/**
 * 
 */
package org.rta.core.dao.office;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.office.RtaOfficeEntity;

/**
 * @author shivangi.gupta
 *
 */
public interface RtaOfficeDAO extends GenericDAO<RtaOfficeEntity> {

    /**
     * Get RTA Office Details by RTA Office Code
     * 
     * @param rtaOfficeCode
     * @return
     */
    public RtaOfficeEntity getRtaOfficeDetailsByCode(String rtaOfficeCode);

    /**
     * Get RTA Office List who RTA Office Active
     * 
     * @param status
     * @return
     */
    public List<RtaOfficeEntity> getRtaOfficeList(Boolean status);
    
    public List<RtaOfficeEntity> getRtaOfficeInfo();
    
    public RtaOfficeEntity getRtaOfficeById(Long rtaOfficeId);
    
    List<RtaOfficeEntity> getRtaOfficeList(List<String> districts, Boolean status, Boolean showUnit);

}
