package org.rta.core.entity.vehicle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.master.TaxTypeEntity;
import org.rta.core.enums.VehicleCategory;

@Entity
@Table(name = "vehicle_details_history")
public class VehicleDetailsHistoryEntity extends BaseEntity {

    private static final long serialVersionUID = -7825694563776977906L;

    @Id
    @Column(name = "vehicle_dtl_history_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_dtl_history_seq")
    @SequenceGenerator(name = "vehicle_dtl_history_seq", sequenceName = "vehicle_dtl_history_seq", allocationSize = 1)
    private Long vehicleDtlHistoryId;

    @Column(name = "vehicle_dtl_id")
    private Long vehicleDtlId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "vehicle_rc_id")
    private VehicleRCEntity vehicleRcId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tax_type_id")
    private TaxTypeEntity taxTypeId;

    @NotNull
    @Column(name = "engine_no")
    private String engineNo;

    @NotNull
    @Column(name = "chassis_no")
    private String chassisNo;


    @Column(name = "vehicle_number")
    private Integer vehicleNumber;

    @Column(name = "seating_capacity")
    private Integer seatingCapacity;

    @NotNull
    @Column(name = "vehicle_class")
    private String vehicleClass;

    @NotNull
    @Column(name = "maker_name")
    private String makerName;

    @Column(name = "maker_class")
    private String makerClass;

    @NotNull
    @Column(name = "fuel_used")
    private String fuelUsed;

    @NotNull
    @Column(name = "color")
    private String color;

    @NotNull
    @Column(name = "mfg_date")
    private String mfgDate;

    @NotNull
    @Column(name = "rlw")
    private Long rlw;

    @NotNull
    @Column(name = "ulw")
    private Long ulw;
    
    @NotNull
    @Column(name = "vehicle_sub_class")
    private String vehicleSubClass;

    // @NotNull
    @Column(name = "vehcle_category", columnDefinition = "int default 0")
    private VehicleCategory vehicleCategory;

    @Column(name = "body_type_update")
    private String bodyTypeUpdated;

    @Column(name = "height_update")
    private Long heightUpdated;

    @Column(name = "width_update")
    private Long widthUpdated;

    @Column(name = "length_update")
    private Long lengthUpdated;

    @Column(name = "completion_date")
    private Long completionDate;

    @Column(name = "alteration_category")
    private Integer alterationCategory;

    @Column(name = "wheelbase")
    private Integer wheelbase;

    @Column(name = "service_code")
    private String serviceCode;
    
    @Column(name = "gas_kit_number")
    private String gasKitNumber;
    
    @Column(name = "multiaxle", length=1)
    private String multiAxle;
    
    @Column(name = "old_veh_id")
    private Long oldVehId;

    public Long getVehicleDtlId() {
        return vehicleDtlId;
    }

    public void setVehicleDtlId(Long vehicleDtlId) {
        this.vehicleDtlId = vehicleDtlId;
    }

    public VehicleRCEntity getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public TaxTypeEntity getTaxTypeId() {
        return taxTypeId;
    }

    public void setTaxTypeId(TaxTypeEntity taxTypeId) {
        this.taxTypeId = taxTypeId;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getChassisNo() {
        return chassisNo;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
    }

    public Integer getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(Integer vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Integer getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(Integer seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public String getMakerClass() {
        return makerClass;
    }

    public void setMakerClass(String makerClass) {
        this.makerClass = makerClass;
    }

    public String getFuelUsed() {
        return fuelUsed;
    }

    public void setFuelUsed(String fuelUsed) {
        this.fuelUsed = fuelUsed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMfgDate() {
        return mfgDate;
    }

    public void setMfgDate(String mfgDate) {
        this.mfgDate = mfgDate;
    }

    public Long getRlw() {
        return rlw;
    }

    public void setRlw(Long rlw) {
        this.rlw = rlw;
    }

    public Long getUlw() {
        return ulw;
    }

    public void setUlw(Long ulw) {
        this.ulw = ulw;
    }

    public String getVehicleSubClass() {
        return vehicleSubClass;
    }

    public void setVehicleSubClass(String vehicleSubClass) {
        this.vehicleSubClass = vehicleSubClass;
    }


    public String getBodyTypeUpdated() {
        return bodyTypeUpdated;
    }

    public void setBodyTypeUpdated(String bodyTypeUpdated) {
        this.bodyTypeUpdated = bodyTypeUpdated;
    }

    public Long getHeightUpdated() {
        return heightUpdated;
    }

    public void setHeightUpdated(Long heightUpdated) {
        this.heightUpdated = heightUpdated;
    }

    public Long getWidthUpdated() {
        return widthUpdated;
    }

    public void setWidthUpdated(Long widthUpdated) {
        this.widthUpdated = widthUpdated;
    }

    public Long getLengthUpdated() {
        return lengthUpdated;
    }

    public void setLengthUpdated(Long lengthUpdated) {
        this.lengthUpdated = lengthUpdated;
    }

    public Long getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Long completionDate) {
        this.completionDate = completionDate;
    }

    public Integer getAlterationCategory() {
        return alterationCategory;
    }

    public void setAlterationCategory(Integer alterationCategory) {
        this.alterationCategory = alterationCategory;
    }

    public Integer getWheelbase() {
        return wheelbase;
    }

    public void setWheelbase(Integer wheelbase) {
        this.wheelbase = wheelbase;
    }

    public VehicleCategory getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(VehicleCategory vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }

    public Long getVehicleDtlHistoryId() {
        return vehicleDtlHistoryId;
    }

    public void setVehicleDtlHistoryId(Long vehicleDtlHistoryId) {
        this.vehicleDtlHistoryId = vehicleDtlHistoryId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getGasKitNumber() {
        return gasKitNumber;
    }

    public void setGasKitNumber(String gasKitNumber) {
        this.gasKitNumber = gasKitNumber;
    }

    public String getMultiAxle() {
        return multiAxle;
    }

    public void setMultiAxle(String multiAxle) {
        this.multiAxle = multiAxle;
    }

    public Long getOldVehId() {
        return oldVehId;
    }

    public void setOldVehId(Long oldVehId) {
        this.oldVehId = oldVehId;
    }

}
