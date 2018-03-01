package org.rta.core.dao.master;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.InsuranceTypeEntity;

/**
 *	@Author sohan.maurya created on Jul 11, 2016.
 */
public interface InsuranceTypeDAO extends GenericDAO<InsuranceTypeEntity> {

    /*
     * Get InsuranceTypeEntity from code
     * 
     * @Param code
     * @Return InsuranceTypeEntity
    */
    public InsuranceTypeEntity getByCode(String code);
}
