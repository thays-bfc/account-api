package com.bank.api.account.util.mapper;

import com.bank.api.account.dto.AccountTransactionResponse;
import com.bank.api.account.dto.TransactionResponse;
import com.bank.api.account.model.Account;
import com.bank.api.account.model.AccountTransaction;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountTransactionMapper {
    private final MapperFacade facade;

    public AccountTransactionMapper(MapperFactory factory) {
        factory.classMap(AccountTransaction.class,AccountTransactionResponse.class)
                .field("account.code","accountCode")
                .field("account.balance","accountBalance")
                .field("type.description","transactions[0].type")
                .field("value","transactions[0].value")
                .field("associatedAccount.customer.name","transactions[0].associatedAccountName")
                .register();

        factory.classMap(AccountTransaction.class,TransactionResponse.class)
                .field("type.description","type")
                .field("value","value")
                .field("associatedAccount.customer.name","associatedAccountName")
                .register();

        factory.classMap(Account.class,AccountTransactionResponse.class)
                .field("code","accountCode")
                .field("balance","accountBalance")
                .register();

        this.facade = factory.getMapperFacade();
    }

    public AccountTransactionResponse mapFrom(AccountTransaction from) {
      return facade.map(from,AccountTransactionResponse.class);
    }

    public TransactionResponse mapFromTransaction(AccountTransaction from) {
        return facade.map(from,TransactionResponse.class);
    }

    public AccountTransactionResponse mapFromAccount(Account from) {
        return facade.map(from,AccountTransactionResponse.class);
    }
}
