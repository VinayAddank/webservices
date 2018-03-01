/**
 * 
 */
package org.rta.core.service.certificate;

import org.rta.core.entity.permit.PermitHeaderEntity;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.permit.PermitAuthorizationCardModel;
import org.rta.core.model.sync.SyncDataModel;

/**
 * @author arun.verma
 *
 */
public interface PermitAuthCardService {

    public SaveUpdateResponse createPermitAuthCard(PermitHeaderEntity permitHeader);

    public SaveUpdateResponse renewPermitAuthCard(SyncDataModel syncDataModel);
    
    public PermitAuthorizationCardModel getPermitAuthCardDetails(String prNumber);
}
