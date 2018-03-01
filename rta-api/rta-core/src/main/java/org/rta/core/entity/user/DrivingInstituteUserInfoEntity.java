package org.rta.core.entity.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "driving_institute_users_info")
public class DrivingInstituteUserInfoEntity extends BaseEntity {

	private static final long serialVersionUID = -5540827268469465425L;

	@Id
    @Column(name = "driving_institute_users_info_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driving_institute_users_info_seq")
    @SequenceGenerator(name = "driving_institute_users_info_seq", sequenceName = "driving_institute_users_info_seq", allocationSize = 1)
    private Long drivingInstituteUsersInfoId;
    
    @Column(name = "number")
    private String number;
    
    @Column(name = "type")
    private Integer type;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "driving_institute_users_id")
    private DrivingInstituteUserEntity drivingInstituteUser;
    
    public Long getDrivingInstituteUsersInfoId() {
        return drivingInstituteUsersInfoId;
    }

    public void setDrivingInstituteUsersInfoId(Long drivingInstituteUsersInfoId) {
        this.drivingInstituteUsersInfoId = drivingInstituteUsersInfoId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public DrivingInstituteUserEntity getDrivingInstituteUser() {
        return drivingInstituteUser;
    }

    public void setDrivingInstituteUser(DrivingInstituteUserEntity drivingInstituteUser) {
        this.drivingInstituteUser = drivingInstituteUser;
    }

}
