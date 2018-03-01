package org.rta.core.model.certificate;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *	@Author sohan.maurya created on Nov 10, 2016.
 */
@XmlRootElement
public class PermitSubTypeModel {

    private Integer code;
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
