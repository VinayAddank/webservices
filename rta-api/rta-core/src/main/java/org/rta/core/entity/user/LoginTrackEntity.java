package org.rta.core.entity.user;

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
@Table(name = "login_track")
public class LoginTrackEntity extends BaseEntity {

    private static final long serialVersionUID = 3606240756291597337L;

    @Id
    @Column(name = "login_track_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "login_track_seq")
    @SequenceGenerator(name = "login_track_seq", sequenceName = "login_track_seq", allocationSize = 1)
    private Long loginTrackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "login_time")
    private Long loginTime;

    @Column(name = "ip_address")
    private String ipAddress;

    public Long getLoginTrackId() {
        return loginTrackId;
    }

    public void setLoginTrackId(Long loginTrackId) {
        this.loginTrackId = loginTrackId;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
