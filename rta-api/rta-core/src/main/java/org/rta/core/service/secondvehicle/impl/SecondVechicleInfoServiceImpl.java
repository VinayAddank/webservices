package org.rta.core.service.secondvehicle.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import javax.xml.bind.JAXBException;

import org.rta.core.service.secondvehicle.SecondVechicleInfoService;
import org.rta.secondvehicle.api.SecondVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for the AadharInfoService. Class calls aadhar api and get
 * response.
 * 
 * @author Sriram
 *
 */
@Service
public class SecondVechicleInfoServiceImpl implements SecondVechicleInfoService {

	private final SecondVehicleService secondVehicleService;

	@Autowired
	public SecondVechicleInfoServiceImpl(final SecondVehicleService secondVehicleService) {
		this.secondVehicleService = secondVehicleService;

	}

	@Override
	public String GetsecondvehiclecheckforotsiDealer(String strFName, String strFHName, String strLName,
			String strDOB) throws FileNotFoundException, UnsupportedEncodingException, JAXBException {
		String secondVehicleResponseModel = secondVehicleService
				.GetsecondvehiclecheckforotsiDealer(strFName, strFHName, strLName, strDOB);
		// System.out.println(secondVehicleResponseModel.getNewDataSet().getTable().getCount());
		// System.out.println(secondVehicleResponseModel.getNewDataSet().getTable1().getAPPLIACNT_LNAME());
		if (Objects.isNull(secondVehicleResponseModel)) {
			return null;
		}

		return secondVehicleResponseModel;
	}
}
