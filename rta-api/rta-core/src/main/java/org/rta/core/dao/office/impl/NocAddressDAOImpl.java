package org.rta.core.dao.office.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.office.NocAddressDAO;
import org.rta.core.entity.office.NocAddressEntity;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Dec 19, 2016.
 */
@Repository("nocAddressDAO")
public class NocAddressDAOImpl extends BaseDAO<NocAddressEntity> implements NocAddressDAO {

    public NocAddressDAOImpl() {
        super(NocAddressEntity.class);
    }

    @Override
    public NocAddressEntity getNocAddressDetails(String nocAddressCode) {

        return (NocAddressEntity) getSession().createCriteria(getPersistentClass()).add(Restrictions.eq("nocAddressCode", nocAddressCode))
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NocAddressEntity> getNocAddressDetails(Long distId) {

        return (List<NocAddressEntity>) getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("districtId.districtId", distId)).list();
    }
    
    @Override
    public NocAddressEntity getNocAddressDetailsByRTAOfficeCode(String rtaOfficeCode) {

        return (NocAddressEntity)getSession().createCriteria(getPersistentClass()).createAlias("rtaOfficeId", "r")
                .add(Restrictions.eq("r.code", rtaOfficeCode)).uniqueResult();
    }
    
    

}
