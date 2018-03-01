package org.rta.core.dao.master;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.BodyTypeMasterEntity;

public interface BodyTypeMasterDAO extends GenericDAO<BodyTypeMasterEntity>{
	
	List<String> getActiveBodyTypeList();

}
