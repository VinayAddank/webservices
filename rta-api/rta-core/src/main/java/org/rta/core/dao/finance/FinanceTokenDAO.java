package org.rta.core.dao.finance;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.finance.FinanceTokenEntity;


public interface FinanceTokenDAO extends GenericDAO<FinanceTokenEntity>{
	public FinanceTokenEntity getTokenId(Long vehicleRc,Integer serviceType);
	public FinanceTokenEntity getVehicleRcIdFromToken(String tokenId);
	public FinanceTokenEntity getLastActionOnVehicleRc(Long vehicleRc);
	
	public FinanceTokenEntity getFinanceTokenDetails(String tokenId, Integer serviceType);
    List<FinanceTokenEntity> getFinanceTokenByVehicleRcId(Long vehicleRc);
    public FinanceTokenEntity getVehicleRcIdFromTokenWithReinitiated(String tokenId);
}
