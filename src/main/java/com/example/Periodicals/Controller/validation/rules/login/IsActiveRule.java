package com.example.Periodicals.Controller.validation.rules.login;

import com.example.Periodicals.Controller.dto.LogInput;
import com.example.Periodicals.Controller.dto.RegInput;
import com.example.Periodicals.Controller.validation.ValidationError;
import com.example.Periodicals.model.service.UserService;


public class IsActiveRule implements LogInRule{


    @Override
    public boolean checkRule(RegInput input) {
        UserService userService=new UserService();
        return userService.userIsNotBanned(input.getEmail());
    }

    @Override
    public ValidationError getError() {
        return ValidationError.USER_IS_BANNED;
    }
}
