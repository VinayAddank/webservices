package org.rta.core.model.application.rejection;

import javax.xml.bind.annotation.XmlRootElement;

import org.rta.core.enums.Status;
import org.rta.core.model.base.BaseModel;
import org.rta.core.model.master.DocTypesModel;
import org.rta.core.model.user.UserModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class RejectionHistoryModel extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long rejectionHistId;
    private Long vehicleRCId;
    private Long attachmentDetailsId;
    private String title;
    private String comment;
    private Status status;
    private DocTypesModel docType;
    private UserModel user;
    private String time;

    public Long getRejectionHistId() {
        return rejectionHistId;
    }

    public void setRejectionHistId(Long rejectionHistId) {
        this.rejectionHistId = rejectionHistId;
    }

    public Long getVehicleRCId() {
        return vehicleRCId;
    }

    public void setVehicleRCId(Long vehicleRCId) {
        this.vehicleRCId = vehicleRCId;
    }

    public Long getAttachmentDetailsId() {
        return attachmentDetailsId;
    }

    public void setAttachmentDetailsId(Long attachmentDetailsId) {
        this.attachmentDetailsId = attachmentDetailsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public DocTypesModel getDocType() {
        return docType;
    }

    public void setDocType(DocTypesModel docType) {
        this.docType = docType;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
}
