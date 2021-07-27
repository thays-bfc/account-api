package com.bank.api.account.service;

import com.bank.api.account.model.Customer;
import com.bank.api.account.repository.CustomerRepository;
import com.bank.api.account.service.impl.CustomerServiceImpl;
import com.bank.api.account.util.exceptions.CustomerException;
import com.bank.api.account.util.message.SystemConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class CustomerServiceTest {
    @MockBean
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        this.customerService = new CustomerServiceImpl(customerRepository);
        this.customer = Customer.builder()
                .cpf("962.593.710-20")
                .name("Jane Doe")
                .build();
    }

    @Test
    void shouldThrowCustomerExceptionWhenCPFExistsOnCreate() {
        given(this.customerRepository.existsByCpf("96259371020")).willReturn(true);
        try {
            customerService.create(customer);
        } catch (CustomerException e) {
            assertEquals(e.getMessage(), SystemConstants.Customer.CUSTOMER_EXISTS);
        }
    }

}