package com.bank.api.account.repository;

import com.bank.api.account.model.Account;
import com.bank.api.account.model.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {

    List<AccountTransaction> findByAccount(Account account);
}
