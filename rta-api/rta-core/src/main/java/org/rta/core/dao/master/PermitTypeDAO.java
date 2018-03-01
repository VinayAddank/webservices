package org.rta.core.dao.master;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.PermitTypeEntity;

/**
 *	@Author sohan.maurya created on Nov 10, 2016.
 */
public interface PermitTypeDAO extends GenericDAO<PermitTypeEntity> {

    public PermitTypeEntity getPermitType(Integer permitTypeId);

    public PermitTypeEntity getPermitTypeByCode(String permitType);

    public List<PermitTypeEntity> getPermitTypeByCodes(List<String> permitTypeList, boolean isTemp);

}
