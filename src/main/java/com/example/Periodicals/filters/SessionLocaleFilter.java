package com.example.Periodicals.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SessionLocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest) request;
        if(req.getParameter("sessionLocale")!=null){
            req.getSession().setAttribute("lang",req.getParameter("sessionLocale"));
            System.out.println("Have set new locale "+req.getParameter("sessionLocale"));
        }
        chain.doFilter(req,response);
    }

    @Override
    public void destroy() {
    }
}
