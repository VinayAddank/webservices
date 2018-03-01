package org.rta.core.exceptioncode;

public enum InvalidPrNumberExceptionCode {
	INVALID_PR_NUMBER_EXCEPTION("2000","Pr not special number");
		private InvalidPrNumberExceptionCode(String errCode, String errMessage) {
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
