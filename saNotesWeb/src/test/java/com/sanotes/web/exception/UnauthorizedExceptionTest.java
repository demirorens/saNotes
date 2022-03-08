package com.sanotes.web.exception;

import com.sanotes.web.payload.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnauthorizedExceptionTest {

    @Test
    void testAccessDeniedException1() {
        try {
            throw new UnauthorizedException(new ApiResponse(false, "exception", HttpStatus.UNAUTHORIZED));
        } catch (Exception e) {
            assertTrue(e instanceof UnauthorizedException);
            assertEquals("exception", e.getMessage());
        }
    }

    @Test
    void testAccessDeniedException2() {
        try {
            throw new UnauthorizedException("exception");
        } catch (Exception e) {
            assertTrue(e instanceof UnauthorizedException);
            assertEquals("exception", e.getMessage());
        }
    }

}