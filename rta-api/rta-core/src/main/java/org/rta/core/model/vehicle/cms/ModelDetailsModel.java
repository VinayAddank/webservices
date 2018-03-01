package org.rta.core.model.vehicle.cms;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *	@Author sohan.maurya created on Sep 9, 2016.
 */

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class ModelDetailsModel {

    private Integer modelDetailsId;
    private String modelName;
    private Integer makerMasterId;


    public Integer getModelDetailsId() {
        return modelDetailsId;
    }

    public void setModelDetailsId(Integer modelDetailsId) {
        this.modelDetailsId = modelDetailsId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getMakerMasterId() {
        return makerMasterId;
    }

    public void setMakerMasterId(Integer makerMasterId) {
        this.makerMasterId = makerMasterId;
    }



}
