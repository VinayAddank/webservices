package org.rta.core.entity.event;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "event_log")
public class EventLogEntity extends BaseEntity {

	@Id
	@Column(name = "event_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_id_seq")
	@SequenceGenerator(name = "event_id_seq", sequenceName = "event_id_seq", allocationSize = 1)
	private Long eventId;
	@Column(name = "vehicle_rc_id")
	private Long vehicleRcId;
	@Column(name = "pr_sms_notified")
	private Boolean prSmsNotified;
	
	@Column(name = "pr_sms_iteration")
	private Integer prSmsIteration;
	
	@Column(name = "pr_email_notified")
	private Boolean prEmailNotified;
	
	@Column(name = "pr_email_iteration")
	private Integer prEmailIteration;
	
	
	@Column(name = "tr_sms_notified")
	private Boolean trSmsNotified;
	
	@Column(name = "tr_sms_iteration")
	private Integer trSmsIteration;
	
	
	@Column(name = "tr_email_notified")
	private Boolean trEmailNotified;
	
	@Column(name = "tr_email_iteration")
	private Integer trEmailIteration;
	public EventLogEntity() {
		super();
		setPrEmailNotified(false);
		setPrSmsNotified(false);
		setTrEmailNotified(false);
		setTrSmsNotified(false);
		
	}

	@Column(name = "pr_attachement")
	private String prAttachement;
	@Column(name = "tr_attachment")
	private String trAttachement;
	
	
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(Long vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public Boolean getPrSmsNotified() {
		return prSmsNotified;
	}

	public void setPrSmsNotified(Boolean prSmsNotified) {
		this.prSmsNotified = prSmsNotified;
	}

	public Boolean getPrEmailNotified() {
		return prEmailNotified;
	}

	public void setPrEmailNotified(Boolean prEmailNotified) {
		this.prEmailNotified = prEmailNotified;
	}

	public Boolean getTrSmsNotified() {
		return trSmsNotified;
	}

	public void setTrSmsNotified(Boolean trSmsNotified) {
		this.trSmsNotified = trSmsNotified;
	}

	public Boolean getTrEmailNotified() {
		return trEmailNotified;
	}

	public void setTrEmailNotified(Boolean trEmailNotified) {
		this.trEmailNotified = trEmailNotified;
	}

	public String getPrAttachement() {
		return prAttachement;
	}

	public void setPrAttachement(String prAttachement) {
		this.prAttachement = prAttachement;
	}

	public String getTrAttachement() {
		return trAttachement;
	}

	public void setTrAttachement(String trAttachement) {
		this.trAttachement = trAttachement;
	}

	public Integer getPrSmsIteration() {
		return prSmsIteration;
	}

	public void setPrSmsIteration(Integer prSmsIteration) {
		this.prSmsIteration = prSmsIteration;
	}

	public Integer getPrEmailIteration() {
		return prEmailIteration;
	}

	public void setPrEmailIteration(Integer prEmailIteration) {
		this.prEmailIteration = prEmailIteration;
	}

	public Integer getTrSmsIteration() {
		return trSmsIteration;
	}

	public void setTrSmsIteration(Integer trSmsIteration) {
		this.trSmsIteration = trSmsIteration;
	}

	public Integer getTrEmailIteration() {
		return trEmailIteration;
	}

	public void setTrEmailIteration(Integer trEmailIteration) {
		this.trEmailIteration = trEmailIteration;
	}
	
	

}
