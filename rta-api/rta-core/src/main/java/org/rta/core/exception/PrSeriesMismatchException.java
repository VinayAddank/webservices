package org.rta.core.exception;

import java.text.MessageFormat;

import org.rta.core.exceptioncode.PrSeriesMismatchExceptionCode;

public class PrSeriesMismatchException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5571775110088475346L;
	private PrSeriesMismatchExceptionCode code;
	private String errMsg;
	public PrSeriesMismatchException(PrSeriesMismatchExceptionCode code, String errMsg) {
		super(MessageFormat.format(code.getErrMsg(), errMsg));
		this.code = code;
		this.errMsg = errMsg;
	}
	public PrSeriesMismatchException() {
		super();
		
	}
	public PrSeriesMismatchException(Throwable arg0) {
		super(arg0);
		
	}
	public PrSeriesMismatchExceptionCode getCode() {
		return code;
	}
	public void setCode(PrSeriesMismatchExceptionCode code) {
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
