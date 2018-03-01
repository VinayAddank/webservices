package org.rta.core.entity.cardprinter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.entity.user.UserEntity;

/**
 * right now map office code with users only
 * 
 * @Author sohan.maurya created on Sep 22, 2016.
 */


@Entity
@Table(name = "rc_card_employee")
public class RcCardEmployeeEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "rccard_employee_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rccard_employee_gen")
    @SequenceGenerator(name = "rccard_employee_gen", sequenceName = "rccard_employee_sequence",
            allocationSize = 1)
    private Integer rccardEmployeeId;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rta_office_id")
    private RtaOfficeEntity rtaOfficeId;


    public Integer getRccardEmployeeId() {
        return rccardEmployeeId;
    }

    public void setRccardEmployeeId(Integer rccardEmployeeId) {
        this.rccardEmployeeId = rccardEmployeeId;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public RtaOfficeEntity getRtaOfficeId() {
        return rtaOfficeId;
    }

    public void setRtaOfficeId(RtaOfficeEntity rtaOfficeId) {
        this.rtaOfficeId = rtaOfficeId;
    }

}
