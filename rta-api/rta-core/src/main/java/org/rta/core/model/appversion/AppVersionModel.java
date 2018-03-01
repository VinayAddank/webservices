/**
 * 
 */
package org.rta.core.model.appversion;

import org.rta.core.enums.Status;

/**
 * @author arun.verma
 *
 */
public class AppVersionModel {

    private String appName;
    private String appUrl;
    private Integer verMajor;
    private Integer verMinor;
    private Integer verRevision;
    private Status status;
    private String message;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Integer getVerMajor() {
        return verMajor;
    }

    public void setVerMajor(Integer verMajor) {
        this.verMajor = verMajor;
    }

    public Integer getVerMinor() {
        return verMinor;
    }

    public void setVerMinor(Integer verMinor) {
        this.verMinor = verMinor;
    }

    public Integer getVerRevision() {
        return verRevision;
    }

    public void setVerRevision(Integer verRevision) {
        this.verRevision = verRevision;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AppVersionModel [appName=" + appName + ", appUrl=" + appUrl + ", verMajor=" + verMajor + ", verMinor="
                + verMinor + ", verRevision=" + verRevision + ", status=" + status + ", message=" + message + "]";
    }

}
