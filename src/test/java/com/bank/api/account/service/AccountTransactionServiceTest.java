package com.bank.api.account.service;

import com.bank.api.account.dto.AccountTransactionResponse;
import com.bank.api.account.dto.DepositForm;
import com.bank.api.account.dto.TransferForm;
import com.bank.api.account.model.Account;
import com.bank.api.account.model.Customer;
import com.bank.api.account.model.enums.TransactionType;
import com.bank.api.account.repository.AccountRepository;
import com.bank.api.account.repository.AccountTransactionRepository;
import com.bank.api.account.repository.CustomerRepository;
import com.bank.api.account.service.impl.AccountServiceImpl;
import com.bank.api.account.service.impl.AccountTransactionServiceImpl;
import com.bank.api.account.service.impl.CustomerServiceImpl;
import com.bank.api.account.util.exceptions.AccountTransactionException;
import com.bank.api.account.util.mapper.AccountMapper;
import com.bank.api.account.util.mapper.AccountTransactionMapper;
import com.bank.api.account.util.message.SystemConstants;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class AccountTransactionServiceTest {

    @MockBean
    private AccountTransactionRepository accountTransactionRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private CustomerRepository customerRepository;

    private AccountTransactionService accountTransactionService;

    private AccountService accountService;

    private Account account;

    private AccountTransactionMapper mapper;

    @BeforeEach
    void setUp() {
        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        AccountMapper accountMapper = new AccountMapper(new DefaultMapperFactory.Builder().build());
        this.mapper = new AccountTransactionMapper(new DefaultMapperFactory.Builder().build());
        accountService = new AccountServiceImpl(accountRepository, customerService, accountMapper);
        accountTransactionService = new AccountTransactionServiceImpl(accountTransactionRepository, accountService, mapper);

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
    void shouldThrowAccountTransactionExceptionWhenDepositValueOverMAX_VALUE() {
        DepositForm form = new DepositForm(1L, new BigDecimal("2001.00"));
        try {
            accountTransactionService.deposit(form);
        } catch (AccountTransactionException e) {
            assertEquals(SystemConstants.AccountTransaction.MAX_VALUE_EXCEEDED, e.getMessage());
        }
    }
    @Test
    void shouldReturnProcessedDepositWhenDeposit() {
        given(this.accountRepository.findByCode(1L)).willReturn(Optional.ofNullable(account));
        BigDecimal accountBalance = account.getBalance();
        DepositForm form = new DepositForm(1L, new BigDecimal("2000.00"));

        AccountTransactionResponse deposit = accountTransactionService.deposit(form);

        assertEquals(TransactionType.D.getDescription(), deposit.getTransactions().get(0).getType());
        assertEquals(form.getDepositValue(), deposit.getTransactions().get(0).getValue());
        assertEquals(accountBalance.add( form.getDepositValue() ), deposit.getAccountBalance());
    }

    @Test
    void shouldThrowAccountTransactionExceptionWhenTransferToSameAccount() {
        TransferForm form = new TransferForm(1L, 1L, new BigDecimal("2000.00"));
        try {
            accountTransactionService.transfer(form);
        } catch (AccountTransactionException e ) {
            assertEquals(SystemConstants.AccountTransaction.INVALID_SAME_ACCOUNT, e.getMessage());
        }
    }

    @Test
    void shouldThrowAccountTransactionExceptionWhenTransferValueNot() {
        given(this.accountRepository.findByCode(1L)).willReturn(Optional.ofNullable(account));
        TransferForm form = new TransferForm(1L, 2L, new BigDecimal("2000.00"));
        try {
            accountTransactionService.transfer(form);
        } catch (AccountTransactionException e ) {
            assertEquals(SystemConstants.AccountTransaction.INSUFFICIENT_BALANCE, e.getMessage());
        }
    }

    @Test
    void shouldReturnProcessedTransferWhenTransfer() {
        Account fromAccount =  Account.builder()
                .code(2L)
                .balance( new BigDecimal("2000.00"))
                .customer(Customer.builder()
                        .id(2L)
                        .cpf("09324055097")
                        .name("John Doe")
                        .build())
                .creationDate(new Date())
                .build();
        BigDecimal fromBalance = fromAccount.getBalance();
        BigDecimal toBalance = account.getBalance();
        given(this.accountRepository.findByCode(2L)).willReturn(Optional.of(fromAccount));
        given(this.accountRepository.findByCode(1L)).willReturn(Optional.ofNullable(account));

        TransferForm form = new TransferForm(2L, 1L, new BigDecimal("1000.00"));

        AccountTransactionResponse transfer = accountTransactionService.transfer(form);

        assertEquals(TransactionType.TS.getDescription(), transfer.getTransactions().get(0).getType());
        assertEquals(form.getTransferValue(), transfer.getTransactions().get(0).getValue());
        assertEquals(fromBalance.subtract( form.getTransferValue() ), transfer.getAccountBalance());
        assertEquals(toBalance.add( form.getTransferValue() ), account.getBalance());

    }

}