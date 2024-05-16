package com.iex.demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Paul Ngouabeu
 * This class advice the controllers for triggering the exceptions
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * This handleNotFoundException method handles the not found exception.
     * @param ex - NotFoundException
     * @return It returns ResponseEntity of the exception.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(final NotFoundException ex) {
        logger.warn(ex.getMessage()+"{}", ex.getFieldName());
        final ErrorModel apiError = new ErrorModel(ex.getStatusCode(), ex.getMessage(), ex.getFieldName());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    /**
     * This handleBadRequestException method handles the bad request exception.
     * @param ex - BadRequestException
     * @return It returns ResponseEntity of the exception.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(final BadRequestException ex) {
        logger.warn(ex.getMessage()+"{}", ex.getFieldName());
        final  ErrorModel apiError = new ErrorModel(ex.getStatusCode(), ex.getMessage(), ex.getFieldName());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * This handleBadRequestException method handles the missing parameter exception for mandatory fields.
     * @param ex - BadRequestException
     * @return It returns ResponseEntity of the exception.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        String errorMessage = String.format("Required parameter '%s' is missing", ex.getParameterName());
        final  ErrorModel apiError = new ErrorModel(HttpStatus.BAD_REQUEST.value(),errorMessage,ex.getParameterName());
        logger.warn("Missing required parameter: {}", apiError);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * This handleGenericException method handles the internal server error exception.
     * @param ex - BadRequestException
     * @return It returns ResponseEntity of the exception.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        // Log the exception
        logger.error("Unexpected error occurred", ex);
        String errorMessage = "An unexpected error occurred. Please try again later.";
        final  ErrorModel apiError = new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),errorMessage);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
