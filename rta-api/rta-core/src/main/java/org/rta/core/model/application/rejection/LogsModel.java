package org.rta.core.model.application.rejection;

import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonInclude(Include.ALWAYS)
public class LogsModel extends RejectionHistoryModel {

    private static final long serialVersionUID = 1586308488383630712L;
    
    private Integer iteration;

    public Integer getIteration() {
        return iteration;
    }

    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }

    @Override
    @JsonProperty(value = "createdOn")
    public Long getCreatedOn() {
        return super.getCreatedOn();
    }

    @Override
    public void setCreatedOn(Long createdOn) {
        super.setCreatedOn(createdOn);
    }

    @Override
    @JsonProperty(value = "modifiedOn")
    public Long getModifiedOn() {
        return super.getModifiedOn();
    }

    @Override
    public void setModifiedOn(Long modifiedOn) {
        super.setModifiedOn(modifiedOn);
    }
    
    
}
