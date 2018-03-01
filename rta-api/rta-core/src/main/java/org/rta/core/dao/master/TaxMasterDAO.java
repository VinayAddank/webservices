package org.rta.core.dao.master;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.TaxMasterEntity;

public interface TaxMasterDAO extends GenericDAO<TaxMasterEntity> {

    public TaxMasterEntity getByIsSecondVehicleNdInvoiceAmt(double invoiceAmt, Integer isSecondVehicle);

    public TaxMasterEntity getBytax(double taxPercentage);
}
