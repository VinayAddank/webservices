package org.rta.core.entity.legalhier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "legal_hier_details")
public class LegalHierDetailsEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 5556150639177079302L;

    @Id
    @Column(name = "legal_hier_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "legal_hier_seq")
    @SequenceGenerator(name = "legal_hier_seq", sequenceName = "legal_hier_seq", allocationSize = 1)
    private Long legalHierId;

    @Column(name = "vehicle_rc_id")
    private Long vehicleRcId;

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "pr_number")
    private String prNumber;

    @Column(name = "legal_hier_aadhar_number")
    private String legalHierAadharNumber;

    @Column(name = "hier_consent")
    private Boolean isConsent;

    public Long getLegalHierId() {
        return legalHierId;
    }

    public void setLegalHierId(Long legalHierId) {
        this.legalHierId = legalHierId;
    }

    public Long getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(Long vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(String prNumber) {
        this.prNumber = prNumber;
    }

    public String getLegalHierAadharNumber() {
        return legalHierAadharNumber;
    }

    public void setLegalHierAadharNumber(String legalHierAadharNumber) {
        this.legalHierAadharNumber = legalHierAadharNumber;
    }

    public Boolean getIsConsent() {
        return isConsent;
    }

    public void setIsConsent(Boolean isConsent) {
        this.isConsent = isConsent;
    }

}
