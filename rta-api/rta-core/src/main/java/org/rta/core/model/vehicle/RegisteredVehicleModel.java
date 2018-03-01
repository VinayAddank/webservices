package org.rta.core.model.vehicle;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class RegisteredVehicleModel extends VehicleBaseModel {

    private static final long serialVersionUID = 7876439524612690497L;
    private String registrationDate;
    private String ownerSr;
    private String firstName;
    private String presentAddress;
    private String fitUpto;
    private String taxUpto;
    private String financer;
    private String insuranceCompany;
    private String insurancePolicyNumber;
    private String insuranceUpto;
    private Integer sleeperCapacity;
    private String wheelBase;
    private String registeredAt;
    private String statusAsOn;

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getOwnerSr() {
        return ownerSr;
    }

    public void setOwnerSr(String ownerSr) {
        this.ownerSr = ownerSr;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getFitUpto() {
        return fitUpto;
    }

    public void setFitUpto(String fitUpto) {
        this.fitUpto = fitUpto;
    }

    public String getTaxUpto() {
        return taxUpto;
    }

    public void setTaxUpto(String taxUpto) {
        this.taxUpto = taxUpto;
    }

    public String getFinancer() {
        return financer;
    }

    public void setFinancer(String financer) {
        this.financer = financer;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public String getInsurancePolicyNumber() {
        return insurancePolicyNumber;
    }

    public void setInsurancePolicyNumber(String insurancePolicyNumber) {
        this.insurancePolicyNumber = insurancePolicyNumber;
    }

    public String getInsuranceUpto() {
        return insuranceUpto;
    }

    public void setInsuranceUpto(String insuranceUpto) {
        this.insuranceUpto = insuranceUpto;
    }

    public Integer getSleeperCapacity() {
        return sleeperCapacity;
    }

    public void setSleeperCapacity(Integer sleeperCapacity) {
        this.sleeperCapacity = sleeperCapacity;
    }

    public String getWheelBase() {
        return wheelBase;
    }

    public void setWheelBase(String wheelBase) {
        this.wheelBase = wheelBase;
    }

    public String getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(String registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getStatusAsOn() {
        return statusAsOn;
    }

    public void setStatusAsOn(String statusAsOn) {
        this.statusAsOn = statusAsOn;
    }

    @JsonIgnore
    public static RegisteredVehicleModel createBasicVehicleModel(RegisteredVehicleModel basicVehicleModel) {
        return createBasicVehicleModel(basicVehicleModel.getChassisNumber(), basicVehicleModel.getEngineNumber(),
                basicVehicleModel.getFuelUsed(), basicVehicleModel.getColor(), basicVehicleModel.getMakersClass(),
                basicVehicleModel.getMakersName(), basicVehicleModel.getManufacturingMonthYear(),
                basicVehicleModel.getRlw(), basicVehicleModel.getUlw(), basicVehicleModel.getVehicleClass(),
                basicVehicleModel.getSeatingCapacity());
    }

    @JsonIgnore
    public static RegisteredVehicleModel createBasicVehicleModel(String chassisNumber, String engineNumber,
            String fuelUsed, String color, String makersClass, String makersName, String manuMonthYr, Long rlw,
            Long ulw, String vehicleClass, Integer seatingCapacity) {
        RegisteredVehicleModel vm = new RegisteredVehicleModel();
        vm.setColor(color);
        vm.setMakersClass(makersClass);
        vm.setMakersName(makersName);
        vm.setManufacturingMonthYear(manuMonthYr);
        vm.setRlw(rlw);
        vm.setUlw(ulw);
        vm.setVehicleClass(vehicleClass);
        vm.setSeatingCapacity(seatingCapacity);
        return vm;
    }

}
