package org.rta.core.entity.office;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.master.DistrictEntity;

/**
 *	@Author sohan.maurya created on Dec 19, 2016.
 */

@Entity
@Table(name = "vehicle_noc_address")
public class NocAddressEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "nco_address_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "noc_address_seq")
    @SequenceGenerator(name = "noc_address_seq", sequenceName = "noc_address_seq", allocationSize = 1)
    private Long addressId;


    @NotNull
    @Column(name = "noc_address_code", unique = true)
    private String nocAddressCode;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rta_office_id")
    private RtaOfficeEntity rtaOfficeId;

    @NotNull
    @Column(name = "noc_address")
    private String nocAddress;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private DistrictEntity districtId;

    @Column(name = "status")
    private Boolean status;

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public RtaOfficeEntity getRtaOfficeId() {
        return rtaOfficeId;
    }

    public void setRtaOfficeId(RtaOfficeEntity rtaOfficeId) {
        this.rtaOfficeId = rtaOfficeId;
    }

    public String getNocAddress() {
        return nocAddress;
    }

    public void setNocAddress(String nocAddress) {
        this.nocAddress = nocAddress;
    }

    public DistrictEntity getDistrictId() {
        return districtId;
    }

    public void setDistrictId(DistrictEntity districtId) {
        this.districtId = districtId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getNocAddressCode() {
        return nocAddressCode;
    }

    public void setNocAddressCode(String nocAddressCode) {
        this.nocAddressCode = nocAddressCode;
    }
}
