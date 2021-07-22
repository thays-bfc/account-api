package com.bank.api.account.service;

import com.bank.api.account.model.Customer;

import java.util.Optional;

public interface CustomerService {
    Customer findByCPF(String cpf);
    Optional<Customer> findById(Long id);
    Customer create(Customer customer);
    Boolean existsByCpf(String cpf);
}
