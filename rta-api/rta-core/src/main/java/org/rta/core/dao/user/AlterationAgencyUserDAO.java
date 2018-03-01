package org.rta.core.dao.user;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.user.AlterationAgencyUserEntity;

public interface AlterationAgencyUserDAO extends GenericDAO<AlterationAgencyUserEntity>  {

	AlterationAgencyUserEntity getUserByUserId(Long id);

}
