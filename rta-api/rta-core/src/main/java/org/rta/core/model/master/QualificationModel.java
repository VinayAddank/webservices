package org.rta.core.model.master;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QualificationModel extends BaseMasterModel {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
