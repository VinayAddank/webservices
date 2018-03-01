package org.rta.core.service.certificate;

import java.util.List;

import org.rta.core.exception.DataMismatchException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.certificate.NocDetailsModel;

/**
 *	@Author sohan.maurya created on Dec 13, 2016.
 */

public interface NocDetailsService {

    public NocDetailsModel getNocDetails(Long vehicleRcId, String prNumber) throws NotFoundException;

    public SaveUpdateResponse saveOrUpdate(NocDetailsModel model, String userName) throws DataMismatchException;

    public List<NocDetailsModel> getNocAddressList(String districtCode) throws NotFoundException;

    public NocDetailsModel getNocAddressDetails(String code) throws NotFoundException;
}
