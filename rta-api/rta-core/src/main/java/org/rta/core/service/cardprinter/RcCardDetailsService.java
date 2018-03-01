package org.rta.core.service.cardprinter;

import java.util.List;

import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.cardprinter.PrintStatusModel;
import org.rta.core.model.cardprinter.RcCardDetailsModel;
import org.rta.core.model.cardprinter.RtyFromtoModel;

/**
 *	@Author sohan.maurya created on Sep 12, 2016.
 */
public interface RcCardDetailsService {
    /**
     * get RcCardDetailsModel for a particular prNumber
     * 
     * @param prNumber
     * @param rty
     * @param rtaOfficeCode
     * @return
     */
    public RcCardDetailsModel getRcCardDetails(String prNumber, String rtaOfficeCode);

    /**
     * get
     * 
     * @param from
     * @param to
     * @param rty
     * @param rtaOfficeCode
     * @return
     */
    public List<RcCardDetailsModel> getRcCardDetailsByDate(String date, String rtaOfficeCode);

    /**
     * get RC Card between two dates
     * 
     * @param from
     * @param to
     * @param rtaOfficeCode
     * @return
     */
    public List<RtyFromtoModel> getRcCardBetweenTwoDate(String from, String to, String rtaOfficeCode);

    /**
     * update print date and isprint in VehicleRCEntity
     * 
     * @param printStatusModel
     * @return
     */
    public SaveUpdateResponse updatePrintStatus(PrintStatusModel printStatusModel);

    /**
     * Get RTA office code
     * 
     * @param userId
     * @return
     */
    public String getRtaOfficeCode(Long userId);

    /**
     * Get RTA office CCO signature
     * 
     * @param userId
     * @return
     */
    public String getCcoSignature(Long vehicleRcId);
}
