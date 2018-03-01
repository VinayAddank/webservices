package org.rta.core.exceptioncode;

public enum SameVehicleRcIdFoundExceptionCode {
	SAME_VEHICLE_RCID_FOUND("ERRPVR400","Same vehicle rc id entered");
	
	
	private String errMsg;
	private String errCode;
	private SameVehicleRcIdFoundExceptionCode(String errCode,String errMsg) {
		this.errMsg = errMsg;
		this.errCode = errCode;
	}
	
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	@Override
	public String toString(){
		return errCode+":" +errMsg;
	}
	
}
