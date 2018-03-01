package org.rta.registration.controller;

import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.user.DealerModel;
import org.rta.core.service.user.DealerService;
import org.rta.core.utils.NumberParser;
import org.rta.security.utills.RtaCryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DealerController {

    @Value("${jwt.claim.secret}")
    private String claimSecret;

    @Autowired
    private DealerService dealerService;

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(value = "/dealer/{id}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getUserFromToken(@PathVariable("id") String id) {
        String idStr = RtaCryptoUtil.parseSecureToken(id, claimSecret);
        if (!StringUtils.isEmpty(idStr) && NumberParser.isNumeric(idStr)) {
            DealerModel dealer = dealerService.findByUserId(Long.valueOf(idStr));
            if (dealer == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(dealer);
        }
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        errorModel.setMessage("Please provide a valid info.");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
    }
}
