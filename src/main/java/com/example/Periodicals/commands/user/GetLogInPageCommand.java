package com.example.Periodicals.commands.user;

import com.example.Periodicals.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetLogInPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String lang= (String) session.getAttribute("lang");
        System.out.println("Language in session is "+lang);
        return "/WEB-INF/jsp/login.jsp";
    }
}
