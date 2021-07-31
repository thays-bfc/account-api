package com.bank.api.account.service;

import com.bank.api.account.dto.AccountTransactionResponse;
import com.bank.api.account.dto.DepositForm;
import com.bank.api.account.dto.TransferForm;

public interface AccountTransactionService {
    AccountTransactionResponse deposit(DepositForm depositForm);

    AccountTransactionResponse transfer(TransferForm transferForm);

    AccountTransactionResponse findByAccountCode(Long code);
}
