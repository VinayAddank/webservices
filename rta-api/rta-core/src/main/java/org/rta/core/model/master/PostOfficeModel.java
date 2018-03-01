package org.rta.core.model.master;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author arun.verma
 *
 */
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class PostOfficeModel extends BaseMasterModel {

    private String pinCode;
    private MandalModel mandalModel;

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public MandalModel getMandalModel() {
        return mandalModel;
    }

    public void setMandalModel(MandalModel mandalModel) {
        this.mandalModel = mandalModel;
    }

}
