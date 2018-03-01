package org.rta.core.service.user;

import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.user.DealerModel;

public interface DealerService {

	public DealerModel findByUserId(Long id);

	public DealerModel findByUserName(String userName);

	public DealerModel findByUserIdForPay(Long id);

	public DealerModel findDealerByUserId(Long id);
	
	public SaveUpdateResponse saveOrUpdate(DealerModel model, String createrUserName);
}
