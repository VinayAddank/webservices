package org.rta.core.dao.master.registration;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.registration.SpecialNumberRtaOfficeEntity;

public interface SpecialNumberRtaOfficeDAO extends GenericDAO<SpecialNumberRtaOfficeEntity> {
	
	public SpecialNumberRtaOfficeEntity getUniqueSpecialNumber(String specialNumber);
	
	public SpecialNumberRtaOfficeEntity getSpecialNumberByTrNo(String trnumber);
}
