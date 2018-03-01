package org.rta.core.service.secondvehicle;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

/**
 * AadharInfoService provides details for aadhar
 * 
 * @author Sriram
 *
 */
public interface SecondVechicleInfoService {

	public String GetsecondvehiclecheckforotsiDealer(String strFName, String strFHName,
			String strLName, String strDOB) throws FileNotFoundException, UnsupportedEncodingException, JAXBException;
}
