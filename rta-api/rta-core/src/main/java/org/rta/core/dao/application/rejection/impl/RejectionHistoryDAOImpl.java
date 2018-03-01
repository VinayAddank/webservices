package org.rta.core.dao.application.rejection.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.application.rejection.RejectionHistoryDAO;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.application.rejection.RejectionHistoryEntity;
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.springframework.stereotype.Repository;

@Repository("rejectionHistoryDAO")
public class RejectionHistoryDAOImpl extends BaseDAO<RejectionHistoryEntity> implements RejectionHistoryDAO {

    public RejectionHistoryDAOImpl() {
        super(RejectionHistoryEntity.class);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RejectionHistoryEntity> get(AttachmentDetailsEntity attachmentDetailsId, UserType userType) {

        RejectionHistoryEntity rejectionHistoryEntity = new RejectionHistoryEntity();
        List<RejectionHistoryEntity>results=null;
        try {
            String hql =
                    "FROM RejectionHistoryEntity R WHERE R.attachmentDetailsId = :attachmentDetailsId AND R.userType = :userType ";
            Query query = getSession().createQuery(hql);
            query.setParameter("attachmentDetailsId", attachmentDetailsId);
            query.setParameter("userType", userType.getValue());
            results = query.list();
        } catch (HibernateException he) {
        		he.printStackTrace();
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RejectionHistoryEntity> getAll(AttachmentDetailsEntity attachmentDetailsId) {

        List<RejectionHistoryEntity> rejectionHistoryEntities = new ArrayList<RejectionHistoryEntity>();

        try {
            Criteria criteria = getSession().createCriteria(RejectionHistoryEntity.class);
            criteria.add(Restrictions.eq("attachmentDetailsId", attachmentDetailsId));
            rejectionHistoryEntities = criteria.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return rejectionHistoryEntities;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RejectionHistoryEntity> getRejectionHistory(Long vehicleRcId/*, Integer iteration*/) {
        Criteria criteria = getSession().createCriteria(RejectionHistoryEntity.class);
        criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
//        criteria.add(Restrictions.eq("iteration", iteration));
        criteria.addOrder(Order.desc("modifiedOn"));
        return (List<RejectionHistoryEntity>) criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RejectionHistoryEntity> getApplicationWithUserStatus(Long userId, Status status, String query, Long from, Long to, Integer perPageRecords,
            Integer pageNumber) {
        StringBuilder q = new StringBuilder("SELECT DISTINCT(rh.*) from (select vehicle_rc_id,max(iteration) as mx,user_id from rejection_history where user_id=:userId group by vehicle_rc_id,user_id) t INNER JOIN rejection_history rh ON rh.vehicle_rc_id=t.vehicle_rc_id and t.mx=rh.iteration where rh.user_id=:userId and rh.status=:status order by rh.modified_on desc");
        if (perPageRecords != null && pageNumber != null) {
            q.append(" limit ");
            q.append(perPageRecords);
            q.append(" offset ");
            q.append((pageNumber - 1) * perPageRecords);
        }
        SQLQuery sqlQuery = getSession().createSQLQuery(q.toString());
        sqlQuery.setParameter("status", status.getValue());
        sqlQuery.setParameter("userId", userId);
        sqlQuery.addEntity(RejectionHistoryEntity.class);
        return (List<RejectionHistoryEntity>) sqlQuery.list();
    }
    
    @Override
    public Long countApplicationsWithUserStatus(Long userId, Status status) {
        StringBuilder builder = new StringBuilder("SELECT count(DISTINCT(rh.*)) from (select vehicle_rc_id,max(iteration) as mx,user_id from rejection_history where user_id=:userId group by vehicle_rc_id,user_id) t INNER JOIN rejection_history rh ON rh.vehicle_rc_id=t.vehicle_rc_id and t.mx=rh.iteration where rh.user_id=:userId and rh.status=:status");
        SQLQuery sqlQuery = getSession().createSQLQuery(builder.toString());
        sqlQuery.setParameter("status", status.getValue());
        sqlQuery.setParameter("userId", userId);
        return ((BigInteger)sqlQuery.uniqueResult()).longValue();
    }

}
