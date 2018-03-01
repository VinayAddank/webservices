package org.rta.core.model;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class AadhaarDetailsRequestModel {

	@NotNull(message = "uid_num is missing")
	private String uid_num;

	@NotNull(message = "encryptedPid is missing")
	private byte[] encryptedPid;

	@NotNull(message = "encSessionKey is missing")
	private byte[] encSessionKey;

	@NotNull(message = "encSessionKey is missing")
	private byte[] encHmac;

	@NotNull(message = "timeStamp is missing")
	private String timeStamp;

	public String getUid_num() {
		return uid_num;
	}

	public void setUid_num(String uid_num) {
		this.uid_num = uid_num;
	}

	public byte[] getEncryptedPid() {
		return encryptedPid;
	}

	public void setEncryptedPid(byte[] encryptedPid) {
		this.encryptedPid = encryptedPid;
	}

	public byte[] getEncSessionKey() {
		return encSessionKey;
	}

	public void setEncSessionKey(byte[] encSessionKey) {
		this.encSessionKey = encSessionKey;
	}

	public byte[] getEncHmac() {
		return encHmac;
	}

	public void setEncHmac(byte[] encHmac) {
		this.encHmac = encHmac;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}
