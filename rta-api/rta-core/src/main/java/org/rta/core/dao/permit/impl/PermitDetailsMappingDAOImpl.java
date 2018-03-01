package org.rta.core.dao.permit.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.permit.PermitDetailsMappingDAO;
import org.rta.core.entity.permit.PermitDetailsMappingEntity;
import org.rta.core.entity.permit.PermitHeaderEntity;
import org.rta.core.enums.Status;
import org.springframework.stereotype.Repository;

@Repository
public class PermitDetailsMappingDAOImpl extends BaseDAO<PermitDetailsMappingEntity> implements PermitDetailsMappingDAO {

	public PermitDetailsMappingDAOImpl() {
		super(PermitDetailsMappingEntity.class);
	}

	@Override
	public List<PermitDetailsMappingEntity> getByPermitNoNdDetailType(PermitHeaderEntity permitHeaderEntity , Integer status) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("permitNo", permitHeaderEntity.getPermitNo()));
        criteria.add(Restrictions.eq("status", status));
        return (List<PermitDetailsMappingEntity>) criteria.list();
	}
	
	@Override
    public PermitDetailsMappingEntity getActiveDetails(String permitNo, String typeCode) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("permitNo", permitNo));
        criteria.add(Restrictions.eq("detailType", typeCode));
        criteria.add(Restrictions.eq("status", Status.OPEN.getValue()));
        return (PermitDetailsMappingEntity) criteria.uniqueResult();
    }
	
	@SuppressWarnings("unchecked")
    @Override
    public List<PermitDetailsMappingEntity> getActiveDetailList(String permitNo, String typeCode) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("permitNo", permitNo));
        criteria.add(Restrictions.eq("detailType", typeCode));
        criteria.add(Restrictions.eq("status", Status.OPEN.getValue()));
        return (List<PermitDetailsMappingEntity>) criteria.list();
    }
}
