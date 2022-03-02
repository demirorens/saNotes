package com.sanotes.web.exception;

import com.sanotes.web.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public class ResponseEntityErrorException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private ResponseEntity<ApiResponse> response;

    public ResponseEntityErrorException(ResponseEntity<ApiResponse> response) {
        this.response = response;
    }

    public ResponseEntity<ApiResponse> getResponse() {
        return response;
    }

    public void setResponse(ResponseEntity<ApiResponse> response) {
        this.response = response;
    }
}
