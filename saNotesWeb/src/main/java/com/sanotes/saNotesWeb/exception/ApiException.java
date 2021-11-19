package com.sanotes.saNotesWeb.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;
    private String message;

    public ApiException(HttpStatus httpStatus, String message) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ApiException(String message, Throwable exception, HttpStatus httpStatus) {
        super(exception);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
