package com.bank.api.account.service;

import com.bank.api.account.dto.AccountTransactionResponse;
import com.bank.api.account.dto.DepositForm;

public interface AccountTransactionService {
    AccountTransactionResponse deposit(DepositForm depositForm);
}
