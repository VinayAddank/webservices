/**
 * 
 */
package org.rta.core.dao.master;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.PermitRouteConditionsMasterEntity;

/**
 * @author arun.verma
 *
 */
public interface PermitRouteDAO extends GenericDAO<PermitRouteConditionsMasterEntity>{

    List<PermitRouteConditionsMasterEntity> getPermitRoute(String cov, String permitType);

    public PermitRouteConditionsMasterEntity getPermitRoute(String code, String cov, String permitType);

    List<PermitRouteConditionsMasterEntity> getTempPermitRoute(String primaryPermitType, String tempPermitType);

    public PermitRouteConditionsMasterEntity getPermitRoute(String code);

}
