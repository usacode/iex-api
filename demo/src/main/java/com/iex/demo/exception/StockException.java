package com.iex.demo.exception;


/**
 * @author Paul Ngouabeu
 * This class servers as custom exception
 */
public class StockException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for StockException with one parameter
     * @param message - message
     */
    public StockException(final String message) {
        super(message);
    }

    /**
     * Constructor for StockException with two parameters
     * @param message - message
     * @param cause - cause
     */
    public StockException(final String message, final Throwable cause) {
        super(message, cause);
    }
}