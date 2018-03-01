package org.rta.core.dao.master;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.DocTypesEntity;

public interface DocTypesDAO extends GenericDAO<DocTypesEntity> {

    public List<Long> getMandatoryDocTypesIds();
}
