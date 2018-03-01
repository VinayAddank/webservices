/**
 * 
 */
package org.rta.core.service.service;

import java.util.List;
import java.util.Map;

import org.rta.core.enums.PermitDetailsType;
import org.rta.core.exception.NotFoundException;
import org.rta.core.model.permit.PermitCodeDescModel;
import org.rta.core.model.permit.PermitTempPermitModel;

/**
 * @author arun.verma
 *
 */

public interface PermitService {

    public PermitTempPermitModel getTempPermits(String prNumber) throws NotFoundException;

    public Map<String, Object> getPukkaTempPermit(String prNumber);

    public List<PermitCodeDescModel> getRouteGoodsConditionsForTemporaryPermit(PermitDetailsType permitDetailsType, String primaryPermitType,
            String temporaryPermitType);

    public List<PermitCodeDescModel> getRouteGoodsConditionsForPrimaryPermit(PermitDetailsType permitDetailsType, String cov,
            String permitType);

}
