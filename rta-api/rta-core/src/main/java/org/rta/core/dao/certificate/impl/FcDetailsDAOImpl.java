package org.rta.core.dao.certificate.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.certificate.FcDetailsDAO;
import org.rta.core.entity.certificate.FitnessCertificateEntity;
import org.rta.core.enums.Status;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Nov 11, 2016.
 */

@Repository("fcDetailsDAO")
public class FcDetailsDAOImpl extends BaseDAO<FitnessCertificateEntity> implements FcDetailsDAO {

    public FcDetailsDAOImpl() {
        super(FitnessCertificateEntity.class);
    }

    @Override
    public FitnessCertificateEntity getAnyFcDetails(Long vehicleRcId , String validityFlag) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId", vehicleRcId));
        criteria.add(Restrictions.eq("validityFlag", validityFlag));
        return (FitnessCertificateEntity) criteria.uniqueResult();
        
    }
    
    @Override
    public FitnessCertificateEntity getFcDetails(Long vehicleRcId , String validityFlag) {
    	Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId", vehicleRcId));
        criteria.add(Restrictions.eq("validityFlag", validityFlag));
        criteria.add(Restrictions.ne("statusCode", Status.SUSPENDED.getValue()));
        return (FitnessCertificateEntity) criteria.uniqueResult();
    	
    }
    
    @Override
    public FitnessCertificateEntity getSuspendedFcDetails(Long vehicleRcId , String validityFlag) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId", vehicleRcId));
        criteria.add(Restrictions.eq("validityFlag", validityFlag));
        criteria.add(Restrictions.eq("statusCode", Status.SUSPENDED.getValue()));
        return (FitnessCertificateEntity) criteria.uniqueResult();
        
    }

}
