package com.bank.api.account.config.validation;

public class ErrorFormDTO {
    private String error;

    public ErrorFormDTO(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
