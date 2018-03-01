/**
 * 
 */
package org.rta.core.dao.user.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.PermanentAddressDAO;
import org.rta.core.entity.user.PermanentAddressEntity;
import org.rta.core.enums.AddressType;
import org.springframework.stereotype.Repository;

/**
 * @author arun.verma
 *
 */

@Repository
public class PermanentAddressDAOImpl extends BaseDAO<PermanentAddressEntity> implements PermanentAddressDAO{

    public PermanentAddressDAOImpl() {
        super(PermanentAddressEntity.class);
    }

    @Override
    public PermanentAddressEntity findByUserId(Long id, AddressType addFrom) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("userId", id)).add(Restrictions.eq("addressType", addFrom.getValue()));
        return (PermanentAddressEntity) criteria.uniqueResult();
    }

}
