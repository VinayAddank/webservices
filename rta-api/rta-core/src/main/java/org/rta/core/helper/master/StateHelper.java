package org.rta.core.helper.master;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.master.StateEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.StateModel;
import org.springframework.stereotype.Component;
@Component
public class StateHelper implements BaseConverter<StateEntity, StateModel> {
	
	@Override
	public Collection<StateModel> convertToModelList(Collection<StateEntity> source) {
	    if(source == null){
            return null;
        }
		return source.stream().map(s->convertToModel(s)).collect(Collectors.toList());
	}
	
	@Override
	public StateModel convertToModel(StateEntity source) {
	    if(source == null){
	        return null;
	    }
		StateModel sm = new StateModel();
		sm.setId(source.getStateId());
		sm.setCode(source.getCode());
		sm.setName(source.getName());
		sm.setStatus(source.getStatus());
		sm.setCreatedBy(source.getCreatedBy());
		sm.setCreatedOn(source.getCreatedOn());
        return sm;
	}

    @Override
    public StateEntity convertToEntity(StateModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<StateEntity> convertToEntityList(Collection<StateModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
