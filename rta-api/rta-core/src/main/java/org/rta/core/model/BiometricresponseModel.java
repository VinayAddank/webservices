package org.rta.core.model;

import java.io.Serializable;


public class BiometricresponseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String auth_date;
	private String auth_reason;
	private String auth_status;
	private String auth_transaction_code;


	public String getAuth_date() {
		return auth_date;
	}

	public void setAuth_date(String auth_date) {
		this.auth_date = auth_date;
	}


	public String getAuth_reason() {
		return auth_reason;
	}

	public void setAuth_reason(String auth_reason) {
		this.auth_reason = auth_reason;
	}


	public String getAuth_status() {
		return auth_status;
	}

	public void setAuth_status(String auth_status) {
		this.auth_status = auth_status;
	}


	public String getAuth_transaction_code() {
		return auth_transaction_code;
	}

	public void setAuth_transaction_code(String auth_transaction_code) {
		this.auth_transaction_code = auth_transaction_code;
	}

}
