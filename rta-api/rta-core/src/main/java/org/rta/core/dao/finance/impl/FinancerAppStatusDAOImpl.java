package org.rta.core.dao.finance.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.finance.FinancerAppStatusDAO;
import org.rta.core.entity.finance.FinancerAppStatusEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.stereotype.Repository;

@Repository
public class FinancerAppStatusDAOImpl extends BaseDAO<FinancerAppStatusEntity> implements FinancerAppStatusDAO {
	public FinancerAppStatusDAOImpl() {
		super(FinancerAppStatusEntity.class);
	}

	public FinancerAppStatusDAOImpl(Class<FinancerAppStatusEntity> persistentClass) {
		super(persistentClass);
		// TODO Auto-generated constructor stub
	}
	
	public FinancerAppStatusEntity getFinancerAppFromToken(String tokenId, UserEntity user){
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("tokenId", tokenId))
				.add(Restrictions.ne("financeStatus", Status.APP_RE_INITIATED_FINANCE.getValue()))
				.add(Restrictions.eq("financerId", user));
		FinancerAppStatusEntity financeAppEntity = (FinancerAppStatusEntity) criteria.uniqueResult();
		return financeAppEntity;
	}
	
	
	public List<FinancerAppStatusEntity> getFinancerAppList(UserEntity user, Status status,Integer perPageRecords
			,Integer pageNumber ){
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				  .add(Restrictions.eq("financerId", user))
				  .add(Restrictions.eq("financeStatus", status.getValue()));
		if(pageNumber!=null && pageNumber>0)
		criteria.setFirstResult((pageNumber-1)*perPageRecords);
		if(perPageRecords!=null && perPageRecords>0)
		criteria.setMaxResults(perPageRecords);
		List<FinancerAppStatusEntity> financeAppEntities = (List<FinancerAppStatusEntity>) criteria.list();
		return financeAppEntities;
	}
	
	
	public List<FinancerAppStatusEntity> getFinancerAppList4Token(String token){
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				  .add(Restrictions.ne("financeStatus", Status.APP_RE_INITIATED_FINANCE.getValue()))
				  .add(Restrictions.eq("tokenId", token));
		List<FinancerAppStatusEntity> financeAppEntities = (List<FinancerAppStatusEntity>) criteria.list();
		return financeAppEntities;
	}
	
	public FinancerAppStatusEntity getFinancerApp4Token(String token,Long financerId){
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				  .add(Restrictions.eq("tokenId", token))
				  .add(Restrictions.ne("financeStatus", Status.APP_RE_INITIATED_FINANCE.getValue()))
				  .add(Restrictions.ne("financeStatus", Status.CANCELLED.getValue()))
				  .add(Restrictions.eq("financerId.userId", financerId))
				  .addOrder(Order.desc("requestedDate")).setMaxResults(1);
	    FinancerAppStatusEntity financeAppEntity = (FinancerAppStatusEntity) criteria.uniqueResult();
		return financeAppEntity;
	}

	public List<FinancerAppStatusEntity> getAppsForStatus(Long vehicleRc){
		Query query=getSession().createQuery("select f from FinancerAppStatusEntity f where f.tokenId in "
				+"(select ft.tokenId from FinanceTokenEntity ft where ft.vehicleRc.vehicleRcId =:vehicleRc)").
				setParameter("vehicleRc", vehicleRc);
		List<FinancerAppStatusEntity> financeAppEntities = (List<FinancerAppStatusEntity>) query.list();
		return financeAppEntities;
	}

	@Override
	public List<FinancerAppStatusEntity> getFinancerAppListMultiStatus(UserEntity user, List<Integer> statuses,
			Integer perPageRecords, Integer pageNumber) {
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				  .add(Restrictions.eq("financerId", user))
				  .add(Restrictions.in("financeStatus", statuses));
		if(pageNumber!=null && pageNumber>0 && perPageRecords!=null && perPageRecords>0) {
		    criteria.setFirstResult((pageNumber-1)*perPageRecords);
		    criteria.setMaxResults(perPageRecords);
		}
		List<FinancerAppStatusEntity> financeAppEntities = (List<FinancerAppStatusEntity>) criteria.list();
		return financeAppEntities;
	}
	
	/*
	 * select * from finance_app_status as fas inner join finance_token as ft on fas.token_id=ft.token_id inner join vehicle_rc vrc on vrc.vehicle_rc_id=ft.vehicle_rc_id where fas.user_id=6822 and fas.finance_status in (2) and vrc.rta_office_id=11 and fas ft.service_type=12;(non-Javadoc)
	 */
	@Override
	public List<FinancerAppStatusEntity> getFinancerAppListMultiStatusNew(UserEntity user, List<Integer> statuses,
	        Integer perPageRecords, Integer pageNumber, Long rtaOfficeId, ServiceType serviceType) {
	    StringBuilder query = new StringBuilder("select fas.* from finance_app_status as fas inner join finance_token as ft on fas.token_id=ft.token_id inner join vehicle_rc vrc on vrc.vehicle_rc_id=ft.vehicle_rc_id where fas.user_id=:financerId");
	    if (!ObjectsUtil.isNull(rtaOfficeId)) {
	        query.append(" and vrc.rta_office_id=:rtaOfficeId");
	    }
	    
	    if (!ObjectsUtil.isNull(serviceType)) {
	        query.append(" and ft.service_type=:serviceType");
	    }
	    
	    if (!ObjectsUtil.isNullOrEmpty(statuses)) {
            query.append(" and fas.finance_status in (:financeStatus)");
        }
	    
	    if (!ObjectsUtil.isNull(perPageRecords)) {
	        query.append(" limit :limit");
        }
	    if (!ObjectsUtil.isNull(pageNumber)) {
	        query.append(" offset :start");
	    }
	    SQLQuery criteria = getSession().createSQLQuery(query.toString());
	    
	    criteria.setParameter("financerId", user.getUserId());
	    if (!ObjectsUtil.isNull(rtaOfficeId)) {
	        criteria.setParameter("rtaOfficeId", rtaOfficeId);
        }
	    if (!ObjectsUtil.isNullOrEmpty(statuses)) {
            criteria.setParameterList("financeStatus", statuses);
        }
        if (!ObjectsUtil.isNull(serviceType)) {
            criteria.setParameter("serviceType", serviceType.getValue());
        }
        
        if (!ObjectsUtil.isNull(perPageRecords)) {
            criteria.setParameter("limit", perPageRecords);
        }
        if (!ObjectsUtil.isNull(pageNumber)) {
            criteria.setParameter("start", (pageNumber-1)*perPageRecords);
        }
	    criteria.addEntity(FinancerAppStatusEntity.class);
	    List<FinancerAppStatusEntity> financeAppEntities = (List<FinancerAppStatusEntity>) criteria.list();
	    return financeAppEntities;
	}
	
	@Override
	public Integer getCountFinancerAppListMultiStatusNew(UserEntity user, List<Integer> statuses,
	        Long rtaOfficeId, ServiceType serviceType) {
	    StringBuilder query = new StringBuilder("select fas.* from finance_app_status as fas inner join finance_token as ft on fas.token_id=ft.token_id inner join vehicle_rc vrc on vrc.vehicle_rc_id=ft.vehicle_rc_id where fas.user_id=:financerId");
	    if (!ObjectsUtil.isNull(rtaOfficeId)) {
	        query.append(" and vrc.rta_office_id=:rtaOfficeId");
	    }
	    if (!ObjectsUtil.isNullOrEmpty(statuses)) {
	        query.append(" and fas.finance_status in (:financeStatus)");
	    }
	    if (!ObjectsUtil.isNull(serviceType)) {
            query.append(" and ft.service_type=:serviceType");
        }

	    SQLQuery criteria = getSession().createSQLQuery(query.toString());

	    criteria.setParameter("financerId", user.getUserId());
	    if (!ObjectsUtil.isNull(rtaOfficeId)) {
	        criteria.setParameter("rtaOfficeId", rtaOfficeId);
	    }
	    if (!ObjectsUtil.isNullOrEmpty(statuses)) {
	        criteria.setParameterList("financeStatus", statuses);
	    }
	    if (!ObjectsUtil.isNull(serviceType)) {
            criteria.setParameter("serviceType", serviceType.getValue());
        }

	    criteria.addEntity(FinancerAppStatusEntity.class);
	    List<FinancerAppStatusEntity> financeAppEntities = (List<FinancerAppStatusEntity>) criteria.list();
	    Integer count = 0;
	    if (!ObjectsUtil.isNullOrEmpty(financeAppEntities)) {
	        count = financeAppEntities.size();
	    } else {
	        count =0;
	    }
	    return count;
	}
	
	@Override
    public Long getCountFinancerAppListMultiStatus(UserEntity user, List<Integer> statuses) {
	    Query query = getSession().createQuery("select count(distinct app.financerAppStatusId) from FinancerAppStatusEntity as app inner join app.financerId as fi, FinanceTokenEntity as ft where app.tokenId=ft.tokenId and app.financeStatus in ( :statuses ) and fi.userId = :userId and ft.vehicleRc.trStatus != :cancelStatus");
	    query.setParameter("userId", user.getUserId());
	    query.setParameterList("statuses", statuses);
	    query.setParameter("cancelStatus", Status.CANCELLED.getValue());
	    Long count = (Long)query.uniqueResult();
        return count;
    }

	@Override
	public void setAppStatus(String token, Status status){
	    String str = "update FinancerAppStatusEntity app set app.financeStatus =:status where app.tokenId =:token";
	    Query query = getSession().createQuery(str);
	    query.setParameter("status", status.getValue());
	    query.setParameter("token", token);
	    query.executeUpdate();
	}
	
	@Override
	public List<FinancerAppStatusEntity> getAppStatusForTokens(String tokenId) {
	    Criteria criteria = getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("tokenId", tokenId));
	    return (List<FinancerAppStatusEntity>)criteria.list();
	    
	}
}
