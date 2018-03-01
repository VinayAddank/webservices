/**
 * 
 */
package org.rta.core.service.insurance;

import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.insurance.CfxTxnDtlModel;

/**
 * @author arun.verma
 *
 */
public interface CfxTxnDtlService {

    public CfxTxnDtlModel getByRtaCfxTxnId(String rtaCfxTxnId);

    public CfxTxnDtlModel getByCfxTxnId(String cfxTxnId);

    public CfxTxnDtlModel getByCfxPaymentId(String cfxPaymentId);
    
    public SaveUpdateResponse saveUpdate(CfxTxnDtlModel cfxTxnDtlModel);
    
    public SaveUpdateResponse updatePaymentStatus(String cfxTxnId, String success, String code);
    
    public SaveUpdateResponse updatePolicy(String cfxTxnId, String success, String policyNumber, String policyDoc);
    
}
