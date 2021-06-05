package com.example.Periodicals.commands.admin;

import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.Magazine;
import com.example.Periodicals.model.service.MagazineService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class GetMagDeletePageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String  page="";
        Long id= Long.valueOf(request.getParameter("magazine_id"));
        MagazineService magazineService=new MagazineService();
        Optional<Magazine> magazine =magazineService.find(id);
        if(magazine.isPresent()){
            request.setAttribute("magazine",magazine.get());
        page="/WEB-INF/jsp/delete_mag.jsp";
        } else{
            page="/app/getCatalog";
        }
        return page;
    }
}
