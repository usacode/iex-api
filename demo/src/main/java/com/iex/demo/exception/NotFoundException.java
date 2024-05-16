package com.iex.demo.exception;

import org.springframework.http.HttpStatus;

/**
 *
 * @author Paul Ngouabeu
 * This class holds the implementation of NotFoundException
 *
 */
public class NotFoundException extends StockException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer statusCode;
    private Object fieldName;

    public NotFoundException(String message,Integer statusCode,Object fieldName) {
        super(message);
        this.statusCode=statusCode;
        this.fieldName=fieldName;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Object getFieldName() {
        return fieldName;
    }

    public void setFieldName(Object fieldName) {
        this.fieldName = fieldName;
    }
}

