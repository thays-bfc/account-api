package com.bank.api.account.service;

import com.bank.api.account.dto.AccountResponse;
import com.bank.api.account.dto.CustomerForm;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(CustomerForm customerForm) throws IllegalArgumentException;

    List<AccountResponse> findAllWithCustomer();
}
