package org.rta.core.dao.master.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.MandalDAO;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.stereotype.Repository;

/**
 * @author arun.verma
 *
 */

@Repository
public class MandalDAOImpl extends BaseDAO<MandalEntity> implements MandalDAO {

    public MandalDAOImpl() {
        super(MandalEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MandalEntity> getAll(){
        Criteria criteria = getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("status", true))
                .setProjection(Projections.projectionList()
                .add(Projections.property("mandalId"), "mandalId")
                .add(Projections.property("code"), "code")
                .add(Projections.property("name"), "name"))
                .addOrder(Order.asc("name"))
                .setResultTransformer(Transformers.aliasToBean(MandalEntity.class));
        return (List<MandalEntity>)criteria.list();
    }
    
    @Override
    public MandalEntity getByMandalName(String mandalName) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("name", mandalName).ignoreCase());
        criteria.add(Restrictions.eq("status", true));
        return (MandalEntity) criteria.uniqueResult();
    }

    @Override
    public MandalEntity getByMandalCode(Integer mandalCode) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", mandalCode));
        criteria.add(Restrictions.eq("status", true));
        return (MandalEntity) criteria.uniqueResult();
    }
    
    @Override
    public MandalEntity getMandalByCode(Integer mandalCode) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", mandalCode));
        return (MandalEntity) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<MandalEntity> getByDistrictCode(String code) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("status", true));
        criteria.createAlias("districtEntity", "de");
        criteria.add(Restrictions.eq("de.code", code).ignoreCase());
        criteria.addOrder(Order.asc("name"));
        return (List<MandalEntity>) criteria.list();
    }

    @Override
    public MandalEntity getMandal(String mandalName, String districtName) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("name", mandalName).ignoreCase());
        criteria.add(Restrictions.eq("status", true));
        criteria.createAlias("districtEntity", "de");
        criteria.add(Restrictions.eq("de.name", districtName).ignoreCase());
        return (MandalEntity) criteria.uniqueResult();
    }

    @Override
    public RtaOfficeEntity getRTAOfficeByMandalCode(Integer code) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", code));
        criteria.add(Restrictions.eq("status", true));
        MandalEntity me = (MandalEntity) criteria.uniqueResult();
        if(ObjectsUtil.isNull(me)){
            return null;
        }
        return me.getRtaOfficeEntity();
    }
    @Override
    public Object getRtaMandalMapping(Integer mandalId)
    {
    	String sql = "select rta_office_id from rta_mandal_mapping  where mandal_id =('" + mandalId + "')";
    	SQLQuery query = getSession().createSQLQuery(sql);
		Object results = query.uniqueResult();
		return results;
    	
    }
}
