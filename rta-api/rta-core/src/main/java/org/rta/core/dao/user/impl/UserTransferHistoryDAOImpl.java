package org.rta.core.dao.user.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.UserTransferHistoryDAO;
import org.rta.core.entity.user.UserTransferHistoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserTransferHistoryDAOImpl extends BaseDAO<UserTransferHistoryEntity> implements UserTransferHistoryDAO {

	 public UserTransferHistoryDAOImpl() {
	        super(UserTransferHistoryEntity.class);
	    }

}

