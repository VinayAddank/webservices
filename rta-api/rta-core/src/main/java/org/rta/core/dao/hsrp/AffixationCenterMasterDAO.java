package org.rta.core.dao.hsrp;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.hsrp.AffixationCenterMasterEntity;

/**
 *	@Author sohan.maurya created on Sep 9, 2016.
 */
public interface AffixationCenterMasterDAO extends GenericDAO<AffixationCenterMasterEntity> {

    public String getAffixationCenterMaster(Integer mandalCode,int districtId);
}
