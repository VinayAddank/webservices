package org.rta.registration.pan.model;

public class PanDetailsModel {
	
	private int status;
	private String panNumber;
	private String isValid;
	private String firstName;
	private String lastName;
	private String middleName;
	private String salutation;
	private String panUpdatedDateAtITDEnd;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
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
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public String getPanUpdatedDateAtITDEnd() {
		return panUpdatedDateAtITDEnd;
	}
	public void setPanUpdatedDateAtITDEnd(String panUpdatedDateAtITDEnd) {
		this.panUpdatedDateAtITDEnd = panUpdatedDateAtITDEnd;
	}
	
}
