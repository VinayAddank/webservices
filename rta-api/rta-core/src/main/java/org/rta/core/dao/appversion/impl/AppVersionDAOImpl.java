/**
 * 
 */
package org.rta.core.dao.appversion.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.appversion.AppVersionDAO;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.appversion.AppVersionEntity;
import org.springframework.stereotype.Repository;

/**
 * @author arun.verma
 *
 */
@Repository
public class AppVersionDAOImpl extends BaseDAO<AppVersionEntity> implements AppVersionDAO{

    public AppVersionDAOImpl() {
        super(AppVersionEntity.class);
    }

    @Override
    public AppVersionEntity getAppVersion(Integer major, Integer minor, Integer revision) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("verMajor", major));
        criteria.add(Restrictions.eq("verMinor", minor));
        criteria.add(Restrictions.eq("verRevision", revision));
        return (AppVersionEntity) criteria.uniqueResult();
    }

}
