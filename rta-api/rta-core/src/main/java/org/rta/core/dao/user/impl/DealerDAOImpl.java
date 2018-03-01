package org.rta.core.dao.user.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.DealerDAO;
import org.rta.core.entity.user.DealerEntity;
import org.springframework.stereotype.Repository;

@Repository
public class DealerDAOImpl extends BaseDAO<DealerEntity> implements DealerDAO {

    public DealerDAOImpl() {
        super(DealerEntity.class);
    }

    @Override
    public DealerEntity findByDealerId(Long id) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("dealerId", id));
        return (DealerEntity) criteria.uniqueResult();
    }

    @Override
    public DealerEntity findByUserId(Long id) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("user.userId", id));
        return (DealerEntity) criteria.uniqueResult();
    }

    @Override
    public DealerEntity findByUserName(String name) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.createAlias("user", "u").add(Restrictions.eq("u.userName", name).ignoreCase());
        return (DealerEntity) criteria.uniqueResult();
    }

    

    //select dealer_id from dealer WHERE parent_id in(select dealer_id from dealer where parent_id=1) or parent_id=1
    @Override
    public List<DealerEntity> getAllChildDealers(Long parentId) {
    	String query="select d from DealerEntity d WHERE dealerId= :parentId or d.parentId= :parentId or parentId in(select dealerId from DealerEntity where parentId= :parentId)"
    			+ "";
    	List<DealerEntity> dealers=(List<DealerEntity>)getSession().createQuery(query).setParameter("parentId", parentId).list();
    	return dealers;      
    }

}
