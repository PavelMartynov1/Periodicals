package com.example.Periodicals.Controller.validation.rules.category;

import com.example.Periodicals.Controller.dto.CategoryInput;
import com.example.Periodicals.Controller.validation.ValidationError;

public class CategoryIsNewRule implements CategoryRule{
    @Override
    public boolean checkRule(CategoryInput input) {
        return false;
    }

    @Override
    public ValidationError getError() {
        return null;
    }
}
