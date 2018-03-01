package org.rta.core.dao.vcr;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vcr.VcrTransactionDetailEntity;

public interface VcrTransactionDetailDAO extends GenericDAO<VcrTransactionDetailEntity>{
	 public VcrTransactionDetailEntity getVcrDetailsByVcrNumber(String vcrNumber);

}
