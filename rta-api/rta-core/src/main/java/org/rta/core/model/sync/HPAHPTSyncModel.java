/**
 * 
 */
package org.rta.core.model.sync;

import java.util.List;

import org.rta.core.model.UserActionModel;

/**
 * @author arun.verma
 *
 */
public class HPAHPTSyncModel {

	private String appNumber;
	private String serviceCode;
	private List<UserActionModel> actionModelList;

	public String getAppNumber() {
		return appNumber;
	}

	public void setAppNumber(String appNumber) {
		this.appNumber = appNumber;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public List<UserActionModel> getActionModelList() {
		return actionModelList;
	}

	public void setActionModelList(List<UserActionModel> actionModelList) {
		this.actionModelList = actionModelList;
	}

}
