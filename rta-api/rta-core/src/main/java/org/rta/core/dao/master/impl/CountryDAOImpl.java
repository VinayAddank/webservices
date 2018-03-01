package org.rta.core.dao.master.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.CountryDAO;
import org.rta.core.entity.master.CountryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CountryDAOImpl extends BaseDAO<CountryEntity> implements CountryDAO {

    public CountryDAOImpl() {
        super(CountryEntity.class);
    }
}
