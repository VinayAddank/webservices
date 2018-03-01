package org.rta.core.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.office.RtaOfficeEntity;

/**
 * @author arun.verma
 *
 */

@Entity
@Table(name = "mandal")
public class MandalEntity extends BaseMasterEntity {


    private static final long serialVersionUID = -489118141572813846L;

    @Id
    @Column(name = "mandal_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mandal_id_seq")
    @SequenceGenerator(name = "mandal_id_seq", sequenceName = "mandal_id_seq", allocationSize = 1)
    private Long mandalId;

    @Column(name = "code", length = 50, unique = true)
    private Integer code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private DistrictEntity districtEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "rta_mandal_mapping", joinColumns = {@JoinColumn(name = "mandal_id")},
                inverseJoinColumns = {@JoinColumn(name = "rta_office_id")})
    private RtaOfficeEntity rtaOfficeEntity;

    public Long getMandalId() {
        return mandalId;
    }

    public void setMandalId(Long mandalId) {
        this.mandalId = mandalId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public DistrictEntity getDistrictEntity() {
        return districtEntity;
    }

    public void setDistrictEntity(DistrictEntity districtEntity) {
        this.districtEntity = districtEntity;
    }

    public RtaOfficeEntity getRtaOfficeEntity() {
        return rtaOfficeEntity;
    }

    public void setRtaOfficeEntity(RtaOfficeEntity rtaOfficeEntity) {
        this.rtaOfficeEntity = rtaOfficeEntity;
    }

}
