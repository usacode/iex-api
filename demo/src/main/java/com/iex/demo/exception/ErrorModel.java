package com.iex.demo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * @author Paul Ngouabeu
 * This class serves as model for the exception response.
 */
@Data
@EqualsAndHashCode
public class ErrorModel {
    /**
     * The property for the error status
     */
    private Integer status;

    /**
     * The property for the error message
     */
    private String message;

    /**
     * The property for the error fields
     */
    private Object field;

    /**
     * The class constructor for ErrorModel
     * @param status - status
     * @param message - message
     * @param field - field
     */
    public ErrorModel(final Integer status, final String message, final Object field) {
        super();
        this.status=status;
        this.message=message;
        this.field=field;
    }

    /**
     * The class constructor for ErrorModel
     * @param status - status
     * @param message - message
     */
    public ErrorModel(final Integer status, final String message) {
        super();
        this.status=status;
        this.message=message;
    }


}
