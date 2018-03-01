package org.rta.core.exception;

import java.text.MessageFormat;

import org.rta.core.exceptioncode.PrNumberExistExceptionCode;

public class PrNumberExistException extends RuntimeException{
	

	/**
	 * @author Gautam.kumar
	 * @description validation pr number is special or not
	 */
	private static final long serialVersionUID = -1064039125165702993L;
	private String errMsg=null;
	private PrNumberExistExceptionCode code=null;
	public PrNumberExistException() {
		super();
		
	}
	
	public PrNumberExistException(PrNumberExistExceptionCode code,String errMsg) {
		super(MessageFormat.format(code.getErrMessage(), errMsg));
		this.errMsg = errMsg;
		this.code = code;
	}

	public PrNumberExistException(Throwable cause) {
		super(cause);
		
	}
	
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public PrNumberExistExceptionCode getCode() {
		return code;
	}

	public void setCode(PrNumberExistExceptionCode code) {
		this.code = code;
	}

	@Override
	public String toString(){
		return code +":"+errMsg;
	}

}
