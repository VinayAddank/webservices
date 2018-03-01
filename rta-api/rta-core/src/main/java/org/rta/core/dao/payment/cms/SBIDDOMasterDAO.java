package org.rta.core.dao.payment.cms;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.cms.SBIDDOMasterEntity;

public interface SBIDDOMasterDAO extends GenericDAO<SBIDDOMasterEntity> {

    public SBIDDOMasterEntity getByDistrictCode(String districtCode);
}
