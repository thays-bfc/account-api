package com.bank.api.account.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CpfValidator implements ConstraintValidator<CpfConstraint, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {

        if (cpf == null || !Pattern.matches("[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}", cpf)) {
            return false;
        }

        cpf = cpf.replaceAll("[.-]", "");
        if (cpf.equals("00000000000") ||
            cpf.equals("11111111111") ||
            cpf.equals("22222222222") ||
            cpf.equals("33333333333") ||
            cpf.equals("44444444444") ||
            cpf.equals("55555555555") ||
            cpf.equals("66666666666") ||
            cpf.equals("77777777777") ||
            cpf.equals("88888888888") ||
            cpf.equals("99999999999")) {
            return false;
        }

        int sm = 0;
        int peso = 10;
        int i;
        int num;

        for (i = 0; i < 9; ++i) {
            num = cpf.charAt(i) - 48;
            sm += num * peso;
            --peso;
        }

        int r = 11 - sm % 11;
        char dig10;
        if (r != 10 && r != 11) {
            dig10 = (char) (r + 48);
        } else {
            dig10 = '0';
        }

        sm = 0;
        peso = 11;

        for (i = 0; i < 10; ++i) {
            num = cpf.charAt(i) - 48;
            sm += num * peso;
            --peso;
        }

        r = 11 - sm % 11;
        char dig11;
        if (r != 10 && r != 11) {
            dig11 = (char) (r + 48);
        } else {
            dig11 = '0';
        }

        return dig10 == cpf.charAt(9) && dig11 == cpf.charAt(10);

    }

}
