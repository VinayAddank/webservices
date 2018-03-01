/**
 * 
 */
package org.rta.core.dao.insurance.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.insurance.CfxTxnDtlDAO;
import org.rta.core.entity.insurance.CfxTxnDtlEntity;
import org.springframework.stereotype.Repository;

/**
 * @author arun.verma
 *
 */
@Repository
public class CfxTxnDtlDAOImpl extends BaseDAO<CfxTxnDtlEntity> implements CfxTxnDtlDAO{

    public CfxTxnDtlDAOImpl() {
        super(CfxTxnDtlEntity.class);
    }

    @Override
    public CfxTxnDtlEntity getByRtaCfxTxnId(String rtaCfxTxnId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("rtaCfxTxnId", rtaCfxTxnId).ignoreCase());
        return (CfxTxnDtlEntity) criteria.uniqueResult();
    }

    @Override
    public CfxTxnDtlEntity getByCfxTxnId(String cfxTxnId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("cfxTxnId", cfxTxnId).ignoreCase());
        return (CfxTxnDtlEntity) criteria.uniqueResult();
    }

    @Override
    public CfxTxnDtlEntity getByCfxPaymentId(String cfxPaymentId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("cfxPaymentId", cfxPaymentId).ignoreCase());
        return (CfxTxnDtlEntity) criteria.uniqueResult();
    }

    
}
