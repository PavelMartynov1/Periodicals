package com.example.Periodicals.Controller.validation.rules.registration;

import com.example.Periodicals.Controller.dto.RegInput;
import com.example.Periodicals.Controller.validation.ValidationError;

public class PasswordEqualityRule implements  RegistrationRule {
    @Override
    public boolean checkRule(RegInput input) {
        String firstPassword = input.getFirstPassword();
        String secondPassword = input.getSecondPassword();
        return firstPassword.equals(secondPassword);
    }

    @Override
    public ValidationError getError() {
        return ValidationError.PASSWORD_EQUALITY;
    }
}
