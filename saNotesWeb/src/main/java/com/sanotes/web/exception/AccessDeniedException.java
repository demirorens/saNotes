package com.sanotes.web.exception;

import com.sanotes.web.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AccessDeniedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final ApiResponse apiResponse;
    private String message;

    public AccessDeniedException(ApiResponse apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

    public AccessDeniedException(String message) {
        super(message);
        this.message = message;
        apiResponse = new ApiResponse(Boolean.FALSE, message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
        apiResponse = new ApiResponse(Boolean.FALSE, message);
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
