package org.rta.core.entity.office;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.enums.SerialNumberType;

@Entity
@Table(name = "rta_offices_serial_no")
public class RTAOfficeSerialNumberEntity extends BaseEntity {

    private static final long serialVersionUID = 816985640649563016L;
    
    @Id
    @Column(name = "rta_offices_serial_no_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rta_offices_serial_no_seq")
    @SequenceGenerator(name = "rta_offices_serial_no_seq", sequenceName = "rta_offices_serial_no_seq", allocationSize = 1)
    private Long id;
    
    @Column(name = "rta_office_code")
    private String rtaOfficeCode;
    
    @Column(name = "serial_no")
    private Long serialNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SerialNumberType serialNumberType;
    
    @Column(name = "year")
    private Integer year;
    
    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRtaOfficeCode() {
        return rtaOfficeCode;
    }

    public void setRtaOfficeCode(String rtaOfficeCode) {
        this.rtaOfficeCode = rtaOfficeCode;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public SerialNumberType getSerialNumberType() {
        return serialNumberType;
    }

    public void setSerialNumberType(SerialNumberType serialNumberType) {
        this.serialNumberType = serialNumberType;
    }

    public Integer getYear() {
        return year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
}
