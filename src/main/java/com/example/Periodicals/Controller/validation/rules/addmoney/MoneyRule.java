package com.example.Periodicals.Controller.validation.rules.addmoney;

import com.example.Periodicals.Controller.dto.PaymentInput;
import com.example.Periodicals.Controller.validation.ValidationError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoneyRule implements AddMoneyRule{
    private Pattern moneyPattern= Pattern.compile("^\\d+(\\.\\d{2})?$");
    @Override
    public boolean checkRule(PaymentInput input) {
        Matcher matcher=moneyPattern.matcher(input.getMoney());
        return matcher.matches();
    }

    @Override
    public ValidationError getError() {
        return ValidationError.MONEY;
    }
}
