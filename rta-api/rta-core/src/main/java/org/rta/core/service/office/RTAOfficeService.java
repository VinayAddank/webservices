package org.rta.core.service.office;

import java.util.List;

import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.model.office.RTAOfficeModel;

public interface RTAOfficeService {
	public RtaOfficeEntity getRTAOffice4MandalNm(String mandalNm);

    /**
     * Get RTA Office List, who is office active
     * 
     * @param status
     * @return
     */
    public List<RTAOfficeModel> getRtaOfficeList(Boolean status);

    RTAOfficeModel getRTAOffice(String code);
    
    List<RTAOfficeModel> getRtaOfficeInfo();

    List<RTAOfficeModel> getRtaOfficeListByState(String stateCode, Boolean status, Boolean showUnit);

}
