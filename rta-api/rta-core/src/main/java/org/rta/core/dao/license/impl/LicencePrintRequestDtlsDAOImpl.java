package org.rta.core.dao.license.impl;


import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.license.LicencePrintRequestDtlsDAO;
import org.rta.core.entity.licence.LicencePrintRequestEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LicencePrintRequestDtlsDAOImpl extends BaseDAO<LicencePrintRequestEntity>
		implements LicencePrintRequestDtlsDAO {

	public LicencePrintRequestDtlsDAOImpl() {
		super(LicencePrintRequestEntity.class);
	}

}
