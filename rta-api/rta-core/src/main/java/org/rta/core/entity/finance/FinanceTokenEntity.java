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

@Entity
@Table(name="finance_token")
public class FinanceTokenEntity extends BaseEntity{
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = -4180131535847262120L;

		@Id
	    @Column(name = "id")
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finance_token_seq")
	    @SequenceGenerator(name = "finance_token_seq", sequenceName = "finance_token_seq", allocationSize = 1)
	    private Long financeTokenId;
	    
	    @NotNull
	    @ManyToOne(fetch = FetchType.EAGER, optional = false)
	    @JoinColumn(name = "vehicle_rc_id")
	    private VehicleRCEntity vehicleRc;

	    @NotNull
	    @Column(name = "requester_type")
	    private Integer requesterType;
	    
	    @Column(name = "token_id", unique = true)
	    private String tokenId;

	    @Column(name = "date_of_generation")
	    private Long dateOfTokenGen;
	    
	    @Column(name = "service_type")
	    private Integer serviceType;
	    
	    @Column(name = "app_status")   // to determine whether pending on dealer or financier end
	    private Integer appStatus;
	      
	    @Column(name = "quotation_price")
	    private Double quotationPrice;
	    
		public Integer getAppStatus() {
			return appStatus;
		}

		public void setAppStatus(Integer appStatus) {
			this.appStatus = appStatus;
		}

		public Integer getServiceType() {
			return serviceType;
		}

		public void setServiceType(Integer serviceType) {
			this.serviceType = serviceType;
		}

		public Long getFinanceTokenId() {
			return financeTokenId;
		}

		public void setFinanceTokenId(Long financeTokenId) {
			this.financeTokenId = financeTokenId;
		}

		public VehicleRCEntity getVehicleRc() {
			return vehicleRc;
		}

		public void setVehicleRc(VehicleRCEntity vehicleRc) {
			this.vehicleRc = vehicleRc;
		}

		

		public String getTokenId() {
			return tokenId;
		}

		public void setTokenId(String tokenId) {
			this.tokenId = tokenId;
		}

		public Long getDateOfTokenGen() {
			return dateOfTokenGen;
		}

		public void setDateOfTokenGen(Long dateOfTokenGen) {
			this.dateOfTokenGen = dateOfTokenGen;
		}

		public Integer getRequesterType() {
			return requesterType;
		}

		public void setRequesterType(Integer requesterType) {
			this.requesterType = requesterType;
		}

		public Double getQuotationPrice() {
			return quotationPrice;
		}

		public void setQuotationPrice(Double quotationPrice) {
			this.quotationPrice = quotationPrice;
		}
		
		
}
