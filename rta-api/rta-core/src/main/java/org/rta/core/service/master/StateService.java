package org.rta.core.service.master;

import java.util.List;

import org.rta.core.model.master.StateModel;

public interface StateService {
	
	public abstract List<StateModel> getAll();
	
	public abstract List<StateModel> getAllNeighbouring(String prNumber) throws Exception;
	
	public abstract StateModel getStateByName(String stateName);

    public abstract StateModel getStateByNameOrCode(String stateNameOrCode);
}
