/**
 * 
 */
package org.rta.core.model.activiti;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author admin
 *
 */
@JsonInclude(Include.NON_NULL)
public class ActivitiResponseModel<T> {

	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";
	private String status;
	private String statusCode;
	private String message;
	private T data;
	private List<RtaTaskInfo> activeTasks;
	private Map<String, String> errrors;

	public ActivitiResponseModel() {
	}

	public ActivitiResponseModel(String status) {
		this.status = status;
	}

	public ActivitiResponseModel(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public ActivitiResponseModel(String status, T data) {
		this.status = status;
		this.data = data;
	}

	public ActivitiResponseModel(String status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<RtaTaskInfo> getActiveTasks() {
		return activeTasks;
	}

	public void setActiveTasks(List<RtaTaskInfo> activeTasks) {
		this.activeTasks = activeTasks;
	}

	public Map<String, String> getErrrors() {
		return errrors;
	}

	public void setErrrors(Map<String, String> errrors) {
		this.errrors = errrors;
	}

	public static String getSuccess() {
		return SUCCESS;
	}

	public static String getFailure() {
		return FAILURE;
	}

}
