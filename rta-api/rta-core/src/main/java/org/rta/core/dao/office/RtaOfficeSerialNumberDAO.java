/**
 * 
 */
package org.rta.core.dao.office;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.office.RTAOfficeSerialNumberEntity;
import org.rta.core.enums.SerialNumberType;

/**
 * @author shivangi.gupta
 *
 */
public interface RtaOfficeSerialNumberDAO extends GenericDAO<RTAOfficeSerialNumberEntity> {

    public RTAOfficeSerialNumberEntity getSerialNumber(String rtaOfficeCode, Integer year, SerialNumberType type);

}
