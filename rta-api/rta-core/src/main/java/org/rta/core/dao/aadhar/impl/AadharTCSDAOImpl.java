package org.rta.core.dao.aadhar.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.aadhar.AadhaTCSDAO;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.applicant.AadharEntity;
import org.rta.core.model.AadharModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AadharTCSDAOImpl extends BaseDAO<AadharEntity> implements AadhaTCSDAO {

    public AadharTCSDAOImpl() {
		super(AadharEntity.class);
    }

	@Override
	@Transactional
	public void saveOrUpdate(AadharModel aadharModel, String userName) {

	}

    @Override
    public AadharEntity getAadharDetails(Long aadharNumber) {
        Criteria criteria = getSession().createCriteria(getPersistentClass()).add(Restrictions.eq("uid", aadharNumber))
                .add(Restrictions.eq("auth_status", "SUCCESS").ignoreCase()).addOrder(Order.desc("modifiedOn"))
                .setMaxResults(1);
        return (AadharEntity) criteria.uniqueResult();
    }
}
