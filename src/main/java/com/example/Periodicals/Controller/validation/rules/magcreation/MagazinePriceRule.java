package com.example.Periodicals.Controller.validation.rules.magcreation;

import com.example.Periodicals.Controller.dto.MagazineCreationInput;
import com.example.Periodicals.Controller.validation.ValidationError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MagazinePriceRule implements MagCreationRule {
    private Pattern pricePattern= Pattern.compile("^\\d+(\\.\\d{2})?$");
    @Override
    public boolean checkRule(MagazineCreationInput input) {
        Matcher matcher=pricePattern.matcher(input.getPrice());
        return matcher.matches();
    }

    @Override
    public ValidationError getError() {
        return ValidationError.PERIODICAL_COST;
    }
}
