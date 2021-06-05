package com.example.Periodicals.commands.admin;

import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.Status;
import com.example.Periodicals.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class ChangeUserStatusCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String command=request.getParameter("command");
        Long id= Long.valueOf(request.getParameter("user_id"));
        if(command!=null){
            changeStatus(command,id);
        }
        return "redirect:/getLoggedInUsers";
    }

    private void changeStatus(String command,long user_id) {
   //add validation for command
        Status status=Status.valueOf(command);
        UserService userService=new UserService();
        userService.setUserStatus(status,user_id);
    }
}
