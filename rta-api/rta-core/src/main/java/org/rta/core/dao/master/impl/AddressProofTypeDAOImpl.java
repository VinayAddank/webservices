package org.rta.core.dao.master.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.AddressProofTypeDAO;
import org.rta.core.entity.master.AddressProofTypeEntity;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Jul 11, 2016.
 */

@Repository("addressProofTypeDAO")
public class AddressProofTypeDAOImpl extends BaseDAO<AddressProofTypeEntity> implements AddressProofTypeDAO {

	public AddressProofTypeDAOImpl() 
	{
        super(AddressProofTypeEntity.class);
	}


}
