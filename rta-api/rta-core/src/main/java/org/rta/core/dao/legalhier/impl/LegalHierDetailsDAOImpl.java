package org.rta.core.dao.legalhier.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.legalhier.LegalHierDetailsDAO;
import org.rta.core.entity.legalhier.LegalHierDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LegalHierDetailsDAOImpl extends BaseDAO<LegalHierDetailsEntity> implements LegalHierDetailsDAO {

    public LegalHierDetailsDAOImpl() {
        super(LegalHierDetailsEntity.class);
        // TODO Auto-generated constructor stub
    }

}
