package org.rta.core.service.export;

import java.util.List;

import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.model.export.FormsModel;
import org.rta.core.model.export.IibExportModel;
import org.rta.core.model.export.OwnerDetails;
import org.rta.core.model.export.SinkAllData;
import org.rta.core.model.export.SinkTaxDetails;

/**
 *	@Author sohan.maurya created on Sep 16, 2016.
 */
public interface ExportDataService {

    /**
     * Get Vehicle Related Details with dealer and owner etc.. between a day
     * 
     * @return
     */
    public List<SinkAllData> getAllVehicleDetailsBetweenADay(String from, String to, Boolean byTrIssueTime);

    /**
     * Get Vehicle Tax Related Details between a day
     * 
     * @return
     */
    public List<SinkTaxDetails> getAllTaxDetailsBetweenADay(String from, String to, Boolean byTrIssueTime);

    /**
     * Get vehicle details for all information
     * 
     * @param vehicleRcId
     * @return
     */
    public FormsModel getVehicleDetails(Long vehicleRcId);

    /**
     * To get vehicle details all Information
     * 
     * @param vehicleRcId
     * @return
     */
    public SinkAllData getVehicleDetailsInfo(Long vehicleRcId, String prNumber);



    public OwnerDetails getOwnerDetails(VehicleRCEntity vehicleRCEntity);
    
    /**
     * Getting data between two date for IIB Things
     * @param from
     * @param to
     * @return
     */

	public List<IibExportModel> getVehicleRegistrationDetails(String keyType, String from, String to);
}
