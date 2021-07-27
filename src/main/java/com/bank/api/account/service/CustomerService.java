package com.bank.api.account.service;

import com.bank.api.account.model.Customer;

public interface CustomerService {
    Customer create(Customer customer);
    Boolean existsByCpf(String cpf);
}
