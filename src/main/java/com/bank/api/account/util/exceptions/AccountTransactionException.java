package com.bank.api.account.util.exceptions;

import org.springframework.http.HttpStatus;

public class AccountTransactionException extends RuntimeException {
    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private Object[] params;

    public AccountTransactionException() {
    }

    public AccountTransactionException(String message) {
        super(message);
    }
    public AccountTransactionException(String message,  HttpStatus status) {
        super(message);
        this.status = status;
    }

    public AccountTransactionException(String message,  HttpStatus status, Object... params) {
        super(message);
        this.status = status;
        this.params = params;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Object[] getParams() {
        return params;
    }
}
