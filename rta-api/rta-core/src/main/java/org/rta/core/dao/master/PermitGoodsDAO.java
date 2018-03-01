/**
 * 
 */
package org.rta.core.dao.master;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.PermitGoodsMasterEntity;

/**
 * @author arun.verma
 *
 */
public interface PermitGoodsDAO extends GenericDAO<PermitGoodsMasterEntity>{

    List<PermitGoodsMasterEntity> getPermitGoods(String cov, String permitType);

    PermitGoodsMasterEntity getPermitGood(String code, String cov, String permitType);

    List<PermitGoodsMasterEntity> getTempPermitGoods(String primaryPermitType, String tempPermitType);

    PermitGoodsMasterEntity getPermitGood(String code);

}
