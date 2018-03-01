package org.rta.core.dao.master.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.TaxMasterDAO;
import org.rta.core.entity.master.TaxMasterEntity;
import org.springframework.stereotype.Repository;

@Repository
public class TaxMasterDAOImpl extends BaseDAO<TaxMasterEntity> implements TaxMasterDAO {
    public TaxMasterDAOImpl() {
        super(TaxMasterEntity.class);
    }

    public TaxMasterEntity getByIsSecondVehicleNdInvoiceAmt(double invoiceAmt, Integer isSecondVehicle) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        if (isSecondVehicle > 1)
            criteria.add(Restrictions.eq("vehicleNo", isSecondVehicle));
        else {
            criteria.add(Restrictions.eq("vehicleNo", isSecondVehicle));
            criteria.add(Restrictions.lt("priceFrom", invoiceAmt));
            criteria.add(Restrictions.gt("priceTo", invoiceAmt));
            // criteria.add(Restrictions.gt("priceFrom", new Double(invoiceAmt)));
        }
        return (TaxMasterEntity) criteria.uniqueResult();
    }

    @Override
    public TaxMasterEntity getBytax(double taxPercentage) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("taxPercent", new Double(taxPercentage)));
        return (TaxMasterEntity) criteria.uniqueResult();
    }
}
