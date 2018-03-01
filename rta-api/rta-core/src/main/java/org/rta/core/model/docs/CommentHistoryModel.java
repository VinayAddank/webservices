package org.rta.core.model.docs;

import org.rta.core.enums.Status;

public class CommentHistoryModel {

	private Status status;
	private String comment;

	// private Long modifiedOn;
	public Status getStatus() {
		return status;
	}

	public CommentHistoryModel() {
		super();
	}

	public CommentHistoryModel(Status status, String comment) {
		super();
		this.status = status;
		this.comment = comment;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
