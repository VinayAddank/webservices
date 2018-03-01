package org.rta.core.model.vehicle.cms;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *	@Author sohan.maurya created on Sep 9, 2016.
 */

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class MakerMasterModel {

    private Integer makerId;
    private String makerName;

    public Integer getMakerId() {
        return makerId;
    }

    public void setMakerId(Integer makerId) {
        this.makerId = makerId;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }


}
