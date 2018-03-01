package org.rta.core.dao.user.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.enums.UserType;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl extends BaseDAO<UserEntity> implements UserDAO {

    public UserDAOImpl() {
        super(UserEntity.class);
    }

    @Override
    public UserEntity findByUserId(Long id) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("userId", id));
        return (UserEntity) criteria.uniqueResult();
    }

    @Override
    public UserEntity findByUserName(String userName) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("userName", userName));
        return (UserEntity) criteria.uniqueResult();
    }

    @Override
    public UserEntity findByAadhar(String aadharNumber) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("aadharNumber", aadharNumber));
        return (UserEntity) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<UserEntity> findUsersByAadhar(String aadharNumber, UserType userType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("aadharNumber", aadharNumber));
        criteria.add(Restrictions.eq("userType", userType));
        return (List<UserEntity>) criteria.list();
    }

    @Override
    public UserEntity findByUserNameNdUserType(String userName , UserType userType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("userName", userName));
        criteria.add(Restrictions.eq("userType", userType));
        return (UserEntity) criteria.uniqueResult();
    }
    
}
