package com.example.Periodicals.Controller.validation.validators;

import com.example.Periodicals.Controller.dto.PaymentInput;
import com.example.Periodicals.Controller.validation.rules.addmoney.AddMoneyRule;
import com.example.Periodicals.Controller.validation.rules.addmoney.MoneyRule;

public class AddingMoneyValidator extends Validator<AddMoneyRule, PaymentInput>{
    public AddingMoneyValidator() {
        this.addRule(new MoneyRule());
    }
}
