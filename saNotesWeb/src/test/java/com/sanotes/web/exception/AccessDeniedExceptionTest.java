package com.sanotes.web.exception;

import com.sanotes.web.payload.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccessDeniedExceptionTest {
    @Test
    void testAccessDeniedException() {
        try {
            throw new AccessDeniedException(new ApiResponse(false, "exception", HttpStatus.UNAUTHORIZED));
        } catch (Exception e) {
            assertTrue(e instanceof AccessDeniedException);
            assertEquals("exception", e.getMessage());
        }
    }

}