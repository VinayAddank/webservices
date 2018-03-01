package org.rta.core.service.master;

import java.util.List;

import org.rta.core.model.master.InsuranceTypeModel;

/**
 *	@Author sohan.maurya created on Jul 12, 2016.
 */
public interface InsuranceTypeService {

    public void create(InsuranceTypeModel model);

    public InsuranceTypeModel getByCode(String code);
    
    public List<InsuranceTypeModel> getAll();

}
