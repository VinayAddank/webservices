/**
 * 
 */
package org.rta.core.model.sync;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author arun.verma
 *
 */
@JsonInclude(Include.NON_NULL)
public class ApplicationFormDataModel {

    @JsonIgnore
    private Long formDataId;
    @JsonIgnore
    private Long applicationId;
    private String applicaionNumber;
    @NotNull(message = "FormData Required !")
    private String formData;
    @NotNull(message = "FormCode Required !")
    private String formCode;

    public Long getFormDataId() {
        return formDataId;
    }

    public void setFormDataId(Long formDataId) {
        this.formDataId = formDataId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicaionNumber() {
        return applicaionNumber;
    }

    public void setApplicaionNumber(String applicaionNumber) {
        this.applicaionNumber = applicaionNumber;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) throws JsonProcessingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.readTree(formData);

        this.formData = formData;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }
}
