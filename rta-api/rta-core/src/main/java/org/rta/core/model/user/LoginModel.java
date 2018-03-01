package org.rta.core.model.user;

import org.rta.core.model.base.BaseModel;

public class LoginModel extends BaseModel {

    private static final long serialVersionUID = -728306390324723610L;
    private String username;
    private String password;

    public LoginModel() {}

    public LoginModel(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
