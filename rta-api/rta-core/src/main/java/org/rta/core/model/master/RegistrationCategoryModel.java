package org.rta.core.model.master;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegistrationCategoryModel extends BaseMasterModel {

    private String code;
    private Integer registrationCategoryId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getRegistrationCategoryId() {
        return registrationCategoryId;
    }

    public void setRegistrationCategoryId(Integer registrationCategoryId) {
        this.registrationCategoryId = registrationCategoryId;
    }


}
