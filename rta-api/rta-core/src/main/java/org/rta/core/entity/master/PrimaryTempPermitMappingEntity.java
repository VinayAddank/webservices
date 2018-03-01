/**
 * 
 */
package org.rta.core.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

/**
 * @author arun.verma
 *
 */
@Entity
@Table(name = "primary_temp_permit_mapping")
public class PrimaryTempPermitMappingEntity extends BaseEntity {

    private static final long serialVersionUID = -2503436734243178467L;

    @Id
    @Column(name = "primary_temp_permit_mapping_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_temp_permit_mapping_seq")
    @SequenceGenerator(name = "primary_temp_permit_mapping_seq", sequenceName = "primary_temp_permit_mapping_seq",
            allocationSize = 1)
    private Long primaryTempPermitMappingId;

    @Column(name = "primary_permit_code")
    private String primaryPermitCode;

    @Column(name = "temporary_permit_code")
    private String temporaryPermitCode;

    public Long getPrimaryTempPermitMappingId() {
        return primaryTempPermitMappingId;
    }

    public void setPrimaryTempPermitMappingId(Long primaryTempPermitMappingId) {
        this.primaryTempPermitMappingId = primaryTempPermitMappingId;
    }

    public String getPrimaryPermitCode() {
        return primaryPermitCode;
    }

    public void setPrimaryPermitCode(String primaryPermitCode) {
        this.primaryPermitCode = primaryPermitCode;
    }

    public String getTemporaryPermitCode() {
        return temporaryPermitCode;
    }

    public void setTemporaryPermitCode(String temporaryPermitCode) {
        this.temporaryPermitCode = temporaryPermitCode;
    }

}
