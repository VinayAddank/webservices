package org.rta.core.exceptioncode;
import java.text.MessageFormat;


/**
 * 
 * @author Gautam.kumar
 *
 */
public enum InsurancePolicyValidationExceptionCode {
		INVALID_INSURANCE_POLICY("ERRIP400","Insurance policy not match with iib"),
		INSURANCE_POLICY_NOT_FOUND("ERRIP410","Insurance policy not found");
		private InsurancePolicyValidationExceptionCode(String errCode, String errMessage) {
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
