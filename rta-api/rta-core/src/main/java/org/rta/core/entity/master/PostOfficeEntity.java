package org.rta.core.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author arun.verma
 *
 */

@Entity
@Table(name = "post_office")
public class PostOfficeEntity extends BaseMasterEntity {
    private static final long serialVersionUID = -1464812739738841949L;
    @Id
    @Column(name = "post_office_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_office_id_seq")
    @SequenceGenerator(name = "post_office_id_seq", sequenceName = "post_office_id_seq", allocationSize = 1)
    private Long postOfficeId;
    @Column(name = "pin_code", length = 6, unique = true)
    private String pinCode;

    public Long getPostOfficeId() {
        return postOfficeId;
    }

    public void setPostOfficeId(Long postOfficeId) {
        this.postOfficeId = postOfficeId;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
