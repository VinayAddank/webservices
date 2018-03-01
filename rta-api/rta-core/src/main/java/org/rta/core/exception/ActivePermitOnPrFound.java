package org.rta.core.exception;

import org.rta.core.exceptioncode.ActivePermitOnPrFoundCode;
/**
 * 
 * @author Gautam.kumar
 *
 */
public class ActivePermitOnPrFound extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7758729784703045255L;
	private ActivePermitOnPrFoundCode code=null;
	private String errMsg=null;
	public ActivePermitOnPrFound() {
		super();
	}
	public ActivePermitOnPrFound(Throwable cause) {
		super(cause);
	}
	public ActivePermitOnPrFound(ActivePermitOnPrFoundCode code, String errMsg) {
		super(String.format(code.getErrMsg(), errMsg));
		this.code = code;
		this.errMsg = errMsg;
	}
	public ActivePermitOnPrFoundCode getCode() {
		return code;
	}
	public void setCode(ActivePermitOnPrFoundCode code) {
		this.code = code;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	@Override
	public String toString(){
		return code+":"+errMsg;
	}
	
}
