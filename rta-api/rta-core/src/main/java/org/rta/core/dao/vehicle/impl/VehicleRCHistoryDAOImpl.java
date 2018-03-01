package org.rta.core.dao.vehicle.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.VehicleRCHistoryDAO;
import org.rta.core.entity.application.VehicleRCHistoryEntity;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleRCHistoryDAOImpl extends BaseDAO<VehicleRCHistoryEntity> implements VehicleRCHistoryDAO {

	public VehicleRCHistoryDAOImpl() {
		super(VehicleRCHistoryEntity.class);
	}

	@SuppressWarnings("unchecked")
    @Override
    public List<VehicleRCHistoryEntity> migrateScript() {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.createAlias("vehicleRc", "vRc");
        criteria.add(Restrictions.eqProperty("iteration", "vRc.iteration"));
        criteria.addOrder(Order.asc("modifiedOn"));
        return criteria.list();
    }
	
	@Override
	public VehicleRCHistoryEntity getLastAction(Long userId, UserType userRole, Long vehicleRcId, Integer iteration) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("userEntity.userId", userId));
		criteria.add(Restrictions.eq("rtaEmployeeType", userRole.getValue()));
		criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
		criteria.add(Restrictions.eq("iteration", iteration));
		criteria.addOrder(Order.desc("modifiedOn"));
		criteria.setMaxResults(1);
		VehicleRCHistoryEntity r = (VehicleRCHistoryEntity) criteria.uniqueResult();
		return r;
	}

	@Override
	public VehicleRCHistoryEntity getLastAction(UserType userRole, Long vehicleRcId, Integer iteration) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("rtaEmployeeType", userRole.getValue()));
		criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
		criteria.add(Restrictions.eq("iteration", iteration));
		criteria.addOrder(Order.desc("modifiedOn"));
		criteria.setMaxResults(1);
		VehicleRCHistoryEntity r = (VehicleRCHistoryEntity) criteria.uniqueResult();
		return r;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleRCHistoryEntity> getAppFromHistoryForPending(Long userId, UserType userType,
			List<Long> appList) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		// criteria.add(Restrictions.eq("userEntity.userId", userId));
		criteria.add(Restrictions.in("vehicleRc.vehicleRcId", appList));
		if (userType == UserType.ROLE_AO) {
			// Criterion cOpen = Restrictions.ne("status",
			// Status.OPEN.getValue());
			Criterion cCCO = Restrictions.eq("rtaEmployeeType", UserType.ROLE_CCO.getValue());
			Criterion cMVI = Restrictions.eq("rtaEmployeeType", UserType.ROLE_MVI.getValue());
			criteria.add(Restrictions.or(cCCO, cMVI));
		} else if (userType == UserType.ROLE_RTO) {
			criteria.add(Restrictions.eq("rtaEmployeeType", UserType.ROLE_AO.getValue()));
			// criteria.add(Restrictions.ne("status", Status.OPEN.getValue()));
		}
		criteria.createAlias("vehicleRc", "vRc");
		criteria.add(Restrictions.eqProperty("iteration", "vRc.iteration"));
		criteria.addOrder(Order.desc("modifiedOn"));
		// criteria.setProjection(Projections.groupProperty("vehicleRc.vehicleRcId"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleRCHistoryEntity> getAppFromHistoryByVehicleId(List<Long> vehicleRcIds) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.in("vehicleRc.vehicleRcId", vehicleRcIds));
		criteria.addOrder(Order.desc("modifiedOn"));
		return criteria.list();
	}

	@Override
	public VehicleRCHistoryEntity getLastActionApp(Long userId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("userEntity.userId", userId));
		criteria.addOrder(Order.desc("modifiedOn"));
		criteria.setMaxResults(1);
		return (VehicleRCHistoryEntity) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getActionedApp(UserType userType, List<Long> vehicleRcIds) {
		String hql = "Select vehicleRc.vehicleRcId From VehicleRCHistoryEntity vrch where rtaEmployeeType = :userType and vehicleRc.vehicleRcId in(:vehicleRcIds) and status in(:status) and iteration = vehicleRc.iteration order by modifiedOn desc";
		Query query = getSession().createQuery(hql);
		query.setParameter("userType", userType.getValue());
		query.setParameterList("vehicleRcIds", vehicleRcIds);
		query.setParameterList("status", new ArrayList<Integer>(
				Arrays.asList(Status.OPEN.getValue(), Status.REJECTED.getValue(), Status.APPROVED.getValue())));
		return (List<Long>) query.list();
	}

	@Override
	public VehicleRCHistoryEntity getLastActionOnVehicleRcId(Long vehicleRcId, Integer iteration, UserType userType) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("iteration", iteration));
		criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
		criteria.add(Restrictions.eq("rtaEmployeeType", userType.getValue()));
		criteria.addOrder(Order.desc("modifiedOn"));
		criteria.setMaxResults(1);
		return (VehicleRCHistoryEntity) criteria.uniqueResult();
	}

	@Override
	public List<VehicleRCHistoryEntity> getHistforVehicleByUserType(Long vehicleRcId, UserType userType) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
		criteria.add(Restrictions.eq("rtaEmployeeType", userType.getValue()));
		criteria.addOrder(Order.desc("modifiedOn"));
		// criteria.setMaxResults(1);
		return (List<VehicleRCHistoryEntity>) criteria.list();
	}

	@Override
	public List<VehicleRCHistoryEntity> getHistryOfVehicleRcId(Long vehicleRcId, Integer iteration) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("iteration", iteration));
		criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
		criteria.addOrder(Order.desc("modifiedOn"));
		return (List<VehicleRCHistoryEntity>) criteria.list();
	}

	@Override
	public VehicleRCHistoryEntity getLastVehicleRcHistory(Long vehicleRcId, Integer iteration, UserType userType,
			Status status) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
		if (iteration != null && iteration >= 0) {
			criteria.add(Restrictions.eq("iteration", iteration));
		}
		if (userType != null) {
			criteria.add(Restrictions.eq("rtaEmployeeType", userType.getValue()));
		}
		if (status != null) {
			criteria.add(Restrictions.eq("status", status.getValue()));
		}
		criteria.addOrder(Order.desc("modifiedOn"));
		criteria.setMaxResults(1);
		return (VehicleRCHistoryEntity) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleRCHistoryEntity> getApplicationWithUserStatus(Long userId, Status status, String query,
            Long from, Long to, Integer perPageRecords, Integer pageNumber, Integer regCategory) {
		StringBuilder q;
		if(ObjectsUtil.isNull(regCategory)){
		    q = new StringBuilder(
	                "SELECT DISTINCT(vh.*) from (select vehicle_rc_id,MAX(iteration) as mx,user_id from vehicle_rc_history where user_id=:userId and status=:status group by vehicle_rc_id,user_id) t INNER JOIN vehicle_rc_history vh ON vh.vehicle_rc_id=t.vehicle_rc_id and t.mx=vh.iteration where vh.user_id=:userId and vh.status=:status");
		} else {
		    q = new StringBuilder(
	                "SELECT DISTINCT(vh.*) from (select vehicle_rc_id,MAX(iteration) as mx,user_id from vehicle_rc_history where user_id=:userId and status=:status group by vehicle_rc_id,user_id) t INNER JOIN vehicle_rc_history vh ON vh.vehicle_rc_id=t.vehicle_rc_id and t.mx=vh.iteration INNER JOIN vehicle_rc vrc ON vrc.vehicle_rc_id=vh.vehicle_rc_id where vh.user_id=:userId and vh.status=:status and vrc.reg_category=:regCategory");
		}
		if (!ObjectsUtil.isNull(from) && !ObjectsUtil.isNull(to)) {
			q.append(" AND vh.modified_on BETWEEN '").append(from).append("' AND '").append(to).append("'");
		}
		q.append(" ORDER BY vh.modified_on DESC ");
		if (perPageRecords != null && pageNumber != null) {
			q.append(" LIMIT ");
			q.append(perPageRecords);
			q.append(" OFFSET ");
			q.append((pageNumber - 1) * perPageRecords);
		}
		SQLQuery sqlQuery = getSession().createSQLQuery(q.toString());
		sqlQuery.setParameter("status", status.getValue());
		sqlQuery.setParameter("userId", userId);
		if(!ObjectsUtil.isNull(regCategory)){
		    sqlQuery.setParameter("regCategory", regCategory);
		}
		/*
		 * Criteria criteria =
		 * getSession().createCriteria(RejectionHistoryEntity.class);
		 * criteria.add(Restrictions.eq("userId.userId", userId));
		 * criteria.add(Restrictions.eq("status", status.getValue()));
		 */
		sqlQuery.addEntity(VehicleRCHistoryEntity.class);
		return (List<VehicleRCHistoryEntity>) sqlQuery.list();
	}

	@Override
	public Long countApplicationsWithUserStatus(Long userId, Status status, String query, Long from, Long to,
            Integer perPageRecords, Integer pageNumber, boolean count, Integer regCategory) {
		StringBuilder q;
		if(ObjectsUtil.isNull(regCategory)){
		    q = new StringBuilder(
	                "SELECT count(DISTINCT(vh.*)) from (select vehicle_rc_id,MAX(iteration) as mx,user_id from vehicle_rc_history where user_id=:userId and status=:status group by vehicle_rc_id,user_id) t INNER JOIN vehicle_rc_history vh ON vh.vehicle_rc_id=t.vehicle_rc_id and t.mx=vh.iteration where vh.user_id=:userId and vh.status=:status");
		} else {
		    q = new StringBuilder(
	                "SELECT count(DISTINCT(vh.*)) from (select vehicle_rc_id,MAX(iteration) as mx,user_id from vehicle_rc_history where user_id=:userId and status=:status group by vehicle_rc_id,user_id) t INNER JOIN vehicle_rc_history vh ON vh.vehicle_rc_id=t.vehicle_rc_id and t.mx=vh.iteration INNER JOIN vehicle_rc vrc ON vrc.vehicle_rc_id=vh.vehicle_rc_id where vh.user_id=:userId and vh.status=:status and vrc.reg_category=:regCategory");
		}
		if (!count) {
			if (!ObjectsUtil.isNull(from) && !ObjectsUtil.isNull(to)) {
				q.append(" AND vh.modified_on BETWEEN '").append(from).append("' AND '").append(to).append("'");
			}
		}
		SQLQuery sqlQuery = getSession().createSQLQuery(q.toString());
		sqlQuery.setParameter("status", status.getValue());
		sqlQuery.setParameter("userId", userId);
		if(!ObjectsUtil.isNull(regCategory)){
		    sqlQuery.setParameter("regCategory", regCategory);
		}
		return ((BigInteger) sqlQuery.uniqueResult()).longValue();
	}

	@Override
	public List<VehicleRCHistoryEntity> getApprovedAppByFromAndToDate(Long from, Long to) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		if (from != null && from.longValue() > 0l)
			criteria = criteria.add(Restrictions.ge("createdOn", from));
		if (to != null && to.longValue() > 0l)
			criteria = criteria.add(Restrictions.le("createdOn", to));
		//criteria.add(Restrictions.eq("status", Status.APPROVED.getValue()));
		criteria.createAlias("userEntity", "user");
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("user.userName"));
		projectionList.add(Projections.groupProperty("user.firstName"));
		projectionList.add(Projections.groupProperty("rtaEmployeeType"));
		projectionList.add(Projections.rowCount());
		projectionList.add(Projections.groupProperty("status"));
		
		criteria.setProjection(projectionList);
		return (List<VehicleRCHistoryEntity>)criteria.list();
	}
	
	
	
	@Override
	public List<VehicleRCHistoryEntity> getRejetedByFromAndToDate(Long from, Long to) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		if (from != null && from.longValue() > 0l)
		criteria.add(Restrictions.ge("createdOn", from));
		if (to != null && to.longValue() > 0l)
		criteria.add(Restrictions.le("createdOn", to));
		criteria.add(Restrictions.eq("status", Status.REJECTED.getValue()));
		criteria.createAlias("userEntity", "user");
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("user.userName"));
		projectionList.add(Projections.groupProperty("user.firstName"));
		projectionList.add(Projections.groupProperty("user.lastName"));
		projectionList.add(Projections.rowCount());
		criteria.setProjection(projectionList);
		return (List<VehicleRCHistoryEntity>)criteria.list();
	}

	

	/*@Override
	public List<VehicleRCHistoryEntity> getApprovedAppByFromAndToDate(Long from, Long to) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		if (from != null && from.longValue() > 0l)
			criteria.add(Restrictions.ge("createdOn", from));
		if (to != null && to.longValue() > 0l)
			criteria.add(Restrictions.le("createdOn", to));
		criteria.add(Restrictions.eq("status", Status.APPROVED.getValue()));
		criteria.createAlias("userEntity", "user");
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property("user.userName"));
		criteria.setProjection(pl);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getPersistentClass()));
		return (List<VehicleRCHistoryEntity>)criteria.list();
	}

	@Override
	public List<VehicleRCHistoryEntity> getRejectedAppByFromAndToDate(Long from, Long to) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		if (from != null && from.longValue() > 0l)
			criteria.add(Restrictions.ge("createdOn", from));
		if (to != null && to.longValue() > 0l)
			criteria.add(Restrictions.le("createdOn", to));
		criteria.add(Restrictions.eq("status", Status.REJECTED.getValue()));
		criteria.createAlias("userEntity", "user");
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property("user.userName"));
		criteria.setProjection(pl);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(getPersistentClass()));
		return (List<VehicleRCHistoryEntity>)criteria.list();
	}
*/
	
	@SuppressWarnings("unchecked")
	@Override
    public List<VehicleRCHistoryEntity> getRejectionHistory(Long vehicleRcId) {
	    Criteria criteria = getSession().createCriteria(getPersistentClass());
	    criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
	    criteria.add(Restrictions.eq("status", Status.REJECTED.getValue()));
	    criteria.addOrder(Order.desc("modifiedOn"));
	    return (List<VehicleRCHistoryEntity>)criteria.list();
	}
	
    @SuppressWarnings("unchecked")
    @Override
    public List<Long> getVehicleRcIdsByUserId(Long userId, Status status) {

        Query query = getSession()
                .createQuery(
                        "select r.vehicleRc.vehicleRcId from VehicleRCHistoryEntity r where r.userEntity.userId = :userId and  r.status = :status")
                .setParameter("userId", userId).setParameter("status", status.getValue());

        return query.list();
    }

    @Override
    public VehicleRCHistoryEntity getLastActionOnVehicleRcId(Long vehicleRcId, UserType userType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
        criteria.add(Restrictions.eq("rtaEmployeeType", userType.getValue()));
        criteria.addOrder(Order.desc("modifiedOn"));
        criteria.setMaxResults(1);
        return (VehicleRCHistoryEntity) criteria.uniqueResult();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleRCHistoryEntity> getOpenedApplications() {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.gt("createdOn", DateUtil.dateSeconds(DateUtil.toCurrentUTCTimeStamp() * 1000)));
		criteria.add(Restrictions.eq("status", Status.OPEN.getValue()));
		List<Integer> userTypeList = new ArrayList<>();
		userTypeList.add(UserType.ROLE_AO.getValue());
		userTypeList.add(UserType.ROLE_CCO.getValue());
		userTypeList.add(UserType.ROLE_MVI.getValue());
		userTypeList.add(UserType.ROLE_RTO.getValue());
		criteria.add(Restrictions.in("rtaEmployeeType", userTypeList));
		return (List<VehicleRCHistoryEntity>)criteria.list();
	}
	
	@Override
	public VehicleRCHistoryEntity getOpenedApplications(Long userId, Long vehicleRcId, Integer iteration) {
	    Criteria criteria = getSession().createCriteria(getPersistentClass());
	    criteria.add(Restrictions.eq("userEntity.userId", userId));
	    criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
	    criteria.add(Restrictions.eq("status", Status.OPEN.getValue()));
	    criteria.add(Restrictions.eq("iteration", iteration));
	    return (VehicleRCHistoryEntity)criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleRCHistoryEntity> getActionedEntries(Long vehicleRCId, UserType userType) {
	    Criteria criteria = getSession().createCriteria(getPersistentClass());
	    criteria.add(Restrictions.or(Restrictions.eq("status", Status.APPROVED.getValue()), Restrictions.eq("status", Status.REJECTED.getValue())));
	    List<Integer> userTypeList = new ArrayList<>();
	    userTypeList.add(userType.getValue());
	    criteria.add(Restrictions.in("rtaEmployeeType", userTypeList));
	    criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRCId));
	    return (List<VehicleRCHistoryEntity>)criteria.list();
	}
    
}
