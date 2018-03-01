package org.rta.core.service.master.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.master.NeighDistrictDAO;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.OwnershipType;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.helper.master.DistrictHelper;
import org.rta.core.model.master.DistrictModel;
import org.rta.core.model.master.StateModel;
import org.rta.core.service.master.DistrictService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistrictServiceImpl implements DistrictService {

	@Autowired
	public DistrictDAO districtDAO ;
	
	@Autowired
	public VehicleDAO vehicleDAO ;
	
	@Autowired
	public AddressDAO addressDAO ;
	
	@Autowired
	public NeighDistrictDAO neighDistrictDAO ;
	
	@Autowired
	public DistrictHelper districtHelper ;
	
	@Autowired
    private CustomerCorporateDAO customerCorporateDAO;
	@Autowired
	private  CustomerDAO customerDAO;
	
	@Override
	@Transactional
	public List<DistrictModel> getAll() {
	    boolean status = true;
		return (List<DistrictModel>)districtHelper.convertToModelList(districtDAO.getAll(status));
	}
	
	@Override
	@Transactional
	public List<DistrictModel> getAllNeighbouring(String prNumber) throws Exception {
		VehicleRCEntity vehicle = vehicleDAO.getVehicleRCByPRNumber(prNumber);
		if (ObjectsUtil.isNull(vehicle)) {
            throw new VehicleRcNotFoundException("VehicleRc not found for PR Number : " + prNumber);
        }
		AddressEntity address = null;
		switch (OwnershipType.getOwnershipType(vehicle.getOwnershipType())) {
        case INDIVIDUAL:
        	CustIndividualDetailsEntity custInd = customerDAO.getByVehicleRcId(vehicle.getVehicleRcId());
            address = customerDAO.getAddressDetailsByUserId(custInd.getCustIndDtlId(), "T");
            break;
        case POLICE:
        case COMPANY:
        case STU_VEHICLES:
        case ORGANIZATION:
            CustCorporateDetailsEntity custCor = customerCorporateDAO.getByVehicleRcId(vehicle.getVehicleRcId());
            address = customerCorporateDAO.getAddressDetailsByUserId(custCor.getCustCorpDtlsId(), "T");
            break;
            
		}
		if (ObjectsUtil.isNull(address)) {
            throw new VehicleRcNotFoundException("Address not found for User ID : " + vehicle.getUserId().getUserId());
        }
		List<DistrictEntity> neighDistrictList = neighDistrictDAO.getAllNeighbouring(address.getDistrict());
		List<DistrictEntity> stateDistrictList = districtDAO.getDistrictsByStateId(address.getState());
		List<DistrictModel> districtModelList=new ArrayList<>();
		DistrictModel districtModel;
		StateModel stateModel;
		for(DistrictEntity districtEntity:neighDistrictList){
			for(DistrictEntity stateDistrictEntity:stateDistrictList){
				if(districtEntity.getDistrictId()==stateDistrictEntity.getDistrictId()){
					districtModel=new DistrictModel();
					stateModel=new StateModel();
					districtModel.setCode(districtEntity.getCode());
					districtModel.setName(districtEntity.getName());
					stateModel.setCode(districtEntity.getStateEntity().getCode());
					stateModel.setName(districtEntity.getStateEntity().getName());
					districtModel.setStateModel(stateModel);
					districtModelList.add(districtModel);
					break;
				}
			}
		}
		return districtModelList;
	}

    @Override
    @Transactional
    public DistrictModel getDistrictByName(String name) {
    	if(StringsUtil.isNullOrEmpty(name)){
    		return null;
    	}
        return districtHelper.convertToModelNoState(districtDAO.getDistrictByName(name));
    }

    @Override
    @Transactional
    public List<DistrictModel> getDistrictsByStateCode(String code) {
        return (List<DistrictModel>) districtHelper.convertToModelListNoState(districtDAO.getDistrictsByStateCode(code));
    }

	
}
