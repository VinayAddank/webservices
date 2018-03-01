package org.rta.core.dao.master.registration;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.registration.ChallanNumberEntity;

public interface ChallanNumberDAO extends GenericDAO<ChallanNumberEntity> {

    public ChallanNumberEntity getBytreasuryCode(String treasuryCode);
}
