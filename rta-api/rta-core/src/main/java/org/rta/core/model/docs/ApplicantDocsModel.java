package org.rta.core.model.docs;

import java.util.List;
/**
 * To contain All documents for a vehicleRcId
 * for document approval(checklist)
 *
 * @author shivangi.gupta
 *
 */
public class ApplicantDocsModel {

	private Long vehicleRcId;
	private List<ApprovalDocModel> appDocs;

	public Long getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(Long vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public List<ApprovalDocModel> getAppDocs() {
		return appDocs;
	}

	public void setAppDocs(List<ApprovalDocModel> appDocs) {
		this.appDocs = appDocs;
	}

}
