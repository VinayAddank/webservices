package org.rta.core.dao.finance.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.finance.FinanceTokenDAO;
import org.rta.core.entity.finance.FinanceTokenEntity;
import org.rta.core.enums.Status;
import org.springframework.stereotype.Repository;


@Repository
public class FinanceTokenDAOImpl extends BaseDAO<FinanceTokenEntity> implements FinanceTokenDAO {

	public FinanceTokenDAOImpl() {
		super(FinanceTokenEntity.class);
		// TODO Auto-generated constructor stub
	}
	
	public FinanceTokenDAOImpl(Class<FinanceTokenEntity> persistentClass) {
		super(persistentClass);
		// TODO Auto-generated constructor stub
	}

	public FinanceTokenEntity getTokenId(Long vehicleRc,Integer serviceType) {
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRc))
				.add(Restrictions.ne("appStatus", Status.APP_RE_INITIATED_FINANCE.getValue()))
				.add(Restrictions.eq("serviceType", serviceType));
		FinanceTokenEntity financeEntity = (FinanceTokenEntity) criteria.uniqueResult();
		return financeEntity;

	}

	@Override
	public FinanceTokenEntity getVehicleRcIdFromToken(String tokenId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("tokenId", tokenId))
				.add(Restrictions.ne("appStatus", Status.APP_RE_INITIATED_FINANCE.getValue()))
				.addOrder(Order.desc("dateOfTokenGen"))
				.addOrder(Order.desc("modifiedOn")).setMaxResults(1);
		FinanceTokenEntity financeEntity = (FinanceTokenEntity) criteria.uniqueResult();
		return financeEntity;
	}
	
	@Override
    public FinanceTokenEntity getFinanceTokenDetails(String tokenId, Integer serviceType) {
	    Criteria criteria = getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("tokenId", tokenId))
                .add(Restrictions.eq("serviceType", serviceType));
        FinanceTokenEntity financeEntity = (FinanceTokenEntity) criteria.uniqueResult();
        return financeEntity;
    }
	
	@Override
	public FinanceTokenEntity getLastActionOnVehicleRc(Long vehicleRc) {
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRc))
				.addOrder(Order.desc("dateOfTokenGen")).setMaxResults(1);
		FinanceTokenEntity financeEntity = (FinanceTokenEntity) criteria.uniqueResult();
		return financeEntity;
	}
	
	public void setReIterateStatusToAllFinanceTable(String token){
		//---- update FinanceTokenEntity ------------
		String hql = "UPDATE FinanceTokenEntity set appStatus = :status WHERE tokenId = :tokenId";
		Query query = getSession().createQuery(hql);
		query.setParameter("status", Status.APP_RE_INITIATED_FINANCE.getValue());
		query.setParameter("tokenId", token);
		query.executeUpdate();
		//---- update FinancerAppStatusEntity ------------
		hql = "UPDATE FinancerAppStatusEntity set financeStatus = :status WHERE tokenId = :tokenId";
		query = getSession().createQuery(hql);
		query.setParameter("status", Status.APP_RE_INITIATED_FINANCE.getValue());
		query.setParameter("tokenId", token);
		query.executeUpdate();
	}
	
	@Override
	public List<FinanceTokenEntity> getFinanceTokenByVehicleRcId(Long vehicleRc) {
	    Criteria criteria = getSession().createCriteria(getPersistentClass())
	            .add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRc));
	    List<FinanceTokenEntity> financeEntity = (List<FinanceTokenEntity>) criteria.list();
	    return financeEntity;
	}
	
	@Override
    public FinanceTokenEntity getVehicleRcIdFromTokenWithReinitiated(String tokenId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("tokenId", tokenId))
                .addOrder(Order.desc("dateOfTokenGen"))
                .addOrder(Order.desc("modifiedOn")).setMaxResults(1);
        FinanceTokenEntity financeEntity = (FinanceTokenEntity) criteria.uniqueResult();
        return financeEntity;
    }
	
//	public List<FinanceTokenEntity> getAppsForFinancerAndStatus(List<Integer> status,Long financerId){
//		Query query=getSession().createQuery("select f from FinanceTokenEntity f where f.tokenId in "
//				+"(select ft.tokenId from FinancerAppStatusEntity ft where ft.financerId.user_id =:financerId)").
//				setParameter("vehicleRc", vehicleRc);
//		List<FinanceTokenEntity> financeTokensEntities = (List<FinanceTokenEntity>) query.list();
//		return financeTokensEntities;
//	}


}
