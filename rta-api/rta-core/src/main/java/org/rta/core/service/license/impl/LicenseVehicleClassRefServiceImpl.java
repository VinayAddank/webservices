package org.rta.core.service.license.impl;

import javax.transaction.Transactional;

import org.rta.core.dao.license.LicenseVehicleClassRefDAO;
import org.rta.core.helper.license.LicenseVehicleClassRefHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.license.LicenseVehicleClassRefModel;
import org.rta.core.service.license.LicenseVehicleClassRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenseVehicleClassRefServiceImpl implements LicenseVehicleClassRefService {

	@Autowired
	private LicenseVehicleClassRefDAO licensevehicleclassrefdao;
	
	@Autowired
	private LicenseVehicleClassRefHelper licenseVehicleClassRefHelper;
	
	@Override
	@Transactional
	public LicenseVehicleClassRefModel getLicenseVehicleClassRef(String vehicleClassCode) {
		
		return licenseVehicleClassRefHelper.convertToModel(licensevehicleclassrefdao.getLicenseVehicleClassRef(vehicleClassCode));
	}

	@Override
	public SaveUpdateResponse saveLicenseVehicleClassRef(LicenseVehicleClassRefModel model) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
