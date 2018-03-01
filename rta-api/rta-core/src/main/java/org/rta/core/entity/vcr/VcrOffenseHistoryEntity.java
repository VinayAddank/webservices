package org.rta.core.entity.vcr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "vcr_offense_logs")
public class VcrOffenseHistoryEntity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vcr_offense_seq")
	@SequenceGenerator(name = "vcr_offense_seq", sequenceName = "vcr_offense_seq", allocationSize = 1)
	private Long id;
	@Column(name = "vcr_numvber")
	private String vcrNumber;
	@Column(name = "offense")
	private String offense;
	@Column(name = "fineAmount")
	private String fineAmount;
	
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getVcrNumber() {
		return vcrNumber;
	}
	public void setVcrNumber(String vcrNumber) {
		this.vcrNumber = vcrNumber;
	}
	public String getOffense() {
		return offense;
	}
	public void setOffense(String offense) {
		this.offense = offense;
	}
	public String getFineAmount() {
		return fineAmount;
	}
	public void setFineAmount(String fineAmount) {
		this.fineAmount = fineAmount;
	}
	
	
}
