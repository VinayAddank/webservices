package org.rta.core.dao.office;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.office.NocAddressEntity;

/**
 *	@Author sohan.maurya created on Dec 19, 2016.
 */
public interface NocAddressDAO extends GenericDAO<NocAddressEntity> {

    public NocAddressEntity getNocAddressDetails(String nocAddressCode);

    public List<NocAddressEntity> getNocAddressDetails(Long districtId);

    NocAddressEntity getNocAddressDetailsByRTAOfficeCode(String rtaOfficeCode);
}
