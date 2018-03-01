package org.rta.core.dao.permit;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.permit.PermitDetailsMappingEntity;
import org.rta.core.entity.permit.PermitHeaderEntity;

public interface PermitDetailsMappingDAO extends GenericDAO<PermitDetailsMappingEntity> {

	public List<PermitDetailsMappingEntity> getByPermitNoNdDetailType(PermitHeaderEntity permitHeaderEntity  , Integer status) ;

	public PermitDetailsMappingEntity getActiveDetails(String permitNo, String typeCode);

	public List<PermitDetailsMappingEntity> getActiveDetailList(String permitNo, String typeCode);
}
