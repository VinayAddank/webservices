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
@Table(name="route_master")
public class RouteMasterEntity implements Serializable {

    private static final long serialVersionUID = 2453143217803395653L;

    @Id
    @Column(name = "route_serial")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_master_seq")
    @SequenceGenerator(name = "route_master_seq", sequenceName = "route_master_seq", allocationSize = 1)
    private Long routeSerial;

    @Column(name="service_type", length=3)
    private String serviceType;

    @Column(name="area_type", length=3)
    private String areaType;    
        
    @Column(name="area_name", length=150)
    private String areaName;
    
    @Column(name="start_point", length=150)
    private String startPoint;
    
    @Column(name="end_point", length=30)
    private String endPoint;    
    
    @Column(name="distance")
    private Integer distance;

    @Column(name="dist_num")
    private Integer distNumber;    
    
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
   
    @Column(name="office_cd", nullable=false, length=5)
    private String officeCode;
    
    @Column(name="oper_type", length=3)
	private String operType;

	@Column(name="state_cd", nullable=false, length=2)
	private String stateCode;

    public Long getRouteSerial() {
        return routeSerial;
    }

    public void setRouteSerial(Long routeSerial) {
        this.routeSerial = routeSerial;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getDistNumber() {
        return distNumber;
    }

    public void setDistNumber(Integer distNumber) {
        this.distNumber = distNumber;
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

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
}