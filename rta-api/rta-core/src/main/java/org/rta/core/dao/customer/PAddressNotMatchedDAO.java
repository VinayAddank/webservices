/**
 * 
 */
package org.rta.core.dao.customer;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.applicant.PAddressNotMatchedEntity;

/**
 * @author arun.verma
 *
 */
public interface PAddressNotMatchedDAO extends GenericDAO<PAddressNotMatchedEntity>{

    public PAddressNotMatchedEntity getPAddressNotMatched(String aadharNo);

    public PAddressNotMatchedEntity getPAddressNotMatchedByVehId(Long vehicleRcId);
}