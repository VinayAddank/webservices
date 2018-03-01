package org.rta.core.dao.office.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.springframework.stereotype.Repository;

@Repository("rtaOfficeDAO")
public class RtaOfficeDAOImpl extends BaseDAO<RtaOfficeEntity> implements RtaOfficeDAO{

    public RtaOfficeDAOImpl() {
        super(RtaOfficeEntity.class);
	}

    @Override
    public RtaOfficeEntity getRtaOfficeDetailsByCode(String rtaOfficeCode) {
        Criteria criteria =
                getSession().createCriteria(getPersistentClass()).add(Restrictions.eq("code", rtaOfficeCode));
        return (RtaOfficeEntity) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RtaOfficeEntity> getRtaOfficeList(Boolean status) {

        Criteria criteria = getSession().createCriteria(getPersistentClass()).add(Restrictions.eq("status", status));

        return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<RtaOfficeEntity> getRtaOfficeList(List<String> districts, Boolean status, Boolean showUnit) {
        Criteria criteria = getSession().createCriteria(getPersistentClass())
                .add(Restrictions.in("districtCode", districts)).add(Restrictions.eq("status", status));
        if (showUnit) {
            criteria.add(Restrictions.or(Restrictions.eq("officeType", "RTA"), Restrictions.eq("officeType", "UNI")));
        } else {
            criteria.add(Restrictions.eq("officeType", "RTA"));
        }
        return criteria.list();
    }
   
    @SuppressWarnings("unchecked")
	@Override
	public List<RtaOfficeEntity> getRtaOfficeInfo() {
    	Long stateId=3l; 
    	String sql ="select * from rta_office ro where ro.rta_office_id in (select rm.rta_office_id from rta_mandal_mapping rm where rm.mandal_id in ( select m.mandal_id from mandal m where m.district_id in (select d.district_id from district d where state_id= :stateId)))";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.addEntity(RtaOfficeEntity.class);
		query.setParameter("stateId", stateId);
		return (List<RtaOfficeEntity>) query.list();
	}

    @Override
    public RtaOfficeEntity getRtaOfficeById(Long rtaOfficeId) {
        Criteria criteria =
                getSession().createCriteria(getPersistentClass()).add(Restrictions.eq("rtaOfficeId", rtaOfficeId));
        return (RtaOfficeEntity) criteria.uniqueResult();
    }


}
