package org.rta.core.entity.payment.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.enums.Status;

@Entity
@Table(name = "vehicle_weight_master")
public class VehicleWgtMasterEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 7867304654018185163L;

    @Id
	@Column(name = "vehicle_weight_master_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_weight_master_id_seq")
	@SequenceGenerator(name = "vehicle_weight_master_id_seq", sequenceName = "vehicle_weight_master_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "veh_wgt_catg_code")
	private String vehWgtCatgCode;

	@Column(name = "veh_wgt_catg_desc")
	private String vehWgtCatgDesc;

	@Column(name = "status")
	private Status status;

	@Column(name = "frm_weight")
	private Long frmWeight;

	@Column(name = "to_weight")
	private Long toWeight;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVehWgtCatgCode() {
		return vehWgtCatgCode;
	}

	public void setVehWgtCatgCode(String vehWgtCatgCode) {
		this.vehWgtCatgCode = vehWgtCatgCode;
	}

	public String getVehWgtCatgDesc() {
		return vehWgtCatgDesc;
	}

	public void setVehWgtCatgDesc(String vehWgtCatgDesc) {
		this.vehWgtCatgDesc = vehWgtCatgDesc;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getFrmWeight() {
		return frmWeight;
	}

	public void setFrmWeight(Long frmWeight) {
		this.frmWeight = frmWeight;
	}

	public Long getToWeight() {
		return toWeight;
	}

	public void setToWeight(Long toWeight) {
		this.toWeight = toWeight;
	}

}
