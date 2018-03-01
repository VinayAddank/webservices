package org.rta.core.dao.license.impl;


import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.license.LicenceHlderTxnDtlsDAO;
import org.rta.core.entity.licence.LicenseHolderTxnEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LicenceHlderTxnDtlsDAOImpl extends BaseDAO<LicenseHolderTxnEntity>
		implements LicenceHlderTxnDtlsDAO {

	public LicenceHlderTxnDtlsDAOImpl() {
		super(LicenseHolderTxnEntity.class);
	}

}
