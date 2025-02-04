package com.framework.api;

import io.qameta.allure.Step;

public class ApiException extends RuntimeException {

    /**
     * Constructor for custom exception
     * @param message Exception message
     * @param cause The exception that caused this exception
     */
    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for custom exception
     * @param message Exception message
     */
    public ApiException(String message) {
        super(message);
    }
}