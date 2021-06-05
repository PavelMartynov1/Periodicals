package com.example.Periodicals.Controller.validation.validators;

import com.example.Periodicals.Controller.dto.LogInput;
import com.example.Periodicals.Controller.dto.RegInput;
import com.example.Periodicals.Controller.validation.rules.login.IsActiveRule;
import com.example.Periodicals.Controller.validation.rules.login.LogInRule;
import com.example.Periodicals.Controller.validation.rules.login.EmailRule;
import com.example.Periodicals.Controller.validation.rules.login.PasswordRule;

public class LogInValidator extends Validator<LogInRule, RegInput> {
    public LogInValidator() {
        this.addRule(new EmailRule());
        this.addRule(new PasswordRule());
        this.addRule(new IsActiveRule());
    }
}
