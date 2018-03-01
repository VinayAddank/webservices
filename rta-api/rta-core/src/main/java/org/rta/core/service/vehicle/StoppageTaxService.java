/**
 * 
 */
package org.rta.core.service.vehicle;

import java.io.IOException;
import java.util.List;

import org.rta.core.exception.DataMismatchException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.sync.ApplicationFormDataModel;
import org.rta.core.model.vehicle.StoppageTaxDetailsModel;
import org.rta.core.model.vehicle.StoppageTaxReportModel;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author sohan.maurya
 *
 */
public interface StoppageTaxService {

	public SaveUpdateResponse saveOrUpdateStoppageTax(List<ApplicationFormDataModel> models, String prNumber, String userName)
			throws DataMismatchException, JsonParseException, JsonMappingException, IOException;

	public SaveUpdateResponse saveStoppageTaxReportDetails(StoppageTaxReportModel model, String prNumber, String userName);
	
	public StoppageTaxDetailsModel getStoppageApplication(String prNumber);
	
	public List<StoppageTaxReportModel> getStoppageInspections(String applicationNo);

	public StoppageTaxReportModel getStoppageInspection(Long stoppageReportId);
	
}
