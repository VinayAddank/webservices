package org.rta.core.dao.master;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.QualificationEntity;

public interface QualificationDAO extends GenericDAO<QualificationEntity> {

    public QualificationEntity getByCode(Integer code);
}
