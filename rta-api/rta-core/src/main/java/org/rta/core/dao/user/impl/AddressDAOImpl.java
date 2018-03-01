package org.rta.core.dao.user.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.enums.UserType;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDAOImpl extends BaseDAO<AddressEntity> implements AddressDAO {

    public AddressDAOImpl() {
        super(AddressEntity.class);
    }

    @Override
    public AddressEntity findByUserId(Long id) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("userId", id));
        return (AddressEntity) criteria.uniqueResult();
    }
    
    @Override
    public AddressEntity findByUserIdAndType(Long id,String type) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("userId", id));
        criteria.add(Restrictions.eq("type", type));
        return (AddressEntity) criteria.uniqueResult();
    }

    @Override
    public List<AddressEntity> getAllByUserType(UserType userType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("userType", userType));
        return (List<AddressEntity>) criteria.list();
    }

}
