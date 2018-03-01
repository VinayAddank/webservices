/**
 * 
 */
package org.rta.core.model.permit;

import java.util.List;

import org.rta.core.model.master.PermitTypeModel;

/**
 * @author arun.verma
 *
 */
public class PermitTempPermitModel {

    private PermitTypeModel permitType;
    private List<PermitTypeModel> temporaryPermits;

    public PermitTypeModel getPermitType() {
        return permitType;
    }

    public void setPermitType(PermitTypeModel permitType) {
        this.permitType = permitType;
    }

    public List<PermitTypeModel> getTemporaryPermits() {
        return temporaryPermits;
    }

    public void setTemporaryPermits(List<PermitTypeModel> temporaryPermits) {
        this.temporaryPermits = temporaryPermits;
    }

}
