package org.rta.core.model.docs;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.rta.core.enums.AttachmentFrom;
import org.rta.core.enums.ServiceType;
import org.rta.core.model.master.DocTypesModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * this model is use for store and get url documents, and these are applicant documents.. uploaded
 * by dealer on other server
 */
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class AttachmentDetailsModel extends DocTypesModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long attachmentDlId;

    @NotNull(message = " File Name is Missing")
    private String fileName;

    @NotNull(message = " Attachment Title is Missing")
    private String attachmentTitle;

    @NotNull(message = " Source is Missing")
    private String source;

    private String status;

    // @NotNull(message = " vehicle RC Id is Missing")
    private String vehicleRcId;

    private String chassisNumber;
    private ServiceType serviecType;

    @NotNull(message = " attachment device is Missing(eg. Mobile, Desktop)")
    private AttachmentFrom attachmentFrom;

    private String userName;
    private String userType;

    public Long getAttachmentDlId() {
        return attachmentDlId;
    }

    public void setAttachmentDlId(Long attachmentDlId) {
        this.attachmentDlId = attachmentDlId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setVehicleRcId(String vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public String getVehicleRcId() {
        return vehicleRcId;
    }

    public String getAttachmentTitle() {
        return attachmentTitle;
    }

    public void setAttachmentTitle(String attachmentTitle) {
        this.attachmentTitle = attachmentTitle;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public AttachmentFrom getAttachmentFrom() {
        return attachmentFrom;
    }

    public void setAttachmentFrom(AttachmentFrom attachmentFrom) {
        this.attachmentFrom = attachmentFrom;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public ServiceType getServiecType() {
		return serviecType;
	}
    public void setServiecType(ServiceType serviecType) {
		this.serviecType = serviecType;
	}
    
    public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
    public String toString() {
        return "AttachmentDetailsModel [attachmentDlId=" + attachmentDlId + ", fileName=" + fileName + ", source="
                + source + ", status=" + status + ", vehicleRcId=" + vehicleRcId + ", chassisNumber=" + chassisNumber
                + ", attachmentFrom=" + attachmentFrom + ", super.toString()= " + super.toString() + "]";
    }



}
