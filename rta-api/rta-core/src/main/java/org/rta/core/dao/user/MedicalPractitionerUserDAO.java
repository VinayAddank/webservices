package org.rta.core.dao.user;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.user.MedicalPractitionerUserEntity;

public interface MedicalPractitionerUserDAO extends GenericDAO<MedicalPractitionerUserEntity>  {

	MedicalPractitionerUserEntity getMedicalPractitionerUserByUserId(Long id);

}
