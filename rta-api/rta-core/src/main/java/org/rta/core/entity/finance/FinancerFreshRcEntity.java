package org.rta.core.entity.finance;

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
import org.rta.core.entity.vehicle.VehicleRCEntity;

/**
 * @author Ashutosh.Sharma
 *
 */
@Entity
@Table(name="financer_fresh_rc")
public class FinancerFreshRcEntity extends BaseEntity{
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Id
	    @Column(name = "id")
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financer_fresh_rc_seq")
	    @SequenceGenerator(name = "financer_fresh_rc_seq", sequenceName = "financer_fresh_rc_seq", allocationSize = 1)
	    private Long id;

	    @NotNull
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "vehicle_rc_id")
	    private VehicleRCEntity vehicleRcId;
	    
	 /*   @NotNull
		@ManyToOne(fetch = FetchType.LAZY, optional = false)
		@JoinColumn(name = "financer_id", referencedColumnName = "user_id")
	    private UserEntity financerId;
	    */
	    @Column(name = "under_possession")
	    private Boolean vehicleUnderPossession;

	    @Column(name = "owner_consent")
	    private Boolean ownerConsent;
	    
	    @Column(name = "yard")
	    private Long yard;
	    
	    @Column(name = "amount_due")
	    private Long amountDue;
	    
	    @Column(name = "due_since")
	    private Long overDueSince;
	    
	    @Column(name ="current_step")
	    private Integer currentStep;
	    
	    @Column(name ="cco_status")
	    private Integer ccoStatus;
	    
	    @Column(name ="dtc_status")
	    private Integer dtcStatus;
	    
	    @Column(name = "frf_number")
	    private String frfNumber;

	    @Column(name = "defaulted_amount")
	    private Long defaultedAmount;

	    @Column(name = "no_of_emi")
	    private Long noOfEmi;
	    
	    @Column(name = "owner_comment")
	    private String ownerComment;
	    
	    @Column(name = "show_cause_issued_by")
	    private String showCauseIssuedBy;
	    
	    @Column(name = "show_cause_date")
	    private Long showCauseDate;
	    
	    @Column(name = "application_number")
	    private String applicationNumber;
	    
	    @Column(name = "owner_conscent_date")
	    private Long ownerConscentDate;
	    
	    @Column(name = "show_cause_doc")
	    private String showCauseDoc;
	    
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public VehicleRCEntity getVehicleRcId() {
			return vehicleRcId;
		}

		public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
			this.vehicleRcId = vehicleRcId;
		}

		public Boolean getVehicleUnderPossession() {
			return vehicleUnderPossession;
		}

		public void setVehicleUnderPossession(Boolean vehicleUnderPossession) {
			this.vehicleUnderPossession = vehicleUnderPossession;
		}

		public Boolean getOwnerConsent() {
			return ownerConsent;
		}

		public void setOwnerConsent(Boolean ownerConsent) {
			this.ownerConsent = ownerConsent;
		}

		public Long getYard() {
			return yard;
		}

		public void setYard(Long yard) {
			this.yard = yard;
		}

		public Long getAmountDue() {
			return amountDue;
		}

		public void setAmountDue(Long amountDue) {
			this.amountDue = amountDue;
		}

		public Long getOverDueSince() {
			return overDueSince;
		}

		public void setOverDueSince(Long overDueSince) {
			this.overDueSince = overDueSince;
		}

		public Integer getCurrentStep() {
			return currentStep;
		}

		public void setCurrentStep(Integer currentStep) {
			this.currentStep = currentStep;
		}

		public Integer getCcoStatus() {
			return ccoStatus;
		}

		public void setCcoStatus(Integer ccoStatus) {
			this.ccoStatus = ccoStatus;
		}

		public Integer getDtcStatus() {
			return dtcStatus;
		}

		public void setDtcStatus(Integer dtcStatus) {
			this.dtcStatus = dtcStatus;
		}

        public String getFrfNumber() {
            return frfNumber;
        }

        public void setFrfNumber(String frfNumber) {
            this.frfNumber = frfNumber;
        }

		public Long getDefaultedAmount() {
			return defaultedAmount;
		}

		public void setDefaultedAmount(Long defaultedAmount) {
			this.defaultedAmount = defaultedAmount;
		}

		public Long getNoOfEmi() {
			return noOfEmi;
		}

		public void setNoOfEmi(Long noOfEmi) {
			this.noOfEmi = noOfEmi;
		}

		public String getOwnerComment() {
			return ownerComment;
		}

		public void setOwnerComment(String ownerComment) {
			this.ownerComment = ownerComment;
		}

		public String getShowCauseIssuedBy() {
			return showCauseIssuedBy;
		}

		public void setShowCauseIssuedBy(String showCauseIssuedBy) {
			this.showCauseIssuedBy = showCauseIssuedBy;
		}

		public Long getShowCauseDate() {
			return showCauseDate;
		}

		public void setShowCauseDate(Long showCauseDate) {
			this.showCauseDate = showCauseDate;
		}

		public String getApplicationNumber() {
			return applicationNumber;
		}

		public void setApplicationNumber(String applicationNumber) {
			this.applicationNumber = applicationNumber;
		}

		public Long getOwnerConscentDate() {
			return ownerConscentDate;
		}

		public void setOwnerConscentDate(Long ownerConscentDate) {
			this.ownerConscentDate = ownerConscentDate;
		}

		public String getShowCauseDoc() {
			return showCauseDoc;
		}

		public void setShowCauseDoc(String showCauseDoc) {
			this.showCauseDoc = showCauseDoc;
		}
		
		/*public UserEntity getFinancerId() {
			return financerId;
		}

		public void setFinancerId(UserEntity financerId) {
			this.financerId = financerId;
		}*/
		
		
}
