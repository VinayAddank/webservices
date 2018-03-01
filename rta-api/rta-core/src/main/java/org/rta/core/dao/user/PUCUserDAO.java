package org.rta.core.dao.user;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.user.BodyBuilderUserEntity;
import org.rta.core.entity.user.PUCUserEntity;

public interface PUCUserDAO extends GenericDAO<PUCUserEntity>  {

	PUCUserEntity getUserByUserId(Long id);

}
