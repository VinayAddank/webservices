package org.rta.core.exceptioncode;

public enum PrSeriesMismatchExceptionCode {
	PR_SERIES_MISMATCH("2002","This pr series doesn't belongs to your RTA office.");
	private String errCode;
	private String errMsg;
	private PrSeriesMismatchExceptionCode(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	@Override
	public String toString(){
		return errCode +":" + errMsg;
	}
}
