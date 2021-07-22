package com.bank.api.account.dto;

import com.bank.api.account.util.validator.CpfConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerForm {
    @NotNull
    @NotEmpty
    @CpfConstraint
    private String cpf;

    @NotNull
    @NotEmpty
    @Length(min = 5)
    private String name;
}
