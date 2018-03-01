package org.rta.core.dao.master;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.OwnershipTypeEntity;

public interface OwnershipDAO extends GenericDAO<OwnershipTypeEntity>{

    public OwnershipTypeEntity getById(Long id);
    public OwnershipTypeEntity getByCode(String code);
}
