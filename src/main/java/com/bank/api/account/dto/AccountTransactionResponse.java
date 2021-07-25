package com.bank.api.account.dto;

import com.bank.api.account.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionResponse {
    private Long accountCode;
    private TransactionType transactionType;
    private BigDecimal transactionValue;
    private BigDecimal accountBalance;
}
