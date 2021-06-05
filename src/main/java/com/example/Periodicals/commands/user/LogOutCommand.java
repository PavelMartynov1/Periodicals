package com.example.Periodicals.commands.user;

import com.example.Periodicals.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session=request.getSession(false);
//        ArrayList<User> users=(ArrayList<User>) request.getServletContext().getAttribute("users");
//        System.out.println("Logged in users "+ Arrays.toString(new ArrayList[]{users}));
//        User user=(User)session.getAttribute("user");
//        users.remove(user);
        if(session!=null){
            session.invalidate();
        }
        return "";
    }
}
