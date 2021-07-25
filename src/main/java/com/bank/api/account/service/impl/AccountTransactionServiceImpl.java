package com.bank.api.account.service.impl;

import com.bank.api.account.dto.AccountTransactionResponse;
import com.bank.api.account.dto.DepositForm;
import com.bank.api.account.dto.TransferForm;
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
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public AccountTransactionResponse transfer(TransferForm transferForm) {

        if (transferForm.getToAccountCode().equals(transferForm.getFromAccountCode())){
            throw new AccountTransactionException(SystemConstants.AccountTransaction.INVALID_SAME_ACCOUNT, HttpStatus.BAD_REQUEST);
        }

        Account fromAccount = accountService.findAccountByCode(transferForm.getFromAccountCode());

        if (fromAccount.getBalance().compareTo(transferForm.getTransferValue()) < 0) {
            throw new AccountTransactionException(SystemConstants.AccountTransaction.INSUFFICIENT_BALANCE, HttpStatus.BAD_REQUEST);
        }

        Account toAccount = accountService.findAccountByCode(transferForm.getToAccountCode());

        AccountTransaction fromAccountTransaction = AccountTransaction.builder()
                .account(fromAccount)
                .associatedAccount(toAccount)
                .type(TransactionType.TS)
                .value(transferForm.getTransferValue())
                .transactionDate(new Date())
                .build();

        AccountTransaction toAccountTransaction = AccountTransaction.builder()
                .account(toAccount)
                .associatedAccount(fromAccount)
                .type(TransactionType.TR)
                .value(transferForm.getTransferValue())
                .transactionDate(new Date())
                .build();

        BigDecimal fromBalance = fromAccount.getBalance().subtract( transferForm.getTransferValue() );
        BigDecimal toBalance = toAccount.getBalance().add( transferForm.getTransferValue() );

        accountTransactionRepository.save(fromAccountTransaction);
        accountTransactionRepository.save(toAccountTransaction);
        fromAccount.setBalance(fromBalance);
        accountService.save(fromAccount);
        toAccount.setBalance(toBalance);
        accountService.save(toAccount);

        return mapper.mapFrom(fromAccountTransaction);
    }

    @Override
    public AccountTransactionResponse findByAccountCode(Long code) {
        Account account = accountService.findAccountByCode(code);

        List<AccountTransaction> accountTransactionList = accountTransactionRepository.findByAccount(account);

        if (accountTransactionList.isEmpty()) {
            throw new AccountTransactionException(SystemConstants.AccountTransaction.NO_TRANSACTION, HttpStatus.NOT_FOUND);
        }
        AccountTransactionResponse accountTransactionResponse = mapper.mapFromAccount(account);
        accountTransactionResponse.setTransactions(accountTransactionList.stream().map(a -> mapper.mapFromTransaction(a)).collect(Collectors.toList()));
        return accountTransactionResponse;
    }
}
