package com.example.Periodicals.Controller.validation.rules.magcreation;

import com.example.Periodicals.Controller.dto.MagazineCreationInput;
import com.example.Periodicals.Controller.validation.ValidationError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MagazineDescriptionRule implements MagCreationRule {
    private Pattern textPattern = Pattern.compile("^[a-zA-Zа-яА-Я\\s\\d\\-]*$");
    @Override
    public boolean checkRule(MagazineCreationInput input) {
        Matcher matcher = textPattern.matcher(input.getDescription());
        return matcher.matches();
    }

    @Override
    public ValidationError getError() {

        return ValidationError.PERIODICAL_DESCRIPTION;
    }
}
