/**
 * 
 */
package org.rta.core.entity.appversion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.enums.Status;

/**
 * @author arun.verma
 *
 */
@Entity
@Table(name = "app_version")
public class AppVersionEntity extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = -8417788499339751618L;

    @Id
    @Column(name = "version_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_version_seq")
    @SequenceGenerator(name = "app_version_seq", sequenceName = "app_version_seq", allocationSize = 1)
    private Integer versionId;

    @Column(name = "app_name", nullable = false)
    private String appName;

    @Column(name = "app_url")
    private String appUrl;

    @Column(name = "ver_major", nullable = false)
    private Integer verMajor;

    @Column(name = "ver_minor", nullable = false)
    private Integer verMinor;

    @Column(name = "ver_revision", nullable = false)
    private Integer verRevision;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "message")
    private String message;

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
