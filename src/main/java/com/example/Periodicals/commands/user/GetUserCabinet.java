package com.example.Periodicals.commands.user;

import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.Subscription;
import com.example.Periodicals.model.entity.User;
import com.example.Periodicals.model.service.SubscriptionService;
import com.example.Periodicals.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetUserCabinet implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String page = "redirect:/index.jsp";
        if (session != null) {
            User user = (User) session.getAttribute("user");

            if (user != null) {
                List<Subscription> subs = new SubscriptionService().findUserSubs(user);
                request.setAttribute("balance", new UserService().getBalance(user));
                if (!subs.isEmpty()) {
                    request.setAttribute("subscriptions",subs);
                }
                page = "/WEB-INF/jsp/cabinet.jsp";
            }
        }
        return page;
    }
}
