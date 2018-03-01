package org.rta.core.service.sync;

import org.rta.core.exception.DataMismatchException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.sync.RCAadharSeedModel;
import org.rta.core.model.sync.SyncDataModel;

public interface SyncService {

	public SaveUpdateResponse syncData(SyncDataModel syncDataMOdel);
	
	public SaveUpdateResponse aadhaarSeeding(RCAadharSeedModel rcAadharSeedModel, String userName) throws DataMismatchException;

}
