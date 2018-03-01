package org.rta.core.dao.vcr;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vcr.VcrDetailsEntity;

public interface VcrHistoryDAO extends GenericDAO<VcrDetailsEntity> {

    VcrDetailsEntity getVcrDetailsByVcrNumber(String vcrNumber);

}
