package org.rta.core.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.rta.core.model.SaveUpdateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionMapper {
    
    @ExceptionHandler
    public ResponseEntity<SaveUpdateResponse> handleException(MethodArgumentNotValidException exception) {
        return createValidationError(exception);
    }

    private ResponseEntity<SaveUpdateResponse> createValidationError(MethodArgumentNotValidException e) {
        Errors errors = e.getBindingResult();
        SaveUpdateResponse errorRes = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        Map<String, Object> errorMap = new HashMap<>();
        for (ObjectError objectError : errors.getAllErrors()) {
            errorMap.put(objectError.getCodes()[0], objectError.getDefaultMessage());
        }
        errorRes.setAttributes(errorMap);
        errorRes.setCode(HttpStatus.BAD_REQUEST.value());
        errorRes.setMessage("Validation failed. " + errors.getErrorCount() + " error(s)");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorRes);
    }
    
    @ExceptionHandler({IllegalArgumentException.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
