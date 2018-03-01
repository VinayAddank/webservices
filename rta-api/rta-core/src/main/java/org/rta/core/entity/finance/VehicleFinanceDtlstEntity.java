package org.rta.core.entity.finance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

@Entity
@Table(name = "vehicle_finance_details")
public class VehicleFinanceDtlstEntity extends BaseEntity {
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1443351655181162738L;

		@Id
	    @Column(name = "id")
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rta_financeDetails_seq")
	    @SequenceGenerator(name = "rta_financeDetails_seq", sequenceName = "rta_financeDetails_seq", allocationSize = 1)
	    private Long id;

	    @NotNull
	    @OneToOne(fetch = FetchType.LAZY)
	    @Cascade(CascadeType.SAVE_UPDATE)
	    @JoinColumn(name = "vehicle_rc_id")
	    private VehicleRCEntity vehicleRcId;

	    @Column(name = "finance_date_of_agreement")
	    private Long dateOfAgreement;

	    @NotNull
	    @Column(name = "financer_mode")
	    private Integer financerMode;

	    @Column(name = "financer_b_id")
	    private Long financerBId;
	    
	    @Column(name = "financer_id")
	    private Long financerId;
	    
	    @Column(name = "finance_amount")
	    private double financeAmount;

	    @Column(name = "finance_emi")
	    private float financeEmi;

	    @Column(name = "finance_tenure")
	    private Integer tenure;
	    
	    @Column(name = "finance_terminated")
	    private Boolean financeTerminated;
	    
	    @Column(name = "intrest_rate",precision=2) 
	    private Float intrestRate;
	    
	    @Column(name = "payment_mode")
	    private Integer paymentMode;
	    
	    @Column(name = "cheque_no")
	    private Long chequeNo;
	    
	    @Column(name = "end_dt")
	    private Long endDt;
	    
	    @Column(name = "rto_approved")
	    private Integer rtoApproved;
	    
		public Integer getRtoApproved() {
			return rtoApproved;
		}

		public void setRtoApproved(Integer rtoApproved) {
			this.rtoApproved = rtoApproved;
		}

		public Long getEndDt() {
			return endDt;
		}

		public void setEndDt(Long endDt) {
			this.endDt = endDt;
		}

		public Float getIntrestRate() {
			return intrestRate;
		}

		public void setIntrestRate(Float intrestRate) {
			this.intrestRate = intrestRate;
		}

		public Integer getPaymentMode() {
			return paymentMode;
		}

		public void setPaymentMode(Integer paymentMode) {
			this.paymentMode = paymentMode;
		}

		public Long getChequeNo() {
			return chequeNo;
		}

		public void setChequeNo(Long chequeNo) {
			this.chequeNo = chequeNo;
		}

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

		public Long getDateOfAgreement() {
			return dateOfAgreement;
		}

		public void setDateOfAgreement(Long dateOfAgreement) {
			this.dateOfAgreement = dateOfAgreement;
		}

		public Integer getFinancerMode() {
			return financerMode;
		}

		public void setFinancerMode(Integer financerMode) {
			this.financerMode = financerMode;
		}

		public Long getFinancerBId() {
			return financerBId;
		}

		public void setFinancerBId(Long financerBId) {
			this.financerBId = financerBId;
		}

		public Long getFinancerId() {
			return financerId;
		}

		public void setFinancerId(Long financerId) {
			this.financerId = financerId;
		}

		public double getFinanceAmount() {
			return financeAmount;
		}

		public void setFinanceAmount(double financeAmount) {
			this.financeAmount = financeAmount;
		}

		public float getFinanceEmi() {
			return financeEmi;
		}

		public void setFinanceEmi(float financeEmi) {
			this.financeEmi = financeEmi;
		}

		public Integer getTenure() {
			return tenure;
		}

		public void setTenure(Integer tenure) {
			this.tenure = tenure;
		}

		public Boolean getFinanceTerminated() {
			return financeTerminated;
		}

		public void setFinanceTerminated(Boolean financeTerminated) {
			this.financeTerminated = financeTerminated;
		}
	    
	    
	    
	    

	  


}
