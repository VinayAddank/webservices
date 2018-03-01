package org.rta.core.dao.master;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.RegistrationCategoryEntity;

public interface RegistrationCategoryDAO extends GenericDAO<RegistrationCategoryEntity> {
    
    public RegistrationCategoryEntity getByCode(String code);
}
