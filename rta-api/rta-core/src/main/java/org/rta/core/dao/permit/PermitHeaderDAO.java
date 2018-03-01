/**
 * 
 */
package org.rta.core.dao.permit;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.permit.PermitHeaderEntity;

/**
 * @author arun.verma
 *
 */
public interface PermitHeaderDAO extends GenericDAO<PermitHeaderEntity> {

    public PermitHeaderEntity getPrimaryPermitActiveByPr(String prNumber);

    public PermitHeaderEntity getTempPermitActiveByPr(String prNumber);
    List<PermitHeaderEntity> getPermitActiveByPr(String prNumber);
    
    public PermitHeaderEntity getActivePermit(String prNumber, Boolean isTemp);
}
