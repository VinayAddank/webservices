package org.rta.core.dao.vehicle;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.SuspendedRCNumbersEntity;
import org.rta.core.enums.Status;

public interface SuspendedRCNumberDAO extends GenericDAO<SuspendedRCNumbersEntity> {

    public SuspendedRCNumbersEntity getDetails(String prNumber, Boolean isRevoked, Status status);

    public SuspendedRCNumbersEntity getDetails(String prNumber, Status status);
}
