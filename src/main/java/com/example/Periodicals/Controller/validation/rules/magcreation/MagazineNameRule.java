package com.example.Periodicals.Controller.validation.rules.magcreation;

import com.example.Periodicals.Controller.dto.MagazineCreationInput;
import com.example.Periodicals.Controller.validation.ValidationError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MagazineNameRule implements  MagCreationRule{
    private Pattern namePattern = Pattern.compile("^[a-zA-Zа-яА-Я\\s\\d]*$");
    @Override
    public boolean checkRule(MagazineCreationInput input) {
        Matcher nameMatcher=namePattern.matcher(input.getName());
        return nameMatcher.matches();
    }

    @Override
    public ValidationError getError() {
        return ValidationError.PERIODICAL_NAME;
    }
}
