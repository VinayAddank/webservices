package org.rta.core.service.master;

import java.util.List;

import org.rta.core.model.master.DistrictModel;

public interface DistrictService {
	
	public abstract List<DistrictModel> getAll();
	
	public abstract List<DistrictModel> getAllNeighbouring(String prNumber) throws Exception;

	public DistrictModel getDistrictByName(String name);
    
    public List<DistrictModel> getDistrictsByStateCode(String name);
}
