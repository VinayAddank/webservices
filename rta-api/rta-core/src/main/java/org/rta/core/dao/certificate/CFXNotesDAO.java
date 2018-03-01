package org.rta.core.dao.certificate;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.certificate.CFXNotesEntity;
import org.rta.core.enums.Status;

public interface CFXNotesDAO extends GenericDAO<CFXNotesEntity> {

    CFXNotesEntity getByPrNumber(String prNumber, Status status);

}
