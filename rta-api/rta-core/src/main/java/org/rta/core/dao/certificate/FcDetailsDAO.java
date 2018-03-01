package org.rta.core.dao.certificate;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.certificate.FitnessCertificateEntity;

/**
 *	@Author sohan.maurya created on Nov 11, 2016.
 */
public interface FcDetailsDAO extends GenericDAO<FitnessCertificateEntity> {

	public FitnessCertificateEntity getFcDetails(Long vehicleRcId , String validityFlag);

    FitnessCertificateEntity getSuspendedFcDetails(Long vehicleRcId, String validityFlag);

    FitnessCertificateEntity getAnyFcDetails(Long vehicleRcId, String validityFlag);
}
