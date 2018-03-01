/**
 * shivangi.gupta
 */
package org.rta.core.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author shivangi.gupta
 *
 */

@Entity
@Table(name = "ownership_type")
public class OwnershipTypeEntity extends BaseMasterEntity {

    private static final long serialVersionUID = -2729155802634215121L;

    @Id
    @Column(name = "ownership_type_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ownership_type_seq")
    @SequenceGenerator(name = "ownership_type_seq", sequenceName = "ownership_type_seq", allocationSize = 1)
    private Long ownershipTypeId;
    
    @Column(name = "code", length = 50, unique = true)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
