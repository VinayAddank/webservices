package org.rta.core.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
public class RTAExceptionHandler extends ResponseEntityExceptionHandler {

   /* @ExceptionHandler({IllegalArgumentException.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }*/
    
    @ExceptionHandler({Exception.class})
    void handleBadRequests1(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.ACCEPTED.value());
    }
    
}