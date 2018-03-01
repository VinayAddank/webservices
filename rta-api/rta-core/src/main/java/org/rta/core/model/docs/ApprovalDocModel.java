package org.rta.core.model.docs;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.rta.core.enums.Status;
import org.rta.core.model.master.DocTypesModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/**
 * used for checklist
 * @author shivangi.gupta
 *
 */

@JsonInclude(Include.NON_NULL)
public class ApprovalDocModel {

	private DocTypesModel docType;
	private String comments;
	private HashMap<Long , CommentHistoryModel> commentHistory=new HashMap();
	@NotNull(message = "status required")
	private Status status;
	@NotNull(message = "attachmentDlId required")
	private Long attachmentDlId;
	private String attachmentUrl;
	private Map<String, String> docRelatedInfo;

	public DocTypesModel getDocType() {
		return docType;
	}

	public void setDocType(DocTypesModel docType) {
		this.docType = docType;
	}

	public void setDocType(Integer id, String name, Boolean isMandatory, String roleName) {
		this.docType=new DocTypesModel();
		this.docType.setId(id);
		this.docType.setName(name);
		this.docType.setRoleName(roleName);
		this.docType.setIsMandatory(isMandatory);
}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Map<String, String> getDocRelatedInfo() {
		return docRelatedInfo;
	}

	public void setDocRelatedInfo(Map<String, String> docRelatedInfo) {
		this.docRelatedInfo = docRelatedInfo;
	}

	public Long getAttachmentDlId() {
		return attachmentDlId;
	}

	public void setAttachmentDlId(Long attachmentDlId) {
		this.attachmentDlId = attachmentDlId;
	}

	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}

	public HashMap<Long, CommentHistoryModel> getCommentHistory() {
		return commentHistory;
	}

	public void setCommentHistory(HashMap<Long, CommentHistoryModel> commentHistory) {
		this.commentHistory = commentHistory;
	}

	
}
