package org.rta.core.dao.master.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.master.NeighStateDAO;
import org.rta.core.dao.master.StateDAO;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.NeighStateEntity;
import org.rta.core.entity.master.StateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NeighStateDAOImpl extends BaseDAO<NeighStateEntity> implements NeighStateDAO {

	@Autowired
	public StateDAO stateDAO;
	
    public NeighStateDAOImpl() {
        super(NeighStateEntity.class);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public Collection<StateEntity> getAllNeighbouring(Long stateId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("stateId", stateId));
		criteria.setProjection(Projections.property("neighStateId"));
		List<Long> list = (List<Long>) criteria.list();
		return stateDAO.getStatesById(list);
	}

}
