package com.example.Periodicals.filters;

import com.example.Periodicals.model.entity.Role;
import com.example.Periodicals.model.entity.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "SecurityFilter")
public class SecurityFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String HOME_PAGE = "/Periodicals/app/index.jsp";
    Set<String> userPages = new HashSet<>();
    Set<String> guestPages = new HashSet<>();
    Set<String> adminPages = new HashSet<>();

    public void init(FilterConfig config) throws ServletException {
        userPages.add("/index.jsp");
        userPages.add("/register");
        userPages.add("/logOut");
        userPages.add("/getUserCabinet");
        userPages.add("");
        userPages.add("/getCatalog");
        userPages.add("/get_mag_info");
        userPages.add("/get_subscribe_page");
        userPages.add("/subscribe");
        userPages.add("/get_money_page");
        userPages.add("/add_money");

        guestPages.add("/register");
        guestPages.add("/getLogInPage");
        guestPages.add("/login");
        guestPages.add("/regPage");
        guestPages.add("");
        guestPages.add("/index.jsp");
        guestPages.add("/getCatalog");
        guestPages.add("/get_mag_info");

        adminPages.add("/admin");
        adminPages.add("/index.jsp");
        adminPages.add("/getLoggedInUsers");
        adminPages.add("");
        adminPages.add("/logOut");
        adminPages.add("/getMagCreationPage");
        adminPages.add("/add_magazine");
        adminPages.add("/addCategoryPage");
        adminPages.add("/add_category");
        adminPages.add("/getCatalog");
        adminPages.add("/get_mag_info");
        adminPages.add("/get_user_info");
        adminPages.add("/change_user_status");
        adminPages.add("/get_edit_page");
        adminPages.add("/edit_magazine");
        adminPages.add("/getMagDeletePage");
        adminPages.add("/delete_magazine");


    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        String command = getCurrentUrlFromRequest(request);
        HttpSession session = getSession(request);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                boolean isAdmin = isAdmin(user);
                if (isAdmin) {
                    if (!adminPages.contains(command)) {
                        LOGGER.info("Access denied for admin "+user.getEmail());
                        ((HttpServletResponse) response).sendError(403);
                        return;
                    }
                }
                if(isUser(user)) {
                    if (!userPages.contains(command)) {
                        LOGGER.info("Access denied for user "+user.getEmail());
                        //((HttpServletRequest)request).getRequestDispatcher("/index.jsp").forward(request,response);
                        ((HttpServletResponse)response).sendRedirect(HOME_PAGE);
                        return;
                    }
                }
            }
            else if (!guestPages.contains(command)) {
               // ((HttpServletResponse) response).sendRedirect(HOME_PAGE);
                ((HttpServletResponse) response).sendError(403);
                return;
            }

        }
        chain.doFilter(request, response);
    }

    public boolean isAdmin(User user) {
        return user.getRole().equals(Role.ADMIN);
    }

    public boolean isUser(User user) {
        return user.getRole().equals(Role.USER);
    }

//    public boolean isQuest(User user) {
//        return user.getRole().equals(Role.GUEST);
//    }

    public static HttpSession getSession(ServletRequest request) {
        if (!(request instanceof HttpServletRequest))
            return null;
        return ((HttpServletRequest) request).getSession();
    }

    public static String getCurrentUrlFromRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest))
            return null;
        return getCurrentUrlFromRequest((HttpServletRequest) request);
    }

    public static String getCurrentUrlFromRequest(HttpServletRequest request) {
        StringBuffer command = request.getRequestURL();
        return command.toString().replaceAll(".*/app", "");
    }
}
