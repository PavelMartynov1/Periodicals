package com.example.Periodicals.commands.user;

import com.example.Periodicals.Controller.dto.RegInput;
import com.example.Periodicals.Controller.validation.ValidationError;
import com.example.Periodicals.Controller.validation.ValidationFactory;
import com.example.Periodicals.Controller.validation.rules.login.LogInRule;
import com.example.Periodicals.Controller.validation.rules.registration.RegistrationRule;
import com.example.Periodicals.Controller.validation.validators.Validator;
import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.Subscription;
import com.example.Periodicals.model.entity.User;
import com.example.Periodicals.model.service.ServiceFactory;
import com.example.Periodicals.model.service.SubscriptionService;
import com.example.Periodicals.model.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LogInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private ValidationFactory validationFactory = ValidationFactory.getInstance();
    ServletContext context;
    private final UserService userService = ServiceFactory.getUserService();
    ArrayList<String> users = new ArrayList<>();

    @Override
    public String execute(HttpServletRequest request) {
        this.context = request.getServletContext();
        String page = "";
        RegInput input = getInput(request);
        Validator<LogInRule, RegInput> validator = validationFactory.getLogInValidator();
        List<ValidationError> errors = validator.validate(input);
        if (errors.isEmpty()) {
            users = (ArrayList<String>) context.getAttribute("users");
            if (users.contains(input.getEmail())) {
                LOGGER.info("user already logged in " + input.getEmail());
                request.setAttribute("userLoggedIn",true);
                return "/app/getLogInPage";
            }
            page = validate(request, input);
        } else{
            request.setAttribute("errors",errors);
            page="/app/getLogInPage";
        }


        return page;
    }


    private String validate(HttpServletRequest request, RegInput input) {

        String page ;
        Optional<User> user = userService.findUser(input);
        if (user.isPresent()) {
            if (userService.checkPassword(user.get(), input.getFirstPassword())) {
                HttpSession session = request.getSession();
                List<Subscription> subs = new SubscriptionService().findUserSubs(user.get());
                if (!subs.isEmpty()) {
                    user.get().setSubs(subs);
                }
                session.setAttribute("user", user.get());
                users = (ArrayList<String>) context.getAttribute("users");
                users.add(user.get().getEmail());
                page = "redirect:/index.jsp";
            } else {

                request.setAttribute("passErr", ValidationError.PASSWORD);
                page = "/app/getLogInPage";
            }
        } else {
            request.setAttribute("emailNotFound", ValidationError.EMAIL_NOT_FOUND);
            page = "/app/getLogInPage";
            LOGGER.debug("email was not found ");
        }

        return page;
    }

    private RegInput getInput(HttpServletRequest request) {
        return new RegInput.Builder().setEmail(request.getParameter("email"))
                .setFirstPassword(request.getParameter("password"))
                .build();

    }
}
