package com.example.Periodicals.commands.admin;

import com.example.Periodicals.commands.Command;
import com.example.Periodicals.exceptions.NoSuchUser;
import com.example.Periodicals.model.entity.Subscription;
import com.example.Periodicals.model.entity.User;
import com.example.Periodicals.model.service.SubscriptionService;
import com.example.Periodicals.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetUserInfoCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        Integer id= Integer.valueOf(request.getParameter("id"));
        if(id!=null) {
            UserService userService = new UserService();
            try {
                User user = userService.findById(id);
                request.setAttribute("user",user);
                List<Subscription> subs=new SubscriptionService().findUserSubs(user);
                request.setAttribute("subs",subs);
            } catch (NoSuchUser e){
                request.setAttribute("NotFound",e);
            }
        }
        return "/WEB-INF/jsp/user_info.jsp";
    }
}
