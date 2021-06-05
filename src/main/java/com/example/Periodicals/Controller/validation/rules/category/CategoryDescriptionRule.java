package com.example.Periodicals.Controller.validation.rules.category;

import com.example.Periodicals.Controller.dto.CategoryInput;
import com.example.Periodicals.Controller.validation.ValidationError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CategoryDescriptionRule implements CategoryRule{
    private Pattern descrPattern = Pattern.compile("^[a-zA-Zа-яА-Я\\s]+[\\d]*$");
    @Override
    public boolean checkRule(CategoryInput input) {
        Matcher matcher=descrPattern.matcher(input.getDescription());
        return matcher.matches();
    }

    @Override
    public ValidationError getError() {
        return ValidationError.CATEGORY_DESCRIPTION;
    }
}
