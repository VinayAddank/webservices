package org.rta.core.dao.license;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.licence.LearnersPermitDtlEntity;

public interface LearnersPermitDtlDAO extends GenericDAO<LearnersPermitDtlEntity>{
	
	public List<LearnersPermitDtlEntity> getLearnersPermitDtl(Long pk);
	
	public List<LearnersPermitDtlEntity> getLearnersPermitDetails(String llrNumber);
	

}
