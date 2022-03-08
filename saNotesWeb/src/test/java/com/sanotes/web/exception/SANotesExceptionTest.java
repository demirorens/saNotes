package com.sanotes.web.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SANotesExceptionTest {

    @Test
    void testSANotesException() {
        try {
            throw new SANotesException("exception");
        } catch (Exception e) {
            assertTrue(e instanceof SANotesException);
            assertEquals("exception", e.getMessage());
        }
    }

}