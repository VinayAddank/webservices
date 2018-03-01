package org.rta.core.model.master;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CountryModel extends BaseMasterModel {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
