package org.rta.core.exception;

import org.rta.core.exceptioncode.SameVehicleRcIdFoundExceptionCode;
/**
 * 
 * @author Gautam.kumar
 *
 */
public class SameVehicleRcIdFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8601871351437667875L;
	private SameVehicleRcIdFoundExceptionCode errCode=null;
	private String errMsg=null;
	public SameVehicleRcIdFoundException() {
		super();
		
	}
	
	public SameVehicleRcIdFoundException(Throwable cause) {
		super(cause);
		
	}

	public SameVehicleRcIdFoundException(SameVehicleRcIdFoundExceptionCode errCode, String errMsg) {
		super(String.format(errCode.getErrMsg(), errMsg));
		this.errCode=errCode;
		this.errMsg=errMsg;
		
	}
	public SameVehicleRcIdFoundExceptionCode getErrCode() {
		return errCode;
	}
	public void setErrCode(SameVehicleRcIdFoundExceptionCode errCode) {
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
		return errCode+":"+errMsg;
	}
	
}
