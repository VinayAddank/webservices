package org.rta.core.dao.user.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.MedicalPractitionerUserDAO;
import org.rta.core.entity.user.MedicalPractitionerUserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class MedicalPractitionerUserDAOImpl extends BaseDAO<MedicalPractitionerUserEntity> implements MedicalPractitionerUserDAO {

	public MedicalPractitionerUserDAOImpl() {
		super(MedicalPractitionerUserEntity.class);
	}

    @Override
    public MedicalPractitionerUserEntity getMedicalPractitionerUserByUserId(Long id) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("user.userId", id));
        return (MedicalPractitionerUserEntity) criteria.uniqueResult();
    }

	
}
