package com.sanotes.web.exception;

import com.sanotes.web.payload.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BadRequestExceptionTest {

    @Test
    void testBadRequestException() {
        try {
            throw new BadRequestException(new ApiResponse(false, "exception", HttpStatus.BAD_REQUEST));
        } catch (Exception e) {
            assertTrue(e instanceof BadRequestException);
        }
    }

}