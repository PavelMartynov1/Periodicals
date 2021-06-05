package com.example.Periodicals.commands.user;

import com.example.Periodicals.Controller.dto.MagazineCreationInput;
import com.example.Periodicals.Controller.dto.PaymentInput;
import com.example.Periodicals.Controller.validation.ValidationError;
import com.example.Periodicals.Controller.validation.ValidationFactory;
import com.example.Periodicals.Controller.validation.rules.addmoney.AddMoneyRule;
import com.example.Periodicals.Controller.validation.rules.addmoney.MoneyRule;
import com.example.Periodicals.Controller.validation.rules.magcreation.MagCreationRule;
import com.example.Periodicals.Controller.validation.validators.Validator;
import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.Payment;
import com.example.Periodicals.model.entity.User;
import com.example.Periodicals.model.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public class AddMoneyCommand implements Command {
    private ValidationFactory validationFactory = ValidationFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public String execute(HttpServletRequest request) {
        String page=null;
        page=add_money(request);

        return page;
    }

    private String add_money(HttpServletRequest request) {
        String page="/WEB-INF/jsp/money_page.jsp";
        PaymentInput payment=buildPayment(request);
        UserService service=new UserService();
        Validator<AddMoneyRule, PaymentInput> validator=validationFactory.getAddingMoneyValidator();
        List<ValidationError> errors = validator.validate(payment);
        if(!errors.isEmpty()){
            request.setAttribute("errors",errors);
            return page;
        }
        if(service.addMoney(payment)) {
            request.setAttribute("transfer_status", "transfer complete");
        } else {
            LOGGER.info("transfer is not complete");
            request.setAttribute("transfer_status", "transfer is not complete");
        }
        return page;
    }

    private PaymentInput buildPayment(HttpServletRequest request) {
    PaymentInput paymentInput=new PaymentInput();
    paymentInput.setUser((User)request.getSession().getAttribute("user"));
    paymentInput.setMoney(request.getParameter("money_amount"));
    return paymentInput;
    }

}
