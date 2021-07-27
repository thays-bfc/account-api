package com.bank.api.account.service.impl;

import com.bank.api.account.model.Customer;
import com.bank.api.account.repository.CustomerRepository;
import com.bank.api.account.service.CustomerService;
import com.bank.api.account.util.exceptions.CustomerException;
import com.bank.api.account.util.message.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer create(Customer customer) {
        customer.setCpf(processCPF(customer.getCpf()));
        if (existsByCpf(customer.getCpf())) {
            throw new CustomerException(SystemConstants.Customer.CUSTOMER_EXISTS);
        }
        return customerRepository.save(customer);
    }

    @Override
    public Boolean existsByCpf(String cpf) {
        return customerRepository.existsByCpf(processCPF(cpf));
    }

    private String processCPF(String cpf) {
        return cpf.replace( "." , "").replace("-", "");
    }
}
