package com.sanotes.web.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResourceNotFoundExceptionTest {

    @Test
    void testResourceNotFoundException() {
        try {
            throw new ResourceNotFoundException("resourceName", "fieldName", "fieldValue");
        } catch (Exception e) {
            assertTrue(e instanceof ResourceNotFoundException);
            assertEquals("resourceName", ((ResourceNotFoundException) e).getResourceName());
        }
    }

}