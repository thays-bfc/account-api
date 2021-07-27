package com.bank.api.account.service;

import com.bank.api.account.dto.AccountResponse;
import com.bank.api.account.dto.CustomerForm;
import com.bank.api.account.model.Account;
import com.bank.api.account.model.Customer;
import com.bank.api.account.repository.AccountRepository;
import com.bank.api.account.repository.CustomerRepository;
import com.bank.api.account.service.impl.AccountServiceImpl;
import com.bank.api.account.service.impl.CustomerServiceImpl;
import com.bank.api.account.util.exceptions.AccountException;
import com.bank.api.account.util.exceptions.CustomerException;
import com.bank.api.account.util.mapper.AccountMapper;
import com.bank.api.account.util.message.SystemConstants;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private CustomerRepository customerRepository;

    private AccountMapper accountMapper;

    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        this.accountMapper = new AccountMapper(new DefaultMapperFactory.Builder().build());
        accountService = new AccountServiceImpl(accountRepository, customerService, accountMapper);

        Customer customer = Customer.builder()
                .id(1L)
                .cpf("96259371020")
                .name("Jane Doe")
                .build();

        this.account = Account.builder()
                .code(1L)
                .balance(BigDecimal.ZERO)
                .customer(customer)
                .creationDate(new Date())
                .build();

    }

    @Test
    void shouldReturnMappedFieldsWhenCreateAccount() {
        given(this.customerRepository.existsByCpf("96259371020")).willReturn(false);
        given(this.customerRepository.save(Customer.builder()
                .cpf("96259371020")
                .name("Jane Doe")
                .build())).willReturn(account.getCustomer());

        given(this.accountRepository.save(account))
                .willReturn(Account.builder().id(1L)
                .code(1L)
                .balance(BigDecimal.ZERO)
                .customer(account.getCustomer())
                .creationDate(new Date())
                .build());

        CustomerForm form = new CustomerForm();
        form.setCpf("96259371020");
        form.setName("Jane Doe");
        AccountResponse accountResponse = accountService.createAccount(form);

        assertEquals("962.593.710-20", accountResponse.getCpf());
        assertEquals(account.getCustomer().getName(), accountResponse.getName());
        assertEquals(account.getCode(),accountResponse.getCode());
        assertEquals(account.getId(),accountResponse.getId());

    }

    @Test
    void shouldThrowAccountExceptionWhenAccountDoesNotExistsByCode() {
        given(this.accountRepository.findByCode(2L)).willReturn(Optional.empty());
        try {
            accountService.findAccountByCode(2L);
        } catch (AccountException e) {
            assertEquals(SystemConstants.Account.NOT_FOUND, e.getMessage());
        }
    }

}