package org.rta.registration.service.hsrp;

import org.rta.core.model.hsrp.HSRPRTADetailModel;
import org.springframework.http.ResponseEntity;

public interface HSRPRestApiCall {

    public ResponseEntity<String> callAPIPost(String apiPath, HSRPRTADetailModel sendData, String contentType);
}
