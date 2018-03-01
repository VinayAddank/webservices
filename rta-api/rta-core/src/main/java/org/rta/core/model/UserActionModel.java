package org.rta.core.model;

import org.rta.core.enums.UserType;

public class UserActionModel {

	private UserType userType;
	private String userAction;
	private String userId;
	
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public String getUserAction() {
		return userAction;
	}
	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "UserActionModel [userType=" + userType + ", userAction=" + userAction + ", userId=" + userId + "]";
	}
	
}
