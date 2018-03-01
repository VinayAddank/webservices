/**
 * 
 */
package org.rta.core.dao.tempreport;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.Status;

/**
 * @author arun.verma
 *
 */
public interface TempReportDAO extends GenericDAO<VehicleRCEntity>{

    public List<Object[]> getByDate4TR(Long from, Long to, Status status);

    public List<Object[]> getByDate4PR(Long from, Long to, Status status);
    
    public List<Object[]> getByDateFromHist(Long from, Long to, Status status) ;
    
    public List<Object[]> getRejHistory(Long from, Long to) ;
    
    
}
