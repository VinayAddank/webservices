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
@Table(name = "tax_type")
public class TaxTypeEntity extends BaseMasterEntity {

    private static final long serialVersionUID = -7741425246015526622L;

    @Id
    @Column(name = "tax_type_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tax_type_seq")
    @SequenceGenerator(name = "tax_type_seq", sequenceName = "tax_type_seq", allocationSize = 1)
    private Long taxTypeId;

    @Column(name = "code", length = 50, unique = true)
    private String code;

    @Column(name = "percentage")
    private double percentage;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getTaxTypeId() {
        return taxTypeId;
    }

    public void setTaxTypeId(Long taxTypeId) {
        this.taxTypeId = taxTypeId;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }


}
