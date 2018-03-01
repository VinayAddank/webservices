package org.rta.core.model.master;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *	@Author sohan.maurya created on Jul 12, 2016.
 */
@XmlRootElement
public class InsuranceTypeModel extends BaseMasterModel {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "InsuranceTypeModel [code=" + code + ", toString()=" + super.toString() + "]";
    }


}
