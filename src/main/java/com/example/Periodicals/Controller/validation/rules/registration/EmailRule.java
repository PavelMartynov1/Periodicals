package com.example.Periodicals.Controller.validation.rules.registration;

import com.example.Periodicals.Controller.dto.RegInput;
import com.example.Periodicals.Controller.validation.ValidationError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailRule implements  RegistrationRule{
    private Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
    @Override
    public boolean checkRule(RegInput input) {
        Matcher matcher=emailPattern.matcher(input.getEmail());
        return matcher.matches();
    }

    @Override
    public ValidationError getError() {
        return ValidationError.EMAIL;
    }
}
