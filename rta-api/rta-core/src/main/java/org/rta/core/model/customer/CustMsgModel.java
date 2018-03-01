/**
 * 
 */
package org.rta.core.model.customer;

import java.util.List;

/**
 * @author shivangi.gupta
 *
 */
public class CustMsgModel {

	String vehicleRcId;
	int ownershipType;
	String custName;
	private String smsMsg;
	private String mobileNo; // , Separated mobile Nos
	private String subject;
	private String to;
	private String cc;
	private String bcc;
	private String mailContent;
	private List<String> attachments;
	public String getVehicleRcId() {
		return vehicleRcId;
	}
	public void setVehicleRcId(String vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}
	public int getOwnershipType() {
		return ownershipType;
	}
	public void setOwnershipType(int ownershipType) {
		this.ownershipType = ownershipType;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getSmsMsg() {
		return smsMsg;
	}
	public void setSmsMsg(String smsMsg) {
		this.smsMsg = smsMsg;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getMailContent() {
		return mailContent;
	}
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
	public List<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
	
	
	
	
	

}
