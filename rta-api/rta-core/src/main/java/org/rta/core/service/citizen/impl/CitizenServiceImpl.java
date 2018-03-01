package org.rta.core.service.citizen.impl;

import java.util.List;

import org.rta.core.enums.ServiceType;
import org.rta.core.enums.citizen.SlotServiceType;
import org.rta.core.model.citizen.CitizenApplicationModel;
import org.rta.core.model.citizen.CitizenServiceResponseModel;
import org.rta.core.model.payment.tax.DifferentialTaxFeeModel;
import org.rta.core.model.sync.ApplicationFormDataModel;
import org.rta.core.service.citizen.CitizenService;
import org.rta.core.service.citizen.ResponseModel;
import org.rta.core.service.citizen.model.AuthenticationModel;
import org.rta.core.service.citizen.model.CitizenTokenModel;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class CitizenServiceImpl implements CitizenService{

	@Autowired
    private RestTemplate restTemplate;
    
    @Value("${url.citizen}")
    private String citizenRootURL;
    
    @Override
	public CitizenServiceResponseModel<String> saveOrUpdateByBodyBuilder(String appnumber, String token) {
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", token);
        HttpEntity<Object> httpEntity = new HttpEntity<>( headers);
        ResponseEntity<Object> response =null;
        HttpStatus httpStatus =null;
        try{
        	response = restTemplate.exchange( new StringBuilder(citizenRootURL).append("/application/terminate/activiti/task/bodybuilder")
        				.append("?appnumber=").append(appnumber).toString(), HttpMethod.POST, httpEntity, Object.class);
            httpStatus = response.getStatusCode();
        } catch(HttpStatusCodeException ex){
            httpStatus = ex.getStatusCode();
        }
        String responseBody = null;
        if (httpStatus == HttpStatus.OK) {
            if (response.hasBody()) {
                responseBody = ResponseModel.SUCCESS;
            }
        }
        return new CitizenServiceResponseModel<String>(httpStatus, responseBody);
	}

    @Override
    public CitizenServiceResponseModel<List<CitizenApplicationModel>> getApplications(String token, ServiceType st,
            Long timestamp, SlotServiceType slotType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", token);
        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
        ResponseEntity<List<CitizenApplicationModel>> response = null;
        HttpStatus httpStatus = null;
        try {
            response = restTemplate.exchange(
                    new StringBuilder(citizenRootURL).append("/").append(st)
                            .append("/applications/pending").append("?timestamp=").append(timestamp)
                            .append("&slotservicetype=").append(slotType).toString(),
                    HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<CitizenApplicationModel>>() {});
            httpStatus = response.getStatusCode();
        } catch (HttpStatusCodeException ex) {
            httpStatus = ex.getStatusCode();
        }
        List<CitizenApplicationModel> responseBody = null;
        if (httpStatus == HttpStatus.OK) {
            if (response.hasBody()) {
                responseBody = response.getBody();
            }
        }
        return new CitizenServiceResponseModel<List<CitizenApplicationModel>>(httpStatus, responseBody);
    }
    
    @Override
    public CitizenServiceResponseModel<ResponseModel<CitizenTokenModel>> login(AuthenticationModel authenticationModel, ServiceType st, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", token);
        HttpEntity<AuthenticationModel> httpEntity = new HttpEntity<AuthenticationModel>(authenticationModel, headers);
        ResponseEntity<ResponseModel<CitizenTokenModel>> response = null;
        HttpStatus httpStatus = null;
        String faildMsg = "";
        String faildCode = "0";
        try {
            response = restTemplate.exchange(
                    new StringBuilder(citizenRootURL).append("/").append(st.getCode()).append("/login").toString(),
                    HttpMethod.POST, httpEntity, new ParameterizedTypeReference<ResponseModel<CitizenTokenModel>>() {});
            httpStatus = response.getStatusCode();
        } catch (HttpClientErrorException ex) {
            httpStatus = ex.getStatusCode();
            try{
                faildMsg = ex.getResponseHeaders().get("message").toString();
                faildCode = ex.getResponseHeaders().get("code").toString();
            } catch(Exception e){
                faildMsg = ex.getMessage();
                faildCode = httpStatus.value() + "";
            }
        }
        ResponseModel<CitizenTokenModel> responseBody = null;
        if (httpStatus == HttpStatus.OK) {
            if (response.hasBody()) {
                responseBody = response.getBody();
            }
        } else {
//            faildMsg = response.getHeaders().get("message").toString();
            List<String> stringList = response.getHeaders().get("code");
            if (!ObjectsUtil.isNullOrEmpty(stringList)) {
                faildCode = stringList.get(0);
            }
            if(StringsUtil.isNullOrEmpty(faildMsg) && !ObjectsUtil.isNullOrEmpty(response.getHeaders().get("message"))){
            	faildMsg = response.getHeaders().get("message").get(0);
            }
            responseBody = new ResponseModel<>();
            responseBody.setStatus(ResponseModel.FAILED);
            responseBody.setMessage(faildMsg);
            Integer code = null;
            try{
                code = Integer.parseInt(faildCode);
            } catch(Exception ex){
            }
            responseBody.setStatusCode(code);
        }
        return new CitizenServiceResponseModel<ResponseModel<CitizenTokenModel>>(httpStatus, responseBody);
    }
    
    @Override
	public CitizenServiceResponseModel<DifferentialTaxFeeModel> getDiffertialTaxFeeDetails(String trNumber, String token) {
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", token);
        HttpEntity<Object> httpEntity = new HttpEntity<>( headers);
        ResponseEntity<DifferentialTaxFeeModel> response =null;
        HttpStatus httpStatus =null;
        try{
        	response = restTemplate.exchange( new StringBuilder(citizenRootURL).append("/differntialtax/details")
        				.append("?trnumber=").append(trNumber).toString(), HttpMethod.GET, httpEntity, DifferentialTaxFeeModel.class);
            httpStatus = response.getStatusCode();
        } catch(HttpStatusCodeException ex){
            httpStatus = ex.getStatusCode();
        }
        DifferentialTaxFeeModel responseBody = null;
        if (httpStatus == HttpStatus.OK) {
            if (response.hasBody()) {
                responseBody = response.getBody();
            }
        }
        return new CitizenServiceResponseModel<DifferentialTaxFeeModel>(httpStatus, responseBody);
	}

    @Override
    public CitizenServiceResponseModel<ApplicationFormDataModel> getApplicantDetails(String applicationNo, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", token);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ApplicationFormDataModel> response = null;
        HttpStatus httpStatus = null;
        try {
            response = restTemplate.exchange(new StringBuilder(citizenRootURL).append("/form/otd_form/otd")
                       .append("?applicationno=").append(applicationNo).toString(), HttpMethod.GET, httpEntity, ApplicationFormDataModel.class);
            httpStatus = response.getStatusCode();
        } catch (HttpStatusCodeException ex) {
            httpStatus = ex.getStatusCode();
        }
        ApplicationFormDataModel responseBody = null;
        if (httpStatus == HttpStatus.OK) {
            if (response.hasBody()) {
                responseBody = response.getBody();
            }
        }
        return new CitizenServiceResponseModel<ApplicationFormDataModel>(httpStatus, responseBody);
    }

}
