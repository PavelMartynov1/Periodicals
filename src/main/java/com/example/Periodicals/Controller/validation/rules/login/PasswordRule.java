package com.example.Periodicals.Controller.validation.rules.login;

import com.example.Periodicals.Controller.dto.LogInput;
import com.example.Periodicals.Controller.dto.RegInput;
import com.example.Periodicals.Controller.validation.ValidationError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PasswordRule implements LogInRule {
    private Pattern passwordPattern= Pattern.compile("^[a-zA-Z]\\w{3,14}$");
    @Override
    public boolean checkRule(RegInput input) {
        String password = input.getFirstPassword();
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    @Override
    public ValidationError getError() {
        return ValidationError.PASSWORD;
    }
}
