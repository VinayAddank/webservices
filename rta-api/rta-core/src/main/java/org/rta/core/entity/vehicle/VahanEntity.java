package org.rta.core.entity.vehicle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "vahan_master")
public class VahanEntity extends BaseEntity {

    private static final long serialVersionUID = -7864536924593685457L;
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
    private Long id;
    
    @Column(name = "chassis_no", unique = true)
    private String chassisNumber;
    
    @Column(name = "engine_no")
    private String engineNumber;
    
    @Column(name = "vehicle_class")
    private String vehicleClass;
    
    @Column(name = "makers_desc")
    private String makersDesc;
    
    @Column(name = "makers_class")
    private String makersClass;
    
    @Column(name = "mfg_date")
    private String mfgDate;
    
    @Column(name = "body_type_desc")
    private String bodyTypeDesc;
    
    @Column(name = "color")
    private String color;
    
    @Column(name = "seat_capacity")
    private Integer seatingCapacity;
    
    @Column(name = "stand_capacity")
    private Integer standCapacity;
    
    @Column(name = "cubic_capacity")
    private Double cubicCap;
    
    @Column(name = "engine_power")
    private Double enginePower;
    
    @Column(name = "fuel_used")
    private String fuelUsed;
    
    @Column(name = "unladen_weight")
    private Long ulw;
    
    @Column(name = "laden_weight")
    private Long rlw;

    @Column(name = "wheelbase")
    private Integer wheelbase;
    
    @Column(name = "height")
    private Long height;
    
    @Column(name = "width")
    private Long width;
    
    @Column(name = "length")
    private Long length;
    
    @Column(name = "no_cyl")
    private Integer noCyl;
    
    @Column(name = "pollution_norms_desc")
    private String pollutionNormsDesc;
    
    @Column(name = "front_axle_desc")
    private String frontAxleDesc;
    
    @Column(name = "front_axle_weight")
    private Double frontAxleWeight;
    
    @Column(name = "rear_axle_desc")
    private String rearAxleDesc;
    
    @Column(name = "rear_axle_weight")
    private Integer rearAxleWeight;
    
    @Column(name = "tandem_axel_desc")
    private String tandemAxelDescp;
    
    @Column(name = "tandem_axel_weight")
    private Integer tandemAxelWeight;
    
    @Column(name = "o1_axle_desc")
    private String o1AxleDesc;
    
    @Column(name = "o1_axle_weight")
    private Integer o1AxleWeight;
    
    @Column(name = "o2_axle_desc")
    private String o2AxleDesc;
    
    @Column(name = "o2_axle_weight")
    private Integer o2AxleWeight;
    
    @Column(name = "o3_axle_desc")
    private String o3AxleDesc;
    
    @Column(name = "o3_axle_weight")
    private Integer o3AxleWeight;
    
    @Column(name = "o4_axle_desc")
    private String o4AxleDesc;
    
    @Column(name = "o4_axle_weight")
    private Integer o4AxleWeight;
    
    @Column(name = "o5_axle_desc")
    private String o5AxleDesc;
    
