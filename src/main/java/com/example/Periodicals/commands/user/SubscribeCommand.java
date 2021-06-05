package com.example.Periodicals.commands.user;

import com.example.Periodicals.commands.Command;
import com.example.Periodicals.exceptions.NotEnoughFunds;
import com.example.Periodicals.model.dao.impl.JDBCSubscriptionDao;
import com.example.Periodicals.model.entity.Subscription;
import com.example.Periodicals.model.entity.User;
import com.example.Periodicals.model.service.SubscriptionService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SubscribeCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        User user = (User) (session.getAttribute("user"));
        if (user != null) {
            page=subscribe(user, request);
        }

        return page;
    }

    private String subscribe(User user, HttpServletRequest request) {
        Long id= Long.valueOf(request.getParameter("id"));
        SubscriptionService service=new SubscriptionService();

        if(!service.checkUserSubscription(user,id)){
            try {
                Optional<Subscription> subscription = service.subscribeUser(user, id);
                if (subscription.isPresent()) {
                    user.getSubs().add(subscription.get());
                    LOGGER.info("subscribed");
                }
            } catch(NotEnoughFunds e ){
                request.setAttribute("notEnoughFunds",e);
                return "/WEB-INF/jsp/subscribe.jsp";
            }
        }else{
            LOGGER.info("already subscribed");
            request.setAttribute("alreadySubscribed",true);
            return "/WEB-INF/jsp/subscribe.jsp";
        }
    return "redirect:/getUserCabinet";

    }
}
