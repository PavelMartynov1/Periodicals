package com.example.Periodicals.Controller.validation.validators;

import com.example.Periodicals.Controller.dto.RegInput;
import com.example.Periodicals.Controller.validation.rules.registration.EmailRule;
import com.example.Periodicals.Controller.validation.rules.registration.PasswordEqualityRule;
import com.example.Periodicals.Controller.validation.rules.registration.PasswordRule;
import com.example.Periodicals.Controller.validation.rules.registration.RegistrationRule;

public class RegistrationValidator extends Validator<RegistrationRule, RegInput> {


    public RegistrationValidator() {
        this.addRule(new EmailRule());
        this.addRule(new PasswordRule());
        this.addRule(new PasswordEqualityRule());
    }

}
