package com.bank.api.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferForm {
    @NotNull
    private Long fromAccountCode;
    @NotNull
    private Long toAccountCode;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal transferValue;
}
