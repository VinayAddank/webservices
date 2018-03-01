package org.rta.core.entity.certificate;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.rta.core.entity.base.BaseCommonEntity;
import org.rta.core.entity.office.RtaOfficeEntity;

@Entity
@Table(name = "fitness_cert_details_history")
public class FitnessCertificateHistoryEntity extends BaseCommonEntity {

	private static final long serialVersionUID = -9109959867119055454L;

	@Id
    @Column(name = "fitness_history_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fitness_history_seq")
    @SequenceGenerator(name = "fitness_history_seq", sequenceName = "fitness_history_seq", allocationSize = 1)
    private Long fitnessHistoryId;
	
    @Column(name = "fitness_id")
    private Long fitnessId;

	@Column(name = "vehicle_rc_id")
    private Long vehicleRcId;
	
	@Column(name = "vehicle_regd_no")
    private String vehicleRegdNo;
	
	@Column(name = "appl_id")
    private String applId;
	
	@Column(name = "applicant_id")
    private String applicantId;
    
	@Column(name = "fc_no")
    private String fcNo;
	
	@Column(name = "fc_type")
    private String fcType;
	
	@Column(name = "appl_type")
    private String applType;
	
	@Column(name = "validity_flag")
    private String validityFlag;
	
	@Column(name = "fexm_reason")
    private String fexmReason;
	
	@Column(name = "fexm")
    private String fexm;
	
	@Column(name = "status_code")
	private Integer statusCode;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "issue_date")
    private Date issueDate;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "approvedby_mvi")
    private String approvedbyMVI;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rta_office_code")
	private RtaOfficeEntity rtaOfficeId;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insp_office_code")
	private RtaOfficeEntity inspRtaOfficeId;
    
    @Column(name = "ghat_road_flag")
    private Boolean ghatRoadFlag;
    
    @Column(name = "remarks")
    private String remarks;
    
    @Column(name = "support_ticket_details")
    private String supportTicketDetails;
    
    @Column(name = "cfrr_id")
    private Long cfrrId;
    
    @Column(name = "cfrr_count")
    private Integer cfrrCount;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cfrr_next_insp_dt")
    private Date cfrrNextInspDt;
    
    @Column(name = "appl_origination")
    private String applOrigination;
    
    @Column(name = "module_code")
    private String moduleCode;

    @Column(name = "engine_no")
    private String engineNo;
    
    @Column(name = "chasis_no")
    private String chassisNo;
    
	
    public Long getFitnessHistoryId() {
		return fitnessHistoryId;
	}

	public void setFitnessHistoryId(Long fitnessHistoryId) {
		this.fitnessHistoryId = fitnessHistoryId;
	}

	public Long getFitnessId() {
		return fitnessId;
	}

	public void setFitnessId(Long fitnessId) {
		this.fitnessId = fitnessId;
	}

	public String getVehicleRegdNo() {
		return vehicleRegdNo;
	}

	public void setVehicleRegdNo(String vehicleRegdNo) {
		this.vehicleRegdNo = vehicleRegdNo;
	}

	public String getApplId() {
		return applId;
	}

	public void setApplId(String applId) {
		this.applId = applId;
	}

	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	public String getFcNo() {
		return fcNo;
	}

	public void setFcNo(String fcNo) {
		this.fcNo = fcNo;
	}

	public String getFcType() {
		return fcType;
	}

	public void setFcType(String fcType) {
		this.fcType = fcType;
	}

	public String getApplType() {
		return applType;
	}

	public void setApplType(String applType) {
		this.applType = applType;
	}

	public String getValidityFlag() {
		return validityFlag;
	}

	public void setValidityFlag(String validityFlag) {
		this.validityFlag = validityFlag;
	}

	public String getFexmReason() {
		return fexmReason;
	}

	public void setFexmReason(String fexmReason) {
		this.fexmReason = fexmReason;
	}

	public String getFexm() {
		return fexm;
	}

	public void setFexm(String fexm) {
		this.fexm = fexm;
	}

	public String getApprovedbyMVI() {
		return approvedbyMVI;
	}

	public void setApprovedbyMVI(String approvedbyMVI) {
		this.approvedbyMVI = approvedbyMVI;
	}

	public Boolean getGhatRoadFlag() {
		return ghatRoadFlag;
	}

	public void setGhatRoadFlag(Boolean ghatRoadFlag) {
		this.ghatRoadFlag = ghatRoadFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSupportTicketDetails() {
		return supportTicketDetails;
	}

	public void setSupportTicketDetails(String supportTicketDetails) {
		this.supportTicketDetails = supportTicketDetails;
	}

	public Date getCfrrNextInspDt() {
		return cfrrNextInspDt;
	}

	public void setCfrrNextInspDt(Date cfrrNextInspDt) {
		this.cfrrNextInspDt = cfrrNextInspDt;
	}

	public String getApplOrigination() {
		return applOrigination;
	}

	public void setApplOrigination(String applOrigination) {
		this.applOrigination = applOrigination;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public Long getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(Long vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public RtaOfficeEntity getRtaOfficeId() {
		return rtaOfficeId;
	}

	public void setRtaOfficeId(RtaOfficeEntity rtaOfficeId) {
		this.rtaOfficeId = rtaOfficeId;
	}

	public RtaOfficeEntity getInspRtaOfficeId() {
		return inspRtaOfficeId;
	}

	public void setInspRtaOfficeId(RtaOfficeEntity inspRtaOfficeId) {
		this.inspRtaOfficeId = inspRtaOfficeId;
	}

	public Long getCfrrId() {
		return cfrrId;
	}

	public void setCfrrId(Long cfrrId) {
		this.cfrrId = cfrrId;
	}

	public Integer getCfrrCount() {
		return cfrrCount;
	}

	public void setCfrrCount(Integer cfrrCount) {
		this.cfrrCount = cfrrCount;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getChassisNo() {
		return chassisNo;
	}

	public void setChassisNo(String chassisNo) {
		this.chassisNo = chassisNo;
	}
    
}
