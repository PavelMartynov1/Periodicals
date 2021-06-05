package com.example.Periodicals.Controller.validation.validators;

import com.example.Periodicals.Controller.validation.ValidationError;
import com.example.Periodicals.Controller.validation.rules.ValidationRule;

import java.util.LinkedList;
import java.util.List;

public abstract class Validator<R extends ValidationRule<E>,E> {

    private List<R> rules = new LinkedList<>();

    public List<ValidationError> validate(E inputEntity) {
        List<ValidationError> errors = new LinkedList<>();
        for(R rule:rules){
            if(!rule.checkRule(inputEntity)){
                errors.add(rule.getError());
            }
        }
        return errors;
    }

    void addRule(R rule) {
        rules.add(rule);
    }




}
