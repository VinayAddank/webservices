/**
 * 
 */
package org.rta.core.dao.permit.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.permit.PermitHeaderDAO;
import org.rta.core.entity.permit.PermitHeaderEntity;
import org.rta.core.enums.Status;
import org.rta.core.model.permit.PermitNewRequestModel;
import org.rta.core.model.sync.SyncDataModel;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.stereotype.Repository;

/**
 * @author arun.verma
 *
 */
@Repository
public class PermitHeaderDAOImpl extends BaseDAO<PermitHeaderEntity> implements PermitHeaderDAO{

    public PermitHeaderDAOImpl() {
        super(PermitHeaderEntity.class);
    }
    
    @Override
    public PermitHeaderEntity getPrimaryPermitActiveByPr(String prNumber){
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRegdNo", prNumber));
        criteria.add(Restrictions.eq("isTempPermit", false));
        criteria.add(Restrictions.eq("status", Status.OPEN.getValue()));
        return (PermitHeaderEntity) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PermitHeaderEntity> getPermitActiveByPr(String prNumber){
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRegdNo", prNumber));
        criteria.add(Restrictions.eq("status", Status.OPEN.getValue()));
        return (List<PermitHeaderEntity>) criteria.list();
    }

    @Override
    public PermitHeaderEntity getTempPermitActiveByPr(String prNumber) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRegdNo", prNumber));
        criteria.add(Restrictions.eq("isTempPermit", true));
        criteria.add(Restrictions.eq("status", Status.OPEN.getValue()));
        return (PermitHeaderEntity) criteria.uniqueResult();
	}
    
    @Override
    public PermitHeaderEntity getActivePermit(String prNumber, Boolean isTemp) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRegdNo", prNumber));
        if(ObjectsUtil.isNotNull(isTemp)){
            criteria.add(Restrictions.eq("isTempPermit", isTemp));
        } else {
            criteria.add(Restrictions.eq("isTempPermit", false));
        }
        criteria.add(Restrictions.eq("status", Status.OPEN.getValue()));
        return (PermitHeaderEntity) criteria.uniqueResult();
    }
}