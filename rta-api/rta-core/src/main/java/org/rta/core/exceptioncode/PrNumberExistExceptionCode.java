package org.rta.core.exceptioncode;

public enum PrNumberExistExceptionCode {
	
	PRNUMBER_ALREADY_EXIST("2001","RC already exists, please select another no"),
	RC_EXIST("2011","RC does not exists, please check"),
	DD_NUMBER_OR_TRANSACTION_ID("2021","Either DD number OR Transaction Id is required"),
	TRANSACTION("2031","payment is pending ");
	private PrNumberExistExceptionCode(String errCode, String errMessage) {
		this.errCode = errCode;
		this.errMessage = errMessage;
	}

	private String errCode;
	private String errMessage;
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	
	@Override
	public String toString(){
		return errCode +":" + errMessage;
	}
}
