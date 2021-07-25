package com.bank.api.account.util.mapper;

import com.bank.api.account.dto.AccountTransactionResponse;
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
                .field("type","transactionType")
                .field("value","transactionValue")
                .field("account.balance","accountBalance")
                .register();

        this.facade = factory.getMapperFacade();
    }

    public AccountTransactionResponse mapFrom(AccountTransaction from) {
      return   facade.map(from,AccountTransactionResponse.class);
    }
}
