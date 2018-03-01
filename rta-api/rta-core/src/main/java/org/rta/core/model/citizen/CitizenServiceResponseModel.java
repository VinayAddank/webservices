package org.rta.core.model.citizen;

import org.springframework.http.HttpStatus;

public class CitizenServiceResponseModel<T> {

    private HttpStatus httpStatus;
    private T responseBody;

    public CitizenServiceResponseModel(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public CitizenServiceResponseModel(HttpStatus httpStatus, T responseBody) {
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }

}
