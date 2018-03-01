package org.rta.core.model.master;

import javax.xml.bind.annotation.XmlRootElement;

import org.rta.core.model.base.BaseModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
public class DocTypesModel extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Boolean isMandatory;

    @JsonIgnore
    private String roleName;

    public Boolean getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
