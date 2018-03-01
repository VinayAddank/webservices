package org.rta.core.dao.master.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.QualificationDAO;
import org.rta.core.entity.master.QualificationEntity;
import org.springframework.stereotype.Repository;

@Repository
public class QualificationDAOImpl extends BaseDAO<QualificationEntity> implements QualificationDAO {

    public QualificationDAOImpl() {
        super(QualificationEntity.class);
    }

    @Override
    public QualificationEntity getByCode(Integer code) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", code));
        return (QualificationEntity) criteria.uniqueResult();
    }
}
