package com.bank.api.account.util.mapper;

import com.bank.api.account.dto.AccountResponse;
import com.bank.api.account.model.Account;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;

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
        try {
            javax.swing.text.MaskFormatter mask = new javax.swing.text.MaskFormatter("###.###.###-##");
            javax.swing.JFormattedTextField cpfMask = new javax.swing.JFormattedTextField(mask);
            cpfMask.setText(cpf);
            return cpfMask.getText();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cpf;
    }
}
