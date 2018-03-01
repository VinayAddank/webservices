package org.rta.core.service.certificate;

import org.rta.core.exception.DataMismatchException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.certificate.PucDetailsModel;

/**
 *	@Author sohan.maurya created on Dec 15, 2016.
 */
public interface PucDetailsService {

    public PucDetailsModel getPucDetails(Long vehicleRcId) throws NotFoundException;

    public SaveUpdateResponse saveOrUpdate(PucDetailsModel model, String userName) throws DataMismatchException;
}
