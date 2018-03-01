package org.rta.core.entity.vehicle;

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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.master.RegistrationCategoryEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.entity.user.UserEntity;

@Entity
@Table(name = "vehicle_rc_change_history")
public class VehicleRCChangeHistoryEntity extends BaseEntity {

    private static final long serialVersionUID = -7864536924593685457L;

    @Id
    @Column(name = "vehicle_rc_change_history_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_change_history_seq")
    @SequenceGenerator(name = "vehicle_change_history_seq", sequenceName = "vehicle_change_history_seq", allocationSize = 1)
    private Long vehicleRcChangeHistoryId;
    
    @Column(name = "vehicle_rc_id")
    private Long vehicleRcId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "rta_office_id")
    private RtaOfficeEntity rtaOfficeId;

    @Column(name = "aadhar_no")
    private String aadharNo;


    @Column(name = "chassis_no")
    private String chassisNumber;

    @NotNull
    @Column(name = "current_step")
	private Integer currentStep;

    @Column(name = "process_status")
	private Integer processStatus;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_category", referencedColumnName = "registration_category_id")
    private RegistrationCategoryEntity regCategory;

    @NotNull
    @Column(name = "tr_status")
	private Integer trStatus;

    @Column(name = "pr_status")
    private Integer prStatus;

    @Column(name = "tr_issue_time")
    private Long trIssueTime;

    @Column(name = "pr_issue_time")
    private Long prIssueTime;

    @Column(name = "tr_number")
    private String trNumber;

    @Column(name = "pr_number")
    private String prNumber;

    @NotNull
    @Column(name = "ownership_type")
    private Integer ownershipType;

    @Column(name = "iteration")
    private Integer iteration;
    
    @Column(name = "is_migrated")
    private boolean isMigrated;
    
    @Column(name = "is_printed", columnDefinition = "boolean default false")
    private boolean isPrinted;

    @Column(name = "rc_print_date")
    private Long rcPrintDate;
    
    @Column(name = "is_aadhar_verified", columnDefinition = "boolean default false")
    private boolean isAadharVerified;
    
    @Column(name = "doc_upload_consent", columnDefinition = "boolean default false")
    private boolean docUploadConsent;
    
    @Column(name = "pr_type")
    private Integer prType;

    @Column(name = "service_code")
    private String serviceCode;
    
    @Column(name = "pr_expire_date")
    private Long prExpireDate;
    
    @Column(name = "application_number")
    private String applicationNumber;
    
    @Column(name = "migration_source")
    private String migrationSource;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "cco_user_id", nullable = true)
    private UserEntity ccoUserId;
    
    @Column(name = "cco_action_status")
    private Integer ccoActionStatus;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "mvi_user_id", nullable = true)
    private UserEntity mviUserId;
    
    @Column(name = "mvi_action_status")
    private Integer mviActionStatus;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ao_user_id", nullable = true)
    private UserEntity aoUserId;
    
    @Column(name = "ao_action_status")
    private Integer aoActionStatus;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "rto_user_id", nullable = true)
    private UserEntity rtoUserId;
    
    @Column(name = "rto_action_status")
    private Integer rtoActionStatus;

    @Column(name="old_applicant_id", length=80)
    private String oldApplicantId;
    
    @Column(name="vehicle_dtl_id")
    private Long vehicleDetailId;

    @Column(name="cust_dtl_id")
    private Long customerDetailId;

    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;

    public VehicleRCChangeHistoryEntity() {}

    public VehicleRCChangeHistoryEntity(Long vehicleRcId, String chassisNumber) {
        this.vehicleRcId = vehicleRcId;
        this.chassisNumber = chassisNumber;
    }

    public Long getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(Long vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public RtaOfficeEntity getRtaOfficeId() {
        return rtaOfficeId;
    }

    public void setRtaOfficeId(RtaOfficeEntity rtaOfficeId) {
        this.rtaOfficeId = rtaOfficeId;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

	public Integer getCurrentStep() {
        return currentStep;
    }

	public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
    }

	public Integer getProcessStatus() {
        return processStatus;
    }

	public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public RegistrationCategoryEntity getRegCategory() {
        return regCategory;
    }

    public void setRegCategory(RegistrationCategoryEntity regCategory) {
        this.regCategory = regCategory;
    }

	public Integer getTrStatus() {
        return trStatus;
    }

	public void setTrStatus(Integer trStatus) {
        this.trStatus = trStatus;
    }

    public Integer getPrStatus() {
        return prStatus;
    }

    public void setPrStatus(Integer prStatus) {
        this.prStatus = prStatus;
    }

    public Long getTrIssueTime() {
        return trIssueTime;
    }

    public void setTrIssueTime(Long trIssueTime) {
        this.trIssueTime = trIssueTime;
    }

    public Long getPrIssueTime() {
        return prIssueTime;
    }

    public void setPrIssueTime(Long prIssueTime) {
        this.prIssueTime = prIssueTime;
    }

    public String getTrNumber() {
        return trNumber;
    }

    public void setTrNumber(String trNumber) {
        this.trNumber = trNumber;
    }

    public String getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(String prNumber) {
        this.prNumber = prNumber;
    }

    public Integer getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(Integer ownershipType) {
        this.ownershipType = ownershipType;
    }

    public Integer getIteration() {
        return iteration;
    }

    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }

    public boolean isMigrated() {
        return isMigrated;
    }

    public void setMigrated(boolean isMigrated) {
        this.isMigrated = isMigrated;
    }

    public boolean getIsPrinted() {
        return isPrinted;
    }

    public void setIsPrinted(boolean isPrinted) {
        this.isPrinted = isPrinted;
    }

    public Long getRcPrintDate() {
        return rcPrintDate;
    }

    public void setRcPrintDate(Long rcPrintDate) {
        this.rcPrintDate = rcPrintDate;
    }

    public boolean isAadharVerified() {
        return isAadharVerified;
    }

    public void setAadharVerified(boolean isAadharVerified) {
        this.isAadharVerified = isAadharVerified;
    }

	public boolean isDocUploadConsent() {
		return docUploadConsent;
	}

	public void setDocUploadConsent(boolean docUploadConsent) {
		this.docUploadConsent = docUploadConsent;
	}

    public Integer getPrType() {
        return prType;
    }

    public void setPrType(Integer prType) {
        this.prType = prType;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Long getVehicleRcChangeHistoryId() {
        return vehicleRcChangeHistoryId;
    }

    public void setVehicleRcChangeHistoryId(Long vehicleRcChangeHistoryId) {
        this.vehicleRcChangeHistoryId = vehicleRcChangeHistoryId;
    }

    public void setPrinted(boolean isPrinted) {
        this.isPrinted = isPrinted;
    }

	public Long getPrExpireDate() {
		return prExpireDate;
	}

	public void setPrExpireDate(Long prExpireDate) {
		this.prExpireDate = prExpireDate;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getMigrationSource() {
		return migrationSource;
	}

	public void setMigrationSource(String migrationSource) {
		this.migrationSource = migrationSource;
	}

    public UserEntity getCcoUserId() {
        return ccoUserId;
    }

    public void setCcoUserId(UserEntity ccoUserId) {
        this.ccoUserId = ccoUserId;
    }

    public Integer getCcoActionStatus() {
        return ccoActionStatus;
    }

    public void setCcoActionStatus(Integer ccoActionStatus) {
        this.ccoActionStatus = ccoActionStatus;
    }

    public UserEntity getMviUserId() {
        return mviUserId;
    }

    public void setMviUserId(UserEntity mviUserId) {
        this.mviUserId = mviUserId;
    }

    public Integer getMviActionStatus() {
        return mviActionStatus;
    }

    public void setMviActionStatus(Integer mviActionStatus) {
        this.mviActionStatus = mviActionStatus;
    }

    public UserEntity getAoUserId() {
        return aoUserId;
    }

    public void setAoUserId(UserEntity aoUserId) {
        this.aoUserId = aoUserId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getAoActionStatus() {
        return aoActionStatus;
    }

    public void setAoActionStatus(Integer aoActionStatus) {
        this.aoActionStatus = aoActionStatus;
    }

    public UserEntity getRtoUserId() {
        return rtoUserId;
    }

    public void setRtoUserId(UserEntity rtoUserId) {
        this.rtoUserId = rtoUserId;
    }

    public Integer getRtoActionStatus() {
        return rtoActionStatus;
    }

    public void setRtoActionStatus(Integer rtoActionStatus) {
        this.rtoActionStatus = rtoActionStatus;
    }

    public String getOldApplicantId() {
        return oldApplicantId;
    }

    public void setOldApplicantId(String oldApplicantId) {
        this.oldApplicantId = oldApplicantId;
    }

    public Long getVehicleDetailId() {
        return vehicleDetailId;
    }

    public void setVehicleDetailId(Long vehicleDetailId) {
        this.vehicleDetailId = vehicleDetailId;
    }

    public Long getCustomerDetailId() {
        return customerDetailId;
    }

    public void setCustomerDetailId(Long customerDetailId) {
        this.customerDetailId = customerDetailId;
    }
}
