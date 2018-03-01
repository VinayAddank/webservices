package org.rta.core.dao.user.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.RTAEmployeeDAO;
import org.rta.core.entity.user.RTAEmployeeEntity;
import org.rta.core.enums.UserType;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.stereotype.Repository;

@Repository
public class RTAEmployeeDAOImpl extends BaseDAO<RTAEmployeeEntity> implements RTAEmployeeDAO {

    public RTAEmployeeDAOImpl() {
        super(RTAEmployeeEntity.class);
    }

    @Override
    public RTAEmployeeEntity getByUserId(Long userId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("userEntity.userId", userId));
        return (RTAEmployeeEntity) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RTAEmployeeEntity> getRtaEmployeeDetails(Long rtaOfficeId, String  employeeName) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("rtaOfficeEntity.rtaOfficeId", rtaOfficeId));
        if(!StringsUtil.isNullOrEmpty(employeeName)){
        	criteria.add(Restrictions.like("userEntity.firstName", employeeName, MatchMode.ANYWHERE).ignoreCase());
        }
        return (List<RTAEmployeeEntity>) criteria.list();
     }

    @Override
    public RTAEmployeeEntity getRTAEmp(String rtaOfficeCode, UserType userType, Boolean status) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.createAlias("rtaOfficeEntity", "rtaOffice");
        criteria.createAlias("userEntity", "user");
        criteria.add(Restrictions.eq("rtaOffice.code", rtaOfficeCode));
        criteria.add(Restrictions.eq("user.userType", userType));
        if(ObjectsUtil.isNotNull(status)){
        	criteria.add(Restrictions.eq("user.status", status));
        }
        criteria.setMaxResults(1);
        return (RTAEmployeeEntity) criteria.uniqueResult();
    }
}
