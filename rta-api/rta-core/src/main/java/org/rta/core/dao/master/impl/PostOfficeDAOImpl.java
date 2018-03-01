/**
 * 
 */
package org.rta.core.dao.master.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.PostOfficeDAO;
import org.rta.core.entity.master.PostOfficeEntity;
import org.springframework.stereotype.Repository;

/**
 * @author arun.verma
 *
 */

@Repository
public class PostOfficeDAOImpl extends BaseDAO<PostOfficeEntity> implements PostOfficeDAO {

    public PostOfficeDAOImpl() {
        super(PostOfficeEntity.class);
    }

    /*@SuppressWarnings("unchecked")
    @Override
    public List<PostOfficeEntity> getAll(){
        Criteria criteria = getSession().createCriteria(getPersistentClass())
                .setProjection(Projections.projectionList()
                .add(Projections.property("id"), "id")
                .add(Projections.property("pinCode"), "pinCode")
                .add(Projections.property("name"), "name")
                .add(Projections.property("status"), "status"))
                .setResultTransformer(Transformers.aliasToBean(PostOfficeEntity.class));
        return (List<PostOfficeEntity>)criteria.list();
    }*/
    
    @Override
    public PostOfficeEntity getByPinCode(String pinCode) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("pinCode", pinCode));
        return (PostOfficeEntity) criteria.uniqueResult();
    }

    @Override
    public PostOfficeEntity getByPostOfficeName(String postOfficeName) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("name", postOfficeName));
        return (PostOfficeEntity) criteria.uniqueResult();
    }

}
