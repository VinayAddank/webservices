package org.rta.core.dao.certificate.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.certificate.CFXNotesDAO;
import org.rta.core.entity.certificate.CFXNotesEntity;
import org.rta.core.enums.Status;
import org.springframework.stereotype.Repository;

@Repository
public class CFXNotesDAOImpl extends BaseDAO<CFXNotesEntity> implements CFXNotesDAO {

    public CFXNotesDAOImpl() {
        super(CFXNotesEntity.class);
    }

    @Override
    public CFXNotesEntity getByPrNumber(String prNumber, Status status) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("prNumber", prNumber));
        criteria.add(Restrictions.eq("status", status.getValue()));
        return (CFXNotesEntity) criteria.uniqueResult();
    }

}
