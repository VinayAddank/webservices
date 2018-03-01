package org.rta.core.dao.aadhar;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.applicant.AadharEntity;
import org.rta.core.model.AadharModel;

public interface AadharDAO extends GenericDAO<AadharEntity> {

	public abstract void saveOrUpdate(AadharModel aadharModel, String token);

}
