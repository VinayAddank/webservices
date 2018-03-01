package org.rta.core.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.rta.core.model.base.BaseModel;

@XmlRootElement
public class TokenModel extends BaseModel {

    private static final long serialVersionUID = 3665400640422269434L;

    private String id;
    private String token;
    private List<String> roles;

    public TokenModel() {}

    public TokenModel(String token) {
        this.setToken(token);
    }

    public TokenModel(String token, String id) {
        this.setToken(token);
        this.setId(id);
    }

    public TokenModel(String token, String id, List<String> roles) {
        this.setToken(token);
        this.setId(id);
        this.setRoles(roles);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
