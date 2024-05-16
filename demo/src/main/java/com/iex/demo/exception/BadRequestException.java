package com.iex.demo.exception;


/**
 * @author Paul Ngouabeu
 * This class holds the implementation of BadRequestException
 */
public class BadRequestException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Object fieldName;
    private  Integer statusCode;

    public BadRequestException(Integer statusCode,String message,Object fieldName) {
        super(message);
        this.fieldName=fieldName;
        this.statusCode=statusCode;
    }

    public BadRequestException(Integer statusCode,String message) {
        super(message);
        this.statusCode=statusCode;
    }

    public Object getFieldName() {
        return fieldName;
    }

    public void setAllFields(Object fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setFieldName(Integer statusCode) {
        this.statusCode = statusCode;
    }


}
