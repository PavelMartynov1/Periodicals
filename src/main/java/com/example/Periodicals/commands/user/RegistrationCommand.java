package com.example.Periodicals.commands.user;

import com.example.Periodicals.Controller.dto.RegInput;
import com.example.Periodicals.Controller.validation.ValidationError;
import com.example.Periodicals.Controller.validation.ValidationFactory;
import com.example.Periodicals.Controller.validation.rules.registration.RegistrationRule;
import com.example.Periodicals.Controller.validation.validators.Validator;
import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.Gender;
import com.example.Periodicals.model.entity.User;
import com.example.Periodicals.model.service.ServiceFactory;
import com.example.Periodicals.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

public class RegistrationCommand implements Command {
    private final UserService userService = ServiceFactory.getUserService();
    private ValidationFactory validationFactory = ValidationFactory.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        String page=null;
        RegInput input = getInput(request);
        Validator<RegistrationRule, RegInput> validator = validationFactory.getRegistrationValidator();
        List<ValidationError> errors = validator.validate(input);
        if (errors.isEmpty()) {
            User user = userService.insertNewUser(input);
            if (user == null) {
                request.setAttribute("email",input.getEmail());
                request.setAttribute("emailOccupied",ValidationError.EMAIL_OCCUPIED);
                page="/app/regPage";
            } else {
                page="/app/getLogInPage";
            }
        } else {
            request.setAttribute("email", input.getEmail());
            request.setAttribute("errors", errors);
            request.setAttribute("emailError", ValidationError.EMAIL);
            request.setAttribute("passwordError", ValidationError.PASSWORD);
            request.setAttribute("passwordEqualityError", ValidationError.PASSWORD_EQUALITY);
            page = "/app/regPage";
        }
        return page;
    }

    private RegInput getInput(HttpServletRequest request) {
        return new RegInput.Builder().setEmail(request.getParameter("email"))
                .setFirstName(request.getParameter("first_name"))
                .setLastName(request.getParameter("last_name"))
                .setFirstPassword(request.getParameter("password"))
                .setSecondPassword(request.getParameter("password-repeat"))
                .setGender(Gender.valueOf(request.getParameter(
                        "gender").toUpperCase())).build();

    }
}
