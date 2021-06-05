package com.example.Periodicals.Controller;

import com.example.Periodicals.commands.*;
import com.example.Periodicals.commands.admin.*;
import com.example.Periodicals.commands.user.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;



public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<String, Command> commands = new HashMap<>();
    private List<String> users_emails=new ArrayList<>();
    public void init(){
        commands.put("regPage", new GetRegistrationPage());
        commands.put("register", new RegistrationCommand());
        commands.put("login",new LogInCommand());
        commands.put("getLogInPage",new GetLogInPageCommand());
        commands.put("logOut",new LogOutCommand());
        commands.put("getUserCabinet",new GetUserCabinet());
        commands.put("admin",new GetAdminPageCommand());
        commands.put("getLoggedInUsers",new GetUsersPageCommand());
        commands.put("getMagCreationPage",new GetMagaZineCreationPageCommand());
        commands.put("add_magazine",new AddMagazineCommand());
        commands.put("addCategoryPage",new AddCategoryPageCommand());
        commands.put("add_category",new AddCategoryCommand());
        commands.put("getCatalog",new GetCatalogPageCommand());
        commands.put("get_mag_info",new GetMagPageCommand());
        commands.put("get_subscribe_page",new GetSubscribePage());
        commands.put("subscribe",new SubscribeCommand());
        commands.put("get_money_page",new GetMoneyPageCommand());
        commands.put("add_money",new AddMoneyCommand());
        commands.put("get_user_info",new GetUserInfoCommand());
        commands.put("change_user_status",new ChangeUserStatusCommand());
        commands.put("get_edit_page",new GetMagazineEditPageCommand());
        commands.put("edit_magazine",new EditMagazineCommand());
        commands.put("getMagDeletePage",new GetMagDeletePageCommand());
        commands.put("delete_magazine",new MagDeletionCommand());
        getServletContext().setAttribute("users",users_emails);
        }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getRequestURI();

        path = path.replaceAll(".*/app/" , "");

        Command command = commands.getOrDefault(path ,
                new DefaultCommand());
        String page = command.execute(request);
        if(page.contains("redirect:")){
            response.sendRedirect(page.replace("redirect:", "/Periodicals/app"));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

}
