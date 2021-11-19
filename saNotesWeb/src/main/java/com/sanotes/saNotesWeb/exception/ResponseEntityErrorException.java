package com.sanotes.saNotesWeb.exception;

import com.sanotes.saNotesWeb.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public class ResponseEntityErrorException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private transient ResponseEntity<ApiResponse> response;

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
