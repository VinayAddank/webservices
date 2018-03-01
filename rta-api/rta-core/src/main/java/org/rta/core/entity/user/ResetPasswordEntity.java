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
@Table(name = "reset_password_log")
public class ResetPasswordEntity extends BaseEntity {

    private static final long serialVersionUID = -466039844531028673L;

    @Id
    @Column(name = "reset_pwd_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reset_pwd_seq")
    @SequenceGenerator(name = "reset_pwd_seq", sequenceName = "reset_pwd_seq", allocationSize = 1)
    private Long resetPwdId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "old_password")
    private String oldPassword;

    @Column(name = "new_password")
    private String newPassword;

    public Long getResetPwdId() {
        return resetPwdId;
    }

    public void setResetPwdId(Long resetPwdId) {
        this.resetPwdId = resetPwdId;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
