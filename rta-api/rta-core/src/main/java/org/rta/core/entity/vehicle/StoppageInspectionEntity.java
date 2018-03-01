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
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.user.UserEntity;

@Entity
@Table(name = "stoppage_inspection")
public class StoppageInspectionEntity extends BaseEntity{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -3802626709407759102L;

	@Id
	@NotNull
	@Column(name = "stoppage_inspection_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stoppage_inspection_seq")
	@SequenceGenerator(name = "stoppage_inspection_seq", sequenceName = "stoppage_inspection_seq", allocationSize = 1)
	private Long stoppageInspectionId;

	@Column(name = "stoppage_no", length = 50)
	private String stoppageNo;
	
	@Column(name = "inspected_by")
	private String inspectedBy;
	
	@Column(name = "inspected_dt")
	private Long inspectedDt;
	
	@Column(name = "regn_no", length = 50)
	private String regnNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRcId;
		
	@Column(name = "quarter_from_dt")
	private Long quarterFromDt;
    
	@Column(name = "quarter_end_dt")
	private Long quarterEndDt;
	
	@Column(name = "owner")
	private String owner;
	
	@Column(name = "other")
	private String other;
	
	@Column(name = "garaged_place_reported")
	private Boolean garagedPlaceReported;
	
	@Column(name = "shifted_comment")
	private String shiftedComment;
	
	@Column(name = "mechanics_statements")
	private String mechanicsStatements;
	
	@Column(name = "repairs_comments")
	private String repairsComments;
	
	@Column(name = "is_repairs_condemned")
	private Boolean isRepairsCondemned;
	
	@Column(name = "repairs_condemned_comments")
	private String repairsCondemnedComments;
	
	@Column(name = "is_verified_gvr")
	private Boolean isVerifiedGVR;
	
	@Column(name = "oil_bunks")
	private String oilBunks;
	
	@Column(name = "all_check_posts")
	private String allCheckPosts;
	
	@Column(name = "over_all_comments")
	private String overAllComments;
	
	public Long getStoppageInspectionId() {
		return stoppageInspectionId;
	}

	public void setStoppageInspectionId(Long stoppageInspectionId) {
		this.stoppageInspectionId = stoppageInspectionId;
	}

	public String getStoppageNo() {
		return stoppageNo;
	}

	public void setStoppageNo(String stoppageNo) {
		this.stoppageNo = stoppageNo;
	}

	public String getInspectedBy() {
		return inspectedBy;
	}

	public void setInspectedBy(String inspectedBy) {
		this.inspectedBy = inspectedBy;
	}

	public Long getInspectedDt() {
		return inspectedDt;
	}

	public void setInspectedDt(Long inspectedDt) {
		this.inspectedDt = inspectedDt;
	}

	public String getRegnNo() {
		return regnNo;
	}

	public void setRegnNo(String regnNo) {
		this.regnNo = regnNo;
	}

	public VehicleRCEntity getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public Long getQuarterFromDt() {
		return quarterFromDt;
	}

	public void setQuarterFromDt(Long quarterFromDt) {
		this.quarterFromDt = quarterFromDt;
	}

	public Long getQuarterEndDt() {
		return quarterEndDt;
	}

	public void setQuarterEndDt(Long quarterEndDt) {
		this.quarterEndDt = quarterEndDt;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Boolean getGaragedPlaceReported() {
		return garagedPlaceReported;
	}

	public void setGaragedPlaceReported(Boolean garagedPlaceReported) {
		this.garagedPlaceReported = garagedPlaceReported;
	}

	public String getShiftedComment() {
		return shiftedComment;
	}

	public void setShiftedComment(String shiftedComment) {
		this.shiftedComment = shiftedComment;
	}

	public String getMechanicsStatements() {
		return mechanicsStatements;
	}

	public void setMechanicsStatements(String mechanicsStatements) {
		this.mechanicsStatements = mechanicsStatements;
	}

	public String getRepairsComments() {
		return repairsComments;
	}

	public void setRepairsComments(String repairsComments) {
		this.repairsComments = repairsComments;
	}

	public Boolean getIsRepairsCondemned() {
		return isRepairsCondemned;
	}

	public void setIsRepairsCondemned(Boolean isRepairsCondemned) {
		this.isRepairsCondemned = isRepairsCondemned;
	}

	public String getRepairsCondemnedComments() {
		return repairsCondemnedComments;
	}

	public void setRepairsCondemnedComments(String repairsCondemnedComments) {
		this.repairsCondemnedComments = repairsCondemnedComments;
	}

	public Boolean getIsVerifiedGVR() {
		return isVerifiedGVR;
	}

	public void setIsVerifiedGVR(Boolean isVerifiedGVR) {
		this.isVerifiedGVR = isVerifiedGVR;
	}

	public String getOilBunks() {
		return oilBunks;
	}

	public void setOilBunks(String oilBunks) {
		this.oilBunks = oilBunks;
	}

	public String getAllCheckPosts() {
		return allCheckPosts;
	}

	public void setAllCheckPosts(String allCheckPosts) {
		this.allCheckPosts = allCheckPosts;
	}

	public String getOverAllComments() {
		return overAllComments;
	}

	public void setOverAllComments(String overAllComments) {
		this.overAllComments = overAllComments;
	}
	
}
