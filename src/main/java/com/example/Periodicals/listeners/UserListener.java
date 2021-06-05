package com.example.Periodicals.listeners;
import com.example.Periodicals.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class UserListener implements HttpSessionListener{
    private static final Logger LOGGER = LogManager.getLogger();

    public UserListener() {
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session=se.getSession();
        User user=(User)(session.getAttribute("user"));
        List<String> users_emails=(ArrayList)session.getServletContext().getAttribute("users");
        if(user!=null){
            users_emails.remove(user.getEmail());
            LOGGER.info("Session has been destroyed for user "+ user.getEmail());
        }else{
            LOGGER.info("Session has been destroyed without user ");
        }

    }




}
