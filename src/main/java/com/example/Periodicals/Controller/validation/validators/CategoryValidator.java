package com.example.Periodicals.Controller.validation.validators;

import com.example.Periodicals.Controller.dto.CategoryInput;
import com.example.Periodicals.Controller.validation.rules.category.CategoryDescriptionRule;
import com.example.Periodicals.Controller.validation.rules.category.CategoryNameRule;
import com.example.Periodicals.Controller.validation.rules.category.CategoryRule;

public class CategoryValidator extends Validator<CategoryRule, CategoryInput>{
    public CategoryValidator() {
        this.addRule(new CategoryDescriptionRule());
        this.addRule(new CategoryNameRule());
    }
}
