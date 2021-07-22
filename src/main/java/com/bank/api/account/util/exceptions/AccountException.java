package com.bank.api.account.util.exceptions;

public class AccountException extends RuntimeException {
    public AccountException() {
    }

    public AccountException(String message) {
        super(message);
    }
}
