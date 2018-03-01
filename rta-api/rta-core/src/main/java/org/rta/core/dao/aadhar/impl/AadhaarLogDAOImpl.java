package org.rta.core.dao.aadhar.impl;

import org.rta.core.dao.aadhar.AadhaarLogDAO;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.applicant.AadhaarLogEntity;
import org.springframework.stereotype.Repository;

@Repository
public class AadhaarLogDAOImpl extends BaseDAO<AadhaarLogEntity> implements AadhaarLogDAO {

    public AadhaarLogDAOImpl() {
        super(AadhaarLogEntity.class);
    }
}