    @Column(name = "o5_axle_weight")
    private Integer o5AxleWeight;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getChassisNumber() {
        return chassisNumber;
    }
    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }
    public String getEngineNumber() {
        return engineNumber;
    }
    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }
    public String getVehicleClass() {
        return vehicleClass;
    }
    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }
    public String getMakersDesc() {
        return makersDesc;
    }
    public void setMakersDesc(String makersDesc) {
        this.makersDesc = makersDesc;
    }
    public String getMakersClass() {
        return makersClass;
    }
    public void setMakersClass(String makersClass) {
        this.makersClass = makersClass;
    }
    public String getMfgDate() {
        return mfgDate;
    }
    public void setMfgDate(String mfgDate) {
        this.mfgDate = mfgDate;
    }
    public String getBodyTypeDesc() {
        return bodyTypeDesc;
    }
    public void setBodyTypeDesc(String bodyTypeDesc) {
        this.bodyTypeDesc = bodyTypeDesc;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public Integer getSeatingCapacity() {
        return seatingCapacity;
    }
    public void setSeatingCapacity(Integer seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }
    public Integer getStandCapacity() {
        return standCapacity;
    }
    public void setStandCapacity(Integer standCapacity) {
        this.standCapacity = standCapacity;
    }
    public Double getCubicCap() {
        return cubicCap;
    }
    public void setCubicCap(Double cubicCap) {
        this.cubicCap = cubicCap;
    }
    public Double getEnginePower() {
        return enginePower;
    }
    public void setEnginePower(Double enginePower) {
        this.enginePower = enginePower;
    }
    public String getFuelUsed() {
        return fuelUsed;
    }
    public void setFuelUsed(String fuelUsed) {
        this.fuelUsed = fuelUsed;
    }
    public Long getUlw() {
        return ulw;
    }
    public void setUlw(Long ulw) {
        this.ulw = ulw;
    }
    public Long getRlw() {
        return rlw;
    }
    public void setRlw(Long rlw) {
        this.rlw = rlw;
    }
    public Integer getWheelbase() {
        return wheelbase;
    }
    public void setWheelbase(Integer wheelbase) {
        this.wheelbase = wheelbase;
    }
    public Long getHeight() {
        return height;
    }
    public void setHeight(Long height) {
        this.height = height;
    }
    public Long getWidth() {
        return width;
    }
    public void setWidth(Long width) {
        this.width = width;
    }
    public Long getLength() {
        return length;
    }
    public void setLength(Long length) {
        this.length = length;
    }
    public Integer getNoCyl() {
        return noCyl;
    }
    public void setNoCyl(Integer noCyl) {
        this.noCyl = noCyl;
    }
    public String getPollutionNormsDesc() {
        return pollutionNormsDesc;
    }
    public void setPollutionNormsDesc(String pollutionNormsDesc) {
        this.pollutionNormsDesc = pollutionNormsDesc;
    }
    public String getFrontAxleDesc() {
        return frontAxleDesc;
    }
    public void setFrontAxleDesc(String frontAxleDesc) {
        this.frontAxleDesc = frontAxleDesc;
    }
    public Double getFrontAxleWeight() {
        return frontAxleWeight;
    }
    public void setFrontAxleWeight(Double frontAxleWeight) {
        this.frontAxleWeight = frontAxleWeight;
    }
    public String getRearAxleDesc() {
        return rearAxleDesc;
    }
    public void setRearAxleDesc(String rearAxleDesc) {
        this.rearAxleDesc = rearAxleDesc;
    }
    public Integer getRearAxleWeight() {
        return rearAxleWeight;
    }
    public void setRearAxleWeight(Integer rearAxleWeight) {
        this.rearAxleWeight = rearAxleWeight;
    }
    public String getTandemAxelDescp() {
        return tandemAxelDescp;
    }
    public void setTandemAxelDescp(String tandemAxelDescp) {
        this.tandemAxelDescp = tandemAxelDescp;
    }
    public Integer getTandemAxelWeight() {
        return tandemAxelWeight;
    }
    public void setTandemAxelWeight(Integer tandemAxelWeight) {
        this.tandemAxelWeight = tandemAxelWeight;
    }
    public String getO1AxleDesc() {
        return o1AxleDesc;
    }
    public void setO1AxleDesc(String o1AxleDesc) {
        this.o1AxleDesc = o1AxleDesc;
    }
    public Integer getO1AxleWeight() {
        return o1AxleWeight;
    }
    public void setO1AxleWeight(Integer o1AxleWeight) {
        this.o1AxleWeight = o1AxleWeight;
    }
    public String getO2AxleDesc() {
        return o2AxleDesc;
    }
    public void setO2AxleDesc(String o2AxleDesc) {
        this.o2AxleDesc = o2AxleDesc;
    }
    public Integer getO2AxleWeight() {
        return o2AxleWeight;
    }
    public void setO2AxleWeight(Integer o2AxleWeight) {
        this.o2AxleWeight = o2AxleWeight;
    }
    public String getO3AxleDesc() {
        return o3AxleDesc;
    }
    public void setO3AxleDesc(String o3AxleDesc) {
        this.o3AxleDesc = o3AxleDesc;
    }
    public Integer getO3AxleWeight() {
        return o3AxleWeight;
    }
    public void setO3AxleWeight(Integer o3AxleWeight) {
        this.o3AxleWeight = o3AxleWeight;
    }
    public String getO4AxleDesc() {
        return o4AxleDesc;
    }
    public void setO4AxleDesc(String o4AxleDesc) {
        this.o4AxleDesc = o4AxleDesc;
    }
    public Integer getO4AxleWeight() {
        return o4AxleWeight;
    }
    public void setO4AxleWeight(Integer o4AxleWeight) {
        this.o4AxleWeight = o4AxleWeight;
    }
    public String getO5AxleDesc() {
        return o5AxleDesc;
    }
    public void setO5AxleDesc(String o5AxleDesc) {
        this.o5AxleDesc = o5AxleDesc;
    }
    public Integer getO5AxleWeight() {
        return o5AxleWeight;
    }
    public void setO5AxleWeight(Integer o5AxleWeight) {
        this.o5AxleWeight = o5AxleWeight;
    }
    
    /*@Column(name = "owner_sr")
    private String ownerSr;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "present_address")
    private String presentAddress;
    
    @Column(name = "fit_upto")
    private String fitUpto;
    
    @Column(name = "tax_upto")
    private String taxUpto;
    
    @Column(name = "financer")
    private String financer;
    
    @Column(name = "insurance_company")
    private String insuranceCompany;
    
    @Column(name = "insurance_policy_number")
    private String insurancePolicyNumber;
    
    @Column(name = "insurance_upto")
    private String insuranceUpto;
    
    @Column(name = "cubic_capacity")
    private String cubicCapacity;
    
    @Column(name = "sleeper_capacity")
    private Integer sleeperCapacity;
    
    @Column(name = "wheel_base")
    private String wheelBase;
    
    @Column(name = "registered_at")
    private String registeredAt;
    
    @Column(name = "status_as_on")
    private String statusAsOn;*/

    
    
}
