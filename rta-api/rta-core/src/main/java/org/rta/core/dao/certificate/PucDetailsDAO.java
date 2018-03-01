package org.rta.core.dao.certificate;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.certificate.PucDetailsEntity;

/**
 *	@Author sohan.maurya created on Dec 15, 2016.
 */

public interface PucDetailsDAO extends GenericDAO<PucDetailsEntity> {

    public PucDetailsEntity getPucDetails(Long vehiclercId);
}
