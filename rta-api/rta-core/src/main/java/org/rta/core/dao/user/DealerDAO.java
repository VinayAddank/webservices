package org.rta.core.dao.user;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.user.DealerEntity;

public interface DealerDAO extends GenericDAO<DealerEntity> {

    DealerEntity findByDealerId(Long id);

    DealerEntity findByUserId(Long id);

    DealerEntity findByUserName(String name);
    
    List<DealerEntity> getAllChildDealers(Long parentId);
}
