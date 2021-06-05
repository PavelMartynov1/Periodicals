package com.example.Periodicals.commands.admin;

import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.Magazine;
import com.example.Periodicals.model.entity.User;
import com.example.Periodicals.model.service.MagazineService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class MagDeletionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page="/app/getCatalog";
        Long magazine_id= Long.valueOf(request.getParameter("magazine_id"));
        MagazineService magazineService=new MagazineService();
        Optional<Magazine> magazine= magazineService.find(magazine_id);
        if(magazine.isPresent()){
            User user = (User) request.getSession().getAttribute("user");
            boolean result = magazineService.delete(magazine.get(), user);
            if (result) {
                request.setAttribute("Deleted", true);
                page = "redirect:/getMagDeletePage?magazine_id=" + magazine.get().getId();
           // page="/app/getCatalog";
            }
        }
        return page;
    }
}
