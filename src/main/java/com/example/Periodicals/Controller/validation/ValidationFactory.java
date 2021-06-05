package com.example.Periodicals.Controller.validation;

import com.example.Periodicals.Controller.validation.validators.*;


public class ValidationFactory {
    private static ValidationFactory validationFactory;

    public static ValidationFactory getInstance() {
        if (validationFactory == null) {
            synchronized (ValidationFactory.class) {
                if (validationFactory == null) {
                    ValidationFactory temp = new ValidationFactory();
                    validationFactory = temp;
                }
            }
        }
        return validationFactory;

    }

    public RegistrationValidator getRegistrationValidator() {
        return new RegistrationValidator();
    }
    public LogInValidator getLogInValidator() {
        return new LogInValidator();
    }
    public MagazineCreationValidator getMagazineCreationValidator() {
        return new MagazineCreationValidator();
    }
    public AddingMoneyValidator getAddingMoneyValidator() {
        return new AddingMoneyValidator();
    }
    public CategoryValidator getCategoryValidator() {
        return new CategoryValidator();
    }

}
