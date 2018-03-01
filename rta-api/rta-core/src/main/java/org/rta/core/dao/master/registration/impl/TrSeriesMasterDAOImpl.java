package org.rta.core.dao.master.registration.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.registration.TrSeriesMasterDAO;
import org.rta.core.entity.master.registration.TrSeriesMasterEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.enums.Status;
import org.springframework.stereotype.Repository;

@Repository
public class TrSeriesMasterDAOImpl extends BaseDAO<TrSeriesMasterEntity> implements TrSeriesMasterDAO {

	public TrSeriesMasterDAOImpl() {
		super(TrSeriesMasterEntity.class);
	}

	@Override
	public TrSeriesMasterEntity getByDistrict(AddressEntity addressEntity) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("districtEntity.districtId", addressEntity.getDistrict()));
		criteria.add(Restrictions.eq("status", Status.PRESENT.getValue()));
		return (TrSeriesMasterEntity) criteria.uniqueResult();
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<TrSeriesMasterEntity> getFutureTrSeries(AddressEntity addressEntity) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("districtEntity.districtId", addressEntity.getDistrict()));
        criteria.add(Restrictions.eq("status", Status.FUTURE.getValue()));
        return (List<TrSeriesMasterEntity>) criteria.list();
    }

}
