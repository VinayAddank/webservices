package org.rta.core.entity.docs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.master.DocTypesEntity;
import org.rta.core.entity.user.UserEntity;

/**
 * this entity is use for store url and get documents, and these are applicant documents.. uploaded
 * by citizen on other server
 */

@Entity
@Table(name = "user_attachment_details")
public class UserAttachmentDetailsEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "attachment_detail_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_attachment_details_gen")
    @SequenceGenerator(name = "user_attachment_details_gen", sequenceName = "user_attachment_details_sequence",
            allocationSize = 1)
    private Long attachmentDlId;

    @NotNull
    @Column(name = "file_name", length = 200)
    private String fileName;

    @NotNull
    @Column(name = "source")
    private String source;

    @NotNull
    @Column(name = "status", length = 2)
    private Integer status;

    @NotNull
    @Column(name = "attachment_title", length = 200)
    private String attachmentTitle;

    @NotNull
    @Column(name = "attachment_from")
    private Integer attachmentFrom;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_master_id")
    private DocTypesEntity docTypes;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @Column(name = "user_role", length = 3)
    private Integer userRole;

    public UserAttachmentDetailsEntity() {}

    public UserAttachmentDetailsEntity(Long attachmentDlId) {
        this.attachmentDlId = attachmentDlId;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DocTypesEntity getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(DocTypesEntity docTypes) {
        this.docTypes = docTypes;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public String getAttachmentTitle() {
        return attachmentTitle;
    }

    public void setAttachmentTitle(String attachmentTitle) {
        this.attachmentTitle = attachmentTitle;
    }

    public Integer getAttachmentFrom() {
        return attachmentFrom;
    }

    public void setAttachmentFrom(Integer attachmentFrom) {
        this.attachmentFrom = attachmentFrom;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

}
