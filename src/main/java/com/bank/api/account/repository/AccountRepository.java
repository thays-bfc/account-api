package com.bank.api.account.repository;

import com.bank.api.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(value="SELECT coalesce(max(ac.code), 0) FROM Account ac")
    Long findNextCode();

    Optional<Account> findByCode(Long code);
}
