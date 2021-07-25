package com.bank.api.account.model.enums;

import lombok.Getter;

public enum TransactionType {
    D("Deposit"),
    W("Withdrawal"),
    TS("Transfer sent"),
    TR("Transfer received");

    @Getter
    private String description;

    TransactionType(String description) {
        this.description = description;
    }
}
