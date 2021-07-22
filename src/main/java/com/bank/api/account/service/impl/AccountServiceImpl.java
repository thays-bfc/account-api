package com.bank.api.account.service.impl;

import com.bank.api.account.dto.AccountResponse;
import com.bank.api.account.dto.CustomerForm;
import com.bank.api.account.model.Account;
import com.bank.api.account.model.Customer;
import com.bank.api.account.repository.AccountRepository;
import com.bank.api.account.service.AccountService;
import com.bank.api.account.service.CustomerService;
import com.bank.api.account.util.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private CustomerService customerService;

    private AccountMapper accountMapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, CustomerService customerService, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountResponse createAccount(CustomerForm customerForm) throws IllegalArgumentException {
        Customer customer =customerService.create(Customer.builder().cpf(customerForm.getCpf()).name(customerForm.getName()).build());
        Account account = Account.builder()
                .code(createCodeNumber())
                .balance(BigDecimal.ZERO)
                .customer(customer)
                .creationDate(new Date())
                .build();

        accountRepository.save(account);

        return accountMapper.mapFrom(account);
    }

    @Override
    public List<AccountResponse> findAllWithCustomer() {
        return accountRepository.findAll().stream().map(a -> accountMapper.mapFrom(a)).collect(Collectors.toList());
    }

    private Long createCodeNumber() {
     Long nextCode = accountRepository.findNextCode();
        return nextCode + 1L;
    }
}
