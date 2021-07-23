package com.bank.api.account.util.exceptions;

import org.springframework.http.HttpStatus;

public class AccountException extends RuntimeException {
    private HttpStatus status = HttpStatus.BAD_REQUEST;
    public AccountException() {
    }

    public AccountException(String message) {
        super(message);
    }
    public AccountException(String message,  HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
