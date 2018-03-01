package org.rta.core.model.license;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class ForgetLicenseModel {
	private String licenceType;
	@NotNull
	private String aadharNumber;
	@NotNull
	private String rtaOffice;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String fatherName;
	@NotNull
	private Date dob;
	
	public String getLicenceType() {
		return licenceType;
	}
	public void setLicenceType(String licenceType) {
		this.licenceType = licenceType;
	}
	public String getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getRtaOffice() {
		return rtaOffice;
	}
	public void setRtaOffice(String rtaOffice) {
		this.rtaOffice = rtaOffice;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
}
