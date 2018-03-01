/**
 * 
 */
package org.rta.registration.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rta.core.model.SaveUpdateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author arun.verma
 *
 */
@RestController
@RequestMapping(value = "/error")
public class ErrorController {

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> error(HttpServletRequest request, HttpServletResponse response) {
        SaveUpdateResponse error = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        HttpStatus status = getStatus(request);
        error.setCode(status.value());
        error.setMessage(request.getAttribute(RequestDispatcher.ERROR_MESSAGE) + "");
        if(status == HttpStatus.UNAUTHORIZED){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(error);
    }
    
    private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
    
}
