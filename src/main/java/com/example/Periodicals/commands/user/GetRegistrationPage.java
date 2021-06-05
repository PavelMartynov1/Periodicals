package com.example.Periodicals.commands.user;

import com.example.Periodicals.commands.Command;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class GetRegistrationPage implements Command {
    ArrayList<String> users=new ArrayList<>();
    @Override
    public String execute(HttpServletRequest request) {
        String lang= (String) request.getSession(false).getAttribute("lang");
        System.out.println("Language in session is "+lang);
        return "/WEB-INF/jsp/registration.jsp";
    }
}
