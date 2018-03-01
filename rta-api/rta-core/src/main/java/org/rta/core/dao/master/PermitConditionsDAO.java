package org.rta.core.dao.master;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.PermitConditionsEntity;

public interface PermitConditionsDAO extends GenericDAO<PermitConditionsEntity> {

    List<PermitConditionsEntity> getPermitConditions(String cov, String permitType);

    PermitConditionsEntity getPermitCondition(String code, String cov, String permitType);

    List<PermitConditionsEntity> getTempPermitConditions(String primaryPermit, String tempPermitType);

    PermitConditionsEntity getPermitCondition(String code);

}