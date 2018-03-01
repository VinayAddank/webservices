package org.rta.core.exception;

import java.text.MessageFormat;

import org.rta.core.exceptioncode.InvalidPrNumberExceptionCode;

public class InvalidPrNumberException extends RuntimeException{
	
	/**
	 * @author Gautam.kumar
	 * @description validation pr number is special or not
	 */
	private static final long serialVersionUID = -1064039125165702993L;
	private String errMsg=null;
	private InvalidPrNumberExceptionCode code=null;
	public InvalidPrNumberException() {
		super();
		
	}
	
	public InvalidPrNumberException(InvalidPrNumberExceptionCode code,String errMsg) {
		super(MessageFormat.format(code.getErrMessage(), errMsg));
		this.errMsg = errMsg;
		this.code = code;
	}

	public InvalidPrNumberException(Throwable cause) {
		super(cause);
		
	}
	
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public InvalidPrNumberExceptionCode getCode() {
		return code;
	}

	public void setCode(InvalidPrNumberExceptionCode code) {
		this.code = code;
	}

	@Override
	public String toString(){
		return code +":"+errMsg;
	}
	
}
