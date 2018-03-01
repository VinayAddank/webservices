package org.rta.core.entity.master;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="route_details_master")
public class RouteDetailsEntity implements Serializable {

    private static final long serialVersionUID = 2452313729517132804L;

    @Id
    @Column(name = "stage_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_stage_seq")
    @SequenceGenerator(name = "route_stage_seq", sequenceName = "route_stage_seq", allocationSize = 1)
	private Long stageNo;

    @Column(name="distance", nullable=false)
    private Integer distance;
    
    @Column(name="office_cd", nullable=false, length=5)
    private String officeCode;
    
    @Column(name="route_serial", nullable=false)
    private Integer routeSerial;
    
    @Column(name="stage_name", nullable=false, length=50)
    private String stageName;
    
    @Column(name = "status")
    private Integer status;
    
    @Column(name="created_by", length=20)
    private String createdBy;

    @Column(name="created_on")
    private Timestamp createdOn;

    @Column(name="modified_by", length=20)
    private String modifiedBy;

    @Column(name="modified_on")
    private Timestamp modifiedOn;

    public Long getStageNo() {
        return stageNo;
    }

    public void setStageNo(Long stageNo) {
        this.stageNo = stageNo;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public Integer getRouteSerial() {
        return routeSerial;
    }

    public void setRouteSerial(Integer routeSerial) {
        this.routeSerial = routeSerial;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Timestamp modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
}