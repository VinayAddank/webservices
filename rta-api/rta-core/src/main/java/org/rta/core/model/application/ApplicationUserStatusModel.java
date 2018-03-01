package org.rta.core.model.application;

import java.util.Map;

public class ApplicationUserStatusModel {

    private Map<Long, Integer> ccoActionStatus;
    private Map<Long, Integer> mviActionStatus;
    private Map<Long, Integer> aoActionStatus;
    private Map<Long, Integer> rtoActionStatus;
    
    public Map<Long, Integer> getCcoActionStatus() {
        return ccoActionStatus;
    }
    public void setCcoActionStatus(Map<Long, Integer> ccoActionStatus) {
        this.ccoActionStatus = ccoActionStatus;
    }
    public Map<Long, Integer> getMviActionStatus() {
        return mviActionStatus;
    }
    public void setMviActionStatus(Map<Long, Integer> mviActionStatus) {
        this.mviActionStatus = mviActionStatus;
    }
    public Map<Long, Integer> getAoActionStatus() {
        return aoActionStatus;
    }
    public void setAoActionStatus(Map<Long, Integer> aoActionStatus) {
        this.aoActionStatus = aoActionStatus;
    }
    public Map<Long, Integer> getRtoActionStatus() {
        return rtoActionStatus;
    }
    public void setRtoActionStatus(Map<Long, Integer> rtoActionStatus) {
        this.rtoActionStatus = rtoActionStatus;
    }
    
}
