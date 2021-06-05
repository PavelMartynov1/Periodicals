package com.example.Periodicals.listeners;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.File;


@WebListener
public class ContextListener implements ServletContextListener {

    public ContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        ServletContext context = sce.getServletContext();
//        String log4jConfigFile = context.getInitParameter("log4j-configuration-loc");
//        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
//
//        PropertyConfigurator.configure(fullPath);
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }


}
