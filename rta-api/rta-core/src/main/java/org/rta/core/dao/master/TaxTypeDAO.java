package org.rta.core.dao.master;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.TaxTypeEntity;


public interface TaxTypeDAO extends GenericDAO<TaxTypeEntity> {

    public abstract TaxTypeEntity getByCode(String code);
    
}
