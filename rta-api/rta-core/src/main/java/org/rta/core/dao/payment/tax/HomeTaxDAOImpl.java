package org.rta.core.dao.payment.tax;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.tax.HomeTaxEntity;
import org.rta.core.utils.StringsUtil;
import org.springframework.stereotype.Repository;

@Repository
public class HomeTaxDAOImpl extends BaseDAO<HomeTaxEntity> implements HomeTaxDAO {

	public HomeTaxDAOImpl() {
		super(HomeTaxEntity.class);
	}

	@Override
	public HomeTaxEntity getHomeTax(String vehicleSubClass, String permitCode, String ownershipType, String stateCode, String permitSubType,
			Integer seat, Integer ulw, Integer rlw,  double invoiceAmt) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("vehicleSubClass", vehicleSubClass));
		criteria.add(Restrictions.and(Restrictions.ge("fromInvoiceAmt", invoiceAmt),Restrictions.le("toInvoiceAmt", invoiceAmt)));
		if(!StringsUtil.isNullOrEmpty(permitCode)){
			criteria.add(Restrictions.eq("permitCode", permitCode));
		}
		if(!StringsUtil.isNullOrEmpty(stateCode)){
			criteria.add(Restrictions.eq("stateCode", stateCode));
		}
		if(!StringsUtil.isNullOrEmpty(ownershipType)){
			criteria.add(Restrictions.eq("ownershipType", ownershipType));
		}
		if(!StringsUtil.isNullOrEmpty(permitSubType)){
			criteria.add(Restrictions.eq("permitSubType", permitSubType));
		}
		if(seat != null){
			criteria.add(Restrictions.and(Restrictions.ge("seatFrom", seat),Restrictions.le("seatTo", seat)));
		}
		if(ulw != null){
			criteria.add(Restrictions.and(Restrictions.ge("ulwFrom", ulw),Restrictions.le("ulwTo", ulw)));
		}
		if(rlw != null){
			criteria.add(Restrictions.and(Restrictions.ge("rlwFrom", rlw),Restrictions.le("rlwTo", rlw)));
		}
		criteria.addOrder(Order.desc("homeTaxId"));
		criteria.setMaxResults(1);
		return (HomeTaxEntity)criteria.uniqueResult();
	}




}
