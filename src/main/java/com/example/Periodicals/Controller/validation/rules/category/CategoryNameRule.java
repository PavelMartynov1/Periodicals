package com.example.Periodicals.Controller.validation.rules.category;

import com.example.Periodicals.Controller.dto.CategoryInput;
import com.example.Periodicals.Controller.validation.ValidationError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CategoryNameRule implements  CategoryRule{
    private Pattern namePattern = Pattern.compile("^[a-zA-Zа-яА-Я\\s]+[\\d]*$");
    @Override
    public boolean checkRule(CategoryInput input) {
        Matcher matcher=namePattern.matcher(input.getName());
        return matcher.matches();
    }

    @Override
    public ValidationError getError() {
        return ValidationError.CATEGORY_NAME;
    }
}
