package com.bank.api.account.model.enums;

import lombok.Getter;

public enum TransactionType {
    D("Deposit"),
    W("Withdrawal"),
    T("Transfer");

    @Getter
    private String description;

    TransactionType(String description) {
        this.description = description;
    }
}
