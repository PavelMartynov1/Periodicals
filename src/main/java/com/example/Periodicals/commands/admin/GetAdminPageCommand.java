package com.example.Periodicals.commands.admin;

import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.Role;
import com.example.Periodicals.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetAdminPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session= request.getSession(false);
        String page="redirect:/index.jsp";
        if(session!=null){
            User user=(User)session.getAttribute("user");
            if(user!=null & user.getRole().equals(Role.ADMIN) ){
                page="/WEB-INF/jsp/adminCab.jsp";
            }
        }
        return page;
    }
}
