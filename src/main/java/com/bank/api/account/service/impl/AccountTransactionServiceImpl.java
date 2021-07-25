package com.bank.api.account.service.impl;

import com.bank.api.account.dto.AccountTransactionResponse;
import com.bank.api.account.dto.DepositForm;
import com.bank.api.account.model.Account;
import com.bank.api.account.model.AccountTransaction;
import com.bank.api.account.model.enums.TransactionType;
import com.bank.api.account.repository.AccountTransactionRepository;
import com.bank.api.account.service.AccountService;
import com.bank.api.account.service.AccountTransactionService;
import com.bank.api.account.util.exceptions.AccountTransactionException;
import com.bank.api.account.util.mapper.AccountTransactionMapper;
import com.bank.api.account.util.message.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private AccountTransactionRepository accountTransactionRepository;

    private AccountService accountService;

    private AccountTransactionMapper mapper;

    @Autowired
    public AccountTransactionServiceImpl(AccountTransactionRepository accountTransactionRepository, AccountService accountService, AccountTransactionMapper mapper) {
        this.accountTransactionRepository = accountTransactionRepository;
        this.accountService = accountService;
        this.mapper = mapper;
    }

    @Override
    public AccountTransactionResponse deposit(DepositForm depositForm) {
        if (depositForm.getDepositValue().compareTo(new BigDecimal(SystemConstants.AccountTransaction.MAX_VALUE)) > 0){
            throw new AccountTransactionException(SystemConstants.AccountTransaction.MAX_VALUE_EXCEEDED, HttpStatus.BAD_REQUEST,SystemConstants.AccountTransaction.MAX_VALUE);
        }

        Account account = accountService.findAccountByCode(depositForm.getAccountCode());
        AccountTransaction accountTransaction = AccountTransaction.builder()
                .account(account)
                .type(TransactionType.D)
                .value(depositForm.getDepositValue())
                .transactionDate(new Date())
                .build();

        accountTransactionRepository.save(accountTransaction);

        BigDecimal balance = account.getBalance().add( accountTransaction.getValue() );
        account.setBalance(balance);
        accountService.save(account);

        return mapper.mapFrom(accountTransaction);
    }
}
