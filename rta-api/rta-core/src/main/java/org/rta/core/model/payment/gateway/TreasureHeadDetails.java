package org.rta.core.model.payment.gateway;

public class TreasureHeadDetails {

	private String challanNo;
	private String deptCode;
	private String txtId;
	private String dateOfTxt;
	private String pos;
	private String bankRefNo;
	private int amount;
	private String remitterName;
	private String headOfAccount;
	private String ddoCode;

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getTxtId() {
		return txtId;
	}

	public void setTxtId(String txtId) {
		this.txtId = txtId;
	}

	public String getDateOfTxt() {
		return dateOfTxt;
	}

	public void setDateOfTxt(String dateOfTxt) {
		this.dateOfTxt = dateOfTxt;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getBankRefNo() {
		return bankRefNo;
	}

	public void setBankRefNo(String bankRefNo) {
		this.bankRefNo = bankRefNo;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getRemitterName() {
		return remitterName;
	}

	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}

	public String getHeadOfAccount() {
		return headOfAccount;
	}

	public void setHeadOfAccount(String headOfAccount) {
		this.headOfAccount = headOfAccount;
	}

	public String getDdoCode() {
		return ddoCode;
	}

	public void setDdoCode(String ddoCode) {
		this.ddoCode = ddoCode;
	}

}
