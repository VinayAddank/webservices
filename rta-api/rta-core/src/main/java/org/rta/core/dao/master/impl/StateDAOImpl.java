package org.rta.core.dao.master.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.StateDAO;
import org.rta.core.entity.master.StateEntity;
import org.springframework.stereotype.Repository;

@Repository
public class StateDAOImpl extends BaseDAO<StateEntity>implements StateDAO{

	public StateDAOImpl() 
	{
		super(StateEntity.class);
	}

    @Override
    public StateEntity getStateByName(String name) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("name", name).ignoreCase());
        criteria.add(Restrictions.eq("status", true));
        return (StateEntity) criteria.uniqueResult();
    }
    
    @Override
    public StateEntity getStateByNameOrCode(String nameOrCode) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("name", nameOrCode).ignoreCase());
        criteria.add(Restrictions.eq("status", true));
        return (StateEntity) criteria.uniqueResult();
    }

    @Override
    public StateEntity getStateByCode(String code) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", code).ignoreCase());
        criteria.add(Restrictions.eq("status", true));
        return (StateEntity) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<StateEntity> getStatesById(List<Long> ids) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.in("stateId", ids));
		return (List<StateEntity>) criteria.list();
	}
}
