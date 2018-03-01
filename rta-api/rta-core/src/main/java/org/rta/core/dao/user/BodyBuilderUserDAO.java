package org.rta.core.dao.user;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.user.BodyBuilderUserEntity;

public interface BodyBuilderUserDAO extends GenericDAO<BodyBuilderUserEntity>  {

	BodyBuilderUserEntity getBodyBuilderUserByUserId(Long id);

}
