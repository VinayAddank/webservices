package org.rta.core.dao.master.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.DocTypesDAO;
import org.rta.core.entity.master.DocTypesEntity;
import org.springframework.stereotype.Repository;

@Repository("docTypesDAO")
public class DocTypesDAOImpl extends BaseDAO<DocTypesEntity> implements DocTypesDAO {

    private Criteria criteria = null;

    public DocTypesDAOImpl() {
        super(DocTypesEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> getMandatoryDocTypesIds() {
        List<Long> docIds = new ArrayList<Long>();
        try {
            criteria = getSession().createCriteria(getPersistentClass())
                    .add(Restrictions.eq("isMandatory", true)).setProjection(Projections.property("docTypeId"));
            docIds = criteria.list();
        } catch (HibernateException he) {
        }
        return docIds;
    }
}
