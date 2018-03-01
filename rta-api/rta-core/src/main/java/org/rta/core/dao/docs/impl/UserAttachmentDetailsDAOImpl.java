package org.rta.core.dao.docs.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.docs.UserAttachmentDetailsDAO;
import org.rta.core.entity.docs.UserAttachmentDetailsEntity;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Jan 3, 2017.
 */

@Repository("userAttachmentDetailsDAO")
public class UserAttachmentDetailsDAOImpl extends BaseDAO<UserAttachmentDetailsEntity> implements  UserAttachmentDetailsDAO{

    private Criteria criteria = null;

    public UserAttachmentDetailsDAOImpl() {
        super(UserAttachmentDetailsEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserAttachmentDetailsEntity> getAllAttachmentDetails(String userName) {
        criteria = getSession().createCriteria(getPersistentClass());
        criteria.createAlias("userId", "u").add(Restrictions.eq("u.userName", userName));
        return criteria.list();
    }

    @Override
    public UserAttachmentDetailsEntity getAttachmentDetails(String userName, Integer docId) {
        criteria = getSession().createCriteria(getPersistentClass());
        criteria.createAlias("userId", "u").add(Restrictions.eq("u.userName", userName))
                .add(Restrictions.eq("docTypes.docTypeId", docId));
        return (UserAttachmentDetailsEntity) criteria.uniqueResult();
    }

}
