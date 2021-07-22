package com.bank.api.account.util.message;

public interface SystemConstants {
    interface Customer {
        String VALID_CPF = "CPF inválido.";
        String CUSTOMER_EXISTS = "Já existe cadastro para o CPF informado.";
    }
    interface Account {
        String NOT_FOUND = "Conta não encontrada.";
    }
}
