package org.rta.core.exception;

import java.text.MessageFormat;

import org.rta.core.exceptioncode.InsurancePolicyValidationExceptionCode;

/**
 * 
 * @author Gautam.kumar
 *
 */
public class InsurancePolicyValidationException extends RuntimeException{
		/**
	 * 
	 */
	private static final long serialVersionUID = 3974173164726184192L;
		private String errMsg=null;
		private InsurancePolicyValidationExceptionCode code=null;
		public InsurancePolicyValidationException() {
			super();
			
		}
		public InsurancePolicyValidationException(Throwable cause) {
			super(cause);
			
		}
		public InsurancePolicyValidationException(InsurancePolicyValidationExceptionCode code,String errMsg ) {
			super(MessageFormat.format(code.getErrMessage(), errMsg));
			this.errMsg = errMsg;
			this.code = code;
		}
		public String getErrMsg() {
			return errMsg;
		}
		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}
		public InsurancePolicyValidationExceptionCode getCode() {
			return code;
		}
		public void setCode(InsurancePolicyValidationExceptionCode code) {
			this.code = code;
		}
		
		@Override
		public String toString(){
			return code +":"+errMsg;
		}
		
}

