package org.rta.core.dao.master.registration;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.registration.SpecialNumberEntity;

public interface SpecialNumberDAO extends GenericDAO<SpecialNumberEntity> {	
	 public List<Integer> getAllSpecialNo();
	 public List<SpecialNumberEntity> getAllSpecialNoEntity();
	 public SpecialNumberEntity  checkSpecialNumber(String specialNumber);
}
