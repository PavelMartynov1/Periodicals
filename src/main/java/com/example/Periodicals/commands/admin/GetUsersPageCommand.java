package com.example.Periodicals.commands.admin;

import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.Role;
import com.example.Periodicals.model.entity.User;
import com.example.Periodicals.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetUsersPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = "/WEB-INF/jsp/ActiveUsers.jsp";
        List<User> list=null;
        if (request.getParameter("command") != null) {
           list= filter(request);
            request.setAttribute("users",list);
           return page;
        }
        list=new UserService().findAll();
        request.setAttribute("users",list);
        return page;
    }

    private List<User> filter(HttpServletRequest request) {
        String mail = request.getParameter("mail");
        UserService userService = new UserService();
       return userService.findByMail(mail);
    }
}
