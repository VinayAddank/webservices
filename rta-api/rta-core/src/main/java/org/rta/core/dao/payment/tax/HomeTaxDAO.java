package org.rta.core.dao.payment.tax;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.tax.HomeTaxEntity;

public interface HomeTaxDAO extends GenericDAO<HomeTaxEntity>{

	public HomeTaxEntity getHomeTax(String vehicleSubClass, String permitCode, String ownershipType, String stateCode,String permitSubType,
			Integer seat, Integer ulw, Integer rlw,  double invoiceAmt);
}
