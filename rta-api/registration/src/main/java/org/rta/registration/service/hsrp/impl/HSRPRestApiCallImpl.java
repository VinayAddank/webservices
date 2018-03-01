package org.rta.registration.service.hsrp.impl;

import org.apache.log4j.Logger;
import org.rta.core.model.hsrp.HSRPRTADetailModel;
import org.rta.registration.service.hsrp.HSRPRestApiCall;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HSRPRestApiCallImpl implements HSRPRestApiCall {

    private static final Logger log = Logger.getLogger(HSRPRestApiCall.class);
    private static final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> callAPIPost(String apiPath, HSRPRTADetailModel sendData, String contentType)
            throws RuntimeException {
        log.info("::::::::::callAPIPost:::::::::::");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", contentType);
        HttpEntity<HSRPRTADetailModel> entity = new HttpEntity<HSRPRTADetailModel>(sendData, headers);
        return restTemplate.exchange(apiPath, HttpMethod.POST, entity, String.class);
    }
}
