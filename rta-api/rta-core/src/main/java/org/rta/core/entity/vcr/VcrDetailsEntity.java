package org.rta.core.entity.vcr;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "vcr_details")
public class VcrDetailsEntity {
    
	@Id
	@Column(name = "vcr_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vcr_details_seq")
	@SequenceGenerator(name = "vcr_details_seq", sequenceName = "vcr_details_seq", allocationSize = 1)
	private Long vcrId;

	@Column(name = "vcr_number", unique = true)
	private String vcrNum;
	@Column(name = "reg_number")
	private String regNo;
	@Column(name = "dl_number")
	private String dlNumber;
	@Column(name = "booked_by_mvi")
	private String bookedByMvi;
	@Column(name = "booked_date")
	private String bookedDate;
	@Column(name = "booked_time")
	private String bookedTime;
	@Column(name = "booked_place")
	private String placeBooked;
	@Column(name = "vcr_status")
	private String vcrStatus;
	@Column(name = "id_type")
	private String idType;
	@Column(name = "sezd_flag")
	private String vehsezdFlg;
	@Column(name = "pliedas")
	private String pliedas;
	
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "vcr_offense_id")
    private List<VcrOffenseHistoryEntity> vcrOffenseHistoryEntity;
	
	//Transaction Details
    @Column(name = "transaction_id")
    private Long transactionId;
    @Column(name = "bank_payment_status")
    private Integer bankPaymentStatus;
    @Column(name = "vcr_fine_amount")
    private Double vcrFineAmount;
    @Column(name = "vcr_tax_amount")
    private Double vcrTaxAmount;
    @Column(name = "exemption_flag")
    private Boolean exemptionFlag;
    
    @Column(name = "vehicle_rc_id")
    private String vehicleRcId;
    
    @Version
    @Column(name = "version", columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;

	public Long getVcrId() {
        return vcrId;
    }

    public void setVcrId(Long vcrId) {
        this.vcrId = vcrId;
    }

    public String getVcrNum() {
		return vcrNum;
	}

	public void setVcrNum(String vcrNum) {
		this.vcrNum = vcrNum;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getDlNumber() {
		return dlNumber;
	}

	public void setDlNumber(String dlNumber) {
		this.dlNumber = dlNumber;
	}

	public String getBookedByMvi() {
		return bookedByMvi;
	}

	public void setBookedByMvi(String bookedByMvi) {
		this.bookedByMvi = bookedByMvi;
	}

	public String getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(String bookedDate) {
		this.bookedDate = bookedDate;
	}

	public String getBookedTime() {
		return bookedTime;
	}

	public void setBookedTime(String bookedTime) {
		this.bookedTime = bookedTime;
	}

	public String getPlaceBooked() {
		return placeBooked;
	}

	public void setPlaceBooked(String placeBooked) {
		this.placeBooked = placeBooked;
	}

	public String getVcrStatus() {
		return vcrStatus;
	}

	public void setVcrStatus(String vcrStatus) {
		this.vcrStatus = vcrStatus;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getVehsezdFlg() {
		return vehsezdFlg;
	}

	public void setVehsezdFlg(String vehsezdFlg) {
		this.vehsezdFlg = vehsezdFlg;
	}

	public String getPliedas() {
		return pliedas;
	}

	public void setPliedas(String pliedas) {
		this.pliedas = pliedas;
	}
	
    public List<VcrOffenseHistoryEntity> getVcrOffenseHistoryEntity() {
        return vcrOffenseHistoryEntity;
    }

    public void setVcrOffenseHistoryEntity(List<VcrOffenseHistoryEntity> vcrOffenseHistoryEntity) {
        this.vcrOffenseHistoryEntity = vcrOffenseHistoryEntity;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getBankPaymentStatus() {
        return bankPaymentStatus;
    }

    public void setBankPaymentStatus(Integer bankPaymentStatus) {
        this.bankPaymentStatus = bankPaymentStatus;
    }

    public Double getVcrFineAmount() {
        return vcrFineAmount;
    }

    public void setVcrFineAmount(Double vcrFeeAmount) {
        this.vcrFineAmount = vcrFeeAmount;
    }

    public Double getVcrTaxAmount() {
        return vcrTaxAmount;
    }

    public void setVcrTaxAmount(Double vcrTaxAmount) {
        this.vcrTaxAmount = vcrTaxAmount;
    }

    public Boolean getExemptionFlag() {
        return exemptionFlag;
    }

    public void setExemptionFlag(Boolean exemptionFlag) {
        this.exemptionFlag = exemptionFlag;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

	public String getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(String vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}
    

}
