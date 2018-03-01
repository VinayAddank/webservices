/**
 * 
 */
package org.rta.core.dao.insurance;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.insurance.CfxTxnDtlEntity;

/**
 * @author arun.verma
 *
 */
public interface CfxTxnDtlDAO extends GenericDAO<CfxTxnDtlEntity>{

    public CfxTxnDtlEntity getByRtaCfxTxnId(String rtaCfxTxnId);
    
    public CfxTxnDtlEntity getByCfxTxnId(String cfxTxnId);
    
    public CfxTxnDtlEntity getByCfxPaymentId(String cfxPaymentId);
    
}
