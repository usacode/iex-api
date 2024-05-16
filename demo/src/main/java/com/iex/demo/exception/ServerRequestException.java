package com.iex.demo.exception;

/**
 * @author Paul Ngouabeu
 * This class holds the implementation of ServerRequestException
 */
public class ServerRequestException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private  Integer statusCode;

    public ServerRequestException(Integer statusCode,String message) {
        super(message);
        this.statusCode=statusCode;
    }
    public Integer getStatusCode() {
        return statusCode;
    }

    public void setFieldName(Integer statusCode) {
        this.statusCode = statusCode;
    }


}
