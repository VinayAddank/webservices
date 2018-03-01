package org.rta.core.model.specialnumber;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SpecialNumberFeeModel {

	//@NotNull(message = "dd number must not be null")
	private String ddNumber;
	//@NotNull(message = "transaction id number must not be null")
	private String transactionId;

	@NotNull(message = "amount must not be null")
	private Double amount;
	@NotNull(message = "bank name must not be null")
	private String bankName;
	@NotNull(message = "pr number must not be null")
	private String prNumber;
	@NotNull(message = "vehical rc id must not be null")
	private Long vehicalRcId;

	public String getDdNumber() {
		return ddNumber;
	}

	public void setDdNumber(String ddNumber) {
		this.ddNumber = ddNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}

	public Long getVehicalRcId() {
		return vehicalRcId;
	}

	public void setVehicalRcId(Long vehicalRcId) {
		this.vehicalRcId = vehicalRcId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
