/**
 * 
 */
package org.rta.core.entity.permit;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.rta.core.entity.base.SerializableEntity;

/**
 * @author arun.verma
 *
 */
@Entity
@Table(name = "permit_header")
public class PermitHeaderEntity extends SerializableEntity implements Cloneable{

    private static final long serialVersionUID = 8099785649524318301L;

    @Id
    @Column(name = "permit_header_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permit_header_seq")
    @SequenceGenerator(name = "permit_header_seq", sequenceName = "permit_header_seq", allocationSize = 1)
    private Long permitHeaderId;
    
    @Column(name="appl_id", length=80)
    private String applId;

    @Column(name="appl_origination", length=80)
    private String applOrigination;

    @Column(name="appl_type", length=20)
    private String applType;

    @Column(name="approval_rmks", length=255)
    private String approvalRmks;

    @Column(name="approved_by", length=80)
    private String approvedBy;

    @Column(name="approved_dt")
    private Timestamp approvedDt;

    @Column(name="created_by", length=80)
    private String createdBy;

    @Column(name="created_on")
    private Timestamp createdOn;

    @Column(name="days_per_trip")
    private Integer daysPerTrip;

    @Column(name="entity_cd", length=80)
    private String entityCd;

    @Column(name="forward_journey_destination", length=600)
    private String forwardJourneyDestination;

    @Column(name="forward_journey_enroute", length=600)
    private String forwardJourneyEnroute;

    @Column(name="forward_journey_starting_station", length=600)
    private String forwardJourneyStartingStation;

    @Column(name="hire_flag", length=1)
    private String hireFlag;

    @Column(name="holder_aadhar_no", length=80)
    private String holderAadharNo;

    @Column(name="holder_mobile_no", length=12)
    private String holderMobileNo;

    @Column(name="holder_nm", length=80)
    private String holderNm;

    @Column(name="home_state_authority", length=80)
    private String homeStateAuthority;

    @Column(name="is_temp_permit")
    private Boolean isTempPermit;

    @Column(name="issue_date")
    private Timestamp issueDate;

    @Column(name="modified_by", length=80)
    private String modifiedBy;

    @Column(name="modified_on")
    private Timestamp modifiedOn;

    @Column(name="module_cd", length=80)
    private String moduleCd;

    @Column(name="no_of_trips")
    private Integer noOfTrips;

    @Column(name="os_recom_lttr_id", length=80)
    private String osRecomLttrId;

    @Column(name="other_state_nm", length=80)
    private String otherStateNm;

    @Column(name="passanger_list_encl_id", length=80)
    private String passangerListEnclId;

    @Column(name="permit_issued_to", length=80)
    private String permitIssuedTo;

    @Column(name="permit_no", length=80)
    private String permitNo;

    @Column(name="permit_sequence_id")
    private Integer permitSequenceId;

    @Column(name="permit_sub_type")
    private String permitSubType;

    @Column(name="permit_type")
    private String permitType;

    @Column(name="ppd_kms", precision=7, scale=2)
    private BigDecimal ppdKms;

    @Column(name="ppd_plw", precision=8, scale=2)
    private BigDecimal ppdPlw;

    @Column(length=200)
    private String purpose;

    @Column(name="return_journey_destination", length=600)
    private String returnJourneyDestination;

    @Column(name="return_journey_enroute", length=600)
    private String returnJourneyEnroute;

    @Column(name="return_journey_starting_station", length=600)
    private String returnJourneyStartingStation;

    @Column(name="rta_office_id")
    private Long rtaOfficeId;

    @Column(name="state_entry_dt")
    private Timestamp stateEntryDt;

    @Column(name="state_national_permit", length=1)
    private String stateNationalPermit;

    @Column(name="status")
    private Integer status;

    @Column(name="stu_rep_name", length=80)
    private String stuRepName;

    @Column(name="support_ticket_remarks", length=600)
    private String supportTicketRemarks;

    @Temporal(TemporalType.DATE)
    @Column(name="valid_from_date")
    private Date validFromDate;

    @Temporal(TemporalType.DATE)
    @Column(name="valid_to_date")
    private Date validToDate;

    @Column(name="vehicle_class", length=10)
    private String vehicleClass;

    @Column(name="vehicle_regd_no", length=80)
    private String vehicleRegdNo;

    public Long getPermitHeaderId() {
        return permitHeaderId;
    }

