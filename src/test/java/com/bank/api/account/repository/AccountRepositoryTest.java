package com.bank.api.account.repository;

import com.bank.api.account.model.Account;
import com.bank.api.account.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AccountRepositoryTest {

    @Autowired
    AccountRepository repository;

    @Autowired
    private TestEntityManager em;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder()
                .cpf("96259371020")
                .name("Jane Doe")
                .build();

        em.persist(customer);

        Account account = Account.builder()
                .customer(customer)
                .code(1L)
                .balance(BigDecimal.ZERO)
                .creationDate(new Date())
                .build();

        em.persist(account);
    }

    @Test
    void shouldCreateAccount() {
        Customer customer = Customer.builder()
                .cpf("29994958038")
                .name("Felipe Alves")
                .build();
        em.persist(customer);
        Account account = Account.builder()
                .customer(customer)
                .code(2L)
                .balance(BigDecimal.ZERO)
                .creationDate(new Date())
                .build();

        repository.save(account);

        Assertions.assertNotNull(account.getId());

    }

    @Test
    void shouldReturnCurrentAccountCode() {
        Long code =  repository.findCurrentCode();
        Assertions.assertEquals(1, code);
    }

    @Test
    void shouldFindAccountByCode() {
        Optional<Account> bankAccount = repository.findByCode(1L);
        Assertions.assertNotNull(bankAccount.get());
        Assertions.assertEquals("96259371020",bankAccount.get().getCustomer().getCpf() );
    }

}
