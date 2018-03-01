package org.rta.core.service.master;

import java.util.List;

import org.rta.core.model.master.TaxTypeModel;

public interface TaxTypeService {
	public List<TaxTypeModel> getAll();
	
	public abstract TaxTypeModel getByCode(String code);

}