    public void setPermitHeaderId(Long permitHeaderId) {
        this.permitHeaderId = permitHeaderId;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getApplOrigination() {
        return applOrigination;
    }

    public void setApplOrigination(String applOrigination) {
        this.applOrigination = applOrigination;
    }

    public String getApplType() {
        return applType;
    }

    public void setApplType(String applType) {
        this.applType = applType;
    }

    public String getApprovalRmks() {
        return approvalRmks;
    }

    public void setApprovalRmks(String approvalRmks) {
        this.approvalRmks = approvalRmks;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Timestamp getApprovedDt() {
        return approvedDt;
    }

    public void setApprovedDt(Timestamp approvedDt) {
        this.approvedDt = approvedDt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getDaysPerTrip() {
        return daysPerTrip;
    }

    public void setDaysPerTrip(Integer daysPerTrip) {
        this.daysPerTrip = daysPerTrip;
    }

    public String getEntityCd() {
        return entityCd;
    }

    public void setEntityCd(String entityCd) {
        this.entityCd = entityCd;
    }

    public String getForwardJourneyDestination() {
        return forwardJourneyDestination;
    }

    public void setForwardJourneyDestination(String forwardJourneyDestination) {
        this.forwardJourneyDestination = forwardJourneyDestination;
    }

    public String getForwardJourneyEnroute() {
        return forwardJourneyEnroute;
    }

    public void setForwardJourneyEnroute(String forwardJourneyEnroute) {
        this.forwardJourneyEnroute = forwardJourneyEnroute;
    }

    public String getForwardJourneyStartingStation() {
        return forwardJourneyStartingStation;
    }

    public void setForwardJourneyStartingStation(String forwardJourneyStartingStation) {
        this.forwardJourneyStartingStation = forwardJourneyStartingStation;
    }

    public String getHireFlag() {
        return hireFlag;
    }

    public void setHireFlag(String hireFlag) {
        this.hireFlag = hireFlag;
    }

    public String getHolderAadharNo() {
        return holderAadharNo;
    }

    public void setHolderAadharNo(String holderAadharNo) {
        this.holderAadharNo = holderAadharNo;
    }

    public String getHolderMobileNo() {
        return holderMobileNo;
    }

    public void setHolderMobileNo(String holderMobileNo) {
        this.holderMobileNo = holderMobileNo;
    }

    public String getHolderNm() {
        return holderNm;
    }

    public void setHolderNm(String holderNm) {
        this.holderNm = holderNm;
    }

    public String getHomeStateAuthority() {
        return homeStateAuthority;
    }

    public void setHomeStateAuthority(String homeStateAuthority) {
        this.homeStateAuthority = homeStateAuthority;
    }

    public Boolean getIsTempPermit() {
        return isTempPermit;
    }

    public void setIsTempPermit(Boolean isTempPermit) {
        this.isTempPermit = isTempPermit;
    }

    public Timestamp getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Timestamp modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getModuleCd() {
        return moduleCd;
    }

    public void setModuleCd(String moduleCd) {
        this.moduleCd = moduleCd;
    }

    public Integer getNoOfTrips() {
        return noOfTrips;
    }

    public void setNoOfTrips(Integer noOfTrips) {
        this.noOfTrips = noOfTrips;
    }

    public String getOsRecomLttrId() {
        return osRecomLttrId;
    }

    public void setOsRecomLttrId(String osRecomLttrId) {
        this.osRecomLttrId = osRecomLttrId;
    }

    public String getOtherStateNm() {
        return otherStateNm;
    }

    public void setOtherStateNm(String otherStateNm) {
        this.otherStateNm = otherStateNm;
    }

    public String getPassangerListEnclId() {
        return passangerListEnclId;
    }

    public void setPassangerListEnclId(String passangerListEnclId) {
        this.passangerListEnclId = passangerListEnclId;
    }

    public String getPermitIssuedTo() {
        return permitIssuedTo;
    }

    public void setPermitIssuedTo(String permitIssuedTo) {
        this.permitIssuedTo = permitIssuedTo;
    }

    public String getPermitNo() {
        return permitNo;
    }

    public void setPermitNo(String permitNo) {
        this.permitNo = permitNo;
    }

    public Integer getPermitSequenceId() {
        return permitSequenceId;
    }

    public void setPermitSequenceId(Integer permitSequenceId) {
        this.permitSequenceId = permitSequenceId;
    }

    public String getPermitSubType() {
        return permitSubType;
    }

    public void setPermitSubType(String permitSubType) {
        this.permitSubType = permitSubType;
    }

    public String getPermitType() {
        return permitType;
    }

    public void setPermitType(String permitType) {
        this.permitType = permitType;
    }

    public BigDecimal getPpdKms() {
        return ppdKms;
    }

    public void setPpdKms(BigDecimal ppdKms) {
        this.ppdKms = ppdKms;
    }

    public BigDecimal getPpdPlw() {
        return ppdPlw;
    }

    public void setPpdPlw(BigDecimal ppdPlw) {
        this.ppdPlw = ppdPlw;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getReturnJourneyDestination() {
        return returnJourneyDestination;
    }

    public void setReturnJourneyDestination(String returnJourneyDestination) {
        this.returnJourneyDestination = returnJourneyDestination;
    }

    public String getReturnJourneyEnroute() {
        return returnJourneyEnroute;
    }

    public void setReturnJourneyEnroute(String returnJourneyEnroute) {
        this.returnJourneyEnroute = returnJourneyEnroute;
    }

    public String getReturnJourneyStartingStation() {
        return returnJourneyStartingStation;
    }

    public void setReturnJourneyStartingStation(String returnJourneyStartingStation) {
        this.returnJourneyStartingStation = returnJourneyStartingStation;
    }

    public Long getRtaOfficeId() {
        return rtaOfficeId;
    }

    public void setRtaOfficeId(Long rtaOfficeId) {
        this.rtaOfficeId = rtaOfficeId;
    }

    public Timestamp getStateEntryDt() {
        return stateEntryDt;
    }

    public void setStateEntryDt(Timestamp stateEntryDt) {
        this.stateEntryDt = stateEntryDt;
    }

    public String getStateNationalPermit() {
        return stateNationalPermit;
    }

    public void setStateNationalPermit(String stateNationalPermit) {
        this.stateNationalPermit = stateNationalPermit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStuRepName() {
        return stuRepName;
    }

    public void setStuRepName(String stuRepName) {
        this.stuRepName = stuRepName;
    }

    public String getSupportTicketRemarks() {
        return supportTicketRemarks;
    }

    public void setSupportTicketRemarks(String supportTicketRemarks) {
        this.supportTicketRemarks = supportTicketRemarks;
    }

    public Date getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(Date validFromDate) {
        this.validFromDate = validFromDate;
    }

    public Date getValidToDate() {
        return validToDate;
    }

    public void setValidToDate(Date validToDate) {
        this.validToDate = validToDate;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public String getVehicleRegdNo() {
        return vehicleRegdNo;
    }

    public void setVehicleRegdNo(String vehicleRegdNo) {
        this.vehicleRegdNo = vehicleRegdNo;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
