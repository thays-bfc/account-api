package com.bank.api.account.service;

import com.bank.api.account.dto.AccountResponse;
import com.bank.api.account.dto.CustomerForm;
import com.bank.api.account.model.Account;
import java.util.List;

public interface AccountService {

    AccountResponse createAccount(CustomerForm customerForm) throws IllegalArgumentException;

    List<AccountResponse> findAllWithCustomer();

    AccountResponse findById(Long id);

    Account findAccountByCode(Long code);

    void save(Account account);
}
