package org.rta.core.service.master.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.master.NeighStateDAO;
import org.rta.core.dao.master.impl.StateDAOImpl;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.helper.master.StateHelper;
import org.rta.core.model.master.StateModel;
import org.rta.core.service.master.StateService;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateServiceImpl implements StateService  {

	@Autowired
	private StateDAOImpl stateDAO ;
	
	@Autowired
	public VehicleDAO vehicleDAO ;
	
	@Autowired
	public AddressDAO addressDAO ;
	
	@Autowired
	public NeighStateDAO neighStateDAO ;
	
	@Autowired
	private StateHelper stateHelper ;
	
	@Override
	@Transactional
	public List<StateModel> getAll() {
	    boolean status = true;
		return (List<StateModel>)stateHelper.convertToModelList(stateDAO.getAll(status));
	}
	
	@Override
	@Transactional
	public List<StateModel> getAllNeighbouring(String prNumber) throws Exception {
		VehicleRCEntity vehicle = vehicleDAO.getVehicleRCByPRNumber(prNumber);
		if (ObjectsUtil.isNull(vehicle)) {
            throw new VehicleRcNotFoundException("VehicleRc not found for PR Number : " + prNumber);
        }
		AddressEntity address = addressDAO.findByUserId(vehicle.getUserId().getUserId());
		if (ObjectsUtil.isNull(address)) {
            throw new VehicleRcNotFoundException("Address not found for User ID : " + vehicle.getUserId().getUserId());
        }
		return (List<StateModel>)stateHelper.convertToModelList(neighStateDAO.getAllNeighbouring(address.getState()));
	}

    @Override
    @Transactional
    public StateModel getStateByName(String stateName) {
        StateModel mdl = stateHelper.convertToModel(stateDAO.getStateByName(stateName));
        return mdl;
    }
    
    @Override
    @Transactional
    public StateModel getStateByNameOrCode(String stateNameOrCode) {
        StateModel mdl = stateHelper.convertToModel(stateDAO.getStateByNameOrCode(stateNameOrCode));
        return mdl;
    }

}
