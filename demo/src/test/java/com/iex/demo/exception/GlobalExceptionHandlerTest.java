package com.iex.demo.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @Test
    public void testHandleNotFoundException() {
        NotFoundException ex = new NotFoundException("Resource not found", 404, HttpStatus.NOT_FOUND.value());

        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<Object> response = handler.handleNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorModel error = (ErrorModel) response.getBody();
        assertEquals("Resource not found", error.getMessage());

    }

    @Test
    public void testHandleBadRequestException() {
        BadRequestException ex = new BadRequestException(400, "parameterName", HttpStatus.BAD_REQUEST.value());

        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<Object> response = handler.handleBadRequestException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorModel error = (ErrorModel) response.getBody();
        assertEquals("parameterName", error.getMessage());

    }

    @Test
    public void testHandleMissingServletRequestParameter() {
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("param", "String");

        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<Object> response = handler.handleMissingServletRequestParameter(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorModel error = (ErrorModel) response.getBody();
        String expectedMessage = "Required parameter 'param' is missing";
        assertEquals(expectedMessage, error.getMessage());

    }

    @Test
    public void testHandleGenericException() {
        Exception ex = new Exception("General error");

        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ResponseEntity<Object> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorModel error = (ErrorModel) response.getBody();
        String expectedMessage = "An unexpected error occurred. Please try again later.";
        assertEquals(expectedMessage, error.getMessage());
    }
}