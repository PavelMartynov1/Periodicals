package com.example.Periodicals.Controller.validation.rules;

import com.example.Periodicals.Controller.validation.ValidationError;

public interface ValidationRule<E> {
    boolean checkRule(E input);
    ValidationError getError();
}
