package com.bank.api.account.util.exceptions;

import org.springframework.http.HttpStatus;

public class CustomerException extends RuntimeException {
    private HttpStatus status = HttpStatus.BAD_REQUEST;
    public CustomerException() {
    }

    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(String message,  HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
