package com.bank.api.account.util.mapper;

import com.bank.api.account.dto.AccountResponse;
import com.bank.api.account.model.Account;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class AccountMapper {
    private final MapperFacade facade;

    public AccountMapper(MapperFactory factory) {
        factory.classMap(Account.class, AccountResponse.class)
                .byDefault()
                .field("customer.cpf","cpf")
                .field("customer.name","name")
        .register();
        this.facade = factory.getMapperFacade();
    }

    public AccountResponse mapFrom(Account from) {
        AccountResponse accountResponse = facade.map(from, AccountResponse.class);
        if(!ObjectUtils.isEmpty(accountResponse.getCpf())){
            accountResponse.setCpf(cpfFormatter(accountResponse.getCpf()));
        }
        return accountResponse;
    }

    private String cpfFormatter(String cpf) {
        if(cpf.length()==11)
            return cpf.substring(0,3) + "."+cpf.substring(3,6)+"."+cpf.substring(6,9)+"-"+cpf.substring(9,11);

        return cpf;
    }
}
