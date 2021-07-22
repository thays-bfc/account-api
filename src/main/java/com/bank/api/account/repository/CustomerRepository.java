package com.bank.api.account.repository;

import com.bank.api.account.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customer, Long> {
    Customer findByCpf(String cpf);
    Boolean existsByCpf(String cpf);
}
