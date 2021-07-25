package com.bank.api.account.util.message;

public interface SystemConstants {
    interface Customer {
        String VALID_CPF = "CPF inválido.";
        String CUSTOMER_EXISTS = "Já existe cadastro para o CPF informado.";
    }
    interface Account {
        String NOT_FOUND = "Conta não encontrada.";
    }
    interface AccountTransaction {
        String MAX_VALUE = "2000";
        String MAX_VALUE_EXCEEDED = "Valor máximo %s para depósito excedido.";
    }
}
