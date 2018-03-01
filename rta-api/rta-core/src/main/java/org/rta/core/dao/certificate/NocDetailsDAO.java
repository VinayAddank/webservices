package org.rta.core.dao.certificate;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.certificate.NocDetailsEntity;

/**
 *	@Author sohan.maurya created on Dec 13, 2016.
 */

public interface NocDetailsDAO extends GenericDAO<NocDetailsEntity> {

    public NocDetailsEntity getNocDetails(Long vehicleRcId);
}
