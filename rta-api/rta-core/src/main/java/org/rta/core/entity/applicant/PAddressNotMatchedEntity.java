/**
 * 
 */
package org.rta.core.entity.applicant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.rta.core.entity.base.BaseEntity;

/**
 * @author arun.verma
 *
 */
@Entity
@Table(name = "p_address_not_matched")
public class PAddressNotMatchedEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -2854118562762014574L;

    @Id
    @Column(name = "p_address_not_matched_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "p_address_not_matched_id_seq")
    @SequenceGenerator(name = "p_address_not_matched_id_seq", sequenceName = "p_address_not_matched_id_seq",
            allocationSize = 1)
    private Long PAddressNotMatchedId;

    @Column(name = "aadhar_no")
    private String aadharNo;

    @Column(name = "aadhar_state")
    private String aadharState;

    @Column(name = "aadhar_district")
    private String aadharDistrict;

    @Column(name = "aadhar_mandal")
    private String aadharMandal;

    @Column(name = "customer_state")
    private String customerState;

    @Column(name = "customer_district")
    private String customerDistrict;

    @Column(name = "customer_mandal")
    private String customerMandal;

    @Column(name = "vehicle_rc_id")
    private Long vehicleRcId;

    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;
    
    public Long getPAddressNotMatchedId() {
        return PAddressNotMatchedId;
    }

    public void setPAddressNotMatchedId(Long pAddressNotMatchedId) {
        PAddressNotMatchedId = pAddressNotMatchedId;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getAadharState() {
        return aadharState;
    }

    public void setAadharState(String aadharState) {
        this.aadharState = aadharState;
    }

    public String getAadharDistrict() {
        return aadharDistrict;
    }

    public void setAadharDistrict(String aadharDistrict) {
        this.aadharDistrict = aadharDistrict;
    }

    public String getAadharMandal() {
        return aadharMandal;
    }

    public void setAadharMandal(String aadharMandal) {
        this.aadharMandal = aadharMandal;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getCustomerDistrict() {
        return customerDistrict;
    }

    public void setCustomerDistrict(String customerDistrict) {
        this.customerDistrict = customerDistrict;
    }

    public String getCustomerMandal() {
        return customerMandal;
    }

    public void setCustomerMandal(String customerMandal) {
        this.customerMandal = customerMandal;
    }

    public Long getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(Long vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }
    
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "PAddressNotMatchedEntity [PAddressNotMatchedId=" + PAddressNotMatchedId + ", aadharNo=" + aadharNo
                + ", aadharState=" + aadharState + ", aadharDistrict=" + aadharDistrict + ", aadharMandal="
                + aadharMandal + ", customerState=" + customerState + ", customerDistrict=" + customerDistrict
                + ", customerMandal=" + customerMandal + ", vehicleRcId=" + vehicleRcId + "]";
    }

}
