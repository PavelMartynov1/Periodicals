package com.example.Periodicals.commands.user;

import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.User;
import com.example.Periodicals.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class GetSubscribePage implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session= request.getSession();
        User user=(User)session.getAttribute("user");
        BigDecimal balance=new UserService().getBalance(user);
        return "/WEB-INF/jsp/subscribe.jsp";
    }
}
